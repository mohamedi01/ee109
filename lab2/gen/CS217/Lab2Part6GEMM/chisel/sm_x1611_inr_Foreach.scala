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

/** Hierarchy: x1611 -> x1635 -> x1636 -> x1637 -> x1638 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1611_inr_Foreach **/
class x1611_inr_Foreach_kernel(
  list_b1168: List[Bool],
  list_b1164: List[FixedPoint],
  list_x934_tileA_sram_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 11.4.toInt, myName = "x1611_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(4.0.toInt, 2 + _root_.utils.math.log2Up(4.0.toInt), "x1611_inr_Foreach_iiCtr"))
  
  abstract class x1611_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b1168 = Input(Bool())
      val in_b1164 = Input(new FixedPoint(true, 32, 0))
      val in_b1529 = Input(Bool())
      val in_x934_tileA_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x934_tileA_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b925 = Input(Bool())
      val in_x1017_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1006 = Input(Bool())
      val in_b1525 = Input(new FixedPoint(true, 32, 0))
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
    def b1529 = {io.in_b1529} 
    def x934_tileA_sram_0 = {io.in_x934_tileA_sram_0} ; io.in_x934_tileA_sram_0 := DontCare
    def b925 = {io.in_b925} 
    def x1017_tileB_sram_0 = {io.in_x1017_tileB_sram_0} ; io.in_x1017_tileB_sram_0 := DontCare
    def b1006 = {io.in_b1006} 
    def b1525 = {io.in_b1525} 
    def x1018_tileC_sram_0 = {io.in_x1018_tileC_sram_0} ; io.in_x1018_tileC_sram_0 := DontCare
    def b914 = {io.in_b914} 
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
  }
  def connectWires0(module: x1611_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b1168 <> b1168
    module.io.in_b1164 <> b1164
    module.io.in_b1529 <> b1529
    x934_tileA_sram_0.connectLedger(module.io.in_x934_tileA_sram_0)
    module.io.in_b925 <> b925
    x1017_tileB_sram_0.connectLedger(module.io.in_x1017_tileB_sram_0)
    module.io.in_b1006 <> b1006
    module.io.in_b1525 <> b1525
    x1018_tileC_sram_0.connectLedger(module.io.in_x1018_tileC_sram_0)
    module.io.in_b914 <> b914
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
  }
  val b1168 = list_b1168(0)
  val b1529 = list_b1168(1)
  val b925 = list_b1168(2)
  val b1006 = list_b1168(3)
  val b914 = list_b1168(4)
  val b1164 = list_b1164(0)
  val b1525 = list_b1164(1)
  val x934_tileA_sram_0 = list_x934_tileA_sram_0(0)
  val x1017_tileB_sram_0 = list_x934_tileA_sram_0(1)
  val x1018_tileC_sram_0 = list_x934_tileA_sram_0(2)
  val x1019_tileC_sram_1 = list_x934_tileA_sram_0(3)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1611_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1611_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1611_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1611_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x1611_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x1611_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1611_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1611_instrctr, cycles_x1611_inr_Foreach.io.count, iters_x1611_inr_Foreach.io.count, 0.U, 0.U)
      val b1589 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1589.suggestName("b1589")
      val b1590 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1590.suggestName("b1590")
      val x1593 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1593""")
      x1593.r := Math.arith_right_shift_div(b1164, 2, Some(0.2), true.B,"x1593").r
      val x1594 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1594""")
      x1594.r := Math.arith_left_shift(x1593, 2, Some(0.2), true.B,"x1594").r
      val x1595 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1595""")
      x1595.r := Math.arith_right_shift_div(b1525, 2, Some(0.2), true.B,"x1595").r
      val x1596_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1596_sum""")
      x1596_sum.r := Math.add(x1594,x1595,Some(1.0), true.B, Truncate, Wrapping, "x1596_sum").r
      val x2142 = Wire(Bool()).suggestName("x2142_b1168_D7") 
      x2142.r := getRetimed(b1168.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2143 = Wire(new FixedPoint(true, 32, 0)).suggestName("x2143_x1596_sum_D6") 
      x2143.r := getRetimed(x1596_sum.r, 6.toInt, io.sigsIn.backpressure & true.B)
      val x2144 = Wire(Bool()).suggestName("x2144_b1529_D7") 
      x2144.r := getRetimed(b1529.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2145 = Wire(Bool()).suggestName("x2145_b925_D7") 
      x2145.r := getRetimed(b925.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2146 = Wire(Bool()).suggestName("x2146_b1006_D7") 
      x2146.r := getRetimed(b1006.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2147 = Wire(Bool()).suggestName("x2147_b1590_D7") 
      x2147.r := getRetimed(b1590.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2148 = Wire(Bool()).suggestName("x2148_b914_D7") 
      x2148.r := getRetimed(b914.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1597_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1597_rd""")
      val x1597_rd_banks = List[UInt](3L.FP(true, 32, 0).r,2L.FP(true, 32, 0).r)
      val x1597_rd_ofs = List[UInt](x2143.r)
      val x1597_rd_en = List[Bool](true.B)
      val x1597_rd_shared_en = ((io.sigsIn.forwardpressure).DS(7.4.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(7.4.toInt, rr, io.sigsIn.backpressure & true.B) && x2144 & x2148 & x2145 & x2147 & x2142 & x2146 ).suggestName("x1597_rd_shared_en")
      x1597_rd.toSeq.zip(x1018_tileC_sram_0.connectRPort(1597, x1597_rd_banks, x1597_rd_ofs, io.sigsIn.backpressure, x1597_rd_en.map(_ && x1597_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x1598 = VecApply(x1597,0)
      val x1598_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1598_elem_0""")
      x1598_elem_0.r := x1597_rd(0).r
      val x1601_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1601_rd""")
      val x1601_rd_banks = List[UInt](3L.FP(true, 32, 0).r,0.U)
      val x1601_rd_ofs = List[UInt](0.U)
      val x1601_rd_en = List[Bool](true.B)
      val x1601_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1601_rd_shared_en")
      x1601_rd.toSeq.zip(x934_tileA_sram_0.connectRPort(1601, x1601_rd_banks, x1601_rd_ofs, io.sigsIn.backpressure, x1601_rd_en.map(_ && x1601_rd_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      // x1602 = VecApply(x1601,0)
      val x1602_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1602_elem_0""")
      x1602_elem_0.r := x1601_rd(0).r
      val x1605_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1605_rd""")
      val x1605_rd_banks = List[UInt](0.U,2L.FP(true, 32, 0).r)
      val x1605_rd_ofs = List[UInt](0.U)
      val x1605_rd_en = List[Bool](true.B)
      val x1605_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1605_rd_shared_en")
      x1605_rd.toSeq.zip(x1017_tileB_sram_0.connectRPort(1605, x1605_rd_banks, x1605_rd_ofs, io.sigsIn.backpressure, x1605_rd_en.map(_ && x1605_rd_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      // x1606 = VecApply(x1605,0)
      val x1606_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1606_elem_0""")
      x1606_elem_0.r := x1605_rd(0).r
      val x1607_mul = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1607_mul""")
      x1607_mul.r := (Math.mul(x1602_elem_0, x1606_elem_0, Some(6.0), true.B, Truncate, Wrapping, "x1607_mul")).r
      val x1608_sum = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1608_sum""")
      x1608_sum.r := Math.add(x1598_elem_0,x1607_mul,Some(1.0), true.B, Truncate, Wrapping, "x1608_sum").r
      val x2155 = Wire(Bool()).suggestName("x2155_b1168_D10") 
      x2155.r := getRetimed(b1168.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2156 = Wire(new FixedPoint(true, 32, 0)).suggestName("x2156_x1596_sum_D9") 
      x2156.r := getRetimed(x1596_sum.r, 9.toInt, io.sigsIn.backpressure & true.B)
      val x2157 = Wire(Bool()).suggestName("x2157_b1529_D10") 
      x2157.r := getRetimed(b1529.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2158 = Wire(Bool()).suggestName("x2158_b925_D10") 
      x2158.r := getRetimed(b925.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2159 = Wire(Bool()).suggestName("x2159_b1006_D10") 
      x2159.r := getRetimed(b1006.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2160 = Wire(Bool()).suggestName("x2160_b1590_D10") 
      x2160.r := getRetimed(b1590.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2161 = Wire(Bool()).suggestName("x2161_b914_D10") 
      x2161.r := getRetimed(b914.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1609_wr_banks = List[UInt](3L.FP(true, 32, 0).r,2L.FP(true, 32, 0).r)
      val x1609_wr_ofs = List[UInt](x2156.r)
      val x1609_wr_en = List[Bool](true.B)
      val x1609_wr_data = List[UInt](x1608_sum.r)
      x1019_tileC_sram_1.connectWPort(1609, x1609_wr_banks, x1609_wr_ofs, x1609_wr_data, x1609_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x2159 & x2155 & x2160 & x2158 & x2161 & x2157))
      val x1610_wr_banks = List[UInt](3L.FP(true, 32, 0).r,2L.FP(true, 32, 0).r)
      val x1610_wr_ofs = List[UInt](x2156.r)
      val x1610_wr_en = List[Bool](true.B)
      val x1610_wr_data = List[UInt](x1608_sum.r)
      x1018_tileC_sram_0.connectWPort(1610, x1610_wr_banks, x1610_wr_ofs, x1610_wr_data, x1610_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x2159 & x2155 & x2160 & x2158 & x2161 & x2157))
    }
    val module = Module(new x1611_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1611_inr_Foreach **/
