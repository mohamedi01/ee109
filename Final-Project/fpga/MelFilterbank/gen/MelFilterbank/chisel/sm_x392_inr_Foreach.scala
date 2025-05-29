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

/** Hierarchy: x392 -> x393 -> x394 -> x450 -> x453 **/
/** BEGIN None x392_inr_Foreach **/
class x392_inr_Foreach_kernel(
  list_x362: List[DecoupledIO[AppLoadData]],
  list_x371_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 2.0.toInt, myName = "x392_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x392_inr_Foreach_iiCtr"))
  
  abstract class x392_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x371_reg = Flipped(new StandardInterface(ModuleParams.getParams("x371_reg_p").asInstanceOf[MemParams] ))
      val in_x362 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x362_p").asInstanceOf[(Int, Int)] )))
      val in_x295_vecSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x295_vecSram_0_p").asInstanceOf[MemParams] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x371_reg = {io.in_x371_reg} ; io.in_x371_reg := DontCare
    def x362 = {io.in_x362} 
    def x295_vecSram_0 = {io.in_x295_vecSram_0} ; io.in_x295_vecSram_0 := DontCare
  }
  def connectWires0(module: x392_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x371_reg.connectLedger(module.io.in_x371_reg)
    module.io.in_x362 <> x362
    x295_vecSram_0.connectLedger(module.io.in_x295_vecSram_0)
  }
  val x362 = list_x362(0)
  val x371_reg = list_x371_reg(0)
  val x295_vecSram_0 = list_x371_reg(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x392_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x392_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x392_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val b383 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b383.suggestName("b383")
      val b384 = ~io.sigsIn.cchainOutputs.head.oobs(0); b384.suggestName("b384")
      val x385 = Wire(Bool()).suggestName("""x385""")
      x385.r := Math.lte(0L.FP(true, 32, 0), b383, Some(0.4), true.B,"x385").r
      val x386_rd_x371 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x386_rd_x371""")
      val x386_rd_x371_banks = List[UInt]()
      val x386_rd_x371_ofs = List[UInt]()
      val x386_rd_x371_en = List[Bool](true.B)
      val x386_rd_x371_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x386_rd_x371_shared_en")
      x386_rd_x371.toSeq.zip(x371_reg.connectRPort(386, x386_rd_x371_banks, x386_rd_x371_ofs, io.sigsIn.backpressure, x386_rd_x371_en.map(_ && x386_rd_x371_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x387 = Wire(Bool()).suggestName("""x387""")
      x387.r := Math.lt(b383, x386_rd_x371, Some(0.4), true.B,"x387").r
      val x388 = Wire(Bool()).suggestName("""x388""")
      x388 := x385 & x387
      val x389 = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x389""")
      x362.ready := b384 & (io.sigsIn.datapathEn) 
      (0 until 1).map{ i => x389(i).r := x362.bits.rdata(i).r }
      val x464 = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("x464_x389_D1") 
      (0 until 1).foreach{i => x464(i).r := getRetimed(x389(i).r, 1.toInt, io.sigsIn.backpressure & true.B)}
      // x390 = VecApply(x464,0)
      val x390_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x390_elem_0""")
      x390_elem_0.r := x464(0).r
      val x465 = Wire(new FixedPoint(true, 32, 0)).suggestName("x465_b383_D1") 
      x465.r := getRetimed(b383.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x466 = Wire(Bool()).suggestName("x466_x388_D1") 
      x466.r := getRetimed(x388.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x467 = Wire(Bool()).suggestName("x467_b384_D1") 
      x467.r := getRetimed(b384.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x391_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x391_wr_ofs = List[UInt](x465.r)
      val x391_wr_en = List[Bool](true.B)
      val x391_wr_data = List[UInt](x390_elem_0.r)
      x295_vecSram_0.connectWPort(391, x391_wr_banks, x391_wr_ofs, x391_wr_data, x391_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x466 & x467))
    }
    val module = Module(new x392_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnrolledForeach x392_inr_Foreach **/
