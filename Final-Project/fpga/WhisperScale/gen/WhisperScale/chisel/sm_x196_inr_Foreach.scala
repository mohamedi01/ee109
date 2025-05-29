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

/** Hierarchy: x196 -> x201 -> x122 **/
/** BEGIN None x196_inr_Foreach **/
class x196_inr_Foreach_kernel(
  list_x152_buf_0: List[StandardInterface],
  list_x181: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 2.0.toInt, myName = "x196_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x196_inr_Foreach_iiCtr"))
  
  abstract class x196_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x152_buf_0 = Flipped(new StandardInterface(ModuleParams.getParams("x152_buf_0_p").asInstanceOf[MemParams] ))
      val in_x181 = Decoupled(new AppStoreData(ModuleParams.getParams("x181_p").asInstanceOf[(Int,Int)] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x152_buf_0 = {io.in_x152_buf_0} ; io.in_x152_buf_0 := DontCare
    def x181 = {io.in_x181} 
  }
  def connectWires0(module: x196_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x152_buf_0.connectLedger(module.io.in_x152_buf_0)
    module.io.in_x181 <> x181
  }
  val x152_buf_0 = list_x152_buf_0(0)
  val x181 = list_x181(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x196_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x196_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x196_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val b190 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b190.suggestName("b190")
      val b191 = ~io.sigsIn.cchainOutputs.head.oobs(0); b191.suggestName("b191")
      val x192_rd = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x192_rd""")
      val x192_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x192_rd_ofs = List[UInt](b190.r)
      val x192_rd_en = List[Bool](true.B)
      val x192_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B & b191 ).suggestName("x192_rd_shared_en")
      x192_rd.toSeq.zip(x152_buf_0.connectRPort(192, x192_rd_banks, x192_rd_ofs, io.sigsIn.backpressure, x192_rd_en.map(_ && x192_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x193 = VecApply(x192,0)
      val x193_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x193_elem_0""")
      x193_elem_0.r := x192_rd(0).r
      val x194_tuple = Wire(UInt(33.W)).suggestName("""x194_tuple""")
      x194_tuple.r := ConvAndCat(true.B,x193_elem_0.r)
      val x226 = Wire(Bool()).suggestName("x226_b191_D2") 
      x226.r := getRetimed(b191.r, 2.toInt, io.sigsIn.backpressure & true.B)
      x181.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x226 & io.sigsIn.backpressure
      x181.bits.wdata(0) := x194_tuple(31,0)
      x181.bits.wstrb := x194_tuple(32,32)
    }
    val module = Module(new x196_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnrolledForeach x196_inr_Foreach **/
