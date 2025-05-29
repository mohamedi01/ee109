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

/** Hierarchy: x358 -> x383 -> x489 -> x491 **/
/** BEGIN None x358_inr_UnitPipe **/
class x358_inr_UnitPipe_kernel(
  list_x329_inDram: List[FixedPoint],
  list_x345_fifo: List[FIFOInterface],
  list_x344: List[DecoupledIO[AppCommandDense]],
  list_x326_argIn: List[UInt],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 2.4.toInt, myName = "x358_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(2.4.toInt, 2 + _root_.utils.math.log2Up(2.4.toInt), "x358_inr_UnitPipe_iiCtr"))
  
  abstract class x358_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x344 = Decoupled(new AppCommandDense(ModuleParams.getParams("x344_p").asInstanceOf[(Int,Int)] ))
      val in_x329_inDram = Input(new FixedPoint(true, 64, 0))
      val in_x345_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x345_fifo_p").asInstanceOf[MemParams] ))
      val in_x326_argIn = Input(UInt(64.W))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x344 = {io.in_x344} 
    def x329_inDram = {io.in_x329_inDram} 
    def x345_fifo = {io.in_x345_fifo} ; io.in_x345_fifo := DontCare
    def x326_argIn = {io.in_x326_argIn} 
  }
  def connectWires0(module: x358_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x344 <> x344
    module.io.in_x329_inDram <> x329_inDram
    x345_fifo.connectLedger(module.io.in_x345_fifo)
    module.io.in_x326_argIn <> x326_argIn
  }
  val x329_inDram = list_x329_inDram(0)
  val x345_fifo = list_x345_fifo(0)
  val x344 = list_x344(0)
  val x326_argIn = list_x326_argIn(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x358_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x358_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x358_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x347_rd_x326 = Wire(new FixedPoint(true, 32, 0))
      x347_rd_x326.r := x326_argIn.r
      val x348_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x348_sum""")
      val ensig0 = Wire(Bool())
      ensig0 := (~x345_fifo.full.D(2.4-1) | ~(x345_fifo.active(0).out)) & x344.ready
      x348_sum.r := Math.add(x347_rd_x326,15L.FP(true, 32, 0),Some(1.0), ensig0, Truncate, Wrapping, "x348_sum").r
      val x349 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x349""")
      x349.r := Math.arith_right_shift_div(x348_sum, 4, Some(0.2), ensig0,"x349").r
      val x350 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x350""")
      x350.r := Math.arith_left_shift(x349, 4, Some(0.2), ensig0,"x350").r
      val x487 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x487""")
      x487.r := Math.arith_left_shift(x349, 6, Some(0.2), ensig0,"x487").r
      val x352 = x329_inDram
      val x492 = Wire(new FixedPoint(true, 64, 0)).suggestName("x492_x352_D1") 
      x492.r := getRetimed(x352.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x353_tuple = Wire(UInt(97.W)).suggestName("""x353_tuple""")
      x353_tuple.r := ConvAndCat(true.B,x487.r,x492.r)
      val x354 = true.B
      val x493 = Wire(Bool()).suggestName("x493_x354_D1") 
      x493.r := getRetimed(x354.r, 1.toInt, io.sigsIn.backpressure & true.B)
      x344.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.4.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x493 & io.sigsIn.backpressure
      x344.bits.addr := x353_tuple(63,0)
      x344.bits.size := x353_tuple(95,64)
      val x494 = Wire(new FixedPoint(true, 32, 0)).suggestName("x494_x347_rd_x326_D1") 
      x494.r := getRetimed(x347_rd_x326.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x356_tuple = Wire(UInt(96.W)).suggestName("""x356_tuple""")
      x356_tuple.r := ConvAndCat(x494.r,0L.FP(true, 32, 0).r,x350.r)
      val x357_enq_x345_banks = List[UInt]()
      val x357_enq_x345_ofs = List[UInt]()
      val x357_enq_x345_en = List[Bool](true.B)
      val x357_enq_x345_data = List[UInt](x356_tuple.r)
      x345_fifo.connectWPort(357, x357_enq_x345_banks, x357_enq_x345_ofs, x357_enq_x345_data, x357_enq_x345_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      x345_fifo.connectAccessActivesIn(0, ((true.B)))
    }
    val module = Module(new x358_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x358_inr_UnitPipe **/
