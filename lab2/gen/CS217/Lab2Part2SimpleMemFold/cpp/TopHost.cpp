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
// Need to instrument List((x119,1), (x186,2), (x176,3), (x185,3), (x208,2), (x194,3), (x203,3), (x207,3))
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
 // immediate parent hashmap now Map(2 -> x186, 1 -> x119), current node x186 is at depth 2
long x186_cycles = c1->getArg(X186_cycles_arg, false);
long x186_iters = c1->getArg(X186_iters_arg, false);
long x186_iters_per_parent = x186_iters / std::max((long)1,x119_iters);
long x186_avg = x186_cycles / std::max((long)1,x186_iters);
std::cout << "    x186 - " << x186_avg << " (" << x186_cycles << " / " << x186_iters << ") ";
std::cout << "[" << x186_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "    x186 - " << x186_avg << " (" << x186_cycles << " / " << x186_iters << ") ";
instrumentation << "[" << x186_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x186, 1 -> x119, 3 -> x176), current node x176 is at depth 3
long x176_cycles = c1->getArg(X176_cycles_arg, false);
long x176_iters = c1->getArg(X176_iters_arg, false);
long x176_iters_per_parent = x176_iters / std::max((long)1,x186_iters);
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
 // immediate parent hashmap now Map(2 -> x186, 1 -> x119, 3 -> x185), current node x185 is at depth 3
long x185_cycles = c1->getArg(X185_cycles_arg, false);
long x185_iters = c1->getArg(X185_iters_arg, false);
long x185_iters_per_parent = x185_iters / std::max((long)1,x186_iters);
long x185_avg = x185_cycles / std::max((long)1,x185_iters);
std::cout << "      x185 - " << x185_avg << " (" << x185_cycles << " / " << x185_iters << ") ";
std::cout << "[" << x185_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x185 - " << x185_avg << " (" << x185_cycles << " / " << x185_iters << ") ";
instrumentation << "[" << x185_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x208, 1 -> x119, 3 -> x185), current node x208 is at depth 2
long x208_cycles = c1->getArg(X208_cycles_arg, false);
long x208_iters = c1->getArg(X208_iters_arg, false);
long x208_iters_per_parent = x208_iters / std::max((long)1,x119_iters);
long x208_avg = x208_cycles / std::max((long)1,x208_iters);
std::cout << "    x208 - " << x208_avg << " (" << x208_cycles << " / " << x208_iters << ") ";
std::cout << "[" << x208_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "    x208 - " << x208_avg << " (" << x208_cycles << " / " << x208_iters << ") ";
instrumentation << "[" << x208_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x208, 1 -> x119, 3 -> x194), current node x194 is at depth 3
long x194_cycles = c1->getArg(X194_cycles_arg, false);
long x194_iters = c1->getArg(X194_iters_arg, false);
long x194_iters_per_parent = x194_iters / std::max((long)1,x208_iters);
long x194_avg = x194_cycles / std::max((long)1,x194_iters);
std::cout << "      x194 - " << x194_avg << " (" << x194_cycles << " / " << x194_iters << ") ";
std::cout << "[" << x194_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x194 - " << x194_avg << " (" << x194_cycles << " / " << x194_iters << ") ";
instrumentation << "[" << x194_iters_per_parent << " iters/parent execution]";
}
long x194_stalled = c1->getArg(X194_stalled_arg, false);
long x194_idle = c1->getArg(X194_idle_arg, false);
std::cout << " <# stalled: " << x194_stalled << ", #idle: " << x194_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x194_stalled << ", # idle: " << x194_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x208, 1 -> x119, 3 -> x203), current node x203 is at depth 3
long x203_cycles = c1->getArg(X203_cycles_arg, false);
long x203_iters = c1->getArg(X203_iters_arg, false);
long x203_iters_per_parent = x203_iters / std::max((long)1,x208_iters);
long x203_avg = x203_cycles / std::max((long)1,x203_iters);
std::cout << "      x203 - " << x203_avg << " (" << x203_cycles << " / " << x203_iters << ") ";
std::cout << "[" << x203_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x203 - " << x203_avg << " (" << x203_cycles << " / " << x203_iters << ") ";
instrumentation << "[" << x203_iters_per_parent << " iters/parent execution]";
}
long x203_stalled = c1->getArg(X203_stalled_arg, false);
long x203_idle = c1->getArg(X203_idle_arg, false);
std::cout << " <# stalled: " << x203_stalled << ", #idle: " << x203_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x203_stalled << ", # idle: " << x203_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x208, 1 -> x119, 3 -> x207), current node x207 is at depth 3
long x207_cycles = c1->getArg(X207_cycles_arg, false);
long x207_iters = c1->getArg(X207_iters_arg, false);
long x207_iters_per_parent = x207_iters / std::max((long)1,x208_iters);
long x207_avg = x207_cycles / std::max((long)1,x207_iters);
std::cout << "      x207 - " << x207_avg << " (" << x207_cycles << " / " << x207_iters << ") ";
std::cout << "[" << x207_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x207 - " << x207_avg << " (" << x207_cycles << " / " << x207_iters << ") ";
instrumentation << "[" << x207_iters_per_parent << " iters/parent execution]";
}
long x207_stalled = c1->getArg(X207_stalled_arg, false);
long x207_idle = c1->getArg(X207_idle_arg, false);
std::cout << " <# stalled: " << x207_stalled << ", #idle: " << x207_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x207_stalled << ", # idle: " << x207_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
instrumentation.close();
vector<int32_t>* x209 = new vector<int32_t>(16);
c1->memcpy(&(*x209)[0], x160, (*x209).size() * sizeof(int32_t));
vector<int32_t>* x211 = new vector<int32_t>(16);
for (int b26 = 0; b26 < 16; b26++) {
(*x211)[b26] = 10;
}
string x212 = (string("expected: ") + string("\n"));
std::cout << x212;
int32_t x214 = (*x211).size();
for (int b31 = 0; b31 < x214; b31 = b31 + 1) {
int32_t x215 = (*x211)[b31];
string x216 = std::to_string(x215);
string x217 = (x216 + string(" "));
std::cout << x217;
}
std::cout << string("\n");
string x221 = (string("result:   ") + string("\n"));
std::cout << x221;
int32_t x223 = (*x209).size();
for (int b41 = 0; b41 < x223; b41 = b41 + 1) {
int32_t x224 = (*x209)[b41];
string x225 = std::to_string(x224);
string x226 = (x225 + string(" "));
std::cout << x226;
}
std::cout << string("\n");
vector<bool>* x233 = new vector<bool>((*x211).size());
for (int b48 = 0; b48 < (*x211).size(); b48++) { 
int32_t x230 = (*x211)[b48];
int32_t x231 = (*x209)[b48];
bool x232 = x230 == x231;
(*x233)[b48] = x232;
}
bool x236;
if ((*x233).size() > 0) { // Hack to handle reductions on things of length 0
x236 = (*x233)[0];
}
else {
x236 = 0;
}
for (int b53 = 1; b53 < (*x233).size(); b53++) {
bool b54 = (*x233)[b53];
bool b55 = x236;
bool x235 = b54 & b55;
x236 = x235;
}
string x237 = x236 ? string("true") : string("false");
string x238 = (string("PASS: ") + x237);
string x239 = (x238 + string(" (Lab2Part2SimpleMemFold)"));
string x240 = (x239 + string("\n"));
std::cout << x240;
string x242 = ("\n=================\n" + (string("Lab2Part1.scala:54:11: Assertion failure") + "\n=================\n"));
if (true) { std::cout << std::flush; ASSERT(x236, "%s", x242.c_str()); }
delete c1;
}

void printHelp() {
fprintf(stderr, "Help for app: Lab2Part2SimpleMemFold\n");
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
