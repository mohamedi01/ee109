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

/** Hierarchy: x196 **/
/** BEGIN None RootController **/
class RootController_kernel(
  list_x267: List[DecoupledIO[Bool]],
  list_x265: List[DecoupledIO[AppCommandDense]],
  list_x266: List[DecoupledIO[AppStoreData]],
  list_x217: List[DecoupledIO[AppLoadData]],
  list_x211_Accel_n: List[UInt],
  list_x209_inDram: List[FixedPoint],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 3, isFSM = false   , latency = 0.0.toInt, myName = "RootController_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "RootController_iiCtr"))
  
  abstract class RootController_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x217 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x217_p").asInstanceOf[(Int, Int)] )))
      val in_x211_Accel_n = Input(UInt(64.W))
      val in_x265 = Decoupled(new AppCommandDense(ModuleParams.getParams("x265_p").asInstanceOf[(Int,Int)] ))
      val in_x206_argIn = Input(UInt(64.W))
      val in_x266 = Decoupled(new AppStoreData(ModuleParams.getParams("x266_p").asInstanceOf[(Int,Int)] ))
      val in_x267 = Flipped(Decoupled(Bool()))
      val in_x209_inDram = Input(new FixedPoint(true, 64, 0))
      val in_x210_outDram = Input(new FixedPoint(true, 64, 0))
      val in_x215 = Decoupled(new AppCommandDense(ModuleParams.getParams("x215_p").asInstanceOf[(Int,Int)] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(3, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(3, 1))
      val rr = Input(Bool())
    })
    def x217 = {io.in_x217} 
    def x211_Accel_n = {io.in_x211_Accel_n} 
    def x265 = {io.in_x265} 
    def x206_argIn = {io.in_x206_argIn} 
    def x266 = {io.in_x266} 
    def x267 = {io.in_x267} 
    def x209_inDram = {io.in_x209_inDram} 
    def x210_outDram = {io.in_x210_outDram} 
    def x215 = {io.in_x215} 
  }
  def connectWires0(module: RootController_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x217 <> x217
    module.io.in_x211_Accel_n <> x211_Accel_n
    module.io.in_x265 <> x265
    module.io.in_x206_argIn <> x206_argIn
    module.io.in_x266 <> x266
    module.io.in_x267 <> x267
    module.io.in_x209_inDram <> x209_inDram
    module.io.in_x210_outDram <> x210_outDram
    module.io.in_x215 <> x215
  }
  val x267 = list_x267(0)
  val x265 = list_x265(0)
  val x215 = list_x265(1)
  val x266 = list_x266(0)
  val x217 = list_x217(0)
  val x211_Accel_n = list_x211_Accel_n(0)
  val x206_argIn = list_x211_Accel_n(1)
  val x209_inDram = list_x209_inDram(0)
  val x210_outDram = list_x209_inDram(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "RootController")
    implicit val stack = ControllerStack.stack.toList
    class RootController_concrete(depth: Int)(implicit stack: List[KernelHash]) extends RootController_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x214_buf_0 = (new x214_buf_0).m.io.asInstanceOf[StandardInterface]
      val x254_outr_UnitPipe_DenseTransfer = new x254_outr_UnitPipe_DenseTransfer_kernel(List(x217), List(x209_inDram), List(x215), List(x206_argIn), List(x214_buf_0) ,  Some(me), List(), 0, 2, 2, List(1), List(32), breakpoints, rr)
      x254_outr_UnitPipe_DenseTransfer.backpressure := true.B | x254_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x254_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x254_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x254_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x254_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x254_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x254_outr_UnitPipe_DenseTransfer.mask := true.B & true.B
      x254_outr_UnitPipe_DenseTransfer.configure("x254_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x254_outr_UnitPipe_DenseTransfer.kernel()
      val x304_rd_x211 = Wire(new FixedPoint(true, 32, 0))
      x304_rd_x211.r := x211_Accel_n.r
      val x255_ctr = new CtrObject(Left(Some(0)), Right(x304_rd_x211), Left(Some(1)), 1, 32, false)
      val x256_ctrchain = (new CChainObject(List[CtrObject](x255_ctr), "x256_ctrchain")).cchain.io 
      x256_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x256_ctrchain_p", (x256_ctrchain.par, x256_ctrchain.widths))
      val x264_inr_Foreach = new x264_inr_Foreach_kernel(List(x214_buf_0) ,  Some(me), List(x256_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, rr)
      x264_inr_Foreach.sm.io.ctrDone := (x264_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x264_inr_Foreach.backpressure := true.B | x264_inr_Foreach.sm.io.doneLatch
      x264_inr_Foreach.forwardpressure := (true.B) && (true.B) | x264_inr_Foreach.sm.io.doneLatch
      x264_inr_Foreach.sm.io.enableOut.zip(x264_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x264_inr_Foreach.sm.io.break := false.B
      x264_inr_Foreach.mask := ~x264_inr_Foreach.cchain.head.output.noop & true.B
      x264_inr_Foreach.configure("x264_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x264_inr_Foreach.kernel()
      val x302_outr_UnitPipe_DenseTransfer = new x302_outr_UnitPipe_DenseTransfer_kernel(List(x267), List(x266), List(x210_outDram), List(x206_argIn), List(x214_buf_0), List(x265) ,  Some(me), List(), 2, 1, 1, List(1), List(32), breakpoints, rr)
      x302_outr_UnitPipe_DenseTransfer.backpressure := true.B | x302_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x302_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x302_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x302_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x302_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x302_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x302_outr_UnitPipe_DenseTransfer.mask := true.B & true.B
      x302_outr_UnitPipe_DenseTransfer.configure("x302_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x302_outr_UnitPipe_DenseTransfer.kernel()
    }
    val module = Module(new RootController_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END AccelScope RootController **/
