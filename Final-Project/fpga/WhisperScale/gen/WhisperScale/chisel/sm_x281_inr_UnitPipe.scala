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

/** Hierarchy: x281 -> x296 -> x301 -> x302 -> x196 **/
/** BEGIN None x281_inr_UnitPipe **/
class x281_inr_UnitPipe_kernel(
  list_x210_outDram: List[FixedPoint],
  list_x265: List[DecoupledIO[AppCommandDense]],
  list_x269_reg: List[StandardInterface],
  list_x206_argIn: List[UInt],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 2.4.toInt, myName = "x281_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x281_inr_UnitPipe_iiCtr"))
  
  abstract class x281_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x269_reg = Flipped(new StandardInterface(ModuleParams.getParams("x269_reg_p").asInstanceOf[MemParams] ))
      val in_x265 = Decoupled(new AppCommandDense(ModuleParams.getParams("x265_p").asInstanceOf[(Int,Int)] ))
      val in_x206_argIn = Input(UInt(64.W))
      val in_x210_outDram = Input(new FixedPoint(true, 64, 0))
      val in_x268_reg = Flipped(new StandardInterface(ModuleParams.getParams("x268_reg_p").asInstanceOf[MemParams] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x269_reg = {io.in_x269_reg} ; io.in_x269_reg := DontCare
    def x265 = {io.in_x265} 
    def x206_argIn = {io.in_x206_argIn} 
    def x210_outDram = {io.in_x210_outDram} 
    def x268_reg = {io.in_x268_reg} ; io.in_x268_reg := DontCare
  }
  def connectWires0(module: x281_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x269_reg.connectLedger(module.io.in_x269_reg)
    module.io.in_x265 <> x265
    module.io.in_x206_argIn <> x206_argIn
    module.io.in_x210_outDram <> x210_outDram
    x268_reg.connectLedger(module.io.in_x268_reg)
  }
  val x210_outDram = list_x210_outDram(0)
  val x265 = list_x265(0)
  val x269_reg = list_x269_reg(0)
  val x268_reg = list_x269_reg(1)
  val x206_argIn = list_x206_argIn(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x281_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x281_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x281_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x270_rd_x206 = Wire(new FixedPoint(true, 32, 0))
      x270_rd_x206.r := x206_argIn.r
      val x271_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x271_sum""")
      val ensig0 = Wire(Bool())
      ensig0 := x265.ready
      x271_sum.r := Math.add(x270_rd_x206,15L.FP(true, 32, 0),Some(1.0), ensig0, Truncate, Wrapping, "x271_sum").r
      val x272 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x272""")
      x272.r := Math.arith_right_shift_div(x271_sum, 4, Some(0.2), ensig0,"x272").r
      val x273 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x273""")
      x273.r := Math.arith_left_shift(x272, 4, Some(0.2), ensig0,"x273").r
      val x307 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x307""")
      x307.r := Math.arith_left_shift(x272, 6, Some(0.2), ensig0,"x307").r
      val x275 = x210_outDram
      val x317 = Wire(new FixedPoint(true, 64, 0)).suggestName("x317_x275_D1") 
      x317.r := getRetimed(x275.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x276_tuple = Wire(UInt(97.W)).suggestName("""x276_tuple""")
      x276_tuple.r := ConvAndCat(false.B,x307.r,x317.r)
      val x277 = true.B
      val x318 = Wire(Bool()).suggestName("x318_x277_D1") 
      x318.r := getRetimed(x277.r, 1.toInt, io.sigsIn.backpressure & true.B)
      x265.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.4.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x318 & io.sigsIn.backpressure
      x265.bits.addr := x276_tuple(63,0)
      x265.bits.size := x276_tuple(95,64)
      val x279_wr_x268_banks = List[UInt]()
      val x279_wr_x268_ofs = List[UInt]()
      val x279_wr_x268_en = List[Bool](true.B)
      val x279_wr_x268_data = List[UInt](x270_rd_x206.r)
      x268_reg.connectWPort(279, x279_wr_x268_banks, x279_wr_x268_ofs, x279_wr_x268_data, x279_wr_x268_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x280_wr_x269_banks = List[UInt]()
      val x280_wr_x269_ofs = List[UInt]()
      val x280_wr_x269_en = List[Bool](true.B)
      val x280_wr_x269_data = List[UInt](x273.r)
      x269_reg.connectWPort(280, x280_wr_x269_banks, x280_wr_x269_ofs, x280_wr_x269_data, x280_wr_x269_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(1.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x281_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x281_inr_UnitPipe **/
