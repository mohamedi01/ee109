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

/** Hierarchy: x203 -> x208 -> x119 **/
/** BEGIN None x203_inr_Foreach **/
class x203_inr_Foreach_kernel(
  list_x161_a_0: List[StandardInterface],
  list_x188: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 2.0.toInt, myName = "x203_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x203_inr_Foreach_iiCtr"))
  
  abstract class x203_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x188 = Decoupled(new AppStoreData(ModuleParams.getParams("x188_p").asInstanceOf[(Int,Int)] ))
      val in_x161_a_0 = Flipped(new StandardInterface(ModuleParams.getParams("x161_a_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x188 = {io.in_x188} 
    def x161_a_0 = {io.in_x161_a_0} ; io.in_x161_a_0 := DontCare
  }
  def connectWires0(module: x203_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x188 <> x188
    x161_a_0.connectLedger(module.io.in_x161_a_0)
  }
  val x161_a_0 = list_x161_a_0(0)
  val x188 = list_x188(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x203_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x203_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x203_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x203_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x203_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x203_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x203_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x203_inr_Foreach = Module(new InstrumentationCounter())
      val idles_x203_inr_Foreach = Module(new InstrumentationCounter())
      stalls_x203_inr_Foreach.io.enable := io.sigsIn.baseEn & ~(x188.ready)
      idles_x203_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X203_instrctr, cycles_x203_inr_Foreach.io.count, iters_x203_inr_Foreach.io.count, stalls_x203_inr_Foreach.io.count, idles_x203_inr_Foreach.io.count)
      val b197 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b197.suggestName("b197")
      val b198 = ~io.sigsIn.cchainOutputs.head.oobs(0); b198.suggestName("b198")
      val x199_rd = Wire(Vec(1, new FixedPoint(true, 32, 0))).suggestName("""x199_rd""")
      val x199_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x199_rd_ofs = List[UInt](b197.r)
      val x199_rd_en = List[Bool](true.B)
      val x199_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && b198 ).suggestName("x199_rd_shared_en")
      x199_rd.toSeq.zip(x161_a_0.connectRPort(199, x199_rd_banks, x199_rd_ofs, io.sigsIn.backpressure, x199_rd_en.map(_ && x199_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x200 = VecApply(x199,0)
      val x200_elem_0 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x200_elem_0""")
      x200_elem_0.r := x199_rd(0).r
      val x201_tuple = Wire(UInt(33.W)).suggestName("""x201_tuple""")
      x201_tuple.r := ConvAndCat(true.B,x200_elem_0.r)
      val x245 = Wire(Bool()).suggestName("x245_b198_D2") 
      x245.r := getRetimed(b198.r, 2.toInt, io.sigsIn.backpressure & true.B)
      x188.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x245 & io.sigsIn.backpressure
      x188.bits.wdata(0) := x201_tuple(31,0)
      x188.bits.wstrb := x201_tuple(32,32)
    }
    val module = Module(new x203_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x203_inr_Foreach **/
