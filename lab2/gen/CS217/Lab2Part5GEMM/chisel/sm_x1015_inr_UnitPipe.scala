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

/** Hierarchy: x1015 -> x1335 -> x1250 -> x1251 -> x810 **/
/** BEGIN None x1015_inr_UnitPipe **/
class x1015_inr_UnitPipe_kernel(
  list_b1005: List[Bool],
  list_b1004: List[FixedPoint],
  list_x1010_reg: List[NBufInterface],
  list_x879_N: List[UInt],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 2.0.toInt, myName = "x1015_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1015_inr_UnitPipe_iiCtr"))
  
  abstract class x1015_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b1005 = Input(Bool())
      val in_b925 = Input(Bool())
      val in_x879_N = Input(UInt(64.W))
      val in_x1010_reg = Flipped(new NBufInterface(ModuleParams.getParams("x1010_reg_p").asInstanceOf[NBufParams] ))
      val in_b914 = Input(Bool())
      val in_b1004 = Input(new FixedPoint(true, 32, 0))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def b1005 = {io.in_b1005} 
    def b925 = {io.in_b925} 
    def x879_N = {io.in_x879_N} 
    def x1010_reg = {io.in_x1010_reg} ; io.in_x1010_reg := DontCare
    def b914 = {io.in_b914} 
    def b1004 = {io.in_b1004} 
  }
  def connectWires0(module: x1015_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b1005 <> b1005
    module.io.in_b925 <> b925
    module.io.in_x879_N <> x879_N
    x1010_reg.connectLedger(module.io.in_x1010_reg)
    module.io.in_b914 <> b914
    module.io.in_b1004 <> b1004
  }
  val b1005 = list_b1005(0)
  val b925 = list_b1005(1)
  val b914 = list_b1005(2)
  val b1004 = list_b1004(0)
  val x1010_reg = list_x1010_reg(0)
  val x879_N = list_x879_N(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1015_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x1015_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1015_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1015_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x1015_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x1015_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x1015_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1015_instrctr, cycles_x1015_inr_UnitPipe.io.count, iters_x1015_inr_UnitPipe.io.count, 0.U, 0.U)
      val x1011_rd_x879 = Wire(new FixedPoint(true, 32, 0))
      x1011_rd_x879.r := x879_N.r
      val x1012_sub = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1012_sub""")
      x1012_sub.r := Math.sub(x1011_rd_x879,b1004,Some(1.0), true.B, Truncate, Wrapping, "x1012_sub").r
      val x1013 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1013""")
      x1013.r := Mux((16L.FP(true, 32, 0) < x1012_sub), 16L.FP(true, 32, 0), x1012_sub).r
      val x1014_wr_x1010_banks = List[UInt]()
      val x1014_wr_x1010_ofs = List[UInt]()
      val x1014_wr_x1010_en = List[Bool](true.B)
      val x1014_wr_x1010_data = List[UInt](x1013.r)
      x1010_reg.connectWPort(1014, x1014_wr_x1010_banks, x1014_wr_x1010_ofs, x1014_wr_x1010_data, x1014_wr_x1010_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      x1010_reg.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 0)
    }
    val module = Module(new x1015_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x1015_inr_UnitPipe **/
