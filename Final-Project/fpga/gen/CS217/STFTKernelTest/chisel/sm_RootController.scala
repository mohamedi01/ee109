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

/** Hierarchy: x710 **/
/** BEGIN None RootController **/
class RootController_kernel(
  list_x610: List[DecoupledIO[AppStoreData]],
  list_x471: List[DecoupledIO[AppCommandDense]],
  list_x473: List[DecoupledIO[AppLoadData]],
  list_x555: List[DecoupledIO[Bool]],
  list_x460_inDRAM: List[FixedPoint],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 3, isFSM = false   , latency = 0.0.toInt, myName = "RootController_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "RootController_iiCtr"))
  
  abstract class RootController_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x555 = Flipped(Decoupled(Bool()))
      val in_x460_inDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x461_realDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x610 = Decoupled(new AppStoreData(ModuleParams.getParams("x610_p").asInstanceOf[(Int,Int)] ))
      val in_x471 = Decoupled(new AppCommandDense(ModuleParams.getParams("x471_p").asInstanceOf[(Int,Int)] ))
      val in_x554 = Decoupled(new AppStoreData(ModuleParams.getParams("x554_p").asInstanceOf[(Int,Int)] ))
      val in_x611 = Flipped(Decoupled(Bool()))
      val in_x609 = Decoupled(new AppCommandDense(ModuleParams.getParams("x609_p").asInstanceOf[(Int,Int)] ))
      val in_x462_imagDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x473 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x473_p").asInstanceOf[(Int, Int)] )))
      val in_x553 = Decoupled(new AppCommandDense(ModuleParams.getParams("x553_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(3, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(3, 1))
      val rr = Input(Bool())
    })
    def x555 = {io.in_x555} 
    def x460_inDRAM = {io.in_x460_inDRAM} 
    def x461_realDRAM = {io.in_x461_realDRAM} 
    def x610 = {io.in_x610} 
    def x471 = {io.in_x471} 
    def x554 = {io.in_x554} 
    def x611 = {io.in_x611} 
    def x609 = {io.in_x609} 
    def x462_imagDRAM = {io.in_x462_imagDRAM} 
    def x473 = {io.in_x473} 
    def x553 = {io.in_x553} 
  }
  def connectWires0(module: RootController_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x555 <> x555
    module.io.in_x460_inDRAM <> x460_inDRAM
    module.io.in_x461_realDRAM <> x461_realDRAM
    module.io.in_x610 <> x610
    module.io.in_x471 <> x471
    module.io.in_x554 <> x554
    module.io.in_x611 <> x611
    module.io.in_x609 <> x609
    module.io.in_x462_imagDRAM <> x462_imagDRAM
    module.io.in_x473 <> x473
    module.io.in_x553 <> x553
  }
  val x610 = list_x610(0)
  val x554 = list_x610(1)
  val x471 = list_x471(0)
  val x609 = list_x471(1)
  val x553 = list_x471(2)
  val x473 = list_x473(0)
  val x555 = list_x555(0)
  val x611 = list_x555(1)
  val x460_inDRAM = list_x460_inDRAM(0)
  val x461_realDRAM = list_x460_inDRAM(1)
  val x462_imagDRAM = list_x460_inDRAM(2)
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
      Ledger.tieInstrCtr(instrctrs.toList, X710_instrctr, cycles_RootController.io.count, iters_RootController.io.count, 0.U, 0.U)
      val x464_winSRAM_0 = (new x464_winSRAM_0).m.io.asInstanceOf[StandardInterface]
      val x465_inSRAM_0 = (new x465_inSRAM_0).m.io.asInstanceOf[StandardInterface]
      val x466_frame_0 = (new x466_frame_0).m.io.asInstanceOf[NBufInterface]
      val x467_real_0 = (new x467_real_0).m.io.asInstanceOf[NBufInterface]
      val x468_imag_0 = (new x468_imag_0).m.io.asInstanceOf[NBufInterface]
      val x469_real2D_0 = (new x469_real2D_0).m.io.asInstanceOf[StandardInterface]
      val x470_imag2D_0 = (new x470_imag2D_0).m.io.asInstanceOf[StandardInterface]
      val x506_ctr = new CtrObject(Left(Some(0)), Left(Some(4)), Left(Some(1)), 1, 5, false)
      val x507_ctrchain = (new CChainObject(List[CtrObject](x506_ctr), "x507_ctrchain")).cchain.io 
      x507_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x507_ctrchain_p", (x507_ctrchain.par, x507_ctrchain.widths))
      val x708 = new x708_kernel(List(x507_ctrchain), List(x471), List(x473), List(x460_inDRAM), List(x465_inSRAM_0,x464_winSRAM_0) ,  Some(me), List(), 0, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x708.sm.io.ctrDone := risingEdge(x708.sm.io.ctrInc)
      x708.backpressure := true.B | x708.sm.io.doneLatch
      x708.forwardpressure := (true.B) && (true.B) | x708.sm.io.doneLatch
      x708.sm.io.enableOut.zip(x708.smEnableOuts).foreach{case (l,r) => r := l}
      x708.sm.io.break := false.B
      x708.mask := true.B & true.B
      x708.configure("x708", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x708.kernel()
      val x512_ctr = new CtrObject(Left(Some(0)), Left(Some(2)), Left(Some(1)), 1, 4, false)
      val x513_ctrchain = (new CChainObject(List[CtrObject](x512_ctr), "x513_ctrchain")).cchain.io 
      x513_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x513_ctrchain_p", (x513_ctrchain.par, x513_ctrchain.widths))
      val x552_outr_Foreach = new x552_outr_Foreach_kernel(List(x470_imag2D_0,x465_inSRAM_0,x464_winSRAM_0,x469_real2D_0), List(x468_imag_0,x467_real_0,x466_frame_0) ,  Some(me), List(x513_ctrchain), 1, 3, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x552_outr_Foreach.sm.io.ctrDone := (x552_outr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x552_outr_Foreach.backpressure := true.B | x552_outr_Foreach.sm.io.doneLatch
      x552_outr_Foreach.forwardpressure := (true.B) && (true.B) | x552_outr_Foreach.sm.io.doneLatch
      x552_outr_Foreach.sm.io.enableOut.zip(x552_outr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x552_outr_Foreach.sm.io.break := false.B
      x552_outr_Foreach.mask := ~x552_outr_Foreach.cchain.head.output.noop & true.B
      x552_outr_Foreach.configure("x552_outr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x552_outr_Foreach.kernel()
      val x709 = new x709_kernel(List(x610,x554), List(x461_realDRAM,x462_imagDRAM), List(x470_imag2D_0,x469_real2D_0), List(x609,x553), List(x555,x611) ,  Some(me), List(), 2, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x709.sm.io.ctrDone := risingEdge(x709.sm.io.ctrInc)
      x709.backpressure := true.B | x709.sm.io.doneLatch
      x709.forwardpressure := (true.B) && (true.B) | x709.sm.io.doneLatch
      x709.sm.io.enableOut.zip(x709.smEnableOuts).foreach{case (l,r) => r := l}
      x709.sm.io.break := false.B
      x709.mask := true.B & true.B
      x709.configure("x709", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x709.kernel()
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
