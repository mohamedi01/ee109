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

/** Hierarchy: x398 -> x399 -> x439 **/
/** BEGIN None x398_outr_UnitPipe **/
class x398_outr_UnitPipe_kernel(
  list_x295_out_0: List[StandardInterface],
  list_x369: List[DecoupledIO[Bool]],
  list_x292_outDRAM: List[FixedPoint],
  list_x368: List[DecoupledIO[AppStoreData]],
  list_x367: List[DecoupledIO[AppCommandDense]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x398_outr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x398_outr_UnitPipe_iiCtr"))
  
  abstract class x398_outr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x292_outDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x367 = Decoupled(new AppCommandDense(ModuleParams.getParams("x367_p").asInstanceOf[(Int,Int)] ))
      val in_x295_out_0 = Flipped(new StandardInterface(ModuleParams.getParams("x295_out_0_p").asInstanceOf[MemParams] ))
      val in_x368 = Decoupled(new AppStoreData(ModuleParams.getParams("x368_p").asInstanceOf[(Int,Int)] ))
      val in_x369 = Flipped(Decoupled(Bool()))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x292_outDRAM = {io.in_x292_outDRAM} 
    def x367 = {io.in_x367} 
    def x295_out_0 = {io.in_x295_out_0} ; io.in_x295_out_0 := DontCare
    def x368 = {io.in_x368} 
    def x369 = {io.in_x369} 
  }
  def connectWires0(module: x398_outr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
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
    Ledger.enter(this.hashCode, "x398_outr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x398_outr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x398_outr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x398_outr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x398_outr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x398_outr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x398_outr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X398_instrctr, cycles_x398_outr_UnitPipe.io.count, iters_x398_outr_UnitPipe.io.count, 0.U, 0.U)
      val x393_outr_UnitPipe = new x393_outr_UnitPipe_kernel(List(x292_outDRAM), List(x367), List(x295_out_0), List(x368) ,  Some(me), List(), 0, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x393_outr_UnitPipe.sm.io.ctrDone := risingEdge(x393_outr_UnitPipe.sm.io.ctrInc)
      x393_outr_UnitPipe.backpressure := true.B | x393_outr_UnitPipe.sm.io.doneLatch
      x393_outr_UnitPipe.forwardpressure := (true.B) && (true.B) | x393_outr_UnitPipe.sm.io.doneLatch
      x393_outr_UnitPipe.sm.io.enableOut.zip(x393_outr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x393_outr_UnitPipe.sm.io.break := false.B
      x393_outr_UnitPipe.mask := true.B & true.B
      x393_outr_UnitPipe.configure("x393_outr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x393_outr_UnitPipe.kernel()
      val x397_inr_UnitPipe = new x397_inr_UnitPipe_kernel(List(x369) ,  Some(me), List(), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x397_inr_UnitPipe.sm.io.ctrDone := risingEdge(x397_inr_UnitPipe.sm.io.ctrInc)
      x397_inr_UnitPipe.backpressure := true.B | x397_inr_UnitPipe.sm.io.doneLatch
      x397_inr_UnitPipe.forwardpressure := (x369.valid) && (true.B) | x397_inr_UnitPipe.sm.io.doneLatch
      x397_inr_UnitPipe.sm.io.enableOut.zip(x397_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x397_inr_UnitPipe.sm.io.break := false.B
      x397_inr_UnitPipe.mask := true.B & true.B
      x397_inr_UnitPipe.configure("x397_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x397_inr_UnitPipe.kernel()
    }
    val module = Module(new x398_outr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x398_outr_UnitPipe **/
