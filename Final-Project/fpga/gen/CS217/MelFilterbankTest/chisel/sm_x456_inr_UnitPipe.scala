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

/** Hierarchy: x456 -> x475 -> x476 -> x613 -> x614 **/
/** BEGIN None x456_inr_UnitPipe **/
class x456_inr_UnitPipe_kernel(
  list_b444: List[Bool],
  list_x415_fifo: List[FIFOInterface],
  list_x445_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x456_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x456_inr_UnitPipe_iiCtr"))
  
  abstract class x456_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b444 = Input(Bool())
      val in_x445_reg = Flipped(new StandardInterface(ModuleParams.getParams("x445_reg_p").asInstanceOf[MemParams] ))
      val in_x446_reg = Flipped(new StandardInterface(ModuleParams.getParams("x446_reg_p").asInstanceOf[MemParams] ))
      val in_x447_reg = Flipped(new StandardInterface(ModuleParams.getParams("x447_reg_p").asInstanceOf[MemParams] ))
      val in_x415_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x415_fifo_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def b444 = {io.in_b444} 
    def x445_reg = {io.in_x445_reg} ; io.in_x445_reg := DontCare
    def x446_reg = {io.in_x446_reg} ; io.in_x446_reg := DontCare
    def x447_reg = {io.in_x447_reg} ; io.in_x447_reg := DontCare
    def x415_fifo = {io.in_x415_fifo} ; io.in_x415_fifo := DontCare
  }
  def connectWires0(module: x456_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b444 <> b444
    x445_reg.connectLedger(module.io.in_x445_reg)
    x446_reg.connectLedger(module.io.in_x446_reg)
    x447_reg.connectLedger(module.io.in_x447_reg)
    x415_fifo.connectLedger(module.io.in_x415_fifo)
  }
  val b444 = list_b444(0)
  val x415_fifo = list_x415_fifo(0)
  val x445_reg = list_x445_reg(0)
  val x446_reg = list_x445_reg(1)
  val x447_reg = list_x445_reg(2)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x456_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x456_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x456_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x456_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x456_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x456_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x456_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x456_inr_UnitPipe = Module(new InstrumentationCounter())
      val idles_x456_inr_UnitPipe = Module(new InstrumentationCounter())
      stalls_x456_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(true.B)
      idles_x456_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(((~x415_fifo.empty.D(1.0-1) | ~(x415_fifo.active(1).out))) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X456_instrctr, cycles_x456_inr_UnitPipe.io.count, iters_x456_inr_UnitPipe.io.count, stalls_x456_inr_UnitPipe.io.count, idles_x456_inr_UnitPipe.io.count)
      val x448_deq_x415 = Wire(Vec(1, UInt(96.W))).suggestName("""x448_deq_x415""")
      val x448_deq_x415_banks = List[UInt]()
      val x448_deq_x415_ofs = List[UInt]()
      val x448_deq_x415_en = List[Bool](true.B)
      val x448_deq_x415_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x448_deq_x415_shared_en")
      x448_deq_x415.toSeq.zip(x415_fifo.connectRPort(448, x448_deq_x415_banks, x448_deq_x415_ofs, io.sigsIn.backpressure, x448_deq_x415_en.map(_ && x448_deq_x415_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      x415_fifo.connectAccessActivesIn(1, ((true.B)))
      // x449 = VecApply(x448,0)
      val x449_elem_0 = Wire(UInt(96.W)).suggestName("""x449_elem_0""")
      x449_elem_0.r := x448_deq_x415(0).r
      val x450_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x450_apply""")
      x450_apply.r := x449_elem_0(63, 32)
      val x451_wr_x445_banks = List[UInt]()
      val x451_wr_x445_ofs = List[UInt]()
      val x451_wr_x445_en = List[Bool](true.B)
      val x451_wr_x445_data = List[UInt](x450_apply.r)
      x445_reg.connectWPort(451, x451_wr_x445_banks, x451_wr_x445_ofs, x451_wr_x445_data, x451_wr_x445_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x452_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x452_apply""")
      x452_apply.r := x449_elem_0(95, 64)
      val x453_wr_x446_banks = List[UInt]()
      val x453_wr_x446_ofs = List[UInt]()
      val x453_wr_x446_en = List[Bool](true.B)
      val x453_wr_x446_data = List[UInt](x452_apply.r)
      x446_reg.connectWPort(453, x453_wr_x446_banks, x453_wr_x446_ofs, x453_wr_x446_data, x453_wr_x446_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x454_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x454_apply""")
      x454_apply.r := x449_elem_0(31, 0)
      val x455_wr_x447_banks = List[UInt]()
      val x455_wr_x447_ofs = List[UInt]()
      val x455_wr_x447_en = List[Bool](true.B)
      val x455_wr_x447_data = List[UInt](x454_apply.r)
      x447_reg.connectWPort(455, x455_wr_x447_banks, x455_wr_x447_ofs, x455_wr_x447_data, x455_wr_x447_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x456_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x456_inr_UnitPipe **/
