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

/** Hierarchy: x602 -> x607 -> x608 -> x709 -> x710 **/
/** BEGIN None x602_outr_UnitPipe **/
class x602_outr_UnitPipe_kernel(
  list_b559: List[Bool],
  list_x554: List[DecoupledIO[AppStoreData]],
  list_x461_realDRAM: List[FixedPoint],
  list_x469_real2D_0: List[StandardInterface],
  list_x553: List[DecoupledIO[AppCommandDense]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x602_outr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x602_outr_UnitPipe_iiCtr"))
  
  abstract class x602_outr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b559 = Input(Bool())
      val in_x461_realDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x554 = Decoupled(new AppStoreData(ModuleParams.getParams("x554_p").asInstanceOf[(Int,Int)] ))
      val in_b558 = Input(new FixedPoint(true, 32, 0))
      val in_x553 = Decoupled(new AppCommandDense(ModuleParams.getParams("x553_p").asInstanceOf[(Int,Int)] ))
      val in_x469_real2D_0 = Flipped(new StandardInterface(ModuleParams.getParams("x469_real2D_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def b559 = {io.in_b559} 
    def x461_realDRAM = {io.in_x461_realDRAM} 
    def x554 = {io.in_x554} 
    def b558 = {io.in_b558} 
    def x553 = {io.in_x553} 
    def x469_real2D_0 = {io.in_x469_real2D_0} ; io.in_x469_real2D_0 := DontCare
  }
  def connectWires0(module: x602_outr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b559 <> b559
    module.io.in_x461_realDRAM <> x461_realDRAM
    module.io.in_x554 <> x554
    module.io.in_b558 <> b558
    module.io.in_x553 <> x553
    x469_real2D_0.connectLedger(module.io.in_x469_real2D_0)
  }
  val b559 = list_b559(0)
  val x554 = list_x554(0)
  val x461_realDRAM = list_x461_realDRAM(0)
  val b558 = list_x461_realDRAM(1)
  val x469_real2D_0 = list_x469_real2D_0(0)
  val x553 = list_x553(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x602_outr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x602_outr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x602_outr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x602_outr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x602_outr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x602_outr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x602_outr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X602_instrctr, cycles_x602_outr_UnitPipe.io.count, iters_x602_outr_UnitPipe.io.count, 0.U, 0.U)
      val x560_reg = (new x560_reg).m.io.asInstanceOf[StandardInterface]
      val x561_reg = (new x561_reg).m.io.asInstanceOf[StandardInterface]
      val x562_reg = (new x562_reg).m.io.asInstanceOf[StandardInterface]
      val x582_inr_UnitPipe = new x582_inr_UnitPipe_kernel(List(x461_realDRAM,b558), List(x553), List(x561_reg,x562_reg,x560_reg) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x582_inr_UnitPipe.sm.io.ctrDone := risingEdge(x582_inr_UnitPipe.sm.io.ctrInc)
      x582_inr_UnitPipe.backpressure := x553.ready | x582_inr_UnitPipe.sm.io.doneLatch
      x582_inr_UnitPipe.forwardpressure := (true.B) && (true.B) | x582_inr_UnitPipe.sm.io.doneLatch
      x582_inr_UnitPipe.sm.io.enableOut.zip(x582_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x582_inr_UnitPipe.sm.io.break := false.B
      x582_inr_UnitPipe.mask := true.B & true.B
      x582_inr_UnitPipe.configure("x582_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x582_inr_UnitPipe.kernel()
      val x702_rd_x562 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x702_rd_x562""")
      val x702_rd_x562_banks = List[UInt]()
      val x702_rd_x562_ofs = List[UInt]()
      val x702_rd_x562_en = List[Bool](true.B)
      val x702_rd_x562_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x702_rd_x562_shared_en")
      x702_rd_x562.toSeq.zip(x562_reg.connectRPort(702, x702_rd_x562_banks, x702_rd_x562_ofs, io.sigsIn.backpressure, x702_rd_x562_en.map(_ && x702_rd_x562_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x584_ctr = new CtrObject(Left(Some(0)), Right(x702_rd_x562), Left(Some(1)), 1, 32, false)
      val x585_ctrchain = (new CChainObject(List[CtrObject](x584_ctr), "x585_ctrchain")).cchain.io 
      x585_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x585_ctrchain_p", (x585_ctrchain.par, x585_ctrchain.widths))
      val x601_inr_Foreach = new x601_inr_Foreach_kernel(List(b558), List(x561_reg,x469_real2D_0,x560_reg), List(x554) ,  Some(me), List(x585_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x601_inr_Foreach.sm.io.ctrDone := (x601_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x601_inr_Foreach.backpressure := x554.ready | x601_inr_Foreach.sm.io.doneLatch
      x601_inr_Foreach.forwardpressure := (true.B) && (true.B) | x601_inr_Foreach.sm.io.doneLatch
      x601_inr_Foreach.sm.io.enableOut.zip(x601_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x601_inr_Foreach.sm.io.break := false.B
      x601_inr_Foreach.mask := ~x601_inr_Foreach.cchain.head.output.noop & true.B
      x601_inr_Foreach.configure("x601_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x601_inr_Foreach.kernel()
    }
    val module = Module(new x602_outr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x602_outr_UnitPipe **/
