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

/** Hierarchy: x1337 -> x1407 -> x1408 -> x1637 -> x1638 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1337_inr_Foreach **/
class x1337_inr_Foreach_kernel(
  list_b925: List[Bool],
  list_b1295: List[FixedPoint],
  list_x934_tileA_sram_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 11.4.toInt, myName = "x1337_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(4.0.toInt, 2 + _root_.utils.math.log2Up(4.0.toInt), "x1337_inr_Foreach_iiCtr"))
  
  abstract class x1337_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b1295 = Input(new FixedPoint(true, 32, 0))
      val in_x934_tileA_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x934_tileA_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b925 = Input(Bool())
      val in_x1017_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1006 = Input(Bool())
      val in_b1162 = Input(new FixedPoint(true, 32, 0))
      val in_b1166 = Input(Bool())
      val in_x1018_tileC_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b914 = Input(Bool())
      val in_x1019_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1019_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_b1299 = Input(Bool())
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def b1295 = {io.in_b1295} 
    def x934_tileA_sram_0 = {io.in_x934_tileA_sram_0} ; io.in_x934_tileA_sram_0 := DontCare
    def b925 = {io.in_b925} 
    def x1017_tileB_sram_0 = {io.in_x1017_tileB_sram_0} ; io.in_x1017_tileB_sram_0 := DontCare
    def b1006 = {io.in_b1006} 
    def b1162 = {io.in_b1162} 
    def b1166 = {io.in_b1166} 
    def x1018_tileC_sram_0 = {io.in_x1018_tileC_sram_0} ; io.in_x1018_tileC_sram_0 := DontCare
    def b914 = {io.in_b914} 
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
    def b1299 = {io.in_b1299} 
  }
  def connectWires0(module: x1337_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b1295 <> b1295
    x934_tileA_sram_0.connectLedger(module.io.in_x934_tileA_sram_0)
    module.io.in_b925 <> b925
    x1017_tileB_sram_0.connectLedger(module.io.in_x1017_tileB_sram_0)
    module.io.in_b1006 <> b1006
    module.io.in_b1162 <> b1162
    module.io.in_b1166 <> b1166
    x1018_tileC_sram_0.connectLedger(module.io.in_x1018_tileC_sram_0)
    module.io.in_b914 <> b914
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
    module.io.in_b1299 <> b1299
  }
  val b925 = list_b925(0)
  val b1006 = list_b925(1)
  val b1166 = list_b925(2)
  val b914 = list_b925(3)
  val b1299 = list_b925(4)
  val b1295 = list_b1295(0)
  val b1162 = list_b1295(1)
  val x934_tileA_sram_0 = list_x934_tileA_sram_0(0)
  val x1017_tileB_sram_0 = list_x934_tileA_sram_0(1)
  val x1018_tileC_sram_0 = list_x934_tileA_sram_0(2)
  val x1019_tileC_sram_1 = list_x934_tileA_sram_0(3)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1337_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1337_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1337_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1337_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x1337_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x1337_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1337_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1337_instrctr, cycles_x1337_inr_Foreach.io.count, iters_x1337_inr_Foreach.io.count, 0.U, 0.U)
      val b1315 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1315.suggestName("b1315")
      val b1316 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1316.suggestName("b1316")
      val x1319 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1319""")
      x1319.r := Math.arith_right_shift_div(b1162, 2, Some(0.2), true.B,"x1319").r
      val x1320 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1320""")
      x1320.r := Math.arith_left_shift(x1319, 2, Some(0.2), true.B,"x1320").r
      val x1321 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1321""")
      x1321.r := Math.arith_right_shift_div(b1295, 2, Some(0.2), true.B,"x1321").r
      val x1322_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1322_sum""")
      x1322_sum.r := Math.add(x1320,x1321,Some(1.0), true.B, Truncate, Wrapping, "x1322_sum").r
      val x1942 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1942_x1322_sum_D6") 
      x1942.r := getRetimed(x1322_sum.r, 6.toInt, io.sigsIn.backpressure & true.B)
      val x1943 = Wire(Bool()).suggestName("x1943_b925_D7") 
      x1943.r := getRetimed(b925.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1944 = Wire(Bool()).suggestName("x1944_b1006_D7") 
      x1944.r := getRetimed(b1006.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1945 = Wire(Bool()).suggestName("x1945_b1166_D7") 
      x1945.r := getRetimed(b1166.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1946 = Wire(Bool()).suggestName("x1946_b1316_D7") 
      x1946.r := getRetimed(b1316.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1947 = Wire(Bool()).suggestName("x1947_b914_D7") 
      x1947.r := getRetimed(b914.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1948 = Wire(Bool()).suggestName("x1948_b1299_D7") 
      x1948.r := getRetimed(b1299.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1323_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1323_rd""")
      val x1323_rd_banks = List[UInt](1L.FP(true, 32, 0).r,0L.FP(true, 32, 0).r)
      val x1323_rd_ofs = List[UInt](x1942.r)
      val x1323_rd_en = List[Bool](true.B)
      val x1323_rd_shared_en = ((io.sigsIn.forwardpressure).DS(7.4.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(7.4.toInt, rr, io.sigsIn.backpressure & true.B) && x1947 & x1944 & x1946 & x1945 & x1943 & x1948 ).suggestName("x1323_rd_shared_en")
      x1323_rd.toSeq.zip(x1018_tileC_sram_0.connectRPort(1323, x1323_rd_banks, x1323_rd_ofs, io.sigsIn.backpressure, x1323_rd_en.map(_ && x1323_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x1324 = VecApply(x1323,0)
      val x1324_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1324_elem_0""")
      x1324_elem_0.r := x1323_rd(0).r
      val x1325 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1325""")
      x1325.r := Math.arith_left_shift(x1319, 4, Some(0.2), true.B,"x1325").r
      val x1326_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1326_sum""")
      x1326_sum.r := Math.add(x1325,b1315,Some(1.0), true.B, Truncate, Wrapping, "x1326_sum").r
      val x1949 = Wire(Bool()).suggestName("x1949_b925_D1") 
      x1949.r := getRetimed(b925.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1950 = Wire(Bool()).suggestName("x1950_b1006_D1") 
      x1950.r := getRetimed(b1006.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1951 = Wire(Bool()).suggestName("x1951_b1166_D1") 
      x1951.r := getRetimed(b1166.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1952 = Wire(Bool()).suggestName("x1952_b1316_D1") 
      x1952.r := getRetimed(b1316.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1953 = Wire(Bool()).suggestName("x1953_b914_D1") 
      x1953.r := getRetimed(b914.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1954 = Wire(Bool()).suggestName("x1954_b1299_D1") 
      x1954.r := getRetimed(b1299.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1327_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1327_rd""")
      val x1327_rd_banks = List[UInt](1L.FP(true, 32, 0).r,0L.FP(true, 32, 0).r)
      val x1327_rd_ofs = List[UInt](x1326_sum.r)
      val x1327_rd_en = List[Bool](true.B)
      val x1327_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) && x1951 & x1950 & x1952 & x1953 & x1954 & x1949 ).suggestName("x1327_rd_shared_en")
      x1327_rd.toSeq.zip(x934_tileA_sram_0.connectRPort(1327, x1327_rd_banks, x1327_rd_ofs, io.sigsIn.backpressure, x1327_rd_en.map(_ && x1327_rd_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      // x1328 = VecApply(x1327,0)
      val x1328_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1328_elem_0""")
      x1328_elem_0.r := x1327_rd(0).r
      val x1331_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1331_rd""")
      val x1331_rd_banks = List[UInt](0.U,0L.FP(true, 32, 0).r)
      val x1331_rd_ofs = List[UInt](0.U)
      val x1331_rd_en = List[Bool](true.B)
      val x1331_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && x1951 & x1950 & x1952 & x1953 & x1954 & x1949 ).suggestName("x1331_rd_shared_en")
      x1331_rd.toSeq.zip(x1017_tileB_sram_0.connectRPort(1331, x1331_rd_banks, x1331_rd_ofs, io.sigsIn.backpressure, x1331_rd_en.map(_ && x1331_rd_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      // x1332 = VecApply(x1331,0)
      val x1332_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1332_elem_0""")
      x1332_elem_0.r := x1331_rd(0).r
      val x1333_mul = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1333_mul""")
      x1333_mul.r := (Math.mul(x1328_elem_0, x1332_elem_0, Some(6.0), true.B, Truncate, Wrapping, "x1333_mul")).r
      val x1334_sum = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1334_sum""")
      x1334_sum.r := Math.add(x1324_elem_0,x1333_mul,Some(1.0), true.B, Truncate, Wrapping, "x1334_sum").r
      val x1955 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1955_x1322_sum_D9") 
      x1955.r := getRetimed(x1322_sum.r, 9.toInt, io.sigsIn.backpressure & true.B)
      val x1956 = Wire(Bool()).suggestName("x1956_b925_D10") 
      x1956.r := getRetimed(b925.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1957 = Wire(Bool()).suggestName("x1957_b1006_D10") 
      x1957.r := getRetimed(b1006.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1958 = Wire(Bool()).suggestName("x1958_b1166_D10") 
      x1958.r := getRetimed(b1166.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1959 = Wire(Bool()).suggestName("x1959_b1316_D10") 
      x1959.r := getRetimed(b1316.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1960 = Wire(Bool()).suggestName("x1960_b914_D10") 
      x1960.r := getRetimed(b914.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1961 = Wire(Bool()).suggestName("x1961_b1299_D10") 
      x1961.r := getRetimed(b1299.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1335_wr_banks = List[UInt](1L.FP(true, 32, 0).r,0L.FP(true, 32, 0).r)
      val x1335_wr_ofs = List[UInt](x1955.r)
      val x1335_wr_en = List[Bool](true.B)
      val x1335_wr_data = List[UInt](x1334_sum.r)
      x1019_tileC_sram_1.connectWPort(1335, x1335_wr_banks, x1335_wr_ofs, x1335_wr_data, x1335_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x1958 & x1961 & x1957 & x1956 & x1959 & x1960))
      val x1336_wr_banks = List[UInt](1L.FP(true, 32, 0).r,0L.FP(true, 32, 0).r)
      val x1336_wr_ofs = List[UInt](x1955.r)
      val x1336_wr_en = List[Bool](true.B)
      val x1336_wr_data = List[UInt](x1334_sum.r)
      x1018_tileC_sram_0.connectWPort(1336, x1336_wr_banks, x1336_wr_ofs, x1336_wr_data, x1336_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x1958 & x1961 & x1957 & x1956 & x1959 & x1960))
    }
    val module = Module(new x1337_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1337_inr_Foreach **/
