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

/** Hierarchy: x476 -> x481 -> x482 -> x490 -> x491 **/
/** BEGIN None x476_outr_UnitPipe **/
class x476_outr_UnitPipe_kernel(
  list_x446: List[DecoupledIO[AppStoreData]],
  list_x330_outDram: List[FixedPoint],
  list_x445: List[DecoupledIO[AppCommandDense]],
  list_x444_outSram_0: List[StandardInterface],
  list_x326_argIn: List[UInt],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x476_outr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x476_outr_UnitPipe_iiCtr"))
  
  abstract class x476_outr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x444_outSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x444_outSram_0_p").asInstanceOf[MemParams] ))
      val in_x445 = Decoupled(new AppCommandDense(ModuleParams.getParams("x445_p").asInstanceOf[(Int,Int)] ))
      val in_x330_outDram = Input(new FixedPoint(true, 64, 0))
      val in_x446 = Decoupled(new AppStoreData(ModuleParams.getParams("x446_p").asInstanceOf[(Int,Int)] ))
      val in_x326_argIn = Input(UInt(64.W))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x444_outSram_0 = {io.in_x444_outSram_0} ; io.in_x444_outSram_0 := DontCare
    def x445 = {io.in_x445} 
    def x330_outDram = {io.in_x330_outDram} 
    def x446 = {io.in_x446} 
    def x326_argIn = {io.in_x326_argIn} 
  }
  def connectWires0(module: x476_outr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    x444_outSram_0.connectLedger(module.io.in_x444_outSram_0)
    module.io.in_x445 <> x445
    module.io.in_x330_outDram <> x330_outDram
    module.io.in_x446 <> x446
    module.io.in_x326_argIn <> x326_argIn
  }
  val x446 = list_x446(0)
  val x330_outDram = list_x330_outDram(0)
  val x445 = list_x445(0)
  val x444_outSram_0 = list_x444_outSram_0(0)
  val x326_argIn = list_x326_argIn(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x476_outr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x476_outr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x476_outr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x448_reg = (new x448_reg).m.io.asInstanceOf[StandardInterface]
      val x449_reg = (new x449_reg).m.io.asInstanceOf[StandardInterface]
      val x461_inr_UnitPipe = new x461_inr_UnitPipe_kernel(List(x330_outDram), List(x445), List(x449_reg,x448_reg), List(x326_argIn) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, rr)
      x461_inr_UnitPipe.sm.io.ctrDone := risingEdge(x461_inr_UnitPipe.sm.io.ctrInc)
      x461_inr_UnitPipe.backpressure := x445.ready | x461_inr_UnitPipe.sm.io.doneLatch
      x461_inr_UnitPipe.forwardpressure := (true.B) && (true.B) | x461_inr_UnitPipe.sm.io.doneLatch
      x461_inr_UnitPipe.sm.io.enableOut.zip(x461_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x461_inr_UnitPipe.sm.io.break := false.B
      x461_inr_UnitPipe.mask := true.B & true.B
      x461_inr_UnitPipe.configure("x461_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x461_inr_UnitPipe.kernel()
      val x486_rd_x449 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x486_rd_x449""")
      val x486_rd_x449_banks = List[UInt]()
      val x486_rd_x449_ofs = List[UInt]()
      val x486_rd_x449_en = List[Bool](true.B)
      val x486_rd_x449_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x486_rd_x449_shared_en")
      x486_rd_x449.toSeq.zip(x449_reg.connectRPort(486, x486_rd_x449_banks, x486_rd_x449_ofs, io.sigsIn.backpressure, x486_rd_x449_en.map(_ && x486_rd_x449_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x463_ctr = new CtrObject(Left(Some(0)), Right(x486_rd_x449), Left(Some(1)), 1, 32, false)
      val x464_ctrchain = (new CChainObject(List[CtrObject](x463_ctr), "x464_ctrchain")).cchain.io 
      x464_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x464_ctrchain_p", (x464_ctrchain.par, x464_ctrchain.widths))
      val x475_inr_Foreach = new x475_inr_Foreach_kernel(List(x448_reg,x444_outSram_0), List(x446) ,  Some(me), List(x464_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, rr)
      x475_inr_Foreach.sm.io.ctrDone := (x475_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x475_inr_Foreach.backpressure := x446.ready | x475_inr_Foreach.sm.io.doneLatch
      x475_inr_Foreach.forwardpressure := (true.B) && (true.B) | x475_inr_Foreach.sm.io.doneLatch
      x475_inr_Foreach.sm.io.enableOut.zip(x475_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x475_inr_Foreach.sm.io.break := false.B
      x475_inr_Foreach.mask := ~x475_inr_Foreach.cchain.head.output.noop & true.B
      x475_inr_Foreach.configure("x475_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x475_inr_Foreach.kernel()
    }
    val module = Module(new x476_outr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x476_outr_UnitPipe **/
