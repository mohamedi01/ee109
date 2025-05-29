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

/** Hierarchy: x328 -> x329 -> x330 -> x439 **/
/** BEGIN None x328_inr_Foreach **/
class x328_inr_Foreach_kernel(
  list_x298: List[DecoupledIO[AppLoadData]],
  list_x307_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 2.0.toInt, myName = "x328_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x328_inr_Foreach_iiCtr"))
  
  abstract class x328_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x307_reg = Flipped(new StandardInterface(ModuleParams.getParams("x307_reg_p").asInstanceOf[MemParams] ))
      val in_x298 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x298_p").asInstanceOf[(Int, Int)] )))
      val in_x294_buf_0 = Flipped(new StandardInterface(ModuleParams.getParams("x294_buf_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x307_reg = {io.in_x307_reg} ; io.in_x307_reg := DontCare
    def x298 = {io.in_x298} 
    def x294_buf_0 = {io.in_x294_buf_0} ; io.in_x294_buf_0 := DontCare
  }
  def connectWires0(module: x328_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x307_reg.connectLedger(module.io.in_x307_reg)
    module.io.in_x298 <> x298
    x294_buf_0.connectLedger(module.io.in_x294_buf_0)
  }
  val x298 = list_x298(0)
  val x307_reg = list_x307_reg(0)
  val x294_buf_0 = list_x307_reg(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x328_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x328_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x328_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x328_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x328_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x328_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x328_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x328_inr_Foreach = Module(new InstrumentationCounter())
      val idles_x328_inr_Foreach = Module(new InstrumentationCounter())
      stalls_x328_inr_Foreach.io.enable := io.sigsIn.baseEn & ~(true.B)
      idles_x328_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((x298.valid) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X328_instrctr, cycles_x328_inr_Foreach.io.count, iters_x328_inr_Foreach.io.count, stalls_x328_inr_Foreach.io.count, idles_x328_inr_Foreach.io.count)
      val b319 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b319.suggestName("b319")
      val b320 = ~io.sigsIn.cchainOutputs.head.oobs(0); b320.suggestName("b320")
      val x321 = Wire(Bool()).suggestName("""x321""")
      x321.r := Math.lte(0L.FP(true, 32, 0), b319, Some(0.4), true.B,"x321").r
      val x322_rd_x307 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x322_rd_x307""")
      val x322_rd_x307_banks = List[UInt]()
      val x322_rd_x307_ofs = List[UInt]()
      val x322_rd_x307_en = List[Bool](true.B)
      val x322_rd_x307_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x322_rd_x307_shared_en")
      x322_rd_x307.toSeq.zip(x307_reg.connectRPort(322, x322_rd_x307_banks, x322_rd_x307_ofs, io.sigsIn.backpressure, x322_rd_x307_en.map(_ && x322_rd_x307_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x323 = Wire(Bool()).suggestName("""x323""")
      x323.r := Math.lt(b319, x322_rd_x307, Some(0.4), true.B,"x323").r
      val x324 = Wire(Bool()).suggestName("""x324""")
      x324 := x321 & x323
      val x325 = Wire(Vec(1, new FixedPoint(true, 16, 8))).suggestName("""x325""")
      x298.ready := b320 & (io.sigsIn.datapathEn) 
      (0 until 1).map{ i => x325(i).r := x298.bits.rdata(i).r }
      val x441 = Wire(Vec(1, new FixedPoint(true, 16, 8))).suggestName("x441_x325_D1") 
      (0 until 1).foreach{i => x441(i).r := getRetimed(x325(i).r, 1.toInt, io.sigsIn.backpressure & true.B)}
      // x326 = VecApply(x441,0)
      val x326_elem_0 = Wire(new FixedPoint(true, 16, 8)).suggestName("""x326_elem_0""")
      x326_elem_0.r := x441(0).r
      val x442 = Wire(new FixedPoint(true, 32, 0)).suggestName("x442_b319_D1") 
      x442.r := getRetimed(b319.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x443 = Wire(Bool()).suggestName("x443_x324_D1") 
      x443.r := getRetimed(x324.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x444 = Wire(Bool()).suggestName("x444_b320_D1") 
      x444.r := getRetimed(b320.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x327_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x327_wr_ofs = List[UInt](x442.r)
      val x327_wr_en = List[Bool](true.B)
      val x327_wr_data = List[UInt](x326_elem_0.r)
      x294_buf_0.connectWPort(327, x327_wr_banks, x327_wr_ofs, x327_wr_data, x327_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x443 & x444))
    }
    val module = Module(new x328_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x328_inr_Foreach **/
