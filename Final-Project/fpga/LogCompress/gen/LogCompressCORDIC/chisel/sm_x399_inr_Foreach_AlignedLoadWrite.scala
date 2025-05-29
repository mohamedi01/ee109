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

/** Hierarchy: x399 -> x400 -> x489 -> x491 **/
/** BEGIN Some(AlignedLoadWrite) x399_inr_Foreach_AlignedLoadWrite **/
class x399_inr_Foreach_AlignedLoadWrite_kernel(
  list_x385: List[DecoupledIO[AppLoadData]],
  list_x342_Ktable_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 2.0.toInt, myName = "x399_inr_Foreach_AlignedLoadWrite_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x399_inr_Foreach_AlignedLoadWrite_iiCtr"))
  
  abstract class x399_inr_Foreach_AlignedLoadWrite_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x385 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x385_p").asInstanceOf[(Int, Int)] )))
      val in_x342_Ktable_0 = Flipped(new StandardInterface(ModuleParams.getParams("x342_Ktable_0_p").asInstanceOf[MemParams] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x385 = {io.in_x385} 
    def x342_Ktable_0 = {io.in_x342_Ktable_0} ; io.in_x342_Ktable_0 := DontCare
  }
  def connectWires0(module: x399_inr_Foreach_AlignedLoadWrite_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x385 <> x385
    x342_Ktable_0.connectLedger(module.io.in_x342_Ktable_0)
  }
  val x385 = list_x385(0)
  val x342_Ktable_0 = list_x342_Ktable_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x399_inr_Foreach_AlignedLoadWrite")
    implicit val stack = ControllerStack.stack.toList
    class x399_inr_Foreach_AlignedLoadWrite_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x399_inr_Foreach_AlignedLoadWrite_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val b394 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b394.suggestName("b394")
      val b395 = ~io.sigsIn.cchainOutputs.head.oobs(0); b395.suggestName("b395")
      val x396 = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x396""")
      x385.ready := b395 & (io.sigsIn.datapathEn) 
      (0 until 1).map{ i => x396(i).r := x385.bits.rdata(i).r }
      val x499 = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("x499_x396_D1") 
      (0 until 1).foreach{i => x499(i).r := getRetimed(x396(i).r, 1.toInt, io.sigsIn.backpressure & true.B)}
      // x397 = VecApply(x499,0)
      val x397_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x397_elem_0""")
      x397_elem_0.r := x499(0).r
      val x500 = Wire(Bool()).suggestName("x500_b395_D1") 
      x500.r := getRetimed(b395.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x501 = Wire(new FixedPoint(true, 32, 0)).suggestName("x501_b394_D1") 
      x501.r := getRetimed(b394.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x398_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x398_wr_ofs = List[UInt](x501.r)
      val x398_wr_en = List[Bool](true.B)
      val x398_wr_data = List[UInt](x397_elem_0.r)
      x342_Ktable_0.connectWPort(398, x398_wr_banks, x398_wr_ofs, x398_wr_data, x398_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x500))
    }
    val module = Module(new x399_inr_Foreach_AlignedLoadWrite_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnrolledForeach x399_inr_Foreach_AlignedLoadWrite **/
