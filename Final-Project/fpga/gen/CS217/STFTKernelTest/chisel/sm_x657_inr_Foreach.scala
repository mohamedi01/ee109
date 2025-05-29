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

/** Hierarchy: x657 -> x658 -> x663 -> x664 -> x709 -> x710 **/
/** BEGIN None x657_inr_Foreach **/
class x657_inr_Foreach_kernel(
  list_b614: List[FixedPoint],
  list_x616_reg: List[StandardInterface],
  list_x610: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 4.0.toInt, myName = "x657_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x657_inr_Foreach_iiCtr"))
  
  abstract class x657_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b614 = Input(new FixedPoint(true, 32, 0))
      val in_x610 = Decoupled(new AppStoreData(ModuleParams.getParams("x610_p").asInstanceOf[(Int,Int)] ))
      val in_x616_reg = Flipped(new StandardInterface(ModuleParams.getParams("x616_reg_p").asInstanceOf[MemParams] ))
      val in_x617_reg = Flipped(new StandardInterface(ModuleParams.getParams("x617_reg_p").asInstanceOf[MemParams] ))
      val in_x470_imag2D_0 = Flipped(new StandardInterface(ModuleParams.getParams("x470_imag2D_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def b614 = {io.in_b614} 
    def x610 = {io.in_x610} 
    def x616_reg = {io.in_x616_reg} ; io.in_x616_reg := DontCare
    def x617_reg = {io.in_x617_reg} ; io.in_x617_reg := DontCare
    def x470_imag2D_0 = {io.in_x470_imag2D_0} ; io.in_x470_imag2D_0 := DontCare
  }
  def connectWires0(module: x657_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b614 <> b614
    module.io.in_x610 <> x610
    x616_reg.connectLedger(module.io.in_x616_reg)
    x617_reg.connectLedger(module.io.in_x617_reg)
    x470_imag2D_0.connectLedger(module.io.in_x470_imag2D_0)
  }
  val b614 = list_b614(0)
  val x616_reg = list_x616_reg(0)
  val x617_reg = list_x616_reg(1)
  val x470_imag2D_0 = list_x616_reg(2)
  val x610 = list_x610(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x657_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x657_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x657_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x657_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x657_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x657_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x657_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x657_inr_Foreach = Module(new InstrumentationCounter())
      val idles_x657_inr_Foreach = Module(new InstrumentationCounter())
      stalls_x657_inr_Foreach.io.enable := io.sigsIn.baseEn & ~(x610.ready)
      idles_x657_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X657_instrctr, cycles_x657_inr_Foreach.io.count, iters_x657_inr_Foreach.io.count, stalls_x657_inr_Foreach.io.count, idles_x657_inr_Foreach.io.count)
      val b642 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b642.suggestName("b642")
      val b643 = ~io.sigsIn.cchainOutputs.head.oobs(0); b643.suggestName("b643")
      val x644_rd_x616 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x644_rd_x616""")
      val x644_rd_x616_banks = List[UInt]()
      val x644_rd_x616_ofs = List[UInt]()
      val x644_rd_x616_en = List[Bool](true.B)
      val x644_rd_x616_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x644_rd_x616_shared_en")
      x644_rd_x616.toSeq.zip(x616_reg.connectRPort(644, x644_rd_x616_banks, x644_rd_x616_ofs, io.sigsIn.backpressure, x644_rd_x616_en.map(_ && x644_rd_x616_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x645 = Wire(Bool()).suggestName("""x645""")
      val ensig0 = Wire(Bool())
      ensig0 := x610.ready
      x645.r := Math.lte(x644_rd_x616, b642, Some(0.4), ensig0,"x645").r
      val x646_rd_x617 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x646_rd_x617""")
      val x646_rd_x617_banks = List[UInt]()
      val x646_rd_x617_ofs = List[UInt]()
      val x646_rd_x617_en = List[Bool](true.B)
      val x646_rd_x617_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x646_rd_x617_shared_en")
      x646_rd_x617.toSeq.zip(x617_reg.connectRPort(646, x646_rd_x617_banks, x646_rd_x617_ofs, io.sigsIn.backpressure, x646_rd_x617_en.map(_ && x646_rd_x617_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x647 = Wire(Bool()).suggestName("""x647""")
      x647.r := Math.lt(b642, x646_rd_x617, Some(0.4), ensig0,"x647").r
      val x648 = Wire(Bool()).suggestName("""x648""")
      x648 := x645 & x647
      val x649_sub = Wire(new FixedPoint(true, 32, 0)).suggestName("""x649_sub""")
      x649_sub.r := Math.sub(b642,x644_rd_x616,Some(1.0), ensig0, Truncate, Wrapping, "x649_sub").r
      val x651 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x651""")
      x651.r := Math.arith_left_shift(b614, 1, Some(0.2), ensig0,"x651").r
      val x736 = Wire(new FixedPoint(true, 32, 0)).suggestName("x736_x651_D1") 
      x736.r := getRetimed(x651.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x652_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x652_sum""")
      x652_sum.r := Math.add(x736,x649_sub,Some(1.0), ensig0, Truncate, Wrapping, "x652_sum").r
      val x737 = Wire(Bool()).suggestName("x737_b643_D2") 
      x737.r := getRetimed(b643.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x738 = Wire(Bool()).suggestName("x738_x648_D2") 
      x738.r := getRetimed(x648.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x653_rd = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x653_rd""")
      val x653_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x653_rd_ofs = List[UInt](x652_sum.r)
      val x653_rd_en = List[Bool](true.B)
      val x653_rd_shared_en = ((io.sigsIn.forwardpressure).DS(2.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.0.toInt, rr, io.sigsIn.backpressure & true.B) && x738 & x737 ).suggestName("x653_rd_shared_en")
      x653_rd.toSeq.zip(x470_imag2D_0.connectRPort(653, x653_rd_banks, x653_rd_ofs, io.sigsIn.backpressure, x653_rd_en.map(_ && x653_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x654 = VecApply(x653,0)
      val x654_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x654_elem_0""")
      x654_elem_0.r := x653_rd(0).r
      val x739 = Wire(Bool()).suggestName("x739_x648_D4") 
      x739.r := getRetimed(x648.r, 4.toInt, io.sigsIn.backpressure & true.B)
      val x655_tuple = Wire(UInt(33.W)).suggestName("""x655_tuple""")
      x655_tuple.r := ConvAndCat(x739,x654_elem_0.r)
      val x740 = Wire(Bool()).suggestName("x740_b643_D4") 
      x740.r := getRetimed(b643.r, 4.toInt, io.sigsIn.backpressure & true.B)
      x610.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(4.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x740 & io.sigsIn.backpressure
      x610.bits.wdata(0) := x655_tuple(31,0)
      x610.bits.wstrb := x655_tuple(32,32)
    }
    val module = Module(new x657_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x657_inr_Foreach **/
