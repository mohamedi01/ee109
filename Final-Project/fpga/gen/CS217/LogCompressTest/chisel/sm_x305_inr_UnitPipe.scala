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

/** Hierarchy: x305 -> x330 -> x439 **/
/** BEGIN None x305_inr_UnitPipe **/
class x305_inr_UnitPipe_kernel(
  list_x291_inDRAM: List[FixedPoint],
  list_x297_fifo: List[FIFOInterface],
  list_x296: List[DecoupledIO[AppCommandDense]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x305_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x305_inr_UnitPipe_iiCtr"))
  
  abstract class x305_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x297_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x297_fifo_p").asInstanceOf[MemParams] ))
      val in_x296 = Decoupled(new AppCommandDense(ModuleParams.getParams("x296_p").asInstanceOf[(Int,Int)] ))
      val in_x291_inDRAM = Input(new FixedPoint(true, 64, 0))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x297_fifo = {io.in_x297_fifo} ; io.in_x297_fifo := DontCare
    def x296 = {io.in_x296} 
    def x291_inDRAM = {io.in_x291_inDRAM} 
  }
  def connectWires0(module: x305_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x297_fifo.connectLedger(module.io.in_x297_fifo)
    module.io.in_x296 <> x296
    module.io.in_x291_inDRAM <> x291_inDRAM
  }
  val x291_inDRAM = list_x291_inDRAM(0)
  val x297_fifo = list_x297_fifo(0)
  val x296 = list_x296(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x305_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x305_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x305_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x305_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x305_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x305_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x305_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x305_inr_UnitPipe = Module(new InstrumentationCounter())
      val idles_x305_inr_UnitPipe = Module(new InstrumentationCounter())
      stalls_x305_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(x296.ready & (~x297_fifo.full.D(1.0-1) | ~(x297_fifo.active(0).out)))
      idles_x305_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X305_instrctr, cycles_x305_inr_UnitPipe.io.count, iters_x305_inr_UnitPipe.io.count, stalls_x305_inr_UnitPipe.io.count, idles_x305_inr_UnitPipe.io.count)
      val x299 = x291_inDRAM
      val x300_tuple = Wire(UInt(97.W)).suggestName("""x300_tuple""")
      x300_tuple.r := ConvAndCat(true.B,63L.FP(true, 32, 0).r,x299.r)
      val x301 = true.B
      x296.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x301 & io.sigsIn.backpressure
      x296.bits.addr := x300_tuple(63,0)
      x296.bits.size := x300_tuple(95,64)
      val x303_tuple = Wire(UInt(96.W)).suggestName("""x303_tuple""")
      x303_tuple.r := ConvAndCat(2L.FP(true, 32, 0).r,0L.FP(true, 32, 0).r,21L.FP(true, 32, 0).r)
      val x304_enq_x297_banks = List[UInt]()
      val x304_enq_x297_ofs = List[UInt]()
      val x304_enq_x297_en = List[Bool](true.B)
      val x304_enq_x297_data = List[UInt](x303_tuple.r)
      x297_fifo.connectWPort(304, x304_enq_x297_banks, x304_enq_x297_ofs, x304_enq_x297_data, x304_enq_x297_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      x297_fifo.connectAccessActivesIn(0, ((true.B)))
    }
    val module = Module(new x305_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x305_inr_UnitPipe **/
