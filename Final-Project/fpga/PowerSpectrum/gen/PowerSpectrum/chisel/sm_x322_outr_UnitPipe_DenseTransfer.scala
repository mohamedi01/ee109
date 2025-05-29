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

/** Hierarchy: x322 -> x374 -> x375 **/
/** BEGIN Some(DenseTransfer) x322_outr_UnitPipe_DenseTransfer **/
class x322_outr_UnitPipe_DenseTransfer_kernel(
  list_x248_imagDram: List[FixedPoint],
  list_x288: List[DecoupledIO[AppCommandDense]],
  list_x290: List[DecoupledIO[AppLoadData]],
  list_x251_imagSram_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 2, isFSM = false   , latency = 0.0.toInt, myName = "x322_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x322_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x322_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x248_imagDram = Input(new FixedPoint(true, 64, 0))
      val in_x251_imagSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x251_imagSram_0_p").asInstanceOf[MemParams] ))
      val in_x288 = Decoupled(new AppCommandDense(ModuleParams.getParams("x288_p").asInstanceOf[(Int,Int)] ))
      val in_x290 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x290_p").asInstanceOf[(Int, Int)] )))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 2, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 2))
      val rr = Input(Bool())
    })
    def x248_imagDram = {io.in_x248_imagDram} 
    def x251_imagSram_0 = {io.in_x251_imagSram_0} ; io.in_x251_imagSram_0 := DontCare
    def x288 = {io.in_x288} 
    def x290 = {io.in_x290} 
  }
  def connectWires0(module: x322_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x248_imagDram <> x248_imagDram
    x251_imagSram_0.connectLedger(module.io.in_x251_imagSram_0)
    module.io.in_x288 <> x288
    module.io.in_x290 <> x290
  }
  val x248_imagDram = list_x248_imagDram(0)
  val x288 = list_x288(0)
  val x290 = list_x290(0)
  val x251_imagSram_0 = list_x251_imagSram_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x322_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x322_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x322_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x289_fifo = (new x289_fifo).m.io.asInstanceOf[FIFOInterface]
      val x297_inr_UnitPipe = new x297_inr_UnitPipe_kernel(List(x248_imagDram), List(x289_fifo), List(x288) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, rr)
      x297_inr_UnitPipe.sm.io.ctrDone := risingEdge(x297_inr_UnitPipe.sm.io.ctrInc)
      x297_inr_UnitPipe.backpressure := x288.ready & (~x289_fifo.full.D(1.0-1) | ~(x289_fifo.active(0).out)) | x297_inr_UnitPipe.sm.io.doneLatch
      x297_inr_UnitPipe.forwardpressure := (true.B) && (true.B) | x297_inr_UnitPipe.sm.io.doneLatch
      x297_inr_UnitPipe.sm.io.enableOut.zip(x297_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x297_inr_UnitPipe.sm.io.break := false.B
      x297_inr_UnitPipe.mask := true.B & true.B
      x297_inr_UnitPipe.configure("x297_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x297_inr_UnitPipe.kernel()
      val x321_outr_UnitPipe = new x321_outr_UnitPipe_kernel(List(x289_fifo), List(x290), List(x251_imagSram_0) ,  Some(me), List(), 1, 2, 1, List(1), List(32), breakpoints, rr)
      x321_outr_UnitPipe.sm.io.ctrDone := risingEdge(x321_outr_UnitPipe.sm.io.ctrInc)
      x321_outr_UnitPipe.backpressure := true.B | x321_outr_UnitPipe.sm.io.doneLatch
      x321_outr_UnitPipe.forwardpressure := (true.B) && (true.B) | x321_outr_UnitPipe.sm.io.doneLatch
      x321_outr_UnitPipe.sm.io.enableOut.zip(x321_outr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x321_outr_UnitPipe.sm.io.break := false.B
      x321_outr_UnitPipe.mask := true.B & true.B
      x321_outr_UnitPipe.configure("x321_outr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x321_outr_UnitPipe.kernel()
    }
    val module = Module(new x322_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x322_outr_UnitPipe_DenseTransfer **/
