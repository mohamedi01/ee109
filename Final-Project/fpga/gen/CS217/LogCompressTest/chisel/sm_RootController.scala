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

/** Hierarchy: x439 **/
/** BEGIN None RootController **/
class RootController_kernel(
  list_x369: List[DecoupledIO[Bool]],
  list_x298: List[DecoupledIO[AppLoadData]],
  list_x368: List[DecoupledIO[AppStoreData]],
  list_x292_outDRAM: List[FixedPoint],
  list_x296: List[DecoupledIO[AppCommandDense]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 4, isFSM = false   , latency = 0.0.toInt, myName = "RootController_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "RootController_iiCtr"))
  
  abstract class RootController_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x292_outDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x298 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x298_p").asInstanceOf[(Int, Int)] )))
      val in_x296 = Decoupled(new AppCommandDense(ModuleParams.getParams("x296_p").asInstanceOf[(Int,Int)] ))
      val in_x291_inDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x367 = Decoupled(new AppCommandDense(ModuleParams.getParams("x367_p").asInstanceOf[(Int,Int)] ))
      val in_x368 = Decoupled(new AppStoreData(ModuleParams.getParams("x368_p").asInstanceOf[(Int,Int)] ))
      val in_x369 = Flipped(Decoupled(Bool()))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(4, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(4, 1))
      val rr = Input(Bool())
    })
    def x292_outDRAM = {io.in_x292_outDRAM} 
    def x298 = {io.in_x298} 
    def x296 = {io.in_x296} 
    def x291_inDRAM = {io.in_x291_inDRAM} 
    def x367 = {io.in_x367} 
    def x368 = {io.in_x368} 
    def x369 = {io.in_x369} 
  }
  def connectWires0(module: RootController_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x292_outDRAM <> x292_outDRAM
    module.io.in_x298 <> x298
    module.io.in_x296 <> x296
    module.io.in_x291_inDRAM <> x291_inDRAM
    module.io.in_x367 <> x367
    module.io.in_x368 <> x368
    module.io.in_x369 <> x369
  }
  val x369 = list_x369(0)
  val x298 = list_x298(0)
  val x368 = list_x368(0)
  val x292_outDRAM = list_x292_outDRAM(0)
  val x291_inDRAM = list_x292_outDRAM(1)
  val x296 = list_x296(0)
  val x367 = list_x296(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "RootController")
    implicit val stack = ControllerStack.stack.toList
    class RootController_concrete(depth: Int)(implicit stack: List[KernelHash]) extends RootController_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_RootController = Module(new InstrumentationCounter())
      val iters_RootController = Module(new InstrumentationCounter())
      cycles_RootController.io.enable := io.sigsIn.baseEn
      iters_RootController.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X439_instrctr, cycles_RootController.io.count, iters_RootController.io.count, 0.U, 0.U)
      val x294_buf_0 = (new x294_buf_0).m.io.asInstanceOf[StandardInterface]
      val x295_out_0 = (new x295_out_0).m.io.asInstanceOf[StandardInterface]
      val x330_outr_UnitPipe_DenseTransfer = new x330_outr_UnitPipe_DenseTransfer_kernel(List(x291_inDRAM), List(x296), List(x298), List(x294_buf_0) ,  Some(me), List(), 0, 2, 2, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x330_outr_UnitPipe_DenseTransfer.backpressure := true.B | x330_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x330_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x330_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x330_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x330_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x330_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x330_outr_UnitPipe_DenseTransfer.mask := true.B & true.B
      x330_outr_UnitPipe_DenseTransfer.configure("x330_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x330_outr_UnitPipe_DenseTransfer.kernel()
      val x331_mx_0 = (new x331_mx_0).m.io.asInstanceOf[FixOpAccumBundle]
      val x332_ctr = new CtrObject(Left(Some(0)), Left(Some(2)), Left(Some(1)), 1, 4, false)
      val x333_ctrchain = (new CChainObject(List[CtrObject](x332_ctr), "x333_ctrchain")).cchain.io 
      x333_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x333_ctrchain_p", (x333_ctrchain.par, x333_ctrchain.widths))
      val x343_inr_Reduce = new x343_inr_Reduce_kernel(List(x331_mx_0), List(x294_buf_0) ,  Some(me), List(x333_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x343_inr_Reduce.sm.io.ctrDone := (x343_inr_Reduce.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x343_inr_Reduce.backpressure := true.B | x343_inr_Reduce.sm.io.doneLatch
      x343_inr_Reduce.forwardpressure := (true.B) && (true.B) | x343_inr_Reduce.sm.io.doneLatch
      x343_inr_Reduce.sm.io.enableOut.zip(x343_inr_Reduce.smEnableOuts).foreach{case (l,r) => r := l}
      x343_inr_Reduce.sm.io.break := false.B
      x343_inr_Reduce.mask := ~x343_inr_Reduce.cchain.head.output.noop & true.B
      x343_inr_Reduce.configure("x343_inr_Reduce", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x343_inr_Reduce.kernel()
      val x344_ctr = new CtrObject(Left(Some(0)), Left(Some(2)), Left(Some(1)), 1, 4, false)
      val x345_ctrchain = (new CChainObject(List[CtrObject](x344_ctr), "x345_ctrchain")).cchain.io 
      x345_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x345_ctrchain_p", (x345_ctrchain.par, x345_ctrchain.widths))
      val x355_ctr = new CtrObject(Left(Some(0)), Left(Some(2)), Left(Some(1)), 1, 4, false)
      val x356_ctrchain = (new CChainObject(List[CtrObject](x355_ctr), "x356_ctrchain")).cchain.io 
      x356_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x356_ctrchain_p", (x356_ctrchain.par, x356_ctrchain.widths))
      val x438 = new x438_kernel(List(x331_mx_0), List(x356_ctrchain,x345_ctrchain), List(x295_out_0,x294_buf_0) ,  Some(me), List(), 2, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x438.sm.io.ctrDone := risingEdge(x438.sm.io.ctrInc)
      x438.backpressure := true.B | x438.sm.io.doneLatch
      x438.forwardpressure := (true.B) && (true.B) | x438.sm.io.doneLatch
      x438.sm.io.enableOut.zip(x438.smEnableOuts).foreach{case (l,r) => r := l}
      x438.sm.io.break := false.B
      x438.mask := true.B & true.B
      x438.configure("x438", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x438.kernel()
      val x399_outr_UnitPipe_DenseTransfer = new x399_outr_UnitPipe_DenseTransfer_kernel(List(x295_out_0), List(x369), List(x292_outDRAM), List(x368), List(x367) ,  Some(me), List(), 3, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x399_outr_UnitPipe_DenseTransfer.backpressure := true.B | x399_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x399_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x399_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x399_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x399_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x399_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x399_outr_UnitPipe_DenseTransfer.mask := true.B & true.B
      x399_outr_UnitPipe_DenseTransfer.configure("x399_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x399_outr_UnitPipe_DenseTransfer.kernel()
    }
    val module = Module(new RootController_concrete(sm.p.depth)); module.io := DontCare
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
/** END AccelScope RootController **/
