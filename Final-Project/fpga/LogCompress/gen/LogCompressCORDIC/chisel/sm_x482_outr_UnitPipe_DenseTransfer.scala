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

/** Hierarchy: x482 -> x490 -> x491 **/
/** BEGIN Some(DenseTransfer) x482_outr_UnitPipe_DenseTransfer **/
class x482_outr_UnitPipe_DenseTransfer_kernel(
  list_x446: List[DecoupledIO[AppStoreData]],
  list_x330_outDram: List[FixedPoint],
  list_x445: List[DecoupledIO[AppCommandDense]],
  list_x444_outSram_0: List[StandardInterface],
  list_x447: List[DecoupledIO[Bool]],
  list_x326_argIn: List[UInt],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 1, isFSM = false   , latency = 0.0.toInt, myName = "x482_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x482_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x482_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x444_outSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x444_outSram_0_p").asInstanceOf[MemParams] ))
      val in_x445 = Decoupled(new AppCommandDense(ModuleParams.getParams("x445_p").asInstanceOf[(Int,Int)] ))
      val in_x330_outDram = Input(new FixedPoint(true, 64, 0))
      val in_x446 = Decoupled(new AppStoreData(ModuleParams.getParams("x446_p").asInstanceOf[(Int,Int)] ))
      val in_x447 = Flipped(Decoupled(Bool()))
      val in_x326_argIn = Input(UInt(64.W))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x444_outSram_0 = {io.in_x444_outSram_0} ; io.in_x444_outSram_0 := DontCare
    def x445 = {io.in_x445} 
    def x330_outDram = {io.in_x330_outDram} 
    def x446 = {io.in_x446} 
    def x447 = {io.in_x447} 
    def x326_argIn = {io.in_x326_argIn} 
  }
  def connectWires0(module: x482_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    x444_outSram_0.connectLedger(module.io.in_x444_outSram_0)
    module.io.in_x445 <> x445
    module.io.in_x330_outDram <> x330_outDram
    module.io.in_x446 <> x446
    module.io.in_x447 <> x447
    module.io.in_x326_argIn <> x326_argIn
  }
  val x446 = list_x446(0)
  val x330_outDram = list_x330_outDram(0)
  val x445 = list_x445(0)
  val x444_outSram_0 = list_x444_outSram_0(0)
  val x447 = list_x447(0)
  val x326_argIn = list_x326_argIn(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x482_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x482_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x482_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x481_outr_UnitPipe = new x481_outr_UnitPipe_kernel(List(x446), List(x330_outDram), List(x445), List(x444_outSram_0), List(x447), List(x326_argIn) ,  Some(me), List(), 0, 2, 1, List(1), List(32), breakpoints, rr)
      x481_outr_UnitPipe.sm.io.ctrDone := risingEdge(x481_outr_UnitPipe.sm.io.ctrInc)
      x481_outr_UnitPipe.backpressure := true.B | x481_outr_UnitPipe.sm.io.doneLatch
      x481_outr_UnitPipe.forwardpressure := (true.B) && (true.B) | x481_outr_UnitPipe.sm.io.doneLatch
      x481_outr_UnitPipe.sm.io.enableOut.zip(x481_outr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x481_outr_UnitPipe.sm.io.break := false.B
      x481_outr_UnitPipe.mask := true.B & true.B
      x481_outr_UnitPipe.configure("x481_outr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x481_outr_UnitPipe.kernel()
    }
    val module = Module(new x482_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x482_outr_UnitPipe_DenseTransfer **/
