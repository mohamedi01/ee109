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

/** Hierarchy: x1497 -> x1521 -> x1522 -> x1637 -> x1638 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1497_inr_Foreach **/
class x1497_inr_Foreach_kernel(
  list_b1415: List[Bool],
  list_b1411: List[FixedPoint],
  list_x934_tileA_sram_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 11.4.toInt, myName = "x1497_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(4.0.toInt, 2 + _root_.utils.math.log2Up(4.0.toInt), "x1497_inr_Foreach_iiCtr"))
  
  abstract class x1497_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b1411 = Input(new FixedPoint(true, 32, 0))
      val in_x934_tileA_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x934_tileA_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1415 = Input(Bool())
      val in_b925 = Input(Bool())
      val in_b1163 = Input(new FixedPoint(true, 32, 0))
      val in_x1017_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1006 = Input(Bool())
      val in_b1167 = Input(Bool())
      val in_x1018_tileC_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b914 = Input(Bool())
      val in_x1019_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1019_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def b1411 = {io.in_b1411} 
    def x934_tileA_sram_0 = {io.in_x934_tileA_sram_0} ; io.in_x934_tileA_sram_0 := DontCare
    def b1415 = {io.in_b1415} 
    def b925 = {io.in_b925} 
    def b1163 = {io.in_b1163} 
    def x1017_tileB_sram_0 = {io.in_x1017_tileB_sram_0} ; io.in_x1017_tileB_sram_0 := DontCare
    def b1006 = {io.in_b1006} 
    def b1167 = {io.in_b1167} 
    def x1018_tileC_sram_0 = {io.in_x1018_tileC_sram_0} ; io.in_x1018_tileC_sram_0 := DontCare
    def b914 = {io.in_b914} 
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
  }
  def connectWires0(module: x1497_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b1411 <> b1411
    x934_tileA_sram_0.connectLedger(module.io.in_x934_tileA_sram_0)
    module.io.in_b1415 <> b1415
    module.io.in_b925 <> b925
    module.io.in_b1163 <> b1163
    x1017_tileB_sram_0.connectLedger(module.io.in_x1017_tileB_sram_0)
    module.io.in_b1006 <> b1006
    module.io.in_b1167 <> b1167
    x1018_tileC_sram_0.connectLedger(module.io.in_x1018_tileC_sram_0)
    module.io.in_b914 <> b914
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
  }
  val b1415 = list_b1415(0)
  val b925 = list_b1415(1)
  val b1006 = list_b1415(2)
  val b1167 = list_b1415(3)
  val b914 = list_b1415(4)
  val b1411 = list_b1411(0)
  val b1163 = list_b1411(1)
  val x934_tileA_sram_0 = list_x934_tileA_sram_0(0)
  val x1017_tileB_sram_0 = list_x934_tileA_sram_0(1)
  val x1018_tileC_sram_0 = list_x934_tileA_sram_0(2)
  val x1019_tileC_sram_1 = list_x934_tileA_sram_0(3)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1497_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1497_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1497_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1497_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x1497_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x1497_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1497_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1497_instrctr, cycles_x1497_inr_Foreach.io.count, iters_x1497_inr_Foreach.io.count, 0.U, 0.U)
      val b1475 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1475.suggestName("b1475")
      val b1476 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1476.suggestName("b1476")
      val x1479 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1479""")
      x1479.r := Math.arith_right_shift_div(b1163, 2, Some(0.2), true.B,"x1479").r
      val x1480 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1480""")
      x1480.r := Math.arith_left_shift(x1479, 2, Some(0.2), true.B,"x1480").r
      val x1481 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1481""")
      x1481.r := Math.arith_right_shift_div(b1411, 2, Some(0.2), true.B,"x1481").r
      val x1482_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1482_sum""")
      x1482_sum.r := Math.add(x1480,x1481,Some(1.0), true.B, Truncate, Wrapping, "x1482_sum").r
      val x2062 = Wire(new FixedPoint(true, 32, 0)).suggestName("x2062_x1482_sum_D6") 
      x2062.r := getRetimed(x1482_sum.r, 6.toInt, io.sigsIn.backpressure & true.B)
      val x2063 = Wire(Bool()).suggestName("x2063_b1415_D7") 
      x2063.r := getRetimed(b1415.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2064 = Wire(Bool()).suggestName("x2064_b925_D7") 
      x2064.r := getRetimed(b925.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2065 = Wire(Bool()).suggestName("x2065_b1006_D7") 
      x2065.r := getRetimed(b1006.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2066 = Wire(Bool()).suggestName("x2066_b1167_D7") 
      x2066.r := getRetimed(b1167.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2067 = Wire(Bool()).suggestName("x2067_b1476_D7") 
      x2067.r := getRetimed(b1476.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2068 = Wire(Bool()).suggestName("x2068_b914_D7") 
      x2068.r := getRetimed(b914.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1483_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1483_rd""")
      val x1483_rd_banks = List[UInt](2L.FP(true, 32, 0).r,0.U)
      val x1483_rd_ofs = List[UInt](x2062.r)
      val x1483_rd_en = List[Bool](true.B)
      val x1483_rd_shared_en = ((io.sigsIn.forwardpressure).DS(7.4.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(7.4.toInt, rr, io.sigsIn.backpressure & true.B) && x2068 & x2065 & x2066 & x2063 & x2064 & x2067 ).suggestName("x1483_rd_shared_en")
      x1483_rd.toSeq.zip(x1018_tileC_sram_0.connectRPort(1483, x1483_rd_banks, x1483_rd_ofs, io.sigsIn.backpressure, x1483_rd_en.map(_ && x1483_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x1484 = VecApply(x1483,0)
      val x1484_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1484_elem_0""")
      x1484_elem_0.r := x1483_rd(0).r
      val x1487_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1487_rd""")
      val x1487_rd_banks = List[UInt](2L.FP(true, 32, 0).r,0.U)
      val x1487_rd_ofs = List[UInt](0.U)
      val x1487_rd_en = List[Bool](true.B)
      val x1487_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1487_rd_shared_en")
      x1487_rd.toSeq.zip(x934_tileA_sram_0.connectRPort(1487, x1487_rd_banks, x1487_rd_ofs, io.sigsIn.backpressure, x1487_rd_en.map(_ && x1487_rd_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      // x1488 = VecApply(x1487,0)
      val x1488_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1488_elem_0""")
      x1488_elem_0.r := x1487_rd(0).r
      val x1491_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1491_rd""")
      val x1491_rd_banks = List[UInt](0.U,0.U)
      val x1491_rd_ofs = List[UInt](0.U)
      val x1491_rd_en = List[Bool](true.B)
      val x1491_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1491_rd_shared_en")
      x1491_rd.toSeq.zip(x1017_tileB_sram_0.connectRPort(1491, x1491_rd_banks, x1491_rd_ofs, io.sigsIn.backpressure, x1491_rd_en.map(_ && x1491_rd_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      // x1492 = VecApply(x1491,0)
      val x1492_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1492_elem_0""")
      x1492_elem_0.r := x1491_rd(0).r
      val x1493_mul = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1493_mul""")
      x1493_mul.r := (Math.mul(x1488_elem_0, x1492_elem_0, Some(6.0), true.B, Truncate, Wrapping, "x1493_mul")).r
      val x1494_sum = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1494_sum""")
      x1494_sum.r := Math.add(x1484_elem_0,x1493_mul,Some(1.0), true.B, Truncate, Wrapping, "x1494_sum").r
      val x2075 = Wire(new FixedPoint(true, 32, 0)).suggestName("x2075_x1482_sum_D9") 
      x2075.r := getRetimed(x1482_sum.r, 9.toInt, io.sigsIn.backpressure & true.B)
      val x2076 = Wire(Bool()).suggestName("x2076_b1415_D10") 
      x2076.r := getRetimed(b1415.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2077 = Wire(Bool()).suggestName("x2077_b925_D10") 
      x2077.r := getRetimed(b925.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2078 = Wire(Bool()).suggestName("x2078_b1006_D10") 
      x2078.r := getRetimed(b1006.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2079 = Wire(Bool()).suggestName("x2079_b1167_D10") 
      x2079.r := getRetimed(b1167.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2080 = Wire(Bool()).suggestName("x2080_b1476_D10") 
      x2080.r := getRetimed(b1476.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2081 = Wire(Bool()).suggestName("x2081_b914_D10") 
      x2081.r := getRetimed(b914.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1495_wr_banks = List[UInt](2L.FP(true, 32, 0).r,0.U)
      val x1495_wr_ofs = List[UInt](x2075.r)
      val x1495_wr_en = List[Bool](true.B)
      val x1495_wr_data = List[UInt](x1494_sum.r)
      x1019_tileC_sram_1.connectWPort(1495, x1495_wr_banks, x1495_wr_ofs, x1495_wr_data, x1495_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x2078 & x2081 & x2079 & x2076 & x2080 & x2077))
      val x1496_wr_banks = List[UInt](2L.FP(true, 32, 0).r,0.U)
      val x1496_wr_ofs = List[UInt](x2075.r)
      val x1496_wr_en = List[Bool](true.B)
      val x1496_wr_data = List[UInt](x1494_sum.r)
      x1018_tileC_sram_0.connectWPort(1496, x1496_wr_banks, x1496_wr_ofs, x1496_wr_data, x1496_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x2078 & x2081 & x2079 & x2076 & x2080 & x2077))
    }
    val module = Module(new x1497_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1497_inr_Foreach **/
