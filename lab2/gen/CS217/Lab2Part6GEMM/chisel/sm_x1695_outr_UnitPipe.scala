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

/** Hierarchy: x1695 -> x1700 -> x1701 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1695_outr_UnitPipe **/
class x1695_outr_UnitPipe_kernel(
  list_x1640: List[DecoupledIO[AppStoreData]],
  list_b1645: List[Bool],
  list_x879_N: List[UInt],
  list_x1639: List[DecoupledIO[AppCommandDense]],
  list_b1005: List[FixedPoint],
  list_x1011_reg: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x1695_outr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1695_outr_UnitPipe_iiCtr"))
  
  abstract class x1695_outr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b1005 = Input(new FixedPoint(true, 32, 0))
      val in_x1640 = Decoupled(new AppStoreData(ModuleParams.getParams("x1640_p").asInstanceOf[(Int,Int)] ))
      val in_b1645 = Input(Bool())
      val in_b924 = Input(new FixedPoint(true, 32, 0))
      val in_x879_N = Input(UInt(64.W))
      val in_b1644 = Input(new FixedPoint(true, 32, 0))
      val in_x906_c = Input(new FixedPoint(true, 64, 0))
      val in_x1011_reg = Flipped(new NBufInterface(ModuleParams.getParams("x1011_reg_p").asInstanceOf[NBufParams] ))
      val in_x1639 = Decoupled(new AppCommandDense(ModuleParams.getParams("x1639_p").asInstanceOf[(Int,Int)] ))
      val in_x1019_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1019_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def b1005 = {io.in_b1005} 
    def x1640 = {io.in_x1640} 
    def b1645 = {io.in_b1645} 
    def b924 = {io.in_b924} 
    def x879_N = {io.in_x879_N} 
    def b1644 = {io.in_b1644} 
    def x906_c = {io.in_x906_c} 
    def x1011_reg = {io.in_x1011_reg} ; io.in_x1011_reg := DontCare
    def x1639 = {io.in_x1639} 
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
  }
  def connectWires0(module: x1695_outr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b1005 <> b1005
    module.io.in_x1640 <> x1640
    module.io.in_b1645 <> b1645
    module.io.in_b924 <> b924
    module.io.in_x879_N <> x879_N
    module.io.in_b1644 <> b1644
    module.io.in_x906_c <> x906_c
    x1011_reg.connectLedger(module.io.in_x1011_reg)
    module.io.in_x1639 <> x1639
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
  }
  val x1640 = list_x1640(0)
  val b1645 = list_b1645(0)
  val x879_N = list_x879_N(0)
  val x1639 = list_x1639(0)
  val b1005 = list_b1005(0)
  val b924 = list_b1005(1)
  val b1644 = list_b1005(2)
  val x906_c = list_b1005(3)
  val x1011_reg = list_x1011_reg(0)
  val x1019_tileC_sram_1 = list_x1011_reg(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1695_outr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x1695_outr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1695_outr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1695_outr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x1695_outr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x1695_outr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x1695_outr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1695_instrctr, cycles_x1695_outr_UnitPipe.io.count, iters_x1695_outr_UnitPipe.io.count, 0.U, 0.U)
      val x1646_reg = (new x1646_reg).m.io.asInstanceOf[StandardInterface]
      val x1647_reg = (new x1647_reg).m.io.asInstanceOf[StandardInterface]
      val x1648_reg = (new x1648_reg).m.io.asInstanceOf[StandardInterface]
      val x1672_inr_UnitPipe = new x1672_inr_UnitPipe_kernel(List(x1648_reg,x1647_reg,x1646_reg), List(x1011_reg), List(x879_N), List(x1639), List(b1005,b924,b1644,x906_c) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1672_inr_UnitPipe.sm.io.ctrDone := risingEdge(x1672_inr_UnitPipe.sm.io.ctrInc)
      x1672_inr_UnitPipe.backpressure := x1639.ready | x1672_inr_UnitPipe.sm.io.doneLatch
      x1672_inr_UnitPipe.forwardpressure := (true.B) && (true.B) | x1672_inr_UnitPipe.sm.io.doneLatch
      x1672_inr_UnitPipe.sm.io.enableOut.zip(x1672_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x1672_inr_UnitPipe.sm.io.break := false.B
      x1672_inr_UnitPipe.mask := true.B & true.B
      x1672_inr_UnitPipe.configure("x1672_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1672_inr_UnitPipe.kernel()
      val x1792_rd_x1648 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1792_rd_x1648""")
      val x1792_rd_x1648_banks = List[UInt]()
      val x1792_rd_x1648_ofs = List[UInt]()
      val x1792_rd_x1648_en = List[Bool](true.B)
      val x1792_rd_x1648_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1792_rd_x1648_shared_en")
      x1792_rd_x1648.toSeq.zip(x1648_reg.connectRPort(1792, x1792_rd_x1648_banks, x1792_rd_x1648_ofs, io.sigsIn.backpressure, x1792_rd_x1648_en.map(_ && x1792_rd_x1648_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1674_ctr = new CtrObject(Left(Some(0)), Right(x1792_rd_x1648), Left(Some(1)), 1, 32, false)
      val x1675_ctrchain = (new CChainObject(List[CtrObject](x1674_ctr), "x1675_ctrchain")).cchain.io 
      x1675_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1675_ctrchain_p", (x1675_ctrchain.par, x1675_ctrchain.widths))
      val x1694_inr_Foreach = new x1694_inr_Foreach_kernel(List(b1644), List(x1019_tileC_sram_1), List(x1647_reg,x1646_reg), List(x1640) ,  Some(me), List(x1675_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1694_inr_Foreach.sm.io.ctrDone := (x1694_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1694_inr_Foreach.backpressure := x1640.ready | x1694_inr_Foreach.sm.io.doneLatch
      x1694_inr_Foreach.forwardpressure := (true.B) && (true.B) | x1694_inr_Foreach.sm.io.doneLatch
      x1694_inr_Foreach.sm.io.enableOut.zip(x1694_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1694_inr_Foreach.sm.io.break := false.B
      x1694_inr_Foreach.mask := ~x1694_inr_Foreach.cchain.head.output.noop & true.B
      x1694_inr_Foreach.configure("x1694_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1694_inr_Foreach.kernel()
    }
    val module = Module(new x1695_outr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x1695_outr_UnitPipe **/
