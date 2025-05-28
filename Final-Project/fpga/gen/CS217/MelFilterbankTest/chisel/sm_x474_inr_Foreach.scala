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

/** Hierarchy: x474 -> x475 -> x476 -> x611 -> x612 **/
/** BEGIN None x474_inr_Foreach **/
class x474_inr_Foreach_kernel(
  list_b444: List[Bool],
  list_b443: List[FixedPoint],
  list_x416: List[DecoupledIO[AppLoadData]],
  list_x411_matSRAM_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 3.2.toInt, myName = "x474_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x474_inr_Foreach_iiCtr"))
  
  abstract class x474_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x416 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x416_p").asInstanceOf[(Int, Int)] )))
      val in_b443 = Input(new FixedPoint(true, 32, 0))
      val in_x411_matSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x411_matSRAM_0_p").asInstanceOf[MemParams] ))
      val in_b444 = Input(Bool())
      val in_x445_reg = Flipped(new StandardInterface(ModuleParams.getParams("x445_reg_p").asInstanceOf[MemParams] ))
      val in_x446_reg = Flipped(new StandardInterface(ModuleParams.getParams("x446_reg_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x416 = {io.in_x416} 
    def b443 = {io.in_b443} 
    def x411_matSRAM_0 = {io.in_x411_matSRAM_0} ; io.in_x411_matSRAM_0 := DontCare
    def b444 = {io.in_b444} 
    def x445_reg = {io.in_x445_reg} ; io.in_x445_reg := DontCare
    def x446_reg = {io.in_x446_reg} ; io.in_x446_reg := DontCare
  }
  def connectWires0(module: x474_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x416 <> x416
    module.io.in_b443 <> b443
    x411_matSRAM_0.connectLedger(module.io.in_x411_matSRAM_0)
    module.io.in_b444 <> b444
    x445_reg.connectLedger(module.io.in_x445_reg)
    x446_reg.connectLedger(module.io.in_x446_reg)
  }
  val b444 = list_b444(0)
  val b443 = list_b443(0)
  val x416 = list_x416(0)
  val x411_matSRAM_0 = list_x411_matSRAM_0(0)
  val x445_reg = list_x411_matSRAM_0(1)
  val x446_reg = list_x411_matSRAM_0(2)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x474_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x474_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x474_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x474_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x474_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x474_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x474_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x474_inr_Foreach = Module(new InstrumentationCounter())
      val idles_x474_inr_Foreach = Module(new InstrumentationCounter())
      stalls_x474_inr_Foreach.io.enable := io.sigsIn.baseEn & ~(true.B)
      idles_x474_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((x416.valid) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X474_instrctr, cycles_x474_inr_Foreach.io.count, iters_x474_inr_Foreach.io.count, stalls_x474_inr_Foreach.io.count, idles_x474_inr_Foreach.io.count)
      val b460 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b460.suggestName("b460")
      val b461 = ~io.sigsIn.cchainOutputs.head.oobs(0); b461.suggestName("b461")
      val x462_rd_x445 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x462_rd_x445""")
      val x462_rd_x445_banks = List[UInt]()
      val x462_rd_x445_ofs = List[UInt]()
      val x462_rd_x445_en = List[Bool](true.B)
      val x462_rd_x445_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x462_rd_x445_shared_en")
      x462_rd_x445.toSeq.zip(x445_reg.connectRPort(462, x462_rd_x445_banks, x462_rd_x445_ofs, io.sigsIn.backpressure, x462_rd_x445_en.map(_ && x462_rd_x445_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x463 = Wire(Bool()).suggestName("""x463""")
      x463.r := Math.lte(x462_rd_x445, b460, Some(0.4), true.B,"x463").r
      val x464_rd_x446 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x464_rd_x446""")
      val x464_rd_x446_banks = List[UInt]()
      val x464_rd_x446_ofs = List[UInt]()
      val x464_rd_x446_en = List[Bool](true.B)
      val x464_rd_x446_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x464_rd_x446_shared_en")
      x464_rd_x446.toSeq.zip(x446_reg.connectRPort(464, x464_rd_x446_banks, x464_rd_x446_ofs, io.sigsIn.backpressure, x464_rd_x446_en.map(_ && x464_rd_x446_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x465 = Wire(Bool()).suggestName("""x465""")
      x465.r := Math.lt(b460, x464_rd_x446, Some(0.4), true.B,"x465").r
      val x466 = Wire(Bool()).suggestName("""x466""")
      x466 := x463 & x465
      val x467_sub = Wire(new FixedPoint(true, 32, 0)).suggestName("""x467_sub""")
      x467_sub.r := Math.sub(b460,x462_rd_x445,Some(1.0), true.B, Truncate, Wrapping, "x467_sub").r
      val x468 = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x468""")
      x416.ready := b461 & b444 & (io.sigsIn.datapathEn) 
      (0 until 1).map{ i => x468(i).r := x416.bits.rdata(i).r }
      val x620 = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("x620_x468_D1") 
      (0 until 1).foreach{i => x620(i).r := getRetimed(x468(i).r, 1.toInt, io.sigsIn.backpressure & true.B)}
      // x469 = VecApply(x620,0)
      val x469_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x469_elem_0""")
      x469_elem_0.r := x620(0).r
      val x606 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x606""")
      x606.r := Math.arith_left_shift(b443, 1, Some(0.2), true.B,"x606").r
      val x607_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x607_sum""")
      x607_sum.r := Math.add(x606,b443,Some(1.0), true.B, Truncate, Wrapping, "x607_sum").r
      val x472_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x472_sum""")
      x472_sum.r := Math.add(x607_sum,x467_sub,Some(1.0), true.B, Truncate, Wrapping, "x472_sum").r
      val x621 = Wire(Bool()).suggestName("x621_b461_D2") 
      x621.r := getRetimed(b461.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x622 = Wire(Bool()).suggestName("x622_b444_D2") 
      x622.r := getRetimed(b444.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x623 = Wire(Bool()).suggestName("x623_x466_D2") 
      x623.r := getRetimed(x466.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x624 = Wire(new FloatingPoint(24, 8)).suggestName("x624_x469_elem_0_D1") 
      x624.r := getRetimed(x469_elem_0.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x473_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x473_wr_ofs = List[UInt](x472_sum.r)
      val x473_wr_en = List[Bool](true.B)
      val x473_wr_data = List[UInt](x624.r)
      x411_matSRAM_0.connectWPort(473, x473_wr_banks, x473_wr_ofs, x473_wr_data, x473_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.2.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x623 & x621 & x622))
    }
    val module = Module(new x474_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x474_inr_Foreach **/
