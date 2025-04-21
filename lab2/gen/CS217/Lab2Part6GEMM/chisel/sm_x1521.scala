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

/** Hierarchy: x1521 -> x1522 -> x1637 -> x1638 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1521 **/
class x1521_kernel(
  list_b1415: List[Bool],
  list_b1411: List[FixedPoint],
  list_x1426_ctrchain: List[CounterChainInterface],
  list_x934_tileA_sram_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(ForkJoin, 4, isFSM = false   , latency = 0.0.toInt, myName = "x1521_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1521_iiCtr"))
  
  abstract class x1521_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b1411 = Input(new FixedPoint(true, 32, 0))
      val in_x1426_ctrchain = Flipped(new CounterChainInterface(ModuleParams.getParams("x1426_ctrchain_p").asInstanceOf[(List[Int],List[Int])] ))
      val in_b1409 = Input(new FixedPoint(true, 32, 0))
      val in_x934_tileA_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x934_tileA_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1415 = Input(Bool())
      val in_b925 = Input(Bool())
      val in_b1163 = Input(new FixedPoint(true, 32, 0))
      val in_x1017_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_x1425_ctrchain = Flipped(new CounterChainInterface(ModuleParams.getParams("x1425_ctrchain_p").asInstanceOf[(List[Int],List[Int])] ))
      val in_b1006 = Input(Bool())
      val in_b1414 = Input(Bool())
      val in_b1167 = Input(Bool())
      val in_x1428_ctrchain = Flipped(new CounterChainInterface(ModuleParams.getParams("x1428_ctrchain_p").asInstanceOf[(List[Int],List[Int])] ))
      val in_b1413 = Input(Bool())
      val in_x1018_tileC_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_0_p").asInstanceOf[NBufParams] ))
      val in_x1427_ctrchain = Flipped(new CounterChainInterface(ModuleParams.getParams("x1427_ctrchain_p").asInstanceOf[(List[Int],List[Int])] ))
      val in_b1412 = Input(new FixedPoint(true, 32, 0))
      val in_b914 = Input(Bool())
      val in_b1410 = Input(new FixedPoint(true, 32, 0))
      val in_x1019_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1019_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_b1416 = Input(Bool())
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(4, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(4, 1))
      val rr = Input(Bool())
    })
    def b1411 = {io.in_b1411} 
    def x1426_ctrchain = {io.in_x1426_ctrchain} ; io.in_x1426_ctrchain := DontCare
    def b1409 = {io.in_b1409} 
    def x934_tileA_sram_0 = {io.in_x934_tileA_sram_0} ; io.in_x934_tileA_sram_0 := DontCare
    def b1415 = {io.in_b1415} 
    def b925 = {io.in_b925} 
    def b1163 = {io.in_b1163} 
    def x1017_tileB_sram_0 = {io.in_x1017_tileB_sram_0} ; io.in_x1017_tileB_sram_0 := DontCare
    def x1425_ctrchain = {io.in_x1425_ctrchain} ; io.in_x1425_ctrchain := DontCare
    def b1006 = {io.in_b1006} 
    def b1414 = {io.in_b1414} 
    def b1167 = {io.in_b1167} 
    def x1428_ctrchain = {io.in_x1428_ctrchain} ; io.in_x1428_ctrchain := DontCare
    def b1413 = {io.in_b1413} 
    def x1018_tileC_sram_0 = {io.in_x1018_tileC_sram_0} ; io.in_x1018_tileC_sram_0 := DontCare
    def x1427_ctrchain = {io.in_x1427_ctrchain} ; io.in_x1427_ctrchain := DontCare
    def b1412 = {io.in_b1412} 
    def b914 = {io.in_b914} 
    def b1410 = {io.in_b1410} 
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
    def b1416 = {io.in_b1416} 
  }
  def connectWires0(module: x1521_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b1411 <> b1411
    module.io.in_x1426_ctrchain.input <> x1426_ctrchain.input; module.io.in_x1426_ctrchain.output <> x1426_ctrchain.output
    module.io.in_b1409 <> b1409
    x934_tileA_sram_0.connectLedger(module.io.in_x934_tileA_sram_0)
    module.io.in_b1415 <> b1415
    module.io.in_b925 <> b925
    module.io.in_b1163 <> b1163
    x1017_tileB_sram_0.connectLedger(module.io.in_x1017_tileB_sram_0)
    module.io.in_x1425_ctrchain.input <> x1425_ctrchain.input; module.io.in_x1425_ctrchain.output <> x1425_ctrchain.output
    module.io.in_b1006 <> b1006
    module.io.in_b1414 <> b1414
    module.io.in_b1167 <> b1167
    module.io.in_x1428_ctrchain.input <> x1428_ctrchain.input; module.io.in_x1428_ctrchain.output <> x1428_ctrchain.output
    module.io.in_b1413 <> b1413
    x1018_tileC_sram_0.connectLedger(module.io.in_x1018_tileC_sram_0)
    module.io.in_x1427_ctrchain.input <> x1427_ctrchain.input; module.io.in_x1427_ctrchain.output <> x1427_ctrchain.output
    module.io.in_b1412 <> b1412
    module.io.in_b914 <> b914
    module.io.in_b1410 <> b1410
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
    module.io.in_b1416 <> b1416
  }
  val b1415 = list_b1415(0)
  val b925 = list_b1415(1)
  val b1006 = list_b1415(2)
  val b1414 = list_b1415(3)
  val b1167 = list_b1415(4)
  val b1413 = list_b1415(5)
  val b914 = list_b1415(6)
  val b1416 = list_b1415(7)
  val b1411 = list_b1411(0)
  val b1409 = list_b1411(1)
  val b1163 = list_b1411(2)
  val b1412 = list_b1411(3)
  val b1410 = list_b1411(4)
  val x1426_ctrchain = list_x1426_ctrchain(0)
  val x1425_ctrchain = list_x1426_ctrchain(1)
  val x1428_ctrchain = list_x1426_ctrchain(2)
  val x1427_ctrchain = list_x1426_ctrchain(3)
  val x934_tileA_sram_0 = list_x934_tileA_sram_0(0)
  val x1017_tileB_sram_0 = list_x934_tileA_sram_0(1)
  val x1018_tileC_sram_0 = list_x934_tileA_sram_0(2)
  val x1019_tileC_sram_1 = list_x934_tileA_sram_0(3)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1521")
    implicit val stack = ControllerStack.stack.toList
    class x1521_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1521_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1521 = Module(new InstrumentationCounter())
      val iters_x1521 = Module(new InstrumentationCounter())
      cycles_x1521.io.enable := io.sigsIn.baseEn
      iters_x1521.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1521_instrctr, cycles_x1521.io.count, iters_x1521.io.count, 0.U, 0.U)
      val x1451_inr_Foreach = new x1451_inr_Foreach_kernel(List(b925,b1006,b1167,b1413,b914), List(b1409,b1163), List(x934_tileA_sram_0,x1017_tileB_sram_0,x1018_tileC_sram_0,x1019_tileC_sram_1) ,  Some(me), List(x1425_ctrchain), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1451_inr_Foreach.sm.io.ctrDone := (x1451_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1451_inr_Foreach.backpressure := true.B | x1451_inr_Foreach.sm.io.doneLatch
      x1451_inr_Foreach.forwardpressure := (true.B) && (true.B) | x1451_inr_Foreach.sm.io.doneLatch
      x1451_inr_Foreach.sm.io.enableOut.zip(x1451_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1451_inr_Foreach.sm.io.break := false.B
      x1451_inr_Foreach.mask := ~x1451_inr_Foreach.cchain.head.output.noop & b1006 & b925 & b914 & b1413 & b1167
      x1451_inr_Foreach.configure("x1451_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1451_inr_Foreach.kernel()
      val x1474_inr_Foreach = new x1474_inr_Foreach_kernel(List(b925,b1006,b1414,b1167,b914), List(b1163,b1410), List(x934_tileA_sram_0,x1017_tileB_sram_0,x1018_tileC_sram_0,x1019_tileC_sram_1) ,  Some(me), List(x1426_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1474_inr_Foreach.sm.io.ctrDone := (x1474_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1474_inr_Foreach.backpressure := true.B | x1474_inr_Foreach.sm.io.doneLatch
      x1474_inr_Foreach.forwardpressure := (true.B) && (true.B) | x1474_inr_Foreach.sm.io.doneLatch
      x1474_inr_Foreach.sm.io.enableOut.zip(x1474_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1474_inr_Foreach.sm.io.break := false.B
      x1474_inr_Foreach.mask := ~x1474_inr_Foreach.cchain.head.output.noop & b1006 & b925 & b914 & b1414 & b1167
      x1474_inr_Foreach.configure("x1474_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1474_inr_Foreach.kernel()
      val x1497_inr_Foreach = new x1497_inr_Foreach_kernel(List(b1415,b925,b1006,b1167,b914), List(b1411,b1163), List(x934_tileA_sram_0,x1017_tileB_sram_0,x1018_tileC_sram_0,x1019_tileC_sram_1) ,  Some(me), List(x1427_ctrchain), 2, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1497_inr_Foreach.sm.io.ctrDone := (x1497_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1497_inr_Foreach.backpressure := true.B | x1497_inr_Foreach.sm.io.doneLatch
      x1497_inr_Foreach.forwardpressure := (true.B) && (true.B) | x1497_inr_Foreach.sm.io.doneLatch
      x1497_inr_Foreach.sm.io.enableOut.zip(x1497_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1497_inr_Foreach.sm.io.break := false.B
      x1497_inr_Foreach.mask := ~x1497_inr_Foreach.cchain.head.output.noop & b1006 & b925 & b914 & b1415 & b1167
      x1497_inr_Foreach.configure("x1497_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1497_inr_Foreach.kernel()
      val x1520_inr_Foreach = new x1520_inr_Foreach_kernel(List(b925,b1006,b1167,b914,b1416), List(b1163,b1412), List(x934_tileA_sram_0,x1017_tileB_sram_0,x1018_tileC_sram_0,x1019_tileC_sram_1) ,  Some(me), List(x1428_ctrchain), 3, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1520_inr_Foreach.sm.io.ctrDone := (x1520_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1520_inr_Foreach.backpressure := true.B | x1520_inr_Foreach.sm.io.doneLatch
      x1520_inr_Foreach.forwardpressure := (true.B) && (true.B) | x1520_inr_Foreach.sm.io.doneLatch
      x1520_inr_Foreach.sm.io.enableOut.zip(x1520_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1520_inr_Foreach.sm.io.break := false.B
      x1520_inr_Foreach.mask := ~x1520_inr_Foreach.cchain.head.output.noop & b1416 & b1006 & b925 & b914 & b1167
      x1520_inr_Foreach.configure("x1520_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1520_inr_Foreach.kernel()
    }
    val module = Module(new x1521_concrete(sm.p.depth)); module.io := DontCare
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
/** END ParallelPipe x1521 **/
