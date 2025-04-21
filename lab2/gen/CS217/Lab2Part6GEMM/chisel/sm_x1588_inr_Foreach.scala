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

/** Hierarchy: x1588 -> x1635 -> x1636 -> x1637 -> x1638 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1588_inr_Foreach **/
class x1588_inr_Foreach_kernel(
  list_b1168: List[Bool],
  list_b1164: List[FixedPoint],
  list_x934_tileA_sram_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 11.4.toInt, myName = "x1588_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(4.0.toInt, 2 + _root_.utils.math.log2Up(4.0.toInt), "x1588_inr_Foreach_iiCtr"))
  
  abstract class x1588_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b1168 = Input(Bool())
      val in_b1164 = Input(new FixedPoint(true, 32, 0))
      val in_x934_tileA_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x934_tileA_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b925 = Input(Bool())
      val in_x1017_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1006 = Input(Bool())
      val in_b1524 = Input(new FixedPoint(true, 32, 0))
      val in_b1528 = Input(Bool())
      val in_x1018_tileC_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b914 = Input(Bool())
      val in_x1019_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1019_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def b1168 = {io.in_b1168} 
    def b1164 = {io.in_b1164} 
    def x934_tileA_sram_0 = {io.in_x934_tileA_sram_0} ; io.in_x934_tileA_sram_0 := DontCare
    def b925 = {io.in_b925} 
    def x1017_tileB_sram_0 = {io.in_x1017_tileB_sram_0} ; io.in_x1017_tileB_sram_0 := DontCare
    def b1006 = {io.in_b1006} 
    def b1524 = {io.in_b1524} 
    def b1528 = {io.in_b1528} 
    def x1018_tileC_sram_0 = {io.in_x1018_tileC_sram_0} ; io.in_x1018_tileC_sram_0 := DontCare
    def b914 = {io.in_b914} 
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
  }
  def connectWires0(module: x1588_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b1168 <> b1168
    module.io.in_b1164 <> b1164
    x934_tileA_sram_0.connectLedger(module.io.in_x934_tileA_sram_0)
    module.io.in_b925 <> b925
    x1017_tileB_sram_0.connectLedger(module.io.in_x1017_tileB_sram_0)
    module.io.in_b1006 <> b1006
    module.io.in_b1524 <> b1524
    module.io.in_b1528 <> b1528
    x1018_tileC_sram_0.connectLedger(module.io.in_x1018_tileC_sram_0)
    module.io.in_b914 <> b914
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
  }
  val b1168 = list_b1168(0)
  val b925 = list_b1168(1)
  val b1006 = list_b1168(2)
  val b1528 = list_b1168(3)
  val b914 = list_b1168(4)
  val b1164 = list_b1164(0)
  val b1524 = list_b1164(1)
  val x934_tileA_sram_0 = list_x934_tileA_sram_0(0)
  val x1017_tileB_sram_0 = list_x934_tileA_sram_0(1)
  val x1018_tileC_sram_0 = list_x934_tileA_sram_0(2)
  val x1019_tileC_sram_1 = list_x934_tileA_sram_0(3)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1588_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1588_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1588_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1588_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x1588_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x1588_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1588_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1588_instrctr, cycles_x1588_inr_Foreach.io.count, iters_x1588_inr_Foreach.io.count, 0.U, 0.U)
      val b1566 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1566.suggestName("b1566")
      val b1567 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1567.suggestName("b1567")
      val x1570 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1570""")
      x1570.r := Math.arith_right_shift_div(b1164, 2, Some(0.2), true.B,"x1570").r
      val x1571 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1571""")
      x1571.r := Math.arith_left_shift(x1570, 2, Some(0.2), true.B,"x1571").r
      val x1572 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1572""")
      x1572.r := Math.arith_right_shift_div(b1524, 2, Some(0.2), true.B,"x1572").r
      val x1573_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1573_sum""")
      x1573_sum.r := Math.add(x1571,x1572,Some(1.0), true.B, Truncate, Wrapping, "x1573_sum").r
      val x2122 = Wire(Bool()).suggestName("x2122_b1168_D7") 
      x2122.r := getRetimed(b1168.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2123 = Wire(Bool()).suggestName("x2123_b925_D7") 
      x2123.r := getRetimed(b925.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2124 = Wire(new FixedPoint(true, 32, 0)).suggestName("x2124_x1573_sum_D6") 
      x2124.r := getRetimed(x1573_sum.r, 6.toInt, io.sigsIn.backpressure & true.B)
      val x2125 = Wire(Bool()).suggestName("x2125_b1006_D7") 
      x2125.r := getRetimed(b1006.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2126 = Wire(Bool()).suggestName("x2126_b1567_D7") 
      x2126.r := getRetimed(b1567.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2127 = Wire(Bool()).suggestName("x2127_b1528_D7") 
      x2127.r := getRetimed(b1528.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2128 = Wire(Bool()).suggestName("x2128_b914_D7") 
      x2128.r := getRetimed(b914.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1574_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1574_rd""")
      val x1574_rd_banks = List[UInt](3L.FP(true, 32, 0).r,1L.FP(true, 32, 0).r)
      val x1574_rd_ofs = List[UInt](x2124.r)
      val x1574_rd_en = List[Bool](true.B)
      val x1574_rd_shared_en = ((io.sigsIn.forwardpressure).DS(7.4.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(7.4.toInt, rr, io.sigsIn.backpressure & true.B) && x2128 & x2125 & x2123 & x2127 & x2126 & x2122 ).suggestName("x1574_rd_shared_en")
      x1574_rd.toSeq.zip(x1018_tileC_sram_0.connectRPort(1574, x1574_rd_banks, x1574_rd_ofs, io.sigsIn.backpressure, x1574_rd_en.map(_ && x1574_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x1575 = VecApply(x1574,0)
      val x1575_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1575_elem_0""")
      x1575_elem_0.r := x1574_rd(0).r
      val x1578_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1578_rd""")
      val x1578_rd_banks = List[UInt](3L.FP(true, 32, 0).r,0.U)
      val x1578_rd_ofs = List[UInt](0.U)
      val x1578_rd_en = List[Bool](true.B)
      val x1578_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1578_rd_shared_en")
      x1578_rd.toSeq.zip(x934_tileA_sram_0.connectRPort(1578, x1578_rd_banks, x1578_rd_ofs, io.sigsIn.backpressure, x1578_rd_en.map(_ && x1578_rd_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      // x1579 = VecApply(x1578,0)
      val x1579_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1579_elem_0""")
      x1579_elem_0.r := x1578_rd(0).r
      val x1582_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1582_rd""")
      val x1582_rd_banks = List[UInt](0.U,1L.FP(true, 32, 0).r)
      val x1582_rd_ofs = List[UInt](0.U)
      val x1582_rd_en = List[Bool](true.B)
      val x1582_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1582_rd_shared_en")
      x1582_rd.toSeq.zip(x1017_tileB_sram_0.connectRPort(1582, x1582_rd_banks, x1582_rd_ofs, io.sigsIn.backpressure, x1582_rd_en.map(_ && x1582_rd_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      // x1583 = VecApply(x1582,0)
      val x1583_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1583_elem_0""")
      x1583_elem_0.r := x1582_rd(0).r
      val x1584_mul = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1584_mul""")
      x1584_mul.r := (Math.mul(x1579_elem_0, x1583_elem_0, Some(6.0), true.B, Truncate, Wrapping, "x1584_mul")).r
      val x1585_sum = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1585_sum""")
      x1585_sum.r := Math.add(x1575_elem_0,x1584_mul,Some(1.0), true.B, Truncate, Wrapping, "x1585_sum").r
      val x2135 = Wire(Bool()).suggestName("x2135_b1168_D10") 
      x2135.r := getRetimed(b1168.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2136 = Wire(Bool()).suggestName("x2136_b925_D10") 
      x2136.r := getRetimed(b925.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2137 = Wire(new FixedPoint(true, 32, 0)).suggestName("x2137_x1573_sum_D9") 
      x2137.r := getRetimed(x1573_sum.r, 9.toInt, io.sigsIn.backpressure & true.B)
      val x2138 = Wire(Bool()).suggestName("x2138_b1006_D10") 
      x2138.r := getRetimed(b1006.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2139 = Wire(Bool()).suggestName("x2139_b1567_D10") 
      x2139.r := getRetimed(b1567.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2140 = Wire(Bool()).suggestName("x2140_b1528_D10") 
      x2140.r := getRetimed(b1528.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2141 = Wire(Bool()).suggestName("x2141_b914_D10") 
      x2141.r := getRetimed(b914.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1586_wr_banks = List[UInt](3L.FP(true, 32, 0).r,1L.FP(true, 32, 0).r)
      val x1586_wr_ofs = List[UInt](x2137.r)
      val x1586_wr_en = List[Bool](true.B)
      val x1586_wr_data = List[UInt](x1585_sum.r)
      x1019_tileC_sram_1.connectWPort(1586, x1586_wr_banks, x1586_wr_ofs, x1586_wr_data, x1586_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x2140 & x2141 & x2139 & x2136 & x2138 & x2135))
      val x1587_wr_banks = List[UInt](3L.FP(true, 32, 0).r,1L.FP(true, 32, 0).r)
      val x1587_wr_ofs = List[UInt](x2137.r)
      val x1587_wr_en = List[Bool](true.B)
      val x1587_wr_data = List[UInt](x1585_sum.r)
      x1018_tileC_sram_0.connectWPort(1587, x1587_wr_banks, x1587_wr_ofs, x1587_wr_data, x1587_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x2140 & x2141 & x2139 & x2136 & x2138 & x2135))
    }
    val module = Module(new x1588_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1588_inr_Foreach **/
