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

/** Hierarchy: x999 -> x1000 -> x1001 -> x1250 -> x1251 -> x810 **/
/** BEGIN None x999_inr_Foreach **/
class x999_inr_Foreach_kernel(
  list_x937: List[DecoupledIO[AppLoadData]],
  list_x934_tileA_sram_0: List[NBufInterface],
  list_b968: List[FixedPoint],
  list_x970_reg: List[StandardInterface],
  list_b969: List[Bool],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 3.0.toInt, myName = "x999_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x999_inr_Foreach_iiCtr"))
  
  abstract class x999_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x934_tileA_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x934_tileA_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b969 = Input(Bool())
      val in_x937 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x937_p").asInstanceOf[(Int, Int)] )))
      val in_x970_reg = Flipped(new StandardInterface(ModuleParams.getParams("x970_reg_p").asInstanceOf[MemParams] ))
      val in_b968 = Input(new FixedPoint(true, 32, 0))
      val in_x971_reg = Flipped(new StandardInterface(ModuleParams.getParams("x971_reg_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x934_tileA_sram_0 = {io.in_x934_tileA_sram_0} ; io.in_x934_tileA_sram_0 := DontCare
    def b969 = {io.in_b969} 
    def x937 = {io.in_x937} 
    def x970_reg = {io.in_x970_reg} ; io.in_x970_reg := DontCare
    def b968 = {io.in_b968} 
    def x971_reg = {io.in_x971_reg} ; io.in_x971_reg := DontCare
  }
  def connectWires0(module: x999_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x934_tileA_sram_0.connectLedger(module.io.in_x934_tileA_sram_0)
    module.io.in_b969 <> b969
    module.io.in_x937 <> x937
    x970_reg.connectLedger(module.io.in_x970_reg)
    module.io.in_b968 <> b968
    x971_reg.connectLedger(module.io.in_x971_reg)
  }
  val x937 = list_x937(0)
  val x934_tileA_sram_0 = list_x934_tileA_sram_0(0)
  val b968 = list_b968(0)
  val x970_reg = list_x970_reg(0)
  val x971_reg = list_x970_reg(1)
  val b969 = list_b969(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x999_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x999_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x999_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x999_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x999_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x999_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x999_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x999_inr_Foreach = Module(new InstrumentationCounter())
      val idles_x999_inr_Foreach = Module(new InstrumentationCounter())
      stalls_x999_inr_Foreach.io.enable := io.sigsIn.baseEn & ~(true.B)
      idles_x999_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((x937.valid) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X999_instrctr, cycles_x999_inr_Foreach.io.count, iters_x999_inr_Foreach.io.count, stalls_x999_inr_Foreach.io.count, idles_x999_inr_Foreach.io.count)
      val b985 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b985.suggestName("b985")
      val b986 = ~io.sigsIn.cchainOutputs.head.oobs(0); b986.suggestName("b986")
      val x987_rd_x970 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x987_rd_x970""")
      val x987_rd_x970_banks = List[UInt]()
      val x987_rd_x970_ofs = List[UInt]()
      val x987_rd_x970_en = List[Bool](true.B)
      val x987_rd_x970_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x987_rd_x970_shared_en")
      x987_rd_x970.toSeq.zip(x970_reg.connectRPort(987, x987_rd_x970_banks, x987_rd_x970_ofs, io.sigsIn.backpressure, x987_rd_x970_en.map(_ && x987_rd_x970_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x988 = Wire(Bool()).suggestName("""x988""")
      x988.r := Math.lte(x987_rd_x970, b985, Some(0.4), true.B,"x988").r
      val x989_rd_x971 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x989_rd_x971""")
      val x989_rd_x971_banks = List[UInt]()
      val x989_rd_x971_ofs = List[UInt]()
      val x989_rd_x971_en = List[Bool](true.B)
      val x989_rd_x971_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x989_rd_x971_shared_en")
      x989_rd_x971.toSeq.zip(x971_reg.connectRPort(989, x989_rd_x971_banks, x989_rd_x971_ofs, io.sigsIn.backpressure, x989_rd_x971_en.map(_ && x989_rd_x971_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x990 = Wire(Bool()).suggestName("""x990""")
      x990.r := Math.lt(b985, x989_rd_x971, Some(0.4), true.B,"x990").r
      val x991 = Wire(Bool()).suggestName("""x991""")
      x991 := x988 & x990
      val x992_sub = Wire(new FixedPoint(true, 32, 0)).suggestName("""x992_sub""")
      x992_sub.r := Math.sub(b985,x987_rd_x970,Some(1.0), true.B, Truncate, Wrapping, "x992_sub").r
      val x993 = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x993""")
      x937.ready := b986 & b969 & (io.sigsIn.datapathEn) 
      (0 until 1).map{ i => x993(i).r := x937.bits.rdata(i).r }
      val x1345 = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("x1345_x993_D1") 
      (0 until 1).foreach{i => x1345(i).r := getRetimed(x993(i).r, 1.toInt, io.sigsIn.backpressure & true.B)}
      // x994 = VecApply(x1345,0)
      val x994_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x994_elem_0""")
      x994_elem_0.r := x1345(0).r
      val x996 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x996""")
      x996.r := Math.arith_left_shift(b968, 4, Some(0.2), true.B,"x996").r
      val x1346 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1346_x996_D1") 
      x1346.r := getRetimed(x996.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x997_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x997_sum""")
      x997_sum.r := Math.add(x1346,x992_sub,Some(1.0), true.B, Truncate, Wrapping, "x997_sum").r
      val x1347 = Wire(new FixedPoint(true, 24, 8)).suggestName("x1347_x994_elem_0_D1") 
      x1347.r := getRetimed(x994_elem_0.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1348 = Wire(Bool()).suggestName("x1348_b969_D2") 
      x1348.r := getRetimed(b969.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x1349 = Wire(Bool()).suggestName("x1349_b986_D2") 
      x1349.r := getRetimed(b986.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x1350 = Wire(Bool()).suggestName("x1350_x991_D2") 
      x1350.r := getRetimed(x991.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x998_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x998_wr_ofs = List[UInt](x997_sum.r)
      val x998_wr_en = List[Bool](true.B)
      val x998_wr_data = List[UInt](x1347.r)
      x934_tileA_sram_0.connectWPort(998, x998_wr_banks, x998_wr_ofs, x998_wr_data, x998_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x1350 & x1349 & x1348))
    }
    val module = Module(new x999_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x999_inr_Foreach **/
