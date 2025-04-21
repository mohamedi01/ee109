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

/** Hierarchy: x119 **/
/** BEGIN None RootController **/
class RootController_kernel(
  list_x160_out: List[FixedPoint],
  list_x187: List[DecoupledIO[AppCommandDense]],
  list_x189: List[DecoupledIO[Bool]],
  list_x188: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "RootController_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "RootController_iiCtr"))
  
  abstract class RootController_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x160_out = Input(new FixedPoint(true, 64, 0))
      val in_x187 = Decoupled(new AppCommandDense(ModuleParams.getParams("x187_p").asInstanceOf[(Int,Int)] ))
      val in_x188 = Decoupled(new AppStoreData(ModuleParams.getParams("x188_p").asInstanceOf[(Int,Int)] ))
      val in_x189 = Flipped(Decoupled(Bool()))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x160_out = {io.in_x160_out} 
    def x187 = {io.in_x187} 
    def x188 = {io.in_x188} 
    def x189 = {io.in_x189} 
  }
  def connectWires0(module: RootController_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x160_out <> x160_out
    module.io.in_x187 <> x187
    module.io.in_x188 <> x188
    module.io.in_x189 <> x189
  }
  val x160_out = list_x160_out(0)
  val x187 = list_x187(0)
  val x189 = list_x189(0)
  val x188 = list_x188(0)
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
      Ledger.tieInstrCtr(instrctrs.toList, X119_instrctr, cycles_RootController.io.count, iters_RootController.io.count, 0.U, 0.U)
      val x161_a_0 = (new x161_a_0).m.io.asInstanceOf[StandardInterface]
      val x162_ctr = new CtrObject(Left(Some(-5)), Left(Some(5)), Left(Some(1)), 1, 5, false)
      val x163_ctrchain = (new CChainObject(List[CtrObject](x162_ctr), "x163_ctrchain")).cchain.io 
      x163_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x163_ctrchain_p", (x163_ctrchain.par, x163_ctrchain.widths))
      val x164_ctr = new CtrObject(Left(Some(0)), Left(Some(16)), Left(Some(1)), 1, 7, false)
      val x165_ctrchain = (new CChainObject(List[CtrObject](x164_ctr), "x165_ctrchain")).cchain.io 
      x165_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x165_ctrchain_p", (x165_ctrchain.par, x165_ctrchain.widths))
      val x186_outr_Reduce = new x186_outr_Reduce_kernel(List(x165_ctrchain), List(x161_a_0) ,  Some(me), List(x163_ctrchain), 0, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x186_outr_Reduce.sm.io.ctrDone := (x186_outr_Reduce.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x186_outr_Reduce.backpressure := true.B | x186_outr_Reduce.sm.io.doneLatch
      x186_outr_Reduce.forwardpressure := (true.B) && (true.B) | x186_outr_Reduce.sm.io.doneLatch
      x186_outr_Reduce.sm.io.enableOut.zip(x186_outr_Reduce.smEnableOuts).foreach{case (l,r) => r := l}
      x186_outr_Reduce.sm.io.break := false.B
      x186_outr_Reduce.mask := ~x186_outr_Reduce.cchain.head.output.noop & true.B
      x186_outr_Reduce.configure("x186_outr_Reduce", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x186_outr_Reduce.kernel()
      val x208_outr_UnitPipe_DenseTransfer = new x208_outr_UnitPipe_DenseTransfer_kernel(List(x161_a_0), List(x160_out), List(x188), List(x189), List(x187) ,  Some(me), List(), 1, 3, 3, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x208_outr_UnitPipe_DenseTransfer.backpressure := true.B | x208_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x208_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x208_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x208_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x208_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x208_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x208_outr_UnitPipe_DenseTransfer.mask := true.B & true.B
      x208_outr_UnitPipe_DenseTransfer.configure("x208_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x208_outr_UnitPipe_DenseTransfer.kernel()
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
