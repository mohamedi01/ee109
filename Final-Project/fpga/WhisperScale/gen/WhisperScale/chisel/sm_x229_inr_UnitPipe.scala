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

/** Hierarchy: x229 -> x254 -> x196 **/
/** BEGIN None x229_inr_UnitPipe **/
class x229_inr_UnitPipe_kernel(
  list_x209_inDram: List[FixedPoint],
  list_x216_fifo: List[FIFOInterface],
  list_x215: List[DecoupledIO[AppCommandDense]],
  list_x206_argIn: List[UInt],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 2.4.toInt, myName = "x229_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(2.4.toInt, 2 + _root_.utils.math.log2Up(2.4.toInt), "x229_inr_UnitPipe_iiCtr"))
  
  abstract class x229_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x216_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x216_fifo_p").asInstanceOf[MemParams] ))
      val in_x206_argIn = Input(UInt(64.W))
      val in_x209_inDram = Input(new FixedPoint(true, 64, 0))
      val in_x215 = Decoupled(new AppCommandDense(ModuleParams.getParams("x215_p").asInstanceOf[(Int,Int)] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x216_fifo = {io.in_x216_fifo} ; io.in_x216_fifo := DontCare
    def x206_argIn = {io.in_x206_argIn} 
    def x209_inDram = {io.in_x209_inDram} 
    def x215 = {io.in_x215} 
  }
  def connectWires0(module: x229_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x216_fifo.connectLedger(module.io.in_x216_fifo)
    module.io.in_x206_argIn <> x206_argIn
    module.io.in_x209_inDram <> x209_inDram
    module.io.in_x215 <> x215
  }
  val x209_inDram = list_x209_inDram(0)
  val x216_fifo = list_x216_fifo(0)
  val x215 = list_x215(0)
  val x206_argIn = list_x206_argIn(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x229_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x229_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x229_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x218_rd_x206 = Wire(new FixedPoint(true, 32, 0))
      x218_rd_x206.r := x206_argIn.r
      val x219_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x219_sum""")
      val ensig0 = Wire(Bool())
      ensig0 := (~x216_fifo.full.D(2.4-1) | ~(x216_fifo.active(0).out)) & x215.ready
      x219_sum.r := Math.add(x218_rd_x206,15L.FP(true, 32, 0),Some(1.0), ensig0, Truncate, Wrapping, "x219_sum").r
      val x220 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x220""")
      x220.r := Math.arith_right_shift_div(x219_sum, 4, Some(0.2), ensig0,"x220").r
      val x221 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x221""")
      x221.r := Math.arith_left_shift(x220, 4, Some(0.2), ensig0,"x221").r
      val x306 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x306""")
      x306.r := Math.arith_left_shift(x220, 6, Some(0.2), ensig0,"x306").r
      val x223 = x209_inDram
      val x308 = Wire(new FixedPoint(true, 64, 0)).suggestName("x308_x223_D1") 
      x308.r := getRetimed(x223.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x224_tuple = Wire(UInt(97.W)).suggestName("""x224_tuple""")
      x224_tuple.r := ConvAndCat(true.B,x306.r,x308.r)
      val x225 = true.B
      val x309 = Wire(Bool()).suggestName("x309_x225_D1") 
      x309.r := getRetimed(x225.r, 1.toInt, io.sigsIn.backpressure & true.B)
      x215.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.4.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x309 & io.sigsIn.backpressure
      x215.bits.addr := x224_tuple(63,0)
      x215.bits.size := x224_tuple(95,64)
      val x310 = Wire(new FixedPoint(true, 32, 0)).suggestName("x310_x218_rd_x206_D1") 
      x310.r := getRetimed(x218_rd_x206.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x227_tuple = Wire(UInt(96.W)).suggestName("""x227_tuple""")
      x227_tuple.r := ConvAndCat(x310.r,0L.FP(true, 32, 0).r,x221.r)
      val x228_enq_x216_banks = List[UInt]()
      val x228_enq_x216_ofs = List[UInt]()
      val x228_enq_x216_en = List[Bool](true.B)
      val x228_enq_x216_data = List[UInt](x227_tuple.r)
      x216_fifo.connectWPort(228, x228_enq_x216_banks, x228_enq_x216_ofs, x228_enq_x216_data, x228_enq_x216_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      x216_fifo.connectAccessActivesIn(0, ((true.B)))
    }
    val module = Module(new x229_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x229_inr_UnitPipe **/
