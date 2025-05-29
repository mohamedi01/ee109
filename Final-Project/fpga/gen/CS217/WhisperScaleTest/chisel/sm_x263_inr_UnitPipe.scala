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

/** Hierarchy: x263 -> x288 -> x201 **/
/** BEGIN None x263_inr_UnitPipe **/
class x263_inr_UnitPipe_kernel(
  list_x250_inDRAM: List[FixedPoint],
  list_x255_fifo: List[FIFOInterface],
  list_x254: List[DecoupledIO[AppCommandDense]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x263_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x263_inr_UnitPipe_iiCtr"))
  
  abstract class x263_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x255_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x255_fifo_p").asInstanceOf[MemParams] ))
      val in_x250_inDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x254 = Decoupled(new AppCommandDense(ModuleParams.getParams("x254_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x255_fifo = {io.in_x255_fifo} ; io.in_x255_fifo := DontCare
    def x250_inDRAM = {io.in_x250_inDRAM} 
    def x254 = {io.in_x254} 
  }
  def connectWires0(module: x263_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x255_fifo.connectLedger(module.io.in_x255_fifo)
    module.io.in_x250_inDRAM <> x250_inDRAM
    module.io.in_x254 <> x254
  }
  val x250_inDRAM = list_x250_inDRAM(0)
  val x255_fifo = list_x255_fifo(0)
  val x254 = list_x254(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x263_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x263_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x263_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x263_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x263_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x263_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x263_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x263_inr_UnitPipe = Module(new InstrumentationCounter())
      val idles_x263_inr_UnitPipe = Module(new InstrumentationCounter())
      stalls_x263_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~((~x255_fifo.full.D(1.0-1) | ~(x255_fifo.active(0).out)) & x254.ready)
      idles_x263_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X263_instrctr, cycles_x263_inr_UnitPipe.io.count, iters_x263_inr_UnitPipe.io.count, stalls_x263_inr_UnitPipe.io.count, idles_x263_inr_UnitPipe.io.count)
      val x257 = x250_inDRAM
      val x258_tuple = Wire(UInt(97.W)).suggestName("""x258_tuple""")
      x258_tuple.r := ConvAndCat(true.B,63L.FP(true, 32, 0).r,x257.r)
      val x259 = true.B
      x254.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x259 & io.sigsIn.backpressure
      x254.bits.addr := x258_tuple(63,0)
      x254.bits.size := x258_tuple(95,64)
      val x261_tuple = Wire(UInt(96.W)).suggestName("""x261_tuple""")
      x261_tuple.r := ConvAndCat(4L.FP(true, 32, 0).r,0L.FP(true, 32, 0).r,21L.FP(true, 32, 0).r)
      val x262_enq_x255_banks = List[UInt]()
      val x262_enq_x255_ofs = List[UInt]()
      val x262_enq_x255_en = List[Bool](true.B)
      val x262_enq_x255_data = List[UInt](x261_tuple.r)
      x255_fifo.connectWPort(262, x262_enq_x255_banks, x262_enq_x255_ofs, x262_enq_x255_data, x262_enq_x255_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      x255_fifo.connectAccessActivesIn(0, ((true.B)))
    }
    val module = Module(new x263_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x263_inr_UnitPipe **/
