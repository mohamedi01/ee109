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

/** Hierarchy: x394 -> x450 -> x453 **/
/** BEGIN Some(DenseTransfer) x394_outr_UnitPipe_DenseTransfer **/
class x394_outr_UnitPipe_DenseTransfer_kernel(
  list_x292_vecDram: List[FixedPoint],
  list_x360: List[DecoupledIO[AppCommandDense]],
  list_x362: List[DecoupledIO[AppLoadData]],
  list_x295_vecSram_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 2, isFSM = false   , latency = 0.0.toInt, myName = "x394_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x394_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x394_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x292_vecDram = Input(new FixedPoint(true, 64, 0))
      val in_x295_vecSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x295_vecSram_0_p").asInstanceOf[MemParams] ))
      val in_x360 = Decoupled(new AppCommandDense(ModuleParams.getParams("x360_p").asInstanceOf[(Int,Int)] ))
      val in_x362 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x362_p").asInstanceOf[(Int, Int)] )))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 2, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 2))
      val rr = Input(Bool())
    })
    def x292_vecDram = {io.in_x292_vecDram} 
    def x295_vecSram_0 = {io.in_x295_vecSram_0} ; io.in_x295_vecSram_0 := DontCare
    def x360 = {io.in_x360} 
    def x362 = {io.in_x362} 
  }
  def connectWires0(module: x394_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x292_vecDram <> x292_vecDram
    x295_vecSram_0.connectLedger(module.io.in_x295_vecSram_0)
    module.io.in_x360 <> x360
    module.io.in_x362 <> x362
  }
  val x292_vecDram = list_x292_vecDram(0)
  val x360 = list_x360(0)
  val x362 = list_x362(0)
  val x295_vecSram_0 = list_x295_vecSram_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x394_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x394_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x394_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x361_fifo = (new x361_fifo).m.io.asInstanceOf[FIFOInterface]
      val x369_inr_UnitPipe = new x369_inr_UnitPipe_kernel(List(x292_vecDram), List(x361_fifo), List(x360) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, rr)
      x369_inr_UnitPipe.sm.io.ctrDone := risingEdge(x369_inr_UnitPipe.sm.io.ctrInc)
      x369_inr_UnitPipe.backpressure := x360.ready & (~x361_fifo.full.D(1.0-1) | ~(x361_fifo.active(0).out)) | x369_inr_UnitPipe.sm.io.doneLatch
      x369_inr_UnitPipe.forwardpressure := (true.B) && (true.B) | x369_inr_UnitPipe.sm.io.doneLatch
      x369_inr_UnitPipe.sm.io.enableOut.zip(x369_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x369_inr_UnitPipe.sm.io.break := false.B
      x369_inr_UnitPipe.mask := true.B & true.B
      x369_inr_UnitPipe.configure("x369_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x369_inr_UnitPipe.kernel()
      val x393_outr_UnitPipe = new x393_outr_UnitPipe_kernel(List(x361_fifo), List(x362), List(x295_vecSram_0) ,  Some(me), List(), 1, 2, 1, List(1), List(32), breakpoints, rr)
      x393_outr_UnitPipe.sm.io.ctrDone := risingEdge(x393_outr_UnitPipe.sm.io.ctrInc)
      x393_outr_UnitPipe.backpressure := true.B | x393_outr_UnitPipe.sm.io.doneLatch
      x393_outr_UnitPipe.forwardpressure := (true.B) && (true.B) | x393_outr_UnitPipe.sm.io.doneLatch
      x393_outr_UnitPipe.sm.io.enableOut.zip(x393_outr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x393_outr_UnitPipe.sm.io.break := false.B
      x393_outr_UnitPipe.mask := true.B & true.B
      x393_outr_UnitPipe.configure("x393_outr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x393_outr_UnitPipe.kernel()
    }
    val module = Module(new x394_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x394_outr_UnitPipe_DenseTransfer **/
