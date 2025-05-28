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

/** Hierarchy: x383 -> x397 -> x398 -> x480 -> x481 **/
/** BEGIN None x383_inr_UnitPipe **/
class x383_inr_UnitPipe_kernel(
  list_x365_fifo: List[FIFOInterface],
  list_x376_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x383_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x383_inr_UnitPipe_iiCtr"))
  
  abstract class x383_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x365_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x365_fifo_p").asInstanceOf[MemParams] ))
      val in_x376_reg = Flipped(new StandardInterface(ModuleParams.getParams("x376_reg_p").asInstanceOf[MemParams] ))
      val in_x375_reg = Flipped(new StandardInterface(ModuleParams.getParams("x375_reg_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x365_fifo = {io.in_x365_fifo} ; io.in_x365_fifo := DontCare
    def x376_reg = {io.in_x376_reg} ; io.in_x376_reg := DontCare
    def x375_reg = {io.in_x375_reg} ; io.in_x375_reg := DontCare
  }
  def connectWires0(module: x383_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x365_fifo.connectLedger(module.io.in_x365_fifo)
    x376_reg.connectLedger(module.io.in_x376_reg)
    x375_reg.connectLedger(module.io.in_x375_reg)
  }
  val x365_fifo = list_x365_fifo(0)
  val x376_reg = list_x376_reg(0)
  val x375_reg = list_x376_reg(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x383_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x383_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x383_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x383_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x383_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x383_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x383_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x383_inr_UnitPipe = Module(new InstrumentationCounter())
      val idles_x383_inr_UnitPipe = Module(new InstrumentationCounter())
      stalls_x383_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(true.B)
      idles_x383_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(((~x365_fifo.empty.D(1.0-1) | ~(x365_fifo.active(1).out))) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X383_instrctr, cycles_x383_inr_UnitPipe.io.count, iters_x383_inr_UnitPipe.io.count, stalls_x383_inr_UnitPipe.io.count, idles_x383_inr_UnitPipe.io.count)
      val x377_deq_x365 = Wire(Vec(1, UInt(96.W))).suggestName("""x377_deq_x365""")
      val x377_deq_x365_banks = List[UInt]()
      val x377_deq_x365_ofs = List[UInt]()
      val x377_deq_x365_en = List[Bool](true.B)
      val x377_deq_x365_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x377_deq_x365_shared_en")
      x377_deq_x365.toSeq.zip(x365_fifo.connectRPort(377, x377_deq_x365_banks, x377_deq_x365_ofs, io.sigsIn.backpressure, x377_deq_x365_en.map(_ && x377_deq_x365_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      x365_fifo.connectAccessActivesIn(1, ((true.B)))
      // x378 = VecApply(x377,0)
      val x378_elem_0 = Wire(UInt(96.W)).suggestName("""x378_elem_0""")
      x378_elem_0.r := x377_deq_x365(0).r
      val x379_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x379_apply""")
      x379_apply.r := x378_elem_0(95, 64)
      val x380_wr_x375_banks = List[UInt]()
      val x380_wr_x375_ofs = List[UInt]()
      val x380_wr_x375_en = List[Bool](true.B)
      val x380_wr_x375_data = List[UInt](x379_apply.r)
      x375_reg.connectWPort(380, x380_wr_x375_banks, x380_wr_x375_ofs, x380_wr_x375_data, x380_wr_x375_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x381_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x381_apply""")
      x381_apply.r := x378_elem_0(31, 0)
      val x382_wr_x376_banks = List[UInt]()
      val x382_wr_x376_ofs = List[UInt]()
      val x382_wr_x376_en = List[Bool](true.B)
      val x382_wr_x376_data = List[UInt](x381_apply.r)
      x376_reg.connectWPort(382, x382_wr_x376_banks, x382_wr_x376_ofs, x382_wr_x376_data, x382_wr_x376_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x383_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x383_inr_UnitPipe **/
