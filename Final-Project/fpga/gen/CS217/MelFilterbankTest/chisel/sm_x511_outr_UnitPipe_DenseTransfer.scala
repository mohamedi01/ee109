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

/** Hierarchy: x511 -> x613 -> x614 **/
/** BEGIN Some(DenseTransfer) x511_outr_UnitPipe_DenseTransfer **/
class x511_outr_UnitPipe_DenseTransfer_kernel(
  list_x407_vecDRAM: List[FixedPoint],
  list_x477: List[DecoupledIO[AppCommandDense]],
  list_x479: List[DecoupledIO[AppLoadData]],
  list_x412_vecSRAM_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 2, isFSM = false   , latency = 0.0.toInt, myName = "x511_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x511_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x511_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x407_vecDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x412_vecSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x412_vecSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x479 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x479_p").asInstanceOf[(Int, Int)] )))
      val in_x477 = Decoupled(new AppCommandDense(ModuleParams.getParams("x477_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 2, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 2))
      val rr = Input(Bool())
    })
    def x407_vecDRAM = {io.in_x407_vecDRAM} 
    def x412_vecSRAM_0 = {io.in_x412_vecSRAM_0} ; io.in_x412_vecSRAM_0 := DontCare
    def x479 = {io.in_x479} 
    def x477 = {io.in_x477} 
  }
  def connectWires0(module: x511_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x407_vecDRAM <> x407_vecDRAM
    x412_vecSRAM_0.connectLedger(module.io.in_x412_vecSRAM_0)
    module.io.in_x479 <> x479
    module.io.in_x477 <> x477
  }
  val x407_vecDRAM = list_x407_vecDRAM(0)
  val x477 = list_x477(0)
  val x479 = list_x479(0)
  val x412_vecSRAM_0 = list_x412_vecSRAM_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x511_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x511_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x511_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x511_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      val iters_x511_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      cycles_x511_outr_UnitPipe_DenseTransfer.io.enable := io.sigsIn.baseEn
      iters_x511_outr_UnitPipe_DenseTransfer.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X511_instrctr, cycles_x511_outr_UnitPipe_DenseTransfer.io.count, iters_x511_outr_UnitPipe_DenseTransfer.io.count, 0.U, 0.U)
      val x478_fifo = (new x478_fifo).m.io.asInstanceOf[FIFOInterface]
      val x486_inr_UnitPipe = new x486_inr_UnitPipe_kernel(List(x407_vecDRAM), List(x478_fifo), List(x477) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x486_inr_UnitPipe.sm.io.ctrDone := risingEdge(x486_inr_UnitPipe.sm.io.ctrInc)
      x486_inr_UnitPipe.backpressure := x477.ready & (~x478_fifo.full.D(1.0-1) | ~(x478_fifo.active(0).out)) | x486_inr_UnitPipe.sm.io.doneLatch
      x486_inr_UnitPipe.forwardpressure := (true.B) && (true.B) | x486_inr_UnitPipe.sm.io.doneLatch
      x486_inr_UnitPipe.sm.io.enableOut.zip(x486_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x486_inr_UnitPipe.sm.io.break := false.B
      x486_inr_UnitPipe.mask := true.B & true.B
      x486_inr_UnitPipe.configure("x486_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x486_inr_UnitPipe.kernel()
      val x510_outr_UnitPipe = new x510_outr_UnitPipe_kernel(List(x478_fifo), List(x479), List(x412_vecSRAM_0) ,  Some(me), List(), 1, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x510_outr_UnitPipe.sm.io.ctrDone := risingEdge(x510_outr_UnitPipe.sm.io.ctrInc)
      x510_outr_UnitPipe.backpressure := true.B | x510_outr_UnitPipe.sm.io.doneLatch
      x510_outr_UnitPipe.forwardpressure := (true.B) && (true.B) | x510_outr_UnitPipe.sm.io.doneLatch
      x510_outr_UnitPipe.sm.io.enableOut.zip(x510_outr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x510_outr_UnitPipe.sm.io.break := false.B
      x510_outr_UnitPipe.mask := true.B & true.B
      x510_outr_UnitPipe.configure("x510_outr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x510_outr_UnitPipe.kernel()
    }
    val module = Module(new x511_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x511_outr_UnitPipe_DenseTransfer **/
