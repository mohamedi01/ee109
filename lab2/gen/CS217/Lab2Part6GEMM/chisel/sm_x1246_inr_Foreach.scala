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

/** Hierarchy: x1246 -> x1293 -> x1294 -> x1637 -> x1638 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1246_inr_Foreach **/
class x1246_inr_Foreach_kernel(
  list_b925: List[Bool],
  list_b1182: List[FixedPoint],
  list_x934_tileA_sram_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 11.4.toInt, myName = "x1246_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(4.0.toInt, 2 + _root_.utils.math.log2Up(4.0.toInt), "x1246_inr_Foreach_iiCtr"))
  
  abstract class x1246_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x934_tileA_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x934_tileA_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b925 = Input(Bool())
      val in_x1017_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1006 = Input(Bool())
      val in_b1182 = Input(new FixedPoint(true, 32, 0))
      val in_x1018_tileC_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1186 = Input(Bool())
      val in_b914 = Input(Bool())
      val in_b1161 = Input(new FixedPoint(true, 32, 0))
      val in_b1165 = Input(Bool())
      val in_x1019_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1019_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x934_tileA_sram_0 = {io.in_x934_tileA_sram_0} ; io.in_x934_tileA_sram_0 := DontCare
    def b925 = {io.in_b925} 
    def x1017_tileB_sram_0 = {io.in_x1017_tileB_sram_0} ; io.in_x1017_tileB_sram_0 := DontCare
    def b1006 = {io.in_b1006} 
    def b1182 = {io.in_b1182} 
    def x1018_tileC_sram_0 = {io.in_x1018_tileC_sram_0} ; io.in_x1018_tileC_sram_0 := DontCare
    def b1186 = {io.in_b1186} 
    def b914 = {io.in_b914} 
    def b1161 = {io.in_b1161} 
    def b1165 = {io.in_b1165} 
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
  }
  def connectWires0(module: x1246_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x934_tileA_sram_0.connectLedger(module.io.in_x934_tileA_sram_0)
    module.io.in_b925 <> b925
    x1017_tileB_sram_0.connectLedger(module.io.in_x1017_tileB_sram_0)
    module.io.in_b1006 <> b1006
    module.io.in_b1182 <> b1182
    x1018_tileC_sram_0.connectLedger(module.io.in_x1018_tileC_sram_0)
    module.io.in_b1186 <> b1186
    module.io.in_b914 <> b914
    module.io.in_b1161 <> b1161
    module.io.in_b1165 <> b1165
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
  }
  val b925 = list_b925(0)
  val b1006 = list_b925(1)
  val b1186 = list_b925(2)
  val b914 = list_b925(3)
  val b1165 = list_b925(4)
  val b1182 = list_b1182(0)
  val b1161 = list_b1182(1)
  val x934_tileA_sram_0 = list_x934_tileA_sram_0(0)
  val x1017_tileB_sram_0 = list_x934_tileA_sram_0(1)
  val x1018_tileC_sram_0 = list_x934_tileA_sram_0(2)
  val x1019_tileC_sram_1 = list_x934_tileA_sram_0(3)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1246_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1246_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1246_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1246_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x1246_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x1246_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1246_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1246_instrctr, cycles_x1246_inr_Foreach.io.count, iters_x1246_inr_Foreach.io.count, 0.U, 0.U)
      val b1224 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1224.suggestName("b1224")
      val b1225 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1225.suggestName("b1225")
      val x1228 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1228""")
      x1228.r := Math.arith_right_shift_div(b1161, 2, Some(0.2), true.B,"x1228").r
      val x1229 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1229""")
      x1229.r := Math.arith_left_shift(x1228, 2, Some(0.2), true.B,"x1229").r
      val x1230 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1230""")
      x1230.r := Math.arith_right_shift_div(b1182, 2, Some(0.2), true.B,"x1230").r
      val x1231_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1231_sum""")
      x1231_sum.r := Math.add(x1229,x1230,Some(1.0), true.B, Truncate, Wrapping, "x1231_sum").r
      val x1882 = Wire(Bool()).suggestName("x1882_b925_D7") 
      x1882.r := getRetimed(b925.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1883 = Wire(Bool()).suggestName("x1883_b1006_D7") 
      x1883.r := getRetimed(b1006.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1884 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1884_x1231_sum_D6") 
      x1884.r := getRetimed(x1231_sum.r, 6.toInt, io.sigsIn.backpressure & true.B)
      val x1885 = Wire(Bool()).suggestName("x1885_b1225_D7") 
      x1885.r := getRetimed(b1225.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1886 = Wire(Bool()).suggestName("x1886_b1186_D7") 
      x1886.r := getRetimed(b1186.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1887 = Wire(Bool()).suggestName("x1887_b914_D7") 
      x1887.r := getRetimed(b914.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1888 = Wire(Bool()).suggestName("x1888_b1165_D7") 
      x1888.r := getRetimed(b1165.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1232_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1232_rd""")
      val x1232_rd_banks = List[UInt](0L.FP(true, 32, 0).r,1L.FP(true, 32, 0).r)
      val x1232_rd_ofs = List[UInt](x1884.r)
      val x1232_rd_en = List[Bool](true.B)
      val x1232_rd_shared_en = ((io.sigsIn.forwardpressure).DS(7.4.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(7.4.toInt, rr, io.sigsIn.backpressure & true.B) && x1887 & x1883 & x1888 & x1885 & x1886 & x1882 ).suggestName("x1232_rd_shared_en")
      x1232_rd.toSeq.zip(x1018_tileC_sram_0.connectRPort(1232, x1232_rd_banks, x1232_rd_ofs, io.sigsIn.backpressure, x1232_rd_en.map(_ && x1232_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x1233 = VecApply(x1232,0)
      val x1233_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1233_elem_0""")
      x1233_elem_0.r := x1232_rd(0).r
      val x1889 = Wire(Bool()).suggestName("x1889_b925_D1") 
      x1889.r := getRetimed(b925.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1890 = Wire(Bool()).suggestName("x1890_b1006_D1") 
      x1890.r := getRetimed(b1006.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1891 = Wire(Bool()).suggestName("x1891_b1225_D1") 
      x1891.r := getRetimed(b1225.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1892 = Wire(Bool()).suggestName("x1892_b1186_D1") 
      x1892.r := getRetimed(b1186.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1893 = Wire(Bool()).suggestName("x1893_b914_D1") 
      x1893.r := getRetimed(b914.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1894 = Wire(Bool()).suggestName("x1894_b1165_D1") 
      x1894.r := getRetimed(b1165.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1236_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1236_rd""")
      val x1236_rd_banks = List[UInt](0L.FP(true, 32, 0).r,0L.FP(true, 32, 0).r)
      val x1236_rd_ofs = List[UInt](0.U)
      val x1236_rd_en = List[Bool](true.B)
      val x1236_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) && x1894 & x1890 & x1889 & x1892 & x1891 & x1893 ).suggestName("x1236_rd_shared_en")
      x1236_rd.toSeq.zip(x934_tileA_sram_0.connectRPort(1236, x1236_rd_banks, x1236_rd_ofs, io.sigsIn.backpressure, x1236_rd_en.map(_ && x1236_rd_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      // x1237 = VecApply(x1236,0)
      val x1237_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1237_elem_0""")
      x1237_elem_0.r := x1236_rd(0).r
      val x1238 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1238""")
      x1238.r := Math.arith_left_shift(b1224, 2, Some(0.2), true.B,"x1238").r
      val x1239_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1239_sum""")
      x1239_sum.r := Math.add(x1238,x1230,Some(1.0), true.B, Truncate, Wrapping, "x1239_sum").r
      val x1240_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1240_rd""")
      val x1240_rd_banks = List[UInt](0L.FP(true, 32, 0).r,1L.FP(true, 32, 0).r)
      val x1240_rd_ofs = List[UInt](x1239_sum.r)
      val x1240_rd_en = List[Bool](true.B)
      val x1240_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && x1894 & x1890 & x1889 & x1892 & x1891 & x1893 ).suggestName("x1240_rd_shared_en")
      x1240_rd.toSeq.zip(x1017_tileB_sram_0.connectRPort(1240, x1240_rd_banks, x1240_rd_ofs, io.sigsIn.backpressure, x1240_rd_en.map(_ && x1240_rd_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      // x1241 = VecApply(x1240,0)
      val x1241_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1241_elem_0""")
      x1241_elem_0.r := x1240_rd(0).r
      val x1242_mul = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1242_mul""")
      x1242_mul.r := (Math.mul(x1237_elem_0, x1241_elem_0, Some(6.0), true.B, Truncate, Wrapping, "x1242_mul")).r
      val x1243_sum = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1243_sum""")
      x1243_sum.r := Math.add(x1233_elem_0,x1242_mul,Some(1.0), true.B, Truncate, Wrapping, "x1243_sum").r
      val x1895 = Wire(Bool()).suggestName("x1895_b925_D10") 
      x1895.r := getRetimed(b925.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1896 = Wire(Bool()).suggestName("x1896_b1006_D10") 
      x1896.r := getRetimed(b1006.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1897 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1897_x1231_sum_D9") 
      x1897.r := getRetimed(x1231_sum.r, 9.toInt, io.sigsIn.backpressure & true.B)
      val x1898 = Wire(Bool()).suggestName("x1898_b1225_D10") 
      x1898.r := getRetimed(b1225.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1899 = Wire(Bool()).suggestName("x1899_b1186_D10") 
      x1899.r := getRetimed(b1186.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1900 = Wire(Bool()).suggestName("x1900_b914_D10") 
      x1900.r := getRetimed(b914.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1901 = Wire(Bool()).suggestName("x1901_b1165_D10") 
      x1901.r := getRetimed(b1165.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1244_wr_banks = List[UInt](0L.FP(true, 32, 0).r,1L.FP(true, 32, 0).r)
      val x1244_wr_ofs = List[UInt](x1897.r)
      val x1244_wr_en = List[Bool](true.B)
      val x1244_wr_data = List[UInt](x1243_sum.r)
      x1019_tileC_sram_1.connectWPort(1244, x1244_wr_banks, x1244_wr_ofs, x1244_wr_data, x1244_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x1898 & x1895 & x1899 & x1896 & x1901 & x1900))
      val x1245_wr_banks = List[UInt](0L.FP(true, 32, 0).r,1L.FP(true, 32, 0).r)
      val x1245_wr_ofs = List[UInt](x1897.r)
      val x1245_wr_en = List[Bool](true.B)
      val x1245_wr_data = List[UInt](x1243_sum.r)
      x1018_tileC_sram_0.connectWPort(1245, x1245_wr_banks, x1245_wr_ofs, x1245_wr_data, x1245_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x1898 & x1895 & x1899 & x1896 & x1901 & x1900))
    }
    val module = Module(new x1246_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1246_inr_Foreach **/
