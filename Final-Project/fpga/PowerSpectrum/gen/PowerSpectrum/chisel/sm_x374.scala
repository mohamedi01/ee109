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

/** Hierarchy: x374 -> x375 **/
/** BEGIN None x374 **/
class x374_kernel(
  list_x247_realDram: List[FixedPoint],
  list_x288: List[DecoupledIO[AppCommandDense]],
  list_x255: List[DecoupledIO[AppLoadData]],
  list_x251_imagSram_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(ForkJoin, 2, isFSM = false   , latency = 0.0.toInt, myName = "x374_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x374_iiCtr"))
  
  abstract class x374_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x247_realDram = Input(new FixedPoint(true, 64, 0))
      val in_x288 = Decoupled(new AppCommandDense(ModuleParams.getParams("x288_p").asInstanceOf[(Int,Int)] ))
      val in_x253 = Decoupled(new AppCommandDense(ModuleParams.getParams("x253_p").asInstanceOf[(Int,Int)] ))
      val in_x248_imagDram = Input(new FixedPoint(true, 64, 0))
      val in_x251_imagSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x251_imagSram_0_p").asInstanceOf[MemParams] ))
      val in_x255 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x255_p").asInstanceOf[(Int, Int)] )))
      val in_x250_realSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x250_realSram_0_p").asInstanceOf[MemParams] ))
      val in_x290 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x290_p").asInstanceOf[(Int, Int)] )))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x247_realDram = {io.in_x247_realDram} 
    def x288 = {io.in_x288} 
    def x253 = {io.in_x253} 
    def x248_imagDram = {io.in_x248_imagDram} 
    def x251_imagSram_0 = {io.in_x251_imagSram_0} ; io.in_x251_imagSram_0 := DontCare
    def x255 = {io.in_x255} 
    def x250_realSram_0 = {io.in_x250_realSram_0} ; io.in_x250_realSram_0 := DontCare
    def x290 = {io.in_x290} 
  }
  def connectWires0(module: x374_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x247_realDram <> x247_realDram
    module.io.in_x288 <> x288
    module.io.in_x253 <> x253
    module.io.in_x248_imagDram <> x248_imagDram
    x251_imagSram_0.connectLedger(module.io.in_x251_imagSram_0)
    module.io.in_x255 <> x255
    x250_realSram_0.connectLedger(module.io.in_x250_realSram_0)
    module.io.in_x290 <> x290
  }
  val x247_realDram = list_x247_realDram(0)
  val x248_imagDram = list_x247_realDram(1)
  val x288 = list_x288(0)
  val x253 = list_x288(1)
  val x255 = list_x255(0)
  val x290 = list_x255(1)
  val x251_imagSram_0 = list_x251_imagSram_0(0)
  val x250_realSram_0 = list_x251_imagSram_0(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x374")
    implicit val stack = ControllerStack.stack.toList
    class x374_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x374_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x287_outr_UnitPipe_DenseTransfer = new x287_outr_UnitPipe_DenseTransfer_kernel(List(x247_realDram), List(x253), List(x255), List(x250_realSram_0) ,  Some(me), List(), 0, 2, 2, List(1), List(32), breakpoints, rr)
      x287_outr_UnitPipe_DenseTransfer.backpressure := true.B | x287_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x287_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x287_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x287_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x287_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x287_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x287_outr_UnitPipe_DenseTransfer.mask := true.B & true.B
      x287_outr_UnitPipe_DenseTransfer.configure("x287_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x287_outr_UnitPipe_DenseTransfer.kernel()
      val x322_outr_UnitPipe_DenseTransfer = new x322_outr_UnitPipe_DenseTransfer_kernel(List(x248_imagDram), List(x288), List(x290), List(x251_imagSram_0) ,  Some(me), List(), 1, 2, 2, List(1), List(32), breakpoints, rr)
      x322_outr_UnitPipe_DenseTransfer.backpressure := true.B | x322_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x322_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x322_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x322_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x322_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x322_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x322_outr_UnitPipe_DenseTransfer.mask := true.B & true.B
      x322_outr_UnitPipe_DenseTransfer.configure("x322_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x322_outr_UnitPipe_DenseTransfer.kernel()
    }
    val module = Module(new x374_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END ParallelPipe x374 **/
