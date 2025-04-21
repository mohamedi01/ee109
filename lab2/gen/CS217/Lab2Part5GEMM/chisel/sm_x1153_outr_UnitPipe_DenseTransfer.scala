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

/** Hierarchy: x1153 -> x1334 -> x1335 -> x1250 -> x1251 -> x810 **/
/** BEGIN Some(DenseTransfer) x1153_outr_UnitPipe_DenseTransfer **/
class x1153_outr_UnitPipe_DenseTransfer_kernel(
  list_x1088: List[DecoupledIO[AppLoadData]],
  list_x1086: List[DecoupledIO[AppCommandDense]],
  list_b924: List[FixedPoint],
  list_x879_N: List[UInt],
  list_x1017_tileC_sram_0: List[NBufInterface],
  list_b1005: List[Bool],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 2, isFSM = false   , latency = 0.0.toInt, myName = "x1153_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1153_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x1153_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b1005 = Input(Bool())
      val in_b924 = Input(new FixedPoint(true, 32, 0))
      val in_b925 = Input(Bool())
      val in_x879_N = Input(UInt(64.W))
      val in_x1017_tileC_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileC_sram_0_p").asInstanceOf[NBufParams] ))
      val in_x1086 = Decoupled(new AppCommandDense(ModuleParams.getParams("x1086_p").asInstanceOf[(Int,Int)] ))
      val in_x906_c = Input(new FixedPoint(true, 64, 0))
      val in_x927_reg = Flipped(new NBufInterface(ModuleParams.getParams("x927_reg_p").asInstanceOf[NBufParams] ))
      val in_x1018_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_x1010_reg = Flipped(new NBufInterface(ModuleParams.getParams("x1010_reg_p").asInstanceOf[NBufParams] ))
      val in_b914 = Input(Bool())
      val in_b1004 = Input(new FixedPoint(true, 32, 0))
      val in_x1088 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x1088_p").asInstanceOf[(Int, Int)] )))
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
    def x1017_tileC_sram_0 = {io.in_x1017_tileC_sram_0} ; io.in_x1017_tileC_sram_0 := DontCare
    def x1086 = {io.in_x1086} 
    def x906_c = {io.in_x906_c} 
    def x927_reg = {io.in_x927_reg} ; io.in_x927_reg := DontCare
    def x1018_tileC_sram_1 = {io.in_x1018_tileC_sram_1} ; io.in_x1018_tileC_sram_1 := DontCare
    def x1010_reg = {io.in_x1010_reg} ; io.in_x1010_reg := DontCare
    def b914 = {io.in_b914} 
    def b1004 = {io.in_b1004} 
    def x1088 = {io.in_x1088} 
  }
  def connectWires0(module: x1153_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b1005 <> b1005
    module.io.in_b924 <> b924
    module.io.in_b925 <> b925
    module.io.in_x879_N <> x879_N
    x1017_tileC_sram_0.connectLedger(module.io.in_x1017_tileC_sram_0)
    module.io.in_x1086 <> x1086
    module.io.in_x906_c <> x906_c
    x927_reg.connectLedger(module.io.in_x927_reg)
    x1018_tileC_sram_1.connectLedger(module.io.in_x1018_tileC_sram_1)
    x1010_reg.connectLedger(module.io.in_x1010_reg)
    module.io.in_b914 <> b914
    module.io.in_b1004 <> b1004
    module.io.in_x1088 <> x1088
  }
  val x1088 = list_x1088(0)
  val x1086 = list_x1086(0)
  val b924 = list_b924(0)
  val x906_c = list_b924(1)
  val b1004 = list_b924(2)
  val x879_N = list_x879_N(0)
  val x1017_tileC_sram_0 = list_x1017_tileC_sram_0(0)
  val x927_reg = list_x1017_tileC_sram_0(1)
  val x1018_tileC_sram_1 = list_x1017_tileC_sram_0(2)
  val x1010_reg = list_x1017_tileC_sram_0(3)
  val b1005 = list_b1005(0)
  val b925 = list_b1005(1)
  val b914 = list_b1005(2)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1153_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x1153_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1153_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1153_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      val iters_x1153_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      cycles_x1153_outr_UnitPipe_DenseTransfer.io.enable := io.sigsIn.baseEn
      iters_x1153_outr_UnitPipe_DenseTransfer.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1153_instrctr, cycles_x1153_outr_UnitPipe_DenseTransfer.io.count, iters_x1153_outr_UnitPipe_DenseTransfer.io.count, 0.U, 0.U)
      val x1087_fifo = (new x1087_fifo).m.io.asInstanceOf[FIFOInterface]
      val x1314_rd_x927 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1314_rd_x927""")
      val x1314_rd_x927_banks = List[UInt]()
      val x1314_rd_x927_ofs = List[UInt]()
      val x1314_rd_x927_en = List[Bool](true.B)
      val x1314_rd_x927_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1314_rd_x927_shared_en")
      x1314_rd_x927.toSeq.zip(x927_reg.connectRPort(1314, x1314_rd_x927_banks, x1314_rd_x927_ofs, io.sigsIn.backpressure, x1314_rd_x927_en.map(_ && x1314_rd_x927_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1089_ctr = new CtrObject(Left(Some(0)), Right(x1314_rd_x927), Left(Some(1)), 1, 32, false)
      val x1090_ctrchain = (new CChainObject(List[CtrObject](x1089_ctr), "x1090_ctrchain")).cchain.io 
      x1090_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1090_ctrchain_p", (x1090_ctrchain.par, x1090_ctrchain.widths))
      val x1115_inr_Foreach = new x1115_inr_Foreach_kernel(List(x1086), List(b924,x906_c,b1004), List(x1087_fifo), List(x879_N), List(x1010_reg) ,  Some(me), List(x1090_ctrchain), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1115_inr_Foreach.sm.io.ctrDone := (x1115_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1115_inr_Foreach.backpressure := x1086.ready & (~x1087_fifo.full.D(11.8-1) | ~(x1087_fifo.active(0).out)) | x1115_inr_Foreach.sm.io.doneLatch
      x1115_inr_Foreach.forwardpressure := (true.B) && (true.B) | x1115_inr_Foreach.sm.io.doneLatch
      x1115_inr_Foreach.sm.io.enableOut.zip(x1115_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1115_inr_Foreach.sm.io.break := false.B
      x1115_inr_Foreach.mask := ~x1115_inr_Foreach.cchain.head.output.noop & true.B
      x1115_inr_Foreach.configure("x1115_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1115_inr_Foreach.kernel()
      val x1315_rd_x927 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1315_rd_x927""")
      val x1315_rd_x927_banks = List[UInt]()
      val x1315_rd_x927_ofs = List[UInt]()
      val x1315_rd_x927_en = List[Bool](true.B)
      val x1315_rd_x927_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1315_rd_x927_shared_en")
      x1315_rd_x927.toSeq.zip(x927_reg.connectRPort(1315, x1315_rd_x927_banks, x1315_rd_x927_ofs, io.sigsIn.backpressure, x1315_rd_x927_en.map(_ && x1315_rd_x927_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1117_ctr = new CtrObject(Left(Some(0)), Right(x1315_rd_x927), Left(Some(1)), 1, 32, false)
      val x1118_ctrchain = (new CChainObject(List[CtrObject](x1117_ctr), "x1118_ctrchain")).cchain.io 
      x1118_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1118_ctrchain_p", (x1118_ctrchain.par, x1118_ctrchain.widths))
      val x1152_outr_Foreach = new x1152_outr_Foreach_kernel(List(x1087_fifo), List(x1088), List(x1017_tileC_sram_0,x1018_tileC_sram_1) ,  Some(me), List(x1118_ctrchain), 1, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1152_outr_Foreach.sm.io.ctrDone := (x1152_outr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1152_outr_Foreach.backpressure := true.B | x1152_outr_Foreach.sm.io.doneLatch
      x1152_outr_Foreach.forwardpressure := (true.B) && (true.B) | x1152_outr_Foreach.sm.io.doneLatch
      x1152_outr_Foreach.sm.io.enableOut.zip(x1152_outr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1152_outr_Foreach.sm.io.break := false.B
      x1152_outr_Foreach.mask := ~x1152_outr_Foreach.cchain.head.output.noop & true.B
      x1152_outr_Foreach.configure("x1152_outr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1152_outr_Foreach.kernel()
    }
    val module = Module(new x1153_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x1153_outr_UnitPipe_DenseTransfer **/
