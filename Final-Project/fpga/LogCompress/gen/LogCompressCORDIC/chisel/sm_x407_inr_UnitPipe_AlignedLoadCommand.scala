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

/** Hierarchy: x407 -> x417 -> x489 -> x491 **/
/** BEGIN Some(AlignedLoadCommand) x407_inr_UnitPipe_AlignedLoadCommand **/
class x407_inr_UnitPipe_AlignedLoadCommand_kernel(
  list_x332_twoNegDram: List[FixedPoint],
  list_x401: List[DecoupledIO[AppCommandDense]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 0.0.toInt, myName = "x407_inr_UnitPipe_AlignedLoadCommand_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x407_inr_UnitPipe_AlignedLoadCommand_iiCtr"))
  
  abstract class x407_inr_UnitPipe_AlignedLoadCommand_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x401 = Decoupled(new AppCommandDense(ModuleParams.getParams("x401_p").asInstanceOf[(Int,Int)] ))
      val in_x332_twoNegDram = Input(new FixedPoint(true, 64, 0))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x401 = {io.in_x401} 
    def x332_twoNegDram = {io.in_x332_twoNegDram} 
  }
  def connectWires0(module: x407_inr_UnitPipe_AlignedLoadCommand_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x401 <> x401
    module.io.in_x332_twoNegDram <> x332_twoNegDram
  }
  val x332_twoNegDram = list_x332_twoNegDram(0)
  val x401 = list_x401(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x407_inr_UnitPipe_AlignedLoadCommand")
    implicit val stack = ControllerStack.stack.toList
    class x407_inr_UnitPipe_AlignedLoadCommand_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x407_inr_UnitPipe_AlignedLoadCommand_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x403 = x332_twoNegDram
      val x404_tuple = Wire(UInt(97.W)).suggestName("""x404_tuple""")
      x404_tuple.r := ConvAndCat(true.B,64L.FP(true, 32, 0).r,x403.r)
      val x405 = true.B
      x401.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x405 & io.sigsIn.backpressure
      x401.bits.addr := x404_tuple(63,0)
      x401.bits.size := x404_tuple(95,64)
    }
    val module = Module(new x407_inr_UnitPipe_AlignedLoadCommand_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x407_inr_UnitPipe_AlignedLoadCommand **/
