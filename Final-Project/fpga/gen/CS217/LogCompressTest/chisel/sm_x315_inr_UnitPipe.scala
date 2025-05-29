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

/** Hierarchy: x315 -> x329 -> x330 -> x439 **/
/** BEGIN None x315_inr_UnitPipe **/
class x315_inr_UnitPipe_kernel(
  list_x297_fifo: List[FIFOInterface],
  list_x308_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x315_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x315_inr_UnitPipe_iiCtr"))
  
  abstract class x315_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x308_reg = Flipped(new StandardInterface(ModuleParams.getParams("x308_reg_p").asInstanceOf[MemParams] ))
      val in_x307_reg = Flipped(new StandardInterface(ModuleParams.getParams("x307_reg_p").asInstanceOf[MemParams] ))
      val in_x297_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x297_fifo_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x308_reg = {io.in_x308_reg} ; io.in_x308_reg := DontCare
    def x307_reg = {io.in_x307_reg} ; io.in_x307_reg := DontCare
    def x297_fifo = {io.in_x297_fifo} ; io.in_x297_fifo := DontCare
  }
  def connectWires0(module: x315_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x308_reg.connectLedger(module.io.in_x308_reg)
    x307_reg.connectLedger(module.io.in_x307_reg)
    x297_fifo.connectLedger(module.io.in_x297_fifo)
  }
  val x297_fifo = list_x297_fifo(0)
  val x308_reg = list_x308_reg(0)
  val x307_reg = list_x308_reg(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x315_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x315_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x315_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x315_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x315_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x315_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x315_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x315_inr_UnitPipe = Module(new InstrumentationCounter())
      val idles_x315_inr_UnitPipe = Module(new InstrumentationCounter())
      stalls_x315_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(true.B)
      idles_x315_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(((~x297_fifo.empty.D(1.0-1) | ~(x297_fifo.active(1).out))) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X315_instrctr, cycles_x315_inr_UnitPipe.io.count, iters_x315_inr_UnitPipe.io.count, stalls_x315_inr_UnitPipe.io.count, idles_x315_inr_UnitPipe.io.count)
      val x309_deq_x297 = Wire(Vec(1, UInt(96.W))).suggestName("""x309_deq_x297""")
      val x309_deq_x297_banks = List[UInt]()
      val x309_deq_x297_ofs = List[UInt]()
      val x309_deq_x297_en = List[Bool](true.B)
      val x309_deq_x297_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x309_deq_x297_shared_en")
      x309_deq_x297.toSeq.zip(x297_fifo.connectRPort(309, x309_deq_x297_banks, x309_deq_x297_ofs, io.sigsIn.backpressure, x309_deq_x297_en.map(_ && x309_deq_x297_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      x297_fifo.connectAccessActivesIn(1, ((true.B)))
      // x310 = VecApply(x309,0)
      val x310_elem_0 = Wire(UInt(96.W)).suggestName("""x310_elem_0""")
      x310_elem_0.r := x309_deq_x297(0).r
      val x311_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x311_apply""")
      x311_apply.r := x310_elem_0(95, 64)
      val x312_wr_x307_banks = List[UInt]()
      val x312_wr_x307_ofs = List[UInt]()
      val x312_wr_x307_en = List[Bool](true.B)
      val x312_wr_x307_data = List[UInt](x311_apply.r)
      x307_reg.connectWPort(312, x312_wr_x307_banks, x312_wr_x307_ofs, x312_wr_x307_data, x312_wr_x307_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x313_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x313_apply""")
      x313_apply.r := x310_elem_0(31, 0)
      val x314_wr_x308_banks = List[UInt]()
      val x314_wr_x308_ofs = List[UInt]()
      val x314_wr_x308_en = List[Bool](true.B)
      val x314_wr_x308_data = List[UInt](x313_apply.r)
      x308_reg.connectWPort(314, x314_wr_x308_banks, x314_wr_x308_ofs, x314_wr_x308_data, x314_wr_x308_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x315_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x315_inr_UnitPipe **/
