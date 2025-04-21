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

/** Hierarchy: x1117 -> x1158 -> x1811 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1117_inr_Foreach **/
class x1117_inr_Foreach_kernel(
  list_x1088: List[DecoupledIO[AppCommandDense]],
  list_x1089_fifo: List[FIFOInterface],
  list_x1011_reg: List[NBufInterface],
  list_x879_N: List[UInt],
  list_b1005: List[FixedPoint],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 11.8.toInt, myName = "x1117_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(11.8.toInt, 2 + _root_.utils.math.log2Up(11.8.toInt), "x1117_inr_Foreach_iiCtr"))
  
  abstract class x1117_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b1005 = Input(new FixedPoint(true, 32, 0))
      val in_b924 = Input(new FixedPoint(true, 32, 0))
      val in_x879_N = Input(UInt(64.W))
      val in_x1089_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x1089_fifo_p").asInstanceOf[MemParams] ))
      val in_x906_c = Input(new FixedPoint(true, 64, 0))
      val in_x1011_reg = Flipped(new NBufInterface(ModuleParams.getParams("x1011_reg_p").asInstanceOf[NBufParams] ))
      val in_x1088 = Decoupled(new AppCommandDense(ModuleParams.getParams("x1088_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def b1005 = {io.in_b1005} 
    def b924 = {io.in_b924} 
    def x879_N = {io.in_x879_N} 
    def x1089_fifo = {io.in_x1089_fifo} ; io.in_x1089_fifo := DontCare
    def x906_c = {io.in_x906_c} 
    def x1011_reg = {io.in_x1011_reg} ; io.in_x1011_reg := DontCare
    def x1088 = {io.in_x1088} 
  }
  def connectWires0(module: x1117_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b1005 <> b1005
    module.io.in_b924 <> b924
    module.io.in_x879_N <> x879_N
    x1089_fifo.connectLedger(module.io.in_x1089_fifo)
    module.io.in_x906_c <> x906_c
    x1011_reg.connectLedger(module.io.in_x1011_reg)
    module.io.in_x1088 <> x1088
  }
  val x1088 = list_x1088(0)
  val x1089_fifo = list_x1089_fifo(0)
  val x1011_reg = list_x1011_reg(0)
  val x879_N = list_x879_N(0)
  val b1005 = list_b1005(0)
  val b924 = list_b1005(1)
  val x906_c = list_b1005(2)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1117_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1117_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1117_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1117_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x1117_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x1117_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1117_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x1117_inr_Foreach = Module(new InstrumentationCounter())
      val idles_x1117_inr_Foreach = Module(new InstrumentationCounter())
      stalls_x1117_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((~x1089_fifo.full.D(11.8-1) | ~(x1089_fifo.active(0).out)) & x1088.ready)
      idles_x1117_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X1117_instrctr, cycles_x1117_inr_Foreach.io.count, iters_x1117_inr_Foreach.io.count, stalls_x1117_inr_Foreach.io.count, idles_x1117_inr_Foreach.io.count)
      val b1093 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1093.suggestName("b1093")
      val b1094 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1094.suggestName("b1094")
      val x1095_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1095_sum""")
      val ensig0 = Wire(Bool())
      ensig0 := (~x1089_fifo.full.D(11.8-1) | ~(x1089_fifo.active(0).out)) & x1088.ready
      x1095_sum.r := Math.add(b924,b1093,Some(1.0), ensig0, Truncate, Wrapping, "x1095_sum").r
      val x1096_rd_x879 = Wire(new FixedPoint(true, 32, 0))
      x1096_rd_x879.r := x879_N.r
      val x1845 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1845_x1096_rd_x879_D1") 
      x1845.r := getRetimed(x1096_rd_x879.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1846 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1846_b1005_D1") 
      x1846.r := getRetimed(b1005.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1801 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1801""")
      x1801.r := Math.fma(x1095_sum,x1845,x1846,Some(6.0), ensig0, "x1801").toFixed(x1801, "cast_x1801").r
      val x1099 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1099""")
      x1099.r := Math.arith_right_shift_div(x1801, 4, Some(0.2), ensig0,"x1099").r
      val x1100 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1100""")
      x1100.r := Math.arith_left_shift(x1099, 4, Some(0.2), ensig0,"x1100").r
      val x1802 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1802""")
      x1802.r := Math.arith_left_shift(x1099, 6, Some(0.2), ensig0,"x1802").r
      val x1102_sub = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1102_sub""")
      x1102_sub.r := Math.sub(x1801,x1100,Some(1.0), ensig0, Truncate, Wrapping, "x1102_sub").r
      val x1103_rd_x1011 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1103_rd_x1011""")
      val x1103_rd_x1011_banks = List[UInt]()
      val x1103_rd_x1011_ofs = List[UInt]()
      val x1103_rd_x1011_en = List[Bool](true.B)
      val x1103_rd_x1011_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1103_rd_x1011_shared_en")
      x1103_rd_x1011.toSeq.zip(x1011_reg.connectRPort(1103, x1103_rd_x1011_banks, x1103_rd_x1011_ofs, io.sigsIn.backpressure, x1103_rd_x1011_en.map(_ && x1103_rd_x1011_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1847 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1847_x1103_rd_x1011_D8") 
      x1847.r := getRetimed(x1103_rd_x1011.r, 8.toInt, io.sigsIn.backpressure & true.B)
      val x1104_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1104_sum""")
      x1104_sum.r := Math.add(x1102_sub,x1847,Some(1.0), ensig0, Truncate, Wrapping, "x1104_sum").r
      val x1105_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1105_sum""")
      x1105_sum.r := Math.add(x1104_sum,15L.FP(true, 32, 0),Some(1.0), ensig0, Truncate, Wrapping, "x1105_sum").r
      val x1106 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1106""")
      x1106.r := Math.arith_right_shift_div(x1105_sum, 4, Some(0.2), ensig0,"x1106").r
      val x1107 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1107""")
      x1107.r := Math.arith_left_shift(x1106, 4, Some(0.2), ensig0,"x1107").r
      val x1803 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1803""")
      x1803.r := Math.arith_left_shift(x1106, 6, Some(0.2), ensig0,"x1803").r
      val x1109 = Wire(new FixedPoint(true, 64, 0)).suggestName("""x1109""")
      x1109.r := Math.fix2fix(x1802, true, 64, 0, Some(0.0), ensig0, Truncate, Wrapping, "x1109").r
      val x1110 = x906_c
      val x1848 = Wire(new FixedPoint(true, 64, 0)).suggestName("x1848_x1110_D7") 
      x1848.r := getRetimed(x1110.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1111_sum = Wire(new FixedPoint(true, 64, 0)).suggestName("""x1111_sum""")
      x1111_sum.r := Math.add(x1109,x1848,Some(2.0), ensig0, Truncate, Wrapping, "x1111_sum").r
      val x1849 = Wire(new FixedPoint(true, 64, 0)).suggestName("x1849_x1111_sum_D1") 
      x1849.r := getRetimed(x1111_sum.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1112_tuple = Wire(UInt(97.W)).suggestName("""x1112_tuple""")
      x1112_tuple.r := ConvAndCat(true.B,x1803.r,x1849.r)
      val x1113 = true.B
      val x1850 = Wire(Bool()).suggestName("x1850_b1094_D10") 
      x1850.r := getRetimed(b1094.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1851 = Wire(Bool()).suggestName("x1851_x1113_D10") 
      x1851.r := getRetimed(x1113.r, 10.toInt, io.sigsIn.backpressure & true.B)
      x1088.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.8.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x1851&x1850 & io.sigsIn.backpressure
      x1088.bits.addr := x1112_tuple(63,0)
      x1088.bits.size := x1112_tuple(95,64)
      val x1852 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1852_x1102_sub_D2") 
      x1852.r := getRetimed(x1102_sub.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x1853 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1853_x1104_sum_D1") 
      x1853.r := getRetimed(x1104_sum.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1115_tuple = Wire(UInt(96.W)).suggestName("""x1115_tuple""")
      x1115_tuple.r := ConvAndCat(x1853.r,x1852.r,x1107.r)
      val x1116_enq_x1089_banks = List[UInt]()
      val x1116_enq_x1089_ofs = List[UInt]()
      val x1116_enq_x1089_en = List[Bool](true.B)
      val x1116_enq_x1089_data = List[UInt](x1115_tuple.r)
      x1089_fifo.connectWPort(1116, x1116_enq_x1089_banks, x1116_enq_x1089_ofs, x1116_enq_x1089_data, x1116_enq_x1089_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.8.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B & x1850))
      x1089_fifo.connectAccessActivesIn(0, ((true.B & x1850)))
    }
    val module = Module(new x1117_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1117_inr_Foreach **/
