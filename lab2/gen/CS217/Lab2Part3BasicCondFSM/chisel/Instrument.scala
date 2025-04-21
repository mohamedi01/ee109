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
    accelUnit.io.argOuts(api.X168_cycles_arg).port.bits := instrctrs(X168_instrctr).cycs
    accelUnit.io.argOuts(api.X168_cycles_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X168_iters_arg).port.bits := instrctrs(X168_instrctr).iters
    accelUnit.io.argOuts(api.X168_iters_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X214_cycles_arg).port.bits := instrctrs(X214_instrctr).cycs
    accelUnit.io.argOuts(api.X214_cycles_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X214_iters_arg).port.bits := instrctrs(X214_instrctr).iters
    accelUnit.io.argOuts(api.X214_iters_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X245_cycles_arg).port.bits := instrctrs(X245_instrctr).cycs
    accelUnit.io.argOuts(api.X245_cycles_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X245_iters_arg).port.bits := instrctrs(X245_instrctr).iters
    accelUnit.io.argOuts(api.X245_iters_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X267_cycles_arg).port.bits := instrctrs(X267_instrctr).cycs
    accelUnit.io.argOuts(api.X267_cycles_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X267_iters_arg).port.bits := instrctrs(X267_instrctr).iters
    accelUnit.io.argOuts(api.X267_iters_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X253_cycles_arg).port.bits := instrctrs(X253_instrctr).cycs
    accelUnit.io.argOuts(api.X253_cycles_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X253_iters_arg).port.bits := instrctrs(X253_instrctr).iters
    accelUnit.io.argOuts(api.X253_iters_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X253_stalled_arg).port.bits := instrctrs(X253_instrctr).stalls
    accelUnit.io.argOuts(api.X253_stalled_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X253_idle_arg).port.bits := instrctrs(X253_instrctr).idles
    accelUnit.io.argOuts(api.X253_idle_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X262_cycles_arg).port.bits := instrctrs(X262_instrctr).cycs
    accelUnit.io.argOuts(api.X262_cycles_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X262_iters_arg).port.bits := instrctrs(X262_instrctr).iters
    accelUnit.io.argOuts(api.X262_iters_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X262_stalled_arg).port.bits := instrctrs(X262_instrctr).stalls
    accelUnit.io.argOuts(api.X262_stalled_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X262_idle_arg).port.bits := instrctrs(X262_instrctr).idles
    accelUnit.io.argOuts(api.X262_idle_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X266_cycles_arg).port.bits := instrctrs(X266_instrctr).cycs
    accelUnit.io.argOuts(api.X266_cycles_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X266_iters_arg).port.bits := instrctrs(X266_instrctr).iters
    accelUnit.io.argOuts(api.X266_iters_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X266_stalled_arg).port.bits := instrctrs(X266_instrctr).stalls
    accelUnit.io.argOuts(api.X266_stalled_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X266_idle_arg).port.bits := instrctrs(X266_instrctr).idles
    accelUnit.io.argOuts(api.X266_idle_arg).port.valid := accelUnit.io.enable
    val numArgOuts_breakpts = 0
  }
}
