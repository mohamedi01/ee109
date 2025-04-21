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
        accelUnit.io.argOuts(api.X1251_cycles_arg).port.bits := instrctrs(X1251_instrctr).cycs
        accelUnit.io.argOuts(api.X1251_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1251_iters_arg).port.bits := instrctrs(X1251_instrctr).iters
        accelUnit.io.argOuts(api.X1251_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X921_cycles_arg).port.bits := instrctrs(X921_instrctr).cycs
        accelUnit.io.argOuts(api.X921_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X921_iters_arg).port.bits := instrctrs(X921_instrctr).iters
        accelUnit.io.argOuts(api.X921_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1250_cycles_arg).port.bits := instrctrs(X1250_instrctr).cycs
        accelUnit.io.argOuts(api.X1250_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1250_iters_arg).port.bits := instrctrs(X1250_instrctr).iters
        accelUnit.io.argOuts(api.X1250_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X932_cycles_arg).port.bits := instrctrs(X932_instrctr).cycs
        accelUnit.io.argOuts(api.X932_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X932_iters_arg).port.bits := instrctrs(X932_instrctr).iters
        accelUnit.io.argOuts(api.X932_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1001_cycles_arg).port.bits := instrctrs(X1001_instrctr).cycs
        accelUnit.io.argOuts(api.X1001_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1001_iters_arg).port.bits := instrctrs(X1001_instrctr).iters
        accelUnit.io.argOuts(api.X1001_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X964_cycles_arg).port.bits := instrctrs(X964_instrctr).cycs
        accelUnit.io.argOuts(api.X964_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X964_iters_arg).port.bits := instrctrs(X964_instrctr).iters
        accelUnit.io.argOuts(api.X964_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X964_stalled_arg).port.bits := instrctrs(X964_instrctr).stalls
        accelUnit.io.argOuts(api.X964_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X964_idle_arg).port.bits := instrctrs(X964_instrctr).idles
        accelUnit.io.argOuts(api.X964_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1000_cycles_arg).port.bits := instrctrs(X1000_instrctr).cycs
        accelUnit.io.argOuts(api.X1000_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1000_iters_arg).port.bits := instrctrs(X1000_instrctr).iters
        accelUnit.io.argOuts(api.X1000_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X981_cycles_arg).port.bits := instrctrs(X981_instrctr).cycs
        accelUnit.io.argOuts(api.X981_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X981_iters_arg).port.bits := instrctrs(X981_instrctr).iters
        accelUnit.io.argOuts(api.X981_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X981_stalled_arg).port.bits := instrctrs(X981_instrctr).stalls
        accelUnit.io.argOuts(api.X981_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X981_idle_arg).port.bits := instrctrs(X981_instrctr).idles
        accelUnit.io.argOuts(api.X981_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X999_cycles_arg).port.bits := instrctrs(X999_instrctr).cycs
        accelUnit.io.argOuts(api.X999_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X999_iters_arg).port.bits := instrctrs(X999_instrctr).iters
        accelUnit.io.argOuts(api.X999_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X999_stalled_arg).port.bits := instrctrs(X999_instrctr).stalls
        Map[String,Any]()
      }
    }
    val block1chunk0: Map[String, Any] = Block1Chunker0.gen()
    object Block1Chunker1 { // 49 nodes, 49 weight
      def gen(): Map[String, Any] = {
        accelUnit.io.argOuts(api.X999_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X999_idle_arg).port.bits := instrctrs(X999_instrctr).idles
        accelUnit.io.argOuts(api.X999_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1335_cycles_arg).port.bits := instrctrs(X1335_instrctr).cycs
        accelUnit.io.argOuts(api.X1335_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1335_iters_arg).port.bits := instrctrs(X1335_instrctr).iters
        accelUnit.io.argOuts(api.X1335_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1015_cycles_arg).port.bits := instrctrs(X1015_instrctr).cycs
        accelUnit.io.argOuts(api.X1015_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1015_iters_arg).port.bits := instrctrs(X1015_instrctr).iters
        accelUnit.io.argOuts(api.X1015_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1334_cycles_arg).port.bits := instrctrs(X1334_instrctr).cycs
        accelUnit.io.argOuts(api.X1334_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1334_iters_arg).port.bits := instrctrs(X1334_instrctr).iters
        accelUnit.io.argOuts(api.X1334_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1085_cycles_arg).port.bits := instrctrs(X1085_instrctr).cycs
        accelUnit.io.argOuts(api.X1085_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1085_iters_arg).port.bits := instrctrs(X1085_instrctr).iters
        accelUnit.io.argOuts(api.X1085_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1048_cycles_arg).port.bits := instrctrs(X1048_instrctr).cycs
        accelUnit.io.argOuts(api.X1048_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1048_iters_arg).port.bits := instrctrs(X1048_instrctr).iters
        accelUnit.io.argOuts(api.X1048_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1048_stalled_arg).port.bits := instrctrs(X1048_instrctr).stalls
        accelUnit.io.argOuts(api.X1048_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1048_idle_arg).port.bits := instrctrs(X1048_instrctr).idles
        accelUnit.io.argOuts(api.X1048_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1084_cycles_arg).port.bits := instrctrs(X1084_instrctr).cycs
        accelUnit.io.argOuts(api.X1084_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1084_iters_arg).port.bits := instrctrs(X1084_instrctr).iters
        accelUnit.io.argOuts(api.X1084_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1065_cycles_arg).port.bits := instrctrs(X1065_instrctr).cycs
        accelUnit.io.argOuts(api.X1065_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1065_iters_arg).port.bits := instrctrs(X1065_instrctr).iters
        accelUnit.io.argOuts(api.X1065_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1065_stalled_arg).port.bits := instrctrs(X1065_instrctr).stalls
        accelUnit.io.argOuts(api.X1065_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1065_idle_arg).port.bits := instrctrs(X1065_instrctr).idles
        accelUnit.io.argOuts(api.X1065_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1083_cycles_arg).port.bits := instrctrs(X1083_instrctr).cycs
        accelUnit.io.argOuts(api.X1083_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1083_iters_arg).port.bits := instrctrs(X1083_instrctr).iters
        accelUnit.io.argOuts(api.X1083_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1083_stalled_arg).port.bits := instrctrs(X1083_instrctr).stalls
        accelUnit.io.argOuts(api.X1083_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1083_idle_arg).port.bits := instrctrs(X1083_instrctr).idles
        accelUnit.io.argOuts(api.X1083_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1153_cycles_arg).port.bits := instrctrs(X1153_instrctr).cycs
        accelUnit.io.argOuts(api.X1153_cycles_arg).port.valid := accelUnit.io.enable
        Map[String,Any]()
      }
    }
    val block1chunk1: Map[String, Any] = Block1Chunker1.gen()
    object Block1Chunker2 { // 49 nodes, 49 weight
      def gen(): Map[String, Any] = {
        accelUnit.io.argOuts(api.X1153_iters_arg).port.bits := instrctrs(X1153_instrctr).iters
        accelUnit.io.argOuts(api.X1153_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1115_cycles_arg).port.bits := instrctrs(X1115_instrctr).cycs
        accelUnit.io.argOuts(api.X1115_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1115_iters_arg).port.bits := instrctrs(X1115_instrctr).iters
        accelUnit.io.argOuts(api.X1115_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1115_stalled_arg).port.bits := instrctrs(X1115_instrctr).stalls
        accelUnit.io.argOuts(api.X1115_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1115_idle_arg).port.bits := instrctrs(X1115_instrctr).idles
        accelUnit.io.argOuts(api.X1115_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1152_cycles_arg).port.bits := instrctrs(X1152_instrctr).cycs
        accelUnit.io.argOuts(api.X1152_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1152_iters_arg).port.bits := instrctrs(X1152_instrctr).iters
        accelUnit.io.argOuts(api.X1152_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1132_cycles_arg).port.bits := instrctrs(X1132_instrctr).cycs
        accelUnit.io.argOuts(api.X1132_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1132_iters_arg).port.bits := instrctrs(X1132_instrctr).iters
        accelUnit.io.argOuts(api.X1132_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1132_stalled_arg).port.bits := instrctrs(X1132_instrctr).stalls
        accelUnit.io.argOuts(api.X1132_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1132_idle_arg).port.bits := instrctrs(X1132_instrctr).idles
        accelUnit.io.argOuts(api.X1132_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1151_cycles_arg).port.bits := instrctrs(X1151_instrctr).cycs
        accelUnit.io.argOuts(api.X1151_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1151_iters_arg).port.bits := instrctrs(X1151_instrctr).iters
        accelUnit.io.argOuts(api.X1151_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1151_stalled_arg).port.bits := instrctrs(X1151_instrctr).stalls
        accelUnit.io.argOuts(api.X1151_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1151_idle_arg).port.bits := instrctrs(X1151_instrctr).idles
        accelUnit.io.argOuts(api.X1151_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1188_cycles_arg).port.bits := instrctrs(X1188_instrctr).cycs
        accelUnit.io.argOuts(api.X1188_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1188_iters_arg).port.bits := instrctrs(X1188_instrctr).iters
        accelUnit.io.argOuts(api.X1188_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1187_cycles_arg).port.bits := instrctrs(X1187_instrctr).cycs
        accelUnit.io.argOuts(api.X1187_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1187_iters_arg).port.bits := instrctrs(X1187_instrctr).iters
        accelUnit.io.argOuts(api.X1187_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1186_cycles_arg).port.bits := instrctrs(X1186_instrctr).cycs
        accelUnit.io.argOuts(api.X1186_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1186_iters_arg).port.bits := instrctrs(X1186_instrctr).iters
        accelUnit.io.argOuts(api.X1186_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1248_cycles_arg).port.bits := instrctrs(X1248_instrctr).cycs
        accelUnit.io.argOuts(api.X1248_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1248_iters_arg).port.bits := instrctrs(X1248_instrctr).iters
        accelUnit.io.argOuts(api.X1248_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1247_cycles_arg).port.bits := instrctrs(X1247_instrctr).cycs
        accelUnit.io.argOuts(api.X1247_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1247_iters_arg).port.bits := instrctrs(X1247_instrctr).iters
        Map[String,Any]()
      }
    }
    val block1chunk2: Map[String, Any] = Block1Chunker2.gen()
    object Block1Chunker3 { // 29 nodes, 29 weight
      def gen(): Map[String, Any] = {
        accelUnit.io.argOuts(api.X1247_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1242_cycles_arg).port.bits := instrctrs(X1242_instrctr).cycs
        accelUnit.io.argOuts(api.X1242_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1242_iters_arg).port.bits := instrctrs(X1242_instrctr).iters
        accelUnit.io.argOuts(api.X1242_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1222_cycles_arg).port.bits := instrctrs(X1222_instrctr).cycs
        accelUnit.io.argOuts(api.X1222_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1222_iters_arg).port.bits := instrctrs(X1222_instrctr).iters
        accelUnit.io.argOuts(api.X1222_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1222_stalled_arg).port.bits := instrctrs(X1222_instrctr).stalls
        accelUnit.io.argOuts(api.X1222_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1222_idle_arg).port.bits := instrctrs(X1222_instrctr).idles
        accelUnit.io.argOuts(api.X1222_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1241_cycles_arg).port.bits := instrctrs(X1241_instrctr).cycs
        accelUnit.io.argOuts(api.X1241_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1241_iters_arg).port.bits := instrctrs(X1241_instrctr).iters
        accelUnit.io.argOuts(api.X1241_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1241_stalled_arg).port.bits := instrctrs(X1241_instrctr).stalls
        accelUnit.io.argOuts(api.X1241_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1241_idle_arg).port.bits := instrctrs(X1241_instrctr).idles
        accelUnit.io.argOuts(api.X1241_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1246_cycles_arg).port.bits := instrctrs(X1246_instrctr).cycs
        accelUnit.io.argOuts(api.X1246_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1246_iters_arg).port.bits := instrctrs(X1246_instrctr).iters
        accelUnit.io.argOuts(api.X1246_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1246_stalled_arg).port.bits := instrctrs(X1246_instrctr).stalls
        accelUnit.io.argOuts(api.X1246_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X1246_idle_arg).port.bits := instrctrs(X1246_instrctr).idles
        accelUnit.io.argOuts(api.X1246_idle_arg).port.valid := accelUnit.io.enable
        Map[String,Any]()
      }
    }
    val block1chunk3: Map[String, Any] = Block1Chunker3.gen()
    val numArgOuts_breakpts = 0
  }
}
