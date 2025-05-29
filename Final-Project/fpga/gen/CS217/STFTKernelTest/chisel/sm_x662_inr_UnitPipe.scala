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

/** Hierarchy: x662 -> x663 -> x664 -> x709 -> x710 **/
/** BEGIN None x662_inr_UnitPipe **/
class x662_inr_UnitPipe_kernel(
  list_b615: List[Bool],
  list_x611: List[DecoupledIO[Bool]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x662_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x662_inr_UnitPipe_iiCtr"))
  
  abstract class x662_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b615 = Input(Bool())
      val in_x611 = Flipped(Decoupled(Bool()))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def b615 = {io.in_b615} 
    def x611 = {io.in_x611} 
  }
  def connectWires0(module: x662_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b615 <> b615
    module.io.in_x611 <> x611
  }
  val b615 = list_b615(0)
  val x611 = list_x611(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x662_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x662_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x662_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x662_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x662_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x662_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x662_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x662_inr_UnitPipe = Module(new InstrumentationCounter())
      val idles_x662_inr_UnitPipe = Module(new InstrumentationCounter())
      stalls_x662_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(true.B)
      idles_x662_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~((x611.valid) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X662_instrctr, cycles_x662_inr_UnitPipe.io.count, iters_x662_inr_UnitPipe.io.count, stalls_x662_inr_UnitPipe.io.count, idles_x662_inr_UnitPipe.io.count)
      val x660 = Wire(Vec(1, Bool())).suggestName("""x660""")
      x611.ready := true.B & (io.sigsIn.datapathEn) 
      (0 until 1).map{ i => x660(i) := x611.bits }
    }
    val module = Module(new x662_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x662_inr_UnitPipe **/
