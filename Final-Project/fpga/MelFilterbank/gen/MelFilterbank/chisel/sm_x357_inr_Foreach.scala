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

/** Hierarchy: x357 -> x358 -> x359 -> x450 -> x453 **/
/** BEGIN None x357_inr_Foreach **/
class x357_inr_Foreach_kernel(
  list_b327: List[Bool],
  list_b326: List[FixedPoint],
  list_x299: List[DecoupledIO[AppLoadData]],
  list_x329_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 8.0.toInt, myName = "x357_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x357_inr_Foreach_iiCtr"))
  
  abstract class x357_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x329_reg = Flipped(new StandardInterface(ModuleParams.getParams("x329_reg_p").asInstanceOf[MemParams] ))
      val in_x328_reg = Flipped(new StandardInterface(ModuleParams.getParams("x328_reg_p").asInstanceOf[MemParams] ))
      val in_x299 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x299_p").asInstanceOf[(Int, Int)] )))
      val in_b327 = Input(Bool())
      val in_b326 = Input(new FixedPoint(true, 32, 0))
      val in_x294_matSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x294_matSram_0_p").asInstanceOf[MemParams] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x329_reg = {io.in_x329_reg} ; io.in_x329_reg := DontCare
    def x328_reg = {io.in_x328_reg} ; io.in_x328_reg := DontCare
    def x299 = {io.in_x299} 
    def b327 = {io.in_b327} 
    def b326 = {io.in_b326} 
    def x294_matSram_0 = {io.in_x294_matSram_0} ; io.in_x294_matSram_0 := DontCare
  }
  def connectWires0(module: x357_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x329_reg.connectLedger(module.io.in_x329_reg)
    x328_reg.connectLedger(module.io.in_x328_reg)
    module.io.in_x299 <> x299
    module.io.in_b327 <> b327
    module.io.in_b326 <> b326
    x294_matSram_0.connectLedger(module.io.in_x294_matSram_0)
  }
  val b327 = list_b327(0)
  val b326 = list_b326(0)
  val x299 = list_x299(0)
  val x329_reg = list_x329_reg(0)
  val x328_reg = list_x329_reg(1)
  val x294_matSram_0 = list_x329_reg(2)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x357_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x357_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x357_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val b343 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b343.suggestName("b343")
      val b344 = ~io.sigsIn.cchainOutputs.head.oobs(0); b344.suggestName("b344")
      val x345_rd_x328 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x345_rd_x328""")
      val x345_rd_x328_banks = List[UInt]()
      val x345_rd_x328_ofs = List[UInt]()
      val x345_rd_x328_en = List[Bool](true.B)
      val x345_rd_x328_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x345_rd_x328_shared_en")
      x345_rd_x328.toSeq.zip(x328_reg.connectRPort(345, x345_rd_x328_banks, x345_rd_x328_ofs, io.sigsIn.backpressure, x345_rd_x328_en.map(_ && x345_rd_x328_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x346 = Wire(Bool()).suggestName("""x346""")
      x346.r := Math.lte(x345_rd_x328, b343, Some(0.4), true.B,"x346").r
      val x347_rd_x329 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x347_rd_x329""")
      val x347_rd_x329_banks = List[UInt]()
      val x347_rd_x329_ofs = List[UInt]()
      val x347_rd_x329_en = List[Bool](true.B)
      val x347_rd_x329_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x347_rd_x329_shared_en")
      x347_rd_x329.toSeq.zip(x329_reg.connectRPort(347, x347_rd_x329_banks, x347_rd_x329_ofs, io.sigsIn.backpressure, x347_rd_x329_en.map(_ && x347_rd_x329_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x348 = Wire(Bool()).suggestName("""x348""")
      x348.r := Math.lt(b343, x347_rd_x329, Some(0.4), true.B,"x348").r
      val x349 = Wire(Bool()).suggestName("""x349""")
      x349 := x346 & x348
      val x350_sub = Wire(new FixedPoint(true, 32, 0)).suggestName("""x350_sub""")
      x350_sub.r := Math.sub(b343,x345_rd_x328,Some(1.0), true.B, Truncate, Wrapping, "x350_sub").r
      val x351 = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x351""")
      x299.ready := b344 & b327 & (io.sigsIn.datapathEn) 
      (0 until 1).map{ i => x351(i).r := x299.bits.rdata(i).r }
      val x458 = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("x458_x351_D1") 
      (0 until 1).foreach{i => x458(i).r := getRetimed(x351(i).r, 1.toInt, io.sigsIn.backpressure & true.B)}
      // x352 = VecApply(x458,0)
      val x352_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x352_elem_0""")
      x352_elem_0.r := x458(0).r
      val x459 = Wire(new FixedPoint(true, 32, 0)).suggestName("x459_b326_D1") 
      x459.r := getRetimed(b326.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x447 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x447""")
      x447.r := Math.fma(x459,201L.FP(true, 32, 0),x350_sub,Some(6.0), true.B, "x447").toFixed(x447, "cast_x447").r
      val x460 = Wire(new FloatingPoint(24, 8)).suggestName("x460_x352_elem_0_D6") 
      x460.r := getRetimed(x352_elem_0.r, 6.toInt, io.sigsIn.backpressure & true.B)
      val x461 = Wire(Bool()).suggestName("x461_b344_D7") 
      x461.r := getRetimed(b344.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x462 = Wire(Bool()).suggestName("x462_x349_D7") 
      x462.r := getRetimed(x349.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x463 = Wire(Bool()).suggestName("x463_b327_D7") 
      x463.r := getRetimed(b327.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x356_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x356_wr_ofs = List[UInt](x447.r)
      val x356_wr_en = List[Bool](true.B)
      val x356_wr_data = List[UInt](x460.r)
      x294_matSram_0.connectWPort(356, x356_wr_banks, x356_wr_ofs, x356_wr_data, x356_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(7.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x462 & x461 & x463))
    }
    val module = Module(new x357_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnrolledForeach x357_inr_Foreach **/
