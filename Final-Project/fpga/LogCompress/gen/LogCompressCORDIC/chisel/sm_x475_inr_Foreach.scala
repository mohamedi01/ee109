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

/** Hierarchy: x475 -> x476 -> x481 -> x482 -> x490 -> x491 **/
/** BEGIN None x475_inr_Foreach **/
class x475_inr_Foreach_kernel(
  list_x448_reg: List[StandardInterface],
  list_x446: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 2.6.toInt, myName = "x475_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x475_inr_Foreach_iiCtr"))
  
  abstract class x475_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x448_reg = Flipped(new StandardInterface(ModuleParams.getParams("x448_reg_p").asInstanceOf[MemParams] ))
      val in_x444_outSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x444_outSram_0_p").asInstanceOf[MemParams] ))
      val in_x446 = Decoupled(new AppStoreData(ModuleParams.getParams("x446_p").asInstanceOf[(Int,Int)] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x448_reg = {io.in_x448_reg} ; io.in_x448_reg := DontCare
    def x444_outSram_0 = {io.in_x444_outSram_0} ; io.in_x444_outSram_0 := DontCare
    def x446 = {io.in_x446} 
  }
  def connectWires0(module: x475_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x448_reg.connectLedger(module.io.in_x448_reg)
    x444_outSram_0.connectLedger(module.io.in_x444_outSram_0)
    module.io.in_x446 <> x446
  }
  val x448_reg = list_x448_reg(0)
  val x444_outSram_0 = list_x448_reg(1)
  val x446 = list_x446(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x475_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x475_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x475_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val b465 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b465.suggestName("b465")
      val b466 = ~io.sigsIn.cchainOutputs.head.oobs(0); b466.suggestName("b466")
      val x467 = Wire(Bool()).suggestName("""x467""")
      val ensig0 = Wire(Bool())
      ensig0 := x446.ready
      x467.r := Math.lte(0L.FP(true, 32, 0), b465, Some(0.4), ensig0,"x467").r
      val x468_rd_x448 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x468_rd_x448""")
      val x468_rd_x448_banks = List[UInt]()
      val x468_rd_x448_ofs = List[UInt]()
      val x468_rd_x448_en = List[Bool](true.B)
      val x468_rd_x448_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x468_rd_x448_shared_en")
      x468_rd_x448.toSeq.zip(x448_reg.connectRPort(468, x468_rd_x448_banks, x468_rd_x448_ofs, io.sigsIn.backpressure, x468_rd_x448_en.map(_ && x468_rd_x448_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x469 = Wire(Bool()).suggestName("""x469""")
      x469.r := Math.lt(b465, x468_rd_x448, Some(0.4), ensig0,"x469").r
      val x470 = Wire(Bool()).suggestName("""x470""")
      x470 := x467 & x469
      val x471_rd = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x471_rd""")
      val x471_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x471_rd_ofs = List[UInt](b465.r)
      val x471_rd_en = List[Bool](true.B)
      val x471_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.6.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.6.toInt, rr, io.sigsIn.backpressure & true.B) && x470 & true.B & b466 ).suggestName("x471_rd_shared_en")
      x471_rd.toSeq.zip(x444_outSram_0.connectRPort(471, x471_rd_banks, x471_rd_ofs, io.sigsIn.backpressure, x471_rd_en.map(_ && x471_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x472 = VecApply(x471,0)
      val x472_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x472_elem_0""")
      x472_elem_0.r := x471_rd(0).r
      val x513 = Wire(Bool()).suggestName("x513_x470_D2") 
      x513.r := getRetimed(x470.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x473_tuple = Wire(UInt(33.W)).suggestName("""x473_tuple""")
      x473_tuple.r := ConvAndCat(x513,x472_elem_0.r)
      val x514 = Wire(Bool()).suggestName("x514_b466_D2") 
      x514.r := getRetimed(b466.r, 2.toInt, io.sigsIn.backpressure & true.B)
      x446.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.6.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x514 & io.sigsIn.backpressure
      x446.bits.wdata(0) := x473_tuple(31,0)
      x446.bits.wstrb := x473_tuple(32,32)
    }
    val module = Module(new x475_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnrolledForeach x475_inr_Foreach **/
