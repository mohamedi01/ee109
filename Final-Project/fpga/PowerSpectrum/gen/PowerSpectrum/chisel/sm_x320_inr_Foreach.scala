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

/** Hierarchy: x320 -> x321 -> x322 -> x374 -> x375 **/
/** BEGIN None x320_inr_Foreach **/
class x320_inr_Foreach_kernel(
  list_x290: List[DecoupledIO[AppLoadData]],
  list_x251_imagSram_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 2.0.toInt, myName = "x320_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x320_inr_Foreach_iiCtr"))
  
  abstract class x320_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x251_imagSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x251_imagSram_0_p").asInstanceOf[MemParams] ))
      val in_x299_reg = Flipped(new StandardInterface(ModuleParams.getParams("x299_reg_p").asInstanceOf[MemParams] ))
      val in_x290 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x290_p").asInstanceOf[(Int, Int)] )))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x251_imagSram_0 = {io.in_x251_imagSram_0} ; io.in_x251_imagSram_0 := DontCare
    def x299_reg = {io.in_x299_reg} ; io.in_x299_reg := DontCare
    def x290 = {io.in_x290} 
  }
  def connectWires0(module: x320_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x251_imagSram_0.connectLedger(module.io.in_x251_imagSram_0)
    x299_reg.connectLedger(module.io.in_x299_reg)
    module.io.in_x290 <> x290
  }
  val x290 = list_x290(0)
  val x251_imagSram_0 = list_x251_imagSram_0(0)
  val x299_reg = list_x251_imagSram_0(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x320_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x320_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x320_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val b311 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b311.suggestName("b311")
      val b312 = ~io.sigsIn.cchainOutputs.head.oobs(0); b312.suggestName("b312")
      val x313 = Wire(Bool()).suggestName("""x313""")
      x313.r := Math.lte(0L.FP(true, 32, 0), b311, Some(0.4), true.B,"x313").r
      val x314_rd_x299 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x314_rd_x299""")
      val x314_rd_x299_banks = List[UInt]()
      val x314_rd_x299_ofs = List[UInt]()
      val x314_rd_x299_en = List[Bool](true.B)
      val x314_rd_x299_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x314_rd_x299_shared_en")
      x314_rd_x299.toSeq.zip(x299_reg.connectRPort(314, x314_rd_x299_banks, x314_rd_x299_ofs, io.sigsIn.backpressure, x314_rd_x299_en.map(_ && x314_rd_x299_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x315 = Wire(Bool()).suggestName("""x315""")
      x315.r := Math.lt(b311, x314_rd_x299, Some(0.4), true.B,"x315").r
      val x316 = Wire(Bool()).suggestName("""x316""")
      x316 := x313 & x315
      val x317 = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x317""")
      x290.ready := b312 & (io.sigsIn.datapathEn) 
      (0 until 1).map{ i => x317(i).r := x290.bits.rdata(i).r }
      val x380 = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("x380_x317_D1") 
      (0 until 1).foreach{i => x380(i).r := getRetimed(x317(i).r, 1.toInt, io.sigsIn.backpressure & true.B)}
      // x318 = VecApply(x380,0)
      val x318_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x318_elem_0""")
      x318_elem_0.r := x380(0).r
      val x381 = Wire(new FixedPoint(true, 32, 0)).suggestName("x381_b311_D1") 
      x381.r := getRetimed(b311.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x382 = Wire(Bool()).suggestName("x382_b312_D1") 
      x382.r := getRetimed(b312.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x383 = Wire(Bool()).suggestName("x383_x316_D1") 
      x383.r := getRetimed(x316.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x319_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x319_wr_ofs = List[UInt](x381.r)
      val x319_wr_en = List[Bool](true.B)
      val x319_wr_data = List[UInt](x318_elem_0.r)
      x251_imagSram_0.connectWPort(319, x319_wr_banks, x319_wr_ofs, x319_wr_data, x319_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x383 & x382))
    }
    val module = Module(new x320_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnrolledForeach x320_inr_Foreach **/
