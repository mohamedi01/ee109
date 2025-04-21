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

class x916_reg {
  lazy val w0 = Access(920, 0, 0, List(0), List(0), None, PortInfo(Some(0), 1, 1, List(1), 32, List(List())))
  lazy val r0 = Access(950, 0, 2, List(2), List(0), None, PortInfo(Some(1), 1, 1, List(1), 32, List(List())))
  lazy val r1 = Access(1311, 1, 1, List(1), List(0), None, PortInfo(Some(1), 1, 1, List(1), 32, List(List())))
  lazy val r2 = Access(1312, 0, 1, List(1), List(0), None, PortInfo(Some(1), 1, 1, List(1), 32, List(List())))
  lazy val r3 = Access(1319, 0, 0, List(0), List(0), None, PortInfo(Some(1), 1, 1, List(1), 32, List(List())))
  lazy val m = Module(new NBufMem(FFType, 
    List[Int](1),
    2, 32, 
    List[Int](1),
    List[Int](1),
    List[Int](),
    List(w0),
    List(r0,r1,r2,r3),
    BankedMemory, 
    Some(List((0L).toDouble)), 
    true, 
    0,
    5, 
    myName = "x916_reg"
  ))
  m.io.asInstanceOf[NBufInterface] <> DontCare
  m.io.reset := false.B
  ModuleParams.addParams("x916_reg_p", m.io.np)
}
