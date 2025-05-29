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
        accelUnit.io.argOuts(api.X614_cycles_arg).port.bits := instrctrs(X614_instrctr).cycs
        accelUnit.io.argOuts(api.X614_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X614_iters_arg).port.bits := instrctrs(X614_instrctr).iters
        accelUnit.io.argOuts(api.X614_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X613_cycles_arg).port.bits := instrctrs(X613_instrctr).cycs
        accelUnit.io.argOuts(api.X613_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X613_iters_arg).port.bits := instrctrs(X613_instrctr).iters
        accelUnit.io.argOuts(api.X613_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X476_cycles_arg).port.bits := instrctrs(X476_instrctr).cycs
        accelUnit.io.argOuts(api.X476_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X476_iters_arg).port.bits := instrctrs(X476_instrctr).iters
        accelUnit.io.argOuts(api.X476_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X439_cycles_arg).port.bits := instrctrs(X439_instrctr).cycs
        accelUnit.io.argOuts(api.X439_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X439_iters_arg).port.bits := instrctrs(X439_instrctr).iters
        accelUnit.io.argOuts(api.X439_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X439_stalled_arg).port.bits := instrctrs(X439_instrctr).stalls
        accelUnit.io.argOuts(api.X439_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X439_idle_arg).port.bits := instrctrs(X439_instrctr).idles
        accelUnit.io.argOuts(api.X439_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X475_cycles_arg).port.bits := instrctrs(X475_instrctr).cycs
        accelUnit.io.argOuts(api.X475_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X475_iters_arg).port.bits := instrctrs(X475_instrctr).iters
        accelUnit.io.argOuts(api.X475_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X456_cycles_arg).port.bits := instrctrs(X456_instrctr).cycs
        accelUnit.io.argOuts(api.X456_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X456_iters_arg).port.bits := instrctrs(X456_instrctr).iters
        accelUnit.io.argOuts(api.X456_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X456_stalled_arg).port.bits := instrctrs(X456_instrctr).stalls
        accelUnit.io.argOuts(api.X456_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X456_idle_arg).port.bits := instrctrs(X456_instrctr).idles
        accelUnit.io.argOuts(api.X456_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X474_cycles_arg).port.bits := instrctrs(X474_instrctr).cycs
        accelUnit.io.argOuts(api.X474_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X474_iters_arg).port.bits := instrctrs(X474_instrctr).iters
        accelUnit.io.argOuts(api.X474_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X474_stalled_arg).port.bits := instrctrs(X474_instrctr).stalls
        accelUnit.io.argOuts(api.X474_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X474_idle_arg).port.bits := instrctrs(X474_instrctr).idles
        accelUnit.io.argOuts(api.X474_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X511_cycles_arg).port.bits := instrctrs(X511_instrctr).cycs
        accelUnit.io.argOuts(api.X511_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X511_iters_arg).port.bits := instrctrs(X511_instrctr).iters
        accelUnit.io.argOuts(api.X511_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X486_cycles_arg).port.bits := instrctrs(X486_instrctr).cycs
        accelUnit.io.argOuts(api.X486_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X486_iters_arg).port.bits := instrctrs(X486_instrctr).iters
        accelUnit.io.argOuts(api.X486_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X486_stalled_arg).port.bits := instrctrs(X486_instrctr).stalls
        Map[String,Any]()
      }
    }
    val block1chunk0: Map[String, Any] = Block1Chunker0.gen()
    object Block1Chunker1 { // 49 nodes, 49 weight
      def gen(): Map[String, Any] = {
        accelUnit.io.argOuts(api.X486_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X486_idle_arg).port.bits := instrctrs(X486_instrctr).idles
        accelUnit.io.argOuts(api.X486_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X510_cycles_arg).port.bits := instrctrs(X510_instrctr).cycs
        accelUnit.io.argOuts(api.X510_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X510_iters_arg).port.bits := instrctrs(X510_instrctr).iters
        accelUnit.io.argOuts(api.X510_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X496_cycles_arg).port.bits := instrctrs(X496_instrctr).cycs
        accelUnit.io.argOuts(api.X496_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X496_iters_arg).port.bits := instrctrs(X496_instrctr).iters
        accelUnit.io.argOuts(api.X496_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X496_stalled_arg).port.bits := instrctrs(X496_instrctr).stalls
        accelUnit.io.argOuts(api.X496_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X496_idle_arg).port.bits := instrctrs(X496_instrctr).idles
        accelUnit.io.argOuts(api.X496_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X509_cycles_arg).port.bits := instrctrs(X509_instrctr).cycs
        accelUnit.io.argOuts(api.X509_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X509_iters_arg).port.bits := instrctrs(X509_instrctr).iters
        accelUnit.io.argOuts(api.X509_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X509_stalled_arg).port.bits := instrctrs(X509_instrctr).stalls
        accelUnit.io.argOuts(api.X509_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X509_idle_arg).port.bits := instrctrs(X509_instrctr).idles
        accelUnit.io.argOuts(api.X509_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X541_cycles_arg).port.bits := instrctrs(X541_instrctr).cycs
        accelUnit.io.argOuts(api.X541_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X541_iters_arg).port.bits := instrctrs(X541_instrctr).iters
        accelUnit.io.argOuts(api.X541_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X537_cycles_arg).port.bits := instrctrs(X537_instrctr).cycs
        accelUnit.io.argOuts(api.X537_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X537_iters_arg).port.bits := instrctrs(X537_instrctr).iters
        accelUnit.io.argOuts(api.X537_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X540_cycles_arg).port.bits := instrctrs(X540_instrctr).cycs
        accelUnit.io.argOuts(api.X540_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X540_iters_arg).port.bits := instrctrs(X540_instrctr).iters
        accelUnit.io.argOuts(api.X540_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X574_cycles_arg).port.bits := instrctrs(X574_instrctr).cycs
        accelUnit.io.argOuts(api.X574_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X574_iters_arg).port.bits := instrctrs(X574_instrctr).iters
        accelUnit.io.argOuts(api.X574_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X573_cycles_arg).port.bits := instrctrs(X573_instrctr).cycs
        accelUnit.io.argOuts(api.X573_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X573_iters_arg).port.bits := instrctrs(X573_instrctr).iters
        accelUnit.io.argOuts(api.X573_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X568_cycles_arg).port.bits := instrctrs(X568_instrctr).cycs
        accelUnit.io.argOuts(api.X568_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X568_iters_arg).port.bits := instrctrs(X568_instrctr).iters
        accelUnit.io.argOuts(api.X568_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X553_cycles_arg).port.bits := instrctrs(X553_instrctr).cycs
        accelUnit.io.argOuts(api.X553_cycles_arg).port.valid := accelUnit.io.enable
        Map[String,Any]()
      }
    }
    val block1chunk1: Map[String, Any] = Block1Chunker1.gen()
    object Block1Chunker2 { // 22 nodes, 22 weight
      def gen(): Map[String, Any] = {
        accelUnit.io.argOuts(api.X553_iters_arg).port.bits := instrctrs(X553_instrctr).iters
        accelUnit.io.argOuts(api.X553_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X553_stalled_arg).port.bits := instrctrs(X553_instrctr).stalls
        accelUnit.io.argOuts(api.X553_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X553_idle_arg).port.bits := instrctrs(X553_instrctr).idles
        accelUnit.io.argOuts(api.X553_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X567_cycles_arg).port.bits := instrctrs(X567_instrctr).cycs
        accelUnit.io.argOuts(api.X567_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X567_iters_arg).port.bits := instrctrs(X567_instrctr).iters
        accelUnit.io.argOuts(api.X567_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X567_stalled_arg).port.bits := instrctrs(X567_instrctr).stalls
        accelUnit.io.argOuts(api.X567_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X567_idle_arg).port.bits := instrctrs(X567_instrctr).idles
        accelUnit.io.argOuts(api.X567_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X572_cycles_arg).port.bits := instrctrs(X572_instrctr).cycs
        accelUnit.io.argOuts(api.X572_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X572_iters_arg).port.bits := instrctrs(X572_instrctr).iters
        accelUnit.io.argOuts(api.X572_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X572_stalled_arg).port.bits := instrctrs(X572_instrctr).stalls
        accelUnit.io.argOuts(api.X572_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X572_idle_arg).port.bits := instrctrs(X572_instrctr).idles
        accelUnit.io.argOuts(api.X572_idle_arg).port.valid := accelUnit.io.enable
        Map[String,Any]()
      }
    }
    val block1chunk2: Map[String, Any] = Block1Chunker2.gen()
    val numArgOuts_breakpts = 0
  }
}
