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

/** Hierarchy: x194 -> x208 -> x119 **/
/** BEGIN Some(DenseTransferCommands) x194_inr_UnitPipe_DenseTransferCommands **/
class x194_inr_UnitPipe_DenseTransferCommands_kernel(
  list_x160_out: List[FixedPoint],
  list_x187: List[DecoupledIO[AppCommandDense]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 0.0.toInt, myName = "x194_inr_UnitPipe_DenseTransferCommands_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x194_inr_UnitPipe_DenseTransferCommands_iiCtr"))
  
  abstract class x194_inr_UnitPipe_DenseTransferCommands_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x160_out = Input(new FixedPoint(true, 64, 0))
      val in_x187 = Decoupled(new AppCommandDense(ModuleParams.getParams("x187_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x160_out = {io.in_x160_out} 
    def x187 = {io.in_x187} 
  }
  def connectWires0(module: x194_inr_UnitPipe_DenseTransferCommands_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x160_out <> x160_out
    module.io.in_x187 <> x187
  }
  val x160_out = list_x160_out(0)
  val x187 = list_x187(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x194_inr_UnitPipe_DenseTransferCommands")
    implicit val stack = ControllerStack.stack.toList
    class x194_inr_UnitPipe_DenseTransferCommands_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x194_inr_UnitPipe_DenseTransferCommands_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x194_inr_UnitPipe_DenseTransferCommands = Module(new InstrumentationCounter())
      val iters_x194_inr_UnitPipe_DenseTransferCommands = Module(new InstrumentationCounter())
      cycles_x194_inr_UnitPipe_DenseTransferCommands.io.enable := io.sigsIn.baseEn
      iters_x194_inr_UnitPipe_DenseTransferCommands.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x194_inr_UnitPipe_DenseTransferCommands = Module(new InstrumentationCounter())
      val idles_x194_inr_UnitPipe_DenseTransferCommands = Module(new InstrumentationCounter())
      stalls_x194_inr_UnitPipe_DenseTransferCommands.io.enable := io.sigsIn.baseEn & ~(x187.ready)
      idles_x194_inr_UnitPipe_DenseTransferCommands.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X194_instrctr, cycles_x194_inr_UnitPipe_DenseTransferCommands.io.count, iters_x194_inr_UnitPipe_DenseTransferCommands.io.count, stalls_x194_inr_UnitPipe_DenseTransferCommands.io.count, idles_x194_inr_UnitPipe_DenseTransferCommands.io.count)
      val x190 = x160_out
      val x191_tuple = Wire(UInt(97.W)).suggestName("""x191_tuple""")
      x191_tuple.r := ConvAndCat(false.B,64L.FP(true, 32, 0).r,x190.r)
      val x192 = true.B
      x187.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x192 & io.sigsIn.backpressure
      x187.bits.addr := x191_tuple(63,0)
      x187.bits.size := x191_tuple(95,64)
    }
    val module = Module(new x194_inr_UnitPipe_DenseTransferCommands_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x194_inr_UnitPipe_DenseTransferCommands **/
