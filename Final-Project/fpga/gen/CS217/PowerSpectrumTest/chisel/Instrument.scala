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
        accelUnit.io.argOuts(api.X481_cycles_arg).port.bits := instrctrs(X481_instrctr).cycs
        accelUnit.io.argOuts(api.X481_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X481_iters_arg).port.bits := instrctrs(X481_instrctr).iters
        accelUnit.io.argOuts(api.X481_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X480_cycles_arg).port.bits := instrctrs(X480_instrctr).cycs
        accelUnit.io.argOuts(api.X480_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X480_iters_arg).port.bits := instrctrs(X480_instrctr).iters
        accelUnit.io.argOuts(api.X480_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X363_cycles_arg).port.bits := instrctrs(X363_instrctr).cycs
        accelUnit.io.argOuts(api.X363_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X363_iters_arg).port.bits := instrctrs(X363_instrctr).iters
        accelUnit.io.argOuts(api.X363_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X338_cycles_arg).port.bits := instrctrs(X338_instrctr).cycs
        accelUnit.io.argOuts(api.X338_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X338_iters_arg).port.bits := instrctrs(X338_instrctr).iters
        accelUnit.io.argOuts(api.X338_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X338_stalled_arg).port.bits := instrctrs(X338_instrctr).stalls
        accelUnit.io.argOuts(api.X338_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X338_idle_arg).port.bits := instrctrs(X338_instrctr).idles
        accelUnit.io.argOuts(api.X338_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X362_cycles_arg).port.bits := instrctrs(X362_instrctr).cycs
        accelUnit.io.argOuts(api.X362_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X362_iters_arg).port.bits := instrctrs(X362_instrctr).iters
        accelUnit.io.argOuts(api.X362_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X348_cycles_arg).port.bits := instrctrs(X348_instrctr).cycs
        accelUnit.io.argOuts(api.X348_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X348_iters_arg).port.bits := instrctrs(X348_instrctr).iters
        accelUnit.io.argOuts(api.X348_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X348_stalled_arg).port.bits := instrctrs(X348_instrctr).stalls
        accelUnit.io.argOuts(api.X348_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X348_idle_arg).port.bits := instrctrs(X348_instrctr).idles
        accelUnit.io.argOuts(api.X348_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X361_cycles_arg).port.bits := instrctrs(X361_instrctr).cycs
        accelUnit.io.argOuts(api.X361_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X361_iters_arg).port.bits := instrctrs(X361_instrctr).iters
        accelUnit.io.argOuts(api.X361_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X361_stalled_arg).port.bits := instrctrs(X361_instrctr).stalls
        accelUnit.io.argOuts(api.X361_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X361_idle_arg).port.bits := instrctrs(X361_instrctr).idles
        accelUnit.io.argOuts(api.X361_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X398_cycles_arg).port.bits := instrctrs(X398_instrctr).cycs
        accelUnit.io.argOuts(api.X398_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X398_iters_arg).port.bits := instrctrs(X398_instrctr).iters
        accelUnit.io.argOuts(api.X398_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X373_cycles_arg).port.bits := instrctrs(X373_instrctr).cycs
        accelUnit.io.argOuts(api.X373_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X373_iters_arg).port.bits := instrctrs(X373_instrctr).iters
        accelUnit.io.argOuts(api.X373_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X373_stalled_arg).port.bits := instrctrs(X373_instrctr).stalls
        Map[String,Any]()
      }
    }
    val block1chunk0: Map[String, Any] = Block1Chunker0.gen()
    object Block1Chunker1 { // 49 nodes, 49 weight
      def gen(): Map[String, Any] = {
        accelUnit.io.argOuts(api.X373_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X373_idle_arg).port.bits := instrctrs(X373_instrctr).idles
        accelUnit.io.argOuts(api.X373_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X397_cycles_arg).port.bits := instrctrs(X397_instrctr).cycs
        accelUnit.io.argOuts(api.X397_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X397_iters_arg).port.bits := instrctrs(X397_instrctr).iters
        accelUnit.io.argOuts(api.X397_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X383_cycles_arg).port.bits := instrctrs(X383_instrctr).cycs
        accelUnit.io.argOuts(api.X383_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X383_iters_arg).port.bits := instrctrs(X383_instrctr).iters
        accelUnit.io.argOuts(api.X383_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X383_stalled_arg).port.bits := instrctrs(X383_instrctr).stalls
        accelUnit.io.argOuts(api.X383_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X383_idle_arg).port.bits := instrctrs(X383_instrctr).idles
        accelUnit.io.argOuts(api.X383_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X396_cycles_arg).port.bits := instrctrs(X396_instrctr).cycs
        accelUnit.io.argOuts(api.X396_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X396_iters_arg).port.bits := instrctrs(X396_instrctr).iters
        accelUnit.io.argOuts(api.X396_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X396_stalled_arg).port.bits := instrctrs(X396_instrctr).stalls
        accelUnit.io.argOuts(api.X396_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X396_idle_arg).port.bits := instrctrs(X396_instrctr).idles
        accelUnit.io.argOuts(api.X396_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X411_cycles_arg).port.bits := instrctrs(X411_instrctr).cycs
        accelUnit.io.argOuts(api.X411_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X411_iters_arg).port.bits := instrctrs(X411_instrctr).iters
        accelUnit.io.argOuts(api.X411_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X444_cycles_arg).port.bits := instrctrs(X444_instrctr).cycs
        accelUnit.io.argOuts(api.X444_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X444_iters_arg).port.bits := instrctrs(X444_instrctr).iters
        accelUnit.io.argOuts(api.X444_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X443_cycles_arg).port.bits := instrctrs(X443_instrctr).cycs
        accelUnit.io.argOuts(api.X443_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X443_iters_arg).port.bits := instrctrs(X443_instrctr).iters
        accelUnit.io.argOuts(api.X443_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X438_cycles_arg).port.bits := instrctrs(X438_instrctr).cycs
        accelUnit.io.argOuts(api.X438_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X438_iters_arg).port.bits := instrctrs(X438_instrctr).iters
        accelUnit.io.argOuts(api.X438_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X423_cycles_arg).port.bits := instrctrs(X423_instrctr).cycs
        accelUnit.io.argOuts(api.X423_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X423_iters_arg).port.bits := instrctrs(X423_instrctr).iters
        accelUnit.io.argOuts(api.X423_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X423_stalled_arg).port.bits := instrctrs(X423_instrctr).stalls
        accelUnit.io.argOuts(api.X423_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X423_idle_arg).port.bits := instrctrs(X423_instrctr).idles
        accelUnit.io.argOuts(api.X423_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X437_cycles_arg).port.bits := instrctrs(X437_instrctr).cycs
        accelUnit.io.argOuts(api.X437_cycles_arg).port.valid := accelUnit.io.enable
        Map[String,Any]()
      }
    }
    val block1chunk1: Map[String, Any] = Block1Chunker1.gen()
    object Block1Chunker2 { // 14 nodes, 14 weight
      def gen(): Map[String, Any] = {
        accelUnit.io.argOuts(api.X437_iters_arg).port.bits := instrctrs(X437_instrctr).iters
        accelUnit.io.argOuts(api.X437_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X437_stalled_arg).port.bits := instrctrs(X437_instrctr).stalls
        accelUnit.io.argOuts(api.X437_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X437_idle_arg).port.bits := instrctrs(X437_instrctr).idles
        accelUnit.io.argOuts(api.X437_idle_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X442_cycles_arg).port.bits := instrctrs(X442_instrctr).cycs
        accelUnit.io.argOuts(api.X442_cycles_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X442_iters_arg).port.bits := instrctrs(X442_instrctr).iters
        accelUnit.io.argOuts(api.X442_iters_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X442_stalled_arg).port.bits := instrctrs(X442_instrctr).stalls
        accelUnit.io.argOuts(api.X442_stalled_arg).port.valid := accelUnit.io.enable
        accelUnit.io.argOuts(api.X442_idle_arg).port.bits := instrctrs(X442_instrctr).idles
        accelUnit.io.argOuts(api.X442_idle_arg).port.valid := accelUnit.io.enable
        Map[String,Any]()
      }
    }
    val block1chunk2: Map[String, Any] = Block1Chunker2.gen()
    val numArgOuts_breakpts = 0
  }
}
