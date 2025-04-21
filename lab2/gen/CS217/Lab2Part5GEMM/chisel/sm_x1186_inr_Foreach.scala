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

/** Hierarchy: x1186 -> x1187 -> x1188 -> x1335 -> x1250 -> x1251 -> x810 **/
/** BEGIN None x1186_inr_Foreach **/
class x1186_inr_Foreach_kernel(
  list_b1005: List[Bool],
  list_b1156: List[FixedPoint],
  list_x934_tileA_sram_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 11.2.toInt, myName = "x1186_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(4.0.toInt, 2 + _root_.utils.math.log2Up(4.0.toInt), "x1186_inr_Foreach_iiCtr"))
  
  abstract class x1186_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b1005 = Input(Bool())
      val in_x934_tileA_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x934_tileA_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b925 = Input(Bool())
      val in_x1017_tileC_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileC_sram_0_p").asInstanceOf[NBufParams] ))
      val in_x1016_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1016_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1162 = Input(Bool())
      val in_x1018_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_b1156 = Input(new FixedPoint(true, 32, 0))
      val in_b1157 = Input(Bool())
      val in_b914 = Input(Bool())
      val in_b1161 = Input(new FixedPoint(true, 32, 0))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def b1005 = {io.in_b1005} 
    def x934_tileA_sram_0 = {io.in_x934_tileA_sram_0} ; io.in_x934_tileA_sram_0 := DontCare
    def b925 = {io.in_b925} 
    def x1017_tileC_sram_0 = {io.in_x1017_tileC_sram_0} ; io.in_x1017_tileC_sram_0 := DontCare
    def x1016_tileB_sram_0 = {io.in_x1016_tileB_sram_0} ; io.in_x1016_tileB_sram_0 := DontCare
    def b1162 = {io.in_b1162} 
    def x1018_tileC_sram_1 = {io.in_x1018_tileC_sram_1} ; io.in_x1018_tileC_sram_1 := DontCare
    def b1156 = {io.in_b1156} 
    def b1157 = {io.in_b1157} 
    def b914 = {io.in_b914} 
    def b1161 = {io.in_b1161} 
  }
  def connectWires0(module: x1186_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b1005 <> b1005
    x934_tileA_sram_0.connectLedger(module.io.in_x934_tileA_sram_0)
    module.io.in_b925 <> b925
    x1017_tileC_sram_0.connectLedger(module.io.in_x1017_tileC_sram_0)
    x1016_tileB_sram_0.connectLedger(module.io.in_x1016_tileB_sram_0)
    module.io.in_b1162 <> b1162
    x1018_tileC_sram_1.connectLedger(module.io.in_x1018_tileC_sram_1)
    module.io.in_b1156 <> b1156
    module.io.in_b1157 <> b1157
    module.io.in_b914 <> b914
    module.io.in_b1161 <> b1161
  }
  val b1005 = list_b1005(0)
  val b925 = list_b1005(1)
  val b1162 = list_b1005(2)
  val b1157 = list_b1005(3)
  val b914 = list_b1005(4)
  val b1156 = list_b1156(0)
  val b1161 = list_b1156(1)
  val x934_tileA_sram_0 = list_x934_tileA_sram_0(0)
  val x1017_tileC_sram_0 = list_x934_tileA_sram_0(1)
  val x1016_tileB_sram_0 = list_x934_tileA_sram_0(2)
  val x1018_tileC_sram_1 = list_x934_tileA_sram_0(3)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1186_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1186_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1186_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1186_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x1186_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x1186_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1186_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1186_instrctr, cycles_x1186_inr_Foreach.io.count, iters_x1186_inr_Foreach.io.count, 0.U, 0.U)
      val b1166 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1166.suggestName("b1166")
      val b1167 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1167.suggestName("b1167")
      val x1169 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1169""")
      x1169.r := Math.arith_left_shift(b1156, 4, Some(0.2), true.B,"x1169").r
      val x1170_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1170_sum""")
      x1170_sum.r := Math.add(x1169,b1161,Some(1.0), true.B, Truncate, Wrapping, "x1170_sum").r
      val x1381 = Wire(Bool()).suggestName("x1381_b1005_D7") 
      x1381.r := getRetimed(b1005.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1382 = Wire(Bool()).suggestName("x1382_b925_D7") 
      x1382.r := getRetimed(b925.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1383 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1383_x1170_sum_D6") 
      x1383.r := getRetimed(x1170_sum.r, 6.toInt, io.sigsIn.backpressure & true.B)
      val x1384 = Wire(Bool()).suggestName("x1384_b1167_D7") 
      x1384.r := getRetimed(b1167.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1385 = Wire(Bool()).suggestName("x1385_b1162_D7") 
      x1385.r := getRetimed(b1162.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1386 = Wire(Bool()).suggestName("x1386_b1157_D7") 
      x1386.r := getRetimed(b1157.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1387 = Wire(Bool()).suggestName("x1387_b914_D7") 
      x1387.r := getRetimed(b914.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1171_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1171_rd""")
      val x1171_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x1171_rd_ofs = List[UInt](x1383.r)
      val x1171_rd_en = List[Bool](true.B)
      val x1171_rd_shared_en = ((io.sigsIn.forwardpressure).DS(7.2.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(7.2.toInt, rr, io.sigsIn.backpressure & true.B) && x1381 & x1385 & x1382 & x1386 & x1384 & x1387 ).suggestName("x1171_rd_shared_en")
      x1171_rd.toSeq.zip(x1017_tileC_sram_0.connectRPort(1171, x1171_rd_banks, x1171_rd_ofs, io.sigsIn.backpressure, x1171_rd_en.map(_ && x1171_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x1172 = VecApply(x1171,0)
      val x1172_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1172_elem_0""")
      x1172_elem_0.r := x1171_rd(0).r
      val x1174_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1174_sum""")
      x1174_sum.r := Math.add(x1169,b1166,Some(1.0), true.B, Truncate, Wrapping, "x1174_sum").r
      val x1388 = Wire(Bool()).suggestName("x1388_b1005_D1") 
      x1388.r := getRetimed(b1005.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1389 = Wire(Bool()).suggestName("x1389_b925_D1") 
      x1389.r := getRetimed(b925.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1390 = Wire(Bool()).suggestName("x1390_b1167_D1") 
      x1390.r := getRetimed(b1167.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1391 = Wire(Bool()).suggestName("x1391_b1162_D1") 
      x1391.r := getRetimed(b1162.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1392 = Wire(Bool()).suggestName("x1392_b1157_D1") 
      x1392.r := getRetimed(b1157.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1393 = Wire(Bool()).suggestName("x1393_b914_D1") 
      x1393.r := getRetimed(b914.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1175_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1175_rd""")
      val x1175_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x1175_rd_ofs = List[UInt](x1174_sum.r)
      val x1175_rd_en = List[Bool](true.B)
      val x1175_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && x1392 & x1393 & x1389 & x1390 & x1388 & x1391 ).suggestName("x1175_rd_shared_en")
      x1175_rd.toSeq.zip(x934_tileA_sram_0.connectRPort(1175, x1175_rd_banks, x1175_rd_ofs, io.sigsIn.backpressure, x1175_rd_en.map(_ && x1175_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x1176 = VecApply(x1175,0)
      val x1176_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1176_elem_0""")
      x1176_elem_0.r := x1175_rd(0).r
      val x1178 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1178""")
      x1178.r := Math.arith_left_shift(b1166, 4, Some(0.2), true.B,"x1178").r
      val x1179_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1179_sum""")
      x1179_sum.r := Math.add(x1178,b1161,Some(1.0), true.B, Truncate, Wrapping, "x1179_sum").r
      val x1180_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1180_rd""")
      val x1180_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x1180_rd_ofs = List[UInt](x1179_sum.r)
      val x1180_rd_en = List[Bool](true.B)
      val x1180_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && x1392 & x1393 & x1389 & x1390 & x1388 & x1391 ).suggestName("x1180_rd_shared_en")
      x1180_rd.toSeq.zip(x1016_tileB_sram_0.connectRPort(1180, x1180_rd_banks, x1180_rd_ofs, io.sigsIn.backpressure, x1180_rd_en.map(_ && x1180_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x1181 = VecApply(x1180,0)
      val x1181_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1181_elem_0""")
      x1181_elem_0.r := x1180_rd(0).r
      val x1182_mul = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1182_mul""")
      x1182_mul.r := (Math.mul(x1176_elem_0, x1181_elem_0, Some(6.0), true.B, Truncate, Wrapping, "x1182_mul")).r
      val x1183_sum = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1183_sum""")
      x1183_sum.r := Math.add(x1172_elem_0,x1182_mul,Some(1.0), true.B, Truncate, Wrapping, "x1183_sum").r
      val x1394 = Wire(Bool()).suggestName("x1394_b1005_D10") 
      x1394.r := getRetimed(b1005.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1395 = Wire(Bool()).suggestName("x1395_b925_D10") 
      x1395.r := getRetimed(b925.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1396 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1396_x1170_sum_D9") 
      x1396.r := getRetimed(x1170_sum.r, 9.toInt, io.sigsIn.backpressure & true.B)
      val x1397 = Wire(Bool()).suggestName("x1397_b1167_D10") 
      x1397.r := getRetimed(b1167.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1398 = Wire(Bool()).suggestName("x1398_b1162_D10") 
      x1398.r := getRetimed(b1162.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1399 = Wire(Bool()).suggestName("x1399_b1157_D10") 
      x1399.r := getRetimed(b1157.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1400 = Wire(Bool()).suggestName("x1400_b914_D10") 
      x1400.r := getRetimed(b914.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1184_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x1184_wr_ofs = List[UInt](x1396.r)
      val x1184_wr_en = List[Bool](true.B)
      val x1184_wr_data = List[UInt](x1183_sum.r)
      x1018_tileC_sram_1.connectWPort(1184, x1184_wr_banks, x1184_wr_ofs, x1184_wr_data, x1184_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.2.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x1397 & x1394 & x1399 & x1400 & x1395 & x1398))
      val x1185_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x1185_wr_ofs = List[UInt](x1396.r)
      val x1185_wr_en = List[Bool](true.B)
      val x1185_wr_data = List[UInt](x1183_sum.r)
      x1017_tileC_sram_0.connectWPort(1185, x1185_wr_banks, x1185_wr_ofs, x1185_wr_data, x1185_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.2.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x1397 & x1394 & x1399 & x1400 & x1395 & x1398))
    }
    val module = Module(new x1186_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1186_inr_Foreach **/
