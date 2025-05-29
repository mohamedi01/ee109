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

/** Hierarchy: x339 -> x358 -> x359 -> x450 -> x453 **/
/** BEGIN None x339_inr_UnitPipe **/
class x339_inr_UnitPipe_kernel(
  list_b327: List[Bool],
  list_x298_fifo: List[FIFOInterface],
  list_x329_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x339_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x339_inr_UnitPipe_iiCtr"))
  
  abstract class x339_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x329_reg = Flipped(new StandardInterface(ModuleParams.getParams("x329_reg_p").asInstanceOf[MemParams] ))
      val in_x328_reg = Flipped(new StandardInterface(ModuleParams.getParams("x328_reg_p").asInstanceOf[MemParams] ))
      val in_x298_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x298_fifo_p").asInstanceOf[MemParams] ))
      val in_x330_reg = Flipped(new StandardInterface(ModuleParams.getParams("x330_reg_p").asInstanceOf[MemParams] ))
      val in_b327 = Input(Bool())
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x329_reg = {io.in_x329_reg} ; io.in_x329_reg := DontCare
    def x328_reg = {io.in_x328_reg} ; io.in_x328_reg := DontCare
    def x298_fifo = {io.in_x298_fifo} ; io.in_x298_fifo := DontCare
    def x330_reg = {io.in_x330_reg} ; io.in_x330_reg := DontCare
    def b327 = {io.in_b327} 
  }
  def connectWires0(module: x339_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x329_reg.connectLedger(module.io.in_x329_reg)
    x328_reg.connectLedger(module.io.in_x328_reg)
    x298_fifo.connectLedger(module.io.in_x298_fifo)
    x330_reg.connectLedger(module.io.in_x330_reg)
    module.io.in_b327 <> b327
  }
  val b327 = list_b327(0)
  val x298_fifo = list_x298_fifo(0)
  val x329_reg = list_x329_reg(0)
  val x328_reg = list_x329_reg(1)
  val x330_reg = list_x329_reg(2)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x339_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x339_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x339_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x331_deq_x298 = Wire(Vec(1, UInt(96.W))).suggestName("""x331_deq_x298""")
      val x331_deq_x298_banks = List[UInt]()
      val x331_deq_x298_ofs = List[UInt]()
      val x331_deq_x298_en = List[Bool](true.B)
      val x331_deq_x298_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x331_deq_x298_shared_en")
      x331_deq_x298.toSeq.zip(x298_fifo.connectRPort(331, x331_deq_x298_banks, x331_deq_x298_ofs, io.sigsIn.backpressure, x331_deq_x298_en.map(_ && x331_deq_x298_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      x298_fifo.connectAccessActivesIn(1, ((true.B)))
      // x332 = VecApply(x331,0)
      val x332_elem_0 = Wire(UInt(96.W)).suggestName("""x332_elem_0""")
      x332_elem_0.r := x331_deq_x298(0).r
      val x333_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x333_apply""")
      x333_apply.r := x332_elem_0(63, 32)
      val x334_wr_x328_banks = List[UInt]()
      val x334_wr_x328_ofs = List[UInt]()
      val x334_wr_x328_en = List[Bool](true.B)
      val x334_wr_x328_data = List[UInt](x333_apply.r)
      x328_reg.connectWPort(334, x334_wr_x328_banks, x334_wr_x328_ofs, x334_wr_x328_data, x334_wr_x328_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x335_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x335_apply""")
      x335_apply.r := x332_elem_0(95, 64)
      val x336_wr_x329_banks = List[UInt]()
      val x336_wr_x329_ofs = List[UInt]()
      val x336_wr_x329_en = List[Bool](true.B)
      val x336_wr_x329_data = List[UInt](x335_apply.r)
      x329_reg.connectWPort(336, x336_wr_x329_banks, x336_wr_x329_ofs, x336_wr_x329_data, x336_wr_x329_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x337_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x337_apply""")
      x337_apply.r := x332_elem_0(31, 0)
      val x338_wr_x330_banks = List[UInt]()
      val x338_wr_x330_ofs = List[UInt]()
      val x338_wr_x330_en = List[Bool](true.B)
      val x338_wr_x330_data = List[UInt](x337_apply.r)
      x330_reg.connectWPort(338, x338_wr_x330_banks, x338_wr_x330_ofs, x338_wr_x330_data, x338_wr_x330_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x339_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x339_inr_UnitPipe **/
