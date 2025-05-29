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

/** Hierarchy: x122 **/
/** BEGIN None RootController **/
class RootController_kernel(
  list_x154: List[DecoupledIO[AppLoadData]],
  list_x147_inDram: List[FixedPoint],
  list_x181: List[DecoupledIO[AppStoreData]],
  list_x153: List[DecoupledIO[AppCommandDense]],
  list_x149_Accel_n: List[UInt],
  list_x182: List[DecoupledIO[Bool]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 3, isFSM = false   , latency = 0.0.toInt, myName = "RootController_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "RootController_iiCtr"))
  
  abstract class RootController_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x153 = Decoupled(new AppCommandDense(ModuleParams.getParams("x153_p").asInstanceOf[(Int,Int)] ))
      val in_x147_inDram = Input(new FixedPoint(true, 64, 0))
      val in_x148_outDram = Input(new FixedPoint(true, 64, 0))
      val in_x180 = Decoupled(new AppCommandDense(ModuleParams.getParams("x180_p").asInstanceOf[(Int,Int)] ))
      val in_x149_Accel_n = Input(UInt(64.W))
      val in_x181 = Decoupled(new AppStoreData(ModuleParams.getParams("x181_p").asInstanceOf[(Int,Int)] ))
      val in_x182 = Flipped(Decoupled(Bool()))
      val in_x154 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x154_p").asInstanceOf[(Int, Int)] )))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(3, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(3, 1))
      val rr = Input(Bool())
    })
    def x153 = {io.in_x153} 
    def x147_inDram = {io.in_x147_inDram} 
    def x148_outDram = {io.in_x148_outDram} 
    def x180 = {io.in_x180} 
    def x149_Accel_n = {io.in_x149_Accel_n} 
    def x181 = {io.in_x181} 
    def x182 = {io.in_x182} 
    def x154 = {io.in_x154} 
  }
  def connectWires0(module: RootController_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x153 <> x153
    module.io.in_x147_inDram <> x147_inDram
    module.io.in_x148_outDram <> x148_outDram
    module.io.in_x180 <> x180
    module.io.in_x149_Accel_n <> x149_Accel_n
    module.io.in_x181 <> x181
    module.io.in_x182 <> x182
    module.io.in_x154 <> x154
  }
  val x154 = list_x154(0)
  val x147_inDram = list_x147_inDram(0)
  val x148_outDram = list_x147_inDram(1)
  val x181 = list_x181(0)
  val x153 = list_x153(0)
  val x180 = list_x153(1)
  val x149_Accel_n = list_x149_Accel_n(0)
  val x182 = list_x182(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "RootController")
    implicit val stack = ControllerStack.stack.toList
    class RootController_concrete(depth: Int)(implicit stack: List[KernelHash]) extends RootController_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x152_buf_0 = (new x152_buf_0).m.io.asInstanceOf[StandardInterface]
      val x169_outr_UnitPipe_DenseTransfer = new x169_outr_UnitPipe_DenseTransfer_kernel(List(x147_inDram), List(x153), List(x154), List(x152_buf_0) ,  Some(me), List(), 0, 2, 2, List(1), List(32), breakpoints, rr)
      x169_outr_UnitPipe_DenseTransfer.backpressure := true.B | x169_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x169_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x169_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x169_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x169_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x169_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x169_outr_UnitPipe_DenseTransfer.mask := true.B & true.B
      x169_outr_UnitPipe_DenseTransfer.configure("x169_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x169_outr_UnitPipe_DenseTransfer.kernel()
      val x220_rd_x149 = Wire(new FixedPoint(true, 32, 0))
      x220_rd_x149.r := x149_Accel_n.r
      val x170_ctr = new CtrObject(Left(Some(0)), Right(x220_rd_x149), Left(Some(1)), 1, 32, false)
      val x171_ctrchain = (new CChainObject(List[CtrObject](x170_ctr), "x171_ctrchain")).cchain.io 
      x171_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x171_ctrchain_p", (x171_ctrchain.par, x171_ctrchain.widths))
      val x179_inr_Foreach = new x179_inr_Foreach_kernel(List(x152_buf_0) ,  Some(me), List(x171_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, rr)
      x179_inr_Foreach.sm.io.ctrDone := (x179_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x179_inr_Foreach.backpressure := true.B | x179_inr_Foreach.sm.io.doneLatch
      x179_inr_Foreach.forwardpressure := (true.B) && (true.B) | x179_inr_Foreach.sm.io.doneLatch
      x179_inr_Foreach.sm.io.enableOut.zip(x179_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x179_inr_Foreach.sm.io.break := false.B
      x179_inr_Foreach.mask := ~x179_inr_Foreach.cchain.head.output.noop & true.B
      x179_inr_Foreach.configure("x179_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x179_inr_Foreach.kernel()
      val x201_outr_UnitPipe_DenseTransfer = new x201_outr_UnitPipe_DenseTransfer_kernel(List(x181), List(x148_outDram), List(x180), List(x152_buf_0), List(x182) ,  Some(me), List(), 2, 3, 3, List(1), List(32), breakpoints, rr)
      x201_outr_UnitPipe_DenseTransfer.backpressure := true.B | x201_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x201_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x201_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x201_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x201_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x201_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x201_outr_UnitPipe_DenseTransfer.mask := true.B & true.B
      x201_outr_UnitPipe_DenseTransfer.configure("x201_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x201_outr_UnitPipe_DenseTransfer.kernel()
    }
    val module = Module(new RootController_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END AccelScope RootController **/
