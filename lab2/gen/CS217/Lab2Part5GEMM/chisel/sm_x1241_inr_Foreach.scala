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

/** Hierarchy: x1241 -> x1242 -> x1247 -> x1248 -> x1335 -> x1250 -> x1251 -> x810 **/
/** BEGIN None x1241_inr_Foreach **/
class x1241_inr_Foreach_kernel(
  list_b1194: List[FixedPoint],
  list_x1018_tileC_sram_1: List[NBufInterface],
  list_x1196_reg: List[StandardInterface],
  list_x1190: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 4.0.toInt, myName = "x1241_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1241_inr_Foreach_iiCtr"))
  
  abstract class x1241_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x1190 = Decoupled(new AppStoreData(ModuleParams.getParams("x1190_p").asInstanceOf[(Int,Int)] ))
      val in_x1196_reg = Flipped(new StandardInterface(ModuleParams.getParams("x1196_reg_p").asInstanceOf[MemParams] ))
      val in_b1194 = Input(new FixedPoint(true, 32, 0))
      val in_x1018_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_x1197_reg = Flipped(new StandardInterface(ModuleParams.getParams("x1197_reg_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x1190 = {io.in_x1190} 
    def x1196_reg = {io.in_x1196_reg} ; io.in_x1196_reg := DontCare
    def b1194 = {io.in_b1194} 
    def x1018_tileC_sram_1 = {io.in_x1018_tileC_sram_1} ; io.in_x1018_tileC_sram_1 := DontCare
    def x1197_reg = {io.in_x1197_reg} ; io.in_x1197_reg := DontCare
  }
  def connectWires0(module: x1241_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x1190 <> x1190
    x1196_reg.connectLedger(module.io.in_x1196_reg)
    module.io.in_b1194 <> b1194
    x1018_tileC_sram_1.connectLedger(module.io.in_x1018_tileC_sram_1)
    x1197_reg.connectLedger(module.io.in_x1197_reg)
  }
  val b1194 = list_b1194(0)
  val x1018_tileC_sram_1 = list_x1018_tileC_sram_1(0)
  val x1196_reg = list_x1196_reg(0)
  val x1197_reg = list_x1196_reg(1)
  val x1190 = list_x1190(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1241_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1241_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1241_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1241_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x1241_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x1241_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1241_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x1241_inr_Foreach = Module(new InstrumentationCounter())
      val idles_x1241_inr_Foreach = Module(new InstrumentationCounter())
      stalls_x1241_inr_Foreach.io.enable := io.sigsIn.baseEn & ~(x1190.ready)
      idles_x1241_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X1241_instrctr, cycles_x1241_inr_Foreach.io.count, iters_x1241_inr_Foreach.io.count, stalls_x1241_inr_Foreach.io.count, idles_x1241_inr_Foreach.io.count)
      val b1226 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1226.suggestName("b1226")
      val b1227 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1227.suggestName("b1227")
      val x1228_rd_x1196 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1228_rd_x1196""")
      val x1228_rd_x1196_banks = List[UInt]()
      val x1228_rd_x1196_ofs = List[UInt]()
      val x1228_rd_x1196_en = List[Bool](true.B)
      val x1228_rd_x1196_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1228_rd_x1196_shared_en")
      x1228_rd_x1196.toSeq.zip(x1196_reg.connectRPort(1228, x1228_rd_x1196_banks, x1228_rd_x1196_ofs, io.sigsIn.backpressure, x1228_rd_x1196_en.map(_ && x1228_rd_x1196_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1229 = Wire(Bool()).suggestName("""x1229""")
      val ensig0 = Wire(Bool())
      ensig0 := x1190.ready
      x1229.r := Math.lte(x1228_rd_x1196, b1226, Some(0.4), ensig0,"x1229").r
      val x1230_rd_x1197 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1230_rd_x1197""")
      val x1230_rd_x1197_banks = List[UInt]()
      val x1230_rd_x1197_ofs = List[UInt]()
      val x1230_rd_x1197_en = List[Bool](true.B)
      val x1230_rd_x1197_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1230_rd_x1197_shared_en")
      x1230_rd_x1197.toSeq.zip(x1197_reg.connectRPort(1230, x1230_rd_x1197_banks, x1230_rd_x1197_ofs, io.sigsIn.backpressure, x1230_rd_x1197_en.map(_ && x1230_rd_x1197_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1231 = Wire(Bool()).suggestName("""x1231""")
      x1231.r := Math.lt(b1226, x1230_rd_x1197, Some(0.4), ensig0,"x1231").r
      val x1232 = Wire(Bool()).suggestName("""x1232""")
      x1232 := x1229 & x1231
      val x1233_sub = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1233_sub""")
      x1233_sub.r := Math.sub(b1226,x1228_rd_x1196,Some(1.0), ensig0, Truncate, Wrapping, "x1233_sub").r
      val x1235 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1235""")
      x1235.r := Math.arith_left_shift(b1194, 4, Some(0.2), ensig0,"x1235").r
      val x1407 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1407_x1235_D1") 
      x1407.r := getRetimed(x1235.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1236_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1236_sum""")
      x1236_sum.r := Math.add(x1407,x1233_sub,Some(1.0), ensig0, Truncate, Wrapping, "x1236_sum").r
      val x1408 = Wire(Bool()).suggestName("x1408_x1232_D2") 
      x1408.r := getRetimed(x1232.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x1409 = Wire(Bool()).suggestName("x1409_b1227_D2") 
      x1409.r := getRetimed(b1227.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x1237_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1237_rd""")
      val x1237_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x1237_rd_ofs = List[UInt](x1236_sum.r)
      val x1237_rd_en = List[Bool](true.B)
      val x1237_rd_shared_en = ((io.sigsIn.forwardpressure).DS(2.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.0.toInt, rr, io.sigsIn.backpressure & true.B) && x1408 & x1409 ).suggestName("x1237_rd_shared_en")
      x1237_rd.toSeq.zip(x1018_tileC_sram_1.connectRPort(1237, x1237_rd_banks, x1237_rd_ofs, io.sigsIn.backpressure, x1237_rd_en.map(_ && x1237_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x1238 = VecApply(x1237,0)
      val x1238_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1238_elem_0""")
      x1238_elem_0.r := x1237_rd(0).r
      val x1410 = Wire(Bool()).suggestName("x1410_x1232_D4") 
      x1410.r := getRetimed(x1232.r, 4.toInt, io.sigsIn.backpressure & true.B)
      val x1239_tuple = Wire(UInt(33.W)).suggestName("""x1239_tuple""")
      x1239_tuple.r := ConvAndCat(x1410,x1238_elem_0.r)
      val x1411 = Wire(Bool()).suggestName("x1411_b1227_D4") 
      x1411.r := getRetimed(b1227.r, 4.toInt, io.sigsIn.backpressure & true.B)
      x1190.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(4.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x1411 & io.sigsIn.backpressure
      x1190.bits.wdata(0) := x1239_tuple(31,0)
      x1190.bits.wstrb := x1239_tuple(32,32)
    }
    val module = Module(new x1241_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1241_inr_Foreach **/
