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

/** Hierarchy: x1085 -> x1086 -> x1087 -> x1811 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1085_inr_Foreach **/
class x1085_inr_Foreach_kernel(
  list_x1055_reg: List[StandardInterface],
  list_x1017_tileB_sram_0: List[NBufInterface],
  list_b1053: List[FixedPoint],
  list_b1054: List[Bool],
  list_x1022: List[DecoupledIO[AppLoadData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 3.2.toInt, myName = "x1085_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1085_inr_Foreach_iiCtr"))
  
  abstract class x1085_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x1055_reg = Flipped(new StandardInterface(ModuleParams.getParams("x1055_reg_p").asInstanceOf[MemParams] ))
      val in_x1017_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1054 = Input(Bool())
      val in_x1022 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x1022_p").asInstanceOf[(Int, Int)] )))
      val in_b1053 = Input(new FixedPoint(true, 32, 0))
      val in_x1056_reg = Flipped(new StandardInterface(ModuleParams.getParams("x1056_reg_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x1055_reg = {io.in_x1055_reg} ; io.in_x1055_reg := DontCare
    def x1017_tileB_sram_0 = {io.in_x1017_tileB_sram_0} ; io.in_x1017_tileB_sram_0 := DontCare
    def b1054 = {io.in_b1054} 
    def x1022 = {io.in_x1022} 
    def b1053 = {io.in_b1053} 
    def x1056_reg = {io.in_x1056_reg} ; io.in_x1056_reg := DontCare
  }
  def connectWires0(module: x1085_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x1055_reg.connectLedger(module.io.in_x1055_reg)
    x1017_tileB_sram_0.connectLedger(module.io.in_x1017_tileB_sram_0)
    module.io.in_b1054 <> b1054
    module.io.in_x1022 <> x1022
    module.io.in_b1053 <> b1053
    x1056_reg.connectLedger(module.io.in_x1056_reg)
  }
  val x1055_reg = list_x1055_reg(0)
  val x1056_reg = list_x1055_reg(1)
  val x1017_tileB_sram_0 = list_x1017_tileB_sram_0(0)
  val b1053 = list_b1053(0)
  val b1054 = list_b1054(0)
  val x1022 = list_x1022(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1085_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1085_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1085_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1085_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x1085_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x1085_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1085_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x1085_inr_Foreach = Module(new InstrumentationCounter())
      val idles_x1085_inr_Foreach = Module(new InstrumentationCounter())
      stalls_x1085_inr_Foreach.io.enable := io.sigsIn.baseEn & ~(true.B)
      idles_x1085_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((x1022.valid) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X1085_instrctr, cycles_x1085_inr_Foreach.io.count, iters_x1085_inr_Foreach.io.count, stalls_x1085_inr_Foreach.io.count, idles_x1085_inr_Foreach.io.count)
      val b1070 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1070.suggestName("b1070")
      val b1071 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1071.suggestName("b1071")
      val x1072_rd_x1055 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1072_rd_x1055""")
      val x1072_rd_x1055_banks = List[UInt]()
      val x1072_rd_x1055_ofs = List[UInt]()
      val x1072_rd_x1055_en = List[Bool](true.B)
      val x1072_rd_x1055_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1072_rd_x1055_shared_en")
      x1072_rd_x1055.toSeq.zip(x1055_reg.connectRPort(1072, x1072_rd_x1055_banks, x1072_rd_x1055_ofs, io.sigsIn.backpressure, x1072_rd_x1055_en.map(_ && x1072_rd_x1055_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1073 = Wire(Bool()).suggestName("""x1073""")
      x1073.r := Math.lte(x1072_rd_x1055, b1070, Some(0.4), true.B,"x1073").r
      val x1074_rd_x1056 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1074_rd_x1056""")
      val x1074_rd_x1056_banks = List[UInt]()
      val x1074_rd_x1056_ofs = List[UInt]()
      val x1074_rd_x1056_en = List[Bool](true.B)
      val x1074_rd_x1056_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1074_rd_x1056_shared_en")
      x1074_rd_x1056.toSeq.zip(x1056_reg.connectRPort(1074, x1074_rd_x1056_banks, x1074_rd_x1056_ofs, io.sigsIn.backpressure, x1074_rd_x1056_en.map(_ && x1074_rd_x1056_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1075 = Wire(Bool()).suggestName("""x1075""")
      x1075.r := Math.lt(b1070, x1074_rd_x1056, Some(0.4), true.B,"x1075").r
      val x1076 = Wire(Bool()).suggestName("""x1076""")
      x1076 := x1073 & x1075
      val x1077_sub = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1077_sub""")
      x1077_sub.r := Math.sub(b1070,x1072_rd_x1055,Some(1.0), true.B, Truncate, Wrapping, "x1077_sub").r
      val x1078 = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1078""")
      x1022.ready := b1071 & b1054 & (io.sigsIn.datapathEn) 
      (0 until 1).map{ i => x1078(i).r := x1022.bits.rdata(i).r }
      val x1838 = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("x1838_x1078_D1") 
      (0 until 1).foreach{i => x1838(i).r := getRetimed(x1078(i).r, 1.toInt, io.sigsIn.backpressure & true.B)}
      // x1079 = VecApply(x1838,0)
      val x1079_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1079_elem_0""")
      x1079_elem_0.r := x1838(0).r
      val x1800 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1800""")
      x1800.r := Math.and(x1077_sub,3L.FP(true, 32, 0),Some(0.2), true.B,"x1800").r
      val x1081 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1081""")
      x1081.r := Math.arith_left_shift(b1053, 2, Some(0.2), true.B,"x1081").r
      val x1082 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1082""")
      x1082.r := Math.arith_right_shift_div(x1077_sub, 2, Some(0.2), true.B,"x1082").r
      val x1839 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1839_x1081_D1") 
      x1839.r := getRetimed(x1081.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1083_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1083_sum""")
      x1083_sum.r := Math.add(x1839,x1082,Some(1.0), true.B, Truncate, Wrapping, "x1083_sum").r
      val x1840 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1840_x1800_D1") 
      x1840.r := getRetimed(x1800.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1841 = Wire(new FixedPoint(true, 24, 8)).suggestName("x1841_x1079_elem_0_D1") 
      x1841.r := getRetimed(x1079_elem_0.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1842 = Wire(Bool()).suggestName("x1842_b1054_D2") 
      x1842.r := getRetimed(b1054.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x1843 = Wire(Bool()).suggestName("x1843_b1071_D2") 
      x1843.r := getRetimed(b1071.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x1844 = Wire(Bool()).suggestName("x1844_x1076_D2") 
      x1844.r := getRetimed(x1076.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x1084_wr_banks = List[UInt](0L.FP(true, 32, 0).r,x1840.r)
      val x1084_wr_ofs = List[UInt](x1083_sum.r)
      val x1084_wr_en = List[Bool](true.B)
      val x1084_wr_data = List[UInt](x1841.r)
      x1017_tileB_sram_0.connectWPort(1084, x1084_wr_banks, x1084_wr_ofs, x1084_wr_data, x1084_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.2.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x1844 & x1843 & x1842))
    }
    val module = Module(new x1085_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1085_inr_Foreach **/
