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

/** Hierarchy: x443 -> x490 -> x491 **/
/** BEGIN None x443_inr_Foreach **/
class x443_inr_Foreach_kernel(
  list_x418_maxReg_0: List[StandardInterface],
  list_x337_argIn: List[UInt],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 13.0.toInt, myName = "x443_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(3.0.toInt, 2 + _root_.utils.math.log2Up(3.0.toInt), "x443_inr_Foreach_iiCtr"))
  
  abstract class x443_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x418_maxReg_0 = Flipped(new StandardInterface(ModuleParams.getParams("x418_maxReg_0_p").asInstanceOf[MemParams] ))
      val in_x341_buf_0 = Flipped(new StandardInterface(ModuleParams.getParams("x341_buf_0_p").asInstanceOf[MemParams] ))
      val in_x337_argIn = Input(UInt(64.W))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x418_maxReg_0 = {io.in_x418_maxReg_0} ; io.in_x418_maxReg_0 := DontCare
    def x341_buf_0 = {io.in_x341_buf_0} ; io.in_x341_buf_0 := DontCare
    def x337_argIn = {io.in_x337_argIn} 
  }
  def connectWires0(module: x443_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x418_maxReg_0.connectLedger(module.io.in_x418_maxReg_0)
    x341_buf_0.connectLedger(module.io.in_x341_buf_0)
    module.io.in_x337_argIn <> x337_argIn
  }
  val x418_maxReg_0 = list_x418_maxReg_0(0)
  val x341_buf_0 = list_x418_maxReg_0(1)
  val x337_argIn = list_x337_argIn(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x443_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x443_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x443_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val b434 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b434.suggestName("b434")
      val b435 = ~io.sigsIn.cchainOutputs.head.oobs(0); b435.suggestName("b435")
      val x507 = Wire(new FixedPoint(true, 32, 0)).suggestName("x507_b434_D10") 
      x507.r := getRetimed(b434.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x508 = Wire(Bool()).suggestName("x508_b435_D10") 
      x508.r := getRetimed(b435.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x436_rd = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x436_rd""")
      val x436_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x436_rd_ofs = List[UInt](x507.r)
      val x436_rd_en = List[Bool](true.B)
      val x436_rd_shared_en = ((io.sigsIn.forwardpressure).DS(10.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.0.toInt, rr, io.sigsIn.backpressure & true.B) && x508 ).suggestName("x436_rd_shared_en")
      x436_rd.toSeq.zip(x341_buf_0.connectRPort(436, x436_rd_banks, x436_rd_ofs, io.sigsIn.backpressure, x436_rd_en.map(_ && x436_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x437 = VecApply(x436,0)
      val x437_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x437_elem_0""")
      x437_elem_0.r := x436_rd(0).r
      val x438_rd_x418 = Wire(new FloatingPoint(24, 8)).suggestName("""x438_rd_x418""")
      val x438_rd_x418_banks = List[UInt]()
      val x438_rd_x418_ofs = List[UInt]()
      val x438_rd_x418_en = List[Bool](true.B)
      val x438_rd_x418_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x438_rd_x418_shared_en")
      x438_rd_x418.toSeq.zip(x418_maxReg_0.connectRPort(438, x438_rd_x418_banks, x438_rd_x418_ofs, io.sigsIn.backpressure, x438_rd_x418_en.map(_ && x438_rd_x418_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x439_rd_x337 = Wire(new FloatingPoint(24, 8))
      x439_rd_x337.r := x337_argIn.r
      val x440 = Wire(new FloatingPoint(24, 8)).suggestName("""x440""")
      x440.r := Math.fsub(x438_rd_x418, x439_rd_x337, Some(12.0), true.B,"x440").r
      val x441 = Wire(new FloatingPoint(24, 8)).suggestName("""x441""")
      x441.r := Mux(x437_elem_0 > x440, x437_elem_0.r, x440.r)
      val x509 = Wire(new FixedPoint(true, 32, 0)).suggestName("x509_b434_D12") 
      x509.r := getRetimed(b434.r, 12.toInt, io.sigsIn.backpressure & true.B)
      val x510 = Wire(Bool()).suggestName("x510_b435_D12") 
      x510.r := getRetimed(b435.r, 12.toInt, io.sigsIn.backpressure & true.B)
      val x442_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x442_wr_ofs = List[UInt](x509.r)
      val x442_wr_en = List[Bool](true.B)
      val x442_wr_data = List[UInt](x441.r)
      x341_buf_0.connectWPort(442, x442_wr_banks, x442_wr_ofs, x442_wr_data, x442_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(12.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x510))
    }
    val module = Module(new x443_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnrolledForeach x443_inr_Foreach **/
