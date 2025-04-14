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
  vector<string>* x83 = args;
  string x84;
  try {
    x84 = (*x83).at(0);
  }
  catch (std::out_of_range& e) {
    printHelp();
  }
  int32_t x85 = std::stol(x84);
  string x86;
  try {
    x86 = (*x83).at(1);
  }
  catch (std::out_of_range& e) {
    printHelp();
  }
  int32_t x87 = std::stol(x86);
  string x88;
  try {
    x88 = (*x83).at(2);
  }
  catch (std::out_of_range& e) {
    printHelp();
  }
  int32_t x89 = std::stol(x88);
  int32_t x90 = 0;
  int32_t x91 = 0;
  int32_t x92 = 0;
  c1->setArg(ARGREGIN0_arg, x85, false);
  x90 = x85;
  c1->setArg(ARGREGIN1_arg, x87, false);
  x91 = x87;
  c1->setArg(ARGREGIN2_arg, x89, false);
  x92 = x89;
  //int32_t* x96 = new int32_t {0}; // Initialize cpp argout ???
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
  // Need to instrument List((x59,1))
  std::cout << "ArgIns:" << x90 << " " << x91 << " " << x92 << ", ArgIOs:" <<  " "  << std::endl;
  if (instrumentation.is_open()) {
    instrumentation << "ArgIns:" << x90 << " " << x91 << " " << x92 << ", ArgIOs:" <<  " "  << std::endl;
  }
   // immediate parent hashmap now Map(1 -> x59), current node x59 is at depth 1
  long x59_cycles = c1->getArg(X59_cycles_arg, false);
  long x59_iters = c1->getArg(X59_iters_arg, false);
  long x59_iters_per_parent = x59_iters / std::max((long)1,x59_iters);
  long x59_avg = x59_cycles / std::max((long)1,x59_iters);
  std::cout << "  x59 - " << x59_avg << " (" << x59_cycles << " / " << x59_iters << ") ";
  std::cout << "[" << x59_iters_per_parent << " iters/parent execution]";
  if (instrumentation.is_open()) {
    instrumentation << "  x59 - " << x59_avg << " (" << x59_cycles << " / " << x59_iters << ") ";
    instrumentation << "[" << x59_iters_per_parent << " iters/parent execution]";
  }
  std::cout << std::endl;
  if (instrumentation.is_open()) {
    instrumentation << std::endl;
  }
  instrumentation.close();
  int64_t x103_tmp = c1->getArg(ARGREGOUT_arg, false);
  bool x103_sgned = true & ((x103_tmp & ((int64_t)1 << 31)) > 0); // Determine sign
  if (x103_sgned) x103_tmp = x103_tmp | ~(((int64_t)1 << 32)-1); // Sign-extend if necessary
  int32_t x103 = (int32_t) x103_tmp / ((int64_t)1 << 0);
  string x104 = std::to_string(x103);
  string x105 = (string("Result = ") + x104);
  string x106 = (x105 + string("\n"));
  std::cout << x106;
  int32_t x108 = x87 + x85;
  int32_t x109 = x108 + x89;
  string x110 = std::to_string(x109);
  string x111 = (string("Gold = ") + x110);
  string x112 = (x111 + string("\n"));
  std::cout << x112;
  bool x114 = x109 == x103;
  string x115 = x114 ? string("true") : string("false");
  string x116 = (string("PASS = ") + x115);
  string x117 = (x116 + string("\n"));
  std::cout << x117;
  string x119 = ("\n=================\n" + (string("Lab1.scala:62:15: Assertion failure") + "\n=================\n"));
  if (true) { std::cout << std::flush; ASSERT(x114, "%s", x119.c_str()); }
  delete c1;
}

void printHelp() {
  fprintf(stderr, "Help for app: Lab1Part1RegExample\n");
  fprintf(stderr, "  -- Args:    <0: N> <1: M> <2: K>\n");
  fprintf(stderr, "    -- Example: bash run.sh 3 5 7\n");
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
