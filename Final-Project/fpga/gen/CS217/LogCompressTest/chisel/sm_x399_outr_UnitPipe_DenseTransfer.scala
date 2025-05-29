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

/** Hierarchy: x399 -> x439 **/
/** BEGIN Some(DenseTransfer) x399_outr_UnitPipe_DenseTransfer **/
class x399_outr_UnitPipe_DenseTransfer_kernel(
  list_x295_out_0: List[StandardInterface],
  list_x369: List[DecoupledIO[Bool]],
  list_x292_outDRAM: List[FixedPoint],
  list_x368: List[DecoupledIO[AppStoreData]],
  list_x367: List[DecoupledIO[AppCommandDense]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 1, isFSM = false   , latency = 0.0.toInt, myName = "x399_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x399_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x399_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x292_outDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x367 = Decoupled(new AppCommandDense(ModuleParams.getParams("x367_p").asInstanceOf[(Int,Int)] ))
      val in_x295_out_0 = Flipped(new StandardInterface(ModuleParams.getParams("x295_out_0_p").asInstanceOf[MemParams] ))
      val in_x368 = Decoupled(new AppStoreData(ModuleParams.getParams("x368_p").asInstanceOf[(Int,Int)] ))
      val in_x369 = Flipped(Decoupled(Bool()))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x292_outDRAM = {io.in_x292_outDRAM} 
    def x367 = {io.in_x367} 
    def x295_out_0 = {io.in_x295_out_0} ; io.in_x295_out_0 := DontCare
    def x368 = {io.in_x368} 
    def x369 = {io.in_x369} 
  }
  def connectWires0(module: x399_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x292_outDRAM <> x292_outDRAM
    module.io.in_x367 <> x367
    x295_out_0.connectLedger(module.io.in_x295_out_0)
    module.io.in_x368 <> x368
    module.io.in_x369 <> x369
  }
  val x295_out_0 = list_x295_out_0(0)
  val x369 = list_x369(0)
  val x292_outDRAM = list_x292_outDRAM(0)
  val x368 = list_x368(0)
  val x367 = list_x367(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x399_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x399_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x399_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x399_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      val iters_x399_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      cycles_x399_outr_UnitPipe_DenseTransfer.io.enable := io.sigsIn.baseEn
      iters_x399_outr_UnitPipe_DenseTransfer.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X399_instrctr, cycles_x399_outr_UnitPipe_DenseTransfer.io.count, iters_x399_outr_UnitPipe_DenseTransfer.io.count, 0.U, 0.U)
      val x398_outr_UnitPipe = new x398_outr_UnitPipe_kernel(List(x295_out_0), List(x369), List(x292_outDRAM), List(x368), List(x367) ,  Some(me), List(), 0, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x398_outr_UnitPipe.sm.io.ctrDone := risingEdge(x398_outr_UnitPipe.sm.io.ctrInc)
      x398_outr_UnitPipe.backpressure := true.B | x398_outr_UnitPipe.sm.io.doneLatch
      x398_outr_UnitPipe.forwardpressure := (true.B) && (true.B) | x398_outr_UnitPipe.sm.io.doneLatch
      x398_outr_UnitPipe.sm.io.enableOut.zip(x398_outr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x398_outr_UnitPipe.sm.io.break := false.B
      x398_outr_UnitPipe.mask := true.B & true.B
      x398_outr_UnitPipe.configure("x398_outr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x398_outr_UnitPipe.kernel()
    }
    val module = Module(new x399_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x399_outr_UnitPipe_DenseTransfer **/
