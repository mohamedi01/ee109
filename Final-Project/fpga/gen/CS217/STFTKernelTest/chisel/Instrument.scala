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
        accelUnit.io.argOuts(api.X710_cycles_arg).port.bits := instrctrs(X710_instrctr).cycs
        accelUnit.io.argOuts(api.X710_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X710_iters_arg).port.bits := instrctrs(X710_instrctr).iters
        accelUnit.io.argOuts(api.X710_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X708_cycles_arg).port.bits := instrctrs(X708_instrctr).cycs
        accelUnit.io.argOuts(api.X708_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X708_iters_arg).port.bits := instrctrs(X708_instrctr).iters
        accelUnit.io.argOuts(api.X708_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X505_cycles_arg).port.bits := instrctrs(X505_instrctr).cycs
        accelUnit.io.argOuts(api.X505_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X505_iters_arg).port.bits := instrctrs(X505_instrctr).iters
        accelUnit.io.argOuts(api.X505_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X480_cycles_arg).port.bits := instrctrs(X480_instrctr).cycs
        accelUnit.io.argOuts(api.X480_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X480_iters_arg).port.bits := instrctrs(X480_instrctr).iters
        accelUnit.io.argOuts(api.X480_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X480_stalled_arg).port.bits := instrctrs(X480_instrctr).stalls
        accelUnit.io.argOuts(api.X480_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X480_idle_arg).port.bits := instrctrs(X480_instrctr).idles
        accelUnit.io.argOuts(api.X480_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X504_cycles_arg).port.bits := instrctrs(X504_instrctr).cycs
        accelUnit.io.argOuts(api.X504_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X504_iters_arg).port.bits := instrctrs(X504_instrctr).iters
        accelUnit.io.argOuts(api.X504_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X490_cycles_arg).port.bits := instrctrs(X490_instrctr).cycs
        accelUnit.io.argOuts(api.X490_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X490_iters_arg).port.bits := instrctrs(X490_instrctr).iters
        accelUnit.io.argOuts(api.X490_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X490_stalled_arg).port.bits := instrctrs(X490_instrctr).stalls
        accelUnit.io.argOuts(api.X490_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X490_idle_arg).port.bits := instrctrs(X490_instrctr).idles
        accelUnit.io.argOuts(api.X490_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X503_cycles_arg).port.bits := instrctrs(X503_instrctr).cycs
        accelUnit.io.argOuts(api.X503_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X503_iters_arg).port.bits := instrctrs(X503_instrctr).iters
        accelUnit.io.argOuts(api.X503_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X503_stalled_arg).port.bits := instrctrs(X503_instrctr).stalls
        accelUnit.io.argOuts(api.X503_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X503_idle_arg).port.bits := instrctrs(X503_instrctr).idles
        accelUnit.io.argOuts(api.X503_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X511_cycles_arg).port.bits := instrctrs(X511_instrctr).cycs
        accelUnit.io.argOuts(api.X511_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X511_iters_arg).port.bits := instrctrs(X511_instrctr).iters
        accelUnit.io.argOuts(api.X511_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X552_cycles_arg).port.bits := instrctrs(X552_instrctr).cycs
        accelUnit.io.argOuts(api.X552_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X552_iters_arg).port.bits := instrctrs(X552_instrctr).iters
        accelUnit.io.argOuts(api.X552_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X528_cycles_arg).port.bits := instrctrs(X528_instrctr).cycs
        Map[String,Any]()
      }
    }
    val block1chunk0: Map[String, Any] = Block1Chunker0.gen()
    object Block1Chunker1 { // 49 nodes, 49 weight
      def gen(): Map[String, Any] = {
        accelUnit.io.argOuts(api.X528_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X528_iters_arg).port.bits := instrctrs(X528_instrctr).iters
        accelUnit.io.argOuts(api.X528_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X537_cycles_arg).port.bits := instrctrs(X537_instrctr).cycs
        accelUnit.io.argOuts(api.X537_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X537_iters_arg).port.bits := instrctrs(X537_instrctr).iters
        accelUnit.io.argOuts(api.X537_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X551_cycles_arg).port.bits := instrctrs(X551_instrctr).cycs
        accelUnit.io.argOuts(api.X551_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X551_iters_arg).port.bits := instrctrs(X551_instrctr).iters
        accelUnit.io.argOuts(api.X551_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X709_cycles_arg).port.bits := instrctrs(X709_instrctr).cycs
        accelUnit.io.argOuts(api.X709_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X709_iters_arg).port.bits := instrctrs(X709_instrctr).iters
        accelUnit.io.argOuts(api.X709_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X608_cycles_arg).port.bits := instrctrs(X608_instrctr).cycs
        accelUnit.io.argOuts(api.X608_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X608_iters_arg).port.bits := instrctrs(X608_instrctr).iters
        accelUnit.io.argOuts(api.X608_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X607_cycles_arg).port.bits := instrctrs(X607_instrctr).cycs
        accelUnit.io.argOuts(api.X607_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X607_iters_arg).port.bits := instrctrs(X607_instrctr).iters
        accelUnit.io.argOuts(api.X607_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X602_cycles_arg).port.bits := instrctrs(X602_instrctr).cycs
        accelUnit.io.argOuts(api.X602_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X602_iters_arg).port.bits := instrctrs(X602_instrctr).iters
        accelUnit.io.argOuts(api.X602_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X582_cycles_arg).port.bits := instrctrs(X582_instrctr).cycs
        accelUnit.io.argOuts(api.X582_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X582_iters_arg).port.bits := instrctrs(X582_instrctr).iters
        accelUnit.io.argOuts(api.X582_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X582_stalled_arg).port.bits := instrctrs(X582_instrctr).stalls
        accelUnit.io.argOuts(api.X582_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X582_idle_arg).port.bits := instrctrs(X582_instrctr).idles
        accelUnit.io.argOuts(api.X582_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X601_cycles_arg).port.bits := instrctrs(X601_instrctr).cycs
        accelUnit.io.argOuts(api.X601_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X601_iters_arg).port.bits := instrctrs(X601_instrctr).iters
        accelUnit.io.argOuts(api.X601_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X601_stalled_arg).port.bits := instrctrs(X601_instrctr).stalls
        accelUnit.io.argOuts(api.X601_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X601_idle_arg).port.bits := instrctrs(X601_instrctr).idles
        accelUnit.io.argOuts(api.X601_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X606_cycles_arg).port.bits := instrctrs(X606_instrctr).cycs
        accelUnit.io.argOuts(api.X606_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X606_iters_arg).port.bits := instrctrs(X606_instrctr).iters
        accelUnit.io.argOuts(api.X606_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X606_stalled_arg).port.bits := instrctrs(X606_instrctr).stalls
        accelUnit.io.argOuts(api.X606_stalled_arg).port.valid := accelUnit.io.enable
        Map[String,Any]()
      }
    }
    val block1chunk1: Map[String, Any] = Block1Chunker1.gen()
    object Block1Chunker2 { // 38 nodes, 38 weight
      def gen(): Map[String, Any] = {
        accelUnit.io.argOuts(api.X606_idle_arg).port.bits := instrctrs(X606_instrctr).idles
        accelUnit.io.argOuts(api.X606_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X664_cycles_arg).port.bits := instrctrs(X664_instrctr).cycs
        accelUnit.io.argOuts(api.X664_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X664_iters_arg).port.bits := instrctrs(X664_instrctr).iters
        accelUnit.io.argOuts(api.X664_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X663_cycles_arg).port.bits := instrctrs(X663_instrctr).cycs
        accelUnit.io.argOuts(api.X663_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X663_iters_arg).port.bits := instrctrs(X663_instrctr).iters
        accelUnit.io.argOuts(api.X663_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X658_cycles_arg).port.bits := instrctrs(X658_instrctr).cycs
        accelUnit.io.argOuts(api.X658_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X658_iters_arg).port.bits := instrctrs(X658_instrctr).iters
        accelUnit.io.argOuts(api.X658_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X638_cycles_arg).port.bits := instrctrs(X638_instrctr).cycs
        accelUnit.io.argOuts(api.X638_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X638_iters_arg).port.bits := instrctrs(X638_instrctr).iters
        accelUnit.io.argOuts(api.X638_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X638_stalled_arg).port.bits := instrctrs(X638_instrctr).stalls
        accelUnit.io.argOuts(api.X638_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X638_idle_arg).port.bits := instrctrs(X638_instrctr).idles
        accelUnit.io.argOuts(api.X638_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X657_cycles_arg).port.bits := instrctrs(X657_instrctr).cycs
        accelUnit.io.argOuts(api.X657_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X657_iters_arg).port.bits := instrctrs(X657_instrctr).iters
        accelUnit.io.argOuts(api.X657_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X657_stalled_arg).port.bits := instrctrs(X657_instrctr).stalls
        accelUnit.io.argOuts(api.X657_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X657_idle_arg).port.bits := instrctrs(X657_instrctr).idles
        accelUnit.io.argOuts(api.X657_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X662_cycles_arg).port.bits := instrctrs(X662_instrctr).cycs
        accelUnit.io.argOuts(api.X662_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X662_iters_arg).port.bits := instrctrs(X662_instrctr).iters
        accelUnit.io.argOuts(api.X662_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X662_stalled_arg).port.bits := instrctrs(X662_instrctr).stalls
        accelUnit.io.argOuts(api.X662_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X662_idle_arg).port.bits := instrctrs(X662_instrctr).idles
        accelUnit.io.argOuts(api.X662_idle_arg).port.valid := accelUnit.io.enable
        Map[String,Any]()
      }
    }
    val block1chunk2: Map[String, Any] = Block1Chunker2.gen()
    val numArgOuts_breakpts = 0
  }
}
