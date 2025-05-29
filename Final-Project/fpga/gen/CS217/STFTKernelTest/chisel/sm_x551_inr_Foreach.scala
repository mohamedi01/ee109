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

/** Hierarchy: x551 -> x552 -> x710 **/
/** BEGIN None x551_inr_Foreach **/
class x551_inr_Foreach_kernel(
  list_b515: List[Bool],
  list_b514: List[FixedPoint],
  list_x470_imag2D_0: List[StandardInterface],
  list_x468_imag_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 3.0.toInt, myName = "x551_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x551_inr_Foreach_iiCtr"))
  
  abstract class x551_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x468_imag_0 = Flipped(new NBufInterface(ModuleParams.getParams("x468_imag_0_p").asInstanceOf[NBufParams] ))
      val in_b514 = Input(new FixedPoint(true, 32, 0))
      val in_x467_real_0 = Flipped(new NBufInterface(ModuleParams.getParams("x467_real_0_p").asInstanceOf[NBufParams] ))
      val in_b515 = Input(Bool())
      val in_x470_imag2D_0 = Flipped(new StandardInterface(ModuleParams.getParams("x470_imag2D_0_p").asInstanceOf[MemParams] ))
      val in_x469_real2D_0 = Flipped(new StandardInterface(ModuleParams.getParams("x469_real2D_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x468_imag_0 = {io.in_x468_imag_0} ; io.in_x468_imag_0 := DontCare
    def b514 = {io.in_b514} 
    def x467_real_0 = {io.in_x467_real_0} ; io.in_x467_real_0 := DontCare
    def b515 = {io.in_b515} 
    def x470_imag2D_0 = {io.in_x470_imag2D_0} ; io.in_x470_imag2D_0 := DontCare
    def x469_real2D_0 = {io.in_x469_real2D_0} ; io.in_x469_real2D_0 := DontCare
  }
  def connectWires0(module: x551_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x468_imag_0.connectLedger(module.io.in_x468_imag_0)
    module.io.in_b514 <> b514
    x467_real_0.connectLedger(module.io.in_x467_real_0)
    module.io.in_b515 <> b515
    x470_imag2D_0.connectLedger(module.io.in_x470_imag2D_0)
    x469_real2D_0.connectLedger(module.io.in_x469_real2D_0)
  }
  val b515 = list_b515(0)
  val b514 = list_b514(0)
  val x470_imag2D_0 = list_x470_imag2D_0(0)
  val x469_real2D_0 = list_x470_imag2D_0(1)
  val x468_imag_0 = list_x468_imag_0(0)
  val x467_real_0 = list_x468_imag_0(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x551_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x551_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x551_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x551_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x551_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x551_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x551_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X551_instrctr, cycles_x551_inr_Foreach.io.count, iters_x551_inr_Foreach.io.count, 0.U, 0.U)
      val b540 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b540.suggestName("b540")
      val b541 = ~io.sigsIn.cchainOutputs.head.oobs(0); b541.suggestName("b541")
      val x542_rd = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x542_rd""")
      val x542_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x542_rd_ofs = List[UInt](b540.r)
      val x542_rd_en = List[Bool](true.B)
      val x542_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && b541 & b515 ).suggestName("x542_rd_shared_en")
      x542_rd.toSeq.zip(x467_real_0.connectRPort(542, x542_rd_banks, x542_rd_ofs, io.sigsIn.backpressure, x542_rd_en.map(_ && x542_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x543 = VecApply(x542,0)
      val x543_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x543_elem_0""")
      x543_elem_0.r := x542_rd(0).r
      val x545 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x545""")
      x545.r := Math.arith_left_shift(b540, 1, Some(0.2), true.B,"x545").r
      val x546_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x546_sum""")
      x546_sum.r := Math.add(x545,b514,Some(1.0), true.B, Truncate, Wrapping, "x546_sum").r
      val x724 = Wire(Bool()).suggestName("x724_b515_D2") 
      x724.r := getRetimed(b515.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x725 = Wire(new FixedPoint(true, 32, 0)).suggestName("x725_x546_sum_D1") 
      x725.r := getRetimed(x546_sum.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x726 = Wire(Bool()).suggestName("x726_b541_D2") 
      x726.r := getRetimed(b541.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x547_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x547_wr_ofs = List[UInt](x725.r)
      val x547_wr_en = List[Bool](true.B)
      val x547_wr_data = List[UInt](x543_elem_0.r)
      x469_real2D_0.connectWPort(547, x547_wr_banks, x547_wr_ofs, x547_wr_data, x547_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x726 & x724))
      val x548_rd = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x548_rd""")
      val x548_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x548_rd_ofs = List[UInt](b540.r)
      val x548_rd_en = List[Bool](true.B)
      val x548_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && b541 & b515 ).suggestName("x548_rd_shared_en")
      x548_rd.toSeq.zip(x468_imag_0.connectRPort(548, x548_rd_banks, x548_rd_ofs, io.sigsIn.backpressure, x548_rd_en.map(_ && x548_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x549 = VecApply(x548,0)
      val x549_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x549_elem_0""")
      x549_elem_0.r := x548_rd(0).r
      val x550_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x550_wr_ofs = List[UInt](x725.r)
      val x550_wr_en = List[Bool](true.B)
      val x550_wr_data = List[UInt](x549_elem_0.r)
      x470_imag2D_0.connectWPort(550, x550_wr_banks, x550_wr_ofs, x550_wr_data, x550_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x726 & x724))
      x467_real_0.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 1)
      x468_imag_0.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 1)
    }
    val module = Module(new x551_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x551_inr_Foreach **/
