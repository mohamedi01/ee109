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

/** Hierarchy: x537 -> x552 -> x710 **/
/** BEGIN None x537_inr_Foreach **/
class x537_inr_Foreach_kernel(
  list_b515: List[Bool],
  list_x468_imag_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 3.0.toInt, myName = "x537_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x537_inr_Foreach_iiCtr"))
  
  abstract class x537_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x468_imag_0 = Flipped(new NBufInterface(ModuleParams.getParams("x468_imag_0_p").asInstanceOf[NBufParams] ))
      val in_x467_real_0 = Flipped(new NBufInterface(ModuleParams.getParams("x467_real_0_p").asInstanceOf[NBufParams] ))
      val in_b515 = Input(Bool())
      val in_x466_frame_0 = Flipped(new NBufInterface(ModuleParams.getParams("x466_frame_0_p").asInstanceOf[NBufParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x468_imag_0 = {io.in_x468_imag_0} ; io.in_x468_imag_0 := DontCare
    def x467_real_0 = {io.in_x467_real_0} ; io.in_x467_real_0 := DontCare
    def b515 = {io.in_b515} 
    def x466_frame_0 = {io.in_x466_frame_0} ; io.in_x466_frame_0 := DontCare
  }
  def connectWires0(module: x537_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x468_imag_0.connectLedger(module.io.in_x468_imag_0)
    x467_real_0.connectLedger(module.io.in_x467_real_0)
    module.io.in_b515 <> b515
    x466_frame_0.connectLedger(module.io.in_x466_frame_0)
  }
  val b515 = list_b515(0)
  val x468_imag_0 = list_x468_imag_0(0)
  val x467_real_0 = list_x468_imag_0(1)
  val x466_frame_0 = list_x468_imag_0(2)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x537_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x537_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x537_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x537_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x537_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x537_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x537_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X537_instrctr, cycles_x537_inr_Foreach.io.count, iters_x537_inr_Foreach.io.count, 0.U, 0.U)
      val b531 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b531.suggestName("b531")
      val b532 = ~io.sigsIn.cchainOutputs.head.oobs(0); b532.suggestName("b532")
      val x533_rd = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x533_rd""")
      val x533_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x533_rd_ofs = List[UInt](b531.r)
      val x533_rd_en = List[Bool](true.B)
      val x533_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && b532 & b515 ).suggestName("x533_rd_shared_en")
      x533_rd.toSeq.zip(x466_frame_0.connectRPort(533, x533_rd_banks, x533_rd_ofs, io.sigsIn.backpressure, x533_rd_en.map(_ && x533_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x534 = VecApply(x533,0)
      val x534_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x534_elem_0""")
      x534_elem_0.r := x533_rd(0).r
      val x721 = Wire(Bool()).suggestName("x721_b515_D2") 
      x721.r := getRetimed(b515.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x722 = Wire(new FixedPoint(true, 32, 0)).suggestName("x722_b531_D2") 
      x722.r := getRetimed(b531.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x723 = Wire(Bool()).suggestName("x723_b532_D2") 
      x723.r := getRetimed(b532.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x535_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x535_wr_ofs = List[UInt](x722.r)
      val x535_wr_en = List[Bool](true.B)
      val x535_wr_data = List[UInt](x534_elem_0.r)
      x467_real_0.connectWPort(535, x535_wr_banks, x535_wr_ofs, x535_wr_data, x535_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x723 & x721))
      val x536_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x536_wr_ofs = List[UInt](b531.r)
      val x536_wr_en = List[Bool](true.B)
      val x536_wr_data = List[UInt](0.0.FlP(24, 8).r)
      x468_imag_0.connectWPort(536, x536_wr_banks, x536_wr_ofs, x536_wr_data, x536_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && b532 & b515))
      x466_frame_0.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 1)
      x467_real_0.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 0)
      x468_imag_0.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 0)
    }
    val module = Module(new x537_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x537_inr_Foreach **/
