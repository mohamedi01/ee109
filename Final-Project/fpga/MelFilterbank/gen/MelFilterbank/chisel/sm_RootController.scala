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

/** Hierarchy: x453 **/
/** BEGIN None RootController **/
class RootController_kernel(
  list_x362: List[DecoupledIO[AppLoadData]],
  list_x297: List[DecoupledIO[AppCommandDense]],
  list_x422: List[DecoupledIO[Bool]],
  list_x293_outDram: List[FixedPoint],
  list_x421: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 3, isFSM = false   , latency = 0.0.toInt, myName = "RootController_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "RootController_iiCtr"))
  
  abstract class RootController_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x421 = Decoupled(new AppStoreData(ModuleParams.getParams("x421_p").asInstanceOf[(Int,Int)] ))
      val in_x293_outDram = Input(new FixedPoint(true, 64, 0))
      val in_x292_vecDram = Input(new FixedPoint(true, 64, 0))
      val in_x297 = Decoupled(new AppCommandDense(ModuleParams.getParams("x297_p").asInstanceOf[(Int,Int)] ))
      val in_x420 = Decoupled(new AppCommandDense(ModuleParams.getParams("x420_p").asInstanceOf[(Int,Int)] ))
      val in_x360 = Decoupled(new AppCommandDense(ModuleParams.getParams("x360_p").asInstanceOf[(Int,Int)] ))
      val in_x291_matDram = Input(new FixedPoint(true, 64, 0))
      val in_x362 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x362_p").asInstanceOf[(Int, Int)] )))
      val in_x299 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x299_p").asInstanceOf[(Int, Int)] )))
      val in_x422 = Flipped(Decoupled(Bool()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(3, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(3, 1))
      val rr = Input(Bool())
    })
    def x421 = {io.in_x421} 
    def x293_outDram = {io.in_x293_outDram} 
    def x292_vecDram = {io.in_x292_vecDram} 
    def x297 = {io.in_x297} 
    def x420 = {io.in_x420} 
    def x360 = {io.in_x360} 
    def x291_matDram = {io.in_x291_matDram} 
    def x362 = {io.in_x362} 
    def x299 = {io.in_x299} 
    def x422 = {io.in_x422} 
  }
  def connectWires0(module: RootController_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x421 <> x421
    module.io.in_x293_outDram <> x293_outDram
    module.io.in_x292_vecDram <> x292_vecDram
    module.io.in_x297 <> x297
    module.io.in_x420 <> x420
    module.io.in_x360 <> x360
    module.io.in_x291_matDram <> x291_matDram
    module.io.in_x362 <> x362
    module.io.in_x299 <> x299
    module.io.in_x422 <> x422
  }
  val x362 = list_x362(0)
  val x299 = list_x362(1)
  val x297 = list_x297(0)
  val x420 = list_x297(1)
  val x360 = list_x297(2)
  val x422 = list_x422(0)
  val x293_outDram = list_x293_outDram(0)
  val x292_vecDram = list_x293_outDram(1)
  val x291_matDram = list_x293_outDram(2)
  val x421 = list_x421(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "RootController")
    implicit val stack = ControllerStack.stack.toList
    class RootController_concrete(depth: Int)(implicit stack: List[KernelHash]) extends RootController_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x294_matSram_0 = (new x294_matSram_0).m.io.asInstanceOf[StandardInterface]
      val x295_vecSram_0 = (new x295_vecSram_0).m.io.asInstanceOf[StandardInterface]
      val x296_outSram_0 = (new x296_outSram_0).m.io.asInstanceOf[StandardInterface]
      val x450 = new x450_kernel(List(x292_vecDram,x291_matDram), List(x297,x360), List(x362,x299), List(x295_vecSram_0,x294_matSram_0) ,  Some(me), List(), 0, 2, 1, List(1), List(32), breakpoints, rr)
      x450.sm.io.ctrDone := risingEdge(x450.sm.io.ctrInc)
      x450.backpressure := true.B | x450.sm.io.doneLatch
      x450.forwardpressure := (true.B) && (true.B) | x450.sm.io.doneLatch
      x450.sm.io.enableOut.zip(x450.smEnableOuts).foreach{case (l,r) => r := l}
      x450.sm.io.break := false.B
      x450.mask := true.B & true.B
      x450.configure("x450", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x450.kernel()
      val x395_ctr = new CtrObject(Left(Some(0)), Left(Some(80)), Left(Some(1)), 1, 9, false)
      val x396_ctrchain = (new CChainObject(List[CtrObject](x395_ctr), "x396_ctrchain")).cchain.io 
      x396_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x396_ctrchain_p", (x396_ctrchain.par, x396_ctrchain.widths))
      val x452_outr_Foreach = new x452_outr_Foreach_kernel(List(x296_outSram_0,x295_vecSram_0,x294_matSram_0) ,  Some(me), List(x396_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, rr)
      x452_outr_Foreach.sm.io.ctrDone := (x452_outr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x452_outr_Foreach.backpressure := true.B | x452_outr_Foreach.sm.io.doneLatch
      x452_outr_Foreach.forwardpressure := (true.B) && (true.B) | x452_outr_Foreach.sm.io.doneLatch
      x452_outr_Foreach.sm.io.enableOut.zip(x452_outr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x452_outr_Foreach.sm.io.break := false.B
      x452_outr_Foreach.mask := ~x452_outr_Foreach.cchain.head.output.noop & true.B
      x452_outr_Foreach.configure("x452_outr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x452_outr_Foreach.kernel()
      val x441_outr_UnitPipe_DenseTransfer = new x441_outr_UnitPipe_DenseTransfer_kernel(List(x422), List(x293_outDram), List(x296_outSram_0), List(x420), List(x421) ,  Some(me), List(), 2, 3, 3, List(1), List(32), breakpoints, rr)
      x441_outr_UnitPipe_DenseTransfer.backpressure := true.B | x441_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x441_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x441_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x441_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x441_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x441_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x441_outr_UnitPipe_DenseTransfer.mask := true.B & true.B
      x441_outr_UnitPipe_DenseTransfer.configure("x441_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x441_outr_UnitPipe_DenseTransfer.kernel()
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
