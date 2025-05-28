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

/** Hierarchy: x338 -> x363 -> x480 -> x481 **/
/** BEGIN None x338_inr_UnitPipe **/
class x338_inr_UnitPipe_kernel(
  list_x321_realDRAM: List[FixedPoint],
  list_x330_fifo: List[FIFOInterface],
  list_x329: List[DecoupledIO[AppCommandDense]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x338_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x338_inr_UnitPipe_iiCtr"))
  
  abstract class x338_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x321_realDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x329 = Decoupled(new AppCommandDense(ModuleParams.getParams("x329_p").asInstanceOf[(Int,Int)] ))
      val in_x330_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x330_fifo_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x321_realDRAM = {io.in_x321_realDRAM} 
    def x329 = {io.in_x329} 
    def x330_fifo = {io.in_x330_fifo} ; io.in_x330_fifo := DontCare
  }
  def connectWires0(module: x338_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x321_realDRAM <> x321_realDRAM
    module.io.in_x329 <> x329
    x330_fifo.connectLedger(module.io.in_x330_fifo)
  }
  val x321_realDRAM = list_x321_realDRAM(0)
  val x330_fifo = list_x330_fifo(0)
  val x329 = list_x329(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x338_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x338_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x338_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x338_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x338_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x338_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x338_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x338_inr_UnitPipe = Module(new InstrumentationCounter())
      val idles_x338_inr_UnitPipe = Module(new InstrumentationCounter())
      stalls_x338_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(x329.ready & (~x330_fifo.full.D(1.0-1) | ~(x330_fifo.active(0).out)))
      idles_x338_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X338_instrctr, cycles_x338_inr_UnitPipe.io.count, iters_x338_inr_UnitPipe.io.count, stalls_x338_inr_UnitPipe.io.count, idles_x338_inr_UnitPipe.io.count)
      val x332 = x321_realDRAM
      val x333_tuple = Wire(UInt(97.W)).suggestName("""x333_tuple""")
      x333_tuple.r := ConvAndCat(true.B,64L.FP(true, 32, 0).r,x332.r)
      val x334 = true.B
      x329.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x334 & io.sigsIn.backpressure
      x329.bits.addr := x333_tuple(63,0)
      x329.bits.size := x333_tuple(95,64)
      val x336_tuple = Wire(UInt(96.W)).suggestName("""x336_tuple""")
      x336_tuple.r := ConvAndCat(4L.FP(true, 32, 0).r,0L.FP(true, 32, 0).r,16L.FP(true, 32, 0).r)
      val x337_enq_x330_banks = List[UInt]()
      val x337_enq_x330_ofs = List[UInt]()
      val x337_enq_x330_en = List[Bool](true.B)
      val x337_enq_x330_data = List[UInt](x336_tuple.r)
      x330_fifo.connectWPort(337, x337_enq_x330_banks, x337_enq_x330_ofs, x337_enq_x330_data, x337_enq_x330_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      x330_fifo.connectAccessActivesIn(0, ((true.B)))
    }
    val module = Module(new x338_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x338_inr_UnitPipe **/
