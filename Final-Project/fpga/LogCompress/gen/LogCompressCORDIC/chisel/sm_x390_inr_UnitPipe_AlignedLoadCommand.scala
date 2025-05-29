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

/** Hierarchy: x390 -> x400 -> x489 -> x491 **/
/** BEGIN Some(AlignedLoadCommand) x390_inr_UnitPipe_AlignedLoadCommand **/
class x390_inr_UnitPipe_AlignedLoadCommand_kernel(
  list_x331_constDram: List[FixedPoint],
  list_x384: List[DecoupledIO[AppCommandDense]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 0.0.toInt, myName = "x390_inr_UnitPipe_AlignedLoadCommand_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x390_inr_UnitPipe_AlignedLoadCommand_iiCtr"))
  
  abstract class x390_inr_UnitPipe_AlignedLoadCommand_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x384 = Decoupled(new AppCommandDense(ModuleParams.getParams("x384_p").asInstanceOf[(Int,Int)] ))
      val in_x331_constDram = Input(new FixedPoint(true, 64, 0))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x384 = {io.in_x384} 
    def x331_constDram = {io.in_x331_constDram} 
  }
  def connectWires0(module: x390_inr_UnitPipe_AlignedLoadCommand_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x384 <> x384
    module.io.in_x331_constDram <> x331_constDram
  }
  val x331_constDram = list_x331_constDram(0)
  val x384 = list_x384(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x390_inr_UnitPipe_AlignedLoadCommand")
    implicit val stack = ControllerStack.stack.toList
    class x390_inr_UnitPipe_AlignedLoadCommand_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x390_inr_UnitPipe_AlignedLoadCommand_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x386 = x331_constDram
      val x387_tuple = Wire(UInt(97.W)).suggestName("""x387_tuple""")
      x387_tuple.r := ConvAndCat(true.B,64L.FP(true, 32, 0).r,x386.r)
      val x388 = true.B
      x384.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x388 & io.sigsIn.backpressure
      x384.bits.addr := x387_tuple(63,0)
      x384.bits.size := x387_tuple(95,64)
    }
    val module = Module(new x390_inr_UnitPipe_AlignedLoadCommand_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x390_inr_UnitPipe_AlignedLoadCommand **/
