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

/** Hierarchy: x1522 -> x1637 -> x1638 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1522_outr_Foreach **/
class x1522_outr_Foreach_kernel(
  list_b925: List[Bool],
  list_b1163: List[FixedPoint],
  list_x934_tileA_sram_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 1, isFSM = false   , latency = 0.0.toInt, myName = "x1522_outr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1522_outr_Foreach_iiCtr"))
  
  abstract class x1522_outr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x934_tileA_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x934_tileA_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b925 = Input(Bool())
      val in_b1163 = Input(new FixedPoint(true, 32, 0))
      val in_x916_reg = Flipped(new NBufInterface(ModuleParams.getParams("x916_reg_p").asInstanceOf[NBufParams] ))
      val in_x1017_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1006 = Input(Bool())
      val in_b1167 = Input(Bool())
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
    def b1163 = {io.in_b1163} 
    def x916_reg = {io.in_x916_reg} ; io.in_x916_reg := DontCare
    def x1017_tileB_sram_0 = {io.in_x1017_tileB_sram_0} ; io.in_x1017_tileB_sram_0 := DontCare
    def b1006 = {io.in_b1006} 
    def b1167 = {io.in_b1167} 
    def x1018_tileC_sram_0 = {io.in_x1018_tileC_sram_0} ; io.in_x1018_tileC_sram_0 := DontCare
    def b914 = {io.in_b914} 
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
  }
  def connectWires0(module: x1522_outr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x934_tileA_sram_0.connectLedger(module.io.in_x934_tileA_sram_0)
    module.io.in_b925 <> b925
    module.io.in_b1163 <> b1163
    x916_reg.connectLedger(module.io.in_x916_reg)
    x1017_tileB_sram_0.connectLedger(module.io.in_x1017_tileB_sram_0)
    module.io.in_b1006 <> b1006
    module.io.in_b1167 <> b1167
    x1018_tileC_sram_0.connectLedger(module.io.in_x1018_tileC_sram_0)
    module.io.in_b914 <> b914
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
  }
  val b925 = list_b925(0)
  val b1006 = list_b925(1)
  val b1167 = list_b925(2)
  val b914 = list_b925(3)
  val b1163 = list_b1163(0)
  val x934_tileA_sram_0 = list_x934_tileA_sram_0(0)
  val x916_reg = list_x934_tileA_sram_0(1)
  val x1017_tileB_sram_0 = list_x934_tileA_sram_0(2)
  val x1018_tileC_sram_0 = list_x934_tileA_sram_0(3)
  val x1019_tileC_sram_1 = list_x934_tileA_sram_0(4)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1522_outr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1522_outr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1522_outr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1522_outr_Foreach = Module(new InstrumentationCounter())
      val iters_x1522_outr_Foreach = Module(new InstrumentationCounter())
      cycles_x1522_outr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1522_outr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1522_instrctr, cycles_x1522_outr_Foreach.io.count, iters_x1522_outr_Foreach.io.count, 0.U, 0.U)
      val b1409 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1409.suggestName("b1409")
      val b1410 = io.sigsIn.cchainOutputs.head.counts(1).FP(true, 32, 0); b1410.suggestName("b1410")
      val b1411 = io.sigsIn.cchainOutputs.head.counts(2).FP(true, 32, 0); b1411.suggestName("b1411")
      val b1412 = io.sigsIn.cchainOutputs.head.counts(3).FP(true, 32, 0); b1412.suggestName("b1412")
      val b1413 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1413.suggestName("b1413")
      val b1414 = ~io.sigsIn.cchainOutputs.head.oobs(1); b1414.suggestName("b1414")
      val b1415 = ~io.sigsIn.cchainOutputs.head.oobs(2); b1415.suggestName("b1415")
      val b1416 = ~io.sigsIn.cchainOutputs.head.oobs(3); b1416.suggestName("b1416")
      val x1783_rd_x916 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1783_rd_x916""")
      val x1783_rd_x916_banks = List[UInt]()
      val x1783_rd_x916_ofs = List[UInt]()
      val x1783_rd_x916_en = List[Bool](true.B)
      val x1783_rd_x916_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1783_rd_x916_shared_en")
      x1783_rd_x916.toSeq.zip(x916_reg.connectRPort(1783, x1783_rd_x916_banks, x1783_rd_x916_ofs, io.sigsIn.backpressure, x1783_rd_x916_en.map(_ && x1783_rd_x916_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      val x1421_ctr = new CtrObject(Left(Some(0)), Right(x1783_rd_x916), Left(Some(1)), 1, 32, false)
      val x1784_rd_x916 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1784_rd_x916""")
      val x1784_rd_x916_banks = List[UInt]()
      val x1784_rd_x916_ofs = List[UInt]()
      val x1784_rd_x916_en = List[Bool](true.B)
      val x1784_rd_x916_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1784_rd_x916_shared_en")
      x1784_rd_x916.toSeq.zip(x916_reg.connectRPort(1784, x1784_rd_x916_banks, x1784_rd_x916_ofs, io.sigsIn.backpressure, x1784_rd_x916_en.map(_ && x1784_rd_x916_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      val x1422_ctr = new CtrObject(Left(Some(0)), Right(x1784_rd_x916), Left(Some(1)), 1, 32, false)
      val x1785_rd_x916 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1785_rd_x916""")
      val x1785_rd_x916_banks = List[UInt]()
      val x1785_rd_x916_ofs = List[UInt]()
      val x1785_rd_x916_en = List[Bool](true.B)
      val x1785_rd_x916_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1785_rd_x916_shared_en")
      x1785_rd_x916.toSeq.zip(x916_reg.connectRPort(1785, x1785_rd_x916_banks, x1785_rd_x916_ofs, io.sigsIn.backpressure, x1785_rd_x916_en.map(_ && x1785_rd_x916_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      val x1423_ctr = new CtrObject(Left(Some(0)), Right(x1785_rd_x916), Left(Some(1)), 1, 32, false)
      val x1786_rd_x916 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1786_rd_x916""")
      val x1786_rd_x916_banks = List[UInt]()
      val x1786_rd_x916_ofs = List[UInt]()
      val x1786_rd_x916_en = List[Bool](true.B)
      val x1786_rd_x916_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1786_rd_x916_shared_en")
      x1786_rd_x916.toSeq.zip(x916_reg.connectRPort(1786, x1786_rd_x916_banks, x1786_rd_x916_ofs, io.sigsIn.backpressure, x1786_rd_x916_en.map(_ && x1786_rd_x916_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      val x1424_ctr = new CtrObject(Left(Some(0)), Right(x1786_rd_x916), Left(Some(1)), 1, 32, false)
      val x1425_ctrchain = (new CChainObject(List[CtrObject](x1421_ctr), "x1425_ctrchain")).cchain.io 
      x1425_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1425_ctrchain_p", (x1425_ctrchain.par, x1425_ctrchain.widths))
      val x1426_ctrchain = (new CChainObject(List[CtrObject](x1422_ctr), "x1426_ctrchain")).cchain.io 
      x1426_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1426_ctrchain_p", (x1426_ctrchain.par, x1426_ctrchain.widths))
      val x1427_ctrchain = (new CChainObject(List[CtrObject](x1423_ctr), "x1427_ctrchain")).cchain.io 
      x1427_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1427_ctrchain_p", (x1427_ctrchain.par, x1427_ctrchain.widths))
      val x1428_ctrchain = (new CChainObject(List[CtrObject](x1424_ctr), "x1428_ctrchain")).cchain.io 
      x1428_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1428_ctrchain_p", (x1428_ctrchain.par, x1428_ctrchain.widths))
      val x1521 = new x1521_kernel(List(b1415,b925,b1006,b1414,b1167,b1413,b914,b1416), List(b1411,b1409,b1163,b1412,b1410), List(x1426_ctrchain,x1425_ctrchain,x1428_ctrchain,x1427_ctrchain), List(x934_tileA_sram_0,x1017_tileB_sram_0,x1018_tileC_sram_0,x1019_tileC_sram_1) ,  Some(me), List(), 0, 4, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1521.sm.io.ctrDone := risingEdge(x1521.sm.io.ctrInc)
      x1521.backpressure := true.B | x1521.sm.io.doneLatch
      x1521.forwardpressure := (true.B) && (true.B) | x1521.sm.io.doneLatch
      x1521.sm.io.enableOut.zip(x1521.smEnableOuts).foreach{case (l,r) => r := l}
      x1521.sm.io.break := false.B
      x1521.mask := true.B & b1167 & b1006 & b925 & b914
      x1521.configure("x1521", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1521.kernel()
    }
    val module = Module(new x1522_outr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1522_outr_Foreach **/
