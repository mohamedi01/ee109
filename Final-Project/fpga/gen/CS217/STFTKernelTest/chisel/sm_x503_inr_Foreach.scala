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

/** Hierarchy: x503 -> x504 -> x505 -> x708 -> x710 **/
/** BEGIN None x503_inr_Foreach **/
class x503_inr_Foreach_kernel(
  list_x473: List[DecoupledIO[AppLoadData]],
  list_x465_inSRAM_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 2.0.toInt, myName = "x503_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x503_inr_Foreach_iiCtr"))
  
  abstract class x503_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x473 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x473_p").asInstanceOf[(Int, Int)] )))
      val in_x465_inSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x465_inSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x482_reg = Flipped(new StandardInterface(ModuleParams.getParams("x482_reg_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x473 = {io.in_x473} 
    def x465_inSRAM_0 = {io.in_x465_inSRAM_0} ; io.in_x465_inSRAM_0 := DontCare
    def x482_reg = {io.in_x482_reg} ; io.in_x482_reg := DontCare
  }
  def connectWires0(module: x503_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x473 <> x473
    x465_inSRAM_0.connectLedger(module.io.in_x465_inSRAM_0)
    x482_reg.connectLedger(module.io.in_x482_reg)
  }
  val x473 = list_x473(0)
  val x465_inSRAM_0 = list_x465_inSRAM_0(0)
  val x482_reg = list_x465_inSRAM_0(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x503_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x503_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x503_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x503_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x503_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x503_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x503_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x503_inr_Foreach = Module(new InstrumentationCounter())
      val idles_x503_inr_Foreach = Module(new InstrumentationCounter())
      stalls_x503_inr_Foreach.io.enable := io.sigsIn.baseEn & ~(true.B)
      idles_x503_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((x473.valid) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X503_instrctr, cycles_x503_inr_Foreach.io.count, iters_x503_inr_Foreach.io.count, stalls_x503_inr_Foreach.io.count, idles_x503_inr_Foreach.io.count)
      val b494 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b494.suggestName("b494")
      val b495 = ~io.sigsIn.cchainOutputs.head.oobs(0); b495.suggestName("b495")
      val x496 = Wire(Bool()).suggestName("""x496""")
      x496.r := Math.lte(0L.FP(true, 32, 0), b494, Some(0.4), true.B,"x496").r
      val x497_rd_x482 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x497_rd_x482""")
      val x497_rd_x482_banks = List[UInt]()
      val x497_rd_x482_ofs = List[UInt]()
      val x497_rd_x482_en = List[Bool](true.B)
      val x497_rd_x482_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x497_rd_x482_shared_en")
      x497_rd_x482.toSeq.zip(x482_reg.connectRPort(497, x497_rd_x482_banks, x497_rd_x482_ofs, io.sigsIn.backpressure, x497_rd_x482_en.map(_ && x497_rd_x482_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x498 = Wire(Bool()).suggestName("""x498""")
      x498.r := Math.lt(b494, x497_rd_x482, Some(0.4), true.B,"x498").r
      val x499 = Wire(Bool()).suggestName("""x499""")
      x499 := x496 & x498
      val x500 = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x500""")
      x473.ready := b495 & (io.sigsIn.datapathEn) 
      (0 until 1).map{ i => x500(i).r := x473.bits.rdata(i).r }
      val x711 = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("x711_x500_D1") 
      (0 until 1).foreach{i => x711(i).r := getRetimed(x500(i).r, 1.toInt, io.sigsIn.backpressure & true.B)}
      // x501 = VecApply(x711,0)
      val x501_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x501_elem_0""")
      x501_elem_0.r := x711(0).r
      val x712 = Wire(Bool()).suggestName("x712_b495_D1") 
      x712.r := getRetimed(b495.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x713 = Wire(new FixedPoint(true, 32, 0)).suggestName("x713_b494_D1") 
      x713.r := getRetimed(b494.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x714 = Wire(Bool()).suggestName("x714_x499_D1") 
      x714.r := getRetimed(x499.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x502_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x502_wr_ofs = List[UInt](x713.r)
      val x502_wr_en = List[Bool](true.B)
      val x502_wr_data = List[UInt](x501_elem_0.r)
      x465_inSRAM_0.connectWPort(502, x502_wr_banks, x502_wr_ofs, x502_wr_data, x502_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x714 & x712))
    }
    val module = Module(new x503_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x503_inr_Foreach **/
