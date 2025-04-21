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

/** Hierarchy: x1334 -> x1335 -> x1250 -> x1251 -> x810 **/
/** BEGIN None x1334 **/
class x1334_kernel(
  list_x1021: List[DecoupledIO[AppLoadData]],
  list_x916_reg: List[NBufInterface],
  list_x879_N: List[UInt],
  list_b913: List[FixedPoint],
  list_x1086: List[DecoupledIO[AppCommandDense]],
  list_b1005: List[Bool],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(ForkJoin, 2, isFSM = false   , latency = 0.0.toInt, myName = "x1334_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1334_iiCtr"))
  
  abstract class x1334_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b1005 = Input(Bool())
      val in_b913 = Input(new FixedPoint(true, 32, 0))
      val in_b924 = Input(new FixedPoint(true, 32, 0))
      val in_b925 = Input(Bool())
      val in_x1021 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x1021_p").asInstanceOf[(Int, Int)] )))
      val in_x916_reg = Flipped(new NBufInterface(ModuleParams.getParams("x916_reg_p").asInstanceOf[NBufParams] ))
      val in_x879_N = Input(UInt(64.W))
      val in_x1017_tileC_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileC_sram_0_p").asInstanceOf[NBufParams] ))
      val in_x1016_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1016_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_x1086 = Decoupled(new AppCommandDense(ModuleParams.getParams("x1086_p").asInstanceOf[(Int,Int)] ))
      val in_x906_c = Input(new FixedPoint(true, 64, 0))
      val in_x927_reg = Flipped(new NBufInterface(ModuleParams.getParams("x927_reg_p").asInstanceOf[NBufParams] ))
      val in_x1018_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_x903_b = Input(new FixedPoint(true, 64, 0))
      val in_x1010_reg = Flipped(new NBufInterface(ModuleParams.getParams("x1010_reg_p").asInstanceOf[NBufParams] ))
      val in_b914 = Input(Bool())
      val in_x1019 = Decoupled(new AppCommandDense(ModuleParams.getParams("x1019_p").asInstanceOf[(Int,Int)] ))
      val in_b1004 = Input(new FixedPoint(true, 32, 0))
      val in_x1088 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x1088_p").asInstanceOf[(Int, Int)] )))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def b1005 = {io.in_b1005} 
    def b913 = {io.in_b913} 
    def b924 = {io.in_b924} 
    def b925 = {io.in_b925} 
    def x1021 = {io.in_x1021} 
    def x916_reg = {io.in_x916_reg} ; io.in_x916_reg := DontCare
    def x879_N = {io.in_x879_N} 
    def x1017_tileC_sram_0 = {io.in_x1017_tileC_sram_0} ; io.in_x1017_tileC_sram_0 := DontCare
    def x1016_tileB_sram_0 = {io.in_x1016_tileB_sram_0} ; io.in_x1016_tileB_sram_0 := DontCare
    def x1086 = {io.in_x1086} 
    def x906_c = {io.in_x906_c} 
    def x927_reg = {io.in_x927_reg} ; io.in_x927_reg := DontCare
    def x1018_tileC_sram_1 = {io.in_x1018_tileC_sram_1} ; io.in_x1018_tileC_sram_1 := DontCare
    def x903_b = {io.in_x903_b} 
    def x1010_reg = {io.in_x1010_reg} ; io.in_x1010_reg := DontCare
    def b914 = {io.in_b914} 
    def x1019 = {io.in_x1019} 
    def b1004 = {io.in_b1004} 
    def x1088 = {io.in_x1088} 
  }
  def connectWires0(module: x1334_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b1005 <> b1005
    module.io.in_b913 <> b913
    module.io.in_b924 <> b924
    module.io.in_b925 <> b925
    module.io.in_x1021 <> x1021
    x916_reg.connectLedger(module.io.in_x916_reg)
    module.io.in_x879_N <> x879_N
    x1017_tileC_sram_0.connectLedger(module.io.in_x1017_tileC_sram_0)
    x1016_tileB_sram_0.connectLedger(module.io.in_x1016_tileB_sram_0)
    module.io.in_x1086 <> x1086
    module.io.in_x906_c <> x906_c
    x927_reg.connectLedger(module.io.in_x927_reg)
    x1018_tileC_sram_1.connectLedger(module.io.in_x1018_tileC_sram_1)
    module.io.in_x903_b <> x903_b
    x1010_reg.connectLedger(module.io.in_x1010_reg)
    module.io.in_b914 <> b914
    module.io.in_x1019 <> x1019
    module.io.in_b1004 <> b1004
    module.io.in_x1088 <> x1088
  }
  val x1021 = list_x1021(0)
  val x1088 = list_x1021(1)
  val x916_reg = list_x916_reg(0)
  val x1017_tileC_sram_0 = list_x916_reg(1)
  val x1016_tileB_sram_0 = list_x916_reg(2)
  val x927_reg = list_x916_reg(3)
  val x1018_tileC_sram_1 = list_x916_reg(4)
  val x1010_reg = list_x916_reg(5)
  val x879_N = list_x879_N(0)
  val b913 = list_b913(0)
  val b924 = list_b913(1)
  val x906_c = list_b913(2)
  val x903_b = list_b913(3)
  val b1004 = list_b913(4)
  val x1086 = list_x1086(0)
  val x1019 = list_x1086(1)
  val b1005 = list_b1005(0)
  val b925 = list_b1005(1)
  val b914 = list_b1005(2)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1334")
    implicit val stack = ControllerStack.stack.toList
    class x1334_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1334_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1334 = Module(new InstrumentationCounter())
      val iters_x1334 = Module(new InstrumentationCounter())
      cycles_x1334.io.enable := io.sigsIn.baseEn
      iters_x1334.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1334_instrctr, cycles_x1334.io.count, iters_x1334.io.count, 0.U, 0.U)
      val x1085_outr_UnitPipe_DenseTransfer = new x1085_outr_UnitPipe_DenseTransfer_kernel(List(x1021), List(x1019), List(x916_reg,x1016_tileB_sram_0,x1010_reg), List(b913,x903_b,b1004), List(x879_N), List(b1005,b925,b914) ,  Some(me), List(), 0, 2, 2, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1085_outr_UnitPipe_DenseTransfer.backpressure := true.B | x1085_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x1085_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x1085_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x1085_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x1085_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x1085_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x1085_outr_UnitPipe_DenseTransfer.mask := true.B & b1005 & b925 & b914
      x1085_outr_UnitPipe_DenseTransfer.configure("x1085_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1085_outr_UnitPipe_DenseTransfer.kernel()
      val x1153_outr_UnitPipe_DenseTransfer = new x1153_outr_UnitPipe_DenseTransfer_kernel(List(x1088), List(x1086), List(b924,x906_c,b1004), List(x879_N), List(x1017_tileC_sram_0,x927_reg,x1018_tileC_sram_1,x1010_reg), List(b1005,b925,b914) ,  Some(me), List(), 1, 2, 2, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1153_outr_UnitPipe_DenseTransfer.backpressure := true.B | x1153_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x1153_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x1153_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x1153_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x1153_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x1153_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x1153_outr_UnitPipe_DenseTransfer.mask := true.B & b1005 & b925 & b914
      x1153_outr_UnitPipe_DenseTransfer.configure("x1153_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1153_outr_UnitPipe_DenseTransfer.kernel()
      x1010_reg.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 1)
      x1016_tileB_sram_0.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 0)
      x1017_tileC_sram_0.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 0)
      x1018_tileC_sram_1.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 0)
    }
    val module = Module(new x1334_concrete(sm.p.depth)); module.io := DontCare
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
/** END ParallelPipe x1334 **/
