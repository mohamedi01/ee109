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

/** Hierarchy: x607 -> x608 -> x709 -> x710 **/
/** BEGIN None x607_outr_Foreach **/
class x607_outr_Foreach_kernel(
  list_x554: List[DecoupledIO[AppStoreData]],
  list_x461_realDRAM: List[FixedPoint],
  list_x555: List[DecoupledIO[Bool]],
  list_x469_real2D_0: List[StandardInterface],
  list_x553: List[DecoupledIO[AppCommandDense]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x607_outr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x607_outr_Foreach_iiCtr"))
  
  abstract class x607_outr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x555 = Flipped(Decoupled(Bool()))
      val in_x461_realDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x554 = Decoupled(new AppStoreData(ModuleParams.getParams("x554_p").asInstanceOf[(Int,Int)] ))
      val in_x553 = Decoupled(new AppCommandDense(ModuleParams.getParams("x553_p").asInstanceOf[(Int,Int)] ))
      val in_x469_real2D_0 = Flipped(new StandardInterface(ModuleParams.getParams("x469_real2D_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x555 = {io.in_x555} 
    def x461_realDRAM = {io.in_x461_realDRAM} 
    def x554 = {io.in_x554} 
    def x553 = {io.in_x553} 
    def x469_real2D_0 = {io.in_x469_real2D_0} ; io.in_x469_real2D_0 := DontCare
  }
  def connectWires0(module: x607_outr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x555 <> x555
    module.io.in_x461_realDRAM <> x461_realDRAM
    module.io.in_x554 <> x554
    module.io.in_x553 <> x553
    x469_real2D_0.connectLedger(module.io.in_x469_real2D_0)
  }
  val x554 = list_x554(0)
  val x461_realDRAM = list_x461_realDRAM(0)
  val x555 = list_x555(0)
  val x469_real2D_0 = list_x469_real2D_0(0)
  val x553 = list_x553(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x607_outr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x607_outr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x607_outr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x607_outr_Foreach = Module(new InstrumentationCounter())
      val iters_x607_outr_Foreach = Module(new InstrumentationCounter())
      cycles_x607_outr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x607_outr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X607_instrctr, cycles_x607_outr_Foreach.io.count, iters_x607_outr_Foreach.io.count, 0.U, 0.U)
      val b558 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b558.suggestName("b558")
      val b559 = ~io.sigsIn.cchainOutputs.head.oobs(0); b559.suggestName("b559")
      val x602_outr_UnitPipe = new x602_outr_UnitPipe_kernel(List(b559), List(x554), List(x461_realDRAM,b558), List(x469_real2D_0), List(x553) ,  Some(me), List(), 0, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x602_outr_UnitPipe.sm.io.ctrDone := risingEdge(x602_outr_UnitPipe.sm.io.ctrInc)
      x602_outr_UnitPipe.backpressure := true.B | x602_outr_UnitPipe.sm.io.doneLatch
      x602_outr_UnitPipe.forwardpressure := (true.B) && (true.B) | x602_outr_UnitPipe.sm.io.doneLatch
      x602_outr_UnitPipe.sm.io.enableOut.zip(x602_outr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x602_outr_UnitPipe.sm.io.break := false.B
      x602_outr_UnitPipe.mask := true.B & b559
      x602_outr_UnitPipe.configure("x602_outr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x602_outr_UnitPipe.kernel()
      val x606_inr_UnitPipe = new x606_inr_UnitPipe_kernel(List(b559), List(x555) ,  Some(me), List(), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x606_inr_UnitPipe.sm.io.ctrDone := risingEdge(x606_inr_UnitPipe.sm.io.ctrInc)
      x606_inr_UnitPipe.backpressure := true.B | x606_inr_UnitPipe.sm.io.doneLatch
      x606_inr_UnitPipe.forwardpressure := (x555.valid) && (true.B) | x606_inr_UnitPipe.sm.io.doneLatch
      x606_inr_UnitPipe.sm.io.enableOut.zip(x606_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x606_inr_UnitPipe.sm.io.break := false.B
      x606_inr_UnitPipe.mask := true.B & b559
      x606_inr_UnitPipe.configure("x606_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x606_inr_UnitPipe.kernel()
    }
    val module = Module(new x607_outr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x607_outr_Foreach **/
