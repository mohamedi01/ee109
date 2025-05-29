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

/** Hierarchy: x287 -> x288 -> x201 **/
/** BEGIN None x287_outr_UnitPipe **/
class x287_outr_UnitPipe_kernel(
  list_x255_fifo: List[FIFOInterface],
  list_x256: List[DecoupledIO[AppLoadData]],
  list_x253_buf_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x287_outr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x287_outr_UnitPipe_iiCtr"))
  
  abstract class x287_outr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x253_buf_0 = Flipped(new StandardInterface(ModuleParams.getParams("x253_buf_0_p").asInstanceOf[MemParams] ))
      val in_x256 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x256_p").asInstanceOf[(Int, Int)] )))
      val in_x255_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x255_fifo_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x253_buf_0 = {io.in_x253_buf_0} ; io.in_x253_buf_0 := DontCare
    def x256 = {io.in_x256} 
    def x255_fifo = {io.in_x255_fifo} ; io.in_x255_fifo := DontCare
  }
  def connectWires0(module: x287_outr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x253_buf_0.connectLedger(module.io.in_x253_buf_0)
    module.io.in_x256 <> x256
    x255_fifo.connectLedger(module.io.in_x255_fifo)
  }
  val x255_fifo = list_x255_fifo(0)
  val x256 = list_x256(0)
  val x253_buf_0 = list_x253_buf_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x287_outr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x287_outr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x287_outr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x287_outr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x287_outr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x287_outr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x287_outr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X287_instrctr, cycles_x287_outr_UnitPipe.io.count, iters_x287_outr_UnitPipe.io.count, 0.U, 0.U)
      val x265_reg = (new x265_reg).m.io.asInstanceOf[StandardInterface]
      val x266_reg = (new x266_reg).m.io.asInstanceOf[StandardInterface]
      val x273_inr_UnitPipe = new x273_inr_UnitPipe_kernel(List(x255_fifo), List(x265_reg,x266_reg) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x273_inr_UnitPipe.sm.io.ctrDone := risingEdge(x273_inr_UnitPipe.sm.io.ctrInc)
      x273_inr_UnitPipe.backpressure := true.B | x273_inr_UnitPipe.sm.io.doneLatch
      x273_inr_UnitPipe.forwardpressure := ((~x255_fifo.empty.D(1.0-1) | ~(x255_fifo.active(1).out))) && (true.B) | x273_inr_UnitPipe.sm.io.doneLatch
      x273_inr_UnitPipe.sm.io.enableOut.zip(x273_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x273_inr_UnitPipe.sm.io.break := false.B
      x273_inr_UnitPipe.mask := true.B & true.B
      x273_inr_UnitPipe.configure("x273_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x273_inr_UnitPipe.kernel()
      val x372_rd_x266 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x372_rd_x266""")
      val x372_rd_x266_banks = List[UInt]()
      val x372_rd_x266_ofs = List[UInt]()
      val x372_rd_x266_en = List[Bool](true.B)
      val x372_rd_x266_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x372_rd_x266_shared_en")
      x372_rd_x266.toSeq.zip(x266_reg.connectRPort(372, x372_rd_x266_banks, x372_rd_x266_ofs, io.sigsIn.backpressure, x372_rd_x266_en.map(_ && x372_rd_x266_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x275_ctr = new CtrObject(Left(Some(0)), Right(x372_rd_x266), Left(Some(1)), 1, 32, false)
      val x276_ctrchain = (new CChainObject(List[CtrObject](x275_ctr), "x276_ctrchain")).cchain.io 
      x276_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x276_ctrchain_p", (x276_ctrchain.par, x276_ctrchain.widths))
      val x286_inr_Foreach = new x286_inr_Foreach_kernel(List(x256), List(x253_buf_0,x265_reg) ,  Some(me), List(x276_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x286_inr_Foreach.sm.io.ctrDone := (x286_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x286_inr_Foreach.backpressure := true.B | x286_inr_Foreach.sm.io.doneLatch
      x286_inr_Foreach.forwardpressure := (x256.valid) && (true.B) | x286_inr_Foreach.sm.io.doneLatch
      x286_inr_Foreach.sm.io.enableOut.zip(x286_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x286_inr_Foreach.sm.io.break := false.B
      x286_inr_Foreach.mask := ~x286_inr_Foreach.cchain.head.output.noop & true.B
      x286_inr_Foreach.configure("x286_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x286_inr_Foreach.kernel()
    }
    val module = Module(new x287_outr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x287_outr_UnitPipe **/
