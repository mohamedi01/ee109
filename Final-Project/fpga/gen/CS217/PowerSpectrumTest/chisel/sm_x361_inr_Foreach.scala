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

/** Hierarchy: x361 -> x362 -> x363 -> x480 -> x481 **/
/** BEGIN None x361_inr_Foreach **/
class x361_inr_Foreach_kernel(
  list_x331: List[DecoupledIO[AppLoadData]],
  list_x340_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 2.0.toInt, myName = "x361_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x361_inr_Foreach_iiCtr"))
  
  abstract class x361_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x340_reg = Flipped(new StandardInterface(ModuleParams.getParams("x340_reg_p").asInstanceOf[MemParams] ))
      val in_x331 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x331_p").asInstanceOf[(Int, Int)] )))
      val in_x326_realSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x326_realSRAM_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x340_reg = {io.in_x340_reg} ; io.in_x340_reg := DontCare
    def x331 = {io.in_x331} 
    def x326_realSRAM_0 = {io.in_x326_realSRAM_0} ; io.in_x326_realSRAM_0 := DontCare
  }
  def connectWires0(module: x361_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x340_reg.connectLedger(module.io.in_x340_reg)
    module.io.in_x331 <> x331
    x326_realSRAM_0.connectLedger(module.io.in_x326_realSRAM_0)
  }
  val x331 = list_x331(0)
  val x340_reg = list_x340_reg(0)
  val x326_realSRAM_0 = list_x340_reg(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x361_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x361_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x361_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x361_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x361_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x361_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x361_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x361_inr_Foreach = Module(new InstrumentationCounter())
      val idles_x361_inr_Foreach = Module(new InstrumentationCounter())
      stalls_x361_inr_Foreach.io.enable := io.sigsIn.baseEn & ~(true.B)
      idles_x361_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((x331.valid) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X361_instrctr, cycles_x361_inr_Foreach.io.count, iters_x361_inr_Foreach.io.count, stalls_x361_inr_Foreach.io.count, idles_x361_inr_Foreach.io.count)
      val b352 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b352.suggestName("b352")
      val b353 = ~io.sigsIn.cchainOutputs.head.oobs(0); b353.suggestName("b353")
      val x354 = Wire(Bool()).suggestName("""x354""")
      x354.r := Math.lte(0L.FP(true, 32, 0), b352, Some(0.4), true.B,"x354").r
      val x355_rd_x340 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x355_rd_x340""")
      val x355_rd_x340_banks = List[UInt]()
      val x355_rd_x340_ofs = List[UInt]()
      val x355_rd_x340_en = List[Bool](true.B)
      val x355_rd_x340_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x355_rd_x340_shared_en")
      x355_rd_x340.toSeq.zip(x340_reg.connectRPort(355, x355_rd_x340_banks, x355_rd_x340_ofs, io.sigsIn.backpressure, x355_rd_x340_en.map(_ && x355_rd_x340_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x356 = Wire(Bool()).suggestName("""x356""")
      x356.r := Math.lt(b352, x355_rd_x340, Some(0.4), true.B,"x356").r
      val x357 = Wire(Bool()).suggestName("""x357""")
      x357 := x354 & x356
      val x358 = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x358""")
      x331.ready := b353 & (io.sigsIn.datapathEn) 
      (0 until 1).map{ i => x358(i).r := x331.bits.rdata(i).r }
      val x482 = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("x482_x358_D1") 
      (0 until 1).foreach{i => x482(i).r := getRetimed(x358(i).r, 1.toInt, io.sigsIn.backpressure & true.B)}
      // x359 = VecApply(x482,0)
      val x359_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x359_elem_0""")
      x359_elem_0.r := x482(0).r
      val x483 = Wire(Bool()).suggestName("x483_b353_D1") 
      x483.r := getRetimed(b353.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x484 = Wire(Bool()).suggestName("x484_x357_D1") 
      x484.r := getRetimed(x357.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x485 = Wire(new FixedPoint(true, 32, 0)).suggestName("x485_b352_D1") 
      x485.r := getRetimed(b352.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x360_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x360_wr_ofs = List[UInt](x485.r)
      val x360_wr_en = List[Bool](true.B)
      val x360_wr_data = List[UInt](x359_elem_0.r)
      x326_realSRAM_0.connectWPort(360, x360_wr_banks, x360_wr_ofs, x360_wr_data, x360_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x484 & x483))
    }
    val module = Module(new x361_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x361_inr_Foreach **/
