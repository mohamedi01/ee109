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

/** Hierarchy: x381 -> x382 -> x383 -> x489 -> x491 **/
/** BEGIN None x381_inr_Foreach **/
class x381_inr_Foreach_kernel(
  list_x346: List[DecoupledIO[AppLoadData]],
  list_x360_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 2.0.toInt, myName = "x381_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x381_inr_Foreach_iiCtr"))
  
  abstract class x381_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x360_reg = Flipped(new StandardInterface(ModuleParams.getParams("x360_reg_p").asInstanceOf[MemParams] ))
      val in_x346 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x346_p").asInstanceOf[(Int, Int)] )))
      val in_x341_buf_0 = Flipped(new StandardInterface(ModuleParams.getParams("x341_buf_0_p").asInstanceOf[MemParams] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x360_reg = {io.in_x360_reg} ; io.in_x360_reg := DontCare
    def x346 = {io.in_x346} 
    def x341_buf_0 = {io.in_x341_buf_0} ; io.in_x341_buf_0 := DontCare
  }
  def connectWires0(module: x381_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x360_reg.connectLedger(module.io.in_x360_reg)
    module.io.in_x346 <> x346
    x341_buf_0.connectLedger(module.io.in_x341_buf_0)
  }
  val x346 = list_x346(0)
  val x360_reg = list_x360_reg(0)
  val x341_buf_0 = list_x360_reg(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x381_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x381_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x381_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val b372 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b372.suggestName("b372")
      val b373 = ~io.sigsIn.cchainOutputs.head.oobs(0); b373.suggestName("b373")
      val x374 = Wire(Bool()).suggestName("""x374""")
      x374.r := Math.lte(0L.FP(true, 32, 0), b372, Some(0.4), true.B,"x374").r
      val x375_rd_x360 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x375_rd_x360""")
      val x375_rd_x360_banks = List[UInt]()
      val x375_rd_x360_ofs = List[UInt]()
      val x375_rd_x360_en = List[Bool](true.B)
      val x375_rd_x360_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x375_rd_x360_shared_en")
      x375_rd_x360.toSeq.zip(x360_reg.connectRPort(375, x375_rd_x360_banks, x375_rd_x360_ofs, io.sigsIn.backpressure, x375_rd_x360_en.map(_ && x375_rd_x360_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x376 = Wire(Bool()).suggestName("""x376""")
      x376.r := Math.lt(b372, x375_rd_x360, Some(0.4), true.B,"x376").r
      val x377 = Wire(Bool()).suggestName("""x377""")
      x377 := x374 & x376
      val x378 = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x378""")
      x346.ready := b373 & (io.sigsIn.datapathEn) 
      (0 until 1).map{ i => x378(i).r := x346.bits.rdata(i).r }
      val x495 = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("x495_x378_D1") 
      (0 until 1).foreach{i => x495(i).r := getRetimed(x378(i).r, 1.toInt, io.sigsIn.backpressure & true.B)}
      // x379 = VecApply(x495,0)
      val x379_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x379_elem_0""")
      x379_elem_0.r := x495(0).r
      val x496 = Wire(Bool()).suggestName("x496_b373_D1") 
      x496.r := getRetimed(b373.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x497 = Wire(Bool()).suggestName("x497_x377_D1") 
      x497.r := getRetimed(x377.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x498 = Wire(new FixedPoint(true, 32, 0)).suggestName("x498_b372_D1") 
      x498.r := getRetimed(b372.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x380_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x380_wr_ofs = List[UInt](x498.r)
      val x380_wr_en = List[Bool](true.B)
      val x380_wr_data = List[UInt](x379_elem_0.r)
      x341_buf_0.connectWPort(380, x380_wr_banks, x380_wr_ofs, x380_wr_data, x380_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x497 & x496))
    }
    val module = Module(new x381_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnrolledForeach x381_inr_Foreach **/
