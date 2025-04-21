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

/** Hierarchy: x168 **/
/** BEGIN None RootController **/
class RootController_kernel(
  list_x210_dram: List[FixedPoint],
  list_x246: List[DecoupledIO[AppCommandDense]],
  list_x248: List[DecoupledIO[Bool]],
  list_x247: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 3, isFSM = false   , latency = 0.0.toInt, myName = "RootController_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "RootController_iiCtr"))
  
  abstract class RootController_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x210_dram = Input(new FixedPoint(true, 64, 0))
      val in_x246 = Decoupled(new AppCommandDense(ModuleParams.getParams("x246_p").asInstanceOf[(Int,Int)] ))
      val in_x247 = Decoupled(new AppStoreData(ModuleParams.getParams("x247_p").asInstanceOf[(Int,Int)] ))
      val in_x248 = Flipped(Decoupled(Bool()))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(3, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(3, 1))
      val rr = Input(Bool())
    })
    def x210_dram = {io.in_x210_dram} 
    def x246 = {io.in_x246} 
    def x247 = {io.in_x247} 
    def x248 = {io.in_x248} 
  }
  def connectWires0(module: RootController_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x210_dram <> x210_dram
    module.io.in_x246 <> x246
    module.io.in_x247 <> x247
    module.io.in_x248 <> x248
  }
  val x210_dram = list_x210_dram(0)
  val x246 = list_x246(0)
  val x248 = list_x248(0)
  val x247 = list_x247(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "RootController")
    implicit val stack = ControllerStack.stack.toList
    class RootController_concrete(depth: Int)(implicit stack: List[KernelHash]) extends RootController_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_RootController = Module(new InstrumentationCounter())
      val iters_RootController = Module(new InstrumentationCounter())
      cycles_RootController.io.enable := io.sigsIn.baseEn
      iters_RootController.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X168_instrctr, cycles_RootController.io.count, iters_RootController.io.count, 0.U, 0.U)
      val x211_bram_0 = (new x211_bram_0).m.io.asInstanceOf[StandardInterface]
      val x212_reg_0 = (new x212_reg_0).m.io.asInstanceOf[StandardInterface]
      val x214_inr_UnitPipe = new x214_inr_UnitPipe_kernel(List(x212_reg_0) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x214_inr_UnitPipe.sm.io.ctrDone := risingEdge(x214_inr_UnitPipe.sm.io.ctrInc)
      x214_inr_UnitPipe.backpressure := true.B | x214_inr_UnitPipe.sm.io.doneLatch
      x214_inr_UnitPipe.forwardpressure := (true.B) && (true.B) | x214_inr_UnitPipe.sm.io.doneLatch
      x214_inr_UnitPipe.sm.io.enableOut.zip(x214_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x214_inr_UnitPipe.sm.io.break := false.B
      x214_inr_UnitPipe.mask := true.B & true.B
      x214_inr_UnitPipe.configure("x214_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x214_inr_UnitPipe.kernel()
      val x245_inr_FSM = new x245_inr_FSM_kernel(List(x211_bram_0,x212_reg_0) ,  Some(me), List(), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x245_inr_FSM.sm.io.ctrDone := x245_inr_FSM.iiDone //.D(4.0)
      x245_inr_FSM.backpressure := true.B | x245_inr_FSM.sm.io.doneLatch
      x245_inr_FSM.forwardpressure := (true.B) && (true.B) | x245_inr_FSM.sm.io.doneLatch
      x245_inr_FSM.sm.io.enableOut.zip(x245_inr_FSM.smEnableOuts).foreach{case (l,r) => r := l}
      x245_inr_FSM.sm.io.break := false.B
      x245_inr_FSM.mask := true.B & true.B
      x245_inr_FSM.configure("x245_inr_FSM", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x245_inr_FSM.kernel()
      val x267_outr_UnitPipe_DenseTransfer = new x267_outr_UnitPipe_DenseTransfer_kernel(List(x246), List(x211_bram_0), List(x210_dram), List(x248), List(x247) ,  Some(me), List(), 2, 3, 3, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x267_outr_UnitPipe_DenseTransfer.backpressure := true.B | x267_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x267_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x267_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x267_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x267_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x267_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x267_outr_UnitPipe_DenseTransfer.mask := true.B & true.B
      x267_outr_UnitPipe_DenseTransfer.configure("x267_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x267_outr_UnitPipe_DenseTransfer.kernel()
    }
    val module = Module(new RootController_concrete(sm.p.depth)); module.io := DontCare
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
/** END AccelScope RootController **/
