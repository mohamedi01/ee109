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

/** Hierarchy: x315 -> x316 -> x244 **/
/** BEGIN None x315_outr_UnitPipe **/
class x315_outr_UnitPipe_kernel(
  list_x283_fifo: List[FIFOInterface],
  list_x284: List[DecoupledIO[AppLoadData]],
  list_x280_inSRAM_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x315_outr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x315_outr_UnitPipe_iiCtr"))
  
  abstract class x315_outr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x284 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x284_p").asInstanceOf[(Int, Int)] )))
      val in_x280_inSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x280_inSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x283_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x283_fifo_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x284 = {io.in_x284} 
    def x280_inSRAM_0 = {io.in_x280_inSRAM_0} ; io.in_x280_inSRAM_0 := DontCare
    def x283_fifo = {io.in_x283_fifo} ; io.in_x283_fifo := DontCare
  }
  def connectWires0(module: x315_outr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x284 <> x284
    x280_inSRAM_0.connectLedger(module.io.in_x280_inSRAM_0)
    x283_fifo.connectLedger(module.io.in_x283_fifo)
  }
  val x283_fifo = list_x283_fifo(0)
  val x284 = list_x284(0)
  val x280_inSRAM_0 = list_x280_inSRAM_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x315_outr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x315_outr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x315_outr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x315_outr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x315_outr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x315_outr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x315_outr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X315_instrctr, cycles_x315_outr_UnitPipe.io.count, iters_x315_outr_UnitPipe.io.count, 0.U, 0.U)
      val x293_reg = (new x293_reg).m.io.asInstanceOf[StandardInterface]
      val x294_reg = (new x294_reg).m.io.asInstanceOf[StandardInterface]
      val x301_inr_UnitPipe = new x301_inr_UnitPipe_kernel(List(x283_fifo), List(x293_reg,x294_reg) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x301_inr_UnitPipe.sm.io.ctrDone := risingEdge(x301_inr_UnitPipe.sm.io.ctrInc)
      x301_inr_UnitPipe.backpressure := true.B | x301_inr_UnitPipe.sm.io.doneLatch
      x301_inr_UnitPipe.forwardpressure := ((~x283_fifo.empty.D(1.0-1) | ~(x283_fifo.active(1).out))) && (true.B) | x301_inr_UnitPipe.sm.io.doneLatch
      x301_inr_UnitPipe.sm.io.enableOut.zip(x301_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x301_inr_UnitPipe.sm.io.break := false.B
      x301_inr_UnitPipe.mask := true.B & true.B
      x301_inr_UnitPipe.configure("x301_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x301_inr_UnitPipe.kernel()
      val x406_rd_x294 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x406_rd_x294""")
      val x406_rd_x294_banks = List[UInt]()
      val x406_rd_x294_ofs = List[UInt]()
      val x406_rd_x294_en = List[Bool](true.B)
      val x406_rd_x294_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x406_rd_x294_shared_en")
      x406_rd_x294.toSeq.zip(x294_reg.connectRPort(406, x406_rd_x294_banks, x406_rd_x294_ofs, io.sigsIn.backpressure, x406_rd_x294_en.map(_ && x406_rd_x294_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x303_ctr = new CtrObject(Left(Some(0)), Right(x406_rd_x294), Left(Some(1)), 1, 32, false)
      val x304_ctrchain = (new CChainObject(List[CtrObject](x303_ctr), "x304_ctrchain")).cchain.io 
      x304_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x304_ctrchain_p", (x304_ctrchain.par, x304_ctrchain.widths))
      val x314_inr_Foreach = new x314_inr_Foreach_kernel(List(x284), List(x280_inSRAM_0,x293_reg) ,  Some(me), List(x304_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x314_inr_Foreach.sm.io.ctrDone := (x314_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x314_inr_Foreach.backpressure := true.B | x314_inr_Foreach.sm.io.doneLatch
      x314_inr_Foreach.forwardpressure := (x284.valid) && (true.B) | x314_inr_Foreach.sm.io.doneLatch
      x314_inr_Foreach.sm.io.enableOut.zip(x314_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x314_inr_Foreach.sm.io.break := false.B
      x314_inr_Foreach.mask := ~x314_inr_Foreach.cchain.head.output.noop & true.B
      x314_inr_Foreach.configure("x314_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x314_inr_Foreach.kernel()
    }
    val module = Module(new x315_outr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x315_outr_UnitPipe **/
