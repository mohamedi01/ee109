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

/** Hierarchy: x291 -> x316 -> x244 **/
/** BEGIN None x291_inr_UnitPipe **/
class x291_inr_UnitPipe_kernel(
  list_x277_inDRAM: List[FixedPoint],
  list_x283_fifo: List[FIFOInterface],
  list_x282: List[DecoupledIO[AppCommandDense]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x291_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x291_inr_UnitPipe_iiCtr"))
  
  abstract class x291_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x282 = Decoupled(new AppCommandDense(ModuleParams.getParams("x282_p").asInstanceOf[(Int,Int)] ))
      val in_x283_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x283_fifo_p").asInstanceOf[MemParams] ))
      val in_x277_inDRAM = Input(new FixedPoint(true, 64, 0))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x282 = {io.in_x282} 
    def x283_fifo = {io.in_x283_fifo} ; io.in_x283_fifo := DontCare
    def x277_inDRAM = {io.in_x277_inDRAM} 
  }
  def connectWires0(module: x291_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x282 <> x282
    x283_fifo.connectLedger(module.io.in_x283_fifo)
    module.io.in_x277_inDRAM <> x277_inDRAM
  }
  val x277_inDRAM = list_x277_inDRAM(0)
  val x283_fifo = list_x283_fifo(0)
  val x282 = list_x282(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x291_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x291_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x291_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x291_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x291_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x291_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x291_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x291_inr_UnitPipe = Module(new InstrumentationCounter())
      val idles_x291_inr_UnitPipe = Module(new InstrumentationCounter())
      stalls_x291_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(x282.ready & (~x283_fifo.full.D(1.0-1) | ~(x283_fifo.active(0).out)))
      idles_x291_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X291_instrctr, cycles_x291_inr_UnitPipe.io.count, iters_x291_inr_UnitPipe.io.count, stalls_x291_inr_UnitPipe.io.count, idles_x291_inr_UnitPipe.io.count)
      val x285 = x277_inDRAM
      val x286_tuple = Wire(UInt(97.W)).suggestName("""x286_tuple""")
      x286_tuple.r := ConvAndCat(true.B,64L.FP(true, 32, 0).r,x285.r)
      val x287 = true.B
      x282.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x287 & io.sigsIn.backpressure
      x282.bits.addr := x286_tuple(63,0)
      x282.bits.size := x286_tuple(95,64)
      val x289_tuple = Wire(UInt(96.W)).suggestName("""x289_tuple""")
      x289_tuple.r := ConvAndCat(9L.FP(true, 32, 0).r,0L.FP(true, 32, 0).r,16L.FP(true, 32, 0).r)
      val x290_enq_x283_banks = List[UInt]()
      val x290_enq_x283_ofs = List[UInt]()
      val x290_enq_x283_en = List[Bool](true.B)
      val x290_enq_x283_data = List[UInt](x289_tuple.r)
      x283_fifo.connectWPort(290, x290_enq_x283_banks, x290_enq_x283_ofs, x290_enq_x283_data, x290_enq_x283_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      x283_fifo.connectAccessActivesIn(0, ((true.B)))
    }
    val module = Module(new x291_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x291_inr_UnitPipe **/
