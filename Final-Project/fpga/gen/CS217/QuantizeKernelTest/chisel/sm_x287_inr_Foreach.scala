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

/** Hierarchy: x287 -> x288 -> x289 -> x213 **/
/** BEGIN None x287_inr_Foreach **/
class x287_inr_Foreach_kernel(
  list_x257: List[DecoupledIO[AppLoadData]],
  list_x253_inSRAM_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 2.0.toInt, myName = "x287_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x287_inr_Foreach_iiCtr"))
  
  abstract class x287_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x257 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x257_p").asInstanceOf[(Int, Int)] )))
      val in_x253_inSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x253_inSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x266_reg = Flipped(new StandardInterface(ModuleParams.getParams("x266_reg_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x257 = {io.in_x257} 
    def x253_inSRAM_0 = {io.in_x253_inSRAM_0} ; io.in_x253_inSRAM_0 := DontCare
    def x266_reg = {io.in_x266_reg} ; io.in_x266_reg := DontCare
  }
  def connectWires0(module: x287_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x257 <> x257
    x253_inSRAM_0.connectLedger(module.io.in_x253_inSRAM_0)
    x266_reg.connectLedger(module.io.in_x266_reg)
  }
  val x257 = list_x257(0)
  val x253_inSRAM_0 = list_x253_inSRAM_0(0)
  val x266_reg = list_x253_inSRAM_0(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x287_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x287_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x287_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x287_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x287_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x287_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x287_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x287_inr_Foreach = Module(new InstrumentationCounter())
      val idles_x287_inr_Foreach = Module(new InstrumentationCounter())
      stalls_x287_inr_Foreach.io.enable := io.sigsIn.baseEn & ~(true.B)
      idles_x287_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((x257.valid) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X287_instrctr, cycles_x287_inr_Foreach.io.count, iters_x287_inr_Foreach.io.count, stalls_x287_inr_Foreach.io.count, idles_x287_inr_Foreach.io.count)
      val b278 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b278.suggestName("b278")
      val b279 = ~io.sigsIn.cchainOutputs.head.oobs(0); b279.suggestName("b279")
      val x280 = Wire(Bool()).suggestName("""x280""")
      x280.r := Math.lte(0L.FP(true, 32, 0), b278, Some(0.4), true.B,"x280").r
      val x281_rd_x266 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x281_rd_x266""")
      val x281_rd_x266_banks = List[UInt]()
      val x281_rd_x266_ofs = List[UInt]()
      val x281_rd_x266_en = List[Bool](true.B)
      val x281_rd_x266_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x281_rd_x266_shared_en")
      x281_rd_x266.toSeq.zip(x266_reg.connectRPort(281, x281_rd_x266_banks, x281_rd_x266_ofs, io.sigsIn.backpressure, x281_rd_x266_en.map(_ && x281_rd_x266_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x282 = Wire(Bool()).suggestName("""x282""")
      x282.r := Math.lt(b278, x281_rd_x266, Some(0.4), true.B,"x282").r
      val x283 = Wire(Bool()).suggestName("""x283""")
      x283 := x280 & x282
      val x284 = Wire(Vec(1, new FixedPoint(true, 16, 8))).suggestName("""x284""")
      x257.ready := b279 & (io.sigsIn.datapathEn) 
      (0 until 1).map{ i => x284(i).r := x257.bits.rdata(i).r }
      val x376 = Wire(Vec(1, new FixedPoint(true, 16, 8))).suggestName("x376_x284_D1") 
      (0 until 1).foreach{i => x376(i).r := getRetimed(x284(i).r, 1.toInt, io.sigsIn.backpressure & true.B)}
      // x285 = VecApply(x376,0)
      val x285_elem_0 = Wire(new FixedPoint(true, 16, 8)).suggestName("""x285_elem_0""")
      x285_elem_0.r := x376(0).r
      val x377 = Wire(Bool()).suggestName("x377_x283_D1") 
      x377.r := getRetimed(x283.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x378 = Wire(new FixedPoint(true, 32, 0)).suggestName("x378_b278_D1") 
      x378.r := getRetimed(b278.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x379 = Wire(Bool()).suggestName("x379_b279_D1") 
      x379.r := getRetimed(b279.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x286_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x286_wr_ofs = List[UInt](x378.r)
      val x286_wr_en = List[Bool](true.B)
      val x286_wr_data = List[UInt](x285_elem_0.r)
      x253_inSRAM_0.connectWPort(286, x286_wr_banks, x286_wr_ofs, x286_wr_data, x286_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x377 & x379))
    }
    val module = Module(new x287_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x287_inr_Foreach **/
