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

/** Hierarchy: x61 **/
/** BEGIN None RootController **/
class RootController_kernel(
  list_x88_out: List[MultiArgOut],
  list_x89_i: List[UInt],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 3.0.toInt, myName = "RootController_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "RootController_iiCtr"))
  
  abstract class RootController_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x88_out = new MultiArgOut(1)
      val in_x89_i = Input(UInt(64.W))
      val in_x87_in = Input(UInt(64.W))
      val in_x90_j = Input(UInt(64.W))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x88_out = {io.in_x88_out} ; io.in_x88_out := DontCare
    def x89_i = {io.in_x89_i} 
    def x87_in = {io.in_x87_in} 
    def x90_j = {io.in_x90_j} 
  }
  def connectWires0(module: RootController_module)(implicit stack: List[KernelHash]): Unit = {
    x88_out.connectLedger(module.io.in_x88_out)
    module.io.in_x88_out.port.zip(x88_out.port).foreach{case (l,r) => l.ready := r.ready}
    module.io.in_x89_i <> x89_i
    module.io.in_x87_in <> x87_in
    module.io.in_x90_j <> x90_j
  }
  val x88_out = list_x88_out(0)
  val x89_i = list_x89_i(0)
  val x87_in = list_x89_i(1)
  val x90_j = list_x89_i(2)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "RootController")
    implicit val stack = ControllerStack.stack.toList
    class RootController_concrete(depth: Int)(implicit stack: List[KernelHash]) extends RootController_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_RootController = Module(new InstrumentationCounter())
      val iters_RootController = Module(new InstrumentationCounter())
      cycles_RootController.io.enable := io.sigsIn.baseEn
      iters_RootController.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X61_instrctr, cycles_RootController.io.count, iters_RootController.io.count, 0.U, 0.U)
      val x100_lut_0 = (new x100_lut_0).m.io.asInstanceOf[StandardInterface]
      val x101_rd_x89 = Wire(new FixedPoint(true, 32, 0))
      x101_rd_x89.r := x89_i.r
      val x102_rd_x90 = Wire(new FixedPoint(true, 32, 0))
      x102_rd_x90.r := x90_j.r
      val x105 = Wire(Vec(1, new FixedPoint(true, 32, 0))).suggestName("""x105""")
      val x105_banks = List[UInt](x101_rd_x89.r,x102_rd_x90.r)
      val x105_ofs = List[UInt](0L.FP(true, 32, 0).r)
      val x105_en = List[Bool](true.B)
      val x105_shared_en = (true.B && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x105_shared_en")
      x105.toSeq.zip(x100_lut_0.connectRPort(105, x105_banks, x105_ofs, io.sigsIn.backpressure, x105_en.map(_ && x105_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x126 = Wire(Vec(1, new FixedPoint(true, 32, 0))).suggestName("x126_x105_D1") 
      (0 until 1).foreach{i => x126(i).r := getRetimed(x105(i).r, 1.toInt, io.sigsIn.backpressure & true.B)}
      // x106 = VecApply(x126,0)
      val x106_elem_0 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x106_elem_0""")
      x106_elem_0.r := x126(0).r
      val x107_rd_x87 = Wire(new FixedPoint(true, 32, 0))
      x107_rd_x87.r := x87_in.r
      val x127 = Wire(new FixedPoint(true, 32, 0)).suggestName("x127_x107_rd_x87_D1") 
      x127.r := getRetimed(x107_rd_x87.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x108_output = Wire(new FixedPoint(true, 32, 0)).suggestName("""x108_output""")
      x108_output.r := Math.add(x106_elem_0,x127,Some(1.0), true.B, Truncate, Wrapping, "x108_output").r
      x88_out.connectWPort(0, util.Cat(util.Fill(32, x108_output.msb), x108_output.r), true.B & getRetimed(io.sigsIn.datapathEn & io.sigsIn.iiIssue, 2.0.toInt, io.sigsIn.backpressure & true.B))
    }
    val module = Module(new RootController_concrete(sm.p.depth)); module.io := DontCare
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
/** END AccelScope RootController **/
