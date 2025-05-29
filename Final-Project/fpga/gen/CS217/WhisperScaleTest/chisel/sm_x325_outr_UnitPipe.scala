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

/** Hierarchy: x325 -> x330 -> x331 -> x201 **/
/** BEGIN None x325_outr_UnitPipe **/
class x325_outr_UnitPipe_kernel(
  list_x251_outDRAM: List[FixedPoint],
  list_x299: List[DecoupledIO[AppCommandDense]],
  list_x253_buf_0: List[StandardInterface],
  list_x300: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x325_outr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x325_outr_UnitPipe_iiCtr"))
  
  abstract class x325_outr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x253_buf_0 = Flipped(new StandardInterface(ModuleParams.getParams("x253_buf_0_p").asInstanceOf[MemParams] ))
      val in_x251_outDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x299 = Decoupled(new AppCommandDense(ModuleParams.getParams("x299_p").asInstanceOf[(Int,Int)] ))
      val in_x300 = Decoupled(new AppStoreData(ModuleParams.getParams("x300_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x253_buf_0 = {io.in_x253_buf_0} ; io.in_x253_buf_0 := DontCare
    def x251_outDRAM = {io.in_x251_outDRAM} 
    def x299 = {io.in_x299} 
    def x300 = {io.in_x300} 
  }
  def connectWires0(module: x325_outr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x253_buf_0.connectLedger(module.io.in_x253_buf_0)
    module.io.in_x251_outDRAM <> x251_outDRAM
    module.io.in_x299 <> x299
    module.io.in_x300 <> x300
  }
  val x251_outDRAM = list_x251_outDRAM(0)
  val x299 = list_x299(0)
  val x253_buf_0 = list_x253_buf_0(0)
  val x300 = list_x300(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x325_outr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x325_outr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x325_outr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x325_outr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x325_outr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x325_outr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x325_outr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X325_instrctr, cycles_x325_outr_UnitPipe.io.count, iters_x325_outr_UnitPipe.io.count, 0.U, 0.U)
      val x302_reg = (new x302_reg).m.io.asInstanceOf[StandardInterface]
      val x303_reg = (new x303_reg).m.io.asInstanceOf[StandardInterface]
      val x310_inr_UnitPipe = new x310_inr_UnitPipe_kernel(List(x251_outDRAM), List(x299), List(x302_reg,x303_reg) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x310_inr_UnitPipe.sm.io.ctrDone := risingEdge(x310_inr_UnitPipe.sm.io.ctrInc)
      x310_inr_UnitPipe.backpressure := x299.ready | x310_inr_UnitPipe.sm.io.doneLatch
      x310_inr_UnitPipe.forwardpressure := (true.B) && (true.B) | x310_inr_UnitPipe.sm.io.doneLatch
      x310_inr_UnitPipe.sm.io.enableOut.zip(x310_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x310_inr_UnitPipe.sm.io.break := false.B
      x310_inr_UnitPipe.mask := true.B & true.B
      x310_inr_UnitPipe.configure("x310_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x310_inr_UnitPipe.kernel()
      val x373_rd_x303 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x373_rd_x303""")
      val x373_rd_x303_banks = List[UInt]()
      val x373_rd_x303_ofs = List[UInt]()
      val x373_rd_x303_en = List[Bool](true.B)
      val x373_rd_x303_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x373_rd_x303_shared_en")
      x373_rd_x303.toSeq.zip(x303_reg.connectRPort(373, x373_rd_x303_banks, x373_rd_x303_ofs, io.sigsIn.backpressure, x373_rd_x303_en.map(_ && x373_rd_x303_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x312_ctr = new CtrObject(Left(Some(0)), Right(x373_rd_x303), Left(Some(1)), 1, 32, false)
      val x313_ctrchain = (new CChainObject(List[CtrObject](x312_ctr), "x313_ctrchain")).cchain.io 
      x313_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x313_ctrchain_p", (x313_ctrchain.par, x313_ctrchain.widths))
      val x324_inr_Foreach = new x324_inr_Foreach_kernel(List(x253_buf_0,x302_reg), List(x300) ,  Some(me), List(x313_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x324_inr_Foreach.sm.io.ctrDone := (x324_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x324_inr_Foreach.backpressure := x300.ready | x324_inr_Foreach.sm.io.doneLatch
      x324_inr_Foreach.forwardpressure := (true.B) && (true.B) | x324_inr_Foreach.sm.io.doneLatch
      x324_inr_Foreach.sm.io.enableOut.zip(x324_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x324_inr_Foreach.sm.io.break := false.B
      x324_inr_Foreach.mask := ~x324_inr_Foreach.cchain.head.output.noop & true.B
      x324_inr_Foreach.configure("x324_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x324_inr_Foreach.kernel()
    }
    val module = Module(new x325_outr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x325_outr_UnitPipe **/
