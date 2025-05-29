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

/** Hierarchy: x289 -> x213 **/
/** BEGIN Some(DenseTransfer) x289_outr_UnitPipe_DenseTransfer **/
class x289_outr_UnitPipe_DenseTransfer_kernel(
  list_x250_inDRAM: List[FixedPoint],
  list_x255: List[DecoupledIO[AppCommandDense]],
  list_x257: List[DecoupledIO[AppLoadData]],
  list_x253_inSRAM_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 2, isFSM = false   , latency = 0.0.toInt, myName = "x289_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x289_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x289_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x253_inSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x253_inSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x250_inDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x257 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x257_p").asInstanceOf[(Int, Int)] )))
      val in_x255 = Decoupled(new AppCommandDense(ModuleParams.getParams("x255_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 2, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 2))
      val rr = Input(Bool())
    })
    def x253_inSRAM_0 = {io.in_x253_inSRAM_0} ; io.in_x253_inSRAM_0 := DontCare
    def x250_inDRAM = {io.in_x250_inDRAM} 
    def x257 = {io.in_x257} 
    def x255 = {io.in_x255} 
  }
  def connectWires0(module: x289_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    x253_inSRAM_0.connectLedger(module.io.in_x253_inSRAM_0)
    module.io.in_x250_inDRAM <> x250_inDRAM
    module.io.in_x257 <> x257
    module.io.in_x255 <> x255
  }
  val x250_inDRAM = list_x250_inDRAM(0)
  val x255 = list_x255(0)
  val x257 = list_x257(0)
  val x253_inSRAM_0 = list_x253_inSRAM_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x289_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x289_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x289_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x289_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      val iters_x289_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      cycles_x289_outr_UnitPipe_DenseTransfer.io.enable := io.sigsIn.baseEn
      iters_x289_outr_UnitPipe_DenseTransfer.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X289_instrctr, cycles_x289_outr_UnitPipe_DenseTransfer.io.count, iters_x289_outr_UnitPipe_DenseTransfer.io.count, 0.U, 0.U)
      val x256_fifo = (new x256_fifo).m.io.asInstanceOf[FIFOInterface]
      val x264_inr_UnitPipe = new x264_inr_UnitPipe_kernel(List(x250_inDRAM), List(x256_fifo), List(x255) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x264_inr_UnitPipe.sm.io.ctrDone := risingEdge(x264_inr_UnitPipe.sm.io.ctrInc)
      x264_inr_UnitPipe.backpressure := (~x256_fifo.full.D(1.0-1) | ~(x256_fifo.active(0).out)) & x255.ready | x264_inr_UnitPipe.sm.io.doneLatch
      x264_inr_UnitPipe.forwardpressure := (true.B) && (true.B) | x264_inr_UnitPipe.sm.io.doneLatch
      x264_inr_UnitPipe.sm.io.enableOut.zip(x264_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x264_inr_UnitPipe.sm.io.break := false.B
      x264_inr_UnitPipe.mask := true.B & true.B
      x264_inr_UnitPipe.configure("x264_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x264_inr_UnitPipe.kernel()
      val x288_outr_UnitPipe = new x288_outr_UnitPipe_kernel(List(x256_fifo), List(x257), List(x253_inSRAM_0) ,  Some(me), List(), 1, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x288_outr_UnitPipe.sm.io.ctrDone := risingEdge(x288_outr_UnitPipe.sm.io.ctrInc)
      x288_outr_UnitPipe.backpressure := true.B | x288_outr_UnitPipe.sm.io.doneLatch
      x288_outr_UnitPipe.forwardpressure := (true.B) && (true.B) | x288_outr_UnitPipe.sm.io.doneLatch
      x288_outr_UnitPipe.sm.io.enableOut.zip(x288_outr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x288_outr_UnitPipe.sm.io.break := false.B
      x288_outr_UnitPipe.mask := true.B & true.B
      x288_outr_UnitPipe.configure("x288_outr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x288_outr_UnitPipe.kernel()
    }
    val module = Module(new x289_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x289_outr_UnitPipe_DenseTransfer **/
