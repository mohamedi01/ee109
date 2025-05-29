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

/** Hierarchy: x392 -> x393 -> x398 -> x399 -> x439 **/
/** BEGIN None x392_inr_Foreach **/
class x392_inr_Foreach_kernel(
  list_x295_out_0: List[StandardInterface],
  list_x368: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 2.6.toInt, myName = "x392_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x392_inr_Foreach_iiCtr"))
  
  abstract class x392_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x295_out_0 = Flipped(new StandardInterface(ModuleParams.getParams("x295_out_0_p").asInstanceOf[MemParams] ))
      val in_x370_reg = Flipped(new StandardInterface(ModuleParams.getParams("x370_reg_p").asInstanceOf[MemParams] ))
      val in_x368 = Decoupled(new AppStoreData(ModuleParams.getParams("x368_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x295_out_0 = {io.in_x295_out_0} ; io.in_x295_out_0 := DontCare
    def x370_reg = {io.in_x370_reg} ; io.in_x370_reg := DontCare
    def x368 = {io.in_x368} 
  }
  def connectWires0(module: x392_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x295_out_0.connectLedger(module.io.in_x295_out_0)
    x370_reg.connectLedger(module.io.in_x370_reg)
    module.io.in_x368 <> x368
  }
  val x295_out_0 = list_x295_out_0(0)
  val x370_reg = list_x295_out_0(1)
  val x368 = list_x368(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x392_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x392_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x392_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x392_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x392_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x392_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x392_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x392_inr_Foreach = Module(new InstrumentationCounter())
      val idles_x392_inr_Foreach = Module(new InstrumentationCounter())
      stalls_x392_inr_Foreach.io.enable := io.sigsIn.baseEn & ~(x368.ready)
      idles_x392_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X392_instrctr, cycles_x392_inr_Foreach.io.count, iters_x392_inr_Foreach.io.count, stalls_x392_inr_Foreach.io.count, idles_x392_inr_Foreach.io.count)
      val b382 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b382.suggestName("b382")
      val b383 = ~io.sigsIn.cchainOutputs.head.oobs(0); b383.suggestName("b383")
      val x384 = Wire(Bool()).suggestName("""x384""")
      val ensig0 = Wire(Bool())
      ensig0 := x368.ready
      x384.r := Math.lte(0L.FP(true, 32, 0), b382, Some(0.4), ensig0,"x384").r
      val x385_rd_x370 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x385_rd_x370""")
      val x385_rd_x370_banks = List[UInt]()
      val x385_rd_x370_ofs = List[UInt]()
      val x385_rd_x370_en = List[Bool](true.B)
      val x385_rd_x370_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x385_rd_x370_shared_en")
      x385_rd_x370.toSeq.zip(x370_reg.connectRPort(385, x385_rd_x370_banks, x385_rd_x370_ofs, io.sigsIn.backpressure, x385_rd_x370_en.map(_ && x385_rd_x370_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x386 = Wire(Bool()).suggestName("""x386""")
      x386.r := Math.lt(b382, x385_rd_x370, Some(0.4), ensig0,"x386").r
      val x387 = Wire(Bool()).suggestName("""x387""")
      x387 := x384 & x386
      val x388_rd = Wire(Vec(1, new FixedPoint(true, 16, 8))).suggestName("""x388_rd""")
      val x388_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x388_rd_ofs = List[UInt](b382.r)
      val x388_rd_en = List[Bool](true.B)
      val x388_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.6.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.6.toInt, rr, io.sigsIn.backpressure & true.B) && x387 & b383 ).suggestName("x388_rd_shared_en")
      x388_rd.toSeq.zip(x295_out_0.connectRPort(388, x388_rd_banks, x388_rd_ofs, io.sigsIn.backpressure, x388_rd_en.map(_ && x388_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x389 = VecApply(x388,0)
      val x389_elem_0 = Wire(new FixedPoint(true, 16, 8)).suggestName("""x389_elem_0""")
      x389_elem_0.r := x388_rd(0).r
      val x451 = Wire(Bool()).suggestName("x451_x387_D2") 
      x451.r := getRetimed(x387.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x390_tuple = Wire(UInt(25.W)).suggestName("""x390_tuple""")
      x390_tuple.r := ConvAndCat(x451,x389_elem_0.r)
      val x452 = Wire(Bool()).suggestName("x452_b383_D2") 
      x452.r := getRetimed(b383.r, 2.toInt, io.sigsIn.backpressure & true.B)
      x368.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.6.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x452 & io.sigsIn.backpressure
      x368.bits.wdata(0) := x390_tuple(23,0)
      x368.bits.wstrb := x390_tuple(24,24)
    }
    val module = Module(new x392_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x392_inr_Foreach **/
