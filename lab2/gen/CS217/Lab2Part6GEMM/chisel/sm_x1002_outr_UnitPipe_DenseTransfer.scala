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

/** Hierarchy: x1002 -> x1703 -> x1704 -> x810 **/
/** BEGIN Some(DenseTransfer) x1002_outr_UnitPipe_DenseTransfer **/
class x1002_outr_UnitPipe_DenseTransfer_kernel(
  list_x937: List[DecoupledIO[AppLoadData]],
  list_x935: List[DecoupledIO[AppCommandDense]],
  list_x934_tileA_sram_0: List[NBufInterface],
  list_b913: List[FixedPoint],
  list_b925: List[Bool],
  list_x880_K: List[UInt],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 2, isFSM = false   , latency = 0.0.toInt, myName = "x1002_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1002_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x1002_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b913 = Input(new FixedPoint(true, 32, 0))
      val in_x934_tileA_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x934_tileA_sram_0_p").asInstanceOf[NBufParams] ))
      val in_b924 = Input(new FixedPoint(true, 32, 0))
      val in_x937 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x937_p").asInstanceOf[(Int, Int)] )))
      val in_b925 = Input(Bool())
      val in_x916_reg = Flipped(new NBufInterface(ModuleParams.getParams("x916_reg_p").asInstanceOf[NBufParams] ))
      val in_x900_a = Input(new FixedPoint(true, 64, 0))
      val in_x880_K = Input(UInt(64.W))
      val in_x927_reg = Flipped(new NBufInterface(ModuleParams.getParams("x927_reg_p").asInstanceOf[NBufParams] ))
      val in_b914 = Input(Bool())
      val in_x935 = Decoupled(new AppCommandDense(ModuleParams.getParams("x935_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 2, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 2))
      val rr = Input(Bool())
    })
    def b913 = {io.in_b913} 
    def x934_tileA_sram_0 = {io.in_x934_tileA_sram_0} ; io.in_x934_tileA_sram_0 := DontCare
    def b924 = {io.in_b924} 
    def x937 = {io.in_x937} 
    def b925 = {io.in_b925} 
    def x916_reg = {io.in_x916_reg} ; io.in_x916_reg := DontCare
    def x900_a = {io.in_x900_a} 
    def x880_K = {io.in_x880_K} 
    def x927_reg = {io.in_x927_reg} ; io.in_x927_reg := DontCare
    def b914 = {io.in_b914} 
    def x935 = {io.in_x935} 
  }
  def connectWires0(module: x1002_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b913 <> b913
    x934_tileA_sram_0.connectLedger(module.io.in_x934_tileA_sram_0)
    module.io.in_b924 <> b924
    module.io.in_x937 <> x937
    module.io.in_b925 <> b925
    x916_reg.connectLedger(module.io.in_x916_reg)
    module.io.in_x900_a <> x900_a
    module.io.in_x880_K <> x880_K
    x927_reg.connectLedger(module.io.in_x927_reg)
    module.io.in_b914 <> b914
    module.io.in_x935 <> x935
  }
  val x937 = list_x937(0)
  val x935 = list_x935(0)
  val x934_tileA_sram_0 = list_x934_tileA_sram_0(0)
  val x916_reg = list_x934_tileA_sram_0(1)
  val x927_reg = list_x934_tileA_sram_0(2)
  val b913 = list_b913(0)
  val b924 = list_b913(1)
  val x900_a = list_b913(2)
  val b925 = list_b925(0)
  val b914 = list_b925(1)
  val x880_K = list_x880_K(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1002_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x1002_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1002_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1002_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      val iters_x1002_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      cycles_x1002_outr_UnitPipe_DenseTransfer.io.enable := io.sigsIn.baseEn
      iters_x1002_outr_UnitPipe_DenseTransfer.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1002_instrctr, cycles_x1002_outr_UnitPipe_DenseTransfer.io.count, iters_x1002_outr_UnitPipe_DenseTransfer.io.count, 0.U, 0.U)
      val x936_fifo = (new x936_fifo).m.io.asInstanceOf[FIFOInterface]
      val x1760_rd_x927 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1760_rd_x927""")
      val x1760_rd_x927_banks = List[UInt]()
      val x1760_rd_x927_ofs = List[UInt]()
      val x1760_rd_x927_en = List[Bool](true.B)
      val x1760_rd_x927_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1760_rd_x927_shared_en")
      x1760_rd_x927.toSeq.zip(x927_reg.connectRPort(1760, x1760_rd_x927_banks, x1760_rd_x927_ofs, io.sigsIn.backpressure, x1760_rd_x927_en.map(_ && x1760_rd_x927_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x938_ctr = new CtrObject(Left(Some(0)), Right(x1760_rd_x927), Left(Some(1)), 1, 32, false)
      val x939_ctrchain = (new CChainObject(List[CtrObject](x938_ctr), "x939_ctrchain")).cchain.io 
      x939_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x939_ctrchain_p", (x939_ctrchain.par, x939_ctrchain.widths))
      val x964_inr_Foreach = new x964_inr_Foreach_kernel(List(x916_reg), List(x936_fifo), List(x935), List(b913,b924,x900_a), List(x880_K) ,  Some(me), List(x939_ctrchain), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x964_inr_Foreach.sm.io.ctrDone := (x964_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x964_inr_Foreach.backpressure := (~x936_fifo.full.D(11.8-1) | ~(x936_fifo.active(0).out)) & x935.ready | x964_inr_Foreach.sm.io.doneLatch
      x964_inr_Foreach.forwardpressure := (true.B) && (true.B) | x964_inr_Foreach.sm.io.doneLatch
      x964_inr_Foreach.sm.io.enableOut.zip(x964_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x964_inr_Foreach.sm.io.break := false.B
      x964_inr_Foreach.mask := ~x964_inr_Foreach.cchain.head.output.noop & true.B
      x964_inr_Foreach.configure("x964_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x964_inr_Foreach.kernel()
      val x1761_rd_x927 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1761_rd_x927""")
      val x1761_rd_x927_banks = List[UInt]()
      val x1761_rd_x927_ofs = List[UInt]()
      val x1761_rd_x927_en = List[Bool](true.B)
      val x1761_rd_x927_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1761_rd_x927_shared_en")
      x1761_rd_x927.toSeq.zip(x927_reg.connectRPort(1761, x1761_rd_x927_banks, x1761_rd_x927_ofs, io.sigsIn.backpressure, x1761_rd_x927_en.map(_ && x1761_rd_x927_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x966_ctr = new CtrObject(Left(Some(0)), Right(x1761_rd_x927), Left(Some(1)), 1, 32, false)
      val x967_ctrchain = (new CChainObject(List[CtrObject](x966_ctr), "x967_ctrchain")).cchain.io 
      x967_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x967_ctrchain_p", (x967_ctrchain.par, x967_ctrchain.widths))
      val x1001_outr_Foreach = new x1001_outr_Foreach_kernel(List(x936_fifo), List(x937), List(x934_tileA_sram_0) ,  Some(me), List(x967_ctrchain), 1, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1001_outr_Foreach.sm.io.ctrDone := (x1001_outr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1001_outr_Foreach.backpressure := true.B | x1001_outr_Foreach.sm.io.doneLatch
      x1001_outr_Foreach.forwardpressure := (true.B) && (true.B) | x1001_outr_Foreach.sm.io.doneLatch
      x1001_outr_Foreach.sm.io.enableOut.zip(x1001_outr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1001_outr_Foreach.sm.io.break := false.B
      x1001_outr_Foreach.mask := ~x1001_outr_Foreach.cchain.head.output.noop & true.B
      x1001_outr_Foreach.configure("x1001_outr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1001_outr_Foreach.kernel()
      x927_reg.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 1)
      x934_tileA_sram_0.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 0)
    }
    val module = Module(new x1002_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x1002_outr_UnitPipe_DenseTransfer **/
