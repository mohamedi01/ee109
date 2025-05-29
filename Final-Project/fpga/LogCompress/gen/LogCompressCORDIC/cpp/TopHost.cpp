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
  vector<string>* x319 = args;
  string x320;
  try {
    x320 = (*x319).at(0);
  }
  catch (std::out_of_range& e) {
    printHelp();
  }
  int32_t x321 = std::stol(x320);
  string x322;
  try {
    x322 = (*x319).at(1);
  }
  catch (std::out_of_range& e) {
    printHelp();
  }
  float x323 = std::stof(x322);
  vector<float>* x324 = new vector<float>(16);
  (*x324)[0] = 2.718281745910644531250;
  (*x324)[1] = 1.648721218109130859375;
  (*x324)[2] = 1.2840254306793212890625;
  (*x324)[3] = 1.1331484317779541015625;
  (*x324)[4] = 1.06449449062347412109375;
  (*x324)[5] = 1.03174340724945068359375;
  (*x324)[6] = 1.01574766635894775390625;
  (*x324)[7] = 1.00784313678741455078125;
  (*x324)[8] = 1.00391387939453125;
  (*x324)[9] = 1.0019550323486328125;
  (*x324)[10] = 1.000977039337158203125;
  (*x324)[11] = 1.00048840045928955078125;
  (*x324)[12] = 1.000244140625;
  (*x324)[13] = 1.0001220703125;
  (*x324)[14] = 1.00006103515625;
  (*x324)[15] = 1.000030517578125;
  vector<float>* x325 = new vector<float>(16);
  (*x325)[0] = 1;
  (*x325)[1] = 0.5;
  (*x325)[2] = 0.25;
  (*x325)[3] = 0.125;
  (*x325)[4] = 0.0625;
  (*x325)[5] = 0.03125;
  (*x325)[6] = 0.015625;
  (*x325)[7] = 0.0078125;
  (*x325)[8] = 0.00390625;
  (*x325)[9] = 0.001953125;
  (*x325)[10] = 0.0009765625;
  (*x325)[11] = 0.00048828125;
  (*x325)[12] = 0.000244140625;
  (*x325)[13] = 0.0001220703125;
  (*x325)[14] = 0.00006103515625;
  (*x325)[15] = 0.000030517578125;
  int32_t x326 = 0;
  c1->setArg(X326_arg, x321, false);
  x326 = x321;
  int32_t x328 = x326;
  uint64_t x329 = c1->malloc(sizeof(float) * x328);
  c1->setArg(INDRAM_ptr, x329, false);
  printf("Allocate mem of size x328 at %p\n", (void*)x329);
  uint64_t x330 = c1->malloc(sizeof(float) * x328);
  c1->setArg(OUTDRAM_ptr, x330, false);
  printf("Allocate mem of size x328 at %p\n", (void*)x330);
  uint64_t x331 = c1->malloc(sizeof(float) * 16);
  c1->setArg(CONSTDRAM_ptr, x331, false);
  printf("Allocate mem of size 16 at %p\n", (void*)x331);
  uint64_t x332 = c1->malloc(sizeof(float) * 16);
  c1->setArg(TWONEGDRAM_ptr, x332, false);
  printf("Allocate mem of size 16 at %p\n", (void*)x332);
  c1->memcpy(x331, &(*x324)[0], (*x324).size() * sizeof(float));
  c1->memcpy(x332, &(*x325)[0], (*x325).size() * sizeof(float));
  int32_t x335 = 0;
  c1->setArg(ACCEL_N_arg, x321, false);
  x335 = x321;
  float x337 = 0.0;
  int64_t x323_raw;
  memcpy(&x323_raw, &x323, sizeof(x323));
  x323_raw = x323_raw & ((int64_t) 0 | (int64_t) pow(2,32) - 1);
  c1->setArg(X337_arg, x323_raw, false); // x337
  x337 = x323;
// Register ArgIns and ArgIOs in case some are unused
c1->setNumArgIns(3 + 4 + 0);
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
fprintf(stderr, "Help for app: LogCompressCORDIC\n");
fprintf(stderr, "  -- Args:    <0: n_runtime> <1: dynRange>\n");
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
