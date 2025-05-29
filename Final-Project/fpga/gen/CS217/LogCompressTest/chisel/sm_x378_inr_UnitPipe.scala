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

/** Hierarchy: x378 -> x393 -> x398 -> x399 -> x439 **/
/** BEGIN None x378_inr_UnitPipe **/
class x378_inr_UnitPipe_kernel(
  list_x292_outDRAM: List[FixedPoint],
  list_x367: List[DecoupledIO[AppCommandDense]],
  list_x371_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x378_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x378_inr_UnitPipe_iiCtr"))
  
  abstract class x378_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x292_outDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x371_reg = Flipped(new StandardInterface(ModuleParams.getParams("x371_reg_p").asInstanceOf[MemParams] ))
      val in_x367 = Decoupled(new AppCommandDense(ModuleParams.getParams("x367_p").asInstanceOf[(Int,Int)] ))
      val in_x370_reg = Flipped(new StandardInterface(ModuleParams.getParams("x370_reg_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x292_outDRAM = {io.in_x292_outDRAM} 
    def x371_reg = {io.in_x371_reg} ; io.in_x371_reg := DontCare
    def x367 = {io.in_x367} 
    def x370_reg = {io.in_x370_reg} ; io.in_x370_reg := DontCare
  }
  def connectWires0(module: x378_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x292_outDRAM <> x292_outDRAM
    x371_reg.connectLedger(module.io.in_x371_reg)
    module.io.in_x367 <> x367
    x370_reg.connectLedger(module.io.in_x370_reg)
  }
  val x292_outDRAM = list_x292_outDRAM(0)
  val x367 = list_x367(0)
  val x371_reg = list_x371_reg(0)
  val x370_reg = list_x371_reg(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x378_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x378_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x378_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x378_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x378_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x378_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x378_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x378_inr_UnitPipe = Module(new InstrumentationCounter())
      val idles_x378_inr_UnitPipe = Module(new InstrumentationCounter())
      stalls_x378_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(x367.ready)
      idles_x378_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X378_instrctr, cycles_x378_inr_UnitPipe.io.count, iters_x378_inr_UnitPipe.io.count, stalls_x378_inr_UnitPipe.io.count, idles_x378_inr_UnitPipe.io.count)
      val x372 = x292_outDRAM
      val x373_tuple = Wire(UInt(97.W)).suggestName("""x373_tuple""")
      x373_tuple.r := ConvAndCat(false.B,63L.FP(true, 32, 0).r,x372.r)
      val x374 = true.B
      x367.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x374 & io.sigsIn.backpressure
      x367.bits.addr := x373_tuple(63,0)
      x367.bits.size := x373_tuple(95,64)
      val x376_wr_x370_banks = List[UInt]()
      val x376_wr_x370_ofs = List[UInt]()
      val x376_wr_x370_en = List[Bool](true.B)
      val x376_wr_x370_data = List[UInt](2L.FP(true, 32, 0).r)
      x370_reg.connectWPort(376, x376_wr_x370_banks, x376_wr_x370_ofs, x376_wr_x370_data, x376_wr_x370_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x377_wr_x371_banks = List[UInt]()
      val x377_wr_x371_ofs = List[UInt]()
      val x377_wr_x371_en = List[Bool](true.B)
      val x377_wr_x371_data = List[UInt](21L.FP(true, 32, 0).r)
      x371_reg.connectWPort(377, x377_wr_x371_banks, x377_wr_x371_ofs, x377_wr_x371_data, x377_wr_x371_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x378_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x378_inr_UnitPipe **/
