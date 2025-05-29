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

/** Hierarchy: x201 -> x122 **/
/** BEGIN Some(DenseTransfer) x201_outr_UnitPipe_DenseTransfer **/
class x201_outr_UnitPipe_DenseTransfer_kernel(
  list_x181: List[DecoupledIO[AppStoreData]],
  list_x148_outDram: List[FixedPoint],
  list_x180: List[DecoupledIO[AppCommandDense]],
  list_x152_buf_0: List[StandardInterface],
  list_x182: List[DecoupledIO[Bool]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 3, isFSM = false   , latency = 0.0.toInt, myName = "x201_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x201_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x201_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x152_buf_0 = Flipped(new StandardInterface(ModuleParams.getParams("x152_buf_0_p").asInstanceOf[MemParams] ))
      val in_x148_outDram = Input(new FixedPoint(true, 64, 0))
      val in_x180 = Decoupled(new AppCommandDense(ModuleParams.getParams("x180_p").asInstanceOf[(Int,Int)] ))
      val in_x181 = Decoupled(new AppStoreData(ModuleParams.getParams("x181_p").asInstanceOf[(Int,Int)] ))
      val in_x182 = Flipped(Decoupled(Bool()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(3, 3, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(3, 3))
      val rr = Input(Bool())
    })
    def x152_buf_0 = {io.in_x152_buf_0} ; io.in_x152_buf_0 := DontCare
    def x148_outDram = {io.in_x148_outDram} 
    def x180 = {io.in_x180} 
    def x181 = {io.in_x181} 
    def x182 = {io.in_x182} 
  }
  def connectWires0(module: x201_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    x152_buf_0.connectLedger(module.io.in_x152_buf_0)
    module.io.in_x148_outDram <> x148_outDram
    module.io.in_x180 <> x180
    module.io.in_x181 <> x181
    module.io.in_x182 <> x182
  }
  val x181 = list_x181(0)
  val x148_outDram = list_x148_outDram(0)
  val x180 = list_x180(0)
  val x152_buf_0 = list_x152_buf_0(0)
  val x182 = list_x182(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x201_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x201_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x201_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x187_inr_UnitPipe_DenseTransferCommands = new x187_inr_UnitPipe_DenseTransferCommands_kernel(List(x148_outDram), List(x180) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, rr)
      x187_inr_UnitPipe_DenseTransferCommands.sm.io.ctrDone := risingEdge(x187_inr_UnitPipe_DenseTransferCommands.sm.io.ctrInc)
      x187_inr_UnitPipe_DenseTransferCommands.backpressure := x180.ready | x187_inr_UnitPipe_DenseTransferCommands.sm.io.doneLatch
      x187_inr_UnitPipe_DenseTransferCommands.forwardpressure := (true.B) && (true.B) | x187_inr_UnitPipe_DenseTransferCommands.sm.io.doneLatch
      x187_inr_UnitPipe_DenseTransferCommands.sm.io.enableOut.zip(x187_inr_UnitPipe_DenseTransferCommands.smEnableOuts).foreach{case (l,r) => r := l}
      x187_inr_UnitPipe_DenseTransferCommands.sm.io.break := false.B
      x187_inr_UnitPipe_DenseTransferCommands.mask := true.B & true.B
      x187_inr_UnitPipe_DenseTransferCommands.configure("x187_inr_UnitPipe_DenseTransferCommands", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x187_inr_UnitPipe_DenseTransferCommands.kernel()
      val x188_ctr = new CtrObject(Left(Some(0)), Left(Some(152960)), Left(Some(1)), 1, 20, false)
      val x189_ctrchain = (new CChainObject(List[CtrObject](x188_ctr), "x189_ctrchain")).cchain.io 
      x189_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x189_ctrchain_p", (x189_ctrchain.par, x189_ctrchain.widths))
      val x196_inr_Foreach = new x196_inr_Foreach_kernel(List(x152_buf_0), List(x181) ,  Some(me), List(x189_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, rr)
      x196_inr_Foreach.sm.io.ctrDone := (x196_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x196_inr_Foreach.backpressure := x181.ready | x196_inr_Foreach.sm.io.doneLatch
      x196_inr_Foreach.forwardpressure := (true.B) && (true.B) | x196_inr_Foreach.sm.io.doneLatch
      x196_inr_Foreach.sm.io.enableOut.zip(x196_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x196_inr_Foreach.sm.io.break := false.B
      x196_inr_Foreach.mask := ~x196_inr_Foreach.cchain.head.output.noop & true.B
      x196_inr_Foreach.configure("x196_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x196_inr_Foreach.kernel()
      val x200_inr_UnitPipe_DenseTransferAck = new x200_inr_UnitPipe_DenseTransferAck_kernel(List(x182) ,  Some(me), List(), 2, 1, 1, List(1), List(32), breakpoints, rr)
      x200_inr_UnitPipe_DenseTransferAck.sm.io.ctrDone := risingEdge(x200_inr_UnitPipe_DenseTransferAck.sm.io.ctrInc)
      x200_inr_UnitPipe_DenseTransferAck.backpressure := true.B | x200_inr_UnitPipe_DenseTransferAck.sm.io.doneLatch
      x200_inr_UnitPipe_DenseTransferAck.forwardpressure := (x182.valid) && (true.B) | x200_inr_UnitPipe_DenseTransferAck.sm.io.doneLatch
      x200_inr_UnitPipe_DenseTransferAck.sm.io.enableOut.zip(x200_inr_UnitPipe_DenseTransferAck.smEnableOuts).foreach{case (l,r) => r := l}
      x200_inr_UnitPipe_DenseTransferAck.sm.io.break := false.B
      x200_inr_UnitPipe_DenseTransferAck.mask := true.B & true.B
      x200_inr_UnitPipe_DenseTransferAck.configure("x200_inr_UnitPipe_DenseTransferAck", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x200_inr_UnitPipe_DenseTransferAck.kernel()
    }
    val module = Module(new x201_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x201_outr_UnitPipe_DenseTransfer **/
