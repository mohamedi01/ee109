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

/** Hierarchy: x1087 -> x1811 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN Some(DenseTransfer) x1087_outr_UnitPipe_DenseTransfer **/
class x1087_outr_UnitPipe_DenseTransfer_kernel(
  list_b1005: List[FixedPoint],
  list_x916_reg: List[NBufInterface],
  list_x879_N: List[UInt],
  list_x1020: List[DecoupledIO[AppCommandDense]],
  list_b925: List[Bool],
  list_x1022: List[DecoupledIO[AppLoadData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 2, isFSM = false   , latency = 0.0.toInt, myName = "x1087_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1087_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x1087_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b1005 = Input(new FixedPoint(true, 32, 0))
      val in_b913 = Input(new FixedPoint(true, 32, 0))
      val in_x1020 = Decoupled(new AppCommandDense(ModuleParams.getParams("x1020_p").asInstanceOf[(Int,Int)] ))
      val in_b925 = Input(Bool())
      val in_x916_reg = Flipped(new NBufInterface(ModuleParams.getParams("x916_reg_p").asInstanceOf[NBufParams] ))
      val in_x879_N = Input(UInt(64.W))
      val in_x1017_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1006 = Input(Bool())
      val in_x1011_reg = Flipped(new NBufInterface(ModuleParams.getParams("x1011_reg_p").asInstanceOf[NBufParams] ))
      val in_x1022 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x1022_p").asInstanceOf[(Int, Int)] )))
      val in_x903_b = Input(new FixedPoint(true, 64, 0))
      val in_b914 = Input(Bool())
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 2, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 2))
      val rr = Input(Bool())
    })
    def b1005 = {io.in_b1005} 
    def b913 = {io.in_b913} 
    def x1020 = {io.in_x1020} 
    def b925 = {io.in_b925} 
    def x916_reg = {io.in_x916_reg} ; io.in_x916_reg := DontCare
    def x879_N = {io.in_x879_N} 
    def x1017_tileB_sram_0 = {io.in_x1017_tileB_sram_0} ; io.in_x1017_tileB_sram_0 := DontCare
    def b1006 = {io.in_b1006} 
    def x1011_reg = {io.in_x1011_reg} ; io.in_x1011_reg := DontCare
    def x1022 = {io.in_x1022} 
    def x903_b = {io.in_x903_b} 
    def b914 = {io.in_b914} 
  }
  def connectWires0(module: x1087_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b1005 <> b1005
    module.io.in_b913 <> b913
    module.io.in_x1020 <> x1020
    module.io.in_b925 <> b925
    x916_reg.connectLedger(module.io.in_x916_reg)
    module.io.in_x879_N <> x879_N
    x1017_tileB_sram_0.connectLedger(module.io.in_x1017_tileB_sram_0)
    module.io.in_b1006 <> b1006
    x1011_reg.connectLedger(module.io.in_x1011_reg)
    module.io.in_x1022 <> x1022
    module.io.in_x903_b <> x903_b
    module.io.in_b914 <> b914
  }
  val b1005 = list_b1005(0)
  val b913 = list_b1005(1)
  val x903_b = list_b1005(2)
  val x916_reg = list_x916_reg(0)
  val x1017_tileB_sram_0 = list_x916_reg(1)
  val x1011_reg = list_x916_reg(2)
  val x879_N = list_x879_N(0)
  val x1020 = list_x1020(0)
  val b925 = list_b925(0)
  val b1006 = list_b925(1)
  val b914 = list_b925(2)
  val x1022 = list_x1022(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1087_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x1087_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1087_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1087_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      val iters_x1087_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      cycles_x1087_outr_UnitPipe_DenseTransfer.io.enable := io.sigsIn.baseEn
      iters_x1087_outr_UnitPipe_DenseTransfer.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1087_instrctr, cycles_x1087_outr_UnitPipe_DenseTransfer.io.count, iters_x1087_outr_UnitPipe_DenseTransfer.io.count, 0.U, 0.U)
      val x1021_fifo = (new x1021_fifo).m.io.asInstanceOf[FIFOInterface]
      val x1764_rd_x916 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1764_rd_x916""")
      val x1764_rd_x916_banks = List[UInt]()
      val x1764_rd_x916_ofs = List[UInt]()
      val x1764_rd_x916_en = List[Bool](true.B)
      val x1764_rd_x916_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1764_rd_x916_shared_en")
      x1764_rd_x916.toSeq.zip(x916_reg.connectRPort(1764, x1764_rd_x916_banks, x1764_rd_x916_ofs, io.sigsIn.backpressure, x1764_rd_x916_en.map(_ && x1764_rd_x916_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      val x1023_ctr = new CtrObject(Left(Some(0)), Right(x1764_rd_x916), Left(Some(1)), 1, 32, false)
      val x1024_ctrchain = (new CChainObject(List[CtrObject](x1023_ctr), "x1024_ctrchain")).cchain.io 
      x1024_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1024_ctrchain_p", (x1024_ctrchain.par, x1024_ctrchain.widths))
      val x1049_inr_Foreach = new x1049_inr_Foreach_kernel(List(x1021_fifo), List(b1005,b913,x903_b), List(x1011_reg), List(x879_N), List(x1020) ,  Some(me), List(x1024_ctrchain), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1049_inr_Foreach.sm.io.ctrDone := (x1049_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1049_inr_Foreach.backpressure := x1020.ready & (~x1021_fifo.full.D(11.8-1) | ~(x1021_fifo.active(0).out)) | x1049_inr_Foreach.sm.io.doneLatch
      x1049_inr_Foreach.forwardpressure := (true.B) && (true.B) | x1049_inr_Foreach.sm.io.doneLatch
      x1049_inr_Foreach.sm.io.enableOut.zip(x1049_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1049_inr_Foreach.sm.io.break := false.B
      x1049_inr_Foreach.mask := ~x1049_inr_Foreach.cchain.head.output.noop & true.B
      x1049_inr_Foreach.configure("x1049_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1049_inr_Foreach.kernel()
      val x1765_rd_x916 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1765_rd_x916""")
      val x1765_rd_x916_banks = List[UInt]()
      val x1765_rd_x916_ofs = List[UInt]()
      val x1765_rd_x916_en = List[Bool](true.B)
      val x1765_rd_x916_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1765_rd_x916_shared_en")
      x1765_rd_x916.toSeq.zip(x916_reg.connectRPort(1765, x1765_rd_x916_banks, x1765_rd_x916_ofs, io.sigsIn.backpressure, x1765_rd_x916_en.map(_ && x1765_rd_x916_shared_en), false)).foreach{case (a,b) => a.r := b.r}
      val x1051_ctr = new CtrObject(Left(Some(0)), Right(x1765_rd_x916), Left(Some(1)), 1, 32, false)
      val x1052_ctrchain = (new CChainObject(List[CtrObject](x1051_ctr), "x1052_ctrchain")).cchain.io 
      x1052_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1052_ctrchain_p", (x1052_ctrchain.par, x1052_ctrchain.widths))
      val x1086_outr_Foreach = new x1086_outr_Foreach_kernel(List(x1021_fifo), List(x1022), List(x1017_tileB_sram_0) ,  Some(me), List(x1052_ctrchain), 1, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1086_outr_Foreach.sm.io.ctrDone := (x1086_outr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1086_outr_Foreach.backpressure := true.B | x1086_outr_Foreach.sm.io.doneLatch
      x1086_outr_Foreach.forwardpressure := (true.B) && (true.B) | x1086_outr_Foreach.sm.io.doneLatch
      x1086_outr_Foreach.sm.io.enableOut.zip(x1086_outr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1086_outr_Foreach.sm.io.break := false.B
      x1086_outr_Foreach.mask := ~x1086_outr_Foreach.cchain.head.output.noop & true.B
      x1086_outr_Foreach.configure("x1086_outr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1086_outr_Foreach.kernel()
    }
    val module = Module(new x1087_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x1087_outr_UnitPipe_DenseTransfer **/
