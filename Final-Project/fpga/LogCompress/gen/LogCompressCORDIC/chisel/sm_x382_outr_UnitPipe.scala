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

/** Hierarchy: x382 -> x383 -> x489 -> x491 **/
/** BEGIN None x382_outr_UnitPipe **/
class x382_outr_UnitPipe_kernel(
  list_x345_fifo: List[FIFOInterface],
  list_x346: List[DecoupledIO[AppLoadData]],
  list_x341_buf_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x382_outr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x382_outr_UnitPipe_iiCtr"))
  
  abstract class x382_outr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x345_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x345_fifo_p").asInstanceOf[MemParams] ))
      val in_x346 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x346_p").asInstanceOf[(Int, Int)] )))
      val in_x341_buf_0 = Flipped(new StandardInterface(ModuleParams.getParams("x341_buf_0_p").asInstanceOf[MemParams] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x345_fifo = {io.in_x345_fifo} ; io.in_x345_fifo := DontCare
    def x346 = {io.in_x346} 
    def x341_buf_0 = {io.in_x341_buf_0} ; io.in_x341_buf_0 := DontCare
  }
  def connectWires0(module: x382_outr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x345_fifo.connectLedger(module.io.in_x345_fifo)
    module.io.in_x346 <> x346
    x341_buf_0.connectLedger(module.io.in_x341_buf_0)
  }
  val x345_fifo = list_x345_fifo(0)
  val x346 = list_x346(0)
  val x341_buf_0 = list_x341_buf_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x382_outr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x382_outr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x382_outr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x360_reg = (new x360_reg).m.io.asInstanceOf[StandardInterface]
      val x361_reg = (new x361_reg).m.io.asInstanceOf[StandardInterface]
      val x368_inr_UnitPipe = new x368_inr_UnitPipe_kernel(List(x345_fifo), List(x361_reg,x360_reg) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, rr)
      x368_inr_UnitPipe.sm.io.ctrDone := risingEdge(x368_inr_UnitPipe.sm.io.ctrInc)
      x368_inr_UnitPipe.backpressure := true.B | x368_inr_UnitPipe.sm.io.doneLatch
      x368_inr_UnitPipe.forwardpressure := ((~x345_fifo.empty.D(1.0-1) | ~(x345_fifo.active(1).out))) && (true.B) | x368_inr_UnitPipe.sm.io.doneLatch
      x368_inr_UnitPipe.sm.io.enableOut.zip(x368_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x368_inr_UnitPipe.sm.io.break := false.B
      x368_inr_UnitPipe.mask := true.B & true.B
      x368_inr_UnitPipe.configure("x368_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x368_inr_UnitPipe.kernel()
      val x483_rd_x361 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x483_rd_x361""")
      val x483_rd_x361_banks = List[UInt]()
      val x483_rd_x361_ofs = List[UInt]()
      val x483_rd_x361_en = List[Bool](true.B)
      val x483_rd_x361_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x483_rd_x361_shared_en")
      x483_rd_x361.toSeq.zip(x361_reg.connectRPort(483, x483_rd_x361_banks, x483_rd_x361_ofs, io.sigsIn.backpressure, x483_rd_x361_en.map(_ && x483_rd_x361_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x370_ctr = new CtrObject(Left(Some(0)), Right(x483_rd_x361), Left(Some(1)), 1, 32, false)
      val x371_ctrchain = (new CChainObject(List[CtrObject](x370_ctr), "x371_ctrchain")).cchain.io 
      x371_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x371_ctrchain_p", (x371_ctrchain.par, x371_ctrchain.widths))
      val x381_inr_Foreach = new x381_inr_Foreach_kernel(List(x346), List(x360_reg,x341_buf_0) ,  Some(me), List(x371_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, rr)
      x381_inr_Foreach.sm.io.ctrDone := (x381_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x381_inr_Foreach.backpressure := true.B | x381_inr_Foreach.sm.io.doneLatch
      x381_inr_Foreach.forwardpressure := (x346.valid) && (true.B) | x381_inr_Foreach.sm.io.doneLatch
      x381_inr_Foreach.sm.io.enableOut.zip(x381_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x381_inr_Foreach.sm.io.break := false.B
      x381_inr_Foreach.mask := ~x381_inr_Foreach.cchain.head.output.noop & true.B
      x381_inr_Foreach.configure("x381_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x381_inr_Foreach.kernel()
    }
    val module = Module(new x382_outr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x382_outr_UnitPipe **/
