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
    accelUnit.io.argOuts(api.X164_cycles_arg).port.bits := instrctrs(X164_instrctr).cycs
    accelUnit.io.argOuts(api.X164_cycles_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X164_iters_arg).port.bits := instrctrs(X164_instrctr).iters
    accelUnit.io.argOuts(api.X164_iters_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X236_cycles_arg).port.bits := instrctrs(X236_instrctr).cycs
    accelUnit.io.argOuts(api.X236_cycles_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X236_iters_arg).port.bits := instrctrs(X236_instrctr).iters
    accelUnit.io.argOuts(api.X236_iters_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X258_cycles_arg).port.bits := instrctrs(X258_instrctr).cycs
    accelUnit.io.argOuts(api.X258_cycles_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X258_iters_arg).port.bits := instrctrs(X258_instrctr).iters
    accelUnit.io.argOuts(api.X258_iters_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X244_cycles_arg).port.bits := instrctrs(X244_instrctr).cycs
    accelUnit.io.argOuts(api.X244_cycles_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X244_iters_arg).port.bits := instrctrs(X244_instrctr).iters
    accelUnit.io.argOuts(api.X244_iters_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X244_stalled_arg).port.bits := instrctrs(X244_instrctr).stalls
    accelUnit.io.argOuts(api.X244_stalled_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X244_idle_arg).port.bits := instrctrs(X244_instrctr).idles
    accelUnit.io.argOuts(api.X244_idle_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X253_cycles_arg).port.bits := instrctrs(X253_instrctr).cycs
    accelUnit.io.argOuts(api.X253_cycles_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X253_iters_arg).port.bits := instrctrs(X253_instrctr).iters
    accelUnit.io.argOuts(api.X253_iters_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X253_stalled_arg).port.bits := instrctrs(X253_instrctr).stalls
    accelUnit.io.argOuts(api.X253_stalled_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X253_idle_arg).port.bits := instrctrs(X253_instrctr).idles
    accelUnit.io.argOuts(api.X253_idle_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X257_cycles_arg).port.bits := instrctrs(X257_instrctr).cycs
    accelUnit.io.argOuts(api.X257_cycles_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X257_iters_arg).port.bits := instrctrs(X257_instrctr).iters
    accelUnit.io.argOuts(api.X257_iters_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X257_stalled_arg).port.bits := instrctrs(X257_instrctr).stalls
    accelUnit.io.argOuts(api.X257_stalled_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X257_idle_arg).port.bits := instrctrs(X257_instrctr).idles
    accelUnit.io.argOuts(api.X257_idle_arg).port.valid := accelUnit.io.enable
    val numArgOuts_breakpts = 0
  }
}
