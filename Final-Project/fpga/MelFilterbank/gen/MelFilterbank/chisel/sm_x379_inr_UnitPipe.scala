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

/** Hierarchy: x379 -> x393 -> x394 -> x450 -> x453 **/
/** BEGIN None x379_inr_UnitPipe **/
class x379_inr_UnitPipe_kernel(
  list_x361_fifo: List[FIFOInterface],
  list_x372_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x379_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x379_inr_UnitPipe_iiCtr"))
  
  abstract class x379_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x372_reg = Flipped(new StandardInterface(ModuleParams.getParams("x372_reg_p").asInstanceOf[MemParams] ))
      val in_x361_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x361_fifo_p").asInstanceOf[MemParams] ))
      val in_x371_reg = Flipped(new StandardInterface(ModuleParams.getParams("x371_reg_p").asInstanceOf[MemParams] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x372_reg = {io.in_x372_reg} ; io.in_x372_reg := DontCare
    def x361_fifo = {io.in_x361_fifo} ; io.in_x361_fifo := DontCare
    def x371_reg = {io.in_x371_reg} ; io.in_x371_reg := DontCare
  }
  def connectWires0(module: x379_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x372_reg.connectLedger(module.io.in_x372_reg)
    x361_fifo.connectLedger(module.io.in_x361_fifo)
    x371_reg.connectLedger(module.io.in_x371_reg)
  }
  val x361_fifo = list_x361_fifo(0)
  val x372_reg = list_x372_reg(0)
  val x371_reg = list_x372_reg(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x379_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x379_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x379_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x373_deq_x361 = Wire(Vec(1, UInt(96.W))).suggestName("""x373_deq_x361""")
      val x373_deq_x361_banks = List[UInt]()
      val x373_deq_x361_ofs = List[UInt]()
      val x373_deq_x361_en = List[Bool](true.B)
      val x373_deq_x361_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x373_deq_x361_shared_en")
      x373_deq_x361.toSeq.zip(x361_fifo.connectRPort(373, x373_deq_x361_banks, x373_deq_x361_ofs, io.sigsIn.backpressure, x373_deq_x361_en.map(_ && x373_deq_x361_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      x361_fifo.connectAccessActivesIn(1, ((true.B)))
      // x374 = VecApply(x373,0)
      val x374_elem_0 = Wire(UInt(96.W)).suggestName("""x374_elem_0""")
      x374_elem_0.r := x373_deq_x361(0).r
      val x375_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x375_apply""")
      x375_apply.r := x374_elem_0(95, 64)
      val x376_wr_x371_banks = List[UInt]()
      val x376_wr_x371_ofs = List[UInt]()
      val x376_wr_x371_en = List[Bool](true.B)
      val x376_wr_x371_data = List[UInt](x375_apply.r)
      x371_reg.connectWPort(376, x376_wr_x371_banks, x376_wr_x371_ofs, x376_wr_x371_data, x376_wr_x371_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x377_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x377_apply""")
      x377_apply.r := x374_elem_0(31, 0)
      val x378_wr_x372_banks = List[UInt]()
      val x378_wr_x372_ofs = List[UInt]()
      val x378_wr_x372_en = List[Bool](true.B)
      val x378_wr_x372_data = List[UInt](x377_apply.r)
      x372_reg.connectWPort(378, x378_wr_x372_banks, x378_wr_x372_ofs, x378_wr_x372_data, x378_wr_x372_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x379_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x379_inr_UnitPipe **/
