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

/** Hierarchy: x186 -> x187 -> x119 **/
/** BEGIN None x186_inr_Foreach **/
class x186_inr_Foreach_kernel(
  list_b166: List[FixedPoint],
  list_x161_a_0: List[StandardInterface],
  list_x170_tmp_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 4.5.toInt, myName = "x186_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x186_inr_Foreach_iiCtr"))
  
  abstract class x186_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x170_tmp_0 = Flipped(new NBufInterface(ModuleParams.getParams("x170_tmp_0_p").asInstanceOf[NBufParams] ))
      val in_b166 = Input(new FixedPoint(true, 32, 0))
      val in_x161_a_0 = Flipped(new StandardInterface(ModuleParams.getParams("x161_a_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x170_tmp_0 = {io.in_x170_tmp_0} ; io.in_x170_tmp_0 := DontCare
    def b166 = {io.in_b166} 
    def x161_a_0 = {io.in_x161_a_0} ; io.in_x161_a_0 := DontCare
  }
  def connectWires0(module: x186_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x170_tmp_0.connectLedger(module.io.in_x170_tmp_0)
    module.io.in_b166 <> b166
    x161_a_0.connectLedger(module.io.in_x161_a_0)
  }
  val b166 = list_b166(0)
  val x161_a_0 = list_x161_a_0(0)
  val x170_tmp_0 = list_x170_tmp_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x186_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x186_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x186_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x186_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x186_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x186_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x186_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X186_instrctr, cycles_x186_inr_Foreach.io.count, iters_x186_inr_Foreach.io.count, 0.U, 0.U)
      val b167 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b167.suggestName("b167")
      val b169 = ~io.sigsIn.cchainOutputs.head.oobs(0); b169.suggestName("b169")
      val x177_rd = Wire(Vec(1, new FixedPoint(true, 32, 0))).suggestName("""x177_rd""")
      val x177_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x177_rd_ofs = List[UInt](b167.r)
      val x177_rd_en = List[Bool](true.B)
      val x177_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && b169 ).suggestName("x177_rd_shared_en")
      x177_rd.toSeq.zip(x170_tmp_0.connectRPort(177, x177_rd_banks, x177_rd_ofs, io.sigsIn.backpressure, x177_rd_en.map(_ && x177_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x178 = VecApply(x177,0)
      val x178_elem_0 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x178_elem_0""")
      x178_elem_0.r := x177_rd(0).r
      val x179_rd = Wire(Vec(1, new FixedPoint(true, 32, 0))).suggestName("""x179_rd""")
      val x179_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x179_rd_ofs = List[UInt](b167.r)
      val x179_rd_en = List[Bool](true.B)
      val x179_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && b169 ).suggestName("x179_rd_shared_en")
      x179_rd.toSeq.zip(x161_a_0.connectRPort(179, x179_rd_banks, x179_rd_ofs, io.sigsIn.backpressure, x179_rd_en.map(_ && x179_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x180 = VecApply(x179,0)
      val x180_elem_0 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x180_elem_0""")
      x180_elem_0.r := x179_rd(0).r
      val x182 = Wire(Bool()).suggestName("""x182""")
      x182.r := Math.eql(b166, -5L.FP(true, 32, 0), Some(0.2), true.B,"x182").r
      val x183_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x183_sum""")
      x183_sum.r := Math.add(x178_elem_0,x180_elem_0,Some(1.0), true.B, Truncate, Wrapping, "x183_sum").r
      val x244 = Wire(new FixedPoint(true, 32, 0)).suggestName("x244_x178_elem_0_D1") 
      x244.r := getRetimed(x178_elem_0.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x245 = Wire(Bool()).suggestName("x245_x182_D3") 
      x245.r := getRetimed(x182.r, 3.toInt, io.sigsIn.backpressure & true.B)
      val x184 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x184""")
      x184.r := Mux((x245), x244.r, x183_sum.r)
      val x246 = Wire(new FixedPoint(true, 32, 0)).suggestName("x246_b167_D3") 
      x246.r := getRetimed(b167.r, 3.toInt, io.sigsIn.backpressure & true.B)
      val x247 = Wire(Bool()).suggestName("x247_b169_D3") 
      x247.r := getRetimed(b169.r, 3.toInt, io.sigsIn.backpressure & true.B)
      val x185_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x185_wr_ofs = List[UInt](x246.r)
      val x185_wr_en = List[Bool](true.B)
      val x185_wr_data = List[UInt](x184.r)
      x161_a_0.connectWPort(185, x185_wr_banks, x185_wr_ofs, x185_wr_data, x185_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(3.5.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x247))
      x170_tmp_0.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 1)
    }
    val module = Module(new x186_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x186_inr_Foreach **/
