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

/** Hierarchy: x361 -> x362 -> x367 -> x368 -> x375 **/
/** BEGIN None x361_inr_Foreach **/
class x361_inr_Foreach_kernel(
  list_x252_outSram_0: List[StandardInterface],
  list_x337: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 2.6.toInt, myName = "x361_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x361_inr_Foreach_iiCtr"))
  
  abstract class x361_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x252_outSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x252_outSram_0_p").asInstanceOf[MemParams] ))
      val in_x339_reg = Flipped(new StandardInterface(ModuleParams.getParams("x339_reg_p").asInstanceOf[MemParams] ))
      val in_x337 = Decoupled(new AppStoreData(ModuleParams.getParams("x337_p").asInstanceOf[(Int,Int)] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x252_outSram_0 = {io.in_x252_outSram_0} ; io.in_x252_outSram_0 := DontCare
    def x339_reg = {io.in_x339_reg} ; io.in_x339_reg := DontCare
    def x337 = {io.in_x337} 
  }
  def connectWires0(module: x361_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x252_outSram_0.connectLedger(module.io.in_x252_outSram_0)
    x339_reg.connectLedger(module.io.in_x339_reg)
    module.io.in_x337 <> x337
  }
  val x252_outSram_0 = list_x252_outSram_0(0)
  val x339_reg = list_x252_outSram_0(1)
  val x337 = list_x337(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x361_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x361_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x361_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val b351 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b351.suggestName("b351")
      val b352 = ~io.sigsIn.cchainOutputs.head.oobs(0); b352.suggestName("b352")
      val x353 = Wire(Bool()).suggestName("""x353""")
      val ensig0 = Wire(Bool())
      ensig0 := x337.ready
      x353.r := Math.lte(0L.FP(true, 32, 0), b351, Some(0.4), ensig0,"x353").r
      val x354_rd_x339 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x354_rd_x339""")
      val x354_rd_x339_banks = List[UInt]()
      val x354_rd_x339_ofs = List[UInt]()
      val x354_rd_x339_en = List[Bool](true.B)
      val x354_rd_x339_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x354_rd_x339_shared_en")
      x354_rd_x339.toSeq.zip(x339_reg.connectRPort(354, x354_rd_x339_banks, x354_rd_x339_ofs, io.sigsIn.backpressure, x354_rd_x339_en.map(_ && x354_rd_x339_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x355 = Wire(Bool()).suggestName("""x355""")
      x355.r := Math.lt(b351, x354_rd_x339, Some(0.4), ensig0,"x355").r
      val x356 = Wire(Bool()).suggestName("""x356""")
      x356 := x353 & x355
      val x357_rd = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x357_rd""")
      val x357_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x357_rd_ofs = List[UInt](b351.r)
      val x357_rd_en = List[Bool](true.B)
      val x357_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.6.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.6.toInt, rr, io.sigsIn.backpressure & true.B) && x356 & b352 ).suggestName("x357_rd_shared_en")
      x357_rd.toSeq.zip(x252_outSram_0.connectRPort(357, x357_rd_banks, x357_rd_ofs, io.sigsIn.backpressure, x357_rd_en.map(_ && x357_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x358 = VecApply(x357,0)
      val x358_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x358_elem_0""")
      x358_elem_0.r := x357_rd(0).r
      val x387 = Wire(Bool()).suggestName("x387_x356_D2") 
      x387.r := getRetimed(x356.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x359_tuple = Wire(UInt(33.W)).suggestName("""x359_tuple""")
      x359_tuple.r := ConvAndCat(x387,x358_elem_0.r)
      val x388 = Wire(Bool()).suggestName("x388_b352_D2") 
      x388.r := getRetimed(b352.r, 2.toInt, io.sigsIn.backpressure & true.B)
      x337.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.6.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x388 & io.sigsIn.backpressure
      x337.bits.wdata(0) := x359_tuple(31,0)
      x337.bits.wstrb := x359_tuple(32,32)
    }
    val module = Module(new x361_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnrolledForeach x361_inr_Foreach **/
