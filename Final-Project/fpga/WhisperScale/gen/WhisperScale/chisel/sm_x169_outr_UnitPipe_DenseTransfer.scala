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

/** Hierarchy: x169 -> x122 **/
/** BEGIN Some(DenseTransfer) x169_outr_UnitPipe_DenseTransfer **/
class x169_outr_UnitPipe_DenseTransfer_kernel(
  list_x147_inDram: List[FixedPoint],
  list_x153: List[DecoupledIO[AppCommandDense]],
  list_x154: List[DecoupledIO[AppLoadData]],
  list_x152_buf_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 2, isFSM = false   , latency = 0.0.toInt, myName = "x169_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x169_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x169_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x152_buf_0 = Flipped(new StandardInterface(ModuleParams.getParams("x152_buf_0_p").asInstanceOf[MemParams] ))
      val in_x147_inDram = Input(new FixedPoint(true, 64, 0))
      val in_x153 = Decoupled(new AppCommandDense(ModuleParams.getParams("x153_p").asInstanceOf[(Int,Int)] ))
      val in_x154 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x154_p").asInstanceOf[(Int, Int)] )))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 2, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 2))
      val rr = Input(Bool())
    })
    def x152_buf_0 = {io.in_x152_buf_0} ; io.in_x152_buf_0 := DontCare
    def x147_inDram = {io.in_x147_inDram} 
    def x153 = {io.in_x153} 
    def x154 = {io.in_x154} 
  }
  def connectWires0(module: x169_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    x152_buf_0.connectLedger(module.io.in_x152_buf_0)
    module.io.in_x147_inDram <> x147_inDram
    module.io.in_x153 <> x153
    module.io.in_x154 <> x154
  }
  val x147_inDram = list_x147_inDram(0)
  val x153 = list_x153(0)
  val x154 = list_x154(0)
  val x152_buf_0 = list_x152_buf_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x169_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x169_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x169_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x159_inr_UnitPipe_AlignedLoadCommand = new x159_inr_UnitPipe_AlignedLoadCommand_kernel(List(x147_inDram), List(x153) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, rr)
      x159_inr_UnitPipe_AlignedLoadCommand.sm.io.ctrDone := risingEdge(x159_inr_UnitPipe_AlignedLoadCommand.sm.io.ctrInc)
      x159_inr_UnitPipe_AlignedLoadCommand.backpressure := x153.ready | x159_inr_UnitPipe_AlignedLoadCommand.sm.io.doneLatch
      x159_inr_UnitPipe_AlignedLoadCommand.forwardpressure := (true.B) && (true.B) | x159_inr_UnitPipe_AlignedLoadCommand.sm.io.doneLatch
      x159_inr_UnitPipe_AlignedLoadCommand.sm.io.enableOut.zip(x159_inr_UnitPipe_AlignedLoadCommand.smEnableOuts).foreach{case (l,r) => r := l}
      x159_inr_UnitPipe_AlignedLoadCommand.sm.io.break := false.B
      x159_inr_UnitPipe_AlignedLoadCommand.mask := true.B & true.B
      x159_inr_UnitPipe_AlignedLoadCommand.configure("x159_inr_UnitPipe_AlignedLoadCommand", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x159_inr_UnitPipe_AlignedLoadCommand.kernel()
      val x161_ctr = new CtrObject(Left(Some(0)), Left(Some(152960)), Left(Some(1)), 1, 20, false)
      val x162_ctrchain = (new CChainObject(List[CtrObject](x161_ctr), "x162_ctrchain")).cchain.io 
      x162_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x162_ctrchain_p", (x162_ctrchain.par, x162_ctrchain.widths))
      val x168_inr_Foreach_AlignedLoadWrite = new x168_inr_Foreach_AlignedLoadWrite_kernel(List(x154), List(x152_buf_0) ,  Some(me), List(x162_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, rr)
      x168_inr_Foreach_AlignedLoadWrite.sm.io.ctrDone := (x168_inr_Foreach_AlignedLoadWrite.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x168_inr_Foreach_AlignedLoadWrite.backpressure := true.B | x168_inr_Foreach_AlignedLoadWrite.sm.io.doneLatch
      x168_inr_Foreach_AlignedLoadWrite.forwardpressure := (x154.valid) && (true.B) | x168_inr_Foreach_AlignedLoadWrite.sm.io.doneLatch
      x168_inr_Foreach_AlignedLoadWrite.sm.io.enableOut.zip(x168_inr_Foreach_AlignedLoadWrite.smEnableOuts).foreach{case (l,r) => r := l}
      x168_inr_Foreach_AlignedLoadWrite.sm.io.break := false.B
      x168_inr_Foreach_AlignedLoadWrite.mask := ~x168_inr_Foreach_AlignedLoadWrite.cchain.head.output.noop & true.B
      x168_inr_Foreach_AlignedLoadWrite.configure("x168_inr_Foreach_AlignedLoadWrite", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x168_inr_Foreach_AlignedLoadWrite.kernel()
    }
    val module = Module(new x169_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x169_outr_UnitPipe_DenseTransfer **/
