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

/** Hierarchy: x366 -> x438 -> x439 **/
/** BEGIN None x366_inr_Foreach **/
class x366_inr_Foreach_kernel(
  list_x295_out_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 2.2.toInt, myName = "x366_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x366_inr_Foreach_iiCtr"))
  
  abstract class x366_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x295_out_0 = Flipped(new StandardInterface(ModuleParams.getParams("x295_out_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x295_out_0 = {io.in_x295_out_0} ; io.in_x295_out_0 := DontCare
  }
  def connectWires0(module: x366_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x295_out_0.connectLedger(module.io.in_x295_out_0)
  }
  val x295_out_0 = list_x295_out_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x366_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x366_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x366_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x366_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x366_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x366_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x366_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X366_instrctr, cycles_x366_inr_Foreach.io.count, iters_x366_inr_Foreach.io.count, 0.U, 0.U)
      val b357 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b357.suggestName("b357")
      val b358 = ~io.sigsIn.cchainOutputs.head.oobs(0); b358.suggestName("b358")
      val x359 = Wire(Bool()).suggestName("""x359""")
      x359.r := Math.eql(b357, 0L.FP(true, 32, 0), Some(0.2), true.B,"x359").r
      val x360 = Wire(Bool()).suggestName("""x360""")
      x360 := ~x359
      val x361_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x361_wr_ofs = List[UInt](b357.r)
      val x361_wr_en = List[Bool](true.B)
      val x361_wr_data = List[UInt](1.FP(true, 16, 8).r)
      x295_out_0.connectWPort(361, x361_wr_banks, x361_wr_ofs, x361_wr_data, x361_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.2.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x359))
      val x449 = Wire(new FixedPoint(true, 32, 0)).suggestName("x449_b357_D1") 
      x449.r := getRetimed(b357.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x450 = Wire(Bool()).suggestName("x450_x360_D1") 
      x450.r := getRetimed(x360.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x363_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x363_wr_ofs = List[UInt](x449.r)
      val x363_wr_en = List[Bool](true.B)
      val x363_wr_data = List[UInt](-2.FP(true, 16, 8).r)
      x295_out_0.connectWPort(363, x363_wr_banks, x363_wr_ofs, x363_wr_data, x363_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x450))
    }
    val module = Module(new x366_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x366_inr_Foreach **/
