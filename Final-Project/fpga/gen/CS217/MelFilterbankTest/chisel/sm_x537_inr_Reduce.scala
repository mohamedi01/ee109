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

/** Hierarchy: x537 -> x541 -> x614 **/
/** BEGIN None x537_inr_Reduce **/
class x537_inr_Reduce_kernel(
  list_b514: List[FixedPoint],
  list_x517_sum_1: List[NBufInterface],
  list_b515: List[Bool],
  list_x516_sum_0: List[FixFMAAccumBundle],
  list_x411_matSRAM_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 13.159.toInt, myName = "x537_inr_Reduce_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x537_inr_Reduce_iiCtr"))
  
  abstract class x537_inr_Reduce_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b514 = Input(new FixedPoint(true, 32, 0))
      val in_x411_matSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x411_matSRAM_0_p").asInstanceOf[MemParams] ))
      val in_b515 = Input(Bool())
      val in_x412_vecSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x412_vecSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x516_sum_0 = Flipped(new FixFMAAccumBundle(1, 16, 8))
      val in_x517_sum_1 = Flipped(new NBufInterface(ModuleParams.getParams("x517_sum_1_p").asInstanceOf[NBufParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def b514 = {io.in_b514} 
    def x411_matSRAM_0 = {io.in_x411_matSRAM_0} ; io.in_x411_matSRAM_0 := DontCare
    def b515 = {io.in_b515} 
    def x412_vecSRAM_0 = {io.in_x412_vecSRAM_0} ; io.in_x412_vecSRAM_0 := DontCare
    def x516_sum_0 = {io.in_x516_sum_0} ; io.in_x516_sum_0 := DontCare
    def x517_sum_1 = {io.in_x517_sum_1} ; io.in_x517_sum_1 := DontCare
  }
  def connectWires0(module: x537_inr_Reduce_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b514 <> b514
    x411_matSRAM_0.connectLedger(module.io.in_x411_matSRAM_0)
    module.io.in_b515 <> b515
    x412_vecSRAM_0.connectLedger(module.io.in_x412_vecSRAM_0)
    x516_sum_0.connectLedger(module.io.in_x516_sum_0)
    x517_sum_1.connectLedger(module.io.in_x517_sum_1)
  }
  val b514 = list_b514(0)
  val x517_sum_1 = list_x517_sum_1(0)
  val b515 = list_b515(0)
  val x516_sum_0 = list_x516_sum_0(0)
  val x411_matSRAM_0 = list_x411_matSRAM_0(0)
  val x412_vecSRAM_0 = list_x411_matSRAM_0(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x537_inr_Reduce")
    implicit val stack = ControllerStack.stack.toList
    class x537_inr_Reduce_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x537_inr_Reduce_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x537_inr_Reduce = Module(new InstrumentationCounter())
      val iters_x537_inr_Reduce = Module(new InstrumentationCounter())
      cycles_x537_inr_Reduce.io.enable := io.sigsIn.baseEn
      iters_x537_inr_Reduce.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X537_instrctr, cycles_x537_inr_Reduce.io.count, iters_x537_inr_Reduce.io.count, 0.U, 0.U)
      val b520 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b520.suggestName("b520")
      val b521 = ~io.sigsIn.cchainOutputs.head.oobs(0); b521.suggestName("b521")
      val x610 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x610""")
      x610.r := Math.arith_left_shift(b514, 1, Some(0.2), true.B,"x610").r
      val x611_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x611_sum""")
      x611_sum.r := Math.add(x610,b514,Some(1.0), true.B, Truncate, Wrapping, "x611_sum").r
      val x633 = Wire(new FixedPoint(true, 32, 0)).suggestName("x633_b520_D1") 
      x633.r := getRetimed(b520.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x524_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x524_sum""")
      x524_sum.r := Math.add(x611_sum,x633,Some(1.0), true.B, Truncate, Wrapping, "x524_sum").r
      val x634 = Wire(Bool()).suggestName("x634_b521_D2") 
      x634.r := getRetimed(b521.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x635 = Wire(Bool()).suggestName("x635_b515_D2") 
      x635.r := getRetimed(b515.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x525_rd = Wire(Vec(1, new FixedPoint(true, 16, 8))).suggestName("""x525_rd""")
      val x525_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x525_rd_ofs = List[UInt](x524_sum.r)
      val x525_rd_en = List[Bool](true.B)
      val x525_rd_shared_en = ((io.sigsIn.forwardpressure).DS(2.2.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.2.toInt, rr, io.sigsIn.backpressure & true.B) && x634 & x635 ).suggestName("x525_rd_shared_en")
      x525_rd.toSeq.zip(x411_matSRAM_0.connectRPort(525, x525_rd_banks, x525_rd_ofs, io.sigsIn.backpressure, x525_rd_en.map(_ && x525_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x526 = VecApply(x525,0)
      val x526_elem_0 = Wire(new FixedPoint(true, 16, 8)).suggestName("""x526_elem_0""")
      x526_elem_0.r := x525_rd(0).r
      val x527_rd = Wire(Vec(1, new FixedPoint(true, 16, 8))).suggestName("""x527_rd""")
      val x527_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x527_rd_ofs = List[UInt](b520.r)
      val x527_rd_en = List[Bool](true.B)
      val x527_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && b521 & b515 ).suggestName("x527_rd_shared_en")
      x527_rd.toSeq.zip(x412_vecSRAM_0.connectRPort(527, x527_rd_banks, x527_rd_ofs, io.sigsIn.backpressure, x527_rd_en.map(_ && x527_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x528 = VecApply(x527,0)
      val x528_elem_0 = Wire(new FixedPoint(true, 16, 8)).suggestName("""x528_elem_0""")
      x528_elem_0.r := x527_rd(0).r
      val x532 = Wire(Bool()).suggestName("""x532""")
      x532.r := Math.eql(b520, 0L.FP(true, 32, 0), Some(0.2), true.B,"x532").r
      val x636 = Wire(new FixedPoint(true, 16, 8)).suggestName("x636_x528_elem_0_D2") 
      x636.r := getRetimed(x528_elem_0.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x637 = Wire(Bool()).suggestName("x637_x532_D4") 
      x637.r := getRetimed(x532.r, 4.toInt, io.sigsIn.backpressure & true.B)
      x516_sum_0.connectWPort(0, x526_elem_0.r, x636.r, true.B && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(4.2.toInt, rr, io.sigsIn.backpressure & true.B), (io.sigsIn.ctrDone).DS(4.2.toInt, rr, io.sigsIn.backpressure & true.B), x637)
      val x615 = Wire(new FixedPoint(true, 16, 8)).suggestName("""x615""")
      x615.r := x516_sum_0.output
      val x535_wr_x517_banks = List[UInt]()
      val x535_wr_x517_ofs = List[UInt]()
      val x535_wr_x517_en = List[Bool](true.B)
      val x535_wr_x517_data = List[UInt](x615.r)
      x517_sum_1.connectWPort(535, x535_wr_x517_banks, x535_wr_x517_ofs, x535_wr_x517_data, x535_wr_x517_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(12.159.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      x517_sum_1.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 0)
    }
    val module = Module(new x537_inr_Reduce_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledReduce x537_inr_Reduce **/
