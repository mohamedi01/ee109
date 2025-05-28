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

/** Hierarchy: x316 -> x244 **/
/** BEGIN Some(DenseTransfer) x316_outr_UnitPipe_DenseTransfer **/
class x316_outr_UnitPipe_DenseTransfer_kernel(
  list_x277_inDRAM: List[FixedPoint],
  list_x282: List[DecoupledIO[AppCommandDense]],
  list_x284: List[DecoupledIO[AppLoadData]],
  list_x280_inSRAM_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 2, isFSM = false   , latency = 0.0.toInt, myName = "x316_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x316_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x316_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x280_inSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x280_inSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x277_inDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x284 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x284_p").asInstanceOf[(Int, Int)] )))
      val in_x282 = Decoupled(new AppCommandDense(ModuleParams.getParams("x282_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 2, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 2))
      val rr = Input(Bool())
    })
    def x280_inSRAM_0 = {io.in_x280_inSRAM_0} ; io.in_x280_inSRAM_0 := DontCare
    def x277_inDRAM = {io.in_x277_inDRAM} 
    def x284 = {io.in_x284} 
    def x282 = {io.in_x282} 
  }
  def connectWires0(module: x316_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    x280_inSRAM_0.connectLedger(module.io.in_x280_inSRAM_0)
    module.io.in_x277_inDRAM <> x277_inDRAM
    module.io.in_x284 <> x284
    module.io.in_x282 <> x282
  }
  val x277_inDRAM = list_x277_inDRAM(0)
  val x282 = list_x282(0)
  val x284 = list_x284(0)
  val x280_inSRAM_0 = list_x280_inSRAM_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x316_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x316_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x316_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x316_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      val iters_x316_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      cycles_x316_outr_UnitPipe_DenseTransfer.io.enable := io.sigsIn.baseEn
      iters_x316_outr_UnitPipe_DenseTransfer.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X316_instrctr, cycles_x316_outr_UnitPipe_DenseTransfer.io.count, iters_x316_outr_UnitPipe_DenseTransfer.io.count, 0.U, 0.U)
      val x283_fifo = (new x283_fifo).m.io.asInstanceOf[FIFOInterface]
      val x291_inr_UnitPipe = new x291_inr_UnitPipe_kernel(List(x277_inDRAM), List(x283_fifo), List(x282) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x291_inr_UnitPipe.sm.io.ctrDone := risingEdge(x291_inr_UnitPipe.sm.io.ctrInc)
      x291_inr_UnitPipe.backpressure := x282.ready & (~x283_fifo.full.D(1.0-1) | ~(x283_fifo.active(0).out)) | x291_inr_UnitPipe.sm.io.doneLatch
      x291_inr_UnitPipe.forwardpressure := (true.B) && (true.B) | x291_inr_UnitPipe.sm.io.doneLatch
      x291_inr_UnitPipe.sm.io.enableOut.zip(x291_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x291_inr_UnitPipe.sm.io.break := false.B
      x291_inr_UnitPipe.mask := true.B & true.B
      x291_inr_UnitPipe.configure("x291_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x291_inr_UnitPipe.kernel()
      val x315_outr_UnitPipe = new x315_outr_UnitPipe_kernel(List(x283_fifo), List(x284), List(x280_inSRAM_0) ,  Some(me), List(), 1, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x315_outr_UnitPipe.sm.io.ctrDone := risingEdge(x315_outr_UnitPipe.sm.io.ctrInc)
      x315_outr_UnitPipe.backpressure := true.B | x315_outr_UnitPipe.sm.io.doneLatch
      x315_outr_UnitPipe.forwardpressure := (true.B) && (true.B) | x315_outr_UnitPipe.sm.io.doneLatch
      x315_outr_UnitPipe.sm.io.enableOut.zip(x315_outr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x315_outr_UnitPipe.sm.io.break := false.B
      x315_outr_UnitPipe.mask := true.B & true.B
      x315_outr_UnitPipe.configure("x315_outr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x315_outr_UnitPipe.kernel()
    }
    val module = Module(new x316_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x316_outr_UnitPipe_DenseTransfer **/
