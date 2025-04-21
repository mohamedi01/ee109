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

/** Hierarchy: x1635 -> x1636 -> x1637 -> x1638 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1635 **/
class x1635_kernel(
  list_b1168: List[Bool],
  list_b1164: List[FixedPoint],
  list_x1541_ctrchain: List[CounterChainInterface],
  list_x934_tileA_sram_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(ForkJoin, 4, isFSM = false   , latency = 0.0.toInt, myName = "x1635_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1635_iiCtr"))
  
  abstract class x1635_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b1168 = Input(Bool())
      val in_b1164 = Input(new FixedPoint(true, 32, 0))
      val in_b1529 = Input(Bool())
      val in_x934_tileA_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x934_tileA_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1526 = Input(new FixedPoint(true, 32, 0))
      val in_b925 = Input(Bool())
      val in_x1541_ctrchain = Flipped(new CounterChainInterface(ModuleParams.getParams("x1541_ctrchain_p").asInstanceOf[(List[Int],List[Int])] ))
      val in_x1017_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_x1540_ctrchain = Flipped(new CounterChainInterface(ModuleParams.getParams("x1540_ctrchain_p").asInstanceOf[(List[Int],List[Int])] ))
      val in_b1006 = Input(Bool())
      val in_b1530 = Input(Bool())
      val in_x1539_ctrchain = Flipped(new CounterChainInterface(ModuleParams.getParams("x1539_ctrchain_p").asInstanceOf[(List[Int],List[Int])] ))
      val in_b1525 = Input(new FixedPoint(true, 32, 0))
      val in_b1524 = Input(new FixedPoint(true, 32, 0))
      val in_x1542_ctrchain = Flipped(new CounterChainInterface(ModuleParams.getParams("x1542_ctrchain_p").asInstanceOf[(List[Int],List[Int])] ))
      val in_b1528 = Input(Bool())
      val in_x1018_tileC_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b1523 = Input(new FixedPoint(true, 32, 0))
      val in_b914 = Input(Bool())
      val in_b1527 = Input(Bool())
      val in_x1019_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1019_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(4, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(4, 1))
      val rr = Input(Bool())
    })
    def b1168 = {io.in_b1168} 
    def b1164 = {io.in_b1164} 
    def b1529 = {io.in_b1529} 
    def x934_tileA_sram_0 = {io.in_x934_tileA_sram_0} ; io.in_x934_tileA_sram_0 := DontCare
    def b1526 = {io.in_b1526} 
    def b925 = {io.in_b925} 
    def x1541_ctrchain = {io.in_x1541_ctrchain} ; io.in_x1541_ctrchain := DontCare
    def x1017_tileB_sram_0 = {io.in_x1017_tileB_sram_0} ; io.in_x1017_tileB_sram_0 := DontCare
    def x1540_ctrchain = {io.in_x1540_ctrchain} ; io.in_x1540_ctrchain := DontCare
    def b1006 = {io.in_b1006} 
    def b1530 = {io.in_b1530} 
    def x1539_ctrchain = {io.in_x1539_ctrchain} ; io.in_x1539_ctrchain := DontCare
    def b1525 = {io.in_b1525} 
    def b1524 = {io.in_b1524} 
    def x1542_ctrchain = {io.in_x1542_ctrchain} ; io.in_x1542_ctrchain := DontCare
    def b1528 = {io.in_b1528} 
    def x1018_tileC_sram_0 = {io.in_x1018_tileC_sram_0} ; io.in_x1018_tileC_sram_0 := DontCare
    def b1523 = {io.in_b1523} 
    def b914 = {io.in_b914} 
    def b1527 = {io.in_b1527} 
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
  }
  def connectWires0(module: x1635_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b1168 <> b1168
    module.io.in_b1164 <> b1164
    module.io.in_b1529 <> b1529
    x934_tileA_sram_0.connectLedger(module.io.in_x934_tileA_sram_0)
    module.io.in_b1526 <> b1526
    module.io.in_b925 <> b925
    module.io.in_x1541_ctrchain.input <> x1541_ctrchain.input; module.io.in_x1541_ctrchain.output <> x1541_ctrchain.output
    x1017_tileB_sram_0.connectLedger(module.io.in_x1017_tileB_sram_0)
    module.io.in_x1540_ctrchain.input <> x1540_ctrchain.input; module.io.in_x1540_ctrchain.output <> x1540_ctrchain.output
    module.io.in_b1006 <> b1006
    module.io.in_b1530 <> b1530
    module.io.in_x1539_ctrchain.input <> x1539_ctrchain.input; module.io.in_x1539_ctrchain.output <> x1539_ctrchain.output
    module.io.in_b1525 <> b1525
    module.io.in_b1524 <> b1524
    module.io.in_x1542_ctrchain.input <> x1542_ctrchain.input; module.io.in_x1542_ctrchain.output <> x1542_ctrchain.output
    module.io.in_b1528 <> b1528
    x1018_tileC_sram_0.connectLedger(module.io.in_x1018_tileC_sram_0)
    module.io.in_b1523 <> b1523
    module.io.in_b914 <> b914
    module.io.in_b1527 <> b1527
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
  }
  val b1168 = list_b1168(0)
  val b1529 = list_b1168(1)
  val b925 = list_b1168(2)
  val b1006 = list_b1168(3)
  val b1530 = list_b1168(4)
  val b1528 = list_b1168(5)
  val b914 = list_b1168(6)
  val b1527 = list_b1168(7)
  val b1164 = list_b1164(0)
  val b1526 = list_b1164(1)
  val b1525 = list_b1164(2)
  val b1524 = list_b1164(3)
  val b1523 = list_b1164(4)
  val x1541_ctrchain = list_x1541_ctrchain(0)
  val x1540_ctrchain = list_x1541_ctrchain(1)
  val x1539_ctrchain = list_x1541_ctrchain(2)
  val x1542_ctrchain = list_x1541_ctrchain(3)
  val x934_tileA_sram_0 = list_x934_tileA_sram_0(0)
  val x1017_tileB_sram_0 = list_x934_tileA_sram_0(1)
  val x1018_tileC_sram_0 = list_x934_tileA_sram_0(2)
  val x1019_tileC_sram_1 = list_x934_tileA_sram_0(3)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1635")
    implicit val stack = ControllerStack.stack.toList
    class x1635_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1635_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1635 = Module(new InstrumentationCounter())
      val iters_x1635 = Module(new InstrumentationCounter())
      cycles_x1635.io.enable := io.sigsIn.baseEn
      iters_x1635.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1635_instrctr, cycles_x1635.io.count, iters_x1635.io.count, 0.U, 0.U)
      val x1565_inr_Foreach = new x1565_inr_Foreach_kernel(List(b1168,b925,b1006,b914,b1527), List(b1164,b1523), List(x934_tileA_sram_0,x1017_tileB_sram_0,x1018_tileC_sram_0,x1019_tileC_sram_1) ,  Some(me), List(x1539_ctrchain), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1565_inr_Foreach.sm.io.ctrDone := (x1565_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1565_inr_Foreach.backpressure := true.B | x1565_inr_Foreach.sm.io.doneLatch
      x1565_inr_Foreach.forwardpressure := (true.B) && (true.B) | x1565_inr_Foreach.sm.io.doneLatch
      x1565_inr_Foreach.sm.io.enableOut.zip(x1565_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1565_inr_Foreach.sm.io.break := false.B
      x1565_inr_Foreach.mask := ~x1565_inr_Foreach.cchain.head.output.noop & b1006 & b925 & b914 & b1168 & b1527
      x1565_inr_Foreach.configure("x1565_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1565_inr_Foreach.kernel()
      val x1588_inr_Foreach = new x1588_inr_Foreach_kernel(List(b1168,b925,b1006,b1528,b914), List(b1164,b1524), List(x934_tileA_sram_0,x1017_tileB_sram_0,x1018_tileC_sram_0,x1019_tileC_sram_1) ,  Some(me), List(x1540_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1588_inr_Foreach.sm.io.ctrDone := (x1588_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1588_inr_Foreach.backpressure := true.B | x1588_inr_Foreach.sm.io.doneLatch
      x1588_inr_Foreach.forwardpressure := (true.B) && (true.B) | x1588_inr_Foreach.sm.io.doneLatch
      x1588_inr_Foreach.sm.io.enableOut.zip(x1588_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1588_inr_Foreach.sm.io.break := false.B
      x1588_inr_Foreach.mask := ~x1588_inr_Foreach.cchain.head.output.noop & b1528 & b1006 & b925 & b914 & b1168
      x1588_inr_Foreach.configure("x1588_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1588_inr_Foreach.kernel()
      val x1611_inr_Foreach = new x1611_inr_Foreach_kernel(List(b1168,b1529,b925,b1006,b914), List(b1164,b1525), List(x934_tileA_sram_0,x1017_tileB_sram_0,x1018_tileC_sram_0,x1019_tileC_sram_1) ,  Some(me), List(x1541_ctrchain), 2, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1611_inr_Foreach.sm.io.ctrDone := (x1611_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1611_inr_Foreach.backpressure := true.B | x1611_inr_Foreach.sm.io.doneLatch
      x1611_inr_Foreach.forwardpressure := (true.B) && (true.B) | x1611_inr_Foreach.sm.io.doneLatch
      x1611_inr_Foreach.sm.io.enableOut.zip(x1611_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1611_inr_Foreach.sm.io.break := false.B
      x1611_inr_Foreach.mask := ~x1611_inr_Foreach.cchain.head.output.noop & b1006 & b925 & b1529 & b914 & b1168
      x1611_inr_Foreach.configure("x1611_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1611_inr_Foreach.kernel()
      val x1634_inr_Foreach = new x1634_inr_Foreach_kernel(List(b1168,b925,b1006,b1530,b914), List(b1164,b1526), List(x934_tileA_sram_0,x1017_tileB_sram_0,x1018_tileC_sram_0,x1019_tileC_sram_1) ,  Some(me), List(x1542_ctrchain), 3, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1634_inr_Foreach.sm.io.ctrDone := (x1634_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1634_inr_Foreach.backpressure := true.B | x1634_inr_Foreach.sm.io.doneLatch
      x1634_inr_Foreach.forwardpressure := (true.B) && (true.B) | x1634_inr_Foreach.sm.io.doneLatch
      x1634_inr_Foreach.sm.io.enableOut.zip(x1634_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1634_inr_Foreach.sm.io.break := false.B
      x1634_inr_Foreach.mask := ~x1634_inr_Foreach.cchain.head.output.noop & b1006 & b925 & b1530 & b914 & b1168
      x1634_inr_Foreach.configure("x1634_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1634_inr_Foreach.kernel()
    }
    val module = Module(new x1635_concrete(sm.p.depth)); module.io := DontCare
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
/** END ParallelPipe x1635 **/
