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
  uint64_t x147 = c1->malloc(sizeof(float) * 152960);
  c1->setArg(INDRAM_ptr, x147, false);
  printf("Allocate mem of size 152960 at %p\n", (void*)x147);
  uint64_t x148 = c1->malloc(sizeof(float) * 152960);
  c1->setArg(OUTDRAM_ptr, x148, false);
  printf("Allocate mem of size 152960 at %p\n", (void*)x148);
  int32_t x149 = 0;
  c1->setArg(ACCEL_N_arg, 152960, false);
  x149 = 152960;
// Register ArgIns and ArgIOs in case some are unused
c1->setNumArgIns(1 + 2 + 0);
c1->setNumArgIOs(0);
c1->setNumArgOuts(0);
c1->setNumArgOutInstrs(0);
c1->setNumEarlyExits(0);
c1->flushCache(1024);
time_t tstart = time(0);
c1->run();
time_t tend = time(0);
double elapsed = difftime(tend, tstart);
std::cout << "Kernel done, test run time = " << elapsed << " ms" << std::endl;
c1->flushCache(1024);
vector<float>* x202 = new vector<float>(152960);
c1->memcpy(&(*x202)[0], x148, (*x202).size() * sizeof(float));
std::cout << string("FPGA_OUTPUT_START\n");
string x205 = (string("") + string("\n"));
std::cout << x205;
int32_t x207 = (*x202).size();
for (int b27 = 0; b27 < x207; b27 = b27 + 1) {
float x208 = (*x202)[b27];
string x209 = std::to_string(x208);
string x210 = (x209 + string(" "));
std::cout << x210;
}
std::cout << string("\n");
std::cout << string("FPGA_OUTPUT_END\n");
std::ofstream x215_file (string("../../fpga_io/fpga_output.csv"));
assert(x215_file.good() && "File Const(../../fpga_io/fpga_output.csv) does not exist"); 
for (int b36 = 0; b36 < x207; b36++) {
if (x215_file.is_open()) {
  float x216 = (*x202)[b36];
  string x217 = std::to_string(x216);
  x215_file << x217;
  x215_file << '\n';
}
}
x215_file.close();
delete c1;
}

void printHelp() {
fprintf(stderr, "Help for app: WhisperScale\n");
fprintf(stderr, "  -- Args:    <No input args>\n");
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
