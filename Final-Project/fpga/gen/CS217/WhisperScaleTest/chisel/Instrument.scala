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
        accelUnit.io.argOuts(api.X201_cycles_arg).port.bits := instrctrs(X201_instrctr).cycs
        accelUnit.io.argOuts(api.X201_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X201_iters_arg).port.bits := instrctrs(X201_instrctr).iters
        accelUnit.io.argOuts(api.X201_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X288_cycles_arg).port.bits := instrctrs(X288_instrctr).cycs
        accelUnit.io.argOuts(api.X288_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X288_iters_arg).port.bits := instrctrs(X288_instrctr).iters
        accelUnit.io.argOuts(api.X288_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X263_cycles_arg).port.bits := instrctrs(X263_instrctr).cycs
        accelUnit.io.argOuts(api.X263_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X263_iters_arg).port.bits := instrctrs(X263_instrctr).iters
        accelUnit.io.argOuts(api.X263_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X263_stalled_arg).port.bits := instrctrs(X263_instrctr).stalls
        accelUnit.io.argOuts(api.X263_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X263_idle_arg).port.bits := instrctrs(X263_instrctr).idles
        accelUnit.io.argOuts(api.X263_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X287_cycles_arg).port.bits := instrctrs(X287_instrctr).cycs
        accelUnit.io.argOuts(api.X287_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X287_iters_arg).port.bits := instrctrs(X287_instrctr).iters
        accelUnit.io.argOuts(api.X287_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X273_cycles_arg).port.bits := instrctrs(X273_instrctr).cycs
        accelUnit.io.argOuts(api.X273_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X273_iters_arg).port.bits := instrctrs(X273_instrctr).iters
        accelUnit.io.argOuts(api.X273_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X273_stalled_arg).port.bits := instrctrs(X273_instrctr).stalls
        accelUnit.io.argOuts(api.X273_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X273_idle_arg).port.bits := instrctrs(X273_instrctr).idles
        accelUnit.io.argOuts(api.X273_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X286_cycles_arg).port.bits := instrctrs(X286_instrctr).cycs
        accelUnit.io.argOuts(api.X286_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X286_iters_arg).port.bits := instrctrs(X286_instrctr).iters
        accelUnit.io.argOuts(api.X286_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X286_stalled_arg).port.bits := instrctrs(X286_instrctr).stalls
        accelUnit.io.argOuts(api.X286_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X286_idle_arg).port.bits := instrctrs(X286_instrctr).idles
        accelUnit.io.argOuts(api.X286_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X298_cycles_arg).port.bits := instrctrs(X298_instrctr).cycs
        accelUnit.io.argOuts(api.X298_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X298_iters_arg).port.bits := instrctrs(X298_instrctr).iters
        accelUnit.io.argOuts(api.X298_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X331_cycles_arg).port.bits := instrctrs(X331_instrctr).cycs
        accelUnit.io.argOuts(api.X331_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X331_iters_arg).port.bits := instrctrs(X331_instrctr).iters
        accelUnit.io.argOuts(api.X331_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X330_cycles_arg).port.bits := instrctrs(X330_instrctr).cycs
        accelUnit.io.argOuts(api.X330_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X330_iters_arg).port.bits := instrctrs(X330_instrctr).iters
        accelUnit.io.argOuts(api.X330_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X325_cycles_arg).port.bits := instrctrs(X325_instrctr).cycs
        Map[String,Any]()
      }
    }
    val block1chunk0: Map[String, Any] = Block1Chunker0.gen()
    object Block1Chunker1 { // 27 nodes, 27 weight
      def gen(): Map[String, Any] = {
        accelUnit.io.argOuts(api.X325_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X325_iters_arg).port.bits := instrctrs(X325_instrctr).iters
        accelUnit.io.argOuts(api.X325_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X310_cycles_arg).port.bits := instrctrs(X310_instrctr).cycs
        accelUnit.io.argOuts(api.X310_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X310_iters_arg).port.bits := instrctrs(X310_instrctr).iters
        accelUnit.io.argOuts(api.X310_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X310_stalled_arg).port.bits := instrctrs(X310_instrctr).stalls
        accelUnit.io.argOuts(api.X310_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X310_idle_arg).port.bits := instrctrs(X310_instrctr).idles
        accelUnit.io.argOuts(api.X310_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X324_cycles_arg).port.bits := instrctrs(X324_instrctr).cycs
        accelUnit.io.argOuts(api.X324_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X324_iters_arg).port.bits := instrctrs(X324_instrctr).iters
        accelUnit.io.argOuts(api.X324_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X324_stalled_arg).port.bits := instrctrs(X324_instrctr).stalls
        accelUnit.io.argOuts(api.X324_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X324_idle_arg).port.bits := instrctrs(X324_instrctr).idles
        accelUnit.io.argOuts(api.X324_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X329_cycles_arg).port.bits := instrctrs(X329_instrctr).cycs
        accelUnit.io.argOuts(api.X329_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X329_iters_arg).port.bits := instrctrs(X329_instrctr).iters
        accelUnit.io.argOuts(api.X329_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X329_stalled_arg).port.bits := instrctrs(X329_instrctr).stalls
        accelUnit.io.argOuts(api.X329_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X329_idle_arg).port.bits := instrctrs(X329_instrctr).idles
        accelUnit.io.argOuts(api.X329_idle_arg).port.valid := accelUnit.io.enable
        Map[String,Any]()
      }
    }
    val block1chunk1: Map[String, Any] = Block1Chunker1.gen()
    val numArgOuts_breakpts = 0
  }
}
