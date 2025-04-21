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

/** Hierarchy: x1634 -> x1635 -> x1636 -> x1637 -> x1638 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1634_inr_Foreach **/
class x1634_inr_Foreach_kernel(
  list_b1168: List[Bool],
  list_b1164: List[FixedPoint],
  list_x934_tileA_sram_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 11.4.toInt, myName = "x1634_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(4.0.toInt, 2 + _root_.utils.math.log2Up(4.0.toInt), "x1634_inr_Foreach_iiCtr"))
  
  abstract class x1634_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b1168 = Input(Bool())
      val in_b1164 = Input(new FixedPoint(true, 32, 0))
      val in_x934_tileA_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x934_tileA_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1526 = Input(new FixedPoint(true, 32, 0))
      val in_b925 = Input(Bool())
      val in_x1017_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1006 = Input(Bool())
      val in_b1530 = Input(Bool())
      val in_x1018_tileC_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b914 = Input(Bool())
      val in_x1019_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1019_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def b1168 = {io.in_b1168} 
    def b1164 = {io.in_b1164} 
    def x934_tileA_sram_0 = {io.in_x934_tileA_sram_0} ; io.in_x934_tileA_sram_0 := DontCare
    def b1526 = {io.in_b1526} 
    def b925 = {io.in_b925} 
    def x1017_tileB_sram_0 = {io.in_x1017_tileB_sram_0} ; io.in_x1017_tileB_sram_0 := DontCare
    def b1006 = {io.in_b1006} 
    def b1530 = {io.in_b1530} 
    def x1018_tileC_sram_0 = {io.in_x1018_tileC_sram_0} ; io.in_x1018_tileC_sram_0 := DontCare
    def b914 = {io.in_b914} 
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
  }
  def connectWires0(module: x1634_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b1168 <> b1168
    module.io.in_b1164 <> b1164
    x934_tileA_sram_0.connectLedger(module.io.in_x934_tileA_sram_0)
    module.io.in_b1526 <> b1526
    module.io.in_b925 <> b925
    x1017_tileB_sram_0.connectLedger(module.io.in_x1017_tileB_sram_0)
    module.io.in_b1006 <> b1006
    module.io.in_b1530 <> b1530
    x1018_tileC_sram_0.connectLedger(module.io.in_x1018_tileC_sram_0)
    module.io.in_b914 <> b914
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
  }
  val b1168 = list_b1168(0)
  val b925 = list_b1168(1)
  val b1006 = list_b1168(2)
  val b1530 = list_b1168(3)
  val b914 = list_b1168(4)
  val b1164 = list_b1164(0)
  val b1526 = list_b1164(1)
  val x934_tileA_sram_0 = list_x934_tileA_sram_0(0)
  val x1017_tileB_sram_0 = list_x934_tileA_sram_0(1)
  val x1018_tileC_sram_0 = list_x934_tileA_sram_0(2)
  val x1019_tileC_sram_1 = list_x934_tileA_sram_0(3)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1634_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1634_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1634_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1634_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x1634_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x1634_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1634_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1634_instrctr, cycles_x1634_inr_Foreach.io.count, iters_x1634_inr_Foreach.io.count, 0.U, 0.U)
      val b1612 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1612.suggestName("b1612")
      val b1613 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1613.suggestName("b1613")
      val x1616 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1616""")
      x1616.r := Math.arith_right_shift_div(b1164, 2, Some(0.2), true.B,"x1616").r
      val x1617 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1617""")
      x1617.r := Math.arith_left_shift(x1616, 2, Some(0.2), true.B,"x1617").r
      val x1618 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1618""")
      x1618.r := Math.arith_right_shift_div(b1526, 2, Some(0.2), true.B,"x1618").r
      val x1619_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1619_sum""")
      x1619_sum.r := Math.add(x1617,x1618,Some(1.0), true.B, Truncate, Wrapping, "x1619_sum").r
      val x2162 = Wire(Bool()).suggestName("x2162_b1168_D7") 
      x2162.r := getRetimed(b1168.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2163 = Wire(Bool()).suggestName("x2163_b1613_D7") 
      x2163.r := getRetimed(b1613.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2164 = Wire(Bool()).suggestName("x2164_b925_D7") 
      x2164.r := getRetimed(b925.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2165 = Wire(new FixedPoint(true, 32, 0)).suggestName("x2165_x1619_sum_D6") 
      x2165.r := getRetimed(x1619_sum.r, 6.toInt, io.sigsIn.backpressure & true.B)
      val x2166 = Wire(Bool()).suggestName("x2166_b1006_D7") 
      x2166.r := getRetimed(b1006.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2167 = Wire(Bool()).suggestName("x2167_b1530_D7") 
      x2167.r := getRetimed(b1530.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2168 = Wire(Bool()).suggestName("x2168_b914_D7") 
      x2168.r := getRetimed(b914.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1620_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1620_rd""")
      val x1620_rd_banks = List[UInt](3L.FP(true, 32, 0).r,0.U)
      val x1620_rd_ofs = List[UInt](x2165.r)
      val x1620_rd_en = List[Bool](true.B)
      val x1620_rd_shared_en = ((io.sigsIn.forwardpressure).DS(7.4.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(7.4.toInt, rr, io.sigsIn.backpressure & true.B) && x2166 & x2162 & x2167 & x2163 & x2164 & x2168 ).suggestName("x1620_rd_shared_en")
      x1620_rd.toSeq.zip(x1018_tileC_sram_0.connectRPort(1620, x1620_rd_banks, x1620_rd_ofs, io.sigsIn.backpressure, x1620_rd_en.map(_ && x1620_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x1621 = VecApply(x1620,0)
      val x1621_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1621_elem_0""")
      x1621_elem_0.r := x1620_rd(0).r
      val x1624_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1624_rd""")
      val x1624_rd_banks = List[UInt](3L.FP(true, 32, 0).r,0.U)
      val x1624_rd_ofs = List[UInt](0.U)
      val x1624_rd_en = List[Bool](true.B)
      val x1624_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1624_rd_shared_en")
      x1624_rd.toSeq.zip(x934_tileA_sram_0.connectRPort(1624, x1624_rd_banks, x1624_rd_ofs, io.sigsIn.backpressure, x1624_rd_en.map(_ && x1624_rd_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      // x1625 = VecApply(x1624,0)
      val x1625_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1625_elem_0""")
      x1625_elem_0.r := x1624_rd(0).r
      val x1628_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1628_rd""")
      val x1628_rd_banks = List[UInt](0.U,0.U)
      val x1628_rd_ofs = List[UInt](0.U)
      val x1628_rd_en = List[Bool](true.B)
      val x1628_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1628_rd_shared_en")
      x1628_rd.toSeq.zip(x1017_tileB_sram_0.connectRPort(1628, x1628_rd_banks, x1628_rd_ofs, io.sigsIn.backpressure, x1628_rd_en.map(_ && x1628_rd_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      // x1629 = VecApply(x1628,0)
      val x1629_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1629_elem_0""")
      x1629_elem_0.r := x1628_rd(0).r
      val x1630_mul = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1630_mul""")
      x1630_mul.r := (Math.mul(x1625_elem_0, x1629_elem_0, Some(6.0), true.B, Truncate, Wrapping, "x1630_mul")).r
      val x1631_sum = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1631_sum""")
      x1631_sum.r := Math.add(x1621_elem_0,x1630_mul,Some(1.0), true.B, Truncate, Wrapping, "x1631_sum").r
      val x2175 = Wire(Bool()).suggestName("x2175_b1168_D10") 
      x2175.r := getRetimed(b1168.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2176 = Wire(Bool()).suggestName("x2176_b1613_D10") 
      x2176.r := getRetimed(b1613.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2177 = Wire(Bool()).suggestName("x2177_b925_D10") 
      x2177.r := getRetimed(b925.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2178 = Wire(new FixedPoint(true, 32, 0)).suggestName("x2178_x1619_sum_D9") 
      x2178.r := getRetimed(x1619_sum.r, 9.toInt, io.sigsIn.backpressure & true.B)
      val x2179 = Wire(Bool()).suggestName("x2179_b1006_D10") 
      x2179.r := getRetimed(b1006.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2180 = Wire(Bool()).suggestName("x2180_b1530_D10") 
      x2180.r := getRetimed(b1530.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2181 = Wire(Bool()).suggestName("x2181_b914_D10") 
      x2181.r := getRetimed(b914.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1632_wr_banks = List[UInt](3L.FP(true, 32, 0).r,0.U)
      val x1632_wr_ofs = List[UInt](x2178.r)
      val x1632_wr_en = List[Bool](true.B)
      val x1632_wr_data = List[UInt](x1631_sum.r)
      x1019_tileC_sram_1.connectWPort(1632, x1632_wr_banks, x1632_wr_ofs, x1632_wr_data, x1632_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x2177 & x2179 & x2175 & x2181 & x2180 & x2176))
      val x1633_wr_banks = List[UInt](3L.FP(true, 32, 0).r,0.U)
      val x1633_wr_ofs = List[UInt](x2178.r)
      val x1633_wr_en = List[Bool](true.B)
      val x1633_wr_data = List[UInt](x1631_sum.r)
      x1018_tileC_sram_0.connectWPort(1633, x1633_wr_banks, x1633_wr_ofs, x1633_wr_data, x1633_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x2177 & x2179 & x2175 & x2181 & x2180 & x2176))
    }
    val module = Module(new x1634_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectInstrCtrs(instrctrs, module.io.in_instrctrs)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnrolledForeach x1634_inr_Foreach **/
