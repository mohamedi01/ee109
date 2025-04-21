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

/** Hierarchy: x1132 -> x1152 -> x1153 -> x1334 -> x1335 -> x1250 -> x1251 -> x810 **/
/** BEGIN None x1132_inr_UnitPipe **/
class x1132_inr_UnitPipe_kernel(
  list_b1120: List[Bool],
  list_x1087_fifo: List[FIFOInterface],
  list_x1123_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x1132_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1132_inr_UnitPipe_iiCtr"))
  
  abstract class x1132_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x1087_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x1087_fifo_p").asInstanceOf[MemParams] ))
      val in_x1123_reg = Flipped(new StandardInterface(ModuleParams.getParams("x1123_reg_p").asInstanceOf[MemParams] ))
      val in_x1121_reg = Flipped(new StandardInterface(ModuleParams.getParams("x1121_reg_p").asInstanceOf[MemParams] ))
      val in_x1122_reg = Flipped(new StandardInterface(ModuleParams.getParams("x1122_reg_p").asInstanceOf[MemParams] ))
      val in_b1120 = Input(Bool())
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x1087_fifo = {io.in_x1087_fifo} ; io.in_x1087_fifo := DontCare
    def x1123_reg = {io.in_x1123_reg} ; io.in_x1123_reg := DontCare
    def x1121_reg = {io.in_x1121_reg} ; io.in_x1121_reg := DontCare
    def x1122_reg = {io.in_x1122_reg} ; io.in_x1122_reg := DontCare
    def b1120 = {io.in_b1120} 
  }
  def connectWires0(module: x1132_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x1087_fifo.connectLedger(module.io.in_x1087_fifo)
    x1123_reg.connectLedger(module.io.in_x1123_reg)
    x1121_reg.connectLedger(module.io.in_x1121_reg)
    x1122_reg.connectLedger(module.io.in_x1122_reg)
    module.io.in_b1120 <> b1120
  }
  val b1120 = list_b1120(0)
  val x1087_fifo = list_x1087_fifo(0)
  val x1123_reg = list_x1123_reg(0)
  val x1121_reg = list_x1123_reg(1)
  val x1122_reg = list_x1123_reg(2)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1132_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x1132_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1132_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1132_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x1132_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x1132_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x1132_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x1132_inr_UnitPipe = Module(new InstrumentationCounter())
      val idles_x1132_inr_UnitPipe = Module(new InstrumentationCounter())
      stalls_x1132_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(true.B)
      idles_x1132_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(((~x1087_fifo.empty.D(1.0-1) | ~(x1087_fifo.active(1).out))) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X1132_instrctr, cycles_x1132_inr_UnitPipe.io.count, iters_x1132_inr_UnitPipe.io.count, stalls_x1132_inr_UnitPipe.io.count, idles_x1132_inr_UnitPipe.io.count)
      val x1124_deq_x1087 = Wire(Vec(1, UInt(96.W))).suggestName("""x1124_deq_x1087""")
      val x1124_deq_x1087_banks = List[UInt]()
      val x1124_deq_x1087_ofs = List[UInt]()
      val x1124_deq_x1087_en = List[Bool](true.B)
      val x1124_deq_x1087_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1124_deq_x1087_shared_en")
      x1124_deq_x1087.toSeq.zip(x1087_fifo.connectRPort(1124, x1124_deq_x1087_banks, x1124_deq_x1087_ofs, io.sigsIn.backpressure, x1124_deq_x1087_en.map(_ && x1124_deq_x1087_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      x1087_fifo.connectAccessActivesIn(1, ((true.B)))
      // x1125 = VecApply(x1124,0)
      val x1125_elem_0 = Wire(UInt(96.W)).suggestName("""x1125_elem_0""")
      x1125_elem_0.r := x1124_deq_x1087(0).r
      val x1126_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1126_apply""")
      x1126_apply.r := x1125_elem_0(63, 32)
      val x1127_wr_x1121_banks = List[UInt]()
      val x1127_wr_x1121_ofs = List[UInt]()
      val x1127_wr_x1121_en = List[Bool](true.B)
      val x1127_wr_x1121_data = List[UInt](x1126_apply.r)
      x1121_reg.connectWPort(1127, x1127_wr_x1121_banks, x1127_wr_x1121_ofs, x1127_wr_x1121_data, x1127_wr_x1121_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x1128_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1128_apply""")
      x1128_apply.r := x1125_elem_0(95, 64)
      val x1129_wr_x1122_banks = List[UInt]()
      val x1129_wr_x1122_ofs = List[UInt]()
      val x1129_wr_x1122_en = List[Bool](true.B)
      val x1129_wr_x1122_data = List[UInt](x1128_apply.r)
      x1122_reg.connectWPort(1129, x1129_wr_x1122_banks, x1129_wr_x1122_ofs, x1129_wr_x1122_data, x1129_wr_x1122_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x1130_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1130_apply""")
      x1130_apply.r := x1125_elem_0(31, 0)
      val x1131_wr_x1123_banks = List[UInt]()
      val x1131_wr_x1123_ofs = List[UInt]()
      val x1131_wr_x1123_en = List[Bool](true.B)
      val x1131_wr_x1123_data = List[UInt](x1130_apply.r)
      x1123_reg.connectWPort(1131, x1131_wr_x1123_banks, x1131_wr_x1123_ofs, x1131_wr_x1123_data, x1131_wr_x1123_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x1132_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x1132_inr_UnitPipe **/
