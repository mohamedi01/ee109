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

/** Hierarchy: x288 -> x289 -> x213 **/
/** BEGIN None x288_outr_UnitPipe **/
class x288_outr_UnitPipe_kernel(
  list_x256_fifo: List[FIFOInterface],
  list_x257: List[DecoupledIO[AppLoadData]],
  list_x253_inSRAM_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x288_outr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x288_outr_UnitPipe_iiCtr"))
  
  abstract class x288_outr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x257 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x257_p").asInstanceOf[(Int, Int)] )))
      val in_x253_inSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x253_inSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x256_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x256_fifo_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x257 = {io.in_x257} 
    def x253_inSRAM_0 = {io.in_x253_inSRAM_0} ; io.in_x253_inSRAM_0 := DontCare
    def x256_fifo = {io.in_x256_fifo} ; io.in_x256_fifo := DontCare
  }
  def connectWires0(module: x288_outr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x257 <> x257
    x253_inSRAM_0.connectLedger(module.io.in_x253_inSRAM_0)
    x256_fifo.connectLedger(module.io.in_x256_fifo)
  }
  val x256_fifo = list_x256_fifo(0)
  val x257 = list_x257(0)
  val x253_inSRAM_0 = list_x253_inSRAM_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x288_outr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x288_outr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x288_outr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x288_outr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x288_outr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x288_outr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x288_outr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X288_instrctr, cycles_x288_outr_UnitPipe.io.count, iters_x288_outr_UnitPipe.io.count, 0.U, 0.U)
      val x266_reg = (new x266_reg).m.io.asInstanceOf[StandardInterface]
      val x267_reg = (new x267_reg).m.io.asInstanceOf[StandardInterface]
      val x274_inr_UnitPipe = new x274_inr_UnitPipe_kernel(List(x256_fifo), List(x266_reg,x267_reg) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x274_inr_UnitPipe.sm.io.ctrDone := risingEdge(x274_inr_UnitPipe.sm.io.ctrInc)
      x274_inr_UnitPipe.backpressure := true.B | x274_inr_UnitPipe.sm.io.doneLatch
      x274_inr_UnitPipe.forwardpressure := ((~x256_fifo.empty.D(1.0-1) | ~(x256_fifo.active(1).out))) && (true.B) | x274_inr_UnitPipe.sm.io.doneLatch
      x274_inr_UnitPipe.sm.io.enableOut.zip(x274_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x274_inr_UnitPipe.sm.io.break := false.B
      x274_inr_UnitPipe.mask := true.B & true.B
      x274_inr_UnitPipe.configure("x274_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x274_inr_UnitPipe.kernel()
      val x371_rd_x267 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x371_rd_x267""")
      val x371_rd_x267_banks = List[UInt]()
      val x371_rd_x267_ofs = List[UInt]()
      val x371_rd_x267_en = List[Bool](true.B)
      val x371_rd_x267_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x371_rd_x267_shared_en")
      x371_rd_x267.toSeq.zip(x267_reg.connectRPort(371, x371_rd_x267_banks, x371_rd_x267_ofs, io.sigsIn.backpressure, x371_rd_x267_en.map(_ && x371_rd_x267_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x276_ctr = new CtrObject(Left(Some(0)), Right(x371_rd_x267), Left(Some(1)), 1, 32, false)
      val x277_ctrchain = (new CChainObject(List[CtrObject](x276_ctr), "x277_ctrchain")).cchain.io 
      x277_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x277_ctrchain_p", (x277_ctrchain.par, x277_ctrchain.widths))
      val x287_inr_Foreach = new x287_inr_Foreach_kernel(List(x257), List(x253_inSRAM_0,x266_reg) ,  Some(me), List(x277_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x287_inr_Foreach.sm.io.ctrDone := (x287_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x287_inr_Foreach.backpressure := true.B | x287_inr_Foreach.sm.io.doneLatch
      x287_inr_Foreach.forwardpressure := (x257.valid) && (true.B) | x287_inr_Foreach.sm.io.doneLatch
      x287_inr_Foreach.sm.io.enableOut.zip(x287_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x287_inr_Foreach.sm.io.break := false.B
      x287_inr_Foreach.mask := ~x287_inr_Foreach.cchain.head.output.noop & true.B
      x287_inr_Foreach.configure("x287_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x287_inr_Foreach.kernel()
    }
    val module = Module(new x288_outr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x288_outr_UnitPipe **/
