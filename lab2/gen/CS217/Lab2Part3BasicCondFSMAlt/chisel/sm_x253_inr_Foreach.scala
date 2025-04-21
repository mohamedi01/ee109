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

/** Hierarchy: x253 -> x258 -> x164 **/
/** BEGIN None x253_inr_Foreach **/
class x253_inr_Foreach_kernel(
  list_x207_bram_0: List[StandardInterface],
  list_x238: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 2.0.toInt, myName = "x253_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x253_inr_Foreach_iiCtr"))
  
  abstract class x253_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x238 = Decoupled(new AppStoreData(ModuleParams.getParams("x238_p").asInstanceOf[(Int,Int)] ))
      val in_x207_bram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x207_bram_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x238 = {io.in_x238} 
    def x207_bram_0 = {io.in_x207_bram_0} ; io.in_x207_bram_0 := DontCare
  }
  def connectWires0(module: x253_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x238 <> x238
    x207_bram_0.connectLedger(module.io.in_x207_bram_0)
  }
  val x207_bram_0 = list_x207_bram_0(0)
  val x238 = list_x238(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x253_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x253_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x253_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x253_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x253_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x253_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x253_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x253_inr_Foreach = Module(new InstrumentationCounter())
      val idles_x253_inr_Foreach = Module(new InstrumentationCounter())
      stalls_x253_inr_Foreach.io.enable := io.sigsIn.baseEn & ~(x238.ready)
      idles_x253_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X253_instrctr, cycles_x253_inr_Foreach.io.count, iters_x253_inr_Foreach.io.count, stalls_x253_inr_Foreach.io.count, idles_x253_inr_Foreach.io.count)
      val b247 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b247.suggestName("b247")
      val b248 = ~io.sigsIn.cchainOutputs.head.oobs(0); b248.suggestName("b248")
      val x249_rd = Wire(Vec(1, new FixedPoint(true, 32, 0))).suggestName("""x249_rd""")
      val x249_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x249_rd_ofs = List[UInt](b247.r)
      val x249_rd_en = List[Bool](true.B)
      val x249_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && b248 ).suggestName("x249_rd_shared_en")
      x249_rd.toSeq.zip(x207_bram_0.connectRPort(249, x249_rd_banks, x249_rd_ofs, io.sigsIn.backpressure, x249_rd_en.map(_ && x249_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x250 = VecApply(x249,0)
      val x250_elem_0 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x250_elem_0""")
      x250_elem_0.r := x249_rd(0).r
      val x251_tuple = Wire(UInt(33.W)).suggestName("""x251_tuple""")
      x251_tuple.r := ConvAndCat(true.B,x250_elem_0.r)
      val x310 = Wire(Bool()).suggestName("x310_b248_D2") 
      x310.r := getRetimed(b248.r, 2.toInt, io.sigsIn.backpressure & true.B)
      x238.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x310 & io.sigsIn.backpressure
      x238.bits.wdata(0) := x251_tuple(31,0)
      x238.bits.wstrb := x251_tuple(32,32)
    }
    val module = Module(new x253_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x253_inr_Foreach **/
