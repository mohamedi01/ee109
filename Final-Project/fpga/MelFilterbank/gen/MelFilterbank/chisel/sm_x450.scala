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

/** Hierarchy: x450 -> x453 **/
/** BEGIN None x450 **/
class x450_kernel(
  list_x292_vecDram: List[FixedPoint],
  list_x297: List[DecoupledIO[AppCommandDense]],
  list_x362: List[DecoupledIO[AppLoadData]],
  list_x295_vecSram_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(ForkJoin, 2, isFSM = false   , latency = 0.0.toInt, myName = "x450_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x450_iiCtr"))
  
  abstract class x450_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x292_vecDram = Input(new FixedPoint(true, 64, 0))
      val in_x297 = Decoupled(new AppCommandDense(ModuleParams.getParams("x297_p").asInstanceOf[(Int,Int)] ))
      val in_x360 = Decoupled(new AppCommandDense(ModuleParams.getParams("x360_p").asInstanceOf[(Int,Int)] ))
      val in_x291_matDram = Input(new FixedPoint(true, 64, 0))
      val in_x362 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x362_p").asInstanceOf[(Int, Int)] )))
      val in_x299 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x299_p").asInstanceOf[(Int, Int)] )))
      val in_x295_vecSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x295_vecSram_0_p").asInstanceOf[MemParams] ))
      val in_x294_matSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x294_matSram_0_p").asInstanceOf[MemParams] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x292_vecDram = {io.in_x292_vecDram} 
    def x297 = {io.in_x297} 
    def x360 = {io.in_x360} 
    def x291_matDram = {io.in_x291_matDram} 
    def x362 = {io.in_x362} 
    def x299 = {io.in_x299} 
    def x295_vecSram_0 = {io.in_x295_vecSram_0} ; io.in_x295_vecSram_0 := DontCare
    def x294_matSram_0 = {io.in_x294_matSram_0} ; io.in_x294_matSram_0 := DontCare
  }
  def connectWires0(module: x450_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x292_vecDram <> x292_vecDram
    module.io.in_x297 <> x297
    module.io.in_x360 <> x360
    module.io.in_x291_matDram <> x291_matDram
    module.io.in_x362 <> x362
    module.io.in_x299 <> x299
    x295_vecSram_0.connectLedger(module.io.in_x295_vecSram_0)
    x294_matSram_0.connectLedger(module.io.in_x294_matSram_0)
  }
  val x292_vecDram = list_x292_vecDram(0)
  val x291_matDram = list_x292_vecDram(1)
  val x297 = list_x297(0)
  val x360 = list_x297(1)
  val x362 = list_x362(0)
  val x299 = list_x362(1)
  val x295_vecSram_0 = list_x295_vecSram_0(0)
  val x294_matSram_0 = list_x295_vecSram_0(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x450")
    implicit val stack = ControllerStack.stack.toList
    class x450_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x450_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x359_outr_UnitPipe_DenseTransfer = new x359_outr_UnitPipe_DenseTransfer_kernel(List(x291_matDram), List(x297), List(x299), List(x294_matSram_0) ,  Some(me), List(), 0, 2, 2, List(1), List(32), breakpoints, rr)
      x359_outr_UnitPipe_DenseTransfer.backpressure := true.B | x359_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x359_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x359_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x359_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x359_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x359_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x359_outr_UnitPipe_DenseTransfer.mask := true.B & true.B
      x359_outr_UnitPipe_DenseTransfer.configure("x359_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x359_outr_UnitPipe_DenseTransfer.kernel()
      val x394_outr_UnitPipe_DenseTransfer = new x394_outr_UnitPipe_DenseTransfer_kernel(List(x292_vecDram), List(x360), List(x362), List(x295_vecSram_0) ,  Some(me), List(), 1, 2, 2, List(1), List(32), breakpoints, rr)
      x394_outr_UnitPipe_DenseTransfer.backpressure := true.B | x394_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x394_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x394_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x394_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x394_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x394_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x394_outr_UnitPipe_DenseTransfer.mask := true.B & true.B
      x394_outr_UnitPipe_DenseTransfer.configure("x394_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x394_outr_UnitPipe_DenseTransfer.kernel()
    }
    val module = Module(new x450_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END ParallelPipe x450 **/
