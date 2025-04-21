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

/** Hierarchy: x195 -> x209 -> x119 **/
/** BEGIN Some(DenseTransferCommands) x195_inr_UnitPipe_DenseTransferCommands **/
class x195_inr_UnitPipe_DenseTransferCommands_kernel(
  list_x160_out: List[FixedPoint],
  list_x188: List[DecoupledIO[AppCommandDense]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 0.0.toInt, myName = "x195_inr_UnitPipe_DenseTransferCommands_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x195_inr_UnitPipe_DenseTransferCommands_iiCtr"))
  
  abstract class x195_inr_UnitPipe_DenseTransferCommands_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x160_out = Input(new FixedPoint(true, 64, 0))
      val in_x188 = Decoupled(new AppCommandDense(ModuleParams.getParams("x188_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x160_out = {io.in_x160_out} 
    def x188 = {io.in_x188} 
  }
  def connectWires0(module: x195_inr_UnitPipe_DenseTransferCommands_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x160_out <> x160_out
    module.io.in_x188 <> x188
  }
  val x160_out = list_x160_out(0)
  val x188 = list_x188(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x195_inr_UnitPipe_DenseTransferCommands")
    implicit val stack = ControllerStack.stack.toList
    class x195_inr_UnitPipe_DenseTransferCommands_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x195_inr_UnitPipe_DenseTransferCommands_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x195_inr_UnitPipe_DenseTransferCommands = Module(new InstrumentationCounter())
      val iters_x195_inr_UnitPipe_DenseTransferCommands = Module(new InstrumentationCounter())
      cycles_x195_inr_UnitPipe_DenseTransferCommands.io.enable := io.sigsIn.baseEn
      iters_x195_inr_UnitPipe_DenseTransferCommands.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x195_inr_UnitPipe_DenseTransferCommands = Module(new InstrumentationCounter())
      val idles_x195_inr_UnitPipe_DenseTransferCommands = Module(new InstrumentationCounter())
      stalls_x195_inr_UnitPipe_DenseTransferCommands.io.enable := io.sigsIn.baseEn & ~(x188.ready)
      idles_x195_inr_UnitPipe_DenseTransferCommands.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X195_instrctr, cycles_x195_inr_UnitPipe_DenseTransferCommands.io.count, iters_x195_inr_UnitPipe_DenseTransferCommands.io.count, stalls_x195_inr_UnitPipe_DenseTransferCommands.io.count, idles_x195_inr_UnitPipe_DenseTransferCommands.io.count)
      val x191 = x160_out
      val x192_tuple = Wire(UInt(97.W)).suggestName("""x192_tuple""")
      x192_tuple.r := ConvAndCat(false.B,64L.FP(true, 32, 0).r,x191.r)
      val x193 = true.B
      x188.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x193 & io.sigsIn.backpressure
      x188.bits.addr := x192_tuple(63,0)
      x188.bits.size := x192_tuple(95,64)
    }
    val module = Module(new x195_inr_UnitPipe_DenseTransferCommands_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x195_inr_UnitPipe_DenseTransferCommands **/
