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

/** Hierarchy: x1066 -> x1086 -> x1087 -> x1811 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1066_inr_UnitPipe **/
class x1066_inr_UnitPipe_kernel(
  list_b1054: List[Bool],
  list_x1021_fifo: List[FIFOInterface],
  list_x1055_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x1066_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1066_inr_UnitPipe_iiCtr"))
  
  abstract class x1066_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x1055_reg = Flipped(new StandardInterface(ModuleParams.getParams("x1055_reg_p").asInstanceOf[MemParams] ))
      val in_x1057_reg = Flipped(new StandardInterface(ModuleParams.getParams("x1057_reg_p").asInstanceOf[MemParams] ))
      val in_x1021_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x1021_fifo_p").asInstanceOf[MemParams] ))
      val in_b1054 = Input(Bool())
      val in_x1056_reg = Flipped(new StandardInterface(ModuleParams.getParams("x1056_reg_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x1055_reg = {io.in_x1055_reg} ; io.in_x1055_reg := DontCare
    def x1057_reg = {io.in_x1057_reg} ; io.in_x1057_reg := DontCare
    def x1021_fifo = {io.in_x1021_fifo} ; io.in_x1021_fifo := DontCare
    def b1054 = {io.in_b1054} 
    def x1056_reg = {io.in_x1056_reg} ; io.in_x1056_reg := DontCare
  }
  def connectWires0(module: x1066_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x1055_reg.connectLedger(module.io.in_x1055_reg)
    x1057_reg.connectLedger(module.io.in_x1057_reg)
    x1021_fifo.connectLedger(module.io.in_x1021_fifo)
    module.io.in_b1054 <> b1054
    x1056_reg.connectLedger(module.io.in_x1056_reg)
  }
  val b1054 = list_b1054(0)
  val x1021_fifo = list_x1021_fifo(0)
  val x1055_reg = list_x1055_reg(0)
  val x1057_reg = list_x1055_reg(1)
  val x1056_reg = list_x1055_reg(2)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1066_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x1066_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1066_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1066_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x1066_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x1066_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x1066_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x1066_inr_UnitPipe = Module(new InstrumentationCounter())
      val idles_x1066_inr_UnitPipe = Module(new InstrumentationCounter())
      stalls_x1066_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(true.B)
      idles_x1066_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(((~x1021_fifo.empty.D(1.0-1) | ~(x1021_fifo.active(1).out))) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X1066_instrctr, cycles_x1066_inr_UnitPipe.io.count, iters_x1066_inr_UnitPipe.io.count, stalls_x1066_inr_UnitPipe.io.count, idles_x1066_inr_UnitPipe.io.count)
      val x1058_deq_x1021 = Wire(Vec(1, UInt(96.W))).suggestName("""x1058_deq_x1021""")
      val x1058_deq_x1021_banks = List[UInt]()
      val x1058_deq_x1021_ofs = List[UInt]()
      val x1058_deq_x1021_en = List[Bool](true.B)
      val x1058_deq_x1021_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1058_deq_x1021_shared_en")
      x1058_deq_x1021.toSeq.zip(x1021_fifo.connectRPort(1058, x1058_deq_x1021_banks, x1058_deq_x1021_ofs, io.sigsIn.backpressure, x1058_deq_x1021_en.map(_ && x1058_deq_x1021_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      x1021_fifo.connectAccessActivesIn(1, ((true.B)))
      // x1059 = VecApply(x1058,0)
      val x1059_elem_0 = Wire(UInt(96.W)).suggestName("""x1059_elem_0""")
      x1059_elem_0.r := x1058_deq_x1021(0).r
      val x1060_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1060_apply""")
      x1060_apply.r := x1059_elem_0(63, 32)
      val x1061_wr_x1055_banks = List[UInt]()
      val x1061_wr_x1055_ofs = List[UInt]()
      val x1061_wr_x1055_en = List[Bool](true.B)
      val x1061_wr_x1055_data = List[UInt](x1060_apply.r)
      x1055_reg.connectWPort(1061, x1061_wr_x1055_banks, x1061_wr_x1055_ofs, x1061_wr_x1055_data, x1061_wr_x1055_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x1062_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1062_apply""")
      x1062_apply.r := x1059_elem_0(95, 64)
      val x1063_wr_x1056_banks = List[UInt]()
      val x1063_wr_x1056_ofs = List[UInt]()
      val x1063_wr_x1056_en = List[Bool](true.B)
      val x1063_wr_x1056_data = List[UInt](x1062_apply.r)
      x1056_reg.connectWPort(1063, x1063_wr_x1056_banks, x1063_wr_x1056_ofs, x1063_wr_x1056_data, x1063_wr_x1056_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x1064_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1064_apply""")
      x1064_apply.r := x1059_elem_0(31, 0)
      val x1065_wr_x1057_banks = List[UInt]()
      val x1065_wr_x1057_ofs = List[UInt]()
      val x1065_wr_x1057_en = List[Bool](true.B)
      val x1065_wr_x1057_data = List[UInt](x1064_apply.r)
      x1057_reg.connectWPort(1065, x1065_wr_x1057_banks, x1065_wr_x1057_ofs, x1065_wr_x1057_data, x1065_wr_x1057_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x1066_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x1066_inr_UnitPipe **/
