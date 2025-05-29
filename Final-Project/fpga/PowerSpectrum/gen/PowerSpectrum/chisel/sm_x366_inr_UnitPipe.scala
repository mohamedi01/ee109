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

/** Hierarchy: x366 -> x367 -> x368 -> x375 **/
/** BEGIN None x366_inr_UnitPipe **/
class x366_inr_UnitPipe_kernel(
  list_x338: List[DecoupledIO[Bool]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x366_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x366_inr_UnitPipe_iiCtr"))
  
  abstract class x366_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x338 = Flipped(Decoupled(Bool()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x338 = {io.in_x338} 
  }
  def connectWires0(module: x366_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x338 <> x338
  }
  val x338 = list_x338(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x366_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x366_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x366_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x364 = Wire(Vec(1, Bool())).suggestName("""x364""")
      x338.ready := true.B & (io.sigsIn.datapathEn) 
      (0 until 1).map{ i => x364(i) := x338.bits }
    }
    val module = Module(new x366_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x366_inr_UnitPipe **/
