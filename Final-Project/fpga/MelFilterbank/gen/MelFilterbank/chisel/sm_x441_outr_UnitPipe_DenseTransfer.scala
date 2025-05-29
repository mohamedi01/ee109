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

/** Hierarchy: x441 -> x453 **/
/** BEGIN Some(DenseTransfer) x441_outr_UnitPipe_DenseTransfer **/
class x441_outr_UnitPipe_DenseTransfer_kernel(
  list_x422: List[DecoupledIO[Bool]],
  list_x293_outDram: List[FixedPoint],
  list_x296_outSram_0: List[StandardInterface],
  list_x420: List[DecoupledIO[AppCommandDense]],
  list_x421: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 3, isFSM = false   , latency = 0.0.toInt, myName = "x441_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x441_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x441_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x421 = Decoupled(new AppStoreData(ModuleParams.getParams("x421_p").asInstanceOf[(Int,Int)] ))
      val in_x293_outDram = Input(new FixedPoint(true, 64, 0))
      val in_x420 = Decoupled(new AppCommandDense(ModuleParams.getParams("x420_p").asInstanceOf[(Int,Int)] ))
      val in_x296_outSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x296_outSram_0_p").asInstanceOf[MemParams] ))
      val in_x422 = Flipped(Decoupled(Bool()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(3, 3, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(3, 3))
      val rr = Input(Bool())
    })
    def x421 = {io.in_x421} 
    def x293_outDram = {io.in_x293_outDram} 
    def x420 = {io.in_x420} 
    def x296_outSram_0 = {io.in_x296_outSram_0} ; io.in_x296_outSram_0 := DontCare
    def x422 = {io.in_x422} 
  }
  def connectWires0(module: x441_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x421 <> x421
    module.io.in_x293_outDram <> x293_outDram
    module.io.in_x420 <> x420
    x296_outSram_0.connectLedger(module.io.in_x296_outSram_0)
    module.io.in_x422 <> x422
  }
  val x422 = list_x422(0)
  val x293_outDram = list_x293_outDram(0)
  val x296_outSram_0 = list_x296_outSram_0(0)
  val x420 = list_x420(0)
  val x421 = list_x421(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x441_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x441_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x441_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x427_inr_UnitPipe_DenseTransferCommands = new x427_inr_UnitPipe_DenseTransferCommands_kernel(List(x293_outDram), List(x420) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, rr)
      x427_inr_UnitPipe_DenseTransferCommands.sm.io.ctrDone := risingEdge(x427_inr_UnitPipe_DenseTransferCommands.sm.io.ctrInc)
      x427_inr_UnitPipe_DenseTransferCommands.backpressure := x420.ready | x427_inr_UnitPipe_DenseTransferCommands.sm.io.doneLatch
      x427_inr_UnitPipe_DenseTransferCommands.forwardpressure := (true.B) && (true.B) | x427_inr_UnitPipe_DenseTransferCommands.sm.io.doneLatch
      x427_inr_UnitPipe_DenseTransferCommands.sm.io.enableOut.zip(x427_inr_UnitPipe_DenseTransferCommands.smEnableOuts).foreach{case (l,r) => r := l}
      x427_inr_UnitPipe_DenseTransferCommands.sm.io.break := false.B
      x427_inr_UnitPipe_DenseTransferCommands.mask := true.B & true.B
      x427_inr_UnitPipe_DenseTransferCommands.configure("x427_inr_UnitPipe_DenseTransferCommands", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x427_inr_UnitPipe_DenseTransferCommands.kernel()
      val x428_ctr = new CtrObject(Left(Some(0)), Left(Some(80)), Left(Some(1)), 1, 9, false)
      val x429_ctrchain = (new CChainObject(List[CtrObject](x428_ctr), "x429_ctrchain")).cchain.io 
      x429_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x429_ctrchain_p", (x429_ctrchain.par, x429_ctrchain.widths))
      val x436_inr_Foreach = new x436_inr_Foreach_kernel(List(x296_outSram_0), List(x421) ,  Some(me), List(x429_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, rr)
      x436_inr_Foreach.sm.io.ctrDone := (x436_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x436_inr_Foreach.backpressure := x421.ready | x436_inr_Foreach.sm.io.doneLatch
      x436_inr_Foreach.forwardpressure := (true.B) && (true.B) | x436_inr_Foreach.sm.io.doneLatch
      x436_inr_Foreach.sm.io.enableOut.zip(x436_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x436_inr_Foreach.sm.io.break := false.B
      x436_inr_Foreach.mask := ~x436_inr_Foreach.cchain.head.output.noop & true.B
      x436_inr_Foreach.configure("x436_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x436_inr_Foreach.kernel()
      val x440_inr_UnitPipe_DenseTransferAck = new x440_inr_UnitPipe_DenseTransferAck_kernel(List(x422) ,  Some(me), List(), 2, 1, 1, List(1), List(32), breakpoints, rr)
      x440_inr_UnitPipe_DenseTransferAck.sm.io.ctrDone := risingEdge(x440_inr_UnitPipe_DenseTransferAck.sm.io.ctrInc)
      x440_inr_UnitPipe_DenseTransferAck.backpressure := true.B | x440_inr_UnitPipe_DenseTransferAck.sm.io.doneLatch
      x440_inr_UnitPipe_DenseTransferAck.forwardpressure := (x422.valid) && (true.B) | x440_inr_UnitPipe_DenseTransferAck.sm.io.doneLatch
      x440_inr_UnitPipe_DenseTransferAck.sm.io.enableOut.zip(x440_inr_UnitPipe_DenseTransferAck.smEnableOuts).foreach{case (l,r) => r := l}
      x440_inr_UnitPipe_DenseTransferAck.sm.io.break := false.B
      x440_inr_UnitPipe_DenseTransferAck.mask := true.B & true.B
      x440_inr_UnitPipe_DenseTransferAck.configure("x440_inr_UnitPipe_DenseTransferAck", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x440_inr_UnitPipe_DenseTransferAck.kernel()
    }
    val module = Module(new x441_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x441_outr_UnitPipe_DenseTransfer **/
