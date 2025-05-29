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
  vector<float>* x459 = new vector<float>(6);
  (*x459)[0] = 1;
  (*x459)[1] = 2;
  (*x459)[2] = 3.0;
  (*x459)[3] = 4;
  (*x459)[4] = 5.00;
  (*x459)[5] = 6.0;
  uint64_t x460 = c1->malloc(sizeof(float) * 6);
  c1->setArg(INDRAM_ptr, x460, false);
  printf("Allocate mem of size 6 at %p\n", (void*)x460);
  uint64_t x461 = c1->malloc(sizeof(float) * 3*2);
  c1->setArg(REALDRAM_ptr, x461, false);
  printf("Allocate mem of size 3*2 at %p\n", (void*)x461);
  uint64_t x462 = c1->malloc(sizeof(float) * 3*2);
  c1->setArg(IMAGDRAM_ptr, x462, false);
  printf("Allocate mem of size 3*2 at %p\n", (void*)x462);
  c1->memcpy(x460, &(*x459)[0], (*x459).size() * sizeof(float));
// Register ArgIns and ArgIOs in case some are unused
c1->setNumArgIns(0 + 3 + 0);
c1->setNumArgIOs(0);
c1->setNumArgOuts(0);
c1->setNumArgOutInstrs(68);
c1->setNumEarlyExits(0);
c1->flushCache(1024);
time_t tstart = time(0);
c1->run();
time_t tend = time(0);
double elapsed = difftime(tend, tstart);
std::cout << "Kernel done, test run time = " << elapsed << " ms" << std::endl;
c1->flushCache(1024);
std::ofstream instrumentation ("./instrumentation.txt");
// Need to instrument List((x710,1), (x708,2), (x505,3), (x480,4), (x504,4), (x490,5), (x503,5), (x511,3), (x552,2), (x528,3), (x537,3), (x551,3), (x709,2), (x608,3), (x607,4), (x602,5), (x582,6), (x601,6), (x606,5), (x664,3), (x663,4), (x658,5), (x638,6), (x657,6), (x662,5))
std::cout << "ArgIns:" <<  " "  << ", ArgIOs:" <<  " "  << std::endl;
if (instrumentation.is_open()) {
instrumentation << "ArgIns:" <<  " "  << ", ArgIOs:" <<  " "  << std::endl;
}
 // immediate parent hashmap now Map(1 -> x710), current node x710 is at depth 1
long x710_cycles = c1->getArg(X710_cycles_arg, false);
long x710_iters = c1->getArg(X710_iters_arg, false);
long x710_iters_per_parent = x710_iters / std::max((long)1,x710_iters);
long x710_avg = x710_cycles / std::max((long)1,x710_iters);
std::cout << "  x710 - " << x710_avg << " (" << x710_cycles << " / " << x710_iters << ") ";
std::cout << "[" << x710_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "  x710 - " << x710_avg << " (" << x710_cycles << " / " << x710_iters << ") ";
instrumentation << "[" << x710_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x708, 1 -> x710), current node x708 is at depth 2
long x708_cycles = c1->getArg(X708_cycles_arg, false);
long x708_iters = c1->getArg(X708_iters_arg, false);
long x708_iters_per_parent = x708_iters / std::max((long)1,x710_iters);
long x708_avg = x708_cycles / std::max((long)1,x708_iters);
std::cout << "    x708 - " << x708_avg << " (" << x708_cycles << " / " << x708_iters << ") ";
std::cout << "[" << x708_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "    x708 - " << x708_avg << " (" << x708_cycles << " / " << x708_iters << ") ";
instrumentation << "[" << x708_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x708, 1 -> x710, 3 -> x505), current node x505 is at depth 3
long x505_cycles = c1->getArg(X505_cycles_arg, false);
long x505_iters = c1->getArg(X505_iters_arg, false);
long x505_iters_per_parent = x505_iters / std::max((long)1,x708_iters);
long x505_avg = x505_cycles / std::max((long)1,x505_iters);
std::cout << "      x505 - " << x505_avg << " (" << x505_cycles << " / " << x505_iters << ") ";
std::cout << "[" << x505_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x505 - " << x505_avg << " (" << x505_cycles << " / " << x505_iters << ") ";
instrumentation << "[" << x505_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x708, 4 -> x480, 1 -> x710, 3 -> x505), current node x480 is at depth 4
long x480_cycles = c1->getArg(X480_cycles_arg, false);
long x480_iters = c1->getArg(X480_iters_arg, false);
long x480_iters_per_parent = x480_iters / std::max((long)1,x505_iters);
long x480_avg = x480_cycles / std::max((long)1,x480_iters);
std::cout << "        x480 - " << x480_avg << " (" << x480_cycles << " / " << x480_iters << ") ";
std::cout << "[" << x480_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "        x480 - " << x480_avg << " (" << x480_cycles << " / " << x480_iters << ") ";
instrumentation << "[" << x480_iters_per_parent << " iters/parent execution]";
}
long x480_stalled = c1->getArg(X480_stalled_arg, false);
long x480_idle = c1->getArg(X480_idle_arg, false);
std::cout << " <# stalled: " << x480_stalled << ", #idle: " << x480_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x480_stalled << ", # idle: " << x480_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x708, 4 -> x504, 1 -> x710, 3 -> x505), current node x504 is at depth 4
long x504_cycles = c1->getArg(X504_cycles_arg, false);
long x504_iters = c1->getArg(X504_iters_arg, false);
long x504_iters_per_parent = x504_iters / std::max((long)1,x505_iters);
long x504_avg = x504_cycles / std::max((long)1,x504_iters);
std::cout << "        x504 - " << x504_avg << " (" << x504_cycles << " / " << x504_iters << ") ";
std::cout << "[" << x504_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "        x504 - " << x504_avg << " (" << x504_cycles << " / " << x504_iters << ") ";
instrumentation << "[" << x504_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x708, 5 -> x490, 4 -> x504, 1 -> x710, 3 -> x505), current node x490 is at depth 5
long x490_cycles = c1->getArg(X490_cycles_arg, false);
long x490_iters = c1->getArg(X490_iters_arg, false);
long x490_iters_per_parent = x490_iters / std::max((long)1,x504_iters);
long x490_avg = x490_cycles / std::max((long)1,x490_iters);
std::cout << "          x490 - " << x490_avg << " (" << x490_cycles << " / " << x490_iters << ") ";
std::cout << "[" << x490_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x490 - " << x490_avg << " (" << x490_cycles << " / " << x490_iters << ") ";
instrumentation << "[" << x490_iters_per_parent << " iters/parent execution]";
}
long x490_stalled = c1->getArg(X490_stalled_arg, false);
long x490_idle = c1->getArg(X490_idle_arg, false);
std::cout << " <# stalled: " << x490_stalled << ", #idle: " << x490_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x490_stalled << ", # idle: " << x490_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x708, 5 -> x503, 4 -> x504, 1 -> x710, 3 -> x505), current node x503 is at depth 5
long x503_cycles = c1->getArg(X503_cycles_arg, false);
long x503_iters = c1->getArg(X503_iters_arg, false);
long x503_iters_per_parent = x503_iters / std::max((long)1,x504_iters);
long x503_avg = x503_cycles / std::max((long)1,x503_iters);
std::cout << "          x503 - " << x503_avg << " (" << x503_cycles << " / " << x503_iters << ") ";
std::cout << "[" << x503_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x503 - " << x503_avg << " (" << x503_cycles << " / " << x503_iters << ") ";
instrumentation << "[" << x503_iters_per_parent << " iters/parent execution]";
}
long x503_stalled = c1->getArg(X503_stalled_arg, false);
long x503_idle = c1->getArg(X503_idle_arg, false);
std::cout << " <# stalled: " << x503_stalled << ", #idle: " << x503_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x503_stalled << ", # idle: " << x503_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x708, 5 -> x503, 4 -> x504, 1 -> x710, 3 -> x511), current node x511 is at depth 3
long x511_cycles = c1->getArg(X511_cycles_arg, false);
long x511_iters = c1->getArg(X511_iters_arg, false);
long x511_iters_per_parent = x511_iters / std::max((long)1,x708_iters);
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
 // immediate parent hashmap now Map(2 -> x552, 5 -> x503, 4 -> x504, 1 -> x710, 3 -> x511), current node x552 is at depth 2
long x552_cycles = c1->getArg(X552_cycles_arg, false);
long x552_iters = c1->getArg(X552_iters_arg, false);
long x552_iters_per_parent = x552_iters / std::max((long)1,x710_iters);
long x552_avg = x552_cycles / std::max((long)1,x552_iters);
std::cout << "    x552 - " << x552_avg << " (" << x552_cycles << " / " << x552_iters << ") ";
std::cout << "[" << x552_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "    x552 - " << x552_avg << " (" << x552_cycles << " / " << x552_iters << ") ";
instrumentation << "[" << x552_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x552, 5 -> x503, 4 -> x504, 1 -> x710, 3 -> x528), current node x528 is at depth 3
long x528_cycles = c1->getArg(X528_cycles_arg, false);
long x528_iters = c1->getArg(X528_iters_arg, false);
long x528_iters_per_parent = x528_iters / std::max((long)1,x552_iters);
long x528_avg = x528_cycles / std::max((long)1,x528_iters);
std::cout << "      x528 - " << x528_avg << " (" << x528_cycles << " / " << x528_iters << ") ";
std::cout << "[" << x528_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x528 - " << x528_avg << " (" << x528_cycles << " / " << x528_iters << ") ";
instrumentation << "[" << x528_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x552, 5 -> x503, 4 -> x504, 1 -> x710, 3 -> x537), current node x537 is at depth 3
long x537_cycles = c1->getArg(X537_cycles_arg, false);
long x537_iters = c1->getArg(X537_iters_arg, false);
long x537_iters_per_parent = x537_iters / std::max((long)1,x552_iters);
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
 // immediate parent hashmap now Map(2 -> x552, 5 -> x503, 4 -> x504, 1 -> x710, 3 -> x551), current node x551 is at depth 3
long x551_cycles = c1->getArg(X551_cycles_arg, false);
long x551_iters = c1->getArg(X551_iters_arg, false);
long x551_iters_per_parent = x551_iters / std::max((long)1,x552_iters);
long x551_avg = x551_cycles / std::max((long)1,x551_iters);
std::cout << "      x551 - " << x551_avg << " (" << x551_cycles << " / " << x551_iters << ") ";
std::cout << "[" << x551_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x551 - " << x551_avg << " (" << x551_cycles << " / " << x551_iters << ") ";
instrumentation << "[" << x551_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x709, 5 -> x503, 4 -> x504, 1 -> x710, 3 -> x551), current node x709 is at depth 2
long x709_cycles = c1->getArg(X709_cycles_arg, false);
long x709_iters = c1->getArg(X709_iters_arg, false);
long x709_iters_per_parent = x709_iters / std::max((long)1,x710_iters);
long x709_avg = x709_cycles / std::max((long)1,x709_iters);
std::cout << "    x709 - " << x709_avg << " (" << x709_cycles << " / " << x709_iters << ") ";
std::cout << "[" << x709_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "    x709 - " << x709_avg << " (" << x709_cycles << " / " << x709_iters << ") ";
instrumentation << "[" << x709_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x709, 5 -> x503, 4 -> x504, 1 -> x710, 3 -> x608), current node x608 is at depth 3
long x608_cycles = c1->getArg(X608_cycles_arg, false);
long x608_iters = c1->getArg(X608_iters_arg, false);
long x608_iters_per_parent = x608_iters / std::max((long)1,x709_iters);
long x608_avg = x608_cycles / std::max((long)1,x608_iters);
std::cout << "      x608 - " << x608_avg << " (" << x608_cycles << " / " << x608_iters << ") ";
std::cout << "[" << x608_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x608 - " << x608_avg << " (" << x608_cycles << " / " << x608_iters << ") ";
instrumentation << "[" << x608_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x709, 5 -> x503, 4 -> x607, 1 -> x710, 3 -> x608), current node x607 is at depth 4
long x607_cycles = c1->getArg(X607_cycles_arg, false);
long x607_iters = c1->getArg(X607_iters_arg, false);
long x607_iters_per_parent = x607_iters / std::max((long)1,x608_iters);
long x607_avg = x607_cycles / std::max((long)1,x607_iters);
std::cout << "        x607 - " << x607_avg << " (" << x607_cycles << " / " << x607_iters << ") ";
std::cout << "[" << x607_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "        x607 - " << x607_avg << " (" << x607_cycles << " / " << x607_iters << ") ";
instrumentation << "[" << x607_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x709, 5 -> x602, 4 -> x607, 1 -> x710, 3 -> x608), current node x602 is at depth 5
long x602_cycles = c1->getArg(X602_cycles_arg, false);
long x602_iters = c1->getArg(X602_iters_arg, false);
long x602_iters_per_parent = x602_iters / std::max((long)1,x607_iters);
long x602_avg = x602_cycles / std::max((long)1,x602_iters);
std::cout << "          x602 - " << x602_avg << " (" << x602_cycles << " / " << x602_iters << ") ";
std::cout << "[" << x602_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x602 - " << x602_avg << " (" << x602_cycles << " / " << x602_iters << ") ";
instrumentation << "[" << x602_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x709, 5 -> x602, 4 -> x607, 1 -> x710, 3 -> x608, 6 -> x582), current node x582 is at depth 6
long x582_cycles = c1->getArg(X582_cycles_arg, false);
long x582_iters = c1->getArg(X582_iters_arg, false);
long x582_iters_per_parent = x582_iters / std::max((long)1,x602_iters);
long x582_avg = x582_cycles / std::max((long)1,x582_iters);
std::cout << "            x582 - " << x582_avg << " (" << x582_cycles << " / " << x582_iters << ") ";
std::cout << "[" << x582_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "            x582 - " << x582_avg << " (" << x582_cycles << " / " << x582_iters << ") ";
instrumentation << "[" << x582_iters_per_parent << " iters/parent execution]";
}
long x582_stalled = c1->getArg(X582_stalled_arg, false);
long x582_idle = c1->getArg(X582_idle_arg, false);
std::cout << " <# stalled: " << x582_stalled << ", #idle: " << x582_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x582_stalled << ", # idle: " << x582_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x709, 5 -> x602, 4 -> x607, 1 -> x710, 3 -> x608, 6 -> x601), current node x601 is at depth 6
long x601_cycles = c1->getArg(X601_cycles_arg, false);
long x601_iters = c1->getArg(X601_iters_arg, false);
long x601_iters_per_parent = x601_iters / std::max((long)1,x602_iters);
long x601_avg = x601_cycles / std::max((long)1,x601_iters);
std::cout << "            x601 - " << x601_avg << " (" << x601_cycles << " / " << x601_iters << ") ";
std::cout << "[" << x601_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "            x601 - " << x601_avg << " (" << x601_cycles << " / " << x601_iters << ") ";
instrumentation << "[" << x601_iters_per_parent << " iters/parent execution]";
}
long x601_stalled = c1->getArg(X601_stalled_arg, false);
long x601_idle = c1->getArg(X601_idle_arg, false);
std::cout << " <# stalled: " << x601_stalled << ", #idle: " << x601_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x601_stalled << ", # idle: " << x601_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x709, 5 -> x606, 4 -> x607, 1 -> x710, 3 -> x608, 6 -> x601), current node x606 is at depth 5
long x606_cycles = c1->getArg(X606_cycles_arg, false);
long x606_iters = c1->getArg(X606_iters_arg, false);
long x606_iters_per_parent = x606_iters / std::max((long)1,x607_iters);
long x606_avg = x606_cycles / std::max((long)1,x606_iters);
std::cout << "          x606 - " << x606_avg << " (" << x606_cycles << " / " << x606_iters << ") ";
std::cout << "[" << x606_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x606 - " << x606_avg << " (" << x606_cycles << " / " << x606_iters << ") ";
instrumentation << "[" << x606_iters_per_parent << " iters/parent execution]";
}
long x606_stalled = c1->getArg(X606_stalled_arg, false);
long x606_idle = c1->getArg(X606_idle_arg, false);
std::cout << " <# stalled: " << x606_stalled << ", #idle: " << x606_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x606_stalled << ", # idle: " << x606_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x709, 5 -> x606, 4 -> x607, 1 -> x710, 3 -> x664, 6 -> x601), current node x664 is at depth 3
long x664_cycles = c1->getArg(X664_cycles_arg, false);
long x664_iters = c1->getArg(X664_iters_arg, false);
long x664_iters_per_parent = x664_iters / std::max((long)1,x709_iters);
long x664_avg = x664_cycles / std::max((long)1,x664_iters);
std::cout << "      x664 - " << x664_avg << " (" << x664_cycles << " / " << x664_iters << ") ";
std::cout << "[" << x664_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x664 - " << x664_avg << " (" << x664_cycles << " / " << x664_iters << ") ";
instrumentation << "[" << x664_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x709, 5 -> x606, 4 -> x663, 1 -> x710, 3 -> x664, 6 -> x601), current node x663 is at depth 4
long x663_cycles = c1->getArg(X663_cycles_arg, false);
long x663_iters = c1->getArg(X663_iters_arg, false);
long x663_iters_per_parent = x663_iters / std::max((long)1,x664_iters);
long x663_avg = x663_cycles / std::max((long)1,x663_iters);
std::cout << "        x663 - " << x663_avg << " (" << x663_cycles << " / " << x663_iters << ") ";
std::cout << "[" << x663_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "        x663 - " << x663_avg << " (" << x663_cycles << " / " << x663_iters << ") ";
instrumentation << "[" << x663_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x709, 5 -> x658, 4 -> x663, 1 -> x710, 3 -> x664, 6 -> x601), current node x658 is at depth 5
long x658_cycles = c1->getArg(X658_cycles_arg, false);
long x658_iters = c1->getArg(X658_iters_arg, false);
long x658_iters_per_parent = x658_iters / std::max((long)1,x663_iters);
long x658_avg = x658_cycles / std::max((long)1,x658_iters);
std::cout << "          x658 - " << x658_avg << " (" << x658_cycles << " / " << x658_iters << ") ";
std::cout << "[" << x658_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x658 - " << x658_avg << " (" << x658_cycles << " / " << x658_iters << ") ";
instrumentation << "[" << x658_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x709, 5 -> x658, 4 -> x663, 1 -> x710, 3 -> x664, 6 -> x638), current node x638 is at depth 6
long x638_cycles = c1->getArg(X638_cycles_arg, false);
long x638_iters = c1->getArg(X638_iters_arg, false);
long x638_iters_per_parent = x638_iters / std::max((long)1,x658_iters);
long x638_avg = x638_cycles / std::max((long)1,x638_iters);
std::cout << "            x638 - " << x638_avg << " (" << x638_cycles << " / " << x638_iters << ") ";
std::cout << "[" << x638_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "            x638 - " << x638_avg << " (" << x638_cycles << " / " << x638_iters << ") ";
instrumentation << "[" << x638_iters_per_parent << " iters/parent execution]";
}
long x638_stalled = c1->getArg(X638_stalled_arg, false);
long x638_idle = c1->getArg(X638_idle_arg, false);
std::cout << " <# stalled: " << x638_stalled << ", #idle: " << x638_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x638_stalled << ", # idle: " << x638_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x709, 5 -> x658, 4 -> x663, 1 -> x710, 3 -> x664, 6 -> x657), current node x657 is at depth 6
long x657_cycles = c1->getArg(X657_cycles_arg, false);
long x657_iters = c1->getArg(X657_iters_arg, false);
long x657_iters_per_parent = x657_iters / std::max((long)1,x658_iters);
long x657_avg = x657_cycles / std::max((long)1,x657_iters);
std::cout << "            x657 - " << x657_avg << " (" << x657_cycles << " / " << x657_iters << ") ";
std::cout << "[" << x657_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "            x657 - " << x657_avg << " (" << x657_cycles << " / " << x657_iters << ") ";
instrumentation << "[" << x657_iters_per_parent << " iters/parent execution]";
}
long x657_stalled = c1->getArg(X657_stalled_arg, false);
long x657_idle = c1->getArg(X657_idle_arg, false);
std::cout << " <# stalled: " << x657_stalled << ", #idle: " << x657_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x657_stalled << ", # idle: " << x657_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x709, 5 -> x662, 4 -> x663, 1 -> x710, 3 -> x664, 6 -> x657), current node x662 is at depth 5
long x662_cycles = c1->getArg(X662_cycles_arg, false);
long x662_iters = c1->getArg(X662_iters_arg, false);
long x662_iters_per_parent = x662_iters / std::max((long)1,x663_iters);
long x662_avg = x662_cycles / std::max((long)1,x662_iters);
std::cout << "          x662 - " << x662_avg << " (" << x662_cycles << " / " << x662_iters << ") ";
std::cout << "[" << x662_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x662 - " << x662_avg << " (" << x662_cycles << " / " << x662_iters << ") ";
instrumentation << "[" << x662_iters_per_parent << " iters/parent execution]";
}
long x662_stalled = c1->getArg(X662_stalled_arg, false);
long x662_idle = c1->getArg(X662_idle_arg, false);
std::cout << " <# stalled: " << x662_stalled << ", #idle: " << x662_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x662_stalled << ", # idle: " << x662_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
instrumentation.close();
vector<float>* x665 = new vector<float>(6);
c1->memcpy(&(*x665)[0], x461, (*x665).size() * sizeof(float));
vector<float>* x667 = new vector<float>(6);
c1->memcpy(&(*x667)[0], x462, (*x667).size() * sizeof(float));
std::cout << string("Real Output:\n");
float x670 = (*x665)[0];
string x671 = std::to_string(x670);
string x672 = (string("real(0,0): ") + x671);
string x673 = (x672 + string("\n"));
std::cout << x673;
float x675 = (*x665)[2];
string x676 = std::to_string(x675);
string x677 = (string("real(1,0): ") + x676);
string x678 = (x677 + string("\n"));
std::cout << x678;
std::cout << string("Imag Output:\n");
float x681 = (*x667)[0];
string x682 = std::to_string(x681);
string x683 = (string("imag(0,0): ") + x682);
string x684 = (x683 + string("\n"));
std::cout << x684;
float x686 = (*x667)[2];
string x687 = std::to_string(x686);
string x688 = (string("imag(1,0): ") + x687);
string x689 = (x688 + string("\n"));
std::cout << x689;
bool x691 = 1 == x670;
bool x692 = 2 == x675;
bool x693 = x691 & x692;
int32_t x694;
if (x693) { 
x694 = 1;
}
else {
x694 = 0;
}
string x695 = std::to_string(x694);
string x696 = (string("PASS: ") + x695);
string x697 = (x696 + string("\n"));
std::cout << x697;
bool x699 = x694 == 1;
string x700 = ("\n=================\n" + (string("STFTKernelTest.scala:64:11: Assertion failure") + "\n=================\n"));
if (true) { std::cout << std::flush; ASSERT(x699, "%s", x700.c_str()); }
delete c1;
}

void printHelp() {
fprintf(stderr, "Help for app: STFTKernelTest\n");
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
