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

/** Hierarchy: x451 -> x452 -> x453 **/
/** BEGIN None x451 **/
class x451_kernel(
  list_x399: List[String],
  list_b398: List[Bool],
  list_x401_ctrchain: List[CounterChainInterface],
  list_b397: List[FixedPoint],
  list_x296_outSram_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(ForkJoin, 2, isFSM = false   , latency = 0.0.toInt, myName = "x451_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x451_iiCtr"))
  
  abstract class x451_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b397 = Input(new FixedPoint(true, 32, 0))
      val in_b398 = Input(Bool())
      val in_x296_outSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x296_outSram_0_p").asInstanceOf[MemParams] ))
      val in_x399 = String
      val in_x295_vecSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x295_vecSram_0_p").asInstanceOf[MemParams] ))
      val in_x401_ctrchain = Flipped(new CounterChainInterface(ModuleParams.getParams("x401_ctrchain_p").asInstanceOf[(List[Int],List[Int])] ))
      val in_x294_matSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x294_matSram_0_p").asInstanceOf[MemParams] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def b397 = {io.in_b397} 
    def b398 = {io.in_b398} 
    def x296_outSram_0 = {io.in_x296_outSram_0} ; io.in_x296_outSram_0 := DontCare
    def x399 = {io.in_x399} 
    def x295_vecSram_0 = {io.in_x295_vecSram_0} ; io.in_x295_vecSram_0 := DontCare
    def x401_ctrchain = {io.in_x401_ctrchain} ; io.in_x401_ctrchain := DontCare
    def x294_matSram_0 = {io.in_x294_matSram_0} ; io.in_x294_matSram_0 := DontCare
  }
  def connectWires0(module: x451_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b397 <> b397
    module.io.in_b398 <> b398
    x296_outSram_0.connectLedger(module.io.in_x296_outSram_0)
    module.io.in_x399 <> x399
    x295_vecSram_0.connectLedger(module.io.in_x295_vecSram_0)
    module.io.in_x401_ctrchain.input <> x401_ctrchain.input; module.io.in_x401_ctrchain.output <> x401_ctrchain.output
    x294_matSram_0.connectLedger(module.io.in_x294_matSram_0)
  }
  val x399 = list_x399(0)
  val b398 = list_b398(0)
  val x401_ctrchain = list_x401_ctrchain(0)
  val b397 = list_b397(0)
  val x296_outSram_0 = list_x296_outSram_0(0)
  val x295_vecSram_0 = list_x296_outSram_0(1)
  val x294_matSram_0 = list_x296_outSram_0(2)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x451")
    implicit val stack = ControllerStack.stack.toList
    class x451_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x451_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x415_inr_Foreach = new x415_inr_Foreach_kernel(List(b398), List(b397), List(x399), List(x295_vecSram_0,x294_matSram_0) ,  Some(me), List(x401_ctrchain), 0, 1, 1, List(1), List(32), breakpoints, rr)
      x415_inr_Foreach.sm.io.ctrDone := (x415_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x415_inr_Foreach.backpressure := true.B | x415_inr_Foreach.sm.io.doneLatch
      x415_inr_Foreach.forwardpressure := (true.B) && (true.B) | x415_inr_Foreach.sm.io.doneLatch
      x415_inr_Foreach.sm.io.enableOut.zip(x415_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x415_inr_Foreach.sm.io.break := false.B
      x415_inr_Foreach.mask := ~x415_inr_Foreach.cchain.head.output.noop & b398
      x415_inr_Foreach.configure("x415_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x415_inr_Foreach.kernel()
      val x418_inr_UnitPipe = new x418_inr_UnitPipe_kernel(List(b398), List(b397), List(x399), List(x296_outSram_0) ,  Some(me), List(), 1, 1, 1, List(1), List(32), breakpoints, rr)
      x418_inr_UnitPipe.sm.io.ctrDone := risingEdge(x418_inr_UnitPipe.sm.io.ctrInc)
      x418_inr_UnitPipe.backpressure := true.B | x418_inr_UnitPipe.sm.io.doneLatch
      x418_inr_UnitPipe.forwardpressure := (true.B) && (true.B) | x418_inr_UnitPipe.sm.io.doneLatch
      x418_inr_UnitPipe.sm.io.enableOut.zip(x418_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x418_inr_UnitPipe.sm.io.break := false.B
      x418_inr_UnitPipe.mask := true.B & b398
      x418_inr_UnitPipe.configure("x418_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x418_inr_UnitPipe.kernel()
    }
    val module = Module(new x451_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END ParallelPipe x451 **/
