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
    accelUnit.io.argOuts(api.X186_cycles_arg).port.bits := instrctrs(X186_instrctr).cycs
    accelUnit.io.argOuts(api.X186_cycles_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X186_iters_arg).port.bits := instrctrs(X186_instrctr).iters
    accelUnit.io.argOuts(api.X186_iters_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X176_cycles_arg).port.bits := instrctrs(X176_instrctr).cycs
    accelUnit.io.argOuts(api.X176_cycles_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X176_iters_arg).port.bits := instrctrs(X176_instrctr).iters
    accelUnit.io.argOuts(api.X176_iters_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X185_cycles_arg).port.bits := instrctrs(X185_instrctr).cycs
    accelUnit.io.argOuts(api.X185_cycles_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X185_iters_arg).port.bits := instrctrs(X185_instrctr).iters
    accelUnit.io.argOuts(api.X185_iters_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X208_cycles_arg).port.bits := instrctrs(X208_instrctr).cycs
    accelUnit.io.argOuts(api.X208_cycles_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X208_iters_arg).port.bits := instrctrs(X208_instrctr).iters
    accelUnit.io.argOuts(api.X208_iters_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X194_cycles_arg).port.bits := instrctrs(X194_instrctr).cycs
    accelUnit.io.argOuts(api.X194_cycles_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X194_iters_arg).port.bits := instrctrs(X194_instrctr).iters
    accelUnit.io.argOuts(api.X194_iters_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X194_stalled_arg).port.bits := instrctrs(X194_instrctr).stalls
    accelUnit.io.argOuts(api.X194_stalled_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X194_idle_arg).port.bits := instrctrs(X194_instrctr).idles
    accelUnit.io.argOuts(api.X194_idle_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X203_cycles_arg).port.bits := instrctrs(X203_instrctr).cycs
    accelUnit.io.argOuts(api.X203_cycles_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X203_iters_arg).port.bits := instrctrs(X203_instrctr).iters
    accelUnit.io.argOuts(api.X203_iters_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X203_stalled_arg).port.bits := instrctrs(X203_instrctr).stalls
    accelUnit.io.argOuts(api.X203_stalled_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X203_idle_arg).port.bits := instrctrs(X203_instrctr).idles
    accelUnit.io.argOuts(api.X203_idle_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X207_cycles_arg).port.bits := instrctrs(X207_instrctr).cycs
    accelUnit.io.argOuts(api.X207_cycles_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X207_iters_arg).port.bits := instrctrs(X207_instrctr).iters
    accelUnit.io.argOuts(api.X207_iters_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X207_stalled_arg).port.bits := instrctrs(X207_instrctr).stalls
    accelUnit.io.argOuts(api.X207_stalled_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X207_idle_arg).port.bits := instrctrs(X207_instrctr).idles
    accelUnit.io.argOuts(api.X207_idle_arg).port.valid := accelUnit.io.enable
    val numArgOuts_breakpts = 0
  }
}
