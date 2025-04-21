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

/** Hierarchy: x1700 -> x1701 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1700_outr_Foreach **/
class x1700_outr_Foreach_kernel(
  list_x1641: List[DecoupledIO[Bool]],
  list_x1640: List[DecoupledIO[AppStoreData]],
  list_x879_N: List[UInt],
  list_b1005: List[FixedPoint],
  list_x1639: List[DecoupledIO[AppCommandDense]],
  list_x1011_reg: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x1700_outr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1700_outr_Foreach_iiCtr"))
  
  abstract class x1700_outr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b1005 = Input(new FixedPoint(true, 32, 0))
      val in_x1640 = Decoupled(new AppStoreData(ModuleParams.getParams("x1640_p").asInstanceOf[(Int,Int)] ))
      val in_x1641 = Flipped(Decoupled(Bool()))
      val in_b924 = Input(new FixedPoint(true, 32, 0))
      val in_x879_N = Input(UInt(64.W))
      val in_x906_c = Input(new FixedPoint(true, 64, 0))
      val in_x1011_reg = Flipped(new NBufInterface(ModuleParams.getParams("x1011_reg_p").asInstanceOf[NBufParams] ))
      val in_x1639 = Decoupled(new AppCommandDense(ModuleParams.getParams("x1639_p").asInstanceOf[(Int,Int)] ))
      val in_x1019_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1019_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def b1005 = {io.in_b1005} 
    def x1640 = {io.in_x1640} 
    def x1641 = {io.in_x1641} 
    def b924 = {io.in_b924} 
    def x879_N = {io.in_x879_N} 
    def x906_c = {io.in_x906_c} 
    def x1011_reg = {io.in_x1011_reg} ; io.in_x1011_reg := DontCare
    def x1639 = {io.in_x1639} 
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
  }
  def connectWires0(module: x1700_outr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b1005 <> b1005
    module.io.in_x1640 <> x1640
    module.io.in_x1641 <> x1641
    module.io.in_b924 <> b924
    module.io.in_x879_N <> x879_N
    module.io.in_x906_c <> x906_c
    x1011_reg.connectLedger(module.io.in_x1011_reg)
    module.io.in_x1639 <> x1639
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
  }
  val x1641 = list_x1641(0)
  val x1640 = list_x1640(0)
  val x879_N = list_x879_N(0)
  val b1005 = list_b1005(0)
  val b924 = list_b1005(1)
  val x906_c = list_b1005(2)
  val x1639 = list_x1639(0)
  val x1011_reg = list_x1011_reg(0)
  val x1019_tileC_sram_1 = list_x1011_reg(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1700_outr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1700_outr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1700_outr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1700_outr_Foreach = Module(new InstrumentationCounter())
      val iters_x1700_outr_Foreach = Module(new InstrumentationCounter())
      cycles_x1700_outr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1700_outr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1700_instrctr, cycles_x1700_outr_Foreach.io.count, iters_x1700_outr_Foreach.io.count, 0.U, 0.U)
      val b1644 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1644.suggestName("b1644")
      val b1645 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1645.suggestName("b1645")
      val x1695_outr_UnitPipe = new x1695_outr_UnitPipe_kernel(List(x1640), List(b1645), List(x879_N), List(x1639), List(b1005,b924,b1644,x906_c), List(x1011_reg,x1019_tileC_sram_1) ,  Some(me), List(), 0, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1695_outr_UnitPipe.sm.io.ctrDone := risingEdge(x1695_outr_UnitPipe.sm.io.ctrInc)
      x1695_outr_UnitPipe.backpressure := true.B | x1695_outr_UnitPipe.sm.io.doneLatch
      x1695_outr_UnitPipe.forwardpressure := (true.B) && (true.B) | x1695_outr_UnitPipe.sm.io.doneLatch
      x1695_outr_UnitPipe.sm.io.enableOut.zip(x1695_outr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x1695_outr_UnitPipe.sm.io.break := false.B
      x1695_outr_UnitPipe.mask := true.B & b1645
      x1695_outr_UnitPipe.configure("x1695_outr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1695_outr_UnitPipe.kernel()
      val x1699_inr_UnitPipe = new x1699_inr_UnitPipe_kernel(List(b1645), List(x1641) ,  Some(me), List(), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1699_inr_UnitPipe.sm.io.ctrDone := risingEdge(x1699_inr_UnitPipe.sm.io.ctrInc)
      x1699_inr_UnitPipe.backpressure := true.B | x1699_inr_UnitPipe.sm.io.doneLatch
      x1699_inr_UnitPipe.forwardpressure := (x1641.valid) && (true.B) | x1699_inr_UnitPipe.sm.io.doneLatch
      x1699_inr_UnitPipe.sm.io.enableOut.zip(x1699_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x1699_inr_UnitPipe.sm.io.break := false.B
      x1699_inr_UnitPipe.mask := true.B & b1645
      x1699_inr_UnitPipe.configure("x1699_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1699_inr_UnitPipe.kernel()
    }
    val module = Module(new x1700_outr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1700_outr_Foreach **/
