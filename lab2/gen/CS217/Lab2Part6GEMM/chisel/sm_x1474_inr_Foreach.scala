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

/** Hierarchy: x1474 -> x1521 -> x1522 -> x1637 -> x1638 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1474_inr_Foreach **/
class x1474_inr_Foreach_kernel(
  list_b925: List[Bool],
  list_b1163: List[FixedPoint],
  list_x934_tileA_sram_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 11.4.toInt, myName = "x1474_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(4.0.toInt, 2 + _root_.utils.math.log2Up(4.0.toInt), "x1474_inr_Foreach_iiCtr"))
  
  abstract class x1474_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x934_tileA_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x934_tileA_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b925 = Input(Bool())
      val in_b1163 = Input(new FixedPoint(true, 32, 0))
      val in_x1017_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1006 = Input(Bool())
      val in_b1414 = Input(Bool())
      val in_b1167 = Input(Bool())
      val in_x1018_tileC_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b914 = Input(Bool())
      val in_b1410 = Input(new FixedPoint(true, 32, 0))
      val in_x1019_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1019_tileC_sram_1_p").asInstanceOf[NBufParams] ))
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
    def b1414 = {io.in_b1414} 
    def b1167 = {io.in_b1167} 
    def x1018_tileC_sram_0 = {io.in_x1018_tileC_sram_0} ; io.in_x1018_tileC_sram_0 := DontCare
    def b914 = {io.in_b914} 
    def b1410 = {io.in_b1410} 
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
  }
  def connectWires0(module: x1474_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x934_tileA_sram_0.connectLedger(module.io.in_x934_tileA_sram_0)
    module.io.in_b925 <> b925
    module.io.in_b1163 <> b1163
    x1017_tileB_sram_0.connectLedger(module.io.in_x1017_tileB_sram_0)
    module.io.in_b1006 <> b1006
    module.io.in_b1414 <> b1414
    module.io.in_b1167 <> b1167
    x1018_tileC_sram_0.connectLedger(module.io.in_x1018_tileC_sram_0)
    module.io.in_b914 <> b914
    module.io.in_b1410 <> b1410
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
  }
  val b925 = list_b925(0)
  val b1006 = list_b925(1)
  val b1414 = list_b925(2)
  val b1167 = list_b925(3)
  val b914 = list_b925(4)
  val b1163 = list_b1163(0)
  val b1410 = list_b1163(1)
  val x934_tileA_sram_0 = list_x934_tileA_sram_0(0)
  val x1017_tileB_sram_0 = list_x934_tileA_sram_0(1)
  val x1018_tileC_sram_0 = list_x934_tileA_sram_0(2)
  val x1019_tileC_sram_1 = list_x934_tileA_sram_0(3)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1474_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1474_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1474_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1474_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x1474_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x1474_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1474_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1474_instrctr, cycles_x1474_inr_Foreach.io.count, iters_x1474_inr_Foreach.io.count, 0.U, 0.U)
      val b1452 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1452.suggestName("b1452")
      val b1453 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1453.suggestName("b1453")
      val x1456 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1456""")
      x1456.r := Math.arith_right_shift_div(b1163, 2, Some(0.2), true.B,"x1456").r
      val x1457 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1457""")
      x1457.r := Math.arith_left_shift(x1456, 2, Some(0.2), true.B,"x1457").r
      val x1458 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1458""")
      x1458.r := Math.arith_right_shift_div(b1410, 2, Some(0.2), true.B,"x1458").r
      val x1459_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1459_sum""")
      x1459_sum.r := Math.add(x1457,x1458,Some(1.0), true.B, Truncate, Wrapping, "x1459_sum").r
      val x2042 = Wire(Bool()).suggestName("x2042_b925_D7") 
      x2042.r := getRetimed(b925.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2043 = Wire(Bool()).suggestName("x2043_b1006_D7") 
      x2043.r := getRetimed(b1006.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2044 = Wire(Bool()).suggestName("x2044_b1414_D7") 
      x2044.r := getRetimed(b1414.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2045 = Wire(Bool()).suggestName("x2045_b1167_D7") 
      x2045.r := getRetimed(b1167.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2046 = Wire(new FixedPoint(true, 32, 0)).suggestName("x2046_x1459_sum_D6") 
      x2046.r := getRetimed(x1459_sum.r, 6.toInt, io.sigsIn.backpressure & true.B)
      val x2047 = Wire(Bool()).suggestName("x2047_b1453_D7") 
      x2047.r := getRetimed(b1453.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2048 = Wire(Bool()).suggestName("x2048_b914_D7") 
      x2048.r := getRetimed(b914.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1460_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1460_rd""")
      val x1460_rd_banks = List[UInt](2L.FP(true, 32, 0).r,1L.FP(true, 32, 0).r)
      val x1460_rd_ofs = List[UInt](x2046.r)
      val x1460_rd_en = List[Bool](true.B)
      val x1460_rd_shared_en = ((io.sigsIn.forwardpressure).DS(7.4.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(7.4.toInt, rr, io.sigsIn.backpressure & true.B) && x2047 & x2043 & x2045 & x2044 & x2048 & x2042 ).suggestName("x1460_rd_shared_en")
      x1460_rd.toSeq.zip(x1018_tileC_sram_0.connectRPort(1460, x1460_rd_banks, x1460_rd_ofs, io.sigsIn.backpressure, x1460_rd_en.map(_ && x1460_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x1461 = VecApply(x1460,0)
      val x1461_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1461_elem_0""")
      x1461_elem_0.r := x1460_rd(0).r
      val x1464_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1464_rd""")
      val x1464_rd_banks = List[UInt](2L.FP(true, 32, 0).r,0.U)
      val x1464_rd_ofs = List[UInt](0.U)
      val x1464_rd_en = List[Bool](true.B)
      val x1464_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1464_rd_shared_en")
      x1464_rd.toSeq.zip(x934_tileA_sram_0.connectRPort(1464, x1464_rd_banks, x1464_rd_ofs, io.sigsIn.backpressure, x1464_rd_en.map(_ && x1464_rd_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      // x1465 = VecApply(x1464,0)
      val x1465_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1465_elem_0""")
      x1465_elem_0.r := x1464_rd(0).r
      val x1468_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1468_rd""")
      val x1468_rd_banks = List[UInt](0.U,1L.FP(true, 32, 0).r)
      val x1468_rd_ofs = List[UInt](0.U)
      val x1468_rd_en = List[Bool](true.B)
      val x1468_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1468_rd_shared_en")
      x1468_rd.toSeq.zip(x1017_tileB_sram_0.connectRPort(1468, x1468_rd_banks, x1468_rd_ofs, io.sigsIn.backpressure, x1468_rd_en.map(_ && x1468_rd_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      // x1469 = VecApply(x1468,0)
      val x1469_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1469_elem_0""")
      x1469_elem_0.r := x1468_rd(0).r
      val x1470_mul = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1470_mul""")
      x1470_mul.r := (Math.mul(x1465_elem_0, x1469_elem_0, Some(6.0), true.B, Truncate, Wrapping, "x1470_mul")).r
      val x1471_sum = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1471_sum""")
      x1471_sum.r := Math.add(x1461_elem_0,x1470_mul,Some(1.0), true.B, Truncate, Wrapping, "x1471_sum").r
      val x2055 = Wire(Bool()).suggestName("x2055_b925_D10") 
      x2055.r := getRetimed(b925.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2056 = Wire(Bool()).suggestName("x2056_b1006_D10") 
      x2056.r := getRetimed(b1006.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2057 = Wire(Bool()).suggestName("x2057_b1414_D10") 
      x2057.r := getRetimed(b1414.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2058 = Wire(Bool()).suggestName("x2058_b1167_D10") 
      x2058.r := getRetimed(b1167.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2059 = Wire(new FixedPoint(true, 32, 0)).suggestName("x2059_x1459_sum_D9") 
      x2059.r := getRetimed(x1459_sum.r, 9.toInt, io.sigsIn.backpressure & true.B)
      val x2060 = Wire(Bool()).suggestName("x2060_b1453_D10") 
      x2060.r := getRetimed(b1453.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2061 = Wire(Bool()).suggestName("x2061_b914_D10") 
      x2061.r := getRetimed(b914.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1472_wr_banks = List[UInt](2L.FP(true, 32, 0).r,1L.FP(true, 32, 0).r)
      val x1472_wr_ofs = List[UInt](x2059.r)
      val x1472_wr_en = List[Bool](true.B)
      val x1472_wr_data = List[UInt](x1471_sum.r)
      x1019_tileC_sram_1.connectWPort(1472, x1472_wr_banks, x1472_wr_ofs, x1472_wr_data, x1472_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x2060 & x2061 & x2058 & x2055 & x2056 & x2057))
      val x1473_wr_banks = List[UInt](2L.FP(true, 32, 0).r,1L.FP(true, 32, 0).r)
      val x1473_wr_ofs = List[UInt](x2059.r)
      val x1473_wr_en = List[Bool](true.B)
      val x1473_wr_data = List[UInt](x1471_sum.r)
      x1018_tileC_sram_0.connectWPort(1473, x1473_wr_banks, x1473_wr_ofs, x1473_wr_data, x1473_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x2060 & x2061 & x2058 & x2055 & x2056 & x2057))
    }
    val module = Module(new x1474_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1474_inr_Foreach **/
