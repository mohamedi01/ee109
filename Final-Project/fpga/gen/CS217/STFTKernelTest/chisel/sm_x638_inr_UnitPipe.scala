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

/** Hierarchy: x638 -> x658 -> x663 -> x664 -> x709 -> x710 **/
/** BEGIN None x638_inr_UnitPipe **/
class x638_inr_UnitPipe_kernel(
  list_b614: List[FixedPoint],
  list_x609: List[DecoupledIO[AppCommandDense]],
  list_x618_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 4.0.toInt, myName = "x638_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x638_inr_UnitPipe_iiCtr"))
  
  abstract class x638_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b614 = Input(new FixedPoint(true, 32, 0))
      val in_x618_reg = Flipped(new StandardInterface(ModuleParams.getParams("x618_reg_p").asInstanceOf[MemParams] ))
      val in_x616_reg = Flipped(new StandardInterface(ModuleParams.getParams("x616_reg_p").asInstanceOf[MemParams] ))
      val in_x609 = Decoupled(new AppCommandDense(ModuleParams.getParams("x609_p").asInstanceOf[(Int,Int)] ))
      val in_x462_imagDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x617_reg = Flipped(new StandardInterface(ModuleParams.getParams("x617_reg_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def b614 = {io.in_b614} 
    def x618_reg = {io.in_x618_reg} ; io.in_x618_reg := DontCare
    def x616_reg = {io.in_x616_reg} ; io.in_x616_reg := DontCare
    def x609 = {io.in_x609} 
    def x462_imagDRAM = {io.in_x462_imagDRAM} 
    def x617_reg = {io.in_x617_reg} ; io.in_x617_reg := DontCare
  }
  def connectWires0(module: x638_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b614 <> b614
    x618_reg.connectLedger(module.io.in_x618_reg)
    x616_reg.connectLedger(module.io.in_x616_reg)
    module.io.in_x609 <> x609
    module.io.in_x462_imagDRAM <> x462_imagDRAM
    x617_reg.connectLedger(module.io.in_x617_reg)
  }
  val b614 = list_b614(0)
  val x462_imagDRAM = list_b614(1)
  val x609 = list_x609(0)
  val x618_reg = list_x618_reg(0)
  val x616_reg = list_x618_reg(1)
  val x617_reg = list_x618_reg(2)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x638_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x638_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x638_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x638_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x638_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x638_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x638_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x638_inr_UnitPipe = Module(new InstrumentationCounter())
      val idles_x638_inr_UnitPipe = Module(new InstrumentationCounter())
      stalls_x638_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(x609.ready)
      idles_x638_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X638_instrctr, cycles_x638_inr_UnitPipe.io.count, iters_x638_inr_UnitPipe.io.count, stalls_x638_inr_UnitPipe.io.count, idles_x638_inr_UnitPipe.io.count)
      val x619 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x619""")
      val ensig0 = Wire(Bool())
      ensig0 := x609.ready
      x619.r := Math.arith_left_shift(b614, 1, Some(0.2), ensig0,"x619").r
      val x620 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x620""")
      x620.r := Math.arith_right_shift_div(x619, 4, Some(0.2), ensig0,"x620").r
      val x621 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x621""")
      x621.r := Math.arith_left_shift(x620, 4, Some(0.2), ensig0,"x621").r
      val x706 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x706""")
      x706.r := Math.arith_left_shift(x620, 6, Some(0.2), ensig0,"x706").r
      val x623_sub = Wire(new FixedPoint(true, 32, 0)).suggestName("""x623_sub""")
      x623_sub.r := Math.sub(x619,x621,Some(1.0), ensig0, Truncate, Wrapping, "x623_sub").r
      val x624_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x624_sum""")
      x624_sum.r := Math.add(x623_sub,2L.FP(true, 32, 0),Some(1.0), ensig0, Truncate, Wrapping, "x624_sum").r
      val x625_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x625_sum""")
      x625_sum.r := Math.add(x623_sub,17L.FP(true, 32, 0),Some(1.0), ensig0, Truncate, Wrapping, "x625_sum").r
      val x626 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x626""")
      x626.r := Math.arith_right_shift_div(x625_sum, 4, Some(0.2), ensig0,"x626").r
      val x627 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x627""")
      x627.r := Math.arith_left_shift(x626, 4, Some(0.2 + 1.0), ensig0,"x627").r
      val x707 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x707""")
      x707.r := Math.arith_left_shift(x626, 6, Some(0.2 + 1.0), ensig0,"x707").r
      val x629 = Wire(new FixedPoint(true, 64, 0)).suggestName("""x629""")
      x629.r := Math.fix2fix(x706, true, 64, 0, Some(0.0), ensig0, Truncate, Wrapping, "x629").r
      val x630 = x462_imagDRAM
      val x631_sum = Wire(new FixedPoint(true, 64, 0)).suggestName("""x631_sum""")
      x631_sum.r := Math.add(x629,x630,Some(2.0), ensig0, Truncate, Wrapping, "x631_sum").r
      val x734 = Wire(new FixedPoint(true, 64, 0)).suggestName("x734_x631_sum_D1") 
      x734.r := getRetimed(x631_sum.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x632_tuple = Wire(UInt(97.W)).suggestName("""x632_tuple""")
      x632_tuple.r := ConvAndCat(false.B,x707.r,x734.r)
      val x633 = true.B
      val x735 = Wire(Bool()).suggestName("x735_x633_D3") 
      x735.r := getRetimed(x633.r, 3.toInt, io.sigsIn.backpressure & true.B)
      x609.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(3.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x735 & io.sigsIn.backpressure
      x609.bits.addr := x632_tuple(63,0)
      x609.bits.size := x632_tuple(95,64)
      val x635_wr_x616_banks = List[UInt]()
      val x635_wr_x616_ofs = List[UInt]()
      val x635_wr_x616_en = List[Bool](true.B)
      val x635_wr_x616_data = List[UInt](x623_sub.r)
      x616_reg.connectWPort(635, x635_wr_x616_banks, x635_wr_x616_ofs, x635_wr_x616_data, x635_wr_x616_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.6.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x636_wr_x617_banks = List[UInt]()
      val x636_wr_x617_ofs = List[UInt]()
      val x636_wr_x617_en = List[Bool](true.B)
      val x636_wr_x617_data = List[UInt](x624_sum.r)
      x617_reg.connectWPort(636, x636_wr_x617_banks, x636_wr_x617_ofs, x636_wr_x617_data, x636_wr_x617_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.6.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x637_wr_x618_banks = List[UInt]()
      val x637_wr_x618_ofs = List[UInt]()
      val x637_wr_x618_en = List[Bool](true.B)
      val x637_wr_x618_data = List[UInt](x627.r)
      x618_reg.connectWPort(637, x637_wr_x618_banks, x637_wr_x618_ofs, x637_wr_x618_data, x637_wr_x618_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(3.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x638_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x638_inr_UnitPipe **/
