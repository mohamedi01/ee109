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

/** Hierarchy: x298 -> x201 **/
/** BEGIN None x298_inr_Foreach **/
class x298_inr_Foreach_kernel(
  list_x253_buf_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 3.95.toInt, myName = "x298_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(3.0.toInt, 2 + _root_.utils.math.log2Up(3.0.toInt), "x298_inr_Foreach_iiCtr"))
  
  abstract class x298_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x253_buf_0 = Flipped(new StandardInterface(ModuleParams.getParams("x253_buf_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x253_buf_0 = {io.in_x253_buf_0} ; io.in_x253_buf_0 := DontCare
  }
  def connectWires0(module: x298_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x253_buf_0.connectLedger(module.io.in_x253_buf_0)
  }
  val x253_buf_0 = list_x253_buf_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x298_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x298_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x298_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x298_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x298_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x298_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x298_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X298_instrctr, cycles_x298_inr_Foreach.io.count, iters_x298_inr_Foreach.io.count, 0.U, 0.U)
      val b291 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b291.suggestName("b291")
      val b292 = ~io.sigsIn.cchainOutputs.head.oobs(0); b292.suggestName("b292")
      val x293_rd = Wire(Vec(1, new FixedPoint(true, 16, 8))).suggestName("""x293_rd""")
      val x293_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x293_rd_ofs = List[UInt](b291.r)
      val x293_rd_en = List[Bool](true.B)
      val x293_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && b292 ).suggestName("x293_rd_shared_en")
      x293_rd.toSeq.zip(x253_buf_0.connectRPort(293, x293_rd_banks, x293_rd_ofs, io.sigsIn.backpressure, x293_rd_en.map(_ && x293_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x294 = VecApply(x293,0)
      val x294_elem_0 = Wire(new FixedPoint(true, 16, 8)).suggestName("""x294_elem_0""")
      x294_elem_0.r := x293_rd(0).r
      val x295_sum = Wire(new FixedPoint(true, 16, 8)).suggestName("""x295_sum""")
      x295_sum.r := Math.add(x294_elem_0,4.FP(true, 16, 8),Some(0.75), true.B, Truncate, Wrapping, "x295_sum").r
      val x296 = Wire(new FixedPoint(true, 16, 8)).suggestName("""x296""")
      x296.r := Math.arith_right_shift_div(x295_sum, 2, Some(0.2), true.B,"x296").r
      val x378 = Wire(new FixedPoint(true, 32, 0)).suggestName("x378_b291_D2") 
      x378.r := getRetimed(b291.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x379 = Wire(Bool()).suggestName("x379_b292_D2") 
      x379.r := getRetimed(b292.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x297_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x297_wr_ofs = List[UInt](x378.r)
      val x297_wr_en = List[Bool](true.B)
      val x297_wr_data = List[UInt](x296.r)
      x253_buf_0.connectWPort(297, x297_wr_banks, x297_wr_ofs, x297_wr_data, x297_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.95.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x379))
    }
    val module = Module(new x298_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x298_inr_Foreach **/
