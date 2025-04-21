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

/** Hierarchy: x236 -> x164 **/
/** BEGIN None x236_inr_FSM **/
class x236_inr_FSM_kernel(
  list_x207_bram_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, true ,stateWidth = 32  , latency = 5.8.toInt, myName = "x236_inr_FSM_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(2.0.toInt, 2 + _root_.utils.math.log2Up(2.0.toInt), "x236_inr_FSM_iiCtr"))
  
  abstract class x236_inr_FSM_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x207_bram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x207_bram_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x207_bram_0 = {io.in_x207_bram_0} ; io.in_x207_bram_0 := DontCare
  }
  def connectWires0(module: x236_inr_FSM_module)(implicit stack: List[KernelHash]): Unit = {
    x207_bram_0.connectLedger(module.io.in_x207_bram_0)
  }
  val x207_bram_0 = list_x207_bram_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x236_inr_FSM")
    implicit val stack = ControllerStack.stack.toList
    class x236_inr_FSM_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x236_inr_FSM_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x236_inr_FSM = Module(new InstrumentationCounter())
      val iters_x236_inr_FSM = Module(new InstrumentationCounter())
      cycles_x236_inr_FSM.io.enable := io.sigsIn.baseEn
      iters_x236_inr_FSM.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X236_instrctr, cycles_x236_inr_FSM.io.count, iters_x236_inr_FSM.io.count, 0.U, 0.U)
      val b5 = Wire(new FixedPoint(true, 32, 0)).suggestName("""b5""")
      b5.r := io.sigsIn.smState.r
      val x208 = Wire(Bool()).suggestName("""x208""")
      x208.r := Math.lt(b5, 32L.FP(true, 32, 0), Some(0.4), true.B,"x208").r
      val x209 = Wire(Bool()).suggestName("""x209""")
      x209.r := Math.lt(b5, 8L.FP(true, 32, 0), Some(0.4), true.B,"x209").r
      val x210 = Wire(Bool()).suggestName("""x210""")
      x210 := ~x209
      val x211_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x211_wr_ofs = List[UInt](b5.r)
      val x211_wr_en = List[Bool](true.B)
      val x211_wr_data = List[UInt](b5.r)
      x207_bram_0.connectWPort(211, x211_wr_banks, x211_wr_ofs, x211_wr_data, x211_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x209))
      val x213 = Wire(Bool()).suggestName("""x213""")
      x213.r := Math.lt(b5, 16L.FP(true, 32, 0), Some(0.4), true.B,"x213").r
      val x214 = Wire(Bool()).suggestName("""x214""")
      x214.r := Math.lte(8L.FP(true, 32, 0), b5, Some(0.4), true.B,"x214").r
      val x215 = Wire(Bool()).suggestName("""x215""")
      x215 := x213 & x214
      val x216 = Wire(Bool()).suggestName("""x216""")
      x216 := ~x215
      val x217 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x217""")
      x217.r := Math.arith_left_shift(b5, 1, Some(0.2), true.B,"x217").r
      val x295 = Wire(new FixedPoint(true, 32, 0)).suggestName("x295_x217_D1") 
      x295.r := getRetimed(x217.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x296 = Wire(new FixedPoint(true, 32, 0)).suggestName("x296_b5_D1") 
      x296.r := getRetimed(b5.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x297 = Wire(Bool()).suggestName("x297_x210_D1") 
      x297.r := getRetimed(x210.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x298 = Wire(Bool()).suggestName("x298_x215_D1") 
      x298.r := getRetimed(x215.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x218_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x218_wr_ofs = List[UInt](x296.r)
      val x218_wr_en = List[Bool](true.B)
      val x218_wr_data = List[UInt](x295.r)
      x207_bram_0.connectWPort(218, x218_wr_banks, x218_wr_ofs, x218_wr_data, x218_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x297 & x298))
      val x220 = Wire(Bool()).suggestName("""x220""")
      x220.r := Math.lt(b5, 24L.FP(true, 32, 0), Some(0.4), true.B,"x220").r
      val x221 = Wire(Bool()).suggestName("""x221""")
      x221.r := Math.lte(16L.FP(true, 32, 0), b5, Some(0.4), true.B,"x221").r
      val x222 = Wire(Bool()).suggestName("""x222""")
      x222 := x220 & x221
      val x223 = Wire(Bool()).suggestName("""x223""")
      x223 := ~x222
      val x294_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x294_sum""")
      x294_sum.r := Math.add(x217,b5,Some(1.0), true.B, Truncate, Wrapping, "x294_sum").r
      val x299 = Wire(new FixedPoint(true, 32, 0)).suggestName("x299_b5_D2") 
      x299.r := getRetimed(b5.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x300 = Wire(Bool()).suggestName("x300_x216_D2") 
      x300.r := getRetimed(x216.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x301 = Wire(Bool()).suggestName("x301_x210_D2") 
      x301.r := getRetimed(x210.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x302 = Wire(new FixedPoint(true, 32, 0)).suggestName("x302_x294_sum_D1") 
      x302.r := getRetimed(x294_sum.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x303 = Wire(Bool()).suggestName("x303_x222_D2") 
      x303.r := getRetimed(x222.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x225_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x225_wr_ofs = List[UInt](x299.r)
      val x225_wr_en = List[Bool](true.B)
      val x225_wr_data = List[UInt](x302.r)
      x207_bram_0.connectWPort(225, x225_wr_banks, x225_wr_ofs, x225_wr_data, x225_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x301 & x300 & x303))
      val x227 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x227""")
      x227.r := Math.arith_left_shift(b5, 2, Some(0.2), true.B,"x227").r
      val x304 = Wire(new FixedPoint(true, 32, 0)).suggestName("x304_b5_D3") 
      x304.r := getRetimed(b5.r, 3.toInt, io.sigsIn.backpressure & true.B)
      val x305 = Wire(Bool()).suggestName("x305_x216_D3") 
      x305.r := getRetimed(x216.r, 3.toInt, io.sigsIn.backpressure & true.B)
      val x306 = Wire(Bool()).suggestName("x306_x223_D3") 
      x306.r := getRetimed(x223.r, 3.toInt, io.sigsIn.backpressure & true.B)
      val x307 = Wire(Bool()).suggestName("x307_x210_D3") 
      x307.r := getRetimed(x210.r, 3.toInt, io.sigsIn.backpressure & true.B)
      val x308 = Wire(new FixedPoint(true, 32, 0)).suggestName("x308_x227_D3") 
      x308.r := getRetimed(x227.r, 3.toInt, io.sigsIn.backpressure & true.B)
      val x228_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x228_wr_ofs = List[UInt](x304.r)
      val x228_wr_en = List[Bool](true.B)
      val x228_wr_data = List[UInt](x308.r)
      x207_bram_0.connectWPort(228, x228_wr_banks, x228_wr_ofs, x228_wr_data, x228_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(3.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x307 & x305 & x306))
      val x309 = Wire(new FixedPoint(true, 32, 0)).suggestName("x309_b5_D4") 
      x309.r := getRetimed(b5.r, 4.toInt, io.sigsIn.backpressure & true.B)
      val x235_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x235_sum""")
      x235_sum.r := Math.add(x309,1L.FP(true, 32, 0),Some(1.0), true.B, Truncate, Wrapping, "x235_sum").r
      io.sigsOut.smNextState := x235_sum.r.asSInt 
      io.sigsOut.smInitState := 0L.FP(true, 32, 0).r.asSInt
      io.sigsOut.smDoneCondition := ~x208
    }
    val module = Module(new x236_inr_FSM_concrete(sm.p.depth)); module.io := DontCare
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
/** END StateMachine x236_inr_FSM **/
