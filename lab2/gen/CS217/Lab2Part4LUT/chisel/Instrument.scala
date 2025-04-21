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
    accelUnit.io.argOuts(api.X61_cycles_arg).port.bits := instrctrs(X61_instrctr).cycs
    accelUnit.io.argOuts(api.X61_cycles_arg).port.valid := accelUnit.io.enable
    accelUnit.io.argOuts(api.X61_iters_arg).port.bits := instrctrs(X61_instrctr).iters
    accelUnit.io.argOuts(api.X61_iters_arg).port.valid := accelUnit.io.enable
    val numArgOuts_breakpts = 0
  }
}
