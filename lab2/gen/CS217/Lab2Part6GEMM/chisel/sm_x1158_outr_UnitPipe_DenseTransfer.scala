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

/** Hierarchy: x1158 -> x1811 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN Some(DenseTransfer) x1158_outr_UnitPipe_DenseTransfer **/
class x1158_outr_UnitPipe_DenseTransfer_kernel(
  list_x1090: List[DecoupledIO[AppLoadData]],
  list_x1088: List[DecoupledIO[AppCommandDense]],
  list_x1011_reg: List[NBufInterface],
  list_x879_N: List[UInt],
  list_b1005: List[FixedPoint],
  list_b925: List[Bool],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 2, isFSM = false   , latency = 0.0.toInt, myName = "x1158_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1158_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x1158_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b1005 = Input(new FixedPoint(true, 32, 0))
      val in_b924 = Input(new FixedPoint(true, 32, 0))
      val in_b925 = Input(Bool())
      val in_x879_N = Input(UInt(64.W))
      val in_b1006 = Input(Bool())
      val in_x906_c = Input(new FixedPoint(true, 64, 0))
      val in_x1090 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x1090_p").asInstanceOf[(Int, Int)] )))
      val in_x1011_reg = Flipped(new NBufInterface(ModuleParams.getParams("x1011_reg_p").asInstanceOf[NBufParams] ))
      val in_x927_reg = Flipped(new NBufInterface(ModuleParams.getParams("x927_reg_p").asInstanceOf[NBufParams] ))
      val in_x1018_tileC_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b914 = Input(Bool())
      val in_x1019_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1019_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_x1088 = Decoupled(new AppCommandDense(ModuleParams.getParams("x1088_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 2, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 2))
      val rr = Input(Bool())
    })
    def b1005 = {io.in_b1005} 
    def b924 = {io.in_b924} 
    def b925 = {io.in_b925} 
    def x879_N = {io.in_x879_N} 
    def b1006 = {io.in_b1006} 
    def x906_c = {io.in_x906_c} 
    def x1090 = {io.in_x1090} 
    def x1011_reg = {io.in_x1011_reg} ; io.in_x1011_reg := DontCare
    def x927_reg = {io.in_x927_reg} ; io.in_x927_reg := DontCare
    def x1018_tileC_sram_0 = {io.in_x1018_tileC_sram_0} ; io.in_x1018_tileC_sram_0 := DontCare
    def b914 = {io.in_b914} 
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
    def x1088 = {io.in_x1088} 
  }
  def connectWires0(module: x1158_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b1005 <> b1005
    module.io.in_b924 <> b924
    module.io.in_b925 <> b925
    module.io.in_x879_N <> x879_N
    module.io.in_b1006 <> b1006
    module.io.in_x906_c <> x906_c
    module.io.in_x1090 <> x1090
    x1011_reg.connectLedger(module.io.in_x1011_reg)
    x927_reg.connectLedger(module.io.in_x927_reg)
    x1018_tileC_sram_0.connectLedger(module.io.in_x1018_tileC_sram_0)
    module.io.in_b914 <> b914
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
    module.io.in_x1088 <> x1088
  }
  val x1090 = list_x1090(0)
  val x1088 = list_x1088(0)
  val x1011_reg = list_x1011_reg(0)
  val x927_reg = list_x1011_reg(1)
  val x1018_tileC_sram_0 = list_x1011_reg(2)
  val x1019_tileC_sram_1 = list_x1011_reg(3)
  val x879_N = list_x879_N(0)
  val b1005 = list_b1005(0)
  val b924 = list_b1005(1)
  val x906_c = list_b1005(2)
  val b925 = list_b925(0)
  val b1006 = list_b925(1)
  val b914 = list_b925(2)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1158_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x1158_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1158_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1158_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      val iters_x1158_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      cycles_x1158_outr_UnitPipe_DenseTransfer.io.enable := io.sigsIn.baseEn
      iters_x1158_outr_UnitPipe_DenseTransfer.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1158_instrctr, cycles_x1158_outr_UnitPipe_DenseTransfer.io.count, iters_x1158_outr_UnitPipe_DenseTransfer.io.count, 0.U, 0.U)
      val x1089_fifo = (new x1089_fifo).m.io.asInstanceOf[FIFOInterface]
      val x1767_rd_x927 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1767_rd_x927""")
      val x1767_rd_x927_banks = List[UInt]()
      val x1767_rd_x927_ofs = List[UInt]()
      val x1767_rd_x927_en = List[Bool](true.B)
      val x1767_rd_x927_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1767_rd_x927_shared_en")
      x1767_rd_x927.toSeq.zip(x927_reg.connectRPort(1767, x1767_rd_x927_banks, x1767_rd_x927_ofs, io.sigsIn.backpressure, x1767_rd_x927_en.map(_ && x1767_rd_x927_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1091_ctr = new CtrObject(Left(Some(0)), Right(x1767_rd_x927), Left(Some(1)), 1, 32, false)
      val x1092_ctrchain = (new CChainObject(List[CtrObject](x1091_ctr), "x1092_ctrchain")).cchain.io 
      x1092_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1092_ctrchain_p", (x1092_ctrchain.par, x1092_ctrchain.widths))
      val x1117_inr_Foreach = new x1117_inr_Foreach_kernel(List(x1088), List(x1089_fifo), List(x1011_reg), List(x879_N), List(b1005,b924,x906_c) ,  Some(me), List(x1092_ctrchain), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1117_inr_Foreach.sm.io.ctrDone := (x1117_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1117_inr_Foreach.backpressure := (~x1089_fifo.full.D(11.8-1) | ~(x1089_fifo.active(0).out)) & x1088.ready | x1117_inr_Foreach.sm.io.doneLatch
      x1117_inr_Foreach.forwardpressure := (true.B) && (true.B) | x1117_inr_Foreach.sm.io.doneLatch
      x1117_inr_Foreach.sm.io.enableOut.zip(x1117_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1117_inr_Foreach.sm.io.break := false.B
      x1117_inr_Foreach.mask := ~x1117_inr_Foreach.cchain.head.output.noop & true.B
      x1117_inr_Foreach.configure("x1117_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1117_inr_Foreach.kernel()
      val x1768_rd_x927 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1768_rd_x927""")
      val x1768_rd_x927_banks = List[UInt]()
      val x1768_rd_x927_ofs = List[UInt]()
      val x1768_rd_x927_en = List[Bool](true.B)
      val x1768_rd_x927_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1768_rd_x927_shared_en")
      x1768_rd_x927.toSeq.zip(x927_reg.connectRPort(1768, x1768_rd_x927_banks, x1768_rd_x927_ofs, io.sigsIn.backpressure, x1768_rd_x927_en.map(_ && x1768_rd_x927_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1119_ctr = new CtrObject(Left(Some(0)), Right(x1768_rd_x927), Left(Some(1)), 1, 32, false)
      val x1120_ctrchain = (new CChainObject(List[CtrObject](x1119_ctr), "x1120_ctrchain")).cchain.io 
      x1120_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1120_ctrchain_p", (x1120_ctrchain.par, x1120_ctrchain.widths))
      val x1157_outr_Foreach = new x1157_outr_Foreach_kernel(List(x1089_fifo), List(x1090), List(x1018_tileC_sram_0,x1019_tileC_sram_1) ,  Some(me), List(x1120_ctrchain), 1, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1157_outr_Foreach.sm.io.ctrDone := (x1157_outr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1157_outr_Foreach.backpressure := true.B | x1157_outr_Foreach.sm.io.doneLatch
      x1157_outr_Foreach.forwardpressure := (true.B) && (true.B) | x1157_outr_Foreach.sm.io.doneLatch
      x1157_outr_Foreach.sm.io.enableOut.zip(x1157_outr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1157_outr_Foreach.sm.io.break := false.B
      x1157_outr_Foreach.mask := ~x1157_outr_Foreach.cchain.head.output.noop & true.B
      x1157_outr_Foreach.configure("x1157_outr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1157_outr_Foreach.kernel()
    }
    val module = Module(new x1158_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x1158_outr_UnitPipe_DenseTransfer **/
