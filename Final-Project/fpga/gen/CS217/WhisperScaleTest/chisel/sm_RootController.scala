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

/** Hierarchy: x201 **/
/** BEGIN None RootController **/
class RootController_kernel(
  list_x299: List[DecoupledIO[AppCommandDense]],
  list_x256: List[DecoupledIO[AppLoadData]],
  list_x301: List[DecoupledIO[Bool]],
  list_x300: List[DecoupledIO[AppStoreData]],
  list_x251_outDRAM: List[FixedPoint],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 3, isFSM = false   , latency = 0.0.toInt, myName = "RootController_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "RootController_iiCtr"))
  
  abstract class RootController_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x301 = Flipped(Decoupled(Bool()))
      val in_x256 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x256_p").asInstanceOf[(Int, Int)] )))
      val in_x251_outDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x299 = Decoupled(new AppCommandDense(ModuleParams.getParams("x299_p").asInstanceOf[(Int,Int)] ))
      val in_x250_inDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x300 = Decoupled(new AppStoreData(ModuleParams.getParams("x300_p").asInstanceOf[(Int,Int)] ))
      val in_x254 = Decoupled(new AppCommandDense(ModuleParams.getParams("x254_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(3, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(3, 1))
      val rr = Input(Bool())
    })
    def x301 = {io.in_x301} 
    def x256 = {io.in_x256} 
    def x251_outDRAM = {io.in_x251_outDRAM} 
    def x299 = {io.in_x299} 
    def x250_inDRAM = {io.in_x250_inDRAM} 
    def x300 = {io.in_x300} 
    def x254 = {io.in_x254} 
  }
  def connectWires0(module: RootController_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x301 <> x301
    module.io.in_x256 <> x256
    module.io.in_x251_outDRAM <> x251_outDRAM
    module.io.in_x299 <> x299
    module.io.in_x250_inDRAM <> x250_inDRAM
    module.io.in_x300 <> x300
    module.io.in_x254 <> x254
  }
  val x299 = list_x299(0)
  val x254 = list_x299(1)
  val x256 = list_x256(0)
  val x301 = list_x301(0)
  val x300 = list_x300(0)
  val x251_outDRAM = list_x251_outDRAM(0)
  val x250_inDRAM = list_x251_outDRAM(1)
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
      Ledger.tieInstrCtr(instrctrs.toList, X201_instrctr, cycles_RootController.io.count, iters_RootController.io.count, 0.U, 0.U)
      val x253_buf_0 = (new x253_buf_0).m.io.asInstanceOf[StandardInterface]
      val x288_outr_UnitPipe_DenseTransfer = new x288_outr_UnitPipe_DenseTransfer_kernel(List(x250_inDRAM), List(x254), List(x256), List(x253_buf_0) ,  Some(me), List(), 0, 2, 2, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x288_outr_UnitPipe_DenseTransfer.backpressure := true.B | x288_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x288_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x288_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x288_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x288_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x288_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x288_outr_UnitPipe_DenseTransfer.mask := true.B & true.B
      x288_outr_UnitPipe_DenseTransfer.configure("x288_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x288_outr_UnitPipe_DenseTransfer.kernel()
      val x289_ctr = new CtrObject(Left(Some(0)), Left(Some(4)), Left(Some(1)), 1, 5, false)
      val x290_ctrchain = (new CChainObject(List[CtrObject](x289_ctr), "x290_ctrchain")).cchain.io 
      x290_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x290_ctrchain_p", (x290_ctrchain.par, x290_ctrchain.widths))
      val x298_inr_Foreach = new x298_inr_Foreach_kernel(List(x253_buf_0) ,  Some(me), List(x290_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x298_inr_Foreach.sm.io.ctrDone := (x298_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x298_inr_Foreach.backpressure := true.B | x298_inr_Foreach.sm.io.doneLatch
      x298_inr_Foreach.forwardpressure := (true.B) && (true.B) | x298_inr_Foreach.sm.io.doneLatch
      x298_inr_Foreach.sm.io.enableOut.zip(x298_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x298_inr_Foreach.sm.io.break := false.B
      x298_inr_Foreach.mask := ~x298_inr_Foreach.cchain.head.output.noop & true.B
      x298_inr_Foreach.configure("x298_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x298_inr_Foreach.kernel()
      val x331_outr_UnitPipe_DenseTransfer = new x331_outr_UnitPipe_DenseTransfer_kernel(List(x253_buf_0), List(x299), List(x301), List(x300), List(x251_outDRAM) ,  Some(me), List(), 2, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x331_outr_UnitPipe_DenseTransfer.backpressure := true.B | x331_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x331_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x331_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x331_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x331_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x331_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x331_outr_UnitPipe_DenseTransfer.mask := true.B & true.B
      x331_outr_UnitPipe_DenseTransfer.configure("x331_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x331_outr_UnitPipe_DenseTransfer.kernel()
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
