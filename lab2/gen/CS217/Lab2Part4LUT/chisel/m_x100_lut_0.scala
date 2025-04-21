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

class x100_lut_0 {
  lazy val w0 = AccessHelper.singular(32)
  lazy val r0 = Access(105, 0, 0, List(0), List(0), None, PortInfo(Some(0), 1, 3, List(2,2), 32, List(List(RG(1,0,0),RG(1,0,0)))))
  lazy val m = Module(new LUT(
    List[Int](3,3),
     32, 
    List[Int](3,3),
    List[Int](1,1),
    List[Int](3,3),
    List(w0),
    List(r0),
    BankedMemory, 
    Some(List((1L).toDouble,(2L).toDouble,(3L).toDouble,(4L).toDouble,(5L).toDouble,(6L).toDouble,(7L).toDouble,(8L).toDouble,(9L).toDouble)), 
    true, 
    0,
    1, 
    myName = "x100_lut_0"
  ))
  m.io.asInstanceOf[StandardInterface] <> DontCare
  m.io.reset := false.B
  ModuleParams.addParams("x100_lut_0_p", m.io.p)
}
