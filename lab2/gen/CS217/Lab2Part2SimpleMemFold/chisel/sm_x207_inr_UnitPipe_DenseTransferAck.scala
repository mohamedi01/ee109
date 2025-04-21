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

/** Hierarchy: x207 -> x208 -> x119 **/
/** BEGIN Some(DenseTransferAck) x207_inr_UnitPipe_DenseTransferAck **/
class x207_inr_UnitPipe_DenseTransferAck_kernel(
  list_x189: List[DecoupledIO[Bool]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x207_inr_UnitPipe_DenseTransferAck_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x207_inr_UnitPipe_DenseTransferAck_iiCtr"))
  
  abstract class x207_inr_UnitPipe_DenseTransferAck_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x189 = Flipped(Decoupled(Bool()))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x189 = {io.in_x189} 
  }
  def connectWires0(module: x207_inr_UnitPipe_DenseTransferAck_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x189 <> x189
  }
  val x189 = list_x189(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x207_inr_UnitPipe_DenseTransferAck")
    implicit val stack = ControllerStack.stack.toList
    class x207_inr_UnitPipe_DenseTransferAck_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x207_inr_UnitPipe_DenseTransferAck_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x207_inr_UnitPipe_DenseTransferAck = Module(new InstrumentationCounter())
      val iters_x207_inr_UnitPipe_DenseTransferAck = Module(new InstrumentationCounter())
      cycles_x207_inr_UnitPipe_DenseTransferAck.io.enable := io.sigsIn.baseEn
      iters_x207_inr_UnitPipe_DenseTransferAck.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x207_inr_UnitPipe_DenseTransferAck = Module(new InstrumentationCounter())
      val idles_x207_inr_UnitPipe_DenseTransferAck = Module(new InstrumentationCounter())
      stalls_x207_inr_UnitPipe_DenseTransferAck.io.enable := io.sigsIn.baseEn & ~(true.B)
      idles_x207_inr_UnitPipe_DenseTransferAck.io.enable := io.sigsIn.baseEn & ~((x189.valid) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X207_instrctr, cycles_x207_inr_UnitPipe_DenseTransferAck.io.count, iters_x207_inr_UnitPipe_DenseTransferAck.io.count, stalls_x207_inr_UnitPipe_DenseTransferAck.io.count, idles_x207_inr_UnitPipe_DenseTransferAck.io.count)
      val x205 = Wire(Vec(1, Bool())).suggestName("""x205""")
      x189.ready := true.B & (io.sigsIn.datapathEn) 
      (0 until 1).map{ i => x205(i) := x189.bits }
    }
    val module = Module(new x207_inr_UnitPipe_DenseTransferAck_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x207_inr_UnitPipe_DenseTransferAck **/
