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

/** Hierarchy: x396 -> x397 -> x398 -> x480 -> x481 **/
/** BEGIN None x396_inr_Foreach **/
class x396_inr_Foreach_kernel(
  list_x366: List[DecoupledIO[AppLoadData]],
  list_x375_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 2.0.toInt, myName = "x396_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x396_inr_Foreach_iiCtr"))
  
  abstract class x396_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x366 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x366_p").asInstanceOf[(Int, Int)] )))
      val in_x375_reg = Flipped(new StandardInterface(ModuleParams.getParams("x375_reg_p").asInstanceOf[MemParams] ))
      val in_x327_imagSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x327_imagSRAM_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x366 = {io.in_x366} 
    def x375_reg = {io.in_x375_reg} ; io.in_x375_reg := DontCare
    def x327_imagSRAM_0 = {io.in_x327_imagSRAM_0} ; io.in_x327_imagSRAM_0 := DontCare
  }
  def connectWires0(module: x396_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x366 <> x366
    x375_reg.connectLedger(module.io.in_x375_reg)
    x327_imagSRAM_0.connectLedger(module.io.in_x327_imagSRAM_0)
  }
  val x366 = list_x366(0)
  val x375_reg = list_x375_reg(0)
  val x327_imagSRAM_0 = list_x375_reg(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x396_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x396_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x396_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x396_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x396_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x396_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x396_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x396_inr_Foreach = Module(new InstrumentationCounter())
      val idles_x396_inr_Foreach = Module(new InstrumentationCounter())
      stalls_x396_inr_Foreach.io.enable := io.sigsIn.baseEn & ~(true.B)
      idles_x396_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((x366.valid) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X396_instrctr, cycles_x396_inr_Foreach.io.count, iters_x396_inr_Foreach.io.count, stalls_x396_inr_Foreach.io.count, idles_x396_inr_Foreach.io.count)
      val b387 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b387.suggestName("b387")
      val b388 = ~io.sigsIn.cchainOutputs.head.oobs(0); b388.suggestName("b388")
      val x389 = Wire(Bool()).suggestName("""x389""")
      x389.r := Math.lte(0L.FP(true, 32, 0), b387, Some(0.4), true.B,"x389").r
      val x390_rd_x375 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x390_rd_x375""")
      val x390_rd_x375_banks = List[UInt]()
      val x390_rd_x375_ofs = List[UInt]()
      val x390_rd_x375_en = List[Bool](true.B)
      val x390_rd_x375_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x390_rd_x375_shared_en")
      x390_rd_x375.toSeq.zip(x375_reg.connectRPort(390, x390_rd_x375_banks, x390_rd_x375_ofs, io.sigsIn.backpressure, x390_rd_x375_en.map(_ && x390_rd_x375_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x391 = Wire(Bool()).suggestName("""x391""")
      x391.r := Math.lt(b387, x390_rd_x375, Some(0.4), true.B,"x391").r
      val x392 = Wire(Bool()).suggestName("""x392""")
      x392 := x389 & x391
      val x393 = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x393""")
      x366.ready := b388 & (io.sigsIn.datapathEn) 
      (0 until 1).map{ i => x393(i).r := x366.bits.rdata(i).r }
      val x486 = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("x486_x393_D1") 
      (0 until 1).foreach{i => x486(i).r := getRetimed(x393(i).r, 1.toInt, io.sigsIn.backpressure & true.B)}
      // x394 = VecApply(x486,0)
      val x394_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x394_elem_0""")
      x394_elem_0.r := x486(0).r
      val x487 = Wire(new FixedPoint(true, 32, 0)).suggestName("x487_b387_D1") 
      x487.r := getRetimed(b387.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x488 = Wire(Bool()).suggestName("x488_b388_D1") 
      x488.r := getRetimed(b388.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x489 = Wire(Bool()).suggestName("x489_x392_D1") 
      x489.r := getRetimed(x392.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x395_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x395_wr_ofs = List[UInt](x487.r)
      val x395_wr_en = List[Bool](true.B)
      val x395_wr_data = List[UInt](x394_elem_0.r)
      x327_imagSRAM_0.connectWPort(395, x395_wr_banks, x395_wr_ofs, x395_wr_data, x395_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x489 & x488))
    }
    val module = Module(new x396_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x396_inr_Foreach **/
