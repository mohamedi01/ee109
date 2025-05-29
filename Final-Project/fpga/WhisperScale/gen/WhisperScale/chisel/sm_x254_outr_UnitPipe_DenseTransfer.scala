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

/** Hierarchy: x254 -> x196 **/
/** BEGIN Some(DenseTransfer) x254_outr_UnitPipe_DenseTransfer **/
class x254_outr_UnitPipe_DenseTransfer_kernel(
  list_x217: List[DecoupledIO[AppLoadData]],
  list_x209_inDram: List[FixedPoint],
  list_x215: List[DecoupledIO[AppCommandDense]],
  list_x206_argIn: List[UInt],
  list_x214_buf_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 2, isFSM = false   , latency = 0.0.toInt, myName = "x254_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x254_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x254_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x217 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x217_p").asInstanceOf[(Int, Int)] )))
      val in_x206_argIn = Input(UInt(64.W))
      val in_x209_inDram = Input(new FixedPoint(true, 64, 0))
      val in_x214_buf_0 = Flipped(new StandardInterface(ModuleParams.getParams("x214_buf_0_p").asInstanceOf[MemParams] ))
      val in_x215 = Decoupled(new AppCommandDense(ModuleParams.getParams("x215_p").asInstanceOf[(Int,Int)] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 2, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 2))
      val rr = Input(Bool())
    })
    def x217 = {io.in_x217} 
    def x206_argIn = {io.in_x206_argIn} 
    def x209_inDram = {io.in_x209_inDram} 
    def x214_buf_0 = {io.in_x214_buf_0} ; io.in_x214_buf_0 := DontCare
    def x215 = {io.in_x215} 
  }
  def connectWires0(module: x254_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x217 <> x217
    module.io.in_x206_argIn <> x206_argIn
    module.io.in_x209_inDram <> x209_inDram
    x214_buf_0.connectLedger(module.io.in_x214_buf_0)
    module.io.in_x215 <> x215
  }
  val x217 = list_x217(0)
  val x209_inDram = list_x209_inDram(0)
  val x215 = list_x215(0)
  val x206_argIn = list_x206_argIn(0)
  val x214_buf_0 = list_x214_buf_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x254_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x254_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x254_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x216_fifo = (new x216_fifo).m.io.asInstanceOf[FIFOInterface]
      val x229_inr_UnitPipe = new x229_inr_UnitPipe_kernel(List(x209_inDram), List(x216_fifo), List(x215), List(x206_argIn) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, rr)
      x229_inr_UnitPipe.sm.io.ctrDone := risingEdge(x229_inr_UnitPipe.sm.io.ctrInc)
      x229_inr_UnitPipe.backpressure := (~x216_fifo.full.D(2.4-1) | ~(x216_fifo.active(0).out)) & x215.ready | x229_inr_UnitPipe.sm.io.doneLatch
      x229_inr_UnitPipe.forwardpressure := (true.B) && (true.B) | x229_inr_UnitPipe.sm.io.doneLatch
      x229_inr_UnitPipe.sm.io.enableOut.zip(x229_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x229_inr_UnitPipe.sm.io.break := false.B
      x229_inr_UnitPipe.mask := true.B & true.B
      x229_inr_UnitPipe.configure("x229_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x229_inr_UnitPipe.kernel()
      val x253_outr_UnitPipe = new x253_outr_UnitPipe_kernel(List(x216_fifo), List(x217), List(x214_buf_0) ,  Some(me), List(), 1, 2, 1, List(1), List(32), breakpoints, rr)
      x253_outr_UnitPipe.sm.io.ctrDone := risingEdge(x253_outr_UnitPipe.sm.io.ctrInc)
      x253_outr_UnitPipe.backpressure := true.B | x253_outr_UnitPipe.sm.io.doneLatch
      x253_outr_UnitPipe.forwardpressure := (true.B) && (true.B) | x253_outr_UnitPipe.sm.io.doneLatch
      x253_outr_UnitPipe.sm.io.enableOut.zip(x253_outr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x253_outr_UnitPipe.sm.io.break := false.B
      x253_outr_UnitPipe.mask := true.B & true.B
      x253_outr_UnitPipe.configure("x253_outr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x253_outr_UnitPipe.kernel()
    }
    val module = Module(new x254_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x254_outr_UnitPipe_DenseTransfer **/
