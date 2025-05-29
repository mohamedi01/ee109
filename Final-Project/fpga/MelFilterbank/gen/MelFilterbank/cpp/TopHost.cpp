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
  std::cout << string("DEBUG: MelFilterbank main method started.\n");
  std::cout << string("DEBUG: Read bands = Const(80), bins = Const(201) from config file.\n");
  uint64_t x291 = c1->malloc(sizeof(float) * 80*201);
  c1->setArg(MATDRAM_ptr, x291, false);
  printf("Allocate mem of size 80*201 at %p\n", (void*)x291);
  uint64_t x292 = c1->malloc(sizeof(float) * 201);
  c1->setArg(VECDRAM_ptr, x292, false);
  printf("Allocate mem of size 201 at %p\n", (void*)x292);
  uint64_t x293 = c1->malloc(sizeof(float) * 80);
  c1->setArg(OUTDRAM_ptr, x293, false);
  printf("Allocate mem of size 80 at %p\n", (void*)x293);
// Register ArgIns and ArgIOs in case some are unused
c1->setNumArgIns(0 + 3 + 0);
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
std::cout << string("DEBUG: MelFilterbank Accel block finished.\n");
delete c1;
}

void printHelp() {
fprintf(stderr, "Help for app: MelFilterbank\n");
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
