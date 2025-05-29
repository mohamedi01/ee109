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

/** Hierarchy: x439 -> x476 -> x613 -> x614 **/
/** BEGIN None x439_inr_Foreach **/
class x439_inr_Foreach_kernel(
  list_x406_matDRAM: List[FixedPoint],
  list_x415_fifo: List[FIFOInterface],
  list_x414: List[DecoupledIO[AppCommandDense]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 56.2.toInt, myName = "x439_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(56.2.toInt, 2 + _root_.utils.math.log2Up(56.2.toInt), "x439_inr_Foreach_iiCtr"))
  
  abstract class x439_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x406_matDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x414 = Decoupled(new AppCommandDense(ModuleParams.getParams("x414_p").asInstanceOf[(Int,Int)] ))
      val in_x415_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x415_fifo_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x406_matDRAM = {io.in_x406_matDRAM} 
    def x414 = {io.in_x414} 
    def x415_fifo = {io.in_x415_fifo} ; io.in_x415_fifo := DontCare
  }
  def connectWires0(module: x439_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x406_matDRAM <> x406_matDRAM
    module.io.in_x414 <> x414
    x415_fifo.connectLedger(module.io.in_x415_fifo)
  }
  val x406_matDRAM = list_x406_matDRAM(0)
  val x415_fifo = list_x415_fifo(0)
  val x414 = list_x414(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x439_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x439_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x439_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x439_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x439_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x439_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x439_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x439_inr_Foreach = Module(new InstrumentationCounter())
      val idles_x439_inr_Foreach = Module(new InstrumentationCounter())
      stalls_x439_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((~x415_fifo.full.D(56.2-1) | ~(x415_fifo.active(0).out)) & x414.ready)
      idles_x439_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X439_instrctr, cycles_x439_inr_Foreach.io.count, iters_x439_inr_Foreach.io.count, stalls_x439_inr_Foreach.io.count, idles_x439_inr_Foreach.io.count)
      val b419 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b419.suggestName("b419")
      val b420 = ~io.sigsIn.cchainOutputs.head.oobs(0); b420.suggestName("b420")
      val x602 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x602""")
      val ensig0 = Wire(Bool())
      ensig0 := (~x415_fifo.full.D(56.2-1) | ~(x415_fifo.active(0).out)) & x414.ready
      x602.r := Math.arith_left_shift(b419, 1, Some(0.2), ensig0,"x602").r
      val x603_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x603_sum""")
      x603_sum.r := Math.add(x602,b419,Some(1.0), ensig0, Truncate, Wrapping, "x603_sum").r
      val x422_div = Wire(new FixedPoint(true, 32, 0)).suggestName("""x422_div""")
      x422_div.r := (Math.div(x603_sum, 21L.FP(true, 32, 0), Some(20.0), ensig0, Truncate, Wrapping, "x422_div")).r
      val x423_mul = Wire(new FixedPoint(true, 32, 0)).suggestName("""x423_mul""")
      x423_mul.r := (Math.mul(x422_div, 21L.FP(true, 32, 0), Some(6.0), ensig0, Truncate, Wrapping, "x423_mul")).r
      val x604 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x604""")
      x604.r := Math.arith_left_shift(x422_div, 6, Some(0.2), ensig0,"x604").r
      val x605_sub = Wire(new FixedPoint(true, 32, 0)).suggestName("""x605_sub""")
      x605_sub.r := Math.sub(x604,x422_div,Some(1.0), ensig0, Truncate, Wrapping, "x605_sub").r
      val x616 = Wire(new FixedPoint(true, 32, 0)).suggestName("x616_x603_sum_D26") 
      x616.r := getRetimed(x603_sum.r, 26.toInt, io.sigsIn.backpressure & true.B)
      val x425_sub = Wire(new FixedPoint(true, 32, 0)).suggestName("""x425_sub""")
      x425_sub.r := Math.sub(x616,x423_mul,Some(1.0), ensig0, Truncate, Wrapping, "x425_sub").r
      val x426_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x426_sum""")
      x426_sum.r := Math.add(x425_sub,3L.FP(true, 32, 0),Some(1.0), ensig0, Truncate, Wrapping, "x426_sum").r
      val x427_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x427_sum""")
      x427_sum.r := Math.add(x425_sub,23L.FP(true, 32, 0),Some(1.0), ensig0, Truncate, Wrapping, "x427_sum").r
      val x428_div = Wire(new FixedPoint(true, 32, 0)).suggestName("""x428_div""")
      x428_div.r := (Math.div(x427_sum, 21L.FP(true, 32, 0), Some(20.0), ensig0, Truncate, Wrapping, "x428_div")).r
      val x429_mul = Wire(new FixedPoint(true, 32, 0)).suggestName("""x429_mul""")
      x429_mul.r := (Math.mul(x428_div, 21L.FP(true, 32, 0), Some(6.0), ensig0, Truncate, Wrapping, "x429_mul")).r
      val x606 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x606""")
      x606.r := Math.arith_left_shift(x428_div, 6, Some(0.2), ensig0,"x606").r
      val x607_sub = Wire(new FixedPoint(true, 32, 0)).suggestName("""x607_sub""")
      x607_sub.r := Math.sub(x606,x428_div,Some(1.0), ensig0, Truncate, Wrapping, "x607_sub").r
      val x431 = Wire(new FixedPoint(true, 64, 0)).suggestName("""x431""")
      x431.r := Math.fix2fix(x605_sub, true, 64, 0, Some(0.0), ensig0, Truncate, Wrapping, "x431").r
      val x432 = x406_matDRAM
      val x617 = Wire(new FixedPoint(true, 64, 0)).suggestName("x617_x432_D22") 
      x617.r := getRetimed(x432.r, 22.toInt, io.sigsIn.backpressure & true.B)
      val x433_sum = Wire(new FixedPoint(true, 64, 0)).suggestName("""x433_sum""")
      x433_sum.r := Math.add(x431,x617,Some(2.0), ensig0, Truncate, Wrapping, "x433_sum").r
      val x618 = Wire(new FixedPoint(true, 64, 0)).suggestName("x618_x433_sum_D26") 
      x618.r := getRetimed(x433_sum.r, 26.toInt, io.sigsIn.backpressure & true.B)
      val x434_tuple = Wire(UInt(97.W)).suggestName("""x434_tuple""")
      x434_tuple.r := ConvAndCat(true.B,x607_sub.r,x618.r)
      val x435 = true.B
      val x619 = Wire(Bool()).suggestName("x619_b420_D50") 
      x619.r := getRetimed(b420.r, 50.toInt, io.sigsIn.backpressure & true.B)
      val x620 = Wire(Bool()).suggestName("x620_x435_D50") 
      x620.r := getRetimed(x435.r, 50.toInt, io.sigsIn.backpressure & true.B)
      x414.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(50.4.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x620&x619 & io.sigsIn.backpressure
      x414.bits.addr := x434_tuple(63,0)
      x414.bits.size := x434_tuple(95,64)
      val x621 = Wire(new FixedPoint(true, 32, 0)).suggestName("x621_x426_sum_D26") 
      x621.r := getRetimed(x426_sum.r, 26.toInt, io.sigsIn.backpressure & true.B)
      val x622 = Wire(new FixedPoint(true, 32, 0)).suggestName("x622_x425_sub_D27") 
      x622.r := getRetimed(x425_sub.r, 27.toInt, io.sigsIn.backpressure & true.B)
      val x437_tuple = Wire(UInt(96.W)).suggestName("""x437_tuple""")
      x437_tuple.r := ConvAndCat(x621.r,x622.r,x429_mul.r)
      val x623 = Wire(Bool()).suggestName("x623_b420_D55") 
      x623.r := getRetimed(b420.r, 55.toInt, io.sigsIn.backpressure & true.B)
      val x438_enq_x415_banks = List[UInt]()
      val x438_enq_x415_ofs = List[UInt]()
      val x438_enq_x415_en = List[Bool](true.B)
      val x438_enq_x415_data = List[UInt](x437_tuple.r)
      x415_fifo.connectWPort(438, x438_enq_x415_banks, x438_enq_x415_ofs, x438_enq_x415_data, x438_enq_x415_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(55.2.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B & x623))
      x415_fifo.connectAccessActivesIn(0, ((true.B & x623)))
    }
    val module = Module(new x439_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x439_inr_Foreach **/
