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
  uint64_t x206 = c1->malloc(sizeof(int32_t) * 32);
  c1->setArg(DRAM_ptr, x206, false);
  printf("Allocate mem of size 32 at %p\n", (void*)x206);
// Register ArgIns and ArgIOs in case some are unused
c1->setNumArgIns(0 + 1 + 0);
c1->setNumArgIOs(0);
c1->setNumArgOuts(0);
c1->setNumArgOutInstrs(18);
c1->setNumEarlyExits(0);
c1->flushCache(1024);
time_t tstart = time(0);
c1->run();
time_t tend = time(0);
double elapsed = difftime(tend, tstart);
std::cout << "Kernel done, test run time = " << elapsed << " ms" << std::endl;
c1->flushCache(1024);
std::ofstream instrumentation ("./instrumentation.txt");
// Need to instrument List((x164,1), (x236,2), (x258,2), (x244,3), (x253,3), (x257,3))
std::cout << "ArgIns:" <<  " "  << ", ArgIOs:" <<  " "  << std::endl;
if (instrumentation.is_open()) {
instrumentation << "ArgIns:" <<  " "  << ", ArgIOs:" <<  " "  << std::endl;
}
 // immediate parent hashmap now Map(1 -> x164), current node x164 is at depth 1
long x164_cycles = c1->getArg(X164_cycles_arg, false);
long x164_iters = c1->getArg(X164_iters_arg, false);
long x164_iters_per_parent = x164_iters / std::max((long)1,x164_iters);
long x164_avg = x164_cycles / std::max((long)1,x164_iters);
std::cout << "  x164 - " << x164_avg << " (" << x164_cycles << " / " << x164_iters << ") ";
std::cout << "[" << x164_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "  x164 - " << x164_avg << " (" << x164_cycles << " / " << x164_iters << ") ";
instrumentation << "[" << x164_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x236, 1 -> x164), current node x236 is at depth 2
long x236_cycles = c1->getArg(X236_cycles_arg, false);
long x236_iters = c1->getArg(X236_iters_arg, false);
long x236_iters_per_parent = x236_iters / std::max((long)1,x164_iters);
long x236_avg = x236_cycles / std::max((long)1,x236_iters);
std::cout << "    x236 - " << x236_avg << " (" << x236_cycles << " / " << x236_iters << ") ";
std::cout << "[" << x236_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "    x236 - " << x236_avg << " (" << x236_cycles << " / " << x236_iters << ") ";
instrumentation << "[" << x236_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x258, 1 -> x164), current node x258 is at depth 2
long x258_cycles = c1->getArg(X258_cycles_arg, false);
long x258_iters = c1->getArg(X258_iters_arg, false);
long x258_iters_per_parent = x258_iters / std::max((long)1,x164_iters);
long x258_avg = x258_cycles / std::max((long)1,x258_iters);
std::cout << "    x258 - " << x258_avg << " (" << x258_cycles << " / " << x258_iters << ") ";
std::cout << "[" << x258_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "    x258 - " << x258_avg << " (" << x258_cycles << " / " << x258_iters << ") ";
instrumentation << "[" << x258_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x258, 1 -> x164, 3 -> x244), current node x244 is at depth 3
long x244_cycles = c1->getArg(X244_cycles_arg, false);
long x244_iters = c1->getArg(X244_iters_arg, false);
long x244_iters_per_parent = x244_iters / std::max((long)1,x258_iters);
long x244_avg = x244_cycles / std::max((long)1,x244_iters);
std::cout << "      x244 - " << x244_avg << " (" << x244_cycles << " / " << x244_iters << ") ";
std::cout << "[" << x244_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x244 - " << x244_avg << " (" << x244_cycles << " / " << x244_iters << ") ";
instrumentation << "[" << x244_iters_per_parent << " iters/parent execution]";
}
long x244_stalled = c1->getArg(X244_stalled_arg, false);
long x244_idle = c1->getArg(X244_idle_arg, false);
std::cout << " <# stalled: " << x244_stalled << ", #idle: " << x244_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x244_stalled << ", # idle: " << x244_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x258, 1 -> x164, 3 -> x253), current node x253 is at depth 3
long x253_cycles = c1->getArg(X253_cycles_arg, false);
long x253_iters = c1->getArg(X253_iters_arg, false);
long x253_iters_per_parent = x253_iters / std::max((long)1,x258_iters);
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
 // immediate parent hashmap now Map(2 -> x258, 1 -> x164, 3 -> x257), current node x257 is at depth 3
long x257_cycles = c1->getArg(X257_cycles_arg, false);
long x257_iters = c1->getArg(X257_iters_arg, false);
long x257_iters_per_parent = x257_iters / std::max((long)1,x258_iters);
long x257_avg = x257_cycles / std::max((long)1,x257_iters);
std::cout << "      x257 - " << x257_avg << " (" << x257_cycles << " / " << x257_iters << ") ";
std::cout << "[" << x257_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x257 - " << x257_avg << " (" << x257_cycles << " / " << x257_iters << ") ";
instrumentation << "[" << x257_iters_per_parent << " iters/parent execution]";
}
long x257_stalled = c1->getArg(X257_stalled_arg, false);
long x257_idle = c1->getArg(X257_idle_arg, false);
std::cout << " <# stalled: " << x257_stalled << ", #idle: " << x257_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x257_stalled << ", # idle: " << x257_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
instrumentation.close();
vector<int32_t>* x259 = new vector<int32_t>(32);
c1->memcpy(&(*x259)[0], x206, (*x259).size() * sizeof(int32_t));
vector<int32_t>* x261 = new vector<int32_t>(32);
(*x261)[0] = 0;
(*x261)[1] = 1;
(*x261)[2] = 2;
(*x261)[3] = 3;
(*x261)[4] = 4;
(*x261)[5] = 5;
(*x261)[6] = 6;
(*x261)[7] = 7;
(*x261)[8] = 16;
(*x261)[9] = 18;
(*x261)[10] = 20;
(*x261)[11] = 22;
(*x261)[12] = 24;
(*x261)[13] = 26;
(*x261)[14] = 28;
(*x261)[15] = 30;
(*x261)[16] = 48;
(*x261)[17] = 51;
(*x261)[18] = 54;
(*x261)[19] = 57;
(*x261)[20] = 60;
(*x261)[21] = 63;
(*x261)[22] = 66;
(*x261)[23] = 69;
(*x261)[24] = 96;
(*x261)[25] = 100;
(*x261)[26] = 104;
(*x261)[27] = 108;
(*x261)[28] = 112;
(*x261)[29] = 116;
(*x261)[30] = 120;
(*x261)[31] = 124;
string x262 = (string("Result") + string("\n"));
std::cout << x262;
int32_t x264 = (*x259).size();
for (int b35 = 0; b35 < x264; b35 = b35 + 1) {
int32_t x265 = (*x259)[b35];
string x266 = std::to_string(x265);
string x267 = (x266 + string(" "));
std::cout << x267;
}
std::cout << string("\n");
string x271 = (string("Gold") + string("\n"));
std::cout << x271;
int32_t x273 = (*x261).size();
for (int b45 = 0; b45 < x273; b45 = b45 + 1) {
int32_t x274 = (*x261)[b45];
string x275 = std::to_string(x274);
string x276 = (x275 + string(" "));
std::cout << x276;
}
std::cout << string("\n");
vector<bool>* x283 = new vector<bool>((*x261).size());
for (int b52 = 0; b52 < (*x261).size(); b52++) { 
int32_t x280 = (*x261)[b52];
int32_t x281 = (*x259)[b52];
bool x282 = x280 == x281;
(*x283)[b52] = x282;
}
bool x286;
if ((*x283).size() > 0) { // Hack to handle reductions on things of length 0
x286 = (*x283)[0];
}
else {
x286 = 0;
}
for (int b57 = 1; b57 < (*x283).size(); b57++) {
bool b58 = (*x283)[b57];
bool b59 = x286;
bool x285 = b58 & b59;
x286 = x285;
}
string x287 = x286 ? string("true") : string("false");
string x288 = (string("PASS: ") + x287);
string x289 = (x288 + string(" (Lab2Part3BasicCondFSMAlt)"));
string x290 = (x289 + string("\n"));
std::cout << x290;
string x292 = ("\n=================\n" + (string("Lab2Part3.scala:71:11: Assertion failure") + "\n=================\n"));
if (true) { std::cout << std::flush; ASSERT(x286, "%s", x292.c_str()); }
delete c1;
}

void printHelp() {
fprintf(stderr, "Help for app: Lab2Part3BasicCondFSMAlt\n");
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
