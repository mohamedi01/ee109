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

/** Hierarchy: x363 -> x480 -> x481 **/
/** BEGIN Some(DenseTransfer) x363_outr_UnitPipe_DenseTransfer **/
class x363_outr_UnitPipe_DenseTransfer_kernel(
  list_x321_realDRAM: List[FixedPoint],
  list_x329: List[DecoupledIO[AppCommandDense]],
  list_x331: List[DecoupledIO[AppLoadData]],
  list_x326_realSRAM_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 2, isFSM = false   , latency = 0.0.toInt, myName = "x363_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x363_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x363_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x321_realDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x326_realSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x326_realSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x329 = Decoupled(new AppCommandDense(ModuleParams.getParams("x329_p").asInstanceOf[(Int,Int)] ))
      val in_x331 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x331_p").asInstanceOf[(Int, Int)] )))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 2, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 2))
      val rr = Input(Bool())
    })
    def x321_realDRAM = {io.in_x321_realDRAM} 
    def x326_realSRAM_0 = {io.in_x326_realSRAM_0} ; io.in_x326_realSRAM_0 := DontCare
    def x329 = {io.in_x329} 
    def x331 = {io.in_x331} 
  }
  def connectWires0(module: x363_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x321_realDRAM <> x321_realDRAM
    x326_realSRAM_0.connectLedger(module.io.in_x326_realSRAM_0)
    module.io.in_x329 <> x329
    module.io.in_x331 <> x331
  }
  val x321_realDRAM = list_x321_realDRAM(0)
  val x329 = list_x329(0)
  val x331 = list_x331(0)
  val x326_realSRAM_0 = list_x326_realSRAM_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x363_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x363_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x363_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x363_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      val iters_x363_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      cycles_x363_outr_UnitPipe_DenseTransfer.io.enable := io.sigsIn.baseEn
      iters_x363_outr_UnitPipe_DenseTransfer.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X363_instrctr, cycles_x363_outr_UnitPipe_DenseTransfer.io.count, iters_x363_outr_UnitPipe_DenseTransfer.io.count, 0.U, 0.U)
      val x330_fifo = (new x330_fifo).m.io.asInstanceOf[FIFOInterface]
      val x338_inr_UnitPipe = new x338_inr_UnitPipe_kernel(List(x321_realDRAM), List(x330_fifo), List(x329) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x338_inr_UnitPipe.sm.io.ctrDone := risingEdge(x338_inr_UnitPipe.sm.io.ctrInc)
      x338_inr_UnitPipe.backpressure := x329.ready & (~x330_fifo.full.D(1.0-1) | ~(x330_fifo.active(0).out)) | x338_inr_UnitPipe.sm.io.doneLatch
      x338_inr_UnitPipe.forwardpressure := (true.B) && (true.B) | x338_inr_UnitPipe.sm.io.doneLatch
      x338_inr_UnitPipe.sm.io.enableOut.zip(x338_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x338_inr_UnitPipe.sm.io.break := false.B
      x338_inr_UnitPipe.mask := true.B & true.B
      x338_inr_UnitPipe.configure("x338_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x338_inr_UnitPipe.kernel()
      val x362_outr_UnitPipe = new x362_outr_UnitPipe_kernel(List(x330_fifo), List(x331), List(x326_realSRAM_0) ,  Some(me), List(), 1, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x362_outr_UnitPipe.sm.io.ctrDone := risingEdge(x362_outr_UnitPipe.sm.io.ctrInc)
      x362_outr_UnitPipe.backpressure := true.B | x362_outr_UnitPipe.sm.io.doneLatch
      x362_outr_UnitPipe.forwardpressure := (true.B) && (true.B) | x362_outr_UnitPipe.sm.io.doneLatch
      x362_outr_UnitPipe.sm.io.enableOut.zip(x362_outr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x362_outr_UnitPipe.sm.io.break := false.B
      x362_outr_UnitPipe.mask := true.B & true.B
      x362_outr_UnitPipe.configure("x362_outr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x362_outr_UnitPipe.kernel()
    }
    val module = Module(new x363_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x363_outr_UnitPipe_DenseTransfer **/
