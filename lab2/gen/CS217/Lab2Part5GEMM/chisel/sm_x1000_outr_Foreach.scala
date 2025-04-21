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

/** Hierarchy: x1000 -> x1001 -> x1250 -> x1251 -> x810 **/
/** BEGIN None x1000_outr_Foreach **/
class x1000_outr_Foreach_kernel(
  list_x936_fifo: List[FIFOInterface],
  list_x937: List[DecoupledIO[AppLoadData]],
  list_x934_tileA_sram_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x1000_outr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1000_outr_Foreach_iiCtr"))
  
  abstract class x1000_outr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x934_tileA_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x934_tileA_sram_0_p").asInstanceOf[NBufParams] ))
      val in_x937 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x937_p").asInstanceOf[(Int, Int)] )))
      val in_x936_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x936_fifo_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x934_tileA_sram_0 = {io.in_x934_tileA_sram_0} ; io.in_x934_tileA_sram_0 := DontCare
    def x937 = {io.in_x937} 
    def x936_fifo = {io.in_x936_fifo} ; io.in_x936_fifo := DontCare
  }
  def connectWires0(module: x1000_outr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x934_tileA_sram_0.connectLedger(module.io.in_x934_tileA_sram_0)
    module.io.in_x937 <> x937
    x936_fifo.connectLedger(module.io.in_x936_fifo)
  }
  val x936_fifo = list_x936_fifo(0)
  val x937 = list_x937(0)
  val x934_tileA_sram_0 = list_x934_tileA_sram_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1000_outr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1000_outr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1000_outr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1000_outr_Foreach = Module(new InstrumentationCounter())
      val iters_x1000_outr_Foreach = Module(new InstrumentationCounter())
      cycles_x1000_outr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1000_outr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1000_instrctr, cycles_x1000_outr_Foreach.io.count, iters_x1000_outr_Foreach.io.count, 0.U, 0.U)
      val b968 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b968.suggestName("b968")
      val b969 = ~io.sigsIn.cchainOutputs.head.oobs(0); b969.suggestName("b969")
      val x970_reg = (new x970_reg).m.io.asInstanceOf[StandardInterface]
      val x971_reg = (new x971_reg).m.io.asInstanceOf[StandardInterface]
      val x972_reg = (new x972_reg).m.io.asInstanceOf[StandardInterface]
      val x981_inr_UnitPipe = new x981_inr_UnitPipe_kernel(List(b969), List(x936_fifo), List(x970_reg,x971_reg,x972_reg) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x981_inr_UnitPipe.sm.io.ctrDone := risingEdge(x981_inr_UnitPipe.sm.io.ctrInc)
      x981_inr_UnitPipe.backpressure := true.B | x981_inr_UnitPipe.sm.io.doneLatch
      x981_inr_UnitPipe.forwardpressure := ((~x936_fifo.empty.D(1.0-1) | ~(x936_fifo.active(1).out))) && (true.B) | x981_inr_UnitPipe.sm.io.doneLatch
      x981_inr_UnitPipe.sm.io.enableOut.zip(x981_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x981_inr_UnitPipe.sm.io.break := false.B
      x981_inr_UnitPipe.mask := true.B & b969
      x981_inr_UnitPipe.configure("x981_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x981_inr_UnitPipe.kernel()
      val x1309_rd_x972 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1309_rd_x972""")
      val x1309_rd_x972_banks = List[UInt]()
      val x1309_rd_x972_ofs = List[UInt]()
      val x1309_rd_x972_en = List[Bool](true.B)
      val x1309_rd_x972_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1309_rd_x972_shared_en")
      x1309_rd_x972.toSeq.zip(x972_reg.connectRPort(1309, x1309_rd_x972_banks, x1309_rd_x972_ofs, io.sigsIn.backpressure, x1309_rd_x972_en.map(_ && x1309_rd_x972_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x983_ctr = new CtrObject(Left(Some(0)), Right(x1309_rd_x972), Left(Some(1)), 1, 32, false)
      val x984_ctrchain = (new CChainObject(List[CtrObject](x983_ctr), "x984_ctrchain")).cchain.io 
      x984_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x984_ctrchain_p", (x984_ctrchain.par, x984_ctrchain.widths))
      val x999_inr_Foreach = new x999_inr_Foreach_kernel(List(x937), List(x934_tileA_sram_0), List(b968), List(x970_reg,x971_reg), List(b969) ,  Some(me), List(x984_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x999_inr_Foreach.sm.io.ctrDone := (x999_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x999_inr_Foreach.backpressure := true.B | x999_inr_Foreach.sm.io.doneLatch
      x999_inr_Foreach.forwardpressure := (x937.valid) && (true.B) | x999_inr_Foreach.sm.io.doneLatch
      x999_inr_Foreach.sm.io.enableOut.zip(x999_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x999_inr_Foreach.sm.io.break := false.B
      x999_inr_Foreach.mask := ~x999_inr_Foreach.cchain.head.output.noop & b969
      x999_inr_Foreach.configure("x999_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x999_inr_Foreach.kernel()
    }
    val module = Module(new x1000_outr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1000_outr_Foreach **/
