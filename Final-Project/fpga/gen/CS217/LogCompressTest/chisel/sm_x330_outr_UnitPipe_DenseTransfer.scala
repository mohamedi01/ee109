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

/** Hierarchy: x330 -> x439 **/
/** BEGIN Some(DenseTransfer) x330_outr_UnitPipe_DenseTransfer **/
class x330_outr_UnitPipe_DenseTransfer_kernel(
  list_x291_inDRAM: List[FixedPoint],
  list_x296: List[DecoupledIO[AppCommandDense]],
  list_x298: List[DecoupledIO[AppLoadData]],
  list_x294_buf_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 2, isFSM = false   , latency = 0.0.toInt, myName = "x330_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x330_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x330_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x291_inDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x294_buf_0 = Flipped(new StandardInterface(ModuleParams.getParams("x294_buf_0_p").asInstanceOf[MemParams] ))
      val in_x298 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x298_p").asInstanceOf[(Int, Int)] )))
      val in_x296 = Decoupled(new AppCommandDense(ModuleParams.getParams("x296_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 2, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 2))
      val rr = Input(Bool())
    })
    def x291_inDRAM = {io.in_x291_inDRAM} 
    def x294_buf_0 = {io.in_x294_buf_0} ; io.in_x294_buf_0 := DontCare
    def x298 = {io.in_x298} 
    def x296 = {io.in_x296} 
  }
  def connectWires0(module: x330_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x291_inDRAM <> x291_inDRAM
    x294_buf_0.connectLedger(module.io.in_x294_buf_0)
    module.io.in_x298 <> x298
    module.io.in_x296 <> x296
  }
  val x291_inDRAM = list_x291_inDRAM(0)
  val x296 = list_x296(0)
  val x298 = list_x298(0)
  val x294_buf_0 = list_x294_buf_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x330_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x330_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x330_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x330_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      val iters_x330_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      cycles_x330_outr_UnitPipe_DenseTransfer.io.enable := io.sigsIn.baseEn
      iters_x330_outr_UnitPipe_DenseTransfer.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X330_instrctr, cycles_x330_outr_UnitPipe_DenseTransfer.io.count, iters_x330_outr_UnitPipe_DenseTransfer.io.count, 0.U, 0.U)
      val x297_fifo = (new x297_fifo).m.io.asInstanceOf[FIFOInterface]
      val x305_inr_UnitPipe = new x305_inr_UnitPipe_kernel(List(x291_inDRAM), List(x297_fifo), List(x296) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x305_inr_UnitPipe.sm.io.ctrDone := risingEdge(x305_inr_UnitPipe.sm.io.ctrInc)
      x305_inr_UnitPipe.backpressure := x296.ready & (~x297_fifo.full.D(1.0-1) | ~(x297_fifo.active(0).out)) | x305_inr_UnitPipe.sm.io.doneLatch
      x305_inr_UnitPipe.forwardpressure := (true.B) && (true.B) | x305_inr_UnitPipe.sm.io.doneLatch
      x305_inr_UnitPipe.sm.io.enableOut.zip(x305_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x305_inr_UnitPipe.sm.io.break := false.B
      x305_inr_UnitPipe.mask := true.B & true.B
      x305_inr_UnitPipe.configure("x305_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x305_inr_UnitPipe.kernel()
      val x329_outr_UnitPipe = new x329_outr_UnitPipe_kernel(List(x297_fifo), List(x298), List(x294_buf_0) ,  Some(me), List(), 1, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x329_outr_UnitPipe.sm.io.ctrDone := risingEdge(x329_outr_UnitPipe.sm.io.ctrInc)
      x329_outr_UnitPipe.backpressure := true.B | x329_outr_UnitPipe.sm.io.doneLatch
      x329_outr_UnitPipe.forwardpressure := (true.B) && (true.B) | x329_outr_UnitPipe.sm.io.doneLatch
      x329_outr_UnitPipe.sm.io.enableOut.zip(x329_outr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x329_outr_UnitPipe.sm.io.break := false.B
      x329_outr_UnitPipe.mask := true.B & true.B
      x329_outr_UnitPipe.configure("x329_outr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x329_outr_UnitPipe.kernel()
    }
    val module = Module(new x330_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x330_outr_UnitPipe_DenseTransfer **/
