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

/** Hierarchy: x368 -> x382 -> x383 -> x489 -> x491 **/
/** BEGIN None x368_inr_UnitPipe **/
class x368_inr_UnitPipe_kernel(
  list_x345_fifo: List[FIFOInterface],
  list_x361_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x368_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x368_inr_UnitPipe_iiCtr"))
  
  abstract class x368_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x361_reg = Flipped(new StandardInterface(ModuleParams.getParams("x361_reg_p").asInstanceOf[MemParams] ))
      val in_x360_reg = Flipped(new StandardInterface(ModuleParams.getParams("x360_reg_p").asInstanceOf[MemParams] ))
      val in_x345_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x345_fifo_p").asInstanceOf[MemParams] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x361_reg = {io.in_x361_reg} ; io.in_x361_reg := DontCare
    def x360_reg = {io.in_x360_reg} ; io.in_x360_reg := DontCare
    def x345_fifo = {io.in_x345_fifo} ; io.in_x345_fifo := DontCare
  }
  def connectWires0(module: x368_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x361_reg.connectLedger(module.io.in_x361_reg)
    x360_reg.connectLedger(module.io.in_x360_reg)
    x345_fifo.connectLedger(module.io.in_x345_fifo)
  }
  val x345_fifo = list_x345_fifo(0)
  val x361_reg = list_x361_reg(0)
  val x360_reg = list_x361_reg(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x368_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x368_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x368_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x362_deq_x345 = Wire(Vec(1, UInt(96.W))).suggestName("""x362_deq_x345""")
      val x362_deq_x345_banks = List[UInt]()
      val x362_deq_x345_ofs = List[UInt]()
      val x362_deq_x345_en = List[Bool](true.B)
      val x362_deq_x345_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x362_deq_x345_shared_en")
      x362_deq_x345.toSeq.zip(x345_fifo.connectRPort(362, x362_deq_x345_banks, x362_deq_x345_ofs, io.sigsIn.backpressure, x362_deq_x345_en.map(_ && x362_deq_x345_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      x345_fifo.connectAccessActivesIn(1, ((true.B)))
      // x363 = VecApply(x362,0)
      val x363_elem_0 = Wire(UInt(96.W)).suggestName("""x363_elem_0""")
      x363_elem_0.r := x362_deq_x345(0).r
      val x364_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x364_apply""")
      x364_apply.r := x363_elem_0(95, 64)
      val x365_wr_x360_banks = List[UInt]()
      val x365_wr_x360_ofs = List[UInt]()
      val x365_wr_x360_en = List[Bool](true.B)
      val x365_wr_x360_data = List[UInt](x364_apply.r)
      x360_reg.connectWPort(365, x365_wr_x360_banks, x365_wr_x360_ofs, x365_wr_x360_data, x365_wr_x360_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x366_apply = Wire(new FixedPoint(true, 32, 0)).suggestName("""x366_apply""")
      x366_apply.r := x363_elem_0(31, 0)
      val x367_wr_x361_banks = List[UInt]()
      val x367_wr_x361_ofs = List[UInt]()
      val x367_wr_x361_en = List[Bool](true.B)
      val x367_wr_x361_data = List[UInt](x366_apply.r)
      x361_reg.connectWPort(367, x367_wr_x361_banks, x367_wr_x361_ofs, x367_wr_x361_data, x367_wr_x361_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x368_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x368_inr_UnitPipe **/
