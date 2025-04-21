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

/** Hierarchy: x1292 -> x1293 -> x1294 -> x1637 -> x1638 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1292_inr_Foreach **/
class x1292_inr_Foreach_kernel(
  list_b925: List[Bool],
  list_b1161: List[FixedPoint],
  list_x934_tileA_sram_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 11.4.toInt, myName = "x1292_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(4.0.toInt, 2 + _root_.utils.math.log2Up(4.0.toInt), "x1292_inr_Foreach_iiCtr"))
  
  abstract class x1292_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x934_tileA_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x934_tileA_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b925 = Input(Bool())
      val in_x1017_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1006 = Input(Bool())
      val in_x1018_tileC_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1188 = Input(Bool())
      val in_b914 = Input(Bool())
      val in_b1161 = Input(new FixedPoint(true, 32, 0))
      val in_b1165 = Input(Bool())
      val in_x1019_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1019_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_b1184 = Input(new FixedPoint(true, 32, 0))
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
    def x1018_tileC_sram_0 = {io.in_x1018_tileC_sram_0} ; io.in_x1018_tileC_sram_0 := DontCare
    def b1188 = {io.in_b1188} 
    def b914 = {io.in_b914} 
    def b1161 = {io.in_b1161} 
    def b1165 = {io.in_b1165} 
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
    def b1184 = {io.in_b1184} 
  }
  def connectWires0(module: x1292_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x934_tileA_sram_0.connectLedger(module.io.in_x934_tileA_sram_0)
    module.io.in_b925 <> b925
    x1017_tileB_sram_0.connectLedger(module.io.in_x1017_tileB_sram_0)
    module.io.in_b1006 <> b1006
    x1018_tileC_sram_0.connectLedger(module.io.in_x1018_tileC_sram_0)
    module.io.in_b1188 <> b1188
    module.io.in_b914 <> b914
    module.io.in_b1161 <> b1161
    module.io.in_b1165 <> b1165
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
    module.io.in_b1184 <> b1184
  }
  val b925 = list_b925(0)
  val b1006 = list_b925(1)
  val b1188 = list_b925(2)
  val b914 = list_b925(3)
  val b1165 = list_b925(4)
  val b1161 = list_b1161(0)
  val b1184 = list_b1161(1)
  val x934_tileA_sram_0 = list_x934_tileA_sram_0(0)
  val x1017_tileB_sram_0 = list_x934_tileA_sram_0(1)
  val x1018_tileC_sram_0 = list_x934_tileA_sram_0(2)
  val x1019_tileC_sram_1 = list_x934_tileA_sram_0(3)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1292_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1292_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1292_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1292_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x1292_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x1292_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1292_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1292_instrctr, cycles_x1292_inr_Foreach.io.count, iters_x1292_inr_Foreach.io.count, 0.U, 0.U)
      val b1270 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1270.suggestName("b1270")
      val b1271 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1271.suggestName("b1271")
      val x1274 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1274""")
      x1274.r := Math.arith_right_shift_div(b1161, 2, Some(0.2), true.B,"x1274").r
      val x1275 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1275""")
      x1275.r := Math.arith_left_shift(x1274, 2, Some(0.2), true.B,"x1275").r
      val x1276 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1276""")
      x1276.r := Math.arith_right_shift_div(b1184, 2, Some(0.2), true.B,"x1276").r
      val x1277_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1277_sum""")
      x1277_sum.r := Math.add(x1275,x1276,Some(1.0), true.B, Truncate, Wrapping, "x1277_sum").r
      val x1922 = Wire(Bool()).suggestName("x1922_b925_D7") 
      x1922.r := getRetimed(b925.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1923 = Wire(Bool()).suggestName("x1923_b1006_D7") 
      x1923.r := getRetimed(b1006.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1924 = Wire(Bool()).suggestName("x1924_b1271_D7") 
      x1924.r := getRetimed(b1271.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1925 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1925_x1277_sum_D6") 
      x1925.r := getRetimed(x1277_sum.r, 6.toInt, io.sigsIn.backpressure & true.B)
      val x1926 = Wire(Bool()).suggestName("x1926_b1188_D7") 
      x1926.r := getRetimed(b1188.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1927 = Wire(Bool()).suggestName("x1927_b914_D7") 
      x1927.r := getRetimed(b914.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1928 = Wire(Bool()).suggestName("x1928_b1165_D7") 
      x1928.r := getRetimed(b1165.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1278_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1278_rd""")
      val x1278_rd_banks = List[UInt](0L.FP(true, 32, 0).r,3L.FP(true, 32, 0).r)
      val x1278_rd_ofs = List[UInt](x1925.r)
      val x1278_rd_en = List[Bool](true.B)
      val x1278_rd_shared_en = ((io.sigsIn.forwardpressure).DS(7.4.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(7.4.toInt, rr, io.sigsIn.backpressure & true.B) && x1926 & x1927 & x1923 & x1928 & x1922 & x1924 ).suggestName("x1278_rd_shared_en")
      x1278_rd.toSeq.zip(x1018_tileC_sram_0.connectRPort(1278, x1278_rd_banks, x1278_rd_ofs, io.sigsIn.backpressure, x1278_rd_en.map(_ && x1278_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x1279 = VecApply(x1278,0)
      val x1279_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1279_elem_0""")
      x1279_elem_0.r := x1278_rd(0).r
      val x1929 = Wire(Bool()).suggestName("x1929_b925_D1") 
      x1929.r := getRetimed(b925.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1930 = Wire(Bool()).suggestName("x1930_b1006_D1") 
      x1930.r := getRetimed(b1006.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1931 = Wire(Bool()).suggestName("x1931_b1271_D1") 
      x1931.r := getRetimed(b1271.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1932 = Wire(Bool()).suggestName("x1932_b1188_D1") 
      x1932.r := getRetimed(b1188.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1933 = Wire(Bool()).suggestName("x1933_b914_D1") 
      x1933.r := getRetimed(b914.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1934 = Wire(Bool()).suggestName("x1934_b1165_D1") 
      x1934.r := getRetimed(b1165.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1282_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1282_rd""")
      val x1282_rd_banks = List[UInt](0L.FP(true, 32, 0).r,0L.FP(true, 32, 0).r)
      val x1282_rd_ofs = List[UInt](0.U)
      val x1282_rd_en = List[Bool](true.B)
      val x1282_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) && x1932 & x1931 & x1934 & x1930 & x1933 & x1929 ).suggestName("x1282_rd_shared_en")
      x1282_rd.toSeq.zip(x934_tileA_sram_0.connectRPort(1282, x1282_rd_banks, x1282_rd_ofs, io.sigsIn.backpressure, x1282_rd_en.map(_ && x1282_rd_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      // x1283 = VecApply(x1282,0)
      val x1283_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1283_elem_0""")
      x1283_elem_0.r := x1282_rd(0).r
      val x1284 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1284""")
      x1284.r := Math.arith_left_shift(b1270, 2, Some(0.2), true.B,"x1284").r
      val x1285_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1285_sum""")
      x1285_sum.r := Math.add(x1284,x1276,Some(1.0), true.B, Truncate, Wrapping, "x1285_sum").r
      val x1286_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1286_rd""")
      val x1286_rd_banks = List[UInt](0L.FP(true, 32, 0).r,3L.FP(true, 32, 0).r)
      val x1286_rd_ofs = List[UInt](x1285_sum.r)
      val x1286_rd_en = List[Bool](true.B)
      val x1286_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && x1932 & x1931 & x1934 & x1930 & x1933 & x1929 ).suggestName("x1286_rd_shared_en")
      x1286_rd.toSeq.zip(x1017_tileB_sram_0.connectRPort(1286, x1286_rd_banks, x1286_rd_ofs, io.sigsIn.backpressure, x1286_rd_en.map(_ && x1286_rd_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      // x1287 = VecApply(x1286,0)
      val x1287_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1287_elem_0""")
      x1287_elem_0.r := x1286_rd(0).r
      val x1288_mul = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1288_mul""")
      x1288_mul.r := (Math.mul(x1283_elem_0, x1287_elem_0, Some(6.0), true.B, Truncate, Wrapping, "x1288_mul")).r
      val x1289_sum = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1289_sum""")
      x1289_sum.r := Math.add(x1279_elem_0,x1288_mul,Some(1.0), true.B, Truncate, Wrapping, "x1289_sum").r
      val x1935 = Wire(Bool()).suggestName("x1935_b925_D10") 
      x1935.r := getRetimed(b925.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1936 = Wire(Bool()).suggestName("x1936_b1006_D10") 
      x1936.r := getRetimed(b1006.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1937 = Wire(Bool()).suggestName("x1937_b1271_D10") 
      x1937.r := getRetimed(b1271.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1938 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1938_x1277_sum_D9") 
      x1938.r := getRetimed(x1277_sum.r, 9.toInt, io.sigsIn.backpressure & true.B)
      val x1939 = Wire(Bool()).suggestName("x1939_b1188_D10") 
      x1939.r := getRetimed(b1188.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1940 = Wire(Bool()).suggestName("x1940_b914_D10") 
      x1940.r := getRetimed(b914.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1941 = Wire(Bool()).suggestName("x1941_b1165_D10") 
      x1941.r := getRetimed(b1165.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1290_wr_banks = List[UInt](0L.FP(true, 32, 0).r,3L.FP(true, 32, 0).r)
      val x1290_wr_ofs = List[UInt](x1938.r)
      val x1290_wr_en = List[Bool](true.B)
      val x1290_wr_data = List[UInt](x1289_sum.r)
      x1019_tileC_sram_1.connectWPort(1290, x1290_wr_banks, x1290_wr_ofs, x1290_wr_data, x1290_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x1940 & x1941 & x1939 & x1935 & x1937 & x1936))
      val x1291_wr_banks = List[UInt](0L.FP(true, 32, 0).r,3L.FP(true, 32, 0).r)
      val x1291_wr_ofs = List[UInt](x1938.r)
      val x1291_wr_en = List[Bool](true.B)
      val x1291_wr_data = List[UInt](x1289_sum.r)
      x1018_tileC_sram_0.connectWPort(1291, x1291_wr_banks, x1291_wr_ofs, x1291_wr_data, x1291_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x1940 & x1941 & x1939 & x1935 & x1937 & x1936))
    }
    val module = Module(new x1292_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1292_inr_Foreach **/
