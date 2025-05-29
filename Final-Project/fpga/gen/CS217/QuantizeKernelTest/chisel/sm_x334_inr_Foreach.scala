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

/** Hierarchy: x334 -> x335 -> x340 -> x341 -> x213 **/
/** BEGIN None x334_inr_Foreach **/
class x334_inr_Foreach_kernel(
  list_x312_reg: List[StandardInterface],
  list_x310: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 2.6.toInt, myName = "x334_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x334_inr_Foreach_iiCtr"))
  
  abstract class x334_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x312_reg = Flipped(new StandardInterface(ModuleParams.getParams("x312_reg_p").asInstanceOf[MemParams] ))
      val in_x310 = Decoupled(new AppStoreData(ModuleParams.getParams("x310_p").asInstanceOf[(Int,Int)] ))
      val in_x254_outSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x254_outSRAM_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x312_reg = {io.in_x312_reg} ; io.in_x312_reg := DontCare
    def x310 = {io.in_x310} 
    def x254_outSRAM_0 = {io.in_x254_outSRAM_0} ; io.in_x254_outSRAM_0 := DontCare
  }
  def connectWires0(module: x334_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x312_reg.connectLedger(module.io.in_x312_reg)
    module.io.in_x310 <> x310
    x254_outSRAM_0.connectLedger(module.io.in_x254_outSRAM_0)
  }
  val x312_reg = list_x312_reg(0)
  val x254_outSRAM_0 = list_x312_reg(1)
  val x310 = list_x310(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x334_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x334_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x334_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x334_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x334_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x334_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x334_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x334_inr_Foreach = Module(new InstrumentationCounter())
      val idles_x334_inr_Foreach = Module(new InstrumentationCounter())
      stalls_x334_inr_Foreach.io.enable := io.sigsIn.baseEn & ~(x310.ready)
      idles_x334_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X334_instrctr, cycles_x334_inr_Foreach.io.count, iters_x334_inr_Foreach.io.count, stalls_x334_inr_Foreach.io.count, idles_x334_inr_Foreach.io.count)
      val b324 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b324.suggestName("b324")
      val b325 = ~io.sigsIn.cchainOutputs.head.oobs(0); b325.suggestName("b325")
      val x326 = Wire(Bool()).suggestName("""x326""")
      val ensig0 = Wire(Bool())
      ensig0 := x310.ready
      x326.r := Math.lte(0L.FP(true, 32, 0), b324, Some(0.4), ensig0,"x326").r
      val x327_rd_x312 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x327_rd_x312""")
      val x327_rd_x312_banks = List[UInt]()
      val x327_rd_x312_ofs = List[UInt]()
      val x327_rd_x312_en = List[Bool](true.B)
      val x327_rd_x312_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x327_rd_x312_shared_en")
      x327_rd_x312.toSeq.zip(x312_reg.connectRPort(327, x327_rd_x312_banks, x327_rd_x312_ofs, io.sigsIn.backpressure, x327_rd_x312_en.map(_ && x327_rd_x312_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x328 = Wire(Bool()).suggestName("""x328""")
      x328.r := Math.lt(b324, x327_rd_x312, Some(0.4), ensig0,"x328").r
      val x329 = Wire(Bool()).suggestName("""x329""")
      x329 := x326 & x328
      val x330_rd = Wire(Vec(1, new FixedPoint(true, 16, 8))).suggestName("""x330_rd""")
      val x330_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x330_rd_ofs = List[UInt](b324.r)
      val x330_rd_en = List[Bool](true.B)
      val x330_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.6.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.6.toInt, rr, io.sigsIn.backpressure & true.B) && x329 & b325 ).suggestName("x330_rd_shared_en")
      x330_rd.toSeq.zip(x254_outSRAM_0.connectRPort(330, x330_rd_banks, x330_rd_ofs, io.sigsIn.backpressure, x330_rd_en.map(_ && x330_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x331 = VecApply(x330,0)
      val x331_elem_0 = Wire(new FixedPoint(true, 16, 8)).suggestName("""x331_elem_0""")
      x331_elem_0.r := x330_rd(0).r
      val x384 = Wire(Bool()).suggestName("x384_x329_D2") 
      x384.r := getRetimed(x329.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x332_tuple = Wire(UInt(25.W)).suggestName("""x332_tuple""")
      x332_tuple.r := ConvAndCat(x384,x331_elem_0.r)
      val x385 = Wire(Bool()).suggestName("x385_b325_D2") 
      x385.r := getRetimed(b325.r, 2.toInt, io.sigsIn.backpressure & true.B)
      x310.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.6.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x385 & io.sigsIn.backpressure
      x310.bits.wdata(0) := x332_tuple(23,0)
      x310.bits.wstrb := x332_tuple(24,24)
    }
    val module = Module(new x334_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x334_inr_Foreach **/
