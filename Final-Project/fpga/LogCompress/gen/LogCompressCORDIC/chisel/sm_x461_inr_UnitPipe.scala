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

/** Hierarchy: x461 -> x476 -> x481 -> x482 -> x490 -> x491 **/
/** BEGIN None x461_inr_UnitPipe **/
class x461_inr_UnitPipe_kernel(
  list_x330_outDram: List[FixedPoint],
  list_x445: List[DecoupledIO[AppCommandDense]],
  list_x449_reg: List[StandardInterface],
  list_x326_argIn: List[UInt],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 2.4.toInt, myName = "x461_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x461_inr_UnitPipe_iiCtr"))
  
  abstract class x461_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x449_reg = Flipped(new StandardInterface(ModuleParams.getParams("x449_reg_p").asInstanceOf[MemParams] ))
      val in_x448_reg = Flipped(new StandardInterface(ModuleParams.getParams("x448_reg_p").asInstanceOf[MemParams] ))
      val in_x445 = Decoupled(new AppCommandDense(ModuleParams.getParams("x445_p").asInstanceOf[(Int,Int)] ))
      val in_x330_outDram = Input(new FixedPoint(true, 64, 0))
      val in_x326_argIn = Input(UInt(64.W))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x449_reg = {io.in_x449_reg} ; io.in_x449_reg := DontCare
    def x448_reg = {io.in_x448_reg} ; io.in_x448_reg := DontCare
    def x445 = {io.in_x445} 
    def x330_outDram = {io.in_x330_outDram} 
    def x326_argIn = {io.in_x326_argIn} 
  }
  def connectWires0(module: x461_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x449_reg.connectLedger(module.io.in_x449_reg)
    x448_reg.connectLedger(module.io.in_x448_reg)
    module.io.in_x445 <> x445
    module.io.in_x330_outDram <> x330_outDram
    module.io.in_x326_argIn <> x326_argIn
  }
  val x330_outDram = list_x330_outDram(0)
  val x445 = list_x445(0)
  val x449_reg = list_x449_reg(0)
  val x448_reg = list_x449_reg(1)
  val x326_argIn = list_x326_argIn(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x461_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x461_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x461_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x450_rd_x326 = Wire(new FixedPoint(true, 32, 0))
      x450_rd_x326.r := x326_argIn.r
      val x451_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x451_sum""")
      val ensig0 = Wire(Bool())
      ensig0 := x445.ready
      x451_sum.r := Math.add(x450_rd_x326,15L.FP(true, 32, 0),Some(1.0), ensig0, Truncate, Wrapping, "x451_sum").r
      val x452 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x452""")
      x452.r := Math.arith_right_shift_div(x451_sum, 4, Some(0.2), ensig0,"x452").r
      val x453 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x453""")
      x453.r := Math.arith_left_shift(x452, 4, Some(0.2), ensig0,"x453").r
      val x488 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x488""")
      x488.r := Math.arith_left_shift(x452, 6, Some(0.2), ensig0,"x488").r
      val x455 = x330_outDram
      val x511 = Wire(new FixedPoint(true, 64, 0)).suggestName("x511_x455_D1") 
      x511.r := getRetimed(x455.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x456_tuple = Wire(UInt(97.W)).suggestName("""x456_tuple""")
      x456_tuple.r := ConvAndCat(false.B,x488.r,x511.r)
      val x457 = true.B
      val x512 = Wire(Bool()).suggestName("x512_x457_D1") 
      x512.r := getRetimed(x457.r, 1.toInt, io.sigsIn.backpressure & true.B)
      x445.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.4.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x512 & io.sigsIn.backpressure
      x445.bits.addr := x456_tuple(63,0)
      x445.bits.size := x456_tuple(95,64)
      val x459_wr_x448_banks = List[UInt]()
      val x459_wr_x448_ofs = List[UInt]()
      val x459_wr_x448_en = List[Bool](true.B)
      val x459_wr_x448_data = List[UInt](x450_rd_x326.r)
      x448_reg.connectWPort(459, x459_wr_x448_banks, x459_wr_x448_ofs, x459_wr_x448_data, x459_wr_x448_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x460_wr_x449_banks = List[UInt]()
      val x460_wr_x449_ofs = List[UInt]()
      val x460_wr_x449_en = List[Bool](true.B)
      val x460_wr_x449_data = List[UInt](x453.r)
      x449_reg.connectWPort(460, x460_wr_x449_banks, x460_wr_x449_ofs, x460_wr_x449_data, x460_wr_x449_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x461_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x461_inr_UnitPipe **/
