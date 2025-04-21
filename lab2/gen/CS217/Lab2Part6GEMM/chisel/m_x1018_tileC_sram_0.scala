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

class x1018_tileC_sram_0 {
  lazy val w15 = Access(1610, 0, 14, List(14), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(3),RG(2)))))
  lazy val w11 = Access(1245, 0, 1, List(1), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(0),RG(1)))))
  lazy val w4 = Access(1405, 0, 7, List(7), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(1),RG(3)))))
  lazy val w6 = Access(1519, 0, 11, List(11), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(2),RG(3)))))
  lazy val w3 = Access(1450, 0, 8, List(8), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(2),RG(0)))))
  lazy val w16 = Access(1268, 0, 2, List(2), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(0),RG(2)))))
  lazy val w14 = Access(1473, 0, 9, List(9), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(2),RG(1)))))
  lazy val w8 = Access(1587, 0, 13, List(13), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(3),RG(1)))))
  lazy val w7 = Access(1291, 0, 3, List(3), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(0),RG(3)))))
  lazy val w13 = Access(1336, 0, 4, List(4), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(1),RG(0)))))
  lazy val w5 = Access(1564, 0, 12, List(12), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(3),RG(0)))))
  lazy val w12 = Access(1496, 0, 10, List(10), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(2),RG(2)))))
  lazy val w10 = Access(1382, 0, 6, List(6), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(1),RG(2)))))
  lazy val w0 = Access(1633, 0, 15, List(15), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(3),RG(3)))))
  lazy val w1 = Access(1222, 0, 0, List(0), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(0),RG(0)))))
  lazy val w2 = Access(1359, 0, 5, List(5), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(1),RG(1)))))
  lazy val w9 = Access(1155, 0, 0, List(0), List(0), None, PortInfo(Some(0), 1, 5, List(3,3), 32, List(List(RG(1,0,4),RG(1,0,4)))))
  lazy val r15 = Access(1597, 0, 14, List(14), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(3),RG(2)))))
  lazy val r13 = Access(1506, 0, 11, List(11), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(2),RG(3)))))
  lazy val r5 = Access(1392, 0, 7, List(7), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(1),RG(3)))))
  lazy val r11 = Access(1460, 0, 9, List(9), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(2),RG(1)))))
  lazy val r2 = Access(1323, 0, 4, List(4), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(1),RG(0)))))
  lazy val r12 = Access(1551, 0, 12, List(12), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(3),RG(0)))))
  lazy val r0 = Access(1369, 0, 6, List(6), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(1),RG(2)))))
  lazy val r4 = Access(1255, 0, 2, List(2), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(0),RG(2)))))
  lazy val r9 = Access(1209, 0, 0, List(0), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(0),RG(0)))))
  lazy val r7 = Access(1483, 0, 10, List(10), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(2),RG(2)))))
  lazy val r8 = Access(1620, 0, 15, List(15), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(3),RG(3)))))
  lazy val r14 = Access(1346, 0, 5, List(5), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(1),RG(1)))))
  lazy val r1 = Access(1437, 0, 8, List(8), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(2),RG(0)))))
  lazy val r6 = Access(1278, 0, 3, List(3), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(0),RG(3)))))
  lazy val r10 = Access(1574, 0, 13, List(13), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(3),RG(1)))))
  lazy val r3 = Access(1232, 0, 1, List(1), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(0),RG(1)))))
  lazy val m = Module(new NBufMem(BankedSRAMType, 
    List[Int](16,16),
    2, 32, 
    List[Int](4,4),
    List[Int](1,1),
    List[Int](4,4),
    List(w0,w1,w2,w3,w4,w5,w6,w7,w8,w9,w10,w11,w12,w13,w14,w15,w16),
    List(r0,r1,r2,r3,r4,r5,r6,r7,r8,r9,r10,r11,r12,r13,r14,r15),
    BankedMemory, 
    None, 
    true, 
    8,
    33, 
    myName = "x1018_tileC_sram_0"
  ))
  m.io.asInstanceOf[NBufInterface] <> DontCare
  m.io.reset := false.B
  ModuleParams.addParams("x1018_tileC_sram_0_p", m.io.np)
}
