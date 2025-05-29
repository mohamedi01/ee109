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

/** Hierarchy: x400 -> x489 -> x491 **/
/** BEGIN Some(DenseTransfer) x400_outr_UnitPipe_DenseTransfer **/
class x400_outr_UnitPipe_DenseTransfer_kernel(
  list_x331_constDram: List[FixedPoint],
  list_x384: List[DecoupledIO[AppCommandDense]],
  list_x385: List[DecoupledIO[AppLoadData]],
  list_x342_Ktable_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 2, isFSM = false   , latency = 0.0.toInt, myName = "x400_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x400_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x400_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x342_Ktable_0 = Flipped(new StandardInterface(ModuleParams.getParams("x342_Ktable_0_p").asInstanceOf[MemParams] ))
      val in_x331_constDram = Input(new FixedPoint(true, 64, 0))
      val in_x385 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x385_p").asInstanceOf[(Int, Int)] )))
      val in_x384 = Decoupled(new AppCommandDense(ModuleParams.getParams("x384_p").asInstanceOf[(Int,Int)] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 2, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 2))
      val rr = Input(Bool())
    })
    def x342_Ktable_0 = {io.in_x342_Ktable_0} ; io.in_x342_Ktable_0 := DontCare
    def x331_constDram = {io.in_x331_constDram} 
    def x385 = {io.in_x385} 
    def x384 = {io.in_x384} 
  }
  def connectWires0(module: x400_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    x342_Ktable_0.connectLedger(module.io.in_x342_Ktable_0)
    module.io.in_x331_constDram <> x331_constDram
    module.io.in_x385 <> x385
    module.io.in_x384 <> x384
  }
  val x331_constDram = list_x331_constDram(0)
  val x384 = list_x384(0)
  val x385 = list_x385(0)
  val x342_Ktable_0 = list_x342_Ktable_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x400_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x400_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x400_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x390_inr_UnitPipe_AlignedLoadCommand = new x390_inr_UnitPipe_AlignedLoadCommand_kernel(List(x331_constDram), List(x384) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, rr)
      x390_inr_UnitPipe_AlignedLoadCommand.sm.io.ctrDone := risingEdge(x390_inr_UnitPipe_AlignedLoadCommand.sm.io.ctrInc)
      x390_inr_UnitPipe_AlignedLoadCommand.backpressure := x384.ready | x390_inr_UnitPipe_AlignedLoadCommand.sm.io.doneLatch
      x390_inr_UnitPipe_AlignedLoadCommand.forwardpressure := (true.B) && (true.B) | x390_inr_UnitPipe_AlignedLoadCommand.sm.io.doneLatch
      x390_inr_UnitPipe_AlignedLoadCommand.sm.io.enableOut.zip(x390_inr_UnitPipe_AlignedLoadCommand.smEnableOuts).foreach{case (l,r) => r := l}
      x390_inr_UnitPipe_AlignedLoadCommand.sm.io.break := false.B
      x390_inr_UnitPipe_AlignedLoadCommand.mask := true.B & true.B
      x390_inr_UnitPipe_AlignedLoadCommand.configure("x390_inr_UnitPipe_AlignedLoadCommand", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x390_inr_UnitPipe_AlignedLoadCommand.kernel()
      val x392_ctr = new CtrObject(Left(Some(0)), Left(Some(16)), Left(Some(1)), 1, 7, false)
      val x393_ctrchain = (new CChainObject(List[CtrObject](x392_ctr), "x393_ctrchain")).cchain.io 
      x393_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x393_ctrchain_p", (x393_ctrchain.par, x393_ctrchain.widths))
      val x399_inr_Foreach_AlignedLoadWrite = new x399_inr_Foreach_AlignedLoadWrite_kernel(List(x385), List(x342_Ktable_0) ,  Some(me), List(x393_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, rr)
      x399_inr_Foreach_AlignedLoadWrite.sm.io.ctrDone := (x399_inr_Foreach_AlignedLoadWrite.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x399_inr_Foreach_AlignedLoadWrite.backpressure := true.B | x399_inr_Foreach_AlignedLoadWrite.sm.io.doneLatch
      x399_inr_Foreach_AlignedLoadWrite.forwardpressure := (x385.valid) && (true.B) | x399_inr_Foreach_AlignedLoadWrite.sm.io.doneLatch
      x399_inr_Foreach_AlignedLoadWrite.sm.io.enableOut.zip(x399_inr_Foreach_AlignedLoadWrite.smEnableOuts).foreach{case (l,r) => r := l}
      x399_inr_Foreach_AlignedLoadWrite.sm.io.break := false.B
      x399_inr_Foreach_AlignedLoadWrite.mask := ~x399_inr_Foreach_AlignedLoadWrite.cchain.head.output.noop & true.B
      x399_inr_Foreach_AlignedLoadWrite.configure("x399_inr_Foreach_AlignedLoadWrite", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x399_inr_Foreach_AlignedLoadWrite.kernel()
    }
    val module = Module(new x400_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x400_outr_UnitPipe_DenseTransfer **/
