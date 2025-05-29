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

/** Hierarchy: x489 -> x491 **/
/** BEGIN None x489 **/
class x489_kernel(
  list_x384: List[DecoupledIO[AppCommandDense]],
  list_x329_inDram: List[FixedPoint],
  list_x326_argIn: List[UInt],
  list_x343_twoNegSram_0: List[StandardInterface],
  list_x385: List[DecoupledIO[AppLoadData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(ForkJoin, 3, isFSM = false   , latency = 0.0.toInt, myName = "x489_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x489_iiCtr"))
  
  abstract class x489_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x385 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x385_p").asInstanceOf[(Int, Int)] )))
      val in_x384 = Decoupled(new AppCommandDense(ModuleParams.getParams("x384_p").asInstanceOf[(Int,Int)] ))
      val in_x344 = Decoupled(new AppCommandDense(ModuleParams.getParams("x344_p").asInstanceOf[(Int,Int)] ))
      val in_x329_inDram = Input(new FixedPoint(true, 64, 0))
      val in_x343_twoNegSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x343_twoNegSram_0_p").asInstanceOf[MemParams] ))
      val in_x402 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x402_p").asInstanceOf[(Int, Int)] )))
      val in_x342_Ktable_0 = Flipped(new StandardInterface(ModuleParams.getParams("x342_Ktable_0_p").asInstanceOf[MemParams] ))
      val in_x331_constDram = Input(new FixedPoint(true, 64, 0))
      val in_x401 = Decoupled(new AppCommandDense(ModuleParams.getParams("x401_p").asInstanceOf[(Int,Int)] ))
      val in_x346 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x346_p").asInstanceOf[(Int, Int)] )))
      val in_x326_argIn = Input(UInt(64.W))
      val in_x341_buf_0 = Flipped(new StandardInterface(ModuleParams.getParams("x341_buf_0_p").asInstanceOf[MemParams] ))
      val in_x332_twoNegDram = Input(new FixedPoint(true, 64, 0))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(3, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(3, 1))
      val rr = Input(Bool())
    })
    def x385 = {io.in_x385} 
    def x384 = {io.in_x384} 
    def x344 = {io.in_x344} 
    def x329_inDram = {io.in_x329_inDram} 
    def x343_twoNegSram_0 = {io.in_x343_twoNegSram_0} ; io.in_x343_twoNegSram_0 := DontCare
    def x402 = {io.in_x402} 
    def x342_Ktable_0 = {io.in_x342_Ktable_0} ; io.in_x342_Ktable_0 := DontCare
    def x331_constDram = {io.in_x331_constDram} 
    def x401 = {io.in_x401} 
    def x346 = {io.in_x346} 
    def x326_argIn = {io.in_x326_argIn} 
    def x341_buf_0 = {io.in_x341_buf_0} ; io.in_x341_buf_0 := DontCare
    def x332_twoNegDram = {io.in_x332_twoNegDram} 
  }
  def connectWires0(module: x489_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x385 <> x385
    module.io.in_x384 <> x384
    module.io.in_x344 <> x344
    module.io.in_x329_inDram <> x329_inDram
    x343_twoNegSram_0.connectLedger(module.io.in_x343_twoNegSram_0)
    module.io.in_x402 <> x402
    x342_Ktable_0.connectLedger(module.io.in_x342_Ktable_0)
    module.io.in_x331_constDram <> x331_constDram
    module.io.in_x401 <> x401
    module.io.in_x346 <> x346
    module.io.in_x326_argIn <> x326_argIn
    x341_buf_0.connectLedger(module.io.in_x341_buf_0)
    module.io.in_x332_twoNegDram <> x332_twoNegDram
  }
  val x384 = list_x384(0)
  val x344 = list_x384(1)
  val x401 = list_x384(2)
  val x329_inDram = list_x329_inDram(0)
  val x331_constDram = list_x329_inDram(1)
  val x332_twoNegDram = list_x329_inDram(2)
  val x326_argIn = list_x326_argIn(0)
  val x343_twoNegSram_0 = list_x343_twoNegSram_0(0)
  val x342_Ktable_0 = list_x343_twoNegSram_0(1)
  val x341_buf_0 = list_x343_twoNegSram_0(2)
  val x385 = list_x385(0)
  val x402 = list_x385(1)
  val x346 = list_x385(2)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x489")
    implicit val stack = ControllerStack.stack.toList
    class x489_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x489_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x383_outr_UnitPipe_DenseTransfer = new x383_outr_UnitPipe_DenseTransfer_kernel(List(x329_inDram), List(x344), List(x341_buf_0), List(x326_argIn), List(x346) ,  Some(me), List(), 0, 2, 2, List(1), List(32), breakpoints, rr)
      x383_outr_UnitPipe_DenseTransfer.backpressure := true.B | x383_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x383_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x383_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x383_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x383_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x383_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x383_outr_UnitPipe_DenseTransfer.mask := true.B & true.B
      x383_outr_UnitPipe_DenseTransfer.configure("x383_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x383_outr_UnitPipe_DenseTransfer.kernel()
      val x400_outr_UnitPipe_DenseTransfer = new x400_outr_UnitPipe_DenseTransfer_kernel(List(x331_constDram), List(x384), List(x385), List(x342_Ktable_0) ,  Some(me), List(), 1, 2, 2, List(1), List(32), breakpoints, rr)
      x400_outr_UnitPipe_DenseTransfer.backpressure := true.B | x400_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x400_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x400_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x400_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x400_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x400_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x400_outr_UnitPipe_DenseTransfer.mask := true.B & true.B
      x400_outr_UnitPipe_DenseTransfer.configure("x400_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x400_outr_UnitPipe_DenseTransfer.kernel()
      val x417_outr_UnitPipe_DenseTransfer = new x417_outr_UnitPipe_DenseTransfer_kernel(List(x332_twoNegDram), List(x401), List(x402), List(x343_twoNegSram_0) ,  Some(me), List(), 2, 2, 2, List(1), List(32), breakpoints, rr)
      x417_outr_UnitPipe_DenseTransfer.backpressure := true.B | x417_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x417_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x417_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x417_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x417_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x417_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x417_outr_UnitPipe_DenseTransfer.mask := true.B & true.B
      x417_outr_UnitPipe_DenseTransfer.configure("x417_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x417_outr_UnitPipe_DenseTransfer.kernel()
    }
    val module = Module(new x489_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END ParallelPipe x489 **/
