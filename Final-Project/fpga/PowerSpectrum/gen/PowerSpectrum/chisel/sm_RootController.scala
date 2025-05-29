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

/** Hierarchy: x375 **/
/** BEGIN None RootController **/
class RootController_kernel(
  list_x249_outDram: List[FixedPoint],
  list_x338: List[DecoupledIO[Bool]],
  list_x255: List[DecoupledIO[AppLoadData]],
  list_x288: List[DecoupledIO[AppCommandDense]],
  list_x337: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 3, isFSM = false   , latency = 0.0.toInt, myName = "RootController_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "RootController_iiCtr"))
  
  abstract class RootController_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x249_outDram = Input(new FixedPoint(true, 64, 0))
      val in_x247_realDram = Input(new FixedPoint(true, 64, 0))
      val in_x288 = Decoupled(new AppCommandDense(ModuleParams.getParams("x288_p").asInstanceOf[(Int,Int)] ))
      val in_x253 = Decoupled(new AppCommandDense(ModuleParams.getParams("x253_p").asInstanceOf[(Int,Int)] ))
      val in_x248_imagDram = Input(new FixedPoint(true, 64, 0))
      val in_x255 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x255_p").asInstanceOf[(Int, Int)] )))
      val in_x338 = Flipped(Decoupled(Bool()))
      val in_x336 = Decoupled(new AppCommandDense(ModuleParams.getParams("x336_p").asInstanceOf[(Int,Int)] ))
      val in_x290 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x290_p").asInstanceOf[(Int, Int)] )))
      val in_x337 = Decoupled(new AppStoreData(ModuleParams.getParams("x337_p").asInstanceOf[(Int,Int)] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(3, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(3, 1))
      val rr = Input(Bool())
    })
    def x249_outDram = {io.in_x249_outDram} 
    def x247_realDram = {io.in_x247_realDram} 
    def x288 = {io.in_x288} 
    def x253 = {io.in_x253} 
    def x248_imagDram = {io.in_x248_imagDram} 
    def x255 = {io.in_x255} 
    def x338 = {io.in_x338} 
    def x336 = {io.in_x336} 
    def x290 = {io.in_x290} 
    def x337 = {io.in_x337} 
  }
  def connectWires0(module: RootController_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x249_outDram <> x249_outDram
    module.io.in_x247_realDram <> x247_realDram
    module.io.in_x288 <> x288
    module.io.in_x253 <> x253
    module.io.in_x248_imagDram <> x248_imagDram
    module.io.in_x255 <> x255
    module.io.in_x338 <> x338
    module.io.in_x336 <> x336
    module.io.in_x290 <> x290
    module.io.in_x337 <> x337
  }
  val x249_outDram = list_x249_outDram(0)
  val x247_realDram = list_x249_outDram(1)
  val x248_imagDram = list_x249_outDram(2)
  val x338 = list_x338(0)
  val x255 = list_x255(0)
  val x290 = list_x255(1)
  val x288 = list_x288(0)
  val x253 = list_x288(1)
  val x336 = list_x288(2)
  val x337 = list_x337(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "RootController")
    implicit val stack = ControllerStack.stack.toList
    class RootController_concrete(depth: Int)(implicit stack: List[KernelHash]) extends RootController_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x250_realSram_0 = (new x250_realSram_0).m.io.asInstanceOf[StandardInterface]
      val x251_imagSram_0 = (new x251_imagSram_0).m.io.asInstanceOf[StandardInterface]
      val x252_outSram_0 = (new x252_outSram_0).m.io.asInstanceOf[StandardInterface]
      val x374 = new x374_kernel(List(x247_realDram,x248_imagDram), List(x288,x253), List(x255,x290), List(x251_imagSram_0,x250_realSram_0) ,  Some(me), List(), 0, 2, 1, List(1), List(32), breakpoints, rr)
      x374.sm.io.ctrDone := risingEdge(x374.sm.io.ctrInc)
      x374.backpressure := true.B | x374.sm.io.doneLatch
      x374.forwardpressure := (true.B) && (true.B) | x374.sm.io.doneLatch
      x374.sm.io.enableOut.zip(x374.smEnableOuts).foreach{case (l,r) => r := l}
      x374.sm.io.break := false.B
      x374.mask := true.B & true.B
      x374.configure("x374", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x374.kernel()
      val x323_ctr = new CtrObject(Left(Some(0)), Left(Some(384312)), Left(Some(1)), 1, 21, false)
      val x324_ctrchain = (new CChainObject(List[CtrObject](x323_ctr), "x324_ctrchain")).cchain.io 
      x324_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x324_ctrchain_p", (x324_ctrchain.par, x324_ctrchain.widths))
      val x335_inr_Foreach = new x335_inr_Foreach_kernel(List(x252_outSram_0,x251_imagSram_0,x250_realSram_0) ,  Some(me), List(x324_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, rr)
      x335_inr_Foreach.sm.io.ctrDone := (x335_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x335_inr_Foreach.backpressure := true.B | x335_inr_Foreach.sm.io.doneLatch
      x335_inr_Foreach.forwardpressure := (true.B) && (true.B) | x335_inr_Foreach.sm.io.doneLatch
      x335_inr_Foreach.sm.io.enableOut.zip(x335_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x335_inr_Foreach.sm.io.break := false.B
      x335_inr_Foreach.mask := ~x335_inr_Foreach.cchain.head.output.noop & true.B
      x335_inr_Foreach.configure("x335_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x335_inr_Foreach.kernel()
      val x368_outr_UnitPipe_DenseTransfer = new x368_outr_UnitPipe_DenseTransfer_kernel(List(x249_outDram), List(x336), List(x252_outSram_0), List(x338), List(x337) ,  Some(me), List(), 2, 1, 1, List(1), List(32), breakpoints, rr)
      x368_outr_UnitPipe_DenseTransfer.backpressure := true.B | x368_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x368_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x368_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x368_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x368_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x368_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x368_outr_UnitPipe_DenseTransfer.mask := true.B & true.B
      x368_outr_UnitPipe_DenseTransfer.configure("x368_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x368_outr_UnitPipe_DenseTransfer.kernel()
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
