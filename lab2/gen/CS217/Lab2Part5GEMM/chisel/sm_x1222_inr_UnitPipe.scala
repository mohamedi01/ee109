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

/** Hierarchy: x1222 -> x1242 -> x1247 -> x1248 -> x1335 -> x1250 -> x1251 -> x810 **/
/** BEGIN None x1222_inr_UnitPipe **/
class x1222_inr_UnitPipe_kernel(
  list_x1189: List[DecoupledIO[AppCommandDense]],
  list_x1196_reg: List[StandardInterface],
  list_x879_N: List[UInt],
  list_b924: List[FixedPoint],
  list_x1010_reg: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 11.8.toInt, myName = "x1222_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1222_inr_UnitPipe_iiCtr"))
  
  abstract class x1222_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x1196_reg = Flipped(new StandardInterface(ModuleParams.getParams("x1196_reg_p").asInstanceOf[MemParams] ))
      val in_b924 = Input(new FixedPoint(true, 32, 0))
      val in_x879_N = Input(UInt(64.W))
      val in_x906_c = Input(new FixedPoint(true, 64, 0))
      val in_b1194 = Input(new FixedPoint(true, 32, 0))
      val in_x1198_reg = Flipped(new StandardInterface(ModuleParams.getParams("x1198_reg_p").asInstanceOf[MemParams] ))
      val in_x1189 = Decoupled(new AppCommandDense(ModuleParams.getParams("x1189_p").asInstanceOf[(Int,Int)] ))
      val in_x1010_reg = Flipped(new NBufInterface(ModuleParams.getParams("x1010_reg_p").asInstanceOf[NBufParams] ))
      val in_x1197_reg = Flipped(new StandardInterface(ModuleParams.getParams("x1197_reg_p").asInstanceOf[MemParams] ))
      val in_b1004 = Input(new FixedPoint(true, 32, 0))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x1196_reg = {io.in_x1196_reg} ; io.in_x1196_reg := DontCare
    def b924 = {io.in_b924} 
    def x879_N = {io.in_x879_N} 
    def x906_c = {io.in_x906_c} 
    def b1194 = {io.in_b1194} 
    def x1198_reg = {io.in_x1198_reg} ; io.in_x1198_reg := DontCare
    def x1189 = {io.in_x1189} 
    def x1010_reg = {io.in_x1010_reg} ; io.in_x1010_reg := DontCare
    def x1197_reg = {io.in_x1197_reg} ; io.in_x1197_reg := DontCare
    def b1004 = {io.in_b1004} 
  }
  def connectWires0(module: x1222_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x1196_reg.connectLedger(module.io.in_x1196_reg)
    module.io.in_b924 <> b924
    module.io.in_x879_N <> x879_N
    module.io.in_x906_c <> x906_c
    module.io.in_b1194 <> b1194
    x1198_reg.connectLedger(module.io.in_x1198_reg)
    module.io.in_x1189 <> x1189
    x1010_reg.connectLedger(module.io.in_x1010_reg)
    x1197_reg.connectLedger(module.io.in_x1197_reg)
    module.io.in_b1004 <> b1004
  }
  val x1189 = list_x1189(0)
  val x1196_reg = list_x1196_reg(0)
  val x1198_reg = list_x1196_reg(1)
  val x1197_reg = list_x1196_reg(2)
  val x879_N = list_x879_N(0)
  val b924 = list_b924(0)
  val x906_c = list_b924(1)
  val b1194 = list_b924(2)
  val b1004 = list_b924(3)
  val x1010_reg = list_x1010_reg(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1222_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x1222_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1222_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1222_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x1222_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x1222_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x1222_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x1222_inr_UnitPipe = Module(new InstrumentationCounter())
      val idles_x1222_inr_UnitPipe = Module(new InstrumentationCounter())
      stalls_x1222_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(x1189.ready)
      idles_x1222_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X1222_instrctr, cycles_x1222_inr_UnitPipe.io.count, iters_x1222_inr_UnitPipe.io.count, stalls_x1222_inr_UnitPipe.io.count, idles_x1222_inr_UnitPipe.io.count)
      val x1199_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1199_sum""")
      val ensig0 = Wire(Bool())
      ensig0 := x1189.ready
      x1199_sum.r := Math.add(b924,b1194,Some(1.0), ensig0, Truncate, Wrapping, "x1199_sum").r
      val x1200_rd_x879 = Wire(new FixedPoint(true, 32, 0))
      x1200_rd_x879.r := x879_N.r
      val x1401 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1401_b1004_D1") 
      x1401.r := getRetimed(b1004.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1402 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1402_x1200_rd_x879_D1") 
      x1402.r := getRetimed(x1200_rd_x879.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1331 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1331""")
      x1331.r := Math.fma(x1199_sum,x1402,x1401,Some(6.0), ensig0, "x1331").toFixed(x1331, "cast_x1331").r
      val x1203 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1203""")
      x1203.r := Math.arith_right_shift_div(x1331, 4, Some(0.2), ensig0,"x1203").r
      val x1204 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1204""")
      x1204.r := Math.arith_left_shift(x1203, 4, Some(0.2), ensig0,"x1204").r
      val x1332 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1332""")
      x1332.r := Math.arith_left_shift(x1203, 6, Some(0.2), ensig0,"x1332").r
      val x1206_sub = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1206_sub""")
      x1206_sub.r := Math.sub(x1331,x1204,Some(1.0), ensig0, Truncate, Wrapping, "x1206_sub").r
      val x1207_rd_x1010 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1207_rd_x1010""")
      val x1207_rd_x1010_banks = List[UInt]()
      val x1207_rd_x1010_ofs = List[UInt]()
      val x1207_rd_x1010_en = List[Bool](true.B)
      val x1207_rd_x1010_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1207_rd_x1010_shared_en")
      x1207_rd_x1010.toSeq.zip(x1010_reg.connectRPort(1207, x1207_rd_x1010_banks, x1207_rd_x1010_ofs, io.sigsIn.backpressure, x1207_rd_x1010_en.map(_ && x1207_rd_x1010_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1403 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1403_x1207_rd_x1010_D8") 
      x1403.r := getRetimed(x1207_rd_x1010.r, 8.toInt, io.sigsIn.backpressure & true.B)
      val x1208_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1208_sum""")
      x1208_sum.r := Math.add(x1206_sub,x1403,Some(1.0), ensig0, Truncate, Wrapping, "x1208_sum").r
      val x1209_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1209_sum""")
      x1209_sum.r := Math.add(x1208_sum,15L.FP(true, 32, 0),Some(1.0), ensig0, Truncate, Wrapping, "x1209_sum").r
      val x1210 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1210""")
      x1210.r := Math.arith_right_shift_div(x1209_sum, 4, Some(0.2), ensig0,"x1210").r
      val x1211 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1211""")
      x1211.r := Math.arith_left_shift(x1210, 4, Some(0.2), ensig0,"x1211").r
      val x1333 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1333""")
      x1333.r := Math.arith_left_shift(x1210, 6, Some(0.2), ensig0,"x1333").r
      val x1213 = Wire(new FixedPoint(true, 64, 0)).suggestName("""x1213""")
      x1213.r := Math.fix2fix(x1332, true, 64, 0, Some(0.0), ensig0, Truncate, Wrapping, "x1213").r
      val x1214 = x906_c
      val x1404 = Wire(new FixedPoint(true, 64, 0)).suggestName("x1404_x1214_D7") 
      x1404.r := getRetimed(x1214.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1215_sum = Wire(new FixedPoint(true, 64, 0)).suggestName("""x1215_sum""")
      x1215_sum.r := Math.add(x1213,x1404,Some(2.0), ensig0, Truncate, Wrapping, "x1215_sum").r
      val x1405 = Wire(new FixedPoint(true, 64, 0)).suggestName("x1405_x1215_sum_D1") 
      x1405.r := getRetimed(x1215_sum.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1216_tuple = Wire(UInt(97.W)).suggestName("""x1216_tuple""")
      x1216_tuple.r := ConvAndCat(false.B,x1333.r,x1405.r)
      val x1217 = true.B
      val x1406 = Wire(Bool()).suggestName("x1406_x1217_D10") 
      x1406.r := getRetimed(x1217.r, 10.toInt, io.sigsIn.backpressure & true.B)
      x1189.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.8.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x1406 & io.sigsIn.backpressure
      x1189.bits.addr := x1216_tuple(63,0)
      x1189.bits.size := x1216_tuple(95,64)
      val x1219_wr_x1196_banks = List[UInt]()
      val x1219_wr_x1196_ofs = List[UInt]()
      val x1219_wr_x1196_en = List[Bool](true.B)
      val x1219_wr_x1196_data = List[UInt](x1206_sub.r)
      x1196_reg.connectWPort(1219, x1219_wr_x1196_banks, x1219_wr_x1196_ofs, x1219_wr_x1196_data, x1219_wr_x1196_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(8.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x1220_wr_x1197_banks = List[UInt]()
      val x1220_wr_x1197_ofs = List[UInt]()
      val x1220_wr_x1197_en = List[Bool](true.B)
      val x1220_wr_x1197_data = List[UInt](x1208_sum.r)
      x1197_reg.connectWPort(1220, x1220_wr_x1197_banks, x1220_wr_x1197_ofs, x1220_wr_x1197_data, x1220_wr_x1197_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(9.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x1221_wr_x1198_banks = List[UInt]()
      val x1221_wr_x1198_ofs = List[UInt]()
      val x1221_wr_x1198_en = List[Bool](true.B)
      val x1221_wr_x1198_data = List[UInt](x1211.r)
      x1198_reg.connectWPort(1221, x1221_wr_x1198_banks, x1221_wr_x1198_ofs, x1221_wr_x1198_data, x1221_wr_x1198_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.8.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x1222_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x1222_inr_UnitPipe **/
