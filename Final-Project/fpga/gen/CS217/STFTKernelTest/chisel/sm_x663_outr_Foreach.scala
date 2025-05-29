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

/** Hierarchy: x663 -> x664 -> x709 -> x710 **/
/** BEGIN None x663_outr_Foreach **/
class x663_outr_Foreach_kernel(
  list_x470_imag2D_0: List[StandardInterface],
  list_x609: List[DecoupledIO[AppCommandDense]],
  list_x611: List[DecoupledIO[Bool]],
  list_x462_imagDRAM: List[FixedPoint],
  list_x610: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x663_outr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x663_outr_Foreach_iiCtr"))
  
  abstract class x663_outr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x610 = Decoupled(new AppStoreData(ModuleParams.getParams("x610_p").asInstanceOf[(Int,Int)] ))
      val in_x611 = Flipped(Decoupled(Bool()))
      val in_x609 = Decoupled(new AppCommandDense(ModuleParams.getParams("x609_p").asInstanceOf[(Int,Int)] ))
      val in_x462_imagDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x470_imag2D_0 = Flipped(new StandardInterface(ModuleParams.getParams("x470_imag2D_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x610 = {io.in_x610} 
    def x611 = {io.in_x611} 
    def x609 = {io.in_x609} 
    def x462_imagDRAM = {io.in_x462_imagDRAM} 
    def x470_imag2D_0 = {io.in_x470_imag2D_0} ; io.in_x470_imag2D_0 := DontCare
  }
  def connectWires0(module: x663_outr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
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
    Ledger.enter(this.hashCode, "x663_outr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x663_outr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x663_outr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x663_outr_Foreach = Module(new InstrumentationCounter())
      val iters_x663_outr_Foreach = Module(new InstrumentationCounter())
      cycles_x663_outr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x663_outr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X663_instrctr, cycles_x663_outr_Foreach.io.count, iters_x663_outr_Foreach.io.count, 0.U, 0.U)
      val b614 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b614.suggestName("b614")
      val b615 = ~io.sigsIn.cchainOutputs.head.oobs(0); b615.suggestName("b615")
      val x658_outr_UnitPipe = new x658_outr_UnitPipe_kernel(List(b615), List(x470_imag2D_0), List(x609), List(x610), List(b614,x462_imagDRAM) ,  Some(me), List(), 0, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x658_outr_UnitPipe.sm.io.ctrDone := risingEdge(x658_outr_UnitPipe.sm.io.ctrInc)
      x658_outr_UnitPipe.backpressure := true.B | x658_outr_UnitPipe.sm.io.doneLatch
      x658_outr_UnitPipe.forwardpressure := (true.B) && (true.B) | x658_outr_UnitPipe.sm.io.doneLatch
      x658_outr_UnitPipe.sm.io.enableOut.zip(x658_outr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x658_outr_UnitPipe.sm.io.break := false.B
      x658_outr_UnitPipe.mask := true.B & b615
      x658_outr_UnitPipe.configure("x658_outr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x658_outr_UnitPipe.kernel()
      val x662_inr_UnitPipe = new x662_inr_UnitPipe_kernel(List(b615), List(x611) ,  Some(me), List(), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x662_inr_UnitPipe.sm.io.ctrDone := risingEdge(x662_inr_UnitPipe.sm.io.ctrInc)
      x662_inr_UnitPipe.backpressure := true.B | x662_inr_UnitPipe.sm.io.doneLatch
      x662_inr_UnitPipe.forwardpressure := (x611.valid) && (true.B) | x662_inr_UnitPipe.sm.io.doneLatch
      x662_inr_UnitPipe.sm.io.enableOut.zip(x662_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x662_inr_UnitPipe.sm.io.break := false.B
      x662_inr_UnitPipe.mask := true.B & b615
      x662_inr_UnitPipe.configure("x662_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x662_inr_UnitPipe.kernel()
    }
    val module = Module(new x663_outr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x663_outr_Foreach **/
