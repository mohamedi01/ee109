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

class x934_tileA_sram_0 {
  lazy val w0 = Access(999, 0, 0, List(0), List(0), None, PortInfo(Some(0), 1, 7, List(3,1), 32, List(List(RG(1,0,4),RG(0)))))
  lazy val r8 = Access(1259, 0, 0, List(0), List(2), None, PortInfo(Some(1), 1, 7, List(3,1), 32, List(List(RG(0),RG(0)))))
  lazy val r4 = Access(1487, 0, 2, List(2), List(2), None, PortInfo(Some(1), 1, 7, List(3,1), 32, List(List(RG(2),RG(0)))))
  lazy val r7 = Access(1350, 0, 1, List(1), List(1), None, PortInfo(Some(1), 1, 7, List(3,1), 32, List(List(RG(1),RG(0)))))
  lazy val r9 = Access(1510, 0, 2, List(2), List(3), None, PortInfo(Some(1), 1, 7, List(3,1), 32, List(List(RG(2),RG(0)))))
  lazy val r11 = Access(1282, 0, 0, List(0), List(3), None, PortInfo(Some(1), 1, 7, List(3,1), 32, List(List(RG(0),RG(0)))))
  lazy val r5 = Access(1555, 0, 3, List(3), List(0), None, PortInfo(Some(1), 1, 7, List(3,1), 32, List(List(RG(3),RG(0)))))
  lazy val r0 = Access(1327, 0, 1, List(1), List(0), None, PortInfo(Some(1), 1, 7, List(3,1), 32, List(List(RG(1),RG(0)))))
  lazy val r10 = Access(1464, 0, 2, List(2), List(1), None, PortInfo(Some(1), 1, 7, List(3,1), 32, List(List(RG(2),RG(0)))))
  lazy val r6 = Access(1624, 0, 3, List(3), List(3), None, PortInfo(Some(1), 1, 7, List(3,1), 32, List(List(RG(3),RG(0)))))
  lazy val r15 = Access(1236, 0, 0, List(0), List(1), None, PortInfo(Some(1), 1, 7, List(3,1), 32, List(List(RG(0),RG(0)))))
  lazy val r3 = Access(1373, 0, 1, List(1), List(2), None, PortInfo(Some(1), 1, 7, List(3,1), 32, List(List(RG(1),RG(0)))))
  lazy val r13 = Access(1213, 0, 0, List(0), List(0), None, PortInfo(Some(1), 1, 7, List(3,1), 32, List(List(RG(0),RG(0)))))
  lazy val r2 = Access(1601, 0, 3, List(3), List(2), None, PortInfo(Some(1), 1, 7, List(3,1), 32, List(List(RG(3),RG(0)))))
  lazy val r14 = Access(1578, 0, 3, List(3), List(1), None, PortInfo(Some(1), 1, 7, List(3,1), 32, List(List(RG(3),RG(0)))))
  lazy val r1 = Access(1441, 0, 2, List(2), List(0), None, PortInfo(Some(1), 1, 7, List(3,1), 32, List(List(RG(2),RG(0)))))
  lazy val r12 = Access(1396, 0, 1, List(1), List(3), None, PortInfo(Some(1), 1, 7, List(3,1), 32, List(List(RG(1),RG(0)))))
  lazy val m = Module(new NBufMem(BankedSRAMType, 
    List[Int](16,16),
    2, 32, 
    List[Int](4,1),
    List[Int](1,1),
    List[Int](4,1),
    List(w0),
    List(r0,r1,r2,r3,r4,r5,r6,r7,r8,r9,r10,r11,r12,r13,r14,r15),
    BankedMemory, 
    None, 
    true, 
    8,
    17, 
    myName = "x934_tileA_sram_0"
  ))
  m.io.asInstanceOf[NBufInterface] <> DontCare
  m.io.reset := false.B
  ModuleParams.addParams("x934_tileA_sram_0_p", m.io.np)
}
