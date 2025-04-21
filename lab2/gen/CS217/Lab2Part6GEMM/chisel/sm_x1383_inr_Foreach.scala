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

/** Hierarchy: x1383 -> x1407 -> x1408 -> x1637 -> x1638 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1383_inr_Foreach **/
class x1383_inr_Foreach_kernel(
  list_b925: List[Bool],
  list_b1297: List[FixedPoint],
  list_x934_tileA_sram_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 11.4.toInt, myName = "x1383_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(4.0.toInt, 2 + _root_.utils.math.log2Up(4.0.toInt), "x1383_inr_Foreach_iiCtr"))
  
  abstract class x1383_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x934_tileA_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x934_tileA_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b925 = Input(Bool())
      val in_x1017_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1006 = Input(Bool())
      val in_b1297 = Input(new FixedPoint(true, 32, 0))
      val in_b1301 = Input(Bool())
      val in_b1162 = Input(new FixedPoint(true, 32, 0))
      val in_b1166 = Input(Bool())
      val in_x1018_tileC_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b914 = Input(Bool())
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
    def b1297 = {io.in_b1297} 
    def b1301 = {io.in_b1301} 
    def b1162 = {io.in_b1162} 
    def b1166 = {io.in_b1166} 
    def x1018_tileC_sram_0 = {io.in_x1018_tileC_sram_0} ; io.in_x1018_tileC_sram_0 := DontCare
    def b914 = {io.in_b914} 
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
  }
  def connectWires0(module: x1383_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x934_tileA_sram_0.connectLedger(module.io.in_x934_tileA_sram_0)
    module.io.in_b925 <> b925
    x1017_tileB_sram_0.connectLedger(module.io.in_x1017_tileB_sram_0)
    module.io.in_b1006 <> b1006
    module.io.in_b1297 <> b1297
    module.io.in_b1301 <> b1301
    module.io.in_b1162 <> b1162
    module.io.in_b1166 <> b1166
    x1018_tileC_sram_0.connectLedger(module.io.in_x1018_tileC_sram_0)
    module.io.in_b914 <> b914
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
  }
  val b925 = list_b925(0)
  val b1006 = list_b925(1)
  val b1301 = list_b925(2)
  val b1166 = list_b925(3)
  val b914 = list_b925(4)
  val b1297 = list_b1297(0)
  val b1162 = list_b1297(1)
  val x934_tileA_sram_0 = list_x934_tileA_sram_0(0)
  val x1017_tileB_sram_0 = list_x934_tileA_sram_0(1)
  val x1018_tileC_sram_0 = list_x934_tileA_sram_0(2)
  val x1019_tileC_sram_1 = list_x934_tileA_sram_0(3)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1383_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1383_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1383_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1383_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x1383_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x1383_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1383_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1383_instrctr, cycles_x1383_inr_Foreach.io.count, iters_x1383_inr_Foreach.io.count, 0.U, 0.U)
      val b1361 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1361.suggestName("b1361")
      val b1362 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1362.suggestName("b1362")
      val x1365 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1365""")
      x1365.r := Math.arith_right_shift_div(b1162, 2, Some(0.2), true.B,"x1365").r
      val x1366 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1366""")
      x1366.r := Math.arith_left_shift(x1365, 2, Some(0.2), true.B,"x1366").r
      val x1367 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1367""")
      x1367.r := Math.arith_right_shift_div(b1297, 2, Some(0.2), true.B,"x1367").r
      val x1368_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1368_sum""")
      x1368_sum.r := Math.add(x1366,x1367,Some(1.0), true.B, Truncate, Wrapping, "x1368_sum").r
      val x1982 = Wire(Bool()).suggestName("x1982_b1362_D7") 
      x1982.r := getRetimed(b1362.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1983 = Wire(Bool()).suggestName("x1983_b925_D7") 
      x1983.r := getRetimed(b925.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1984 = Wire(Bool()).suggestName("x1984_b1006_D7") 
      x1984.r := getRetimed(b1006.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1985 = Wire(Bool()).suggestName("x1985_b1301_D7") 
      x1985.r := getRetimed(b1301.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1986 = Wire(Bool()).suggestName("x1986_b1166_D7") 
      x1986.r := getRetimed(b1166.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1987 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1987_x1368_sum_D6") 
      x1987.r := getRetimed(x1368_sum.r, 6.toInt, io.sigsIn.backpressure & true.B)
      val x1988 = Wire(Bool()).suggestName("x1988_b914_D7") 
      x1988.r := getRetimed(b914.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1369_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1369_rd""")
      val x1369_rd_banks = List[UInt](1L.FP(true, 32, 0).r,2L.FP(true, 32, 0).r)
      val x1369_rd_ofs = List[UInt](x1987.r)
      val x1369_rd_en = List[Bool](true.B)
      val x1369_rd_shared_en = ((io.sigsIn.forwardpressure).DS(7.4.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(7.4.toInt, rr, io.sigsIn.backpressure & true.B) && x1984 & x1988 & x1985 & x1982 & x1986 & x1983 ).suggestName("x1369_rd_shared_en")
      x1369_rd.toSeq.zip(x1018_tileC_sram_0.connectRPort(1369, x1369_rd_banks, x1369_rd_ofs, io.sigsIn.backpressure, x1369_rd_en.map(_ && x1369_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x1370 = VecApply(x1369,0)
      val x1370_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1370_elem_0""")
      x1370_elem_0.r := x1369_rd(0).r
      val x1373_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1373_rd""")
      val x1373_rd_banks = List[UInt](1L.FP(true, 32, 0).r,0.U)
      val x1373_rd_ofs = List[UInt](0.U)
      val x1373_rd_en = List[Bool](true.B)
      val x1373_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1373_rd_shared_en")
      x1373_rd.toSeq.zip(x934_tileA_sram_0.connectRPort(1373, x1373_rd_banks, x1373_rd_ofs, io.sigsIn.backpressure, x1373_rd_en.map(_ && x1373_rd_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      // x1374 = VecApply(x1373,0)
      val x1374_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1374_elem_0""")
      x1374_elem_0.r := x1373_rd(0).r
      val x1377_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1377_rd""")
      val x1377_rd_banks = List[UInt](0.U,2L.FP(true, 32, 0).r)
      val x1377_rd_ofs = List[UInt](0.U)
      val x1377_rd_en = List[Bool](true.B)
      val x1377_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1377_rd_shared_en")
      x1377_rd.toSeq.zip(x1017_tileB_sram_0.connectRPort(1377, x1377_rd_banks, x1377_rd_ofs, io.sigsIn.backpressure, x1377_rd_en.map(_ && x1377_rd_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      // x1378 = VecApply(x1377,0)
      val x1378_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1378_elem_0""")
      x1378_elem_0.r := x1377_rd(0).r
      val x1379_mul = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1379_mul""")
      x1379_mul.r := (Math.mul(x1374_elem_0, x1378_elem_0, Some(6.0), true.B, Truncate, Wrapping, "x1379_mul")).r
      val x1380_sum = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1380_sum""")
      x1380_sum.r := Math.add(x1370_elem_0,x1379_mul,Some(1.0), true.B, Truncate, Wrapping, "x1380_sum").r
      val x1995 = Wire(Bool()).suggestName("x1995_b1362_D10") 
      x1995.r := getRetimed(b1362.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1996 = Wire(Bool()).suggestName("x1996_b925_D10") 
      x1996.r := getRetimed(b925.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1997 = Wire(Bool()).suggestName("x1997_b1006_D10") 
      x1997.r := getRetimed(b1006.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1998 = Wire(Bool()).suggestName("x1998_b1301_D10") 
      x1998.r := getRetimed(b1301.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1999 = Wire(Bool()).suggestName("x1999_b1166_D10") 
      x1999.r := getRetimed(b1166.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2000 = Wire(new FixedPoint(true, 32, 0)).suggestName("x2000_x1368_sum_D9") 
      x2000.r := getRetimed(x1368_sum.r, 9.toInt, io.sigsIn.backpressure & true.B)
      val x2001 = Wire(Bool()).suggestName("x2001_b914_D10") 
      x2001.r := getRetimed(b914.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1381_wr_banks = List[UInt](1L.FP(true, 32, 0).r,2L.FP(true, 32, 0).r)
      val x1381_wr_ofs = List[UInt](x2000.r)
      val x1381_wr_en = List[Bool](true.B)
      val x1381_wr_data = List[UInt](x1380_sum.r)
      x1019_tileC_sram_1.connectWPort(1381, x1381_wr_banks, x1381_wr_ofs, x1381_wr_data, x1381_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x1999 & x1995 & x2001 & x1996 & x1997 & x1998))
      val x1382_wr_banks = List[UInt](1L.FP(true, 32, 0).r,2L.FP(true, 32, 0).r)
      val x1382_wr_ofs = List[UInt](x2000.r)
      val x1382_wr_en = List[Bool](true.B)
      val x1382_wr_data = List[UInt](x1380_sum.r)
      x1018_tileC_sram_0.connectWPort(1382, x1382_wr_banks, x1382_wr_ofs, x1382_wr_data, x1382_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x1999 & x1995 & x2001 & x1996 & x1997 & x1998))
    }
    val module = Module(new x1383_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1383_inr_Foreach **/
