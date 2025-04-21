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

/** Hierarchy: x1151 -> x1152 -> x1153 -> x1334 -> x1335 -> x1250 -> x1251 -> x810 **/
/** BEGIN None x1151_inr_Foreach **/
class x1151_inr_Foreach_kernel(
  list_x1088: List[DecoupledIO[AppLoadData]],
  list_x1017_tileC_sram_0: List[NBufInterface],
  list_b1119: List[FixedPoint],
  list_b1120: List[Bool],
  list_x1121_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 3.0.toInt, myName = "x1151_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1151_inr_Foreach_iiCtr"))
  
  abstract class x1151_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b1119 = Input(new FixedPoint(true, 32, 0))
      val in_x1017_tileC_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileC_sram_0_p").asInstanceOf[NBufParams] ))
      val in_x1121_reg = Flipped(new StandardInterface(ModuleParams.getParams("x1121_reg_p").asInstanceOf[MemParams] ))
      val in_x1122_reg = Flipped(new StandardInterface(ModuleParams.getParams("x1122_reg_p").asInstanceOf[MemParams] ))
      val in_x1018_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_b1120 = Input(Bool())
      val in_x1088 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x1088_p").asInstanceOf[(Int, Int)] )))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def b1119 = {io.in_b1119} 
    def x1017_tileC_sram_0 = {io.in_x1017_tileC_sram_0} ; io.in_x1017_tileC_sram_0 := DontCare
    def x1121_reg = {io.in_x1121_reg} ; io.in_x1121_reg := DontCare
    def x1122_reg = {io.in_x1122_reg} ; io.in_x1122_reg := DontCare
    def x1018_tileC_sram_1 = {io.in_x1018_tileC_sram_1} ; io.in_x1018_tileC_sram_1 := DontCare
    def b1120 = {io.in_b1120} 
    def x1088 = {io.in_x1088} 
  }
  def connectWires0(module: x1151_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b1119 <> b1119
    x1017_tileC_sram_0.connectLedger(module.io.in_x1017_tileC_sram_0)
    x1121_reg.connectLedger(module.io.in_x1121_reg)
    x1122_reg.connectLedger(module.io.in_x1122_reg)
    x1018_tileC_sram_1.connectLedger(module.io.in_x1018_tileC_sram_1)
    module.io.in_b1120 <> b1120
    module.io.in_x1088 <> x1088
  }
  val x1088 = list_x1088(0)
  val x1017_tileC_sram_0 = list_x1017_tileC_sram_0(0)
  val x1018_tileC_sram_1 = list_x1017_tileC_sram_0(1)
  val b1119 = list_b1119(0)
  val b1120 = list_b1120(0)
  val x1121_reg = list_x1121_reg(0)
  val x1122_reg = list_x1121_reg(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1151_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1151_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1151_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1151_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x1151_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x1151_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1151_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x1151_inr_Foreach = Module(new InstrumentationCounter())
      val idles_x1151_inr_Foreach = Module(new InstrumentationCounter())
      stalls_x1151_inr_Foreach.io.enable := io.sigsIn.baseEn & ~(true.B)
      idles_x1151_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((x1088.valid) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X1151_instrctr, cycles_x1151_inr_Foreach.io.count, iters_x1151_inr_Foreach.io.count, stalls_x1151_inr_Foreach.io.count, idles_x1151_inr_Foreach.io.count)
      val b1136 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1136.suggestName("b1136")
      val b1137 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1137.suggestName("b1137")
      val x1138_rd_x1121 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1138_rd_x1121""")
      val x1138_rd_x1121_banks = List[UInt]()
      val x1138_rd_x1121_ofs = List[UInt]()
      val x1138_rd_x1121_en = List[Bool](true.B)
      val x1138_rd_x1121_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1138_rd_x1121_shared_en")
      x1138_rd_x1121.toSeq.zip(x1121_reg.connectRPort(1138, x1138_rd_x1121_banks, x1138_rd_x1121_ofs, io.sigsIn.backpressure, x1138_rd_x1121_en.map(_ && x1138_rd_x1121_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1139 = Wire(Bool()).suggestName("""x1139""")
      x1139.r := Math.lte(x1138_rd_x1121, b1136, Some(0.4), true.B,"x1139").r
      val x1140_rd_x1122 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1140_rd_x1122""")
      val x1140_rd_x1122_banks = List[UInt]()
      val x1140_rd_x1122_ofs = List[UInt]()
      val x1140_rd_x1122_en = List[Bool](true.B)
      val x1140_rd_x1122_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1140_rd_x1122_shared_en")
      x1140_rd_x1122.toSeq.zip(x1122_reg.connectRPort(1140, x1140_rd_x1122_banks, x1140_rd_x1122_ofs, io.sigsIn.backpressure, x1140_rd_x1122_en.map(_ && x1140_rd_x1122_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1141 = Wire(Bool()).suggestName("""x1141""")
      x1141.r := Math.lt(b1136, x1140_rd_x1122, Some(0.4), true.B,"x1141").r
      val x1142 = Wire(Bool()).suggestName("""x1142""")
      x1142 := x1139 & x1141
      val x1143_sub = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1143_sub""")
      x1143_sub.r := Math.sub(b1136,x1138_rd_x1121,Some(1.0), true.B, Truncate, Wrapping, "x1143_sub").r
      val x1144 = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1144""")
      x1088.ready := b1137 & b1120 & (io.sigsIn.datapathEn) 
      (0 until 1).map{ i => x1144(i).r := x1088.bits.rdata(i).r }
      val x1375 = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("x1375_x1144_D1") 
      (0 until 1).foreach{i => x1375(i).r := getRetimed(x1144(i).r, 1.toInt, io.sigsIn.backpressure & true.B)}
      // x1145 = VecApply(x1375,0)
      val x1145_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1145_elem_0""")
      x1145_elem_0.r := x1375(0).r
      val x1147 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1147""")
      x1147.r := Math.arith_left_shift(b1119, 4, Some(0.2), true.B,"x1147").r
      val x1376 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1376_x1147_D1") 
      x1376.r := getRetimed(x1147.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1148_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1148_sum""")
      x1148_sum.r := Math.add(x1376,x1143_sub,Some(1.0), true.B, Truncate, Wrapping, "x1148_sum").r
      val x1377 = Wire(Bool()).suggestName("x1377_x1142_D2") 
      x1377.r := getRetimed(x1142.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x1378 = Wire(Bool()).suggestName("x1378_b1137_D2") 
      x1378.r := getRetimed(b1137.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x1379 = Wire(new FixedPoint(true, 24, 8)).suggestName("x1379_x1145_elem_0_D1") 
      x1379.r := getRetimed(x1145_elem_0.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1380 = Wire(Bool()).suggestName("x1380_b1120_D2") 
      x1380.r := getRetimed(b1120.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x1149_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x1149_wr_ofs = List[UInt](x1148_sum.r)
      val x1149_wr_en = List[Bool](true.B)
      val x1149_wr_data = List[UInt](x1379.r)
      x1018_tileC_sram_1.connectWPort(1149, x1149_wr_banks, x1149_wr_ofs, x1149_wr_data, x1149_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x1377 & x1378 & x1380))
      val x1150_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x1150_wr_ofs = List[UInt](x1148_sum.r)
      val x1150_wr_en = List[Bool](true.B)
      val x1150_wr_data = List[UInt](x1379.r)
      x1017_tileC_sram_0.connectWPort(1150, x1150_wr_banks, x1150_wr_ofs, x1150_wr_data, x1150_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x1377 & x1378 & x1380))
    }
    val module = Module(new x1151_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1151_inr_Foreach **/
