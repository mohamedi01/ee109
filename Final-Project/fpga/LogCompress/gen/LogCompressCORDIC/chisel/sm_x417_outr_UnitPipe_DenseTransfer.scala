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

/** Hierarchy: x417 -> x489 -> x491 **/
/** BEGIN Some(DenseTransfer) x417_outr_UnitPipe_DenseTransfer **/
class x417_outr_UnitPipe_DenseTransfer_kernel(
  list_x332_twoNegDram: List[FixedPoint],
  list_x401: List[DecoupledIO[AppCommandDense]],
  list_x402: List[DecoupledIO[AppLoadData]],
  list_x343_twoNegSram_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 2, isFSM = false   , latency = 0.0.toInt, myName = "x417_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x417_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x417_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x343_twoNegSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x343_twoNegSram_0_p").asInstanceOf[MemParams] ))
      val in_x332_twoNegDram = Input(new FixedPoint(true, 64, 0))
      val in_x402 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x402_p").asInstanceOf[(Int, Int)] )))
      val in_x401 = Decoupled(new AppCommandDense(ModuleParams.getParams("x401_p").asInstanceOf[(Int,Int)] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 2, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 2))
      val rr = Input(Bool())
    })
    def x343_twoNegSram_0 = {io.in_x343_twoNegSram_0} ; io.in_x343_twoNegSram_0 := DontCare
    def x332_twoNegDram = {io.in_x332_twoNegDram} 
    def x402 = {io.in_x402} 
    def x401 = {io.in_x401} 
  }
  def connectWires0(module: x417_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    x343_twoNegSram_0.connectLedger(module.io.in_x343_twoNegSram_0)
    module.io.in_x332_twoNegDram <> x332_twoNegDram
    module.io.in_x402 <> x402
    module.io.in_x401 <> x401
  }
  val x332_twoNegDram = list_x332_twoNegDram(0)
  val x401 = list_x401(0)
  val x402 = list_x402(0)
  val x343_twoNegSram_0 = list_x343_twoNegSram_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x417_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x417_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x417_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x407_inr_UnitPipe_AlignedLoadCommand = new x407_inr_UnitPipe_AlignedLoadCommand_kernel(List(x332_twoNegDram), List(x401) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, rr)
      x407_inr_UnitPipe_AlignedLoadCommand.sm.io.ctrDone := risingEdge(x407_inr_UnitPipe_AlignedLoadCommand.sm.io.ctrInc)
      x407_inr_UnitPipe_AlignedLoadCommand.backpressure := x401.ready | x407_inr_UnitPipe_AlignedLoadCommand.sm.io.doneLatch
      x407_inr_UnitPipe_AlignedLoadCommand.forwardpressure := (true.B) && (true.B) | x407_inr_UnitPipe_AlignedLoadCommand.sm.io.doneLatch
      x407_inr_UnitPipe_AlignedLoadCommand.sm.io.enableOut.zip(x407_inr_UnitPipe_AlignedLoadCommand.smEnableOuts).foreach{case (l,r) => r := l}
      x407_inr_UnitPipe_AlignedLoadCommand.sm.io.break := false.B
      x407_inr_UnitPipe_AlignedLoadCommand.mask := true.B & true.B
      x407_inr_UnitPipe_AlignedLoadCommand.configure("x407_inr_UnitPipe_AlignedLoadCommand", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x407_inr_UnitPipe_AlignedLoadCommand.kernel()
      val x409_ctr = new CtrObject(Left(Some(0)), Left(Some(16)), Left(Some(1)), 1, 7, false)
      val x410_ctrchain = (new CChainObject(List[CtrObject](x409_ctr), "x410_ctrchain")).cchain.io 
      x410_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x410_ctrchain_p", (x410_ctrchain.par, x410_ctrchain.widths))
      val x416_inr_Foreach_AlignedLoadWrite = new x416_inr_Foreach_AlignedLoadWrite_kernel(List(x402), List(x343_twoNegSram_0) ,  Some(me), List(x410_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, rr)
      x416_inr_Foreach_AlignedLoadWrite.sm.io.ctrDone := (x416_inr_Foreach_AlignedLoadWrite.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x416_inr_Foreach_AlignedLoadWrite.backpressure := true.B | x416_inr_Foreach_AlignedLoadWrite.sm.io.doneLatch
      x416_inr_Foreach_AlignedLoadWrite.forwardpressure := (x402.valid) && (true.B) | x416_inr_Foreach_AlignedLoadWrite.sm.io.doneLatch
      x416_inr_Foreach_AlignedLoadWrite.sm.io.enableOut.zip(x416_inr_Foreach_AlignedLoadWrite.smEnableOuts).foreach{case (l,r) => r := l}
      x416_inr_Foreach_AlignedLoadWrite.sm.io.break := false.B
      x416_inr_Foreach_AlignedLoadWrite.mask := ~x416_inr_Foreach_AlignedLoadWrite.cchain.head.output.noop & true.B
      x416_inr_Foreach_AlignedLoadWrite.configure("x416_inr_Foreach_AlignedLoadWrite", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x416_inr_Foreach_AlignedLoadWrite.kernel()
    }
    val module = Module(new x417_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x417_outr_UnitPipe_DenseTransfer **/
