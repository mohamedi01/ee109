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

/** Hierarchy: x358 -> x359 -> x450 -> x453 **/
/** BEGIN None x358_outr_Foreach **/
class x358_outr_Foreach_kernel(
  list_x298_fifo: List[FIFOInterface],
  list_x299: List[DecoupledIO[AppLoadData]],
  list_x294_matSram_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x358_outr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x358_outr_Foreach_iiCtr"))
  
  abstract class x358_outr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x298_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x298_fifo_p").asInstanceOf[MemParams] ))
      val in_x299 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x299_p").asInstanceOf[(Int, Int)] )))
      val in_x294_matSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x294_matSram_0_p").asInstanceOf[MemParams] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x298_fifo = {io.in_x298_fifo} ; io.in_x298_fifo := DontCare
    def x299 = {io.in_x299} 
    def x294_matSram_0 = {io.in_x294_matSram_0} ; io.in_x294_matSram_0 := DontCare
  }
  def connectWires0(module: x358_outr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x298_fifo.connectLedger(module.io.in_x298_fifo)
    module.io.in_x299 <> x299
    x294_matSram_0.connectLedger(module.io.in_x294_matSram_0)
  }
  val x298_fifo = list_x298_fifo(0)
  val x299 = list_x299(0)
  val x294_matSram_0 = list_x294_matSram_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x358_outr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x358_outr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x358_outr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val b326 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b326.suggestName("b326")
      val b327 = ~io.sigsIn.cchainOutputs.head.oobs(0); b327.suggestName("b327")
      val x328_reg = (new x328_reg).m.io.asInstanceOf[StandardInterface]
      val x329_reg = (new x329_reg).m.io.asInstanceOf[StandardInterface]
      val x330_reg = (new x330_reg).m.io.asInstanceOf[StandardInterface]
      val x339_inr_UnitPipe = new x339_inr_UnitPipe_kernel(List(b327), List(x298_fifo), List(x329_reg,x328_reg,x330_reg) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, rr)
      x339_inr_UnitPipe.sm.io.ctrDone := risingEdge(x339_inr_UnitPipe.sm.io.ctrInc)
      x339_inr_UnitPipe.backpressure := true.B | x339_inr_UnitPipe.sm.io.doneLatch
      x339_inr_UnitPipe.forwardpressure := ((~x298_fifo.empty.D(1.0-1) | ~(x298_fifo.active(1).out))) && (true.B) | x339_inr_UnitPipe.sm.io.doneLatch
      x339_inr_UnitPipe.sm.io.enableOut.zip(x339_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x339_inr_UnitPipe.sm.io.break := false.B
      x339_inr_UnitPipe.mask := true.B & b327
      x339_inr_UnitPipe.configure("x339_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x339_inr_UnitPipe.kernel()
      val x443_rd_x330 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x443_rd_x330""")
      val x443_rd_x330_banks = List[UInt]()
      val x443_rd_x330_ofs = List[UInt]()
      val x443_rd_x330_en = List[Bool](true.B)
      val x443_rd_x330_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x443_rd_x330_shared_en")
      x443_rd_x330.toSeq.zip(x330_reg.connectRPort(443, x443_rd_x330_banks, x443_rd_x330_ofs, io.sigsIn.backpressure, x443_rd_x330_en.map(_ && x443_rd_x330_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x341_ctr = new CtrObject(Left(Some(0)), Right(x443_rd_x330), Left(Some(1)), 1, 32, false)
      val x342_ctrchain = (new CChainObject(List[CtrObject](x341_ctr), "x342_ctrchain")).cchain.io 
      x342_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x342_ctrchain_p", (x342_ctrchain.par, x342_ctrchain.widths))
      val x357_inr_Foreach = new x357_inr_Foreach_kernel(List(b327), List(b326), List(x299), List(x329_reg,x328_reg,x294_matSram_0) ,  Some(me), List(x342_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, rr)
      x357_inr_Foreach.sm.io.ctrDone := (x357_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x357_inr_Foreach.backpressure := true.B | x357_inr_Foreach.sm.io.doneLatch
      x357_inr_Foreach.forwardpressure := (x299.valid) && (true.B) | x357_inr_Foreach.sm.io.doneLatch
      x357_inr_Foreach.sm.io.enableOut.zip(x357_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x357_inr_Foreach.sm.io.break := false.B
      x357_inr_Foreach.mask := ~x357_inr_Foreach.cchain.head.output.noop & b327
      x357_inr_Foreach.configure("x357_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x357_inr_Foreach.kernel()
    }
    val module = Module(new x358_outr_Foreach_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnrolledForeach x358_outr_Foreach **/
