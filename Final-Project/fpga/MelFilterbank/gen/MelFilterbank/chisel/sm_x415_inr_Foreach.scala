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

/** Hierarchy: x415 -> x451 -> x452 -> x453 **/
/** BEGIN None x415_inr_Foreach **/
class x415_inr_Foreach_kernel(
  list_b398: List[Bool],
  list_b397: List[FixedPoint],
  list_x399: List[String],
  list_x295_vecSram_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 27.0.toInt, myName = "x415_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x415_inr_Foreach_iiCtr"))
  
  abstract class x415_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b397 = Input(new FixedPoint(true, 32, 0))
      val in_b398 = Input(Bool())
      val in_x399 = String
      val in_x295_vecSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x295_vecSram_0_p").asInstanceOf[MemParams] ))
      val in_x294_matSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x294_matSram_0_p").asInstanceOf[MemParams] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def b397 = {io.in_b397} 
    def b398 = {io.in_b398} 
    def x399 = {io.in_x399} 
    def x295_vecSram_0 = {io.in_x295_vecSram_0} ; io.in_x295_vecSram_0 := DontCare
    def x294_matSram_0 = {io.in_x294_matSram_0} ; io.in_x294_matSram_0 := DontCare
  }
  def connectWires0(module: x415_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b397 <> b397
    module.io.in_b398 <> b398
    module.io.in_x399 <> x399
    x295_vecSram_0.connectLedger(module.io.in_x295_vecSram_0)
    x294_matSram_0.connectLedger(module.io.in_x294_matSram_0)
  }
  val b398 = list_b398(0)
  val b397 = list_b397(0)
  val x399 = list_x399(0)
  val x295_vecSram_0 = list_x295_vecSram_0(0)
  val x294_matSram_0 = list_x295_vecSram_0(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x415_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x415_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x415_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val b402 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b402.suggestName("b402")
      val b403 = ~io.sigsIn.cchainOutputs.head.oobs(0); b403.suggestName("b403")
      val x404 = "" 
      val x448 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x448""")
      x448.r := Math.fma(b397,201L.FP(true, 32, 0),b402,Some(6.0), true.B, "x448").toFixed(x448, "cast_x448").r
      val x468 = Wire(Bool()).suggestName("x468_b398_D6") 
      x468.r := getRetimed(b398.r, 6.toInt, io.sigsIn.backpressure & true.B)
      val x469 = Wire(Bool()).suggestName("x469_b403_D6") 
      x469.r := getRetimed(b403.r, 6.toInt, io.sigsIn.backpressure & true.B)
      val x408_rd = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x408_rd""")
      val x408_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x408_rd_ofs = List[UInt](x448.r)
      val x408_rd_en = List[Bool](true.B)
      val x408_rd_shared_en = ((io.sigsIn.forwardpressure).DS(6.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(6.0.toInt, rr, io.sigsIn.backpressure & true.B) && x469 & x468 ).suggestName("x408_rd_shared_en")
      x408_rd.toSeq.zip(x294_matSram_0.connectRPort(408, x408_rd_banks, x408_rd_ofs, io.sigsIn.backpressure, x408_rd_en.map(_ && x408_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x409 = VecApply(x408,0)
      val x409_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x409_elem_0""")
      x409_elem_0.r := x408_rd(0).r
      val x410_rd = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x410_rd""")
      val x410_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x410_rd_ofs = List[UInt](b402.r)
      val x410_rd_en = List[Bool](true.B)
      val x410_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && b403 & b398 ).suggestName("x410_rd_shared_en")
      x410_rd.toSeq.zip(x295_vecSram_0.connectRPort(410, x410_rd_banks, x410_rd_ofs, io.sigsIn.backpressure, x410_rd_en.map(_ && x410_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x411 = VecApply(x410,0)
      val x411_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x411_elem_0""")
      x411_elem_0.r := x410_rd(0).r
      val x470 = Wire(new FloatingPoint(24, 8)).suggestName("x470_x411_elem_0_D6") 
      x470.r := getRetimed(x411_elem_0.r, 6.toInt, io.sigsIn.backpressure & true.B)
      val x471 = Wire(new FloatingPoint(24, 8)).suggestName("x471_x404_D8") 
      x471.r := getRetimed(x404.r, 8.toInt, io.sigsIn.backpressure & true.B)
      val x449 = Wire(new FloatingPoint(24, 8)).suggestName("""x449""")
      x449.r := Math.fma(x409_elem_0,x470,x471,Some(19.0), true.B,"x449").r
      val x414 = "" 
    }
    val module = Module(new x415_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnrolledForeach x415_inr_Foreach **/
