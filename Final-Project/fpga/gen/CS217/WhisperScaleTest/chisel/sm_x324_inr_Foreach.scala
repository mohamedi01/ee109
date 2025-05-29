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

/** Hierarchy: x324 -> x325 -> x330 -> x331 -> x201 **/
/** BEGIN None x324_inr_Foreach **/
class x324_inr_Foreach_kernel(
  list_x253_buf_0: List[StandardInterface],
  list_x300: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 2.6.toInt, myName = "x324_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x324_inr_Foreach_iiCtr"))
  
  abstract class x324_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x253_buf_0 = Flipped(new StandardInterface(ModuleParams.getParams("x253_buf_0_p").asInstanceOf[MemParams] ))
      val in_x302_reg = Flipped(new StandardInterface(ModuleParams.getParams("x302_reg_p").asInstanceOf[MemParams] ))
      val in_x300 = Decoupled(new AppStoreData(ModuleParams.getParams("x300_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x253_buf_0 = {io.in_x253_buf_0} ; io.in_x253_buf_0 := DontCare
    def x302_reg = {io.in_x302_reg} ; io.in_x302_reg := DontCare
    def x300 = {io.in_x300} 
  }
  def connectWires0(module: x324_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x253_buf_0.connectLedger(module.io.in_x253_buf_0)
    x302_reg.connectLedger(module.io.in_x302_reg)
    module.io.in_x300 <> x300
  }
  val x253_buf_0 = list_x253_buf_0(0)
  val x302_reg = list_x253_buf_0(1)
  val x300 = list_x300(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x324_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x324_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x324_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x324_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x324_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x324_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x324_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x324_inr_Foreach = Module(new InstrumentationCounter())
      val idles_x324_inr_Foreach = Module(new InstrumentationCounter())
      stalls_x324_inr_Foreach.io.enable := io.sigsIn.baseEn & ~(x300.ready)
      idles_x324_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X324_instrctr, cycles_x324_inr_Foreach.io.count, iters_x324_inr_Foreach.io.count, stalls_x324_inr_Foreach.io.count, idles_x324_inr_Foreach.io.count)
      val b314 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b314.suggestName("b314")
      val b315 = ~io.sigsIn.cchainOutputs.head.oobs(0); b315.suggestName("b315")
      val x316 = Wire(Bool()).suggestName("""x316""")
      val ensig0 = Wire(Bool())
      ensig0 := x300.ready
      x316.r := Math.lte(0L.FP(true, 32, 0), b314, Some(0.4), ensig0,"x316").r
      val x317_rd_x302 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x317_rd_x302""")
      val x317_rd_x302_banks = List[UInt]()
      val x317_rd_x302_ofs = List[UInt]()
      val x317_rd_x302_en = List[Bool](true.B)
      val x317_rd_x302_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x317_rd_x302_shared_en")
      x317_rd_x302.toSeq.zip(x302_reg.connectRPort(317, x317_rd_x302_banks, x317_rd_x302_ofs, io.sigsIn.backpressure, x317_rd_x302_en.map(_ && x317_rd_x302_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x318 = Wire(Bool()).suggestName("""x318""")
      x318.r := Math.lt(b314, x317_rd_x302, Some(0.4), ensig0,"x318").r
      val x319 = Wire(Bool()).suggestName("""x319""")
      x319 := x316 & x318
      val x320_rd = Wire(Vec(1, new FixedPoint(true, 16, 8))).suggestName("""x320_rd""")
      val x320_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x320_rd_ofs = List[UInt](b314.r)
      val x320_rd_en = List[Bool](true.B)
      val x320_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.6.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.6.toInt, rr, io.sigsIn.backpressure & true.B) && x319 & b315 ).suggestName("x320_rd_shared_en")
      x320_rd.toSeq.zip(x253_buf_0.connectRPort(320, x320_rd_banks, x320_rd_ofs, io.sigsIn.backpressure, x320_rd_en.map(_ && x320_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x321 = VecApply(x320,0)
      val x321_elem_0 = Wire(new FixedPoint(true, 16, 8)).suggestName("""x321_elem_0""")
      x321_elem_0.r := x320_rd(0).r
      val x380 = Wire(Bool()).suggestName("x380_x319_D2") 
      x380.r := getRetimed(x319.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x322_tuple = Wire(UInt(25.W)).suggestName("""x322_tuple""")
      x322_tuple.r := ConvAndCat(x380,x321_elem_0.r)
      val x381 = Wire(Bool()).suggestName("x381_b315_D2") 
      x381.r := getRetimed(b315.r, 2.toInt, io.sigsIn.backpressure & true.B)
      x300.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.6.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x381 & io.sigsIn.backpressure
      x300.bits.wdata(0) := x322_tuple(23,0)
      x300.bits.wstrb := x322_tuple(24,24)
    }
    val module = Module(new x324_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x324_inr_Foreach **/
