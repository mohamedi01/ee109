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

/** Hierarchy: x1294 -> x1637 -> x1638 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1294_outr_Foreach **/
class x1294_outr_Foreach_kernel(
  list_b925: List[Bool],
  list_b1161: List[FixedPoint],
  list_x934_tileA_sram_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 1, isFSM = false   , latency = 0.0.toInt, myName = "x1294_outr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1294_outr_Foreach_iiCtr"))
  
  abstract class x1294_outr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x934_tileA_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x934_tileA_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b925 = Input(Bool())
      val in_x916_reg = Flipped(new NBufInterface(ModuleParams.getParams("x916_reg_p").asInstanceOf[NBufParams] ))
      val in_x1017_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1006 = Input(Bool())
      val in_x1018_tileC_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b914 = Input(Bool())
      val in_b1161 = Input(new FixedPoint(true, 32, 0))
      val in_b1165 = Input(Bool())
      val in_x1019_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1019_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(4), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x934_tileA_sram_0 = {io.in_x934_tileA_sram_0} ; io.in_x934_tileA_sram_0 := DontCare
    def b925 = {io.in_b925} 
    def x916_reg = {io.in_x916_reg} ; io.in_x916_reg := DontCare
    def x1017_tileB_sram_0 = {io.in_x1017_tileB_sram_0} ; io.in_x1017_tileB_sram_0 := DontCare
    def b1006 = {io.in_b1006} 
    def x1018_tileC_sram_0 = {io.in_x1018_tileC_sram_0} ; io.in_x1018_tileC_sram_0 := DontCare
    def b914 = {io.in_b914} 
    def b1161 = {io.in_b1161} 
    def b1165 = {io.in_b1165} 
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
  }
  def connectWires0(module: x1294_outr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x934_tileA_sram_0.connectLedger(module.io.in_x934_tileA_sram_0)
    module.io.in_b925 <> b925
    x916_reg.connectLedger(module.io.in_x916_reg)
    x1017_tileB_sram_0.connectLedger(module.io.in_x1017_tileB_sram_0)
    module.io.in_b1006 <> b1006
    x1018_tileC_sram_0.connectLedger(module.io.in_x1018_tileC_sram_0)
    module.io.in_b914 <> b914
    module.io.in_b1161 <> b1161
    module.io.in_b1165 <> b1165
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
  }
  val b925 = list_b925(0)
  val b1006 = list_b925(1)
  val b914 = list_b925(2)
  val b1165 = list_b925(3)
  val b1161 = list_b1161(0)
  val x934_tileA_sram_0 = list_x934_tileA_sram_0(0)
  val x916_reg = list_x934_tileA_sram_0(1)
  val x1017_tileB_sram_0 = list_x934_tileA_sram_0(2)
  val x1018_tileC_sram_0 = list_x934_tileA_sram_0(3)
  val x1019_tileC_sram_1 = list_x934_tileA_sram_0(4)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1294_outr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1294_outr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1294_outr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1294_outr_Foreach = Module(new InstrumentationCounter())
      val iters_x1294_outr_Foreach = Module(new InstrumentationCounter())
      cycles_x1294_outr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1294_outr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1294_instrctr, cycles_x1294_outr_Foreach.io.count, iters_x1294_outr_Foreach.io.count, 0.U, 0.U)
      val b1181 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1181.suggestName("b1181")
      val b1182 = io.sigsIn.cchainOutputs.head.counts(1).FP(true, 32, 0); b1182.suggestName("b1182")
      val b1183 = io.sigsIn.cchainOutputs.head.counts(2).FP(true, 32, 0); b1183.suggestName("b1183")
      val b1184 = io.sigsIn.cchainOutputs.head.counts(3).FP(true, 32, 0); b1184.suggestName("b1184")
      val b1185 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1185.suggestName("b1185")
      val b1186 = ~io.sigsIn.cchainOutputs.head.oobs(1); b1186.suggestName("b1186")
      val b1187 = ~io.sigsIn.cchainOutputs.head.oobs(2); b1187.suggestName("b1187")
      val b1188 = ~io.sigsIn.cchainOutputs.head.oobs(3); b1188.suggestName("b1188")
      val x1775_rd_x916 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1775_rd_x916""")
      val x1775_rd_x916_banks = List[UInt]()
      val x1775_rd_x916_ofs = List[UInt]()
      val x1775_rd_x916_en = List[Bool](true.B)
      val x1775_rd_x916_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1775_rd_x916_shared_en")
      x1775_rd_x916.toSeq.zip(x916_reg.connectRPort(1775, x1775_rd_x916_banks, x1775_rd_x916_ofs, io.sigsIn.backpressure, x1775_rd_x916_en.map(_ && x1775_rd_x916_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      val x1193_ctr = new CtrObject(Left(Some(0)), Right(x1775_rd_x916), Left(Some(1)), 1, 32, false)
      val x1776_rd_x916 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1776_rd_x916""")
      val x1776_rd_x916_banks = List[UInt]()
      val x1776_rd_x916_ofs = List[UInt]()
      val x1776_rd_x916_en = List[Bool](true.B)
      val x1776_rd_x916_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1776_rd_x916_shared_en")
      x1776_rd_x916.toSeq.zip(x916_reg.connectRPort(1776, x1776_rd_x916_banks, x1776_rd_x916_ofs, io.sigsIn.backpressure, x1776_rd_x916_en.map(_ && x1776_rd_x916_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      val x1194_ctr = new CtrObject(Left(Some(0)), Right(x1776_rd_x916), Left(Some(1)), 1, 32, false)
      val x1777_rd_x916 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1777_rd_x916""")
      val x1777_rd_x916_banks = List[UInt]()
      val x1777_rd_x916_ofs = List[UInt]()
      val x1777_rd_x916_en = List[Bool](true.B)
      val x1777_rd_x916_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1777_rd_x916_shared_en")
      x1777_rd_x916.toSeq.zip(x916_reg.connectRPort(1777, x1777_rd_x916_banks, x1777_rd_x916_ofs, io.sigsIn.backpressure, x1777_rd_x916_en.map(_ && x1777_rd_x916_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      val x1195_ctr = new CtrObject(Left(Some(0)), Right(x1777_rd_x916), Left(Some(1)), 1, 32, false)
      val x1778_rd_x916 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1778_rd_x916""")
      val x1778_rd_x916_banks = List[UInt]()
      val x1778_rd_x916_ofs = List[UInt]()
      val x1778_rd_x916_en = List[Bool](true.B)
      val x1778_rd_x916_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1778_rd_x916_shared_en")
      x1778_rd_x916.toSeq.zip(x916_reg.connectRPort(1778, x1778_rd_x916_banks, x1778_rd_x916_ofs, io.sigsIn.backpressure, x1778_rd_x916_en.map(_ && x1778_rd_x916_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      val x1196_ctr = new CtrObject(Left(Some(0)), Right(x1778_rd_x916), Left(Some(1)), 1, 32, false)
      val x1197_ctrchain = (new CChainObject(List[CtrObject](x1193_ctr), "x1197_ctrchain")).cchain.io 
      x1197_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1197_ctrchain_p", (x1197_ctrchain.par, x1197_ctrchain.widths))
      val x1198_ctrchain = (new CChainObject(List[CtrObject](x1194_ctr), "x1198_ctrchain")).cchain.io 
      x1198_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1198_ctrchain_p", (x1198_ctrchain.par, x1198_ctrchain.widths))
      val x1199_ctrchain = (new CChainObject(List[CtrObject](x1195_ctr), "x1199_ctrchain")).cchain.io 
      x1199_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1199_ctrchain_p", (x1199_ctrchain.par, x1199_ctrchain.widths))
      val x1200_ctrchain = (new CChainObject(List[CtrObject](x1196_ctr), "x1200_ctrchain")).cchain.io 
      x1200_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1200_ctrchain_p", (x1200_ctrchain.par, x1200_ctrchain.widths))
      val x1293 = new x1293_kernel(List(b925,b1006,b1187,b1185,b1188,b1186,b914,b1165), List(b1183,b1182,b1181,b1161,b1184), List(x1200_ctrchain,x1199_ctrchain,x1198_ctrchain,x1197_ctrchain), List(x934_tileA_sram_0,x1017_tileB_sram_0,x1018_tileC_sram_0,x1019_tileC_sram_1) ,  Some(me), List(), 0, 4, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1293.sm.io.ctrDone := risingEdge(x1293.sm.io.ctrInc)
      x1293.backpressure := true.B | x1293.sm.io.doneLatch
      x1293.forwardpressure := (true.B) && (true.B) | x1293.sm.io.doneLatch
      x1293.sm.io.enableOut.zip(x1293.smEnableOuts).foreach{case (l,r) => r := l}
      x1293.sm.io.break := false.B
      x1293.mask := true.B & b1165 & b1006 & b925 & b914
      x1293.configure("x1293", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1293.kernel()
    }
    val module = Module(new x1294_outr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1294_outr_Foreach **/
