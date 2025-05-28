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

/** Hierarchy: x437 -> x438 -> x443 -> x444 -> x481 **/
/** BEGIN None x437_inr_Foreach **/
class x437_inr_Foreach_kernel(
  list_x328_outSRAM_0: List[StandardInterface],
  list_x413: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 2.6.toInt, myName = "x437_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x437_inr_Foreach_iiCtr"))
  
  abstract class x437_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x328_outSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x328_outSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x413 = Decoupled(new AppStoreData(ModuleParams.getParams("x413_p").asInstanceOf[(Int,Int)] ))
      val in_x415_reg = Flipped(new StandardInterface(ModuleParams.getParams("x415_reg_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x328_outSRAM_0 = {io.in_x328_outSRAM_0} ; io.in_x328_outSRAM_0 := DontCare
    def x413 = {io.in_x413} 
    def x415_reg = {io.in_x415_reg} ; io.in_x415_reg := DontCare
  }
  def connectWires0(module: x437_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x328_outSRAM_0.connectLedger(module.io.in_x328_outSRAM_0)
    module.io.in_x413 <> x413
    x415_reg.connectLedger(module.io.in_x415_reg)
  }
  val x328_outSRAM_0 = list_x328_outSRAM_0(0)
  val x415_reg = list_x328_outSRAM_0(1)
  val x413 = list_x413(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x437_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x437_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x437_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x437_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x437_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x437_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x437_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x437_inr_Foreach = Module(new InstrumentationCounter())
      val idles_x437_inr_Foreach = Module(new InstrumentationCounter())
      stalls_x437_inr_Foreach.io.enable := io.sigsIn.baseEn & ~(x413.ready)
      idles_x437_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X437_instrctr, cycles_x437_inr_Foreach.io.count, iters_x437_inr_Foreach.io.count, stalls_x437_inr_Foreach.io.count, idles_x437_inr_Foreach.io.count)
      val b427 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b427.suggestName("b427")
      val b428 = ~io.sigsIn.cchainOutputs.head.oobs(0); b428.suggestName("b428")
      val x429 = Wire(Bool()).suggestName("""x429""")
      val ensig0 = Wire(Bool())
      ensig0 := x413.ready
      x429.r := Math.lte(0L.FP(true, 32, 0), b427, Some(0.4), ensig0,"x429").r
      val x430_rd_x415 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x430_rd_x415""")
      val x430_rd_x415_banks = List[UInt]()
      val x430_rd_x415_ofs = List[UInt]()
      val x430_rd_x415_en = List[Bool](true.B)
      val x430_rd_x415_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x430_rd_x415_shared_en")
      x430_rd_x415.toSeq.zip(x415_reg.connectRPort(430, x430_rd_x415_banks, x430_rd_x415_ofs, io.sigsIn.backpressure, x430_rd_x415_en.map(_ && x430_rd_x415_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x431 = Wire(Bool()).suggestName("""x431""")
      x431.r := Math.lt(b427, x430_rd_x415, Some(0.4), ensig0,"x431").r
      val x432 = Wire(Bool()).suggestName("""x432""")
      x432 := x429 & x431
      val x433_rd = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x433_rd""")
      val x433_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x433_rd_ofs = List[UInt](b427.r)
      val x433_rd_en = List[Bool](true.B)
      val x433_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.6.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.6.toInt, rr, io.sigsIn.backpressure & true.B) && x432 & b428 ).suggestName("x433_rd_shared_en")
      x433_rd.toSeq.zip(x328_outSRAM_0.connectRPort(433, x433_rd_banks, x433_rd_ofs, io.sigsIn.backpressure, x433_rd_en.map(_ && x433_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x434 = VecApply(x433,0)
      val x434_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x434_elem_0""")
      x434_elem_0.r := x433_rd(0).r
      val x493 = Wire(Bool()).suggestName("x493_x432_D2") 
      x493.r := getRetimed(x432.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x435_tuple = Wire(UInt(33.W)).suggestName("""x435_tuple""")
      x435_tuple.r := ConvAndCat(x493,x434_elem_0.r)
      val x494 = Wire(Bool()).suggestName("x494_b428_D2") 
      x494.r := getRetimed(b428.r, 2.toInt, io.sigsIn.backpressure & true.B)
      x413.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.6.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x494 & io.sigsIn.backpressure
      x413.bits.wdata(0) := x435_tuple(31,0)
      x413.bits.wstrb := x435_tuple(32,32)
    }
    val module = Module(new x437_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x437_inr_Foreach **/
