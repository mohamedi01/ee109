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

/** Hierarchy: x310 -> x325 -> x330 -> x331 -> x201 **/
/** BEGIN None x310_inr_UnitPipe **/
class x310_inr_UnitPipe_kernel(
  list_x251_outDRAM: List[FixedPoint],
  list_x299: List[DecoupledIO[AppCommandDense]],
  list_x302_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x310_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x310_inr_UnitPipe_iiCtr"))
  
  abstract class x310_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x302_reg = Flipped(new StandardInterface(ModuleParams.getParams("x302_reg_p").asInstanceOf[MemParams] ))
      val in_x303_reg = Flipped(new StandardInterface(ModuleParams.getParams("x303_reg_p").asInstanceOf[MemParams] ))
      val in_x251_outDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x299 = Decoupled(new AppCommandDense(ModuleParams.getParams("x299_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x302_reg = {io.in_x302_reg} ; io.in_x302_reg := DontCare
    def x303_reg = {io.in_x303_reg} ; io.in_x303_reg := DontCare
    def x251_outDRAM = {io.in_x251_outDRAM} 
    def x299 = {io.in_x299} 
  }
  def connectWires0(module: x310_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x302_reg.connectLedger(module.io.in_x302_reg)
    x303_reg.connectLedger(module.io.in_x303_reg)
    module.io.in_x251_outDRAM <> x251_outDRAM
    module.io.in_x299 <> x299
  }
  val x251_outDRAM = list_x251_outDRAM(0)
  val x299 = list_x299(0)
  val x302_reg = list_x302_reg(0)
  val x303_reg = list_x302_reg(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x310_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x310_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x310_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x310_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x310_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x310_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x310_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x310_inr_UnitPipe = Module(new InstrumentationCounter())
      val idles_x310_inr_UnitPipe = Module(new InstrumentationCounter())
      stalls_x310_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(x299.ready)
      idles_x310_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X310_instrctr, cycles_x310_inr_UnitPipe.io.count, iters_x310_inr_UnitPipe.io.count, stalls_x310_inr_UnitPipe.io.count, idles_x310_inr_UnitPipe.io.count)
      val x304 = x251_outDRAM
      val x305_tuple = Wire(UInt(97.W)).suggestName("""x305_tuple""")
      x305_tuple.r := ConvAndCat(false.B,63L.FP(true, 32, 0).r,x304.r)
      val x306 = true.B
      x299.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x306 & io.sigsIn.backpressure
      x299.bits.addr := x305_tuple(63,0)
      x299.bits.size := x305_tuple(95,64)
      val x308_wr_x302_banks = List[UInt]()
      val x308_wr_x302_ofs = List[UInt]()
      val x308_wr_x302_en = List[Bool](true.B)
      val x308_wr_x302_data = List[UInt](4L.FP(true, 32, 0).r)
      x302_reg.connectWPort(308, x308_wr_x302_banks, x308_wr_x302_ofs, x308_wr_x302_data, x308_wr_x302_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x309_wr_x303_banks = List[UInt]()
      val x309_wr_x303_ofs = List[UInt]()
      val x309_wr_x303_en = List[Bool](true.B)
      val x309_wr_x303_data = List[UInt](21L.FP(true, 32, 0).r)
      x303_reg.connectWPort(309, x309_wr_x303_banks, x309_wr_x303_ofs, x309_wr_x303_data, x309_wr_x303_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x310_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x310_inr_UnitPipe **/
