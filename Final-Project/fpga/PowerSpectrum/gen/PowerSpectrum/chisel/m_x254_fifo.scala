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

class x254_fifo {
  lazy val w0 = Access(261, 0, 0, List(0), List(0), None, PortInfo(Some(0), 1, 5, List(1), 96, List(List(RG(1,0,0)))))
  lazy val r0 = Access(266, 0, 0, List(0), List(0), None, PortInfo(Some(0), 1, 5, List(1), 96, List(List(RG(1,0,0)))))
  lazy val m = Module(new FIFO(
    List[Int](16),
     96, 
    List[Int](1),
    List[Int](1),
    List[Int](1),
    List(w0),
    List(r0),
    BankedMemory, 
    None, 
    true, 
    0,
    2, 
    myName = "x254_fifo"
  ))
  m.io.asInstanceOf[FIFOInterface] <> DontCare
  m.io.reset := false.B
  ModuleParams.addParams("x254_fifo_p", m.io.p)
}
