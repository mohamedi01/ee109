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

/** Hierarchy: x262 -> x267 -> x168 **/
/** BEGIN None x262_inr_Foreach **/
class x262_inr_Foreach_kernel(
  list_x211_bram_0: List[StandardInterface],
  list_x247: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 2.0.toInt, myName = "x262_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x262_inr_Foreach_iiCtr"))
  
  abstract class x262_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x247 = Decoupled(new AppStoreData(ModuleParams.getParams("x247_p").asInstanceOf[(Int,Int)] ))
      val in_x211_bram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x211_bram_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x247 = {io.in_x247} 
    def x211_bram_0 = {io.in_x211_bram_0} ; io.in_x211_bram_0 := DontCare
  }
  def connectWires0(module: x262_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x247 <> x247
    x211_bram_0.connectLedger(module.io.in_x211_bram_0)
  }
  val x211_bram_0 = list_x211_bram_0(0)
  val x247 = list_x247(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x262_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x262_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x262_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x262_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x262_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x262_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x262_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x262_inr_Foreach = Module(new InstrumentationCounter())
      val idles_x262_inr_Foreach = Module(new InstrumentationCounter())
      stalls_x262_inr_Foreach.io.enable := io.sigsIn.baseEn & ~(x247.ready)
      idles_x262_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X262_instrctr, cycles_x262_inr_Foreach.io.count, iters_x262_inr_Foreach.io.count, stalls_x262_inr_Foreach.io.count, idles_x262_inr_Foreach.io.count)
      val b256 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b256.suggestName("b256")
      val b257 = ~io.sigsIn.cchainOutputs.head.oobs(0); b257.suggestName("b257")
      val x258_rd = Wire(Vec(1, new FixedPoint(true, 32, 0))).suggestName("""x258_rd""")
      val x258_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x258_rd_ofs = List[UInt](b256.r)
      val x258_rd_en = List[Bool](true.B)
      val x258_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && b257 ).suggestName("x258_rd_shared_en")
      x258_rd.toSeq.zip(x211_bram_0.connectRPort(258, x258_rd_banks, x258_rd_ofs, io.sigsIn.backpressure, x258_rd_en.map(_ && x258_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x259 = VecApply(x258,0)
      val x259_elem_0 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x259_elem_0""")
      x259_elem_0.r := x258_rd(0).r
      val x260_tuple = Wire(UInt(33.W)).suggestName("""x260_tuple""")
      x260_tuple.r := ConvAndCat(true.B,x259_elem_0.r)
      val x314 = Wire(Bool()).suggestName("x314_b257_D2") 
      x314.r := getRetimed(b257.r, 2.toInt, io.sigsIn.backpressure & true.B)
      x247.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x314 & io.sigsIn.backpressure
      x247.bits.wdata(0) := x260_tuple(31,0)
      x247.bits.wstrb := x260_tuple(32,32)
    }
    val module = Module(new x262_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x262_inr_Foreach **/
