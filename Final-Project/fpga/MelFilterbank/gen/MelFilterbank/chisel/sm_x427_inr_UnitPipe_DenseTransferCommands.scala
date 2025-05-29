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

/** Hierarchy: x427 -> x441 -> x453 **/
/** BEGIN Some(DenseTransferCommands) x427_inr_UnitPipe_DenseTransferCommands **/
class x427_inr_UnitPipe_DenseTransferCommands_kernel(
  list_x293_outDram: List[FixedPoint],
  list_x420: List[DecoupledIO[AppCommandDense]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 0.0.toInt, myName = "x427_inr_UnitPipe_DenseTransferCommands_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x427_inr_UnitPipe_DenseTransferCommands_iiCtr"))
  
  abstract class x427_inr_UnitPipe_DenseTransferCommands_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x293_outDram = Input(new FixedPoint(true, 64, 0))
      val in_x420 = Decoupled(new AppCommandDense(ModuleParams.getParams("x420_p").asInstanceOf[(Int,Int)] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x293_outDram = {io.in_x293_outDram} 
    def x420 = {io.in_x420} 
  }
  def connectWires0(module: x427_inr_UnitPipe_DenseTransferCommands_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x293_outDram <> x293_outDram
    module.io.in_x420 <> x420
  }
  val x293_outDram = list_x293_outDram(0)
  val x420 = list_x420(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x427_inr_UnitPipe_DenseTransferCommands")
    implicit val stack = ControllerStack.stack.toList
    class x427_inr_UnitPipe_DenseTransferCommands_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x427_inr_UnitPipe_DenseTransferCommands_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x423 = x293_outDram
      val x424_tuple = Wire(UInt(97.W)).suggestName("""x424_tuple""")
      x424_tuple.r := ConvAndCat(false.B,320L.FP(true, 32, 0).r,x423.r)
      val x425 = true.B
      x420.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x425 & io.sigsIn.backpressure
      x420.bits.addr := x424_tuple(63,0)
      x420.bits.size := x424_tuple(95,64)
    }
    val module = Module(new x427_inr_UnitPipe_DenseTransferCommands_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x427_inr_UnitPipe_DenseTransferCommands **/
