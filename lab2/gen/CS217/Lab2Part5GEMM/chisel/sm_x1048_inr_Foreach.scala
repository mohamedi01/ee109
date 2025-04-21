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

/** Hierarchy: x1048 -> x1085 -> x1334 -> x1335 -> x1250 -> x1251 -> x810 **/
/** BEGIN None x1048_inr_Foreach **/
class x1048_inr_Foreach_kernel(
  list_x1019: List[DecoupledIO[AppCommandDense]],
  list_b913: List[FixedPoint],
  list_x879_N: List[UInt],
  list_x1020_fifo: List[FIFOInterface],
  list_x1010_reg: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 11.8.toInt, myName = "x1048_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(11.8.toInt, 2 + _root_.utils.math.log2Up(11.8.toInt), "x1048_inr_Foreach_iiCtr"))
  
  abstract class x1048_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b913 = Input(new FixedPoint(true, 32, 0))
      val in_x1020_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x1020_fifo_p").asInstanceOf[MemParams] ))
      val in_x879_N = Input(UInt(64.W))
      val in_x903_b = Input(new FixedPoint(true, 64, 0))
      val in_x1010_reg = Flipped(new NBufInterface(ModuleParams.getParams("x1010_reg_p").asInstanceOf[NBufParams] ))
      val in_x1019 = Decoupled(new AppCommandDense(ModuleParams.getParams("x1019_p").asInstanceOf[(Int,Int)] ))
      val in_b1004 = Input(new FixedPoint(true, 32, 0))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def b913 = {io.in_b913} 
    def x1020_fifo = {io.in_x1020_fifo} ; io.in_x1020_fifo := DontCare
    def x879_N = {io.in_x879_N} 
    def x903_b = {io.in_x903_b} 
    def x1010_reg = {io.in_x1010_reg} ; io.in_x1010_reg := DontCare
    def x1019 = {io.in_x1019} 
    def b1004 = {io.in_b1004} 
  }
  def connectWires0(module: x1048_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b913 <> b913
    x1020_fifo.connectLedger(module.io.in_x1020_fifo)
    module.io.in_x879_N <> x879_N
    module.io.in_x903_b <> x903_b
    x1010_reg.connectLedger(module.io.in_x1010_reg)
    module.io.in_x1019 <> x1019
    module.io.in_b1004 <> b1004
  }
  val x1019 = list_x1019(0)
  val b913 = list_b913(0)
  val x903_b = list_b913(1)
  val b1004 = list_b913(2)
  val x879_N = list_x879_N(0)
  val x1020_fifo = list_x1020_fifo(0)
  val x1010_reg = list_x1010_reg(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1048_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1048_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1048_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1048_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x1048_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x1048_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1048_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x1048_inr_Foreach = Module(new InstrumentationCounter())
      val idles_x1048_inr_Foreach = Module(new InstrumentationCounter())
      stalls_x1048_inr_Foreach.io.enable := io.sigsIn.baseEn & ~(x1019.ready & (~x1020_fifo.full.D(11.8-1) | ~(x1020_fifo.active(0).out)))
      idles_x1048_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X1048_instrctr, cycles_x1048_inr_Foreach.io.count, iters_x1048_inr_Foreach.io.count, stalls_x1048_inr_Foreach.io.count, idles_x1048_inr_Foreach.io.count)
      val b1024 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1024.suggestName("b1024")
      val b1025 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1025.suggestName("b1025")
      val x1026_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1026_sum""")
      val ensig0 = Wire(Bool())
      ensig0 := x1019.ready & (~x1020_fifo.full.D(11.8-1) | ~(x1020_fifo.active(0).out))
      x1026_sum.r := Math.add(b913,b1024,Some(1.0), ensig0, Truncate, Wrapping, "x1026_sum").r
      val x1027_rd_x879 = Wire(new FixedPoint(true, 32, 0))
      x1027_rd_x879.r := x879_N.r
      val x1351 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1351_b1004_D1") 
      x1351.r := getRetimed(b1004.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1352 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1352_x1027_rd_x879_D1") 
      x1352.r := getRetimed(x1027_rd_x879.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1325 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1325""")
      x1325.r := Math.fma(x1026_sum,x1352,x1351,Some(6.0), ensig0, "x1325").toFixed(x1325, "cast_x1325").r
      val x1030 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1030""")
      x1030.r := Math.arith_right_shift_div(x1325, 4, Some(0.2), ensig0,"x1030").r
      val x1031 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1031""")
      x1031.r := Math.arith_left_shift(x1030, 4, Some(0.2), ensig0,"x1031").r
      val x1326 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1326""")
      x1326.r := Math.arith_left_shift(x1030, 6, Some(0.2), ensig0,"x1326").r
      val x1033_sub = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1033_sub""")
      x1033_sub.r := Math.sub(x1325,x1031,Some(1.0), ensig0, Truncate, Wrapping, "x1033_sub").r
      val x1034_rd_x1010 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1034_rd_x1010""")
      val x1034_rd_x1010_banks = List[UInt]()
      val x1034_rd_x1010_ofs = List[UInt]()
      val x1034_rd_x1010_en = List[Bool](true.B)
      val x1034_rd_x1010_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1034_rd_x1010_shared_en")
      x1034_rd_x1010.toSeq.zip(x1010_reg.connectRPort(1034, x1034_rd_x1010_banks, x1034_rd_x1010_ofs, io.sigsIn.backpressure, x1034_rd_x1010_en.map(_ && x1034_rd_x1010_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1353 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1353_x1034_rd_x1010_D8") 
      x1353.r := getRetimed(x1034_rd_x1010.r, 8.toInt, io.sigsIn.backpressure & true.B)
      val x1035_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1035_sum""")
      x1035_sum.r := Math.add(x1033_sub,x1353,Some(1.0), ensig0, Truncate, Wrapping, "x1035_sum").r
      val x1036_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1036_sum""")
      x1036_sum.r := Math.add(x1035_sum,15L.FP(true, 32, 0),Some(1.0), ensig0, Truncate, Wrapping, "x1036_sum").r
      val x1037 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1037""")
      x1037.r := Math.arith_right_shift_div(x1036_sum, 4, Some(0.2), ensig0,"x1037").r
      val x1038 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1038""")
      x1038.r := Math.arith_left_shift(x1037, 4, Some(0.2), ensig0,"x1038").r
      val x1327 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1327""")
      x1327.r := Math.arith_left_shift(x1037, 6, Some(0.2), ensig0,"x1327").r
      val x1040 = Wire(new FixedPoint(true, 64, 0)).suggestName("""x1040""")
      x1040.r := Math.fix2fix(x1326, true, 64, 0, Some(0.0), ensig0, Truncate, Wrapping, "x1040").r
      val x1041 = x903_b
      val x1354 = Wire(new FixedPoint(true, 64, 0)).suggestName("x1354_x1041_D7") 
      x1354.r := getRetimed(x1041.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1042_sum = Wire(new FixedPoint(true, 64, 0)).suggestName("""x1042_sum""")
      x1042_sum.r := Math.add(x1040,x1354,Some(2.0), ensig0, Truncate, Wrapping, "x1042_sum").r
      val x1355 = Wire(new FixedPoint(true, 64, 0)).suggestName("x1355_x1042_sum_D1") 
      x1355.r := getRetimed(x1042_sum.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1043_tuple = Wire(UInt(97.W)).suggestName("""x1043_tuple""")
      x1043_tuple.r := ConvAndCat(true.B,x1327.r,x1355.r)
      val x1044 = true.B
      val x1356 = Wire(Bool()).suggestName("x1356_b1025_D10") 
      x1356.r := getRetimed(b1025.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1357 = Wire(Bool()).suggestName("x1357_x1044_D10") 
      x1357.r := getRetimed(x1044.r, 10.toInt, io.sigsIn.backpressure & true.B)
      x1019.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.8.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x1357&x1356 & io.sigsIn.backpressure
      x1019.bits.addr := x1043_tuple(63,0)
      x1019.bits.size := x1043_tuple(95,64)
      val x1358 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1358_x1033_sub_D2") 
      x1358.r := getRetimed(x1033_sub.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x1359 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1359_x1035_sum_D1") 
      x1359.r := getRetimed(x1035_sum.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1046_tuple = Wire(UInt(96.W)).suggestName("""x1046_tuple""")
      x1046_tuple.r := ConvAndCat(x1359.r,x1358.r,x1038.r)
      val x1047_enq_x1020_banks = List[UInt]()
      val x1047_enq_x1020_ofs = List[UInt]()
      val x1047_enq_x1020_en = List[Bool](true.B)
      val x1047_enq_x1020_data = List[UInt](x1046_tuple.r)
      x1020_fifo.connectWPort(1047, x1047_enq_x1020_banks, x1047_enq_x1020_ofs, x1047_enq_x1020_data, x1047_enq_x1020_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.8.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B & x1356))
      x1020_fifo.connectAccessActivesIn(0, ((true.B & x1356)))
    }
    val module = Module(new x1048_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1048_inr_Foreach **/
