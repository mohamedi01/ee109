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

/** Hierarchy: x568 -> x573 -> x574 -> x614 **/
/** BEGIN None x568_outr_UnitPipe **/
class x568_outr_UnitPipe_kernel(
  list_x408_outDRAM: List[FixedPoint],
  list_x542: List[DecoupledIO[AppCommandDense]],
  list_x413_outSRAM_0: List[StandardInterface],
  list_x543: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x568_outr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x568_outr_UnitPipe_iiCtr"))
  
  abstract class x568_outr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x408_outDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x542 = Decoupled(new AppCommandDense(ModuleParams.getParams("x542_p").asInstanceOf[(Int,Int)] ))
      val in_x413_outSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x413_outSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x543 = Decoupled(new AppStoreData(ModuleParams.getParams("x543_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x408_outDRAM = {io.in_x408_outDRAM} 
    def x542 = {io.in_x542} 
    def x413_outSRAM_0 = {io.in_x413_outSRAM_0} ; io.in_x413_outSRAM_0 := DontCare
    def x543 = {io.in_x543} 
  }
  def connectWires0(module: x568_outr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x408_outDRAM <> x408_outDRAM
    module.io.in_x542 <> x542
    x413_outSRAM_0.connectLedger(module.io.in_x413_outSRAM_0)
    module.io.in_x543 <> x543
  }
  val x408_outDRAM = list_x408_outDRAM(0)
  val x542 = list_x542(0)
  val x413_outSRAM_0 = list_x413_outSRAM_0(0)
  val x543 = list_x543(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x568_outr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x568_outr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x568_outr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x568_outr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x568_outr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x568_outr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x568_outr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X568_instrctr, cycles_x568_outr_UnitPipe.io.count, iters_x568_outr_UnitPipe.io.count, 0.U, 0.U)
      val x545_reg = (new x545_reg).m.io.asInstanceOf[StandardInterface]
      val x546_reg = (new x546_reg).m.io.asInstanceOf[StandardInterface]
      val x553_inr_UnitPipe = new x553_inr_UnitPipe_kernel(List(x408_outDRAM), List(x542), List(x546_reg,x545_reg) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x553_inr_UnitPipe.sm.io.ctrDone := risingEdge(x553_inr_UnitPipe.sm.io.ctrInc)
      x553_inr_UnitPipe.backpressure := x542.ready | x553_inr_UnitPipe.sm.io.doneLatch
      x553_inr_UnitPipe.forwardpressure := (true.B) && (true.B) | x553_inr_UnitPipe.sm.io.doneLatch
      x553_inr_UnitPipe.sm.io.enableOut.zip(x553_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x553_inr_UnitPipe.sm.io.break := false.B
      x553_inr_UnitPipe.mask := true.B & true.B
      x553_inr_UnitPipe.configure("x553_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x553_inr_UnitPipe.kernel()
      val x601_rd_x546 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x601_rd_x546""")
      val x601_rd_x546_banks = List[UInt]()
      val x601_rd_x546_ofs = List[UInt]()
      val x601_rd_x546_en = List[Bool](true.B)
      val x601_rd_x546_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x601_rd_x546_shared_en")
      x601_rd_x546.toSeq.zip(x546_reg.connectRPort(601, x601_rd_x546_banks, x601_rd_x546_ofs, io.sigsIn.backpressure, x601_rd_x546_en.map(_ && x601_rd_x546_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x555_ctr = new CtrObject(Left(Some(0)), Right(x601_rd_x546), Left(Some(1)), 1, 32, false)
      val x556_ctrchain = (new CChainObject(List[CtrObject](x555_ctr), "x556_ctrchain")).cchain.io 
      x556_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x556_ctrchain_p", (x556_ctrchain.par, x556_ctrchain.widths))
      val x567_inr_Foreach = new x567_inr_Foreach_kernel(List(x545_reg,x413_outSRAM_0), List(x543) ,  Some(me), List(x556_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x567_inr_Foreach.sm.io.ctrDone := (x567_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x567_inr_Foreach.backpressure := x543.ready | x567_inr_Foreach.sm.io.doneLatch
      x567_inr_Foreach.forwardpressure := (true.B) && (true.B) | x567_inr_Foreach.sm.io.doneLatch
      x567_inr_Foreach.sm.io.enableOut.zip(x567_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x567_inr_Foreach.sm.io.break := false.B
      x567_inr_Foreach.mask := ~x567_inr_Foreach.cchain.head.output.noop & true.B
      x567_inr_Foreach.configure("x567_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x567_inr_Foreach.kernel()
    }
    val module = Module(new x568_outr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x568_outr_UnitPipe **/
