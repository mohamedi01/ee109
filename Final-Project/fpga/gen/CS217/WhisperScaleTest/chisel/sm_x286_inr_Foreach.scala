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

/** Hierarchy: x286 -> x287 -> x288 -> x201 **/
/** BEGIN None x286_inr_Foreach **/
class x286_inr_Foreach_kernel(
  list_x256: List[DecoupledIO[AppLoadData]],
  list_x253_buf_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 2.0.toInt, myName = "x286_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x286_inr_Foreach_iiCtr"))
  
  abstract class x286_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x253_buf_0 = Flipped(new StandardInterface(ModuleParams.getParams("x253_buf_0_p").asInstanceOf[MemParams] ))
      val in_x265_reg = Flipped(new StandardInterface(ModuleParams.getParams("x265_reg_p").asInstanceOf[MemParams] ))
      val in_x256 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x256_p").asInstanceOf[(Int, Int)] )))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x253_buf_0 = {io.in_x253_buf_0} ; io.in_x253_buf_0 := DontCare
    def x265_reg = {io.in_x265_reg} ; io.in_x265_reg := DontCare
    def x256 = {io.in_x256} 
  }
  def connectWires0(module: x286_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x253_buf_0.connectLedger(module.io.in_x253_buf_0)
    x265_reg.connectLedger(module.io.in_x265_reg)
    module.io.in_x256 <> x256
  }
  val x256 = list_x256(0)
  val x253_buf_0 = list_x253_buf_0(0)
  val x265_reg = list_x253_buf_0(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x286_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x286_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x286_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x286_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x286_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x286_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x286_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x286_inr_Foreach = Module(new InstrumentationCounter())
      val idles_x286_inr_Foreach = Module(new InstrumentationCounter())
      stalls_x286_inr_Foreach.io.enable := io.sigsIn.baseEn & ~(true.B)
      idles_x286_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((x256.valid) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X286_instrctr, cycles_x286_inr_Foreach.io.count, iters_x286_inr_Foreach.io.count, stalls_x286_inr_Foreach.io.count, idles_x286_inr_Foreach.io.count)
      val b277 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b277.suggestName("b277")
      val b278 = ~io.sigsIn.cchainOutputs.head.oobs(0); b278.suggestName("b278")
      val x279 = Wire(Bool()).suggestName("""x279""")
      x279.r := Math.lte(0L.FP(true, 32, 0), b277, Some(0.4), true.B,"x279").r
      val x280_rd_x265 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x280_rd_x265""")
      val x280_rd_x265_banks = List[UInt]()
      val x280_rd_x265_ofs = List[UInt]()
      val x280_rd_x265_en = List[Bool](true.B)
      val x280_rd_x265_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x280_rd_x265_shared_en")
      x280_rd_x265.toSeq.zip(x265_reg.connectRPort(280, x280_rd_x265_banks, x280_rd_x265_ofs, io.sigsIn.backpressure, x280_rd_x265_en.map(_ && x280_rd_x265_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x281 = Wire(Bool()).suggestName("""x281""")
      x281.r := Math.lt(b277, x280_rd_x265, Some(0.4), true.B,"x281").r
      val x282 = Wire(Bool()).suggestName("""x282""")
      x282 := x279 & x281
      val x283 = Wire(Vec(1, new FixedPoint(true, 16, 8))).suggestName("""x283""")
      x256.ready := b278 & (io.sigsIn.datapathEn) 
      (0 until 1).map{ i => x283(i).r := x256.bits.rdata(i).r }
      val x374 = Wire(Vec(1, new FixedPoint(true, 16, 8))).suggestName("x374_x283_D1") 
      (0 until 1).foreach{i => x374(i).r := getRetimed(x283(i).r, 1.toInt, io.sigsIn.backpressure & true.B)}
      // x284 = VecApply(x374,0)
      val x284_elem_0 = Wire(new FixedPoint(true, 16, 8)).suggestName("""x284_elem_0""")
      x284_elem_0.r := x374(0).r
      val x375 = Wire(new FixedPoint(true, 32, 0)).suggestName("x375_b277_D1") 
      x375.r := getRetimed(b277.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x376 = Wire(Bool()).suggestName("x376_x282_D1") 
      x376.r := getRetimed(x282.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x377 = Wire(Bool()).suggestName("x377_b278_D1") 
      x377.r := getRetimed(b278.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x285_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x285_wr_ofs = List[UInt](x375.r)
      val x285_wr_en = List[Bool](true.B)
      val x285_wr_data = List[UInt](x284_elem_0.r)
      x253_buf_0.connectWPort(285, x285_wr_banks, x285_wr_ofs, x285_wr_data, x285_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x376 & x377))
    }
    val module = Module(new x286_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x286_inr_Foreach **/
