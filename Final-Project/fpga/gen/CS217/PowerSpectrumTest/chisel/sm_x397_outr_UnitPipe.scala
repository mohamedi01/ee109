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

/** Hierarchy: x397 -> x398 -> x480 -> x481 **/
/** BEGIN None x397_outr_UnitPipe **/
class x397_outr_UnitPipe_kernel(
  list_x365_fifo: List[FIFOInterface],
  list_x366: List[DecoupledIO[AppLoadData]],
  list_x327_imagSRAM_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x397_outr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x397_outr_UnitPipe_iiCtr"))
  
  abstract class x397_outr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x365_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x365_fifo_p").asInstanceOf[MemParams] ))
      val in_x366 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x366_p").asInstanceOf[(Int, Int)] )))
      val in_x327_imagSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x327_imagSRAM_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x365_fifo = {io.in_x365_fifo} ; io.in_x365_fifo := DontCare
    def x366 = {io.in_x366} 
    def x327_imagSRAM_0 = {io.in_x327_imagSRAM_0} ; io.in_x327_imagSRAM_0 := DontCare
  }
  def connectWires0(module: x397_outr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x365_fifo.connectLedger(module.io.in_x365_fifo)
    module.io.in_x366 <> x366
    x327_imagSRAM_0.connectLedger(module.io.in_x327_imagSRAM_0)
  }
  val x365_fifo = list_x365_fifo(0)
  val x366 = list_x366(0)
  val x327_imagSRAM_0 = list_x327_imagSRAM_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x397_outr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x397_outr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x397_outr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x397_outr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x397_outr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x397_outr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x397_outr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X397_instrctr, cycles_x397_outr_UnitPipe.io.count, iters_x397_outr_UnitPipe.io.count, 0.U, 0.U)
      val x375_reg = (new x375_reg).m.io.asInstanceOf[StandardInterface]
      val x376_reg = (new x376_reg).m.io.asInstanceOf[StandardInterface]
      val x383_inr_UnitPipe = new x383_inr_UnitPipe_kernel(List(x365_fifo), List(x376_reg,x375_reg) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x383_inr_UnitPipe.sm.io.ctrDone := risingEdge(x383_inr_UnitPipe.sm.io.ctrInc)
      x383_inr_UnitPipe.backpressure := true.B | x383_inr_UnitPipe.sm.io.doneLatch
      x383_inr_UnitPipe.forwardpressure := ((~x365_fifo.empty.D(1.0-1) | ~(x365_fifo.active(1).out))) && (true.B) | x383_inr_UnitPipe.sm.io.doneLatch
      x383_inr_UnitPipe.sm.io.enableOut.zip(x383_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x383_inr_UnitPipe.sm.io.break := false.B
      x383_inr_UnitPipe.mask := true.B & true.B
      x383_inr_UnitPipe.configure("x383_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x383_inr_UnitPipe.kernel()
      val x477_rd_x376 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x477_rd_x376""")
      val x477_rd_x376_banks = List[UInt]()
      val x477_rd_x376_ofs = List[UInt]()
      val x477_rd_x376_en = List[Bool](true.B)
      val x477_rd_x376_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x477_rd_x376_shared_en")
      x477_rd_x376.toSeq.zip(x376_reg.connectRPort(477, x477_rd_x376_banks, x477_rd_x376_ofs, io.sigsIn.backpressure, x477_rd_x376_en.map(_ && x477_rd_x376_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x385_ctr = new CtrObject(Left(Some(0)), Right(x477_rd_x376), Left(Some(1)), 1, 32, false)
      val x386_ctrchain = (new CChainObject(List[CtrObject](x385_ctr), "x386_ctrchain")).cchain.io 
      x386_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x386_ctrchain_p", (x386_ctrchain.par, x386_ctrchain.widths))
      val x396_inr_Foreach = new x396_inr_Foreach_kernel(List(x366), List(x375_reg,x327_imagSRAM_0) ,  Some(me), List(x386_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x396_inr_Foreach.sm.io.ctrDone := (x396_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x396_inr_Foreach.backpressure := true.B | x396_inr_Foreach.sm.io.doneLatch
      x396_inr_Foreach.forwardpressure := (x366.valid) && (true.B) | x396_inr_Foreach.sm.io.doneLatch
      x396_inr_Foreach.sm.io.enableOut.zip(x396_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x396_inr_Foreach.sm.io.break := false.B
      x396_inr_Foreach.mask := ~x396_inr_Foreach.cchain.head.output.noop & true.B
      x396_inr_Foreach.configure("x396_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x396_inr_Foreach.kernel()
    }
    val module = Module(new x397_outr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x397_outr_UnitPipe **/
