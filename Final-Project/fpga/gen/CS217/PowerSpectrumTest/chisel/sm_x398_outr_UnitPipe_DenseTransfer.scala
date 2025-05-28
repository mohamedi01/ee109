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

/** Hierarchy: x398 -> x480 -> x481 **/
/** BEGIN Some(DenseTransfer) x398_outr_UnitPipe_DenseTransfer **/
class x398_outr_UnitPipe_DenseTransfer_kernel(
  list_x322_imagDRAM: List[FixedPoint],
  list_x364: List[DecoupledIO[AppCommandDense]],
  list_x366: List[DecoupledIO[AppLoadData]],
  list_x327_imagSRAM_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 2, isFSM = false   , latency = 0.0.toInt, myName = "x398_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x398_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x398_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x327_imagSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x327_imagSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x322_imagDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x366 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x366_p").asInstanceOf[(Int, Int)] )))
      val in_x364 = Decoupled(new AppCommandDense(ModuleParams.getParams("x364_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 2, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 2))
      val rr = Input(Bool())
    })
    def x327_imagSRAM_0 = {io.in_x327_imagSRAM_0} ; io.in_x327_imagSRAM_0 := DontCare
    def x322_imagDRAM = {io.in_x322_imagDRAM} 
    def x366 = {io.in_x366} 
    def x364 = {io.in_x364} 
  }
  def connectWires0(module: x398_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    x327_imagSRAM_0.connectLedger(module.io.in_x327_imagSRAM_0)
    module.io.in_x322_imagDRAM <> x322_imagDRAM
    module.io.in_x366 <> x366
    module.io.in_x364 <> x364
  }
  val x322_imagDRAM = list_x322_imagDRAM(0)
  val x364 = list_x364(0)
  val x366 = list_x366(0)
  val x327_imagSRAM_0 = list_x327_imagSRAM_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x398_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x398_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x398_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x398_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      val iters_x398_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      cycles_x398_outr_UnitPipe_DenseTransfer.io.enable := io.sigsIn.baseEn
      iters_x398_outr_UnitPipe_DenseTransfer.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X398_instrctr, cycles_x398_outr_UnitPipe_DenseTransfer.io.count, iters_x398_outr_UnitPipe_DenseTransfer.io.count, 0.U, 0.U)
      val x365_fifo = (new x365_fifo).m.io.asInstanceOf[FIFOInterface]
      val x373_inr_UnitPipe = new x373_inr_UnitPipe_kernel(List(x322_imagDRAM), List(x365_fifo), List(x364) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x373_inr_UnitPipe.sm.io.ctrDone := risingEdge(x373_inr_UnitPipe.sm.io.ctrInc)
      x373_inr_UnitPipe.backpressure := (~x365_fifo.full.D(1.0-1) | ~(x365_fifo.active(0).out)) & x364.ready | x373_inr_UnitPipe.sm.io.doneLatch
      x373_inr_UnitPipe.forwardpressure := (true.B) && (true.B) | x373_inr_UnitPipe.sm.io.doneLatch
      x373_inr_UnitPipe.sm.io.enableOut.zip(x373_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x373_inr_UnitPipe.sm.io.break := false.B
      x373_inr_UnitPipe.mask := true.B & true.B
      x373_inr_UnitPipe.configure("x373_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x373_inr_UnitPipe.kernel()
      val x397_outr_UnitPipe = new x397_outr_UnitPipe_kernel(List(x365_fifo), List(x366), List(x327_imagSRAM_0) ,  Some(me), List(), 1, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x397_outr_UnitPipe.sm.io.ctrDone := risingEdge(x397_outr_UnitPipe.sm.io.ctrInc)
      x397_outr_UnitPipe.backpressure := true.B | x397_outr_UnitPipe.sm.io.doneLatch
      x397_outr_UnitPipe.forwardpressure := (true.B) && (true.B) | x397_outr_UnitPipe.sm.io.doneLatch
      x397_outr_UnitPipe.sm.io.enableOut.zip(x397_outr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x397_outr_UnitPipe.sm.io.break := false.B
      x397_outr_UnitPipe.mask := true.B & true.B
      x397_outr_UnitPipe.configure("x397_outr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x397_outr_UnitPipe.kernel()
    }
    val module = Module(new x398_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x398_outr_UnitPipe_DenseTransfer **/
