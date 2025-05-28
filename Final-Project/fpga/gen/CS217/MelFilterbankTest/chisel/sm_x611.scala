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

/** Hierarchy: x611 -> x612 **/
/** BEGIN None x611 **/
class x611_kernel(
  list_x407_vecDRAM: List[FixedPoint],
  list_x477: List[DecoupledIO[AppCommandDense]],
  list_x479: List[DecoupledIO[AppLoadData]],
  list_x411_matSRAM_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(ForkJoin, 2, isFSM = false   , latency = 0.0.toInt, myName = "x611_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x611_iiCtr"))
  
  abstract class x611_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x479 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x479_p").asInstanceOf[(Int, Int)] )))
      val in_x416 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x416_p").asInstanceOf[(Int, Int)] )))
      val in_x411_matSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x411_matSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x407_vecDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x412_vecSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x412_vecSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x406_matDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x477 = Decoupled(new AppCommandDense(ModuleParams.getParams("x477_p").asInstanceOf[(Int,Int)] ))
      val in_x414 = Decoupled(new AppCommandDense(ModuleParams.getParams("x414_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x479 = {io.in_x479} 
    def x416 = {io.in_x416} 
    def x411_matSRAM_0 = {io.in_x411_matSRAM_0} ; io.in_x411_matSRAM_0 := DontCare
    def x407_vecDRAM = {io.in_x407_vecDRAM} 
    def x412_vecSRAM_0 = {io.in_x412_vecSRAM_0} ; io.in_x412_vecSRAM_0 := DontCare
    def x406_matDRAM = {io.in_x406_matDRAM} 
    def x477 = {io.in_x477} 
    def x414 = {io.in_x414} 
  }
  def connectWires0(module: x611_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x479 <> x479
    module.io.in_x416 <> x416
    x411_matSRAM_0.connectLedger(module.io.in_x411_matSRAM_0)
    module.io.in_x407_vecDRAM <> x407_vecDRAM
    x412_vecSRAM_0.connectLedger(module.io.in_x412_vecSRAM_0)
    module.io.in_x406_matDRAM <> x406_matDRAM
    module.io.in_x477 <> x477
    module.io.in_x414 <> x414
  }
  val x407_vecDRAM = list_x407_vecDRAM(0)
  val x406_matDRAM = list_x407_vecDRAM(1)
  val x477 = list_x477(0)
  val x414 = list_x477(1)
  val x479 = list_x479(0)
  val x416 = list_x479(1)
  val x411_matSRAM_0 = list_x411_matSRAM_0(0)
  val x412_vecSRAM_0 = list_x411_matSRAM_0(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x611")
    implicit val stack = ControllerStack.stack.toList
    class x611_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x611_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x611 = Module(new InstrumentationCounter())
      val iters_x611 = Module(new InstrumentationCounter())
      cycles_x611.io.enable := io.sigsIn.baseEn
      iters_x611.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X611_instrctr, cycles_x611.io.count, iters_x611.io.count, 0.U, 0.U)
      val x476_outr_UnitPipe_DenseTransfer = new x476_outr_UnitPipe_DenseTransfer_kernel(List(x406_matDRAM), List(x414), List(x416), List(x411_matSRAM_0) ,  Some(me), List(), 0, 2, 2, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x476_outr_UnitPipe_DenseTransfer.backpressure := true.B | x476_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x476_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x476_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x476_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x476_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x476_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x476_outr_UnitPipe_DenseTransfer.mask := true.B & true.B
      x476_outr_UnitPipe_DenseTransfer.configure("x476_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x476_outr_UnitPipe_DenseTransfer.kernel()
      val x511_outr_UnitPipe_DenseTransfer = new x511_outr_UnitPipe_DenseTransfer_kernel(List(x407_vecDRAM), List(x477), List(x479), List(x412_vecSRAM_0) ,  Some(me), List(), 1, 2, 2, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x511_outr_UnitPipe_DenseTransfer.backpressure := true.B | x511_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x511_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x511_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x511_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x511_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x511_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x511_outr_UnitPipe_DenseTransfer.mask := true.B & true.B
      x511_outr_UnitPipe_DenseTransfer.configure("x511_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x511_outr_UnitPipe_DenseTransfer.kernel()
    }
    val module = Module(new x611_concrete(sm.p.depth)); module.io := DontCare
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
/** END ParallelPipe x611 **/
