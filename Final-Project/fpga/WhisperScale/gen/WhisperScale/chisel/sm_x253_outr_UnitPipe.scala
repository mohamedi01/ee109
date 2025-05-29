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

/** Hierarchy: x253 -> x254 -> x196 **/
/** BEGIN None x253_outr_UnitPipe **/
class x253_outr_UnitPipe_kernel(
  list_x216_fifo: List[FIFOInterface],
  list_x217: List[DecoupledIO[AppLoadData]],
  list_x214_buf_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x253_outr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x253_outr_UnitPipe_iiCtr"))
  
  abstract class x253_outr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x217 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x217_p").asInstanceOf[(Int, Int)] )))
      val in_x216_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x216_fifo_p").asInstanceOf[MemParams] ))
      val in_x214_buf_0 = Flipped(new StandardInterface(ModuleParams.getParams("x214_buf_0_p").asInstanceOf[MemParams] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x217 = {io.in_x217} 
    def x216_fifo = {io.in_x216_fifo} ; io.in_x216_fifo := DontCare
    def x214_buf_0 = {io.in_x214_buf_0} ; io.in_x214_buf_0 := DontCare
  }
  def connectWires0(module: x253_outr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x217 <> x217
    x216_fifo.connectLedger(module.io.in_x216_fifo)
    x214_buf_0.connectLedger(module.io.in_x214_buf_0)
  }
  val x216_fifo = list_x216_fifo(0)
  val x217 = list_x217(0)
  val x214_buf_0 = list_x214_buf_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x253_outr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x253_outr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x253_outr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x231_reg = (new x231_reg).m.io.asInstanceOf[StandardInterface]
      val x232_reg = (new x232_reg).m.io.asInstanceOf[StandardInterface]
      val x239_inr_UnitPipe = new x239_inr_UnitPipe_kernel(List(x216_fifo), List(x231_reg,x232_reg) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, rr)
      x239_inr_UnitPipe.sm.io.ctrDone := risingEdge(x239_inr_UnitPipe.sm.io.ctrInc)
      x239_inr_UnitPipe.backpressure := true.B | x239_inr_UnitPipe.sm.io.doneLatch
      x239_inr_UnitPipe.forwardpressure := ((~x216_fifo.empty.D(1.0-1) | ~(x216_fifo.active(1).out))) && (true.B) | x239_inr_UnitPipe.sm.io.doneLatch
      x239_inr_UnitPipe.sm.io.enableOut.zip(x239_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x239_inr_UnitPipe.sm.io.break := false.B
      x239_inr_UnitPipe.mask := true.B & true.B
      x239_inr_UnitPipe.configure("x239_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x239_inr_UnitPipe.kernel()
      val x303_rd_x232 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x303_rd_x232""")
      val x303_rd_x232_banks = List[UInt]()
      val x303_rd_x232_ofs = List[UInt]()
      val x303_rd_x232_en = List[Bool](true.B)
      val x303_rd_x232_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x303_rd_x232_shared_en")
      x303_rd_x232.toSeq.zip(x232_reg.connectRPort(303, x303_rd_x232_banks, x303_rd_x232_ofs, io.sigsIn.backpressure, x303_rd_x232_en.map(_ && x303_rd_x232_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x241_ctr = new CtrObject(Left(Some(0)), Right(x303_rd_x232), Left(Some(1)), 1, 32, false)
      val x242_ctrchain = (new CChainObject(List[CtrObject](x241_ctr), "x242_ctrchain")).cchain.io 
      x242_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x242_ctrchain_p", (x242_ctrchain.par, x242_ctrchain.widths))
      val x252_inr_Foreach = new x252_inr_Foreach_kernel(List(x217), List(x231_reg,x214_buf_0) ,  Some(me), List(x242_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, rr)
      x252_inr_Foreach.sm.io.ctrDone := (x252_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x252_inr_Foreach.backpressure := true.B | x252_inr_Foreach.sm.io.doneLatch
      x252_inr_Foreach.forwardpressure := (x217.valid) && (true.B) | x252_inr_Foreach.sm.io.doneLatch
      x252_inr_Foreach.sm.io.enableOut.zip(x252_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x252_inr_Foreach.sm.io.break := false.B
      x252_inr_Foreach.mask := ~x252_inr_Foreach.cchain.head.output.noop & true.B
      x252_inr_Foreach.configure("x252_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x252_inr_Foreach.kernel()
    }
    val module = Module(new x253_outr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x253_outr_UnitPipe **/
