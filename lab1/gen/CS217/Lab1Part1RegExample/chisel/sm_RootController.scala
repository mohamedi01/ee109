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

/** Hierarchy: x59 **/
/** BEGIN None RootController **/
class RootController_kernel(
  list_x96_argRegOut: List[MultiArgOut],
  list_x92_argRegIn2: List[UInt],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 3.0.toInt, myName = "RootController_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "RootController_iiCtr"))
  
  abstract class RootController_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x92_argRegIn2 = Input(UInt(64.W))
      val in_x96_argRegOut = new MultiArgOut(1)
      val in_x91_argRegIn1 = Input(UInt(64.W))
      val in_x90_argRegIn0 = Input(UInt(64.W))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x92_argRegIn2 = {io.in_x92_argRegIn2} 
    def x96_argRegOut = {io.in_x96_argRegOut} ; io.in_x96_argRegOut := DontCare
    def x91_argRegIn1 = {io.in_x91_argRegIn1} 
    def x90_argRegIn0 = {io.in_x90_argRegIn0} 
  }
  def connectWires0(module: RootController_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x92_argRegIn2 <> x92_argRegIn2
    x96_argRegOut.connectLedger(module.io.in_x96_argRegOut)
    module.io.in_x96_argRegOut.port.zip(x96_argRegOut.port).foreach{case (l,r) => l.ready := r.ready}
    module.io.in_x91_argRegIn1 <> x91_argRegIn1
    module.io.in_x90_argRegIn0 <> x90_argRegIn0
  }
  val x96_argRegOut = list_x96_argRegOut(0)
  val x92_argRegIn2 = list_x92_argRegIn2(0)
  val x91_argRegIn1 = list_x92_argRegIn2(1)
  val x90_argRegIn0 = list_x92_argRegIn2(2)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "RootController")
    implicit val stack = ControllerStack.stack.toList
    class RootController_concrete(depth: Int)(implicit stack: List[KernelHash]) extends RootController_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_RootController = Module(new InstrumentationCounter())
      val iters_RootController = Module(new InstrumentationCounter())
      cycles_RootController.io.enable := io.sigsIn.baseEn
      iters_RootController.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X59_instrctr, cycles_RootController.io.count, iters_RootController.io.count, 0.U, 0.U)
      val x97_rd_x90 = Wire(new FixedPoint(true, 32, 0))
      x97_rd_x90.r := x90_argRegIn0.r
      val x98_rd_x91 = Wire(new FixedPoint(true, 32, 0))
      x98_rd_x91.r := x91_argRegIn1.r
      val x99_rd_x92 = Wire(new FixedPoint(true, 32, 0))
      x99_rd_x92.r := x92_argRegIn2.r
      val x100_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x100_sum""")
      x100_sum.r := Math.add(x97_rd_x90,x98_rd_x91,Some(1.0), true.B, Truncate, Wrapping, "x100_sum").r
      val x120 = Wire(new FixedPoint(true, 32, 0)).suggestName("x120_x99_rd_x92_D1") 
      x120.r := getRetimed(x99_rd_x92.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x101_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x101_sum""")
      x101_sum.r := Math.add(x100_sum,x120,Some(1.0), true.B, Truncate, Wrapping, "x101_sum").r
      x96_argRegOut.connectWPort(0, util.Cat(util.Fill(32, x101_sum.msb), x101_sum.r), true.B & getRetimed(io.sigsIn.datapathEn & io.sigsIn.iiIssue, 2.0.toInt, io.sigsIn.backpressure & true.B))
    }
    val module = Module(new RootController_concrete(sm.p.depth)); module.io := DontCare
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
/** END AccelScope RootController **/
