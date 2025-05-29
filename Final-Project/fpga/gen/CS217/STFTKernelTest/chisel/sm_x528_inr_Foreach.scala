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

/** Hierarchy: x528 -> x552 -> x710 **/
/** BEGIN None x528_inr_Foreach **/
class x528_inr_Foreach_kernel(
  list_b515: List[Bool],
  list_b514: List[FixedPoint],
  list_x465_inSRAM_0: List[StandardInterface],
  list_x466_frame_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 12.2.toInt, myName = "x528_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x528_inr_Foreach_iiCtr"))
  
  abstract class x528_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b514 = Input(new FixedPoint(true, 32, 0))
      val in_b515 = Input(Bool())
      val in_x466_frame_0 = Flipped(new NBufInterface(ModuleParams.getParams("x466_frame_0_p").asInstanceOf[NBufParams] ))
      val in_x465_inSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x465_inSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x464_winSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x464_winSRAM_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def b514 = {io.in_b514} 
    def b515 = {io.in_b515} 
    def x466_frame_0 = {io.in_x466_frame_0} ; io.in_x466_frame_0 := DontCare
    def x465_inSRAM_0 = {io.in_x465_inSRAM_0} ; io.in_x465_inSRAM_0 := DontCare
    def x464_winSRAM_0 = {io.in_x464_winSRAM_0} ; io.in_x464_winSRAM_0 := DontCare
  }
  def connectWires0(module: x528_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b514 <> b514
    module.io.in_b515 <> b515
    x466_frame_0.connectLedger(module.io.in_x466_frame_0)
    x465_inSRAM_0.connectLedger(module.io.in_x465_inSRAM_0)
    x464_winSRAM_0.connectLedger(module.io.in_x464_winSRAM_0)
  }
  val b515 = list_b515(0)
  val b514 = list_b514(0)
  val x465_inSRAM_0 = list_x465_inSRAM_0(0)
  val x464_winSRAM_0 = list_x465_inSRAM_0(1)
  val x466_frame_0 = list_x466_frame_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x528_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x528_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x528_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x528_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x528_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x528_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x528_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X528_instrctr, cycles_x528_inr_Foreach.io.count, iters_x528_inr_Foreach.io.count, 0.U, 0.U)
      val b518 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b518.suggestName("b518")
      val b519 = ~io.sigsIn.cchainOutputs.head.oobs(0); b519.suggestName("b519")
      val x520 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x520""")
      x520.r := Math.arith_left_shift(b514, 1, Some(0.2), true.B,"x520").r
      val x521_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x521_sum""")
      x521_sum.r := Math.add(x520,b518,Some(1.0), true.B, Truncate, Wrapping, "x521_sum").r
      val x715 = Wire(Bool()).suggestName("x715_b515_D1") 
      x715.r := getRetimed(b515.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x716 = Wire(Bool()).suggestName("x716_b519_D1") 
      x716.r := getRetimed(b519.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x522_rd = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x522_rd""")
      val x522_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x522_rd_ofs = List[UInt](x521_sum.r)
      val x522_rd_en = List[Bool](true.B)
      val x522_rd_shared_en = ((io.sigsIn.forwardpressure).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.2.toInt, rr, io.sigsIn.backpressure & true.B) && x716 & x715 ).suggestName("x522_rd_shared_en")
      x522_rd.toSeq.zip(x465_inSRAM_0.connectRPort(522, x522_rd_banks, x522_rd_ofs, io.sigsIn.backpressure, x522_rd_en.map(_ && x522_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x523 = VecApply(x522,0)
      val x523_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x523_elem_0""")
      x523_elem_0.r := x522_rd(0).r
      val x524_rd = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x524_rd""")
      val x524_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x524_rd_ofs = List[UInt](b518.r)
      val x524_rd_en = List[Bool](true.B)
      val x524_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && b519 & b515 ).suggestName("x524_rd_shared_en")
      x524_rd.toSeq.zip(x464_winSRAM_0.connectRPort(524, x524_rd_banks, x524_rd_ofs, io.sigsIn.backpressure, x524_rd_en.map(_ && x524_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x525 = VecApply(x524,0)
      val x525_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x525_elem_0""")
      x525_elem_0.r := x524_rd(0).r
      val x717 = Wire(new FloatingPoint(24, 8)).suggestName("x717_x525_elem_0_D1") 
      x717.r := getRetimed(x525_elem_0.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x526 = Wire(new FloatingPoint(24, 8)).suggestName("""x526""")
      x526.r := Math.fmul(x523_elem_0, x717, Some(8.0), true.B,"x526").r
      val x718 = Wire(Bool()).suggestName("x718_b515_D11") 
      x718.r := getRetimed(b515.r, 11.toInt, io.sigsIn.backpressure & true.B)
      val x719 = Wire(Bool()).suggestName("x719_b519_D11") 
      x719.r := getRetimed(b519.r, 11.toInt, io.sigsIn.backpressure & true.B)
      val x720 = Wire(new FixedPoint(true, 32, 0)).suggestName("x720_b518_D11") 
      x720.r := getRetimed(b518.r, 11.toInt, io.sigsIn.backpressure & true.B)
      val x527_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x527_wr_ofs = List[UInt](x720.r)
      val x527_wr_en = List[Bool](true.B)
      val x527_wr_data = List[UInt](x526.r)
      x466_frame_0.connectWPort(527, x527_wr_banks, x527_wr_ofs, x527_wr_data, x527_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(11.2.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x719 & x718))
      x466_frame_0.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 0)
    }
    val module = Module(new x528_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x528_inr_Foreach **/
