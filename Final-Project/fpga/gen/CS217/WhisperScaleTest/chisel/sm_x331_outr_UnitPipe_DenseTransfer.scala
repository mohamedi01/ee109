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

/** Hierarchy: x331 -> x201 **/
/** BEGIN Some(DenseTransfer) x331_outr_UnitPipe_DenseTransfer **/
class x331_outr_UnitPipe_DenseTransfer_kernel(
  list_x253_buf_0: List[StandardInterface],
  list_x299: List[DecoupledIO[AppCommandDense]],
  list_x301: List[DecoupledIO[Bool]],
  list_x300: List[DecoupledIO[AppStoreData]],
  list_x251_outDRAM: List[FixedPoint],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 1, isFSM = false   , latency = 0.0.toInt, myName = "x331_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x331_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x331_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x301 = Flipped(Decoupled(Bool()))
      val in_x253_buf_0 = Flipped(new StandardInterface(ModuleParams.getParams("x253_buf_0_p").asInstanceOf[MemParams] ))
      val in_x251_outDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x299 = Decoupled(new AppCommandDense(ModuleParams.getParams("x299_p").asInstanceOf[(Int,Int)] ))
      val in_x300 = Decoupled(new AppStoreData(ModuleParams.getParams("x300_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x301 = {io.in_x301} 
    def x253_buf_0 = {io.in_x253_buf_0} ; io.in_x253_buf_0 := DontCare
    def x251_outDRAM = {io.in_x251_outDRAM} 
    def x299 = {io.in_x299} 
    def x300 = {io.in_x300} 
  }
  def connectWires0(module: x331_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x301 <> x301
    x253_buf_0.connectLedger(module.io.in_x253_buf_0)
    module.io.in_x251_outDRAM <> x251_outDRAM
    module.io.in_x299 <> x299
    module.io.in_x300 <> x300
  }
  val x253_buf_0 = list_x253_buf_0(0)
  val x299 = list_x299(0)
  val x301 = list_x301(0)
  val x300 = list_x300(0)
  val x251_outDRAM = list_x251_outDRAM(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x331_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x331_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x331_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x331_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      val iters_x331_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      cycles_x331_outr_UnitPipe_DenseTransfer.io.enable := io.sigsIn.baseEn
      iters_x331_outr_UnitPipe_DenseTransfer.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X331_instrctr, cycles_x331_outr_UnitPipe_DenseTransfer.io.count, iters_x331_outr_UnitPipe_DenseTransfer.io.count, 0.U, 0.U)
      val x330_outr_UnitPipe = new x330_outr_UnitPipe_kernel(List(x253_buf_0), List(x299), List(x301), List(x300), List(x251_outDRAM) ,  Some(me), List(), 0, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x330_outr_UnitPipe.sm.io.ctrDone := risingEdge(x330_outr_UnitPipe.sm.io.ctrInc)
      x330_outr_UnitPipe.backpressure := true.B | x330_outr_UnitPipe.sm.io.doneLatch
      x330_outr_UnitPipe.forwardpressure := (true.B) && (true.B) | x330_outr_UnitPipe.sm.io.doneLatch
      x330_outr_UnitPipe.sm.io.enableOut.zip(x330_outr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x330_outr_UnitPipe.sm.io.break := false.B
      x330_outr_UnitPipe.mask := true.B & true.B
      x330_outr_UnitPipe.configure("x330_outr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x330_outr_UnitPipe.kernel()
    }
    val module = Module(new x331_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x331_outr_UnitPipe_DenseTransfer **/
