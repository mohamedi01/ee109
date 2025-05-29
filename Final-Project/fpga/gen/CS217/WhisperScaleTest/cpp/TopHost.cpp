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
  vector<double>* x248 = new vector<double>(4);
  (*x248)[0] = 0;
  (*x248)[1] = -4;
  (*x248)[2] = 4;
  (*x248)[3] = -2;
  vector<double>* x249 = new vector<double>(4);
  (*x249)[0] = 1;
  (*x249)[1] = 0;
  (*x249)[2] = 2;
  (*x249)[3] = 0.5;
  uint64_t x250 = c1->malloc(sizeof(double) * 4);
  c1->setArg(INDRAM_ptr, x250, false);
  printf("Allocate mem of size 4 at %p\n", (void*)x250);
  uint64_t x251 = c1->malloc(sizeof(double) * 4);
  c1->setArg(OUTDRAM_ptr, x251, false);
  printf("Allocate mem of size 4 at %p\n", (void*)x251);
  vector<int32_t>* x252_rawified = new vector<int32_t>((*x248).size());
  for (int x252_rawified_i = 0; x252_rawified_i < (*x248).size(); x252_rawified_i++) {
    (*x252_rawified)[x252_rawified_i] = (int32_t) ((*x248)[x252_rawified_i] * ((int32_t)1 << 8));
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
// Need to instrument List((x201,1), (x288,2), (x263,3), (x287,3), (x273,4), (x286,4), (x298,2), (x331,2), (x330,3), (x325,4), (x310,5), (x324,5), (x329,4))
std::cout << "ArgIns:" <<  " "  << ", ArgIOs:" <<  " "  << std::endl;
if (instrumentation.is_open()) {
instrumentation << "ArgIns:" <<  " "  << ", ArgIOs:" <<  " "  << std::endl;
}
 // immediate parent hashmap now Map(1 -> x201), current node x201 is at depth 1
long x201_cycles = c1->getArg(X201_cycles_arg, false);
long x201_iters = c1->getArg(X201_iters_arg, false);
long x201_iters_per_parent = x201_iters / std::max((long)1,x201_iters);
long x201_avg = x201_cycles / std::max((long)1,x201_iters);
std::cout << "  x201 - " << x201_avg << " (" << x201_cycles << " / " << x201_iters << ") ";
std::cout << "[" << x201_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "  x201 - " << x201_avg << " (" << x201_cycles << " / " << x201_iters << ") ";
instrumentation << "[" << x201_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x288, 1 -> x201), current node x288 is at depth 2
long x288_cycles = c1->getArg(X288_cycles_arg, false);
long x288_iters = c1->getArg(X288_iters_arg, false);
long x288_iters_per_parent = x288_iters / std::max((long)1,x201_iters);
long x288_avg = x288_cycles / std::max((long)1,x288_iters);
std::cout << "    x288 - " << x288_avg << " (" << x288_cycles << " / " << x288_iters << ") ";
std::cout << "[" << x288_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "    x288 - " << x288_avg << " (" << x288_cycles << " / " << x288_iters << ") ";
instrumentation << "[" << x288_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x288, 1 -> x201, 3 -> x263), current node x263 is at depth 3
long x263_cycles = c1->getArg(X263_cycles_arg, false);
long x263_iters = c1->getArg(X263_iters_arg, false);
long x263_iters_per_parent = x263_iters / std::max((long)1,x288_iters);
long x263_avg = x263_cycles / std::max((long)1,x263_iters);
std::cout << "      x263 - " << x263_avg << " (" << x263_cycles << " / " << x263_iters << ") ";
std::cout << "[" << x263_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x263 - " << x263_avg << " (" << x263_cycles << " / " << x263_iters << ") ";
instrumentation << "[" << x263_iters_per_parent << " iters/parent execution]";
}
long x263_stalled = c1->getArg(X263_stalled_arg, false);
long x263_idle = c1->getArg(X263_idle_arg, false);
std::cout << " <# stalled: " << x263_stalled << ", #idle: " << x263_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x263_stalled << ", # idle: " << x263_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x288, 1 -> x201, 3 -> x287), current node x287 is at depth 3
long x287_cycles = c1->getArg(X287_cycles_arg, false);
long x287_iters = c1->getArg(X287_iters_arg, false);
long x287_iters_per_parent = x287_iters / std::max((long)1,x288_iters);
long x287_avg = x287_cycles / std::max((long)1,x287_iters);
std::cout << "      x287 - " << x287_avg << " (" << x287_cycles << " / " << x287_iters << ") ";
std::cout << "[" << x287_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x287 - " << x287_avg << " (" << x287_cycles << " / " << x287_iters << ") ";
instrumentation << "[" << x287_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x288, 4 -> x273, 1 -> x201, 3 -> x287), current node x273 is at depth 4
long x273_cycles = c1->getArg(X273_cycles_arg, false);
long x273_iters = c1->getArg(X273_iters_arg, false);
long x273_iters_per_parent = x273_iters / std::max((long)1,x287_iters);
long x273_avg = x273_cycles / std::max((long)1,x273_iters);
std::cout << "        x273 - " << x273_avg << " (" << x273_cycles << " / " << x273_iters << ") ";
std::cout << "[" << x273_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "        x273 - " << x273_avg << " (" << x273_cycles << " / " << x273_iters << ") ";
instrumentation << "[" << x273_iters_per_parent << " iters/parent execution]";
}
long x273_stalled = c1->getArg(X273_stalled_arg, false);
long x273_idle = c1->getArg(X273_idle_arg, false);
std::cout << " <# stalled: " << x273_stalled << ", #idle: " << x273_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x273_stalled << ", # idle: " << x273_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x288, 4 -> x286, 1 -> x201, 3 -> x287), current node x286 is at depth 4
long x286_cycles = c1->getArg(X286_cycles_arg, false);
long x286_iters = c1->getArg(X286_iters_arg, false);
long x286_iters_per_parent = x286_iters / std::max((long)1,x287_iters);
long x286_avg = x286_cycles / std::max((long)1,x286_iters);
std::cout << "        x286 - " << x286_avg << " (" << x286_cycles << " / " << x286_iters << ") ";
std::cout << "[" << x286_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "        x286 - " << x286_avg << " (" << x286_cycles << " / " << x286_iters << ") ";
instrumentation << "[" << x286_iters_per_parent << " iters/parent execution]";
}
long x286_stalled = c1->getArg(X286_stalled_arg, false);
long x286_idle = c1->getArg(X286_idle_arg, false);
std::cout << " <# stalled: " << x286_stalled << ", #idle: " << x286_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x286_stalled << ", # idle: " << x286_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x298, 4 -> x286, 1 -> x201, 3 -> x287), current node x298 is at depth 2
long x298_cycles = c1->getArg(X298_cycles_arg, false);
long x298_iters = c1->getArg(X298_iters_arg, false);
long x298_iters_per_parent = x298_iters / std::max((long)1,x201_iters);
long x298_avg = x298_cycles / std::max((long)1,x298_iters);
std::cout << "    x298 - " << x298_avg << " (" << x298_cycles << " / " << x298_iters << ") ";
std::cout << "[" << x298_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "    x298 - " << x298_avg << " (" << x298_cycles << " / " << x298_iters << ") ";
instrumentation << "[" << x298_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x331, 4 -> x286, 1 -> x201, 3 -> x287), current node x331 is at depth 2
long x331_cycles = c1->getArg(X331_cycles_arg, false);
long x331_iters = c1->getArg(X331_iters_arg, false);
long x331_iters_per_parent = x331_iters / std::max((long)1,x201_iters);
long x331_avg = x331_cycles / std::max((long)1,x331_iters);
std::cout << "    x331 - " << x331_avg << " (" << x331_cycles << " / " << x331_iters << ") ";
std::cout << "[" << x331_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "    x331 - " << x331_avg << " (" << x331_cycles << " / " << x331_iters << ") ";
instrumentation << "[" << x331_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x331, 4 -> x286, 1 -> x201, 3 -> x330), current node x330 is at depth 3
long x330_cycles = c1->getArg(X330_cycles_arg, false);
long x330_iters = c1->getArg(X330_iters_arg, false);
long x330_iters_per_parent = x330_iters / std::max((long)1,x331_iters);
long x330_avg = x330_cycles / std::max((long)1,x330_iters);
std::cout << "      x330 - " << x330_avg << " (" << x330_cycles << " / " << x330_iters << ") ";
std::cout << "[" << x330_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x330 - " << x330_avg << " (" << x330_cycles << " / " << x330_iters << ") ";
instrumentation << "[" << x330_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x331, 4 -> x325, 1 -> x201, 3 -> x330), current node x325 is at depth 4
long x325_cycles = c1->getArg(X325_cycles_arg, false);
long x325_iters = c1->getArg(X325_iters_arg, false);
long x325_iters_per_parent = x325_iters / std::max((long)1,x330_iters);
long x325_avg = x325_cycles / std::max((long)1,x325_iters);
std::cout << "        x325 - " << x325_avg << " (" << x325_cycles << " / " << x325_iters << ") ";
std::cout << "[" << x325_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "        x325 - " << x325_avg << " (" << x325_cycles << " / " << x325_iters << ") ";
instrumentation << "[" << x325_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x331, 5 -> x310, 4 -> x325, 1 -> x201, 3 -> x330), current node x310 is at depth 5
long x310_cycles = c1->getArg(X310_cycles_arg, false);
long x310_iters = c1->getArg(X310_iters_arg, false);
long x310_iters_per_parent = x310_iters / std::max((long)1,x325_iters);
long x310_avg = x310_cycles / std::max((long)1,x310_iters);
std::cout << "          x310 - " << x310_avg << " (" << x310_cycles << " / " << x310_iters << ") ";
std::cout << "[" << x310_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x310 - " << x310_avg << " (" << x310_cycles << " / " << x310_iters << ") ";
instrumentation << "[" << x310_iters_per_parent << " iters/parent execution]";
}
long x310_stalled = c1->getArg(X310_stalled_arg, false);
long x310_idle = c1->getArg(X310_idle_arg, false);
std::cout << " <# stalled: " << x310_stalled << ", #idle: " << x310_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x310_stalled << ", # idle: " << x310_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x331, 5 -> x324, 4 -> x325, 1 -> x201, 3 -> x330), current node x324 is at depth 5
long x324_cycles = c1->getArg(X324_cycles_arg, false);
long x324_iters = c1->getArg(X324_iters_arg, false);
long x324_iters_per_parent = x324_iters / std::max((long)1,x325_iters);
long x324_avg = x324_cycles / std::max((long)1,x324_iters);
std::cout << "          x324 - " << x324_avg << " (" << x324_cycles << " / " << x324_iters << ") ";
std::cout << "[" << x324_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x324 - " << x324_avg << " (" << x324_cycles << " / " << x324_iters << ") ";
instrumentation << "[" << x324_iters_per_parent << " iters/parent execution]";
}
long x324_stalled = c1->getArg(X324_stalled_arg, false);
long x324_idle = c1->getArg(X324_idle_arg, false);
std::cout << " <# stalled: " << x324_stalled << ", #idle: " << x324_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x324_stalled << ", # idle: " << x324_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x331, 5 -> x324, 4 -> x329, 1 -> x201, 3 -> x330), current node x329 is at depth 4
long x329_cycles = c1->getArg(X329_cycles_arg, false);
long x329_iters = c1->getArg(X329_iters_arg, false);
long x329_iters_per_parent = x329_iters / std::max((long)1,x330_iters);
long x329_avg = x329_cycles / std::max((long)1,x329_iters);
std::cout << "        x329 - " << x329_avg << " (" << x329_cycles << " / " << x329_iters << ") ";
std::cout << "[" << x329_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "        x329 - " << x329_avg << " (" << x329_cycles << " / " << x329_iters << ") ";
instrumentation << "[" << x329_iters_per_parent << " iters/parent execution]";
}
long x329_stalled = c1->getArg(X329_stalled_arg, false);
long x329_idle = c1->getArg(X329_idle_arg, false);
std::cout << " <# stalled: " << x329_stalled << ", #idle: " << x329_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x329_stalled << ", # idle: " << x329_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
instrumentation.close();
vector<double>* x332 = new vector<double>(4);
vector<int32_t>* x333_rawified = new vector<int32_t>((*x332).size());
c1->memcpy(&(*x333_rawified)[0], x251, (*x333_rawified).size() * sizeof(int32_t));
for (int x332_i = 0; x332_i < (*x332).size(); x332_i++) {
int32_t x332_tmp = (*x333_rawified)[x332_i];
(*x332)[x332_i] = (double) x332_tmp / ((int32_t)1 << 8);
}
double x334 = (*x332)[0];
double x335 = (*x249)[0];
double x336 = x334 - x335;
double x337 = (*x332)[1];
double x338 = (*x249)[1];
double x339 = x337 - x338;
double x340 = (*x332)[2];
double x341 = (*x249)[2];
double x342 = x340 - x341;
double x343 = (*x332)[3];
double x344 = (*x249)[3];
double x345 = x343 - x344;
bool x346 = x336 < 0;
double x348;
if (x346) { 
double x347 = -x336;
x348 = x347;
}
else {
x348 = x336;
}
bool x349 = x339 < 0;
double x351;
if (x349) { 
double x350 = -x339;
x351 = x350;
}
else {
x351 = x339;
}
bool x352 = x342 < 0;
double x354;
if (x352) { 
double x353 = -x342;
x354 = x353;
}
else {
x354 = x342;
}
bool x355 = x345 < 0;
double x357;
if (x355) { 
double x356 = -x345;
x357 = x356;
}
else {
x357 = x345;
}
bool x358 = x348 < 0.0078125;
bool x359 = x351 < 0.0078125;
bool x360 = x358 & x359;
bool x361 = x354 < 0.0078125;
bool x362 = x360 & x361;
bool x363 = x357 < 0.0078125;
bool x364 = x362 & x363;
int32_t x365;
if (x364) { 
x365 = 1;
}
else {
x365 = 0;
}
string x366 = std::to_string(x365);
string x367 = (string("PASS: ") + x366);
string x368 = (x367 + string("\n"));
std::cout << x368;
bool x370 = x365 == 1;
string x371 = ("\n=================\n" + (string("WhisperScaleTest.scala:39:11: Assertion failure") + "\n=================\n"));
if (true) { std::cout << std::flush; ASSERT(x370, "%s", x371.c_str()); }
delete c1;
}

void printHelp() {
fprintf(stderr, "Help for app: WhisperScaleTest\n");
fprintf(stderr, "  -- Args:    <No input args>\n");
fprintf(stderr, "    -- Example: bash run.sh \n");
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
