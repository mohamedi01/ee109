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

/** Hierarchy: x480 -> x481 **/
/** BEGIN None x480 **/
class x480_kernel(
  list_x321_realDRAM: List[FixedPoint],
  list_x329: List[DecoupledIO[AppCommandDense]],
  list_x366: List[DecoupledIO[AppLoadData]],
  list_x327_imagSRAM_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(ForkJoin, 2, isFSM = false   , latency = 0.0.toInt, myName = "x480_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x480_iiCtr"))
  
  abstract class x480_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x321_realDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x366 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x366_p").asInstanceOf[(Int, Int)] )))
      val in_x329 = Decoupled(new AppCommandDense(ModuleParams.getParams("x329_p").asInstanceOf[(Int,Int)] ))
      val in_x327_imagSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x327_imagSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x331 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x331_p").asInstanceOf[(Int, Int)] )))
      val in_x326_realSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x326_realSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x322_imagDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x364 = Decoupled(new AppCommandDense(ModuleParams.getParams("x364_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x321_realDRAM = {io.in_x321_realDRAM} 
    def x366 = {io.in_x366} 
    def x329 = {io.in_x329} 
    def x327_imagSRAM_0 = {io.in_x327_imagSRAM_0} ; io.in_x327_imagSRAM_0 := DontCare
    def x331 = {io.in_x331} 
    def x326_realSRAM_0 = {io.in_x326_realSRAM_0} ; io.in_x326_realSRAM_0 := DontCare
    def x322_imagDRAM = {io.in_x322_imagDRAM} 
    def x364 = {io.in_x364} 
  }
  def connectWires0(module: x480_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x321_realDRAM <> x321_realDRAM
    module.io.in_x366 <> x366
    module.io.in_x329 <> x329
    x327_imagSRAM_0.connectLedger(module.io.in_x327_imagSRAM_0)
    module.io.in_x331 <> x331
    x326_realSRAM_0.connectLedger(module.io.in_x326_realSRAM_0)
    module.io.in_x322_imagDRAM <> x322_imagDRAM
    module.io.in_x364 <> x364
  }
  val x321_realDRAM = list_x321_realDRAM(0)
  val x322_imagDRAM = list_x321_realDRAM(1)
  val x329 = list_x329(0)
  val x364 = list_x329(1)
  val x366 = list_x366(0)
  val x331 = list_x366(1)
  val x327_imagSRAM_0 = list_x327_imagSRAM_0(0)
  val x326_realSRAM_0 = list_x327_imagSRAM_0(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x480")
    implicit val stack = ControllerStack.stack.toList
    class x480_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x480_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x480 = Module(new InstrumentationCounter())
      val iters_x480 = Module(new InstrumentationCounter())
      cycles_x480.io.enable := io.sigsIn.baseEn
      iters_x480.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X480_instrctr, cycles_x480.io.count, iters_x480.io.count, 0.U, 0.U)
      val x363_outr_UnitPipe_DenseTransfer = new x363_outr_UnitPipe_DenseTransfer_kernel(List(x321_realDRAM), List(x329), List(x331), List(x326_realSRAM_0) ,  Some(me), List(), 0, 2, 2, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x363_outr_UnitPipe_DenseTransfer.backpressure := true.B | x363_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x363_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x363_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x363_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x363_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x363_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x363_outr_UnitPipe_DenseTransfer.mask := true.B & true.B
      x363_outr_UnitPipe_DenseTransfer.configure("x363_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x363_outr_UnitPipe_DenseTransfer.kernel()
      val x398_outr_UnitPipe_DenseTransfer = new x398_outr_UnitPipe_DenseTransfer_kernel(List(x322_imagDRAM), List(x364), List(x366), List(x327_imagSRAM_0) ,  Some(me), List(), 1, 2, 2, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x398_outr_UnitPipe_DenseTransfer.backpressure := true.B | x398_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x398_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x398_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x398_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x398_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x398_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x398_outr_UnitPipe_DenseTransfer.mask := true.B & true.B
      x398_outr_UnitPipe_DenseTransfer.configure("x398_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x398_outr_UnitPipe_DenseTransfer.kernel()
    }
    val module = Module(new x480_concrete(sm.p.depth)); module.io := DontCare
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
/** END ParallelPipe x480 **/
