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

/** Hierarchy: x362 -> x363 -> x480 -> x481 **/
/** BEGIN None x362_outr_UnitPipe **/
class x362_outr_UnitPipe_kernel(
  list_x330_fifo: List[FIFOInterface],
  list_x331: List[DecoupledIO[AppLoadData]],
  list_x326_realSRAM_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x362_outr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x362_outr_UnitPipe_iiCtr"))
  
  abstract class x362_outr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x330_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x330_fifo_p").asInstanceOf[MemParams] ))
      val in_x331 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x331_p").asInstanceOf[(Int, Int)] )))
      val in_x326_realSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x326_realSRAM_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x330_fifo = {io.in_x330_fifo} ; io.in_x330_fifo := DontCare
    def x331 = {io.in_x331} 
    def x326_realSRAM_0 = {io.in_x326_realSRAM_0} ; io.in_x326_realSRAM_0 := DontCare
  }
  def connectWires0(module: x362_outr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x330_fifo.connectLedger(module.io.in_x330_fifo)
    module.io.in_x331 <> x331
    x326_realSRAM_0.connectLedger(module.io.in_x326_realSRAM_0)
  }
  val x330_fifo = list_x330_fifo(0)
  val x331 = list_x331(0)
  val x326_realSRAM_0 = list_x326_realSRAM_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x362_outr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x362_outr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x362_outr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x362_outr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x362_outr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x362_outr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x362_outr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X362_instrctr, cycles_x362_outr_UnitPipe.io.count, iters_x362_outr_UnitPipe.io.count, 0.U, 0.U)
      val x340_reg = (new x340_reg).m.io.asInstanceOf[StandardInterface]
      val x341_reg = (new x341_reg).m.io.asInstanceOf[StandardInterface]
      val x348_inr_UnitPipe = new x348_inr_UnitPipe_kernel(List(x330_fifo), List(x340_reg,x341_reg) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x348_inr_UnitPipe.sm.io.ctrDone := risingEdge(x348_inr_UnitPipe.sm.io.ctrInc)
      x348_inr_UnitPipe.backpressure := true.B | x348_inr_UnitPipe.sm.io.doneLatch
      x348_inr_UnitPipe.forwardpressure := ((~x330_fifo.empty.D(1.0-1) | ~(x330_fifo.active(1).out))) && (true.B) | x348_inr_UnitPipe.sm.io.doneLatch
      x348_inr_UnitPipe.sm.io.enableOut.zip(x348_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x348_inr_UnitPipe.sm.io.break := false.B
      x348_inr_UnitPipe.mask := true.B & true.B
      x348_inr_UnitPipe.configure("x348_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x348_inr_UnitPipe.kernel()
      val x476_rd_x341 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x476_rd_x341""")
      val x476_rd_x341_banks = List[UInt]()
      val x476_rd_x341_ofs = List[UInt]()
      val x476_rd_x341_en = List[Bool](true.B)
      val x476_rd_x341_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x476_rd_x341_shared_en")
      x476_rd_x341.toSeq.zip(x341_reg.connectRPort(476, x476_rd_x341_banks, x476_rd_x341_ofs, io.sigsIn.backpressure, x476_rd_x341_en.map(_ && x476_rd_x341_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x350_ctr = new CtrObject(Left(Some(0)), Right(x476_rd_x341), Left(Some(1)), 1, 32, false)
      val x351_ctrchain = (new CChainObject(List[CtrObject](x350_ctr), "x351_ctrchain")).cchain.io 
      x351_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x351_ctrchain_p", (x351_ctrchain.par, x351_ctrchain.widths))
      val x361_inr_Foreach = new x361_inr_Foreach_kernel(List(x331), List(x340_reg,x326_realSRAM_0) ,  Some(me), List(x351_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x361_inr_Foreach.sm.io.ctrDone := (x361_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x361_inr_Foreach.backpressure := true.B | x361_inr_Foreach.sm.io.doneLatch
      x361_inr_Foreach.forwardpressure := (x331.valid) && (true.B) | x361_inr_Foreach.sm.io.doneLatch
      x361_inr_Foreach.sm.io.enableOut.zip(x361_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x361_inr_Foreach.sm.io.break := false.B
      x361_inr_Foreach.mask := ~x361_inr_Foreach.cchain.head.output.noop & true.B
      x361_inr_Foreach.configure("x361_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x361_inr_Foreach.kernel()
    }
    val module = Module(new x362_outr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x362_outr_UnitPipe **/
