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

/** Hierarchy: x1406 -> x1407 -> x1408 -> x1637 -> x1638 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1406_inr_Foreach **/
class x1406_inr_Foreach_kernel(
  list_b1302: List[Bool],
  list_b1298: List[FixedPoint],
  list_x934_tileA_sram_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 11.4.toInt, myName = "x1406_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(4.0.toInt, 2 + _root_.utils.math.log2Up(4.0.toInt), "x1406_inr_Foreach_iiCtr"))
  
  abstract class x1406_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b1298 = Input(new FixedPoint(true, 32, 0))
      val in_x934_tileA_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x934_tileA_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1302 = Input(Bool())
      val in_b925 = Input(Bool())
      val in_x1017_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1006 = Input(Bool())
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
    def b1298 = {io.in_b1298} 
    def x934_tileA_sram_0 = {io.in_x934_tileA_sram_0} ; io.in_x934_tileA_sram_0 := DontCare
    def b1302 = {io.in_b1302} 
    def b925 = {io.in_b925} 
    def x1017_tileB_sram_0 = {io.in_x1017_tileB_sram_0} ; io.in_x1017_tileB_sram_0 := DontCare
    def b1006 = {io.in_b1006} 
    def b1162 = {io.in_b1162} 
    def b1166 = {io.in_b1166} 
    def x1018_tileC_sram_0 = {io.in_x1018_tileC_sram_0} ; io.in_x1018_tileC_sram_0 := DontCare
    def b914 = {io.in_b914} 
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
  }
  def connectWires0(module: x1406_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b1298 <> b1298
    x934_tileA_sram_0.connectLedger(module.io.in_x934_tileA_sram_0)
    module.io.in_b1302 <> b1302
    module.io.in_b925 <> b925
    x1017_tileB_sram_0.connectLedger(module.io.in_x1017_tileB_sram_0)
    module.io.in_b1006 <> b1006
    module.io.in_b1162 <> b1162
    module.io.in_b1166 <> b1166
    x1018_tileC_sram_0.connectLedger(module.io.in_x1018_tileC_sram_0)
    module.io.in_b914 <> b914
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
  }
  val b1302 = list_b1302(0)
  val b925 = list_b1302(1)
  val b1006 = list_b1302(2)
  val b1166 = list_b1302(3)
  val b914 = list_b1302(4)
  val b1298 = list_b1298(0)
  val b1162 = list_b1298(1)
  val x934_tileA_sram_0 = list_x934_tileA_sram_0(0)
  val x1017_tileB_sram_0 = list_x934_tileA_sram_0(1)
  val x1018_tileC_sram_0 = list_x934_tileA_sram_0(2)
  val x1019_tileC_sram_1 = list_x934_tileA_sram_0(3)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1406_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1406_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1406_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1406_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x1406_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x1406_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1406_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1406_instrctr, cycles_x1406_inr_Foreach.io.count, iters_x1406_inr_Foreach.io.count, 0.U, 0.U)
      val b1384 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1384.suggestName("b1384")
      val b1385 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1385.suggestName("b1385")
      val x1388 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1388""")
      x1388.r := Math.arith_right_shift_div(b1162, 2, Some(0.2), true.B,"x1388").r
      val x1389 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1389""")
      x1389.r := Math.arith_left_shift(x1388, 2, Some(0.2), true.B,"x1389").r
      val x1390 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1390""")
      x1390.r := Math.arith_right_shift_div(b1298, 2, Some(0.2), true.B,"x1390").r
      val x1391_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1391_sum""")
      x1391_sum.r := Math.add(x1389,x1390,Some(1.0), true.B, Truncate, Wrapping, "x1391_sum").r
      val x2002 = Wire(new FixedPoint(true, 32, 0)).suggestName("x2002_x1391_sum_D6") 
      x2002.r := getRetimed(x1391_sum.r, 6.toInt, io.sigsIn.backpressure & true.B)
      val x2003 = Wire(Bool()).suggestName("x2003_b1302_D7") 
      x2003.r := getRetimed(b1302.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2004 = Wire(Bool()).suggestName("x2004_b925_D7") 
      x2004.r := getRetimed(b925.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2005 = Wire(Bool()).suggestName("x2005_b1006_D7") 
      x2005.r := getRetimed(b1006.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2006 = Wire(Bool()).suggestName("x2006_b1166_D7") 
      x2006.r := getRetimed(b1166.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2007 = Wire(Bool()).suggestName("x2007_b1385_D7") 
      x2007.r := getRetimed(b1385.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x2008 = Wire(Bool()).suggestName("x2008_b914_D7") 
      x2008.r := getRetimed(b914.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1392_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1392_rd""")
      val x1392_rd_banks = List[UInt](1L.FP(true, 32, 0).r,3L.FP(true, 32, 0).r)
      val x1392_rd_ofs = List[UInt](x2002.r)
      val x1392_rd_en = List[Bool](true.B)
      val x1392_rd_shared_en = ((io.sigsIn.forwardpressure).DS(7.4.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(7.4.toInt, rr, io.sigsIn.backpressure & true.B) && x2004 & x2005 & x2008 & x2006 & x2007 & x2003 ).suggestName("x1392_rd_shared_en")
      x1392_rd.toSeq.zip(x1018_tileC_sram_0.connectRPort(1392, x1392_rd_banks, x1392_rd_ofs, io.sigsIn.backpressure, x1392_rd_en.map(_ && x1392_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x1393 = VecApply(x1392,0)
      val x1393_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1393_elem_0""")
      x1393_elem_0.r := x1392_rd(0).r
      val x1396_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1396_rd""")
      val x1396_rd_banks = List[UInt](1L.FP(true, 32, 0).r,0.U)
      val x1396_rd_ofs = List[UInt](0.U)
      val x1396_rd_en = List[Bool](true.B)
      val x1396_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1396_rd_shared_en")
      x1396_rd.toSeq.zip(x934_tileA_sram_0.connectRPort(1396, x1396_rd_banks, x1396_rd_ofs, io.sigsIn.backpressure, x1396_rd_en.map(_ && x1396_rd_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      // x1397 = VecApply(x1396,0)
      val x1397_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1397_elem_0""")
      x1397_elem_0.r := x1396_rd(0).r
      val x1400_rd = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1400_rd""")
      val x1400_rd_banks = List[UInt](0.U,3L.FP(true, 32, 0).r)
      val x1400_rd_ofs = List[UInt](0.U)
      val x1400_rd_en = List[Bool](true.B)
      val x1400_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1400_rd_shared_en")
      x1400_rd.toSeq.zip(x1017_tileB_sram_0.connectRPort(1400, x1400_rd_banks, x1400_rd_ofs, io.sigsIn.backpressure, x1400_rd_en.map(_ && x1400_rd_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      // x1401 = VecApply(x1400,0)
      val x1401_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1401_elem_0""")
      x1401_elem_0.r := x1400_rd(0).r
      val x1402_mul = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1402_mul""")
      x1402_mul.r := (Math.mul(x1397_elem_0, x1401_elem_0, Some(6.0), true.B, Truncate, Wrapping, "x1402_mul")).r
      val x1403_sum = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1403_sum""")
      x1403_sum.r := Math.add(x1393_elem_0,x1402_mul,Some(1.0), true.B, Truncate, Wrapping, "x1403_sum").r
      val x2015 = Wire(new FixedPoint(true, 32, 0)).suggestName("x2015_x1391_sum_D9") 
      x2015.r := getRetimed(x1391_sum.r, 9.toInt, io.sigsIn.backpressure & true.B)
      val x2016 = Wire(Bool()).suggestName("x2016_b1302_D10") 
      x2016.r := getRetimed(b1302.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2017 = Wire(Bool()).suggestName("x2017_b925_D10") 
      x2017.r := getRetimed(b925.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2018 = Wire(Bool()).suggestName("x2018_b1006_D10") 
      x2018.r := getRetimed(b1006.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2019 = Wire(Bool()).suggestName("x2019_b1166_D10") 
      x2019.r := getRetimed(b1166.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2020 = Wire(Bool()).suggestName("x2020_b1385_D10") 
      x2020.r := getRetimed(b1385.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x2021 = Wire(Bool()).suggestName("x2021_b914_D10") 
      x2021.r := getRetimed(b914.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1404_wr_banks = List[UInt](1L.FP(true, 32, 0).r,3L.FP(true, 32, 0).r)
      val x1404_wr_ofs = List[UInt](x2015.r)
      val x1404_wr_en = List[Bool](true.B)
      val x1404_wr_data = List[UInt](x1403_sum.r)
      x1019_tileC_sram_1.connectWPort(1404, x1404_wr_banks, x1404_wr_ofs, x1404_wr_data, x1404_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x2021 & x2019 & x2016 & x2017 & x2018 & x2020))
      val x1405_wr_banks = List[UInt](1L.FP(true, 32, 0).r,3L.FP(true, 32, 0).r)
      val x1405_wr_ofs = List[UInt](x2015.r)
      val x1405_wr_en = List[Bool](true.B)
      val x1405_wr_data = List[UInt](x1403_sum.r)
      x1018_tileC_sram_0.connectWPort(1405, x1405_wr_banks, x1405_wr_ofs, x1405_wr_data, x1405_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x2021 & x2019 & x2016 & x2017 & x2018 & x2020))
    }
    val module = Module(new x1406_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1406_inr_Foreach **/
