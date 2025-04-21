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

/** Hierarchy: x209 -> x119 **/
/** BEGIN Some(DenseTransfer) x209_outr_UnitPipe_DenseTransfer **/
class x209_outr_UnitPipe_DenseTransfer_kernel(
  list_x161_a_0: List[StandardInterface],
  list_x160_out: List[FixedPoint],
  list_x188: List[DecoupledIO[AppCommandDense]],
  list_x189: List[DecoupledIO[AppStoreData]],
  list_x190: List[DecoupledIO[Bool]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 3, isFSM = false   , latency = 0.0.toInt, myName = "x209_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x209_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x209_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x189 = Decoupled(new AppStoreData(ModuleParams.getParams("x189_p").asInstanceOf[(Int,Int)] ))
      val in_x160_out = Input(new FixedPoint(true, 64, 0))
      val in_x188 = Decoupled(new AppCommandDense(ModuleParams.getParams("x188_p").asInstanceOf[(Int,Int)] ))
      val in_x161_a_0 = Flipped(new StandardInterface(ModuleParams.getParams("x161_a_0_p").asInstanceOf[MemParams] ))
      val in_x190 = Flipped(Decoupled(Bool()))
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
    def x190 = {io.in_x190} 
  }
  def connectWires0(module: x209_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x189 <> x189
    module.io.in_x160_out <> x160_out
    module.io.in_x188 <> x188
    x161_a_0.connectLedger(module.io.in_x161_a_0)
    module.io.in_x190 <> x190
  }
  val x161_a_0 = list_x161_a_0(0)
  val x160_out = list_x160_out(0)
  val x188 = list_x188(0)
  val x189 = list_x189(0)
  val x190 = list_x190(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x209_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x209_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x209_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x209_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      val iters_x209_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      cycles_x209_outr_UnitPipe_DenseTransfer.io.enable := io.sigsIn.baseEn
      iters_x209_outr_UnitPipe_DenseTransfer.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X209_instrctr, cycles_x209_outr_UnitPipe_DenseTransfer.io.count, iters_x209_outr_UnitPipe_DenseTransfer.io.count, 0.U, 0.U)
      val x195_inr_UnitPipe_DenseTransferCommands = new x195_inr_UnitPipe_DenseTransferCommands_kernel(List(x160_out), List(x188) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x195_inr_UnitPipe_DenseTransferCommands.sm.io.ctrDone := risingEdge(x195_inr_UnitPipe_DenseTransferCommands.sm.io.ctrInc)
      x195_inr_UnitPipe_DenseTransferCommands.backpressure := x188.ready | x195_inr_UnitPipe_DenseTransferCommands.sm.io.doneLatch
      x195_inr_UnitPipe_DenseTransferCommands.forwardpressure := (true.B) && (true.B) | x195_inr_UnitPipe_DenseTransferCommands.sm.io.doneLatch
      x195_inr_UnitPipe_DenseTransferCommands.sm.io.enableOut.zip(x195_inr_UnitPipe_DenseTransferCommands.smEnableOuts).foreach{case (l,r) => r := l}
      x195_inr_UnitPipe_DenseTransferCommands.sm.io.break := false.B
      x195_inr_UnitPipe_DenseTransferCommands.mask := true.B & true.B
      x195_inr_UnitPipe_DenseTransferCommands.configure("x195_inr_UnitPipe_DenseTransferCommands", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x195_inr_UnitPipe_DenseTransferCommands.kernel()
      val x196_ctr = new CtrObject(Left(Some(0)), Left(Some(16)), Left(Some(1)), 1, 7, false)
      val x197_ctrchain = (new CChainObject(List[CtrObject](x196_ctr), "x197_ctrchain")).cchain.io 
      x197_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x197_ctrchain_p", (x197_ctrchain.par, x197_ctrchain.widths))
      val x204_inr_Foreach = new x204_inr_Foreach_kernel(List(x161_a_0), List(x189) ,  Some(me), List(x197_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x204_inr_Foreach.sm.io.ctrDone := (x204_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x204_inr_Foreach.backpressure := x189.ready | x204_inr_Foreach.sm.io.doneLatch
      x204_inr_Foreach.forwardpressure := (true.B) && (true.B) | x204_inr_Foreach.sm.io.doneLatch
      x204_inr_Foreach.sm.io.enableOut.zip(x204_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x204_inr_Foreach.sm.io.break := false.B
      x204_inr_Foreach.mask := ~x204_inr_Foreach.cchain.head.output.noop & true.B
      x204_inr_Foreach.configure("x204_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x204_inr_Foreach.kernel()
      val x208_inr_UnitPipe_DenseTransferAck = new x208_inr_UnitPipe_DenseTransferAck_kernel(List(x190) ,  Some(me), List(), 2, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x208_inr_UnitPipe_DenseTransferAck.sm.io.ctrDone := risingEdge(x208_inr_UnitPipe_DenseTransferAck.sm.io.ctrInc)
      x208_inr_UnitPipe_DenseTransferAck.backpressure := true.B | x208_inr_UnitPipe_DenseTransferAck.sm.io.doneLatch
      x208_inr_UnitPipe_DenseTransferAck.forwardpressure := (x190.valid) && (true.B) | x208_inr_UnitPipe_DenseTransferAck.sm.io.doneLatch
      x208_inr_UnitPipe_DenseTransferAck.sm.io.enableOut.zip(x208_inr_UnitPipe_DenseTransferAck.smEnableOuts).foreach{case (l,r) => r := l}
      x208_inr_UnitPipe_DenseTransferAck.sm.io.break := false.B
      x208_inr_UnitPipe_DenseTransferAck.mask := true.B & true.B
      x208_inr_UnitPipe_DenseTransferAck.configure("x208_inr_UnitPipe_DenseTransferAck", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x208_inr_UnitPipe_DenseTransferAck.kernel()
    }
    val module = Module(new x209_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x209_outr_UnitPipe_DenseTransfer **/
