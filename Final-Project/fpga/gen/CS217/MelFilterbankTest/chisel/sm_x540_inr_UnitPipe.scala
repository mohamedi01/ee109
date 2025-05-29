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

/** Hierarchy: x540 -> x541 -> x614 **/
/** BEGIN None x540_inr_UnitPipe **/
class x540_inr_UnitPipe_kernel(
  list_b515: List[Bool],
  list_b514: List[FixedPoint],
  list_x413_outSRAM_0: List[StandardInterface],
  list_x517_sum_1: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x540_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x540_inr_UnitPipe_iiCtr"))
  
  abstract class x540_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b515 = Input(Bool())
      val in_x517_sum_1 = Flipped(new NBufInterface(ModuleParams.getParams("x517_sum_1_p").asInstanceOf[NBufParams] ))
      val in_x413_outSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x413_outSRAM_0_p").asInstanceOf[MemParams] ))
      val in_b514 = Input(new FixedPoint(true, 32, 0))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def b515 = {io.in_b515} 
    def x517_sum_1 = {io.in_x517_sum_1} ; io.in_x517_sum_1 := DontCare
    def x413_outSRAM_0 = {io.in_x413_outSRAM_0} ; io.in_x413_outSRAM_0 := DontCare
    def b514 = {io.in_b514} 
  }
  def connectWires0(module: x540_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b515 <> b515
    x517_sum_1.connectLedger(module.io.in_x517_sum_1)
    x413_outSRAM_0.connectLedger(module.io.in_x413_outSRAM_0)
    module.io.in_b514 <> b514
  }
  val b515 = list_b515(0)
  val b514 = list_b514(0)
  val x413_outSRAM_0 = list_x413_outSRAM_0(0)
  val x517_sum_1 = list_x517_sum_1(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x540_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x540_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x540_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x540_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x540_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x540_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x540_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X540_instrctr, cycles_x540_inr_UnitPipe.io.count, iters_x540_inr_UnitPipe.io.count, 0.U, 0.U)
      val x538_rd_x517 = Wire(new FixedPoint(true, 16, 8)).suggestName("""x538_rd_x517""")
      val x538_rd_x517_banks = List[UInt]()
      val x538_rd_x517_ofs = List[UInt]()
      val x538_rd_x517_en = List[Bool](true.B)
      val x538_rd_x517_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x538_rd_x517_shared_en")
      x538_rd_x517.toSeq.zip(x517_sum_1.connectRPort(538, x538_rd_x517_banks, x538_rd_x517_ofs, io.sigsIn.backpressure, x538_rd_x517_en.map(_ && x538_rd_x517_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x539_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x539_wr_ofs = List[UInt](b514.r)
      val x539_wr_en = List[Bool](true.B)
      val x539_wr_data = List[UInt](x538_rd_x517.r)
      x413_outSRAM_0.connectWPort(539, x539_wr_banks, x539_wr_ofs, x539_wr_data, x539_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      x517_sum_1.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 1)
    }
    val module = Module(new x540_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x540_inr_UnitPipe **/
