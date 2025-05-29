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

/** Hierarchy: x354 -> x438 -> x439 **/
/** BEGIN None x354_inr_Foreach **/
class x354_inr_Foreach_kernel(
  list_x331_mx_0: List[FixOpAccumBundle],
  list_x294_buf_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 3.0.toInt, myName = "x354_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(3.0.toInt, 2 + _root_.utils.math.log2Up(3.0.toInt), "x354_inr_Foreach_iiCtr"))
  
  abstract class x354_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x331_mx_0 = Flipped(new FixOpAccumBundle(1, 16, 8))
      val in_x294_buf_0 = Flipped(new StandardInterface(ModuleParams.getParams("x294_buf_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x331_mx_0 = {io.in_x331_mx_0} ; io.in_x331_mx_0 := DontCare
    def x294_buf_0 = {io.in_x294_buf_0} ; io.in_x294_buf_0 := DontCare
  }
  def connectWires0(module: x354_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x331_mx_0.connectLedger(module.io.in_x331_mx_0)
    x294_buf_0.connectLedger(module.io.in_x294_buf_0)
  }
  val x331_mx_0 = list_x331_mx_0(0)
  val x294_buf_0 = list_x294_buf_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x354_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x354_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x354_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x354_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x354_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x354_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x354_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X354_instrctr, cycles_x354_inr_Foreach.io.count, iters_x354_inr_Foreach.io.count, 0.U, 0.U)
      val b346 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b346.suggestName("b346")
      val b347 = ~io.sigsIn.cchainOutputs.head.oobs(0); b347.suggestName("b347")
      val x348_rd = Wire(Vec(1, new FixedPoint(true, 16, 8))).suggestName("""x348_rd""")
      val x348_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x348_rd_ofs = List[UInt](b346.r)
      val x348_rd_en = List[Bool](true.B)
      val x348_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && b347 ).suggestName("x348_rd_shared_en")
      x348_rd.toSeq.zip(x294_buf_0.connectRPort(348, x348_rd_banks, x348_rd_ofs, io.sigsIn.backpressure, x348_rd_en.map(_ && x348_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x349 = VecApply(x348,0)
      val x349_elem_0 = Wire(new FixedPoint(true, 16, 8)).suggestName("""x349_elem_0""")
      x349_elem_0.r := x348_rd(0).r
      val x350_rd_x331 = Wire(new FixedPoint(true, 16, 8)).suggestName("""x350_rd_x331""")
      val x350_rd_x331_banks = List[UInt]()
      val x350_rd_x331_ofs = List[UInt]()
      val x350_rd_x331_en = List[Bool](true.B)
      val x350_rd_x331_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x350_rd_x331_shared_en")
      x350_rd_x331.toSeq.zip(x331_mx_0.connectRPort(350, x350_rd_x331_banks, x350_rd_x331_ofs, io.sigsIn.backpressure, x350_rd_x331_en.map(_ && x350_rd_x331_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x351_sub = Wire(new FixedPoint(true, 16, 8)).suggestName("""x351_sub""")
      x351_sub.r := Math.sub(x350_rd_x331,8.FP(true, 16, 8),Some(0.75), true.B, Truncate, Wrapping, "x351_sub").r
      val x446 = Wire(new FixedPoint(true, 16, 8)).suggestName("x446_x351_sub_D2") 
      x446.r := getRetimed(x351_sub.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x352 = Wire(new FixedPoint(true, 16, 8)).suggestName("""x352""")
      x352.r := Mux((x349_elem_0 > x446), x349_elem_0, x446).r
      val x447 = Wire(Bool()).suggestName("x447_b347_D2") 
      x447.r := getRetimed(b347.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x448 = Wire(new FixedPoint(true, 32, 0)).suggestName("x448_b346_D2") 
      x448.r := getRetimed(b346.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x353_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x353_wr_ofs = List[UInt](x448.r)
      val x353_wr_en = List[Bool](true.B)
      val x353_wr_data = List[UInt](x352.r)
      x294_buf_0.connectWPort(353, x353_wr_banks, x353_wr_ofs, x353_wr_data, x353_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x447))
    }
    val module = Module(new x354_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x354_inr_Foreach **/
