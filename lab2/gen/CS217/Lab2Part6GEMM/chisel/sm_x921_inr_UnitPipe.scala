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

/** Hierarchy: x921 -> x1704 -> x810 **/
/** BEGIN None x921_inr_UnitPipe **/
class x921_inr_UnitPipe_kernel(
  list_b914: List[Bool],
  list_b913: List[FixedPoint],
  list_x916_reg: List[NBufInterface],
  list_x880_K: List[UInt],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 2.0.toInt, myName = "x921_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x921_inr_UnitPipe_iiCtr"))
  
  abstract class x921_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b914 = Input(Bool())
      val in_b913 = Input(new FixedPoint(true, 32, 0))
      val in_x916_reg = Flipped(new NBufInterface(ModuleParams.getParams("x916_reg_p").asInstanceOf[NBufParams] ))
      val in_x880_K = Input(UInt(64.W))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def b914 = {io.in_b914} 
    def b913 = {io.in_b913} 
    def x916_reg = {io.in_x916_reg} ; io.in_x916_reg := DontCare
    def x880_K = {io.in_x880_K} 
  }
  def connectWires0(module: x921_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b914 <> b914
    module.io.in_b913 <> b913
    x916_reg.connectLedger(module.io.in_x916_reg)
    module.io.in_x880_K <> x880_K
  }
  val b914 = list_b914(0)
  val b913 = list_b913(0)
  val x916_reg = list_x916_reg(0)
  val x880_K = list_x880_K(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x921_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x921_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x921_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x921_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x921_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x921_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x921_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X921_instrctr, cycles_x921_inr_UnitPipe.io.count, iters_x921_inr_UnitPipe.io.count, 0.U, 0.U)
      val x917_rd_x880 = Wire(new FixedPoint(true, 32, 0))
      x917_rd_x880.r := x880_K.r
      val x918_sub = Wire(new FixedPoint(true, 32, 0)).suggestName("""x918_sub""")
      x918_sub.r := Math.sub(x917_rd_x880,b913,Some(1.0), true.B, Truncate, Wrapping, "x918_sub").r
      val x919 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x919""")
      x919.r := Mux((16L.FP(true, 32, 0) < x918_sub), 16L.FP(true, 32, 0), x918_sub).r
      val x920_wr_x916_banks = List[UInt]()
      val x920_wr_x916_ofs = List[UInt]()
      val x920_wr_x916_en = List[Bool](true.B)
      val x920_wr_x916_data = List[UInt](x919.r)
      x916_reg.connectWPort(920, x920_wr_x916_banks, x920_wr_x916_ofs, x920_wr_x916_data, x920_wr_x916_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      x916_reg.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 0)
    }
    val module = Module(new x921_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x921_inr_UnitPipe **/
