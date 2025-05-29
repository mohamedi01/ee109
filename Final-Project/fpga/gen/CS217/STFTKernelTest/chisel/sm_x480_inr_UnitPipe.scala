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

/** Hierarchy: x480 -> x505 -> x708 -> x710 **/
/** BEGIN None x480_inr_UnitPipe **/
class x480_inr_UnitPipe_kernel(
  list_x460_inDRAM: List[FixedPoint],
  list_x472_fifo: List[FIFOInterface],
  list_x471: List[DecoupledIO[AppCommandDense]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x480_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x480_inr_UnitPipe_iiCtr"))
  
  abstract class x480_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x472_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x472_fifo_p").asInstanceOf[MemParams] ))
      val in_x460_inDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x471 = Decoupled(new AppCommandDense(ModuleParams.getParams("x471_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x472_fifo = {io.in_x472_fifo} ; io.in_x472_fifo := DontCare
    def x460_inDRAM = {io.in_x460_inDRAM} 
    def x471 = {io.in_x471} 
  }
  def connectWires0(module: x480_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x472_fifo.connectLedger(module.io.in_x472_fifo)
    module.io.in_x460_inDRAM <> x460_inDRAM
    module.io.in_x471 <> x471
  }
  val x460_inDRAM = list_x460_inDRAM(0)
  val x472_fifo = list_x472_fifo(0)
  val x471 = list_x471(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x480_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x480_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x480_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x480_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x480_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x480_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x480_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x480_inr_UnitPipe = Module(new InstrumentationCounter())
      val idles_x480_inr_UnitPipe = Module(new InstrumentationCounter())
      stalls_x480_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~((~x472_fifo.full.D(1.0-1) | ~(x472_fifo.active(0).out)) & x471.ready)
      idles_x480_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X480_instrctr, cycles_x480_inr_UnitPipe.io.count, iters_x480_inr_UnitPipe.io.count, stalls_x480_inr_UnitPipe.io.count, idles_x480_inr_UnitPipe.io.count)
      val x474 = x460_inDRAM
      val x475_tuple = Wire(UInt(97.W)).suggestName("""x475_tuple""")
      x475_tuple.r := ConvAndCat(true.B,64L.FP(true, 32, 0).r,x474.r)
      val x476 = true.B
      x471.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x476 & io.sigsIn.backpressure
      x471.bits.addr := x475_tuple(63,0)
      x471.bits.size := x475_tuple(95,64)
      val x478_tuple = Wire(UInt(96.W)).suggestName("""x478_tuple""")
      x478_tuple.r := ConvAndCat(6L.FP(true, 32, 0).r,0L.FP(true, 32, 0).r,16L.FP(true, 32, 0).r)
      val x479_enq_x472_banks = List[UInt]()
      val x479_enq_x472_ofs = List[UInt]()
      val x479_enq_x472_en = List[Bool](true.B)
      val x479_enq_x472_data = List[UInt](x478_tuple.r)
      x472_fifo.connectWPort(479, x479_enq_x472_banks, x479_enq_x472_ofs, x479_enq_x472_data, x479_enq_x472_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      x472_fifo.connectAccessActivesIn(0, ((true.B)))
    }
    val module = Module(new x480_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x480_inr_UnitPipe **/
