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

/** Hierarchy: x164 **/
/** BEGIN None RootController **/
class RootController_kernel(
  list_x206_dram: List[FixedPoint],
  list_x237: List[DecoupledIO[AppCommandDense]],
  list_x239: List[DecoupledIO[Bool]],
  list_x238: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "RootController_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "RootController_iiCtr"))
  
  abstract class RootController_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x206_dram = Input(new FixedPoint(true, 64, 0))
      val in_x237 = Decoupled(new AppCommandDense(ModuleParams.getParams("x237_p").asInstanceOf[(Int,Int)] ))
      val in_x238 = Decoupled(new AppStoreData(ModuleParams.getParams("x238_p").asInstanceOf[(Int,Int)] ))
      val in_x239 = Flipped(Decoupled(Bool()))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x206_dram = {io.in_x206_dram} 
    def x237 = {io.in_x237} 
    def x238 = {io.in_x238} 
    def x239 = {io.in_x239} 
  }
  def connectWires0(module: RootController_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x206_dram <> x206_dram
    module.io.in_x237 <> x237
    module.io.in_x238 <> x238
    module.io.in_x239 <> x239
  }
  val x206_dram = list_x206_dram(0)
  val x237 = list_x237(0)
  val x239 = list_x239(0)
  val x238 = list_x238(0)
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
      Ledger.tieInstrCtr(instrctrs.toList, X164_instrctr, cycles_RootController.io.count, iters_RootController.io.count, 0.U, 0.U)
      val x207_bram_0 = (new x207_bram_0).m.io.asInstanceOf[StandardInterface]
      val x236_inr_FSM = new x236_inr_FSM_kernel(List(x207_bram_0) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x236_inr_FSM.sm.io.ctrDone := x236_inr_FSM.iiDone //.D(4.4)
      x236_inr_FSM.backpressure := true.B | x236_inr_FSM.sm.io.doneLatch
      x236_inr_FSM.forwardpressure := (true.B) && (true.B) | x236_inr_FSM.sm.io.doneLatch
      x236_inr_FSM.sm.io.enableOut.zip(x236_inr_FSM.smEnableOuts).foreach{case (l,r) => r := l}
      x236_inr_FSM.sm.io.break := false.B
      x236_inr_FSM.mask := true.B & true.B
      x236_inr_FSM.configure("x236_inr_FSM", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x236_inr_FSM.kernel()
      val x258_outr_UnitPipe_DenseTransfer = new x258_outr_UnitPipe_DenseTransfer_kernel(List(x239), List(x238), List(x206_dram), List(x237), List(x207_bram_0) ,  Some(me), List(), 1, 3, 3, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x258_outr_UnitPipe_DenseTransfer.backpressure := true.B | x258_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x258_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x258_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x258_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x258_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x258_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x258_outr_UnitPipe_DenseTransfer.mask := true.B & true.B
      x258_outr_UnitPipe_DenseTransfer.configure("x258_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x258_outr_UnitPipe_DenseTransfer.kernel()
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
