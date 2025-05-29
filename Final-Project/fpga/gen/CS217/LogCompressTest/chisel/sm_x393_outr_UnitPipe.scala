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

/** Hierarchy: x393 -> x398 -> x399 -> x439 **/
/** BEGIN None x393_outr_UnitPipe **/
class x393_outr_UnitPipe_kernel(
  list_x292_outDRAM: List[FixedPoint],
  list_x367: List[DecoupledIO[AppCommandDense]],
  list_x295_out_0: List[StandardInterface],
  list_x368: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x393_outr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x393_outr_UnitPipe_iiCtr"))
  
  abstract class x393_outr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x292_outDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x367 = Decoupled(new AppCommandDense(ModuleParams.getParams("x367_p").asInstanceOf[(Int,Int)] ))
      val in_x295_out_0 = Flipped(new StandardInterface(ModuleParams.getParams("x295_out_0_p").asInstanceOf[MemParams] ))
      val in_x368 = Decoupled(new AppStoreData(ModuleParams.getParams("x368_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x292_outDRAM = {io.in_x292_outDRAM} 
    def x367 = {io.in_x367} 
    def x295_out_0 = {io.in_x295_out_0} ; io.in_x295_out_0 := DontCare
    def x368 = {io.in_x368} 
  }
  def connectWires0(module: x393_outr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x292_outDRAM <> x292_outDRAM
    module.io.in_x367 <> x367
    x295_out_0.connectLedger(module.io.in_x295_out_0)
    module.io.in_x368 <> x368
  }
  val x292_outDRAM = list_x292_outDRAM(0)
  val x367 = list_x367(0)
  val x295_out_0 = list_x295_out_0(0)
  val x368 = list_x368(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x393_outr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x393_outr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x393_outr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x393_outr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x393_outr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x393_outr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x393_outr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X393_instrctr, cycles_x393_outr_UnitPipe.io.count, iters_x393_outr_UnitPipe.io.count, 0.U, 0.U)
      val x370_reg = (new x370_reg).m.io.asInstanceOf[StandardInterface]
      val x371_reg = (new x371_reg).m.io.asInstanceOf[StandardInterface]
      val x378_inr_UnitPipe = new x378_inr_UnitPipe_kernel(List(x292_outDRAM), List(x367), List(x371_reg,x370_reg) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x378_inr_UnitPipe.sm.io.ctrDone := risingEdge(x378_inr_UnitPipe.sm.io.ctrInc)
      x378_inr_UnitPipe.backpressure := x367.ready | x378_inr_UnitPipe.sm.io.doneLatch
      x378_inr_UnitPipe.forwardpressure := (true.B) && (true.B) | x378_inr_UnitPipe.sm.io.doneLatch
      x378_inr_UnitPipe.sm.io.enableOut.zip(x378_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x378_inr_UnitPipe.sm.io.break := false.B
      x378_inr_UnitPipe.mask := true.B & true.B
      x378_inr_UnitPipe.configure("x378_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x378_inr_UnitPipe.kernel()
      val x437_rd_x371 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x437_rd_x371""")
      val x437_rd_x371_banks = List[UInt]()
      val x437_rd_x371_ofs = List[UInt]()
      val x437_rd_x371_en = List[Bool](true.B)
      val x437_rd_x371_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x437_rd_x371_shared_en")
      x437_rd_x371.toSeq.zip(x371_reg.connectRPort(437, x437_rd_x371_banks, x437_rd_x371_ofs, io.sigsIn.backpressure, x437_rd_x371_en.map(_ && x437_rd_x371_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x380_ctr = new CtrObject(Left(Some(0)), Right(x437_rd_x371), Left(Some(1)), 1, 32, false)
      val x381_ctrchain = (new CChainObject(List[CtrObject](x380_ctr), "x381_ctrchain")).cchain.io 
      x381_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x381_ctrchain_p", (x381_ctrchain.par, x381_ctrchain.widths))
      val x392_inr_Foreach = new x392_inr_Foreach_kernel(List(x295_out_0,x370_reg), List(x368) ,  Some(me), List(x381_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x392_inr_Foreach.sm.io.ctrDone := (x392_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x392_inr_Foreach.backpressure := x368.ready | x392_inr_Foreach.sm.io.doneLatch
      x392_inr_Foreach.forwardpressure := (true.B) && (true.B) | x392_inr_Foreach.sm.io.doneLatch
      x392_inr_Foreach.sm.io.enableOut.zip(x392_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x392_inr_Foreach.sm.io.break := false.B
      x392_inr_Foreach.mask := ~x392_inr_Foreach.cchain.head.output.noop & true.B
      x392_inr_Foreach.configure("x392_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x392_inr_Foreach.kernel()
    }
    val module = Module(new x393_outr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x393_outr_UnitPipe **/
