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

/** Hierarchy: x1637 -> x1638 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1637 **/
class x1637_kernel(
  list_b1168: List[Bool],
  list_b1164: List[FixedPoint],
  list_x1179_ctrchain: List[CounterChainInterface],
  list_x934_tileA_sram_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(ForkJoin, 4, isFSM = false   , latency = 0.0.toInt, myName = "x1637_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1637_iiCtr"))
  
  abstract class x1637_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b1168 = Input(Bool())
      val in_x1179_ctrchain = Flipped(new CounterChainInterface(ModuleParams.getParams("x1179_ctrchain_p").asInstanceOf[(List[Int],List[Int])] ))
      val in_b1164 = Input(new FixedPoint(true, 32, 0))
      val in_x934_tileA_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x934_tileA_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b925 = Input(Bool())
      val in_b1163 = Input(new FixedPoint(true, 32, 0))
      val in_x916_reg = Flipped(new NBufInterface(ModuleParams.getParams("x916_reg_p").asInstanceOf[NBufParams] ))
      val in_x1178_ctrchain = Flipped(new CounterChainInterface(ModuleParams.getParams("x1178_ctrchain_p").asInstanceOf[(List[Int],List[Int])] ))
      val in_x1017_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1006 = Input(Bool())
      val in_b1167 = Input(Bool())
      val in_b1162 = Input(new FixedPoint(true, 32, 0))
      val in_x1177_ctrchain = Flipped(new CounterChainInterface(ModuleParams.getParams("x1177_ctrchain_p").asInstanceOf[(List[Int],List[Int])] ))
      val in_b1166 = Input(Bool())
      val in_x1018_tileC_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_0_p").asInstanceOf[NBufParams] ))
      val in_x1180_ctrchain = Flipped(new CounterChainInterface(ModuleParams.getParams("x1180_ctrchain_p").asInstanceOf[(List[Int],List[Int])] ))
      val in_b914 = Input(Bool())
      val in_b1161 = Input(new FixedPoint(true, 32, 0))
      val in_b1165 = Input(Bool())
      val in_x1019_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1019_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(4, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(4, 1))
      val rr = Input(Bool())
    })
    def b1168 = {io.in_b1168} 
    def x1179_ctrchain = {io.in_x1179_ctrchain} ; io.in_x1179_ctrchain := DontCare
    def b1164 = {io.in_b1164} 
    def x934_tileA_sram_0 = {io.in_x934_tileA_sram_0} ; io.in_x934_tileA_sram_0 := DontCare
    def b925 = {io.in_b925} 
    def b1163 = {io.in_b1163} 
    def x916_reg = {io.in_x916_reg} ; io.in_x916_reg := DontCare
    def x1178_ctrchain = {io.in_x1178_ctrchain} ; io.in_x1178_ctrchain := DontCare
    def x1017_tileB_sram_0 = {io.in_x1017_tileB_sram_0} ; io.in_x1017_tileB_sram_0 := DontCare
    def b1006 = {io.in_b1006} 
    def b1167 = {io.in_b1167} 
    def b1162 = {io.in_b1162} 
    def x1177_ctrchain = {io.in_x1177_ctrchain} ; io.in_x1177_ctrchain := DontCare
    def b1166 = {io.in_b1166} 
    def x1018_tileC_sram_0 = {io.in_x1018_tileC_sram_0} ; io.in_x1018_tileC_sram_0 := DontCare
    def x1180_ctrchain = {io.in_x1180_ctrchain} ; io.in_x1180_ctrchain := DontCare
    def b914 = {io.in_b914} 
    def b1161 = {io.in_b1161} 
    def b1165 = {io.in_b1165} 
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
  }
  def connectWires0(module: x1637_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b1168 <> b1168
    module.io.in_x1179_ctrchain.input <> x1179_ctrchain.input; module.io.in_x1179_ctrchain.output <> x1179_ctrchain.output
    module.io.in_b1164 <> b1164
    x934_tileA_sram_0.connectLedger(module.io.in_x934_tileA_sram_0)
    module.io.in_b925 <> b925
    module.io.in_b1163 <> b1163
    x916_reg.connectLedger(module.io.in_x916_reg)
    module.io.in_x1178_ctrchain.input <> x1178_ctrchain.input; module.io.in_x1178_ctrchain.output <> x1178_ctrchain.output
    x1017_tileB_sram_0.connectLedger(module.io.in_x1017_tileB_sram_0)
    module.io.in_b1006 <> b1006
    module.io.in_b1167 <> b1167
    module.io.in_b1162 <> b1162
    module.io.in_x1177_ctrchain.input <> x1177_ctrchain.input; module.io.in_x1177_ctrchain.output <> x1177_ctrchain.output
    module.io.in_b1166 <> b1166
    x1018_tileC_sram_0.connectLedger(module.io.in_x1018_tileC_sram_0)
    module.io.in_x1180_ctrchain.input <> x1180_ctrchain.input; module.io.in_x1180_ctrchain.output <> x1180_ctrchain.output
    module.io.in_b914 <> b914
    module.io.in_b1161 <> b1161
    module.io.in_b1165 <> b1165
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
  }
  val b1168 = list_b1168(0)
  val b925 = list_b1168(1)
  val b1006 = list_b1168(2)
  val b1167 = list_b1168(3)
  val b1166 = list_b1168(4)
  val b914 = list_b1168(5)
  val b1165 = list_b1168(6)
  val b1164 = list_b1164(0)
  val b1163 = list_b1164(1)
  val b1162 = list_b1164(2)
  val b1161 = list_b1164(3)
  val x1179_ctrchain = list_x1179_ctrchain(0)
  val x1178_ctrchain = list_x1179_ctrchain(1)
  val x1177_ctrchain = list_x1179_ctrchain(2)
  val x1180_ctrchain = list_x1179_ctrchain(3)
  val x934_tileA_sram_0 = list_x934_tileA_sram_0(0)
  val x916_reg = list_x934_tileA_sram_0(1)
  val x1017_tileB_sram_0 = list_x934_tileA_sram_0(2)
  val x1018_tileC_sram_0 = list_x934_tileA_sram_0(3)
  val x1019_tileC_sram_1 = list_x934_tileA_sram_0(4)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1637")
    implicit val stack = ControllerStack.stack.toList
    class x1637_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1637_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1637 = Module(new InstrumentationCounter())
      val iters_x1637 = Module(new InstrumentationCounter())
      cycles_x1637.io.enable := io.sigsIn.baseEn
      iters_x1637.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1637_instrctr, cycles_x1637.io.count, iters_x1637.io.count, 0.U, 0.U)
      val x1294_outr_Foreach = new x1294_outr_Foreach_kernel(List(b925,b1006,b914,b1165), List(b1161), List(x934_tileA_sram_0,x916_reg,x1017_tileB_sram_0,x1018_tileC_sram_0,x1019_tileC_sram_1) ,  Some(me), List(x1177_ctrchain), 0, 1, 1, List(4), List(32), breakpoints, instrctrs.toList, rr)
      x1294_outr_Foreach.sm.io.ctrDone := (x1294_outr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1294_outr_Foreach.backpressure := true.B | x1294_outr_Foreach.sm.io.doneLatch
      x1294_outr_Foreach.forwardpressure := (true.B) && (true.B) | x1294_outr_Foreach.sm.io.doneLatch
      x1294_outr_Foreach.sm.io.enableOut.zip(x1294_outr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1294_outr_Foreach.sm.io.break := false.B
      x1294_outr_Foreach.mask := ~x1294_outr_Foreach.cchain.head.output.noop & b1165 & b1006 & b925 & b914
      x1294_outr_Foreach.configure("x1294_outr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1294_outr_Foreach.kernel()
      val x1408_outr_Foreach = new x1408_outr_Foreach_kernel(List(b925,b1006,b1166,b914), List(b1162), List(x934_tileA_sram_0,x916_reg,x1017_tileB_sram_0,x1018_tileC_sram_0,x1019_tileC_sram_1) ,  Some(me), List(x1178_ctrchain), 1, 1, 1, List(4), List(32), breakpoints, instrctrs.toList, rr)
      x1408_outr_Foreach.sm.io.ctrDone := (x1408_outr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1408_outr_Foreach.backpressure := true.B | x1408_outr_Foreach.sm.io.doneLatch
      x1408_outr_Foreach.forwardpressure := (true.B) && (true.B) | x1408_outr_Foreach.sm.io.doneLatch
      x1408_outr_Foreach.sm.io.enableOut.zip(x1408_outr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1408_outr_Foreach.sm.io.break := false.B
      x1408_outr_Foreach.mask := ~x1408_outr_Foreach.cchain.head.output.noop & b1166 & b1006 & b925 & b914
      x1408_outr_Foreach.configure("x1408_outr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1408_outr_Foreach.kernel()
      val x1522_outr_Foreach = new x1522_outr_Foreach_kernel(List(b925,b1006,b1167,b914), List(b1163), List(x934_tileA_sram_0,x916_reg,x1017_tileB_sram_0,x1018_tileC_sram_0,x1019_tileC_sram_1) ,  Some(me), List(x1179_ctrchain), 2, 1, 1, List(4), List(32), breakpoints, instrctrs.toList, rr)
      x1522_outr_Foreach.sm.io.ctrDone := (x1522_outr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1522_outr_Foreach.backpressure := true.B | x1522_outr_Foreach.sm.io.doneLatch
      x1522_outr_Foreach.forwardpressure := (true.B) && (true.B) | x1522_outr_Foreach.sm.io.doneLatch
      x1522_outr_Foreach.sm.io.enableOut.zip(x1522_outr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1522_outr_Foreach.sm.io.break := false.B
      x1522_outr_Foreach.mask := ~x1522_outr_Foreach.cchain.head.output.noop & b1167 & b1006 & b925 & b914
      x1522_outr_Foreach.configure("x1522_outr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1522_outr_Foreach.kernel()
      val x1636_outr_Foreach = new x1636_outr_Foreach_kernel(List(b1168,b925,b1006,b914), List(b1164), List(x934_tileA_sram_0,x916_reg,x1017_tileB_sram_0,x1018_tileC_sram_0,x1019_tileC_sram_1) ,  Some(me), List(x1180_ctrchain), 3, 1, 1, List(4), List(32), breakpoints, instrctrs.toList, rr)
      x1636_outr_Foreach.sm.io.ctrDone := (x1636_outr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1636_outr_Foreach.backpressure := true.B | x1636_outr_Foreach.sm.io.doneLatch
      x1636_outr_Foreach.forwardpressure := (true.B) && (true.B) | x1636_outr_Foreach.sm.io.doneLatch
      x1636_outr_Foreach.sm.io.enableOut.zip(x1636_outr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1636_outr_Foreach.sm.io.break := false.B
      x1636_outr_Foreach.mask := ~x1636_outr_Foreach.cchain.head.output.noop & b1168 & b1006 & b925 & b914
      x1636_outr_Foreach.configure("x1636_outr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1636_outr_Foreach.kernel()
    }
    val module = Module(new x1637_concrete(sm.p.depth)); module.io := DontCare
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
/** END ParallelPipe x1637 **/
