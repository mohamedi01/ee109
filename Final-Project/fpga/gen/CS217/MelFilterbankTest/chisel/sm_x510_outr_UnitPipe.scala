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

/** Hierarchy: x510 -> x511 -> x611 -> x612 **/
/** BEGIN None x510_outr_UnitPipe **/
class x510_outr_UnitPipe_kernel(
  list_x478_fifo: List[FIFOInterface],
  list_x479: List[DecoupledIO[AppLoadData]],
  list_x412_vecSRAM_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x510_outr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x510_outr_UnitPipe_iiCtr"))
  
  abstract class x510_outr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x479 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x479_p").asInstanceOf[(Int, Int)] )))
      val in_x412_vecSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x412_vecSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x478_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x478_fifo_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x479 = {io.in_x479} 
    def x412_vecSRAM_0 = {io.in_x412_vecSRAM_0} ; io.in_x412_vecSRAM_0 := DontCare
    def x478_fifo = {io.in_x478_fifo} ; io.in_x478_fifo := DontCare
  }
  def connectWires0(module: x510_outr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x479 <> x479
    x412_vecSRAM_0.connectLedger(module.io.in_x412_vecSRAM_0)
    x478_fifo.connectLedger(module.io.in_x478_fifo)
  }
  val x478_fifo = list_x478_fifo(0)
  val x479 = list_x479(0)
  val x412_vecSRAM_0 = list_x412_vecSRAM_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x510_outr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x510_outr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x510_outr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x510_outr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x510_outr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x510_outr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x510_outr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X510_instrctr, cycles_x510_outr_UnitPipe.io.count, iters_x510_outr_UnitPipe.io.count, 0.U, 0.U)
      val x488_reg = (new x488_reg).m.io.asInstanceOf[StandardInterface]
      val x489_reg = (new x489_reg).m.io.asInstanceOf[StandardInterface]
      val x496_inr_UnitPipe = new x496_inr_UnitPipe_kernel(List(x478_fifo), List(x488_reg,x489_reg) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x496_inr_UnitPipe.sm.io.ctrDone := risingEdge(x496_inr_UnitPipe.sm.io.ctrInc)
      x496_inr_UnitPipe.backpressure := true.B | x496_inr_UnitPipe.sm.io.doneLatch
      x496_inr_UnitPipe.forwardpressure := ((~x478_fifo.empty.D(1.0-1) | ~(x478_fifo.active(1).out))) && (true.B) | x496_inr_UnitPipe.sm.io.doneLatch
      x496_inr_UnitPipe.sm.io.enableOut.zip(x496_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x496_inr_UnitPipe.sm.io.break := false.B
      x496_inr_UnitPipe.mask := true.B & true.B
      x496_inr_UnitPipe.configure("x496_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x496_inr_UnitPipe.kernel()
      val x600_rd_x489 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x600_rd_x489""")
      val x600_rd_x489_banks = List[UInt]()
      val x600_rd_x489_ofs = List[UInt]()
      val x600_rd_x489_en = List[Bool](true.B)
      val x600_rd_x489_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x600_rd_x489_shared_en")
      x600_rd_x489.toSeq.zip(x489_reg.connectRPort(600, x600_rd_x489_banks, x600_rd_x489_ofs, io.sigsIn.backpressure, x600_rd_x489_en.map(_ && x600_rd_x489_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x498_ctr = new CtrObject(Left(Some(0)), Right(x600_rd_x489), Left(Some(1)), 1, 32, false)
      val x499_ctrchain = (new CChainObject(List[CtrObject](x498_ctr), "x499_ctrchain")).cchain.io 
      x499_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x499_ctrchain_p", (x499_ctrchain.par, x499_ctrchain.widths))
      val x509_inr_Foreach = new x509_inr_Foreach_kernel(List(x479), List(x488_reg,x412_vecSRAM_0) ,  Some(me), List(x499_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x509_inr_Foreach.sm.io.ctrDone := (x509_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x509_inr_Foreach.backpressure := true.B | x509_inr_Foreach.sm.io.doneLatch
      x509_inr_Foreach.forwardpressure := (x479.valid) && (true.B) | x509_inr_Foreach.sm.io.doneLatch
      x509_inr_Foreach.sm.io.enableOut.zip(x509_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x509_inr_Foreach.sm.io.break := false.B
      x509_inr_Foreach.mask := ~x509_inr_Foreach.cchain.head.output.noop & true.B
      x509_inr_Foreach.configure("x509_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x509_inr_Foreach.kernel()
    }
    val module = Module(new x510_outr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x510_outr_UnitPipe **/
