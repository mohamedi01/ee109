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

/** Hierarchy: x348 -> x362 -> x363 -> x480 -> x481 **/
/** BEGIN None x348_inr_UnitPipe **/
class x348_inr_UnitPipe_kernel(
  list_x330_fifo: List[FIFOInterface],
  list_x340_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x348_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x348_inr_UnitPipe_iiCtr"))
  
  abstract class x348_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x340_reg = Flipped(new StandardInterface(ModuleParams.getParams("x340_reg_p").asInstanceOf[MemParams] ))
      val in_x330_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x330_fifo_p").asInstanceOf[MemParams] ))
      val in_x341_reg = Flipped(new StandardInterface(ModuleParams.getParams("x341_reg_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x340_reg = {io.in_x340_reg} ; io.in_x340_reg := DontCare
    def x330_fifo = {io.in_x330_fifo} ; io.in_x330_fifo := DontCare
    def x341_reg = {io.in_x341_reg} ; io.in_x341_reg := DontCare
  }
  def connectWires0(module: x348_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x340_reg.connectLedger(module.io.in_x340_reg)
    x330_fifo.connectLedger(module.io.in_x330_fifo)
    x341_reg.connectLedger(module.io.in_x341_reg)
  }
  val x330_fifo = list_x330_fifo(0)
  val x340_reg = list_x340_reg(0)
  val x341_reg = list_x340_reg(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x348_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x348_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x348_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x348_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x348_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x348_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x348_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x348_inr_UnitPipe = Module(new InstrumentationCounter())
      val idles_x348_inr_UnitPipe = Module(new InstrumentationCounter())
      stalls_x348_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(true.B)
      idles_x348_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(((~x330_fifo.empty.D(1.0-1) | ~(x330_fifo.active(1).out))) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X348_instrctr, cycles_x348_inr_UnitPipe.io.count, iters_x348_inr_UnitPipe.io.count, stalls_x348_inr_UnitPipe.io.count, idles_x348_inr_UnitPipe.io.count)
      val x342_deq_x330 = Wire(Vec(1, UInt(96.W))).suggestName("""x342_deq_x330""")
      val x342_deq_x330_banks = List[UInt]()
      val x342_deq_x330_ofs = List[UInt]()
      val x342_deq_x330_en = List[Bool](true.B)
      val x342_deq_x330_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x342_deq_x330_shared_en")
      x342_deq_x330.toSeq.zip(x330_fifo.connectRPort(342, x342_deq_x330_banks, x342_deq_x330_ofs, io.sigsIn.backpressure, x342_deq_x330_en.map(_ && x342_deq_x330_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      x330_fifo.connectAccessActivesIn(1, ((true.B)))
      // x343 = VecApply(x342,0)
      val x343_elem_0 = Wire(UInt(96.W)).suggestName("""x343_elem_0""")
      x343_elem_0.r := x342_deq_x330(0).r
      val x344_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x344_apply""")
      x344_apply.r := x343_elem_0(95, 64)
      val x345_wr_x340_banks = List[UInt]()
      val x345_wr_x340_ofs = List[UInt]()
      val x345_wr_x340_en = List[Bool](true.B)
      val x345_wr_x340_data = List[UInt](x344_apply.r)
      x340_reg.connectWPort(345, x345_wr_x340_banks, x345_wr_x340_ofs, x345_wr_x340_data, x345_wr_x340_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x346_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x346_apply""")
      x346_apply.r := x343_elem_0(31, 0)
      val x347_wr_x341_banks = List[UInt]()
      val x347_wr_x341_ofs = List[UInt]()
      val x347_wr_x341_en = List[Bool](true.B)
      val x347_wr_x341_data = List[UInt](x346_apply.r)
      x341_reg.connectWPort(347, x347_wr_x341_banks, x347_wr_x341_ofs, x347_wr_x341_data, x347_wr_x341_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x348_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x348_inr_UnitPipe **/
