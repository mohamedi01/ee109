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

/** Hierarchy: x490 -> x491 **/
/** BEGIN None x490 **/
class x490_kernel(
  list_x446: List[DecoupledIO[AppStoreData]],
  list_x330_outDram: List[FixedPoint],
  list_x445: List[DecoupledIO[AppCommandDense]],
  list_x433_ctrchain: List[CounterChainInterface],
  list_x444_outSram_0: List[StandardInterface],
  list_x447: List[DecoupledIO[Bool]],
  list_x326_argIn: List[UInt],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(ForkJoin, 2, isFSM = false   , latency = 0.0.toInt, myName = "x490_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x490_iiCtr"))
  
  abstract class x490_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x444_outSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x444_outSram_0_p").asInstanceOf[MemParams] ))
      val in_x445 = Decoupled(new AppCommandDense(ModuleParams.getParams("x445_p").asInstanceOf[(Int,Int)] ))
      val in_x330_outDram = Input(new FixedPoint(true, 64, 0))
      val in_x446 = Decoupled(new AppStoreData(ModuleParams.getParams("x446_p").asInstanceOf[(Int,Int)] ))
      val in_x418_maxReg_0 = Flipped(new StandardInterface(ModuleParams.getParams("x418_maxReg_0_p").asInstanceOf[MemParams] ))
      val in_x433_ctrchain = Flipped(new CounterChainInterface(ModuleParams.getParams("x433_ctrchain_p").asInstanceOf[(List[Int],List[Int])] ))
      val in_x447 = Flipped(Decoupled(Bool()))
      val in_x326_argIn = Input(UInt(64.W))
      val in_x341_buf_0 = Flipped(new StandardInterface(ModuleParams.getParams("x341_buf_0_p").asInstanceOf[MemParams] ))
      val in_x337_argIn = Input(UInt(64.W))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x444_outSram_0 = {io.in_x444_outSram_0} ; io.in_x444_outSram_0 := DontCare
    def x445 = {io.in_x445} 
    def x330_outDram = {io.in_x330_outDram} 
    def x446 = {io.in_x446} 
    def x418_maxReg_0 = {io.in_x418_maxReg_0} ; io.in_x418_maxReg_0 := DontCare
    def x433_ctrchain = {io.in_x433_ctrchain} ; io.in_x433_ctrchain := DontCare
    def x447 = {io.in_x447} 
    def x326_argIn = {io.in_x326_argIn} 
    def x341_buf_0 = {io.in_x341_buf_0} ; io.in_x341_buf_0 := DontCare
    def x337_argIn = {io.in_x337_argIn} 
  }
  def connectWires0(module: x490_module)(implicit stack: List[KernelHash]): Unit = {
    x444_outSram_0.connectLedger(module.io.in_x444_outSram_0)
    module.io.in_x445 <> x445
    module.io.in_x330_outDram <> x330_outDram
    module.io.in_x446 <> x446
    x418_maxReg_0.connectLedger(module.io.in_x418_maxReg_0)
    module.io.in_x433_ctrchain.input <> x433_ctrchain.input; module.io.in_x433_ctrchain.output <> x433_ctrchain.output
    module.io.in_x447 <> x447
    module.io.in_x326_argIn <> x326_argIn
    x341_buf_0.connectLedger(module.io.in_x341_buf_0)
    module.io.in_x337_argIn <> x337_argIn
  }
  val x446 = list_x446(0)
  val x330_outDram = list_x330_outDram(0)
  val x445 = list_x445(0)
  val x433_ctrchain = list_x433_ctrchain(0)
  val x444_outSram_0 = list_x444_outSram_0(0)
  val x418_maxReg_0 = list_x444_outSram_0(1)
  val x341_buf_0 = list_x444_outSram_0(2)
  val x447 = list_x447(0)
  val x326_argIn = list_x326_argIn(0)
  val x337_argIn = list_x326_argIn(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x490")
    implicit val stack = ControllerStack.stack.toList
    class x490_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x490_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x443_inr_Foreach = new x443_inr_Foreach_kernel(List(x418_maxReg_0,x341_buf_0), List(x337_argIn) ,  Some(me), List(x433_ctrchain), 0, 1, 1, List(1), List(32), breakpoints, rr)
      x443_inr_Foreach.sm.io.ctrDone := (x443_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x443_inr_Foreach.backpressure := true.B | x443_inr_Foreach.sm.io.doneLatch
      x443_inr_Foreach.forwardpressure := (true.B) && (true.B) | x443_inr_Foreach.sm.io.doneLatch
      x443_inr_Foreach.sm.io.enableOut.zip(x443_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x443_inr_Foreach.sm.io.break := false.B
      x443_inr_Foreach.mask := ~x443_inr_Foreach.cchain.head.output.noop & true.B
      x443_inr_Foreach.configure("x443_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x443_inr_Foreach.kernel()
      val x482_outr_UnitPipe_DenseTransfer = new x482_outr_UnitPipe_DenseTransfer_kernel(List(x446), List(x330_outDram), List(x445), List(x444_outSram_0), List(x447), List(x326_argIn) ,  Some(me), List(), 1, 1, 1, List(1), List(32), breakpoints, rr)
      x482_outr_UnitPipe_DenseTransfer.backpressure := true.B | x482_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x482_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x482_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x482_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x482_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x482_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x482_outr_UnitPipe_DenseTransfer.mask := true.B & true.B
      x482_outr_UnitPipe_DenseTransfer.configure("x482_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x482_outr_UnitPipe_DenseTransfer.kernel()
    }
    val module = Module(new x490_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END ParallelPipe x490 **/
