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

/** Hierarchy: x1242 -> x1247 -> x1248 -> x1335 -> x1250 -> x1251 -> x810 **/
/** BEGIN None x1242_outr_UnitPipe **/
class x1242_outr_UnitPipe_kernel(
  list_x1189: List[DecoupledIO[AppCommandDense]],
  list_x1190: List[DecoupledIO[AppStoreData]],
  list_x1018_tileC_sram_1: List[NBufInterface],
  list_x879_N: List[UInt],
  list_b924: List[FixedPoint],
  list_b1195: List[Bool],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x1242_outr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1242_outr_UnitPipe_iiCtr"))
  
  abstract class x1242_outr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x1190 = Decoupled(new AppStoreData(ModuleParams.getParams("x1190_p").asInstanceOf[(Int,Int)] ))
      val in_b924 = Input(new FixedPoint(true, 32, 0))
      val in_x879_N = Input(UInt(64.W))
      val in_b1195 = Input(Bool())
      val in_x906_c = Input(new FixedPoint(true, 64, 0))
      val in_b1194 = Input(new FixedPoint(true, 32, 0))
      val in_x1018_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_x1189 = Decoupled(new AppCommandDense(ModuleParams.getParams("x1189_p").asInstanceOf[(Int,Int)] ))
      val in_x1010_reg = Flipped(new NBufInterface(ModuleParams.getParams("x1010_reg_p").asInstanceOf[NBufParams] ))
      val in_b1004 = Input(new FixedPoint(true, 32, 0))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x1190 = {io.in_x1190} 
    def b924 = {io.in_b924} 
    def x879_N = {io.in_x879_N} 
    def b1195 = {io.in_b1195} 
    def x906_c = {io.in_x906_c} 
    def b1194 = {io.in_b1194} 
    def x1018_tileC_sram_1 = {io.in_x1018_tileC_sram_1} ; io.in_x1018_tileC_sram_1 := DontCare
    def x1189 = {io.in_x1189} 
    def x1010_reg = {io.in_x1010_reg} ; io.in_x1010_reg := DontCare
    def b1004 = {io.in_b1004} 
  }
  def connectWires0(module: x1242_outr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x1190 <> x1190
    module.io.in_b924 <> b924
    module.io.in_x879_N <> x879_N
    module.io.in_b1195 <> b1195
    module.io.in_x906_c <> x906_c
    module.io.in_b1194 <> b1194
    x1018_tileC_sram_1.connectLedger(module.io.in_x1018_tileC_sram_1)
    module.io.in_x1189 <> x1189
    x1010_reg.connectLedger(module.io.in_x1010_reg)
    module.io.in_b1004 <> b1004
  }
  val x1189 = list_x1189(0)
  val x1190 = list_x1190(0)
  val x1018_tileC_sram_1 = list_x1018_tileC_sram_1(0)
  val x1010_reg = list_x1018_tileC_sram_1(1)
  val x879_N = list_x879_N(0)
  val b924 = list_b924(0)
  val x906_c = list_b924(1)
  val b1194 = list_b924(2)
  val b1004 = list_b924(3)
  val b1195 = list_b1195(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1242_outr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x1242_outr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1242_outr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1242_outr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x1242_outr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x1242_outr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x1242_outr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1242_instrctr, cycles_x1242_outr_UnitPipe.io.count, iters_x1242_outr_UnitPipe.io.count, 0.U, 0.U)
      val x1196_reg = (new x1196_reg).m.io.asInstanceOf[StandardInterface]
      val x1197_reg = (new x1197_reg).m.io.asInstanceOf[StandardInterface]
      val x1198_reg = (new x1198_reg).m.io.asInstanceOf[StandardInterface]
      val x1222_inr_UnitPipe = new x1222_inr_UnitPipe_kernel(List(x1189), List(x1196_reg,x1198_reg,x1197_reg), List(x879_N), List(b924,x906_c,b1194,b1004), List(x1010_reg) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1222_inr_UnitPipe.sm.io.ctrDone := risingEdge(x1222_inr_UnitPipe.sm.io.ctrInc)
      x1222_inr_UnitPipe.backpressure := x1189.ready | x1222_inr_UnitPipe.sm.io.doneLatch
      x1222_inr_UnitPipe.forwardpressure := (true.B) && (true.B) | x1222_inr_UnitPipe.sm.io.doneLatch
      x1222_inr_UnitPipe.sm.io.enableOut.zip(x1222_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x1222_inr_UnitPipe.sm.io.break := false.B
      x1222_inr_UnitPipe.mask := true.B & true.B
      x1222_inr_UnitPipe.configure("x1222_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1222_inr_UnitPipe.kernel()
      val x1321_rd_x1198 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1321_rd_x1198""")
      val x1321_rd_x1198_banks = List[UInt]()
      val x1321_rd_x1198_ofs = List[UInt]()
      val x1321_rd_x1198_en = List[Bool](true.B)
      val x1321_rd_x1198_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1321_rd_x1198_shared_en")
      x1321_rd_x1198.toSeq.zip(x1198_reg.connectRPort(1321, x1321_rd_x1198_banks, x1321_rd_x1198_ofs, io.sigsIn.backpressure, x1321_rd_x1198_en.map(_ && x1321_rd_x1198_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1224_ctr = new CtrObject(Left(Some(0)), Right(x1321_rd_x1198), Left(Some(1)), 1, 32, false)
      val x1225_ctrchain = (new CChainObject(List[CtrObject](x1224_ctr), "x1225_ctrchain")).cchain.io 
      x1225_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1225_ctrchain_p", (x1225_ctrchain.par, x1225_ctrchain.widths))
      val x1241_inr_Foreach = new x1241_inr_Foreach_kernel(List(b1194), List(x1018_tileC_sram_1), List(x1196_reg,x1197_reg), List(x1190) ,  Some(me), List(x1225_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1241_inr_Foreach.sm.io.ctrDone := (x1241_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1241_inr_Foreach.backpressure := x1190.ready | x1241_inr_Foreach.sm.io.doneLatch
      x1241_inr_Foreach.forwardpressure := (true.B) && (true.B) | x1241_inr_Foreach.sm.io.doneLatch
      x1241_inr_Foreach.sm.io.enableOut.zip(x1241_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1241_inr_Foreach.sm.io.break := false.B
      x1241_inr_Foreach.mask := ~x1241_inr_Foreach.cchain.head.output.noop & true.B
      x1241_inr_Foreach.configure("x1241_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1241_inr_Foreach.kernel()
    }
    val module = Module(new x1242_outr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x1242_outr_UnitPipe **/
