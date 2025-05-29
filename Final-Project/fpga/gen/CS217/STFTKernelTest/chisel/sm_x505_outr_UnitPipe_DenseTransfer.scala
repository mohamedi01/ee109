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

/** Hierarchy: x505 -> x708 -> x710 **/
/** BEGIN Some(DenseTransfer) x505_outr_UnitPipe_DenseTransfer **/
class x505_outr_UnitPipe_DenseTransfer_kernel(
  list_x460_inDRAM: List[FixedPoint],
  list_x471: List[DecoupledIO[AppCommandDense]],
  list_x473: List[DecoupledIO[AppLoadData]],
  list_x465_inSRAM_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 2, isFSM = false   , latency = 0.0.toInt, myName = "x505_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x505_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x505_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x460_inDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x465_inSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x465_inSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x471 = Decoupled(new AppCommandDense(ModuleParams.getParams("x471_p").asInstanceOf[(Int,Int)] ))
      val in_x473 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x473_p").asInstanceOf[(Int, Int)] )))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 2, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 2))
      val rr = Input(Bool())
    })
    def x460_inDRAM = {io.in_x460_inDRAM} 
    def x465_inSRAM_0 = {io.in_x465_inSRAM_0} ; io.in_x465_inSRAM_0 := DontCare
    def x471 = {io.in_x471} 
    def x473 = {io.in_x473} 
  }
  def connectWires0(module: x505_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x460_inDRAM <> x460_inDRAM
    x465_inSRAM_0.connectLedger(module.io.in_x465_inSRAM_0)
    module.io.in_x471 <> x471
    module.io.in_x473 <> x473
  }
  val x460_inDRAM = list_x460_inDRAM(0)
  val x471 = list_x471(0)
  val x473 = list_x473(0)
  val x465_inSRAM_0 = list_x465_inSRAM_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x505_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x505_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x505_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x505_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      val iters_x505_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      cycles_x505_outr_UnitPipe_DenseTransfer.io.enable := io.sigsIn.baseEn
      iters_x505_outr_UnitPipe_DenseTransfer.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X505_instrctr, cycles_x505_outr_UnitPipe_DenseTransfer.io.count, iters_x505_outr_UnitPipe_DenseTransfer.io.count, 0.U, 0.U)
      val x472_fifo = (new x472_fifo).m.io.asInstanceOf[FIFOInterface]
      val x480_inr_UnitPipe = new x480_inr_UnitPipe_kernel(List(x460_inDRAM), List(x472_fifo), List(x471) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x480_inr_UnitPipe.sm.io.ctrDone := risingEdge(x480_inr_UnitPipe.sm.io.ctrInc)
      x480_inr_UnitPipe.backpressure := (~x472_fifo.full.D(1.0-1) | ~(x472_fifo.active(0).out)) & x471.ready | x480_inr_UnitPipe.sm.io.doneLatch
      x480_inr_UnitPipe.forwardpressure := (true.B) && (true.B) | x480_inr_UnitPipe.sm.io.doneLatch
      x480_inr_UnitPipe.sm.io.enableOut.zip(x480_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x480_inr_UnitPipe.sm.io.break := false.B
      x480_inr_UnitPipe.mask := true.B & true.B
      x480_inr_UnitPipe.configure("x480_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x480_inr_UnitPipe.kernel()
      val x504_outr_UnitPipe = new x504_outr_UnitPipe_kernel(List(x472_fifo), List(x473), List(x465_inSRAM_0) ,  Some(me), List(), 1, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x504_outr_UnitPipe.sm.io.ctrDone := risingEdge(x504_outr_UnitPipe.sm.io.ctrInc)
      x504_outr_UnitPipe.backpressure := true.B | x504_outr_UnitPipe.sm.io.doneLatch
      x504_outr_UnitPipe.forwardpressure := (true.B) && (true.B) | x504_outr_UnitPipe.sm.io.doneLatch
      x504_outr_UnitPipe.sm.io.enableOut.zip(x504_outr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x504_outr_UnitPipe.sm.io.break := false.B
      x504_outr_UnitPipe.mask := true.B & true.B
      x504_outr_UnitPipe.configure("x504_outr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x504_outr_UnitPipe.kernel()
    }
    val module = Module(new x505_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x505_outr_UnitPipe_DenseTransfer **/
