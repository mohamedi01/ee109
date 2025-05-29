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

/** Hierarchy: x262 -> x287 -> x374 -> x375 **/
/** BEGIN None x262_inr_UnitPipe **/
class x262_inr_UnitPipe_kernel(
  list_x247_realDram: List[FixedPoint],
  list_x254_fifo: List[FIFOInterface],
  list_x253: List[DecoupledIO[AppCommandDense]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x262_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x262_inr_UnitPipe_iiCtr"))
  
  abstract class x262_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x247_realDram = Input(new FixedPoint(true, 64, 0))
      val in_x253 = Decoupled(new AppCommandDense(ModuleParams.getParams("x253_p").asInstanceOf[(Int,Int)] ))
      val in_x254_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x254_fifo_p").asInstanceOf[MemParams] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x247_realDram = {io.in_x247_realDram} 
    def x253 = {io.in_x253} 
    def x254_fifo = {io.in_x254_fifo} ; io.in_x254_fifo := DontCare
  }
  def connectWires0(module: x262_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x247_realDram <> x247_realDram
    module.io.in_x253 <> x253
    x254_fifo.connectLedger(module.io.in_x254_fifo)
  }
  val x247_realDram = list_x247_realDram(0)
  val x254_fifo = list_x254_fifo(0)
  val x253 = list_x253(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x262_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x262_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x262_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x256 = x247_realDram
      val x257_tuple = Wire(UInt(97.W)).suggestName("""x257_tuple""")
      x257_tuple.r := ConvAndCat(true.B,1537280L.FP(true, 32, 0).r,x256.r)
      val x258 = true.B
      x253.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x258 & io.sigsIn.backpressure
      x253.bits.addr := x257_tuple(63,0)
      x253.bits.size := x257_tuple(95,64)
      val x260_tuple = Wire(UInt(96.W)).suggestName("""x260_tuple""")
      x260_tuple.r := ConvAndCat(384312L.FP(true, 32, 0).r,0L.FP(true, 32, 0).r,384320L.FP(true, 32, 0).r)
      val x261_enq_x254_banks = List[UInt]()
      val x261_enq_x254_ofs = List[UInt]()
      val x261_enq_x254_en = List[Bool](true.B)
      val x261_enq_x254_data = List[UInt](x260_tuple.r)
      x254_fifo.connectWPort(261, x261_enq_x254_banks, x261_enq_x254_ofs, x261_enq_x254_data, x261_enq_x254_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      x254_fifo.connectAccessActivesIn(0, ((true.B)))
    }
    val module = Module(new x262_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x262_inr_UnitPipe **/
