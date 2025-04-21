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

/** Hierarchy: x1636 -> x1637 -> x1638 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1636_outr_Foreach **/
class x1636_outr_Foreach_kernel(
  list_b1168: List[Bool],
  list_b1164: List[FixedPoint],
  list_x934_tileA_sram_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 1, isFSM = false   , latency = 0.0.toInt, myName = "x1636_outr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1636_outr_Foreach_iiCtr"))
  
  abstract class x1636_outr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b1168 = Input(Bool())
      val in_b1164 = Input(new FixedPoint(true, 32, 0))
      val in_x934_tileA_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x934_tileA_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b925 = Input(Bool())
      val in_x916_reg = Flipped(new NBufInterface(ModuleParams.getParams("x916_reg_p").asInstanceOf[NBufParams] ))
      val in_x1017_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1006 = Input(Bool())
      val in_x1018_tileC_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b914 = Input(Bool())
      val in_x1019_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1019_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(4), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def b1168 = {io.in_b1168} 
    def b1164 = {io.in_b1164} 
    def x934_tileA_sram_0 = {io.in_x934_tileA_sram_0} ; io.in_x934_tileA_sram_0 := DontCare
    def b925 = {io.in_b925} 
    def x916_reg = {io.in_x916_reg} ; io.in_x916_reg := DontCare
    def x1017_tileB_sram_0 = {io.in_x1017_tileB_sram_0} ; io.in_x1017_tileB_sram_0 := DontCare
    def b1006 = {io.in_b1006} 
    def x1018_tileC_sram_0 = {io.in_x1018_tileC_sram_0} ; io.in_x1018_tileC_sram_0 := DontCare
    def b914 = {io.in_b914} 
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
  }
  def connectWires0(module: x1636_outr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b1168 <> b1168
    module.io.in_b1164 <> b1164
    x934_tileA_sram_0.connectLedger(module.io.in_x934_tileA_sram_0)
    module.io.in_b925 <> b925
    x916_reg.connectLedger(module.io.in_x916_reg)
    x1017_tileB_sram_0.connectLedger(module.io.in_x1017_tileB_sram_0)
    module.io.in_b1006 <> b1006
    x1018_tileC_sram_0.connectLedger(module.io.in_x1018_tileC_sram_0)
    module.io.in_b914 <> b914
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
  }
  val b1168 = list_b1168(0)
  val b925 = list_b1168(1)
  val b1006 = list_b1168(2)
  val b914 = list_b1168(3)
  val b1164 = list_b1164(0)
  val x934_tileA_sram_0 = list_x934_tileA_sram_0(0)
  val x916_reg = list_x934_tileA_sram_0(1)
  val x1017_tileB_sram_0 = list_x934_tileA_sram_0(2)
  val x1018_tileC_sram_0 = list_x934_tileA_sram_0(3)
  val x1019_tileC_sram_1 = list_x934_tileA_sram_0(4)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1636_outr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1636_outr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1636_outr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1636_outr_Foreach = Module(new InstrumentationCounter())
      val iters_x1636_outr_Foreach = Module(new InstrumentationCounter())
      cycles_x1636_outr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1636_outr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1636_instrctr, cycles_x1636_outr_Foreach.io.count, iters_x1636_outr_Foreach.io.count, 0.U, 0.U)
      val b1523 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1523.suggestName("b1523")
      val b1524 = io.sigsIn.cchainOutputs.head.counts(1).FP(true, 32, 0); b1524.suggestName("b1524")
      val b1525 = io.sigsIn.cchainOutputs.head.counts(2).FP(true, 32, 0); b1525.suggestName("b1525")
      val b1526 = io.sigsIn.cchainOutputs.head.counts(3).FP(true, 32, 0); b1526.suggestName("b1526")
      val b1527 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1527.suggestName("b1527")
      val b1528 = ~io.sigsIn.cchainOutputs.head.oobs(1); b1528.suggestName("b1528")
      val b1529 = ~io.sigsIn.cchainOutputs.head.oobs(2); b1529.suggestName("b1529")
      val b1530 = ~io.sigsIn.cchainOutputs.head.oobs(3); b1530.suggestName("b1530")
      val x1787_rd_x916 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1787_rd_x916""")
      val x1787_rd_x916_banks = List[UInt]()
      val x1787_rd_x916_ofs = List[UInt]()
      val x1787_rd_x916_en = List[Bool](true.B)
      val x1787_rd_x916_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1787_rd_x916_shared_en")
      x1787_rd_x916.toSeq.zip(x916_reg.connectRPort(1787, x1787_rd_x916_banks, x1787_rd_x916_ofs, io.sigsIn.backpressure, x1787_rd_x916_en.map(_ && x1787_rd_x916_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      val x1535_ctr = new CtrObject(Left(Some(0)), Right(x1787_rd_x916), Left(Some(1)), 1, 32, false)
      val x1788_rd_x916 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1788_rd_x916""")
      val x1788_rd_x916_banks = List[UInt]()
      val x1788_rd_x916_ofs = List[UInt]()
      val x1788_rd_x916_en = List[Bool](true.B)
      val x1788_rd_x916_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1788_rd_x916_shared_en")
      x1788_rd_x916.toSeq.zip(x916_reg.connectRPort(1788, x1788_rd_x916_banks, x1788_rd_x916_ofs, io.sigsIn.backpressure, x1788_rd_x916_en.map(_ && x1788_rd_x916_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      val x1536_ctr = new CtrObject(Left(Some(0)), Right(x1788_rd_x916), Left(Some(1)), 1, 32, false)
      val x1789_rd_x916 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1789_rd_x916""")
      val x1789_rd_x916_banks = List[UInt]()
      val x1789_rd_x916_ofs = List[UInt]()
      val x1789_rd_x916_en = List[Bool](true.B)
      val x1789_rd_x916_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1789_rd_x916_shared_en")
      x1789_rd_x916.toSeq.zip(x916_reg.connectRPort(1789, x1789_rd_x916_banks, x1789_rd_x916_ofs, io.sigsIn.backpressure, x1789_rd_x916_en.map(_ && x1789_rd_x916_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      val x1537_ctr = new CtrObject(Left(Some(0)), Right(x1789_rd_x916), Left(Some(1)), 1, 32, false)
      val x1790_rd_x916 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1790_rd_x916""")
      val x1790_rd_x916_banks = List[UInt]()
      val x1790_rd_x916_ofs = List[UInt]()
      val x1790_rd_x916_en = List[Bool](true.B)
      val x1790_rd_x916_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1790_rd_x916_shared_en")
      x1790_rd_x916.toSeq.zip(x916_reg.connectRPort(1790, x1790_rd_x916_banks, x1790_rd_x916_ofs, io.sigsIn.backpressure, x1790_rd_x916_en.map(_ && x1790_rd_x916_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      val x1538_ctr = new CtrObject(Left(Some(0)), Right(x1790_rd_x916), Left(Some(1)), 1, 32, false)
      val x1539_ctrchain = (new CChainObject(List[CtrObject](x1535_ctr), "x1539_ctrchain")).cchain.io 
      x1539_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1539_ctrchain_p", (x1539_ctrchain.par, x1539_ctrchain.widths))
      val x1540_ctrchain = (new CChainObject(List[CtrObject](x1536_ctr), "x1540_ctrchain")).cchain.io 
      x1540_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1540_ctrchain_p", (x1540_ctrchain.par, x1540_ctrchain.widths))
      val x1541_ctrchain = (new CChainObject(List[CtrObject](x1537_ctr), "x1541_ctrchain")).cchain.io 
      x1541_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1541_ctrchain_p", (x1541_ctrchain.par, x1541_ctrchain.widths))
      val x1542_ctrchain = (new CChainObject(List[CtrObject](x1538_ctr), "x1542_ctrchain")).cchain.io 
      x1542_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1542_ctrchain_p", (x1542_ctrchain.par, x1542_ctrchain.widths))
      val x1635 = new x1635_kernel(List(b1168,b1529,b925,b1006,b1530,b1528,b914,b1527), List(b1164,b1526,b1525,b1524,b1523), List(x1541_ctrchain,x1540_ctrchain,x1539_ctrchain,x1542_ctrchain), List(x934_tileA_sram_0,x1017_tileB_sram_0,x1018_tileC_sram_0,x1019_tileC_sram_1) ,  Some(me), List(), 0, 4, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1635.sm.io.ctrDone := risingEdge(x1635.sm.io.ctrInc)
      x1635.backpressure := true.B | x1635.sm.io.doneLatch
      x1635.forwardpressure := (true.B) && (true.B) | x1635.sm.io.doneLatch
      x1635.sm.io.enableOut.zip(x1635.smEnableOuts).foreach{case (l,r) => r := l}
      x1635.sm.io.break := false.B
      x1635.mask := true.B & b1168 & b1006 & b925 & b914
      x1635.configure("x1635", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1635.kernel()
    }
    val module = Module(new x1636_outr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1636_outr_Foreach **/
