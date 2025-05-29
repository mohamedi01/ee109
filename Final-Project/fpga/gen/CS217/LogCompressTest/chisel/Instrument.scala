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
        accelUnit.io.argOuts(api.X439_cycles_arg).port.bits := instrctrs(X439_instrctr).cycs
        accelUnit.io.argOuts(api.X439_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X439_iters_arg).port.bits := instrctrs(X439_instrctr).iters
        accelUnit.io.argOuts(api.X439_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X330_cycles_arg).port.bits := instrctrs(X330_instrctr).cycs
        accelUnit.io.argOuts(api.X330_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X330_iters_arg).port.bits := instrctrs(X330_instrctr).iters
        accelUnit.io.argOuts(api.X330_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X305_cycles_arg).port.bits := instrctrs(X305_instrctr).cycs
        accelUnit.io.argOuts(api.X305_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X305_iters_arg).port.bits := instrctrs(X305_instrctr).iters
        accelUnit.io.argOuts(api.X305_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X305_stalled_arg).port.bits := instrctrs(X305_instrctr).stalls
        accelUnit.io.argOuts(api.X305_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X305_idle_arg).port.bits := instrctrs(X305_instrctr).idles
        accelUnit.io.argOuts(api.X305_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X329_cycles_arg).port.bits := instrctrs(X329_instrctr).cycs
        accelUnit.io.argOuts(api.X329_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X329_iters_arg).port.bits := instrctrs(X329_instrctr).iters
        accelUnit.io.argOuts(api.X329_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X315_cycles_arg).port.bits := instrctrs(X315_instrctr).cycs
        accelUnit.io.argOuts(api.X315_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X315_iters_arg).port.bits := instrctrs(X315_instrctr).iters
        accelUnit.io.argOuts(api.X315_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X315_stalled_arg).port.bits := instrctrs(X315_instrctr).stalls
        accelUnit.io.argOuts(api.X315_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X315_idle_arg).port.bits := instrctrs(X315_instrctr).idles
        accelUnit.io.argOuts(api.X315_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X328_cycles_arg).port.bits := instrctrs(X328_instrctr).cycs
        accelUnit.io.argOuts(api.X328_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X328_iters_arg).port.bits := instrctrs(X328_instrctr).iters
        accelUnit.io.argOuts(api.X328_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X328_stalled_arg).port.bits := instrctrs(X328_instrctr).stalls
        accelUnit.io.argOuts(api.X328_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X328_idle_arg).port.bits := instrctrs(X328_instrctr).idles
        accelUnit.io.argOuts(api.X328_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X343_cycles_arg).port.bits := instrctrs(X343_instrctr).cycs
        accelUnit.io.argOuts(api.X343_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X343_iters_arg).port.bits := instrctrs(X343_instrctr).iters
        accelUnit.io.argOuts(api.X343_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X438_cycles_arg).port.bits := instrctrs(X438_instrctr).cycs
        accelUnit.io.argOuts(api.X438_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X438_iters_arg).port.bits := instrctrs(X438_instrctr).iters
        accelUnit.io.argOuts(api.X438_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X354_cycles_arg).port.bits := instrctrs(X354_instrctr).cycs
        accelUnit.io.argOuts(api.X354_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X354_iters_arg).port.bits := instrctrs(X354_instrctr).iters
        accelUnit.io.argOuts(api.X354_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X366_cycles_arg).port.bits := instrctrs(X366_instrctr).cycs
        Map[String,Any]()
      }
    }
    val block1chunk0: Map[String, Any] = Block1Chunker0.gen()
    object Block1Chunker1 { // 39 nodes, 39 weight
      def gen(): Map[String, Any] = {
        accelUnit.io.argOuts(api.X366_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X366_iters_arg).port.bits := instrctrs(X366_instrctr).iters
        accelUnit.io.argOuts(api.X366_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X399_cycles_arg).port.bits := instrctrs(X399_instrctr).cycs
        accelUnit.io.argOuts(api.X399_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X399_iters_arg).port.bits := instrctrs(X399_instrctr).iters
        accelUnit.io.argOuts(api.X399_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X398_cycles_arg).port.bits := instrctrs(X398_instrctr).cycs
        accelUnit.io.argOuts(api.X398_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X398_iters_arg).port.bits := instrctrs(X398_instrctr).iters
        accelUnit.io.argOuts(api.X398_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X393_cycles_arg).port.bits := instrctrs(X393_instrctr).cycs
        accelUnit.io.argOuts(api.X393_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X393_iters_arg).port.bits := instrctrs(X393_instrctr).iters
        accelUnit.io.argOuts(api.X393_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X378_cycles_arg).port.bits := instrctrs(X378_instrctr).cycs
        accelUnit.io.argOuts(api.X378_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X378_iters_arg).port.bits := instrctrs(X378_instrctr).iters
        accelUnit.io.argOuts(api.X378_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X378_stalled_arg).port.bits := instrctrs(X378_instrctr).stalls
        accelUnit.io.argOuts(api.X378_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X378_idle_arg).port.bits := instrctrs(X378_instrctr).idles
        accelUnit.io.argOuts(api.X378_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X392_cycles_arg).port.bits := instrctrs(X392_instrctr).cycs
        accelUnit.io.argOuts(api.X392_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X392_iters_arg).port.bits := instrctrs(X392_instrctr).iters
        accelUnit.io.argOuts(api.X392_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X392_stalled_arg).port.bits := instrctrs(X392_instrctr).stalls
        accelUnit.io.argOuts(api.X392_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X392_idle_arg).port.bits := instrctrs(X392_instrctr).idles
        accelUnit.io.argOuts(api.X392_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X397_cycles_arg).port.bits := instrctrs(X397_instrctr).cycs
        accelUnit.io.argOuts(api.X397_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X397_iters_arg).port.bits := instrctrs(X397_instrctr).iters
        accelUnit.io.argOuts(api.X397_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X397_stalled_arg).port.bits := instrctrs(X397_instrctr).stalls
        accelUnit.io.argOuts(api.X397_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X397_idle_arg).port.bits := instrctrs(X397_instrctr).idles
        accelUnit.io.argOuts(api.X397_idle_arg).port.valid := accelUnit.io.enable
        Map[String,Any]()
      }
    }
    val block1chunk1: Map[String, Any] = Block1Chunker1.gen()
    val numArgOuts_breakpts = 0
  }
}
