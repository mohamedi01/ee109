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

/** Hierarchy: x443 -> x444 -> x481 **/
/** BEGIN None x443_outr_UnitPipe **/
class x443_outr_UnitPipe_kernel(
  list_x414: List[DecoupledIO[Bool]],
  list_x323_outDRAM: List[FixedPoint],
  list_x412: List[DecoupledIO[AppCommandDense]],
  list_x328_outSRAM_0: List[StandardInterface],
  list_x413: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x443_outr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x443_outr_UnitPipe_iiCtr"))
  
  abstract class x443_outr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x328_outSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x328_outSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x412 = Decoupled(new AppCommandDense(ModuleParams.getParams("x412_p").asInstanceOf[(Int,Int)] ))
      val in_x413 = Decoupled(new AppStoreData(ModuleParams.getParams("x413_p").asInstanceOf[(Int,Int)] ))
      val in_x323_outDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x414 = Flipped(Decoupled(Bool()))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x328_outSRAM_0 = {io.in_x328_outSRAM_0} ; io.in_x328_outSRAM_0 := DontCare
    def x412 = {io.in_x412} 
    def x413 = {io.in_x413} 
    def x323_outDRAM = {io.in_x323_outDRAM} 
    def x414 = {io.in_x414} 
  }
  def connectWires0(module: x443_outr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x328_outSRAM_0.connectLedger(module.io.in_x328_outSRAM_0)
    module.io.in_x412 <> x412
    module.io.in_x413 <> x413
    module.io.in_x323_outDRAM <> x323_outDRAM
    module.io.in_x414 <> x414
  }
  val x414 = list_x414(0)
  val x323_outDRAM = list_x323_outDRAM(0)
  val x412 = list_x412(0)
  val x328_outSRAM_0 = list_x328_outSRAM_0(0)
  val x413 = list_x413(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x443_outr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x443_outr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x443_outr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x443_outr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x443_outr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x443_outr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x443_outr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X443_instrctr, cycles_x443_outr_UnitPipe.io.count, iters_x443_outr_UnitPipe.io.count, 0.U, 0.U)
      val x438_outr_UnitPipe = new x438_outr_UnitPipe_kernel(List(x323_outDRAM), List(x412), List(x328_outSRAM_0), List(x413) ,  Some(me), List(), 0, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x438_outr_UnitPipe.sm.io.ctrDone := risingEdge(x438_outr_UnitPipe.sm.io.ctrInc)
      x438_outr_UnitPipe.backpressure := true.B | x438_outr_UnitPipe.sm.io.doneLatch
      x438_outr_UnitPipe.forwardpressure := (true.B) && (true.B) | x438_outr_UnitPipe.sm.io.doneLatch
      x438_outr_UnitPipe.sm.io.enableOut.zip(x438_outr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x438_outr_UnitPipe.sm.io.break := false.B
      x438_outr_UnitPipe.mask := true.B & true.B
      x438_outr_UnitPipe.configure("x438_outr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x438_outr_UnitPipe.kernel()
      val x442_inr_UnitPipe = new x442_inr_UnitPipe_kernel(List(x414) ,  Some(me), List(), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x442_inr_UnitPipe.sm.io.ctrDone := risingEdge(x442_inr_UnitPipe.sm.io.ctrInc)
      x442_inr_UnitPipe.backpressure := true.B | x442_inr_UnitPipe.sm.io.doneLatch
      x442_inr_UnitPipe.forwardpressure := (x414.valid) && (true.B) | x442_inr_UnitPipe.sm.io.doneLatch
      x442_inr_UnitPipe.sm.io.enableOut.zip(x442_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x442_inr_UnitPipe.sm.io.break := false.B
      x442_inr_UnitPipe.mask := true.B & true.B
      x442_inr_UnitPipe.configure("x442_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x442_inr_UnitPipe.kernel()
    }
    val module = Module(new x443_outr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x443_outr_UnitPipe **/
