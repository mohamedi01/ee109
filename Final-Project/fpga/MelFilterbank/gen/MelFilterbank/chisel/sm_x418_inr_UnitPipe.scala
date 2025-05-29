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

/** Hierarchy: x418 -> x451 -> x452 -> x453 **/
/** BEGIN None x418_inr_UnitPipe **/
class x418_inr_UnitPipe_kernel(
  list_b398: List[Bool],
  list_b397: List[FixedPoint],
  list_x399: List[String],
  list_x296_outSram_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x418_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x418_inr_UnitPipe_iiCtr"))
  
  abstract class x418_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b398 = Input(Bool())
      val in_x399 = String
      val in_x296_outSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x296_outSram_0_p").asInstanceOf[MemParams] ))
      val in_b397 = Input(new FixedPoint(true, 32, 0))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def b398 = {io.in_b398} 
    def x399 = {io.in_x399} 
    def x296_outSram_0 = {io.in_x296_outSram_0} ; io.in_x296_outSram_0 := DontCare
    def b397 = {io.in_b397} 
  }
  def connectWires0(module: x418_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b398 <> b398
    module.io.in_x399 <> x399
    x296_outSram_0.connectLedger(module.io.in_x296_outSram_0)
    module.io.in_b397 <> b397
  }
  val b398 = list_b398(0)
  val b397 = list_b397(0)
  val x399 = list_x399(0)
  val x296_outSram_0 = list_x296_outSram_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x418_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x418_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x418_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x416 = "" 
      val x417_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x417_wr_ofs = List[UInt](b397.r)
      val x417_wr_en = List[Bool](true.B)
      val x417_wr_data = List[UInt](x416.r)
      x296_outSram_0.connectWPort(417, x417_wr_banks, x417_wr_ofs, x417_wr_data, x417_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x418_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x418_inr_UnitPipe **/
