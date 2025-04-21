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

/** Hierarchy: x1701 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN Some(DenseTransfer) x1701_outr_UnitPipe_DenseTransfer **/
class x1701_outr_UnitPipe_DenseTransfer_kernel(
  list_x1641: List[DecoupledIO[Bool]],
  list_x1640: List[DecoupledIO[AppStoreData]],
  list_x879_N: List[UInt],
  list_b1005: List[FixedPoint],
  list_x1639: List[DecoupledIO[AppCommandDense]],
  list_b925: List[Bool],
  list_x1011_reg: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Streaming, 1, isFSM = false   , latency = 0.0.toInt, myName = "x1701_outr_UnitPipe_DenseTransfer_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1701_outr_UnitPipe_DenseTransfer_iiCtr"))
  
  abstract class x1701_outr_UnitPipe_DenseTransfer_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b1005 = Input(new FixedPoint(true, 32, 0))
      val in_x1640 = Decoupled(new AppStoreData(ModuleParams.getParams("x1640_p").asInstanceOf[(Int,Int)] ))
      val in_x1641 = Flipped(Decoupled(Bool()))
      val in_b924 = Input(new FixedPoint(true, 32, 0))
      val in_b925 = Input(Bool())
      val in_x879_N = Input(UInt(64.W))
      val in_b1006 = Input(Bool())
      val in_x906_c = Input(new FixedPoint(true, 64, 0))
      val in_x1011_reg = Flipped(new NBufInterface(ModuleParams.getParams("x1011_reg_p").asInstanceOf[NBufParams] ))
      val in_x927_reg = Flipped(new NBufInterface(ModuleParams.getParams("x927_reg_p").asInstanceOf[NBufParams] ))
      val in_x1639 = Decoupled(new AppCommandDense(ModuleParams.getParams("x1639_p").asInstanceOf[(Int,Int)] ))
      val in_b914 = Input(Bool())
      val in_x1019_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1019_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def b1005 = {io.in_b1005} 
    def x1640 = {io.in_x1640} 
    def x1641 = {io.in_x1641} 
    def b924 = {io.in_b924} 
    def b925 = {io.in_b925} 
    def x879_N = {io.in_x879_N} 
    def b1006 = {io.in_b1006} 
    def x906_c = {io.in_x906_c} 
    def x1011_reg = {io.in_x1011_reg} ; io.in_x1011_reg := DontCare
    def x927_reg = {io.in_x927_reg} ; io.in_x927_reg := DontCare
    def x1639 = {io.in_x1639} 
    def b914 = {io.in_b914} 
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
  }
  def connectWires0(module: x1701_outr_UnitPipe_DenseTransfer_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b1005 <> b1005
    module.io.in_x1640 <> x1640
    module.io.in_x1641 <> x1641
    module.io.in_b924 <> b924
    module.io.in_b925 <> b925
    module.io.in_x879_N <> x879_N
    module.io.in_b1006 <> b1006
    module.io.in_x906_c <> x906_c
    x1011_reg.connectLedger(module.io.in_x1011_reg)
    x927_reg.connectLedger(module.io.in_x927_reg)
    module.io.in_x1639 <> x1639
    module.io.in_b914 <> b914
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
  }
  val x1641 = list_x1641(0)
  val x1640 = list_x1640(0)
  val x879_N = list_x879_N(0)
  val b1005 = list_b1005(0)
  val b924 = list_b1005(1)
  val x906_c = list_b1005(2)
  val x1639 = list_x1639(0)
  val b925 = list_b925(0)
  val b1006 = list_b925(1)
  val b914 = list_b925(2)
  val x1011_reg = list_x1011_reg(0)
  val x927_reg = list_x1011_reg(1)
  val x1019_tileC_sram_1 = list_x1011_reg(2)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1701_outr_UnitPipe_DenseTransfer")
    implicit val stack = ControllerStack.stack.toList
    class x1701_outr_UnitPipe_DenseTransfer_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1701_outr_UnitPipe_DenseTransfer_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1701_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      val iters_x1701_outr_UnitPipe_DenseTransfer = Module(new InstrumentationCounter())
      cycles_x1701_outr_UnitPipe_DenseTransfer.io.enable := io.sigsIn.baseEn
      iters_x1701_outr_UnitPipe_DenseTransfer.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1701_instrctr, cycles_x1701_outr_UnitPipe_DenseTransfer.io.count, iters_x1701_outr_UnitPipe_DenseTransfer.io.count, 0.U, 0.U)
      val x1791_rd_x927 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1791_rd_x927""")
      val x1791_rd_x927_banks = List[UInt]()
      val x1791_rd_x927_ofs = List[UInt]()
      val x1791_rd_x927_en = List[Bool](true.B)
      val x1791_rd_x927_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1791_rd_x927_shared_en")
      x1791_rd_x927.toSeq.zip(x927_reg.connectRPort(1791, x1791_rd_x927_banks, x1791_rd_x927_ofs, io.sigsIn.backpressure, x1791_rd_x927_en.map(_ && x1791_rd_x927_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1642_ctr = new CtrObject(Left(Some(0)), Right(x1791_rd_x927), Left(Some(1)), 1, 32, false)
      val x1643_ctrchain = (new CChainObject(List[CtrObject](x1642_ctr), "x1643_ctrchain")).cchain.io 
      x1643_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1643_ctrchain_p", (x1643_ctrchain.par, x1643_ctrchain.widths))
      val x1700_outr_Foreach = new x1700_outr_Foreach_kernel(List(x1641), List(x1640), List(x879_N), List(b1005,b924,x906_c), List(x1639), List(x1011_reg,x1019_tileC_sram_1) ,  Some(me), List(x1643_ctrchain), 0, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1700_outr_Foreach.sm.io.ctrDone := (x1700_outr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1700_outr_Foreach.backpressure := true.B | x1700_outr_Foreach.sm.io.doneLatch
      x1700_outr_Foreach.forwardpressure := (true.B) && (true.B) | x1700_outr_Foreach.sm.io.doneLatch
      x1700_outr_Foreach.sm.io.enableOut.zip(x1700_outr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1700_outr_Foreach.sm.io.break := false.B
      x1700_outr_Foreach.mask := ~x1700_outr_Foreach.cchain.head.output.noop & true.B
      x1700_outr_Foreach.configure("x1700_outr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1700_outr_Foreach.kernel()
      x1011_reg.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 3)
      x1019_tileC_sram_1.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 2)
    }
    val module = Module(new x1701_outr_UnitPipe_DenseTransfer_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x1701_outr_UnitPipe_DenseTransfer **/
