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

/** Hierarchy: x393 -> x394 -> x450 -> x453 **/
/** BEGIN None x393_outr_UnitPipe **/
class x393_outr_UnitPipe_kernel(
  list_x361_fifo: List[FIFOInterface],
  list_x362: List[DecoupledIO[AppLoadData]],
  list_x295_vecSram_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x393_outr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x393_outr_UnitPipe_iiCtr"))
  
  abstract class x393_outr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x361_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x361_fifo_p").asInstanceOf[MemParams] ))
      val in_x362 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x362_p").asInstanceOf[(Int, Int)] )))
      val in_x295_vecSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x295_vecSram_0_p").asInstanceOf[MemParams] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x361_fifo = {io.in_x361_fifo} ; io.in_x361_fifo := DontCare
    def x362 = {io.in_x362} 
    def x295_vecSram_0 = {io.in_x295_vecSram_0} ; io.in_x295_vecSram_0 := DontCare
  }
  def connectWires0(module: x393_outr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x361_fifo.connectLedger(module.io.in_x361_fifo)
    module.io.in_x362 <> x362
    x295_vecSram_0.connectLedger(module.io.in_x295_vecSram_0)
  }
  val x361_fifo = list_x361_fifo(0)
  val x362 = list_x362(0)
  val x295_vecSram_0 = list_x295_vecSram_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x393_outr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x393_outr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x393_outr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x371_reg = (new x371_reg).m.io.asInstanceOf[StandardInterface]
      val x372_reg = (new x372_reg).m.io.asInstanceOf[StandardInterface]
      val x379_inr_UnitPipe = new x379_inr_UnitPipe_kernel(List(x361_fifo), List(x372_reg,x371_reg) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, rr)
      x379_inr_UnitPipe.sm.io.ctrDone := risingEdge(x379_inr_UnitPipe.sm.io.ctrInc)
      x379_inr_UnitPipe.backpressure := true.B | x379_inr_UnitPipe.sm.io.doneLatch
      x379_inr_UnitPipe.forwardpressure := ((~x361_fifo.empty.D(1.0-1) | ~(x361_fifo.active(1).out))) && (true.B) | x379_inr_UnitPipe.sm.io.doneLatch
      x379_inr_UnitPipe.sm.io.enableOut.zip(x379_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x379_inr_UnitPipe.sm.io.break := false.B
      x379_inr_UnitPipe.mask := true.B & true.B
      x379_inr_UnitPipe.configure("x379_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x379_inr_UnitPipe.kernel()
      val x444_rd_x372 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x444_rd_x372""")
      val x444_rd_x372_banks = List[UInt]()
      val x444_rd_x372_ofs = List[UInt]()
      val x444_rd_x372_en = List[Bool](true.B)
      val x444_rd_x372_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x444_rd_x372_shared_en")
      x444_rd_x372.toSeq.zip(x372_reg.connectRPort(444, x444_rd_x372_banks, x444_rd_x372_ofs, io.sigsIn.backpressure, x444_rd_x372_en.map(_ && x444_rd_x372_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x381_ctr = new CtrObject(Left(Some(0)), Right(x444_rd_x372), Left(Some(1)), 1, 32, false)
      val x382_ctrchain = (new CChainObject(List[CtrObject](x381_ctr), "x382_ctrchain")).cchain.io 
      x382_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x382_ctrchain_p", (x382_ctrchain.par, x382_ctrchain.widths))
      val x392_inr_Foreach = new x392_inr_Foreach_kernel(List(x362), List(x371_reg,x295_vecSram_0) ,  Some(me), List(x382_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, rr)
      x392_inr_Foreach.sm.io.ctrDone := (x392_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x392_inr_Foreach.backpressure := true.B | x392_inr_Foreach.sm.io.doneLatch
      x392_inr_Foreach.forwardpressure := (x362.valid) && (true.B) | x392_inr_Foreach.sm.io.doneLatch
      x392_inr_Foreach.sm.io.enableOut.zip(x392_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x392_inr_Foreach.sm.io.break := false.B
      x392_inr_Foreach.mask := ~x392_inr_Foreach.cchain.head.output.noop & true.B
      x392_inr_Foreach.configure("x392_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x392_inr_Foreach.kernel()
    }
    val module = Module(new x393_outr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x393_outr_UnitPipe **/
