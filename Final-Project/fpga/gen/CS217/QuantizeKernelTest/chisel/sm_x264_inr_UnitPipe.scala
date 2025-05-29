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

/** Hierarchy: x264 -> x289 -> x213 **/
/** BEGIN None x264_inr_UnitPipe **/
class x264_inr_UnitPipe_kernel(
  list_x250_inDRAM: List[FixedPoint],
  list_x256_fifo: List[FIFOInterface],
  list_x255: List[DecoupledIO[AppCommandDense]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x264_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x264_inr_UnitPipe_iiCtr"))
  
  abstract class x264_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x256_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x256_fifo_p").asInstanceOf[MemParams] ))
      val in_x255 = Decoupled(new AppCommandDense(ModuleParams.getParams("x255_p").asInstanceOf[(Int,Int)] ))
      val in_x250_inDRAM = Input(new FixedPoint(true, 64, 0))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x256_fifo = {io.in_x256_fifo} ; io.in_x256_fifo := DontCare
    def x255 = {io.in_x255} 
    def x250_inDRAM = {io.in_x250_inDRAM} 
  }
  def connectWires0(module: x264_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x256_fifo.connectLedger(module.io.in_x256_fifo)
    module.io.in_x255 <> x255
    module.io.in_x250_inDRAM <> x250_inDRAM
  }
  val x250_inDRAM = list_x250_inDRAM(0)
  val x256_fifo = list_x256_fifo(0)
  val x255 = list_x255(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x264_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x264_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x264_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x264_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x264_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x264_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x264_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x264_inr_UnitPipe = Module(new InstrumentationCounter())
      val idles_x264_inr_UnitPipe = Module(new InstrumentationCounter())
      stalls_x264_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~((~x256_fifo.full.D(1.0-1) | ~(x256_fifo.active(0).out)) & x255.ready)
      idles_x264_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X264_instrctr, cycles_x264_inr_UnitPipe.io.count, iters_x264_inr_UnitPipe.io.count, stalls_x264_inr_UnitPipe.io.count, idles_x264_inr_UnitPipe.io.count)
      val x258 = x250_inDRAM
      val x259_tuple = Wire(UInt(97.W)).suggestName("""x259_tuple""")
      x259_tuple.r := ConvAndCat(true.B,63L.FP(true, 32, 0).r,x258.r)
      val x260 = true.B
      x255.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x260 & io.sigsIn.backpressure
      x255.bits.addr := x259_tuple(63,0)
      x255.bits.size := x259_tuple(95,64)
      val x262_tuple = Wire(UInt(96.W)).suggestName("""x262_tuple""")
      x262_tuple.r := ConvAndCat(9L.FP(true, 32, 0).r,0L.FP(true, 32, 0).r,21L.FP(true, 32, 0).r)
      val x263_enq_x256_banks = List[UInt]()
      val x263_enq_x256_ofs = List[UInt]()
      val x263_enq_x256_en = List[Bool](true.B)
      val x263_enq_x256_data = List[UInt](x262_tuple.r)
      x256_fifo.connectWPort(263, x263_enq_x256_banks, x263_enq_x256_ofs, x263_enq_x256_data, x263_enq_x256_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      x256_fifo.connectAccessActivesIn(0, ((true.B)))
    }
    val module = Module(new x264_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x264_inr_UnitPipe **/
