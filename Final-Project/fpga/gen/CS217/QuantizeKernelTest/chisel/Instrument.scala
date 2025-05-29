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
        accelUnit.io.argOuts(api.X213_cycles_arg).port.bits := instrctrs(X213_instrctr).cycs
        accelUnit.io.argOuts(api.X213_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X213_iters_arg).port.bits := instrctrs(X213_instrctr).iters
        accelUnit.io.argOuts(api.X213_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X289_cycles_arg).port.bits := instrctrs(X289_instrctr).cycs
        accelUnit.io.argOuts(api.X289_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X289_iters_arg).port.bits := instrctrs(X289_instrctr).iters
        accelUnit.io.argOuts(api.X289_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X264_cycles_arg).port.bits := instrctrs(X264_instrctr).cycs
        accelUnit.io.argOuts(api.X264_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X264_iters_arg).port.bits := instrctrs(X264_instrctr).iters
        accelUnit.io.argOuts(api.X264_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X264_stalled_arg).port.bits := instrctrs(X264_instrctr).stalls
        accelUnit.io.argOuts(api.X264_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X264_idle_arg).port.bits := instrctrs(X264_instrctr).idles
        accelUnit.io.argOuts(api.X264_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X288_cycles_arg).port.bits := instrctrs(X288_instrctr).cycs
        accelUnit.io.argOuts(api.X288_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X288_iters_arg).port.bits := instrctrs(X288_instrctr).iters
        accelUnit.io.argOuts(api.X288_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X274_cycles_arg).port.bits := instrctrs(X274_instrctr).cycs
        accelUnit.io.argOuts(api.X274_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X274_iters_arg).port.bits := instrctrs(X274_instrctr).iters
        accelUnit.io.argOuts(api.X274_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X274_stalled_arg).port.bits := instrctrs(X274_instrctr).stalls
        accelUnit.io.argOuts(api.X274_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X274_idle_arg).port.bits := instrctrs(X274_instrctr).idles
        accelUnit.io.argOuts(api.X274_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X287_cycles_arg).port.bits := instrctrs(X287_instrctr).cycs
        accelUnit.io.argOuts(api.X287_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X287_iters_arg).port.bits := instrctrs(X287_instrctr).iters
        accelUnit.io.argOuts(api.X287_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X287_stalled_arg).port.bits := instrctrs(X287_instrctr).stalls
        accelUnit.io.argOuts(api.X287_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X287_idle_arg).port.bits := instrctrs(X287_instrctr).idles
        accelUnit.io.argOuts(api.X287_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X308_cycles_arg).port.bits := instrctrs(X308_instrctr).cycs
        accelUnit.io.argOuts(api.X308_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X308_iters_arg).port.bits := instrctrs(X308_instrctr).iters
        accelUnit.io.argOuts(api.X308_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X341_cycles_arg).port.bits := instrctrs(X341_instrctr).cycs
        accelUnit.io.argOuts(api.X341_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X341_iters_arg).port.bits := instrctrs(X341_instrctr).iters
        accelUnit.io.argOuts(api.X341_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X340_cycles_arg).port.bits := instrctrs(X340_instrctr).cycs
        accelUnit.io.argOuts(api.X340_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X340_iters_arg).port.bits := instrctrs(X340_instrctr).iters
        accelUnit.io.argOuts(api.X340_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X335_cycles_arg).port.bits := instrctrs(X335_instrctr).cycs
        Map[String,Any]()
      }
    }
    val block1chunk0: Map[String, Any] = Block1Chunker0.gen()
    object Block1Chunker1 { // 27 nodes, 27 weight
      def gen(): Map[String, Any] = {
        accelUnit.io.argOuts(api.X335_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X335_iters_arg).port.bits := instrctrs(X335_instrctr).iters
        accelUnit.io.argOuts(api.X335_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X320_cycles_arg).port.bits := instrctrs(X320_instrctr).cycs
        accelUnit.io.argOuts(api.X320_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X320_iters_arg).port.bits := instrctrs(X320_instrctr).iters
        accelUnit.io.argOuts(api.X320_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X320_stalled_arg).port.bits := instrctrs(X320_instrctr).stalls
        accelUnit.io.argOuts(api.X320_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X320_idle_arg).port.bits := instrctrs(X320_instrctr).idles
        accelUnit.io.argOuts(api.X320_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X334_cycles_arg).port.bits := instrctrs(X334_instrctr).cycs
        accelUnit.io.argOuts(api.X334_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X334_iters_arg).port.bits := instrctrs(X334_instrctr).iters
        accelUnit.io.argOuts(api.X334_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X334_stalled_arg).port.bits := instrctrs(X334_instrctr).stalls
        accelUnit.io.argOuts(api.X334_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X334_idle_arg).port.bits := instrctrs(X334_instrctr).idles
        accelUnit.io.argOuts(api.X334_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X339_cycles_arg).port.bits := instrctrs(X339_instrctr).cycs
        accelUnit.io.argOuts(api.X339_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X339_iters_arg).port.bits := instrctrs(X339_instrctr).iters
        accelUnit.io.argOuts(api.X339_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X339_stalled_arg).port.bits := instrctrs(X339_instrctr).stalls
        accelUnit.io.argOuts(api.X339_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X339_idle_arg).port.bits := instrctrs(X339_instrctr).idles
        accelUnit.io.argOuts(api.X339_idle_arg).port.valid := accelUnit.io.enable
        Map[String,Any]()
      }
    }
    val block1chunk1: Map[String, Any] = Block1Chunker1.gen()
    val numArgOuts_breakpts = 0
  }
}
