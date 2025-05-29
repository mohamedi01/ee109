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

/** Hierarchy: x335 -> x375 **/
/** BEGIN None x335_inr_Foreach **/
class x335_inr_Foreach_kernel(
  list_x252_outSram_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 30.0.toInt, myName = "x335_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x335_inr_Foreach_iiCtr"))
  
  abstract class x335_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x252_outSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x252_outSram_0_p").asInstanceOf[MemParams] ))
      val in_x251_imagSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x251_imagSram_0_p").asInstanceOf[MemParams] ))
      val in_x250_realSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x250_realSram_0_p").asInstanceOf[MemParams] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x252_outSram_0 = {io.in_x252_outSram_0} ; io.in_x252_outSram_0 := DontCare
    def x251_imagSram_0 = {io.in_x251_imagSram_0} ; io.in_x251_imagSram_0 := DontCare
    def x250_realSram_0 = {io.in_x250_realSram_0} ; io.in_x250_realSram_0 := DontCare
  }
  def connectWires0(module: x335_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x252_outSram_0.connectLedger(module.io.in_x252_outSram_0)
    x251_imagSram_0.connectLedger(module.io.in_x251_imagSram_0)
    x250_realSram_0.connectLedger(module.io.in_x250_realSram_0)
  }
  val x252_outSram_0 = list_x252_outSram_0(0)
  val x251_imagSram_0 = list_x252_outSram_0(1)
  val x250_realSram_0 = list_x252_outSram_0(2)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x335_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x335_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x335_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val b325 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b325.suggestName("b325")
      val b326 = ~io.sigsIn.cchainOutputs.head.oobs(0); b326.suggestName("b326")
      val x327_rd = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x327_rd""")
      val x327_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x327_rd_ofs = List[UInt](b325.r)
      val x327_rd_en = List[Bool](true.B)
      val x327_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && b326 ).suggestName("x327_rd_shared_en")
      x327_rd.toSeq.zip(x250_realSram_0.connectRPort(327, x327_rd_banks, x327_rd_ofs, io.sigsIn.backpressure, x327_rd_en.map(_ && x327_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x328 = VecApply(x327,0)
      val x328_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x328_elem_0""")
      x328_elem_0.r := x327_rd(0).r
      val x330_rd = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x330_rd""")
      val x330_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x330_rd_ofs = List[UInt](b325.r)
      val x330_rd_en = List[Bool](true.B)
      val x330_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && b326 ).suggestName("x330_rd_shared_en")
      x330_rd.toSeq.zip(x251_imagSram_0.connectRPort(330, x330_rd_banks, x330_rd_ofs, io.sigsIn.backpressure, x330_rd_en.map(_ && x330_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x331 = VecApply(x330,0)
      val x331_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x331_elem_0""")
      x331_elem_0.r := x330_rd(0).r
      val x332 = Wire(new FloatingPoint(24, 8)).suggestName("""x332""")
      x332.r := Math.fmul(x331_elem_0, x331_elem_0, Some(8.0), true.B,"x332").r
      val x384 = Wire(new FloatingPoint(24, 8)).suggestName("x384_x328_elem_0_D8") 
      x384.r := getRetimed(x328_elem_0.r, 8.toInt, io.sigsIn.backpressure & true.B)
      val x373 = Wire(new FloatingPoint(24, 8)).suggestName("""x373""")
      x373.r := Math.fma(x384,x384,x332,Some(19.0), true.B,"x373").r
      val x385 = Wire(Bool()).suggestName("x385_b326_D29") 
      x385.r := getRetimed(b326.r, 29.toInt, io.sigsIn.backpressure & true.B)
      val x386 = Wire(new FixedPoint(true, 32, 0)).suggestName("x386_b325_D29") 
      x386.r := getRetimed(b325.r, 29.toInt, io.sigsIn.backpressure & true.B)
      val x334_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x334_wr_ofs = List[UInt](x386.r)
      val x334_wr_en = List[Bool](true.B)
      val x334_wr_data = List[UInt](x373.r)
      x252_outSram_0.connectWPort(334, x334_wr_banks, x334_wr_ofs, x334_wr_data, x334_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(29.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x385))
    }
    val module = Module(new x335_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnrolledForeach x335_inr_Foreach **/
