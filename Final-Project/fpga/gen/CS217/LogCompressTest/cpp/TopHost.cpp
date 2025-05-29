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
  vector<double>* x289 = new vector<double>(2);
  (*x289)[0] = 10;
  (*x289)[1] = 0.0078125;
  vector<double>* x290 = new vector<double>(2);
  (*x290)[0] = 1;
  (*x290)[1] = -2;
  uint64_t x291 = c1->malloc(sizeof(double) * 2);
  c1->setArg(INDRAM_ptr, x291, false);
  printf("Allocate mem of size 2 at %p\n", (void*)x291);
  uint64_t x292 = c1->malloc(sizeof(double) * 2);
  c1->setArg(OUTDRAM_ptr, x292, false);
  printf("Allocate mem of size 2 at %p\n", (void*)x292);
  vector<int32_t>* x293_rawified = new vector<int32_t>((*x289).size());
  for (int x293_rawified_i = 0; x293_rawified_i < (*x289).size(); x293_rawified_i++) {
    (*x293_rawified)[x293_rawified_i] = (int32_t) ((*x289)[x293_rawified_i] * ((int32_t)1 << 8));
  }
  c1->memcpy(x291, &(*x293_rawified)[0], (*x293_rawified).size() * sizeof(int32_t));
// Register ArgIns and ArgIOs in case some are unused
c1->setNumArgIns(0 + 2 + 0);
c1->setNumArgIOs(0);
c1->setNumArgOuts(0);
c1->setNumArgOutInstrs(44);
c1->setNumEarlyExits(0);
c1->flushCache(1024);
time_t tstart = time(0);
c1->run();
time_t tend = time(0);
double elapsed = difftime(tend, tstart);
std::cout << "Kernel done, test run time = " << elapsed << " ms" << std::endl;
c1->flushCache(1024);
std::ofstream instrumentation ("./instrumentation.txt");
// Need to instrument List((x439,1), (x330,2), (x305,3), (x329,3), (x315,4), (x328,4), (x343,2), (x438,2), (x354,3), (x366,3), (x399,2), (x398,3), (x393,4), (x378,5), (x392,5), (x397,4))
std::cout << "ArgIns:" <<  " "  << ", ArgIOs:" <<  " "  << std::endl;
if (instrumentation.is_open()) {
instrumentation << "ArgIns:" <<  " "  << ", ArgIOs:" <<  " "  << std::endl;
}
 // immediate parent hashmap now Map(1 -> x439), current node x439 is at depth 1
long x439_cycles = c1->getArg(X439_cycles_arg, false);
long x439_iters = c1->getArg(X439_iters_arg, false);
long x439_iters_per_parent = x439_iters / std::max((long)1,x439_iters);
long x439_avg = x439_cycles / std::max((long)1,x439_iters);
std::cout << "  x439 - " << x439_avg << " (" << x439_cycles << " / " << x439_iters << ") ";
std::cout << "[" << x439_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "  x439 - " << x439_avg << " (" << x439_cycles << " / " << x439_iters << ") ";
instrumentation << "[" << x439_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x330, 1 -> x439), current node x330 is at depth 2
long x330_cycles = c1->getArg(X330_cycles_arg, false);
long x330_iters = c1->getArg(X330_iters_arg, false);
long x330_iters_per_parent = x330_iters / std::max((long)1,x439_iters);
long x330_avg = x330_cycles / std::max((long)1,x330_iters);
std::cout << "    x330 - " << x330_avg << " (" << x330_cycles << " / " << x330_iters << ") ";
std::cout << "[" << x330_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "    x330 - " << x330_avg << " (" << x330_cycles << " / " << x330_iters << ") ";
instrumentation << "[" << x330_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x330, 1 -> x439, 3 -> x305), current node x305 is at depth 3
long x305_cycles = c1->getArg(X305_cycles_arg, false);
long x305_iters = c1->getArg(X305_iters_arg, false);
long x305_iters_per_parent = x305_iters / std::max((long)1,x330_iters);
long x305_avg = x305_cycles / std::max((long)1,x305_iters);
std::cout << "      x305 - " << x305_avg << " (" << x305_cycles << " / " << x305_iters << ") ";
std::cout << "[" << x305_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x305 - " << x305_avg << " (" << x305_cycles << " / " << x305_iters << ") ";
instrumentation << "[" << x305_iters_per_parent << " iters/parent execution]";
}
long x305_stalled = c1->getArg(X305_stalled_arg, false);
long x305_idle = c1->getArg(X305_idle_arg, false);
std::cout << " <# stalled: " << x305_stalled << ", #idle: " << x305_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x305_stalled << ", # idle: " << x305_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x330, 1 -> x439, 3 -> x329), current node x329 is at depth 3
long x329_cycles = c1->getArg(X329_cycles_arg, false);
long x329_iters = c1->getArg(X329_iters_arg, false);
long x329_iters_per_parent = x329_iters / std::max((long)1,x330_iters);
long x329_avg = x329_cycles / std::max((long)1,x329_iters);
std::cout << "      x329 - " << x329_avg << " (" << x329_cycles << " / " << x329_iters << ") ";
std::cout << "[" << x329_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x329 - " << x329_avg << " (" << x329_cycles << " / " << x329_iters << ") ";
instrumentation << "[" << x329_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x330, 4 -> x315, 1 -> x439, 3 -> x329), current node x315 is at depth 4
long x315_cycles = c1->getArg(X315_cycles_arg, false);
long x315_iters = c1->getArg(X315_iters_arg, false);
long x315_iters_per_parent = x315_iters / std::max((long)1,x329_iters);
long x315_avg = x315_cycles / std::max((long)1,x315_iters);
std::cout << "        x315 - " << x315_avg << " (" << x315_cycles << " / " << x315_iters << ") ";
std::cout << "[" << x315_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "        x315 - " << x315_avg << " (" << x315_cycles << " / " << x315_iters << ") ";
instrumentation << "[" << x315_iters_per_parent << " iters/parent execution]";
}
long x315_stalled = c1->getArg(X315_stalled_arg, false);
long x315_idle = c1->getArg(X315_idle_arg, false);
std::cout << " <# stalled: " << x315_stalled << ", #idle: " << x315_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x315_stalled << ", # idle: " << x315_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x330, 4 -> x328, 1 -> x439, 3 -> x329), current node x328 is at depth 4
long x328_cycles = c1->getArg(X328_cycles_arg, false);
long x328_iters = c1->getArg(X328_iters_arg, false);
long x328_iters_per_parent = x328_iters / std::max((long)1,x329_iters);
long x328_avg = x328_cycles / std::max((long)1,x328_iters);
std::cout << "        x328 - " << x328_avg << " (" << x328_cycles << " / " << x328_iters << ") ";
std::cout << "[" << x328_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "        x328 - " << x328_avg << " (" << x328_cycles << " / " << x328_iters << ") ";
instrumentation << "[" << x328_iters_per_parent << " iters/parent execution]";
}
long x328_stalled = c1->getArg(X328_stalled_arg, false);
long x328_idle = c1->getArg(X328_idle_arg, false);
std::cout << " <# stalled: " << x328_stalled << ", #idle: " << x328_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x328_stalled << ", # idle: " << x328_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x343, 4 -> x328, 1 -> x439, 3 -> x329), current node x343 is at depth 2
long x343_cycles = c1->getArg(X343_cycles_arg, false);
long x343_iters = c1->getArg(X343_iters_arg, false);
long x343_iters_per_parent = x343_iters / std::max((long)1,x439_iters);
long x343_avg = x343_cycles / std::max((long)1,x343_iters);
std::cout << "    x343 - " << x343_avg << " (" << x343_cycles << " / " << x343_iters << ") ";
std::cout << "[" << x343_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "    x343 - " << x343_avg << " (" << x343_cycles << " / " << x343_iters << ") ";
instrumentation << "[" << x343_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x438, 4 -> x328, 1 -> x439, 3 -> x329), current node x438 is at depth 2
long x438_cycles = c1->getArg(X438_cycles_arg, false);
long x438_iters = c1->getArg(X438_iters_arg, false);
long x438_iters_per_parent = x438_iters / std::max((long)1,x439_iters);
long x438_avg = x438_cycles / std::max((long)1,x438_iters);
std::cout << "    x438 - " << x438_avg << " (" << x438_cycles << " / " << x438_iters << ") ";
std::cout << "[" << x438_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "    x438 - " << x438_avg << " (" << x438_cycles << " / " << x438_iters << ") ";
instrumentation << "[" << x438_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x438, 4 -> x328, 1 -> x439, 3 -> x354), current node x354 is at depth 3
long x354_cycles = c1->getArg(X354_cycles_arg, false);
long x354_iters = c1->getArg(X354_iters_arg, false);
long x354_iters_per_parent = x354_iters / std::max((long)1,x438_iters);
long x354_avg = x354_cycles / std::max((long)1,x354_iters);
std::cout << "      x354 - " << x354_avg << " (" << x354_cycles << " / " << x354_iters << ") ";
std::cout << "[" << x354_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x354 - " << x354_avg << " (" << x354_cycles << " / " << x354_iters << ") ";
instrumentation << "[" << x354_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x438, 4 -> x328, 1 -> x439, 3 -> x366), current node x366 is at depth 3
long x366_cycles = c1->getArg(X366_cycles_arg, false);
long x366_iters = c1->getArg(X366_iters_arg, false);
long x366_iters_per_parent = x366_iters / std::max((long)1,x438_iters);
long x366_avg = x366_cycles / std::max((long)1,x366_iters);
std::cout << "      x366 - " << x366_avg << " (" << x366_cycles << " / " << x366_iters << ") ";
std::cout << "[" << x366_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x366 - " << x366_avg << " (" << x366_cycles << " / " << x366_iters << ") ";
instrumentation << "[" << x366_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x399, 4 -> x328, 1 -> x439, 3 -> x366), current node x399 is at depth 2
long x399_cycles = c1->getArg(X399_cycles_arg, false);
long x399_iters = c1->getArg(X399_iters_arg, false);
long x399_iters_per_parent = x399_iters / std::max((long)1,x439_iters);
long x399_avg = x399_cycles / std::max((long)1,x399_iters);
std::cout << "    x399 - " << x399_avg << " (" << x399_cycles << " / " << x399_iters << ") ";
std::cout << "[" << x399_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "    x399 - " << x399_avg << " (" << x399_cycles << " / " << x399_iters << ") ";
instrumentation << "[" << x399_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x399, 4 -> x328, 1 -> x439, 3 -> x398), current node x398 is at depth 3
long x398_cycles = c1->getArg(X398_cycles_arg, false);
long x398_iters = c1->getArg(X398_iters_arg, false);
long x398_iters_per_parent = x398_iters / std::max((long)1,x399_iters);
long x398_avg = x398_cycles / std::max((long)1,x398_iters);
std::cout << "      x398 - " << x398_avg << " (" << x398_cycles << " / " << x398_iters << ") ";
std::cout << "[" << x398_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x398 - " << x398_avg << " (" << x398_cycles << " / " << x398_iters << ") ";
instrumentation << "[" << x398_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x399, 4 -> x393, 1 -> x439, 3 -> x398), current node x393 is at depth 4
long x393_cycles = c1->getArg(X393_cycles_arg, false);
long x393_iters = c1->getArg(X393_iters_arg, false);
long x393_iters_per_parent = x393_iters / std::max((long)1,x398_iters);
long x393_avg = x393_cycles / std::max((long)1,x393_iters);
std::cout << "        x393 - " << x393_avg << " (" << x393_cycles << " / " << x393_iters << ") ";
std::cout << "[" << x393_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "        x393 - " << x393_avg << " (" << x393_cycles << " / " << x393_iters << ") ";
instrumentation << "[" << x393_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x399, 5 -> x378, 4 -> x393, 1 -> x439, 3 -> x398), current node x378 is at depth 5
long x378_cycles = c1->getArg(X378_cycles_arg, false);
long x378_iters = c1->getArg(X378_iters_arg, false);
long x378_iters_per_parent = x378_iters / std::max((long)1,x393_iters);
long x378_avg = x378_cycles / std::max((long)1,x378_iters);
std::cout << "          x378 - " << x378_avg << " (" << x378_cycles << " / " << x378_iters << ") ";
std::cout << "[" << x378_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x378 - " << x378_avg << " (" << x378_cycles << " / " << x378_iters << ") ";
instrumentation << "[" << x378_iters_per_parent << " iters/parent execution]";
}
long x378_stalled = c1->getArg(X378_stalled_arg, false);
long x378_idle = c1->getArg(X378_idle_arg, false);
std::cout << " <# stalled: " << x378_stalled << ", #idle: " << x378_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x378_stalled << ", # idle: " << x378_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x399, 5 -> x392, 4 -> x393, 1 -> x439, 3 -> x398), current node x392 is at depth 5
long x392_cycles = c1->getArg(X392_cycles_arg, false);
long x392_iters = c1->getArg(X392_iters_arg, false);
long x392_iters_per_parent = x392_iters / std::max((long)1,x393_iters);
long x392_avg = x392_cycles / std::max((long)1,x392_iters);
std::cout << "          x392 - " << x392_avg << " (" << x392_cycles << " / " << x392_iters << ") ";
std::cout << "[" << x392_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x392 - " << x392_avg << " (" << x392_cycles << " / " << x392_iters << ") ";
instrumentation << "[" << x392_iters_per_parent << " iters/parent execution]";
}
long x392_stalled = c1->getArg(X392_stalled_arg, false);
long x392_idle = c1->getArg(X392_idle_arg, false);
std::cout << " <# stalled: " << x392_stalled << ", #idle: " << x392_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x392_stalled << ", # idle: " << x392_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x399, 5 -> x392, 4 -> x397, 1 -> x439, 3 -> x398), current node x397 is at depth 4
long x397_cycles = c1->getArg(X397_cycles_arg, false);
long x397_iters = c1->getArg(X397_iters_arg, false);
long x397_iters_per_parent = x397_iters / std::max((long)1,x398_iters);
long x397_avg = x397_cycles / std::max((long)1,x397_iters);
std::cout << "        x397 - " << x397_avg << " (" << x397_cycles << " / " << x397_iters << ") ";
std::cout << "[" << x397_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "        x397 - " << x397_avg << " (" << x397_cycles << " / " << x397_iters << ") ";
instrumentation << "[" << x397_iters_per_parent << " iters/parent execution]";
}
long x397_stalled = c1->getArg(X397_stalled_arg, false);
long x397_idle = c1->getArg(X397_idle_arg, false);
std::cout << " <# stalled: " << x397_stalled << ", #idle: " << x397_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x397_stalled << ", # idle: " << x397_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
instrumentation.close();
vector<double>* x400 = new vector<double>(2);
vector<int32_t>* x401_rawified = new vector<int32_t>((*x400).size());
c1->memcpy(&(*x401_rawified)[0], x292, (*x401_rawified).size() * sizeof(int32_t));
for (int x400_i = 0; x400_i < (*x400).size(); x400_i++) {
int32_t x400_tmp = (*x401_rawified)[x400_i];
(*x400)[x400_i] = (double) x400_tmp / ((int32_t)1 << 8);
}
double x402 = (*x400)[0];
double x403 = (*x290)[0];
double x404 = x402 - x403;
bool x405 = x404 < 0;
double x413;
if (x405) { 
double x406 = (*x400)[0];
double x407 = (*x290)[0];
double x408 = x406 - x407;
double x409 = -x408;
x413 = x409;
}
else {
double x410 = (*x400)[0];
double x411 = (*x290)[0];
double x412 = x410 - x411;
x413 = x412;
}
double x414 = (*x400)[1];
double x415 = (*x290)[1];
double x416 = x414 - x415;
bool x417 = x416 < 0;
double x425;
if (x417) { 
double x418 = (*x400)[1];
double x419 = (*x290)[1];
double x420 = x418 - x419;
double x421 = -x420;
x425 = x421;
}
else {
double x422 = (*x400)[1];
double x423 = (*x290)[1];
double x424 = x422 - x423;
x425 = x424;
}
bool x426 = x413 < 0.09765625;
bool x427 = x425 < 0.09765625;
bool x428 = x426 & x427;
int32_t x429;
if (x428) { 
x429 = 1;
}
else {
x429 = 0;
}
string x430 = std::to_string(x429);
string x431 = (string("PASS: ") + x430);
string x432 = (x431 + string("\n"));
std::cout << x432;
bool x434 = x429 == 1;
string x435 = ("\n=================\n" + (string("LogCompressTest.scala:49:11: Assertion failure") + "\n=================\n"));
if (true) { std::cout << std::flush; ASSERT(x434, "%s", x435.c_str()); }
delete c1;
}

void printHelp() {
fprintf(stderr, "Help for app: LogCompressTest\n");
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
