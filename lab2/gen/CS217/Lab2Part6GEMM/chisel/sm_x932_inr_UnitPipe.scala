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

/** Hierarchy: x932 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x932_inr_UnitPipe **/
class x932_inr_UnitPipe_kernel(
  list_b925: List[Bool],
  list_b924: List[FixedPoint],
  list_x927_reg: List[NBufInterface],
  list_x878_M: List[UInt],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 2.0.toInt, myName = "x932_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x932_inr_UnitPipe_iiCtr"))
  
  abstract class x932_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x878_M = Input(UInt(64.W))
      val in_b924 = Input(new FixedPoint(true, 32, 0))
      val in_b925 = Input(Bool())
      val in_x927_reg = Flipped(new NBufInterface(ModuleParams.getParams("x927_reg_p").asInstanceOf[NBufParams] ))
      val in_b914 = Input(Bool())
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x878_M = {io.in_x878_M} 
    def b924 = {io.in_b924} 
    def b925 = {io.in_b925} 
    def x927_reg = {io.in_x927_reg} ; io.in_x927_reg := DontCare
    def b914 = {io.in_b914} 
  }
  def connectWires0(module: x932_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x878_M <> x878_M
    module.io.in_b924 <> b924
    module.io.in_b925 <> b925
    x927_reg.connectLedger(module.io.in_x927_reg)
    module.io.in_b914 <> b914
  }
  val b925 = list_b925(0)
  val b914 = list_b925(1)
  val b924 = list_b924(0)
  val x927_reg = list_x927_reg(0)
  val x878_M = list_x878_M(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x932_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x932_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x932_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x932_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x932_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x932_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x932_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X932_instrctr, cycles_x932_inr_UnitPipe.io.count, iters_x932_inr_UnitPipe.io.count, 0.U, 0.U)
      val x928_rd_x878 = Wire(new FixedPoint(true, 32, 0))
      x928_rd_x878.r := x878_M.r
      val x929_sub = Wire(new FixedPoint(true, 32, 0)).suggestName("""x929_sub""")
      x929_sub.r := Math.sub(x928_rd_x878,b924,Some(1.0), true.B, Truncate, Wrapping, "x929_sub").r
      val x930 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x930""")
      x930.r := Mux((16L.FP(true, 32, 0) < x929_sub), 16L.FP(true, 32, 0), x929_sub).r
      val x931_wr_x927_banks = List[UInt]()
      val x931_wr_x927_ofs = List[UInt]()
      val x931_wr_x927_en = List[Bool](true.B)
      val x931_wr_x927_data = List[UInt](x930.r)
      x927_reg.connectWPort(931, x931_wr_x927_banks, x931_wr_x927_ofs, x931_wr_x927_data, x931_wr_x927_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      x927_reg.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 0)
    }
    val module = Module(new x932_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x932_inr_UnitPipe **/
