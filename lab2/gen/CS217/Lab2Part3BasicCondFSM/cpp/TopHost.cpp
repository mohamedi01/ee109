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
  uint64_t x210 = c1->malloc(sizeof(int32_t) * 32);
  c1->setArg(DRAM_ptr, x210, false);
  printf("Allocate mem of size 32 at %p\n", (void*)x210);
// Register ArgIns and ArgIOs in case some are unused
c1->setNumArgIns(0 + 1 + 0);
c1->setNumArgIOs(0);
c1->setNumArgOuts(0);
c1->setNumArgOutInstrs(20);
c1->setNumEarlyExits(0);
c1->flushCache(1024);
time_t tstart = time(0);
c1->run();
time_t tend = time(0);
double elapsed = difftime(tend, tstart);
std::cout << "Kernel done, test run time = " << elapsed << " ms" << std::endl;
c1->flushCache(1024);
std::ofstream instrumentation ("./instrumentation.txt");
// Need to instrument List((x168,1), (x214,2), (x245,2), (x267,2), (x253,3), (x262,3), (x266,3))
std::cout << "ArgIns:" <<  " "  << ", ArgIOs:" <<  " "  << std::endl;
if (instrumentation.is_open()) {
instrumentation << "ArgIns:" <<  " "  << ", ArgIOs:" <<  " "  << std::endl;
}
 // immediate parent hashmap now Map(1 -> x168), current node x168 is at depth 1
long x168_cycles = c1->getArg(X168_cycles_arg, false);
long x168_iters = c1->getArg(X168_iters_arg, false);
long x168_iters_per_parent = x168_iters / std::max((long)1,x168_iters);
long x168_avg = x168_cycles / std::max((long)1,x168_iters);
std::cout << "  x168 - " << x168_avg << " (" << x168_cycles << " / " << x168_iters << ") ";
std::cout << "[" << x168_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "  x168 - " << x168_avg << " (" << x168_cycles << " / " << x168_iters << ") ";
instrumentation << "[" << x168_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x214, 1 -> x168), current node x214 is at depth 2
long x214_cycles = c1->getArg(X214_cycles_arg, false);
long x214_iters = c1->getArg(X214_iters_arg, false);
long x214_iters_per_parent = x214_iters / std::max((long)1,x168_iters);
long x214_avg = x214_cycles / std::max((long)1,x214_iters);
std::cout << "    x214 - " << x214_avg << " (" << x214_cycles << " / " << x214_iters << ") ";
std::cout << "[" << x214_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "    x214 - " << x214_avg << " (" << x214_cycles << " / " << x214_iters << ") ";
instrumentation << "[" << x214_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x245, 1 -> x168), current node x245 is at depth 2
long x245_cycles = c1->getArg(X245_cycles_arg, false);
long x245_iters = c1->getArg(X245_iters_arg, false);
long x245_iters_per_parent = x245_iters / std::max((long)1,x168_iters);
long x245_avg = x245_cycles / std::max((long)1,x245_iters);
std::cout << "    x245 - " << x245_avg << " (" << x245_cycles << " / " << x245_iters << ") ";
std::cout << "[" << x245_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "    x245 - " << x245_avg << " (" << x245_cycles << " / " << x245_iters << ") ";
instrumentation << "[" << x245_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x267, 1 -> x168), current node x267 is at depth 2
long x267_cycles = c1->getArg(X267_cycles_arg, false);
long x267_iters = c1->getArg(X267_iters_arg, false);
long x267_iters_per_parent = x267_iters / std::max((long)1,x168_iters);
long x267_avg = x267_cycles / std::max((long)1,x267_iters);
std::cout << "    x267 - " << x267_avg << " (" << x267_cycles << " / " << x267_iters << ") ";
std::cout << "[" << x267_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "    x267 - " << x267_avg << " (" << x267_cycles << " / " << x267_iters << ") ";
instrumentation << "[" << x267_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x267, 1 -> x168, 3 -> x253), current node x253 is at depth 3
long x253_cycles = c1->getArg(X253_cycles_arg, false);
long x253_iters = c1->getArg(X253_iters_arg, false);
long x253_iters_per_parent = x253_iters / std::max((long)1,x267_iters);
long x253_avg = x253_cycles / std::max((long)1,x253_iters);
std::cout << "      x253 - " << x253_avg << " (" << x253_cycles << " / " << x253_iters << ") ";
std::cout << "[" << x253_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x253 - " << x253_avg << " (" << x253_cycles << " / " << x253_iters << ") ";
instrumentation << "[" << x253_iters_per_parent << " iters/parent execution]";
}
long x253_stalled = c1->getArg(X253_stalled_arg, false);
long x253_idle = c1->getArg(X253_idle_arg, false);
std::cout << " <# stalled: " << x253_stalled << ", #idle: " << x253_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x253_stalled << ", # idle: " << x253_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x267, 1 -> x168, 3 -> x262), current node x262 is at depth 3
long x262_cycles = c1->getArg(X262_cycles_arg, false);
long x262_iters = c1->getArg(X262_iters_arg, false);
long x262_iters_per_parent = x262_iters / std::max((long)1,x267_iters);
long x262_avg = x262_cycles / std::max((long)1,x262_iters);
std::cout << "      x262 - " << x262_avg << " (" << x262_cycles << " / " << x262_iters << ") ";
std::cout << "[" << x262_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x262 - " << x262_avg << " (" << x262_cycles << " / " << x262_iters << ") ";
instrumentation << "[" << x262_iters_per_parent << " iters/parent execution]";
}
long x262_stalled = c1->getArg(X262_stalled_arg, false);
long x262_idle = c1->getArg(X262_idle_arg, false);
std::cout << " <# stalled: " << x262_stalled << ", #idle: " << x262_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x262_stalled << ", # idle: " << x262_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x267, 1 -> x168, 3 -> x266), current node x266 is at depth 3
long x266_cycles = c1->getArg(X266_cycles_arg, false);
long x266_iters = c1->getArg(X266_iters_arg, false);
long x266_iters_per_parent = x266_iters / std::max((long)1,x267_iters);
long x266_avg = x266_cycles / std::max((long)1,x266_iters);
std::cout << "      x266 - " << x266_avg << " (" << x266_cycles << " / " << x266_iters << ") ";
std::cout << "[" << x266_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x266 - " << x266_avg << " (" << x266_cycles << " / " << x266_iters << ") ";
instrumentation << "[" << x266_iters_per_parent << " iters/parent execution]";
}
long x266_stalled = c1->getArg(X266_stalled_arg, false);
long x266_idle = c1->getArg(X266_idle_arg, false);
std::cout << " <# stalled: " << x266_stalled << ", #idle: " << x266_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x266_stalled << ", # idle: " << x266_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
instrumentation.close();
vector<int32_t>* x268 = new vector<int32_t>(32);
c1->memcpy(&(*x268)[0], x210, (*x268).size() * sizeof(int32_t));
vector<int32_t>* x270 = new vector<int32_t>(32);
(*x270)[0] = 17;
(*x270)[1] = 16;
(*x270)[2] = 18;
(*x270)[3] = 19;
(*x270)[4] = 20;
(*x270)[5] = 21;
(*x270)[6] = 22;
(*x270)[7] = 23;
(*x270)[8] = 24;
(*x270)[9] = 25;
(*x270)[10] = 26;
(*x270)[11] = 27;
(*x270)[12] = 28;
(*x270)[13] = 29;
(*x270)[14] = 30;
(*x270)[15] = 31;
(*x270)[16] = 16;
(*x270)[17] = 15;
(*x270)[18] = 14;
(*x270)[19] = 13;
(*x270)[20] = 12;
(*x270)[21] = 11;
(*x270)[22] = 10;
(*x270)[23] = 9;
(*x270)[24] = 7;
(*x270)[25] = 6;
(*x270)[26] = 5;
(*x270)[27] = 4;
(*x270)[28] = 3;
(*x270)[29] = 2;
(*x270)[30] = 1;
(*x270)[31] = 0;
string x271 = (string("Result") + string("\n"));
std::cout << x271;
int32_t x273 = (*x268).size();
for (int b34 = 0; b34 < x273; b34 = b34 + 1) {
int32_t x274 = (*x268)[b34];
string x275 = std::to_string(x274);
string x276 = (x275 + string(" "));
std::cout << x276;
}
std::cout << string("\n");
string x280 = (string("Gold") + string("\n"));
std::cout << x280;
int32_t x282 = (*x270).size();
for (int b44 = 0; b44 < x282; b44 = b44 + 1) {
int32_t x283 = (*x270)[b44];
string x284 = std::to_string(x283);
string x285 = (x284 + string(" "));
std::cout << x285;
}
std::cout << string("\n");
vector<bool>* x292 = new vector<bool>((*x270).size());
for (int b51 = 0; b51 < (*x270).size(); b51++) { 
int32_t x289 = (*x270)[b51];
int32_t x290 = (*x268)[b51];
bool x291 = x289 == x290;
(*x292)[b51] = x291;
}
bool x295;
if ((*x292).size() > 0) { // Hack to handle reductions on things of length 0
x295 = (*x292)[0];
}
else {
x295 = 0;
}
for (int b56 = 1; b56 < (*x292).size(); b56++) {
bool b57 = (*x292)[b56];
bool b58 = x295;
bool x294 = b57 & b58;
x295 = x294;
}
string x296 = x295 ? string("true") : string("false");
string x297 = (string("PASS: ") + x296);
string x298 = (x297 + string(" (Lab2Part3BasicCondFSM)"));
string x299 = (x298 + string("\n"));
std::cout << x299;
string x301 = ("\n=================\n" + (string("Lab2Part3.scala:35:11: Assertion failure") + "\n=================\n"));
if (true) { std::cout << std::flush; ASSERT(x295, "%s", x301.c_str()); }
delete c1;
}

void printHelp() {
fprintf(stderr, "Help for app: Lab2Part3BasicCondFSM\n");
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
