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

/** Hierarchy: x1049 -> x1087 -> x1811 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1049_inr_Foreach **/
class x1049_inr_Foreach_kernel(
  list_x1021_fifo: List[FIFOInterface],
  list_b1005: List[FixedPoint],
  list_x1011_reg: List[NBufInterface],
  list_x879_N: List[UInt],
  list_x1020: List[DecoupledIO[AppCommandDense]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 11.8.toInt, myName = "x1049_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(11.8.toInt, 2 + _root_.utils.math.log2Up(11.8.toInt), "x1049_inr_Foreach_iiCtr"))
  
  abstract class x1049_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b1005 = Input(new FixedPoint(true, 32, 0))
      val in_b913 = Input(new FixedPoint(true, 32, 0))
      val in_x1020 = Decoupled(new AppCommandDense(ModuleParams.getParams("x1020_p").asInstanceOf[(Int,Int)] ))
      val in_x1021_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x1021_fifo_p").asInstanceOf[MemParams] ))
      val in_x879_N = Input(UInt(64.W))
      val in_x1011_reg = Flipped(new NBufInterface(ModuleParams.getParams("x1011_reg_p").asInstanceOf[NBufParams] ))
      val in_x903_b = Input(new FixedPoint(true, 64, 0))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def b1005 = {io.in_b1005} 
    def b913 = {io.in_b913} 
    def x1020 = {io.in_x1020} 
    def x1021_fifo = {io.in_x1021_fifo} ; io.in_x1021_fifo := DontCare
    def x879_N = {io.in_x879_N} 
    def x1011_reg = {io.in_x1011_reg} ; io.in_x1011_reg := DontCare
    def x903_b = {io.in_x903_b} 
  }
  def connectWires0(module: x1049_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b1005 <> b1005
    module.io.in_b913 <> b913
    module.io.in_x1020 <> x1020
    x1021_fifo.connectLedger(module.io.in_x1021_fifo)
    module.io.in_x879_N <> x879_N
    x1011_reg.connectLedger(module.io.in_x1011_reg)
    module.io.in_x903_b <> x903_b
  }
  val x1021_fifo = list_x1021_fifo(0)
  val b1005 = list_b1005(0)
  val b913 = list_b1005(1)
  val x903_b = list_b1005(2)
  val x1011_reg = list_x1011_reg(0)
  val x879_N = list_x879_N(0)
  val x1020 = list_x1020(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1049_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1049_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1049_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1049_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x1049_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x1049_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1049_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x1049_inr_Foreach = Module(new InstrumentationCounter())
      val idles_x1049_inr_Foreach = Module(new InstrumentationCounter())
      stalls_x1049_inr_Foreach.io.enable := io.sigsIn.baseEn & ~(x1020.ready & (~x1021_fifo.full.D(11.8-1) | ~(x1021_fifo.active(0).out)))
      idles_x1049_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X1049_instrctr, cycles_x1049_inr_Foreach.io.count, iters_x1049_inr_Foreach.io.count, stalls_x1049_inr_Foreach.io.count, idles_x1049_inr_Foreach.io.count)
      val b1025 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1025.suggestName("b1025")
      val b1026 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1026.suggestName("b1026")
      val x1027_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1027_sum""")
      val ensig0 = Wire(Bool())
      ensig0 := x1020.ready & (~x1021_fifo.full.D(11.8-1) | ~(x1021_fifo.active(0).out))
      x1027_sum.r := Math.add(b913,b1025,Some(1.0), ensig0, Truncate, Wrapping, "x1027_sum").r
      val x1028_rd_x879 = Wire(new FixedPoint(true, 32, 0))
      x1028_rd_x879.r := x879_N.r
      val x1829 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1829_x1028_rd_x879_D1") 
      x1829.r := getRetimed(x1028_rd_x879.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1830 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1830_b1005_D1") 
      x1830.r := getRetimed(b1005.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1797 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1797""")
      x1797.r := Math.fma(x1027_sum,x1829,x1830,Some(6.0), ensig0, "x1797").toFixed(x1797, "cast_x1797").r
      val x1031 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1031""")
      x1031.r := Math.arith_right_shift_div(x1797, 4, Some(0.2), ensig0,"x1031").r
      val x1032 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1032""")
      x1032.r := Math.arith_left_shift(x1031, 4, Some(0.2), ensig0,"x1032").r
      val x1798 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1798""")
      x1798.r := Math.arith_left_shift(x1031, 6, Some(0.2), ensig0,"x1798").r
      val x1034_sub = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1034_sub""")
      x1034_sub.r := Math.sub(x1797,x1032,Some(1.0), ensig0, Truncate, Wrapping, "x1034_sub").r
      val x1035_rd_x1011 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1035_rd_x1011""")
      val x1035_rd_x1011_banks = List[UInt]()
      val x1035_rd_x1011_ofs = List[UInt]()
      val x1035_rd_x1011_en = List[Bool](true.B)
      val x1035_rd_x1011_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1035_rd_x1011_shared_en")
      x1035_rd_x1011.toSeq.zip(x1011_reg.connectRPort(1035, x1035_rd_x1011_banks, x1035_rd_x1011_ofs, io.sigsIn.backpressure, x1035_rd_x1011_en.map(_ && x1035_rd_x1011_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1831 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1831_x1035_rd_x1011_D8") 
      x1831.r := getRetimed(x1035_rd_x1011.r, 8.toInt, io.sigsIn.backpressure & true.B)
      val x1036_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1036_sum""")
      x1036_sum.r := Math.add(x1034_sub,x1831,Some(1.0), ensig0, Truncate, Wrapping, "x1036_sum").r
      val x1037_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1037_sum""")
      x1037_sum.r := Math.add(x1036_sum,15L.FP(true, 32, 0),Some(1.0), ensig0, Truncate, Wrapping, "x1037_sum").r
      val x1038 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1038""")
      x1038.r := Math.arith_right_shift_div(x1037_sum, 4, Some(0.2), ensig0,"x1038").r
      val x1039 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1039""")
      x1039.r := Math.arith_left_shift(x1038, 4, Some(0.2), ensig0,"x1039").r
      val x1799 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1799""")
      x1799.r := Math.arith_left_shift(x1038, 6, Some(0.2), ensig0,"x1799").r
      val x1041 = Wire(new FixedPoint(true, 64, 0)).suggestName("""x1041""")
      x1041.r := Math.fix2fix(x1798, true, 64, 0, Some(0.0), ensig0, Truncate, Wrapping, "x1041").r
      val x1042 = x903_b
      val x1832 = Wire(new FixedPoint(true, 64, 0)).suggestName("x1832_x1042_D7") 
      x1832.r := getRetimed(x1042.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1043_sum = Wire(new FixedPoint(true, 64, 0)).suggestName("""x1043_sum""")
      x1043_sum.r := Math.add(x1041,x1832,Some(2.0), ensig0, Truncate, Wrapping, "x1043_sum").r
      val x1833 = Wire(new FixedPoint(true, 64, 0)).suggestName("x1833_x1043_sum_D1") 
      x1833.r := getRetimed(x1043_sum.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1044_tuple = Wire(UInt(97.W)).suggestName("""x1044_tuple""")
      x1044_tuple.r := ConvAndCat(true.B,x1799.r,x1833.r)
      val x1045 = true.B
      val x1834 = Wire(Bool()).suggestName("x1834_x1045_D10") 
      x1834.r := getRetimed(x1045.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1835 = Wire(Bool()).suggestName("x1835_b1026_D10") 
      x1835.r := getRetimed(b1026.r, 10.toInt, io.sigsIn.backpressure & true.B)
      x1020.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.8.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x1834&x1835 & io.sigsIn.backpressure
      x1020.bits.addr := x1044_tuple(63,0)
      x1020.bits.size := x1044_tuple(95,64)
      val x1836 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1836_x1034_sub_D2") 
      x1836.r := getRetimed(x1034_sub.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x1837 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1837_x1036_sum_D1") 
      x1837.r := getRetimed(x1036_sum.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1047_tuple = Wire(UInt(96.W)).suggestName("""x1047_tuple""")
      x1047_tuple.r := ConvAndCat(x1837.r,x1836.r,x1039.r)
      val x1048_enq_x1021_banks = List[UInt]()
      val x1048_enq_x1021_ofs = List[UInt]()
      val x1048_enq_x1021_en = List[Bool](true.B)
      val x1048_enq_x1021_data = List[UInt](x1047_tuple.r)
      x1021_fifo.connectWPort(1048, x1048_enq_x1021_banks, x1048_enq_x1021_ofs, x1048_enq_x1021_data, x1048_enq_x1021_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.8.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B & x1835))
      x1021_fifo.connectAccessActivesIn(0, ((true.B & x1835)))
    }
    val module = Module(new x1049_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1049_inr_Foreach **/
