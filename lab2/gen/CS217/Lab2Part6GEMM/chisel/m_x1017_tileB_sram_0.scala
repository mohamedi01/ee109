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

class x1017_tileB_sram_0 {
  lazy val w0 = Access(1084, 0, 0, List(0), List(0), None, PortInfo(Some(0), 1, 7, List(1,3), 32, List(List(RG(0),RG(1,0,4)))))
  lazy val r10 = Access(1605, 0, 2, List(2), List(3), None, PortInfo(Some(1), 1, 7, List(1,3), 32, List(List(RG(0),RG(2)))))
  lazy val r15 = Access(1468, 0, 1, List(1), List(2), None, PortInfo(Some(1), 1, 7, List(1,3), 32, List(List(RG(0),RG(1)))))
  lazy val r12 = Access(1491, 0, 2, List(2), List(2), None, PortInfo(Some(1), 1, 7, List(1,3), 32, List(List(RG(0),RG(2)))))
  lazy val r13 = Access(1582, 0, 1, List(1), List(3), None, PortInfo(Some(1), 1, 7, List(1,3), 32, List(List(RG(0),RG(1)))))
  lazy val r1 = Access(1354, 0, 1, List(1), List(1), None, PortInfo(Some(1), 1, 7, List(1,3), 32, List(List(RG(0),RG(1)))))
  lazy val r11 = Access(1240, 0, 1, List(1), List(0), None, PortInfo(Some(1), 1, 7, List(1,3), 32, List(List(RG(0),RG(1)))))
  lazy val r3 = Access(1514, 0, 3, List(3), List(2), None, PortInfo(Some(1), 1, 7, List(1,3), 32, List(List(RG(0),RG(3)))))
  lazy val r8 = Access(1445, 0, 0, List(0), List(2), None, PortInfo(Some(1), 1, 7, List(1,3), 32, List(List(RG(0),RG(0)))))
  lazy val r14 = Access(1331, 0, 0, List(0), List(1), None, PortInfo(Some(1), 1, 7, List(1,3), 32, List(List(RG(0),RG(0)))))
  lazy val r4 = Access(1377, 0, 2, List(2), List(1), None, PortInfo(Some(1), 1, 7, List(1,3), 32, List(List(RG(0),RG(2)))))
  lazy val r5 = Access(1217, 0, 0, List(0), List(0), None, PortInfo(Some(1), 1, 7, List(1,3), 32, List(List(RG(0),RG(0)))))
  lazy val r0 = Access(1559, 0, 0, List(0), List(3), None, PortInfo(Some(1), 1, 7, List(1,3), 32, List(List(RG(0),RG(0)))))
  lazy val r2 = Access(1628, 0, 3, List(3), List(3), None, PortInfo(Some(1), 1, 7, List(1,3), 32, List(List(RG(0),RG(3)))))
  lazy val r7 = Access(1286, 0, 3, List(3), List(0), None, PortInfo(Some(1), 1, 7, List(1,3), 32, List(List(RG(0),RG(3)))))
  lazy val r6 = Access(1263, 0, 2, List(2), List(0), None, PortInfo(Some(1), 1, 7, List(1,3), 32, List(List(RG(0),RG(2)))))
  lazy val r9 = Access(1400, 0, 3, List(3), List(1), None, PortInfo(Some(1), 1, 7, List(1,3), 32, List(List(RG(0),RG(3)))))
  lazy val m = Module(new NBufMem(BankedSRAMType, 
    List[Int](16,16),
    2, 32, 
    List[Int](1,4),
    List[Int](1,1),
    List[Int](1,4),
    List(w0),
    List(r0,r1,r2,r3,r4,r5,r6,r7,r8,r9,r10,r11,r12,r13,r14,r15),
    BankedMemory, 
    None, 
    true, 
    8,
    17, 
    myName = "x1017_tileB_sram_0"
  ))
  m.io.asInstanceOf[NBufInterface] <> DontCare
  m.io.reset := false.B
  ModuleParams.addParams("x1017_tileB_sram_0_p", m.io.np)
}
