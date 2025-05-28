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

/** Hierarchy: x301 -> x315 -> x316 -> x244 **/
/** BEGIN None x301_inr_UnitPipe **/
class x301_inr_UnitPipe_kernel(
  list_x283_fifo: List[FIFOInterface],
  list_x293_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x301_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x301_inr_UnitPipe_iiCtr"))
  
  abstract class x301_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x293_reg = Flipped(new StandardInterface(ModuleParams.getParams("x293_reg_p").asInstanceOf[MemParams] ))
      val in_x294_reg = Flipped(new StandardInterface(ModuleParams.getParams("x294_reg_p").asInstanceOf[MemParams] ))
      val in_x283_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x283_fifo_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x293_reg = {io.in_x293_reg} ; io.in_x293_reg := DontCare
    def x294_reg = {io.in_x294_reg} ; io.in_x294_reg := DontCare
    def x283_fifo = {io.in_x283_fifo} ; io.in_x283_fifo := DontCare
  }
  def connectWires0(module: x301_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x293_reg.connectLedger(module.io.in_x293_reg)
    x294_reg.connectLedger(module.io.in_x294_reg)
    x283_fifo.connectLedger(module.io.in_x283_fifo)
  }
  val x283_fifo = list_x283_fifo(0)
  val x293_reg = list_x293_reg(0)
  val x294_reg = list_x293_reg(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x301_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x301_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x301_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x301_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x301_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x301_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x301_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x301_inr_UnitPipe = Module(new InstrumentationCounter())
      val idles_x301_inr_UnitPipe = Module(new InstrumentationCounter())
      stalls_x301_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(true.B)
      idles_x301_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(((~x283_fifo.empty.D(1.0-1) | ~(x283_fifo.active(1).out))) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X301_instrctr, cycles_x301_inr_UnitPipe.io.count, iters_x301_inr_UnitPipe.io.count, stalls_x301_inr_UnitPipe.io.count, idles_x301_inr_UnitPipe.io.count)
      val x295_deq_x283 = Wire(Vec(1, UInt(96.W))).suggestName("""x295_deq_x283""")
      val x295_deq_x283_banks = List[UInt]()
      val x295_deq_x283_ofs = List[UInt]()
      val x295_deq_x283_en = List[Bool](true.B)
      val x295_deq_x283_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x295_deq_x283_shared_en")
      x295_deq_x283.toSeq.zip(x283_fifo.connectRPort(295, x295_deq_x283_banks, x295_deq_x283_ofs, io.sigsIn.backpressure, x295_deq_x283_en.map(_ && x295_deq_x283_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      x283_fifo.connectAccessActivesIn(1, ((true.B)))
      // x296 = VecApply(x295,0)
      val x296_elem_0 = Wire(UInt(96.W)).suggestName("""x296_elem_0""")
      x296_elem_0.r := x295_deq_x283(0).r
      val x297_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x297_apply""")
      x297_apply.r := x296_elem_0(95, 64)
      val x298_wr_x293_banks = List[UInt]()
      val x298_wr_x293_ofs = List[UInt]()
      val x298_wr_x293_en = List[Bool](true.B)
      val x298_wr_x293_data = List[UInt](x297_apply.r)
      x293_reg.connectWPort(298, x298_wr_x293_banks, x298_wr_x293_ofs, x298_wr_x293_data, x298_wr_x293_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x299_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x299_apply""")
      x299_apply.r := x296_elem_0(31, 0)
      val x300_wr_x294_banks = List[UInt]()
      val x300_wr_x294_ofs = List[UInt]()
      val x300_wr_x294_en = List[Bool](true.B)
      val x300_wr_x294_data = List[UInt](x299_apply.r)
      x294_reg.connectWPort(300, x300_wr_x294_banks, x300_wr_x294_ofs, x300_wr_x294_data, x300_wr_x294_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x301_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x301_inr_UnitPipe **/
