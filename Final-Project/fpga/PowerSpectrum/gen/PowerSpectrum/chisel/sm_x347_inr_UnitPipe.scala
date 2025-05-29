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

/** Hierarchy: x347 -> x362 -> x367 -> x368 -> x375 **/
/** BEGIN None x347_inr_UnitPipe **/
class x347_inr_UnitPipe_kernel(
  list_x249_outDram: List[FixedPoint],
  list_x336: List[DecoupledIO[AppCommandDense]],
  list_x340_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x347_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x347_inr_UnitPipe_iiCtr"))
  
  abstract class x347_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x249_outDram = Input(new FixedPoint(true, 64, 0))
      val in_x340_reg = Flipped(new StandardInterface(ModuleParams.getParams("x340_reg_p").asInstanceOf[MemParams] ))
      val in_x339_reg = Flipped(new StandardInterface(ModuleParams.getParams("x339_reg_p").asInstanceOf[MemParams] ))
      val in_x336 = Decoupled(new AppCommandDense(ModuleParams.getParams("x336_p").asInstanceOf[(Int,Int)] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x249_outDram = {io.in_x249_outDram} 
    def x340_reg = {io.in_x340_reg} ; io.in_x340_reg := DontCare
    def x339_reg = {io.in_x339_reg} ; io.in_x339_reg := DontCare
    def x336 = {io.in_x336} 
  }
  def connectWires0(module: x347_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x249_outDram <> x249_outDram
    x340_reg.connectLedger(module.io.in_x340_reg)
    x339_reg.connectLedger(module.io.in_x339_reg)
    module.io.in_x336 <> x336
  }
  val x249_outDram = list_x249_outDram(0)
  val x336 = list_x336(0)
  val x340_reg = list_x340_reg(0)
  val x339_reg = list_x340_reg(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x347_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x347_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x347_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x341 = x249_outDram
      val x342_tuple = Wire(UInt(97.W)).suggestName("""x342_tuple""")
      x342_tuple.r := ConvAndCat(false.B,1537280L.FP(true, 32, 0).r,x341.r)
      val x343 = true.B
      x336.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x343 & io.sigsIn.backpressure
      x336.bits.addr := x342_tuple(63,0)
      x336.bits.size := x342_tuple(95,64)
      val x345_wr_x339_banks = List[UInt]()
      val x345_wr_x339_ofs = List[UInt]()
      val x345_wr_x339_en = List[Bool](true.B)
      val x345_wr_x339_data = List[UInt](384312L.FP(true, 32, 0).r)
      x339_reg.connectWPort(345, x345_wr_x339_banks, x345_wr_x339_ofs, x345_wr_x339_data, x345_wr_x339_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x346_wr_x340_banks = List[UInt]()
      val x346_wr_x340_ofs = List[UInt]()
      val x346_wr_x340_en = List[Bool](true.B)
      val x346_wr_x340_data = List[UInt](384320L.FP(true, 32, 0).r)
      x340_reg.connectWPort(346, x346_wr_x340_banks, x346_wr_x340_ofs, x346_wr_x340_data, x346_wr_x340_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x347_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x347_inr_UnitPipe **/
