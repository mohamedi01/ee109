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

/** Hierarchy: x179 -> x122 **/
/** BEGIN None x179_inr_Foreach **/
class x179_inr_Foreach_kernel(
  list_x152_buf_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 43.0.toInt, myName = "x179_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(43.0.toInt, 2 + _root_.utils.math.log2Up(43.0.toInt), "x179_inr_Foreach_iiCtr"))
  
  abstract class x179_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x152_buf_0 = Flipped(new StandardInterface(ModuleParams.getParams("x152_buf_0_p").asInstanceOf[MemParams] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x152_buf_0 = {io.in_x152_buf_0} ; io.in_x152_buf_0 := DontCare
  }
  def connectWires0(module: x179_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x152_buf_0.connectLedger(module.io.in_x152_buf_0)
  }
  val x152_buf_0 = list_x152_buf_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x179_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x179_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x179_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val b172 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b172.suggestName("b172")
      val b173 = ~io.sigsIn.cchainOutputs.head.oobs(0); b173.suggestName("b173")
      val x174_rd = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x174_rd""")
      val x174_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x174_rd_ofs = List[UInt](b172.r)
      val x174_rd_en = List[Bool](true.B)
      val x174_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && b173 ).suggestName("x174_rd_shared_en")
      x174_rd.toSeq.zip(x152_buf_0.connectRPort(174, x174_rd_banks, x174_rd_ofs, io.sigsIn.backpressure, x174_rd_en.map(_ && x174_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x175 = VecApply(x174,0)
      val x175_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x175_elem_0""")
      x175_elem_0.r := x174_rd(0).r
      val x176 = Wire(new FloatingPoint(24, 8)).suggestName("""x176""")
      x176.r := Math.fadd(x175_elem_0, 4.FlP(24, 8), Some(12.0), true.B,"x176").r
      val x177 = Wire(new FloatingPoint(24, 8)).suggestName("""x177""")
      x177.r := Math.fdiv(x176, 4.FlP(24, 8), Some(28.0), true.B,"x177").r
      val x224 = Wire(new FixedPoint(true, 32, 0)).suggestName("x224_b172_D42") 
      x224.r := getRetimed(b172.r, 42.toInt, io.sigsIn.backpressure & true.B)
      val x225 = Wire(Bool()).suggestName("x225_b173_D42") 
      x225.r := getRetimed(b173.r, 42.toInt, io.sigsIn.backpressure & true.B)
      val x178_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x178_wr_ofs = List[UInt](x224.r)
      val x178_wr_en = List[Bool](true.B)
      val x178_wr_data = List[UInt](x177.r)
      x152_buf_0.connectWPort(178, x178_wr_banks, x178_wr_ofs, x178_wr_data, x178_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(42.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x225))
    }
    val module = Module(new x179_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnrolledForeach x179_inr_Foreach **/
