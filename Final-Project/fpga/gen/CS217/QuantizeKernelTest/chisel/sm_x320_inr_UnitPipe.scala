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

/** Hierarchy: x320 -> x335 -> x340 -> x341 -> x213 **/
/** BEGIN None x320_inr_UnitPipe **/
class x320_inr_UnitPipe_kernel(
  list_x251_outDRAM: List[FixedPoint],
  list_x309: List[DecoupledIO[AppCommandDense]],
  list_x312_reg: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 1.0.toInt, myName = "x320_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x320_inr_UnitPipe_iiCtr"))
  
  abstract class x320_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x312_reg = Flipped(new StandardInterface(ModuleParams.getParams("x312_reg_p").asInstanceOf[MemParams] ))
      val in_x313_reg = Flipped(new StandardInterface(ModuleParams.getParams("x313_reg_p").asInstanceOf[MemParams] ))
      val in_x251_outDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x309 = Decoupled(new AppCommandDense(ModuleParams.getParams("x309_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x312_reg = {io.in_x312_reg} ; io.in_x312_reg := DontCare
    def x313_reg = {io.in_x313_reg} ; io.in_x313_reg := DontCare
    def x251_outDRAM = {io.in_x251_outDRAM} 
    def x309 = {io.in_x309} 
  }
  def connectWires0(module: x320_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x312_reg.connectLedger(module.io.in_x312_reg)
    x313_reg.connectLedger(module.io.in_x313_reg)
    module.io.in_x251_outDRAM <> x251_outDRAM
    module.io.in_x309 <> x309
  }
  val x251_outDRAM = list_x251_outDRAM(0)
  val x309 = list_x309(0)
  val x312_reg = list_x312_reg(0)
  val x313_reg = list_x312_reg(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x320_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x320_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x320_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x320_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x320_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x320_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x320_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x320_inr_UnitPipe = Module(new InstrumentationCounter())
      val idles_x320_inr_UnitPipe = Module(new InstrumentationCounter())
      stalls_x320_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(x309.ready)
      idles_x320_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X320_instrctr, cycles_x320_inr_UnitPipe.io.count, iters_x320_inr_UnitPipe.io.count, stalls_x320_inr_UnitPipe.io.count, idles_x320_inr_UnitPipe.io.count)
      val x314 = x251_outDRAM
      val x315_tuple = Wire(UInt(97.W)).suggestName("""x315_tuple""")
      x315_tuple.r := ConvAndCat(false.B,63L.FP(true, 32, 0).r,x314.r)
      val x316 = true.B
      x309.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x316 & io.sigsIn.backpressure
      x309.bits.addr := x315_tuple(63,0)
      x309.bits.size := x315_tuple(95,64)
      val x318_wr_x312_banks = List[UInt]()
      val x318_wr_x312_ofs = List[UInt]()
      val x318_wr_x312_en = List[Bool](true.B)
      val x318_wr_x312_data = List[UInt](9L.FP(true, 32, 0).r)
      x312_reg.connectWPort(318, x318_wr_x312_banks, x318_wr_x312_ofs, x318_wr_x312_data, x318_wr_x312_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x319_wr_x313_banks = List[UInt]()
      val x319_wr_x313_ofs = List[UInt]()
      val x319_wr_x313_en = List[Bool](true.B)
      val x319_wr_x313_data = List[UInt](21L.FP(true, 32, 0).r)
      x313_reg.connectWPort(319, x319_wr_x313_banks, x319_wr_x313_ofs, x319_wr_x313_data, x319_wr_x313_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x320_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x320_inr_UnitPipe **/
