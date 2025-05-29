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

/** Hierarchy: x476 -> x613 -> x614 **/
/** BEGIN Some(DenseTransfer) x476_outr_UnitPipe_DenseTransfer **/
class x476_outr_UnitPipe_DenseTransfer_kernel(
  list_x406_matDRAM: List[FixedPoint],
  list_x414: List[DecoupledIO[AppCommandDense]],
  list_x416: List[DecoupledIO[AppLoadData]],
  list_x411_matSRAM_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 2, isFSM = false   , latency = 0.0.toInt, myName = "x476_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x476_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x476_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x411_matSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x411_matSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x406_matDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x416 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x416_p").asInstanceOf[(Int, Int)] )))
      val in_x414 = Decoupled(new AppCommandDense(ModuleParams.getParams("x414_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 2, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 2))
      val rr = Input(Bool())
    })
    def x411_matSRAM_0 = {io.in_x411_matSRAM_0} ; io.in_x411_matSRAM_0 := DontCare
    def x406_matDRAM = {io.in_x406_matDRAM} 
    def x416 = {io.in_x416} 
    def x414 = {io.in_x414} 
  }
  def connectWires0(module: x476_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    x411_matSRAM_0.connectLedger(module.io.in_x411_matSRAM_0)
    module.io.in_x406_matDRAM <> x406_matDRAM
    module.io.in_x416 <> x416
    module.io.in_x414 <> x414
  }
  val x406_matDRAM = list_x406_matDRAM(0)
  val x414 = list_x414(0)
  val x416 = list_x416(0)
  val x411_matSRAM_0 = list_x411_matSRAM_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x476_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x476_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x476_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x476_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      val iters_x476_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      cycles_x476_outr_UnitPipe_DenseTransfer.io.enable := io.sigsIn.baseEn
      iters_x476_outr_UnitPipe_DenseTransfer.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X476_instrctr, cycles_x476_outr_UnitPipe_DenseTransfer.io.count, iters_x476_outr_UnitPipe_DenseTransfer.io.count, 0.U, 0.U)
      val x415_fifo = (new x415_fifo).m.io.asInstanceOf[FIFOInterface]
      val x417_ctr = new CtrObject(Left(Some(0)), Left(Some(2)), Left(Some(1)), 1, 4, false)
      val x418_ctrchain = (new CChainObject(List[CtrObject](x417_ctr), "x418_ctrchain")).cchain.io 
      x418_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x418_ctrchain_p", (x418_ctrchain.par, x418_ctrchain.widths))
      val x439_inr_Foreach = new x439_inr_Foreach_kernel(List(x406_matDRAM), List(x415_fifo), List(x414) ,  Some(me), List(x418_ctrchain), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x439_inr_Foreach.sm.io.ctrDone := (x439_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x439_inr_Foreach.backpressure := (~x415_fifo.full.D(56.2-1) | ~(x415_fifo.active(0).out)) & x414.ready | x439_inr_Foreach.sm.io.doneLatch
      x439_inr_Foreach.forwardpressure := (true.B) && (true.B) | x439_inr_Foreach.sm.io.doneLatch
      x439_inr_Foreach.sm.io.enableOut.zip(x439_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x439_inr_Foreach.sm.io.break := false.B
      x439_inr_Foreach.mask := ~x439_inr_Foreach.cchain.head.output.noop & true.B
      x439_inr_Foreach.configure("x439_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x439_inr_Foreach.kernel()
      val x441_ctr = new CtrObject(Left(Some(0)), Left(Some(2)), Left(Some(1)), 1, 4, false)
      val x442_ctrchain = (new CChainObject(List[CtrObject](x441_ctr), "x442_ctrchain")).cchain.io 
      x442_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x442_ctrchain_p", (x442_ctrchain.par, x442_ctrchain.widths))
      val x475_outr_Foreach = new x475_outr_Foreach_kernel(List(x415_fifo), List(x416), List(x411_matSRAM_0) ,  Some(me), List(x442_ctrchain), 1, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x475_outr_Foreach.sm.io.ctrDone := (x475_outr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x475_outr_Foreach.backpressure := true.B | x475_outr_Foreach.sm.io.doneLatch
      x475_outr_Foreach.forwardpressure := (true.B) && (true.B) | x475_outr_Foreach.sm.io.doneLatch
      x475_outr_Foreach.sm.io.enableOut.zip(x475_outr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x475_outr_Foreach.sm.io.break := false.B
      x475_outr_Foreach.mask := ~x475_outr_Foreach.cchain.head.output.noop & true.B
      x475_outr_Foreach.configure("x475_outr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x475_outr_Foreach.kernel()
    }
    val module = Module(new x476_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x476_outr_UnitPipe_DenseTransfer **/
