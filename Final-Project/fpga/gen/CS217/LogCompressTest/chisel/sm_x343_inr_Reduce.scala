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

/** Hierarchy: x343 -> x439 **/
/** BEGIN None x343_inr_Reduce **/
class x343_inr_Reduce_kernel(
  list_x331_mx_0: List[FixOpAccumBundle],
  list_x294_buf_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 4.75.toInt, myName = "x343_inr_Reduce_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x343_inr_Reduce_iiCtr"))
  
  abstract class x343_inr_Reduce_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x331_mx_0 = Flipped(new FixOpAccumBundle(1, 16, 8))
      val in_x294_buf_0 = Flipped(new StandardInterface(ModuleParams.getParams("x294_buf_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x331_mx_0 = {io.in_x331_mx_0} ; io.in_x331_mx_0 := DontCare
    def x294_buf_0 = {io.in_x294_buf_0} ; io.in_x294_buf_0 := DontCare
  }
  def connectWires0(module: x343_inr_Reduce_module)(implicit stack: List[KernelHash]): Unit = {
    x331_mx_0.connectLedger(module.io.in_x331_mx_0)
    x294_buf_0.connectLedger(module.io.in_x294_buf_0)
  }
  val x331_mx_0 = list_x331_mx_0(0)
  val x294_buf_0 = list_x294_buf_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x343_inr_Reduce")
    implicit val stack = ControllerStack.stack.toList
    class x343_inr_Reduce_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x343_inr_Reduce_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x343_inr_Reduce = Module(new InstrumentationCounter())
      val iters_x343_inr_Reduce = Module(new InstrumentationCounter())
      cycles_x343_inr_Reduce.io.enable := io.sigsIn.baseEn
      iters_x343_inr_Reduce.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X343_instrctr, cycles_x343_inr_Reduce.io.count, iters_x343_inr_Reduce.io.count, 0.U, 0.U)
      val b334 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b334.suggestName("b334")
      val b335 = ~io.sigsIn.cchainOutputs.head.oobs(0); b335.suggestName("b335")
      val x336_rd = Wire(Vec(1, new FixedPoint(true, 16, 8))).suggestName("""x336_rd""")
      val x336_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x336_rd_ofs = List[UInt](b334.r)
      val x336_rd_en = List[Bool](true.B)
      val x336_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && b335 ).suggestName("x336_rd_shared_en")
      x336_rd.toSeq.zip(x294_buf_0.connectRPort(336, x336_rd_banks, x336_rd_ofs, io.sigsIn.backpressure, x336_rd_en.map(_ && x336_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x337 = VecApply(x336,0)
      val x337_elem_0 = Wire(new FixedPoint(true, 16, 8)).suggestName("""x337_elem_0""")
      x337_elem_0.r := x336_rd(0).r
      val x339 = Wire(Bool()).suggestName("""x339""")
      x339.r := Math.eql(b334, 0L.FP(true, 32, 0), Some(0.2), true.B,"x339").r
      val x445 = Wire(Bool()).suggestName("x445_x339_D2") 
      x445.r := getRetimed(x339.r, 2.toInt, io.sigsIn.backpressure & true.B)
      x331_mx_0.connectWPort(0, x337_elem_0.r, true.B && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.0.toInt, rr, io.sigsIn.backpressure & true.B), (io.sigsIn.ctrDone).DS(2.0.toInt, rr, io.sigsIn.backpressure & true.B), x445)
      val x440 = Wire(new FixedPoint(true, 16, 8)).suggestName("""x440""")
      x440.r := x331_mx_0.output
    }
    val module = Module(new x343_inr_Reduce_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledReduce x343_inr_Reduce **/
