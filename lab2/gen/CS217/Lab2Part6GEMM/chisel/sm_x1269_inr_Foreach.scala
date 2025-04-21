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

/** Hierarchy: x1269 -> x1293 -> x1294 -> x1637 -> x1638 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1269_inr_Foreach **/
class x1269_inr_Foreach_kernel(
  list_b925: List[Bool],
  list_b1183: List[FixedPoint],
  list_x934_tileA_sram_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 11.4.toInt, myName = "x1269_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(4.0.toInt, 2 + _root_.utils.math.log2Up(4.0.toInt), "x1269_inr_Foreach_iiCtr"))
  
  abstract class x1269_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x934_tileA_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x934_tileA_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1183 = Input(new FixedPoint(true, 32, 0))
      val in_b925 = Input(Bool())
      val in_x1017_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1006 = Input(Bool())
      val in_b1187 = Input(Bool())
      val in_x1018_tileC_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_0_p").asInstanceOf[NBufParams] ))
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
    def b1183 = {io.in_b1183} 
    def b925 = {io.in_b925} 
    def x1017_tileB_sram_0 = {io.in_x1017_tileB_sram_0} ; io.in_x1017_tileB_sram_0 := DontCare
    def b1006 = {io.in_b1006} 
    def b1187 = {io.in_b1187} 
    def x1018_tileC_sram_0 = {io.in_x1018_tileC_sram_0} ; io.in_x1018_tileC_sram_0 := DontCare
    def b914 = {io.in_b914} 
    def b1161 = {io.in_b1161} 
    def b1165 = {io.in_b1165} 
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
  }
  def connectWires0(module: x1269_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x934_tileA_sram_0.connectLedger(module.io.in_x934_tileA_sram_0)
    module.io.in_b1183 <> b1183
    module.io.in_b925 <> b925
    x1017_tileB_sram_0.connectLedger(module.io.in_x1017_tileB_sram_0)
    module.io.in_b1006 <> b1006
    module.io.in_b1187 <> b1187
    x1018_tileC_sram_0.connectLedger(module.io.in_x1018_tileC_sram_0)
    module.io.in_b914 <> b914
    module.io.in_b1161 <> b1161
    module.io.in_b1165 <> b1165
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
  }
  val b925 = list_b925(0)
  val b1006 = list_b925(1)
  val b1187 = list_b925(2)
  val b914 = list_b925(3)
  val b1165 = list_b925(4)
  val b1183 = list_b1183(0)
  val b1161 = list_b1183(1)
  val x934_tileA_sram_0 = list_x934_tileA_sram_0(0)
  val x1017_tileB_sram_0 = list_x934_tileA_sram_0(1)
  val x1018_tileC_sram_0 = list_x934_tileA_sram_0(2)
  val x1019_tileC_sram_1 = list_x934_tileA_sram_0(3)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1269_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1269_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1269_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1269_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x1269_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x1269_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1269_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1269_instrctr, cycles_x1269_inr_Foreach.io.count, iters_x1269_inr_Foreach.io.count, 0.U, 0.U)
      val b1247 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1247.suggestName("b1247")
      val b1248 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1248.suggestName("b1248")
      val x1251 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1251""")
      x1251.r := Math.arith_right_shift_div(b1161, 2, Some(0.2), true.B,"x1251").r
      val x1252 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1252""")
      x1252.r := Math.arith_left_shift(x1251, 2, Some(0.2), true.B,"x1252").r
      val x1253 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1253""")
      x1253.r := Math.arith_right_shift_div(b1183, 2, Some(0.2), true.B,"x1253").r
      val x1254_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1254_sum""")
      x1254_sum.r := Math.add(x1252,x1253,Some(1.0), true.B, Truncate, Wrapping, "x1254_sum").r
      val x1902 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1902_x1254_sum_D6") 
      x1902.r := getRetimed(x1254_sum.r, 6.toInt, io.sigsIn.backpressure & true.B)
      val x1903 = Wire(Bool()).suggestName("x1903_b925_D7") 
      x1903.r := getRetimed(b925.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1904 = Wire(Bool()).suggestName("x1904_b1006_D7") 
      x1904.r := getRetimed(b1006.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1905 = Wire(Bool()).suggestName("x1905_b1187_D7") 
      x1905.r := getRetimed(b1187.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1906 = Wire(Bool()).suggestName("x1906_b914_D7") 
      x1906.r := getRetimed(b914.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1907 = Wire(Bool()).suggestName("x1907_b1248_D7") 
      x1907.r := getRetimed(b1248.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1908 = Wire(Bool()).suggestName("x1908_b1165_D7") 
      x1908.r := getRetimed(b1165.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1255_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1255_rd""")
      val x1255_rd_banks = List[UInt](0L.FP(true, 32, 0).r,2L.FP(true, 32, 0).r)
      val x1255_rd_ofs = List[UInt](x1902.r)
      val x1255_rd_en = List[Bool](true.B)
      val x1255_rd_shared_en = ((io.sigsIn.forwardpressure).DS(7.4.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(7.4.toInt, rr, io.sigsIn.backpressure & true.B) && x1908 & x1904 & x1905 & x1906 & x1907 & x1903 ).suggestName("x1255_rd_shared_en")
      x1255_rd.toSeq.zip(x1018_tileC_sram_0.connectRPort(1255, x1255_rd_banks, x1255_rd_ofs, io.sigsIn.backpressure, x1255_rd_en.map(_ && x1255_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x1256 = VecApply(x1255,0)
      val x1256_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1256_elem_0""")
      x1256_elem_0.r := x1255_rd(0).r
      val x1909 = Wire(Bool()).suggestName("x1909_b925_D1") 
      x1909.r := getRetimed(b925.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1910 = Wire(Bool()).suggestName("x1910_b1006_D1") 
      x1910.r := getRetimed(b1006.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1911 = Wire(Bool()).suggestName("x1911_b1187_D1") 
      x1911.r := getRetimed(b1187.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1912 = Wire(Bool()).suggestName("x1912_b914_D1") 
      x1912.r := getRetimed(b914.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1913 = Wire(Bool()).suggestName("x1913_b1248_D1") 
      x1913.r := getRetimed(b1248.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1914 = Wire(Bool()).suggestName("x1914_b1165_D1") 
      x1914.r := getRetimed(b1165.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1259_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1259_rd""")
      val x1259_rd_banks = List[UInt](0L.FP(true, 32, 0).r,0L.FP(true, 32, 0).r)
      val x1259_rd_ofs = List[UInt](0.U)
      val x1259_rd_en = List[Bool](true.B)
      val x1259_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) && x1910 & x1909 & x1912 & x1911 & x1913 & x1914 ).suggestName("x1259_rd_shared_en")
      x1259_rd.toSeq.zip(x934_tileA_sram_0.connectRPort(1259, x1259_rd_banks, x1259_rd_ofs, io.sigsIn.backpressure, x1259_rd_en.map(_ && x1259_rd_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      // x1260 = VecApply(x1259,0)
      val x1260_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1260_elem_0""")
      x1260_elem_0.r := x1259_rd(0).r
      val x1261 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1261""")
      x1261.r := Math.arith_left_shift(b1247, 2, Some(0.2), true.B,"x1261").r
      val x1262_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1262_sum""")
      x1262_sum.r := Math.add(x1261,x1253,Some(1.0), true.B, Truncate, Wrapping, "x1262_sum").r
      val x1263_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1263_rd""")
      val x1263_rd_banks = List[UInt](0L.FP(true, 32, 0).r,2L.FP(true, 32, 0).r)
      val x1263_rd_ofs = List[UInt](x1262_sum.r)
      val x1263_rd_en = List[Bool](true.B)
      val x1263_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && x1910 & x1909 & x1912 & x1911 & x1913 & x1914 ).suggestName("x1263_rd_shared_en")
      x1263_rd.toSeq.zip(x1017_tileB_sram_0.connectRPort(1263, x1263_rd_banks, x1263_rd_ofs, io.sigsIn.backpressure, x1263_rd_en.map(_ && x1263_rd_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      // x1264 = VecApply(x1263,0)
      val x1264_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1264_elem_0""")
      x1264_elem_0.r := x1263_rd(0).r
      val x1265_mul = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1265_mul""")
      x1265_mul.r := (Math.mul(x1260_elem_0, x1264_elem_0, Some(6.0), true.B, Truncate, Wrapping, "x1265_mul")).r
      val x1266_sum = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1266_sum""")
      x1266_sum.r := Math.add(x1256_elem_0,x1265_mul,Some(1.0), true.B, Truncate, Wrapping, "x1266_sum").r
      val x1915 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1915_x1254_sum_D9") 
      x1915.r := getRetimed(x1254_sum.r, 9.toInt, io.sigsIn.backpressure & true.B)
      val x1916 = Wire(Bool()).suggestName("x1916_b925_D10") 
      x1916.r := getRetimed(b925.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1917 = Wire(Bool()).suggestName("x1917_b1006_D10") 
      x1917.r := getRetimed(b1006.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1918 = Wire(Bool()).suggestName("x1918_b1187_D10") 
      x1918.r := getRetimed(b1187.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1919 = Wire(Bool()).suggestName("x1919_b914_D10") 
      x1919.r := getRetimed(b914.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1920 = Wire(Bool()).suggestName("x1920_b1248_D10") 
      x1920.r := getRetimed(b1248.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1921 = Wire(Bool()).suggestName("x1921_b1165_D10") 
      x1921.r := getRetimed(b1165.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1267_wr_banks = List[UInt](0L.FP(true, 32, 0).r,2L.FP(true, 32, 0).r)
      val x1267_wr_ofs = List[UInt](x1915.r)
      val x1267_wr_en = List[Bool](true.B)
      val x1267_wr_data = List[UInt](x1266_sum.r)
      x1019_tileC_sram_1.connectWPort(1267, x1267_wr_banks, x1267_wr_ofs, x1267_wr_data, x1267_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x1919 & x1921 & x1920 & x1916 & x1917 & x1918))
      val x1268_wr_banks = List[UInt](0L.FP(true, 32, 0).r,2L.FP(true, 32, 0).r)
      val x1268_wr_ofs = List[UInt](x1915.r)
      val x1268_wr_en = List[Bool](true.B)
      val x1268_wr_data = List[UInt](x1266_sum.r)
      x1018_tileC_sram_0.connectWPort(1268, x1268_wr_banks, x1268_wr_ofs, x1268_wr_data, x1268_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x1919 & x1921 & x1920 & x1916 & x1917 & x1918))
    }
    val module = Module(new x1269_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1269_inr_Foreach **/
