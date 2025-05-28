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

/** Hierarchy: x373 -> x398 -> x480 -> x481 **/
/** BEGIN None x373_inr_UnitPipe **/
class x373_inr_UnitPipe_kernel(
  list_x322_imagDRAM: List[FixedPoint],
  list_x365_fifo: List[FIFOInterface],
  list_x364: List[DecoupledIO[AppCommandDense]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x373_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x373_inr_UnitPipe_iiCtr"))
  
  abstract class x373_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x365_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x365_fifo_p").asInstanceOf[MemParams] ))
      val in_x322_imagDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x364 = Decoupled(new AppCommandDense(ModuleParams.getParams("x364_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x365_fifo = {io.in_x365_fifo} ; io.in_x365_fifo := DontCare
    def x322_imagDRAM = {io.in_x322_imagDRAM} 
    def x364 = {io.in_x364} 
  }
  def connectWires0(module: x373_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x365_fifo.connectLedger(module.io.in_x365_fifo)
    module.io.in_x322_imagDRAM <> x322_imagDRAM
    module.io.in_x364 <> x364
  }
  val x322_imagDRAM = list_x322_imagDRAM(0)
  val x365_fifo = list_x365_fifo(0)
  val x364 = list_x364(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x373_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x373_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x373_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x373_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x373_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x373_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x373_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x373_inr_UnitPipe = Module(new InstrumentationCounter())
      val idles_x373_inr_UnitPipe = Module(new InstrumentationCounter())
      stalls_x373_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~((~x365_fifo.full.D(1.0-1) | ~(x365_fifo.active(0).out)) & x364.ready)
      idles_x373_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X373_instrctr, cycles_x373_inr_UnitPipe.io.count, iters_x373_inr_UnitPipe.io.count, stalls_x373_inr_UnitPipe.io.count, idles_x373_inr_UnitPipe.io.count)
      val x367 = x322_imagDRAM
      val x368_tuple = Wire(UInt(97.W)).suggestName("""x368_tuple""")
      x368_tuple.r := ConvAndCat(true.B,64L.FP(true, 32, 0).r,x367.r)
      val x369 = true.B
      x364.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x369 & io.sigsIn.backpressure
      x364.bits.addr := x368_tuple(63,0)
      x364.bits.size := x368_tuple(95,64)
      val x371_tuple = Wire(UInt(96.W)).suggestName("""x371_tuple""")
      x371_tuple.r := ConvAndCat(4L.FP(true, 32, 0).r,0L.FP(true, 32, 0).r,16L.FP(true, 32, 0).r)
      val x372_enq_x365_banks = List[UInt]()
      val x372_enq_x365_ofs = List[UInt]()
      val x372_enq_x365_en = List[Bool](true.B)
      val x372_enq_x365_data = List[UInt](x371_tuple.r)
      x365_fifo.connectWPort(372, x372_enq_x365_banks, x372_enq_x365_ofs, x372_enq_x365_data, x372_enq_x365_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      x365_fifo.connectAccessActivesIn(0, ((true.B)))
    }
    val module = Module(new x373_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x373_inr_UnitPipe **/
