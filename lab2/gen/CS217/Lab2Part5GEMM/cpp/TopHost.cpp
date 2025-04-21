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
  vector<string>* x877 = args;
  int32_t x878 = 0;
  int32_t x879 = 0;
  int32_t x880 = 0;
  string x881;
  try {
    x881 = (*x877).at(0);
  }
  catch (std::out_of_range& e) {
    printHelp();
  }
  int32_t x882 = std::stol(x881);
  c1->setArg(M_arg, x882, false);
  x878 = x882;
  string x884;
  try {
    x884 = (*x877).at(1);
  }
  catch (std::out_of_range& e) {
    printHelp();
  }
  int32_t x885 = std::stol(x884);
  c1->setArg(N_arg, x885, false);
  x879 = x885;
  string x887;
  try {
    x887 = (*x877).at(2);
  }
  catch (std::out_of_range& e) {
    printHelp();
  }
  int32_t x888 = std::stol(x887);
  c1->setArg(K_arg, x888, false);
  x880 = x888;
  int32_t x890 = x882 * x888;
  vector<double>* x892 = new vector<double>(x890);
  for (int b16 = 0; b16 < x890; b16++) {
    double x891 = rand() % 3;
    (*x892)[b16] = x891;
  }
  int32_t x893 = x888 * x885;
  vector<double>* x895 = new vector<double>(x893);
  for (int b24 = 0; b24 < x893; b24++) {
    double x894 = rand() % 3;
    (*x895)[b24] = x894;
  }
  int32_t x896 = x882 * x885;
  vector<double>* x897 = new vector<double>(x896);
  for (int b31 = 0; b31 < x896; b31++) {
    (*x897)[b31] = 0;
  }
  int32_t x898 = x878;
  int32_t x899 = x880;
  uint64_t x900 = c1->malloc(sizeof(double) * x898*x899);
  c1->setArg(A_ptr, x900, false);
  printf("Allocate mem of size x898*x899 at %p\n", (void*)x900);
  int32_t x901 = x880;
  int32_t x902 = x879;
  uint64_t x903 = c1->malloc(sizeof(double) * x901*x902);
  c1->setArg(B_ptr, x903, false);
  printf("Allocate mem of size x901*x902 at %p\n", (void*)x903);
  int32_t x904 = x878;
  int32_t x905 = x879;
  uint64_t x906 = c1->malloc(sizeof(double) * x904*x905);
  c1->setArg(C_ptr, x906, false);
  printf("Allocate mem of size x904*x905 at %p\n", (void*)x906);
  vector<int32_t>* x907_rawified = new vector<int32_t>((*x892).size());
  for (int x907_rawified_i = 0; x907_rawified_i < (*x892).size(); x907_rawified_i++) {
    (*x907_rawified)[x907_rawified_i] = (int32_t) ((*x892)[x907_rawified_i] * ((int32_t)1 << 8));
  }
  c1->memcpy(x900, &(*x907_rawified)[0], (*x907_rawified).size() * sizeof(int32_t));
  vector<int32_t>* x908_rawified = new vector<int32_t>((*x895).size());
  for (int x908_rawified_i = 0; x908_rawified_i < (*x895).size(); x908_rawified_i++) {
    (*x908_rawified)[x908_rawified_i] = (int32_t) ((*x895)[x908_rawified_i] * ((int32_t)1 << 8));
  }
  c1->memcpy(x903, &(*x908_rawified)[0], (*x908_rawified).size() * sizeof(int32_t));
  vector<int32_t>* x909_rawified = new vector<int32_t>((*x897).size());
  for (int x909_rawified_i = 0; x909_rawified_i < (*x897).size(); x909_rawified_i++) {
    (*x909_rawified)[x909_rawified_i] = (int32_t) ((*x897)[x909_rawified_i] * ((int32_t)1 << 8));
  }
  c1->memcpy(x906, &(*x909_rawified)[0], (*x909_rawified).size() * sizeof(int32_t));
// Register ArgIns and ArgIOs in case some are unused
c1->setNumArgIns(3 + 3 + 0);
c1->setNumArgIOs(0);
c1->setNumArgOuts(0);
c1->setNumArgOutInstrs(88);
c1->setNumEarlyExits(0);
c1->flushCache(1024);
time_t tstart = time(0);
c1->run();
time_t tend = time(0);
double elapsed = difftime(tend, tstart);
std::cout << "Kernel done, test run time = " << elapsed << " ms" << std::endl;
c1->flushCache(1024);
std::ofstream instrumentation ("./instrumentation.txt");
// Need to instrument List((x810,1), (x1251,2), (x921,3), (x1250,3), (x932,4), (x1001,4), (x964,5), (x1000,5), (x981,6), (x999,6), (x1335,4), (x1015,5), (x1334,5), (x1085,6), (x1048,7), (x1084,7), (x1065,8), (x1083,8), (x1153,6), (x1115,7), (x1152,7), (x1132,8), (x1151,8), (x1188,5), (x1187,6), (x1186,7), (x1248,5), (x1247,6), (x1242,7), (x1222,8), (x1241,8), (x1246,7))
std::cout << "ArgIns:" << x878 << " " << x879 << " " << x880 << ", ArgIOs:" <<  " "  << std::endl;
if (instrumentation.is_open()) {
instrumentation << "ArgIns:" << x878 << " " << x879 << " " << x880 << ", ArgIOs:" <<  " "  << std::endl;
}
 // immediate parent hashmap now Map(1 -> x810), current node x810 is at depth 1
long x810_cycles = c1->getArg(X810_cycles_arg, false);
long x810_iters = c1->getArg(X810_iters_arg, false);
long x810_iters_per_parent = x810_iters / std::max((long)1,x810_iters);
long x810_avg = x810_cycles / std::max((long)1,x810_iters);
std::cout << "  x810 - " << x810_avg << " (" << x810_cycles << " / " << x810_iters << ") ";
std::cout << "[" << x810_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "  x810 - " << x810_avg << " (" << x810_cycles << " / " << x810_iters << ") ";
instrumentation << "[" << x810_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x1251, 1 -> x810), current node x1251 is at depth 2
long x1251_cycles = c1->getArg(X1251_cycles_arg, false);
long x1251_iters = c1->getArg(X1251_iters_arg, false);
long x1251_iters_per_parent = x1251_iters / std::max((long)1,x810_iters);
long x1251_avg = x1251_cycles / std::max((long)1,x1251_iters);
std::cout << "    x1251 - " << x1251_avg << " (" << x1251_cycles << " / " << x1251_iters << ") ";
std::cout << "[" << x1251_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "    x1251 - " << x1251_avg << " (" << x1251_cycles << " / " << x1251_iters << ") ";
instrumentation << "[" << x1251_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x1251, 1 -> x810, 3 -> x921), current node x921 is at depth 3
long x921_cycles = c1->getArg(X921_cycles_arg, false);
long x921_iters = c1->getArg(X921_iters_arg, false);
long x921_iters_per_parent = x921_iters / std::max((long)1,x1251_iters);
long x921_avg = x921_cycles / std::max((long)1,x921_iters);
std::cout << "      x921 - " << x921_avg << " (" << x921_cycles << " / " << x921_iters << ") ";
std::cout << "[" << x921_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x921 - " << x921_avg << " (" << x921_cycles << " / " << x921_iters << ") ";
instrumentation << "[" << x921_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x1251, 1 -> x810, 3 -> x1250), current node x1250 is at depth 3
long x1250_cycles = c1->getArg(X1250_cycles_arg, false);
long x1250_iters = c1->getArg(X1250_iters_arg, false);
long x1250_iters_per_parent = x1250_iters / std::max((long)1,x1251_iters);
long x1250_avg = x1250_cycles / std::max((long)1,x1250_iters);
std::cout << "      x1250 - " << x1250_avg << " (" << x1250_cycles << " / " << x1250_iters << ") ";
std::cout << "[" << x1250_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x1250 - " << x1250_avg << " (" << x1250_cycles << " / " << x1250_iters << ") ";
instrumentation << "[" << x1250_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x1251, 4 -> x932, 1 -> x810, 3 -> x1250), current node x932 is at depth 4
long x932_cycles = c1->getArg(X932_cycles_arg, false);
long x932_iters = c1->getArg(X932_iters_arg, false);
long x932_iters_per_parent = x932_iters / std::max((long)1,x1250_iters);
long x932_avg = x932_cycles / std::max((long)1,x932_iters);
std::cout << "        x932 - " << x932_avg << " (" << x932_cycles << " / " << x932_iters << ") ";
std::cout << "[" << x932_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "        x932 - " << x932_avg << " (" << x932_cycles << " / " << x932_iters << ") ";
instrumentation << "[" << x932_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x1251, 4 -> x1001, 1 -> x810, 3 -> x1250), current node x1001 is at depth 4
long x1001_cycles = c1->getArg(X1001_cycles_arg, false);
long x1001_iters = c1->getArg(X1001_iters_arg, false);
long x1001_iters_per_parent = x1001_iters / std::max((long)1,x1250_iters);
long x1001_avg = x1001_cycles / std::max((long)1,x1001_iters);
std::cout << "        x1001 - " << x1001_avg << " (" << x1001_cycles << " / " << x1001_iters << ") ";
std::cout << "[" << x1001_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "        x1001 - " << x1001_avg << " (" << x1001_cycles << " / " << x1001_iters << ") ";
instrumentation << "[" << x1001_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x1251, 5 -> x964, 4 -> x1001, 1 -> x810, 3 -> x1250), current node x964 is at depth 5
long x964_cycles = c1->getArg(X964_cycles_arg, false);
long x964_iters = c1->getArg(X964_iters_arg, false);
long x964_iters_per_parent = x964_iters / std::max((long)1,x1001_iters);
long x964_avg = x964_cycles / std::max((long)1,x964_iters);
std::cout << "          x964 - " << x964_avg << " (" << x964_cycles << " / " << x964_iters << ") ";
std::cout << "[" << x964_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x964 - " << x964_avg << " (" << x964_cycles << " / " << x964_iters << ") ";
instrumentation << "[" << x964_iters_per_parent << " iters/parent execution]";
}
long x964_stalled = c1->getArg(X964_stalled_arg, false);
long x964_idle = c1->getArg(X964_idle_arg, false);
std::cout << " <# stalled: " << x964_stalled << ", #idle: " << x964_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x964_stalled << ", # idle: " << x964_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x1251, 5 -> x1000, 4 -> x1001, 1 -> x810, 3 -> x1250), current node x1000 is at depth 5
long x1000_cycles = c1->getArg(X1000_cycles_arg, false);
long x1000_iters = c1->getArg(X1000_iters_arg, false);
long x1000_iters_per_parent = x1000_iters / std::max((long)1,x1001_iters);
long x1000_avg = x1000_cycles / std::max((long)1,x1000_iters);
std::cout << "          x1000 - " << x1000_avg << " (" << x1000_cycles << " / " << x1000_iters << ") ";
std::cout << "[" << x1000_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x1000 - " << x1000_avg << " (" << x1000_cycles << " / " << x1000_iters << ") ";
instrumentation << "[" << x1000_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x1251, 5 -> x1000, 4 -> x1001, 1 -> x810, 3 -> x1250, 6 -> x981), current node x981 is at depth 6
long x981_cycles = c1->getArg(X981_cycles_arg, false);
long x981_iters = c1->getArg(X981_iters_arg, false);
long x981_iters_per_parent = x981_iters / std::max((long)1,x1000_iters);
long x981_avg = x981_cycles / std::max((long)1,x981_iters);
std::cout << "            x981 - " << x981_avg << " (" << x981_cycles << " / " << x981_iters << ") ";
std::cout << "[" << x981_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "            x981 - " << x981_avg << " (" << x981_cycles << " / " << x981_iters << ") ";
instrumentation << "[" << x981_iters_per_parent << " iters/parent execution]";
}
long x981_stalled = c1->getArg(X981_stalled_arg, false);
long x981_idle = c1->getArg(X981_idle_arg, false);
std::cout << " <# stalled: " << x981_stalled << ", #idle: " << x981_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x981_stalled << ", # idle: " << x981_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x1251, 5 -> x1000, 4 -> x1001, 1 -> x810, 3 -> x1250, 6 -> x999), current node x999 is at depth 6
long x999_cycles = c1->getArg(X999_cycles_arg, false);
long x999_iters = c1->getArg(X999_iters_arg, false);
long x999_iters_per_parent = x999_iters / std::max((long)1,x1000_iters);
long x999_avg = x999_cycles / std::max((long)1,x999_iters);
std::cout << "            x999 - " << x999_avg << " (" << x999_cycles << " / " << x999_iters << ") ";
std::cout << "[" << x999_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "            x999 - " << x999_avg << " (" << x999_cycles << " / " << x999_iters << ") ";
instrumentation << "[" << x999_iters_per_parent << " iters/parent execution]";
}
long x999_stalled = c1->getArg(X999_stalled_arg, false);
long x999_idle = c1->getArg(X999_idle_arg, false);
std::cout << " <# stalled: " << x999_stalled << ", #idle: " << x999_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x999_stalled << ", # idle: " << x999_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x1251, 5 -> x1000, 4 -> x1335, 1 -> x810, 3 -> x1250, 6 -> x999), current node x1335 is at depth 4
long x1335_cycles = c1->getArg(X1335_cycles_arg, false);
long x1335_iters = c1->getArg(X1335_iters_arg, false);
long x1335_iters_per_parent = x1335_iters / std::max((long)1,x1250_iters);
long x1335_avg = x1335_cycles / std::max((long)1,x1335_iters);
std::cout << "        x1335 - " << x1335_avg << " (" << x1335_cycles << " / " << x1335_iters << ") ";
std::cout << "[" << x1335_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "        x1335 - " << x1335_avg << " (" << x1335_cycles << " / " << x1335_iters << ") ";
instrumentation << "[" << x1335_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x1251, 5 -> x1015, 4 -> x1335, 1 -> x810, 3 -> x1250, 6 -> x999), current node x1015 is at depth 5
long x1015_cycles = c1->getArg(X1015_cycles_arg, false);
long x1015_iters = c1->getArg(X1015_iters_arg, false);
long x1015_iters_per_parent = x1015_iters / std::max((long)1,x1335_iters);
long x1015_avg = x1015_cycles / std::max((long)1,x1015_iters);
std::cout << "          x1015 - " << x1015_avg << " (" << x1015_cycles << " / " << x1015_iters << ") ";
std::cout << "[" << x1015_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x1015 - " << x1015_avg << " (" << x1015_cycles << " / " << x1015_iters << ") ";
instrumentation << "[" << x1015_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x1251, 5 -> x1334, 4 -> x1335, 1 -> x810, 3 -> x1250, 6 -> x999), current node x1334 is at depth 5
long x1334_cycles = c1->getArg(X1334_cycles_arg, false);
long x1334_iters = c1->getArg(X1334_iters_arg, false);
long x1334_iters_per_parent = x1334_iters / std::max((long)1,x1335_iters);
long x1334_avg = x1334_cycles / std::max((long)1,x1334_iters);
std::cout << "          x1334 - " << x1334_avg << " (" << x1334_cycles << " / " << x1334_iters << ") ";
std::cout << "[" << x1334_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x1334 - " << x1334_avg << " (" << x1334_cycles << " / " << x1334_iters << ") ";
instrumentation << "[" << x1334_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x1251, 5 -> x1334, 4 -> x1335, 1 -> x810, 3 -> x1250, 6 -> x1085), current node x1085 is at depth 6
long x1085_cycles = c1->getArg(X1085_cycles_arg, false);
long x1085_iters = c1->getArg(X1085_iters_arg, false);
long x1085_iters_per_parent = x1085_iters / std::max((long)1,x1334_iters);
long x1085_avg = x1085_cycles / std::max((long)1,x1085_iters);
std::cout << "            x1085 - " << x1085_avg << " (" << x1085_cycles << " / " << x1085_iters << ") ";
std::cout << "[" << x1085_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "            x1085 - " << x1085_avg << " (" << x1085_cycles << " / " << x1085_iters << ") ";
instrumentation << "[" << x1085_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x1251, 5 -> x1334, 4 -> x1335, 7 -> x1048, 1 -> x810, 3 -> x1250, 6 -> x1085), current node x1048 is at depth 7
long x1048_cycles = c1->getArg(X1048_cycles_arg, false);
long x1048_iters = c1->getArg(X1048_iters_arg, false);
long x1048_iters_per_parent = x1048_iters / std::max((long)1,x1085_iters);
long x1048_avg = x1048_cycles / std::max((long)1,x1048_iters);
std::cout << "              x1048 - " << x1048_avg << " (" << x1048_cycles << " / " << x1048_iters << ") ";
std::cout << "[" << x1048_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "              x1048 - " << x1048_avg << " (" << x1048_cycles << " / " << x1048_iters << ") ";
instrumentation << "[" << x1048_iters_per_parent << " iters/parent execution]";
}
long x1048_stalled = c1->getArg(X1048_stalled_arg, false);
long x1048_idle = c1->getArg(X1048_idle_arg, false);
std::cout << " <# stalled: " << x1048_stalled << ", #idle: " << x1048_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x1048_stalled << ", # idle: " << x1048_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x1251, 5 -> x1334, 4 -> x1335, 7 -> x1084, 1 -> x810, 3 -> x1250, 6 -> x1085), current node x1084 is at depth 7
long x1084_cycles = c1->getArg(X1084_cycles_arg, false);
long x1084_iters = c1->getArg(X1084_iters_arg, false);
long x1084_iters_per_parent = x1084_iters / std::max((long)1,x1085_iters);
long x1084_avg = x1084_cycles / std::max((long)1,x1084_iters);
std::cout << "              x1084 - " << x1084_avg << " (" << x1084_cycles << " / " << x1084_iters << ") ";
std::cout << "[" << x1084_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "              x1084 - " << x1084_avg << " (" << x1084_cycles << " / " << x1084_iters << ") ";
instrumentation << "[" << x1084_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1065, 2 -> x1251, 5 -> x1334, 4 -> x1335, 7 -> x1084, 1 -> x810, 3 -> x1250, 6 -> x1085), current node x1065 is at depth 8
long x1065_cycles = c1->getArg(X1065_cycles_arg, false);
long x1065_iters = c1->getArg(X1065_iters_arg, false);
long x1065_iters_per_parent = x1065_iters / std::max((long)1,x1084_iters);
long x1065_avg = x1065_cycles / std::max((long)1,x1065_iters);
std::cout << "                x1065 - " << x1065_avg << " (" << x1065_cycles << " / " << x1065_iters << ") ";
std::cout << "[" << x1065_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "                x1065 - " << x1065_avg << " (" << x1065_cycles << " / " << x1065_iters << ") ";
instrumentation << "[" << x1065_iters_per_parent << " iters/parent execution]";
}
long x1065_stalled = c1->getArg(X1065_stalled_arg, false);
long x1065_idle = c1->getArg(X1065_idle_arg, false);
std::cout << " <# stalled: " << x1065_stalled << ", #idle: " << x1065_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x1065_stalled << ", # idle: " << x1065_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1083, 2 -> x1251, 5 -> x1334, 4 -> x1335, 7 -> x1084, 1 -> x810, 3 -> x1250, 6 -> x1085), current node x1083 is at depth 8
long x1083_cycles = c1->getArg(X1083_cycles_arg, false);
long x1083_iters = c1->getArg(X1083_iters_arg, false);
long x1083_iters_per_parent = x1083_iters / std::max((long)1,x1084_iters);
long x1083_avg = x1083_cycles / std::max((long)1,x1083_iters);
std::cout << "                x1083 - " << x1083_avg << " (" << x1083_cycles << " / " << x1083_iters << ") ";
std::cout << "[" << x1083_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "                x1083 - " << x1083_avg << " (" << x1083_cycles << " / " << x1083_iters << ") ";
instrumentation << "[" << x1083_iters_per_parent << " iters/parent execution]";
}
long x1083_stalled = c1->getArg(X1083_stalled_arg, false);
long x1083_idle = c1->getArg(X1083_idle_arg, false);
std::cout << " <# stalled: " << x1083_stalled << ", #idle: " << x1083_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x1083_stalled << ", # idle: " << x1083_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1083, 2 -> x1251, 5 -> x1334, 4 -> x1335, 7 -> x1084, 1 -> x810, 3 -> x1250, 6 -> x1153), current node x1153 is at depth 6
long x1153_cycles = c1->getArg(X1153_cycles_arg, false);
long x1153_iters = c1->getArg(X1153_iters_arg, false);
long x1153_iters_per_parent = x1153_iters / std::max((long)1,x1334_iters);
long x1153_avg = x1153_cycles / std::max((long)1,x1153_iters);
std::cout << "            x1153 - " << x1153_avg << " (" << x1153_cycles << " / " << x1153_iters << ") ";
std::cout << "[" << x1153_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "            x1153 - " << x1153_avg << " (" << x1153_cycles << " / " << x1153_iters << ") ";
instrumentation << "[" << x1153_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1083, 2 -> x1251, 5 -> x1334, 4 -> x1335, 7 -> x1115, 1 -> x810, 3 -> x1250, 6 -> x1153), current node x1115 is at depth 7
long x1115_cycles = c1->getArg(X1115_cycles_arg, false);
long x1115_iters = c1->getArg(X1115_iters_arg, false);
long x1115_iters_per_parent = x1115_iters / std::max((long)1,x1153_iters);
long x1115_avg = x1115_cycles / std::max((long)1,x1115_iters);
std::cout << "              x1115 - " << x1115_avg << " (" << x1115_cycles << " / " << x1115_iters << ") ";
std::cout << "[" << x1115_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "              x1115 - " << x1115_avg << " (" << x1115_cycles << " / " << x1115_iters << ") ";
instrumentation << "[" << x1115_iters_per_parent << " iters/parent execution]";
}
long x1115_stalled = c1->getArg(X1115_stalled_arg, false);
long x1115_idle = c1->getArg(X1115_idle_arg, false);
std::cout << " <# stalled: " << x1115_stalled << ", #idle: " << x1115_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x1115_stalled << ", # idle: " << x1115_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1083, 2 -> x1251, 5 -> x1334, 4 -> x1335, 7 -> x1152, 1 -> x810, 3 -> x1250, 6 -> x1153), current node x1152 is at depth 7
long x1152_cycles = c1->getArg(X1152_cycles_arg, false);
long x1152_iters = c1->getArg(X1152_iters_arg, false);
long x1152_iters_per_parent = x1152_iters / std::max((long)1,x1153_iters);
long x1152_avg = x1152_cycles / std::max((long)1,x1152_iters);
std::cout << "              x1152 - " << x1152_avg << " (" << x1152_cycles << " / " << x1152_iters << ") ";
std::cout << "[" << x1152_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "              x1152 - " << x1152_avg << " (" << x1152_cycles << " / " << x1152_iters << ") ";
instrumentation << "[" << x1152_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1132, 2 -> x1251, 5 -> x1334, 4 -> x1335, 7 -> x1152, 1 -> x810, 3 -> x1250, 6 -> x1153), current node x1132 is at depth 8
long x1132_cycles = c1->getArg(X1132_cycles_arg, false);
long x1132_iters = c1->getArg(X1132_iters_arg, false);
long x1132_iters_per_parent = x1132_iters / std::max((long)1,x1152_iters);
long x1132_avg = x1132_cycles / std::max((long)1,x1132_iters);
std::cout << "                x1132 - " << x1132_avg << " (" << x1132_cycles << " / " << x1132_iters << ") ";
std::cout << "[" << x1132_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "                x1132 - " << x1132_avg << " (" << x1132_cycles << " / " << x1132_iters << ") ";
instrumentation << "[" << x1132_iters_per_parent << " iters/parent execution]";
}
long x1132_stalled = c1->getArg(X1132_stalled_arg, false);
long x1132_idle = c1->getArg(X1132_idle_arg, false);
std::cout << " <# stalled: " << x1132_stalled << ", #idle: " << x1132_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x1132_stalled << ", # idle: " << x1132_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1151, 2 -> x1251, 5 -> x1334, 4 -> x1335, 7 -> x1152, 1 -> x810, 3 -> x1250, 6 -> x1153), current node x1151 is at depth 8
long x1151_cycles = c1->getArg(X1151_cycles_arg, false);
long x1151_iters = c1->getArg(X1151_iters_arg, false);
long x1151_iters_per_parent = x1151_iters / std::max((long)1,x1152_iters);
long x1151_avg = x1151_cycles / std::max((long)1,x1151_iters);
std::cout << "                x1151 - " << x1151_avg << " (" << x1151_cycles << " / " << x1151_iters << ") ";
std::cout << "[" << x1151_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "                x1151 - " << x1151_avg << " (" << x1151_cycles << " / " << x1151_iters << ") ";
instrumentation << "[" << x1151_iters_per_parent << " iters/parent execution]";
}
long x1151_stalled = c1->getArg(X1151_stalled_arg, false);
long x1151_idle = c1->getArg(X1151_idle_arg, false);
std::cout << " <# stalled: " << x1151_stalled << ", #idle: " << x1151_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x1151_stalled << ", # idle: " << x1151_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1151, 2 -> x1251, 5 -> x1188, 4 -> x1335, 7 -> x1152, 1 -> x810, 3 -> x1250, 6 -> x1153), current node x1188 is at depth 5
long x1188_cycles = c1->getArg(X1188_cycles_arg, false);
long x1188_iters = c1->getArg(X1188_iters_arg, false);
long x1188_iters_per_parent = x1188_iters / std::max((long)1,x1335_iters);
long x1188_avg = x1188_cycles / std::max((long)1,x1188_iters);
std::cout << "          x1188 - " << x1188_avg << " (" << x1188_cycles << " / " << x1188_iters << ") ";
std::cout << "[" << x1188_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x1188 - " << x1188_avg << " (" << x1188_cycles << " / " << x1188_iters << ") ";
instrumentation << "[" << x1188_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1151, 2 -> x1251, 5 -> x1188, 4 -> x1335, 7 -> x1152, 1 -> x810, 3 -> x1250, 6 -> x1187), current node x1187 is at depth 6
long x1187_cycles = c1->getArg(X1187_cycles_arg, false);
long x1187_iters = c1->getArg(X1187_iters_arg, false);
long x1187_iters_per_parent = x1187_iters / std::max((long)1,x1188_iters);
long x1187_avg = x1187_cycles / std::max((long)1,x1187_iters);
std::cout << "            x1187 - " << x1187_avg << " (" << x1187_cycles << " / " << x1187_iters << ") ";
std::cout << "[" << x1187_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "            x1187 - " << x1187_avg << " (" << x1187_cycles << " / " << x1187_iters << ") ";
instrumentation << "[" << x1187_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1151, 2 -> x1251, 5 -> x1188, 4 -> x1335, 7 -> x1186, 1 -> x810, 3 -> x1250, 6 -> x1187), current node x1186 is at depth 7
long x1186_cycles = c1->getArg(X1186_cycles_arg, false);
long x1186_iters = c1->getArg(X1186_iters_arg, false);
long x1186_iters_per_parent = x1186_iters / std::max((long)1,x1187_iters);
long x1186_avg = x1186_cycles / std::max((long)1,x1186_iters);
std::cout << "              x1186 - " << x1186_avg << " (" << x1186_cycles << " / " << x1186_iters << ") ";
std::cout << "[" << x1186_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "              x1186 - " << x1186_avg << " (" << x1186_cycles << " / " << x1186_iters << ") ";
instrumentation << "[" << x1186_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1151, 2 -> x1251, 5 -> x1248, 4 -> x1335, 7 -> x1186, 1 -> x810, 3 -> x1250, 6 -> x1187), current node x1248 is at depth 5
long x1248_cycles = c1->getArg(X1248_cycles_arg, false);
long x1248_iters = c1->getArg(X1248_iters_arg, false);
long x1248_iters_per_parent = x1248_iters / std::max((long)1,x1335_iters);
long x1248_avg = x1248_cycles / std::max((long)1,x1248_iters);
std::cout << "          x1248 - " << x1248_avg << " (" << x1248_cycles << " / " << x1248_iters << ") ";
std::cout << "[" << x1248_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x1248 - " << x1248_avg << " (" << x1248_cycles << " / " << x1248_iters << ") ";
instrumentation << "[" << x1248_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1151, 2 -> x1251, 5 -> x1248, 4 -> x1335, 7 -> x1186, 1 -> x810, 3 -> x1250, 6 -> x1247), current node x1247 is at depth 6
long x1247_cycles = c1->getArg(X1247_cycles_arg, false);
long x1247_iters = c1->getArg(X1247_iters_arg, false);
long x1247_iters_per_parent = x1247_iters / std::max((long)1,x1248_iters);
long x1247_avg = x1247_cycles / std::max((long)1,x1247_iters);
std::cout << "            x1247 - " << x1247_avg << " (" << x1247_cycles << " / " << x1247_iters << ") ";
std::cout << "[" << x1247_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "            x1247 - " << x1247_avg << " (" << x1247_cycles << " / " << x1247_iters << ") ";
instrumentation << "[" << x1247_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1151, 2 -> x1251, 5 -> x1248, 4 -> x1335, 7 -> x1242, 1 -> x810, 3 -> x1250, 6 -> x1247), current node x1242 is at depth 7
long x1242_cycles = c1->getArg(X1242_cycles_arg, false);
long x1242_iters = c1->getArg(X1242_iters_arg, false);
long x1242_iters_per_parent = x1242_iters / std::max((long)1,x1247_iters);
long x1242_avg = x1242_cycles / std::max((long)1,x1242_iters);
std::cout << "              x1242 - " << x1242_avg << " (" << x1242_cycles << " / " << x1242_iters << ") ";
std::cout << "[" << x1242_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "              x1242 - " << x1242_avg << " (" << x1242_cycles << " / " << x1242_iters << ") ";
instrumentation << "[" << x1242_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1222, 2 -> x1251, 5 -> x1248, 4 -> x1335, 7 -> x1242, 1 -> x810, 3 -> x1250, 6 -> x1247), current node x1222 is at depth 8
long x1222_cycles = c1->getArg(X1222_cycles_arg, false);
long x1222_iters = c1->getArg(X1222_iters_arg, false);
long x1222_iters_per_parent = x1222_iters / std::max((long)1,x1242_iters);
long x1222_avg = x1222_cycles / std::max((long)1,x1222_iters);
std::cout << "                x1222 - " << x1222_avg << " (" << x1222_cycles << " / " << x1222_iters << ") ";
std::cout << "[" << x1222_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "                x1222 - " << x1222_avg << " (" << x1222_cycles << " / " << x1222_iters << ") ";
instrumentation << "[" << x1222_iters_per_parent << " iters/parent execution]";
}
long x1222_stalled = c1->getArg(X1222_stalled_arg, false);
long x1222_idle = c1->getArg(X1222_idle_arg, false);
std::cout << " <# stalled: " << x1222_stalled << ", #idle: " << x1222_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x1222_stalled << ", # idle: " << x1222_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1241, 2 -> x1251, 5 -> x1248, 4 -> x1335, 7 -> x1242, 1 -> x810, 3 -> x1250, 6 -> x1247), current node x1241 is at depth 8
long x1241_cycles = c1->getArg(X1241_cycles_arg, false);
long x1241_iters = c1->getArg(X1241_iters_arg, false);
long x1241_iters_per_parent = x1241_iters / std::max((long)1,x1242_iters);
long x1241_avg = x1241_cycles / std::max((long)1,x1241_iters);
std::cout << "                x1241 - " << x1241_avg << " (" << x1241_cycles << " / " << x1241_iters << ") ";
std::cout << "[" << x1241_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "                x1241 - " << x1241_avg << " (" << x1241_cycles << " / " << x1241_iters << ") ";
instrumentation << "[" << x1241_iters_per_parent << " iters/parent execution]";
}
long x1241_stalled = c1->getArg(X1241_stalled_arg, false);
long x1241_idle = c1->getArg(X1241_idle_arg, false);
std::cout << " <# stalled: " << x1241_stalled << ", #idle: " << x1241_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x1241_stalled << ", # idle: " << x1241_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1241, 2 -> x1251, 5 -> x1248, 4 -> x1335, 7 -> x1246, 1 -> x810, 3 -> x1250, 6 -> x1247), current node x1246 is at depth 7
long x1246_cycles = c1->getArg(X1246_cycles_arg, false);
long x1246_iters = c1->getArg(X1246_iters_arg, false);
long x1246_iters_per_parent = x1246_iters / std::max((long)1,x1247_iters);
long x1246_avg = x1246_cycles / std::max((long)1,x1246_iters);
std::cout << "              x1246 - " << x1246_avg << " (" << x1246_cycles << " / " << x1246_iters << ") ";
std::cout << "[" << x1246_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "              x1246 - " << x1246_avg << " (" << x1246_cycles << " / " << x1246_iters << ") ";
instrumentation << "[" << x1246_iters_per_parent << " iters/parent execution]";
}
long x1246_stalled = c1->getArg(X1246_stalled_arg, false);
long x1246_idle = c1->getArg(X1246_idle_arg, false);
std::cout << " <# stalled: " << x1246_stalled << ", #idle: " << x1246_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x1246_stalled << ", # idle: " << x1246_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
instrumentation.close();
int32_t x1252 = x904 * x905;
vector<double>* x1253 = new vector<double>(x1252);
vector<int32_t>* x1254_rawified = new vector<int32_t>((*x1253).size());
c1->memcpy(&(*x1254_rawified)[0], x906, (*x1254_rawified).size() * sizeof(int32_t));
for (int x1253_i = 0; x1253_i < (*x1253).size(); x1253_i++) {
int32_t x1253_tmp = (*x1254_rawified)[x1253_i];
(*x1253)[x1253_i] = (double) x1253_tmp / ((int32_t)1 << 8);
}
vector<double>* x1270 = new vector<double>(x896);
for (int b111 = 0; b111 < x896; b111++) {
int32_t x1255 = b111 / x885;
int32_t x1256 = (int32_t) ((b111 % x885 + x885) % x885);
string x1257;
try {
x1257 = (*x877).at(2);
}
catch (std::out_of_range& e) {
printHelp();
}
int32_t x1258 = std::stol(x1257);
vector<double>* x1266 = new vector<double>(x1258);
for (int b116 = 0; b116 < x1258; b116++) {
int32_t x1259 = x1255 * x888;
int32_t x1260 = x1259 + b116;
double x1261 = (*x892)[x1260];
int32_t x1262 = b116 * x885;
int32_t x1263 = x1262 + x1256;
double x1264 = (*x895)[x1263];
double x1265 = x1261 * x1264;
(*x1266)[b116] = x1265;
}
double x1269;
if ((*x1266).size() > 0) { // Hack to handle reductions on things of length 0
x1269 = (*x1266)[0];
}
else {
x1269 = 0;
}
for (int b125 = 1; b125 < (*x1266).size(); b125++) {
double b126 = (*x1266)[b125];
double b127 = x1269;
double x1268 = b126 + b127;
x1269 = x1268;
}
(*x1270)[b111] = x1269;
}
string x1271 = (string("Received: ") + string("\n"));
std::cout << x1271;
for (int b135 = 0; b135 < x904; b135 = b135 + 1) {
for (int b136 = 0; b136 < x905; b136 = b136 + 1) {
int32_t x1273 = b135 * x905;
int32_t x1274 = x1273 + b136;
double x1275 = (*x1253)[x1274];
string x1276 = std::to_string(x1275);
string x1277 = (x1276 + string("\t"));
std::cout << x1277;
}
std::cout << string("\n");
}
string x1282 = (string("Wanted: ") + string("\n"));
std::cout << x1282;
for (int b148 = 0; b148 < x882; b148 = b148 + 1) {
for (int b149 = 0; b149 < x885; b149 = b149 + 1) {
int32_t x1284 = b148 * x885;
int32_t x1285 = x1284 + b149;
double x1286 = (*x1270)[x1285];
string x1287 = std::to_string(x1286);
string x1288 = (x1287 + string("\t"));
std::cout << x1288;
}
std::cout << string("\n");
}
vector<bool>* x1296 = new vector<bool>((*x1253).size());
for (int b159 = 0; b159 < (*x1253).size(); b159++) { 
double x1293 = (*x1253)[b159];
double x1294 = (*x1270)[b159];
bool x1295 = x1293 == x1294;
(*x1296)[b159] = x1295;
}
bool x1299;
if ((*x1296).size() > 0) { // Hack to handle reductions on things of length 0
x1299 = (*x1296)[0];
}
else {
x1299 = 0;
}
for (int b165 = 1; b165 < (*x1296).size(); b165++) {
bool b166 = (*x1296)[b165];
bool b167 = x1299;
bool x1298 = b166 & b167;
x1299 = x1298;
}
string x1300 = x1299 ? string("true") : string("false");
string x1301 = (string("Pass? ") + x1300);
string x1302 = (x1301 + string("\n"));
std::cout << x1302;
string x1304 = ("\n=================\n" + (string("Lab2GEMM.scala:75:12: Assertion failure") + "\n=================\n"));
if (true) { std::cout << std::flush; ASSERT(x1299, "%s", x1304.c_str()); }
delete c1;
}

void printHelp() {
fprintf(stderr, "Help for app: Lab2Part5GEMM\n");
fprintf(stderr, "  -- Args:    <0: gold_matrix> <1: i> <2: b_data / gold_matrix>\n");
fprintf(stderr, "    -- Example: bash run.sh 32 32 32\n");
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
