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

/** Hierarchy: x553 -> x568 -> x573 -> x574 -> x614 **/
/** BEGIN None x553_inr_UnitPipe **/
class x553_inr_UnitPipe_kernel(
  list_x408_outDRAM: List[FixedPoint],
  list_x542: List[DecoupledIO[AppCommandDense]],
  list_x546_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x553_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x553_inr_UnitPipe_iiCtr"))
  
  abstract class x553_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x408_outDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x542 = Decoupled(new AppCommandDense(ModuleParams.getParams("x542_p").asInstanceOf[(Int,Int)] ))
      val in_x546_reg = Flipped(new StandardInterface(ModuleParams.getParams("x546_reg_p").asInstanceOf[MemParams] ))
      val in_x545_reg = Flipped(new StandardInterface(ModuleParams.getParams("x545_reg_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x408_outDRAM = {io.in_x408_outDRAM} 
    def x542 = {io.in_x542} 
    def x546_reg = {io.in_x546_reg} ; io.in_x546_reg := DontCare
    def x545_reg = {io.in_x545_reg} ; io.in_x545_reg := DontCare
  }
  def connectWires0(module: x553_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x408_outDRAM <> x408_outDRAM
    module.io.in_x542 <> x542
    x546_reg.connectLedger(module.io.in_x546_reg)
    x545_reg.connectLedger(module.io.in_x545_reg)
  }
  val x408_outDRAM = list_x408_outDRAM(0)
  val x542 = list_x542(0)
  val x546_reg = list_x546_reg(0)
  val x545_reg = list_x546_reg(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x553_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x553_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x553_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x553_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x553_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x553_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x553_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x553_inr_UnitPipe = Module(new InstrumentationCounter())
      val idles_x553_inr_UnitPipe = Module(new InstrumentationCounter())
      stalls_x553_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(x542.ready)
      idles_x553_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X553_instrctr, cycles_x553_inr_UnitPipe.io.count, iters_x553_inr_UnitPipe.io.count, stalls_x553_inr_UnitPipe.io.count, idles_x553_inr_UnitPipe.io.count)
      val x547 = x408_outDRAM
      val x548_tuple = Wire(UInt(97.W)).suggestName("""x548_tuple""")
      x548_tuple.r := ConvAndCat(false.B,63L.FP(true, 32, 0).r,x547.r)
      val x549 = true.B
      x542.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x549 & io.sigsIn.backpressure
      x542.bits.addr := x548_tuple(63,0)
      x542.bits.size := x548_tuple(95,64)
      val x551_wr_x545_banks = List[UInt]()
      val x551_wr_x545_ofs = List[UInt]()
      val x551_wr_x545_en = List[Bool](true.B)
      val x551_wr_x545_data = List[UInt](2L.FP(true, 32, 0).r)
      x545_reg.connectWPort(551, x551_wr_x545_banks, x551_wr_x545_ofs, x551_wr_x545_data, x551_wr_x545_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x552_wr_x546_banks = List[UInt]()
      val x552_wr_x546_ofs = List[UInt]()
      val x552_wr_x546_en = List[Bool](true.B)
      val x552_wr_x546_data = List[UInt](21L.FP(true, 32, 0).r)
      x546_reg.connectWPort(552, x552_wr_x546_banks, x552_wr_x546_ofs, x552_wr_x546_data, x552_wr_x546_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x553_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x553_inr_UnitPipe **/
