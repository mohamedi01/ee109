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

/** Hierarchy: x295 -> x296 -> x301 -> x302 -> x196 **/
/** BEGIN None x295_inr_Foreach **/
class x295_inr_Foreach_kernel(
  list_x214_buf_0: List[StandardInterface],
  list_x266: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 2.6.toInt, myName = "x295_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x295_inr_Foreach_iiCtr"))
  
  abstract class x295_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x266 = Decoupled(new AppStoreData(ModuleParams.getParams("x266_p").asInstanceOf[(Int,Int)] ))
      val in_x214_buf_0 = Flipped(new StandardInterface(ModuleParams.getParams("x214_buf_0_p").asInstanceOf[MemParams] ))
      val in_x268_reg = Flipped(new StandardInterface(ModuleParams.getParams("x268_reg_p").asInstanceOf[MemParams] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x266 = {io.in_x266} 
    def x214_buf_0 = {io.in_x214_buf_0} ; io.in_x214_buf_0 := DontCare
    def x268_reg = {io.in_x268_reg} ; io.in_x268_reg := DontCare
  }
  def connectWires0(module: x295_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x266 <> x266
    x214_buf_0.connectLedger(module.io.in_x214_buf_0)
    x268_reg.connectLedger(module.io.in_x268_reg)
  }
  val x214_buf_0 = list_x214_buf_0(0)
  val x268_reg = list_x214_buf_0(1)
  val x266 = list_x266(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x295_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x295_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x295_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val b285 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b285.suggestName("b285")
      val b286 = ~io.sigsIn.cchainOutputs.head.oobs(0); b286.suggestName("b286")
      val x287 = Wire(Bool()).suggestName("""x287""")
      val ensig0 = Wire(Bool())
      ensig0 := x266.ready
      x287.r := Math.lte(0L.FP(true, 32, 0), b285, Some(0.4), ensig0,"x287").r
      val x288_rd_x268 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x288_rd_x268""")
      val x288_rd_x268_banks = List[UInt]()
      val x288_rd_x268_ofs = List[UInt]()
      val x288_rd_x268_en = List[Bool](true.B)
      val x288_rd_x268_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x288_rd_x268_shared_en")
      x288_rd_x268.toSeq.zip(x268_reg.connectRPort(288, x288_rd_x268_banks, x288_rd_x268_ofs, io.sigsIn.backpressure, x288_rd_x268_en.map(_ && x288_rd_x268_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x289 = Wire(Bool()).suggestName("""x289""")
      x289.r := Math.lt(b285, x288_rd_x268, Some(0.4), ensig0,"x289").r
      val x290 = Wire(Bool()).suggestName("""x290""")
      x290 := x287 & x289
      val x291_rd = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x291_rd""")
      val x291_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x291_rd_ofs = List[UInt](b285.r)
      val x291_rd_en = List[Bool](true.B)
      val x291_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.6.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.6.toInt, rr, io.sigsIn.backpressure & true.B) && x290 & true.B & b286 ).suggestName("x291_rd_shared_en")
      x291_rd.toSeq.zip(x214_buf_0.connectRPort(291, x291_rd_banks, x291_rd_ofs, io.sigsIn.backpressure, x291_rd_en.map(_ && x291_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x292 = VecApply(x291,0)
      val x292_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x292_elem_0""")
      x292_elem_0.r := x291_rd(0).r
      val x319 = Wire(Bool()).suggestName("x319_x290_D2") 
      x319.r := getRetimed(x290.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x293_tuple = Wire(UInt(33.W)).suggestName("""x293_tuple""")
      x293_tuple.r := ConvAndCat(x319,x292_elem_0.r)
      val x320 = Wire(Bool()).suggestName("x320_b286_D2") 
      x320.r := getRetimed(b286.r, 2.toInt, io.sigsIn.backpressure & true.B)
      x266.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.6.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x320 & io.sigsIn.backpressure
      x266.bits.wdata(0) := x293_tuple(31,0)
      x266.bits.wstrb := x293_tuple(32,32)
    }
    val module = Module(new x295_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnrolledForeach x295_inr_Foreach **/
