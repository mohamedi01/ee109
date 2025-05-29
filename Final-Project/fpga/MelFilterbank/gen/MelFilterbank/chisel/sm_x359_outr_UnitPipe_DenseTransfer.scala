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

/** Hierarchy: x359 -> x450 -> x453 **/
/** BEGIN Some(DenseTransfer) x359_outr_UnitPipe_DenseTransfer **/
class x359_outr_UnitPipe_DenseTransfer_kernel(
  list_x291_matDram: List[FixedPoint],
  list_x297: List[DecoupledIO[AppCommandDense]],
  list_x299: List[DecoupledIO[AppLoadData]],
  list_x294_matSram_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 2, isFSM = false   , latency = 0.0.toInt, myName = "x359_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x359_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x359_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x291_matDram = Input(new FixedPoint(true, 64, 0))
      val in_x294_matSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x294_matSram_0_p").asInstanceOf[MemParams] ))
      val in_x297 = Decoupled(new AppCommandDense(ModuleParams.getParams("x297_p").asInstanceOf[(Int,Int)] ))
      val in_x299 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x299_p").asInstanceOf[(Int, Int)] )))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 2, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 2))
      val rr = Input(Bool())
    })
    def x291_matDram = {io.in_x291_matDram} 
    def x294_matSram_0 = {io.in_x294_matSram_0} ; io.in_x294_matSram_0 := DontCare
    def x297 = {io.in_x297} 
    def x299 = {io.in_x299} 
  }
  def connectWires0(module: x359_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x291_matDram <> x291_matDram
    x294_matSram_0.connectLedger(module.io.in_x294_matSram_0)
    module.io.in_x297 <> x297
    module.io.in_x299 <> x299
  }
  val x291_matDram = list_x291_matDram(0)
  val x297 = list_x297(0)
  val x299 = list_x299(0)
  val x294_matSram_0 = list_x294_matSram_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x359_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x359_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x359_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x298_fifo = (new x298_fifo).m.io.asInstanceOf[FIFOInterface]
      val x300_ctr = new CtrObject(Left(Some(0)), Left(Some(80)), Left(Some(1)), 1, 9, false)
      val x301_ctrchain = (new CChainObject(List[CtrObject](x300_ctr), "x301_ctrchain")).cchain.io 
      x301_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x301_ctrchain_p", (x301_ctrchain.par, x301_ctrchain.widths))
      val x322_inr_Foreach = new x322_inr_Foreach_kernel(List(x291_matDram), List(x298_fifo), List(x297) ,  Some(me), List(x301_ctrchain), 0, 1, 1, List(1), List(32), breakpoints, rr)
      x322_inr_Foreach.sm.io.ctrDone := (x322_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x322_inr_Foreach.backpressure := x297.ready & (~x298_fifo.full.D(9.8-1) | ~(x298_fifo.active(0).out)) | x322_inr_Foreach.sm.io.doneLatch
      x322_inr_Foreach.forwardpressure := (true.B) && (true.B) | x322_inr_Foreach.sm.io.doneLatch
      x322_inr_Foreach.sm.io.enableOut.zip(x322_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x322_inr_Foreach.sm.io.break := false.B
      x322_inr_Foreach.mask := ~x322_inr_Foreach.cchain.head.output.noop & true.B
      x322_inr_Foreach.configure("x322_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x322_inr_Foreach.kernel()
      val x324_ctr = new CtrObject(Left(Some(0)), Left(Some(80)), Left(Some(1)), 1, 9, false)
      val x325_ctrchain = (new CChainObject(List[CtrObject](x324_ctr), "x325_ctrchain")).cchain.io 
      x325_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x325_ctrchain_p", (x325_ctrchain.par, x325_ctrchain.widths))
      val x358_outr_Foreach = new x358_outr_Foreach_kernel(List(x298_fifo), List(x299), List(x294_matSram_0) ,  Some(me), List(x325_ctrchain), 1, 2, 1, List(1), List(32), breakpoints, rr)
      x358_outr_Foreach.sm.io.ctrDone := (x358_outr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x358_outr_Foreach.backpressure := true.B | x358_outr_Foreach.sm.io.doneLatch
      x358_outr_Foreach.forwardpressure := (true.B) && (true.B) | x358_outr_Foreach.sm.io.doneLatch
      x358_outr_Foreach.sm.io.enableOut.zip(x358_outr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x358_outr_Foreach.sm.io.break := false.B
      x358_outr_Foreach.mask := ~x358_outr_Foreach.cchain.head.output.noop & true.B
      x358_outr_Foreach.configure("x358_outr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x358_outr_Foreach.kernel()
    }
    val module = Module(new x359_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x359_outr_UnitPipe_DenseTransfer **/
