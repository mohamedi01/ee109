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

/** Hierarchy: x431 -> x491 **/
/** BEGIN None x431_inr_Reduce **/
class x431_inr_Reduce_kernel(
  list_x418_maxReg_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 4.0.toInt, myName = "x431_inr_Reduce_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(2.0.toInt, 2 + _root_.utils.math.log2Up(2.0.toInt), "x431_inr_Reduce_iiCtr"))
  
  abstract class x431_inr_Reduce_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x418_maxReg_0 = Flipped(new StandardInterface(ModuleParams.getParams("x418_maxReg_0_p").asInstanceOf[MemParams] ))
      val in_x341_buf_0 = Flipped(new StandardInterface(ModuleParams.getParams("x341_buf_0_p").asInstanceOf[MemParams] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x418_maxReg_0 = {io.in_x418_maxReg_0} ; io.in_x418_maxReg_0 := DontCare
    def x341_buf_0 = {io.in_x341_buf_0} ; io.in_x341_buf_0 := DontCare
  }
  def connectWires0(module: x431_inr_Reduce_module)(implicit stack: List[KernelHash]): Unit = {
    x418_maxReg_0.connectLedger(module.io.in_x418_maxReg_0)
    x341_buf_0.connectLedger(module.io.in_x341_buf_0)
  }
  val x418_maxReg_0 = list_x418_maxReg_0(0)
  val x341_buf_0 = list_x418_maxReg_0(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x431_inr_Reduce")
    implicit val stack = ControllerStack.stack.toList
    class x431_inr_Reduce_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x431_inr_Reduce_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val b421 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b421.suggestName("b421")
      val b422 = ~io.sigsIn.cchainOutputs.head.oobs(0); b422.suggestName("b422")
      val x423_rd = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x423_rd""")
      val x423_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x423_rd_ofs = List[UInt](b421.r)
      val x423_rd_en = List[Bool](true.B)
      val x423_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && b422 ).suggestName("x423_rd_shared_en")
      x423_rd.toSeq.zip(x341_buf_0.connectRPort(423, x423_rd_banks, x423_rd_ofs, io.sigsIn.backpressure, x423_rd_en.map(_ && x423_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x424 = VecApply(x423,0)
      val x424_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x424_elem_0""")
      x424_elem_0.r := x423_rd(0).r
      val x425_rd_x418 = Wire(new FloatingPoint(24, 8)).suggestName("""x425_rd_x418""")
      val x425_rd_x418_banks = List[UInt]()
      val x425_rd_x418_ofs = List[UInt]()
      val x425_rd_x418_en = List[Bool](true.B)
      val x425_rd_x418_shared_en = ((io.sigsIn.forwardpressure).DS(2.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x425_rd_x418_shared_en")
      x425_rd_x418.toSeq.zip(x418_maxReg_0.connectRPort(425, x425_rd_x418_banks, x425_rd_x418_ofs, io.sigsIn.backpressure, x425_rd_x418_en.map(_ && x425_rd_x418_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x426 = Wire(Bool()).suggestName("""x426""")
      x426.r := Math.eql(b421, 0L.FP(true, 32, 0), Some(0.2), true.B,"x426").r
      val x427 = Wire(Bool()).suggestName("""x427""")
      x427.r := Math.flt(x425_rd_x418, x424_elem_0, Some(0.0), true.B,"x427").r
      val x428 = Wire(new FloatingPoint(24, 8)).suggestName("""x428""")
      x428.r := Mux((x427), x424_elem_0.r, x425_rd_x418.r)
      val x505 = Wire(Bool()).suggestName("x505_x426_D2") 
      x505.r := getRetimed(x426.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x429 = Wire(new FloatingPoint(24, 8)).suggestName("""x429""")
      x429.r := Mux((x505), x424_elem_0.r, x428.r)
      val x506 = Wire(new FloatingPoint(24, 8)).suggestName("x506_x429_D1") 
      x506.r := getRetimed(x429.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x430_wr_x418_banks = List[UInt]()
      val x430_wr_x418_ofs = List[UInt]()
      val x430_wr_x418_en = List[Bool](true.B)
      val x430_wr_x418_data = List[UInt](x506.r)
      x418_maxReg_0.connectWPort(430, x430_wr_x418_banks, x430_wr_x418_ofs, x430_wr_x418_data, x430_wr_x418_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(3.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x431_inr_Reduce_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnrolledReduce x431_inr_Reduce **/
