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

/** Hierarchy: x438 -> x443 -> x444 -> x481 **/
/** BEGIN None x438_outr_UnitPipe **/
class x438_outr_UnitPipe_kernel(
  list_x323_outDRAM: List[FixedPoint],
  list_x412: List[DecoupledIO[AppCommandDense]],
  list_x328_outSRAM_0: List[StandardInterface],
  list_x413: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x438_outr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x438_outr_UnitPipe_iiCtr"))
  
  abstract class x438_outr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x328_outSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x328_outSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x412 = Decoupled(new AppCommandDense(ModuleParams.getParams("x412_p").asInstanceOf[(Int,Int)] ))
      val in_x413 = Decoupled(new AppStoreData(ModuleParams.getParams("x413_p").asInstanceOf[(Int,Int)] ))
      val in_x323_outDRAM = Input(new FixedPoint(true, 64, 0))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x328_outSRAM_0 = {io.in_x328_outSRAM_0} ; io.in_x328_outSRAM_0 := DontCare
    def x412 = {io.in_x412} 
    def x413 = {io.in_x413} 
    def x323_outDRAM = {io.in_x323_outDRAM} 
  }
  def connectWires0(module: x438_outr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x328_outSRAM_0.connectLedger(module.io.in_x328_outSRAM_0)
    module.io.in_x412 <> x412
    module.io.in_x413 <> x413
    module.io.in_x323_outDRAM <> x323_outDRAM
  }
  val x323_outDRAM = list_x323_outDRAM(0)
  val x412 = list_x412(0)
  val x328_outSRAM_0 = list_x328_outSRAM_0(0)
  val x413 = list_x413(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x438_outr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x438_outr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x438_outr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x438_outr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x438_outr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x438_outr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x438_outr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X438_instrctr, cycles_x438_outr_UnitPipe.io.count, iters_x438_outr_UnitPipe.io.count, 0.U, 0.U)
      val x415_reg = (new x415_reg).m.io.asInstanceOf[StandardInterface]
      val x416_reg = (new x416_reg).m.io.asInstanceOf[StandardInterface]
      val x423_inr_UnitPipe = new x423_inr_UnitPipe_kernel(List(x323_outDRAM), List(x412), List(x416_reg,x415_reg) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x423_inr_UnitPipe.sm.io.ctrDone := risingEdge(x423_inr_UnitPipe.sm.io.ctrInc)
      x423_inr_UnitPipe.backpressure := x412.ready | x423_inr_UnitPipe.sm.io.doneLatch
      x423_inr_UnitPipe.forwardpressure := (true.B) && (true.B) | x423_inr_UnitPipe.sm.io.doneLatch
      x423_inr_UnitPipe.sm.io.enableOut.zip(x423_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x423_inr_UnitPipe.sm.io.break := false.B
      x423_inr_UnitPipe.mask := true.B & true.B
      x423_inr_UnitPipe.configure("x423_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x423_inr_UnitPipe.kernel()
      val x478_rd_x416 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x478_rd_x416""")
      val x478_rd_x416_banks = List[UInt]()
      val x478_rd_x416_ofs = List[UInt]()
      val x478_rd_x416_en = List[Bool](true.B)
      val x478_rd_x416_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x478_rd_x416_shared_en")
      x478_rd_x416.toSeq.zip(x416_reg.connectRPort(478, x478_rd_x416_banks, x478_rd_x416_ofs, io.sigsIn.backpressure, x478_rd_x416_en.map(_ && x478_rd_x416_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x425_ctr = new CtrObject(Left(Some(0)), Right(x478_rd_x416), Left(Some(1)), 1, 32, false)
      val x426_ctrchain = (new CChainObject(List[CtrObject](x425_ctr), "x426_ctrchain")).cchain.io 
      x426_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x426_ctrchain_p", (x426_ctrchain.par, x426_ctrchain.widths))
      val x437_inr_Foreach = new x437_inr_Foreach_kernel(List(x328_outSRAM_0,x415_reg), List(x413) ,  Some(me), List(x426_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x437_inr_Foreach.sm.io.ctrDone := (x437_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x437_inr_Foreach.backpressure := x413.ready | x437_inr_Foreach.sm.io.doneLatch
      x437_inr_Foreach.forwardpressure := (true.B) && (true.B) | x437_inr_Foreach.sm.io.doneLatch
      x437_inr_Foreach.sm.io.enableOut.zip(x437_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x437_inr_Foreach.sm.io.break := false.B
      x437_inr_Foreach.mask := ~x437_inr_Foreach.cchain.head.output.noop & true.B
      x437_inr_Foreach.configure("x437_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x437_inr_Foreach.kernel()
    }
    val module = Module(new x438_outr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x438_outr_UnitPipe **/
