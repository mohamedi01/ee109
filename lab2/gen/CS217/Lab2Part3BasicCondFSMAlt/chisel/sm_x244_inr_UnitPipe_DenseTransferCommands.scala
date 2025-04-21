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

/** Hierarchy: x244 -> x258 -> x164 **/
/** BEGIN Some(DenseTransferCommands) x244_inr_UnitPipe_DenseTransferCommands **/
class x244_inr_UnitPipe_DenseTransferCommands_kernel(
  list_x206_dram: List[FixedPoint],
  list_x237: List[DecoupledIO[AppCommandDense]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 0.0.toInt, myName = "x244_inr_UnitPipe_DenseTransferCommands_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x244_inr_UnitPipe_DenseTransferCommands_iiCtr"))
  
  abstract class x244_inr_UnitPipe_DenseTransferCommands_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x206_dram = Input(new FixedPoint(true, 64, 0))
      val in_x237 = Decoupled(new AppCommandDense(ModuleParams.getParams("x237_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x206_dram = {io.in_x206_dram} 
    def x237 = {io.in_x237} 
  }
  def connectWires0(module: x244_inr_UnitPipe_DenseTransferCommands_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x206_dram <> x206_dram
    module.io.in_x237 <> x237
  }
  val x206_dram = list_x206_dram(0)
  val x237 = list_x237(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x244_inr_UnitPipe_DenseTransferCommands")
    implicit val stack = ControllerStack.stack.toList
    class x244_inr_UnitPipe_DenseTransferCommands_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x244_inr_UnitPipe_DenseTransferCommands_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x244_inr_UnitPipe_DenseTransferCommands = Module(new InstrumentationCounter())
      val iters_x244_inr_UnitPipe_DenseTransferCommands = Module(new InstrumentationCounter())
      cycles_x244_inr_UnitPipe_DenseTransferCommands.io.enable := io.sigsIn.baseEn
      iters_x244_inr_UnitPipe_DenseTransferCommands.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x244_inr_UnitPipe_DenseTransferCommands = Module(new InstrumentationCounter())
      val idles_x244_inr_UnitPipe_DenseTransferCommands = Module(new InstrumentationCounter())
      stalls_x244_inr_UnitPipe_DenseTransferCommands.io.enable := io.sigsIn.baseEn & ~(x237.ready)
      idles_x244_inr_UnitPipe_DenseTransferCommands.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X244_instrctr, cycles_x244_inr_UnitPipe_DenseTransferCommands.io.count, iters_x244_inr_UnitPipe_DenseTransferCommands.io.count, stalls_x244_inr_UnitPipe_DenseTransferCommands.io.count, idles_x244_inr_UnitPipe_DenseTransferCommands.io.count)
      val x240 = x206_dram
      val x241_tuple = Wire(UInt(97.W)).suggestName("""x241_tuple""")
      x241_tuple.r := ConvAndCat(false.B,128L.FP(true, 32, 0).r,x240.r)
      val x242 = true.B
      x237.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x242 & io.sigsIn.backpressure
      x237.bits.addr := x241_tuple(63,0)
      x237.bits.size := x241_tuple(95,64)
    }
    val module = Module(new x244_inr_UnitPipe_DenseTransferCommands_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x244_inr_UnitPipe_DenseTransferCommands **/
