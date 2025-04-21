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

/** Hierarchy: x1699 -> x1700 -> x1701 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1699_inr_UnitPipe **/
class x1699_inr_UnitPipe_kernel(
  list_b1645: List[Bool],
  list_x1641: List[DecoupledIO[Bool]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x1699_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1699_inr_UnitPipe_iiCtr"))
  
  abstract class x1699_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b1645 = Input(Bool())
      val in_x1641 = Flipped(Decoupled(Bool()))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def b1645 = {io.in_b1645} 
    def x1641 = {io.in_x1641} 
  }
  def connectWires0(module: x1699_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b1645 <> b1645
    module.io.in_x1641 <> x1641
  }
  val b1645 = list_b1645(0)
  val x1641 = list_x1641(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1699_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x1699_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1699_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1699_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x1699_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x1699_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x1699_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x1699_inr_UnitPipe = Module(new InstrumentationCounter())
      val idles_x1699_inr_UnitPipe = Module(new InstrumentationCounter())
      stalls_x1699_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(true.B)
      idles_x1699_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~((x1641.valid) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X1699_instrctr, cycles_x1699_inr_UnitPipe.io.count, iters_x1699_inr_UnitPipe.io.count, stalls_x1699_inr_UnitPipe.io.count, idles_x1699_inr_UnitPipe.io.count)
      val x1697 = Wire(Vec(1, Bool())).suggestName("""x1697""")
      x1641.ready := true.B & (io.sigsIn.datapathEn) 
      (0 until 1).map{ i => x1697(i) := x1641.bits }
    }
    val module = Module(new x1699_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x1699_inr_UnitPipe **/
