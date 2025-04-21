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

/** Hierarchy: x1565 -> x1635 -> x1636 -> x1637 -> x1638 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1565_inr_Foreach **/
class x1565_inr_Foreach_kernel(
  list_b1168: List[Bool],
  list_b1164: List[FixedPoint],
  list_x934_tileA_sram_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 11.4.toInt, myName = "x1565_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(4.0.toInt, 2 + _root_.utils.math.log2Up(4.0.toInt), "x1565_inr_Foreach_iiCtr"))
  
  abstract class x1565_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b1168 = Input(Bool())
      val in_b1164 = Input(new FixedPoint(true, 32, 0))
      val in_x934_tileA_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x934_tileA_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b925 = Input(Bool())
      val in_x1017_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1006 = Input(Bool())
      val in_x1018_tileC_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1523 = Input(new FixedPoint(true, 32, 0))
      val in_b914 = Input(Bool())
      val in_b1527 = Input(Bool())
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
    def x1018_tileC_sram_0 = {io.in_x1018_tileC_sram_0} ; io.in_x1018_tileC_sram_0 := DontCare
    def b1523 = {io.in_b1523} 
    def b914 = {io.in_b914} 
    def b1527 = {io.in_b1527} 
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
  }
  def connectWires0(module: x1565_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b1168 <> b1168
    module.io.in_b1164 <> b1164
    x934_tileA_sram_0.connectLedger(module.io.in_x934_tileA_sram_0)
    module.io.in_b925 <> b925
    x1017_tileB_sram_0.connectLedger(module.io.in_x1017_tileB_sram_0)
    module.io.in_b1006 <> b1006
    x1018_tileC_sram_0.connectLedger(module.io.in_x1018_tileC_sram_0)
    module.io.in_b1523 <> b1523
    module.io.in_b914 <> b914
    module.io.in_b1527 <> b1527
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
  }
  val b1168 = list_b1168(0)
  val b925 = list_b1168(1)
  val b1006 = list_b1168(2)
  val b914 = list_b1168(3)
  val b1527 = list_b1168(4)
  val b1164 = list_b1164(0)
  val b1523 = list_b1164(1)
  val x934_tileA_sram_0 = list_x934_tileA_sram_0(0)
  val x1017_tileB_sram_0 = list_x934_tileA_sram_0(1)
  val x1018_tileC_sram_0 = list_x934_tileA_sram_0(2)
  val x1019_tileC_sram_1 = list_x934_tileA_sram_0(3)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1565_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1565_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1565_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1565_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x1565_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x1565_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1565_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1565_instrctr, cycles_x1565_inr_Foreach.io.count, iters_x1565_inr_Foreach.io.count, 0.U, 0.U)
      val b1543 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1543.suggestName("b1543")
      val b1544 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1544.suggestName("b1544")
      val x1547 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1547""")
      x1547.r := Math.arith_right_shift_div(b1164, 2, Some(0.2), true.B,"x1547").r
      val x1548 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1548""")
      x1548.r := Math.arith_left_shift(x1547, 2, Some(0.2), true.B,"x1548").r
      val x1549 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1549""")
      x1549.r := Math.arith_right_shift_div(b1523, 2, Some(0.2), true.B,"x1549").r
      val x1550_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1550_sum""")
      x1550_sum.r := Math.add(x1548,x1549,Some(1.0), true.B, Truncate, Wrapping, "x1550_sum").r
      val x2102 = Wire(Bool()).suggestName("x2102_b1168_D7") 
      x2102.r := getRetimed(b1168.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2103 = Wire(Bool()).suggestName("x2103_b1544_D7") 
      x2103.r := getRetimed(b1544.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2104 = Wire(Bool()).suggestName("x2104_b925_D7") 
      x2104.r := getRetimed(b925.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2105 = Wire(Bool()).suggestName("x2105_b1006_D7") 
      x2105.r := getRetimed(b1006.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2106 = Wire(Bool()).suggestName("x2106_b914_D7") 
      x2106.r := getRetimed(b914.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2107 = Wire(Bool()).suggestName("x2107_b1527_D7") 
      x2107.r := getRetimed(b1527.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2108 = Wire(new FixedPoint(true, 32, 0)).suggestName("x2108_x1550_sum_D6") 
      x2108.r := getRetimed(x1550_sum.r, 6.toInt, io.sigsIn.backpressure & true.B)
      val x1551_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1551_rd""")
      val x1551_rd_banks = List[UInt](3L.FP(true, 32, 0).r,0L.FP(true, 32, 0).r)
      val x1551_rd_ofs = List[UInt](x2108.r)
      val x1551_rd_en = List[Bool](true.B)
      val x1551_rd_shared_en = ((io.sigsIn.forwardpressure).DS(7.4.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(7.4.toInt, rr, io.sigsIn.backpressure & true.B) && x2105 & x2102 & x2106 & x2103 & x2107 & x2104 ).suggestName("x1551_rd_shared_en")
      x1551_rd.toSeq.zip(x1018_tileC_sram_0.connectRPort(1551, x1551_rd_banks, x1551_rd_ofs, io.sigsIn.backpressure, x1551_rd_en.map(_ && x1551_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x1552 = VecApply(x1551,0)
      val x1552_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1552_elem_0""")
      x1552_elem_0.r := x1551_rd(0).r
      val x1553 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1553""")
      x1553.r := Math.arith_left_shift(x1547, 4, Some(0.2), true.B,"x1553").r
      val x1554_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1554_sum""")
      x1554_sum.r := Math.add(x1553,b1543,Some(1.0), true.B, Truncate, Wrapping, "x1554_sum").r
      val x2109 = Wire(Bool()).suggestName("x2109_b1168_D1") 
      x2109.r := getRetimed(b1168.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x2110 = Wire(Bool()).suggestName("x2110_b1544_D1") 
      x2110.r := getRetimed(b1544.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x2111 = Wire(Bool()).suggestName("x2111_b925_D1") 
      x2111.r := getRetimed(b925.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x2112 = Wire(Bool()).suggestName("x2112_b1006_D1") 
      x2112.r := getRetimed(b1006.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x2113 = Wire(Bool()).suggestName("x2113_b914_D1") 
      x2113.r := getRetimed(b914.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x2114 = Wire(Bool()).suggestName("x2114_b1527_D1") 
      x2114.r := getRetimed(b1527.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1555_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1555_rd""")
      val x1555_rd_banks = List[UInt](3L.FP(true, 32, 0).r,0L.FP(true, 32, 0).r)
      val x1555_rd_ofs = List[UInt](x1554_sum.r)
      val x1555_rd_en = List[Bool](true.B)
      val x1555_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) && x2110 & x2113 & x2114 & x2112 & x2109 & x2111 ).suggestName("x1555_rd_shared_en")
      x1555_rd.toSeq.zip(x934_tileA_sram_0.connectRPort(1555, x1555_rd_banks, x1555_rd_ofs, io.sigsIn.backpressure, x1555_rd_en.map(_ && x1555_rd_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      // x1556 = VecApply(x1555,0)
      val x1556_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1556_elem_0""")
      x1556_elem_0.r := x1555_rd(0).r
      val x1559_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1559_rd""")
      val x1559_rd_banks = List[UInt](0.U,0L.FP(true, 32, 0).r)
      val x1559_rd_ofs = List[UInt](0.U)
      val x1559_rd_en = List[Bool](true.B)
      val x1559_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && x2110 & x2113 & x2114 & x2112 & x2109 & x2111 ).suggestName("x1559_rd_shared_en")
      x1559_rd.toSeq.zip(x1017_tileB_sram_0.connectRPort(1559, x1559_rd_banks, x1559_rd_ofs, io.sigsIn.backpressure, x1559_rd_en.map(_ && x1559_rd_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      // x1560 = VecApply(x1559,0)
      val x1560_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1560_elem_0""")
      x1560_elem_0.r := x1559_rd(0).r
      val x1561_mul = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1561_mul""")
      x1561_mul.r := (Math.mul(x1556_elem_0, x1560_elem_0, Some(6.0), true.B, Truncate, Wrapping, "x1561_mul")).r
      val x1562_sum = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1562_sum""")
      x1562_sum.r := Math.add(x1552_elem_0,x1561_mul,Some(1.0), true.B, Truncate, Wrapping, "x1562_sum").r
      val x2115 = Wire(Bool()).suggestName("x2115_b1168_D10") 
      x2115.r := getRetimed(b1168.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2116 = Wire(Bool()).suggestName("x2116_b1544_D10") 
      x2116.r := getRetimed(b1544.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2117 = Wire(Bool()).suggestName("x2117_b925_D10") 
      x2117.r := getRetimed(b925.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2118 = Wire(Bool()).suggestName("x2118_b1006_D10") 
      x2118.r := getRetimed(b1006.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2119 = Wire(Bool()).suggestName("x2119_b914_D10") 
      x2119.r := getRetimed(b914.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2120 = Wire(Bool()).suggestName("x2120_b1527_D10") 
      x2120.r := getRetimed(b1527.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2121 = Wire(new FixedPoint(true, 32, 0)).suggestName("x2121_x1550_sum_D9") 
      x2121.r := getRetimed(x1550_sum.r, 9.toInt, io.sigsIn.backpressure & true.B)
      val x1563_wr_banks = List[UInt](3L.FP(true, 32, 0).r,0L.FP(true, 32, 0).r)
      val x1563_wr_ofs = List[UInt](x2121.r)
      val x1563_wr_en = List[Bool](true.B)
      val x1563_wr_data = List[UInt](x1562_sum.r)
      x1019_tileC_sram_1.connectWPort(1563, x1563_wr_banks, x1563_wr_ofs, x1563_wr_data, x1563_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x2117 & x2119 & x2120 & x2118 & x2115 & x2116))
      val x1564_wr_banks = List[UInt](3L.FP(true, 32, 0).r,0L.FP(true, 32, 0).r)
      val x1564_wr_ofs = List[UInt](x2121.r)
      val x1564_wr_en = List[Bool](true.B)
      val x1564_wr_data = List[UInt](x1562_sum.r)
      x1018_tileC_sram_0.connectWPort(1564, x1564_wr_banks, x1564_wr_ofs, x1564_wr_data, x1564_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x2117 & x2119 & x2120 & x2118 & x2115 & x2116))
    }
    val module = Module(new x1565_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1565_inr_Foreach **/
