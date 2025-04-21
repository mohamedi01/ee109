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

/** Hierarchy: x176 -> x187 -> x119 **/
/** BEGIN None x176_inr_Foreach **/
class x176_inr_Foreach_kernel(
  list_b168: List[Bool],
  list_x170_tmp_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 1.0.toInt, myName = "x176_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x176_inr_Foreach_iiCtr"))
  
  abstract class x176_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x170_tmp_0 = Flipped(new NBufInterface(ModuleParams.getParams("x170_tmp_0_p").asInstanceOf[NBufParams] ))
      val in_b168 = Input(Bool())
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x170_tmp_0 = {io.in_x170_tmp_0} ; io.in_x170_tmp_0 := DontCare
    def b168 = {io.in_b168} 
  }
  def connectWires0(module: x176_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x170_tmp_0.connectLedger(module.io.in_x170_tmp_0)
    module.io.in_b168 <> b168
  }
  val b168 = list_b168(0)
  val x170_tmp_0 = list_x170_tmp_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x176_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x176_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x176_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x176_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x176_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x176_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x176_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X176_instrctr, cycles_x176_inr_Foreach.io.count, iters_x176_inr_Foreach.io.count, 0.U, 0.U)
      val b173 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b173.suggestName("b173")
      val b174 = ~io.sigsIn.cchainOutputs.head.oobs(0); b174.suggestName("b174")
      val x175_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x175_wr_ofs = List[UInt](b173.r)
      val x175_wr_en = List[Bool](true.B)
      val x175_wr_data = List[UInt](1L.FP(true, 32, 0).r)
      x170_tmp_0.connectWPort(175, x175_wr_banks, x175_wr_ofs, x175_wr_data, x175_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && b174 & b168))
      x170_tmp_0.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 0)
    }
    val module = Module(new x176_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x176_inr_Foreach **/
