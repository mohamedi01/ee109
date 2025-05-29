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

/** Hierarchy: x369 -> x394 -> x450 -> x453 **/
/** BEGIN None x369_inr_UnitPipe **/
class x369_inr_UnitPipe_kernel(
  list_x292_vecDram: List[FixedPoint],
  list_x361_fifo: List[FIFOInterface],
  list_x360: List[DecoupledIO[AppCommandDense]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x369_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x369_inr_UnitPipe_iiCtr"))
  
  abstract class x369_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x292_vecDram = Input(new FixedPoint(true, 64, 0))
      val in_x361_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x361_fifo_p").asInstanceOf[MemParams] ))
      val in_x360 = Decoupled(new AppCommandDense(ModuleParams.getParams("x360_p").asInstanceOf[(Int,Int)] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x292_vecDram = {io.in_x292_vecDram} 
    def x361_fifo = {io.in_x361_fifo} ; io.in_x361_fifo := DontCare
    def x360 = {io.in_x360} 
  }
  def connectWires0(module: x369_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x292_vecDram <> x292_vecDram
    x361_fifo.connectLedger(module.io.in_x361_fifo)
    module.io.in_x360 <> x360
  }
  val x292_vecDram = list_x292_vecDram(0)
  val x361_fifo = list_x361_fifo(0)
  val x360 = list_x360(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x369_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x369_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x369_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x363 = x292_vecDram
      val x364_tuple = Wire(UInt(97.W)).suggestName("""x364_tuple""")
      x364_tuple.r := ConvAndCat(true.B,832L.FP(true, 32, 0).r,x363.r)
      val x365 = true.B
      x360.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x365 & io.sigsIn.backpressure
      x360.bits.addr := x364_tuple(63,0)
      x360.bits.size := x364_tuple(95,64)
      val x367_tuple = Wire(UInt(96.W)).suggestName("""x367_tuple""")
      x367_tuple.r := ConvAndCat(201L.FP(true, 32, 0).r,0L.FP(true, 32, 0).r,208L.FP(true, 32, 0).r)
      val x368_enq_x361_banks = List[UInt]()
      val x368_enq_x361_ofs = List[UInt]()
      val x368_enq_x361_en = List[Bool](true.B)
      val x368_enq_x361_data = List[UInt](x367_tuple.r)
      x361_fifo.connectWPort(368, x368_enq_x361_banks, x368_enq_x361_ofs, x368_enq_x361_data, x368_enq_x361_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      x361_fifo.connectAccessActivesIn(0, ((true.B)))
    }
    val module = Module(new x369_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x369_inr_UnitPipe **/
