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

/** Hierarchy: x208 -> x119 **/
/** BEGIN Some(DenseTransfer) x208_outr_UnitPipe_DenseTransfer **/
class x208_outr_UnitPipe_DenseTransfer_kernel(
  list_x161_a_0: List[StandardInterface],
  list_x160_out: List[FixedPoint],
  list_x188: List[DecoupledIO[AppStoreData]],
  list_x189: List[DecoupledIO[Bool]],
  list_x187: List[DecoupledIO[AppCommandDense]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 3, isFSM = false   , latency = 0.0.toInt, myName = "x208_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x208_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x208_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x189 = Flipped(Decoupled(Bool()))
      val in_x160_out = Input(new FixedPoint(true, 64, 0))
      val in_x188 = Decoupled(new AppStoreData(ModuleParams.getParams("x188_p").asInstanceOf[(Int,Int)] ))
      val in_x161_a_0 = Flipped(new StandardInterface(ModuleParams.getParams("x161_a_0_p").asInstanceOf[MemParams] ))
      val in_x187 = Decoupled(new AppCommandDense(ModuleParams.getParams("x187_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(3, 3, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(3, 3))
      val rr = Input(Bool())
    })
    def x189 = {io.in_x189} 
    def x160_out = {io.in_x160_out} 
    def x188 = {io.in_x188} 
    def x161_a_0 = {io.in_x161_a_0} ; io.in_x161_a_0 := DontCare
    def x187 = {io.in_x187} 
  }
  def connectWires0(module: x208_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x189 <> x189
    module.io.in_x160_out <> x160_out
    module.io.in_x188 <> x188
    x161_a_0.connectLedger(module.io.in_x161_a_0)
    module.io.in_x187 <> x187
  }
  val x161_a_0 = list_x161_a_0(0)
  val x160_out = list_x160_out(0)
  val x188 = list_x188(0)
  val x189 = list_x189(0)
  val x187 = list_x187(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x208_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x208_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x208_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x208_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      val iters_x208_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      cycles_x208_outr_UnitPipe_DenseTransfer.io.enable := io.sigsIn.baseEn
      iters_x208_outr_UnitPipe_DenseTransfer.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X208_instrctr, cycles_x208_outr_UnitPipe_DenseTransfer.io.count, iters_x208_outr_UnitPipe_DenseTransfer.io.count, 0.U, 0.U)
      val x194_inr_UnitPipe_DenseTransferCommands = new x194_inr_UnitPipe_DenseTransferCommands_kernel(List(x160_out), List(x187) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x194_inr_UnitPipe_DenseTransferCommands.sm.io.ctrDone := risingEdge(x194_inr_UnitPipe_DenseTransferCommands.sm.io.ctrInc)
      x194_inr_UnitPipe_DenseTransferCommands.backpressure := x187.ready | x194_inr_UnitPipe_DenseTransferCommands.sm.io.doneLatch
      x194_inr_UnitPipe_DenseTransferCommands.forwardpressure := (true.B) && (true.B) | x194_inr_UnitPipe_DenseTransferCommands.sm.io.doneLatch
      x194_inr_UnitPipe_DenseTransferCommands.sm.io.enableOut.zip(x194_inr_UnitPipe_DenseTransferCommands.smEnableOuts).foreach{case (l,r) => r := l}
      x194_inr_UnitPipe_DenseTransferCommands.sm.io.break := false.B
      x194_inr_UnitPipe_DenseTransferCommands.mask := true.B & true.B
      x194_inr_UnitPipe_DenseTransferCommands.configure("x194_inr_UnitPipe_DenseTransferCommands", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x194_inr_UnitPipe_DenseTransferCommands.kernel()
      val x195_ctr = new CtrObject(Left(Some(0)), Left(Some(16)), Left(Some(1)), 1, 7, false)
      val x196_ctrchain = (new CChainObject(List[CtrObject](x195_ctr), "x196_ctrchain")).cchain.io 
      x196_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x196_ctrchain_p", (x196_ctrchain.par, x196_ctrchain.widths))
      val x203_inr_Foreach = new x203_inr_Foreach_kernel(List(x161_a_0), List(x188) ,  Some(me), List(x196_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x203_inr_Foreach.sm.io.ctrDone := (x203_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x203_inr_Foreach.backpressure := x188.ready | x203_inr_Foreach.sm.io.doneLatch
      x203_inr_Foreach.forwardpressure := (true.B) && (true.B) | x203_inr_Foreach.sm.io.doneLatch
      x203_inr_Foreach.sm.io.enableOut.zip(x203_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x203_inr_Foreach.sm.io.break := false.B
      x203_inr_Foreach.mask := ~x203_inr_Foreach.cchain.head.output.noop & true.B
      x203_inr_Foreach.configure("x203_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x203_inr_Foreach.kernel()
      val x207_inr_UnitPipe_DenseTransferAck = new x207_inr_UnitPipe_DenseTransferAck_kernel(List(x189) ,  Some(me), List(), 2, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x207_inr_UnitPipe_DenseTransferAck.sm.io.ctrDone := risingEdge(x207_inr_UnitPipe_DenseTransferAck.sm.io.ctrInc)
      x207_inr_UnitPipe_DenseTransferAck.backpressure := true.B | x207_inr_UnitPipe_DenseTransferAck.sm.io.doneLatch
      x207_inr_UnitPipe_DenseTransferAck.forwardpressure := (x189.valid) && (true.B) | x207_inr_UnitPipe_DenseTransferAck.sm.io.doneLatch
      x207_inr_UnitPipe_DenseTransferAck.sm.io.enableOut.zip(x207_inr_UnitPipe_DenseTransferAck.smEnableOuts).foreach{case (l,r) => r := l}
      x207_inr_UnitPipe_DenseTransferAck.sm.io.break := false.B
      x207_inr_UnitPipe_DenseTransferAck.mask := true.B & true.B
      x207_inr_UnitPipe_DenseTransferAck.configure("x207_inr_UnitPipe_DenseTransferAck", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x207_inr_UnitPipe_DenseTransferAck.kernel()
    }
    val module = Module(new x208_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x208_outr_UnitPipe_DenseTransfer **/
