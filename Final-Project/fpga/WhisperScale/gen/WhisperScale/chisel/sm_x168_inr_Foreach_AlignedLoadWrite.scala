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

/** Hierarchy: x168 -> x169 -> x122 **/
/** BEGIN Some(AlignedLoadWrite) x168_inr_Foreach_AlignedLoadWrite **/
class x168_inr_Foreach_AlignedLoadWrite_kernel(
  list_x154: List[DecoupledIO[AppLoadData]],
  list_x152_buf_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 2.0.toInt, myName = "x168_inr_Foreach_AlignedLoadWrite_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x168_inr_Foreach_AlignedLoadWrite_iiCtr"))
  
  abstract class x168_inr_Foreach_AlignedLoadWrite_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x152_buf_0 = Flipped(new StandardInterface(ModuleParams.getParams("x152_buf_0_p").asInstanceOf[MemParams] ))
      val in_x154 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x154_p").asInstanceOf[(Int, Int)] )))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x152_buf_0 = {io.in_x152_buf_0} ; io.in_x152_buf_0 := DontCare
    def x154 = {io.in_x154} 
  }
  def connectWires0(module: x168_inr_Foreach_AlignedLoadWrite_module)(implicit stack: List[KernelHash]): Unit = {
    x152_buf_0.connectLedger(module.io.in_x152_buf_0)
    module.io.in_x154 <> x154
  }
  val x154 = list_x154(0)
  val x152_buf_0 = list_x152_buf_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x168_inr_Foreach_AlignedLoadWrite")
    implicit val stack = ControllerStack.stack.toList
    class x168_inr_Foreach_AlignedLoadWrite_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x168_inr_Foreach_AlignedLoadWrite_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val b163 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b163.suggestName("b163")
      val b164 = ~io.sigsIn.cchainOutputs.head.oobs(0); b164.suggestName("b164")
      val x165 = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x165""")
      x154.ready := b164 & (io.sigsIn.datapathEn) 
      (0 until 1).map{ i => x165(i).r := x154.bits.rdata(i).r }
      val x221 = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("x221_x165_D1") 
      (0 until 1).foreach{i => x221(i).r := getRetimed(x165(i).r, 1.toInt, io.sigsIn.backpressure & true.B)}
      // x166 = VecApply(x221,0)
      val x166_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x166_elem_0""")
      x166_elem_0.r := x221(0).r
      val x222 = Wire(new FixedPoint(true, 32, 0)).suggestName("x222_b163_D1") 
      x222.r := getRetimed(b163.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x223 = Wire(Bool()).suggestName("x223_b164_D1") 
      x223.r := getRetimed(b164.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x167_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x167_wr_ofs = List[UInt](x222.r)
      val x167_wr_en = List[Bool](true.B)
      val x167_wr_data = List[UInt](x166_elem_0.r)
      x152_buf_0.connectWPort(167, x167_wr_banks, x167_wr_ofs, x167_wr_data, x167_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x223))
    }
    val module = Module(new x168_inr_Foreach_AlignedLoadWrite_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnrolledForeach x168_inr_Foreach_AlignedLoadWrite **/
