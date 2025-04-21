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

/** Hierarchy: x258 -> x164 **/
/** BEGIN Some(DenseTransfer) x258_outr_UnitPipe_DenseTransfer **/
class x258_outr_UnitPipe_DenseTransfer_kernel(
  list_x239: List[DecoupledIO[Bool]],
  list_x238: List[DecoupledIO[AppStoreData]],
  list_x206_dram: List[FixedPoint],
  list_x237: List[DecoupledIO[AppCommandDense]],
  list_x207_bram_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 3, isFSM = false   , latency = 0.0.toInt, myName = "x258_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x258_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x258_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x238 = Decoupled(new AppStoreData(ModuleParams.getParams("x238_p").asInstanceOf[(Int,Int)] ))
      val in_x206_dram = Input(new FixedPoint(true, 64, 0))
      val in_x237 = Decoupled(new AppCommandDense(ModuleParams.getParams("x237_p").asInstanceOf[(Int,Int)] ))
      val in_x207_bram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x207_bram_0_p").asInstanceOf[MemParams] ))
      val in_x239 = Flipped(Decoupled(Bool()))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(3, 3, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(3, 3))
      val rr = Input(Bool())
    })
    def x238 = {io.in_x238} 
    def x206_dram = {io.in_x206_dram} 
    def x237 = {io.in_x237} 
    def x207_bram_0 = {io.in_x207_bram_0} ; io.in_x207_bram_0 := DontCare
    def x239 = {io.in_x239} 
  }
  def connectWires0(module: x258_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x238 <> x238
    module.io.in_x206_dram <> x206_dram
    module.io.in_x237 <> x237
    x207_bram_0.connectLedger(module.io.in_x207_bram_0)
    module.io.in_x239 <> x239
  }
  val x239 = list_x239(0)
  val x238 = list_x238(0)
  val x206_dram = list_x206_dram(0)
  val x237 = list_x237(0)
  val x207_bram_0 = list_x207_bram_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x258_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x258_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x258_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x258_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      val iters_x258_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      cycles_x258_outr_UnitPipe_DenseTransfer.io.enable := io.sigsIn.baseEn
      iters_x258_outr_UnitPipe_DenseTransfer.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X258_instrctr, cycles_x258_outr_UnitPipe_DenseTransfer.io.count, iters_x258_outr_UnitPipe_DenseTransfer.io.count, 0.U, 0.U)
      val x244_inr_UnitPipe_DenseTransferCommands = new x244_inr_UnitPipe_DenseTransferCommands_kernel(List(x206_dram), List(x237) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x244_inr_UnitPipe_DenseTransferCommands.sm.io.ctrDone := risingEdge(x244_inr_UnitPipe_DenseTransferCommands.sm.io.ctrInc)
      x244_inr_UnitPipe_DenseTransferCommands.backpressure := x237.ready | x244_inr_UnitPipe_DenseTransferCommands.sm.io.doneLatch
      x244_inr_UnitPipe_DenseTransferCommands.forwardpressure := (true.B) && (true.B) | x244_inr_UnitPipe_DenseTransferCommands.sm.io.doneLatch
      x244_inr_UnitPipe_DenseTransferCommands.sm.io.enableOut.zip(x244_inr_UnitPipe_DenseTransferCommands.smEnableOuts).foreach{case (l,r) => r := l}
      x244_inr_UnitPipe_DenseTransferCommands.sm.io.break := false.B
      x244_inr_UnitPipe_DenseTransferCommands.mask := true.B & true.B
      x244_inr_UnitPipe_DenseTransferCommands.configure("x244_inr_UnitPipe_DenseTransferCommands", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x244_inr_UnitPipe_DenseTransferCommands.kernel()
      val x245_ctr = new CtrObject(Left(Some(0)), Left(Some(32)), Left(Some(1)), 1, 8, false)
      val x246_ctrchain = (new CChainObject(List[CtrObject](x245_ctr), "x246_ctrchain")).cchain.io 
      x246_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x246_ctrchain_p", (x246_ctrchain.par, x246_ctrchain.widths))
      val x253_inr_Foreach = new x253_inr_Foreach_kernel(List(x207_bram_0), List(x238) ,  Some(me), List(x246_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x253_inr_Foreach.sm.io.ctrDone := (x253_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x253_inr_Foreach.backpressure := x238.ready | x253_inr_Foreach.sm.io.doneLatch
      x253_inr_Foreach.forwardpressure := (true.B) && (true.B) | x253_inr_Foreach.sm.io.doneLatch
      x253_inr_Foreach.sm.io.enableOut.zip(x253_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x253_inr_Foreach.sm.io.break := false.B
      x253_inr_Foreach.mask := ~x253_inr_Foreach.cchain.head.output.noop & true.B
      x253_inr_Foreach.configure("x253_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x253_inr_Foreach.kernel()
      val x257_inr_UnitPipe_DenseTransferAck = new x257_inr_UnitPipe_DenseTransferAck_kernel(List(x239) ,  Some(me), List(), 2, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x257_inr_UnitPipe_DenseTransferAck.sm.io.ctrDone := risingEdge(x257_inr_UnitPipe_DenseTransferAck.sm.io.ctrInc)
      x257_inr_UnitPipe_DenseTransferAck.backpressure := true.B | x257_inr_UnitPipe_DenseTransferAck.sm.io.doneLatch
      x257_inr_UnitPipe_DenseTransferAck.forwardpressure := (x239.valid) && (true.B) | x257_inr_UnitPipe_DenseTransferAck.sm.io.doneLatch
      x257_inr_UnitPipe_DenseTransferAck.sm.io.enableOut.zip(x257_inr_UnitPipe_DenseTransferAck.smEnableOuts).foreach{case (l,r) => r := l}
      x257_inr_UnitPipe_DenseTransferAck.sm.io.break := false.B
      x257_inr_UnitPipe_DenseTransferAck.mask := true.B & true.B
      x257_inr_UnitPipe_DenseTransferAck.configure("x257_inr_UnitPipe_DenseTransferAck", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x257_inr_UnitPipe_DenseTransferAck.kernel()
    }
    val module = Module(new x258_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x258_outr_UnitPipe_DenseTransfer **/
