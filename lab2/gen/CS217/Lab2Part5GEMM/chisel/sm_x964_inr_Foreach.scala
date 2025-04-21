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

/** Hierarchy: x964 -> x1001 -> x1250 -> x1251 -> x810 **/
/** BEGIN None x964_inr_Foreach **/
class x964_inr_Foreach_kernel(
  list_x916_reg: List[NBufInterface],
  list_x936_fifo: List[FIFOInterface],
  list_x935: List[DecoupledIO[AppCommandDense]],
  list_b913: List[FixedPoint],
  list_x880_K: List[UInt],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 11.8.toInt, myName = "x964_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(11.8.toInt, 2 + _root_.utils.math.log2Up(11.8.toInt), "x964_inr_Foreach_iiCtr"))
  
  abstract class x964_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b913 = Input(new FixedPoint(true, 32, 0))
      val in_b924 = Input(new FixedPoint(true, 32, 0))
      val in_x916_reg = Flipped(new NBufInterface(ModuleParams.getParams("x916_reg_p").asInstanceOf[NBufParams] ))
      val in_x936_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x936_fifo_p").asInstanceOf[MemParams] ))
      val in_x900_a = Input(new FixedPoint(true, 64, 0))
      val in_x880_K = Input(UInt(64.W))
      val in_x935 = Decoupled(new AppCommandDense(ModuleParams.getParams("x935_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def b913 = {io.in_b913} 
    def b924 = {io.in_b924} 
    def x916_reg = {io.in_x916_reg} ; io.in_x916_reg := DontCare
    def x936_fifo = {io.in_x936_fifo} ; io.in_x936_fifo := DontCare
    def x900_a = {io.in_x900_a} 
    def x880_K = {io.in_x880_K} 
    def x935 = {io.in_x935} 
  }
  def connectWires0(module: x964_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b913 <> b913
    module.io.in_b924 <> b924
    x916_reg.connectLedger(module.io.in_x916_reg)
    x936_fifo.connectLedger(module.io.in_x936_fifo)
    module.io.in_x900_a <> x900_a
    module.io.in_x880_K <> x880_K
    module.io.in_x935 <> x935
  }
  val x916_reg = list_x916_reg(0)
  val x936_fifo = list_x936_fifo(0)
  val x935 = list_x935(0)
  val b913 = list_b913(0)
  val b924 = list_b913(1)
  val x900_a = list_b913(2)
  val x880_K = list_x880_K(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x964_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x964_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x964_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x964_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x964_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x964_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x964_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x964_inr_Foreach = Module(new InstrumentationCounter())
      val idles_x964_inr_Foreach = Module(new InstrumentationCounter())
      stalls_x964_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((~x936_fifo.full.D(11.8-1) | ~(x936_fifo.active(0).out)) & x935.ready)
      idles_x964_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X964_instrctr, cycles_x964_inr_Foreach.io.count, iters_x964_inr_Foreach.io.count, stalls_x964_inr_Foreach.io.count, idles_x964_inr_Foreach.io.count)
      val b940 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b940.suggestName("b940")
      val b941 = ~io.sigsIn.cchainOutputs.head.oobs(0); b941.suggestName("b941")
      val x942_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x942_sum""")
      val ensig0 = Wire(Bool())
      ensig0 := (~x936_fifo.full.D(11.8-1) | ~(x936_fifo.active(0).out)) & x935.ready
      x942_sum.r := Math.add(b924,b940,Some(1.0), ensig0, Truncate, Wrapping, "x942_sum").r
      val x943_rd_x880 = Wire(new FixedPoint(true, 32, 0))
      x943_rd_x880.r := x880_K.r
      val x1336 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1336_x943_rd_x880_D1") 
      x1336.r := getRetimed(x943_rd_x880.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1337 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1337_b913_D1") 
      x1337.r := getRetimed(b913.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1322 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1322""")
      x1322.r := Math.fma(x942_sum,x1336,x1337,Some(6.0), ensig0, "x1322").toFixed(x1322, "cast_x1322").r
      val x946 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x946""")
      x946.r := Math.arith_right_shift_div(x1322, 4, Some(0.2), ensig0,"x946").r
      val x947 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x947""")
      x947.r := Math.arith_left_shift(x946, 4, Some(0.2), ensig0,"x947").r
      val x1323 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1323""")
      x1323.r := Math.arith_left_shift(x946, 6, Some(0.2), ensig0,"x1323").r
      val x949_sub = Wire(new FixedPoint(true, 32, 0)).suggestName("""x949_sub""")
      x949_sub.r := Math.sub(x1322,x947,Some(1.0), ensig0, Truncate, Wrapping, "x949_sub").r
      val x950_rd_x916 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x950_rd_x916""")
      val x950_rd_x916_banks = List[UInt]()
      val x950_rd_x916_ofs = List[UInt]()
      val x950_rd_x916_en = List[Bool](true.B)
      val x950_rd_x916_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x950_rd_x916_shared_en")
      x950_rd_x916.toSeq.zip(x916_reg.connectRPort(950, x950_rd_x916_banks, x950_rd_x916_ofs, io.sigsIn.backpressure, x950_rd_x916_en.map(_ && x950_rd_x916_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1338 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1338_x950_rd_x916_D8") 
      x1338.r := getRetimed(x950_rd_x916.r, 8.toInt, io.sigsIn.backpressure & true.B)
      val x951_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x951_sum""")
      x951_sum.r := Math.add(x949_sub,x1338,Some(1.0), ensig0, Truncate, Wrapping, "x951_sum").r
      val x952_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x952_sum""")
      x952_sum.r := Math.add(x951_sum,15L.FP(true, 32, 0),Some(1.0), ensig0, Truncate, Wrapping, "x952_sum").r
      val x953 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x953""")
      x953.r := Math.arith_right_shift_div(x952_sum, 4, Some(0.2), ensig0,"x953").r
      val x954 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x954""")
      x954.r := Math.arith_left_shift(x953, 4, Some(0.2), ensig0,"x954").r
      val x1324 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1324""")
      x1324.r := Math.arith_left_shift(x953, 6, Some(0.2), ensig0,"x1324").r
      val x956 = Wire(new FixedPoint(true, 64, 0)).suggestName("""x956""")
      x956.r := Math.fix2fix(x1323, true, 64, 0, Some(0.0), ensig0, Truncate, Wrapping, "x956").r
      val x957 = x900_a
      val x1339 = Wire(new FixedPoint(true, 64, 0)).suggestName("x1339_x957_D7") 
      x1339.r := getRetimed(x957.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x958_sum = Wire(new FixedPoint(true, 64, 0)).suggestName("""x958_sum""")
      x958_sum.r := Math.add(x956,x1339,Some(2.0), ensig0, Truncate, Wrapping, "x958_sum").r
      val x1340 = Wire(new FixedPoint(true, 64, 0)).suggestName("x1340_x958_sum_D1") 
      x1340.r := getRetimed(x958_sum.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x959_tuple = Wire(UInt(97.W)).suggestName("""x959_tuple""")
      x959_tuple.r := ConvAndCat(true.B,x1324.r,x1340.r)
      val x960 = true.B
      val x1341 = Wire(Bool()).suggestName("x1341_x960_D10") 
      x1341.r := getRetimed(x960.r, 10.toInt, io.sigsIn.backpressure & true.B)
      val x1342 = Wire(Bool()).suggestName("x1342_b941_D10") 
      x1342.r := getRetimed(b941.r, 10.toInt, io.sigsIn.backpressure & true.B)
      x935.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.8.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x1341&x1342 & io.sigsIn.backpressure
      x935.bits.addr := x959_tuple(63,0)
      x935.bits.size := x959_tuple(95,64)
      val x1343 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1343_x951_sum_D1") 
      x1343.r := getRetimed(x951_sum.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1344 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1344_x949_sub_D2") 
      x1344.r := getRetimed(x949_sub.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x962_tuple = Wire(UInt(96.W)).suggestName("""x962_tuple""")
      x962_tuple.r := ConvAndCat(x1343.r,x1344.r,x954.r)
      val x963_enq_x936_banks = List[UInt]()
      val x963_enq_x936_ofs = List[UInt]()
      val x963_enq_x936_en = List[Bool](true.B)
      val x963_enq_x936_data = List[UInt](x962_tuple.r)
      x936_fifo.connectWPort(963, x963_enq_x936_banks, x963_enq_x936_ofs, x963_enq_x936_data, x963_enq_x936_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.8.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B & x1342))
      x936_fifo.connectAccessActivesIn(0, ((true.B & x1342)))
    }
    val module = Module(new x964_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x964_inr_Foreach **/
