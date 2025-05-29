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

/** Hierarchy: x187 -> x201 -> x122 **/
/** BEGIN Some(DenseTransferCommands) x187_inr_UnitPipe_DenseTransferCommands **/
class x187_inr_UnitPipe_DenseTransferCommands_kernel(
  list_x148_outDram: List[FixedPoint],
  list_x180: List[DecoupledIO[AppCommandDense]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 0.0.toInt, myName = "x187_inr_UnitPipe_DenseTransferCommands_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x187_inr_UnitPipe_DenseTransferCommands_iiCtr"))
  
  abstract class x187_inr_UnitPipe_DenseTransferCommands_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x148_outDram = Input(new FixedPoint(true, 64, 0))
      val in_x180 = Decoupled(new AppCommandDense(ModuleParams.getParams("x180_p").asInstanceOf[(Int,Int)] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x148_outDram = {io.in_x148_outDram} 
    def x180 = {io.in_x180} 
  }
  def connectWires0(module: x187_inr_UnitPipe_DenseTransferCommands_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x148_outDram <> x148_outDram
    module.io.in_x180 <> x180
  }
  val x148_outDram = list_x148_outDram(0)
  val x180 = list_x180(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x187_inr_UnitPipe_DenseTransferCommands")
    implicit val stack = ControllerStack.stack.toList
    class x187_inr_UnitPipe_DenseTransferCommands_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x187_inr_UnitPipe_DenseTransferCommands_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x183 = x148_outDram
      val x184_tuple = Wire(UInt(97.W)).suggestName("""x184_tuple""")
      x184_tuple.r := ConvAndCat(false.B,611840L.FP(true, 32, 0).r,x183.r)
      val x185 = true.B
      x180.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x185 & io.sigsIn.backpressure
      x180.bits.addr := x184_tuple(63,0)
      x180.bits.size := x184_tuple(95,64)
    }
    val module = Module(new x187_inr_UnitPipe_DenseTransferCommands_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x187_inr_UnitPipe_DenseTransferCommands **/
