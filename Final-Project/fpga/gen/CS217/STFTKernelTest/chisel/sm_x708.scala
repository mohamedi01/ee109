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

/** Hierarchy: x708 -> x710 **/
/** BEGIN None x708 **/
class x708_kernel(
  list_x507_ctrchain: List[CounterChainInterface],
  list_x471: List[DecoupledIO[AppCommandDense]],
  list_x473: List[DecoupledIO[AppLoadData]],
  list_x460_inDRAM: List[FixedPoint],
  list_x465_inSRAM_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(ForkJoin, 2, isFSM = false   , latency = 0.0.toInt, myName = "x708_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x708_iiCtr"))
  
  abstract class x708_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x460_inDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x507_ctrchain = Flipped(new CounterChainInterface(ModuleParams.getParams("x507_ctrchain_p").asInstanceOf[(List[Int],List[Int])] ))
      val in_x471 = Decoupled(new AppCommandDense(ModuleParams.getParams("x471_p").asInstanceOf[(Int,Int)] ))
      val in_x473 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x473_p").asInstanceOf[(Int, Int)] )))
      val in_x465_inSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x465_inSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x464_winSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x464_winSRAM_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x460_inDRAM = {io.in_x460_inDRAM} 
    def x507_ctrchain = {io.in_x507_ctrchain} ; io.in_x507_ctrchain := DontCare
    def x471 = {io.in_x471} 
    def x473 = {io.in_x473} 
    def x465_inSRAM_0 = {io.in_x465_inSRAM_0} ; io.in_x465_inSRAM_0 := DontCare
    def x464_winSRAM_0 = {io.in_x464_winSRAM_0} ; io.in_x464_winSRAM_0 := DontCare
  }
  def connectWires0(module: x708_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x460_inDRAM <> x460_inDRAM
    module.io.in_x507_ctrchain.input <> x507_ctrchain.input; module.io.in_x507_ctrchain.output <> x507_ctrchain.output
    module.io.in_x471 <> x471
    module.io.in_x473 <> x473
    x465_inSRAM_0.connectLedger(module.io.in_x465_inSRAM_0)
    x464_winSRAM_0.connectLedger(module.io.in_x464_winSRAM_0)
  }
  val x507_ctrchain = list_x507_ctrchain(0)
  val x471 = list_x471(0)
  val x473 = list_x473(0)
  val x460_inDRAM = list_x460_inDRAM(0)
  val x465_inSRAM_0 = list_x465_inSRAM_0(0)
  val x464_winSRAM_0 = list_x465_inSRAM_0(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x708")
    implicit val stack = ControllerStack.stack.toList
    class x708_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x708_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x708 = Module(new InstrumentationCounter())
      val iters_x708 = Module(new InstrumentationCounter())
      cycles_x708.io.enable := io.sigsIn.baseEn
      iters_x708.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X708_instrctr, cycles_x708.io.count, iters_x708.io.count, 0.U, 0.U)
      val x505_outr_UnitPipe_DenseTransfer = new x505_outr_UnitPipe_DenseTransfer_kernel(List(x460_inDRAM), List(x471), List(x473), List(x465_inSRAM_0) ,  Some(me), List(), 0, 2, 2, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x505_outr_UnitPipe_DenseTransfer.backpressure := true.B | x505_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x505_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x505_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x505_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x505_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x505_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x505_outr_UnitPipe_DenseTransfer.mask := true.B & true.B
      x505_outr_UnitPipe_DenseTransfer.configure("x505_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x505_outr_UnitPipe_DenseTransfer.kernel()
      val x511_inr_Foreach = new x511_inr_Foreach_kernel(List(x464_winSRAM_0) ,  Some(me), List(x507_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x511_inr_Foreach.sm.io.ctrDone := (x511_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x511_inr_Foreach.backpressure := true.B | x511_inr_Foreach.sm.io.doneLatch
      x511_inr_Foreach.forwardpressure := (true.B) && (true.B) | x511_inr_Foreach.sm.io.doneLatch
      x511_inr_Foreach.sm.io.enableOut.zip(x511_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x511_inr_Foreach.sm.io.break := false.B
      x511_inr_Foreach.mask := ~x511_inr_Foreach.cchain.head.output.noop & true.B
      x511_inr_Foreach.configure("x511_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x511_inr_Foreach.kernel()
    }
    val module = Module(new x708_concrete(sm.p.depth)); module.io := DontCare
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
/** END ParallelPipe x708 **/
