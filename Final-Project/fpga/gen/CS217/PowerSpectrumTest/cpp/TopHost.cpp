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
  vector<float>* x318 = new vector<float>(4);
  (*x318)[0] = 1;
  (*x318)[1] = 2;
  (*x318)[2] = 3.0;
  (*x318)[3] = 4;
  vector<float>* x319 = new vector<float>(4);
  (*x319)[0] = 4;
  (*x319)[1] = 3.0;
  (*x319)[2] = 2;
  (*x319)[3] = 1;
  vector<float>* x320 = new vector<float>(4);
  (*x320)[0] = 17.0000;
  (*x320)[1] = 13.000;
  (*x320)[2] = 13.000;
  (*x320)[3] = 17.0000;
  uint64_t x321 = c1->malloc(sizeof(float) * 4);
  c1->setArg(REALDRAM_ptr, x321, false);
  printf("Allocate mem of size 4 at %p\n", (void*)x321);
  uint64_t x322 = c1->malloc(sizeof(float) * 4);
  c1->setArg(IMAGDRAM_ptr, x322, false);
  printf("Allocate mem of size 4 at %p\n", (void*)x322);
  uint64_t x323 = c1->malloc(sizeof(float) * 4);
  c1->setArg(OUTDRAM_ptr, x323, false);
  printf("Allocate mem of size 4 at %p\n", (void*)x323);
  c1->memcpy(x321, &(*x318)[0], (*x318).size() * sizeof(float));
  c1->memcpy(x322, &(*x319)[0], (*x319).size() * sizeof(float));
// Register ArgIns and ArgIOs in case some are unused
c1->setNumArgIns(0 + 3 + 0);
c1->setNumArgIOs(0);
c1->setNumArgOuts(0);
c1->setNumArgOutInstrs(56);
c1->setNumEarlyExits(0);
c1->flushCache(1024);
time_t tstart = time(0);
c1->run();
time_t tend = time(0);
double elapsed = difftime(tend, tstart);
std::cout << "Kernel done, test run time = " << elapsed << " ms" << std::endl;
c1->flushCache(1024);
std::ofstream instrumentation ("./instrumentation.txt");
// Need to instrument List((x481,1), (x480,2), (x363,3), (x338,4), (x362,4), (x348,5), (x361,5), (x398,3), (x373,4), (x397,4), (x383,5), (x396,5), (x411,2), (x444,2), (x443,3), (x438,4), (x423,5), (x437,5), (x442,4))
std::cout << "ArgIns:" <<  " "  << ", ArgIOs:" <<  " "  << std::endl;
if (instrumentation.is_open()) {
instrumentation << "ArgIns:" <<  " "  << ", ArgIOs:" <<  " "  << std::endl;
}
 // immediate parent hashmap now Map(1 -> x481), current node x481 is at depth 1
long x481_cycles = c1->getArg(X481_cycles_arg, false);
long x481_iters = c1->getArg(X481_iters_arg, false);
long x481_iters_per_parent = x481_iters / std::max((long)1,x481_iters);
long x481_avg = x481_cycles / std::max((long)1,x481_iters);
std::cout << "  x481 - " << x481_avg << " (" << x481_cycles << " / " << x481_iters << ") ";
std::cout << "[" << x481_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "  x481 - " << x481_avg << " (" << x481_cycles << " / " << x481_iters << ") ";
instrumentation << "[" << x481_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x480, 1 -> x481), current node x480 is at depth 2
long x480_cycles = c1->getArg(X480_cycles_arg, false);
long x480_iters = c1->getArg(X480_iters_arg, false);
long x480_iters_per_parent = x480_iters / std::max((long)1,x481_iters);
long x480_avg = x480_cycles / std::max((long)1,x480_iters);
std::cout << "    x480 - " << x480_avg << " (" << x480_cycles << " / " << x480_iters << ") ";
std::cout << "[" << x480_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "    x480 - " << x480_avg << " (" << x480_cycles << " / " << x480_iters << ") ";
instrumentation << "[" << x480_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x480, 1 -> x481, 3 -> x363), current node x363 is at depth 3
long x363_cycles = c1->getArg(X363_cycles_arg, false);
long x363_iters = c1->getArg(X363_iters_arg, false);
long x363_iters_per_parent = x363_iters / std::max((long)1,x480_iters);
long x363_avg = x363_cycles / std::max((long)1,x363_iters);
std::cout << "      x363 - " << x363_avg << " (" << x363_cycles << " / " << x363_iters << ") ";
std::cout << "[" << x363_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x363 - " << x363_avg << " (" << x363_cycles << " / " << x363_iters << ") ";
instrumentation << "[" << x363_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x480, 4 -> x338, 1 -> x481, 3 -> x363), current node x338 is at depth 4
long x338_cycles = c1->getArg(X338_cycles_arg, false);
long x338_iters = c1->getArg(X338_iters_arg, false);
long x338_iters_per_parent = x338_iters / std::max((long)1,x363_iters);
long x338_avg = x338_cycles / std::max((long)1,x338_iters);
std::cout << "        x338 - " << x338_avg << " (" << x338_cycles << " / " << x338_iters << ") ";
std::cout << "[" << x338_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "        x338 - " << x338_avg << " (" << x338_cycles << " / " << x338_iters << ") ";
instrumentation << "[" << x338_iters_per_parent << " iters/parent execution]";
}
long x338_stalled = c1->getArg(X338_stalled_arg, false);
long x338_idle = c1->getArg(X338_idle_arg, false);
std::cout << " <# stalled: " << x338_stalled << ", #idle: " << x338_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x338_stalled << ", # idle: " << x338_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x480, 4 -> x362, 1 -> x481, 3 -> x363), current node x362 is at depth 4
long x362_cycles = c1->getArg(X362_cycles_arg, false);
long x362_iters = c1->getArg(X362_iters_arg, false);
long x362_iters_per_parent = x362_iters / std::max((long)1,x363_iters);
long x362_avg = x362_cycles / std::max((long)1,x362_iters);
std::cout << "        x362 - " << x362_avg << " (" << x362_cycles << " / " << x362_iters << ") ";
std::cout << "[" << x362_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "        x362 - " << x362_avg << " (" << x362_cycles << " / " << x362_iters << ") ";
instrumentation << "[" << x362_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x480, 5 -> x348, 4 -> x362, 1 -> x481, 3 -> x363), current node x348 is at depth 5
long x348_cycles = c1->getArg(X348_cycles_arg, false);
long x348_iters = c1->getArg(X348_iters_arg, false);
long x348_iters_per_parent = x348_iters / std::max((long)1,x362_iters);
long x348_avg = x348_cycles / std::max((long)1,x348_iters);
std::cout << "          x348 - " << x348_avg << " (" << x348_cycles << " / " << x348_iters << ") ";
std::cout << "[" << x348_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x348 - " << x348_avg << " (" << x348_cycles << " / " << x348_iters << ") ";
instrumentation << "[" << x348_iters_per_parent << " iters/parent execution]";
}
long x348_stalled = c1->getArg(X348_stalled_arg, false);
long x348_idle = c1->getArg(X348_idle_arg, false);
std::cout << " <# stalled: " << x348_stalled << ", #idle: " << x348_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x348_stalled << ", # idle: " << x348_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x480, 5 -> x361, 4 -> x362, 1 -> x481, 3 -> x363), current node x361 is at depth 5
long x361_cycles = c1->getArg(X361_cycles_arg, false);
long x361_iters = c1->getArg(X361_iters_arg, false);
long x361_iters_per_parent = x361_iters / std::max((long)1,x362_iters);
long x361_avg = x361_cycles / std::max((long)1,x361_iters);
std::cout << "          x361 - " << x361_avg << " (" << x361_cycles << " / " << x361_iters << ") ";
std::cout << "[" << x361_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x361 - " << x361_avg << " (" << x361_cycles << " / " << x361_iters << ") ";
instrumentation << "[" << x361_iters_per_parent << " iters/parent execution]";
}
long x361_stalled = c1->getArg(X361_stalled_arg, false);
long x361_idle = c1->getArg(X361_idle_arg, false);
std::cout << " <# stalled: " << x361_stalled << ", #idle: " << x361_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x361_stalled << ", # idle: " << x361_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x480, 5 -> x361, 4 -> x362, 1 -> x481, 3 -> x398), current node x398 is at depth 3
long x398_cycles = c1->getArg(X398_cycles_arg, false);
long x398_iters = c1->getArg(X398_iters_arg, false);
long x398_iters_per_parent = x398_iters / std::max((long)1,x480_iters);
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
 // immediate parent hashmap now Map(2 -> x480, 5 -> x361, 4 -> x373, 1 -> x481, 3 -> x398), current node x373 is at depth 4
long x373_cycles = c1->getArg(X373_cycles_arg, false);
long x373_iters = c1->getArg(X373_iters_arg, false);
long x373_iters_per_parent = x373_iters / std::max((long)1,x398_iters);
long x373_avg = x373_cycles / std::max((long)1,x373_iters);
std::cout << "        x373 - " << x373_avg << " (" << x373_cycles << " / " << x373_iters << ") ";
std::cout << "[" << x373_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "        x373 - " << x373_avg << " (" << x373_cycles << " / " << x373_iters << ") ";
instrumentation << "[" << x373_iters_per_parent << " iters/parent execution]";
}
long x373_stalled = c1->getArg(X373_stalled_arg, false);
long x373_idle = c1->getArg(X373_idle_arg, false);
std::cout << " <# stalled: " << x373_stalled << ", #idle: " << x373_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x373_stalled << ", # idle: " << x373_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x480, 5 -> x361, 4 -> x397, 1 -> x481, 3 -> x398), current node x397 is at depth 4
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
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x480, 5 -> x383, 4 -> x397, 1 -> x481, 3 -> x398), current node x383 is at depth 5
long x383_cycles = c1->getArg(X383_cycles_arg, false);
long x383_iters = c1->getArg(X383_iters_arg, false);
long x383_iters_per_parent = x383_iters / std::max((long)1,x397_iters);
long x383_avg = x383_cycles / std::max((long)1,x383_iters);
std::cout << "          x383 - " << x383_avg << " (" << x383_cycles << " / " << x383_iters << ") ";
std::cout << "[" << x383_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x383 - " << x383_avg << " (" << x383_cycles << " / " << x383_iters << ") ";
instrumentation << "[" << x383_iters_per_parent << " iters/parent execution]";
}
long x383_stalled = c1->getArg(X383_stalled_arg, false);
long x383_idle = c1->getArg(X383_idle_arg, false);
std::cout << " <# stalled: " << x383_stalled << ", #idle: " << x383_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x383_stalled << ", # idle: " << x383_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x480, 5 -> x396, 4 -> x397, 1 -> x481, 3 -> x398), current node x396 is at depth 5
long x396_cycles = c1->getArg(X396_cycles_arg, false);
long x396_iters = c1->getArg(X396_iters_arg, false);
long x396_iters_per_parent = x396_iters / std::max((long)1,x397_iters);
long x396_avg = x396_cycles / std::max((long)1,x396_iters);
std::cout << "          x396 - " << x396_avg << " (" << x396_cycles << " / " << x396_iters << ") ";
std::cout << "[" << x396_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x396 - " << x396_avg << " (" << x396_cycles << " / " << x396_iters << ") ";
instrumentation << "[" << x396_iters_per_parent << " iters/parent execution]";
}
long x396_stalled = c1->getArg(X396_stalled_arg, false);
long x396_idle = c1->getArg(X396_idle_arg, false);
std::cout << " <# stalled: " << x396_stalled << ", #idle: " << x396_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x396_stalled << ", # idle: " << x396_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x411, 5 -> x396, 4 -> x397, 1 -> x481, 3 -> x398), current node x411 is at depth 2
long x411_cycles = c1->getArg(X411_cycles_arg, false);
long x411_iters = c1->getArg(X411_iters_arg, false);
long x411_iters_per_parent = x411_iters / std::max((long)1,x481_iters);
long x411_avg = x411_cycles / std::max((long)1,x411_iters);
std::cout << "    x411 - " << x411_avg << " (" << x411_cycles << " / " << x411_iters << ") ";
std::cout << "[" << x411_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "    x411 - " << x411_avg << " (" << x411_cycles << " / " << x411_iters << ") ";
instrumentation << "[" << x411_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x444, 5 -> x396, 4 -> x397, 1 -> x481, 3 -> x398), current node x444 is at depth 2
long x444_cycles = c1->getArg(X444_cycles_arg, false);
long x444_iters = c1->getArg(X444_iters_arg, false);
long x444_iters_per_parent = x444_iters / std::max((long)1,x481_iters);
long x444_avg = x444_cycles / std::max((long)1,x444_iters);
std::cout << "    x444 - " << x444_avg << " (" << x444_cycles << " / " << x444_iters << ") ";
std::cout << "[" << x444_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "    x444 - " << x444_avg << " (" << x444_cycles << " / " << x444_iters << ") ";
instrumentation << "[" << x444_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x444, 5 -> x396, 4 -> x397, 1 -> x481, 3 -> x443), current node x443 is at depth 3
long x443_cycles = c1->getArg(X443_cycles_arg, false);
long x443_iters = c1->getArg(X443_iters_arg, false);
long x443_iters_per_parent = x443_iters / std::max((long)1,x444_iters);
long x443_avg = x443_cycles / std::max((long)1,x443_iters);
std::cout << "      x443 - " << x443_avg << " (" << x443_cycles << " / " << x443_iters << ") ";
std::cout << "[" << x443_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x443 - " << x443_avg << " (" << x443_cycles << " / " << x443_iters << ") ";
instrumentation << "[" << x443_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x444, 5 -> x396, 4 -> x438, 1 -> x481, 3 -> x443), current node x438 is at depth 4
long x438_cycles = c1->getArg(X438_cycles_arg, false);
long x438_iters = c1->getArg(X438_iters_arg, false);
long x438_iters_per_parent = x438_iters / std::max((long)1,x443_iters);
long x438_avg = x438_cycles / std::max((long)1,x438_iters);
std::cout << "        x438 - " << x438_avg << " (" << x438_cycles << " / " << x438_iters << ") ";
std::cout << "[" << x438_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "        x438 - " << x438_avg << " (" << x438_cycles << " / " << x438_iters << ") ";
instrumentation << "[" << x438_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x444, 5 -> x423, 4 -> x438, 1 -> x481, 3 -> x443), current node x423 is at depth 5
long x423_cycles = c1->getArg(X423_cycles_arg, false);
long x423_iters = c1->getArg(X423_iters_arg, false);
long x423_iters_per_parent = x423_iters / std::max((long)1,x438_iters);
long x423_avg = x423_cycles / std::max((long)1,x423_iters);
std::cout << "          x423 - " << x423_avg << " (" << x423_cycles << " / " << x423_iters << ") ";
std::cout << "[" << x423_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x423 - " << x423_avg << " (" << x423_cycles << " / " << x423_iters << ") ";
instrumentation << "[" << x423_iters_per_parent << " iters/parent execution]";
}
long x423_stalled = c1->getArg(X423_stalled_arg, false);
long x423_idle = c1->getArg(X423_idle_arg, false);
std::cout << " <# stalled: " << x423_stalled << ", #idle: " << x423_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x423_stalled << ", # idle: " << x423_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x444, 5 -> x437, 4 -> x438, 1 -> x481, 3 -> x443), current node x437 is at depth 5
long x437_cycles = c1->getArg(X437_cycles_arg, false);
long x437_iters = c1->getArg(X437_iters_arg, false);
long x437_iters_per_parent = x437_iters / std::max((long)1,x438_iters);
long x437_avg = x437_cycles / std::max((long)1,x437_iters);
std::cout << "          x437 - " << x437_avg << " (" << x437_cycles << " / " << x437_iters << ") ";
std::cout << "[" << x437_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x437 - " << x437_avg << " (" << x437_cycles << " / " << x437_iters << ") ";
instrumentation << "[" << x437_iters_per_parent << " iters/parent execution]";
}
long x437_stalled = c1->getArg(X437_stalled_arg, false);
long x437_idle = c1->getArg(X437_idle_arg, false);
std::cout << " <# stalled: " << x437_stalled << ", #idle: " << x437_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x437_stalled << ", # idle: " << x437_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x444, 5 -> x437, 4 -> x442, 1 -> x481, 3 -> x443), current node x442 is at depth 4
long x442_cycles = c1->getArg(X442_cycles_arg, false);
long x442_iters = c1->getArg(X442_iters_arg, false);
long x442_iters_per_parent = x442_iters / std::max((long)1,x443_iters);
long x442_avg = x442_cycles / std::max((long)1,x442_iters);
std::cout << "        x442 - " << x442_avg << " (" << x442_cycles << " / " << x442_iters << ") ";
std::cout << "[" << x442_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "        x442 - " << x442_avg << " (" << x442_cycles << " / " << x442_iters << ") ";
instrumentation << "[" << x442_iters_per_parent << " iters/parent execution]";
}
long x442_stalled = c1->getArg(X442_stalled_arg, false);
long x442_idle = c1->getArg(X442_idle_arg, false);
std::cout << " <# stalled: " << x442_stalled << ", #idle: " << x442_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x442_stalled << ", # idle: " << x442_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
instrumentation.close();
vector<float>* x445 = new vector<float>(4);
c1->memcpy(&(*x445)[0], x323, (*x445).size() * sizeof(float));
std::cout << string("Result:\n");
for (int b29 = 0; b29 < 4; b29 = b29 + 1) {
float x448 = (*x445)[b29];
string x449 = std::to_string(x448);
string x450 = (x449 + string("\n"));
std::cout << x450;
}
std::cout << string("Gold:\n");
for (int b36 = 0; b36 < 4; b36 = b36 + 1) {
float x454 = (*x320)[b36];
string x455 = std::to_string(x454);
string x456 = (x455 + string("\n"));
std::cout << x456;
}
vector<bool>* x466 = new vector<bool>(4);
for (int b42 = 0; b42 < 4; b42++) {
float x459 = (*x445)[b42];
float x460 = (*x320)[b42];
float x461 = x459 - x460;
bool x462 = x461 < 0.0;
float x464;
if (x462) { 
float x463 = -x461;
x464 = x463;
}
else {
x464 = x461;
}
bool x465 = x464 <= 0.001000000047497451305389404296875;
(*x466)[b42] = x465;
}
bool x469;
if ((*x466).size() > 0) { // Hack to handle reductions on things of length 0
x469 = (*x466)[0];
}
else {
x469 = 0;
}
for (int b51 = 1; b51 < (*x466).size(); b51++) {
bool b52 = (*x466)[b51];
bool b53 = x469;
bool x468 = b52 & b53;
x469 = x468;
}
int32_t x470;
if (x469) { 
x470 = 1;
}
else {
x470 = 0;
}
string x471 = std::to_string(x470);
string x472 = (string("PASS: ") + x471);
string x473 = (x472 + string("\n"));
std::cout << x473;
string x475 = ("\n=================\n" + (string("PowerSpectrumTest.scala:53:11: Assertion failure") + "\n=================\n"));
if (true) { std::cout << std::flush; ASSERT(x469, "%s", x475.c_str()); }
delete c1;
}

void printHelp() {
fprintf(stderr, "Help for app: PowerSpectrumTest\n");
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
