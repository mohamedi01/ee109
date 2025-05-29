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

/** Hierarchy: x285 -> x286 -> x287 -> x374 -> x375 **/
/** BEGIN None x285_inr_Foreach **/
class x285_inr_Foreach_kernel(
  list_x255: List[DecoupledIO[AppLoadData]],
  list_x264_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 2.0.toInt, myName = "x285_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x285_inr_Foreach_iiCtr"))
  
  abstract class x285_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x264_reg = Flipped(new StandardInterface(ModuleParams.getParams("x264_reg_p").asInstanceOf[MemParams] ))
      val in_x255 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x255_p").asInstanceOf[(Int, Int)] )))
      val in_x250_realSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x250_realSram_0_p").asInstanceOf[MemParams] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x264_reg = {io.in_x264_reg} ; io.in_x264_reg := DontCare
    def x255 = {io.in_x255} 
    def x250_realSram_0 = {io.in_x250_realSram_0} ; io.in_x250_realSram_0 := DontCare
  }
  def connectWires0(module: x285_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x264_reg.connectLedger(module.io.in_x264_reg)
    module.io.in_x255 <> x255
    x250_realSram_0.connectLedger(module.io.in_x250_realSram_0)
  }
  val x255 = list_x255(0)
  val x264_reg = list_x264_reg(0)
  val x250_realSram_0 = list_x264_reg(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x285_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x285_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x285_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val b276 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b276.suggestName("b276")
      val b277 = ~io.sigsIn.cchainOutputs.head.oobs(0); b277.suggestName("b277")
      val x278 = Wire(Bool()).suggestName("""x278""")
      x278.r := Math.lte(0L.FP(true, 32, 0), b276, Some(0.4), true.B,"x278").r
      val x279_rd_x264 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x279_rd_x264""")
      val x279_rd_x264_banks = List[UInt]()
      val x279_rd_x264_ofs = List[UInt]()
      val x279_rd_x264_en = List[Bool](true.B)
      val x279_rd_x264_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x279_rd_x264_shared_en")
      x279_rd_x264.toSeq.zip(x264_reg.connectRPort(279, x279_rd_x264_banks, x279_rd_x264_ofs, io.sigsIn.backpressure, x279_rd_x264_en.map(_ && x279_rd_x264_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x280 = Wire(Bool()).suggestName("""x280""")
      x280.r := Math.lt(b276, x279_rd_x264, Some(0.4), true.B,"x280").r
      val x281 = Wire(Bool()).suggestName("""x281""")
      x281 := x278 & x280
      val x282 = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x282""")
      x255.ready := b277 & (io.sigsIn.datapathEn) 
      (0 until 1).map{ i => x282(i).r := x255.bits.rdata(i).r }
      val x376 = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("x376_x282_D1") 
      (0 until 1).foreach{i => x376(i).r := getRetimed(x282(i).r, 1.toInt, io.sigsIn.backpressure & true.B)}
      // x283 = VecApply(x376,0)
      val x283_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x283_elem_0""")
      x283_elem_0.r := x376(0).r
      val x377 = Wire(Bool()).suggestName("x377_b277_D1") 
      x377.r := getRetimed(b277.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x378 = Wire(Bool()).suggestName("x378_x281_D1") 
      x378.r := getRetimed(x281.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x379 = Wire(new FixedPoint(true, 32, 0)).suggestName("x379_b276_D1") 
      x379.r := getRetimed(b276.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x284_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x284_wr_ofs = List[UInt](x379.r)
      val x284_wr_en = List[Bool](true.B)
      val x284_wr_data = List[UInt](x283_elem_0.r)
      x250_realSram_0.connectWPort(284, x284_wr_banks, x284_wr_ofs, x284_wr_data, x284_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x378 & x377))
    }
    val module = Module(new x285_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnrolledForeach x285_inr_Foreach **/
