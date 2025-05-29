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
  vector<string>* x203 = args;
  string x204;
  try {
    x204 = (*x203).at(0);
  }
  catch (std::out_of_range& e) {
    printHelp();
  }
  int32_t x205 = std::stol(x204);
  int32_t x206 = 0;
  c1->setArg(X206_arg, x205, false);
  x206 = x205;
  int32_t x208 = x206;
  uint64_t x209 = c1->malloc(sizeof(float) * x208);
  c1->setArg(INDRAM_ptr, x209, false);
  printf("Allocate mem of size x208 at %p\n", (void*)x209);
  uint64_t x210 = c1->malloc(sizeof(float) * x208);
  c1->setArg(OUTDRAM_ptr, x210, false);
  printf("Allocate mem of size x208 at %p\n", (void*)x210);
  int32_t x211 = 0;
  c1->setArg(ACCEL_N_arg, x205, false);
  x211 = x205;
// Register ArgIns and ArgIOs in case some are unused
c1->setNumArgIns(2 + 2 + 0);
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
delete c1;
}

void printHelp() {
fprintf(stderr, "Help for app: WhisperScale\n");
fprintf(stderr, "  -- Args:    <0: n_runtime>\n");
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
