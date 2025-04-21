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

/** Hierarchy: x1520 -> x1521 -> x1522 -> x1637 -> x1638 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1520_inr_Foreach **/
class x1520_inr_Foreach_kernel(
  list_b925: List[Bool],
  list_b1163: List[FixedPoint],
  list_x934_tileA_sram_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 11.4.toInt, myName = "x1520_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(4.0.toInt, 2 + _root_.utils.math.log2Up(4.0.toInt), "x1520_inr_Foreach_iiCtr"))
  
  abstract class x1520_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x934_tileA_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x934_tileA_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b925 = Input(Bool())
      val in_b1163 = Input(new FixedPoint(true, 32, 0))
      val in_x1017_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1006 = Input(Bool())
      val in_b1167 = Input(Bool())
      val in_x1018_tileC_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1412 = Input(new FixedPoint(true, 32, 0))
      val in_b914 = Input(Bool())
      val in_x1019_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1019_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_b1416 = Input(Bool())
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x934_tileA_sram_0 = {io.in_x934_tileA_sram_0} ; io.in_x934_tileA_sram_0 := DontCare
    def b925 = {io.in_b925} 
    def b1163 = {io.in_b1163} 
    def x1017_tileB_sram_0 = {io.in_x1017_tileB_sram_0} ; io.in_x1017_tileB_sram_0 := DontCare
    def b1006 = {io.in_b1006} 
    def b1167 = {io.in_b1167} 
    def x1018_tileC_sram_0 = {io.in_x1018_tileC_sram_0} ; io.in_x1018_tileC_sram_0 := DontCare
    def b1412 = {io.in_b1412} 
    def b914 = {io.in_b914} 
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
    def b1416 = {io.in_b1416} 
  }
  def connectWires0(module: x1520_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x934_tileA_sram_0.connectLedger(module.io.in_x934_tileA_sram_0)
    module.io.in_b925 <> b925
    module.io.in_b1163 <> b1163
    x1017_tileB_sram_0.connectLedger(module.io.in_x1017_tileB_sram_0)
    module.io.in_b1006 <> b1006
    module.io.in_b1167 <> b1167
    x1018_tileC_sram_0.connectLedger(module.io.in_x1018_tileC_sram_0)
    module.io.in_b1412 <> b1412
    module.io.in_b914 <> b914
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
    module.io.in_b1416 <> b1416
  }
  val b925 = list_b925(0)
  val b1006 = list_b925(1)
  val b1167 = list_b925(2)
  val b914 = list_b925(3)
  val b1416 = list_b925(4)
  val b1163 = list_b1163(0)
  val b1412 = list_b1163(1)
  val x934_tileA_sram_0 = list_x934_tileA_sram_0(0)
  val x1017_tileB_sram_0 = list_x934_tileA_sram_0(1)
  val x1018_tileC_sram_0 = list_x934_tileA_sram_0(2)
  val x1019_tileC_sram_1 = list_x934_tileA_sram_0(3)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1520_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1520_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1520_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1520_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x1520_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x1520_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1520_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1520_instrctr, cycles_x1520_inr_Foreach.io.count, iters_x1520_inr_Foreach.io.count, 0.U, 0.U)
      val b1498 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1498.suggestName("b1498")
      val b1499 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1499.suggestName("b1499")
      val x1502 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1502""")
      x1502.r := Math.arith_right_shift_div(b1163, 2, Some(0.2), true.B,"x1502").r
      val x1503 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1503""")
      x1503.r := Math.arith_left_shift(x1502, 2, Some(0.2), true.B,"x1503").r
      val x1504 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1504""")
      x1504.r := Math.arith_right_shift_div(b1412, 2, Some(0.2), true.B,"x1504").r
      val x1505_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1505_sum""")
      x1505_sum.r := Math.add(x1503,x1504,Some(1.0), true.B, Truncate, Wrapping, "x1505_sum").r
      val x2082 = Wire(Bool()).suggestName("x2082_b925_D7") 
      x2082.r := getRetimed(b925.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2083 = Wire(Bool()).suggestName("x2083_b1006_D7") 
      x2083.r := getRetimed(b1006.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2084 = Wire(Bool()).suggestName("x2084_b1167_D7") 
      x2084.r := getRetimed(b1167.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2085 = Wire(Bool()).suggestName("x2085_b1499_D7") 
      x2085.r := getRetimed(b1499.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2086 = Wire(Bool()).suggestName("x2086_b914_D7") 
      x2086.r := getRetimed(b914.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2087 = Wire(new FixedPoint(true, 32, 0)).suggestName("x2087_x1505_sum_D6") 
      x2087.r := getRetimed(x1505_sum.r, 6.toInt, io.sigsIn.backpressure & true.B)
      val x2088 = Wire(Bool()).suggestName("x2088_b1416_D7") 
      x2088.r := getRetimed(b1416.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1506_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1506_rd""")
      val x1506_rd_banks = List[UInt](2L.FP(true, 32, 0).r,3L.FP(true, 32, 0).r)
      val x1506_rd_ofs = List[UInt](x2087.r)
      val x1506_rd_en = List[Bool](true.B)
      val x1506_rd_shared_en = ((io.sigsIn.forwardpressure).DS(7.4.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(7.4.toInt, rr, io.sigsIn.backpressure & true.B) && x2086 & x2085 & x2083 & x2088 & x2082 & x2084 ).suggestName("x1506_rd_shared_en")
      x1506_rd.toSeq.zip(x1018_tileC_sram_0.connectRPort(1506, x1506_rd_banks, x1506_rd_ofs, io.sigsIn.backpressure, x1506_rd_en.map(_ && x1506_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x1507 = VecApply(x1506,0)
      val x1507_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1507_elem_0""")
      x1507_elem_0.r := x1506_rd(0).r
      val x1510_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1510_rd""")
      val x1510_rd_banks = List[UInt](2L.FP(true, 32, 0).r,0.U)
      val x1510_rd_ofs = List[UInt](0.U)
      val x1510_rd_en = List[Bool](true.B)
      val x1510_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1510_rd_shared_en")
      x1510_rd.toSeq.zip(x934_tileA_sram_0.connectRPort(1510, x1510_rd_banks, x1510_rd_ofs, io.sigsIn.backpressure, x1510_rd_en.map(_ && x1510_rd_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      // x1511 = VecApply(x1510,0)
      val x1511_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1511_elem_0""")
      x1511_elem_0.r := x1510_rd(0).r
      val x1514_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1514_rd""")
      val x1514_rd_banks = List[UInt](0.U,3L.FP(true, 32, 0).r)
      val x1514_rd_ofs = List[UInt](0.U)
      val x1514_rd_en = List[Bool](true.B)
      val x1514_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1514_rd_shared_en")
      x1514_rd.toSeq.zip(x1017_tileB_sram_0.connectRPort(1514, x1514_rd_banks, x1514_rd_ofs, io.sigsIn.backpressure, x1514_rd_en.map(_ && x1514_rd_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      // x1515 = VecApply(x1514,0)
      val x1515_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1515_elem_0""")
      x1515_elem_0.r := x1514_rd(0).r
      val x1516_mul = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1516_mul""")
      x1516_mul.r := (Math.mul(x1511_elem_0, x1515_elem_0, Some(6.0), true.B, Truncate, Wrapping, "x1516_mul")).r
      val x1517_sum = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1517_sum""")
      x1517_sum.r := Math.add(x1507_elem_0,x1516_mul,Some(1.0), true.B, Truncate, Wrapping, "x1517_sum").r
      val x2095 = Wire(Bool()).suggestName("x2095_b925_D10") 
      x2095.r := getRetimed(b925.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2096 = Wire(Bool()).suggestName("x2096_b1006_D10") 
      x2096.r := getRetimed(b1006.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2097 = Wire(Bool()).suggestName("x2097_b1167_D10") 
      x2097.r := getRetimed(b1167.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2098 = Wire(Bool()).suggestName("x2098_b1499_D10") 
      x2098.r := getRetimed(b1499.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2099 = Wire(Bool()).suggestName("x2099_b914_D10") 
      x2099.r := getRetimed(b914.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2100 = Wire(new FixedPoint(true, 32, 0)).suggestName("x2100_x1505_sum_D9") 
      x2100.r := getRetimed(x1505_sum.r, 9.toInt, io.sigsIn.backpressure & true.B)
      val x2101 = Wire(Bool()).suggestName("x2101_b1416_D10") 
      x2101.r := getRetimed(b1416.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1518_wr_banks = List[UInt](2L.FP(true, 32, 0).r,3L.FP(true, 32, 0).r)
      val x1518_wr_ofs = List[UInt](x2100.r)
      val x1518_wr_en = List[Bool](true.B)
      val x1518_wr_data = List[UInt](x1517_sum.r)
      x1019_tileC_sram_1.connectWPort(1518, x1518_wr_banks, x1518_wr_ofs, x1518_wr_data, x1518_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x2098 & x2097 & x2096 & x2101 & x2095 & x2099))
      val x1519_wr_banks = List[UInt](2L.FP(true, 32, 0).r,3L.FP(true, 32, 0).r)
      val x1519_wr_ofs = List[UInt](x2100.r)
      val x1519_wr_en = List[Bool](true.B)
      val x1519_wr_data = List[UInt](x1517_sum.r)
      x1018_tileC_sram_0.connectWPort(1519, x1519_wr_banks, x1519_wr_ofs, x1519_wr_data, x1519_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x2098 & x2097 & x2096 & x2101 & x2095 & x2099))
    }
    val module = Module(new x1520_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1520_inr_Foreach **/
