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

/** Hierarchy: x1811 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1811 **/
class x1811_kernel(
  list_b1005: List[FixedPoint],
  list_x916_reg: List[NBufInterface],
  list_x1020: List[DecoupledIO[AppCommandDense]],
  list_x879_N: List[UInt],
  list_x1090: List[DecoupledIO[AppLoadData]],
  list_b925: List[Bool],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(ForkJoin, 2, isFSM = false   , latency = 0.0.toInt, myName = "x1811_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1811_iiCtr"))
  
  abstract class x1811_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b1005 = Input(new FixedPoint(true, 32, 0))
      val in_b913 = Input(new FixedPoint(true, 32, 0))
      val in_b924 = Input(new FixedPoint(true, 32, 0))
      val in_x1020 = Decoupled(new AppCommandDense(ModuleParams.getParams("x1020_p").asInstanceOf[(Int,Int)] ))
      val in_b925 = Input(Bool())
      val in_x916_reg = Flipped(new NBufInterface(ModuleParams.getParams("x916_reg_p").asInstanceOf[NBufParams] ))
      val in_x879_N = Input(UInt(64.W))
      val in_x1017_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1006 = Input(Bool())
      val in_x906_c = Input(new FixedPoint(true, 64, 0))
      val in_x1090 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x1090_p").asInstanceOf[(Int, Int)] )))
      val in_x1011_reg = Flipped(new NBufInterface(ModuleParams.getParams("x1011_reg_p").asInstanceOf[NBufParams] ))
      val in_x1022 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x1022_p").asInstanceOf[(Int, Int)] )))
      val in_x927_reg = Flipped(new NBufInterface(ModuleParams.getParams("x927_reg_p").asInstanceOf[NBufParams] ))
      val in_x1018_tileC_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_0_p").asInstanceOf[NBufParams] ))
      val in_x903_b = Input(new FixedPoint(true, 64, 0))
      val in_b914 = Input(Bool())
      val in_x1019_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1019_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_x1088 = Decoupled(new AppCommandDense(ModuleParams.getParams("x1088_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def b1005 = {io.in_b1005} 
    def b913 = {io.in_b913} 
    def b924 = {io.in_b924} 
    def x1020 = {io.in_x1020} 
    def b925 = {io.in_b925} 
    def x916_reg = {io.in_x916_reg} ; io.in_x916_reg := DontCare
    def x879_N = {io.in_x879_N} 
    def x1017_tileB_sram_0 = {io.in_x1017_tileB_sram_0} ; io.in_x1017_tileB_sram_0 := DontCare
    def b1006 = {io.in_b1006} 
    def x906_c = {io.in_x906_c} 
    def x1090 = {io.in_x1090} 
    def x1011_reg = {io.in_x1011_reg} ; io.in_x1011_reg := DontCare
    def x1022 = {io.in_x1022} 
    def x927_reg = {io.in_x927_reg} ; io.in_x927_reg := DontCare
    def x1018_tileC_sram_0 = {io.in_x1018_tileC_sram_0} ; io.in_x1018_tileC_sram_0 := DontCare
    def x903_b = {io.in_x903_b} 
    def b914 = {io.in_b914} 
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
    def x1088 = {io.in_x1088} 
  }
  def connectWires0(module: x1811_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b1005 <> b1005
    module.io.in_b913 <> b913
    module.io.in_b924 <> b924
    module.io.in_x1020 <> x1020
    module.io.in_b925 <> b925
    x916_reg.connectLedger(module.io.in_x916_reg)
    module.io.in_x879_N <> x879_N
    x1017_tileB_sram_0.connectLedger(module.io.in_x1017_tileB_sram_0)
    module.io.in_b1006 <> b1006
    module.io.in_x906_c <> x906_c
    module.io.in_x1090 <> x1090
    x1011_reg.connectLedger(module.io.in_x1011_reg)
    module.io.in_x1022 <> x1022
    x927_reg.connectLedger(module.io.in_x927_reg)
    x1018_tileC_sram_0.connectLedger(module.io.in_x1018_tileC_sram_0)
    module.io.in_x903_b <> x903_b
    module.io.in_b914 <> b914
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
    module.io.in_x1088 <> x1088
  }
  val b1005 = list_b1005(0)
  val b913 = list_b1005(1)
  val b924 = list_b1005(2)
  val x906_c = list_b1005(3)
  val x903_b = list_b1005(4)
  val x916_reg = list_x916_reg(0)
  val x1017_tileB_sram_0 = list_x916_reg(1)
  val x1011_reg = list_x916_reg(2)
  val x927_reg = list_x916_reg(3)
  val x1018_tileC_sram_0 = list_x916_reg(4)
  val x1019_tileC_sram_1 = list_x916_reg(5)
  val x1020 = list_x1020(0)
  val x1088 = list_x1020(1)
  val x879_N = list_x879_N(0)
  val x1090 = list_x1090(0)
  val x1022 = list_x1090(1)
  val b925 = list_b925(0)
  val b1006 = list_b925(1)
  val b914 = list_b925(2)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1811")
    implicit val stack = ControllerStack.stack.toList
    class x1811_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1811_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1811 = Module(new InstrumentationCounter())
      val iters_x1811 = Module(new InstrumentationCounter())
      cycles_x1811.io.enable := io.sigsIn.baseEn
      iters_x1811.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1811_instrctr, cycles_x1811.io.count, iters_x1811.io.count, 0.U, 0.U)
      val x1087_outr_UnitPipe_DenseTransfer = new x1087_outr_UnitPipe_DenseTransfer_kernel(List(b1005,b913,x903_b), List(x916_reg,x1017_tileB_sram_0,x1011_reg), List(x879_N), List(x1020), List(b925,b1006,b914), List(x1022) ,  Some(me), List(), 0, 2, 2, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1087_outr_UnitPipe_DenseTransfer.backpressure := true.B | x1087_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x1087_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x1087_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x1087_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x1087_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x1087_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x1087_outr_UnitPipe_DenseTransfer.mask := true.B & b1006 & b925 & b914
      x1087_outr_UnitPipe_DenseTransfer.configure("x1087_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1087_outr_UnitPipe_DenseTransfer.kernel()
      val x1158_outr_UnitPipe_DenseTransfer = new x1158_outr_UnitPipe_DenseTransfer_kernel(List(x1090), List(x1088), List(x1011_reg,x927_reg,x1018_tileC_sram_0,x1019_tileC_sram_1), List(x879_N), List(b1005,b924,x906_c), List(b925,b1006,b914) ,  Some(me), List(), 1, 2, 2, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1158_outr_UnitPipe_DenseTransfer.backpressure := true.B | x1158_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x1158_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x1158_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x1158_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x1158_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x1158_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x1158_outr_UnitPipe_DenseTransfer.mask := true.B & b1006 & b925 & b914
      x1158_outr_UnitPipe_DenseTransfer.configure("x1158_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1158_outr_UnitPipe_DenseTransfer.kernel()
      x1011_reg.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 1)
      x1017_tileB_sram_0.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 0)
      x1018_tileC_sram_0.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 0)
      x1019_tileC_sram_1.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 0)
    }
    val module = Module(new x1811_concrete(sm.p.depth)); module.io := DontCare
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
/** END ParallelPipe x1811 **/
