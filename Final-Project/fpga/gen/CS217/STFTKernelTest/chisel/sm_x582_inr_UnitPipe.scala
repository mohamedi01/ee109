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

/** Hierarchy: x582 -> x602 -> x607 -> x608 -> x709 -> x710 **/
/** BEGIN None x582_inr_UnitPipe **/
class x582_inr_UnitPipe_kernel(
  list_x461_realDRAM: List[FixedPoint],
  list_x553: List[DecoupledIO[AppCommandDense]],
  list_x561_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 4.0.toInt, myName = "x582_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x582_inr_UnitPipe_iiCtr"))
  
  abstract class x582_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x561_reg = Flipped(new StandardInterface(ModuleParams.getParams("x561_reg_p").asInstanceOf[MemParams] ))
      val in_x461_realDRAM = Input(new FixedPoint(true, 64, 0))
      val in_b558 = Input(new FixedPoint(true, 32, 0))
      val in_x562_reg = Flipped(new StandardInterface(ModuleParams.getParams("x562_reg_p").asInstanceOf[MemParams] ))
      val in_x553 = Decoupled(new AppCommandDense(ModuleParams.getParams("x553_p").asInstanceOf[(Int,Int)] ))
      val in_x560_reg = Flipped(new StandardInterface(ModuleParams.getParams("x560_reg_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x561_reg = {io.in_x561_reg} ; io.in_x561_reg := DontCare
    def x461_realDRAM = {io.in_x461_realDRAM} 
    def b558 = {io.in_b558} 
    def x562_reg = {io.in_x562_reg} ; io.in_x562_reg := DontCare
    def x553 = {io.in_x553} 
    def x560_reg = {io.in_x560_reg} ; io.in_x560_reg := DontCare
  }
  def connectWires0(module: x582_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x561_reg.connectLedger(module.io.in_x561_reg)
    module.io.in_x461_realDRAM <> x461_realDRAM
    module.io.in_b558 <> b558
    x562_reg.connectLedger(module.io.in_x562_reg)
    module.io.in_x553 <> x553
    x560_reg.connectLedger(module.io.in_x560_reg)
  }
  val x461_realDRAM = list_x461_realDRAM(0)
  val b558 = list_x461_realDRAM(1)
  val x553 = list_x553(0)
  val x561_reg = list_x561_reg(0)
  val x562_reg = list_x561_reg(1)
  val x560_reg = list_x561_reg(2)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x582_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x582_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x582_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x582_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x582_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x582_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x582_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x582_inr_UnitPipe = Module(new InstrumentationCounter())
      val idles_x582_inr_UnitPipe = Module(new InstrumentationCounter())
      stalls_x582_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(x553.ready)
      idles_x582_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X582_instrctr, cycles_x582_inr_UnitPipe.io.count, iters_x582_inr_UnitPipe.io.count, stalls_x582_inr_UnitPipe.io.count, idles_x582_inr_UnitPipe.io.count)
      val x563 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x563""")
      val ensig0 = Wire(Bool())
      ensig0 := x553.ready
      x563.r := Math.arith_left_shift(b558, 1, Some(0.2), ensig0,"x563").r
      val x564 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x564""")
      x564.r := Math.arith_right_shift_div(x563, 4, Some(0.2), ensig0,"x564").r
      val x565 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x565""")
      x565.r := Math.arith_left_shift(x564, 4, Some(0.2), ensig0,"x565").r
      val x704 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x704""")
      x704.r := Math.arith_left_shift(x564, 6, Some(0.2), ensig0,"x704").r
      val x567_sub = Wire(new FixedPoint(true, 32, 0)).suggestName("""x567_sub""")
      x567_sub.r := Math.sub(x563,x565,Some(1.0), ensig0, Truncate, Wrapping, "x567_sub").r
      val x568_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x568_sum""")
      x568_sum.r := Math.add(x567_sub,2L.FP(true, 32, 0),Some(1.0), ensig0, Truncate, Wrapping, "x568_sum").r
      val x569_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x569_sum""")
      x569_sum.r := Math.add(x567_sub,17L.FP(true, 32, 0),Some(1.0), ensig0, Truncate, Wrapping, "x569_sum").r
      val x570 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x570""")
      x570.r := Math.arith_right_shift_div(x569_sum, 4, Some(0.2), ensig0,"x570").r
      val x571 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x571""")
      x571.r := Math.arith_left_shift(x570, 4, Some(0.2 + 1.0), ensig0,"x571").r
      val x705 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x705""")
      x705.r := Math.arith_left_shift(x570, 6, Some(0.2 + 1.0), ensig0,"x705").r
      val x573 = Wire(new FixedPoint(true, 64, 0)).suggestName("""x573""")
      x573.r := Math.fix2fix(x704, true, 64, 0, Some(0.0), ensig0, Truncate, Wrapping, "x573").r
      val x574 = x461_realDRAM
      val x575_sum = Wire(new FixedPoint(true, 64, 0)).suggestName("""x575_sum""")
      x575_sum.r := Math.add(x573,x574,Some(2.0), ensig0, Truncate, Wrapping, "x575_sum").r
      val x727 = Wire(new FixedPoint(true, 64, 0)).suggestName("x727_x575_sum_D1") 
      x727.r := getRetimed(x575_sum.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x576_tuple = Wire(UInt(97.W)).suggestName("""x576_tuple""")
      x576_tuple.r := ConvAndCat(false.B,x705.r,x727.r)
      val x577 = true.B
      val x728 = Wire(Bool()).suggestName("x728_x577_D3") 
      x728.r := getRetimed(x577.r, 3.toInt, io.sigsIn.backpressure & true.B)
      x553.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(3.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x728 & io.sigsIn.backpressure
      x553.bits.addr := x576_tuple(63,0)
      x553.bits.size := x576_tuple(95,64)
      val x579_wr_x560_banks = List[UInt]()
      val x579_wr_x560_ofs = List[UInt]()
      val x579_wr_x560_en = List[Bool](true.B)
      val x579_wr_x560_data = List[UInt](x567_sub.r)
      x560_reg.connectWPort(579, x579_wr_x560_banks, x579_wr_x560_ofs, x579_wr_x560_data, x579_wr_x560_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.6.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x580_wr_x561_banks = List[UInt]()
      val x580_wr_x561_ofs = List[UInt]()
      val x580_wr_x561_en = List[Bool](true.B)
      val x580_wr_x561_data = List[UInt](x568_sum.r)
      x561_reg.connectWPort(580, x580_wr_x561_banks, x580_wr_x561_ofs, x580_wr_x561_data, x580_wr_x561_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.6.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x581_wr_x562_banks = List[UInt]()
      val x581_wr_x562_ofs = List[UInt]()
      val x581_wr_x562_en = List[Bool](true.B)
      val x581_wr_x562_data = List[UInt](x571.r)
      x562_reg.connectWPort(581, x581_wr_x562_banks, x581_wr_x562_ofs, x581_wr_x562_data, x581_wr_x562_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(3.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x582_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x582_inr_UnitPipe **/
