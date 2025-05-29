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

/** Hierarchy: x287 -> x374 -> x375 **/
/** BEGIN Some(DenseTransfer) x287_outr_UnitPipe_DenseTransfer **/
class x287_outr_UnitPipe_DenseTransfer_kernel(
  list_x247_realDram: List[FixedPoint],
  list_x253: List[DecoupledIO[AppCommandDense]],
  list_x255: List[DecoupledIO[AppLoadData]],
  list_x250_realSram_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 2, isFSM = false   , latency = 0.0.toInt, myName = "x287_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x287_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x287_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x247_realDram = Input(new FixedPoint(true, 64, 0))
      val in_x250_realSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x250_realSram_0_p").asInstanceOf[MemParams] ))
      val in_x253 = Decoupled(new AppCommandDense(ModuleParams.getParams("x253_p").asInstanceOf[(Int,Int)] ))
      val in_x255 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x255_p").asInstanceOf[(Int, Int)] )))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 2, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 2))
      val rr = Input(Bool())
    })
    def x247_realDram = {io.in_x247_realDram} 
    def x250_realSram_0 = {io.in_x250_realSram_0} ; io.in_x250_realSram_0 := DontCare
    def x253 = {io.in_x253} 
    def x255 = {io.in_x255} 
  }
  def connectWires0(module: x287_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x247_realDram <> x247_realDram
    x250_realSram_0.connectLedger(module.io.in_x250_realSram_0)
    module.io.in_x253 <> x253
    module.io.in_x255 <> x255
  }
  val x247_realDram = list_x247_realDram(0)
  val x253 = list_x253(0)
  val x255 = list_x255(0)
  val x250_realSram_0 = list_x250_realSram_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x287_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x287_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x287_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x254_fifo = (new x254_fifo).m.io.asInstanceOf[FIFOInterface]
      val x262_inr_UnitPipe = new x262_inr_UnitPipe_kernel(List(x247_realDram), List(x254_fifo), List(x253) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, rr)
      x262_inr_UnitPipe.sm.io.ctrDone := risingEdge(x262_inr_UnitPipe.sm.io.ctrInc)
      x262_inr_UnitPipe.backpressure := (~x254_fifo.full.D(1.0-1) | ~(x254_fifo.active(0).out)) & x253.ready | x262_inr_UnitPipe.sm.io.doneLatch
      x262_inr_UnitPipe.forwardpressure := (true.B) && (true.B) | x262_inr_UnitPipe.sm.io.doneLatch
      x262_inr_UnitPipe.sm.io.enableOut.zip(x262_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x262_inr_UnitPipe.sm.io.break := false.B
      x262_inr_UnitPipe.mask := true.B & true.B
      x262_inr_UnitPipe.configure("x262_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x262_inr_UnitPipe.kernel()
      val x286_outr_UnitPipe = new x286_outr_UnitPipe_kernel(List(x254_fifo), List(x255), List(x250_realSram_0) ,  Some(me), List(), 1, 2, 1, List(1), List(32), breakpoints, rr)
      x286_outr_UnitPipe.sm.io.ctrDone := risingEdge(x286_outr_UnitPipe.sm.io.ctrInc)
      x286_outr_UnitPipe.backpressure := true.B | x286_outr_UnitPipe.sm.io.doneLatch
      x286_outr_UnitPipe.forwardpressure := (true.B) && (true.B) | x286_outr_UnitPipe.sm.io.doneLatch
      x286_outr_UnitPipe.sm.io.enableOut.zip(x286_outr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x286_outr_UnitPipe.sm.io.break := false.B
      x286_outr_UnitPipe.mask := true.B & true.B
      x286_outr_UnitPipe.configure("x286_outr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x286_outr_UnitPipe.kernel()
    }
    val module = Module(new x287_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x287_outr_UnitPipe_DenseTransfer **/
