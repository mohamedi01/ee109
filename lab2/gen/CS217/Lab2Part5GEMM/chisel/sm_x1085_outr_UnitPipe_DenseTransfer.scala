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

/** Hierarchy: x1085 -> x1334 -> x1335 -> x1250 -> x1251 -> x810 **/
/** BEGIN Some(DenseTransfer) x1085_outr_UnitPipe_DenseTransfer **/
class x1085_outr_UnitPipe_DenseTransfer_kernel(
  list_x1021: List[DecoupledIO[AppLoadData]],
  list_x1019: List[DecoupledIO[AppCommandDense]],
  list_x916_reg: List[NBufInterface],
  list_b913: List[FixedPoint],
  list_x879_N: List[UInt],
  list_b1005: List[Bool],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 2, isFSM = false   , latency = 0.0.toInt, myName = "x1085_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1085_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x1085_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b1005 = Input(Bool())
      val in_b913 = Input(new FixedPoint(true, 32, 0))
      val in_b925 = Input(Bool())
      val in_x1021 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x1021_p").asInstanceOf[(Int, Int)] )))
      val in_x916_reg = Flipped(new NBufInterface(ModuleParams.getParams("x916_reg_p").asInstanceOf[NBufParams] ))
      val in_x879_N = Input(UInt(64.W))
      val in_x1016_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1016_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_x903_b = Input(new FixedPoint(true, 64, 0))
      val in_x1010_reg = Flipped(new NBufInterface(ModuleParams.getParams("x1010_reg_p").asInstanceOf[NBufParams] ))
      val in_b914 = Input(Bool())
      val in_x1019 = Decoupled(new AppCommandDense(ModuleParams.getParams("x1019_p").asInstanceOf[(Int,Int)] ))
      val in_b1004 = Input(new FixedPoint(true, 32, 0))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 2, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 2))
      val rr = Input(Bool())
    })
    def b1005 = {io.in_b1005} 
    def b913 = {io.in_b913} 
    def b925 = {io.in_b925} 
    def x1021 = {io.in_x1021} 
    def x916_reg = {io.in_x916_reg} ; io.in_x916_reg := DontCare
    def x879_N = {io.in_x879_N} 
    def x1016_tileB_sram_0 = {io.in_x1016_tileB_sram_0} ; io.in_x1016_tileB_sram_0 := DontCare
    def x903_b = {io.in_x903_b} 
    def x1010_reg = {io.in_x1010_reg} ; io.in_x1010_reg := DontCare
    def b914 = {io.in_b914} 
    def x1019 = {io.in_x1019} 
    def b1004 = {io.in_b1004} 
  }
  def connectWires0(module: x1085_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b1005 <> b1005
    module.io.in_b913 <> b913
    module.io.in_b925 <> b925
    module.io.in_x1021 <> x1021
    x916_reg.connectLedger(module.io.in_x916_reg)
    module.io.in_x879_N <> x879_N
    x1016_tileB_sram_0.connectLedger(module.io.in_x1016_tileB_sram_0)
    module.io.in_x903_b <> x903_b
    x1010_reg.connectLedger(module.io.in_x1010_reg)
    module.io.in_b914 <> b914
    module.io.in_x1019 <> x1019
    module.io.in_b1004 <> b1004
  }
  val x1021 = list_x1021(0)
  val x1019 = list_x1019(0)
  val x916_reg = list_x916_reg(0)
  val x1016_tileB_sram_0 = list_x916_reg(1)
  val x1010_reg = list_x916_reg(2)
  val b913 = list_b913(0)
  val x903_b = list_b913(1)
  val b1004 = list_b913(2)
  val x879_N = list_x879_N(0)
  val b1005 = list_b1005(0)
  val b925 = list_b1005(1)
  val b914 = list_b1005(2)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1085_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x1085_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1085_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1085_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      val iters_x1085_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      cycles_x1085_outr_UnitPipe_DenseTransfer.io.enable := io.sigsIn.baseEn
      iters_x1085_outr_UnitPipe_DenseTransfer.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1085_instrctr, cycles_x1085_outr_UnitPipe_DenseTransfer.io.count, iters_x1085_outr_UnitPipe_DenseTransfer.io.count, 0.U, 0.U)
      val x1020_fifo = (new x1020_fifo).m.io.asInstanceOf[FIFOInterface]
      val x1311_rd_x916 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1311_rd_x916""")
      val x1311_rd_x916_banks = List[UInt]()
      val x1311_rd_x916_ofs = List[UInt]()
      val x1311_rd_x916_en = List[Bool](true.B)
      val x1311_rd_x916_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1311_rd_x916_shared_en")
      x1311_rd_x916.toSeq.zip(x916_reg.connectRPort(1311, x1311_rd_x916_banks, x1311_rd_x916_ofs, io.sigsIn.backpressure, x1311_rd_x916_en.map(_ && x1311_rd_x916_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1022_ctr = new CtrObject(Left(Some(0)), Right(x1311_rd_x916), Left(Some(1)), 1, 32, false)
      val x1023_ctrchain = (new CChainObject(List[CtrObject](x1022_ctr), "x1023_ctrchain")).cchain.io 
      x1023_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1023_ctrchain_p", (x1023_ctrchain.par, x1023_ctrchain.widths))
      val x1048_inr_Foreach = new x1048_inr_Foreach_kernel(List(x1019), List(b913,x903_b,b1004), List(x879_N), List(x1020_fifo), List(x1010_reg) ,  Some(me), List(x1023_ctrchain), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1048_inr_Foreach.sm.io.ctrDone := (x1048_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1048_inr_Foreach.backpressure := x1019.ready & (~x1020_fifo.full.D(11.8-1) | ~(x1020_fifo.active(0).out)) | x1048_inr_Foreach.sm.io.doneLatch
      x1048_inr_Foreach.forwardpressure := (true.B) && (true.B) | x1048_inr_Foreach.sm.io.doneLatch
      x1048_inr_Foreach.sm.io.enableOut.zip(x1048_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1048_inr_Foreach.sm.io.break := false.B
      x1048_inr_Foreach.mask := ~x1048_inr_Foreach.cchain.head.output.noop & true.B
      x1048_inr_Foreach.configure("x1048_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1048_inr_Foreach.kernel()
      val x1312_rd_x916 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1312_rd_x916""")
      val x1312_rd_x916_banks = List[UInt]()
      val x1312_rd_x916_ofs = List[UInt]()
      val x1312_rd_x916_en = List[Bool](true.B)
      val x1312_rd_x916_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1312_rd_x916_shared_en")
      x1312_rd_x916.toSeq.zip(x916_reg.connectRPort(1312, x1312_rd_x916_banks, x1312_rd_x916_ofs, io.sigsIn.backpressure, x1312_rd_x916_en.map(_ && x1312_rd_x916_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1050_ctr = new CtrObject(Left(Some(0)), Right(x1312_rd_x916), Left(Some(1)), 1, 32, false)
      val x1051_ctrchain = (new CChainObject(List[CtrObject](x1050_ctr), "x1051_ctrchain")).cchain.io 
      x1051_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1051_ctrchain_p", (x1051_ctrchain.par, x1051_ctrchain.widths))
      val x1084_outr_Foreach = new x1084_outr_Foreach_kernel(List(x1020_fifo), List(x1021), List(x1016_tileB_sram_0) ,  Some(me), List(x1051_ctrchain), 1, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1084_outr_Foreach.sm.io.ctrDone := (x1084_outr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1084_outr_Foreach.backpressure := true.B | x1084_outr_Foreach.sm.io.doneLatch
      x1084_outr_Foreach.forwardpressure := (true.B) && (true.B) | x1084_outr_Foreach.sm.io.doneLatch
      x1084_outr_Foreach.sm.io.enableOut.zip(x1084_outr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1084_outr_Foreach.sm.io.break := false.B
      x1084_outr_Foreach.mask := ~x1084_outr_Foreach.cchain.head.output.noop & true.B
      x1084_outr_Foreach.configure("x1084_outr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1084_outr_Foreach.kernel()
    }
    val module = Module(new x1085_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x1085_outr_UnitPipe_DenseTransfer **/
