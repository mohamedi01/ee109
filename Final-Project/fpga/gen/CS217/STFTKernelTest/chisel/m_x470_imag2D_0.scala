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

class x470_imag2D_0 {
  lazy val w0 = Access(550, 0, 0, List(0), List(0), None, PortInfo(Some(0), 1, 3, List(1), 32, List(List(RG(0)))))
  lazy val r0 = Access(653, 0, 0, List(0), List(0), None, PortInfo(Some(0), 1, 3, List(1), 32, List(List(RG(0)))))
  lazy val m = Module(new BankedSRAM(
    List[Int](3,2),
     32, 
    List[Int](1),
    List[Int](1),
    List[Int](1,1),
    List(w0),
    List(r0),
    BankedMemory, 
    None, 
    true, 
    0,
    2, 
    myName = "x470_imag2D_0"
  ))
  m.io.asInstanceOf[StandardInterface] <> DontCare
  m.io.reset := false.B
  ModuleParams.addParams("x470_imag2D_0_p", m.io.p)
}
