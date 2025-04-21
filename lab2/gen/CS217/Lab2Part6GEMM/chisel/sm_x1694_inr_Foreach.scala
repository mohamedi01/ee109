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

/** Hierarchy: x1694 -> x1695 -> x1700 -> x1701 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1694_inr_Foreach **/
class x1694_inr_Foreach_kernel(
  list_b1644: List[FixedPoint],
  list_x1019_tileC_sram_1: List[NBufInterface],
  list_x1647_reg: List[StandardInterface],
  list_x1640: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 4.2.toInt, myName = "x1694_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1694_inr_Foreach_iiCtr"))
  
  abstract class x1694_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x1640 = Decoupled(new AppStoreData(ModuleParams.getParams("x1640_p").asInstanceOf[(Int,Int)] ))
      val in_b1644 = Input(new FixedPoint(true, 32, 0))
      val in_x1647_reg = Flipped(new StandardInterface(ModuleParams.getParams("x1647_reg_p").asInstanceOf[MemParams] ))
      val in_x1646_reg = Flipped(new StandardInterface(ModuleParams.getParams("x1646_reg_p").asInstanceOf[MemParams] ))
      val in_x1019_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1019_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x1640 = {io.in_x1640} 
    def b1644 = {io.in_b1644} 
    def x1647_reg = {io.in_x1647_reg} ; io.in_x1647_reg := DontCare
    def x1646_reg = {io.in_x1646_reg} ; io.in_x1646_reg := DontCare
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
  }
  def connectWires0(module: x1694_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x1640 <> x1640
    module.io.in_b1644 <> b1644
    x1647_reg.connectLedger(module.io.in_x1647_reg)
    x1646_reg.connectLedger(module.io.in_x1646_reg)
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
  }
  val b1644 = list_b1644(0)
  val x1019_tileC_sram_1 = list_x1019_tileC_sram_1(0)
  val x1647_reg = list_x1647_reg(0)
  val x1646_reg = list_x1647_reg(1)
  val x1640 = list_x1640(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1694_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1694_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1694_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1694_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x1694_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x1694_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1694_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x1694_inr_Foreach = Module(new InstrumentationCounter())
      val idles_x1694_inr_Foreach = Module(new InstrumentationCounter())
      stalls_x1694_inr_Foreach.io.enable := io.sigsIn.baseEn & ~(x1640.ready)
      idles_x1694_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X1694_instrctr, cycles_x1694_inr_Foreach.io.count, iters_x1694_inr_Foreach.io.count, stalls_x1694_inr_Foreach.io.count, idles_x1694_inr_Foreach.io.count)
      val b1676 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1676.suggestName("b1676")
      val b1677 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1677.suggestName("b1677")
      val x1678_rd_x1646 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1678_rd_x1646""")
      val x1678_rd_x1646_banks = List[UInt]()
      val x1678_rd_x1646_ofs = List[UInt]()
      val x1678_rd_x1646_en = List[Bool](true.B)
      val x1678_rd_x1646_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1678_rd_x1646_shared_en")
      x1678_rd_x1646.toSeq.zip(x1646_reg.connectRPort(1678, x1678_rd_x1646_banks, x1678_rd_x1646_ofs, io.sigsIn.backpressure, x1678_rd_x1646_en.map(_ && x1678_rd_x1646_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1679 = Wire(Bool()).suggestName("""x1679""")
      val ensig0 = Wire(Bool())
      ensig0 := x1640.ready
      x1679.r := Math.lte(x1678_rd_x1646, b1676, Some(0.4), ensig0,"x1679").r
      val x1680_rd_x1647 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1680_rd_x1647""")
      val x1680_rd_x1647_banks = List[UInt]()
      val x1680_rd_x1647_ofs = List[UInt]()
      val x1680_rd_x1647_en = List[Bool](true.B)
      val x1680_rd_x1647_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1680_rd_x1647_shared_en")
      x1680_rd_x1647.toSeq.zip(x1647_reg.connectRPort(1680, x1680_rd_x1647_banks, x1680_rd_x1647_ofs, io.sigsIn.backpressure, x1680_rd_x1647_en.map(_ && x1680_rd_x1647_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1681 = Wire(Bool()).suggestName("""x1681""")
      x1681.r := Math.lt(b1676, x1680_rd_x1647, Some(0.4), ensig0,"x1681").r
      val x1682 = Wire(Bool()).suggestName("""x1682""")
      x1682 := x1679 & x1681
      val x1683_sub = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1683_sub""")
      x1683_sub.r := Math.sub(b1676,x1678_rd_x1646,Some(1.0), ensig0, Truncate, Wrapping, "x1683_sub").r
      val x1809 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1809""")
      x1809.r := Math.and(b1644,3L.FP(true, 32, 0),Some(0.2), ensig0,"x1809").r
      val x1810 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1810""")
      x1810.r := Math.and(x1683_sub,3L.FP(true, 32, 0),Some(0.2), ensig0,"x1810").r
      val x1686 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1686""")
      x1686.r := Math.arith_right_shift_div(b1644, 2, Some(0.2), ensig0,"x1686").r
      val x1687 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1687""")
      x1687.r := Math.arith_left_shift(x1686, 2, Some(0.2), ensig0,"x1687").r
      val x1688 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1688""")
      x1688.r := Math.arith_right_shift_div(x1683_sub, 2, Some(0.2), ensig0,"x1688").r
      val x2188 = Wire(new FixedPoint(true, 32, 0)).suggestName("x2188_x1687_D1") 
      x2188.r := getRetimed(x1687.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1689_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1689_sum""")
      x1689_sum.r := Math.add(x2188,x1688,Some(1.0), ensig0, Truncate, Wrapping, "x1689_sum").r
      val x2189 = Wire(new FixedPoint(true, 32, 0)).suggestName("x2189_x1809_D2") 
      x2189.r := getRetimed(x1809.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x2190 = Wire(new FixedPoint(true, 32, 0)).suggestName("x2190_x1810_D1") 
      x2190.r := getRetimed(x1810.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x2191 = Wire(Bool()).suggestName("x2191_b1677_D2") 
      x2191.r := getRetimed(b1677.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x2192 = Wire(Bool()).suggestName("x2192_x1682_D2") 
      x2192.r := getRetimed(x1682.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x1690_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1690_rd""")
      val x1690_rd_banks = List[UInt](x2189.r,x2190.r)
      val x1690_rd_ofs = List[UInt](x1689_sum.r)
      val x1690_rd_en = List[Bool](true.B)
      val x1690_rd_shared_en = ((io.sigsIn.forwardpressure).DS(2.2.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.2.toInt, rr, io.sigsIn.backpressure & true.B) && x2192 & x2191 ).suggestName("x1690_rd_shared_en")
      x1690_rd.toSeq.zip(x1019_tileC_sram_1.connectRPort(1690, x1690_rd_banks, x1690_rd_ofs, io.sigsIn.backpressure, x1690_rd_en.map(_ && x1690_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x1691 = VecApply(x1690,0)
      val x1691_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1691_elem_0""")
      x1691_elem_0.r := x1690_rd(0).r
      val x2193 = Wire(Bool()).suggestName("x2193_x1682_D4") 
      x2193.r := getRetimed(x1682.r, 4.toInt, io.sigsIn.backpressure & true.B)
      val x1692_tuple = Wire(UInt(33.W)).suggestName("""x1692_tuple""")
      x1692_tuple.r := ConvAndCat(x2193,x1691_elem_0.r)
      val x2194 = Wire(Bool()).suggestName("x2194_b1677_D4") 
      x2194.r := getRetimed(b1677.r, 4.toInt, io.sigsIn.backpressure & true.B)
      x1640.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(4.2.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x2194 & io.sigsIn.backpressure
      x1640.bits.wdata(0) := x1692_tuple(31,0)
      x1640.bits.wstrb := x1692_tuple(32,32)
    }
    val module = Module(new x1694_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1694_inr_Foreach **/
