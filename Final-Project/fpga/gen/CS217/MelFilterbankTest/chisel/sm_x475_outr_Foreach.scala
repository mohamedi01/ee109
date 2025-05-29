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

/** Hierarchy: x475 -> x476 -> x613 -> x614 **/
/** BEGIN None x475_outr_Foreach **/
class x475_outr_Foreach_kernel(
  list_x415_fifo: List[FIFOInterface],
  list_x416: List[DecoupledIO[AppLoadData]],
  list_x411_matSRAM_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x475_outr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x475_outr_Foreach_iiCtr"))
  
  abstract class x475_outr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x416 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x416_p").asInstanceOf[(Int, Int)] )))
      val in_x411_matSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x411_matSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x415_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x415_fifo_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x416 = {io.in_x416} 
    def x411_matSRAM_0 = {io.in_x411_matSRAM_0} ; io.in_x411_matSRAM_0 := DontCare
    def x415_fifo = {io.in_x415_fifo} ; io.in_x415_fifo := DontCare
  }
  def connectWires0(module: x475_outr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x416 <> x416
    x411_matSRAM_0.connectLedger(module.io.in_x411_matSRAM_0)
    x415_fifo.connectLedger(module.io.in_x415_fifo)
  }
  val x415_fifo = list_x415_fifo(0)
  val x416 = list_x416(0)
  val x411_matSRAM_0 = list_x411_matSRAM_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x475_outr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x475_outr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x475_outr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x475_outr_Foreach = Module(new InstrumentationCounter())
      val iters_x475_outr_Foreach = Module(new InstrumentationCounter())
      cycles_x475_outr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x475_outr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X475_instrctr, cycles_x475_outr_Foreach.io.count, iters_x475_outr_Foreach.io.count, 0.U, 0.U)
      val b443 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b443.suggestName("b443")
      val b444 = ~io.sigsIn.cchainOutputs.head.oobs(0); b444.suggestName("b444")
      val x445_reg = (new x445_reg).m.io.asInstanceOf[StandardInterface]
      val x446_reg = (new x446_reg).m.io.asInstanceOf[StandardInterface]
      val x447_reg = (new x447_reg).m.io.asInstanceOf[StandardInterface]
      val x456_inr_UnitPipe = new x456_inr_UnitPipe_kernel(List(b444), List(x415_fifo), List(x445_reg,x446_reg,x447_reg) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x456_inr_UnitPipe.sm.io.ctrDone := risingEdge(x456_inr_UnitPipe.sm.io.ctrInc)
      x456_inr_UnitPipe.backpressure := true.B | x456_inr_UnitPipe.sm.io.doneLatch
      x456_inr_UnitPipe.forwardpressure := ((~x415_fifo.empty.D(1.0-1) | ~(x415_fifo.active(1).out))) && (true.B) | x456_inr_UnitPipe.sm.io.doneLatch
      x456_inr_UnitPipe.sm.io.enableOut.zip(x456_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x456_inr_UnitPipe.sm.io.break := false.B
      x456_inr_UnitPipe.mask := true.B & b444
      x456_inr_UnitPipe.configure("x456_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x456_inr_UnitPipe.kernel()
      val x599_rd_x447 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x599_rd_x447""")
      val x599_rd_x447_banks = List[UInt]()
      val x599_rd_x447_ofs = List[UInt]()
      val x599_rd_x447_en = List[Bool](true.B)
      val x599_rd_x447_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x599_rd_x447_shared_en")
      x599_rd_x447.toSeq.zip(x447_reg.connectRPort(599, x599_rd_x447_banks, x599_rd_x447_ofs, io.sigsIn.backpressure, x599_rd_x447_en.map(_ && x599_rd_x447_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x458_ctr = new CtrObject(Left(Some(0)), Right(x599_rd_x447), Left(Some(1)), 1, 32, false)
      val x459_ctrchain = (new CChainObject(List[CtrObject](x458_ctr), "x459_ctrchain")).cchain.io 
      x459_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x459_ctrchain_p", (x459_ctrchain.par, x459_ctrchain.widths))
      val x474_inr_Foreach = new x474_inr_Foreach_kernel(List(b444), List(b443), List(x416), List(x411_matSRAM_0,x445_reg,x446_reg) ,  Some(me), List(x459_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x474_inr_Foreach.sm.io.ctrDone := (x474_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x474_inr_Foreach.backpressure := true.B | x474_inr_Foreach.sm.io.doneLatch
      x474_inr_Foreach.forwardpressure := (x416.valid) && (true.B) | x474_inr_Foreach.sm.io.doneLatch
      x474_inr_Foreach.sm.io.enableOut.zip(x474_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x474_inr_Foreach.sm.io.break := false.B
      x474_inr_Foreach.mask := ~x474_inr_Foreach.cchain.head.output.noop & b444
      x474_inr_Foreach.configure("x474_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x474_inr_Foreach.kernel()
    }
    val module = Module(new x475_outr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x475_outr_Foreach **/
