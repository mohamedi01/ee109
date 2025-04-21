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

/** Hierarchy: x214 -> x168 **/
/** BEGIN None x214_inr_UnitPipe **/
class x214_inr_UnitPipe_kernel(
  list_x212_reg_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x214_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x214_inr_UnitPipe_iiCtr"))
  
  abstract class x214_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x212_reg_0 = Flipped(new StandardInterface(ModuleParams.getParams("x212_reg_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x212_reg_0 = {io.in_x212_reg_0} ; io.in_x212_reg_0 := DontCare
  }
  def connectWires0(module: x214_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x212_reg_0.connectLedger(module.io.in_x212_reg_0)
  }
  val x212_reg_0 = list_x212_reg_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x214_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x214_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x214_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x214_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x214_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x214_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x214_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X214_instrctr, cycles_x214_inr_UnitPipe.io.count, iters_x214_inr_UnitPipe.io.count, 0.U, 0.U)
      val x213_wr_x212_banks = List[UInt]()
      val x213_wr_x212_ofs = List[UInt]()
      val x213_wr_x212_en = List[Bool](true.B)
      val x213_wr_x212_data = List[UInt](16L.FP(true, 32, 0).r)
      x212_reg_0.connectWPort(213, x213_wr_x212_banks, x213_wr_x212_ofs, x213_wr_x212_data, x213_wr_x212_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x214_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x214_inr_UnitPipe **/
