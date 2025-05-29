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

/** Hierarchy: x252 -> x253 -> x254 -> x196 **/
/** BEGIN None x252_inr_Foreach **/
class x252_inr_Foreach_kernel(
  list_x217: List[DecoupledIO[AppLoadData]],
  list_x231_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 2.0.toInt, myName = "x252_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x252_inr_Foreach_iiCtr"))
  
  abstract class x252_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x217 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x217_p").asInstanceOf[(Int, Int)] )))
      val in_x231_reg = Flipped(new StandardInterface(ModuleParams.getParams("x231_reg_p").asInstanceOf[MemParams] ))
      val in_x214_buf_0 = Flipped(new StandardInterface(ModuleParams.getParams("x214_buf_0_p").asInstanceOf[MemParams] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x217 = {io.in_x217} 
    def x231_reg = {io.in_x231_reg} ; io.in_x231_reg := DontCare
    def x214_buf_0 = {io.in_x214_buf_0} ; io.in_x214_buf_0 := DontCare
  }
  def connectWires0(module: x252_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x217 <> x217
    x231_reg.connectLedger(module.io.in_x231_reg)
    x214_buf_0.connectLedger(module.io.in_x214_buf_0)
  }
  val x217 = list_x217(0)
  val x231_reg = list_x231_reg(0)
  val x214_buf_0 = list_x231_reg(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x252_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x252_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x252_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val b243 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b243.suggestName("b243")
      val b244 = ~io.sigsIn.cchainOutputs.head.oobs(0); b244.suggestName("b244")
      val x245 = Wire(Bool()).suggestName("""x245""")
      x245.r := Math.lte(0L.FP(true, 32, 0), b243, Some(0.4), true.B,"x245").r
      val x246_rd_x231 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x246_rd_x231""")
      val x246_rd_x231_banks = List[UInt]()
      val x246_rd_x231_ofs = List[UInt]()
      val x246_rd_x231_en = List[Bool](true.B)
      val x246_rd_x231_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x246_rd_x231_shared_en")
      x246_rd_x231.toSeq.zip(x231_reg.connectRPort(246, x246_rd_x231_banks, x246_rd_x231_ofs, io.sigsIn.backpressure, x246_rd_x231_en.map(_ && x246_rd_x231_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x247 = Wire(Bool()).suggestName("""x247""")
      x247.r := Math.lt(b243, x246_rd_x231, Some(0.4), true.B,"x247").r
      val x248 = Wire(Bool()).suggestName("""x248""")
      x248 := x245 & x247
      val x249 = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x249""")
      x217.ready := b244 & (io.sigsIn.datapathEn) 
      (0 until 1).map{ i => x249(i).r := x217.bits.rdata(i).r }
      val x311 = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("x311_x249_D1") 
      (0 until 1).foreach{i => x311(i).r := getRetimed(x249(i).r, 1.toInt, io.sigsIn.backpressure & true.B)}
      // x250 = VecApply(x311,0)
      val x250_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x250_elem_0""")
      x250_elem_0.r := x311(0).r
      val x312 = Wire(Bool()).suggestName("x312_b244_D1") 
      x312.r := getRetimed(b244.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x313 = Wire(Bool()).suggestName("x313_x248_D1") 
      x313.r := getRetimed(x248.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x314 = Wire(new FixedPoint(true, 32, 0)).suggestName("x314_b243_D1") 
      x314.r := getRetimed(b243.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x251_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x251_wr_ofs = List[UInt](x314.r)
      val x251_wr_en = List[Bool](true.B)
      val x251_wr_data = List[UInt](x250_elem_0.r)
      x214_buf_0.connectWPort(251, x251_wr_banks, x251_wr_ofs, x251_wr_data, x251_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x313 & x312))
    }
    val module = Module(new x252_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnrolledForeach x252_inr_Foreach **/
