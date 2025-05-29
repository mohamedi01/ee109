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

/** Hierarchy: x496 -> x510 -> x511 -> x613 -> x614 **/
/** BEGIN None x496_inr_UnitPipe **/
class x496_inr_UnitPipe_kernel(
  list_x478_fifo: List[FIFOInterface],
  list_x488_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x496_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x496_inr_UnitPipe_iiCtr"))
  
  abstract class x496_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x488_reg = Flipped(new StandardInterface(ModuleParams.getParams("x488_reg_p").asInstanceOf[MemParams] ))
      val in_x489_reg = Flipped(new StandardInterface(ModuleParams.getParams("x489_reg_p").asInstanceOf[MemParams] ))
      val in_x478_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x478_fifo_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x488_reg = {io.in_x488_reg} ; io.in_x488_reg := DontCare
    def x489_reg = {io.in_x489_reg} ; io.in_x489_reg := DontCare
    def x478_fifo = {io.in_x478_fifo} ; io.in_x478_fifo := DontCare
  }
  def connectWires0(module: x496_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x488_reg.connectLedger(module.io.in_x488_reg)
    x489_reg.connectLedger(module.io.in_x489_reg)
    x478_fifo.connectLedger(module.io.in_x478_fifo)
  }
  val x478_fifo = list_x478_fifo(0)
  val x488_reg = list_x488_reg(0)
  val x489_reg = list_x488_reg(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x496_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x496_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x496_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x496_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x496_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x496_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x496_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x496_inr_UnitPipe = Module(new InstrumentationCounter())
      val idles_x496_inr_UnitPipe = Module(new InstrumentationCounter())
      stalls_x496_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(true.B)
      idles_x496_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(((~x478_fifo.empty.D(1.0-1) | ~(x478_fifo.active(1).out))) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X496_instrctr, cycles_x496_inr_UnitPipe.io.count, iters_x496_inr_UnitPipe.io.count, stalls_x496_inr_UnitPipe.io.count, idles_x496_inr_UnitPipe.io.count)
      val x490_deq_x478 = Wire(Vec(1, UInt(96.W))).suggestName("""x490_deq_x478""")
      val x490_deq_x478_banks = List[UInt]()
      val x490_deq_x478_ofs = List[UInt]()
      val x490_deq_x478_en = List[Bool](true.B)
      val x490_deq_x478_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x490_deq_x478_shared_en")
      x490_deq_x478.toSeq.zip(x478_fifo.connectRPort(490, x490_deq_x478_banks, x490_deq_x478_ofs, io.sigsIn.backpressure, x490_deq_x478_en.map(_ && x490_deq_x478_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      x478_fifo.connectAccessActivesIn(1, ((true.B)))
      // x491 = VecApply(x490,0)
      val x491_elem_0 = Wire(UInt(96.W)).suggestName("""x491_elem_0""")
      x491_elem_0.r := x490_deq_x478(0).r
      val x492_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x492_apply""")
      x492_apply.r := x491_elem_0(95, 64)
      val x493_wr_x488_banks = List[UInt]()
      val x493_wr_x488_ofs = List[UInt]()
      val x493_wr_x488_en = List[Bool](true.B)
      val x493_wr_x488_data = List[UInt](x492_apply.r)
      x488_reg.connectWPort(493, x493_wr_x488_banks, x493_wr_x488_ofs, x493_wr_x488_data, x493_wr_x488_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x494_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x494_apply""")
      x494_apply.r := x491_elem_0(31, 0)
      val x495_wr_x489_banks = List[UInt]()
      val x495_wr_x489_ofs = List[UInt]()
      val x495_wr_x489_en = List[Bool](true.B)
      val x495_wr_x489_data = List[UInt](x494_apply.r)
      x489_reg.connectWPort(495, x495_wr_x489_banks, x495_wr_x489_ofs, x495_wr_x489_data, x495_wr_x489_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x496_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x496_inr_UnitPipe **/
