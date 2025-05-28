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

/** Hierarchy: x350 -> x244 **/
/** BEGIN None x350_inr_Foreach **/
class x350_inr_Foreach_kernel(
  list_x280_inSRAM_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 63.5.toInt, myName = "x350_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x350_inr_Foreach_iiCtr"))
  
  abstract class x350_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x280_inSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x280_inSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x281_outSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x281_outSRAM_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x280_inSRAM_0 = {io.in_x280_inSRAM_0} ; io.in_x280_inSRAM_0 := DontCare
    def x281_outSRAM_0 = {io.in_x281_outSRAM_0} ; io.in_x281_outSRAM_0 := DontCare
  }
  def connectWires0(module: x350_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x280_inSRAM_0.connectLedger(module.io.in_x280_inSRAM_0)
    x281_outSRAM_0.connectLedger(module.io.in_x281_outSRAM_0)
  }
  val x280_inSRAM_0 = list_x280_inSRAM_0(0)
  val x281_outSRAM_0 = list_x280_inSRAM_0(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x350_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x350_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x350_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x350_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x350_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x350_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x350_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X350_instrctr, cycles_x350_inr_Foreach.io.count, iters_x350_inr_Foreach.io.count, 0.U, 0.U)
      val b319 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b319.suggestName("b319")
      val b320 = ~io.sigsIn.cchainOutputs.head.oobs(0); b320.suggestName("b320")
      val x321_rd = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x321_rd""")
      val x321_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x321_rd_ofs = List[UInt](b319.r)
      val x321_rd_en = List[Bool](true.B)
      val x321_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && b320 ).suggestName("x321_rd_shared_en")
      x321_rd.toSeq.zip(x280_inSRAM_0.connectRPort(321, x321_rd_banks, x321_rd_ofs, io.sigsIn.backpressure, x321_rd_en.map(_ && x321_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x322 = VecApply(x321,0)
      val x322_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x322_elem_0""")
      x322_elem_0.r := x321_rd(0).r
      val x323 = Wire(new FloatingPoint(24, 8)).suggestName("""x323""")
      x323.r := Math.fmul(x322_elem_0, 32767.00000000000000.FlP(24, 8), Some(8.0), true.B,"x323").r
      val x324 = Wire(Bool()).suggestName("""x324""")
      x324.r := Math.flt(x323, -32768.FlP(24, 8), Some(0.0), true.B,"x324").r
      val x325 = Wire(Bool()).suggestName("""x325""")
      x325.r := Math.flt(32767.00000000000000.FlP(24, 8), x323, Some(0.0), true.B,"x325").r
      val x326 = Wire(new FloatingPoint(24, 8)).suggestName("""x326""")
      x326.r := Mux((x325), 32767.00000000000000.FlP(24, 8).r, x323.r)
      val x327 = Wire(new FloatingPoint(24, 8)).suggestName("""x327""")
      x327.r := Mux((x324), -32768.FlP(24, 8).r, x326.r)
      val x412 = Wire(new FloatingPoint(24, 8)).suggestName("x412_x327_D1") 
      x412.r := getRetimed(x327.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x328 = Wire(Bool()).suggestName("""x328""")
      x328.r := Math.flt(x412, 0.0.FlP(24, 8), Some(0.0), true.B,"x328").r
      val x329 = Wire(new FloatingPoint(24, 8)).suggestName("""x329""")
      x329.r := Math.fsub(x412, 0.5.FlP(24, 8), Some(12.0), true.B,"x329").r
      val x330 = Wire(new FixedPoint(true, 16, 0)).suggestName("""x330""")
      x330.r := Math.flt2fix(x329, true,16,0,Some(6.0),true.B, Truncate, Wrapping, "x330").r
      val x331 = Wire(new FloatingPoint(24, 8)).suggestName("""x331""")
      x331.r := Math.fadd(x412, 0.5.FlP(24, 8), Some(12.0), true.B,"x331").r
      val x332 = Wire(new FixedPoint(true, 16, 0)).suggestName("""x332""")
      x332.r := Math.flt2fix(x331, true,16,0,Some(6.0),true.B, Truncate, Wrapping, "x332").r
      val x413 = Wire(Bool()).suggestName("x413_x328_D18") 
      x413.r := getRetimed(x328.r, 18.toInt, io.sigsIn.backpressure & true.B)
      val x333 = Wire(new FixedPoint(true, 16, 0)).suggestName("""x333""")
      x333.r := Mux((x413), x330.r, x332.r)
      val x334 = Wire(new FloatingPoint(24, 8)).suggestName("""x334""")
      x334.r := Math.fix2flt(x333,24,8,Some(5.0),true.B,"x334").r
      val x335 = Wire(new FloatingPoint(24, 8)).suggestName("""x335""")
      x335.r := Math.fdiv(x334, 32768.FlP(24, 8), Some(28.0), true.B,"x335").r
      val x414 = Wire(new FixedPoint(true, 32, 0)).suggestName("x414_b319_D62") 
      x414.r := getRetimed(b319.r, 62.toInt, io.sigsIn.backpressure & true.B)
      val x415 = Wire(Bool()).suggestName("x415_b320_D62") 
      x415.r := getRetimed(b320.r, 62.toInt, io.sigsIn.backpressure & true.B)
      val x336_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x336_wr_ofs = List[UInt](x414.r)
      val x336_wr_en = List[Bool](true.B)
      val x336_wr_data = List[UInt](x335.r)
      x281_outSRAM_0.connectWPort(336, x336_wr_banks, x336_wr_ofs, x336_wr_data, x336_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(62.5.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x415))
