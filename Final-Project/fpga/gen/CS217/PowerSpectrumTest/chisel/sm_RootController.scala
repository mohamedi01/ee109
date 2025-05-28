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

/** Hierarchy: x481 **/
/** BEGIN None RootController **/
class RootController_kernel(
  list_x321_realDRAM: List[FixedPoint],
  list_x329: List[DecoupledIO[AppCommandDense]],
  list_x414: List[DecoupledIO[Bool]],
  list_x366: List[DecoupledIO[AppLoadData]],
  list_x413: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 3, isFSM = false   , latency = 0.0.toInt, myName = "RootController_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "RootController_iiCtr"))
  
  abstract class RootController_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x321_realDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x366 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x366_p").asInstanceOf[(Int, Int)] )))
      val in_x329 = Decoupled(new AppCommandDense(ModuleParams.getParams("x329_p").asInstanceOf[(Int,Int)] ))
      val in_x412 = Decoupled(new AppCommandDense(ModuleParams.getParams("x412_p").asInstanceOf[(Int,Int)] ))
      val in_x413 = Decoupled(new AppStoreData(ModuleParams.getParams("x413_p").asInstanceOf[(Int,Int)] ))
      val in_x323_outDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x414 = Flipped(Decoupled(Bool()))
      val in_x331 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x331_p").asInstanceOf[(Int, Int)] )))
      val in_x322_imagDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x364 = Decoupled(new AppCommandDense(ModuleParams.getParams("x364_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(3, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(3, 1))
      val rr = Input(Bool())
    })
    def x321_realDRAM = {io.in_x321_realDRAM} 
    def x366 = {io.in_x366} 
    def x329 = {io.in_x329} 
    def x412 = {io.in_x412} 
    def x413 = {io.in_x413} 
    def x323_outDRAM = {io.in_x323_outDRAM} 
    def x414 = {io.in_x414} 
    def x331 = {io.in_x331} 
    def x322_imagDRAM = {io.in_x322_imagDRAM} 
    def x364 = {io.in_x364} 
  }
  def connectWires0(module: RootController_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x321_realDRAM <> x321_realDRAM
    module.io.in_x366 <> x366
    module.io.in_x329 <> x329
    module.io.in_x412 <> x412
    module.io.in_x413 <> x413
    module.io.in_x323_outDRAM <> x323_outDRAM
    module.io.in_x414 <> x414
    module.io.in_x331 <> x331
    module.io.in_x322_imagDRAM <> x322_imagDRAM
    module.io.in_x364 <> x364
  }
  val x321_realDRAM = list_x321_realDRAM(0)
  val x323_outDRAM = list_x321_realDRAM(1)
  val x322_imagDRAM = list_x321_realDRAM(2)
  val x329 = list_x329(0)
  val x412 = list_x329(1)
  val x364 = list_x329(2)
  val x414 = list_x414(0)
  val x366 = list_x366(0)
  val x331 = list_x366(1)
  val x413 = list_x413(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "RootController")
    implicit val stack = ControllerStack.stack.toList
    class RootController_concrete(depth: Int)(implicit stack: List[KernelHash]) extends RootController_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_RootController = Module(new InstrumentationCounter())
      val iters_RootController = Module(new InstrumentationCounter())
      cycles_RootController.io.enable := io.sigsIn.baseEn
      iters_RootController.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X481_instrctr, cycles_RootController.io.count, iters_RootController.io.count, 0.U, 0.U)
      val x326_realSRAM_0 = (new x326_realSRAM_0).m.io.asInstanceOf[StandardInterface]
      val x327_imagSRAM_0 = (new x327_imagSRAM_0).m.io.asInstanceOf[StandardInterface]
      val x328_outSRAM_0 = (new x328_outSRAM_0).m.io.asInstanceOf[StandardInterface]
      val x480 = new x480_kernel(List(x321_realDRAM,x322_imagDRAM), List(x329,x364), List(x366,x331), List(x327_imagSRAM_0,x326_realSRAM_0) ,  Some(me), List(), 0, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x480.sm.io.ctrDone := risingEdge(x480.sm.io.ctrInc)
      x480.backpressure := true.B | x480.sm.io.doneLatch
      x480.forwardpressure := (true.B) && (true.B) | x480.sm.io.doneLatch
      x480.sm.io.enableOut.zip(x480.smEnableOuts).foreach{case (l,r) => r := l}
      x480.sm.io.break := false.B
      x480.mask := true.B & true.B
      x480.configure("x480", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x480.kernel()
      val x399_ctr = new CtrObject(Left(Some(0)), Left(Some(4)), Left(Some(1)), 1, 5, false)
      val x400_ctrchain = (new CChainObject(List[CtrObject](x399_ctr), "x400_ctrchain")).cchain.io 
      x400_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x400_ctrchain_p", (x400_ctrchain.par, x400_ctrchain.widths))
      val x411_inr_Foreach = new x411_inr_Foreach_kernel(List(x328_outSRAM_0,x327_imagSRAM_0,x326_realSRAM_0) ,  Some(me), List(x400_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x411_inr_Foreach.sm.io.ctrDone := (x411_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x411_inr_Foreach.backpressure := true.B | x411_inr_Foreach.sm.io.doneLatch
      x411_inr_Foreach.forwardpressure := (true.B) && (true.B) | x411_inr_Foreach.sm.io.doneLatch
      x411_inr_Foreach.sm.io.enableOut.zip(x411_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x411_inr_Foreach.sm.io.break := false.B
      x411_inr_Foreach.mask := ~x411_inr_Foreach.cchain.head.output.noop & true.B
      x411_inr_Foreach.configure("x411_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x411_inr_Foreach.kernel()
      val x444_outr_UnitPipe_DenseTransfer = new x444_outr_UnitPipe_DenseTransfer_kernel(List(x414), List(x323_outDRAM), List(x412), List(x328_outSRAM_0), List(x413) ,  Some(me), List(), 2, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x444_outr_UnitPipe_DenseTransfer.backpressure := true.B | x444_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x444_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x444_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x444_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x444_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x444_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x444_outr_UnitPipe_DenseTransfer.mask := true.B & true.B
      x444_outr_UnitPipe_DenseTransfer.configure("x444_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x444_outr_UnitPipe_DenseTransfer.kernel()
    }
    val module = Module(new RootController_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectInstrCtrs(instrctrs, module.io.in_instrctrs)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END AccelScope RootController **/
