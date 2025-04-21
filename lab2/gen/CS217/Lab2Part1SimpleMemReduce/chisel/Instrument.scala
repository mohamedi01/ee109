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
    accelUnit.io.argOuts(api.X119_cycles_arg).port.bits := instrctrs(X119_instrctr).cycs
    accelUnit.io.argOuts(api.X119_cycles_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X119_iters_arg).port.bits := instrctrs(X119_instrctr).iters
    accelUnit.io.argOuts(api.X119_iters_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X187_cycles_arg).port.bits := instrctrs(X187_instrctr).cycs
    accelUnit.io.argOuts(api.X187_cycles_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X187_iters_arg).port.bits := instrctrs(X187_instrctr).iters
    accelUnit.io.argOuts(api.X187_iters_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X176_cycles_arg).port.bits := instrctrs(X176_instrctr).cycs
    accelUnit.io.argOuts(api.X176_cycles_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X176_iters_arg).port.bits := instrctrs(X176_instrctr).iters
    accelUnit.io.argOuts(api.X176_iters_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X186_cycles_arg).port.bits := instrctrs(X186_instrctr).cycs
    accelUnit.io.argOuts(api.X186_cycles_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X186_iters_arg).port.bits := instrctrs(X186_instrctr).iters
    accelUnit.io.argOuts(api.X186_iters_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X209_cycles_arg).port.bits := instrctrs(X209_instrctr).cycs
    accelUnit.io.argOuts(api.X209_cycles_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X209_iters_arg).port.bits := instrctrs(X209_instrctr).iters
    accelUnit.io.argOuts(api.X209_iters_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X195_cycles_arg).port.bits := instrctrs(X195_instrctr).cycs
    accelUnit.io.argOuts(api.X195_cycles_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X195_iters_arg).port.bits := instrctrs(X195_instrctr).iters
    accelUnit.io.argOuts(api.X195_iters_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X195_stalled_arg).port.bits := instrctrs(X195_instrctr).stalls
    accelUnit.io.argOuts(api.X195_stalled_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X195_idle_arg).port.bits := instrctrs(X195_instrctr).idles
    accelUnit.io.argOuts(api.X195_idle_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X204_cycles_arg).port.bits := instrctrs(X204_instrctr).cycs
    accelUnit.io.argOuts(api.X204_cycles_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X204_iters_arg).port.bits := instrctrs(X204_instrctr).iters
    accelUnit.io.argOuts(api.X204_iters_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X204_stalled_arg).port.bits := instrctrs(X204_instrctr).stalls
    accelUnit.io.argOuts(api.X204_stalled_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X204_idle_arg).port.bits := instrctrs(X204_instrctr).idles
    accelUnit.io.argOuts(api.X204_idle_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X208_cycles_arg).port.bits := instrctrs(X208_instrctr).cycs
    accelUnit.io.argOuts(api.X208_cycles_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X208_iters_arg).port.bits := instrctrs(X208_instrctr).iters
    accelUnit.io.argOuts(api.X208_iters_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X208_stalled_arg).port.bits := instrctrs(X208_instrctr).stalls
    accelUnit.io.argOuts(api.X208_stalled_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X208_idle_arg).port.bits := instrctrs(X208_instrctr).idles
    accelUnit.io.argOuts(api.X208_idle_arg).port.valid := accelUnit.io.enable
    val numArgOuts_breakpts = 0
  }
}
