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

/** Hierarchy: x601 -> x602 -> x607 -> x608 -> x709 -> x710 **/
/** BEGIN None x601_inr_Foreach **/
class x601_inr_Foreach_kernel(
  list_b558: List[FixedPoint],
  list_x561_reg: List[StandardInterface],
  list_x554: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 4.0.toInt, myName = "x601_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x601_inr_Foreach_iiCtr"))
  
  abstract class x601_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x561_reg = Flipped(new StandardInterface(ModuleParams.getParams("x561_reg_p").asInstanceOf[MemParams] ))
      val in_x554 = Decoupled(new AppStoreData(ModuleParams.getParams("x554_p").asInstanceOf[(Int,Int)] ))
      val in_b558 = Input(new FixedPoint(true, 32, 0))
      val in_x469_real2D_0 = Flipped(new StandardInterface(ModuleParams.getParams("x469_real2D_0_p").asInstanceOf[MemParams] ))
      val in_x560_reg = Flipped(new StandardInterface(ModuleParams.getParams("x560_reg_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x561_reg = {io.in_x561_reg} ; io.in_x561_reg := DontCare
    def x554 = {io.in_x554} 
    def b558 = {io.in_b558} 
    def x469_real2D_0 = {io.in_x469_real2D_0} ; io.in_x469_real2D_0 := DontCare
    def x560_reg = {io.in_x560_reg} ; io.in_x560_reg := DontCare
  }
  def connectWires0(module: x601_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x561_reg.connectLedger(module.io.in_x561_reg)
    module.io.in_x554 <> x554
    module.io.in_b558 <> b558
    x469_real2D_0.connectLedger(module.io.in_x469_real2D_0)
    x560_reg.connectLedger(module.io.in_x560_reg)
  }
  val b558 = list_b558(0)
  val x561_reg = list_x561_reg(0)
  val x469_real2D_0 = list_x561_reg(1)
  val x560_reg = list_x561_reg(2)
  val x554 = list_x554(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x601_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x601_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x601_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x601_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x601_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x601_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x601_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x601_inr_Foreach = Module(new InstrumentationCounter())
      val idles_x601_inr_Foreach = Module(new InstrumentationCounter())
      stalls_x601_inr_Foreach.io.enable := io.sigsIn.baseEn & ~(x554.ready)
      idles_x601_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X601_instrctr, cycles_x601_inr_Foreach.io.count, iters_x601_inr_Foreach.io.count, stalls_x601_inr_Foreach.io.count, idles_x601_inr_Foreach.io.count)
      val b586 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b586.suggestName("b586")
      val b587 = ~io.sigsIn.cchainOutputs.head.oobs(0); b587.suggestName("b587")
      val x588_rd_x560 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x588_rd_x560""")
      val x588_rd_x560_banks = List[UInt]()
      val x588_rd_x560_ofs = List[UInt]()
      val x588_rd_x560_en = List[Bool](true.B)
      val x588_rd_x560_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x588_rd_x560_shared_en")
      x588_rd_x560.toSeq.zip(x560_reg.connectRPort(588, x588_rd_x560_banks, x588_rd_x560_ofs, io.sigsIn.backpressure, x588_rd_x560_en.map(_ && x588_rd_x560_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x589 = Wire(Bool()).suggestName("""x589""")
      val ensig0 = Wire(Bool())
      ensig0 := x554.ready
      x589.r := Math.lte(x588_rd_x560, b586, Some(0.4), ensig0,"x589").r
      val x590_rd_x561 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x590_rd_x561""")
      val x590_rd_x561_banks = List[UInt]()
      val x590_rd_x561_ofs = List[UInt]()
      val x590_rd_x561_en = List[Bool](true.B)
      val x590_rd_x561_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x590_rd_x561_shared_en")
      x590_rd_x561.toSeq.zip(x561_reg.connectRPort(590, x590_rd_x561_banks, x590_rd_x561_ofs, io.sigsIn.backpressure, x590_rd_x561_en.map(_ && x590_rd_x561_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x591 = Wire(Bool()).suggestName("""x591""")
      x591.r := Math.lt(b586, x590_rd_x561, Some(0.4), ensig0,"x591").r
      val x592 = Wire(Bool()).suggestName("""x592""")
      x592 := x589 & x591
      val x593_sub = Wire(new FixedPoint(true, 32, 0)).suggestName("""x593_sub""")
      x593_sub.r := Math.sub(b586,x588_rd_x560,Some(1.0), ensig0, Truncate, Wrapping, "x593_sub").r
      val x595 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x595""")
      x595.r := Math.arith_left_shift(b558, 1, Some(0.2), ensig0,"x595").r
      val x729 = Wire(new FixedPoint(true, 32, 0)).suggestName("x729_x595_D1") 
      x729.r := getRetimed(x595.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x596_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x596_sum""")
      x596_sum.r := Math.add(x729,x593_sub,Some(1.0), ensig0, Truncate, Wrapping, "x596_sum").r
      val x730 = Wire(Bool()).suggestName("x730_x592_D2") 
      x730.r := getRetimed(x592.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x731 = Wire(Bool()).suggestName("x731_b587_D2") 
      x731.r := getRetimed(b587.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x597_rd = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x597_rd""")
      val x597_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x597_rd_ofs = List[UInt](x596_sum.r)
      val x597_rd_en = List[Bool](true.B)
      val x597_rd_shared_en = ((io.sigsIn.forwardpressure).DS(2.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.0.toInt, rr, io.sigsIn.backpressure & true.B) && x730 & x731 ).suggestName("x597_rd_shared_en")
      x597_rd.toSeq.zip(x469_real2D_0.connectRPort(597, x597_rd_banks, x597_rd_ofs, io.sigsIn.backpressure, x597_rd_en.map(_ && x597_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x598 = VecApply(x597,0)
      val x598_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x598_elem_0""")
      x598_elem_0.r := x597_rd(0).r
      val x732 = Wire(Bool()).suggestName("x732_x592_D4") 
      x732.r := getRetimed(x592.r, 4.toInt, io.sigsIn.backpressure & true.B)
      val x599_tuple = Wire(UInt(33.W)).suggestName("""x599_tuple""")
      x599_tuple.r := ConvAndCat(x732,x598_elem_0.r)
      val x733 = Wire(Bool()).suggestName("x733_b587_D4") 
      x733.r := getRetimed(b587.r, 4.toInt, io.sigsIn.backpressure & true.B)
      x554.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(4.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x733 & io.sigsIn.backpressure
      x554.bits.wdata(0) := x599_tuple(31,0)
      x554.bits.wstrb := x599_tuple(32,32)
    }
    val module = Module(new x601_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x601_inr_Foreach **/
