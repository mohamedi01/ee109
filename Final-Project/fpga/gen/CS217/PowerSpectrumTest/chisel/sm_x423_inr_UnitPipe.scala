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

/** Hierarchy: x423 -> x438 -> x443 -> x444 -> x481 **/
/** BEGIN None x423_inr_UnitPipe **/
class x423_inr_UnitPipe_kernel(
  list_x323_outDRAM: List[FixedPoint],
  list_x412: List[DecoupledIO[AppCommandDense]],
  list_x416_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x423_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x423_inr_UnitPipe_iiCtr"))
  
  abstract class x423_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x416_reg = Flipped(new StandardInterface(ModuleParams.getParams("x416_reg_p").asInstanceOf[MemParams] ))
      val in_x412 = Decoupled(new AppCommandDense(ModuleParams.getParams("x412_p").asInstanceOf[(Int,Int)] ))
      val in_x323_outDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x415_reg = Flipped(new StandardInterface(ModuleParams.getParams("x415_reg_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x416_reg = {io.in_x416_reg} ; io.in_x416_reg := DontCare
    def x412 = {io.in_x412} 
    def x323_outDRAM = {io.in_x323_outDRAM} 
    def x415_reg = {io.in_x415_reg} ; io.in_x415_reg := DontCare
  }
  def connectWires0(module: x423_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x416_reg.connectLedger(module.io.in_x416_reg)
    module.io.in_x412 <> x412
    module.io.in_x323_outDRAM <> x323_outDRAM
    x415_reg.connectLedger(module.io.in_x415_reg)
  }
  val x323_outDRAM = list_x323_outDRAM(0)
  val x412 = list_x412(0)
  val x416_reg = list_x416_reg(0)
  val x415_reg = list_x416_reg(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x423_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x423_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x423_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x423_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x423_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x423_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x423_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x423_inr_UnitPipe = Module(new InstrumentationCounter())
      val idles_x423_inr_UnitPipe = Module(new InstrumentationCounter())
      stalls_x423_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(x412.ready)
      idles_x423_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X423_instrctr, cycles_x423_inr_UnitPipe.io.count, iters_x423_inr_UnitPipe.io.count, stalls_x423_inr_UnitPipe.io.count, idles_x423_inr_UnitPipe.io.count)
      val x417 = x323_outDRAM
      val x418_tuple = Wire(UInt(97.W)).suggestName("""x418_tuple""")
      x418_tuple.r := ConvAndCat(false.B,64L.FP(true, 32, 0).r,x417.r)
      val x419 = true.B
      x412.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x419 & io.sigsIn.backpressure
      x412.bits.addr := x418_tuple(63,0)
      x412.bits.size := x418_tuple(95,64)
      val x421_wr_x415_banks = List[UInt]()
      val x421_wr_x415_ofs = List[UInt]()
      val x421_wr_x415_en = List[Bool](true.B)
      val x421_wr_x415_data = List[UInt](4L.FP(true, 32, 0).r)
      x415_reg.connectWPort(421, x421_wr_x415_banks, x421_wr_x415_ofs, x421_wr_x415_data, x421_wr_x415_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x422_wr_x416_banks = List[UInt]()
      val x422_wr_x416_ofs = List[UInt]()
      val x422_wr_x416_en = List[Bool](true.B)
      val x422_wr_x416_data = List[UInt](16L.FP(true, 32, 0).r)
      x416_reg.connectWPort(422, x422_wr_x416_banks, x422_wr_x416_ofs, x422_wr_x416_data, x422_wr_x416_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x423_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x423_inr_UnitPipe **/
