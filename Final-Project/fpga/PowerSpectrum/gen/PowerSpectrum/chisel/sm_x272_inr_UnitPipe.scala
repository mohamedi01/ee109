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

/** Hierarchy: x272 -> x286 -> x287 -> x374 -> x375 **/
/** BEGIN None x272_inr_UnitPipe **/
class x272_inr_UnitPipe_kernel(
  list_x254_fifo: List[FIFOInterface],
  list_x265_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x272_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x272_inr_UnitPipe_iiCtr"))
  
  abstract class x272_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x265_reg = Flipped(new StandardInterface(ModuleParams.getParams("x265_reg_p").asInstanceOf[MemParams] ))
      val in_x264_reg = Flipped(new StandardInterface(ModuleParams.getParams("x264_reg_p").asInstanceOf[MemParams] ))
      val in_x254_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x254_fifo_p").asInstanceOf[MemParams] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x265_reg = {io.in_x265_reg} ; io.in_x265_reg := DontCare
    def x264_reg = {io.in_x264_reg} ; io.in_x264_reg := DontCare
    def x254_fifo = {io.in_x254_fifo} ; io.in_x254_fifo := DontCare
  }
  def connectWires0(module: x272_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x265_reg.connectLedger(module.io.in_x265_reg)
    x264_reg.connectLedger(module.io.in_x264_reg)
    x254_fifo.connectLedger(module.io.in_x254_fifo)
  }
  val x254_fifo = list_x254_fifo(0)
  val x265_reg = list_x265_reg(0)
  val x264_reg = list_x265_reg(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x272_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x272_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x272_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x266_deq_x254 = Wire(Vec(1, UInt(96.W))).suggestName("""x266_deq_x254""")
      val x266_deq_x254_banks = List[UInt]()
      val x266_deq_x254_ofs = List[UInt]()
      val x266_deq_x254_en = List[Bool](true.B)
      val x266_deq_x254_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x266_deq_x254_shared_en")
      x266_deq_x254.toSeq.zip(x254_fifo.connectRPort(266, x266_deq_x254_banks, x266_deq_x254_ofs, io.sigsIn.backpressure, x266_deq_x254_en.map(_ && x266_deq_x254_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      x254_fifo.connectAccessActivesIn(1, ((true.B)))
      // x267 = VecApply(x266,0)
      val x267_elem_0 = Wire(UInt(96.W)).suggestName("""x267_elem_0""")
      x267_elem_0.r := x266_deq_x254(0).r
      val x268_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x268_apply""")
      x268_apply.r := x267_elem_0(95, 64)
      val x269_wr_x264_banks = List[UInt]()
      val x269_wr_x264_ofs = List[UInt]()
      val x269_wr_x264_en = List[Bool](true.B)
      val x269_wr_x264_data = List[UInt](x268_apply.r)
      x264_reg.connectWPort(269, x269_wr_x264_banks, x269_wr_x264_ofs, x269_wr_x264_data, x269_wr_x264_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x270_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x270_apply""")
      x270_apply.r := x267_elem_0(31, 0)
      val x271_wr_x265_banks = List[UInt]()
      val x271_wr_x265_ofs = List[UInt]()
      val x271_wr_x265_en = List[Bool](true.B)
      val x271_wr_x265_data = List[UInt](x270_apply.r)
      x265_reg.connectWPort(271, x271_wr_x265_banks, x271_wr_x265_ofs, x271_wr_x265_data, x271_wr_x265_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x272_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x272_inr_UnitPipe **/
