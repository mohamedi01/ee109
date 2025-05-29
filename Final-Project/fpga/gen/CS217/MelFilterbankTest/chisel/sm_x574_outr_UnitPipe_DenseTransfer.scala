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

/** Hierarchy: x574 -> x614 **/
/** BEGIN Some(DenseTransfer) x574_outr_UnitPipe_DenseTransfer **/
class x574_outr_UnitPipe_DenseTransfer_kernel(
  list_x408_outDRAM: List[FixedPoint],
  list_x544: List[DecoupledIO[Bool]],
  list_x542: List[DecoupledIO[AppCommandDense]],
  list_x543: List[DecoupledIO[AppStoreData]],
  list_x413_outSRAM_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 1, isFSM = false   , latency = 0.0.toInt, myName = "x574_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x574_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x574_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x408_outDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x542 = Decoupled(new AppCommandDense(ModuleParams.getParams("x542_p").asInstanceOf[(Int,Int)] ))
      val in_x413_outSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x413_outSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x544 = Flipped(Decoupled(Bool()))
      val in_x543 = Decoupled(new AppStoreData(ModuleParams.getParams("x543_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x408_outDRAM = {io.in_x408_outDRAM} 
    def x542 = {io.in_x542} 
    def x413_outSRAM_0 = {io.in_x413_outSRAM_0} ; io.in_x413_outSRAM_0 := DontCare
    def x544 = {io.in_x544} 
    def x543 = {io.in_x543} 
  }
  def connectWires0(module: x574_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x408_outDRAM <> x408_outDRAM
    module.io.in_x542 <> x542
    x413_outSRAM_0.connectLedger(module.io.in_x413_outSRAM_0)
    module.io.in_x544 <> x544
    module.io.in_x543 <> x543
  }
  val x408_outDRAM = list_x408_outDRAM(0)
  val x544 = list_x544(0)
  val x542 = list_x542(0)
  val x543 = list_x543(0)
  val x413_outSRAM_0 = list_x413_outSRAM_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x574_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x574_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x574_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x574_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      val iters_x574_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      cycles_x574_outr_UnitPipe_DenseTransfer.io.enable := io.sigsIn.baseEn
      iters_x574_outr_UnitPipe_DenseTransfer.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X574_instrctr, cycles_x574_outr_UnitPipe_DenseTransfer.io.count, iters_x574_outr_UnitPipe_DenseTransfer.io.count, 0.U, 0.U)
      val x573_outr_UnitPipe = new x573_outr_UnitPipe_kernel(List(x408_outDRAM), List(x544), List(x542), List(x543), List(x413_outSRAM_0) ,  Some(me), List(), 0, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x573_outr_UnitPipe.sm.io.ctrDone := risingEdge(x573_outr_UnitPipe.sm.io.ctrInc)
      x573_outr_UnitPipe.backpressure := true.B | x573_outr_UnitPipe.sm.io.doneLatch
      x573_outr_UnitPipe.forwardpressure := (true.B) && (true.B) | x573_outr_UnitPipe.sm.io.doneLatch
      x573_outr_UnitPipe.sm.io.enableOut.zip(x573_outr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x573_outr_UnitPipe.sm.io.break := false.B
      x573_outr_UnitPipe.mask := true.B & true.B
      x573_outr_UnitPipe.configure("x573_outr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x573_outr_UnitPipe.kernel()
    }
    val module = Module(new x574_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x574_outr_UnitPipe_DenseTransfer **/
