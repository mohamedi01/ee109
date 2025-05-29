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

/** Hierarchy: x567 -> x568 -> x573 -> x574 -> x614 **/
/** BEGIN None x567_inr_Foreach **/
class x567_inr_Foreach_kernel(
  list_x545_reg: List[StandardInterface],
  list_x543: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 2.6.toInt, myName = "x567_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x567_inr_Foreach_iiCtr"))
  
  abstract class x567_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x545_reg = Flipped(new StandardInterface(ModuleParams.getParams("x545_reg_p").asInstanceOf[MemParams] ))
      val in_x413_outSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x413_outSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x543 = Decoupled(new AppStoreData(ModuleParams.getParams("x543_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x545_reg = {io.in_x545_reg} ; io.in_x545_reg := DontCare
    def x413_outSRAM_0 = {io.in_x413_outSRAM_0} ; io.in_x413_outSRAM_0 := DontCare
    def x543 = {io.in_x543} 
  }
  def connectWires0(module: x567_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x545_reg.connectLedger(module.io.in_x545_reg)
    x413_outSRAM_0.connectLedger(module.io.in_x413_outSRAM_0)
    module.io.in_x543 <> x543
  }
  val x545_reg = list_x545_reg(0)
  val x413_outSRAM_0 = list_x545_reg(1)
  val x543 = list_x543(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x567_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x567_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x567_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x567_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x567_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x567_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x567_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x567_inr_Foreach = Module(new InstrumentationCounter())
      val idles_x567_inr_Foreach = Module(new InstrumentationCounter())
      stalls_x567_inr_Foreach.io.enable := io.sigsIn.baseEn & ~(x543.ready)
      idles_x567_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X567_instrctr, cycles_x567_inr_Foreach.io.count, iters_x567_inr_Foreach.io.count, stalls_x567_inr_Foreach.io.count, idles_x567_inr_Foreach.io.count)
      val b557 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b557.suggestName("b557")
      val b558 = ~io.sigsIn.cchainOutputs.head.oobs(0); b558.suggestName("b558")
      val x559 = Wire(Bool()).suggestName("""x559""")
      val ensig0 = Wire(Bool())
      ensig0 := x543.ready
      x559.r := Math.lte(0L.FP(true, 32, 0), b557, Some(0.4), ensig0,"x559").r
      val x560_rd_x545 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x560_rd_x545""")
      val x560_rd_x545_banks = List[UInt]()
      val x560_rd_x545_ofs = List[UInt]()
      val x560_rd_x545_en = List[Bool](true.B)
      val x560_rd_x545_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x560_rd_x545_shared_en")
      x560_rd_x545.toSeq.zip(x545_reg.connectRPort(560, x560_rd_x545_banks, x560_rd_x545_ofs, io.sigsIn.backpressure, x560_rd_x545_en.map(_ && x560_rd_x545_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x561 = Wire(Bool()).suggestName("""x561""")
      x561.r := Math.lt(b557, x560_rd_x545, Some(0.4), ensig0,"x561").r
      val x562 = Wire(Bool()).suggestName("""x562""")
      x562 := x559 & x561
      val x563_rd = Wire(Vec(1, new FixedPoint(true, 16, 8))).suggestName("""x563_rd""")
      val x563_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x563_rd_ofs = List[UInt](b557.r)
      val x563_rd_en = List[Bool](true.B)
      val x563_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.6.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.6.toInt, rr, io.sigsIn.backpressure & true.B) && x562 & b558 ).suggestName("x563_rd_shared_en")
      x563_rd.toSeq.zip(x413_outSRAM_0.connectRPort(563, x563_rd_banks, x563_rd_ofs, io.sigsIn.backpressure, x563_rd_en.map(_ && x563_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x564 = VecApply(x563,0)
      val x564_elem_0 = Wire(new FixedPoint(true, 16, 8)).suggestName("""x564_elem_0""")
      x564_elem_0.r := x563_rd(0).r
      val x638 = Wire(Bool()).suggestName("x638_x562_D2") 
      x638.r := getRetimed(x562.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x565_tuple = Wire(UInt(25.W)).suggestName("""x565_tuple""")
      x565_tuple.r := ConvAndCat(x638,x564_elem_0.r)
      val x639 = Wire(Bool()).suggestName("x639_b558_D2") 
      x639.r := getRetimed(b558.r, 2.toInt, io.sigsIn.backpressure & true.B)
      x543.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.6.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x639 & io.sigsIn.backpressure
      x543.bits.wdata(0) := x565_tuple(23,0)
      x543.bits.wstrb := x565_tuple(24,24)
    }
    val module = Module(new x567_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x567_inr_Foreach **/
