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

/** Hierarchy: x981 -> x1001 -> x1002 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x981_inr_UnitPipe **/
class x981_inr_UnitPipe_kernel(
  list_b969: List[Bool],
  list_x936_fifo: List[FIFOInterface],
  list_x970_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x981_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x981_inr_UnitPipe_iiCtr"))
  
  abstract class x981_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b969 = Input(Bool())
      val in_x970_reg = Flipped(new StandardInterface(ModuleParams.getParams("x970_reg_p").asInstanceOf[MemParams] ))
      val in_x936_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x936_fifo_p").asInstanceOf[MemParams] ))
      val in_x971_reg = Flipped(new StandardInterface(ModuleParams.getParams("x971_reg_p").asInstanceOf[MemParams] ))
      val in_x972_reg = Flipped(new StandardInterface(ModuleParams.getParams("x972_reg_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def b969 = {io.in_b969} 
    def x970_reg = {io.in_x970_reg} ; io.in_x970_reg := DontCare
    def x936_fifo = {io.in_x936_fifo} ; io.in_x936_fifo := DontCare
    def x971_reg = {io.in_x971_reg} ; io.in_x971_reg := DontCare
    def x972_reg = {io.in_x972_reg} ; io.in_x972_reg := DontCare
  }
  def connectWires0(module: x981_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b969 <> b969
    x970_reg.connectLedger(module.io.in_x970_reg)
    x936_fifo.connectLedger(module.io.in_x936_fifo)
    x971_reg.connectLedger(module.io.in_x971_reg)
    x972_reg.connectLedger(module.io.in_x972_reg)
  }
  val b969 = list_b969(0)
  val x936_fifo = list_x936_fifo(0)
  val x970_reg = list_x970_reg(0)
  val x971_reg = list_x970_reg(1)
  val x972_reg = list_x970_reg(2)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x981_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x981_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x981_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x981_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x981_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x981_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x981_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x981_inr_UnitPipe = Module(new InstrumentationCounter())
      val idles_x981_inr_UnitPipe = Module(new InstrumentationCounter())
      stalls_x981_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(true.B)
      idles_x981_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(((~x936_fifo.empty.D(1.0-1) | ~(x936_fifo.active(1).out))) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X981_instrctr, cycles_x981_inr_UnitPipe.io.count, iters_x981_inr_UnitPipe.io.count, stalls_x981_inr_UnitPipe.io.count, idles_x981_inr_UnitPipe.io.count)
      val x973_deq_x936 = Wire(Vec(1, UInt(96.W))).suggestName("""x973_deq_x936""")
      val x973_deq_x936_banks = List[UInt]()
      val x973_deq_x936_ofs = List[UInt]()
      val x973_deq_x936_en = List[Bool](true.B)
      val x973_deq_x936_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x973_deq_x936_shared_en")
      x973_deq_x936.toSeq.zip(x936_fifo.connectRPort(973, x973_deq_x936_banks, x973_deq_x936_ofs, io.sigsIn.backpressure, x973_deq_x936_en.map(_ && x973_deq_x936_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      x936_fifo.connectAccessActivesIn(1, ((true.B)))
      // x974 = VecApply(x973,0)
      val x974_elem_0 = Wire(UInt(96.W)).suggestName("""x974_elem_0""")
      x974_elem_0.r := x973_deq_x936(0).r
      val x975_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x975_apply""")
      x975_apply.r := x974_elem_0(63, 32)
      val x976_wr_x970_banks = List[UInt]()
      val x976_wr_x970_ofs = List[UInt]()
      val x976_wr_x970_en = List[Bool](true.B)
      val x976_wr_x970_data = List[UInt](x975_apply.r)
      x970_reg.connectWPort(976, x976_wr_x970_banks, x976_wr_x970_ofs, x976_wr_x970_data, x976_wr_x970_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x977_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x977_apply""")
      x977_apply.r := x974_elem_0(95, 64)
      val x978_wr_x971_banks = List[UInt]()
      val x978_wr_x971_ofs = List[UInt]()
      val x978_wr_x971_en = List[Bool](true.B)
      val x978_wr_x971_data = List[UInt](x977_apply.r)
      x971_reg.connectWPort(978, x978_wr_x971_banks, x978_wr_x971_ofs, x978_wr_x971_data, x978_wr_x971_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x979_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x979_apply""")
      x979_apply.r := x974_elem_0(31, 0)
      val x980_wr_x972_banks = List[UInt]()
      val x980_wr_x972_ofs = List[UInt]()
      val x980_wr_x972_en = List[Bool](true.B)
      val x980_wr_x972_data = List[UInt](x979_apply.r)
      x972_reg.connectWPort(980, x980_wr_x972_banks, x980_wr_x972_ofs, x980_wr_x972_data, x980_wr_x972_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x981_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x981_inr_UnitPipe **/
