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

/** Hierarchy: x1247 -> x1248 -> x1335 -> x1250 -> x1251 -> x810 **/
/** BEGIN None x1247_outr_Foreach **/
class x1247_outr_Foreach_kernel(
  list_b924: List[FixedPoint],
  list_x1189: List[DecoupledIO[AppCommandDense]],
  list_x1190: List[DecoupledIO[AppStoreData]],
  list_x1191: List[DecoupledIO[Bool]],
  list_x1018_tileC_sram_1: List[NBufInterface],
  list_x879_N: List[UInt],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x1247_outr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1247_outr_Foreach_iiCtr"))
  
  abstract class x1247_outr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x1190 = Decoupled(new AppStoreData(ModuleParams.getParams("x1190_p").asInstanceOf[(Int,Int)] ))
      val in_x1191 = Flipped(Decoupled(Bool()))
      val in_b924 = Input(new FixedPoint(true, 32, 0))
      val in_x879_N = Input(UInt(64.W))
      val in_x906_c = Input(new FixedPoint(true, 64, 0))
      val in_x1018_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_x1189 = Decoupled(new AppCommandDense(ModuleParams.getParams("x1189_p").asInstanceOf[(Int,Int)] ))
      val in_x1010_reg = Flipped(new NBufInterface(ModuleParams.getParams("x1010_reg_p").asInstanceOf[NBufParams] ))
      val in_b1004 = Input(new FixedPoint(true, 32, 0))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x1190 = {io.in_x1190} 
    def x1191 = {io.in_x1191} 
    def b924 = {io.in_b924} 
    def x879_N = {io.in_x879_N} 
    def x906_c = {io.in_x906_c} 
    def x1018_tileC_sram_1 = {io.in_x1018_tileC_sram_1} ; io.in_x1018_tileC_sram_1 := DontCare
    def x1189 = {io.in_x1189} 
    def x1010_reg = {io.in_x1010_reg} ; io.in_x1010_reg := DontCare
    def b1004 = {io.in_b1004} 
  }
  def connectWires0(module: x1247_outr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x1190 <> x1190
    module.io.in_x1191 <> x1191
    module.io.in_b924 <> b924
    module.io.in_x879_N <> x879_N
    module.io.in_x906_c <> x906_c
    x1018_tileC_sram_1.connectLedger(module.io.in_x1018_tileC_sram_1)
    module.io.in_x1189 <> x1189
    x1010_reg.connectLedger(module.io.in_x1010_reg)
    module.io.in_b1004 <> b1004
  }
  val b924 = list_b924(0)
  val x906_c = list_b924(1)
  val b1004 = list_b924(2)
  val x1189 = list_x1189(0)
  val x1190 = list_x1190(0)
  val x1191 = list_x1191(0)
  val x1018_tileC_sram_1 = list_x1018_tileC_sram_1(0)
  val x1010_reg = list_x1018_tileC_sram_1(1)
  val x879_N = list_x879_N(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1247_outr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1247_outr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1247_outr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1247_outr_Foreach = Module(new InstrumentationCounter())
      val iters_x1247_outr_Foreach = Module(new InstrumentationCounter())
      cycles_x1247_outr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1247_outr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1247_instrctr, cycles_x1247_outr_Foreach.io.count, iters_x1247_outr_Foreach.io.count, 0.U, 0.U)
      val b1194 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1194.suggestName("b1194")
      val b1195 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1195.suggestName("b1195")
      val x1242_outr_UnitPipe = new x1242_outr_UnitPipe_kernel(List(x1189), List(x1190), List(x1018_tileC_sram_1,x1010_reg), List(x879_N), List(b924,x906_c,b1194,b1004), List(b1195) ,  Some(me), List(), 0, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1242_outr_UnitPipe.sm.io.ctrDone := risingEdge(x1242_outr_UnitPipe.sm.io.ctrInc)
      x1242_outr_UnitPipe.backpressure := true.B | x1242_outr_UnitPipe.sm.io.doneLatch
      x1242_outr_UnitPipe.forwardpressure := (true.B) && (true.B) | x1242_outr_UnitPipe.sm.io.doneLatch
      x1242_outr_UnitPipe.sm.io.enableOut.zip(x1242_outr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x1242_outr_UnitPipe.sm.io.break := false.B
      x1242_outr_UnitPipe.mask := true.B & b1195
      x1242_outr_UnitPipe.configure("x1242_outr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1242_outr_UnitPipe.kernel()
      val x1246_inr_UnitPipe = new x1246_inr_UnitPipe_kernel(List(b1195), List(x1191) ,  Some(me), List(), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1246_inr_UnitPipe.sm.io.ctrDone := risingEdge(x1246_inr_UnitPipe.sm.io.ctrInc)
      x1246_inr_UnitPipe.backpressure := true.B | x1246_inr_UnitPipe.sm.io.doneLatch
      x1246_inr_UnitPipe.forwardpressure := (x1191.valid) && (true.B) | x1246_inr_UnitPipe.sm.io.doneLatch
      x1246_inr_UnitPipe.sm.io.enableOut.zip(x1246_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x1246_inr_UnitPipe.sm.io.break := false.B
      x1246_inr_UnitPipe.mask := true.B & b1195
      x1246_inr_UnitPipe.configure("x1246_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1246_inr_UnitPipe.kernel()
    }
    val module = Module(new x1247_outr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1247_outr_Foreach **/
