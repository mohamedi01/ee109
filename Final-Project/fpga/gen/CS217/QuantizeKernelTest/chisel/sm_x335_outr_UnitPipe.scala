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

/** Hierarchy: x335 -> x340 -> x341 -> x213 **/
/** BEGIN None x335_outr_UnitPipe **/
class x335_outr_UnitPipe_kernel(
  list_x251_outDRAM: List[FixedPoint],
  list_x309: List[DecoupledIO[AppCommandDense]],
  list_x254_outSRAM_0: List[StandardInterface],
  list_x310: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x335_outr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x335_outr_UnitPipe_iiCtr"))
  
  abstract class x335_outr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x251_outDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x310 = Decoupled(new AppStoreData(ModuleParams.getParams("x310_p").asInstanceOf[(Int,Int)] ))
      val in_x254_outSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x254_outSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x309 = Decoupled(new AppCommandDense(ModuleParams.getParams("x309_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x251_outDRAM = {io.in_x251_outDRAM} 
    def x310 = {io.in_x310} 
    def x254_outSRAM_0 = {io.in_x254_outSRAM_0} ; io.in_x254_outSRAM_0 := DontCare
    def x309 = {io.in_x309} 
  }
  def connectWires0(module: x335_outr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x251_outDRAM <> x251_outDRAM
    module.io.in_x310 <> x310
    x254_outSRAM_0.connectLedger(module.io.in_x254_outSRAM_0)
    module.io.in_x309 <> x309
  }
  val x251_outDRAM = list_x251_outDRAM(0)
  val x309 = list_x309(0)
  val x254_outSRAM_0 = list_x254_outSRAM_0(0)
  val x310 = list_x310(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x335_outr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x335_outr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x335_outr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x335_outr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x335_outr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x335_outr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x335_outr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X335_instrctr, cycles_x335_outr_UnitPipe.io.count, iters_x335_outr_UnitPipe.io.count, 0.U, 0.U)
      val x312_reg = (new x312_reg).m.io.asInstanceOf[StandardInterface]
      val x313_reg = (new x313_reg).m.io.asInstanceOf[StandardInterface]
      val x320_inr_UnitPipe = new x320_inr_UnitPipe_kernel(List(x251_outDRAM), List(x309), List(x312_reg,x313_reg) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x320_inr_UnitPipe.sm.io.ctrDone := risingEdge(x320_inr_UnitPipe.sm.io.ctrInc)
      x320_inr_UnitPipe.backpressure := x309.ready | x320_inr_UnitPipe.sm.io.doneLatch
      x320_inr_UnitPipe.forwardpressure := (true.B) && (true.B) | x320_inr_UnitPipe.sm.io.doneLatch
      x320_inr_UnitPipe.sm.io.enableOut.zip(x320_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x320_inr_UnitPipe.sm.io.break := false.B
      x320_inr_UnitPipe.mask := true.B & true.B
      x320_inr_UnitPipe.configure("x320_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x320_inr_UnitPipe.kernel()
      val x372_rd_x313 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x372_rd_x313""")
      val x372_rd_x313_banks = List[UInt]()
      val x372_rd_x313_ofs = List[UInt]()
      val x372_rd_x313_en = List[Bool](true.B)
      val x372_rd_x313_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x372_rd_x313_shared_en")
      x372_rd_x313.toSeq.zip(x313_reg.connectRPort(372, x372_rd_x313_banks, x372_rd_x313_ofs, io.sigsIn.backpressure, x372_rd_x313_en.map(_ && x372_rd_x313_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x322_ctr = new CtrObject(Left(Some(0)), Right(x372_rd_x313), Left(Some(1)), 1, 32, false)
      val x323_ctrchain = (new CChainObject(List[CtrObject](x322_ctr), "x323_ctrchain")).cchain.io 
      x323_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x323_ctrchain_p", (x323_ctrchain.par, x323_ctrchain.widths))
      val x334_inr_Foreach = new x334_inr_Foreach_kernel(List(x312_reg,x254_outSRAM_0), List(x310) ,  Some(me), List(x323_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x334_inr_Foreach.sm.io.ctrDone := (x334_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x334_inr_Foreach.backpressure := x310.ready | x334_inr_Foreach.sm.io.doneLatch
      x334_inr_Foreach.forwardpressure := (true.B) && (true.B) | x334_inr_Foreach.sm.io.doneLatch
      x334_inr_Foreach.sm.io.enableOut.zip(x334_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x334_inr_Foreach.sm.io.break := false.B
      x334_inr_Foreach.mask := ~x334_inr_Foreach.cchain.head.output.noop & true.B
      x334_inr_Foreach.configure("x334_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x334_inr_Foreach.kernel()
    }
    val module = Module(new x335_outr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x335_outr_UnitPipe **/
