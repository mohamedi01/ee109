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

/** Hierarchy: x411 -> x481 **/
/** BEGIN None x411_inr_Foreach **/
class x411_inr_Foreach_kernel(
  list_x328_outSRAM_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 30.0.toInt, myName = "x411_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x411_inr_Foreach_iiCtr"))
  
  abstract class x411_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x328_outSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x328_outSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x327_imagSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x327_imagSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x326_realSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x326_realSRAM_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x328_outSRAM_0 = {io.in_x328_outSRAM_0} ; io.in_x328_outSRAM_0 := DontCare
    def x327_imagSRAM_0 = {io.in_x327_imagSRAM_0} ; io.in_x327_imagSRAM_0 := DontCare
    def x326_realSRAM_0 = {io.in_x326_realSRAM_0} ; io.in_x326_realSRAM_0 := DontCare
  }
  def connectWires0(module: x411_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x328_outSRAM_0.connectLedger(module.io.in_x328_outSRAM_0)
    x327_imagSRAM_0.connectLedger(module.io.in_x327_imagSRAM_0)
    x326_realSRAM_0.connectLedger(module.io.in_x326_realSRAM_0)
  }
  val x328_outSRAM_0 = list_x328_outSRAM_0(0)
  val x327_imagSRAM_0 = list_x328_outSRAM_0(1)
  val x326_realSRAM_0 = list_x328_outSRAM_0(2)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x411_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x411_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x411_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x411_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x411_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x411_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x411_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X411_instrctr, cycles_x411_inr_Foreach.io.count, iters_x411_inr_Foreach.io.count, 0.U, 0.U)
      val b401 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b401.suggestName("b401")
      val b402 = ~io.sigsIn.cchainOutputs.head.oobs(0); b402.suggestName("b402")
      val x403_rd = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x403_rd""")
      val x403_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x403_rd_ofs = List[UInt](b401.r)
      val x403_rd_en = List[Bool](true.B)
      val x403_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && b402 ).suggestName("x403_rd_shared_en")
      x403_rd.toSeq.zip(x326_realSRAM_0.connectRPort(403, x403_rd_banks, x403_rd_ofs, io.sigsIn.backpressure, x403_rd_en.map(_ && x403_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x404 = VecApply(x403,0)
      val x404_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x404_elem_0""")
      x404_elem_0.r := x403_rd(0).r
      val x406_rd = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x406_rd""")
      val x406_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x406_rd_ofs = List[UInt](b401.r)
      val x406_rd_en = List[Bool](true.B)
      val x406_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && b402 ).suggestName("x406_rd_shared_en")
      x406_rd.toSeq.zip(x327_imagSRAM_0.connectRPort(406, x406_rd_banks, x406_rd_ofs, io.sigsIn.backpressure, x406_rd_en.map(_ && x406_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x407 = VecApply(x406,0)
      val x407_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x407_elem_0""")
      x407_elem_0.r := x406_rd(0).r
      val x408 = Wire(new FloatingPoint(24, 8)).suggestName("""x408""")
      x408.r := Math.fmul(x407_elem_0, x407_elem_0, Some(8.0), true.B,"x408").r
      val x490 = Wire(new FloatingPoint(24, 8)).suggestName("x490_x404_elem_0_D8") 
      x490.r := getRetimed(x404_elem_0.r, 8.toInt, io.sigsIn.backpressure & true.B)
      val x479 = Wire(new FloatingPoint(24, 8)).suggestName("""x479""")
      x479.r := Math.fma(x490,x490,x408,Some(19.0), true.B,"x479").r
      val x491 = Wire(new FixedPoint(true, 32, 0)).suggestName("x491_b401_D29") 
      x491.r := getRetimed(b401.r, 29.toInt, io.sigsIn.backpressure & true.B)
      val x492 = Wire(Bool()).suggestName("x492_b402_D29") 
      x492.r := getRetimed(b402.r, 29.toInt, io.sigsIn.backpressure & true.B)
      val x410_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x410_wr_ofs = List[UInt](x491.r)
      val x410_wr_en = List[Bool](true.B)
      val x410_wr_data = List[UInt](x479.r)
      x328_outSRAM_0.connectWPort(410, x410_wr_banks, x410_wr_ofs, x410_wr_data, x410_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(29.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x492))
    }
    val module = Module(new x411_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectInstrCtrs(instrctrs, module.io.in_instrctrs)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnrolledForeach x411_inr_Foreach **/
