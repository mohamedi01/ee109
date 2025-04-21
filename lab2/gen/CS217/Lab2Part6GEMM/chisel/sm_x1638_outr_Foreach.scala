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

/** Hierarchy: x1638 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1638_outr_Foreach **/
class x1638_outr_Foreach_kernel(
  list_b925: List[Bool],
  list_x934_tileA_sram_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 1, isFSM = false   , latency = 0.0.toInt, myName = "x1638_outr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1638_outr_Foreach_iiCtr"))
  
  abstract class x1638_outr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x934_tileA_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x934_tileA_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b925 = Input(Bool())
      val in_x916_reg = Flipped(new NBufInterface(ModuleParams.getParams("x916_reg_p").asInstanceOf[NBufParams] ))
      val in_x1017_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1006 = Input(Bool())
      val in_x1011_reg = Flipped(new NBufInterface(ModuleParams.getParams("x1011_reg_p").asInstanceOf[NBufParams] ))
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
    def x1011_reg = {io.in_x1011_reg} ; io.in_x1011_reg := DontCare
    def x1018_tileC_sram_0 = {io.in_x1018_tileC_sram_0} ; io.in_x1018_tileC_sram_0 := DontCare
    def b914 = {io.in_b914} 
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
  }
  def connectWires0(module: x1638_outr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x934_tileA_sram_0.connectLedger(module.io.in_x934_tileA_sram_0)
    module.io.in_b925 <> b925
    x916_reg.connectLedger(module.io.in_x916_reg)
    x1017_tileB_sram_0.connectLedger(module.io.in_x1017_tileB_sram_0)
    module.io.in_b1006 <> b1006
    x1011_reg.connectLedger(module.io.in_x1011_reg)
    x1018_tileC_sram_0.connectLedger(module.io.in_x1018_tileC_sram_0)
    module.io.in_b914 <> b914
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
  }
  val b925 = list_b925(0)
  val b1006 = list_b925(1)
  val b914 = list_b925(2)
  val x934_tileA_sram_0 = list_x934_tileA_sram_0(0)
  val x916_reg = list_x934_tileA_sram_0(1)
  val x1017_tileB_sram_0 = list_x934_tileA_sram_0(2)
  val x1011_reg = list_x934_tileA_sram_0(3)
  val x1018_tileC_sram_0 = list_x934_tileA_sram_0(4)
  val x1019_tileC_sram_1 = list_x934_tileA_sram_0(5)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1638_outr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1638_outr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1638_outr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1638_outr_Foreach = Module(new InstrumentationCounter())
      val iters_x1638_outr_Foreach = Module(new InstrumentationCounter())
      cycles_x1638_outr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1638_outr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1638_instrctr, cycles_x1638_outr_Foreach.io.count, iters_x1638_outr_Foreach.io.count, 0.U, 0.U)
      val b1161 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1161.suggestName("b1161")
      val b1162 = io.sigsIn.cchainOutputs.head.counts(1).FP(true, 32, 0); b1162.suggestName("b1162")
      val b1163 = io.sigsIn.cchainOutputs.head.counts(2).FP(true, 32, 0); b1163.suggestName("b1163")
      val b1164 = io.sigsIn.cchainOutputs.head.counts(3).FP(true, 32, 0); b1164.suggestName("b1164")
      val b1165 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1165.suggestName("b1165")
      val b1166 = ~io.sigsIn.cchainOutputs.head.oobs(1); b1166.suggestName("b1166")
      val b1167 = ~io.sigsIn.cchainOutputs.head.oobs(2); b1167.suggestName("b1167")
      val b1168 = ~io.sigsIn.cchainOutputs.head.oobs(3); b1168.suggestName("b1168")
      val x1771_rd_x1011 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1771_rd_x1011""")
      val x1771_rd_x1011_banks = List[UInt]()
      val x1771_rd_x1011_ofs = List[UInt]()
      val x1771_rd_x1011_en = List[Bool](true.B)
      val x1771_rd_x1011_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1771_rd_x1011_shared_en")
      x1771_rd_x1011.toSeq.zip(x1011_reg.connectRPort(1771, x1771_rd_x1011_banks, x1771_rd_x1011_ofs, io.sigsIn.backpressure, x1771_rd_x1011_en.map(_ && x1771_rd_x1011_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1173_ctr = new CtrObject(Left(Some(0)), Right(x1771_rd_x1011), Left(Some(1)), 4, 32, false)
      val x1772_rd_x1011 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1772_rd_x1011""")
      val x1772_rd_x1011_banks = List[UInt]()
      val x1772_rd_x1011_ofs = List[UInt]()
      val x1772_rd_x1011_en = List[Bool](true.B)
      val x1772_rd_x1011_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1772_rd_x1011_shared_en")
      x1772_rd_x1011.toSeq.zip(x1011_reg.connectRPort(1772, x1772_rd_x1011_banks, x1772_rd_x1011_ofs, io.sigsIn.backpressure, x1772_rd_x1011_en.map(_ && x1772_rd_x1011_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1174_ctr = new CtrObject(Left(Some(0)), Right(x1772_rd_x1011), Left(Some(1)), 4, 32, false)
      val x1773_rd_x1011 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1773_rd_x1011""")
      val x1773_rd_x1011_banks = List[UInt]()
      val x1773_rd_x1011_ofs = List[UInt]()
      val x1773_rd_x1011_en = List[Bool](true.B)
      val x1773_rd_x1011_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1773_rd_x1011_shared_en")
      x1773_rd_x1011.toSeq.zip(x1011_reg.connectRPort(1773, x1773_rd_x1011_banks, x1773_rd_x1011_ofs, io.sigsIn.backpressure, x1773_rd_x1011_en.map(_ && x1773_rd_x1011_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1175_ctr = new CtrObject(Left(Some(0)), Right(x1773_rd_x1011), Left(Some(1)), 4, 32, false)
      val x1774_rd_x1011 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1774_rd_x1011""")
      val x1774_rd_x1011_banks = List[UInt]()
      val x1774_rd_x1011_ofs = List[UInt]()
      val x1774_rd_x1011_en = List[Bool](true.B)
      val x1774_rd_x1011_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1774_rd_x1011_shared_en")
      x1774_rd_x1011.toSeq.zip(x1011_reg.connectRPort(1774, x1774_rd_x1011_banks, x1774_rd_x1011_ofs, io.sigsIn.backpressure, x1774_rd_x1011_en.map(_ && x1774_rd_x1011_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1176_ctr = new CtrObject(Left(Some(0)), Right(x1774_rd_x1011), Left(Some(1)), 4, 32, false)
      val x1177_ctrchain = (new CChainObject(List[CtrObject](x1173_ctr), "x1177_ctrchain")).cchain.io 
      x1177_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1177_ctrchain_p", (x1177_ctrchain.par, x1177_ctrchain.widths))
      val x1178_ctrchain = (new CChainObject(List[CtrObject](x1174_ctr), "x1178_ctrchain")).cchain.io 
      x1178_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1178_ctrchain_p", (x1178_ctrchain.par, x1178_ctrchain.widths))
      val x1179_ctrchain = (new CChainObject(List[CtrObject](x1175_ctr), "x1179_ctrchain")).cchain.io 
      x1179_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1179_ctrchain_p", (x1179_ctrchain.par, x1179_ctrchain.widths))
      val x1180_ctrchain = (new CChainObject(List[CtrObject](x1176_ctr), "x1180_ctrchain")).cchain.io 
      x1180_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1180_ctrchain_p", (x1180_ctrchain.par, x1180_ctrchain.widths))
      val x1637 = new x1637_kernel(List(b1168,b925,b1006,b1167,b1166,b914,b1165), List(b1164,b1163,b1162,b1161), List(x1179_ctrchain,x1178_ctrchain,x1177_ctrchain,x1180_ctrchain), List(x934_tileA_sram_0,x916_reg,x1017_tileB_sram_0,x1018_tileC_sram_0,x1019_tileC_sram_1) ,  Some(me), List(), 0, 4, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1637.sm.io.ctrDone := risingEdge(x1637.sm.io.ctrInc)
      x1637.backpressure := true.B | x1637.sm.io.doneLatch
      x1637.forwardpressure := (true.B) && (true.B) | x1637.sm.io.doneLatch
      x1637.sm.io.enableOut.zip(x1637.smEnableOuts).foreach{case (l,r) => r := l}
      x1637.sm.io.break := false.B
      x1637.mask := true.B & b1006 & b925 & b914
      x1637.configure("x1637", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1637.kernel()
      x1011_reg.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 2)
      x1017_tileB_sram_0.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 1)
      x1018_tileC_sram_0.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 1)
      x1019_tileC_sram_1.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 1)
    }
    val module = Module(new x1638_outr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1638_outr_Foreach **/
