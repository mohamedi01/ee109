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

/** Hierarchy: x264 -> x196 **/
/** BEGIN None x264_inr_Foreach **/
class x264_inr_Foreach_kernel(
  list_x214_buf_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 43.0.toInt, myName = "x264_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(43.0.toInt, 2 + _root_.utils.math.log2Up(43.0.toInt), "x264_inr_Foreach_iiCtr"))
  
  abstract class x264_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x214_buf_0 = Flipped(new StandardInterface(ModuleParams.getParams("x214_buf_0_p").asInstanceOf[MemParams] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x214_buf_0 = {io.in_x214_buf_0} ; io.in_x214_buf_0 := DontCare
  }
  def connectWires0(module: x264_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x214_buf_0.connectLedger(module.io.in_x214_buf_0)
  }
  val x214_buf_0 = list_x214_buf_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x264_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x264_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x264_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val b257 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b257.suggestName("b257")
      val b258 = ~io.sigsIn.cchainOutputs.head.oobs(0); b258.suggestName("b258")
      val x259_rd = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x259_rd""")
      val x259_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x259_rd_ofs = List[UInt](b257.r)
      val x259_rd_en = List[Bool](true.B)
      val x259_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && b258 ).suggestName("x259_rd_shared_en")
      x259_rd.toSeq.zip(x214_buf_0.connectRPort(259, x259_rd_banks, x259_rd_ofs, io.sigsIn.backpressure, x259_rd_en.map(_ && x259_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x260 = VecApply(x259,0)
      val x260_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x260_elem_0""")
      x260_elem_0.r := x259_rd(0).r
      val x261 = Wire(new FloatingPoint(24, 8)).suggestName("""x261""")
      x261.r := Math.fadd(x260_elem_0, 4.FlP(24, 8), Some(12.0), true.B,"x261").r
      val x262 = Wire(new FloatingPoint(24, 8)).suggestName("""x262""")
      x262.r := Math.fdiv(x261, 4.FlP(24, 8), Some(28.0), true.B,"x262").r
      val x315 = Wire(new FixedPoint(true, 32, 0)).suggestName("x315_b257_D42") 
      x315.r := getRetimed(b257.r, 42.toInt, io.sigsIn.backpressure & true.B)
      val x316 = Wire(Bool()).suggestName("x316_b258_D42") 
      x316.r := getRetimed(b258.r, 42.toInt, io.sigsIn.backpressure & true.B)
      val x263_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x263_wr_ofs = List[UInt](x315.r)
      val x263_wr_en = List[Bool](true.B)
      val x263_wr_data = List[UInt](x262.r)
      x214_buf_0.connectWPort(263, x263_wr_banks, x263_wr_ofs, x263_wr_data, x263_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(42.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x316))
    }
    val module = Module(new x264_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnrolledForeach x264_inr_Foreach **/
