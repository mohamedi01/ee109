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

class x1019_tileC_sram_1 {
  lazy val w6 = Access(1154, 0, 0, List(0), List(0), None, PortInfo(Some(0), 1, 5, List(3,3), 32, List(List(RG(1,0,4),RG(1,0,4)))))
  lazy val w16 = Access(1449, 0, 8, List(8), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(2),RG(0)))))
  lazy val w5 = Access(1563, 0, 12, List(12), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(3),RG(0)))))
  lazy val w9 = Access(1267, 0, 2, List(2), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(0),RG(2)))))
  lazy val w1 = Access(1290, 0, 3, List(3), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(0),RG(3)))))
  lazy val w4 = Access(1632, 0, 15, List(15), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(3),RG(3)))))
  lazy val w12 = Access(1221, 0, 0, List(0), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(0),RG(0)))))
  lazy val w0 = Access(1586, 0, 13, List(13), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(3),RG(1)))))
  lazy val w15 = Access(1335, 0, 4, List(4), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(1),RG(0)))))
  lazy val w11 = Access(1358, 0, 5, List(5), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(1),RG(1)))))
  lazy val w3 = Access(1609, 0, 14, List(14), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(3),RG(2)))))
  lazy val w14 = Access(1495, 0, 10, List(10), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(2),RG(2)))))
  lazy val w10 = Access(1404, 0, 7, List(7), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(1),RG(3)))))
  lazy val w2 = Access(1518, 0, 11, List(11), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(2),RG(3)))))
  lazy val w13 = Access(1244, 0, 1, List(1), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(0),RG(1)))))
  lazy val w7 = Access(1381, 0, 6, List(6), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(1),RG(2)))))
  lazy val w8 = Access(1472, 0, 9, List(9), List(0), None, PortInfo(Some(1), 1, 5, List(3,3), 32, List(List(RG(2),RG(1)))))
  lazy val r0 = Access(1690, 0, 0, List(0), List(0), None, PortInfo(Some(2), 1, 5, List(3,3), 32, List(List(RG(1,0,4),RG(1,0,4)))))
  lazy val m = Module(new NBufMem(BankedSRAMType, 
    List[Int](16,16),
    3, 32, 
    List[Int](4,4),
    List[Int](1,1),
    List[Int](4,4),
    List(w0,w1,w2,w3,w4,w5,w6,w7,w8,w9,w10,w11,w12,w13,w14,w15,w16),
    List(r0),
    BankedMemory, 
    None, 
    true, 
    8,
    18, 
    myName = "x1019_tileC_sram_1"
  ))
  m.io.asInstanceOf[NBufInterface] <> DontCare
  m.io.reset := false.B
  ModuleParams.addParams("x1019_tileC_sram_1_p", m.io.np)
}
