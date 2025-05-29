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

/** Hierarchy: x486 -> x511 -> x613 -> x614 **/
/** BEGIN None x486_inr_UnitPipe **/
class x486_inr_UnitPipe_kernel(
  list_x407_vecDRAM: List[FixedPoint],
  list_x478_fifo: List[FIFOInterface],
  list_x477: List[DecoupledIO[AppCommandDense]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x486_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x486_inr_UnitPipe_iiCtr"))
  
  abstract class x486_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x407_vecDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x477 = Decoupled(new AppCommandDense(ModuleParams.getParams("x477_p").asInstanceOf[(Int,Int)] ))
      val in_x478_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x478_fifo_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x407_vecDRAM = {io.in_x407_vecDRAM} 
    def x477 = {io.in_x477} 
    def x478_fifo = {io.in_x478_fifo} ; io.in_x478_fifo := DontCare
  }
  def connectWires0(module: x486_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x407_vecDRAM <> x407_vecDRAM
    module.io.in_x477 <> x477
    x478_fifo.connectLedger(module.io.in_x478_fifo)
  }
  val x407_vecDRAM = list_x407_vecDRAM(0)
  val x478_fifo = list_x478_fifo(0)
  val x477 = list_x477(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x486_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x486_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x486_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x486_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x486_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x486_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x486_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x486_inr_UnitPipe = Module(new InstrumentationCounter())
      val idles_x486_inr_UnitPipe = Module(new InstrumentationCounter())
      stalls_x486_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(x477.ready & (~x478_fifo.full.D(1.0-1) | ~(x478_fifo.active(0).out)))
      idles_x486_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X486_instrctr, cycles_x486_inr_UnitPipe.io.count, iters_x486_inr_UnitPipe.io.count, stalls_x486_inr_UnitPipe.io.count, idles_x486_inr_UnitPipe.io.count)
      val x480 = x407_vecDRAM
      val x481_tuple = Wire(UInt(97.W)).suggestName("""x481_tuple""")
      x481_tuple.r := ConvAndCat(true.B,63L.FP(true, 32, 0).r,x480.r)
      val x482 = true.B
      x477.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x482 & io.sigsIn.backpressure
      x477.bits.addr := x481_tuple(63,0)
      x477.bits.size := x481_tuple(95,64)
      val x484_tuple = Wire(UInt(96.W)).suggestName("""x484_tuple""")
      x484_tuple.r := ConvAndCat(3L.FP(true, 32, 0).r,0L.FP(true, 32, 0).r,21L.FP(true, 32, 0).r)
      val x485_enq_x478_banks = List[UInt]()
      val x485_enq_x478_ofs = List[UInt]()
      val x485_enq_x478_en = List[Bool](true.B)
      val x485_enq_x478_data = List[UInt](x484_tuple.r)
      x478_fifo.connectWPort(485, x485_enq_x478_banks, x485_enq_x478_ofs, x485_enq_x478_data, x485_enq_x478_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      x478_fifo.connectAccessActivesIn(0, ((true.B)))
    }
    val module = Module(new x486_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x486_inr_UnitPipe **/
