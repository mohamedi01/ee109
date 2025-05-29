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

/** Hierarchy: x438 -> x439 **/
/** BEGIN None x438 **/
class x438_kernel(
  list_x331_mx_0: List[FixOpAccumBundle],
  list_x356_ctrchain: List[CounterChainInterface],
  list_x295_out_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(ForkJoin, 2, isFSM = false   , latency = 0.0.toInt, myName = "x438_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x438_iiCtr"))
  
  abstract class x438_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x356_ctrchain = Flipped(new CounterChainInterface(ModuleParams.getParams("x356_ctrchain_p").asInstanceOf[(List[Int],List[Int])] ))
      val in_x345_ctrchain = Flipped(new CounterChainInterface(ModuleParams.getParams("x345_ctrchain_p").asInstanceOf[(List[Int],List[Int])] ))
      val in_x295_out_0 = Flipped(new StandardInterface(ModuleParams.getParams("x295_out_0_p").asInstanceOf[MemParams] ))
      val in_x331_mx_0 = Flipped(new FixOpAccumBundle(1, 16, 8))
      val in_x294_buf_0 = Flipped(new StandardInterface(ModuleParams.getParams("x294_buf_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x356_ctrchain = {io.in_x356_ctrchain} ; io.in_x356_ctrchain := DontCare
    def x345_ctrchain = {io.in_x345_ctrchain} ; io.in_x345_ctrchain := DontCare
    def x295_out_0 = {io.in_x295_out_0} ; io.in_x295_out_0 := DontCare
    def x331_mx_0 = {io.in_x331_mx_0} ; io.in_x331_mx_0 := DontCare
    def x294_buf_0 = {io.in_x294_buf_0} ; io.in_x294_buf_0 := DontCare
  }
  def connectWires0(module: x438_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x356_ctrchain.input <> x356_ctrchain.input; module.io.in_x356_ctrchain.output <> x356_ctrchain.output
    module.io.in_x345_ctrchain.input <> x345_ctrchain.input; module.io.in_x345_ctrchain.output <> x345_ctrchain.output
    x295_out_0.connectLedger(module.io.in_x295_out_0)
    x331_mx_0.connectLedger(module.io.in_x331_mx_0)
    x294_buf_0.connectLedger(module.io.in_x294_buf_0)
  }
  val x331_mx_0 = list_x331_mx_0(0)
  val x356_ctrchain = list_x356_ctrchain(0)
  val x345_ctrchain = list_x356_ctrchain(1)
  val x295_out_0 = list_x295_out_0(0)
  val x294_buf_0 = list_x295_out_0(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x438")
    implicit val stack = ControllerStack.stack.toList
    class x438_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x438_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x438 = Module(new InstrumentationCounter())
      val iters_x438 = Module(new InstrumentationCounter())
      cycles_x438.io.enable := io.sigsIn.baseEn
      iters_x438.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X438_instrctr, cycles_x438.io.count, iters_x438.io.count, 0.U, 0.U)
      val x354_inr_Foreach = new x354_inr_Foreach_kernel(List(x331_mx_0), List(x294_buf_0) ,  Some(me), List(x345_ctrchain), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x354_inr_Foreach.sm.io.ctrDone := (x354_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x354_inr_Foreach.backpressure := true.B | x354_inr_Foreach.sm.io.doneLatch
      x354_inr_Foreach.forwardpressure := (true.B) && (true.B) | x354_inr_Foreach.sm.io.doneLatch
      x354_inr_Foreach.sm.io.enableOut.zip(x354_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x354_inr_Foreach.sm.io.break := false.B
      x354_inr_Foreach.mask := ~x354_inr_Foreach.cchain.head.output.noop & true.B
      x354_inr_Foreach.configure("x354_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x354_inr_Foreach.kernel()
      val x366_inr_Foreach = new x366_inr_Foreach_kernel(List(x295_out_0) ,  Some(me), List(x356_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x366_inr_Foreach.sm.io.ctrDone := (x366_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x366_inr_Foreach.backpressure := true.B | x366_inr_Foreach.sm.io.doneLatch
      x366_inr_Foreach.forwardpressure := (true.B) && (true.B) | x366_inr_Foreach.sm.io.doneLatch
      x366_inr_Foreach.sm.io.enableOut.zip(x366_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x366_inr_Foreach.sm.io.break := false.B
      x366_inr_Foreach.mask := ~x366_inr_Foreach.cchain.head.output.noop & true.B
      x366_inr_Foreach.configure("x366_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x366_inr_Foreach.kernel()
    }
    val module = Module(new x438_concrete(sm.p.depth)); module.io := DontCare
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
/** END ParallelPipe x438 **/
