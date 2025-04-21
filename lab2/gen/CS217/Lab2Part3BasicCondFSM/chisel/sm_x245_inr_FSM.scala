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

/** Hierarchy: x245 -> x168 **/
/** BEGIN None x245_inr_FSM **/
class x245_inr_FSM_kernel(
  list_x211_bram_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, true ,stateWidth = 32  , latency = 5.4.toInt, myName = "x245_inr_FSM_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(2.0.toInt, 2 + _root_.utils.math.log2Up(2.0.toInt), "x245_inr_FSM_iiCtr"))
  
  abstract class x245_inr_FSM_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x211_bram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x211_bram_0_p").asInstanceOf[MemParams] ))
      val in_x212_reg_0 = Flipped(new StandardInterface(ModuleParams.getParams("x212_reg_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x211_bram_0 = {io.in_x211_bram_0} ; io.in_x211_bram_0 := DontCare
    def x212_reg_0 = {io.in_x212_reg_0} ; io.in_x212_reg_0 := DontCare
  }
  def connectWires0(module: x245_inr_FSM_module)(implicit stack: List[KernelHash]): Unit = {
    x211_bram_0.connectLedger(module.io.in_x211_bram_0)
    x212_reg_0.connectLedger(module.io.in_x212_reg_0)
  }
  val x211_bram_0 = list_x211_bram_0(0)
  val x212_reg_0 = list_x211_bram_0(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x245_inr_FSM")
    implicit val stack = ControllerStack.stack.toList
    class x245_inr_FSM_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x245_inr_FSM_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x245_inr_FSM = Module(new InstrumentationCounter())
      val iters_x245_inr_FSM = Module(new InstrumentationCounter())
      cycles_x245_inr_FSM.io.enable := io.sigsIn.baseEn
      iters_x245_inr_FSM.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X245_instrctr, cycles_x245_inr_FSM.io.count, iters_x245_inr_FSM.io.count, 0.U, 0.U)
      val b5 = Wire(new FixedPoint(true, 32, 0)).suggestName("""b5""")
      b5.r := io.sigsIn.smState.r
      val x215 = Wire(Bool()).suggestName("""x215""")
      x215.r := Math.lt(b5, 32L.FP(true, 32, 0), Some(0.4), true.B,"x215").r
      val x216 = Wire(Bool()).suggestName("""x216""")
      x216.r := Math.lt(b5, 16L.FP(true, 32, 0), Some(0.4), true.B,"x216").r
      val x217 = Wire(Bool()).suggestName("""x217""")
      x217 := ~x216
      val x218 = Wire(Bool()).suggestName("""x218""")
      x218.r := Math.lt(b5, 8L.FP(true, 32, 0), Some(0.4), true.B,"x218").r
      val x219 = Wire(Bool()).suggestName("""x219""")
      x219 := ~x218
      val x220_sub = Wire(new FixedPoint(true, 32, 0)).suggestName("""x220_sub""")
      x220_sub.r := Math.sub(31L.FP(true, 32, 0),b5,Some(1.0), true.B, Truncate, Wrapping, "x220_sub").r
      val x303 = Wire(Bool()).suggestName("x303_x218_D1") 
      x303.r := getRetimed(x218.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x304 = Wire(Bool()).suggestName("x304_x216_D1") 
      x304.r := getRetimed(x216.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x305 = Wire(new FixedPoint(true, 32, 0)).suggestName("x305_b5_D1") 
      x305.r := getRetimed(b5.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x221_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x221_wr_ofs = List[UInt](x220_sub.r)
      val x221_wr_en = List[Bool](true.B)
      val x221_wr_data = List[UInt](x305.r)
      x211_bram_0.connectWPort(221, x221_wr_banks, x221_wr_ofs, x221_wr_data, x221_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x304 & x303))
      val x224_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x224_sum""")
      x224_sum.r := Math.add(b5,1L.FP(true, 32, 0),Some(1.0), true.B, Truncate, Wrapping, "x224_sum").r
      val x306 = Wire(Bool()).suggestName("x306_x216_D2") 
      x306.r := getRetimed(x216.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x307 = Wire(new FixedPoint(true, 32, 0)).suggestName("x307_x220_sub_D1") 
      x307.r := getRetimed(x220_sub.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x308 = Wire(new FixedPoint(true, 32, 0)).suggestName("x308_x224_sum_D1") 
      x308.r := getRetimed(x224_sum.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x309 = Wire(Bool()).suggestName("x309_x219_D2") 
      x309.r := getRetimed(x219.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x225_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x225_wr_ofs = List[UInt](x307.r)
      val x225_wr_en = List[Bool](true.B)
      val x225_wr_data = List[UInt](x308.r)
      x211_bram_0.connectWPort(225, x225_wr_banks, x225_wr_ofs, x225_wr_data, x225_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x306 & x309))
      val x229_sub = Wire(new FixedPoint(true, 32, 0)).suggestName("""x229_sub""")
      x229_sub.r := Math.sub(b5,16L.FP(true, 32, 0),Some(1.0), true.B, Truncate, Wrapping, "x229_sub").r
      val x230 = Wire(Bool()).suggestName("""x230""")
      x230.r := Math.eql(b5, 16L.FP(true, 32, 0), Some(0.2), true.B,"x230").r
      val x231 = Wire(Bool()).suggestName("""x231""")
      x231 := ~x230
      val x232 = Wire(Bool()).suggestName("""x232""")
      x232.r := Math.eql(b5, 17L.FP(true, 32, 0), Some(0.2), true.B,"x232").r
      val x233 = Wire(Bool()).suggestName("""x233""")
      x233 := x232 & x231
      val x234 = Wire(Bool()).suggestName("""x234""")
      x234 := ~x232
      val x235 = Wire(Bool()).suggestName("""x235""")
      x235 := x234 & x231
      val x237_rd_x212 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x237_rd_x212""")
      val x237_rd_x212_banks = List[UInt]()
      val x237_rd_x212_ofs = List[UInt]()
      val x237_rd_x212_en = List[Bool](true.B)
      val x237_rd_x212_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x237_rd_x212_shared_en")
      x237_rd_x212.toSeq.zip(x212_reg_0.connectRPort(237, x237_rd_x212_banks, x237_rd_x212_ofs, io.sigsIn.backpressure, x237_rd_x212_en.map(_ && x237_rd_x212_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x302 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x302""")
      x302.r := Mux1H(List(x230, x233, x235), List(17L.FP(true, 32, 0).r, x237_rd_x212.r, b5.r))
      val x310 = Wire(new FixedPoint(true, 32, 0)).suggestName("x310_x229_sub_D2") 
      x310.r := getRetimed(x229_sub.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x311 = Wire(new FixedPoint(true, 32, 0)).suggestName("x311_x302_D3") 
      x311.r := getRetimed(x302.r, 3.toInt, io.sigsIn.backpressure & true.B)
      val x312 = Wire(Bool()).suggestName("x312_x217_D3") 
      x312.r := getRetimed(x217.r, 3.toInt, io.sigsIn.backpressure & true.B)
      val x241_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x241_wr_ofs = List[UInt](x310.r)
      val x241_wr_en = List[Bool](true.B)
      val x241_wr_data = List[UInt](x311.r)
      x211_bram_0.connectWPort(241, x241_wr_banks, x241_wr_ofs, x241_wr_data, x241_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(3.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x312))
      val x313 = Wire(new FixedPoint(true, 32, 0)).suggestName("x313_b5_D4") 
      x313.r := getRetimed(b5.r, 4.toInt, io.sigsIn.backpressure & true.B)
      val x244_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x244_sum""")
      x244_sum.r := Math.add(x313,1L.FP(true, 32, 0),Some(1.0), true.B, Truncate, Wrapping, "x244_sum").r
      io.sigsOut.smNextState := x244_sum.r.asSInt 
      io.sigsOut.smInitState := 0L.FP(true, 32, 0).r.asSInt
      io.sigsOut.smDoneCondition := ~x215
    }
    val module = Module(new x245_inr_FSM_concrete(sm.p.depth)); module.io := DontCare
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
/** END StateMachine x245_inr_FSM **/
