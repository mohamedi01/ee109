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

/** Hierarchy: x213 **/
/** BEGIN None RootController **/
class RootController_kernel(
  list_x310: List[DecoupledIO[AppStoreData]],
  list_x255: List[DecoupledIO[AppCommandDense]],
  list_x257: List[DecoupledIO[AppLoadData]],
  list_x311: List[DecoupledIO[Bool]],
  list_x251_outDRAM: List[FixedPoint],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 3, isFSM = false   , latency = 0.0.toInt, myName = "RootController_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "RootController_iiCtr"))
  
  abstract class RootController_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x257 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x257_p").asInstanceOf[(Int, Int)] )))
      val in_x311 = Flipped(Decoupled(Bool()))
      val in_x251_outDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x255 = Decoupled(new AppCommandDense(ModuleParams.getParams("x255_p").asInstanceOf[(Int,Int)] ))
      val in_x310 = Decoupled(new AppStoreData(ModuleParams.getParams("x310_p").asInstanceOf[(Int,Int)] ))
      val in_x250_inDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x309 = Decoupled(new AppCommandDense(ModuleParams.getParams("x309_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(3, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(3, 1))
      val rr = Input(Bool())
    })
    def x257 = {io.in_x257} 
    def x311 = {io.in_x311} 
    def x251_outDRAM = {io.in_x251_outDRAM} 
    def x255 = {io.in_x255} 
    def x310 = {io.in_x310} 
    def x250_inDRAM = {io.in_x250_inDRAM} 
    def x309 = {io.in_x309} 
  }
  def connectWires0(module: RootController_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x257 <> x257
    module.io.in_x311 <> x311
    module.io.in_x251_outDRAM <> x251_outDRAM
    module.io.in_x255 <> x255
    module.io.in_x310 <> x310
    module.io.in_x250_inDRAM <> x250_inDRAM
    module.io.in_x309 <> x309
  }
  val x310 = list_x310(0)
  val x255 = list_x255(0)
  val x309 = list_x255(1)
  val x257 = list_x257(0)
  val x311 = list_x311(0)
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
      Ledger.tieInstrCtr(instrctrs.toList, X213_instrctr, cycles_RootController.io.count, iters_RootController.io.count, 0.U, 0.U)
      val x253_inSRAM_0 = (new x253_inSRAM_0).m.io.asInstanceOf[StandardInterface]
      val x254_outSRAM_0 = (new x254_outSRAM_0).m.io.asInstanceOf[StandardInterface]
      val x289_outr_UnitPipe_DenseTransfer = new x289_outr_UnitPipe_DenseTransfer_kernel(List(x250_inDRAM), List(x255), List(x257), List(x253_inSRAM_0) ,  Some(me), List(), 0, 2, 2, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x289_outr_UnitPipe_DenseTransfer.backpressure := true.B | x289_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x289_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x289_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x289_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x289_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x289_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x289_outr_UnitPipe_DenseTransfer.mask := true.B & true.B
      x289_outr_UnitPipe_DenseTransfer.configure("x289_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x289_outr_UnitPipe_DenseTransfer.kernel()
      val x290_ctr = new CtrObject(Left(Some(0)), Left(Some(9)), Left(Some(1)), 1, 6, false)
      val x291_ctrchain = (new CChainObject(List[CtrObject](x290_ctr), "x291_ctrchain")).cchain.io 
      x291_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x291_ctrchain_p", (x291_ctrchain.par, x291_ctrchain.widths))
      val x308_inr_Foreach = new x308_inr_Foreach_kernel(List(x253_inSRAM_0,x254_outSRAM_0) ,  Some(me), List(x291_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x308_inr_Foreach.sm.io.ctrDone := (x308_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x308_inr_Foreach.backpressure := true.B | x308_inr_Foreach.sm.io.doneLatch
      x308_inr_Foreach.forwardpressure := (true.B) && (true.B) | x308_inr_Foreach.sm.io.doneLatch
      x308_inr_Foreach.sm.io.enableOut.zip(x308_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x308_inr_Foreach.sm.io.break := false.B
      x308_inr_Foreach.mask := ~x308_inr_Foreach.cchain.head.output.noop & true.B
      x308_inr_Foreach.configure("x308_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x308_inr_Foreach.kernel()
      val x341_outr_UnitPipe_DenseTransfer = new x341_outr_UnitPipe_DenseTransfer_kernel(List(x254_outSRAM_0), List(x310), List(x311), List(x309), List(x251_outDRAM) ,  Some(me), List(), 2, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x341_outr_UnitPipe_DenseTransfer.backpressure := true.B | x341_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x341_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x341_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x341_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x341_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x341_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x341_outr_UnitPipe_DenseTransfer.mask := true.B & true.B
      x341_outr_UnitPipe_DenseTransfer.configure("x341_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x341_outr_UnitPipe_DenseTransfer.kernel()
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
