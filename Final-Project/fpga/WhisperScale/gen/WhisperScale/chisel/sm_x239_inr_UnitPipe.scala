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

/** Hierarchy: x239 -> x253 -> x254 -> x196 **/
/** BEGIN None x239_inr_UnitPipe **/
class x239_inr_UnitPipe_kernel(
  list_x216_fifo: List[FIFOInterface],
  list_x231_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x239_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x239_inr_UnitPipe_iiCtr"))
  
  abstract class x239_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x216_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x216_fifo_p").asInstanceOf[MemParams] ))
      val in_x231_reg = Flipped(new StandardInterface(ModuleParams.getParams("x231_reg_p").asInstanceOf[MemParams] ))
      val in_x232_reg = Flipped(new StandardInterface(ModuleParams.getParams("x232_reg_p").asInstanceOf[MemParams] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x216_fifo = {io.in_x216_fifo} ; io.in_x216_fifo := DontCare
    def x231_reg = {io.in_x231_reg} ; io.in_x231_reg := DontCare
    def x232_reg = {io.in_x232_reg} ; io.in_x232_reg := DontCare
  }
  def connectWires0(module: x239_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x216_fifo.connectLedger(module.io.in_x216_fifo)
    x231_reg.connectLedger(module.io.in_x231_reg)
    x232_reg.connectLedger(module.io.in_x232_reg)
  }
  val x216_fifo = list_x216_fifo(0)
  val x231_reg = list_x231_reg(0)
  val x232_reg = list_x231_reg(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x239_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x239_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x239_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x233_deq_x216 = Wire(Vec(1, UInt(96.W))).suggestName("""x233_deq_x216""")
      val x233_deq_x216_banks = List[UInt]()
      val x233_deq_x216_ofs = List[UInt]()
      val x233_deq_x216_en = List[Bool](true.B)
      val x233_deq_x216_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x233_deq_x216_shared_en")
      x233_deq_x216.toSeq.zip(x216_fifo.connectRPort(233, x233_deq_x216_banks, x233_deq_x216_ofs, io.sigsIn.backpressure, x233_deq_x216_en.map(_ && x233_deq_x216_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      x216_fifo.connectAccessActivesIn(1, ((true.B)))
      // x234 = VecApply(x233,0)
      val x234_elem_0 = Wire(UInt(96.W)).suggestName("""x234_elem_0""")
      x234_elem_0.r := x233_deq_x216(0).r
      val x235_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x235_apply""")
      x235_apply.r := x234_elem_0(95, 64)
      val x236_wr_x231_banks = List[UInt]()
      val x236_wr_x231_ofs = List[UInt]()
      val x236_wr_x231_en = List[Bool](true.B)
      val x236_wr_x231_data = List[UInt](x235_apply.r)
      x231_reg.connectWPort(236, x236_wr_x231_banks, x236_wr_x231_ofs, x236_wr_x231_data, x236_wr_x231_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x237_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x237_apply""")
      x237_apply.r := x234_elem_0(31, 0)
      val x238_wr_x232_banks = List[UInt]()
      val x238_wr_x232_ofs = List[UInt]()
      val x238_wr_x232_en = List[Bool](true.B)
      val x238_wr_x232_data = List[UInt](x237_apply.r)
      x232_reg.connectWPort(238, x238_wr_x232_banks, x238_wr_x232_ofs, x238_wr_x232_data, x238_wr_x232_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x239_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x239_inr_UnitPipe **/
