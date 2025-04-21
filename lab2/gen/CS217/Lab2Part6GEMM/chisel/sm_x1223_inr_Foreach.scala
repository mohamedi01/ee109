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

/** Hierarchy: x1223 -> x1293 -> x1294 -> x1637 -> x1638 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1223_inr_Foreach **/
class x1223_inr_Foreach_kernel(
  list_b925: List[Bool],
  list_b1181: List[FixedPoint],
  list_x934_tileA_sram_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 11.4.toInt, myName = "x1223_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(4.0.toInt, 2 + _root_.utils.math.log2Up(4.0.toInt), "x1223_inr_Foreach_iiCtr"))
  
  abstract class x1223_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x934_tileA_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x934_tileA_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b925 = Input(Bool())
      val in_x1017_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1006 = Input(Bool())
      val in_b1185 = Input(Bool())
      val in_x1018_tileC_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1181 = Input(new FixedPoint(true, 32, 0))
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
    def b1185 = {io.in_b1185} 
    def x1018_tileC_sram_0 = {io.in_x1018_tileC_sram_0} ; io.in_x1018_tileC_sram_0 := DontCare
    def b1181 = {io.in_b1181} 
    def b914 = {io.in_b914} 
    def b1161 = {io.in_b1161} 
    def b1165 = {io.in_b1165} 
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
  }
  def connectWires0(module: x1223_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x934_tileA_sram_0.connectLedger(module.io.in_x934_tileA_sram_0)
    module.io.in_b925 <> b925
    x1017_tileB_sram_0.connectLedger(module.io.in_x1017_tileB_sram_0)
    module.io.in_b1006 <> b1006
    module.io.in_b1185 <> b1185
    x1018_tileC_sram_0.connectLedger(module.io.in_x1018_tileC_sram_0)
    module.io.in_b1181 <> b1181
    module.io.in_b914 <> b914
    module.io.in_b1161 <> b1161
    module.io.in_b1165 <> b1165
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
  }
  val b925 = list_b925(0)
  val b1006 = list_b925(1)
  val b1185 = list_b925(2)
  val b914 = list_b925(3)
  val b1165 = list_b925(4)
  val b1181 = list_b1181(0)
  val b1161 = list_b1181(1)
  val x934_tileA_sram_0 = list_x934_tileA_sram_0(0)
  val x1017_tileB_sram_0 = list_x934_tileA_sram_0(1)
  val x1018_tileC_sram_0 = list_x934_tileA_sram_0(2)
  val x1019_tileC_sram_1 = list_x934_tileA_sram_0(3)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1223_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1223_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1223_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1223_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x1223_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x1223_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1223_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1223_instrctr, cycles_x1223_inr_Foreach.io.count, iters_x1223_inr_Foreach.io.count, 0.U, 0.U)
      val b1201 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1201.suggestName("b1201")
      val b1202 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1202.suggestName("b1202")
      val x1205 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1205""")
      x1205.r := Math.arith_right_shift_div(b1161, 2, Some(0.2), true.B,"x1205").r
      val x1206 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1206""")
      x1206.r := Math.arith_left_shift(x1205, 2, Some(0.2), true.B,"x1206").r
      val x1207 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1207""")
      x1207.r := Math.arith_right_shift_div(b1181, 2, Some(0.2), true.B,"x1207").r
      val x1208_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1208_sum""")
      x1208_sum.r := Math.add(x1206,x1207,Some(1.0), true.B, Truncate, Wrapping, "x1208_sum").r
      val x1862 = Wire(Bool()).suggestName("x1862_b925_D7") 
      x1862.r := getRetimed(b925.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1863 = Wire(Bool()).suggestName("x1863_b1006_D7") 
      x1863.r := getRetimed(b1006.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1864 = Wire(Bool()).suggestName("x1864_b1202_D7") 
      x1864.r := getRetimed(b1202.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1865 = Wire(Bool()).suggestName("x1865_b1185_D7") 
      x1865.r := getRetimed(b1185.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1866 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1866_x1208_sum_D6") 
      x1866.r := getRetimed(x1208_sum.r, 6.toInt, io.sigsIn.backpressure & true.B)
      val x1867 = Wire(Bool()).suggestName("x1867_b914_D7") 
      x1867.r := getRetimed(b914.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1868 = Wire(Bool()).suggestName("x1868_b1165_D7") 
      x1868.r := getRetimed(b1165.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1209_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1209_rd""")
      val x1209_rd_banks = List[UInt](0L.FP(true, 32, 0).r,0L.FP(true, 32, 0).r)
      val x1209_rd_ofs = List[UInt](x1866.r)
      val x1209_rd_en = List[Bool](true.B)
      val x1209_rd_shared_en = ((io.sigsIn.forwardpressure).DS(7.4.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(7.4.toInt, rr, io.sigsIn.backpressure & true.B) && x1868 & x1865 & x1862 & x1863 & x1867 & x1864 ).suggestName("x1209_rd_shared_en")
      x1209_rd.toSeq.zip(x1018_tileC_sram_0.connectRPort(1209, x1209_rd_banks, x1209_rd_ofs, io.sigsIn.backpressure, x1209_rd_en.map(_ && x1209_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x1210 = VecApply(x1209,0)
      val x1210_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1210_elem_0""")
      x1210_elem_0.r := x1209_rd(0).r
      val x1211 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1211""")
      x1211.r := Math.arith_left_shift(x1205, 4, Some(0.2), true.B,"x1211").r
      val x1212_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1212_sum""")
      x1212_sum.r := Math.add(x1211,b1201,Some(1.0), true.B, Truncate, Wrapping, "x1212_sum").r
      val x1869 = Wire(Bool()).suggestName("x1869_b925_D1") 
      x1869.r := getRetimed(b925.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1870 = Wire(Bool()).suggestName("x1870_b1006_D1") 
      x1870.r := getRetimed(b1006.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1871 = Wire(Bool()).suggestName("x1871_b1202_D1") 
      x1871.r := getRetimed(b1202.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1872 = Wire(Bool()).suggestName("x1872_b1185_D1") 
      x1872.r := getRetimed(b1185.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1873 = Wire(Bool()).suggestName("x1873_b914_D1") 
      x1873.r := getRetimed(b914.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1874 = Wire(Bool()).suggestName("x1874_b1165_D1") 
      x1874.r := getRetimed(b1165.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1213_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1213_rd""")
      val x1213_rd_banks = List[UInt](0L.FP(true, 32, 0).r,0L.FP(true, 32, 0).r)
      val x1213_rd_ofs = List[UInt](x1212_sum.r)
      val x1213_rd_en = List[Bool](true.B)
      val x1213_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) && x1872 & x1869 & x1873 & x1874 & x1870 & x1871 ).suggestName("x1213_rd_shared_en")
      x1213_rd.toSeq.zip(x934_tileA_sram_0.connectRPort(1213, x1213_rd_banks, x1213_rd_ofs, io.sigsIn.backpressure, x1213_rd_en.map(_ && x1213_rd_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      // x1214 = VecApply(x1213,0)
      val x1214_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1214_elem_0""")
      x1214_elem_0.r := x1213_rd(0).r
      val x1215 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1215""")
      x1215.r := Math.arith_left_shift(b1201, 2, Some(0.2), true.B,"x1215").r
      val x1216_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1216_sum""")
      x1216_sum.r := Math.add(x1215,x1207,Some(1.0), true.B, Truncate, Wrapping, "x1216_sum").r
      val x1217_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1217_rd""")
      val x1217_rd_banks = List[UInt](0L.FP(true, 32, 0).r,0L.FP(true, 32, 0).r)
      val x1217_rd_ofs = List[UInt](x1216_sum.r)
      val x1217_rd_en = List[Bool](true.B)
      val x1217_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && x1872 & x1869 & x1873 & x1874 & x1870 & x1871 ).suggestName("x1217_rd_shared_en")
      x1217_rd.toSeq.zip(x1017_tileB_sram_0.connectRPort(1217, x1217_rd_banks, x1217_rd_ofs, io.sigsIn.backpressure, x1217_rd_en.map(_ && x1217_rd_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      // x1218 = VecApply(x1217,0)
      val x1218_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1218_elem_0""")
      x1218_elem_0.r := x1217_rd(0).r
      val x1219_mul = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1219_mul""")
      x1219_mul.r := (Math.mul(x1214_elem_0, x1218_elem_0, Some(6.0), true.B, Truncate, Wrapping, "x1219_mul")).r
      val x1220_sum = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1220_sum""")
      x1220_sum.r := Math.add(x1210_elem_0,x1219_mul,Some(1.0), true.B, Truncate, Wrapping, "x1220_sum").r
      val x1875 = Wire(Bool()).suggestName("x1875_b925_D10") 
      x1875.r := getRetimed(b925.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1876 = Wire(Bool()).suggestName("x1876_b1006_D10") 
      x1876.r := getRetimed(b1006.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1877 = Wire(Bool()).suggestName("x1877_b1202_D10") 
      x1877.r := getRetimed(b1202.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1878 = Wire(Bool()).suggestName("x1878_b1185_D10") 
      x1878.r := getRetimed(b1185.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1879 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1879_x1208_sum_D9") 
      x1879.r := getRetimed(x1208_sum.r, 9.toInt, io.sigsIn.backpressure & true.B)
      val x1880 = Wire(Bool()).suggestName("x1880_b914_D10") 
      x1880.r := getRetimed(b914.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1881 = Wire(Bool()).suggestName("x1881_b1165_D10") 
      x1881.r := getRetimed(b1165.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1221_wr_banks = List[UInt](0L.FP(true, 32, 0).r,0L.FP(true, 32, 0).r)
      val x1221_wr_ofs = List[UInt](x1879.r)
      val x1221_wr_en = List[Bool](true.B)
      val x1221_wr_data = List[UInt](x1220_sum.r)
      x1019_tileC_sram_1.connectWPort(1221, x1221_wr_banks, x1221_wr_ofs, x1221_wr_data, x1221_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x1876 & x1877 & x1881 & x1878 & x1880 & x1875))
      val x1222_wr_banks = List[UInt](0L.FP(true, 32, 0).r,0L.FP(true, 32, 0).r)
      val x1222_wr_ofs = List[UInt](x1879.r)
      val x1222_wr_en = List[Bool](true.B)
      val x1222_wr_data = List[UInt](x1220_sum.r)
      x1018_tileC_sram_0.connectWPort(1222, x1222_wr_banks, x1222_wr_ofs, x1222_wr_data, x1222_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x1876 & x1877 & x1881 & x1878 & x1880 & x1875))
    }
    val module = Module(new x1223_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1223_inr_Foreach **/
