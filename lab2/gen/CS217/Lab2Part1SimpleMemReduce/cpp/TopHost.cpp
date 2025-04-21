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
  uint64_t x160 = c1->malloc(sizeof(int32_t) * 16);
  c1->setArg(OUT_ptr, x160, false);
  printf("Allocate mem of size 16 at %p\n", (void*)x160);
// Register ArgIns and ArgIOs in case some are unused
c1->setNumArgIns(0 + 1 + 0);
c1->setNumArgIOs(0);
c1->setNumArgOuts(0);
c1->setNumArgOutInstrs(22);
c1->setNumEarlyExits(0);
c1->flushCache(1024);
time_t tstart = time(0);
c1->run();
time_t tend = time(0);
double elapsed = difftime(tend, tstart);
std::cout << "Kernel done, test run time = " << elapsed << " ms" << std::endl;
c1->flushCache(1024);
std::ofstream instrumentation ("./instrumentation.txt");
// Need to instrument List((x119,1), (x187,2), (x176,3), (x186,3), (x209,2), (x195,3), (x204,3), (x208,3))
std::cout << "ArgIns:" <<  " "  << ", ArgIOs:" <<  " "  << std::endl;
if (instrumentation.is_open()) {
instrumentation << "ArgIns:" <<  " "  << ", ArgIOs:" <<  " "  << std::endl;
}
 // immediate parent hashmap now Map(1 -> x119), current node x119 is at depth 1
long x119_cycles = c1->getArg(X119_cycles_arg, false);
long x119_iters = c1->getArg(X119_iters_arg, false);
long x119_iters_per_parent = x119_iters / std::max((long)1,x119_iters);
long x119_avg = x119_cycles / std::max((long)1,x119_iters);
std::cout << "  x119 - " << x119_avg << " (" << x119_cycles << " / " << x119_iters << ") ";
std::cout << "[" << x119_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "  x119 - " << x119_avg << " (" << x119_cycles << " / " << x119_iters << ") ";
instrumentation << "[" << x119_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x187, 1 -> x119), current node x187 is at depth 2
long x187_cycles = c1->getArg(X187_cycles_arg, false);
long x187_iters = c1->getArg(X187_iters_arg, false);
long x187_iters_per_parent = x187_iters / std::max((long)1,x119_iters);
long x187_avg = x187_cycles / std::max((long)1,x187_iters);
std::cout << "    x187 - " << x187_avg << " (" << x187_cycles << " / " << x187_iters << ") ";
std::cout << "[" << x187_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "    x187 - " << x187_avg << " (" << x187_cycles << " / " << x187_iters << ") ";
instrumentation << "[" << x187_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x187, 1 -> x119, 3 -> x176), current node x176 is at depth 3
long x176_cycles = c1->getArg(X176_cycles_arg, false);
long x176_iters = c1->getArg(X176_iters_arg, false);
long x176_iters_per_parent = x176_iters / std::max((long)1,x187_iters);
long x176_avg = x176_cycles / std::max((long)1,x176_iters);
std::cout << "      x176 - " << x176_avg << " (" << x176_cycles << " / " << x176_iters << ") ";
std::cout << "[" << x176_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x176 - " << x176_avg << " (" << x176_cycles << " / " << x176_iters << ") ";
instrumentation << "[" << x176_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x187, 1 -> x119, 3 -> x186), current node x186 is at depth 3
long x186_cycles = c1->getArg(X186_cycles_arg, false);
long x186_iters = c1->getArg(X186_iters_arg, false);
long x186_iters_per_parent = x186_iters / std::max((long)1,x187_iters);
long x186_avg = x186_cycles / std::max((long)1,x186_iters);
std::cout << "      x186 - " << x186_avg << " (" << x186_cycles << " / " << x186_iters << ") ";
std::cout << "[" << x186_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x186 - " << x186_avg << " (" << x186_cycles << " / " << x186_iters << ") ";
instrumentation << "[" << x186_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x209, 1 -> x119, 3 -> x186), current node x209 is at depth 2
long x209_cycles = c1->getArg(X209_cycles_arg, false);
long x209_iters = c1->getArg(X209_iters_arg, false);
long x209_iters_per_parent = x209_iters / std::max((long)1,x119_iters);
long x209_avg = x209_cycles / std::max((long)1,x209_iters);
std::cout << "    x209 - " << x209_avg << " (" << x209_cycles << " / " << x209_iters << ") ";
std::cout << "[" << x209_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "    x209 - " << x209_avg << " (" << x209_cycles << " / " << x209_iters << ") ";
instrumentation << "[" << x209_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x209, 1 -> x119, 3 -> x195), current node x195 is at depth 3
long x195_cycles = c1->getArg(X195_cycles_arg, false);
long x195_iters = c1->getArg(X195_iters_arg, false);
long x195_iters_per_parent = x195_iters / std::max((long)1,x209_iters);
long x195_avg = x195_cycles / std::max((long)1,x195_iters);
std::cout << "      x195 - " << x195_avg << " (" << x195_cycles << " / " << x195_iters << ") ";
std::cout << "[" << x195_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x195 - " << x195_avg << " (" << x195_cycles << " / " << x195_iters << ") ";
instrumentation << "[" << x195_iters_per_parent << " iters/parent execution]";
}
long x195_stalled = c1->getArg(X195_stalled_arg, false);
long x195_idle = c1->getArg(X195_idle_arg, false);
std::cout << " <# stalled: " << x195_stalled << ", #idle: " << x195_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x195_stalled << ", # idle: " << x195_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x209, 1 -> x119, 3 -> x204), current node x204 is at depth 3
long x204_cycles = c1->getArg(X204_cycles_arg, false);
long x204_iters = c1->getArg(X204_iters_arg, false);
long x204_iters_per_parent = x204_iters / std::max((long)1,x209_iters);
long x204_avg = x204_cycles / std::max((long)1,x204_iters);
std::cout << "      x204 - " << x204_avg << " (" << x204_cycles << " / " << x204_iters << ") ";
std::cout << "[" << x204_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x204 - " << x204_avg << " (" << x204_cycles << " / " << x204_iters << ") ";
instrumentation << "[" << x204_iters_per_parent << " iters/parent execution]";
}
long x204_stalled = c1->getArg(X204_stalled_arg, false);
long x204_idle = c1->getArg(X204_idle_arg, false);
std::cout << " <# stalled: " << x204_stalled << ", #idle: " << x204_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x204_stalled << ", # idle: " << x204_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x209, 1 -> x119, 3 -> x208), current node x208 is at depth 3
long x208_cycles = c1->getArg(X208_cycles_arg, false);
long x208_iters = c1->getArg(X208_iters_arg, false);
long x208_iters_per_parent = x208_iters / std::max((long)1,x209_iters);
long x208_avg = x208_cycles / std::max((long)1,x208_iters);
std::cout << "      x208 - " << x208_avg << " (" << x208_cycles << " / " << x208_iters << ") ";
std::cout << "[" << x208_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x208 - " << x208_avg << " (" << x208_cycles << " / " << x208_iters << ") ";
instrumentation << "[" << x208_iters_per_parent << " iters/parent execution]";
}
long x208_stalled = c1->getArg(X208_stalled_arg, false);
long x208_idle = c1->getArg(X208_idle_arg, false);
std::cout << " <# stalled: " << x208_stalled << ", #idle: " << x208_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x208_stalled << ", # idle: " << x208_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
instrumentation.close();
vector<int32_t>* x210 = new vector<int32_t>(16);
c1->memcpy(&(*x210)[0], x160, (*x210).size() * sizeof(int32_t));
vector<int32_t>* x212 = new vector<int32_t>(16);
for (int b26 = 0; b26 < 16; b26++) {
(*x212)[b26] = 10;
}
string x213 = (string("expected: ") + string("\n"));
std::cout << x213;
int32_t x215 = (*x212).size();
for (int b31 = 0; b31 < x215; b31 = b31 + 1) {
int32_t x216 = (*x212)[b31];
string x217 = std::to_string(x216);
string x218 = (x217 + string(" "));
std::cout << x218;
}
std::cout << string("\n");
string x222 = (string("result:   ") + string("\n"));
std::cout << x222;
int32_t x224 = (*x210).size();
for (int b41 = 0; b41 < x224; b41 = b41 + 1) {
int32_t x225 = (*x210)[b41];
string x226 = std::to_string(x225);
string x227 = (x226 + string(" "));
std::cout << x227;
}
std::cout << string("\n");
vector<bool>* x234 = new vector<bool>((*x212).size());
for (int b48 = 0; b48 < (*x212).size(); b48++) { 
int32_t x231 = (*x212)[b48];
int32_t x232 = (*x210)[b48];
bool x233 = x231 == x232;
(*x234)[b48] = x233;
}
bool x237;
if ((*x234).size() > 0) { // Hack to handle reductions on things of length 0
x237 = (*x234)[0];
}
else {
x237 = 0;
}
for (int b53 = 1; b53 < (*x234).size(); b53++) {
bool b54 = (*x234)[b53];
bool b55 = x237;
bool x236 = b54 & b55;
x237 = x236;
}
string x238 = x237 ? string("true") : string("false");
string x239 = (string("PASS: ") + x238);
string x240 = (x239 + string(" (Lab2Part1SimpleMemReduce)"));
string x241 = (x240 + string("\n"));
std::cout << x241;
string x243 = ("\n=================\n" + (string("Lab2Part1.scala:26:11: Assertion failure") + "\n=================\n"));
if (true) { std::cout << std::flush; ASSERT(x237, "%s", x243.c_str()); }
delete c1;
}

void printHelp() {
fprintf(stderr, "Help for app: Lab2Part1SimpleMemReduce\n");
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
