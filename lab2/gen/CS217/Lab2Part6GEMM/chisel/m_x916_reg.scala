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
  lazy val r16 = Access(1777, 0, 2, List(0), List(0), None, PortInfo(Some(1), 1, 1, List(1), 32, List(List())))
  lazy val r3 = Access(1783, 0, 0, List(0), List(2), None, PortInfo(Some(1), 1, 1, List(1), 32, List(List())))
  lazy val r13 = Access(1790, 0, 3, List(0), List(3), None, PortInfo(Some(1), 1, 1, List(1), 32, List(List())))
  lazy val r8 = Access(1784, 0, 1, List(0), List(2), None, PortInfo(Some(1), 1, 1, List(1), 32, List(List())))
  lazy val r6 = Access(1779, 0, 0, List(0), List(1), None, PortInfo(Some(1), 1, 1, List(1), 32, List(List())))
  lazy val r7 = Access(1764, 1, 1, List(1), List(0), None, PortInfo(Some(1), 1, 1, List(1), 32, List(List())))
  lazy val r11 = Access(1775, 0, 0, List(0), List(0), None, PortInfo(Some(1), 1, 1, List(1), 32, List(List())))
  lazy val r17 = Access(950, 0, 2, List(2), List(0), None, PortInfo(Some(1), 1, 1, List(1), 32, List(List())))
  lazy val r12 = Access(1780, 0, 1, List(0), List(1), None, PortInfo(Some(1), 1, 1, List(1), 32, List(List())))
  lazy val r5 = Access(1776, 0, 1, List(0), List(0), None, PortInfo(Some(1), 1, 1, List(1), 32, List(List())))
  lazy val r10 = Access(1765, 0, 1, List(1), List(0), None, PortInfo(Some(1), 1, 1, List(1), 32, List(List())))
  lazy val r4 = Access(1787, 0, 0, List(0), List(3), None, PortInfo(Some(1), 1, 1, List(1), 32, List(List())))
  lazy val r9 = Access(1786, 0, 3, List(0), List(2), None, PortInfo(Some(1), 1, 1, List(1), 32, List(List())))
  lazy val r18 = Access(1789, 0, 2, List(0), List(3), None, PortInfo(Some(1), 1, 1, List(1), 32, List(List())))
  lazy val r1 = Access(1782, 0, 3, List(0), List(1), None, PortInfo(Some(1), 1, 1, List(1), 32, List(List())))
  lazy val r15 = Access(1781, 0, 2, List(0), List(1), None, PortInfo(Some(1), 1, 1, List(1), 32, List(List())))
  lazy val r14 = Access(1785, 0, 2, List(0), List(2), None, PortInfo(Some(1), 1, 1, List(1), 32, List(List())))
  lazy val r2 = Access(1778, 0, 3, List(0), List(0), None, PortInfo(Some(1), 1, 1, List(1), 32, List(List())))
  lazy val r0 = Access(1788, 0, 1, List(0), List(3), None, PortInfo(Some(1), 1, 1, List(1), 32, List(List())))
  lazy val m = Module(new NBufMem(FFType, 
    List[Int](1),
    2, 32, 
    List[Int](1),
    List[Int](1),
    List[Int](),
    List(w0),
    List(r0,r1,r2,r3,r4,r5,r6,r7,r8,r9,r10,r11,r12,r13,r14,r15,r16,r17,r18),
    BankedMemory, 
    Some(List((0L).toDouble)), 
    true, 
    0,
    20, 
    myName = "x916_reg"
  ))
  m.io.asInstanceOf[NBufInterface] <> DontCare
  m.io.reset := false.B
  ModuleParams.addParams("x916_reg_p", m.io.np)
}
