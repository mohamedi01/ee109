package accel
import fringe._
import fringe.templates.memory._
import fringe.templates._
import fringe.Ledger._
import fringe.utils._
import fringe.utils.implicits._
import fringe.templates.math._
import fringe.templates.counters._
import fringe.templates.vector._
import fringe.templates.axi4._
import fringe.SpatialBlocks._
import fringe.templates.memory._
import fringe.templates.memory.implicits._
import fringe.templates.retiming._
import emul.ResidualGenerator._
import fringe.templates.euresys._
import api._
import chisel3._
import chisel3.util._
import Args._
import scala.collection.immutable._

object Instrument {
  def connect(accelUnit: AccelUnit, instrctrs: List[InstrCtr]): Unit = {
    object Block1Chunker0 { // 49 nodes, 49 weight
      def gen(): Map[String, Any] = {
        accelUnit.io.argOuts(api.X810_cycles_arg).port.bits := instrctrs(X810_instrctr).cycs
        accelUnit.io.argOuts(api.X810_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X810_iters_arg).port.bits := instrctrs(X810_instrctr).iters
        accelUnit.io.argOuts(api.X810_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1704_cycles_arg).port.bits := instrctrs(X1704_instrctr).cycs
        accelUnit.io.argOuts(api.X1704_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1704_iters_arg).port.bits := instrctrs(X1704_instrctr).iters
        accelUnit.io.argOuts(api.X1704_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X921_cycles_arg).port.bits := instrctrs(X921_instrctr).cycs
        accelUnit.io.argOuts(api.X921_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X921_iters_arg).port.bits := instrctrs(X921_instrctr).iters
        accelUnit.io.argOuts(api.X921_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1703_cycles_arg).port.bits := instrctrs(X1703_instrctr).cycs
        accelUnit.io.argOuts(api.X1703_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1703_iters_arg).port.bits := instrctrs(X1703_instrctr).iters
        accelUnit.io.argOuts(api.X1703_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X932_cycles_arg).port.bits := instrctrs(X932_instrctr).cycs
        accelUnit.io.argOuts(api.X932_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X932_iters_arg).port.bits := instrctrs(X932_instrctr).iters
        accelUnit.io.argOuts(api.X932_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1002_cycles_arg).port.bits := instrctrs(X1002_instrctr).cycs
        accelUnit.io.argOuts(api.X1002_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1002_iters_arg).port.bits := instrctrs(X1002_instrctr).iters
        accelUnit.io.argOuts(api.X1002_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X964_cycles_arg).port.bits := instrctrs(X964_instrctr).cycs
        accelUnit.io.argOuts(api.X964_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X964_iters_arg).port.bits := instrctrs(X964_instrctr).iters
        accelUnit.io.argOuts(api.X964_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X964_stalled_arg).port.bits := instrctrs(X964_instrctr).stalls
        accelUnit.io.argOuts(api.X964_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X964_idle_arg).port.bits := instrctrs(X964_instrctr).idles
        accelUnit.io.argOuts(api.X964_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1001_cycles_arg).port.bits := instrctrs(X1001_instrctr).cycs
        accelUnit.io.argOuts(api.X1001_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1001_iters_arg).port.bits := instrctrs(X1001_instrctr).iters
        accelUnit.io.argOuts(api.X1001_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X981_cycles_arg).port.bits := instrctrs(X981_instrctr).cycs
        accelUnit.io.argOuts(api.X981_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X981_iters_arg).port.bits := instrctrs(X981_instrctr).iters
        accelUnit.io.argOuts(api.X981_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X981_stalled_arg).port.bits := instrctrs(X981_instrctr).stalls
        accelUnit.io.argOuts(api.X981_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X981_idle_arg).port.bits := instrctrs(X981_instrctr).idles
        accelUnit.io.argOuts(api.X981_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1000_cycles_arg).port.bits := instrctrs(X1000_instrctr).cycs
        accelUnit.io.argOuts(api.X1000_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1000_iters_arg).port.bits := instrctrs(X1000_instrctr).iters
        accelUnit.io.argOuts(api.X1000_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1000_stalled_arg).port.bits := instrctrs(X1000_instrctr).stalls
        Map[String,Any]()
      }
    }
    val block1chunk0: Map[String, Any] = Block1Chunker0.gen()
    object Block1Chunker1 { // 49 nodes, 49 weight
      def gen(): Map[String, Any] = {
        accelUnit.io.argOuts(api.X1000_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1000_idle_arg).port.bits := instrctrs(X1000_instrctr).idles
        accelUnit.io.argOuts(api.X1000_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1812_cycles_arg).port.bits := instrctrs(X1812_instrctr).cycs
        accelUnit.io.argOuts(api.X1812_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1812_iters_arg).port.bits := instrctrs(X1812_instrctr).iters
        accelUnit.io.argOuts(api.X1812_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1016_cycles_arg).port.bits := instrctrs(X1016_instrctr).cycs
        accelUnit.io.argOuts(api.X1016_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1016_iters_arg).port.bits := instrctrs(X1016_instrctr).iters
        accelUnit.io.argOuts(api.X1016_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1811_cycles_arg).port.bits := instrctrs(X1811_instrctr).cycs
        accelUnit.io.argOuts(api.X1811_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1811_iters_arg).port.bits := instrctrs(X1811_instrctr).iters
        accelUnit.io.argOuts(api.X1811_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1087_cycles_arg).port.bits := instrctrs(X1087_instrctr).cycs
        accelUnit.io.argOuts(api.X1087_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1087_iters_arg).port.bits := instrctrs(X1087_instrctr).iters
        accelUnit.io.argOuts(api.X1087_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1049_cycles_arg).port.bits := instrctrs(X1049_instrctr).cycs
        accelUnit.io.argOuts(api.X1049_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1049_iters_arg).port.bits := instrctrs(X1049_instrctr).iters
        accelUnit.io.argOuts(api.X1049_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1049_stalled_arg).port.bits := instrctrs(X1049_instrctr).stalls
        accelUnit.io.argOuts(api.X1049_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1049_idle_arg).port.bits := instrctrs(X1049_instrctr).idles
        accelUnit.io.argOuts(api.X1049_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1086_cycles_arg).port.bits := instrctrs(X1086_instrctr).cycs
        accelUnit.io.argOuts(api.X1086_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1086_iters_arg).port.bits := instrctrs(X1086_instrctr).iters
        accelUnit.io.argOuts(api.X1086_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1066_cycles_arg).port.bits := instrctrs(X1066_instrctr).cycs
        accelUnit.io.argOuts(api.X1066_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1066_iters_arg).port.bits := instrctrs(X1066_instrctr).iters
        accelUnit.io.argOuts(api.X1066_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1066_stalled_arg).port.bits := instrctrs(X1066_instrctr).stalls
        accelUnit.io.argOuts(api.X1066_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1066_idle_arg).port.bits := instrctrs(X1066_instrctr).idles
        accelUnit.io.argOuts(api.X1066_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1085_cycles_arg).port.bits := instrctrs(X1085_instrctr).cycs
        accelUnit.io.argOuts(api.X1085_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1085_iters_arg).port.bits := instrctrs(X1085_instrctr).iters
        accelUnit.io.argOuts(api.X1085_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1085_stalled_arg).port.bits := instrctrs(X1085_instrctr).stalls
        accelUnit.io.argOuts(api.X1085_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1085_idle_arg).port.bits := instrctrs(X1085_instrctr).idles
        accelUnit.io.argOuts(api.X1085_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1158_cycles_arg).port.bits := instrctrs(X1158_instrctr).cycs
        accelUnit.io.argOuts(api.X1158_cycles_arg).port.valid := accelUnit.io.enable
        Map[String,Any]()
      }
    }
    val block1chunk1: Map[String, Any] = Block1Chunker1.gen()
    object Block1Chunker2 { // 49 nodes, 49 weight
      def gen(): Map[String, Any] = {
        accelUnit.io.argOuts(api.X1158_iters_arg).port.bits := instrctrs(X1158_instrctr).iters
        accelUnit.io.argOuts(api.X1158_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1117_cycles_arg).port.bits := instrctrs(X1117_instrctr).cycs
        accelUnit.io.argOuts(api.X1117_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1117_iters_arg).port.bits := instrctrs(X1117_instrctr).iters
        accelUnit.io.argOuts(api.X1117_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1117_stalled_arg).port.bits := instrctrs(X1117_instrctr).stalls
        accelUnit.io.argOuts(api.X1117_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1117_idle_arg).port.bits := instrctrs(X1117_instrctr).idles
        accelUnit.io.argOuts(api.X1117_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1157_cycles_arg).port.bits := instrctrs(X1157_instrctr).cycs
        accelUnit.io.argOuts(api.X1157_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1157_iters_arg).port.bits := instrctrs(X1157_instrctr).iters
        accelUnit.io.argOuts(api.X1157_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1134_cycles_arg).port.bits := instrctrs(X1134_instrctr).cycs
        accelUnit.io.argOuts(api.X1134_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1134_iters_arg).port.bits := instrctrs(X1134_instrctr).iters
        accelUnit.io.argOuts(api.X1134_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1134_stalled_arg).port.bits := instrctrs(X1134_instrctr).stalls
        accelUnit.io.argOuts(api.X1134_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1134_idle_arg).port.bits := instrctrs(X1134_instrctr).idles
        accelUnit.io.argOuts(api.X1134_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1156_cycles_arg).port.bits := instrctrs(X1156_instrctr).cycs
        accelUnit.io.argOuts(api.X1156_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1156_iters_arg).port.bits := instrctrs(X1156_instrctr).iters
        accelUnit.io.argOuts(api.X1156_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1156_stalled_arg).port.bits := instrctrs(X1156_instrctr).stalls
        accelUnit.io.argOuts(api.X1156_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1156_idle_arg).port.bits := instrctrs(X1156_instrctr).idles
        accelUnit.io.argOuts(api.X1156_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1638_cycles_arg).port.bits := instrctrs(X1638_instrctr).cycs
        accelUnit.io.argOuts(api.X1638_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1638_iters_arg).port.bits := instrctrs(X1638_instrctr).iters
        accelUnit.io.argOuts(api.X1638_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1637_cycles_arg).port.bits := instrctrs(X1637_instrctr).cycs
        accelUnit.io.argOuts(api.X1637_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1637_iters_arg).port.bits := instrctrs(X1637_instrctr).iters
        accelUnit.io.argOuts(api.X1637_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1294_cycles_arg).port.bits := instrctrs(X1294_instrctr).cycs
        accelUnit.io.argOuts(api.X1294_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1294_iters_arg).port.bits := instrctrs(X1294_instrctr).iters
        accelUnit.io.argOuts(api.X1294_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1293_cycles_arg).port.bits := instrctrs(X1293_instrctr).cycs
        accelUnit.io.argOuts(api.X1293_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1293_iters_arg).port.bits := instrctrs(X1293_instrctr).iters
        accelUnit.io.argOuts(api.X1293_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1223_cycles_arg).port.bits := instrctrs(X1223_instrctr).cycs
        accelUnit.io.argOuts(api.X1223_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1223_iters_arg).port.bits := instrctrs(X1223_instrctr).iters
        Map[String,Any]()
      }
    }
    val block1chunk2: Map[String, Any] = Block1Chunker2.gen()
    object Block1Chunker3 { // 49 nodes, 49 weight
      def gen(): Map[String, Any] = {
        accelUnit.io.argOuts(api.X1223_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1246_cycles_arg).port.bits := instrctrs(X1246_instrctr).cycs
        accelUnit.io.argOuts(api.X1246_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1246_iters_arg).port.bits := instrctrs(X1246_instrctr).iters
        accelUnit.io.argOuts(api.X1246_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1269_cycles_arg).port.bits := instrctrs(X1269_instrctr).cycs
        accelUnit.io.argOuts(api.X1269_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1269_iters_arg).port.bits := instrctrs(X1269_instrctr).iters
        accelUnit.io.argOuts(api.X1269_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1292_cycles_arg).port.bits := instrctrs(X1292_instrctr).cycs
        accelUnit.io.argOuts(api.X1292_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1292_iters_arg).port.bits := instrctrs(X1292_instrctr).iters
        accelUnit.io.argOuts(api.X1292_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1408_cycles_arg).port.bits := instrctrs(X1408_instrctr).cycs
        accelUnit.io.argOuts(api.X1408_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1408_iters_arg).port.bits := instrctrs(X1408_instrctr).iters
        accelUnit.io.argOuts(api.X1408_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1407_cycles_arg).port.bits := instrctrs(X1407_instrctr).cycs
        accelUnit.io.argOuts(api.X1407_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1407_iters_arg).port.bits := instrctrs(X1407_instrctr).iters
        accelUnit.io.argOuts(api.X1407_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1337_cycles_arg).port.bits := instrctrs(X1337_instrctr).cycs
        accelUnit.io.argOuts(api.X1337_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1337_iters_arg).port.bits := instrctrs(X1337_instrctr).iters
        accelUnit.io.argOuts(api.X1337_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1360_cycles_arg).port.bits := instrctrs(X1360_instrctr).cycs
        accelUnit.io.argOuts(api.X1360_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1360_iters_arg).port.bits := instrctrs(X1360_instrctr).iters
        accelUnit.io.argOuts(api.X1360_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1383_cycles_arg).port.bits := instrctrs(X1383_instrctr).cycs
        accelUnit.io.argOuts(api.X1383_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1383_iters_arg).port.bits := instrctrs(X1383_instrctr).iters
        accelUnit.io.argOuts(api.X1383_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1406_cycles_arg).port.bits := instrctrs(X1406_instrctr).cycs
        accelUnit.io.argOuts(api.X1406_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1406_iters_arg).port.bits := instrctrs(X1406_instrctr).iters
        accelUnit.io.argOuts(api.X1406_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1522_cycles_arg).port.bits := instrctrs(X1522_instrctr).cycs
        accelUnit.io.argOuts(api.X1522_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1522_iters_arg).port.bits := instrctrs(X1522_instrctr).iters
        accelUnit.io.argOuts(api.X1522_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1521_cycles_arg).port.bits := instrctrs(X1521_instrctr).cycs
        accelUnit.io.argOuts(api.X1521_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1521_iters_arg).port.bits := instrctrs(X1521_instrctr).iters
        accelUnit.io.argOuts(api.X1521_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1451_cycles_arg).port.bits := instrctrs(X1451_instrctr).cycs
        accelUnit.io.argOuts(api.X1451_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1451_iters_arg).port.bits := instrctrs(X1451_instrctr).iters
        accelUnit.io.argOuts(api.X1451_iters_arg).port.valid := accelUnit.io.enable
        Map[String,Any]()
      }
    }
    val block1chunk3: Map[String, Any] = Block1Chunker3.gen()
    object Block1Chunker4 { // 49 nodes, 49 weight
      def gen(): Map[String, Any] = {
        accelUnit.io.argOuts(api.X1474_cycles_arg).port.bits := instrctrs(X1474_instrctr).cycs
        accelUnit.io.argOuts(api.X1474_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1474_iters_arg).port.bits := instrctrs(X1474_instrctr).iters
        accelUnit.io.argOuts(api.X1474_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1497_cycles_arg).port.bits := instrctrs(X1497_instrctr).cycs
        accelUnit.io.argOuts(api.X1497_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1497_iters_arg).port.bits := instrctrs(X1497_instrctr).iters
        accelUnit.io.argOuts(api.X1497_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1520_cycles_arg).port.bits := instrctrs(X1520_instrctr).cycs
        accelUnit.io.argOuts(api.X1520_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1520_iters_arg).port.bits := instrctrs(X1520_instrctr).iters
        accelUnit.io.argOuts(api.X1520_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1636_cycles_arg).port.bits := instrctrs(X1636_instrctr).cycs
        accelUnit.io.argOuts(api.X1636_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1636_iters_arg).port.bits := instrctrs(X1636_instrctr).iters
        accelUnit.io.argOuts(api.X1636_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1635_cycles_arg).port.bits := instrctrs(X1635_instrctr).cycs
        accelUnit.io.argOuts(api.X1635_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1635_iters_arg).port.bits := instrctrs(X1635_instrctr).iters
        accelUnit.io.argOuts(api.X1635_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1565_cycles_arg).port.bits := instrctrs(X1565_instrctr).cycs
        accelUnit.io.argOuts(api.X1565_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1565_iters_arg).port.bits := instrctrs(X1565_instrctr).iters
        accelUnit.io.argOuts(api.X1565_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1588_cycles_arg).port.bits := instrctrs(X1588_instrctr).cycs
        accelUnit.io.argOuts(api.X1588_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1588_iters_arg).port.bits := instrctrs(X1588_instrctr).iters
        accelUnit.io.argOuts(api.X1588_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1611_cycles_arg).port.bits := instrctrs(X1611_instrctr).cycs
        accelUnit.io.argOuts(api.X1611_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1611_iters_arg).port.bits := instrctrs(X1611_instrctr).iters
        accelUnit.io.argOuts(api.X1611_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1634_cycles_arg).port.bits := instrctrs(X1634_instrctr).cycs
        accelUnit.io.argOuts(api.X1634_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1634_iters_arg).port.bits := instrctrs(X1634_instrctr).iters
        accelUnit.io.argOuts(api.X1634_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1701_cycles_arg).port.bits := instrctrs(X1701_instrctr).cycs
        accelUnit.io.argOuts(api.X1701_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1701_iters_arg).port.bits := instrctrs(X1701_instrctr).iters
        accelUnit.io.argOuts(api.X1701_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1700_cycles_arg).port.bits := instrctrs(X1700_instrctr).cycs
        accelUnit.io.argOuts(api.X1700_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1700_iters_arg).port.bits := instrctrs(X1700_instrctr).iters
        accelUnit.io.argOuts(api.X1700_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1695_cycles_arg).port.bits := instrctrs(X1695_instrctr).cycs
        accelUnit.io.argOuts(api.X1695_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1695_iters_arg).port.bits := instrctrs(X1695_instrctr).iters
        accelUnit.io.argOuts(api.X1695_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1672_cycles_arg).port.bits := instrctrs(X1672_instrctr).cycs
        Map[String,Any]()
      }
    }
    val block1chunk4: Map[String, Any] = Block1Chunker4.gen()
    object Block1Chunker5 { // 23 nodes, 23 weight
      def gen(): Map[String, Any] = {
        accelUnit.io.argOuts(api.X1672_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1672_iters_arg).port.bits := instrctrs(X1672_instrctr).iters
        accelUnit.io.argOuts(api.X1672_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1672_stalled_arg).port.bits := instrctrs(X1672_instrctr).stalls
        accelUnit.io.argOuts(api.X1672_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1672_idle_arg).port.bits := instrctrs(X1672_instrctr).idles
        accelUnit.io.argOuts(api.X1672_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1694_cycles_arg).port.bits := instrctrs(X1694_instrctr).cycs
        accelUnit.io.argOuts(api.X1694_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1694_iters_arg).port.bits := instrctrs(X1694_instrctr).iters
        accelUnit.io.argOuts(api.X1694_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1694_stalled_arg).port.bits := instrctrs(X1694_instrctr).stalls
        accelUnit.io.argOuts(api.X1694_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1694_idle_arg).port.bits := instrctrs(X1694_instrctr).idles
        accelUnit.io.argOuts(api.X1694_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1699_cycles_arg).port.bits := instrctrs(X1699_instrctr).cycs
        accelUnit.io.argOuts(api.X1699_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1699_iters_arg).port.bits := instrctrs(X1699_instrctr).iters
        accelUnit.io.argOuts(api.X1699_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1699_stalled_arg).port.bits := instrctrs(X1699_instrctr).stalls
        accelUnit.io.argOuts(api.X1699_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1699_idle_arg).port.bits := instrctrs(X1699_instrctr).idles
        accelUnit.io.argOuts(api.X1699_idle_arg).port.valid := accelUnit.io.enable
        Map[String,Any]()
      }
    }
    val block1chunk5: Map[String, Any] = Block1Chunker5.gen()
    val numArgOuts_breakpts = 0
  }
}
