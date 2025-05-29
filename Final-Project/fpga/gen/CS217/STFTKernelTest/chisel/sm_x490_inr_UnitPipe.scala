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

/** Hierarchy: x490 -> x504 -> x505 -> x708 -> x710 **/
/** BEGIN None x490_inr_UnitPipe **/
class x490_inr_UnitPipe_kernel(
  list_x472_fifo: List[FIFOInterface],
  list_x483_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x490_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x490_inr_UnitPipe_iiCtr"))
  
  abstract class x490_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x472_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x472_fifo_p").asInstanceOf[MemParams] ))
      val in_x483_reg = Flipped(new StandardInterface(ModuleParams.getParams("x483_reg_p").asInstanceOf[MemParams] ))
      val in_x482_reg = Flipped(new StandardInterface(ModuleParams.getParams("x482_reg_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x472_fifo = {io.in_x472_fifo} ; io.in_x472_fifo := DontCare
    def x483_reg = {io.in_x483_reg} ; io.in_x483_reg := DontCare
    def x482_reg = {io.in_x482_reg} ; io.in_x482_reg := DontCare
  }
  def connectWires0(module: x490_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x472_fifo.connectLedger(module.io.in_x472_fifo)
    x483_reg.connectLedger(module.io.in_x483_reg)
    x482_reg.connectLedger(module.io.in_x482_reg)
  }
  val x472_fifo = list_x472_fifo(0)
  val x483_reg = list_x483_reg(0)
  val x482_reg = list_x483_reg(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x490_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x490_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x490_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x490_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x490_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x490_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x490_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x490_inr_UnitPipe = Module(new InstrumentationCounter())
      val idles_x490_inr_UnitPipe = Module(new InstrumentationCounter())
      stalls_x490_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(true.B)
      idles_x490_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(((~x472_fifo.empty.D(1.0-1) | ~(x472_fifo.active(1).out))) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X490_instrctr, cycles_x490_inr_UnitPipe.io.count, iters_x490_inr_UnitPipe.io.count, stalls_x490_inr_UnitPipe.io.count, idles_x490_inr_UnitPipe.io.count)
      val x484_deq_x472 = Wire(Vec(1, UInt(96.W))).suggestName("""x484_deq_x472""")
      val x484_deq_x472_banks = List[UInt]()
      val x484_deq_x472_ofs = List[UInt]()
      val x484_deq_x472_en = List[Bool](true.B)
      val x484_deq_x472_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x484_deq_x472_shared_en")
      x484_deq_x472.toSeq.zip(x472_fifo.connectRPort(484, x484_deq_x472_banks, x484_deq_x472_ofs, io.sigsIn.backpressure, x484_deq_x472_en.map(_ && x484_deq_x472_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      x472_fifo.connectAccessActivesIn(1, ((true.B)))
      // x485 = VecApply(x484,0)
      val x485_elem_0 = Wire(UInt(96.W)).suggestName("""x485_elem_0""")
      x485_elem_0.r := x484_deq_x472(0).r
      val x486_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x486_apply""")
      x486_apply.r := x485_elem_0(95, 64)
      val x487_wr_x482_banks = List[UInt]()
      val x487_wr_x482_ofs = List[UInt]()
      val x487_wr_x482_en = List[Bool](true.B)
      val x487_wr_x482_data = List[UInt](x486_apply.r)
      x482_reg.connectWPort(487, x487_wr_x482_banks, x487_wr_x482_ofs, x487_wr_x482_data, x487_wr_x482_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x488_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x488_apply""")
      x488_apply.r := x485_elem_0(31, 0)
      val x489_wr_x483_banks = List[UInt]()
      val x489_wr_x483_ofs = List[UInt]()
      val x489_wr_x483_en = List[Bool](true.B)
      val x489_wr_x483_data = List[UInt](x488_apply.r)
      x483_reg.connectWPort(489, x489_wr_x483_banks, x489_wr_x483_ofs, x489_wr_x483_data, x489_wr_x483_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x490_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x490_inr_UnitPipe **/
