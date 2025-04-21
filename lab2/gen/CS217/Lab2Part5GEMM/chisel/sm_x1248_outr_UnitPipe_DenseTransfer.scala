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

/** Hierarchy: x1248 -> x1335 -> x1250 -> x1251 -> x810 **/
/** BEGIN Some(DenseTransfer) x1248_outr_UnitPipe_DenseTransfer **/
class x1248_outr_UnitPipe_DenseTransfer_kernel(
  list_b924: List[FixedPoint],
  list_x1189: List[DecoupledIO[AppCommandDense]],
  list_x1190: List[DecoupledIO[AppStoreData]],
  list_x1191: List[DecoupledIO[Bool]],
  list_x879_N: List[UInt],
  list_x927_reg: List[NBufInterface],
  list_b1005: List[Bool],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 1, isFSM = false   , latency = 0.0.toInt, myName = "x1248_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1248_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x1248_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x1190 = Decoupled(new AppStoreData(ModuleParams.getParams("x1190_p").asInstanceOf[(Int,Int)] ))
      val in_b1005 = Input(Bool())
      val in_x1191 = Flipped(Decoupled(Bool()))
      val in_b924 = Input(new FixedPoint(true, 32, 0))
      val in_b925 = Input(Bool())
      val in_x879_N = Input(UInt(64.W))
      val in_x906_c = Input(new FixedPoint(true, 64, 0))
      val in_x927_reg = Flipped(new NBufInterface(ModuleParams.getParams("x927_reg_p").asInstanceOf[NBufParams] ))
      val in_x1018_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_x1189 = Decoupled(new AppCommandDense(ModuleParams.getParams("x1189_p").asInstanceOf[(Int,Int)] ))
      val in_x1010_reg = Flipped(new NBufInterface(ModuleParams.getParams("x1010_reg_p").asInstanceOf[NBufParams] ))
      val in_b914 = Input(Bool())
      val in_b1004 = Input(new FixedPoint(true, 32, 0))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x1190 = {io.in_x1190} 
    def b1005 = {io.in_b1005} 
    def x1191 = {io.in_x1191} 
    def b924 = {io.in_b924} 
    def b925 = {io.in_b925} 
    def x879_N = {io.in_x879_N} 
    def x906_c = {io.in_x906_c} 
    def x927_reg = {io.in_x927_reg} ; io.in_x927_reg := DontCare
    def x1018_tileC_sram_1 = {io.in_x1018_tileC_sram_1} ; io.in_x1018_tileC_sram_1 := DontCare
    def x1189 = {io.in_x1189} 
    def x1010_reg = {io.in_x1010_reg} ; io.in_x1010_reg := DontCare
    def b914 = {io.in_b914} 
    def b1004 = {io.in_b1004} 
  }
  def connectWires0(module: x1248_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x1190 <> x1190
    module.io.in_b1005 <> b1005
    module.io.in_x1191 <> x1191
    module.io.in_b924 <> b924
    module.io.in_b925 <> b925
    module.io.in_x879_N <> x879_N
    module.io.in_x906_c <> x906_c
    x927_reg.connectLedger(module.io.in_x927_reg)
    x1018_tileC_sram_1.connectLedger(module.io.in_x1018_tileC_sram_1)
    module.io.in_x1189 <> x1189
    x1010_reg.connectLedger(module.io.in_x1010_reg)
    module.io.in_b914 <> b914
    module.io.in_b1004 <> b1004
  }
  val b924 = list_b924(0)
  val x906_c = list_b924(1)
  val b1004 = list_b924(2)
  val x1189 = list_x1189(0)
  val x1190 = list_x1190(0)
  val x1191 = list_x1191(0)
  val x879_N = list_x879_N(0)
  val x927_reg = list_x927_reg(0)
  val x1018_tileC_sram_1 = list_x927_reg(1)
  val x1010_reg = list_x927_reg(2)
  val b1005 = list_b1005(0)
  val b925 = list_b1005(1)
  val b914 = list_b1005(2)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1248_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x1248_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1248_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1248_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      val iters_x1248_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      cycles_x1248_outr_UnitPipe_DenseTransfer.io.enable := io.sigsIn.baseEn
      iters_x1248_outr_UnitPipe_DenseTransfer.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1248_instrctr, cycles_x1248_outr_UnitPipe_DenseTransfer.io.count, iters_x1248_outr_UnitPipe_DenseTransfer.io.count, 0.U, 0.U)
      val x1320_rd_x927 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1320_rd_x927""")
      val x1320_rd_x927_banks = List[UInt]()
      val x1320_rd_x927_ofs = List[UInt]()
      val x1320_rd_x927_en = List[Bool](true.B)
      val x1320_rd_x927_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1320_rd_x927_shared_en")
      x1320_rd_x927.toSeq.zip(x927_reg.connectRPort(1320, x1320_rd_x927_banks, x1320_rd_x927_ofs, io.sigsIn.backpressure, x1320_rd_x927_en.map(_ && x1320_rd_x927_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1192_ctr = new CtrObject(Left(Some(0)), Right(x1320_rd_x927), Left(Some(1)), 1, 32, false)
      val x1193_ctrchain = (new CChainObject(List[CtrObject](x1192_ctr), "x1193_ctrchain")).cchain.io 
      x1193_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1193_ctrchain_p", (x1193_ctrchain.par, x1193_ctrchain.widths))
      val x1247_outr_Foreach = new x1247_outr_Foreach_kernel(List(b924,x906_c,b1004), List(x1189), List(x1190), List(x1191), List(x1018_tileC_sram_1,x1010_reg), List(x879_N) ,  Some(me), List(x1193_ctrchain), 0, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1247_outr_Foreach.sm.io.ctrDone := (x1247_outr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1247_outr_Foreach.backpressure := true.B | x1247_outr_Foreach.sm.io.doneLatch
      x1247_outr_Foreach.forwardpressure := (true.B) && (true.B) | x1247_outr_Foreach.sm.io.doneLatch
      x1247_outr_Foreach.sm.io.enableOut.zip(x1247_outr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1247_outr_Foreach.sm.io.break := false.B
      x1247_outr_Foreach.mask := ~x1247_outr_Foreach.cchain.head.output.noop & true.B
      x1247_outr_Foreach.configure("x1247_outr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1247_outr_Foreach.kernel()
      x1010_reg.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 3)
      x1018_tileC_sram_1.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 2)
    }
    val module = Module(new x1248_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x1248_outr_UnitPipe_DenseTransfer **/
