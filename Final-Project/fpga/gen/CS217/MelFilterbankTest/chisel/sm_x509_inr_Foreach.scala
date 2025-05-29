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

/** Hierarchy: x509 -> x510 -> x511 -> x613 -> x614 **/
/** BEGIN None x509_inr_Foreach **/
class x509_inr_Foreach_kernel(
  list_x479: List[DecoupledIO[AppLoadData]],
  list_x488_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 2.0.toInt, myName = "x509_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x509_inr_Foreach_iiCtr"))
  
  abstract class x509_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x479 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x479_p").asInstanceOf[(Int, Int)] )))
      val in_x488_reg = Flipped(new StandardInterface(ModuleParams.getParams("x488_reg_p").asInstanceOf[MemParams] ))
      val in_x412_vecSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x412_vecSRAM_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x479 = {io.in_x479} 
    def x488_reg = {io.in_x488_reg} ; io.in_x488_reg := DontCare
    def x412_vecSRAM_0 = {io.in_x412_vecSRAM_0} ; io.in_x412_vecSRAM_0 := DontCare
  }
  def connectWires0(module: x509_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x479 <> x479
    x488_reg.connectLedger(module.io.in_x488_reg)
    x412_vecSRAM_0.connectLedger(module.io.in_x412_vecSRAM_0)
  }
  val x479 = list_x479(0)
  val x488_reg = list_x488_reg(0)
  val x412_vecSRAM_0 = list_x488_reg(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x509_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x509_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x509_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x509_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x509_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x509_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x509_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x509_inr_Foreach = Module(new InstrumentationCounter())
      val idles_x509_inr_Foreach = Module(new InstrumentationCounter())
      stalls_x509_inr_Foreach.io.enable := io.sigsIn.baseEn & ~(true.B)
      idles_x509_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((x479.valid) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X509_instrctr, cycles_x509_inr_Foreach.io.count, iters_x509_inr_Foreach.io.count, stalls_x509_inr_Foreach.io.count, idles_x509_inr_Foreach.io.count)
      val b500 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b500.suggestName("b500")
      val b501 = ~io.sigsIn.cchainOutputs.head.oobs(0); b501.suggestName("b501")
      val x502 = Wire(Bool()).suggestName("""x502""")
      x502.r := Math.lte(0L.FP(true, 32, 0), b500, Some(0.4), true.B,"x502").r
      val x503_rd_x488 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x503_rd_x488""")
      val x503_rd_x488_banks = List[UInt]()
      val x503_rd_x488_ofs = List[UInt]()
      val x503_rd_x488_en = List[Bool](true.B)
      val x503_rd_x488_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x503_rd_x488_shared_en")
      x503_rd_x488.toSeq.zip(x488_reg.connectRPort(503, x503_rd_x488_banks, x503_rd_x488_ofs, io.sigsIn.backpressure, x503_rd_x488_en.map(_ && x503_rd_x488_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x504 = Wire(Bool()).suggestName("""x504""")
      x504.r := Math.lt(b500, x503_rd_x488, Some(0.4), true.B,"x504").r
      val x505 = Wire(Bool()).suggestName("""x505""")
      x505 := x502 & x504
      val x506 = Wire(Vec(1, new FixedPoint(true, 16, 8))).suggestName("""x506""")
      x479.ready := b501 & (io.sigsIn.datapathEn) 
      (0 until 1).map{ i => x506(i).r := x479.bits.rdata(i).r }
      val x629 = Wire(Vec(1, new FixedPoint(true, 16, 8))).suggestName("x629_x506_D1") 
      (0 until 1).foreach{i => x629(i).r := getRetimed(x506(i).r, 1.toInt, io.sigsIn.backpressure & true.B)}
      // x507 = VecApply(x629,0)
      val x507_elem_0 = Wire(new FixedPoint(true, 16, 8)).suggestName("""x507_elem_0""")
      x507_elem_0.r := x629(0).r
      val x630 = Wire(Bool()).suggestName("x630_b501_D1") 
      x630.r := getRetimed(b501.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x631 = Wire(Bool()).suggestName("x631_x505_D1") 
      x631.r := getRetimed(x505.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x632 = Wire(new FixedPoint(true, 32, 0)).suggestName("x632_b500_D1") 
      x632.r := getRetimed(b500.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x508_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x508_wr_ofs = List[UInt](x632.r)
      val x508_wr_en = List[Bool](true.B)
      val x508_wr_data = List[UInt](x507_elem_0.r)
      x412_vecSRAM_0.connectWPort(508, x508_wr_banks, x508_wr_ofs, x508_wr_data, x508_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x631 & x630))
    }
    val module = Module(new x509_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x509_inr_Foreach **/
