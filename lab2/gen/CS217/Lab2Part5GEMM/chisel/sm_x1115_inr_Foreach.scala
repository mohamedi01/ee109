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

/** Hierarchy: x1115 -> x1153 -> x1334 -> x1335 -> x1250 -> x1251 -> x810 **/
/** BEGIN None x1115_inr_Foreach **/
class x1115_inr_Foreach_kernel(
  list_x1086: List[DecoupledIO[AppCommandDense]],
  list_b924: List[FixedPoint],
  list_x1087_fifo: List[FIFOInterface],
  list_x879_N: List[UInt],
  list_x1010_reg: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 11.8.toInt, myName = "x1115_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(11.8.toInt, 2 + _root_.utils.math.log2Up(11.8.toInt), "x1115_inr_Foreach_iiCtr"))
  
  abstract class x1115_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x1087_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x1087_fifo_p").asInstanceOf[MemParams] ))
      val in_b924 = Input(new FixedPoint(true, 32, 0))
      val in_x879_N = Input(UInt(64.W))
      val in_x1086 = Decoupled(new AppCommandDense(ModuleParams.getParams("x1086_p").asInstanceOf[(Int,Int)] ))
      val in_x906_c = Input(new FixedPoint(true, 64, 0))
      val in_x1010_reg = Flipped(new NBufInterface(ModuleParams.getParams("x1010_reg_p").asInstanceOf[NBufParams] ))
      val in_b1004 = Input(new FixedPoint(true, 32, 0))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x1087_fifo = {io.in_x1087_fifo} ; io.in_x1087_fifo := DontCare
    def b924 = {io.in_b924} 
    def x879_N = {io.in_x879_N} 
    def x1086 = {io.in_x1086} 
    def x906_c = {io.in_x906_c} 
    def x1010_reg = {io.in_x1010_reg} ; io.in_x1010_reg := DontCare
    def b1004 = {io.in_b1004} 
  }
  def connectWires0(module: x1115_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x1087_fifo.connectLedger(module.io.in_x1087_fifo)
    module.io.in_b924 <> b924
    module.io.in_x879_N <> x879_N
    module.io.in_x1086 <> x1086
    module.io.in_x906_c <> x906_c
    x1010_reg.connectLedger(module.io.in_x1010_reg)
    module.io.in_b1004 <> b1004
  }
  val x1086 = list_x1086(0)
  val b924 = list_b924(0)
  val x906_c = list_b924(1)
  val b1004 = list_b924(2)
  val x1087_fifo = list_x1087_fifo(0)
  val x879_N = list_x879_N(0)
  val x1010_reg = list_x1010_reg(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1115_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1115_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1115_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1115_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x1115_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x1115_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1115_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x1115_inr_Foreach = Module(new InstrumentationCounter())
      val idles_x1115_inr_Foreach = Module(new InstrumentationCounter())
      stalls_x1115_inr_Foreach.io.enable := io.sigsIn.baseEn & ~(x1086.ready & (~x1087_fifo.full.D(11.8-1) | ~(x1087_fifo.active(0).out)))
      idles_x1115_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X1115_instrctr, cycles_x1115_inr_Foreach.io.count, iters_x1115_inr_Foreach.io.count, stalls_x1115_inr_Foreach.io.count, idles_x1115_inr_Foreach.io.count)
      val b1091 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1091.suggestName("b1091")
      val b1092 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1092.suggestName("b1092")
      val x1093_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1093_sum""")
      val ensig0 = Wire(Bool())
      ensig0 := x1086.ready & (~x1087_fifo.full.D(11.8-1) | ~(x1087_fifo.active(0).out))
      x1093_sum.r := Math.add(b924,b1091,Some(1.0), ensig0, Truncate, Wrapping, "x1093_sum").r
      val x1094_rd_x879 = Wire(new FixedPoint(true, 32, 0))
      x1094_rd_x879.r := x879_N.r
      val x1366 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1366_b1004_D1") 
      x1366.r := getRetimed(b1004.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1367 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1367_x1094_rd_x879_D1") 
      x1367.r := getRetimed(x1094_rd_x879.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1328 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1328""")
      x1328.r := Math.fma(x1093_sum,x1367,x1366,Some(6.0), ensig0, "x1328").toFixed(x1328, "cast_x1328").r
      val x1097 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1097""")
      x1097.r := Math.arith_right_shift_div(x1328, 4, Some(0.2), ensig0,"x1097").r
      val x1098 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1098""")
      x1098.r := Math.arith_left_shift(x1097, 4, Some(0.2), ensig0,"x1098").r
      val x1329 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1329""")
      x1329.r := Math.arith_left_shift(x1097, 6, Some(0.2), ensig0,"x1329").r
      val x1100_sub = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1100_sub""")
      x1100_sub.r := Math.sub(x1328,x1098,Some(1.0), ensig0, Truncate, Wrapping, "x1100_sub").r
      val x1101_rd_x1010 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1101_rd_x1010""")
      val x1101_rd_x1010_banks = List[UInt]()
      val x1101_rd_x1010_ofs = List[UInt]()
      val x1101_rd_x1010_en = List[Bool](true.B)
      val x1101_rd_x1010_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1101_rd_x1010_shared_en")
      x1101_rd_x1010.toSeq.zip(x1010_reg.connectRPort(1101, x1101_rd_x1010_banks, x1101_rd_x1010_ofs, io.sigsIn.backpressure, x1101_rd_x1010_en.map(_ && x1101_rd_x1010_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1368 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1368_x1101_rd_x1010_D8") 
      x1368.r := getRetimed(x1101_rd_x1010.r, 8.toInt, io.sigsIn.backpressure & true.B)
      val x1102_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1102_sum""")
      x1102_sum.r := Math.add(x1100_sub,x1368,Some(1.0), ensig0, Truncate, Wrapping, "x1102_sum").r
      val x1103_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1103_sum""")
      x1103_sum.r := Math.add(x1102_sum,15L.FP(true, 32, 0),Some(1.0), ensig0, Truncate, Wrapping, "x1103_sum").r
      val x1104 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1104""")
      x1104.r := Math.arith_right_shift_div(x1103_sum, 4, Some(0.2), ensig0,"x1104").r
      val x1105 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1105""")
      x1105.r := Math.arith_left_shift(x1104, 4, Some(0.2), ensig0,"x1105").r
      val x1330 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1330""")
      x1330.r := Math.arith_left_shift(x1104, 6, Some(0.2), ensig0,"x1330").r
      val x1107 = Wire(new FixedPoint(true, 64, 0)).suggestName("""x1107""")
      x1107.r := Math.fix2fix(x1329, true, 64, 0, Some(0.0), ensig0, Truncate, Wrapping, "x1107").r
      val x1108 = x906_c
      val x1369 = Wire(new FixedPoint(true, 64, 0)).suggestName("x1369_x1108_D7") 
      x1369.r := getRetimed(x1108.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1109_sum = Wire(new FixedPoint(true, 64, 0)).suggestName("""x1109_sum""")
      x1109_sum.r := Math.add(x1107,x1369,Some(2.0), ensig0, Truncate, Wrapping, "x1109_sum").r
      val x1370 = Wire(new FixedPoint(true, 64, 0)).suggestName("x1370_x1109_sum_D1") 
      x1370.r := getRetimed(x1109_sum.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1110_tuple = Wire(UInt(97.W)).suggestName("""x1110_tuple""")
      x1110_tuple.r := ConvAndCat(true.B,x1330.r,x1370.r)
      val x1111 = true.B
      val x1371 = Wire(Bool()).suggestName("x1371_b1092_D10") 
      x1371.r := getRetimed(b1092.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1372 = Wire(Bool()).suggestName("x1372_x1111_D10") 
      x1372.r := getRetimed(x1111.r, 10.toInt, io.sigsIn.backpressure & true.B)
      x1086.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.8.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x1372&x1371 & io.sigsIn.backpressure
      x1086.bits.addr := x1110_tuple(63,0)
      x1086.bits.size := x1110_tuple(95,64)
      val x1373 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1373_x1102_sum_D1") 
      x1373.r := getRetimed(x1102_sum.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1374 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1374_x1100_sub_D2") 
      x1374.r := getRetimed(x1100_sub.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x1113_tuple = Wire(UInt(96.W)).suggestName("""x1113_tuple""")
      x1113_tuple.r := ConvAndCat(x1373.r,x1374.r,x1105.r)
      val x1114_enq_x1087_banks = List[UInt]()
      val x1114_enq_x1087_ofs = List[UInt]()
      val x1114_enq_x1087_en = List[Bool](true.B)
      val x1114_enq_x1087_data = List[UInt](x1113_tuple.r)
      x1087_fifo.connectWPort(1114, x1114_enq_x1087_banks, x1114_enq_x1087_ofs, x1114_enq_x1087_data, x1114_enq_x1087_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.8.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B & x1371))
      x1087_fifo.connectAccessActivesIn(0, ((true.B & x1371)))
    }
    val module = Module(new x1115_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1115_inr_Foreach **/
