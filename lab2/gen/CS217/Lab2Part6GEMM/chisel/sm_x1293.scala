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

/** Hierarchy: x1293 -> x1294 -> x1637 -> x1638 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1293 **/
class x1293_kernel(
  list_b925: List[Bool],
  list_b1183: List[FixedPoint],
  list_x1200_ctrchain: List[CounterChainInterface],
  list_x934_tileA_sram_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(ForkJoin, 4, isFSM = false   , latency = 0.0.toInt, myName = "x1293_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1293_iiCtr"))
  
  abstract class x1293_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x1200_ctrchain = Flipped(new CounterChainInterface(ModuleParams.getParams("x1200_ctrchain_p").asInstanceOf[(List[Int],List[Int])] ))
      val in_x934_tileA_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x934_tileA_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1183 = Input(new FixedPoint(true, 32, 0))
      val in_b925 = Input(Bool())
      val in_x1199_ctrchain = Flipped(new CounterChainInterface(ModuleParams.getParams("x1199_ctrchain_p").asInstanceOf[(List[Int],List[Int])] ))
      val in_x1017_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1006 = Input(Bool())
      val in_b1182 = Input(new FixedPoint(true, 32, 0))
      val in_b1187 = Input(Bool())
      val in_b1185 = Input(Bool())
      val in_x1198_ctrchain = Flipped(new CounterChainInterface(ModuleParams.getParams("x1198_ctrchain_p").asInstanceOf[(List[Int],List[Int])] ))
      val in_x1018_tileC_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1181 = Input(new FixedPoint(true, 32, 0))
      val in_b1188 = Input(Bool())
      val in_b1186 = Input(Bool())
      val in_b914 = Input(Bool())
      val in_b1161 = Input(new FixedPoint(true, 32, 0))
      val in_x1197_ctrchain = Flipped(new CounterChainInterface(ModuleParams.getParams("x1197_ctrchain_p").asInstanceOf[(List[Int],List[Int])] ))
      val in_b1165 = Input(Bool())
      val in_x1019_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1019_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_b1184 = Input(new FixedPoint(true, 32, 0))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(4, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(4, 1))
      val rr = Input(Bool())
    })
    def x1200_ctrchain = {io.in_x1200_ctrchain} ; io.in_x1200_ctrchain := DontCare
    def x934_tileA_sram_0 = {io.in_x934_tileA_sram_0} ; io.in_x934_tileA_sram_0 := DontCare
    def b1183 = {io.in_b1183} 
    def b925 = {io.in_b925} 
    def x1199_ctrchain = {io.in_x1199_ctrchain} ; io.in_x1199_ctrchain := DontCare
    def x1017_tileB_sram_0 = {io.in_x1017_tileB_sram_0} ; io.in_x1017_tileB_sram_0 := DontCare
    def b1006 = {io.in_b1006} 
    def b1182 = {io.in_b1182} 
    def b1187 = {io.in_b1187} 
    def b1185 = {io.in_b1185} 
    def x1198_ctrchain = {io.in_x1198_ctrchain} ; io.in_x1198_ctrchain := DontCare
    def x1018_tileC_sram_0 = {io.in_x1018_tileC_sram_0} ; io.in_x1018_tileC_sram_0 := DontCare
    def b1181 = {io.in_b1181} 
    def b1188 = {io.in_b1188} 
    def b1186 = {io.in_b1186} 
    def b914 = {io.in_b914} 
    def b1161 = {io.in_b1161} 
    def x1197_ctrchain = {io.in_x1197_ctrchain} ; io.in_x1197_ctrchain := DontCare
    def b1165 = {io.in_b1165} 
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
    def b1184 = {io.in_b1184} 
  }
  def connectWires0(module: x1293_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x1200_ctrchain.input <> x1200_ctrchain.input; module.io.in_x1200_ctrchain.output <> x1200_ctrchain.output
    x934_tileA_sram_0.connectLedger(module.io.in_x934_tileA_sram_0)
    module.io.in_b1183 <> b1183
    module.io.in_b925 <> b925
    module.io.in_x1199_ctrchain.input <> x1199_ctrchain.input; module.io.in_x1199_ctrchain.output <> x1199_ctrchain.output
    x1017_tileB_sram_0.connectLedger(module.io.in_x1017_tileB_sram_0)
    module.io.in_b1006 <> b1006
    module.io.in_b1182 <> b1182
    module.io.in_b1187 <> b1187
    module.io.in_b1185 <> b1185
    module.io.in_x1198_ctrchain.input <> x1198_ctrchain.input; module.io.in_x1198_ctrchain.output <> x1198_ctrchain.output
    x1018_tileC_sram_0.connectLedger(module.io.in_x1018_tileC_sram_0)
    module.io.in_b1181 <> b1181
    module.io.in_b1188 <> b1188
    module.io.in_b1186 <> b1186
    module.io.in_b914 <> b914
    module.io.in_b1161 <> b1161
    module.io.in_x1197_ctrchain.input <> x1197_ctrchain.input; module.io.in_x1197_ctrchain.output <> x1197_ctrchain.output
    module.io.in_b1165 <> b1165
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
    module.io.in_b1184 <> b1184
  }
  val b925 = list_b925(0)
  val b1006 = list_b925(1)
  val b1187 = list_b925(2)
  val b1185 = list_b925(3)
  val b1188 = list_b925(4)
  val b1186 = list_b925(5)
  val b914 = list_b925(6)
  val b1165 = list_b925(7)
  val b1183 = list_b1183(0)
  val b1182 = list_b1183(1)
  val b1181 = list_b1183(2)
  val b1161 = list_b1183(3)
  val b1184 = list_b1183(4)
  val x1200_ctrchain = list_x1200_ctrchain(0)
  val x1199_ctrchain = list_x1200_ctrchain(1)
  val x1198_ctrchain = list_x1200_ctrchain(2)
  val x1197_ctrchain = list_x1200_ctrchain(3)
  val x934_tileA_sram_0 = list_x934_tileA_sram_0(0)
  val x1017_tileB_sram_0 = list_x934_tileA_sram_0(1)
  val x1018_tileC_sram_0 = list_x934_tileA_sram_0(2)
  val x1019_tileC_sram_1 = list_x934_tileA_sram_0(3)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1293")
    implicit val stack = ControllerStack.stack.toList
    class x1293_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1293_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1293 = Module(new InstrumentationCounter())
      val iters_x1293 = Module(new InstrumentationCounter())
      cycles_x1293.io.enable := io.sigsIn.baseEn
      iters_x1293.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1293_instrctr, cycles_x1293.io.count, iters_x1293.io.count, 0.U, 0.U)
      val x1223_inr_Foreach = new x1223_inr_Foreach_kernel(List(b925,b1006,b1185,b914,b1165), List(b1181,b1161), List(x934_tileA_sram_0,x1017_tileB_sram_0,x1018_tileC_sram_0,x1019_tileC_sram_1) ,  Some(me), List(x1197_ctrchain), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1223_inr_Foreach.sm.io.ctrDone := (x1223_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1223_inr_Foreach.backpressure := true.B | x1223_inr_Foreach.sm.io.doneLatch
      x1223_inr_Foreach.forwardpressure := (true.B) && (true.B) | x1223_inr_Foreach.sm.io.doneLatch
      x1223_inr_Foreach.sm.io.enableOut.zip(x1223_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1223_inr_Foreach.sm.io.break := false.B
      x1223_inr_Foreach.mask := ~x1223_inr_Foreach.cchain.head.output.noop & b1165 & b1006 & b925 & b1185 & b914
      x1223_inr_Foreach.configure("x1223_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1223_inr_Foreach.kernel()
      val x1246_inr_Foreach = new x1246_inr_Foreach_kernel(List(b925,b1006,b1186,b914,b1165), List(b1182,b1161), List(x934_tileA_sram_0,x1017_tileB_sram_0,x1018_tileC_sram_0,x1019_tileC_sram_1) ,  Some(me), List(x1198_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1246_inr_Foreach.sm.io.ctrDone := (x1246_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1246_inr_Foreach.backpressure := true.B | x1246_inr_Foreach.sm.io.doneLatch
      x1246_inr_Foreach.forwardpressure := (true.B) && (true.B) | x1246_inr_Foreach.sm.io.doneLatch
      x1246_inr_Foreach.sm.io.enableOut.zip(x1246_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1246_inr_Foreach.sm.io.break := false.B
      x1246_inr_Foreach.mask := ~x1246_inr_Foreach.cchain.head.output.noop & b1165 & b1006 & b925 & b1186 & b914
      x1246_inr_Foreach.configure("x1246_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1246_inr_Foreach.kernel()
      val x1269_inr_Foreach = new x1269_inr_Foreach_kernel(List(b925,b1006,b1187,b914,b1165), List(b1183,b1161), List(x934_tileA_sram_0,x1017_tileB_sram_0,x1018_tileC_sram_0,x1019_tileC_sram_1) ,  Some(me), List(x1199_ctrchain), 2, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1269_inr_Foreach.sm.io.ctrDone := (x1269_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1269_inr_Foreach.backpressure := true.B | x1269_inr_Foreach.sm.io.doneLatch
      x1269_inr_Foreach.forwardpressure := (true.B) && (true.B) | x1269_inr_Foreach.sm.io.doneLatch
      x1269_inr_Foreach.sm.io.enableOut.zip(x1269_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1269_inr_Foreach.sm.io.break := false.B
      x1269_inr_Foreach.mask := ~x1269_inr_Foreach.cchain.head.output.noop & b1165 & b1006 & b1187 & b925 & b914
      x1269_inr_Foreach.configure("x1269_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1269_inr_Foreach.kernel()
      val x1292_inr_Foreach = new x1292_inr_Foreach_kernel(List(b925,b1006,b1188,b914,b1165), List(b1161,b1184), List(x934_tileA_sram_0,x1017_tileB_sram_0,x1018_tileC_sram_0,x1019_tileC_sram_1) ,  Some(me), List(x1200_ctrchain), 3, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1292_inr_Foreach.sm.io.ctrDone := (x1292_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1292_inr_Foreach.backpressure := true.B | x1292_inr_Foreach.sm.io.doneLatch
      x1292_inr_Foreach.forwardpressure := (true.B) && (true.B) | x1292_inr_Foreach.sm.io.doneLatch
      x1292_inr_Foreach.sm.io.enableOut.zip(x1292_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1292_inr_Foreach.sm.io.break := false.B
      x1292_inr_Foreach.mask := ~x1292_inr_Foreach.cchain.head.output.noop & b1165 & b1006 & b925 & b914 & b1188
      x1292_inr_Foreach.configure("x1292_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1292_inr_Foreach.kernel()
    }
    val module = Module(new x1293_concrete(sm.p.depth)); module.io := DontCare
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
/** END ParallelPipe x1293 **/
