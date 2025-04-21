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

/** Hierarchy: x1065 -> x1084 -> x1085 -> x1334 -> x1335 -> x1250 -> x1251 -> x810 **/
/** BEGIN None x1065_inr_UnitPipe **/
class x1065_inr_UnitPipe_kernel(
  list_b1053: List[Bool],
  list_x1020_fifo: List[FIFOInterface],
  list_x1055_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x1065_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1065_inr_UnitPipe_iiCtr"))
  
  abstract class x1065_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x1055_reg = Flipped(new StandardInterface(ModuleParams.getParams("x1055_reg_p").asInstanceOf[MemParams] ))
      val in_x1020_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x1020_fifo_p").asInstanceOf[MemParams] ))
      val in_x1054_reg = Flipped(new StandardInterface(ModuleParams.getParams("x1054_reg_p").asInstanceOf[MemParams] ))
      val in_b1053 = Input(Bool())
      val in_x1056_reg = Flipped(new StandardInterface(ModuleParams.getParams("x1056_reg_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x1055_reg = {io.in_x1055_reg} ; io.in_x1055_reg := DontCare
    def x1020_fifo = {io.in_x1020_fifo} ; io.in_x1020_fifo := DontCare
    def x1054_reg = {io.in_x1054_reg} ; io.in_x1054_reg := DontCare
    def b1053 = {io.in_b1053} 
    def x1056_reg = {io.in_x1056_reg} ; io.in_x1056_reg := DontCare
  }
  def connectWires0(module: x1065_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x1055_reg.connectLedger(module.io.in_x1055_reg)
    x1020_fifo.connectLedger(module.io.in_x1020_fifo)
    x1054_reg.connectLedger(module.io.in_x1054_reg)
    module.io.in_b1053 <> b1053
    x1056_reg.connectLedger(module.io.in_x1056_reg)
  }
  val b1053 = list_b1053(0)
  val x1020_fifo = list_x1020_fifo(0)
  val x1055_reg = list_x1055_reg(0)
  val x1054_reg = list_x1055_reg(1)
  val x1056_reg = list_x1055_reg(2)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1065_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x1065_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1065_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1065_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x1065_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x1065_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x1065_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x1065_inr_UnitPipe = Module(new InstrumentationCounter())
      val idles_x1065_inr_UnitPipe = Module(new InstrumentationCounter())
      stalls_x1065_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(true.B)
      idles_x1065_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(((~x1020_fifo.empty.D(1.0-1) | ~(x1020_fifo.active(1).out))) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X1065_instrctr, cycles_x1065_inr_UnitPipe.io.count, iters_x1065_inr_UnitPipe.io.count, stalls_x1065_inr_UnitPipe.io.count, idles_x1065_inr_UnitPipe.io.count)
      val x1057_deq_x1020 = Wire(Vec(1, UInt(96.W))).suggestName("""x1057_deq_x1020""")
      val x1057_deq_x1020_banks = List[UInt]()
      val x1057_deq_x1020_ofs = List[UInt]()
      val x1057_deq_x1020_en = List[Bool](true.B)
      val x1057_deq_x1020_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1057_deq_x1020_shared_en")
      x1057_deq_x1020.toSeq.zip(x1020_fifo.connectRPort(1057, x1057_deq_x1020_banks, x1057_deq_x1020_ofs, io.sigsIn.backpressure, x1057_deq_x1020_en.map(_ && x1057_deq_x1020_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      x1020_fifo.connectAccessActivesIn(1, ((true.B)))
      // x1058 = VecApply(x1057,0)
      val x1058_elem_0 = Wire(UInt(96.W)).suggestName("""x1058_elem_0""")
      x1058_elem_0.r := x1057_deq_x1020(0).r
      val x1059_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1059_apply""")
      x1059_apply.r := x1058_elem_0(63, 32)
      val x1060_wr_x1054_banks = List[UInt]()
      val x1060_wr_x1054_ofs = List[UInt]()
      val x1060_wr_x1054_en = List[Bool](true.B)
      val x1060_wr_x1054_data = List[UInt](x1059_apply.r)
      x1054_reg.connectWPort(1060, x1060_wr_x1054_banks, x1060_wr_x1054_ofs, x1060_wr_x1054_data, x1060_wr_x1054_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x1061_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1061_apply""")
      x1061_apply.r := x1058_elem_0(95, 64)
      val x1062_wr_x1055_banks = List[UInt]()
      val x1062_wr_x1055_ofs = List[UInt]()
      val x1062_wr_x1055_en = List[Bool](true.B)
      val x1062_wr_x1055_data = List[UInt](x1061_apply.r)
      x1055_reg.connectWPort(1062, x1062_wr_x1055_banks, x1062_wr_x1055_ofs, x1062_wr_x1055_data, x1062_wr_x1055_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x1063_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1063_apply""")
      x1063_apply.r := x1058_elem_0(31, 0)
      val x1064_wr_x1056_banks = List[UInt]()
      val x1064_wr_x1056_ofs = List[UInt]()
      val x1064_wr_x1056_en = List[Bool](true.B)
      val x1064_wr_x1056_data = List[UInt](x1063_apply.r)
      x1056_reg.connectWPort(1064, x1064_wr_x1056_banks, x1064_wr_x1056_ofs, x1064_wr_x1056_data, x1064_wr_x1056_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x1065_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x1065_inr_UnitPipe **/
