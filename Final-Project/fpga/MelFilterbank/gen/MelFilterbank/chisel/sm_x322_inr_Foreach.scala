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

/** Hierarchy: x322 -> x359 -> x450 -> x453 **/
/** BEGIN None x322_inr_Foreach **/
class x322_inr_Foreach_kernel(
  list_x291_matDram: List[FixedPoint],
  list_x298_fifo: List[FIFOInterface],
  list_x297: List[DecoupledIO[AppCommandDense]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 9.8.toInt, myName = "x322_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(9.8.toInt, 2 + _root_.utils.math.log2Up(9.8.toInt), "x322_inr_Foreach_iiCtr"))
  
  abstract class x322_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x297 = Decoupled(new AppCommandDense(ModuleParams.getParams("x297_p").asInstanceOf[(Int,Int)] ))
      val in_x298_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x298_fifo_p").asInstanceOf[MemParams] ))
      val in_x291_matDram = Input(new FixedPoint(true, 64, 0))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x297 = {io.in_x297} 
    def x298_fifo = {io.in_x298_fifo} ; io.in_x298_fifo := DontCare
    def x291_matDram = {io.in_x291_matDram} 
  }
  def connectWires0(module: x322_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x297 <> x297
    x298_fifo.connectLedger(module.io.in_x298_fifo)
    module.io.in_x291_matDram <> x291_matDram
  }
  val x291_matDram = list_x291_matDram(0)
  val x298_fifo = list_x298_fifo(0)
  val x297 = list_x297(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x322_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x322_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x322_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val b302 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b302.suggestName("b302")
      val b303 = ~io.sigsIn.cchainOutputs.head.oobs(0); b303.suggestName("b303")
      val x304_mul = Wire(new FixedPoint(true, 32, 0)).suggestName("""x304_mul""")
      val ensig0 = Wire(Bool())
      ensig0 := x297.ready & (~x298_fifo.full.D(9.8-1) | ~(x298_fifo.active(0).out))
      x304_mul.r := (Math.mul(b302, 201L.FP(true, 32, 0), Some(6.0), ensig0, Truncate, Wrapping, "x304_mul")).r
      val x305 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x305""")
      x305.r := Math.arith_right_shift_div(x304_mul, 4, Some(0.2), ensig0,"x305").r
      val x306 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x306""")
      x306.r := Math.arith_left_shift(x305, 4, Some(0.2), ensig0,"x306").r
      val x445 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x445""")
      x445.r := Math.arith_left_shift(x305, 6, Some(0.2), ensig0,"x445").r
      val x308_sub = Wire(new FixedPoint(true, 32, 0)).suggestName("""x308_sub""")
      x308_sub.r := Math.sub(x304_mul,x306,Some(1.0), ensig0, Truncate, Wrapping, "x308_sub").r
      val x309_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x309_sum""")
      x309_sum.r := Math.add(x308_sub,201L.FP(true, 32, 0),Some(1.0), ensig0, Truncate, Wrapping, "x309_sum").r
      val x310_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x310_sum""")
      x310_sum.r := Math.add(x308_sub,216L.FP(true, 32, 0),Some(1.0), ensig0, Truncate, Wrapping, "x310_sum").r
      val x311 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x311""")
      x311.r := Math.arith_right_shift_div(x310_sum, 4, Some(0.2), ensig0,"x311").r
      val x312 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x312""")
      x312.r := Math.arith_left_shift(x311, 4, Some(0.2), ensig0,"x312").r
      val x446 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x446""")
      x446.r := Math.arith_left_shift(x311, 6, Some(0.2), ensig0,"x446").r
      val x314 = Wire(new FixedPoint(true, 64, 0)).suggestName("""x314""")
      x314.r := Math.fix2fix(x445, true, 64, 0, Some(0.0), ensig0, Truncate, Wrapping, "x314").r
      val x315 = x291_matDram
      val x454 = Wire(new FixedPoint(true, 64, 0)).suggestName("x454_x315_D6") 
      x454.r := getRetimed(x315.r, 6.toInt, io.sigsIn.backpressure & true.B)
      val x316_sum = Wire(new FixedPoint(true, 64, 0)).suggestName("""x316_sum""")
      x316_sum.r := Math.add(x314,x454,Some(2.0), ensig0, Truncate, Wrapping, "x316_sum").r
      val x317_tuple = Wire(UInt(97.W)).suggestName("""x317_tuple""")
      x317_tuple.r := ConvAndCat(true.B,x446.r,x316_sum.r)
      val x318 = true.B
      val x455 = Wire(Bool()).suggestName("x455_b303_D8") 
      x455.r := getRetimed(b303.r, 8.toInt, io.sigsIn.backpressure & true.B)
      val x456 = Wire(Bool()).suggestName("x456_x318_D8") 
      x456.r := getRetimed(x318.r, 8.toInt, io.sigsIn.backpressure & true.B)
      x297.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(8.8.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x456&x455 & io.sigsIn.backpressure
      x297.bits.addr := x317_tuple(63,0)
      x297.bits.size := x317_tuple(95,64)
      val x457 = Wire(new FixedPoint(true, 32, 0)).suggestName("x457_x308_sub_D1") 
      x457.r := getRetimed(x308_sub.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x320_tuple = Wire(UInt(96.W)).suggestName("""x320_tuple""")
      x320_tuple.r := ConvAndCat(x309_sum.r,x457.r,x312.r)
      val x321_enq_x298_banks = List[UInt]()
      val x321_enq_x298_ofs = List[UInt]()
      val x321_enq_x298_en = List[Bool](true.B)
      val x321_enq_x298_data = List[UInt](x320_tuple.r)
      x298_fifo.connectWPort(321, x321_enq_x298_banks, x321_enq_x298_ofs, x321_enq_x298_data, x321_enq_x298_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(8.8.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B & x455))
      x298_fifo.connectAccessActivesIn(0, ((true.B & x455)))
    }
    val module = Module(new x322_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnrolledForeach x322_inr_Foreach **/
