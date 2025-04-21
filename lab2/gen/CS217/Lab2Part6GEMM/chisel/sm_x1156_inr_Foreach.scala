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

/** Hierarchy: x1156 -> x1157 -> x1158 -> x1811 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1156_inr_Foreach **/
class x1156_inr_Foreach_kernel(
  list_x1090: List[DecoupledIO[AppLoadData]],
  list_x1123_reg: List[StandardInterface],
  list_b1121: List[FixedPoint],
  list_x1018_tileC_sram_0: List[NBufInterface],
  list_b1122: List[Bool],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 3.2.toInt, myName = "x1156_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1156_inr_Foreach_iiCtr"))
  
  abstract class x1156_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x1123_reg = Flipped(new StandardInterface(ModuleParams.getParams("x1123_reg_p").asInstanceOf[MemParams] ))
      val in_b1121 = Input(new FixedPoint(true, 32, 0))
      val in_x1124_reg = Flipped(new StandardInterface(ModuleParams.getParams("x1124_reg_p").asInstanceOf[MemParams] ))
      val in_x1090 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x1090_p").asInstanceOf[(Int, Int)] )))
      val in_b1122 = Input(Bool())
      val in_x1018_tileC_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_0_p").asInstanceOf[NBufParams] ))
      val in_x1019_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1019_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x1123_reg = {io.in_x1123_reg} ; io.in_x1123_reg := DontCare
    def b1121 = {io.in_b1121} 
    def x1124_reg = {io.in_x1124_reg} ; io.in_x1124_reg := DontCare
    def x1090 = {io.in_x1090} 
    def b1122 = {io.in_b1122} 
    def x1018_tileC_sram_0 = {io.in_x1018_tileC_sram_0} ; io.in_x1018_tileC_sram_0 := DontCare
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
  }
  def connectWires0(module: x1156_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x1123_reg.connectLedger(module.io.in_x1123_reg)
    module.io.in_b1121 <> b1121
    x1124_reg.connectLedger(module.io.in_x1124_reg)
    module.io.in_x1090 <> x1090
    module.io.in_b1122 <> b1122
    x1018_tileC_sram_0.connectLedger(module.io.in_x1018_tileC_sram_0)
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
  }
  val x1090 = list_x1090(0)
  val x1123_reg = list_x1123_reg(0)
  val x1124_reg = list_x1123_reg(1)
  val b1121 = list_b1121(0)
  val x1018_tileC_sram_0 = list_x1018_tileC_sram_0(0)
  val x1019_tileC_sram_1 = list_x1018_tileC_sram_0(1)
  val b1122 = list_b1122(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1156_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1156_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1156_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1156_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x1156_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x1156_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1156_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x1156_inr_Foreach = Module(new InstrumentationCounter())
      val idles_x1156_inr_Foreach = Module(new InstrumentationCounter())
      stalls_x1156_inr_Foreach.io.enable := io.sigsIn.baseEn & ~(true.B)
      idles_x1156_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((x1090.valid) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X1156_instrctr, cycles_x1156_inr_Foreach.io.count, iters_x1156_inr_Foreach.io.count, stalls_x1156_inr_Foreach.io.count, idles_x1156_inr_Foreach.io.count)
      val b1138 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1138.suggestName("b1138")
      val b1139 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1139.suggestName("b1139")
      val x1140_rd_x1123 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1140_rd_x1123""")
      val x1140_rd_x1123_banks = List[UInt]()
      val x1140_rd_x1123_ofs = List[UInt]()
      val x1140_rd_x1123_en = List[Bool](true.B)
      val x1140_rd_x1123_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1140_rd_x1123_shared_en")
      x1140_rd_x1123.toSeq.zip(x1123_reg.connectRPort(1140, x1140_rd_x1123_banks, x1140_rd_x1123_ofs, io.sigsIn.backpressure, x1140_rd_x1123_en.map(_ && x1140_rd_x1123_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1141 = Wire(Bool()).suggestName("""x1141""")
      x1141.r := Math.lte(x1140_rd_x1123, b1138, Some(0.4), true.B,"x1141").r
      val x1142_rd_x1124 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1142_rd_x1124""")
      val x1142_rd_x1124_banks = List[UInt]()
      val x1142_rd_x1124_ofs = List[UInt]()
      val x1142_rd_x1124_en = List[Bool](true.B)
      val x1142_rd_x1124_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1142_rd_x1124_shared_en")
      x1142_rd_x1124.toSeq.zip(x1124_reg.connectRPort(1142, x1142_rd_x1124_banks, x1142_rd_x1124_ofs, io.sigsIn.backpressure, x1142_rd_x1124_en.map(_ && x1142_rd_x1124_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1143 = Wire(Bool()).suggestName("""x1143""")
      x1143.r := Math.lt(b1138, x1142_rd_x1124, Some(0.4), true.B,"x1143").r
      val x1144 = Wire(Bool()).suggestName("""x1144""")
      x1144 := x1141 & x1143
      val x1145_sub = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1145_sub""")
      x1145_sub.r := Math.sub(b1138,x1140_rd_x1123,Some(1.0), true.B, Truncate, Wrapping, "x1145_sub").r
      val x1146 = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1146""")
      x1090.ready := b1139 & b1122 & (io.sigsIn.datapathEn) 
      (0 until 1).map{ i => x1146(i).r := x1090.bits.rdata(i).r }
      val x1854 = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("x1854_x1146_D1") 
      (0 until 1).foreach{i => x1854(i).r := getRetimed(x1146(i).r, 1.toInt, io.sigsIn.backpressure & true.B)}
      // x1147 = VecApply(x1854,0)
      val x1147_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1147_elem_0""")
      x1147_elem_0.r := x1854(0).r
      val x1804 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1804""")
      x1804.r := Math.and(b1121,3L.FP(true, 32, 0),Some(0.2), true.B,"x1804").r
      val x1805 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1805""")
      x1805.r := Math.and(x1145_sub,3L.FP(true, 32, 0),Some(0.2), true.B,"x1805").r
      val x1150 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1150""")
      x1150.r := Math.arith_right_shift_div(b1121, 2, Some(0.2), true.B,"x1150").r
      val x1151 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1151""")
      x1151.r := Math.arith_left_shift(x1150, 2, Some(0.2), true.B,"x1151").r
      val x1152 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1152""")
      x1152.r := Math.arith_right_shift_div(x1145_sub, 2, Some(0.2), true.B,"x1152").r
      val x1855 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1855_x1151_D1") 
      x1855.r := getRetimed(x1151.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1153_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1153_sum""")
      x1153_sum.r := Math.add(x1855,x1152,Some(1.0), true.B, Truncate, Wrapping, "x1153_sum").r
      val x1856 = Wire(new FixedPoint(true, 24, 8)).suggestName("x1856_x1147_elem_0_D1") 
      x1856.r := getRetimed(x1147_elem_0.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1857 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1857_x1804_D2") 
      x1857.r := getRetimed(x1804.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x1858 = Wire(Bool()).suggestName("x1858_b1139_D2") 
      x1858.r := getRetimed(b1139.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x1859 = Wire(Bool()).suggestName("x1859_x1144_D2") 
      x1859.r := getRetimed(x1144.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x1860 = Wire(Bool()).suggestName("x1860_b1122_D2") 
      x1860.r := getRetimed(b1122.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x1861 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1861_x1805_D1") 
      x1861.r := getRetimed(x1805.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1154_wr_banks = List[UInt](x1857.r,x1861.r)
      val x1154_wr_ofs = List[UInt](x1153_sum.r)
      val x1154_wr_en = List[Bool](true.B)
      val x1154_wr_data = List[UInt](x1856.r)
      x1019_tileC_sram_1.connectWPort(1154, x1154_wr_banks, x1154_wr_ofs, x1154_wr_data, x1154_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.2.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x1859 & x1858 & x1860))
      val x1155_wr_banks = List[UInt](x1857.r,x1861.r)
      val x1155_wr_ofs = List[UInt](x1153_sum.r)
      val x1155_wr_en = List[Bool](true.B)
      val x1155_wr_data = List[UInt](x1856.r)
      x1018_tileC_sram_0.connectWPort(1155, x1155_wr_banks, x1155_wr_ofs, x1155_wr_data, x1155_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.2.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x1859 & x1858 & x1860))
    }
    val module = Module(new x1156_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1156_inr_Foreach **/
