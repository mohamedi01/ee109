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

/** Hierarchy: x709 -> x710 **/
/** BEGIN None x709 **/
class x709_kernel(
  list_x610: List[DecoupledIO[AppStoreData]],
  list_x461_realDRAM: List[FixedPoint],
  list_x470_imag2D_0: List[StandardInterface],
  list_x609: List[DecoupledIO[AppCommandDense]],
  list_x555: List[DecoupledIO[Bool]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(ForkJoin, 2, isFSM = false   , latency = 0.0.toInt, myName = "x709_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x709_iiCtr"))
  
  abstract class x709_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x555 = Flipped(Decoupled(Bool()))
      val in_x461_realDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x610 = Decoupled(new AppStoreData(ModuleParams.getParams("x610_p").asInstanceOf[(Int,Int)] ))
      val in_x554 = Decoupled(new AppStoreData(ModuleParams.getParams("x554_p").asInstanceOf[(Int,Int)] ))
      val in_x611 = Flipped(Decoupled(Bool()))
      val in_x609 = Decoupled(new AppCommandDense(ModuleParams.getParams("x609_p").asInstanceOf[(Int,Int)] ))
      val in_x462_imagDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x553 = Decoupled(new AppCommandDense(ModuleParams.getParams("x553_p").asInstanceOf[(Int,Int)] ))
      val in_x470_imag2D_0 = Flipped(new StandardInterface(ModuleParams.getParams("x470_imag2D_0_p").asInstanceOf[MemParams] ))
      val in_x469_real2D_0 = Flipped(new StandardInterface(ModuleParams.getParams("x469_real2D_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x555 = {io.in_x555} 
    def x461_realDRAM = {io.in_x461_realDRAM} 
    def x610 = {io.in_x610} 
    def x554 = {io.in_x554} 
    def x611 = {io.in_x611} 
    def x609 = {io.in_x609} 
    def x462_imagDRAM = {io.in_x462_imagDRAM} 
    def x553 = {io.in_x553} 
    def x470_imag2D_0 = {io.in_x470_imag2D_0} ; io.in_x470_imag2D_0 := DontCare
    def x469_real2D_0 = {io.in_x469_real2D_0} ; io.in_x469_real2D_0 := DontCare
  }
  def connectWires0(module: x709_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x555 <> x555
    module.io.in_x461_realDRAM <> x461_realDRAM
    module.io.in_x610 <> x610
    module.io.in_x554 <> x554
    module.io.in_x611 <> x611
    module.io.in_x609 <> x609
    module.io.in_x462_imagDRAM <> x462_imagDRAM
    module.io.in_x553 <> x553
    x470_imag2D_0.connectLedger(module.io.in_x470_imag2D_0)
    x469_real2D_0.connectLedger(module.io.in_x469_real2D_0)
  }
  val x610 = list_x610(0)
  val x554 = list_x610(1)
  val x461_realDRAM = list_x461_realDRAM(0)
  val x462_imagDRAM = list_x461_realDRAM(1)
  val x470_imag2D_0 = list_x470_imag2D_0(0)
  val x469_real2D_0 = list_x470_imag2D_0(1)
  val x609 = list_x609(0)
  val x553 = list_x609(1)
  val x555 = list_x555(0)
  val x611 = list_x555(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x709")
    implicit val stack = ControllerStack.stack.toList
    class x709_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x709_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x709 = Module(new InstrumentationCounter())
      val iters_x709 = Module(new InstrumentationCounter())
      cycles_x709.io.enable := io.sigsIn.baseEn
      iters_x709.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X709_instrctr, cycles_x709.io.count, iters_x709.io.count, 0.U, 0.U)
      val x608_outr_UnitPipe_DenseTransfer = new x608_outr_UnitPipe_DenseTransfer_kernel(List(x554), List(x461_realDRAM), List(x555), List(x469_real2D_0), List(x553) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x608_outr_UnitPipe_DenseTransfer.backpressure := true.B | x608_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x608_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x608_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x608_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x608_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x608_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x608_outr_UnitPipe_DenseTransfer.mask := true.B & true.B
      x608_outr_UnitPipe_DenseTransfer.configure("x608_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x608_outr_UnitPipe_DenseTransfer.kernel()
      val x664_outr_UnitPipe_DenseTransfer = new x664_outr_UnitPipe_DenseTransfer_kernel(List(x470_imag2D_0), List(x609), List(x611), List(x462_imagDRAM), List(x610) ,  Some(me), List(), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x664_outr_UnitPipe_DenseTransfer.backpressure := true.B | x664_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x664_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x664_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x664_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x664_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x664_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x664_outr_UnitPipe_DenseTransfer.mask := true.B & true.B
      x664_outr_UnitPipe_DenseTransfer.configure("x664_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x664_outr_UnitPipe_DenseTransfer.kernel()
    }
    val module = Module(new x709_concrete(sm.p.depth)); module.io := DontCare
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
/** END ParallelPipe x709 **/
