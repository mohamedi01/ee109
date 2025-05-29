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

/** Hierarchy: x321 -> x322 -> x374 -> x375 **/
/** BEGIN None x321_outr_UnitPipe **/
class x321_outr_UnitPipe_kernel(
  list_x289_fifo: List[FIFOInterface],
  list_x290: List[DecoupledIO[AppLoadData]],
  list_x251_imagSram_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x321_outr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x321_outr_UnitPipe_iiCtr"))
  
  abstract class x321_outr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x289_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x289_fifo_p").asInstanceOf[MemParams] ))
      val in_x251_imagSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x251_imagSram_0_p").asInstanceOf[MemParams] ))
      val in_x290 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x290_p").asInstanceOf[(Int, Int)] )))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x289_fifo = {io.in_x289_fifo} ; io.in_x289_fifo := DontCare
    def x251_imagSram_0 = {io.in_x251_imagSram_0} ; io.in_x251_imagSram_0 := DontCare
    def x290 = {io.in_x290} 
  }
  def connectWires0(module: x321_outr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x289_fifo.connectLedger(module.io.in_x289_fifo)
    x251_imagSram_0.connectLedger(module.io.in_x251_imagSram_0)
    module.io.in_x290 <> x290
  }
  val x289_fifo = list_x289_fifo(0)
  val x290 = list_x290(0)
  val x251_imagSram_0 = list_x251_imagSram_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x321_outr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x321_outr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x321_outr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x299_reg = (new x299_reg).m.io.asInstanceOf[StandardInterface]
      val x300_reg = (new x300_reg).m.io.asInstanceOf[StandardInterface]
      val x307_inr_UnitPipe = new x307_inr_UnitPipe_kernel(List(x289_fifo), List(x299_reg,x300_reg) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, rr)
      x307_inr_UnitPipe.sm.io.ctrDone := risingEdge(x307_inr_UnitPipe.sm.io.ctrInc)
      x307_inr_UnitPipe.backpressure := true.B | x307_inr_UnitPipe.sm.io.doneLatch
      x307_inr_UnitPipe.forwardpressure := ((~x289_fifo.empty.D(1.0-1) | ~(x289_fifo.active(1).out))) && (true.B) | x307_inr_UnitPipe.sm.io.doneLatch
      x307_inr_UnitPipe.sm.io.enableOut.zip(x307_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x307_inr_UnitPipe.sm.io.break := false.B
      x307_inr_UnitPipe.mask := true.B & true.B
      x307_inr_UnitPipe.configure("x307_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x307_inr_UnitPipe.kernel()
      val x371_rd_x300 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x371_rd_x300""")
      val x371_rd_x300_banks = List[UInt]()
      val x371_rd_x300_ofs = List[UInt]()
      val x371_rd_x300_en = List[Bool](true.B)
      val x371_rd_x300_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x371_rd_x300_shared_en")
      x371_rd_x300.toSeq.zip(x300_reg.connectRPort(371, x371_rd_x300_banks, x371_rd_x300_ofs, io.sigsIn.backpressure, x371_rd_x300_en.map(_ && x371_rd_x300_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x309_ctr = new CtrObject(Left(Some(0)), Right(x371_rd_x300), Left(Some(1)), 1, 32, false)
      val x310_ctrchain = (new CChainObject(List[CtrObject](x309_ctr), "x310_ctrchain")).cchain.io 
      x310_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x310_ctrchain_p", (x310_ctrchain.par, x310_ctrchain.widths))
      val x320_inr_Foreach = new x320_inr_Foreach_kernel(List(x290), List(x251_imagSram_0,x299_reg) ,  Some(me), List(x310_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, rr)
      x320_inr_Foreach.sm.io.ctrDone := (x320_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x320_inr_Foreach.backpressure := true.B | x320_inr_Foreach.sm.io.doneLatch
      x320_inr_Foreach.forwardpressure := (x290.valid) && (true.B) | x320_inr_Foreach.sm.io.doneLatch
      x320_inr_Foreach.sm.io.enableOut.zip(x320_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x320_inr_Foreach.sm.io.break := false.B
      x320_inr_Foreach.mask := ~x320_inr_Foreach.cchain.head.output.noop & true.B
      x320_inr_Foreach.configure("x320_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x320_inr_Foreach.kernel()
    }
    val module = Module(new x321_outr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x321_outr_UnitPipe **/
