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

/** Hierarchy: x1407 -> x1408 -> x1637 -> x1638 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1407 **/
class x1407_kernel(
  list_b1302: List[Bool],
  list_b1295: List[FixedPoint],
  list_x1313_ctrchain: List[CounterChainInterface],
  list_x934_tileA_sram_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(ForkJoin, 4, isFSM = false   , latency = 0.0.toInt, myName = "x1407_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1407_iiCtr"))
  
  abstract class x1407_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b1295 = Input(new FixedPoint(true, 32, 0))
      val in_b1298 = Input(new FixedPoint(true, 32, 0))
      val in_x1313_ctrchain = Flipped(new CounterChainInterface(ModuleParams.getParams("x1313_ctrchain_p").asInstanceOf[(List[Int],List[Int])] ))
      val in_x934_tileA_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x934_tileA_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1302 = Input(Bool())
      val in_b925 = Input(Bool())
      val in_x1311_ctrchain = Flipped(new CounterChainInterface(ModuleParams.getParams("x1311_ctrchain_p").asInstanceOf[(List[Int],List[Int])] ))
      val in_b1296 = Input(new FixedPoint(true, 32, 0))
      val in_x1017_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1006 = Input(Bool())
      val in_b1297 = Input(new FixedPoint(true, 32, 0))
      val in_b1301 = Input(Bool())
      val in_x1312_ctrchain = Flipped(new CounterChainInterface(ModuleParams.getParams("x1312_ctrchain_p").asInstanceOf[(List[Int],List[Int])] ))
      val in_b1162 = Input(new FixedPoint(true, 32, 0))
      val in_b1166 = Input(Bool())
      val in_x1018_tileC_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1300 = Input(Bool())
      val in_b914 = Input(Bool())
      val in_x1314_ctrchain = Flipped(new CounterChainInterface(ModuleParams.getParams("x1314_ctrchain_p").asInstanceOf[(List[Int],List[Int])] ))
      val in_x1019_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1019_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_b1299 = Input(Bool())
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(4, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(4, 1))
      val rr = Input(Bool())
    })
    def b1295 = {io.in_b1295} 
    def b1298 = {io.in_b1298} 
    def x1313_ctrchain = {io.in_x1313_ctrchain} ; io.in_x1313_ctrchain := DontCare
    def x934_tileA_sram_0 = {io.in_x934_tileA_sram_0} ; io.in_x934_tileA_sram_0 := DontCare
    def b1302 = {io.in_b1302} 
    def b925 = {io.in_b925} 
    def x1311_ctrchain = {io.in_x1311_ctrchain} ; io.in_x1311_ctrchain := DontCare
    def b1296 = {io.in_b1296} 
    def x1017_tileB_sram_0 = {io.in_x1017_tileB_sram_0} ; io.in_x1017_tileB_sram_0 := DontCare
    def b1006 = {io.in_b1006} 
    def b1297 = {io.in_b1297} 
    def b1301 = {io.in_b1301} 
    def x1312_ctrchain = {io.in_x1312_ctrchain} ; io.in_x1312_ctrchain := DontCare
    def b1162 = {io.in_b1162} 
    def b1166 = {io.in_b1166} 
    def x1018_tileC_sram_0 = {io.in_x1018_tileC_sram_0} ; io.in_x1018_tileC_sram_0 := DontCare
    def b1300 = {io.in_b1300} 
    def b914 = {io.in_b914} 
    def x1314_ctrchain = {io.in_x1314_ctrchain} ; io.in_x1314_ctrchain := DontCare
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
    def b1299 = {io.in_b1299} 
  }
  def connectWires0(module: x1407_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b1295 <> b1295
    module.io.in_b1298 <> b1298
    module.io.in_x1313_ctrchain.input <> x1313_ctrchain.input; module.io.in_x1313_ctrchain.output <> x1313_ctrchain.output
    x934_tileA_sram_0.connectLedger(module.io.in_x934_tileA_sram_0)
    module.io.in_b1302 <> b1302
    module.io.in_b925 <> b925
    module.io.in_x1311_ctrchain.input <> x1311_ctrchain.input; module.io.in_x1311_ctrchain.output <> x1311_ctrchain.output
    module.io.in_b1296 <> b1296
    x1017_tileB_sram_0.connectLedger(module.io.in_x1017_tileB_sram_0)
    module.io.in_b1006 <> b1006
    module.io.in_b1297 <> b1297
    module.io.in_b1301 <> b1301
    module.io.in_x1312_ctrchain.input <> x1312_ctrchain.input; module.io.in_x1312_ctrchain.output <> x1312_ctrchain.output
    module.io.in_b1162 <> b1162
    module.io.in_b1166 <> b1166
    x1018_tileC_sram_0.connectLedger(module.io.in_x1018_tileC_sram_0)
    module.io.in_b1300 <> b1300
    module.io.in_b914 <> b914
    module.io.in_x1314_ctrchain.input <> x1314_ctrchain.input; module.io.in_x1314_ctrchain.output <> x1314_ctrchain.output
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
    module.io.in_b1299 <> b1299
  }
  val b1302 = list_b1302(0)
  val b925 = list_b1302(1)
  val b1006 = list_b1302(2)
  val b1301 = list_b1302(3)
  val b1166 = list_b1302(4)
  val b1300 = list_b1302(5)
  val b914 = list_b1302(6)
  val b1299 = list_b1302(7)
  val b1295 = list_b1295(0)
  val b1298 = list_b1295(1)
  val b1296 = list_b1295(2)
  val b1297 = list_b1295(3)
  val b1162 = list_b1295(4)
  val x1313_ctrchain = list_x1313_ctrchain(0)
  val x1311_ctrchain = list_x1313_ctrchain(1)
  val x1312_ctrchain = list_x1313_ctrchain(2)
  val x1314_ctrchain = list_x1313_ctrchain(3)
  val x934_tileA_sram_0 = list_x934_tileA_sram_0(0)
  val x1017_tileB_sram_0 = list_x934_tileA_sram_0(1)
  val x1018_tileC_sram_0 = list_x934_tileA_sram_0(2)
  val x1019_tileC_sram_1 = list_x934_tileA_sram_0(3)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1407")
    implicit val stack = ControllerStack.stack.toList
    class x1407_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1407_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1407 = Module(new InstrumentationCounter())
      val iters_x1407 = Module(new InstrumentationCounter())
      cycles_x1407.io.enable := io.sigsIn.baseEn
      iters_x1407.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1407_instrctr, cycles_x1407.io.count, iters_x1407.io.count, 0.U, 0.U)
      val x1337_inr_Foreach = new x1337_inr_Foreach_kernel(List(b925,b1006,b1166,b914,b1299), List(b1295,b1162), List(x934_tileA_sram_0,x1017_tileB_sram_0,x1018_tileC_sram_0,x1019_tileC_sram_1) ,  Some(me), List(x1311_ctrchain), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1337_inr_Foreach.sm.io.ctrDone := (x1337_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1337_inr_Foreach.backpressure := true.B | x1337_inr_Foreach.sm.io.doneLatch
      x1337_inr_Foreach.forwardpressure := (true.B) && (true.B) | x1337_inr_Foreach.sm.io.doneLatch
      x1337_inr_Foreach.sm.io.enableOut.zip(x1337_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1337_inr_Foreach.sm.io.break := false.B
      x1337_inr_Foreach.mask := ~x1337_inr_Foreach.cchain.head.output.noop & b1299 & b1006 & b925 & b914 & b1166
      x1337_inr_Foreach.configure("x1337_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1337_inr_Foreach.kernel()
      val x1360_inr_Foreach = new x1360_inr_Foreach_kernel(List(b925,b1006,b1166,b1300,b914), List(b1296,b1162), List(x934_tileA_sram_0,x1017_tileB_sram_0,x1018_tileC_sram_0,x1019_tileC_sram_1) ,  Some(me), List(x1312_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1360_inr_Foreach.sm.io.ctrDone := (x1360_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1360_inr_Foreach.backpressure := true.B | x1360_inr_Foreach.sm.io.doneLatch
      x1360_inr_Foreach.forwardpressure := (true.B) && (true.B) | x1360_inr_Foreach.sm.io.doneLatch
      x1360_inr_Foreach.sm.io.enableOut.zip(x1360_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1360_inr_Foreach.sm.io.break := false.B
      x1360_inr_Foreach.mask := ~x1360_inr_Foreach.cchain.head.output.noop & b1300 & b1006 & b925 & b914 & b1166
      x1360_inr_Foreach.configure("x1360_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1360_inr_Foreach.kernel()
      val x1383_inr_Foreach = new x1383_inr_Foreach_kernel(List(b925,b1006,b1301,b1166,b914), List(b1297,b1162), List(x934_tileA_sram_0,x1017_tileB_sram_0,x1018_tileC_sram_0,x1019_tileC_sram_1) ,  Some(me), List(x1313_ctrchain), 2, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1383_inr_Foreach.sm.io.ctrDone := (x1383_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1383_inr_Foreach.backpressure := true.B | x1383_inr_Foreach.sm.io.doneLatch
      x1383_inr_Foreach.forwardpressure := (true.B) && (true.B) | x1383_inr_Foreach.sm.io.doneLatch
      x1383_inr_Foreach.sm.io.enableOut.zip(x1383_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1383_inr_Foreach.sm.io.break := false.B
      x1383_inr_Foreach.mask := ~x1383_inr_Foreach.cchain.head.output.noop & b1301 & b1006 & b925 & b914 & b1166
      x1383_inr_Foreach.configure("x1383_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1383_inr_Foreach.kernel()
      val x1406_inr_Foreach = new x1406_inr_Foreach_kernel(List(b1302,b925,b1006,b1166,b914), List(b1298,b1162), List(x934_tileA_sram_0,x1017_tileB_sram_0,x1018_tileC_sram_0,x1019_tileC_sram_1) ,  Some(me), List(x1314_ctrchain), 3, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1406_inr_Foreach.sm.io.ctrDone := (x1406_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1406_inr_Foreach.backpressure := true.B | x1406_inr_Foreach.sm.io.doneLatch
      x1406_inr_Foreach.forwardpressure := (true.B) && (true.B) | x1406_inr_Foreach.sm.io.doneLatch
      x1406_inr_Foreach.sm.io.enableOut.zip(x1406_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1406_inr_Foreach.sm.io.break := false.B
      x1406_inr_Foreach.mask := ~x1406_inr_Foreach.cchain.head.output.noop & b1006 & b925 & b1302 & b914 & b1166
      x1406_inr_Foreach.configure("x1406_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1406_inr_Foreach.kernel()
    }
    val module = Module(new x1407_concrete(sm.p.depth)); module.io := DontCare
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
/** END ParallelPipe x1407 **/
