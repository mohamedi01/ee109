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

/** Hierarchy: x367 -> x368 -> x375 **/
/** BEGIN None x367_outr_UnitPipe **/
class x367_outr_UnitPipe_kernel(
  list_x249_outDram: List[FixedPoint],
  list_x336: List[DecoupledIO[AppCommandDense]],
  list_x252_outSram_0: List[StandardInterface],
  list_x338: List[DecoupledIO[Bool]],
  list_x337: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x367_outr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x367_outr_UnitPipe_iiCtr"))
  
  abstract class x367_outr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x249_outDram = Input(new FixedPoint(true, 64, 0))
      val in_x252_outSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x252_outSram_0_p").asInstanceOf[MemParams] ))
      val in_x338 = Flipped(Decoupled(Bool()))
      val in_x336 = Decoupled(new AppCommandDense(ModuleParams.getParams("x336_p").asInstanceOf[(Int,Int)] ))
      val in_x337 = Decoupled(new AppStoreData(ModuleParams.getParams("x337_p").asInstanceOf[(Int,Int)] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x249_outDram = {io.in_x249_outDram} 
    def x252_outSram_0 = {io.in_x252_outSram_0} ; io.in_x252_outSram_0 := DontCare
    def x338 = {io.in_x338} 
    def x336 = {io.in_x336} 
    def x337 = {io.in_x337} 
  }
  def connectWires0(module: x367_outr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x249_outDram <> x249_outDram
    x252_outSram_0.connectLedger(module.io.in_x252_outSram_0)
    module.io.in_x338 <> x338
    module.io.in_x336 <> x336
    module.io.in_x337 <> x337
  }
  val x249_outDram = list_x249_outDram(0)
  val x336 = list_x336(0)
  val x252_outSram_0 = list_x252_outSram_0(0)
  val x338 = list_x338(0)
  val x337 = list_x337(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x367_outr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x367_outr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x367_outr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x362_outr_UnitPipe = new x362_outr_UnitPipe_kernel(List(x249_outDram), List(x336), List(x252_outSram_0), List(x337) ,  Some(me), List(), 0, 2, 1, List(1), List(32), breakpoints, rr)
      x362_outr_UnitPipe.sm.io.ctrDone := risingEdge(x362_outr_UnitPipe.sm.io.ctrInc)
      x362_outr_UnitPipe.backpressure := true.B | x362_outr_UnitPipe.sm.io.doneLatch
      x362_outr_UnitPipe.forwardpressure := (true.B) && (true.B) | x362_outr_UnitPipe.sm.io.doneLatch
      x362_outr_UnitPipe.sm.io.enableOut.zip(x362_outr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x362_outr_UnitPipe.sm.io.break := false.B
      x362_outr_UnitPipe.mask := true.B & true.B
      x362_outr_UnitPipe.configure("x362_outr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x362_outr_UnitPipe.kernel()
      val x366_inr_UnitPipe = new x366_inr_UnitPipe_kernel(List(x338) ,  Some(me), List(), 1, 1, 1, List(1), List(32), breakpoints, rr)
      x366_inr_UnitPipe.sm.io.ctrDone := risingEdge(x366_inr_UnitPipe.sm.io.ctrInc)
      x366_inr_UnitPipe.backpressure := true.B | x366_inr_UnitPipe.sm.io.doneLatch
      x366_inr_UnitPipe.forwardpressure := (x338.valid) && (true.B) | x366_inr_UnitPipe.sm.io.doneLatch
      x366_inr_UnitPipe.sm.io.enableOut.zip(x366_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x366_inr_UnitPipe.sm.io.break := false.B
      x366_inr_UnitPipe.mask := true.B & true.B
      x366_inr_UnitPipe.configure("x366_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x366_inr_UnitPipe.kernel()
    }
    val module = Module(new x367_outr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x367_outr_UnitPipe **/
