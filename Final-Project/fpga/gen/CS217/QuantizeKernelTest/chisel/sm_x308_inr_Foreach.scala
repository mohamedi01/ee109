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

/** Hierarchy: x308 -> x213 **/
/** BEGIN None x308_inr_Foreach **/
class x308_inr_Foreach_kernel(
  list_x253_inSRAM_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 7.0.toInt, myName = "x308_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x308_inr_Foreach_iiCtr"))
  
  abstract class x308_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x253_inSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x253_inSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x254_outSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x254_outSRAM_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x253_inSRAM_0 = {io.in_x253_inSRAM_0} ; io.in_x253_inSRAM_0 := DontCare
    def x254_outSRAM_0 = {io.in_x254_outSRAM_0} ; io.in_x254_outSRAM_0 := DontCare
  }
  def connectWires0(module: x308_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x253_inSRAM_0.connectLedger(module.io.in_x253_inSRAM_0)
    x254_outSRAM_0.connectLedger(module.io.in_x254_outSRAM_0)
  }
  val x253_inSRAM_0 = list_x253_inSRAM_0(0)
  val x254_outSRAM_0 = list_x253_inSRAM_0(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x308_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x308_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x308_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x308_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x308_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x308_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x308_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X308_instrctr, cycles_x308_inr_Foreach.io.count, iters_x308_inr_Foreach.io.count, 0.U, 0.U)
      val b292 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b292.suggestName("b292")
      val b293 = ~io.sigsIn.cchainOutputs.head.oobs(0); b293.suggestName("b293")
      val x294_rd = Wire(Vec(1, new FixedPoint(true, 16, 8))).suggestName("""x294_rd""")
      val x294_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x294_rd_ofs = List[UInt](b292.r)
      val x294_rd_en = List[Bool](true.B)
      val x294_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && b293 ).suggestName("x294_rd_shared_en")
      x294_rd.toSeq.zip(x253_inSRAM_0.connectRPort(294, x294_rd_banks, x294_rd_ofs, io.sigsIn.backpressure, x294_rd_en.map(_ && x294_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x295 = VecApply(x294,0)
      val x295_elem_0 = Wire(new FixedPoint(true, 16, 8)).suggestName("""x295_elem_0""")
      x295_elem_0.r := x294_rd(0).r
      val x373_x = Wire(new FixedPoint(true, 16, 8)).suggestName("""x373_x""")
      x373_x.r := Math.neg(x295_elem_0,Some(0.1), true.B,"x373_x").r
      val x374 = Wire(new FixedPoint(true, 16, 8)).suggestName("""x374""")
      x374.r := Math.arith_left_shift(x373_x, -1, Some(0.2), true.B,"x374").r
      val x375_x = Wire(new FixedPoint(true, 16, 8)).suggestName("""x375_x""")
      x375_x.r := Math.sub(x374,x295_elem_0,Some(0.75 + 1.0), true.B, Truncate, Wrapping, "x375_x").r
      val x297 = Wire(Bool()).suggestName("""x297""")
      x297.r := Math.lt(x375_x, -32768.FP(true, 16, 8), Some(0.4), true.B,"x297").r
      val x298 = Wire(Bool()).suggestName("""x298""")
      x298.r := Math.lt(32767.FP(true, 16, 8), x375_x, Some(0.4), true.B,"x298").r
      val x299 = Wire(new FixedPoint(true, 16, 8)).suggestName("""x299""")
      x299.r := Mux((x298), 32767.FP(true, 16, 8).r, x375_x.r)
      val x300 = Wire(new FixedPoint(true, 16, 8)).suggestName("""x300""")
      x300.r := Mux((x297), -32768.FP(true, 16, 8).r, x299.r)
      val x380 = Wire(new FixedPoint(true, 16, 8)).suggestName("x380_x300_D1") 
      x380.r := getRetimed(x300.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x301 = Wire(Bool()).suggestName("""x301""")
      x301.r := Math.lt(x380, 0.FP(true, 16, 8), Some(0.4), true.B,"x301").r
      val x302_sub = Wire(new FixedPoint(true, 16, 8)).suggestName("""x302_sub""")
      x302_sub.r := Math.sub(x380,0.5.FP(true, 16, 8),Some(0.75 + 1.0), true.B, Truncate, Wrapping, "x302_sub").r
      val x303_sum = Wire(new FixedPoint(true, 16, 8)).suggestName("""x303_sum""")
      x303_sum.r := Math.add(x380,0.5.FP(true, 16, 8),Some(0.75 + 1.0), true.B, Truncate, Wrapping, "x303_sum").r
      val x381 = Wire(Bool()).suggestName("x381_x301_D1") 
      x381.r := getRetimed(x301.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x304 = Wire(new FixedPoint(true, 16, 8)).suggestName("""x304""")
      x304.r := Mux((x381), x302_sub.r, x303_sum.r)
      val x305_negx304 = Wire(new FixedPoint(true, 16, 8)).suggestName("""x305_negx304""")
      x305_negx304.r := Math.neg(x304,Some(0.1), true.B,"x305_negx304").r
      val x306 = Wire(new FixedPoint(true, 16, 8)).suggestName("""x306""")
      x306.r := Math.arith_right_shift_div(x305_negx304, -1, Some(0.2 + 1.0), true.B,"x306").r
      val x382 = Wire(new FixedPoint(true, 32, 0)).suggestName("x382_b292_D6") 
      x382.r := getRetimed(b292.r, 6.toInt, io.sigsIn.backpressure & true.B)
      val x383 = Wire(Bool()).suggestName("x383_b293_D6") 
      x383.r := getRetimed(b293.r, 6.toInt, io.sigsIn.backpressure & true.B)
      val x307_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x307_wr_ofs = List[UInt](x382.r)
      val x307_wr_en = List[Bool](true.B)
      val x307_wr_data = List[UInt](x306.r)
      x254_outSRAM_0.connectWPort(307, x307_wr_banks, x307_wr_ofs, x307_wr_data, x307_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(6.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x383))
    }
    val module = Module(new x308_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x308_inr_Foreach **/
