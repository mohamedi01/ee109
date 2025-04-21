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

/** Hierarchy: x1408 -> x1637 -> x1638 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1408_outr_Foreach **/
class x1408_outr_Foreach_kernel(
  list_b925: List[Bool],
  list_b1162: List[FixedPoint],
  list_x934_tileA_sram_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 1, isFSM = false   , latency = 0.0.toInt, myName = "x1408_outr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1408_outr_Foreach_iiCtr"))
  
  abstract class x1408_outr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x934_tileA_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x934_tileA_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b925 = Input(Bool())
      val in_x916_reg = Flipped(new NBufInterface(ModuleParams.getParams("x916_reg_p").asInstanceOf[NBufParams] ))
      val in_x1017_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1006 = Input(Bool())
      val in_b1162 = Input(new FixedPoint(true, 32, 0))
      val in_b1166 = Input(Bool())
      val in_x1018_tileC_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b914 = Input(Bool())
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
    def b1162 = {io.in_b1162} 
    def b1166 = {io.in_b1166} 
    def x1018_tileC_sram_0 = {io.in_x1018_tileC_sram_0} ; io.in_x1018_tileC_sram_0 := DontCare
    def b914 = {io.in_b914} 
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
  }
  def connectWires0(module: x1408_outr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x934_tileA_sram_0.connectLedger(module.io.in_x934_tileA_sram_0)
    module.io.in_b925 <> b925
    x916_reg.connectLedger(module.io.in_x916_reg)
    x1017_tileB_sram_0.connectLedger(module.io.in_x1017_tileB_sram_0)
    module.io.in_b1006 <> b1006
    module.io.in_b1162 <> b1162
    module.io.in_b1166 <> b1166
    x1018_tileC_sram_0.connectLedger(module.io.in_x1018_tileC_sram_0)
    module.io.in_b914 <> b914
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
  }
  val b925 = list_b925(0)
  val b1006 = list_b925(1)
  val b1166 = list_b925(2)
  val b914 = list_b925(3)
  val b1162 = list_b1162(0)
  val x934_tileA_sram_0 = list_x934_tileA_sram_0(0)
  val x916_reg = list_x934_tileA_sram_0(1)
  val x1017_tileB_sram_0 = list_x934_tileA_sram_0(2)
  val x1018_tileC_sram_0 = list_x934_tileA_sram_0(3)
  val x1019_tileC_sram_1 = list_x934_tileA_sram_0(4)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1408_outr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1408_outr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1408_outr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1408_outr_Foreach = Module(new InstrumentationCounter())
      val iters_x1408_outr_Foreach = Module(new InstrumentationCounter())
      cycles_x1408_outr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1408_outr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1408_instrctr, cycles_x1408_outr_Foreach.io.count, iters_x1408_outr_Foreach.io.count, 0.U, 0.U)
      val b1295 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1295.suggestName("b1295")
      val b1296 = io.sigsIn.cchainOutputs.head.counts(1).FP(true, 32, 0); b1296.suggestName("b1296")
      val b1297 = io.sigsIn.cchainOutputs.head.counts(2).FP(true, 32, 0); b1297.suggestName("b1297")
      val b1298 = io.sigsIn.cchainOutputs.head.counts(3).FP(true, 32, 0); b1298.suggestName("b1298")
      val b1299 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1299.suggestName("b1299")
      val b1300 = ~io.sigsIn.cchainOutputs.head.oobs(1); b1300.suggestName("b1300")
      val b1301 = ~io.sigsIn.cchainOutputs.head.oobs(2); b1301.suggestName("b1301")
      val b1302 = ~io.sigsIn.cchainOutputs.head.oobs(3); b1302.suggestName("b1302")
      val x1779_rd_x916 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1779_rd_x916""")
      val x1779_rd_x916_banks = List[UInt]()
      val x1779_rd_x916_ofs = List[UInt]()
      val x1779_rd_x916_en = List[Bool](true.B)
      val x1779_rd_x916_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1779_rd_x916_shared_en")
      x1779_rd_x916.toSeq.zip(x916_reg.connectRPort(1779, x1779_rd_x916_banks, x1779_rd_x916_ofs, io.sigsIn.backpressure, x1779_rd_x916_en.map(_ && x1779_rd_x916_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      val x1307_ctr = new CtrObject(Left(Some(0)), Right(x1779_rd_x916), Left(Some(1)), 1, 32, false)
      val x1780_rd_x916 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1780_rd_x916""")
      val x1780_rd_x916_banks = List[UInt]()
      val x1780_rd_x916_ofs = List[UInt]()
      val x1780_rd_x916_en = List[Bool](true.B)
      val x1780_rd_x916_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1780_rd_x916_shared_en")
      x1780_rd_x916.toSeq.zip(x916_reg.connectRPort(1780, x1780_rd_x916_banks, x1780_rd_x916_ofs, io.sigsIn.backpressure, x1780_rd_x916_en.map(_ && x1780_rd_x916_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      val x1308_ctr = new CtrObject(Left(Some(0)), Right(x1780_rd_x916), Left(Some(1)), 1, 32, false)
      val x1781_rd_x916 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1781_rd_x916""")
      val x1781_rd_x916_banks = List[UInt]()
      val x1781_rd_x916_ofs = List[UInt]()
      val x1781_rd_x916_en = List[Bool](true.B)
      val x1781_rd_x916_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1781_rd_x916_shared_en")
      x1781_rd_x916.toSeq.zip(x916_reg.connectRPort(1781, x1781_rd_x916_banks, x1781_rd_x916_ofs, io.sigsIn.backpressure, x1781_rd_x916_en.map(_ && x1781_rd_x916_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      val x1309_ctr = new CtrObject(Left(Some(0)), Right(x1781_rd_x916), Left(Some(1)), 1, 32, false)
      val x1782_rd_x916 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1782_rd_x916""")
      val x1782_rd_x916_banks = List[UInt]()
      val x1782_rd_x916_ofs = List[UInt]()
      val x1782_rd_x916_en = List[Bool](true.B)
      val x1782_rd_x916_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1782_rd_x916_shared_en")
      x1782_rd_x916.toSeq.zip(x916_reg.connectRPort(1782, x1782_rd_x916_banks, x1782_rd_x916_ofs, io.sigsIn.backpressure, x1782_rd_x916_en.map(_ && x1782_rd_x916_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      val x1310_ctr = new CtrObject(Left(Some(0)), Right(x1782_rd_x916), Left(Some(1)), 1, 32, false)
      val x1311_ctrchain = (new CChainObject(List[CtrObject](x1307_ctr), "x1311_ctrchain")).cchain.io 
      x1311_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1311_ctrchain_p", (x1311_ctrchain.par, x1311_ctrchain.widths))
      val x1312_ctrchain = (new CChainObject(List[CtrObject](x1308_ctr), "x1312_ctrchain")).cchain.io 
      x1312_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1312_ctrchain_p", (x1312_ctrchain.par, x1312_ctrchain.widths))
      val x1313_ctrchain = (new CChainObject(List[CtrObject](x1309_ctr), "x1313_ctrchain")).cchain.io 
      x1313_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1313_ctrchain_p", (x1313_ctrchain.par, x1313_ctrchain.widths))
      val x1314_ctrchain = (new CChainObject(List[CtrObject](x1310_ctr), "x1314_ctrchain")).cchain.io 
      x1314_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1314_ctrchain_p", (x1314_ctrchain.par, x1314_ctrchain.widths))
      val x1407 = new x1407_kernel(List(b1302,b925,b1006,b1301,b1166,b1300,b914,b1299), List(b1295,b1298,b1296,b1297,b1162), List(x1313_ctrchain,x1311_ctrchain,x1312_ctrchain,x1314_ctrchain), List(x934_tileA_sram_0,x1017_tileB_sram_0,x1018_tileC_sram_0,x1019_tileC_sram_1) ,  Some(me), List(), 0, 4, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1407.sm.io.ctrDone := risingEdge(x1407.sm.io.ctrInc)
      x1407.backpressure := true.B | x1407.sm.io.doneLatch
      x1407.forwardpressure := (true.B) && (true.B) | x1407.sm.io.doneLatch
      x1407.sm.io.enableOut.zip(x1407.smEnableOuts).foreach{case (l,r) => r := l}
      x1407.sm.io.break := false.B
      x1407.mask := true.B & b1166 & b1006 & b925 & b914
      x1407.configure("x1407", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1407.kernel()
    }
    val module = Module(new x1408_outr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1408_outr_Foreach **/
