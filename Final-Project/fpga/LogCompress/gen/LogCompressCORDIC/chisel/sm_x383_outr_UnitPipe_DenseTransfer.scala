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

/** Hierarchy: x383 -> x489 -> x491 **/
/** BEGIN Some(DenseTransfer) x383_outr_UnitPipe_DenseTransfer **/
class x383_outr_UnitPipe_DenseTransfer_kernel(
  list_x329_inDram: List[FixedPoint],
  list_x344: List[DecoupledIO[AppCommandDense]],
  list_x341_buf_0: List[StandardInterface],
  list_x326_argIn: List[UInt],
  list_x346: List[DecoupledIO[AppLoadData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 2, isFSM = false   , latency = 0.0.toInt, myName = "x383_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x383_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x383_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x344 = Decoupled(new AppCommandDense(ModuleParams.getParams("x344_p").asInstanceOf[(Int,Int)] ))
      val in_x329_inDram = Input(new FixedPoint(true, 64, 0))
      val in_x346 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x346_p").asInstanceOf[(Int, Int)] )))
      val in_x326_argIn = Input(UInt(64.W))
      val in_x341_buf_0 = Flipped(new StandardInterface(ModuleParams.getParams("x341_buf_0_p").asInstanceOf[MemParams] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 2, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 2))
      val rr = Input(Bool())
    })
    def x344 = {io.in_x344} 
    def x329_inDram = {io.in_x329_inDram} 
    def x346 = {io.in_x346} 
    def x326_argIn = {io.in_x326_argIn} 
    def x341_buf_0 = {io.in_x341_buf_0} ; io.in_x341_buf_0 := DontCare
  }
  def connectWires0(module: x383_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x344 <> x344
    module.io.in_x329_inDram <> x329_inDram
    module.io.in_x346 <> x346
    module.io.in_x326_argIn <> x326_argIn
    x341_buf_0.connectLedger(module.io.in_x341_buf_0)
  }
  val x329_inDram = list_x329_inDram(0)
  val x344 = list_x344(0)
  val x341_buf_0 = list_x341_buf_0(0)
  val x326_argIn = list_x326_argIn(0)
  val x346 = list_x346(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x383_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x383_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x383_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x345_fifo = (new x345_fifo).m.io.asInstanceOf[FIFOInterface]
      val x358_inr_UnitPipe = new x358_inr_UnitPipe_kernel(List(x329_inDram), List(x345_fifo), List(x344), List(x326_argIn) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, rr)
      x358_inr_UnitPipe.sm.io.ctrDone := risingEdge(x358_inr_UnitPipe.sm.io.ctrInc)
      x358_inr_UnitPipe.backpressure := (~x345_fifo.full.D(2.4-1) | ~(x345_fifo.active(0).out)) & x344.ready | x358_inr_UnitPipe.sm.io.doneLatch
      x358_inr_UnitPipe.forwardpressure := (true.B) && (true.B) | x358_inr_UnitPipe.sm.io.doneLatch
      x358_inr_UnitPipe.sm.io.enableOut.zip(x358_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x358_inr_UnitPipe.sm.io.break := false.B
      x358_inr_UnitPipe.mask := true.B & true.B
      x358_inr_UnitPipe.configure("x358_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x358_inr_UnitPipe.kernel()
      val x382_outr_UnitPipe = new x382_outr_UnitPipe_kernel(List(x345_fifo), List(x346), List(x341_buf_0) ,  Some(me), List(), 1, 2, 1, List(1), List(32), breakpoints, rr)
      x382_outr_UnitPipe.sm.io.ctrDone := risingEdge(x382_outr_UnitPipe.sm.io.ctrInc)
      x382_outr_UnitPipe.backpressure := true.B | x382_outr_UnitPipe.sm.io.doneLatch
      x382_outr_UnitPipe.forwardpressure := (true.B) && (true.B) | x382_outr_UnitPipe.sm.io.doneLatch
      x382_outr_UnitPipe.sm.io.enableOut.zip(x382_outr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x382_outr_UnitPipe.sm.io.break := false.B
      x382_outr_UnitPipe.mask := true.B & true.B
      x382_outr_UnitPipe.configure("x382_outr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x382_outr_UnitPipe.kernel()
    }
    val module = Module(new x383_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x383_outr_UnitPipe_DenseTransfer **/
