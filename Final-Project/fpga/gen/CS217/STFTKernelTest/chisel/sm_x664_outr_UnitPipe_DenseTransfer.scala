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

/** Hierarchy: x664 -> x709 -> x710 **/
/** BEGIN Some(DenseTransfer) x664_outr_UnitPipe_DenseTransfer **/
class x664_outr_UnitPipe_DenseTransfer_kernel(
  list_x470_imag2D_0: List[StandardInterface],
  list_x609: List[DecoupledIO[AppCommandDense]],
  list_x611: List[DecoupledIO[Bool]],
  list_x462_imagDRAM: List[FixedPoint],
  list_x610: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 1, isFSM = false   , latency = 0.0.toInt, myName = "x664_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x664_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x664_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x610 = Decoupled(new AppStoreData(ModuleParams.getParams("x610_p").asInstanceOf[(Int,Int)] ))
      val in_x611 = Flipped(Decoupled(Bool()))
      val in_x609 = Decoupled(new AppCommandDense(ModuleParams.getParams("x609_p").asInstanceOf[(Int,Int)] ))
      val in_x462_imagDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x470_imag2D_0 = Flipped(new StandardInterface(ModuleParams.getParams("x470_imag2D_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x610 = {io.in_x610} 
    def x611 = {io.in_x611} 
    def x609 = {io.in_x609} 
    def x462_imagDRAM = {io.in_x462_imagDRAM} 
    def x470_imag2D_0 = {io.in_x470_imag2D_0} ; io.in_x470_imag2D_0 := DontCare
  }
  def connectWires0(module: x664_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x610 <> x610
    module.io.in_x611 <> x611
    module.io.in_x609 <> x609
    module.io.in_x462_imagDRAM <> x462_imagDRAM
    x470_imag2D_0.connectLedger(module.io.in_x470_imag2D_0)
  }
  val x470_imag2D_0 = list_x470_imag2D_0(0)
  val x609 = list_x609(0)
  val x611 = list_x611(0)
  val x462_imagDRAM = list_x462_imagDRAM(0)
  val x610 = list_x610(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x664_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x664_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x664_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x664_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      val iters_x664_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      cycles_x664_outr_UnitPipe_DenseTransfer.io.enable := io.sigsIn.baseEn
      iters_x664_outr_UnitPipe_DenseTransfer.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X664_instrctr, cycles_x664_outr_UnitPipe_DenseTransfer.io.count, iters_x664_outr_UnitPipe_DenseTransfer.io.count, 0.U, 0.U)
      val x612_ctr = new CtrObject(Left(Some(0)), Left(Some(3)), Left(Some(1)), 1, 4, false)
      val x613_ctrchain = (new CChainObject(List[CtrObject](x612_ctr), "x613_ctrchain")).cchain.io 
      x613_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x613_ctrchain_p", (x613_ctrchain.par, x613_ctrchain.widths))
      val x663_outr_Foreach = new x663_outr_Foreach_kernel(List(x470_imag2D_0), List(x609), List(x611), List(x462_imagDRAM), List(x610) ,  Some(me), List(x613_ctrchain), 0, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x663_outr_Foreach.sm.io.ctrDone := (x663_outr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x663_outr_Foreach.backpressure := true.B | x663_outr_Foreach.sm.io.doneLatch
      x663_outr_Foreach.forwardpressure := (true.B) && (true.B) | x663_outr_Foreach.sm.io.doneLatch
      x663_outr_Foreach.sm.io.enableOut.zip(x663_outr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x663_outr_Foreach.sm.io.break := false.B
      x663_outr_Foreach.mask := ~x663_outr_Foreach.cchain.head.output.noop & true.B
      x663_outr_Foreach.configure("x663_outr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x663_outr_Foreach.kernel()
    }
    val module = Module(new x664_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x664_outr_UnitPipe_DenseTransfer **/
