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

/** Hierarchy: x314 -> x315 -> x316 -> x244 **/
/** BEGIN None x314_inr_Foreach **/
class x314_inr_Foreach_kernel(
  list_x284: List[DecoupledIO[AppLoadData]],
  list_x280_inSRAM_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 2.0.toInt, myName = "x314_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x314_inr_Foreach_iiCtr"))
  
  abstract class x314_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x284 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x284_p").asInstanceOf[(Int, Int)] )))
      val in_x280_inSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x280_inSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x293_reg = Flipped(new StandardInterface(ModuleParams.getParams("x293_reg_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x284 = {io.in_x284} 
    def x280_inSRAM_0 = {io.in_x280_inSRAM_0} ; io.in_x280_inSRAM_0 := DontCare
    def x293_reg = {io.in_x293_reg} ; io.in_x293_reg := DontCare
  }
  def connectWires0(module: x314_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x284 <> x284
    x280_inSRAM_0.connectLedger(module.io.in_x280_inSRAM_0)
    x293_reg.connectLedger(module.io.in_x293_reg)
  }
  val x284 = list_x284(0)
  val x280_inSRAM_0 = list_x280_inSRAM_0(0)
  val x293_reg = list_x280_inSRAM_0(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x314_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x314_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x314_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x314_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x314_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x314_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x314_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x314_inr_Foreach = Module(new InstrumentationCounter())
      val idles_x314_inr_Foreach = Module(new InstrumentationCounter())
      stalls_x314_inr_Foreach.io.enable := io.sigsIn.baseEn & ~(true.B)
      idles_x314_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((x284.valid) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X314_instrctr, cycles_x314_inr_Foreach.io.count, iters_x314_inr_Foreach.io.count, stalls_x314_inr_Foreach.io.count, idles_x314_inr_Foreach.io.count)
      val b305 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b305.suggestName("b305")
      val b306 = ~io.sigsIn.cchainOutputs.head.oobs(0); b306.suggestName("b306")
      val x307 = Wire(Bool()).suggestName("""x307""")
      x307.r := Math.lte(0L.FP(true, 32, 0), b305, Some(0.4), true.B,"x307").r
      val x308_rd_x293 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x308_rd_x293""")
      val x308_rd_x293_banks = List[UInt]()
      val x308_rd_x293_ofs = List[UInt]()
      val x308_rd_x293_en = List[Bool](true.B)
      val x308_rd_x293_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x308_rd_x293_shared_en")
      x308_rd_x293.toSeq.zip(x293_reg.connectRPort(308, x308_rd_x293_banks, x308_rd_x293_ofs, io.sigsIn.backpressure, x308_rd_x293_en.map(_ && x308_rd_x293_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x309 = Wire(Bool()).suggestName("""x309""")
      x309.r := Math.lt(b305, x308_rd_x293, Some(0.4), true.B,"x309").r
      val x310 = Wire(Bool()).suggestName("""x310""")
      x310 := x307 & x309
      val x311 = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x311""")
      x284.ready := b306 & (io.sigsIn.datapathEn) 
      (0 until 1).map{ i => x311(i).r := x284.bits.rdata(i).r }
      val x408 = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("x408_x311_D1") 
      (0 until 1).foreach{i => x408(i).r := getRetimed(x311(i).r, 1.toInt, io.sigsIn.backpressure & true.B)}
      // x312 = VecApply(x408,0)
      val x312_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x312_elem_0""")
      x312_elem_0.r := x408(0).r
      val x409 = Wire(new FixedPoint(true, 32, 0)).suggestName("x409_b305_D1") 
      x409.r := getRetimed(b305.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x410 = Wire(Bool()).suggestName("x410_x310_D1") 
      x410.r := getRetimed(x310.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x411 = Wire(Bool()).suggestName("x411_b306_D1") 
      x411.r := getRetimed(b306.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x313_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x313_wr_ofs = List[UInt](x409.r)
      val x313_wr_en = List[Bool](true.B)
      val x313_wr_data = List[UInt](x312_elem_0.r)
      x280_inSRAM_0.connectWPort(313, x313_wr_banks, x313_wr_ofs, x313_wr_data, x313_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x410 & x411))
    }
    val module = Module(new x314_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x314_inr_Foreach **/
