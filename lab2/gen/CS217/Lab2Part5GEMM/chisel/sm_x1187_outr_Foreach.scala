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

/** Hierarchy: x1187 -> x1188 -> x1335 -> x1250 -> x1251 -> x810 **/
/** BEGIN None x1187_outr_Foreach **/
class x1187_outr_Foreach_kernel(
  list_b1005: List[Bool],
  list_b1156: List[FixedPoint],
  list_x934_tileA_sram_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 1, isFSM = false   , latency = 0.0.toInt, myName = "x1187_outr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1187_outr_Foreach_iiCtr"))
  
  abstract class x1187_outr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b1005 = Input(Bool())
      val in_x934_tileA_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x934_tileA_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b925 = Input(Bool())
      val in_x916_reg = Flipped(new NBufInterface(ModuleParams.getParams("x916_reg_p").asInstanceOf[NBufParams] ))
      val in_x1017_tileC_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileC_sram_0_p").asInstanceOf[NBufParams] ))
      val in_x1016_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1016_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_x1018_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_b1156 = Input(new FixedPoint(true, 32, 0))
      val in_b1157 = Input(Bool())
      val in_b914 = Input(Bool())
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def b1005 = {io.in_b1005} 
    def x934_tileA_sram_0 = {io.in_x934_tileA_sram_0} ; io.in_x934_tileA_sram_0 := DontCare
    def b925 = {io.in_b925} 
    def x916_reg = {io.in_x916_reg} ; io.in_x916_reg := DontCare
    def x1017_tileC_sram_0 = {io.in_x1017_tileC_sram_0} ; io.in_x1017_tileC_sram_0 := DontCare
    def x1016_tileB_sram_0 = {io.in_x1016_tileB_sram_0} ; io.in_x1016_tileB_sram_0 := DontCare
    def x1018_tileC_sram_1 = {io.in_x1018_tileC_sram_1} ; io.in_x1018_tileC_sram_1 := DontCare
    def b1156 = {io.in_b1156} 
    def b1157 = {io.in_b1157} 
    def b914 = {io.in_b914} 
  }
  def connectWires0(module: x1187_outr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b1005 <> b1005
    x934_tileA_sram_0.connectLedger(module.io.in_x934_tileA_sram_0)
    module.io.in_b925 <> b925
    x916_reg.connectLedger(module.io.in_x916_reg)
    x1017_tileC_sram_0.connectLedger(module.io.in_x1017_tileC_sram_0)
    x1016_tileB_sram_0.connectLedger(module.io.in_x1016_tileB_sram_0)
    x1018_tileC_sram_1.connectLedger(module.io.in_x1018_tileC_sram_1)
    module.io.in_b1156 <> b1156
    module.io.in_b1157 <> b1157
    module.io.in_b914 <> b914
  }
  val b1005 = list_b1005(0)
  val b925 = list_b1005(1)
  val b1157 = list_b1005(2)
  val b914 = list_b1005(3)
  val b1156 = list_b1156(0)
  val x934_tileA_sram_0 = list_x934_tileA_sram_0(0)
  val x916_reg = list_x934_tileA_sram_0(1)
  val x1017_tileC_sram_0 = list_x934_tileA_sram_0(2)
  val x1016_tileB_sram_0 = list_x934_tileA_sram_0(3)
  val x1018_tileC_sram_1 = list_x934_tileA_sram_0(4)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1187_outr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1187_outr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1187_outr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1187_outr_Foreach = Module(new InstrumentationCounter())
      val iters_x1187_outr_Foreach = Module(new InstrumentationCounter())
      cycles_x1187_outr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1187_outr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1187_instrctr, cycles_x1187_outr_Foreach.io.count, iters_x1187_outr_Foreach.io.count, 0.U, 0.U)
      val b1161 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1161.suggestName("b1161")
      val b1162 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1162.suggestName("b1162")
      val x1319_rd_x916 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1319_rd_x916""")
      val x1319_rd_x916_banks = List[UInt]()
      val x1319_rd_x916_ofs = List[UInt]()
      val x1319_rd_x916_en = List[Bool](true.B)
      val x1319_rd_x916_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1319_rd_x916_shared_en")
      x1319_rd_x916.toSeq.zip(x916_reg.connectRPort(1319, x1319_rd_x916_banks, x1319_rd_x916_ofs, io.sigsIn.backpressure, x1319_rd_x916_en.map(_ && x1319_rd_x916_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1164_ctr = new CtrObject(Left(Some(0)), Right(x1319_rd_x916), Left(Some(1)), 1, 32, false)
      val x1165_ctrchain = (new CChainObject(List[CtrObject](x1164_ctr), "x1165_ctrchain")).cchain.io 
      x1165_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1165_ctrchain_p", (x1165_ctrchain.par, x1165_ctrchain.widths))
      val x1186_inr_Foreach = new x1186_inr_Foreach_kernel(List(b1005,b925,b1162,b1157,b914), List(b1156,b1161), List(x934_tileA_sram_0,x1017_tileC_sram_0,x1016_tileB_sram_0,x1018_tileC_sram_1) ,  Some(me), List(x1165_ctrchain), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1186_inr_Foreach.sm.io.ctrDone := (x1186_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1186_inr_Foreach.backpressure := true.B | x1186_inr_Foreach.sm.io.doneLatch
      x1186_inr_Foreach.forwardpressure := (true.B) && (true.B) | x1186_inr_Foreach.sm.io.doneLatch
      x1186_inr_Foreach.sm.io.enableOut.zip(x1186_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1186_inr_Foreach.sm.io.break := false.B
      x1186_inr_Foreach.mask := ~x1186_inr_Foreach.cchain.head.output.noop & b1162 & b925 & b914 & b1157 & b1005
      x1186_inr_Foreach.configure("x1186_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1186_inr_Foreach.kernel()
    }
    val module = Module(new x1187_outr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1187_outr_Foreach **/
