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

/** Hierarchy: x341 -> x213 **/
/** BEGIN Some(DenseTransfer) x341_outr_UnitPipe_DenseTransfer **/
class x341_outr_UnitPipe_DenseTransfer_kernel(
  list_x254_outSRAM_0: List[StandardInterface],
  list_x310: List[DecoupledIO[AppStoreData]],
  list_x311: List[DecoupledIO[Bool]],
  list_x309: List[DecoupledIO[AppCommandDense]],
  list_x251_outDRAM: List[FixedPoint],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 1, isFSM = false   , latency = 0.0.toInt, myName = "x341_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x341_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x341_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x311 = Flipped(Decoupled(Bool()))
      val in_x251_outDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x310 = Decoupled(new AppStoreData(ModuleParams.getParams("x310_p").asInstanceOf[(Int,Int)] ))
      val in_x254_outSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x254_outSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x309 = Decoupled(new AppCommandDense(ModuleParams.getParams("x309_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x311 = {io.in_x311} 
    def x251_outDRAM = {io.in_x251_outDRAM} 
    def x310 = {io.in_x310} 
    def x254_outSRAM_0 = {io.in_x254_outSRAM_0} ; io.in_x254_outSRAM_0 := DontCare
    def x309 = {io.in_x309} 
  }
  def connectWires0(module: x341_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x311 <> x311
    module.io.in_x251_outDRAM <> x251_outDRAM
    module.io.in_x310 <> x310
    x254_outSRAM_0.connectLedger(module.io.in_x254_outSRAM_0)
    module.io.in_x309 <> x309
  }
  val x254_outSRAM_0 = list_x254_outSRAM_0(0)
  val x310 = list_x310(0)
  val x311 = list_x311(0)
  val x309 = list_x309(0)
  val x251_outDRAM = list_x251_outDRAM(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x341_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x341_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x341_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x341_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      val iters_x341_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      cycles_x341_outr_UnitPipe_DenseTransfer.io.enable := io.sigsIn.baseEn
      iters_x341_outr_UnitPipe_DenseTransfer.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X341_instrctr, cycles_x341_outr_UnitPipe_DenseTransfer.io.count, iters_x341_outr_UnitPipe_DenseTransfer.io.count, 0.U, 0.U)
      val x340_outr_UnitPipe = new x340_outr_UnitPipe_kernel(List(x254_outSRAM_0), List(x310), List(x311), List(x309), List(x251_outDRAM) ,  Some(me), List(), 0, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x340_outr_UnitPipe.sm.io.ctrDone := risingEdge(x340_outr_UnitPipe.sm.io.ctrInc)
      x340_outr_UnitPipe.backpressure := true.B | x340_outr_UnitPipe.sm.io.doneLatch
      x340_outr_UnitPipe.forwardpressure := (true.B) && (true.B) | x340_outr_UnitPipe.sm.io.doneLatch
      x340_outr_UnitPipe.sm.io.enableOut.zip(x340_outr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x340_outr_UnitPipe.sm.io.break := false.B
      x340_outr_UnitPipe.mask := true.B & true.B
      x340_outr_UnitPipe.configure("x340_outr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x340_outr_UnitPipe.kernel()
    }
    val module = Module(new x341_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x341_outr_UnitPipe_DenseTransfer **/
