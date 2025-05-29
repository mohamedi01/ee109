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
  vector<double>* x401 = new vector<double>(6);
  for (int b1 = 0; b1 < 6; b1++) {
    int32_t x393 = b1 / 3;
    int32_t x394 = (int32_t) ((b1 % 3 + 3) % 3);
    bool x395 = x393 == 0;
    double x400;
    if (x395) { 
      int32_t x396 = x394 + 1;
      double x397 = (double) x396;
      x400 = x397;
    }
    else {
      int32_t x398 = x394 + 4;
      double x399 = (double) x398;
      x400 = x399;
    }
    (*x401)[b1] = x400;
  }
  vector<double>* x402 = new vector<double>(3);
  for (int b12 = 0; b12 < 3; b12++) {
    (*x402)[b12] = 1;
  }
  vector<double>* x405 = new vector<double>(2);
  for (int b14 = 0; b14 < 2; b14++) {
    bool x403 = b14 == 0;
    double x404;
    if (x403) { 
      x404 = 6;
    }
    else {
      x404 = 15;
    }
    (*x405)[b14] = x404;
  }
  uint64_t x406 = c1->malloc(sizeof(double) * 2*3);
  c1->setArg(MATDRAM_ptr, x406, false);
  printf("Allocate mem of size 2*3 at %p\n", (void*)x406);
  uint64_t x407 = c1->malloc(sizeof(double) * 3);
  c1->setArg(VECDRAM_ptr, x407, false);
  printf("Allocate mem of size 3 at %p\n", (void*)x407);
  uint64_t x408 = c1->malloc(sizeof(double) * 2);
  c1->setArg(OUTDRAM_ptr, x408, false);
  printf("Allocate mem of size 2 at %p\n", (void*)x408);
  vector<int32_t>* x409_rawified = new vector<int32_t>((*x401).size());
  for (int x409_rawified_i = 0; x409_rawified_i < (*x401).size(); x409_rawified_i++) {
    (*x409_rawified)[x409_rawified_i] = (int32_t) ((*x401)[x409_rawified_i] * ((int32_t)1 << 8));
  }
  c1->memcpy(x406, &(*x409_rawified)[0], (*x409_rawified).size() * sizeof(int32_t));
  vector<int32_t>* x410_rawified = new vector<int32_t>((*x402).size());
  for (int x410_rawified_i = 0; x410_rawified_i < (*x402).size(); x410_rawified_i++) {
    (*x410_rawified)[x410_rawified_i] = (int32_t) ((*x402)[x410_rawified_i] * ((int32_t)1 << 8));
  }
  c1->memcpy(x407, &(*x410_rawified)[0], (*x410_rawified).size() * sizeof(int32_t));
// Register ArgIns and ArgIOs in case some are unused
c1->setNumArgIns(0 + 3 + 0);
c1->setNumArgIOs(0);
c1->setNumArgOuts(0);
c1->setNumArgOutInstrs(60);
c1->setNumEarlyExits(0);
c1->flushCache(1024);
time_t tstart = time(0);
c1->run();
time_t tend = time(0);
double elapsed = difftime(tend, tstart);
std::cout << "Kernel done, test run time = " << elapsed << " ms" << std::endl;
c1->flushCache(1024);
std::ofstream instrumentation ("./instrumentation.txt");
// Need to instrument List((x614,1), (x613,2), (x476,3), (x439,4), (x475,4), (x456,5), (x474,5), (x511,3), (x486,4), (x510,4), (x496,5), (x509,5), (x541,2), (x537,3), (x540,3), (x574,2), (x573,3), (x568,4), (x553,5), (x567,5), (x572,4))
std::cout << "ArgIns:" <<  " "  << ", ArgIOs:" <<  " "  << std::endl;
if (instrumentation.is_open()) {
instrumentation << "ArgIns:" <<  " "  << ", ArgIOs:" <<  " "  << std::endl;
}
 // immediate parent hashmap now Map(1 -> x614), current node x614 is at depth 1
long x614_cycles = c1->getArg(X614_cycles_arg, false);
long x614_iters = c1->getArg(X614_iters_arg, false);
long x614_iters_per_parent = x614_iters / std::max((long)1,x614_iters);
long x614_avg = x614_cycles / std::max((long)1,x614_iters);
std::cout << "  x614 - " << x614_avg << " (" << x614_cycles << " / " << x614_iters << ") ";
std::cout << "[" << x614_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "  x614 - " << x614_avg << " (" << x614_cycles << " / " << x614_iters << ") ";
instrumentation << "[" << x614_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x613, 1 -> x614), current node x613 is at depth 2
long x613_cycles = c1->getArg(X613_cycles_arg, false);
long x613_iters = c1->getArg(X613_iters_arg, false);
long x613_iters_per_parent = x613_iters / std::max((long)1,x614_iters);
long x613_avg = x613_cycles / std::max((long)1,x613_iters);
std::cout << "    x613 - " << x613_avg << " (" << x613_cycles << " / " << x613_iters << ") ";
std::cout << "[" << x613_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "    x613 - " << x613_avg << " (" << x613_cycles << " / " << x613_iters << ") ";
instrumentation << "[" << x613_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x613, 1 -> x614, 3 -> x476), current node x476 is at depth 3
long x476_cycles = c1->getArg(X476_cycles_arg, false);
long x476_iters = c1->getArg(X476_iters_arg, false);
long x476_iters_per_parent = x476_iters / std::max((long)1,x613_iters);
long x476_avg = x476_cycles / std::max((long)1,x476_iters);
std::cout << "      x476 - " << x476_avg << " (" << x476_cycles << " / " << x476_iters << ") ";
std::cout << "[" << x476_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x476 - " << x476_avg << " (" << x476_cycles << " / " << x476_iters << ") ";
instrumentation << "[" << x476_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x613, 4 -> x439, 1 -> x614, 3 -> x476), current node x439 is at depth 4
long x439_cycles = c1->getArg(X439_cycles_arg, false);
long x439_iters = c1->getArg(X439_iters_arg, false);
long x439_iters_per_parent = x439_iters / std::max((long)1,x476_iters);
long x439_avg = x439_cycles / std::max((long)1,x439_iters);
std::cout << "        x439 - " << x439_avg << " (" << x439_cycles << " / " << x439_iters << ") ";
std::cout << "[" << x439_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "        x439 - " << x439_avg << " (" << x439_cycles << " / " << x439_iters << ") ";
instrumentation << "[" << x439_iters_per_parent << " iters/parent execution]";
}
long x439_stalled = c1->getArg(X439_stalled_arg, false);
long x439_idle = c1->getArg(X439_idle_arg, false);
std::cout << " <# stalled: " << x439_stalled << ", #idle: " << x439_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x439_stalled << ", # idle: " << x439_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x613, 4 -> x475, 1 -> x614, 3 -> x476), current node x475 is at depth 4
long x475_cycles = c1->getArg(X475_cycles_arg, false);
long x475_iters = c1->getArg(X475_iters_arg, false);
long x475_iters_per_parent = x475_iters / std::max((long)1,x476_iters);
long x475_avg = x475_cycles / std::max((long)1,x475_iters);
std::cout << "        x475 - " << x475_avg << " (" << x475_cycles << " / " << x475_iters << ") ";
std::cout << "[" << x475_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "        x475 - " << x475_avg << " (" << x475_cycles << " / " << x475_iters << ") ";
instrumentation << "[" << x475_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x613, 5 -> x456, 4 -> x475, 1 -> x614, 3 -> x476), current node x456 is at depth 5
long x456_cycles = c1->getArg(X456_cycles_arg, false);
long x456_iters = c1->getArg(X456_iters_arg, false);
long x456_iters_per_parent = x456_iters / std::max((long)1,x475_iters);
long x456_avg = x456_cycles / std::max((long)1,x456_iters);
std::cout << "          x456 - " << x456_avg << " (" << x456_cycles << " / " << x456_iters << ") ";
std::cout << "[" << x456_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x456 - " << x456_avg << " (" << x456_cycles << " / " << x456_iters << ") ";
instrumentation << "[" << x456_iters_per_parent << " iters/parent execution]";
}
long x456_stalled = c1->getArg(X456_stalled_arg, false);
long x456_idle = c1->getArg(X456_idle_arg, false);
std::cout << " <# stalled: " << x456_stalled << ", #idle: " << x456_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x456_stalled << ", # idle: " << x456_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x613, 5 -> x474, 4 -> x475, 1 -> x614, 3 -> x476), current node x474 is at depth 5
long x474_cycles = c1->getArg(X474_cycles_arg, false);
long x474_iters = c1->getArg(X474_iters_arg, false);
long x474_iters_per_parent = x474_iters / std::max((long)1,x475_iters);
long x474_avg = x474_cycles / std::max((long)1,x474_iters);
std::cout << "          x474 - " << x474_avg << " (" << x474_cycles << " / " << x474_iters << ") ";
std::cout << "[" << x474_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x474 - " << x474_avg << " (" << x474_cycles << " / " << x474_iters << ") ";
instrumentation << "[" << x474_iters_per_parent << " iters/parent execution]";
}
long x474_stalled = c1->getArg(X474_stalled_arg, false);
long x474_idle = c1->getArg(X474_idle_arg, false);
std::cout << " <# stalled: " << x474_stalled << ", #idle: " << x474_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x474_stalled << ", # idle: " << x474_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x613, 5 -> x474, 4 -> x475, 1 -> x614, 3 -> x511), current node x511 is at depth 3
long x511_cycles = c1->getArg(X511_cycles_arg, false);
long x511_iters = c1->getArg(X511_iters_arg, false);
long x511_iters_per_parent = x511_iters / std::max((long)1,x613_iters);
long x511_avg = x511_cycles / std::max((long)1,x511_iters);
std::cout << "      x511 - " << x511_avg << " (" << x511_cycles << " / " << x511_iters << ") ";
std::cout << "[" << x511_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x511 - " << x511_avg << " (" << x511_cycles << " / " << x511_iters << ") ";
instrumentation << "[" << x511_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x613, 5 -> x474, 4 -> x486, 1 -> x614, 3 -> x511), current node x486 is at depth 4
long x486_cycles = c1->getArg(X486_cycles_arg, false);
long x486_iters = c1->getArg(X486_iters_arg, false);
long x486_iters_per_parent = x486_iters / std::max((long)1,x511_iters);
long x486_avg = x486_cycles / std::max((long)1,x486_iters);
std::cout << "        x486 - " << x486_avg << " (" << x486_cycles << " / " << x486_iters << ") ";
std::cout << "[" << x486_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "        x486 - " << x486_avg << " (" << x486_cycles << " / " << x486_iters << ") ";
instrumentation << "[" << x486_iters_per_parent << " iters/parent execution]";
}
long x486_stalled = c1->getArg(X486_stalled_arg, false);
long x486_idle = c1->getArg(X486_idle_arg, false);
std::cout << " <# stalled: " << x486_stalled << ", #idle: " << x486_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x486_stalled << ", # idle: " << x486_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x613, 5 -> x474, 4 -> x510, 1 -> x614, 3 -> x511), current node x510 is at depth 4
long x510_cycles = c1->getArg(X510_cycles_arg, false);
long x510_iters = c1->getArg(X510_iters_arg, false);
long x510_iters_per_parent = x510_iters / std::max((long)1,x511_iters);
long x510_avg = x510_cycles / std::max((long)1,x510_iters);
std::cout << "        x510 - " << x510_avg << " (" << x510_cycles << " / " << x510_iters << ") ";
std::cout << "[" << x510_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "        x510 - " << x510_avg << " (" << x510_cycles << " / " << x510_iters << ") ";
instrumentation << "[" << x510_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x613, 5 -> x496, 4 -> x510, 1 -> x614, 3 -> x511), current node x496 is at depth 5
long x496_cycles = c1->getArg(X496_cycles_arg, false);
long x496_iters = c1->getArg(X496_iters_arg, false);
long x496_iters_per_parent = x496_iters / std::max((long)1,x510_iters);
long x496_avg = x496_cycles / std::max((long)1,x496_iters);
std::cout << "          x496 - " << x496_avg << " (" << x496_cycles << " / " << x496_iters << ") ";
std::cout << "[" << x496_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x496 - " << x496_avg << " (" << x496_cycles << " / " << x496_iters << ") ";
instrumentation << "[" << x496_iters_per_parent << " iters/parent execution]";
}
long x496_stalled = c1->getArg(X496_stalled_arg, false);
long x496_idle = c1->getArg(X496_idle_arg, false);
std::cout << " <# stalled: " << x496_stalled << ", #idle: " << x496_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x496_stalled << ", # idle: " << x496_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x613, 5 -> x509, 4 -> x510, 1 -> x614, 3 -> x511), current node x509 is at depth 5
long x509_cycles = c1->getArg(X509_cycles_arg, false);
long x509_iters = c1->getArg(X509_iters_arg, false);
long x509_iters_per_parent = x509_iters / std::max((long)1,x510_iters);
long x509_avg = x509_cycles / std::max((long)1,x509_iters);
std::cout << "          x509 - " << x509_avg << " (" << x509_cycles << " / " << x509_iters << ") ";
std::cout << "[" << x509_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x509 - " << x509_avg << " (" << x509_cycles << " / " << x509_iters << ") ";
instrumentation << "[" << x509_iters_per_parent << " iters/parent execution]";
}
long x509_stalled = c1->getArg(X509_stalled_arg, false);
long x509_idle = c1->getArg(X509_idle_arg, false);
std::cout << " <# stalled: " << x509_stalled << ", #idle: " << x509_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x509_stalled << ", # idle: " << x509_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x541, 5 -> x509, 4 -> x510, 1 -> x614, 3 -> x511), current node x541 is at depth 2
long x541_cycles = c1->getArg(X541_cycles_arg, false);
long x541_iters = c1->getArg(X541_iters_arg, false);
long x541_iters_per_parent = x541_iters / std::max((long)1,x614_iters);
long x541_avg = x541_cycles / std::max((long)1,x541_iters);
std::cout << "    x541 - " << x541_avg << " (" << x541_cycles << " / " << x541_iters << ") ";
std::cout << "[" << x541_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "    x541 - " << x541_avg << " (" << x541_cycles << " / " << x541_iters << ") ";
instrumentation << "[" << x541_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x541, 5 -> x509, 4 -> x510, 1 -> x614, 3 -> x537), current node x537 is at depth 3
long x537_cycles = c1->getArg(X537_cycles_arg, false);
long x537_iters = c1->getArg(X537_iters_arg, false);
long x537_iters_per_parent = x537_iters / std::max((long)1,x541_iters);
long x537_avg = x537_cycles / std::max((long)1,x537_iters);
std::cout << "      x537 - " << x537_avg << " (" << x537_cycles << " / " << x537_iters << ") ";
std::cout << "[" << x537_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x537 - " << x537_avg << " (" << x537_cycles << " / " << x537_iters << ") ";
instrumentation << "[" << x537_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x541, 5 -> x509, 4 -> x510, 1 -> x614, 3 -> x540), current node x540 is at depth 3
long x540_cycles = c1->getArg(X540_cycles_arg, false);
long x540_iters = c1->getArg(X540_iters_arg, false);
long x540_iters_per_parent = x540_iters / std::max((long)1,x541_iters);
long x540_avg = x540_cycles / std::max((long)1,x540_iters);
std::cout << "      x540 - " << x540_avg << " (" << x540_cycles << " / " << x540_iters << ") ";
std::cout << "[" << x540_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x540 - " << x540_avg << " (" << x540_cycles << " / " << x540_iters << ") ";
instrumentation << "[" << x540_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x574, 5 -> x509, 4 -> x510, 1 -> x614, 3 -> x540), current node x574 is at depth 2
long x574_cycles = c1->getArg(X574_cycles_arg, false);
long x574_iters = c1->getArg(X574_iters_arg, false);
long x574_iters_per_parent = x574_iters / std::max((long)1,x614_iters);
long x574_avg = x574_cycles / std::max((long)1,x574_iters);
std::cout << "    x574 - " << x574_avg << " (" << x574_cycles << " / " << x574_iters << ") ";
std::cout << "[" << x574_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "    x574 - " << x574_avg << " (" << x574_cycles << " / " << x574_iters << ") ";
instrumentation << "[" << x574_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x574, 5 -> x509, 4 -> x510, 1 -> x614, 3 -> x573), current node x573 is at depth 3
long x573_cycles = c1->getArg(X573_cycles_arg, false);
long x573_iters = c1->getArg(X573_iters_arg, false);
long x573_iters_per_parent = x573_iters / std::max((long)1,x574_iters);
long x573_avg = x573_cycles / std::max((long)1,x573_iters);
std::cout << "      x573 - " << x573_avg << " (" << x573_cycles << " / " << x573_iters << ") ";
std::cout << "[" << x573_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x573 - " << x573_avg << " (" << x573_cycles << " / " << x573_iters << ") ";
instrumentation << "[" << x573_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x574, 5 -> x509, 4 -> x568, 1 -> x614, 3 -> x573), current node x568 is at depth 4
long x568_cycles = c1->getArg(X568_cycles_arg, false);
long x568_iters = c1->getArg(X568_iters_arg, false);
long x568_iters_per_parent = x568_iters / std::max((long)1,x573_iters);
long x568_avg = x568_cycles / std::max((long)1,x568_iters);
std::cout << "        x568 - " << x568_avg << " (" << x568_cycles << " / " << x568_iters << ") ";
std::cout << "[" << x568_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "        x568 - " << x568_avg << " (" << x568_cycles << " / " << x568_iters << ") ";
instrumentation << "[" << x568_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x574, 5 -> x553, 4 -> x568, 1 -> x614, 3 -> x573), current node x553 is at depth 5
long x553_cycles = c1->getArg(X553_cycles_arg, false);
long x553_iters = c1->getArg(X553_iters_arg, false);
long x553_iters_per_parent = x553_iters / std::max((long)1,x568_iters);
long x553_avg = x553_cycles / std::max((long)1,x553_iters);
std::cout << "          x553 - " << x553_avg << " (" << x553_cycles << " / " << x553_iters << ") ";
std::cout << "[" << x553_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x553 - " << x553_avg << " (" << x553_cycles << " / " << x553_iters << ") ";
instrumentation << "[" << x553_iters_per_parent << " iters/parent execution]";
}
long x553_stalled = c1->getArg(X553_stalled_arg, false);
long x553_idle = c1->getArg(X553_idle_arg, false);
std::cout << " <# stalled: " << x553_stalled << ", #idle: " << x553_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x553_stalled << ", # idle: " << x553_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x574, 5 -> x567, 4 -> x568, 1 -> x614, 3 -> x573), current node x567 is at depth 5
long x567_cycles = c1->getArg(X567_cycles_arg, false);
long x567_iters = c1->getArg(X567_iters_arg, false);
long x567_iters_per_parent = x567_iters / std::max((long)1,x568_iters);
long x567_avg = x567_cycles / std::max((long)1,x567_iters);
std::cout << "          x567 - " << x567_avg << " (" << x567_cycles << " / " << x567_iters << ") ";
std::cout << "[" << x567_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x567 - " << x567_avg << " (" << x567_cycles << " / " << x567_iters << ") ";
instrumentation << "[" << x567_iters_per_parent << " iters/parent execution]";
}
long x567_stalled = c1->getArg(X567_stalled_arg, false);
long x567_idle = c1->getArg(X567_idle_arg, false);
std::cout << " <# stalled: " << x567_stalled << ", #idle: " << x567_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x567_stalled << ", # idle: " << x567_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x574, 5 -> x567, 4 -> x572, 1 -> x614, 3 -> x573), current node x572 is at depth 4
long x572_cycles = c1->getArg(X572_cycles_arg, false);
long x572_iters = c1->getArg(X572_iters_arg, false);
long x572_iters_per_parent = x572_iters / std::max((long)1,x573_iters);
long x572_avg = x572_cycles / std::max((long)1,x572_iters);
std::cout << "        x572 - " << x572_avg << " (" << x572_cycles << " / " << x572_iters << ") ";
std::cout << "[" << x572_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "        x572 - " << x572_avg << " (" << x572_cycles << " / " << x572_iters << ") ";
instrumentation << "[" << x572_iters_per_parent << " iters/parent execution]";
}
long x572_stalled = c1->getArg(X572_stalled_arg, false);
long x572_idle = c1->getArg(X572_idle_arg, false);
std::cout << " <# stalled: " << x572_stalled << ", #idle: " << x572_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x572_stalled << ", # idle: " << x572_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
instrumentation.close();
vector<double>* x575 = new vector<double>(2);
vector<int32_t>* x576_rawified = new vector<int32_t>((*x575).size());
c1->memcpy(&(*x576_rawified)[0], x408, (*x576_rawified).size() * sizeof(int32_t));
for (int x575_i = 0; x575_i < (*x575).size(); x575_i++) {
int32_t x575_tmp = (*x576_rawified)[x575_i];
(*x575)[x575_i] = (double) x575_tmp / ((int32_t)1 << 8);
}
double x577 = (*x575)[0];
double x578 = (*x405)[0];
double x579 = x577 - x578;
bool x580 = x579 < 0;
double x582;
if (x580) { 
double x581 = -x579;
x582 = x581;
}
else {
x582 = x579;
}
double x583 = (*x575)[1];
double x584 = (*x405)[1];
double x585 = x583 - x584;
bool x586 = x585 < 0;
double x588;
if (x586) { 
double x587 = -x585;
x588 = x587;
}
else {
x588 = x585;
}
bool x589 = x582 < 0.0078125;
bool x590 = x588 < 0.0078125;
bool x591 = x589 & x590;
int32_t x592;
if (x591) { 
x592 = 1;
}
else {
x592 = 0;
}
string x593 = std::to_string(x592);
string x594 = (string("PASS: ") + x593);
string x595 = (x594 + string("\n"));
std::cout << x595;
bool x597 = x592 == 1;
string x598 = ("\n=================\n" + (string("MelFilterbankTest.scala:57:11: Assertion failure") + "\n=================\n"));
if (true) { std::cout << std::flush; ASSERT(x597, "%s", x598.c_str()); }
delete c1;
}

void printHelp() {
fprintf(stderr, "Help for app: MelFilterbankTest\n");
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
