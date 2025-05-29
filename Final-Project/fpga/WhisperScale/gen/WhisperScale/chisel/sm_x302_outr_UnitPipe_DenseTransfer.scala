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

/** Hierarchy: x302 -> x196 **/
/** BEGIN Some(DenseTransfer) x302_outr_UnitPipe_DenseTransfer **/
class x302_outr_UnitPipe_DenseTransfer_kernel(
  list_x267: List[DecoupledIO[Bool]],
  list_x266: List[DecoupledIO[AppStoreData]],
  list_x210_outDram: List[FixedPoint],
  list_x206_argIn: List[UInt],
  list_x214_buf_0: List[StandardInterface],
  list_x265: List[DecoupledIO[AppCommandDense]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 1, isFSM = false   , latency = 0.0.toInt, myName = "x302_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x302_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x302_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x265 = Decoupled(new AppCommandDense(ModuleParams.getParams("x265_p").asInstanceOf[(Int,Int)] ))
      val in_x206_argIn = Input(UInt(64.W))
      val in_x266 = Decoupled(new AppStoreData(ModuleParams.getParams("x266_p").asInstanceOf[(Int,Int)] ))
      val in_x267 = Flipped(Decoupled(Bool()))
      val in_x214_buf_0 = Flipped(new StandardInterface(ModuleParams.getParams("x214_buf_0_p").asInstanceOf[MemParams] ))
      val in_x210_outDram = Input(new FixedPoint(true, 64, 0))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x265 = {io.in_x265} 
    def x206_argIn = {io.in_x206_argIn} 
    def x266 = {io.in_x266} 
    def x267 = {io.in_x267} 
    def x214_buf_0 = {io.in_x214_buf_0} ; io.in_x214_buf_0 := DontCare
    def x210_outDram = {io.in_x210_outDram} 
  }
  def connectWires0(module: x302_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x265 <> x265
    module.io.in_x206_argIn <> x206_argIn
    module.io.in_x266 <> x266
    module.io.in_x267 <> x267
    x214_buf_0.connectLedger(module.io.in_x214_buf_0)
    module.io.in_x210_outDram <> x210_outDram
  }
  val x267 = list_x267(0)
  val x266 = list_x266(0)
  val x210_outDram = list_x210_outDram(0)
  val x206_argIn = list_x206_argIn(0)
  val x214_buf_0 = list_x214_buf_0(0)
  val x265 = list_x265(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x302_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x302_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x302_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x301_outr_UnitPipe = new x301_outr_UnitPipe_kernel(List(x267), List(x266), List(x210_outDram), List(x206_argIn), List(x214_buf_0), List(x265) ,  Some(me), List(), 0, 2, 1, List(1), List(32), breakpoints, rr)
      x301_outr_UnitPipe.sm.io.ctrDone := risingEdge(x301_outr_UnitPipe.sm.io.ctrInc)
      x301_outr_UnitPipe.backpressure := true.B | x301_outr_UnitPipe.sm.io.doneLatch
      x301_outr_UnitPipe.forwardpressure := (true.B) && (true.B) | x301_outr_UnitPipe.sm.io.doneLatch
      x301_outr_UnitPipe.sm.io.enableOut.zip(x301_outr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x301_outr_UnitPipe.sm.io.break := false.B
      x301_outr_UnitPipe.mask := true.B & true.B
      x301_outr_UnitPipe.configure("x301_outr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x301_outr_UnitPipe.kernel()
    }
    val module = Module(new x302_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x302_outr_UnitPipe_DenseTransfer **/
