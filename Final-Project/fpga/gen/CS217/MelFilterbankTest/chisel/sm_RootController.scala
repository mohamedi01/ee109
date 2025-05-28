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

/** Hierarchy: x612 **/
/** BEGIN None RootController **/
class RootController_kernel(
  list_x408_outDRAM: List[FixedPoint],
  list_x544: List[DecoupledIO[Bool]],
  list_x542: List[DecoupledIO[AppCommandDense]],
  list_x543: List[DecoupledIO[AppStoreData]],
  list_x479: List[DecoupledIO[AppLoadData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 3, isFSM = false   , latency = 0.0.toInt, myName = "RootController_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "RootController_iiCtr"))
  
  abstract class RootController_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x479 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x479_p").asInstanceOf[(Int, Int)] )))
      val in_x408_outDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x542 = Decoupled(new AppCommandDense(ModuleParams.getParams("x542_p").asInstanceOf[(Int,Int)] ))
      val in_x416 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x416_p").asInstanceOf[(Int, Int)] )))
      val in_x407_vecDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x406_matDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x477 = Decoupled(new AppCommandDense(ModuleParams.getParams("x477_p").asInstanceOf[(Int,Int)] ))
      val in_x414 = Decoupled(new AppCommandDense(ModuleParams.getParams("x414_p").asInstanceOf[(Int,Int)] ))
      val in_x544 = Flipped(Decoupled(Bool()))
      val in_x543 = Decoupled(new AppStoreData(ModuleParams.getParams("x543_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(3, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(3, 1))
      val rr = Input(Bool())
    })
    def x479 = {io.in_x479} 
    def x408_outDRAM = {io.in_x408_outDRAM} 
    def x542 = {io.in_x542} 
    def x416 = {io.in_x416} 
    def x407_vecDRAM = {io.in_x407_vecDRAM} 
    def x406_matDRAM = {io.in_x406_matDRAM} 
    def x477 = {io.in_x477} 
    def x414 = {io.in_x414} 
    def x544 = {io.in_x544} 
    def x543 = {io.in_x543} 
  }
  def connectWires0(module: RootController_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x479 <> x479
    module.io.in_x408_outDRAM <> x408_outDRAM
    module.io.in_x542 <> x542
    module.io.in_x416 <> x416
    module.io.in_x407_vecDRAM <> x407_vecDRAM
    module.io.in_x406_matDRAM <> x406_matDRAM
    module.io.in_x477 <> x477
    module.io.in_x414 <> x414
    module.io.in_x544 <> x544
    module.io.in_x543 <> x543
  }
  val x408_outDRAM = list_x408_outDRAM(0)
  val x407_vecDRAM = list_x408_outDRAM(1)
  val x406_matDRAM = list_x408_outDRAM(2)
  val x544 = list_x544(0)
  val x542 = list_x542(0)
  val x477 = list_x542(1)
  val x414 = list_x542(2)
  val x543 = list_x543(0)
  val x479 = list_x479(0)
  val x416 = list_x479(1)
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
      Ledger.tieInstrCtr(instrctrs.toList, X612_instrctr, cycles_RootController.io.count, iters_RootController.io.count, 0.U, 0.U)
      val x411_matSRAM_0 = (new x411_matSRAM_0).m.io.asInstanceOf[StandardInterface]
      val x412_vecSRAM_0 = (new x412_vecSRAM_0).m.io.asInstanceOf[StandardInterface]
      val x413_outSRAM_0 = (new x413_outSRAM_0).m.io.asInstanceOf[StandardInterface]
      val x611 = new x611_kernel(List(x407_vecDRAM,x406_matDRAM), List(x477,x414), List(x479,x416), List(x411_matSRAM_0,x412_vecSRAM_0) ,  Some(me), List(), 0, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x611.sm.io.ctrDone := risingEdge(x611.sm.io.ctrInc)
      x611.backpressure := true.B | x611.sm.io.doneLatch
      x611.forwardpressure := (true.B) && (true.B) | x611.sm.io.doneLatch
      x611.sm.io.enableOut.zip(x611.smEnableOuts).foreach{case (l,r) => r := l}
      x611.sm.io.break := false.B
      x611.mask := true.B & true.B
      x611.configure("x611", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x611.kernel()
      val x512_ctr = new CtrObject(Left(Some(0)), Left(Some(2)), Left(Some(1)), 1, 4, false)
      val x513_ctrchain = (new CChainObject(List[CtrObject](x512_ctr), "x513_ctrchain")).cchain.io 
      x513_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x513_ctrchain_p", (x513_ctrchain.par, x513_ctrchain.widths))
      val x541_outr_Foreach = new x541_outr_Foreach_kernel(List(x411_matSRAM_0,x412_vecSRAM_0,x413_outSRAM_0) ,  Some(me), List(x513_ctrchain), 1, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x541_outr_Foreach.sm.io.ctrDone := (x541_outr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x541_outr_Foreach.backpressure := true.B | x541_outr_Foreach.sm.io.doneLatch
      x541_outr_Foreach.forwardpressure := (true.B) && (true.B) | x541_outr_Foreach.sm.io.doneLatch
      x541_outr_Foreach.sm.io.enableOut.zip(x541_outr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x541_outr_Foreach.sm.io.break := false.B
      x541_outr_Foreach.mask := ~x541_outr_Foreach.cchain.head.output.noop & true.B
      x541_outr_Foreach.configure("x541_outr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x541_outr_Foreach.kernel()
