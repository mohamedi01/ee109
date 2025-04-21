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
  vector<string>* x86 = args;
  int32_t x87 = 0;
  //int32_t* x88 = new int32_t {0}; // Initialize cpp argout ???
  int32_t x89 = 0;
  int32_t x90 = 0;
  string x91;
  try {
    x91 = (*x86).at(0);
  }
  catch (std::out_of_range& e) {
    printHelp();
  }
  int32_t x92 = std::stol(x91);
  string x93;
  try {
    x93 = (*x86).at(1);
  }
  catch (std::out_of_range& e) {
    printHelp();
  }
  int32_t x94 = std::stol(x93);
  string x95;
  try {
    x95 = (*x86).at(2);
  }
  catch (std::out_of_range& e) {
    printHelp();
  }
  int32_t x96 = std::stol(x95);
  c1->setArg(IN_arg, x92, false);
  x87 = x92;
  c1->setArg(I_arg, x94, false);
  x89 = x94;
  c1->setArg(J_arg, x96, false);
  x90 = x96;
  // Register ArgIns and ArgIOs in case some are unused
  c1->setNumArgIns(3 + 0 + 0);
  c1->setNumArgIOs(0);
  c1->setNumArgOuts(1);
  c1->setNumArgOutInstrs(2);
  c1->setNumEarlyExits(0);
  c1->flushCache(1024);
  time_t tstart = time(0);
  c1->run();
  time_t tend = time(0);
  double elapsed = difftime(tend, tstart);
  std::cout << "Kernel done, test run time = " << elapsed << " ms" << std::endl;
  c1->flushCache(1024);
  std::ofstream instrumentation ("./instrumentation.txt");
  // Need to instrument List((x61,1))
  std::cout << "ArgIns:" << x87 << " " << x89 << " " << x90 << ", ArgIOs:" <<  " "  << std::endl;
  if (instrumentation.is_open()) {
    instrumentation << "ArgIns:" << x87 << " " << x89 << " " << x90 << ", ArgIOs:" <<  " "  << std::endl;
  }
   // immediate parent hashmap now Map(1 -> x61), current node x61 is at depth 1
  long x61_cycles = c1->getArg(X61_cycles_arg, false);
  long x61_iters = c1->getArg(X61_iters_arg, false);
  long x61_iters_per_parent = x61_iters / std::max((long)1,x61_iters);
  long x61_avg = x61_cycles / std::max((long)1,x61_iters);
  std::cout << "  x61 - " << x61_avg << " (" << x61_cycles << " / " << x61_iters << ") ";
  std::cout << "[" << x61_iters_per_parent << " iters/parent execution]";
  if (instrumentation.is_open()) {
    instrumentation << "  x61 - " << x61_avg << " (" << x61_cycles << " / " << x61_iters << ") ";
    instrumentation << "[" << x61_iters_per_parent << " iters/parent execution]";
  }
  std::cout << std::endl;
  if (instrumentation.is_open()) {
    instrumentation << std::endl;
  }
  instrumentation.close();
  int64_t x110_tmp = c1->getArg(OUT_arg, false);
  bool x110_sgned = true & ((x110_tmp & ((int64_t)1 << 31)) > 0); // Determine sign
  if (x110_sgned) x110_tmp = x110_tmp | ~(((int64_t)1 << 32)-1); // Sign-extend if necessary
  int32_t x110 = (int32_t) x110_tmp / ((int64_t)1 << 0);
  vector<int32_t>* x112 = new vector<int32_t>(9);
  for (int b23 = 0; b23 < 9; b23++) {
    int32_t x111 = b23 + 1;
    (*x112)[b23] = x111;
  }
  int32_t x113 = x89;
  int32_t x114 = x113 * 3;
  int32_t x115 = x90;
  int32_t x116 = x114 + x115;
  int32_t x117 = (*x112)[x116];
  int32_t x118 = x92 + x117;
  bool x119 = x118 == x110;
  string x120 = x119 ? string("true") : string("false");
  string x121 = (string("PASS: ") + x120);
  string x122 = (x121 + string("(Lab2Part4LUT)"));
  string x123 = (x122 + string("\n"));
  std::cout << x123;
  string x125 = ("\n=================\n" + (string("Lab2Part4.scala:35:11: Assertion failure") + "\n=================\n"));
  if (true) { std::cout << std::flush; ASSERT(x119, "%s", x125.c_str()); }
  delete c1;
}

void printHelp() {
  fprintf(stderr, "Help for app: Lab2Part4LUT\n");
  fprintf(stderr, "  -- Args:    <0: input> <1: ind_i> <2: ind_j>\n");
  fprintf(stderr, "    -- Example: bash run.sh 0 0 0\n");
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
