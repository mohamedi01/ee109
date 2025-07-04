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

class x152_buf_0 {
  lazy val w0 = Access(167, 1, 0, List(0), List(0), None, PortInfo(Some(0), 1, 19, List(1), 32, List(List(RG(0)))))
  lazy val w1 = Access(178, 0, 0, List(0), List(0), None, PortInfo(Some(0), 1, 19, List(1), 32, List(List(RG(0)))))
  lazy val r0 = Access(174, 0, 0, List(0), List(0), None, PortInfo(Some(0), 1, 19, List(1), 32, List(List(RG(0)))))
  lazy val r1 = Access(192, 1, 0, List(0), List(0), None, PortInfo(Some(0), 1, 19, List(1), 32, List(List(RG(0)))))
  lazy val m = Module(new BankedSRAM(
    List[Int](400000),
     32, 
    List[Int](1),
    List[Int](1),
    List[Int](1),
    List(w0,w1),
    List(r0,r1),
    BankedMemory, 
    None, 
    true, 
    0,
    4, 
    myName = "x152_buf_0"
  ))
  m.io.asInstanceOf[StandardInterface] <> DontCare
  m.io.reset := false.B
  ModuleParams.addParams("x152_buf_0_p", m.io.p)
}
