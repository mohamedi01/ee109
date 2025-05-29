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

/** Hierarchy: x273 -> x287 -> x288 -> x201 **/
/** BEGIN None x273_inr_UnitPipe **/
class x273_inr_UnitPipe_kernel(
  list_x255_fifo: List[FIFOInterface],
  list_x265_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x273_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x273_inr_UnitPipe_iiCtr"))
  
  abstract class x273_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x265_reg = Flipped(new StandardInterface(ModuleParams.getParams("x265_reg_p").asInstanceOf[MemParams] ))
      val in_x266_reg = Flipped(new StandardInterface(ModuleParams.getParams("x266_reg_p").asInstanceOf[MemParams] ))
      val in_x255_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x255_fifo_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x265_reg = {io.in_x265_reg} ; io.in_x265_reg := DontCare
    def x266_reg = {io.in_x266_reg} ; io.in_x266_reg := DontCare
    def x255_fifo = {io.in_x255_fifo} ; io.in_x255_fifo := DontCare
  }
  def connectWires0(module: x273_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x265_reg.connectLedger(module.io.in_x265_reg)
    x266_reg.connectLedger(module.io.in_x266_reg)
    x255_fifo.connectLedger(module.io.in_x255_fifo)
  }
  val x255_fifo = list_x255_fifo(0)
  val x265_reg = list_x265_reg(0)
  val x266_reg = list_x265_reg(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x273_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x273_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x273_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x273_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x273_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x273_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x273_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x273_inr_UnitPipe = Module(new InstrumentationCounter())
      val idles_x273_inr_UnitPipe = Module(new InstrumentationCounter())
      stalls_x273_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(true.B)
      idles_x273_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(((~x255_fifo.empty.D(1.0-1) | ~(x255_fifo.active(1).out))) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X273_instrctr, cycles_x273_inr_UnitPipe.io.count, iters_x273_inr_UnitPipe.io.count, stalls_x273_inr_UnitPipe.io.count, idles_x273_inr_UnitPipe.io.count)
      val x267_deq_x255 = Wire(Vec(1, UInt(96.W))).suggestName("""x267_deq_x255""")
      val x267_deq_x255_banks = List[UInt]()
      val x267_deq_x255_ofs = List[UInt]()
      val x267_deq_x255_en = List[Bool](true.B)
      val x267_deq_x255_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x267_deq_x255_shared_en")
      x267_deq_x255.toSeq.zip(x255_fifo.connectRPort(267, x267_deq_x255_banks, x267_deq_x255_ofs, io.sigsIn.backpressure, x267_deq_x255_en.map(_ && x267_deq_x255_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      x255_fifo.connectAccessActivesIn(1, ((true.B)))
      // x268 = VecApply(x267,0)
      val x268_elem_0 = Wire(UInt(96.W)).suggestName("""x268_elem_0""")
      x268_elem_0.r := x267_deq_x255(0).r
      val x269_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x269_apply""")
      x269_apply.r := x268_elem_0(95, 64)
      val x270_wr_x265_banks = List[UInt]()
      val x270_wr_x265_ofs = List[UInt]()
      val x270_wr_x265_en = List[Bool](true.B)
      val x270_wr_x265_data = List[UInt](x269_apply.r)
      x265_reg.connectWPort(270, x270_wr_x265_banks, x270_wr_x265_ofs, x270_wr_x265_data, x270_wr_x265_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x271_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x271_apply""")
      x271_apply.r := x268_elem_0(31, 0)
      val x272_wr_x266_banks = List[UInt]()
      val x272_wr_x266_ofs = List[UInt]()
      val x272_wr_x266_en = List[Bool](true.B)
      val x272_wr_x266_data = List[UInt](x271_apply.r)
      x266_reg.connectWPort(272, x272_wr_x266_banks, x272_wr_x266_ofs, x272_wr_x266_data, x272_wr_x266_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x273_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x273_inr_UnitPipe **/
