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

/** Hierarchy: x491 **/
/** BEGIN None RootController **/
class RootController_kernel(
  list_x446: List[DecoupledIO[AppStoreData]],
  list_x329_inDram: List[FixedPoint],
  list_x447: List[DecoupledIO[Bool]],
  list_x335_Accel_n: List[UInt],
  list_x384: List[DecoupledIO[AppCommandDense]],
  list_x385: List[DecoupledIO[AppLoadData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 3, isFSM = false   , latency = 0.0.toInt, myName = "RootController_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "RootController_iiCtr"))
  
  abstract class RootController_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x385 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x385_p").asInstanceOf[(Int, Int)] )))
      val in_x384 = Decoupled(new AppCommandDense(ModuleParams.getParams("x384_p").asInstanceOf[(Int,Int)] ))
      val in_x344 = Decoupled(new AppCommandDense(ModuleParams.getParams("x344_p").asInstanceOf[(Int,Int)] ))
      val in_x329_inDram = Input(new FixedPoint(true, 64, 0))
      val in_x402 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x402_p").asInstanceOf[(Int, Int)] )))
      val in_x445 = Decoupled(new AppCommandDense(ModuleParams.getParams("x445_p").asInstanceOf[(Int,Int)] ))
      val in_x335_Accel_n = Input(UInt(64.W))
      val in_x330_outDram = Input(new FixedPoint(true, 64, 0))
      val in_x331_constDram = Input(new FixedPoint(true, 64, 0))
      val in_x446 = Decoupled(new AppStoreData(ModuleParams.getParams("x446_p").asInstanceOf[(Int,Int)] ))
      val in_x401 = Decoupled(new AppCommandDense(ModuleParams.getParams("x401_p").asInstanceOf[(Int,Int)] ))
      val in_x346 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x346_p").asInstanceOf[(Int, Int)] )))
      val in_x447 = Flipped(Decoupled(Bool()))
      val in_x326_argIn = Input(UInt(64.W))
      val in_x332_twoNegDram = Input(new FixedPoint(true, 64, 0))
      val in_x337_argIn = Input(UInt(64.W))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(3, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(3, 1))
      val rr = Input(Bool())
    })
    def x385 = {io.in_x385} 
    def x384 = {io.in_x384} 
    def x344 = {io.in_x344} 
    def x329_inDram = {io.in_x329_inDram} 
    def x402 = {io.in_x402} 
    def x445 = {io.in_x445} 
    def x335_Accel_n = {io.in_x335_Accel_n} 
    def x330_outDram = {io.in_x330_outDram} 
    def x331_constDram = {io.in_x331_constDram} 
    def x446 = {io.in_x446} 
    def x401 = {io.in_x401} 
    def x346 = {io.in_x346} 
    def x447 = {io.in_x447} 
    def x326_argIn = {io.in_x326_argIn} 
    def x332_twoNegDram = {io.in_x332_twoNegDram} 
    def x337_argIn = {io.in_x337_argIn} 
  }
  def connectWires0(module: RootController_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x385 <> x385
    module.io.in_x384 <> x384
    module.io.in_x344 <> x344
    module.io.in_x329_inDram <> x329_inDram
    module.io.in_x402 <> x402
    module.io.in_x445 <> x445
    module.io.in_x335_Accel_n <> x335_Accel_n
    module.io.in_x330_outDram <> x330_outDram
    module.io.in_x331_constDram <> x331_constDram
    module.io.in_x446 <> x446
    module.io.in_x401 <> x401
    module.io.in_x346 <> x346
    module.io.in_x447 <> x447
    module.io.in_x326_argIn <> x326_argIn
    module.io.in_x332_twoNegDram <> x332_twoNegDram
    module.io.in_x337_argIn <> x337_argIn
  }
  val x446 = list_x446(0)
  val x329_inDram = list_x329_inDram(0)
  val x330_outDram = list_x329_inDram(1)
  val x331_constDram = list_x329_inDram(2)
  val x332_twoNegDram = list_x329_inDram(3)
  val x447 = list_x447(0)
  val x335_Accel_n = list_x335_Accel_n(0)
  val x326_argIn = list_x335_Accel_n(1)
  val x337_argIn = list_x335_Accel_n(2)
  val x384 = list_x384(0)
  val x344 = list_x384(1)
  val x445 = list_x384(2)
  val x401 = list_x384(3)
  val x385 = list_x385(0)
  val x402 = list_x385(1)
  val x346 = list_x385(2)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "RootController")
    implicit val stack = ControllerStack.stack.toList
    class RootController_concrete(depth: Int)(implicit stack: List[KernelHash]) extends RootController_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x341_buf_0 = (new x341_buf_0).m.io.asInstanceOf[StandardInterface]
      val x342_Ktable_0 = (new x342_Ktable_0).m.io.asInstanceOf[StandardInterface]
      val x343_twoNegSram_0 = (new x343_twoNegSram_0).m.io.asInstanceOf[StandardInterface]
      val x489 = new x489_kernel(List(x384,x344,x401), List(x329_inDram,x331_constDram,x332_twoNegDram), List(x326_argIn), List(x343_twoNegSram_0,x342_Ktable_0,x341_buf_0), List(x385,x402,x346) ,  Some(me), List(), 0, 3, 1, List(1), List(32), breakpoints, rr)
      x489.sm.io.ctrDone := risingEdge(x489.sm.io.ctrInc)
      x489.backpressure := true.B | x489.sm.io.doneLatch
      x489.forwardpressure := (true.B) && (true.B) | x489.sm.io.doneLatch
      x489.sm.io.enableOut.zip(x489.smEnableOuts).foreach{case (l,r) => r := l}
      x489.sm.io.break := false.B
      x489.mask := true.B & true.B
      x489.configure("x489", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x489.kernel()
      val x418_maxReg_0 = (new x418_maxReg_0).m.io.asInstanceOf[StandardInterface]
      val x484_rd_x335 = Wire(new FixedPoint(true, 32, 0))
      x484_rd_x335.r := x335_Accel_n.r
      val x419_ctr = new CtrObject(Left(Some(0)), Right(x484_rd_x335), Left(Some(1)), 1, 32, false)
      val x420_ctrchain = (new CChainObject(List[CtrObject](x419_ctr), "x420_ctrchain")).cchain.io 
      x420_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x420_ctrchain_p", (x420_ctrchain.par, x420_ctrchain.widths))
      val x431_inr_Reduce = new x431_inr_Reduce_kernel(List(x418_maxReg_0,x341_buf_0) ,  Some(me), List(x420_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, rr)
      x431_inr_Reduce.sm.io.ctrDone := (x431_inr_Reduce.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x431_inr_Reduce.backpressure := true.B | x431_inr_Reduce.sm.io.doneLatch
      x431_inr_Reduce.forwardpressure := (true.B) && (true.B) | x431_inr_Reduce.sm.io.doneLatch
      x431_inr_Reduce.sm.io.enableOut.zip(x431_inr_Reduce.smEnableOuts).foreach{case (l,r) => r := l}
      x431_inr_Reduce.sm.io.break := false.B
      x431_inr_Reduce.mask := ~x431_inr_Reduce.cchain.head.output.noop & true.B
      x431_inr_Reduce.configure("x431_inr_Reduce", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x431_inr_Reduce.kernel()
      val x485_rd_x335 = Wire(new FixedPoint(true, 32, 0))
      x485_rd_x335.r := x335_Accel_n.r
      val x432_ctr = new CtrObject(Left(Some(0)), Right(x485_rd_x335), Left(Some(1)), 1, 32, false)
      val x433_ctrchain = (new CChainObject(List[CtrObject](x432_ctr), "x433_ctrchain")).cchain.io 
      x433_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x433_ctrchain_p", (x433_ctrchain.par, x433_ctrchain.widths))
      val x444_outSram_0 = (new x444_outSram_0).m.io.asInstanceOf[StandardInterface]
      val x490 = new x490_kernel(List(x446), List(x330_outDram), List(x445), List(x433_ctrchain), List(x444_outSram_0,x418_maxReg_0,x341_buf_0), List(x447), List(x326_argIn,x337_argIn) ,  Some(me), List(), 2, 2, 1, List(1), List(32), breakpoints, rr)
      x490.sm.io.ctrDone := risingEdge(x490.sm.io.ctrInc)
      x490.backpressure := true.B | x490.sm.io.doneLatch
      x490.forwardpressure := (true.B) && (true.B) | x490.sm.io.doneLatch
      x490.sm.io.enableOut.zip(x490.smEnableOuts).foreach{case (l,r) => r := l}
      x490.sm.io.break := false.B
      x490.mask := true.B & true.B
      x490.configure("x490", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x490.kernel()
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
