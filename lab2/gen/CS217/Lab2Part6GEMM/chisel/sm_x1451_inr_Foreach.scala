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

/** Hierarchy: x1451 -> x1521 -> x1522 -> x1637 -> x1638 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1451_inr_Foreach **/
class x1451_inr_Foreach_kernel(
  list_b925: List[Bool],
  list_b1409: List[FixedPoint],
  list_x934_tileA_sram_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 11.4.toInt, myName = "x1451_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(4.0.toInt, 2 + _root_.utils.math.log2Up(4.0.toInt), "x1451_inr_Foreach_iiCtr"))
  
  abstract class x1451_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b1409 = Input(new FixedPoint(true, 32, 0))
      val in_x934_tileA_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x934_tileA_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b925 = Input(Bool())
      val in_b1163 = Input(new FixedPoint(true, 32, 0))
      val in_x1017_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1006 = Input(Bool())
      val in_b1167 = Input(Bool())
      val in_b1413 = Input(Bool())
      val in_x1018_tileC_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b914 = Input(Bool())
      val in_x1019_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1019_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def b1409 = {io.in_b1409} 
    def x934_tileA_sram_0 = {io.in_x934_tileA_sram_0} ; io.in_x934_tileA_sram_0 := DontCare
    def b925 = {io.in_b925} 
    def b1163 = {io.in_b1163} 
    def x1017_tileB_sram_0 = {io.in_x1017_tileB_sram_0} ; io.in_x1017_tileB_sram_0 := DontCare
    def b1006 = {io.in_b1006} 
    def b1167 = {io.in_b1167} 
    def b1413 = {io.in_b1413} 
    def x1018_tileC_sram_0 = {io.in_x1018_tileC_sram_0} ; io.in_x1018_tileC_sram_0 := DontCare
    def b914 = {io.in_b914} 
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
  }
  def connectWires0(module: x1451_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b1409 <> b1409
    x934_tileA_sram_0.connectLedger(module.io.in_x934_tileA_sram_0)
    module.io.in_b925 <> b925
    module.io.in_b1163 <> b1163
    x1017_tileB_sram_0.connectLedger(module.io.in_x1017_tileB_sram_0)
    module.io.in_b1006 <> b1006
    module.io.in_b1167 <> b1167
    module.io.in_b1413 <> b1413
    x1018_tileC_sram_0.connectLedger(module.io.in_x1018_tileC_sram_0)
    module.io.in_b914 <> b914
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
  }
  val b925 = list_b925(0)
  val b1006 = list_b925(1)
  val b1167 = list_b925(2)
  val b1413 = list_b925(3)
  val b914 = list_b925(4)
  val b1409 = list_b1409(0)
  val b1163 = list_b1409(1)
  val x934_tileA_sram_0 = list_x934_tileA_sram_0(0)
  val x1017_tileB_sram_0 = list_x934_tileA_sram_0(1)
  val x1018_tileC_sram_0 = list_x934_tileA_sram_0(2)
  val x1019_tileC_sram_1 = list_x934_tileA_sram_0(3)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1451_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1451_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1451_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1451_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x1451_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x1451_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1451_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1451_instrctr, cycles_x1451_inr_Foreach.io.count, iters_x1451_inr_Foreach.io.count, 0.U, 0.U)
      val b1429 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1429.suggestName("b1429")
      val b1430 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1430.suggestName("b1430")
      val x1433 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1433""")
      x1433.r := Math.arith_right_shift_div(b1163, 2, Some(0.2), true.B,"x1433").r
      val x1434 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1434""")
      x1434.r := Math.arith_left_shift(x1433, 2, Some(0.2), true.B,"x1434").r
      val x1435 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1435""")
      x1435.r := Math.arith_right_shift_div(b1409, 2, Some(0.2), true.B,"x1435").r
      val x1436_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1436_sum""")
      x1436_sum.r := Math.add(x1434,x1435,Some(1.0), true.B, Truncate, Wrapping, "x1436_sum").r
      val x2022 = Wire(Bool()).suggestName("x2022_b1430_D7") 
      x2022.r := getRetimed(b1430.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2023 = Wire(Bool()).suggestName("x2023_b925_D7") 
      x2023.r := getRetimed(b925.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2024 = Wire(Bool()).suggestName("x2024_b1006_D7") 
      x2024.r := getRetimed(b1006.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2025 = Wire(Bool()).suggestName("x2025_b1167_D7") 
      x2025.r := getRetimed(b1167.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2026 = Wire(Bool()).suggestName("x2026_b1413_D7") 
      x2026.r := getRetimed(b1413.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2027 = Wire(Bool()).suggestName("x2027_b914_D7") 
      x2027.r := getRetimed(b914.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2028 = Wire(new FixedPoint(true, 32, 0)).suggestName("x2028_x1436_sum_D6") 
      x2028.r := getRetimed(x1436_sum.r, 6.toInt, io.sigsIn.backpressure & true.B)
      val x1437_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1437_rd""")
      val x1437_rd_banks = List[UInt](2L.FP(true, 32, 0).r,0L.FP(true, 32, 0).r)
      val x1437_rd_ofs = List[UInt](x2028.r)
      val x1437_rd_en = List[Bool](true.B)
      val x1437_rd_shared_en = ((io.sigsIn.forwardpressure).DS(7.4.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(7.4.toInt, rr, io.sigsIn.backpressure & true.B) && x2025 & x2022 & x2023 & x2027 & x2026 & x2024 ).suggestName("x1437_rd_shared_en")
      x1437_rd.toSeq.zip(x1018_tileC_sram_0.connectRPort(1437, x1437_rd_banks, x1437_rd_ofs, io.sigsIn.backpressure, x1437_rd_en.map(_ && x1437_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x1438 = VecApply(x1437,0)
      val x1438_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1438_elem_0""")
      x1438_elem_0.r := x1437_rd(0).r
      val x1439 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1439""")
      x1439.r := Math.arith_left_shift(x1433, 4, Some(0.2), true.B,"x1439").r
      val x1440_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1440_sum""")
      x1440_sum.r := Math.add(x1439,b1429,Some(1.0), true.B, Truncate, Wrapping, "x1440_sum").r
      val x2029 = Wire(Bool()).suggestName("x2029_b1430_D1") 
      x2029.r := getRetimed(b1430.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x2030 = Wire(Bool()).suggestName("x2030_b925_D1") 
      x2030.r := getRetimed(b925.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x2031 = Wire(Bool()).suggestName("x2031_b1006_D1") 
      x2031.r := getRetimed(b1006.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x2032 = Wire(Bool()).suggestName("x2032_b1167_D1") 
      x2032.r := getRetimed(b1167.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x2033 = Wire(Bool()).suggestName("x2033_b1413_D1") 
      x2033.r := getRetimed(b1413.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x2034 = Wire(Bool()).suggestName("x2034_b914_D1") 
      x2034.r := getRetimed(b914.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1441_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1441_rd""")
      val x1441_rd_banks = List[UInt](2L.FP(true, 32, 0).r,0L.FP(true, 32, 0).r)
      val x1441_rd_ofs = List[UInt](x1440_sum.r)
      val x1441_rd_en = List[Bool](true.B)
      val x1441_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) && x2033 & x2032 & x2034 & x2030 & x2029 & x2031 ).suggestName("x1441_rd_shared_en")
      x1441_rd.toSeq.zip(x934_tileA_sram_0.connectRPort(1441, x1441_rd_banks, x1441_rd_ofs, io.sigsIn.backpressure, x1441_rd_en.map(_ && x1441_rd_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      // x1442 = VecApply(x1441,0)
      val x1442_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1442_elem_0""")
      x1442_elem_0.r := x1441_rd(0).r
      val x1445_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1445_rd""")
      val x1445_rd_banks = List[UInt](0.U,0L.FP(true, 32, 0).r)
      val x1445_rd_ofs = List[UInt](0.U)
      val x1445_rd_en = List[Bool](true.B)
      val x1445_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && x2033 & x2032 & x2034 & x2030 & x2029 & x2031 ).suggestName("x1445_rd_shared_en")
      x1445_rd.toSeq.zip(x1017_tileB_sram_0.connectRPort(1445, x1445_rd_banks, x1445_rd_ofs, io.sigsIn.backpressure, x1445_rd_en.map(_ && x1445_rd_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      // x1446 = VecApply(x1445,0)
      val x1446_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1446_elem_0""")
      x1446_elem_0.r := x1445_rd(0).r
      val x1447_mul = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1447_mul""")
      x1447_mul.r := (Math.mul(x1442_elem_0, x1446_elem_0, Some(6.0), true.B, Truncate, Wrapping, "x1447_mul")).r
      val x1448_sum = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1448_sum""")
      x1448_sum.r := Math.add(x1438_elem_0,x1447_mul,Some(1.0), true.B, Truncate, Wrapping, "x1448_sum").r
      val x2035 = Wire(Bool()).suggestName("x2035_b1430_D10") 
      x2035.r := getRetimed(b1430.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2036 = Wire(Bool()).suggestName("x2036_b925_D10") 
      x2036.r := getRetimed(b925.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2037 = Wire(Bool()).suggestName("x2037_b1006_D10") 
      x2037.r := getRetimed(b1006.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2038 = Wire(Bool()).suggestName("x2038_b1167_D10") 
      x2038.r := getRetimed(b1167.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2039 = Wire(Bool()).suggestName("x2039_b1413_D10") 
      x2039.r := getRetimed(b1413.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2040 = Wire(Bool()).suggestName("x2040_b914_D10") 
      x2040.r := getRetimed(b914.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2041 = Wire(new FixedPoint(true, 32, 0)).suggestName("x2041_x1436_sum_D9") 
      x2041.r := getRetimed(x1436_sum.r, 9.toInt, io.sigsIn.backpressure & true.B)
      val x1449_wr_banks = List[UInt](2L.FP(true, 32, 0).r,0L.FP(true, 32, 0).r)
      val x1449_wr_ofs = List[UInt](x2041.r)
      val x1449_wr_en = List[Bool](true.B)
      val x1449_wr_data = List[UInt](x1448_sum.r)
      x1019_tileC_sram_1.connectWPort(1449, x1449_wr_banks, x1449_wr_ofs, x1449_wr_data, x1449_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x2036 & x2039 & x2040 & x2038 & x2037 & x2035))
      val x1450_wr_banks = List[UInt](2L.FP(true, 32, 0).r,0L.FP(true, 32, 0).r)
      val x1450_wr_ofs = List[UInt](x2041.r)
      val x1450_wr_en = List[Bool](true.B)
      val x1450_wr_data = List[UInt](x1448_sum.r)
      x1018_tileC_sram_0.connectWPort(1450, x1450_wr_banks, x1450_wr_ofs, x1450_wr_data, x1450_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x2036 & x2039 & x2040 & x2038 & x2037 & x2035))
    }
    val module = Module(new x1451_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1451_inr_Foreach **/
