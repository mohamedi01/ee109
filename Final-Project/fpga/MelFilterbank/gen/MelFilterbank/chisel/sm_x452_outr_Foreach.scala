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

/** Hierarchy: x452 -> x453 **/
/** BEGIN None x452_outr_Foreach **/
class x452_outr_Foreach_kernel(
  list_x296_outSram_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 1, isFSM = false   , latency = 0.0.toInt, myName = "x452_outr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x452_outr_Foreach_iiCtr"))
  
  abstract class x452_outr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x296_outSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x296_outSram_0_p").asInstanceOf[MemParams] ))
      val in_x295_vecSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x295_vecSram_0_p").asInstanceOf[MemParams] ))
      val in_x294_matSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x294_matSram_0_p").asInstanceOf[MemParams] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x296_outSram_0 = {io.in_x296_outSram_0} ; io.in_x296_outSram_0 := DontCare
    def x295_vecSram_0 = {io.in_x295_vecSram_0} ; io.in_x295_vecSram_0 := DontCare
    def x294_matSram_0 = {io.in_x294_matSram_0} ; io.in_x294_matSram_0 := DontCare
  }
  def connectWires0(module: x452_outr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x296_outSram_0.connectLedger(module.io.in_x296_outSram_0)
    x295_vecSram_0.connectLedger(module.io.in_x295_vecSram_0)
    x294_matSram_0.connectLedger(module.io.in_x294_matSram_0)
  }
  val x296_outSram_0 = list_x296_outSram_0(0)
  val x295_vecSram_0 = list_x296_outSram_0(1)
  val x294_matSram_0 = list_x296_outSram_0(2)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x452_outr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x452_outr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x452_outr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val b397 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b397.suggestName("b397")
      val b398 = ~io.sigsIn.cchainOutputs.head.oobs(0); b398.suggestName("b398")
      val x399 = "" 
      val x400_ctr = new CtrObject(Left(Some(0)), Left(Some(201)), Left(Some(1)), 1, 10, false)
      val x401_ctrchain = (new CChainObject(List[CtrObject](x400_ctr), "x401_ctrchain")).cchain.io 
      x401_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x401_ctrchain_p", (x401_ctrchain.par, x401_ctrchain.widths))
      val x451 = new x451_kernel(List(x399), List(b398), List(x401_ctrchain), List(b397), List(x296_outSram_0,x295_vecSram_0,x294_matSram_0) ,  Some(me), List(), 0, 2, 1, List(1), List(32), breakpoints, rr)
      x451.sm.io.ctrDone := risingEdge(x451.sm.io.ctrInc)
      x451.backpressure := true.B | x451.sm.io.doneLatch
      x451.forwardpressure := (true.B) && (true.B) | x451.sm.io.doneLatch
      x451.sm.io.enableOut.zip(x451.smEnableOuts).foreach{case (l,r) => r := l}
      x451.sm.io.break := false.B
      x451.mask := true.B & true.B
      x451.configure("x451", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x451.kernel()
    }
    val module = Module(new x452_outr_Foreach_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnrolledForeach x452_outr_Foreach **/
