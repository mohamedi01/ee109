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
c1->setNumArgOutInstrs(134);
c1->setNumEarlyExits(0);
c1->flushCache(1024);
time_t tstart = time(0);
c1->run();
time_t tend = time(0);
double elapsed = difftime(tend, tstart);
std::cout << "Kernel done, test run time = " << elapsed << " ms" << std::endl;
c1->flushCache(1024);
std::ofstream instrumentation ("./instrumentation.txt");
// Need to instrument List((x810,1), (x1704,2), (x921,3), (x1703,3), (x932,4), (x1002,4), (x964,5), (x1001,5), (x981,6), (x1000,6), (x1812,4), (x1016,5), (x1811,5), (x1087,6), (x1049,7), (x1086,7), (x1066,8), (x1085,8), (x1158,6), (x1117,7), (x1157,7), (x1134,8), (x1156,8), (x1638,5), (x1637,6), (x1294,7), (x1293,8), (x1223,9), (x1246,9), (x1269,9), (x1292,9), (x1408,7), (x1407,8), (x1337,9), (x1360,9), (x1383,9), (x1406,9), (x1522,7), (x1521,8), (x1451,9), (x1474,9), (x1497,9), (x1520,9), (x1636,7), (x1635,8), (x1565,9), (x1588,9), (x1611,9), (x1634,9), (x1701,5), (x1700,6), (x1695,7), (x1672,8), (x1694,8), (x1699,7))
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
 // immediate parent hashmap now Map(2 -> x1704, 1 -> x810), current node x1704 is at depth 2
long x1704_cycles = c1->getArg(X1704_cycles_arg, false);
long x1704_iters = c1->getArg(X1704_iters_arg, false);
long x1704_iters_per_parent = x1704_iters / std::max((long)1,x810_iters);
long x1704_avg = x1704_cycles / std::max((long)1,x1704_iters);
std::cout << "    x1704 - " << x1704_avg << " (" << x1704_cycles << " / " << x1704_iters << ") ";
std::cout << "[" << x1704_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "    x1704 - " << x1704_avg << " (" << x1704_cycles << " / " << x1704_iters << ") ";
instrumentation << "[" << x1704_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x1704, 1 -> x810, 3 -> x921), current node x921 is at depth 3
long x921_cycles = c1->getArg(X921_cycles_arg, false);
long x921_iters = c1->getArg(X921_iters_arg, false);
long x921_iters_per_parent = x921_iters / std::max((long)1,x1704_iters);
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
 // immediate parent hashmap now Map(2 -> x1704, 1 -> x810, 3 -> x1703), current node x1703 is at depth 3
long x1703_cycles = c1->getArg(X1703_cycles_arg, false);
long x1703_iters = c1->getArg(X1703_iters_arg, false);
long x1703_iters_per_parent = x1703_iters / std::max((long)1,x1704_iters);
long x1703_avg = x1703_cycles / std::max((long)1,x1703_iters);
std::cout << "      x1703 - " << x1703_avg << " (" << x1703_cycles << " / " << x1703_iters << ") ";
std::cout << "[" << x1703_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "      x1703 - " << x1703_avg << " (" << x1703_cycles << " / " << x1703_iters << ") ";
instrumentation << "[" << x1703_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x1704, 4 -> x932, 1 -> x810, 3 -> x1703), current node x932 is at depth 4
long x932_cycles = c1->getArg(X932_cycles_arg, false);
long x932_iters = c1->getArg(X932_iters_arg, false);
long x932_iters_per_parent = x932_iters / std::max((long)1,x1703_iters);
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
 // immediate parent hashmap now Map(2 -> x1704, 4 -> x1002, 1 -> x810, 3 -> x1703), current node x1002 is at depth 4
long x1002_cycles = c1->getArg(X1002_cycles_arg, false);
long x1002_iters = c1->getArg(X1002_iters_arg, false);
long x1002_iters_per_parent = x1002_iters / std::max((long)1,x1703_iters);
long x1002_avg = x1002_cycles / std::max((long)1,x1002_iters);
std::cout << "        x1002 - " << x1002_avg << " (" << x1002_cycles << " / " << x1002_iters << ") ";
std::cout << "[" << x1002_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "        x1002 - " << x1002_avg << " (" << x1002_cycles << " / " << x1002_iters << ") ";
instrumentation << "[" << x1002_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x1704, 5 -> x964, 4 -> x1002, 1 -> x810, 3 -> x1703), current node x964 is at depth 5
long x964_cycles = c1->getArg(X964_cycles_arg, false);
long x964_iters = c1->getArg(X964_iters_arg, false);
long x964_iters_per_parent = x964_iters / std::max((long)1,x1002_iters);
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
 // immediate parent hashmap now Map(2 -> x1704, 5 -> x1001, 4 -> x1002, 1 -> x810, 3 -> x1703), current node x1001 is at depth 5
long x1001_cycles = c1->getArg(X1001_cycles_arg, false);
long x1001_iters = c1->getArg(X1001_iters_arg, false);
long x1001_iters_per_parent = x1001_iters / std::max((long)1,x1002_iters);
long x1001_avg = x1001_cycles / std::max((long)1,x1001_iters);
std::cout << "          x1001 - " << x1001_avg << " (" << x1001_cycles << " / " << x1001_iters << ") ";
std::cout << "[" << x1001_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x1001 - " << x1001_avg << " (" << x1001_cycles << " / " << x1001_iters << ") ";
instrumentation << "[" << x1001_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x1704, 5 -> x1001, 4 -> x1002, 1 -> x810, 3 -> x1703, 6 -> x981), current node x981 is at depth 6
long x981_cycles = c1->getArg(X981_cycles_arg, false);
long x981_iters = c1->getArg(X981_iters_arg, false);
long x981_iters_per_parent = x981_iters / std::max((long)1,x1001_iters);
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
 // immediate parent hashmap now Map(2 -> x1704, 5 -> x1001, 4 -> x1002, 1 -> x810, 3 -> x1703, 6 -> x1000), current node x1000 is at depth 6
long x1000_cycles = c1->getArg(X1000_cycles_arg, false);
long x1000_iters = c1->getArg(X1000_iters_arg, false);
long x1000_iters_per_parent = x1000_iters / std::max((long)1,x1001_iters);
long x1000_avg = x1000_cycles / std::max((long)1,x1000_iters);
std::cout << "            x1000 - " << x1000_avg << " (" << x1000_cycles << " / " << x1000_iters << ") ";
std::cout << "[" << x1000_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "            x1000 - " << x1000_avg << " (" << x1000_cycles << " / " << x1000_iters << ") ";
instrumentation << "[" << x1000_iters_per_parent << " iters/parent execution]";
}
long x1000_stalled = c1->getArg(X1000_stalled_arg, false);
long x1000_idle = c1->getArg(X1000_idle_arg, false);
std::cout << " <# stalled: " << x1000_stalled << ", #idle: " << x1000_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x1000_stalled << ", # idle: " << x1000_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x1704, 5 -> x1001, 4 -> x1812, 1 -> x810, 3 -> x1703, 6 -> x1000), current node x1812 is at depth 4
long x1812_cycles = c1->getArg(X1812_cycles_arg, false);
long x1812_iters = c1->getArg(X1812_iters_arg, false);
long x1812_iters_per_parent = x1812_iters / std::max((long)1,x1703_iters);
long x1812_avg = x1812_cycles / std::max((long)1,x1812_iters);
std::cout << "        x1812 - " << x1812_avg << " (" << x1812_cycles << " / " << x1812_iters << ") ";
std::cout << "[" << x1812_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "        x1812 - " << x1812_avg << " (" << x1812_cycles << " / " << x1812_iters << ") ";
instrumentation << "[" << x1812_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x1704, 5 -> x1016, 4 -> x1812, 1 -> x810, 3 -> x1703, 6 -> x1000), current node x1016 is at depth 5
long x1016_cycles = c1->getArg(X1016_cycles_arg, false);
long x1016_iters = c1->getArg(X1016_iters_arg, false);
long x1016_iters_per_parent = x1016_iters / std::max((long)1,x1812_iters);
long x1016_avg = x1016_cycles / std::max((long)1,x1016_iters);
std::cout << "          x1016 - " << x1016_avg << " (" << x1016_cycles << " / " << x1016_iters << ") ";
std::cout << "[" << x1016_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x1016 - " << x1016_avg << " (" << x1016_cycles << " / " << x1016_iters << ") ";
instrumentation << "[" << x1016_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x1704, 5 -> x1811, 4 -> x1812, 1 -> x810, 3 -> x1703, 6 -> x1000), current node x1811 is at depth 5
long x1811_cycles = c1->getArg(X1811_cycles_arg, false);
long x1811_iters = c1->getArg(X1811_iters_arg, false);
long x1811_iters_per_parent = x1811_iters / std::max((long)1,x1812_iters);
long x1811_avg = x1811_cycles / std::max((long)1,x1811_iters);
std::cout << "          x1811 - " << x1811_avg << " (" << x1811_cycles << " / " << x1811_iters << ") ";
std::cout << "[" << x1811_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x1811 - " << x1811_avg << " (" << x1811_cycles << " / " << x1811_iters << ") ";
instrumentation << "[" << x1811_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x1704, 5 -> x1811, 4 -> x1812, 1 -> x810, 3 -> x1703, 6 -> x1087), current node x1087 is at depth 6
long x1087_cycles = c1->getArg(X1087_cycles_arg, false);
long x1087_iters = c1->getArg(X1087_iters_arg, false);
long x1087_iters_per_parent = x1087_iters / std::max((long)1,x1811_iters);
long x1087_avg = x1087_cycles / std::max((long)1,x1087_iters);
std::cout << "            x1087 - " << x1087_avg << " (" << x1087_cycles << " / " << x1087_iters << ") ";
std::cout << "[" << x1087_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "            x1087 - " << x1087_avg << " (" << x1087_cycles << " / " << x1087_iters << ") ";
instrumentation << "[" << x1087_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x1704, 5 -> x1811, 4 -> x1812, 7 -> x1049, 1 -> x810, 3 -> x1703, 6 -> x1087), current node x1049 is at depth 7
long x1049_cycles = c1->getArg(X1049_cycles_arg, false);
long x1049_iters = c1->getArg(X1049_iters_arg, false);
long x1049_iters_per_parent = x1049_iters / std::max((long)1,x1087_iters);
long x1049_avg = x1049_cycles / std::max((long)1,x1049_iters);
std::cout << "              x1049 - " << x1049_avg << " (" << x1049_cycles << " / " << x1049_iters << ") ";
std::cout << "[" << x1049_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "              x1049 - " << x1049_avg << " (" << x1049_cycles << " / " << x1049_iters << ") ";
instrumentation << "[" << x1049_iters_per_parent << " iters/parent execution]";
}
long x1049_stalled = c1->getArg(X1049_stalled_arg, false);
long x1049_idle = c1->getArg(X1049_idle_arg, false);
std::cout << " <# stalled: " << x1049_stalled << ", #idle: " << x1049_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x1049_stalled << ", # idle: " << x1049_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(2 -> x1704, 5 -> x1811, 4 -> x1812, 7 -> x1086, 1 -> x810, 3 -> x1703, 6 -> x1087), current node x1086 is at depth 7
long x1086_cycles = c1->getArg(X1086_cycles_arg, false);
long x1086_iters = c1->getArg(X1086_iters_arg, false);
long x1086_iters_per_parent = x1086_iters / std::max((long)1,x1087_iters);
long x1086_avg = x1086_cycles / std::max((long)1,x1086_iters);
std::cout << "              x1086 - " << x1086_avg << " (" << x1086_cycles << " / " << x1086_iters << ") ";
std::cout << "[" << x1086_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "              x1086 - " << x1086_avg << " (" << x1086_cycles << " / " << x1086_iters << ") ";
instrumentation << "[" << x1086_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1066, 2 -> x1704, 5 -> x1811, 4 -> x1812, 7 -> x1086, 1 -> x810, 3 -> x1703, 6 -> x1087), current node x1066 is at depth 8
long x1066_cycles = c1->getArg(X1066_cycles_arg, false);
long x1066_iters = c1->getArg(X1066_iters_arg, false);
long x1066_iters_per_parent = x1066_iters / std::max((long)1,x1086_iters);
long x1066_avg = x1066_cycles / std::max((long)1,x1066_iters);
std::cout << "                x1066 - " << x1066_avg << " (" << x1066_cycles << " / " << x1066_iters << ") ";
std::cout << "[" << x1066_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "                x1066 - " << x1066_avg << " (" << x1066_cycles << " / " << x1066_iters << ") ";
instrumentation << "[" << x1066_iters_per_parent << " iters/parent execution]";
}
long x1066_stalled = c1->getArg(X1066_stalled_arg, false);
long x1066_idle = c1->getArg(X1066_idle_arg, false);
std::cout << " <# stalled: " << x1066_stalled << ", #idle: " << x1066_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x1066_stalled << ", # idle: " << x1066_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1085, 2 -> x1704, 5 -> x1811, 4 -> x1812, 7 -> x1086, 1 -> x810, 3 -> x1703, 6 -> x1087), current node x1085 is at depth 8
long x1085_cycles = c1->getArg(X1085_cycles_arg, false);
long x1085_iters = c1->getArg(X1085_iters_arg, false);
long x1085_iters_per_parent = x1085_iters / std::max((long)1,x1086_iters);
long x1085_avg = x1085_cycles / std::max((long)1,x1085_iters);
std::cout << "                x1085 - " << x1085_avg << " (" << x1085_cycles << " / " << x1085_iters << ") ";
std::cout << "[" << x1085_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "                x1085 - " << x1085_avg << " (" << x1085_cycles << " / " << x1085_iters << ") ";
instrumentation << "[" << x1085_iters_per_parent << " iters/parent execution]";
}
long x1085_stalled = c1->getArg(X1085_stalled_arg, false);
long x1085_idle = c1->getArg(X1085_idle_arg, false);
std::cout << " <# stalled: " << x1085_stalled << ", #idle: " << x1085_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x1085_stalled << ", # idle: " << x1085_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1085, 2 -> x1704, 5 -> x1811, 4 -> x1812, 7 -> x1086, 1 -> x810, 3 -> x1703, 6 -> x1158), current node x1158 is at depth 6
long x1158_cycles = c1->getArg(X1158_cycles_arg, false);
long x1158_iters = c1->getArg(X1158_iters_arg, false);
long x1158_iters_per_parent = x1158_iters / std::max((long)1,x1811_iters);
long x1158_avg = x1158_cycles / std::max((long)1,x1158_iters);
std::cout << "            x1158 - " << x1158_avg << " (" << x1158_cycles << " / " << x1158_iters << ") ";
std::cout << "[" << x1158_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "            x1158 - " << x1158_avg << " (" << x1158_cycles << " / " << x1158_iters << ") ";
instrumentation << "[" << x1158_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1085, 2 -> x1704, 5 -> x1811, 4 -> x1812, 7 -> x1117, 1 -> x810, 3 -> x1703, 6 -> x1158), current node x1117 is at depth 7
long x1117_cycles = c1->getArg(X1117_cycles_arg, false);
long x1117_iters = c1->getArg(X1117_iters_arg, false);
long x1117_iters_per_parent = x1117_iters / std::max((long)1,x1158_iters);
long x1117_avg = x1117_cycles / std::max((long)1,x1117_iters);
std::cout << "              x1117 - " << x1117_avg << " (" << x1117_cycles << " / " << x1117_iters << ") ";
std::cout << "[" << x1117_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "              x1117 - " << x1117_avg << " (" << x1117_cycles << " / " << x1117_iters << ") ";
instrumentation << "[" << x1117_iters_per_parent << " iters/parent execution]";
}
long x1117_stalled = c1->getArg(X1117_stalled_arg, false);
long x1117_idle = c1->getArg(X1117_idle_arg, false);
std::cout << " <# stalled: " << x1117_stalled << ", #idle: " << x1117_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x1117_stalled << ", # idle: " << x1117_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1085, 2 -> x1704, 5 -> x1811, 4 -> x1812, 7 -> x1157, 1 -> x810, 3 -> x1703, 6 -> x1158), current node x1157 is at depth 7
long x1157_cycles = c1->getArg(X1157_cycles_arg, false);
long x1157_iters = c1->getArg(X1157_iters_arg, false);
long x1157_iters_per_parent = x1157_iters / std::max((long)1,x1158_iters);
long x1157_avg = x1157_cycles / std::max((long)1,x1157_iters);
std::cout << "              x1157 - " << x1157_avg << " (" << x1157_cycles << " / " << x1157_iters << ") ";
std::cout << "[" << x1157_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "              x1157 - " << x1157_avg << " (" << x1157_cycles << " / " << x1157_iters << ") ";
instrumentation << "[" << x1157_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1134, 2 -> x1704, 5 -> x1811, 4 -> x1812, 7 -> x1157, 1 -> x810, 3 -> x1703, 6 -> x1158), current node x1134 is at depth 8
long x1134_cycles = c1->getArg(X1134_cycles_arg, false);
long x1134_iters = c1->getArg(X1134_iters_arg, false);
long x1134_iters_per_parent = x1134_iters / std::max((long)1,x1157_iters);
long x1134_avg = x1134_cycles / std::max((long)1,x1134_iters);
std::cout << "                x1134 - " << x1134_avg << " (" << x1134_cycles << " / " << x1134_iters << ") ";
std::cout << "[" << x1134_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "                x1134 - " << x1134_avg << " (" << x1134_cycles << " / " << x1134_iters << ") ";
instrumentation << "[" << x1134_iters_per_parent << " iters/parent execution]";
}
long x1134_stalled = c1->getArg(X1134_stalled_arg, false);
long x1134_idle = c1->getArg(X1134_idle_arg, false);
std::cout << " <# stalled: " << x1134_stalled << ", #idle: " << x1134_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x1134_stalled << ", # idle: " << x1134_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1156, 2 -> x1704, 5 -> x1811, 4 -> x1812, 7 -> x1157, 1 -> x810, 3 -> x1703, 6 -> x1158), current node x1156 is at depth 8
long x1156_cycles = c1->getArg(X1156_cycles_arg, false);
long x1156_iters = c1->getArg(X1156_iters_arg, false);
long x1156_iters_per_parent = x1156_iters / std::max((long)1,x1157_iters);
long x1156_avg = x1156_cycles / std::max((long)1,x1156_iters);
std::cout << "                x1156 - " << x1156_avg << " (" << x1156_cycles << " / " << x1156_iters << ") ";
std::cout << "[" << x1156_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "                x1156 - " << x1156_avg << " (" << x1156_cycles << " / " << x1156_iters << ") ";
instrumentation << "[" << x1156_iters_per_parent << " iters/parent execution]";
}
long x1156_stalled = c1->getArg(X1156_stalled_arg, false);
long x1156_idle = c1->getArg(X1156_idle_arg, false);
std::cout << " <# stalled: " << x1156_stalled << ", #idle: " << x1156_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x1156_stalled << ", # idle: " << x1156_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1156, 2 -> x1704, 5 -> x1638, 4 -> x1812, 7 -> x1157, 1 -> x810, 3 -> x1703, 6 -> x1158), current node x1638 is at depth 5
long x1638_cycles = c1->getArg(X1638_cycles_arg, false);
long x1638_iters = c1->getArg(X1638_iters_arg, false);
long x1638_iters_per_parent = x1638_iters / std::max((long)1,x1812_iters);
long x1638_avg = x1638_cycles / std::max((long)1,x1638_iters);
std::cout << "          x1638 - " << x1638_avg << " (" << x1638_cycles << " / " << x1638_iters << ") ";
std::cout << "[" << x1638_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x1638 - " << x1638_avg << " (" << x1638_cycles << " / " << x1638_iters << ") ";
instrumentation << "[" << x1638_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1156, 2 -> x1704, 5 -> x1638, 4 -> x1812, 7 -> x1157, 1 -> x810, 3 -> x1703, 6 -> x1637), current node x1637 is at depth 6
long x1637_cycles = c1->getArg(X1637_cycles_arg, false);
long x1637_iters = c1->getArg(X1637_iters_arg, false);
long x1637_iters_per_parent = x1637_iters / std::max((long)1,x1638_iters);
long x1637_avg = x1637_cycles / std::max((long)1,x1637_iters);
std::cout << "            x1637 - " << x1637_avg << " (" << x1637_cycles << " / " << x1637_iters << ") ";
std::cout << "[" << x1637_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "            x1637 - " << x1637_avg << " (" << x1637_cycles << " / " << x1637_iters << ") ";
instrumentation << "[" << x1637_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1156, 2 -> x1704, 5 -> x1638, 4 -> x1812, 7 -> x1294, 1 -> x810, 3 -> x1703, 6 -> x1637), current node x1294 is at depth 7
long x1294_cycles = c1->getArg(X1294_cycles_arg, false);
long x1294_iters = c1->getArg(X1294_iters_arg, false);
long x1294_iters_per_parent = x1294_iters / std::max((long)1,x1637_iters);
long x1294_avg = x1294_cycles / std::max((long)1,x1294_iters);
std::cout << "              x1294 - " << x1294_avg << " (" << x1294_cycles << " / " << x1294_iters << ") ";
std::cout << "[" << x1294_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "              x1294 - " << x1294_avg << " (" << x1294_cycles << " / " << x1294_iters << ") ";
instrumentation << "[" << x1294_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1293, 2 -> x1704, 5 -> x1638, 4 -> x1812, 7 -> x1294, 1 -> x810, 3 -> x1703, 6 -> x1637), current node x1293 is at depth 8
long x1293_cycles = c1->getArg(X1293_cycles_arg, false);
long x1293_iters = c1->getArg(X1293_iters_arg, false);
long x1293_iters_per_parent = x1293_iters / std::max((long)1,x1294_iters);
long x1293_avg = x1293_cycles / std::max((long)1,x1293_iters);
std::cout << "                x1293 - " << x1293_avg << " (" << x1293_cycles << " / " << x1293_iters << ") ";
std::cout << "[" << x1293_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "                x1293 - " << x1293_avg << " (" << x1293_cycles << " / " << x1293_iters << ") ";
instrumentation << "[" << x1293_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1293, 2 -> x1704, 5 -> x1638, 4 -> x1812, 7 -> x1294, 1 -> x810, 9 -> x1223, 3 -> x1703, 6 -> x1637), current node x1223 is at depth 9
long x1223_cycles = c1->getArg(X1223_cycles_arg, false);
long x1223_iters = c1->getArg(X1223_iters_arg, false);
long x1223_iters_per_parent = x1223_iters / std::max((long)1,x1293_iters);
long x1223_avg = x1223_cycles / std::max((long)1,x1223_iters);
std::cout << "                  x1223 - " << x1223_avg << " (" << x1223_cycles << " / " << x1223_iters << ") ";
std::cout << "[" << x1223_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "                  x1223 - " << x1223_avg << " (" << x1223_cycles << " / " << x1223_iters << ") ";
instrumentation << "[" << x1223_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1293, 2 -> x1704, 5 -> x1638, 4 -> x1812, 7 -> x1294, 1 -> x810, 9 -> x1246, 3 -> x1703, 6 -> x1637), current node x1246 is at depth 9
long x1246_cycles = c1->getArg(X1246_cycles_arg, false);
long x1246_iters = c1->getArg(X1246_iters_arg, false);
long x1246_iters_per_parent = x1246_iters / std::max((long)1,x1293_iters);
long x1246_avg = x1246_cycles / std::max((long)1,x1246_iters);
std::cout << "                  x1246 - " << x1246_avg << " (" << x1246_cycles << " / " << x1246_iters << ") ";
std::cout << "[" << x1246_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "                  x1246 - " << x1246_avg << " (" << x1246_cycles << " / " << x1246_iters << ") ";
instrumentation << "[" << x1246_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1293, 2 -> x1704, 5 -> x1638, 4 -> x1812, 7 -> x1294, 1 -> x810, 9 -> x1269, 3 -> x1703, 6 -> x1637), current node x1269 is at depth 9
long x1269_cycles = c1->getArg(X1269_cycles_arg, false);
long x1269_iters = c1->getArg(X1269_iters_arg, false);
long x1269_iters_per_parent = x1269_iters / std::max((long)1,x1293_iters);
long x1269_avg = x1269_cycles / std::max((long)1,x1269_iters);
std::cout << "                  x1269 - " << x1269_avg << " (" << x1269_cycles << " / " << x1269_iters << ") ";
std::cout << "[" << x1269_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "                  x1269 - " << x1269_avg << " (" << x1269_cycles << " / " << x1269_iters << ") ";
instrumentation << "[" << x1269_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1293, 2 -> x1704, 5 -> x1638, 4 -> x1812, 7 -> x1294, 1 -> x810, 9 -> x1292, 3 -> x1703, 6 -> x1637), current node x1292 is at depth 9
long x1292_cycles = c1->getArg(X1292_cycles_arg, false);
long x1292_iters = c1->getArg(X1292_iters_arg, false);
long x1292_iters_per_parent = x1292_iters / std::max((long)1,x1293_iters);
long x1292_avg = x1292_cycles / std::max((long)1,x1292_iters);
std::cout << "                  x1292 - " << x1292_avg << " (" << x1292_cycles << " / " << x1292_iters << ") ";
std::cout << "[" << x1292_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "                  x1292 - " << x1292_avg << " (" << x1292_cycles << " / " << x1292_iters << ") ";
instrumentation << "[" << x1292_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1293, 2 -> x1704, 5 -> x1638, 4 -> x1812, 7 -> x1408, 1 -> x810, 9 -> x1292, 3 -> x1703, 6 -> x1637), current node x1408 is at depth 7
long x1408_cycles = c1->getArg(X1408_cycles_arg, false);
long x1408_iters = c1->getArg(X1408_iters_arg, false);
long x1408_iters_per_parent = x1408_iters / std::max((long)1,x1637_iters);
long x1408_avg = x1408_cycles / std::max((long)1,x1408_iters);
std::cout << "              x1408 - " << x1408_avg << " (" << x1408_cycles << " / " << x1408_iters << ") ";
std::cout << "[" << x1408_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "              x1408 - " << x1408_avg << " (" << x1408_cycles << " / " << x1408_iters << ") ";
instrumentation << "[" << x1408_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1407, 2 -> x1704, 5 -> x1638, 4 -> x1812, 7 -> x1408, 1 -> x810, 9 -> x1292, 3 -> x1703, 6 -> x1637), current node x1407 is at depth 8
long x1407_cycles = c1->getArg(X1407_cycles_arg, false);
long x1407_iters = c1->getArg(X1407_iters_arg, false);
long x1407_iters_per_parent = x1407_iters / std::max((long)1,x1408_iters);
long x1407_avg = x1407_cycles / std::max((long)1,x1407_iters);
std::cout << "                x1407 - " << x1407_avg << " (" << x1407_cycles << " / " << x1407_iters << ") ";
std::cout << "[" << x1407_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "                x1407 - " << x1407_avg << " (" << x1407_cycles << " / " << x1407_iters << ") ";
instrumentation << "[" << x1407_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1407, 2 -> x1704, 5 -> x1638, 4 -> x1812, 7 -> x1408, 1 -> x810, 9 -> x1337, 3 -> x1703, 6 -> x1637), current node x1337 is at depth 9
long x1337_cycles = c1->getArg(X1337_cycles_arg, false);
long x1337_iters = c1->getArg(X1337_iters_arg, false);
long x1337_iters_per_parent = x1337_iters / std::max((long)1,x1407_iters);
long x1337_avg = x1337_cycles / std::max((long)1,x1337_iters);
std::cout << "                  x1337 - " << x1337_avg << " (" << x1337_cycles << " / " << x1337_iters << ") ";
std::cout << "[" << x1337_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "                  x1337 - " << x1337_avg << " (" << x1337_cycles << " / " << x1337_iters << ") ";
instrumentation << "[" << x1337_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1407, 2 -> x1704, 5 -> x1638, 4 -> x1812, 7 -> x1408, 1 -> x810, 9 -> x1360, 3 -> x1703, 6 -> x1637), current node x1360 is at depth 9
long x1360_cycles = c1->getArg(X1360_cycles_arg, false);
long x1360_iters = c1->getArg(X1360_iters_arg, false);
long x1360_iters_per_parent = x1360_iters / std::max((long)1,x1407_iters);
long x1360_avg = x1360_cycles / std::max((long)1,x1360_iters);
std::cout << "                  x1360 - " << x1360_avg << " (" << x1360_cycles << " / " << x1360_iters << ") ";
std::cout << "[" << x1360_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "                  x1360 - " << x1360_avg << " (" << x1360_cycles << " / " << x1360_iters << ") ";
instrumentation << "[" << x1360_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1407, 2 -> x1704, 5 -> x1638, 4 -> x1812, 7 -> x1408, 1 -> x810, 9 -> x1383, 3 -> x1703, 6 -> x1637), current node x1383 is at depth 9
long x1383_cycles = c1->getArg(X1383_cycles_arg, false);
long x1383_iters = c1->getArg(X1383_iters_arg, false);
long x1383_iters_per_parent = x1383_iters / std::max((long)1,x1407_iters);
long x1383_avg = x1383_cycles / std::max((long)1,x1383_iters);
std::cout << "                  x1383 - " << x1383_avg << " (" << x1383_cycles << " / " << x1383_iters << ") ";
std::cout << "[" << x1383_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "                  x1383 - " << x1383_avg << " (" << x1383_cycles << " / " << x1383_iters << ") ";
instrumentation << "[" << x1383_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1407, 2 -> x1704, 5 -> x1638, 4 -> x1812, 7 -> x1408, 1 -> x810, 9 -> x1406, 3 -> x1703, 6 -> x1637), current node x1406 is at depth 9
long x1406_cycles = c1->getArg(X1406_cycles_arg, false);
long x1406_iters = c1->getArg(X1406_iters_arg, false);
long x1406_iters_per_parent = x1406_iters / std::max((long)1,x1407_iters);
long x1406_avg = x1406_cycles / std::max((long)1,x1406_iters);
std::cout << "                  x1406 - " << x1406_avg << " (" << x1406_cycles << " / " << x1406_iters << ") ";
std::cout << "[" << x1406_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "                  x1406 - " << x1406_avg << " (" << x1406_cycles << " / " << x1406_iters << ") ";
instrumentation << "[" << x1406_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1407, 2 -> x1704, 5 -> x1638, 4 -> x1812, 7 -> x1522, 1 -> x810, 9 -> x1406, 3 -> x1703, 6 -> x1637), current node x1522 is at depth 7
long x1522_cycles = c1->getArg(X1522_cycles_arg, false);
long x1522_iters = c1->getArg(X1522_iters_arg, false);
long x1522_iters_per_parent = x1522_iters / std::max((long)1,x1637_iters);
long x1522_avg = x1522_cycles / std::max((long)1,x1522_iters);
std::cout << "              x1522 - " << x1522_avg << " (" << x1522_cycles << " / " << x1522_iters << ") ";
std::cout << "[" << x1522_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "              x1522 - " << x1522_avg << " (" << x1522_cycles << " / " << x1522_iters << ") ";
instrumentation << "[" << x1522_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1521, 2 -> x1704, 5 -> x1638, 4 -> x1812, 7 -> x1522, 1 -> x810, 9 -> x1406, 3 -> x1703, 6 -> x1637), current node x1521 is at depth 8
long x1521_cycles = c1->getArg(X1521_cycles_arg, false);
long x1521_iters = c1->getArg(X1521_iters_arg, false);
long x1521_iters_per_parent = x1521_iters / std::max((long)1,x1522_iters);
long x1521_avg = x1521_cycles / std::max((long)1,x1521_iters);
std::cout << "                x1521 - " << x1521_avg << " (" << x1521_cycles << " / " << x1521_iters << ") ";
std::cout << "[" << x1521_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "                x1521 - " << x1521_avg << " (" << x1521_cycles << " / " << x1521_iters << ") ";
instrumentation << "[" << x1521_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1521, 2 -> x1704, 5 -> x1638, 4 -> x1812, 7 -> x1522, 1 -> x810, 9 -> x1451, 3 -> x1703, 6 -> x1637), current node x1451 is at depth 9
long x1451_cycles = c1->getArg(X1451_cycles_arg, false);
long x1451_iters = c1->getArg(X1451_iters_arg, false);
long x1451_iters_per_parent = x1451_iters / std::max((long)1,x1521_iters);
long x1451_avg = x1451_cycles / std::max((long)1,x1451_iters);
std::cout << "                  x1451 - " << x1451_avg << " (" << x1451_cycles << " / " << x1451_iters << ") ";
std::cout << "[" << x1451_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "                  x1451 - " << x1451_avg << " (" << x1451_cycles << " / " << x1451_iters << ") ";
instrumentation << "[" << x1451_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1521, 2 -> x1704, 5 -> x1638, 4 -> x1812, 7 -> x1522, 1 -> x810, 9 -> x1474, 3 -> x1703, 6 -> x1637), current node x1474 is at depth 9
long x1474_cycles = c1->getArg(X1474_cycles_arg, false);
long x1474_iters = c1->getArg(X1474_iters_arg, false);
long x1474_iters_per_parent = x1474_iters / std::max((long)1,x1521_iters);
long x1474_avg = x1474_cycles / std::max((long)1,x1474_iters);
std::cout << "                  x1474 - " << x1474_avg << " (" << x1474_cycles << " / " << x1474_iters << ") ";
std::cout << "[" << x1474_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "                  x1474 - " << x1474_avg << " (" << x1474_cycles << " / " << x1474_iters << ") ";
instrumentation << "[" << x1474_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1521, 2 -> x1704, 5 -> x1638, 4 -> x1812, 7 -> x1522, 1 -> x810, 9 -> x1497, 3 -> x1703, 6 -> x1637), current node x1497 is at depth 9
long x1497_cycles = c1->getArg(X1497_cycles_arg, false);
long x1497_iters = c1->getArg(X1497_iters_arg, false);
long x1497_iters_per_parent = x1497_iters / std::max((long)1,x1521_iters);
long x1497_avg = x1497_cycles / std::max((long)1,x1497_iters);
std::cout << "                  x1497 - " << x1497_avg << " (" << x1497_cycles << " / " << x1497_iters << ") ";
std::cout << "[" << x1497_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "                  x1497 - " << x1497_avg << " (" << x1497_cycles << " / " << x1497_iters << ") ";
instrumentation << "[" << x1497_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1521, 2 -> x1704, 5 -> x1638, 4 -> x1812, 7 -> x1522, 1 -> x810, 9 -> x1520, 3 -> x1703, 6 -> x1637), current node x1520 is at depth 9
long x1520_cycles = c1->getArg(X1520_cycles_arg, false);
long x1520_iters = c1->getArg(X1520_iters_arg, false);
long x1520_iters_per_parent = x1520_iters / std::max((long)1,x1521_iters);
long x1520_avg = x1520_cycles / std::max((long)1,x1520_iters);
std::cout << "                  x1520 - " << x1520_avg << " (" << x1520_cycles << " / " << x1520_iters << ") ";
std::cout << "[" << x1520_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "                  x1520 - " << x1520_avg << " (" << x1520_cycles << " / " << x1520_iters << ") ";
instrumentation << "[" << x1520_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1521, 2 -> x1704, 5 -> x1638, 4 -> x1812, 7 -> x1636, 1 -> x810, 9 -> x1520, 3 -> x1703, 6 -> x1637), current node x1636 is at depth 7
long x1636_cycles = c1->getArg(X1636_cycles_arg, false);
long x1636_iters = c1->getArg(X1636_iters_arg, false);
long x1636_iters_per_parent = x1636_iters / std::max((long)1,x1637_iters);
long x1636_avg = x1636_cycles / std::max((long)1,x1636_iters);
std::cout << "              x1636 - " << x1636_avg << " (" << x1636_cycles << " / " << x1636_iters << ") ";
std::cout << "[" << x1636_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "              x1636 - " << x1636_avg << " (" << x1636_cycles << " / " << x1636_iters << ") ";
instrumentation << "[" << x1636_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1635, 2 -> x1704, 5 -> x1638, 4 -> x1812, 7 -> x1636, 1 -> x810, 9 -> x1520, 3 -> x1703, 6 -> x1637), current node x1635 is at depth 8
long x1635_cycles = c1->getArg(X1635_cycles_arg, false);
long x1635_iters = c1->getArg(X1635_iters_arg, false);
long x1635_iters_per_parent = x1635_iters / std::max((long)1,x1636_iters);
long x1635_avg = x1635_cycles / std::max((long)1,x1635_iters);
std::cout << "                x1635 - " << x1635_avg << " (" << x1635_cycles << " / " << x1635_iters << ") ";
std::cout << "[" << x1635_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "                x1635 - " << x1635_avg << " (" << x1635_cycles << " / " << x1635_iters << ") ";
instrumentation << "[" << x1635_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1635, 2 -> x1704, 5 -> x1638, 4 -> x1812, 7 -> x1636, 1 -> x810, 9 -> x1565, 3 -> x1703, 6 -> x1637), current node x1565 is at depth 9
long x1565_cycles = c1->getArg(X1565_cycles_arg, false);
long x1565_iters = c1->getArg(X1565_iters_arg, false);
long x1565_iters_per_parent = x1565_iters / std::max((long)1,x1635_iters);
long x1565_avg = x1565_cycles / std::max((long)1,x1565_iters);
std::cout << "                  x1565 - " << x1565_avg << " (" << x1565_cycles << " / " << x1565_iters << ") ";
std::cout << "[" << x1565_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "                  x1565 - " << x1565_avg << " (" << x1565_cycles << " / " << x1565_iters << ") ";
instrumentation << "[" << x1565_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1635, 2 -> x1704, 5 -> x1638, 4 -> x1812, 7 -> x1636, 1 -> x810, 9 -> x1588, 3 -> x1703, 6 -> x1637), current node x1588 is at depth 9
long x1588_cycles = c1->getArg(X1588_cycles_arg, false);
long x1588_iters = c1->getArg(X1588_iters_arg, false);
long x1588_iters_per_parent = x1588_iters / std::max((long)1,x1635_iters);
long x1588_avg = x1588_cycles / std::max((long)1,x1588_iters);
std::cout << "                  x1588 - " << x1588_avg << " (" << x1588_cycles << " / " << x1588_iters << ") ";
std::cout << "[" << x1588_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "                  x1588 - " << x1588_avg << " (" << x1588_cycles << " / " << x1588_iters << ") ";
instrumentation << "[" << x1588_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1635, 2 -> x1704, 5 -> x1638, 4 -> x1812, 7 -> x1636, 1 -> x810, 9 -> x1611, 3 -> x1703, 6 -> x1637), current node x1611 is at depth 9
long x1611_cycles = c1->getArg(X1611_cycles_arg, false);
long x1611_iters = c1->getArg(X1611_iters_arg, false);
long x1611_iters_per_parent = x1611_iters / std::max((long)1,x1635_iters);
long x1611_avg = x1611_cycles / std::max((long)1,x1611_iters);
std::cout << "                  x1611 - " << x1611_avg << " (" << x1611_cycles << " / " << x1611_iters << ") ";
std::cout << "[" << x1611_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "                  x1611 - " << x1611_avg << " (" << x1611_cycles << " / " << x1611_iters << ") ";
instrumentation << "[" << x1611_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1635, 2 -> x1704, 5 -> x1638, 4 -> x1812, 7 -> x1636, 1 -> x810, 9 -> x1634, 3 -> x1703, 6 -> x1637), current node x1634 is at depth 9
long x1634_cycles = c1->getArg(X1634_cycles_arg, false);
long x1634_iters = c1->getArg(X1634_iters_arg, false);
long x1634_iters_per_parent = x1634_iters / std::max((long)1,x1635_iters);
long x1634_avg = x1634_cycles / std::max((long)1,x1634_iters);
std::cout << "                  x1634 - " << x1634_avg << " (" << x1634_cycles << " / " << x1634_iters << ") ";
std::cout << "[" << x1634_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "                  x1634 - " << x1634_avg << " (" << x1634_cycles << " / " << x1634_iters << ") ";
instrumentation << "[" << x1634_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1635, 2 -> x1704, 5 -> x1701, 4 -> x1812, 7 -> x1636, 1 -> x810, 9 -> x1634, 3 -> x1703, 6 -> x1637), current node x1701 is at depth 5
long x1701_cycles = c1->getArg(X1701_cycles_arg, false);
long x1701_iters = c1->getArg(X1701_iters_arg, false);
long x1701_iters_per_parent = x1701_iters / std::max((long)1,x1812_iters);
long x1701_avg = x1701_cycles / std::max((long)1,x1701_iters);
std::cout << "          x1701 - " << x1701_avg << " (" << x1701_cycles << " / " << x1701_iters << ") ";
std::cout << "[" << x1701_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "          x1701 - " << x1701_avg << " (" << x1701_cycles << " / " << x1701_iters << ") ";
instrumentation << "[" << x1701_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1635, 2 -> x1704, 5 -> x1701, 4 -> x1812, 7 -> x1636, 1 -> x810, 9 -> x1634, 3 -> x1703, 6 -> x1700), current node x1700 is at depth 6
long x1700_cycles = c1->getArg(X1700_cycles_arg, false);
long x1700_iters = c1->getArg(X1700_iters_arg, false);
long x1700_iters_per_parent = x1700_iters / std::max((long)1,x1701_iters);
long x1700_avg = x1700_cycles / std::max((long)1,x1700_iters);
std::cout << "            x1700 - " << x1700_avg << " (" << x1700_cycles << " / " << x1700_iters << ") ";
std::cout << "[" << x1700_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "            x1700 - " << x1700_avg << " (" << x1700_cycles << " / " << x1700_iters << ") ";
instrumentation << "[" << x1700_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1635, 2 -> x1704, 5 -> x1701, 4 -> x1812, 7 -> x1695, 1 -> x810, 9 -> x1634, 3 -> x1703, 6 -> x1700), current node x1695 is at depth 7
long x1695_cycles = c1->getArg(X1695_cycles_arg, false);
long x1695_iters = c1->getArg(X1695_iters_arg, false);
long x1695_iters_per_parent = x1695_iters / std::max((long)1,x1700_iters);
long x1695_avg = x1695_cycles / std::max((long)1,x1695_iters);
std::cout << "              x1695 - " << x1695_avg << " (" << x1695_cycles << " / " << x1695_iters << ") ";
std::cout << "[" << x1695_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "              x1695 - " << x1695_avg << " (" << x1695_cycles << " / " << x1695_iters << ") ";
instrumentation << "[" << x1695_iters_per_parent << " iters/parent execution]";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1672, 2 -> x1704, 5 -> x1701, 4 -> x1812, 7 -> x1695, 1 -> x810, 9 -> x1634, 3 -> x1703, 6 -> x1700), current node x1672 is at depth 8
long x1672_cycles = c1->getArg(X1672_cycles_arg, false);
long x1672_iters = c1->getArg(X1672_iters_arg, false);
long x1672_iters_per_parent = x1672_iters / std::max((long)1,x1695_iters);
long x1672_avg = x1672_cycles / std::max((long)1,x1672_iters);
std::cout << "                x1672 - " << x1672_avg << " (" << x1672_cycles << " / " << x1672_iters << ") ";
std::cout << "[" << x1672_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "                x1672 - " << x1672_avg << " (" << x1672_cycles << " / " << x1672_iters << ") ";
instrumentation << "[" << x1672_iters_per_parent << " iters/parent execution]";
}
long x1672_stalled = c1->getArg(X1672_stalled_arg, false);
long x1672_idle = c1->getArg(X1672_idle_arg, false);
std::cout << " <# stalled: " << x1672_stalled << ", #idle: " << x1672_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x1672_stalled << ", # idle: " << x1672_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1694, 2 -> x1704, 5 -> x1701, 4 -> x1812, 7 -> x1695, 1 -> x810, 9 -> x1634, 3 -> x1703, 6 -> x1700), current node x1694 is at depth 8
long x1694_cycles = c1->getArg(X1694_cycles_arg, false);
long x1694_iters = c1->getArg(X1694_iters_arg, false);
long x1694_iters_per_parent = x1694_iters / std::max((long)1,x1695_iters);
long x1694_avg = x1694_cycles / std::max((long)1,x1694_iters);
std::cout << "                x1694 - " << x1694_avg << " (" << x1694_cycles << " / " << x1694_iters << ") ";
std::cout << "[" << x1694_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "                x1694 - " << x1694_avg << " (" << x1694_cycles << " / " << x1694_iters << ") ";
instrumentation << "[" << x1694_iters_per_parent << " iters/parent execution]";
}
long x1694_stalled = c1->getArg(X1694_stalled_arg, false);
long x1694_idle = c1->getArg(X1694_idle_arg, false);
std::cout << " <# stalled: " << x1694_stalled << ", #idle: " << x1694_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x1694_stalled << ", # idle: " << x1694_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
 // immediate parent hashmap now Map(8 -> x1694, 2 -> x1704, 5 -> x1701, 4 -> x1812, 7 -> x1699, 1 -> x810, 9 -> x1634, 3 -> x1703, 6 -> x1700), current node x1699 is at depth 7
long x1699_cycles = c1->getArg(X1699_cycles_arg, false);
long x1699_iters = c1->getArg(X1699_iters_arg, false);
long x1699_iters_per_parent = x1699_iters / std::max((long)1,x1700_iters);
long x1699_avg = x1699_cycles / std::max((long)1,x1699_iters);
std::cout << "              x1699 - " << x1699_avg << " (" << x1699_cycles << " / " << x1699_iters << ") ";
std::cout << "[" << x1699_iters_per_parent << " iters/parent execution]";
if (instrumentation.is_open()) {
instrumentation << "              x1699 - " << x1699_avg << " (" << x1699_cycles << " / " << x1699_iters << ") ";
instrumentation << "[" << x1699_iters_per_parent << " iters/parent execution]";
}
long x1699_stalled = c1->getArg(X1699_stalled_arg, false);
long x1699_idle = c1->getArg(X1699_idle_arg, false);
std::cout << " <# stalled: " << x1699_stalled << ", #idle: " << x1699_idle << ">";
if (instrumentation.is_open()) {
instrumentation << " <# stalled: " << x1699_stalled << ", # idle: " << x1699_idle << ">";
}
std::cout << std::endl;
if (instrumentation.is_open()) {
instrumentation << std::endl;
}
instrumentation.close();
int32_t x1705 = x904 * x905;
vector<double>* x1706 = new vector<double>(x1705);
vector<int32_t>* x1707_rawified = new vector<int32_t>((*x1706).size());
c1->memcpy(&(*x1707_rawified)[0], x906, (*x1707_rawified).size() * sizeof(int32_t));
for (int x1706_i = 0; x1706_i < (*x1706).size(); x1706_i++) {
int32_t x1706_tmp = (*x1707_rawified)[x1706_i];
(*x1706)[x1706_i] = (double) x1706_tmp / ((int32_t)1 << 8);
}
vector<double>* x1723 = new vector<double>(x896);
for (int b111 = 0; b111 < x896; b111++) {
int32_t x1708 = b111 / x885;
int32_t x1709 = (int32_t) ((b111 % x885 + x885) % x885);
string x1710;
try {
x1710 = (*x877).at(2);
}
catch (std::out_of_range& e) {
printHelp();
}
int32_t x1711 = std::stol(x1710);
vector<double>* x1719 = new vector<double>(x1711);
for (int b116 = 0; b116 < x1711; b116++) {
int32_t x1712 = x1708 * x888;
int32_t x1713 = x1712 + b116;
double x1714 = (*x892)[x1713];
int32_t x1715 = b116 * x885;
int32_t x1716 = x1715 + x1709;
double x1717 = (*x895)[x1716];
double x1718 = x1714 * x1717;
(*x1719)[b116] = x1718;
}
double x1722;
if ((*x1719).size() > 0) { // Hack to handle reductions on things of length 0
x1722 = (*x1719)[0];
}
else {
x1722 = 0;
}
for (int b125 = 1; b125 < (*x1719).size(); b125++) {
double b126 = (*x1719)[b125];
double b127 = x1722;
double x1721 = b126 + b127;
x1722 = x1721;
}
(*x1723)[b111] = x1722;
}
string x1724 = (string("Received: ") + string("\n"));
std::cout << x1724;
for (int b135 = 0; b135 < x904; b135 = b135 + 1) {
for (int b136 = 0; b136 < x905; b136 = b136 + 1) {
int32_t x1726 = b135 * x905;
int32_t x1727 = x1726 + b136;
double x1728 = (*x1706)[x1727];
string x1729 = std::to_string(x1728);
string x1730 = (x1729 + string("\t"));
std::cout << x1730;
}
std::cout << string("\n");
}
string x1735 = (string("Wanted: ") + string("\n"));
std::cout << x1735;
for (int b148 = 0; b148 < x882; b148 = b148 + 1) {
for (int b149 = 0; b149 < x885; b149 = b149 + 1) {
int32_t x1737 = b148 * x885;
int32_t x1738 = x1737 + b149;
double x1739 = (*x1723)[x1738];
string x1740 = std::to_string(x1739);
string x1741 = (x1740 + string("\t"));
std::cout << x1741;
}
std::cout << string("\n");
}
vector<bool>* x1749 = new vector<bool>((*x1706).size());
for (int b159 = 0; b159 < (*x1706).size(); b159++) { 
double x1746 = (*x1706)[b159];
double x1747 = (*x1723)[b159];
bool x1748 = x1746 == x1747;
(*x1749)[b159] = x1748;
}
bool x1752;
if ((*x1749).size() > 0) { // Hack to handle reductions on things of length 0
x1752 = (*x1749)[0];
}
else {
x1752 = 0;
}
for (int b165 = 1; b165 < (*x1749).size(); b165++) {
bool b166 = (*x1749)[b165];
bool b167 = x1752;
bool x1751 = b166 & b167;
x1752 = x1751;
}
string x1753 = x1752 ? string("true") : string("false");
string x1754 = (string("Pass? ") + x1753);
string x1755 = (x1754 + string("\n"));
std::cout << x1755;
string x1757 = ("\n=================\n" + (string("Lab2GEMM.scala:154:12: Assertion failure") + "\n=================\n"));
if (true) { std::cout << std::flush; ASSERT(x1752, "%s", x1757.c_str()); }
delete c1;
}

void printHelp() {
fprintf(stderr, "Help for app: Lab2Part6GEMM\n");
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
