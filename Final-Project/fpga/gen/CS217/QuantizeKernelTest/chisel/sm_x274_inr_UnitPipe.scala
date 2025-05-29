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

/** Hierarchy: x274 -> x288 -> x289 -> x213 **/
/** BEGIN None x274_inr_UnitPipe **/
class x274_inr_UnitPipe_kernel(
  list_x256_fifo: List[FIFOInterface],
  list_x266_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x274_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x274_inr_UnitPipe_iiCtr"))
  
  abstract class x274_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x256_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x256_fifo_p").asInstanceOf[MemParams] ))
      val in_x266_reg = Flipped(new StandardInterface(ModuleParams.getParams("x266_reg_p").asInstanceOf[MemParams] ))
      val in_x267_reg = Flipped(new StandardInterface(ModuleParams.getParams("x267_reg_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x256_fifo = {io.in_x256_fifo} ; io.in_x256_fifo := DontCare
    def x266_reg = {io.in_x266_reg} ; io.in_x266_reg := DontCare
    def x267_reg = {io.in_x267_reg} ; io.in_x267_reg := DontCare
  }
  def connectWires0(module: x274_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x256_fifo.connectLedger(module.io.in_x256_fifo)
    x266_reg.connectLedger(module.io.in_x266_reg)
    x267_reg.connectLedger(module.io.in_x267_reg)
  }
  val x256_fifo = list_x256_fifo(0)
  val x266_reg = list_x266_reg(0)
  val x267_reg = list_x266_reg(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x274_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x274_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x274_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x274_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x274_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x274_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x274_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x274_inr_UnitPipe = Module(new InstrumentationCounter())
      val idles_x274_inr_UnitPipe = Module(new InstrumentationCounter())
      stalls_x274_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(true.B)
      idles_x274_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(((~x256_fifo.empty.D(1.0-1) | ~(x256_fifo.active(1).out))) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X274_instrctr, cycles_x274_inr_UnitPipe.io.count, iters_x274_inr_UnitPipe.io.count, stalls_x274_inr_UnitPipe.io.count, idles_x274_inr_UnitPipe.io.count)
      val x268_deq_x256 = Wire(Vec(1, UInt(96.W))).suggestName("""x268_deq_x256""")
      val x268_deq_x256_banks = List[UInt]()
      val x268_deq_x256_ofs = List[UInt]()
      val x268_deq_x256_en = List[Bool](true.B)
      val x268_deq_x256_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x268_deq_x256_shared_en")
      x268_deq_x256.toSeq.zip(x256_fifo.connectRPort(268, x268_deq_x256_banks, x268_deq_x256_ofs, io.sigsIn.backpressure, x268_deq_x256_en.map(_ && x268_deq_x256_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      x256_fifo.connectAccessActivesIn(1, ((true.B)))
      // x269 = VecApply(x268,0)
      val x269_elem_0 = Wire(UInt(96.W)).suggestName("""x269_elem_0""")
      x269_elem_0.r := x268_deq_x256(0).r
      val x270_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x270_apply""")
      x270_apply.r := x269_elem_0(95, 64)
      val x271_wr_x266_banks = List[UInt]()
      val x271_wr_x266_ofs = List[UInt]()
      val x271_wr_x266_en = List[Bool](true.B)
      val x271_wr_x266_data = List[UInt](x270_apply.r)
      x266_reg.connectWPort(271, x271_wr_x266_banks, x271_wr_x266_ofs, x271_wr_x266_data, x271_wr_x266_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x272_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x272_apply""")
      x272_apply.r := x269_elem_0(31, 0)
      val x273_wr_x267_banks = List[UInt]()
      val x273_wr_x267_ofs = List[UInt]()
      val x273_wr_x267_en = List[Bool](true.B)
      val x273_wr_x267_data = List[UInt](x272_apply.r)
      x267_reg.connectWPort(273, x273_wr_x267_banks, x273_wr_x267_ofs, x273_wr_x267_data, x273_wr_x267_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x274_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x274_inr_UnitPipe **/
