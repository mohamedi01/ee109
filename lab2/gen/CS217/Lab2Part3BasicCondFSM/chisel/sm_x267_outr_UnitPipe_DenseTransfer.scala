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

/** Hierarchy: x267 -> x168 **/
/** BEGIN Some(DenseTransfer) x267_outr_UnitPipe_DenseTransfer **/
class x267_outr_UnitPipe_DenseTransfer_kernel(
  list_x246: List[DecoupledIO[AppCommandDense]],
  list_x211_bram_0: List[StandardInterface],
  list_x210_dram: List[FixedPoint],
  list_x248: List[DecoupledIO[Bool]],
  list_x247: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 3, isFSM = false   , latency = 0.0.toInt, myName = "x267_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x267_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x267_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x247 = Decoupled(new AppStoreData(ModuleParams.getParams("x247_p").asInstanceOf[(Int,Int)] ))
      val in_x211_bram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x211_bram_0_p").asInstanceOf[MemParams] ))
      val in_x248 = Flipped(Decoupled(Bool()))
      val in_x246 = Decoupled(new AppCommandDense(ModuleParams.getParams("x246_p").asInstanceOf[(Int,Int)] ))
      val in_x210_dram = Input(new FixedPoint(true, 64, 0))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(3, 3, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(3, 3))
      val rr = Input(Bool())
    })
    def x247 = {io.in_x247} 
    def x211_bram_0 = {io.in_x211_bram_0} ; io.in_x211_bram_0 := DontCare
    def x248 = {io.in_x248} 
    def x246 = {io.in_x246} 
    def x210_dram = {io.in_x210_dram} 
  }
  def connectWires0(module: x267_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x247 <> x247
    x211_bram_0.connectLedger(module.io.in_x211_bram_0)
    module.io.in_x248 <> x248
    module.io.in_x246 <> x246
    module.io.in_x210_dram <> x210_dram
  }
  val x246 = list_x246(0)
  val x211_bram_0 = list_x211_bram_0(0)
  val x210_dram = list_x210_dram(0)
  val x248 = list_x248(0)
  val x247 = list_x247(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x267_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x267_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x267_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x267_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      val iters_x267_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      cycles_x267_outr_UnitPipe_DenseTransfer.io.enable := io.sigsIn.baseEn
      iters_x267_outr_UnitPipe_DenseTransfer.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X267_instrctr, cycles_x267_outr_UnitPipe_DenseTransfer.io.count, iters_x267_outr_UnitPipe_DenseTransfer.io.count, 0.U, 0.U)
      val x253_inr_UnitPipe_DenseTransferCommands = new x253_inr_UnitPipe_DenseTransferCommands_kernel(List(x210_dram), List(x246) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x253_inr_UnitPipe_DenseTransferCommands.sm.io.ctrDone := risingEdge(x253_inr_UnitPipe_DenseTransferCommands.sm.io.ctrInc)
      x253_inr_UnitPipe_DenseTransferCommands.backpressure := x246.ready | x253_inr_UnitPipe_DenseTransferCommands.sm.io.doneLatch
      x253_inr_UnitPipe_DenseTransferCommands.forwardpressure := (true.B) && (true.B) | x253_inr_UnitPipe_DenseTransferCommands.sm.io.doneLatch
      x253_inr_UnitPipe_DenseTransferCommands.sm.io.enableOut.zip(x253_inr_UnitPipe_DenseTransferCommands.smEnableOuts).foreach{case (l,r) => r := l}
      x253_inr_UnitPipe_DenseTransferCommands.sm.io.break := false.B
      x253_inr_UnitPipe_DenseTransferCommands.mask := true.B & true.B
      x253_inr_UnitPipe_DenseTransferCommands.configure("x253_inr_UnitPipe_DenseTransferCommands", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x253_inr_UnitPipe_DenseTransferCommands.kernel()
      val x254_ctr = new CtrObject(Left(Some(0)), Left(Some(32)), Left(Some(1)), 1, 8, false)
      val x255_ctrchain = (new CChainObject(List[CtrObject](x254_ctr), "x255_ctrchain")).cchain.io 
      x255_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x255_ctrchain_p", (x255_ctrchain.par, x255_ctrchain.widths))
      val x262_inr_Foreach = new x262_inr_Foreach_kernel(List(x211_bram_0), List(x247) ,  Some(me), List(x255_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x262_inr_Foreach.sm.io.ctrDone := (x262_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x262_inr_Foreach.backpressure := x247.ready | x262_inr_Foreach.sm.io.doneLatch
      x262_inr_Foreach.forwardpressure := (true.B) && (true.B) | x262_inr_Foreach.sm.io.doneLatch
      x262_inr_Foreach.sm.io.enableOut.zip(x262_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x262_inr_Foreach.sm.io.break := false.B
      x262_inr_Foreach.mask := ~x262_inr_Foreach.cchain.head.output.noop & true.B
      x262_inr_Foreach.configure("x262_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x262_inr_Foreach.kernel()
      val x266_inr_UnitPipe_DenseTransferAck = new x266_inr_UnitPipe_DenseTransferAck_kernel(List(x248) ,  Some(me), List(), 2, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x266_inr_UnitPipe_DenseTransferAck.sm.io.ctrDone := risingEdge(x266_inr_UnitPipe_DenseTransferAck.sm.io.ctrInc)
      x266_inr_UnitPipe_DenseTransferAck.backpressure := true.B | x266_inr_UnitPipe_DenseTransferAck.sm.io.doneLatch
      x266_inr_UnitPipe_DenseTransferAck.forwardpressure := (x248.valid) && (true.B) | x266_inr_UnitPipe_DenseTransferAck.sm.io.doneLatch
      x266_inr_UnitPipe_DenseTransferAck.sm.io.enableOut.zip(x266_inr_UnitPipe_DenseTransferAck.smEnableOuts).foreach{case (l,r) => r := l}
      x266_inr_UnitPipe_DenseTransferAck.sm.io.break := false.B
      x266_inr_UnitPipe_DenseTransferAck.mask := true.B & true.B
      x266_inr_UnitPipe_DenseTransferAck.configure("x266_inr_UnitPipe_DenseTransferAck", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x266_inr_UnitPipe_DenseTransferAck.kernel()
    }
    val module = Module(new x267_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x267_outr_UnitPipe_DenseTransfer **/
