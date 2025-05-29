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

/** Hierarchy: x511 -> x708 -> x710 **/
/** BEGIN None x511_inr_Foreach **/
class x511_inr_Foreach_kernel(
  list_x464_winSRAM_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 1.0.toInt, myName = "x511_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x511_inr_Foreach_iiCtr"))
  
  abstract class x511_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x464_winSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x464_winSRAM_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x464_winSRAM_0 = {io.in_x464_winSRAM_0} ; io.in_x464_winSRAM_0 := DontCare
  }
  def connectWires0(module: x511_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x464_winSRAM_0.connectLedger(module.io.in_x464_winSRAM_0)
  }
  val x464_winSRAM_0 = list_x464_winSRAM_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x511_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x511_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x511_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x511_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x511_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x511_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x511_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X511_instrctr, cycles_x511_inr_Foreach.io.count, iters_x511_inr_Foreach.io.count, 0.U, 0.U)
      val b508 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b508.suggestName("b508")
      val b509 = ~io.sigsIn.cchainOutputs.head.oobs(0); b509.suggestName("b509")
      val x510_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x510_wr_ofs = List[UInt](b508.r)
      val x510_wr_en = List[Bool](true.B)
      val x510_wr_data = List[UInt](1.FlP(24, 8).r)
      x464_winSRAM_0.connectWPort(510, x510_wr_banks, x510_wr_ofs, x510_wr_data, x510_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && b509))
    }
    val module = Module(new x511_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x511_inr_Foreach **/
