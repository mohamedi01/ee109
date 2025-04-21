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

/** Hierarchy: x253 -> x267 -> x168 **/
/** BEGIN Some(DenseTransferCommands) x253_inr_UnitPipe_DenseTransferCommands **/
class x253_inr_UnitPipe_DenseTransferCommands_kernel(
  list_x210_dram: List[FixedPoint],
  list_x246: List[DecoupledIO[AppCommandDense]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 0.0.toInt, myName = "x253_inr_UnitPipe_DenseTransferCommands_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x253_inr_UnitPipe_DenseTransferCommands_iiCtr"))
  
  abstract class x253_inr_UnitPipe_DenseTransferCommands_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x246 = Decoupled(new AppCommandDense(ModuleParams.getParams("x246_p").asInstanceOf[(Int,Int)] ))
      val in_x210_dram = Input(new FixedPoint(true, 64, 0))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x246 = {io.in_x246} 
    def x210_dram = {io.in_x210_dram} 
  }
  def connectWires0(module: x253_inr_UnitPipe_DenseTransferCommands_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x246 <> x246
    module.io.in_x210_dram <> x210_dram
  }
  val x210_dram = list_x210_dram(0)
  val x246 = list_x246(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x253_inr_UnitPipe_DenseTransferCommands")
    implicit val stack = ControllerStack.stack.toList
    class x253_inr_UnitPipe_DenseTransferCommands_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x253_inr_UnitPipe_DenseTransferCommands_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x253_inr_UnitPipe_DenseTransferCommands = Module(new InstrumentationCounter())
      val iters_x253_inr_UnitPipe_DenseTransferCommands = Module(new InstrumentationCounter())
      cycles_x253_inr_UnitPipe_DenseTransferCommands.io.enable := io.sigsIn.baseEn
      iters_x253_inr_UnitPipe_DenseTransferCommands.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x253_inr_UnitPipe_DenseTransferCommands = Module(new InstrumentationCounter())
      val idles_x253_inr_UnitPipe_DenseTransferCommands = Module(new InstrumentationCounter())
      stalls_x253_inr_UnitPipe_DenseTransferCommands.io.enable := io.sigsIn.baseEn & ~(x246.ready)
      idles_x253_inr_UnitPipe_DenseTransferCommands.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X253_instrctr, cycles_x253_inr_UnitPipe_DenseTransferCommands.io.count, iters_x253_inr_UnitPipe_DenseTransferCommands.io.count, stalls_x253_inr_UnitPipe_DenseTransferCommands.io.count, idles_x253_inr_UnitPipe_DenseTransferCommands.io.count)
      val x249 = x210_dram
      val x250_tuple = Wire(UInt(97.W)).suggestName("""x250_tuple""")
      x250_tuple.r := ConvAndCat(false.B,128L.FP(true, 32, 0).r,x249.r)
      val x251 = true.B
      x246.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x251 & io.sigsIn.backpressure
      x246.bits.addr := x250_tuple(63,0)
      x246.bits.size := x250_tuple(95,64)
    }
    val module = Module(new x253_inr_UnitPipe_DenseTransferCommands_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x253_inr_UnitPipe_DenseTransferCommands **/
