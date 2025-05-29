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

/** Hierarchy: x307 -> x321 -> x322 -> x374 -> x375 **/
/** BEGIN None x307_inr_UnitPipe **/
class x307_inr_UnitPipe_kernel(
  list_x289_fifo: List[FIFOInterface],
  list_x299_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x307_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x307_inr_UnitPipe_iiCtr"))
  
  abstract class x307_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x289_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x289_fifo_p").asInstanceOf[MemParams] ))
      val in_x299_reg = Flipped(new StandardInterface(ModuleParams.getParams("x299_reg_p").asInstanceOf[MemParams] ))
      val in_x300_reg = Flipped(new StandardInterface(ModuleParams.getParams("x300_reg_p").asInstanceOf[MemParams] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x289_fifo = {io.in_x289_fifo} ; io.in_x289_fifo := DontCare
    def x299_reg = {io.in_x299_reg} ; io.in_x299_reg := DontCare
    def x300_reg = {io.in_x300_reg} ; io.in_x300_reg := DontCare
  }
  def connectWires0(module: x307_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x289_fifo.connectLedger(module.io.in_x289_fifo)
    x299_reg.connectLedger(module.io.in_x299_reg)
    x300_reg.connectLedger(module.io.in_x300_reg)
  }
  val x289_fifo = list_x289_fifo(0)
  val x299_reg = list_x299_reg(0)
  val x300_reg = list_x299_reg(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x307_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x307_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x307_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x301_deq_x289 = Wire(Vec(1, UInt(96.W))).suggestName("""x301_deq_x289""")
      val x301_deq_x289_banks = List[UInt]()
      val x301_deq_x289_ofs = List[UInt]()
      val x301_deq_x289_en = List[Bool](true.B)
      val x301_deq_x289_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x301_deq_x289_shared_en")
      x301_deq_x289.toSeq.zip(x289_fifo.connectRPort(301, x301_deq_x289_banks, x301_deq_x289_ofs, io.sigsIn.backpressure, x301_deq_x289_en.map(_ && x301_deq_x289_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      x289_fifo.connectAccessActivesIn(1, ((true.B)))
      // x302 = VecApply(x301,0)
      val x302_elem_0 = Wire(UInt(96.W)).suggestName("""x302_elem_0""")
      x302_elem_0.r := x301_deq_x289(0).r
      val x303_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x303_apply""")
      x303_apply.r := x302_elem_0(95, 64)
      val x304_wr_x299_banks = List[UInt]()
      val x304_wr_x299_ofs = List[UInt]()
      val x304_wr_x299_en = List[Bool](true.B)
      val x304_wr_x299_data = List[UInt](x303_apply.r)
      x299_reg.connectWPort(304, x304_wr_x299_banks, x304_wr_x299_ofs, x304_wr_x299_data, x304_wr_x299_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x305_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x305_apply""")
      x305_apply.r := x302_elem_0(31, 0)
      val x306_wr_x300_banks = List[UInt]()
      val x306_wr_x300_ofs = List[UInt]()
      val x306_wr_x300_en = List[Bool](true.B)
      val x306_wr_x300_data = List[UInt](x305_apply.r)
      x300_reg.connectWPort(306, x306_wr_x300_banks, x306_wr_x300_ofs, x306_wr_x300_data, x306_wr_x300_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x307_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x307_inr_UnitPipe **/
