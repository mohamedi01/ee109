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

/** Hierarchy: x1360 -> x1407 -> x1408 -> x1637 -> x1638 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1360_inr_Foreach **/
class x1360_inr_Foreach_kernel(
  list_b925: List[Bool],
  list_b1296: List[FixedPoint],
  list_x934_tileA_sram_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 11.4.toInt, myName = "x1360_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(4.0.toInt, 2 + _root_.utils.math.log2Up(4.0.toInt), "x1360_inr_Foreach_iiCtr"))
  
  abstract class x1360_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x934_tileA_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x934_tileA_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b925 = Input(Bool())
      val in_b1296 = Input(new FixedPoint(true, 32, 0))
      val in_x1017_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1006 = Input(Bool())
      val in_b1162 = Input(new FixedPoint(true, 32, 0))
      val in_b1166 = Input(Bool())
      val in_x1018_tileC_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1300 = Input(Bool())
      val in_b914 = Input(Bool())
      val in_x1019_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1019_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x934_tileA_sram_0 = {io.in_x934_tileA_sram_0} ; io.in_x934_tileA_sram_0 := DontCare
    def b925 = {io.in_b925} 
    def b1296 = {io.in_b1296} 
    def x1017_tileB_sram_0 = {io.in_x1017_tileB_sram_0} ; io.in_x1017_tileB_sram_0 := DontCare
    def b1006 = {io.in_b1006} 
    def b1162 = {io.in_b1162} 
    def b1166 = {io.in_b1166} 
    def x1018_tileC_sram_0 = {io.in_x1018_tileC_sram_0} ; io.in_x1018_tileC_sram_0 := DontCare
    def b1300 = {io.in_b1300} 
    def b914 = {io.in_b914} 
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
  }
  def connectWires0(module: x1360_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x934_tileA_sram_0.connectLedger(module.io.in_x934_tileA_sram_0)
    module.io.in_b925 <> b925
    module.io.in_b1296 <> b1296
    x1017_tileB_sram_0.connectLedger(module.io.in_x1017_tileB_sram_0)
    module.io.in_b1006 <> b1006
    module.io.in_b1162 <> b1162
    module.io.in_b1166 <> b1166
    x1018_tileC_sram_0.connectLedger(module.io.in_x1018_tileC_sram_0)
    module.io.in_b1300 <> b1300
    module.io.in_b914 <> b914
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
  }
  val b925 = list_b925(0)
  val b1006 = list_b925(1)
  val b1166 = list_b925(2)
  val b1300 = list_b925(3)
  val b914 = list_b925(4)
  val b1296 = list_b1296(0)
  val b1162 = list_b1296(1)
  val x934_tileA_sram_0 = list_x934_tileA_sram_0(0)
  val x1017_tileB_sram_0 = list_x934_tileA_sram_0(1)
  val x1018_tileC_sram_0 = list_x934_tileA_sram_0(2)
  val x1019_tileC_sram_1 = list_x934_tileA_sram_0(3)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1360_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1360_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1360_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1360_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x1360_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x1360_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1360_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1360_instrctr, cycles_x1360_inr_Foreach.io.count, iters_x1360_inr_Foreach.io.count, 0.U, 0.U)
      val b1338 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1338.suggestName("b1338")
      val b1339 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1339.suggestName("b1339")
      val x1342 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1342""")
      x1342.r := Math.arith_right_shift_div(b1162, 2, Some(0.2), true.B,"x1342").r
      val x1343 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1343""")
      x1343.r := Math.arith_left_shift(x1342, 2, Some(0.2), true.B,"x1343").r
      val x1344 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1344""")
      x1344.r := Math.arith_right_shift_div(b1296, 2, Some(0.2), true.B,"x1344").r
      val x1345_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1345_sum""")
      x1345_sum.r := Math.add(x1343,x1344,Some(1.0), true.B, Truncate, Wrapping, "x1345_sum").r
      val x1962 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1962_x1345_sum_D6") 
      x1962.r := getRetimed(x1345_sum.r, 6.toInt, io.sigsIn.backpressure & true.B)
      val x1963 = Wire(Bool()).suggestName("x1963_b925_D7") 
      x1963.r := getRetimed(b925.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1964 = Wire(Bool()).suggestName("x1964_b1006_D7") 
      x1964.r := getRetimed(b1006.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1965 = Wire(Bool()).suggestName("x1965_b1339_D7") 
      x1965.r := getRetimed(b1339.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1966 = Wire(Bool()).suggestName("x1966_b1166_D7") 
      x1966.r := getRetimed(b1166.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1967 = Wire(Bool()).suggestName("x1967_b1300_D7") 
      x1967.r := getRetimed(b1300.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1968 = Wire(Bool()).suggestName("x1968_b914_D7") 
      x1968.r := getRetimed(b914.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1346_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1346_rd""")
      val x1346_rd_banks = List[UInt](1L.FP(true, 32, 0).r,0.U)
      val x1346_rd_ofs = List[UInt](x1962.r)
      val x1346_rd_en = List[Bool](true.B)
      val x1346_rd_shared_en = ((io.sigsIn.forwardpressure).DS(7.4.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(7.4.toInt, rr, io.sigsIn.backpressure & true.B) && x1966 & x1968 & x1963 & x1967 & x1964 & x1965 ).suggestName("x1346_rd_shared_en")
      x1346_rd.toSeq.zip(x1018_tileC_sram_0.connectRPort(1346, x1346_rd_banks, x1346_rd_ofs, io.sigsIn.backpressure, x1346_rd_en.map(_ && x1346_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x1347 = VecApply(x1346,0)
      val x1347_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1347_elem_0""")
      x1347_elem_0.r := x1346_rd(0).r
      val x1350_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1350_rd""")
      val x1350_rd_banks = List[UInt](1L.FP(true, 32, 0).r,0.U)
      val x1350_rd_ofs = List[UInt](0.U)
      val x1350_rd_en = List[Bool](true.B)
      val x1350_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1350_rd_shared_en")
      x1350_rd.toSeq.zip(x934_tileA_sram_0.connectRPort(1350, x1350_rd_banks, x1350_rd_ofs, io.sigsIn.backpressure, x1350_rd_en.map(_ && x1350_rd_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      // x1351 = VecApply(x1350,0)
      val x1351_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1351_elem_0""")
      x1351_elem_0.r := x1350_rd(0).r
      val x1354_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1354_rd""")
      val x1354_rd_banks = List[UInt](0.U,0.U)
      val x1354_rd_ofs = List[UInt](0.U)
      val x1354_rd_en = List[Bool](true.B)
      val x1354_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1354_rd_shared_en")
      x1354_rd.toSeq.zip(x1017_tileB_sram_0.connectRPort(1354, x1354_rd_banks, x1354_rd_ofs, io.sigsIn.backpressure, x1354_rd_en.map(_ && x1354_rd_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      // x1355 = VecApply(x1354,0)
      val x1355_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1355_elem_0""")
      x1355_elem_0.r := x1354_rd(0).r
      val x1356_mul = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1356_mul""")
      x1356_mul.r := (Math.mul(x1351_elem_0, x1355_elem_0, Some(6.0), true.B, Truncate, Wrapping, "x1356_mul")).r
      val x1357_sum = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1357_sum""")
      x1357_sum.r := Math.add(x1347_elem_0,x1356_mul,Some(1.0), true.B, Truncate, Wrapping, "x1357_sum").r
      val x1975 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1975_x1345_sum_D9") 
      x1975.r := getRetimed(x1345_sum.r, 9.toInt, io.sigsIn.backpressure & true.B)
      val x1976 = Wire(Bool()).suggestName("x1976_b925_D10") 
      x1976.r := getRetimed(b925.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1977 = Wire(Bool()).suggestName("x1977_b1006_D10") 
      x1977.r := getRetimed(b1006.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1978 = Wire(Bool()).suggestName("x1978_b1339_D10") 
      x1978.r := getRetimed(b1339.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1979 = Wire(Bool()).suggestName("x1979_b1166_D10") 
      x1979.r := getRetimed(b1166.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1980 = Wire(Bool()).suggestName("x1980_b1300_D10") 
      x1980.r := getRetimed(b1300.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1981 = Wire(Bool()).suggestName("x1981_b914_D10") 
      x1981.r := getRetimed(b914.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1358_wr_banks = List[UInt](1L.FP(true, 32, 0).r,0.U)
      val x1358_wr_ofs = List[UInt](x1975.r)
      val x1358_wr_en = List[Bool](true.B)
      val x1358_wr_data = List[UInt](x1357_sum.r)
      x1019_tileC_sram_1.connectWPort(1358, x1358_wr_banks, x1358_wr_ofs, x1358_wr_data, x1358_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x1977 & x1980 & x1981 & x1979 & x1978 & x1976))
      val x1359_wr_banks = List[UInt](1L.FP(true, 32, 0).r,0.U)
      val x1359_wr_ofs = List[UInt](x1975.r)
      val x1359_wr_en = List[Bool](true.B)
      val x1359_wr_data = List[UInt](x1357_sum.r)
      x1018_tileC_sram_0.connectWPort(1359, x1359_wr_banks, x1359_wr_ofs, x1359_wr_data, x1359_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x1977 & x1980 & x1981 & x1979 & x1978 & x1976))
    }
    val module = Module(new x1360_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1360_inr_Foreach **/
