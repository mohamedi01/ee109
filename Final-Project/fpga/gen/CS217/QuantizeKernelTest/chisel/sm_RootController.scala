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

/** Hierarchy: x244 **/
/** BEGIN None RootController **/
class RootController_kernel(
  list_x278_outDRAM: List[FixedPoint],
  list_x353: List[DecoupledIO[Bool]],
  list_x284: List[DecoupledIO[AppLoadData]],
  list_x282: List[DecoupledIO[AppCommandDense]],
  list_x352: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 3, isFSM = false   , latency = 0.0.toInt, myName = "RootController_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "RootController_iiCtr"))
  
  abstract class RootController_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x352 = Decoupled(new AppStoreData(ModuleParams.getParams("x352_p").asInstanceOf[(Int,Int)] ))
      val in_x284 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x284_p").asInstanceOf[(Int, Int)] )))
      val in_x353 = Flipped(Decoupled(Bool()))
      val in_x278_outDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x282 = Decoupled(new AppCommandDense(ModuleParams.getParams("x282_p").asInstanceOf[(Int,Int)] ))
      val in_x351 = Decoupled(new AppCommandDense(ModuleParams.getParams("x351_p").asInstanceOf[(Int,Int)] ))
      val in_x277_inDRAM = Input(new FixedPoint(true, 64, 0))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(3, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(3, 1))
      val rr = Input(Bool())
    })
    def x352 = {io.in_x352} 
    def x284 = {io.in_x284} 
    def x353 = {io.in_x353} 
    def x278_outDRAM = {io.in_x278_outDRAM} 
    def x282 = {io.in_x282} 
    def x351 = {io.in_x351} 
    def x277_inDRAM = {io.in_x277_inDRAM} 
  }
  def connectWires0(module: RootController_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x352 <> x352
    module.io.in_x284 <> x284
    module.io.in_x353 <> x353
    module.io.in_x278_outDRAM <> x278_outDRAM
    module.io.in_x282 <> x282
    module.io.in_x351 <> x351
    module.io.in_x277_inDRAM <> x277_inDRAM
  }
  val x278_outDRAM = list_x278_outDRAM(0)
  val x277_inDRAM = list_x278_outDRAM(1)
  val x353 = list_x353(0)
  val x284 = list_x284(0)
  val x282 = list_x282(0)
  val x351 = list_x282(1)
  val x352 = list_x352(0)
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
      Ledger.tieInstrCtr(instrctrs.toList, X244_instrctr, cycles_RootController.io.count, iters_RootController.io.count, 0.U, 0.U)
      val x280_inSRAM_0 = (new x280_inSRAM_0).m.io.asInstanceOf[StandardInterface]
      val x281_outSRAM_0 = (new x281_outSRAM_0).m.io.asInstanceOf[StandardInterface]
      val x316_outr_UnitPipe_DenseTransfer = new x316_outr_UnitPipe_DenseTransfer_kernel(List(x277_inDRAM), List(x282), List(x284), List(x280_inSRAM_0) ,  Some(me), List(), 0, 2, 2, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x316_outr_UnitPipe_DenseTransfer.backpressure := true.B | x316_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x316_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x316_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x316_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x316_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x316_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x316_outr_UnitPipe_DenseTransfer.mask := true.B & true.B
      x316_outr_UnitPipe_DenseTransfer.configure("x316_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x316_outr_UnitPipe_DenseTransfer.kernel()
      val x317_ctr = new CtrObject(Left(Some(0)), Left(Some(9)), Left(Some(1)), 1, 6, false)
      val x318_ctrchain = (new CChainObject(List[CtrObject](x317_ctr), "x318_ctrchain")).cchain.io 
      x318_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x318_ctrchain_p", (x318_ctrchain.par, x318_ctrchain.widths))
      val x350_inr_Foreach = new x350_inr_Foreach_kernel(List(x280_inSRAM_0,x281_outSRAM_0) ,  Some(me), List(x318_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x350_inr_Foreach.sm.io.ctrDone := (x350_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x350_inr_Foreach.backpressure := true.B | x350_inr_Foreach.sm.io.doneLatch
      x350_inr_Foreach.forwardpressure := (true.B) && (true.B) | x350_inr_Foreach.sm.io.doneLatch
      x350_inr_Foreach.sm.io.enableOut.zip(x350_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x350_inr_Foreach.sm.io.break := false.B
      x350_inr_Foreach.mask := ~x350_inr_Foreach.cchain.head.output.noop & true.B
      x350_inr_Foreach.configure("x350_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x350_inr_Foreach.kernel()
