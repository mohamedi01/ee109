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

/** Hierarchy: x297 -> x322 -> x374 -> x375 **/
/** BEGIN None x297_inr_UnitPipe **/
class x297_inr_UnitPipe_kernel(
  list_x248_imagDram: List[FixedPoint],
  list_x289_fifo: List[FIFOInterface],
  list_x288: List[DecoupledIO[AppCommandDense]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x297_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x297_inr_UnitPipe_iiCtr"))
  
  abstract class x297_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x288 = Decoupled(new AppCommandDense(ModuleParams.getParams("x288_p").asInstanceOf[(Int,Int)] ))
      val in_x289_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x289_fifo_p").asInstanceOf[MemParams] ))
      val in_x248_imagDram = Input(new FixedPoint(true, 64, 0))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x288 = {io.in_x288} 
    def x289_fifo = {io.in_x289_fifo} ; io.in_x289_fifo := DontCare
    def x248_imagDram = {io.in_x248_imagDram} 
  }
  def connectWires0(module: x297_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x288 <> x288
    x289_fifo.connectLedger(module.io.in_x289_fifo)
    module.io.in_x248_imagDram <> x248_imagDram
  }
  val x248_imagDram = list_x248_imagDram(0)
  val x289_fifo = list_x289_fifo(0)
  val x288 = list_x288(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x297_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x297_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x297_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x291 = x248_imagDram
      val x292_tuple = Wire(UInt(97.W)).suggestName("""x292_tuple""")
      x292_tuple.r := ConvAndCat(true.B,1537280L.FP(true, 32, 0).r,x291.r)
      val x293 = true.B
      x288.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x293 & io.sigsIn.backpressure
      x288.bits.addr := x292_tuple(63,0)
      x288.bits.size := x292_tuple(95,64)
      val x295_tuple = Wire(UInt(96.W)).suggestName("""x295_tuple""")
      x295_tuple.r := ConvAndCat(384312L.FP(true, 32, 0).r,0L.FP(true, 32, 0).r,384320L.FP(true, 32, 0).r)
      val x296_enq_x289_banks = List[UInt]()
      val x296_enq_x289_ofs = List[UInt]()
      val x296_enq_x289_en = List[Bool](true.B)
      val x296_enq_x289_data = List[UInt](x295_tuple.r)
      x289_fifo.connectWPort(296, x296_enq_x289_banks, x296_enq_x289_ofs, x296_enq_x289_data, x296_enq_x289_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      x289_fifo.connectAccessActivesIn(0, ((true.B)))
    }
    val module = Module(new x297_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x297_inr_UnitPipe **/
