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

/** Hierarchy: x204 -> x209 -> x119 **/
/** BEGIN None x204_inr_Foreach **/
class x204_inr_Foreach_kernel(
  list_x161_a_0: List[StandardInterface],
  list_x189: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 2.0.toInt, myName = "x204_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x204_inr_Foreach_iiCtr"))
  
  abstract class x204_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x189 = Decoupled(new AppStoreData(ModuleParams.getParams("x189_p").asInstanceOf[(Int,Int)] ))
      val in_x161_a_0 = Flipped(new StandardInterface(ModuleParams.getParams("x161_a_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x189 = {io.in_x189} 
    def x161_a_0 = {io.in_x161_a_0} ; io.in_x161_a_0 := DontCare
  }
  def connectWires0(module: x204_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x189 <> x189
    x161_a_0.connectLedger(module.io.in_x161_a_0)
  }
  val x161_a_0 = list_x161_a_0(0)
  val x189 = list_x189(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x204_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x204_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x204_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x204_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x204_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x204_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x204_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x204_inr_Foreach = Module(new InstrumentationCounter())
      val idles_x204_inr_Foreach = Module(new InstrumentationCounter())
      stalls_x204_inr_Foreach.io.enable := io.sigsIn.baseEn & ~(x189.ready)
      idles_x204_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X204_instrctr, cycles_x204_inr_Foreach.io.count, iters_x204_inr_Foreach.io.count, stalls_x204_inr_Foreach.io.count, idles_x204_inr_Foreach.io.count)
      val b198 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b198.suggestName("b198")
      val b199 = ~io.sigsIn.cchainOutputs.head.oobs(0); b199.suggestName("b199")
      val x200_rd = Wire(Vec(1, new FixedPoint(true, 32, 0))).suggestName("""x200_rd""")
      val x200_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x200_rd_ofs = List[UInt](b198.r)
      val x200_rd_en = List[Bool](true.B)
      val x200_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && b199 ).suggestName("x200_rd_shared_en")
      x200_rd.toSeq.zip(x161_a_0.connectRPort(200, x200_rd_banks, x200_rd_ofs, io.sigsIn.backpressure, x200_rd_en.map(_ && x200_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x201 = VecApply(x200,0)
      val x201_elem_0 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x201_elem_0""")
      x201_elem_0.r := x200_rd(0).r
      val x202_tuple = Wire(UInt(33.W)).suggestName("""x202_tuple""")
      x202_tuple.r := ConvAndCat(true.B,x201_elem_0.r)
      val x248 = Wire(Bool()).suggestName("x248_b199_D2") 
      x248.r := getRetimed(b199.r, 2.toInt, io.sigsIn.backpressure & true.B)
      x189.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x248 & io.sigsIn.backpressure
      x189.bits.wdata(0) := x202_tuple(31,0)
      x189.bits.wstrb := x202_tuple(32,32)
    }
    val module = Module(new x204_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x204_inr_Foreach **/
