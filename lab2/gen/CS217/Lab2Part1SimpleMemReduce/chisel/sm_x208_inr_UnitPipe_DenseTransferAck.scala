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

/** Hierarchy: x208 -> x209 -> x119 **/
/** BEGIN Some(DenseTransferAck) x208_inr_UnitPipe_DenseTransferAck **/
class x208_inr_UnitPipe_DenseTransferAck_kernel(
  list_x190: List[DecoupledIO[Bool]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x208_inr_UnitPipe_DenseTransferAck_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x208_inr_UnitPipe_DenseTransferAck_iiCtr"))
  
  abstract class x208_inr_UnitPipe_DenseTransferAck_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x190 = Flipped(Decoupled(Bool()))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x190 = {io.in_x190} 
  }
  def connectWires0(module: x208_inr_UnitPipe_DenseTransferAck_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x190 <> x190
  }
  val x190 = list_x190(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x208_inr_UnitPipe_DenseTransferAck")
    implicit val stack = ControllerStack.stack.toList
    class x208_inr_UnitPipe_DenseTransferAck_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x208_inr_UnitPipe_DenseTransferAck_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x208_inr_UnitPipe_DenseTransferAck = Module(new InstrumentationCounter())
      val iters_x208_inr_UnitPipe_DenseTransferAck = Module(new InstrumentationCounter())
      cycles_x208_inr_UnitPipe_DenseTransferAck.io.enable := io.sigsIn.baseEn
      iters_x208_inr_UnitPipe_DenseTransferAck.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x208_inr_UnitPipe_DenseTransferAck = Module(new InstrumentationCounter())
      val idles_x208_inr_UnitPipe_DenseTransferAck = Module(new InstrumentationCounter())
      stalls_x208_inr_UnitPipe_DenseTransferAck.io.enable := io.sigsIn.baseEn & ~(true.B)
      idles_x208_inr_UnitPipe_DenseTransferAck.io.enable := io.sigsIn.baseEn & ~((x190.valid) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X208_instrctr, cycles_x208_inr_UnitPipe_DenseTransferAck.io.count, iters_x208_inr_UnitPipe_DenseTransferAck.io.count, stalls_x208_inr_UnitPipe_DenseTransferAck.io.count, idles_x208_inr_UnitPipe_DenseTransferAck.io.count)
      val x206 = Wire(Vec(1, Bool())).suggestName("""x206""")
      x190.ready := true.B & (io.sigsIn.datapathEn) 
      (0 until 1).map{ i => x206(i) := x190.bits }
    }
    val module = Module(new x208_inr_UnitPipe_DenseTransferAck_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x208_inr_UnitPipe_DenseTransferAck **/
