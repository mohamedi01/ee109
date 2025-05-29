#include "structs.hpp"
#include <stdint.h>
#include <sys/time.h>
#include <iostream>
#include <fstream>
#include <string> 
#include <sstream> 
#include <stdarg.h>
#include <signal.h>
#include <sys/wait.h>
#include <pwd.h>
#include <map>
#include <math.h>
#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <errno.h>
#ifndef MAX_CYCLES
#define MAX_CYCLES 10000000000
#endif
#include "FringeContext.h"
#include "functions.hpp"
#include "ArgAPI.hpp"
#include "Fixed.hpp"
#include <vector>
using std::vector;

#ifndef ZYNQ
typedef __int128 int128_t;
#endif

void printHelp(); 

using namespace std;

void Application(int numThreads, vector<string> * args) {
  // Create an execution context.
  FringeContext *c1 = new FringeContext("./verilog/accel.bit.bin");
  c1->load();
  vector<double>* x249 = new vector<double>(9);
  (*x249)[0] = -1;
  (*x249)[1] = -0.75;
  (*x249)[2] = -0.5;
  (*x249)[3] = -0.25;
  (*x249)[4] = 0;
  (*x249)[5] = 0.25;
  (*x249)[6] = 0.5;
  (*x249)[7] = 0.75;
  (*x249)[8] = 1;
  uint64_t x250 = c1->malloc(sizeof(double) * 9);
  c1->setArg(INDRAM_ptr, x250, false);
  printf("Allocate mem of size 9 at %p\n", (void*)x250);
  uint64_t x251 = c1->malloc(sizeof(double) * 9);
  c1->setArg(OUTDRAM_ptr, x251, false);
  printf("Allocate mem of size 9 at %p\n", (void*)x251);
  vector<int32_t>* x252_rawified = new vector<int32_t>((*x249).size());
  for (int x252_rawified_i = 0; x252_rawified_i < (*x249).size(); x252_rawified_i++) {
    (*x252_rawified)[x252_rawified_i] = (int32_t) ((*x249)[x252_rawified_i] * ((int32_t)1 << 8));
  }
  c1->memcpy(x250, &(*x252_rawified)[0], (*x252_rawified).size() * sizeof(int32_t));
// Register ArgIns and ArgIOs in case some are unused
c1->setNumArgIns(0 + 2 + 0);
c1->setNumArgIOs(0);
c1->setNumArgOuts(0);
c1->setNumArgOutInstrs(38);
c1->setNumEarlyExits(0);
c1->flushCache(1024);
time_t tstart = time(0);
c1->run();
time_t tend = time(0);
double elapsed = difftime(tend, tstart);
std::cout << "Kernel done, test run time = " << elapsed << " ms" << std::endl;
c1->flushCache(1024);
std::ofstream instrumentation ("./instrumentation.txt");
// Need to instrument List((x213,1), (x289,2), (x264,3), (x288,3), (x274,4), (x287,4), (x308,2), (x341,2), (x340,3), (x335,4), (x320,5), (x334,5), (x339,4))
std::cout << "ArgIns:" <<  " "  << ", ArgIOs:" <<  " "  << std::endl;
if (instrumentation.is_open()) {
instrumentation << "ArgIns:" <<  " "  << ", ArgIOs:" <<  " "  << std::endl;
}
 // immediate parent hashmap now Map(1 -> x213), current node x213 is at depth 1
long x213_cycles = c1->getArg(X213_cycles_arg, false);
long x213_iters = c1->getArg(X213_iters_arg, false);
long x213_iters_per_parent = x213_iters / std::max((long)1,x213_iters);
long x213_avg = x213_cycles / std::max((long)1,x213_iters);
std::cout << "  x213 - " << x213_avg << " (" << x213_cycles << " / " << x213_iters << ") ";
std::cout << "[" << x213_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "  x213 - " << x213_avg << " (" << x213_cycles << " / " << x213_iters << ") ";
instrumentation << "[" << x213_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x289, 1 -> x213), current node x289 is at depth 2
long x289_cycles = c1->getArg(X289_cycles_arg, false);
long x289_iters = c1->getArg(X289_iters_arg, false);
long x289_iters_per_parent = x289_iters / std::max((long)1,x213_iters);
long x289_avg = x289_cycles / std::max((long)1,x289_iters);
std::cout << "    x289 - " << x289_avg << " (" << x289_cycles << " / " << x289_iters << ") ";
std::cout << "[" << x289_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "    x289 - " << x289_avg << " (" << x289_cycles << " / " << x289_iters << ") ";
instrumentation << "[" << x289_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x289, 1 -> x213, 3 -> x264), current node x264 is at depth 3
long x264_cycles = c1->getArg(X264_cycles_arg, false);
long x264_iters = c1->getArg(X264_iters_arg, false);
long x264_iters_per_parent = x264_iters / std::max((long)1,x289_iters);
long x264_avg = x264_cycles / std::max((long)1,x264_iters);
std::cout << "      x264 - " << x264_avg << " (" << x264_cycles << " / " << x264_iters << ") ";
std::cout << "[" << x264_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x264 - " << x264_avg << " (" << x264_cycles << " / " << x264_iters << ") ";
instrumentation << "[" << x264_iters_per_parent << " iters/parent execution]";
}
long x264_stalled = c1->getArg(X264_stalled_arg, false);
long x264_idle = c1->getArg(X264_idle_arg, false);
std::cout << " <# stalled: " << x264_stalled << ", #idle: " << x264_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x264_stalled << ", # idle: " << x264_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x289, 1 -> x213, 3 -> x288), current node x288 is at depth 3
long x288_cycles = c1->getArg(X288_cycles_arg, false);
long x288_iters = c1->getArg(X288_iters_arg, false);
long x288_iters_per_parent = x288_iters / std::max((long)1,x289_iters);
long x288_avg = x288_cycles / std::max((long)1,x288_iters);
std::cout << "      x288 - " << x288_avg << " (" << x288_cycles << " / " << x288_iters << ") ";
std::cout << "[" << x288_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x288 - " << x288_avg << " (" << x288_cycles << " / " << x288_iters << ") ";
instrumentation << "[" << x288_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x289, 4 -> x274, 1 -> x213, 3 -> x288), current node x274 is at depth 4
long x274_cycles = c1->getArg(X274_cycles_arg, false);
long x274_iters = c1->getArg(X274_iters_arg, false);
long x274_iters_per_parent = x274_iters / std::max((long)1,x288_iters);
long x274_avg = x274_cycles / std::max((long)1,x274_iters);
std::cout << "        x274 - " << x274_avg << " (" << x274_cycles << " / " << x274_iters << ") ";
std::cout << "[" << x274_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "        x274 - " << x274_avg << " (" << x274_cycles << " / " << x274_iters << ") ";
instrumentation << "[" << x274_iters_per_parent << " iters/parent execution]";
}
long x274_stalled = c1->getArg(X274_stalled_arg, false);
long x274_idle = c1->getArg(X274_idle_arg, false);
std::cout << " <# stalled: " << x274_stalled << ", #idle: " << x274_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x274_stalled << ", # idle: " << x274_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x289, 4 -> x287, 1 -> x213, 3 -> x288), current node x287 is at depth 4
long x287_cycles = c1->getArg(X287_cycles_arg, false);
long x287_iters = c1->getArg(X287_iters_arg, false);
long x287_iters_per_parent = x287_iters / std::max((long)1,x288_iters);
long x287_avg = x287_cycles / std::max((long)1,x287_iters);
std::cout << "        x287 - " << x287_avg << " (" << x287_cycles << " / " << x287_iters << ") ";
std::cout << "[" << x287_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "        x287 - " << x287_avg << " (" << x287_cycles << " / " << x287_iters << ") ";
instrumentation << "[" << x287_iters_per_parent << " iters/parent execution]";
}
long x287_stalled = c1->getArg(X287_stalled_arg, false);
long x287_idle = c1->getArg(X287_idle_arg, false);
std::cout << " <# stalled: " << x287_stalled << ", #idle: " << x287_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x287_stalled << ", # idle: " << x287_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x308, 4 -> x287, 1 -> x213, 3 -> x288), current node x308 is at depth 2
long x308_cycles = c1->getArg(X308_cycles_arg, false);
long x308_iters = c1->getArg(X308_iters_arg, false);
long x308_iters_per_parent = x308_iters / std::max((long)1,x213_iters);
long x308_avg = x308_cycles / std::max((long)1,x308_iters);
std::cout << "    x308 - " << x308_avg << " (" << x308_cycles << " / " << x308_iters << ") ";
std::cout << "[" << x308_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "    x308 - " << x308_avg << " (" << x308_cycles << " / " << x308_iters << ") ";
instrumentation << "[" << x308_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x341, 4 -> x287, 1 -> x213, 3 -> x288), current node x341 is at depth 2
long x341_cycles = c1->getArg(X341_cycles_arg, false);
long x341_iters = c1->getArg(X341_iters_arg, false);
long x341_iters_per_parent = x341_iters / std::max((long)1,x213_iters);
long x341_avg = x341_cycles / std::max((long)1,x341_iters);
std::cout << "    x341 - " << x341_avg << " (" << x341_cycles << " / " << x341_iters << ") ";
std::cout << "[" << x341_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "    x341 - " << x341_avg << " (" << x341_cycles << " / " << x341_iters << ") ";
instrumentation << "[" << x341_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x341, 4 -> x287, 1 -> x213, 3 -> x340), current node x340 is at depth 3
long x340_cycles = c1->getArg(X340_cycles_arg, false);
long x340_iters = c1->getArg(X340_iters_arg, false);
long x340_iters_per_parent = x340_iters / std::max((long)1,x341_iters);
long x340_avg = x340_cycles / std::max((long)1,x340_iters);
std::cout << "      x340 - " << x340_avg << " (" << x340_cycles << " / " << x340_iters << ") ";
std::cout << "[" << x340_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x340 - " << x340_avg << " (" << x340_cycles << " / " << x340_iters << ") ";
instrumentation << "[" << x340_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x341, 4 -> x335, 1 -> x213, 3 -> x340), current node x335 is at depth 4
long x335_cycles = c1->getArg(X335_cycles_arg, false);
long x335_iters = c1->getArg(X335_iters_arg, false);
long x335_iters_per_parent = x335_iters / std::max((long)1,x340_iters);
long x335_avg = x335_cycles / std::max((long)1,x335_iters);
std::cout << "        x335 - " << x335_avg << " (" << x335_cycles << " / " << x335_iters << ") ";
std::cout << "[" << x335_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "        x335 - " << x335_avg << " (" << x335_cycles << " / " << x335_iters << ") ";
instrumentation << "[" << x335_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x341, 5 -> x320, 4 -> x335, 1 -> x213, 3 -> x340), current node x320 is at depth 5
long x320_cycles = c1->getArg(X320_cycles_arg, false);
long x320_iters = c1->getArg(X320_iters_arg, false);
long x320_iters_per_parent = x320_iters / std::max((long)1,x335_iters);
long x320_avg = x320_cycles / std::max((long)1,x320_iters);
std::cout << "          x320 - " << x320_avg << " (" << x320_cycles << " / " << x320_iters << ") ";
std::cout << "[" << x320_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x320 - " << x320_avg << " (" << x320_cycles << " / " << x320_iters << ") ";
instrumentation << "[" << x320_iters_per_parent << " iters/parent execution]";
}
long x320_stalled = c1->getArg(X320_stalled_arg, false);
long x320_idle = c1->getArg(X320_idle_arg, false);
std::cout << " <# stalled: " << x320_stalled << ", #idle: " << x320_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x320_stalled << ", # idle: " << x320_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x341, 5 -> x334, 4 -> x335, 1 -> x213, 3 -> x340), current node x334 is at depth 5
long x334_cycles = c1->getArg(X334_cycles_arg, false);
long x334_iters = c1->getArg(X334_iters_arg, false);
long x334_iters_per_parent = x334_iters / std::max((long)1,x335_iters);
long x334_avg = x334_cycles / std::max((long)1,x334_iters);
std::cout << "          x334 - " << x334_avg << " (" << x334_cycles << " / " << x334_iters << ") ";
std::cout << "[" << x334_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x334 - " << x334_avg << " (" << x334_cycles << " / " << x334_iters << ") ";
instrumentation << "[" << x334_iters_per_parent << " iters/parent execution]";
}
long x334_stalled = c1->getArg(X334_stalled_arg, false);
long x334_idle = c1->getArg(X334_idle_arg, false);
std::cout << " <# stalled: " << x334_stalled << ", #idle: " << x334_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x334_stalled << ", # idle: " << x334_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x341, 5 -> x334, 4 -> x339, 1 -> x213, 3 -> x340), current node x339 is at depth 4
long x339_cycles = c1->getArg(X339_cycles_arg, false);
long x339_iters = c1->getArg(X339_iters_arg, false);
long x339_iters_per_parent = x339_iters / std::max((long)1,x340_iters);
long x339_avg = x339_cycles / std::max((long)1,x339_iters);
std::cout << "        x339 - " << x339_avg << " (" << x339_cycles << " / " << x339_iters << ") ";
std::cout << "[" << x339_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "        x339 - " << x339_avg << " (" << x339_cycles << " / " << x339_iters << ") ";
instrumentation << "[" << x339_iters_per_parent << " iters/parent execution]";
}
long x339_stalled = c1->getArg(X339_stalled_arg, false);
long x339_idle = c1->getArg(X339_idle_arg, false);
std::cout << " <# stalled: " << x339_stalled << ", #idle: " << x339_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x339_stalled << ", # idle: " << x339_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
instrumentation.close();
vector<double>* x342 = new vector<double>(9);
vector<int32_t>* x343_rawified = new vector<int32_t>((*x342).size());
c1->memcpy(&(*x343_rawified)[0], x251, (*x343_rawified).size() * sizeof(int32_t));
for (int x342_i = 0; x342_i < (*x342).size(); x342_i++) {
int32_t x342_tmp = (*x343_rawified)[x342_i];
(*x342)[x342_i] = (double) x342_tmp / ((int32_t)1 << 8);
}
vector<double>* x356 = new vector<double>((*x249).size());
for (int b29 = 0; b29 < (*x249).size(); b29++) { 
double x344 = (*x249)[b29];
double x345 = x344 * 32767;
bool x346 = x345 < -32768;
double x349;
if (x346) { 
x349 = -32768;
}
else {
bool x347 = 32767 < x345;
double x348;
if (x347) { 
  x348 = 32767;
}
else {
  x348 = x345;
}
x349 = x348;
}
bool x350 = x349 < 0;
double x353;
if (x350) { 
double x351 = x349 - 0.5;
x353 = x351;
}
else {
double x352 = x349 + 0.5;
x353 = x352;
}
double x354 = -x353;
double x355 = x354 / pow(2.,-1);
(*x356)[b29] = x355;
}
vector<bool>* x362 = new vector<bool>((*x356).size());
for (int b43 = 0; b43 < (*x356).size(); b43++) { 
double x357 = (*x356)[b43];
double x358 = (*x342)[b43];
double x359 = x357 - x358;
double x360 = fabs(x359);
bool x361 = x360 < 0.0078125;
(*x362)[b43] = x361;
}
bool x365;
if ((*x362).size() > 0) { // Hack to handle reductions on things of length 0
x365 = (*x362)[0];
}
else {
x365 = 0;
}
for (int b50 = 1; b50 < (*x362).size(); b50++) {
bool b51 = (*x362)[b50];
bool b52 = x365;
bool x364 = b51 & b52;
x365 = x364;
}
string x366 = x365 ? string("true") : string("false");
string x367 = (string("PASS: ") + x366);
string x368 = (x367 + string("\n"));
std::cout << x368;
string x370 = ("\n=================\n" + (string("QuantizeKernelTest.scala:50:11: Assertion failure") + "\n=================\n"));
if (true) { std::cout << std::flush; ASSERT(x365, "%s", x370.c_str()); }
delete c1;
}

void printHelp() {
fprintf(stderr, "Help for app: QuantizeKernelTest\n");
fprintf(stderr, "  -- Args:    <No input args>\n");
fprintf(stderr, "    -- Example: bash run.sh 128\n");
exit(1);
}

int main(int argc, char *argv[]) {
vector<string> *args = new vector<string>(argc-1);
for (int i=1; i<argc; i++) {
(*args)[i-1] = std::string(argv[i]);
if (std::string(argv[i]) == "--help" | std::string(argv[i]) == "-h") {printHelp();}
}
int numThreads = 1;
char *env_threads = getenv("DELITE_NUM_THREADS");
if (env_threads != NULL) { numThreads = atoi(env_threads); } else {
  fprintf(stderr, "[WARNING]: DELITE_NUM_THREADS undefined, defaulting to 1\n");
}
fprintf(stderr, "Executing with %d thread(s)\n", numThreads);
Application(numThreads, args);
return 0;
}
