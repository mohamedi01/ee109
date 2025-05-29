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

/** Hierarchy: x416 -> x417 -> x489 -> x491 **/
/** BEGIN Some(AlignedLoadWrite) x416_inr_Foreach_AlignedLoadWrite **/
class x416_inr_Foreach_AlignedLoadWrite_kernel(
  list_x402: List[DecoupledIO[AppLoadData]],
  list_x343_twoNegSram_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 2.0.toInt, myName = "x416_inr_Foreach_AlignedLoadWrite_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x416_inr_Foreach_AlignedLoadWrite_iiCtr"))
  
  abstract class x416_inr_Foreach_AlignedLoadWrite_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x343_twoNegSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x343_twoNegSram_0_p").asInstanceOf[MemParams] ))
      val in_x402 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x402_p").asInstanceOf[(Int, Int)] )))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x343_twoNegSram_0 = {io.in_x343_twoNegSram_0} ; io.in_x343_twoNegSram_0 := DontCare
    def x402 = {io.in_x402} 
  }
  def connectWires0(module: x416_inr_Foreach_AlignedLoadWrite_module)(implicit stack: List[KernelHash]): Unit = {
    x343_twoNegSram_0.connectLedger(module.io.in_x343_twoNegSram_0)
    module.io.in_x402 <> x402
  }
  val x402 = list_x402(0)
  val x343_twoNegSram_0 = list_x343_twoNegSram_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x416_inr_Foreach_AlignedLoadWrite")
    implicit val stack = ControllerStack.stack.toList
    class x416_inr_Foreach_AlignedLoadWrite_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x416_inr_Foreach_AlignedLoadWrite_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val b411 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b411.suggestName("b411")
      val b412 = ~io.sigsIn.cchainOutputs.head.oobs(0); b412.suggestName("b412")
      val x413 = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x413""")
      x402.ready := b412 & (io.sigsIn.datapathEn) 
      (0 until 1).map{ i => x413(i).r := x402.bits.rdata(i).r }
      val x502 = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("x502_x413_D1") 
      (0 until 1).foreach{i => x502(i).r := getRetimed(x413(i).r, 1.toInt, io.sigsIn.backpressure & true.B)}
      // x414 = VecApply(x502,0)
      val x414_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x414_elem_0""")
      x414_elem_0.r := x502(0).r
      val x503 = Wire(new FixedPoint(true, 32, 0)).suggestName("x503_b411_D1") 
      x503.r := getRetimed(b411.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x504 = Wire(Bool()).suggestName("x504_b412_D1") 
      x504.r := getRetimed(b412.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x415_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x415_wr_ofs = List[UInt](x503.r)
      val x415_wr_en = List[Bool](true.B)
      val x415_wr_data = List[UInt](x414_elem_0.r)
      x343_twoNegSram_0.connectWPort(415, x415_wr_banks, x415_wr_ofs, x415_wr_data, x415_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x504))
    }
    val module = Module(new x416_inr_Foreach_AlignedLoadWrite_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnrolledForeach x416_inr_Foreach_AlignedLoadWrite **/
