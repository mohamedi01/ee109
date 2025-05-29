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

/** Hierarchy: x362 -> x367 -> x368 -> x375 **/
/** BEGIN None x362_outr_UnitPipe **/
class x362_outr_UnitPipe_kernel(
  list_x249_outDram: List[FixedPoint],
  list_x336: List[DecoupledIO[AppCommandDense]],
  list_x252_outSram_0: List[StandardInterface],
  list_x337: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x362_outr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x362_outr_UnitPipe_iiCtr"))
  
  abstract class x362_outr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x249_outDram = Input(new FixedPoint(true, 64, 0))
      val in_x252_outSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x252_outSram_0_p").asInstanceOf[MemParams] ))
      val in_x336 = Decoupled(new AppCommandDense(ModuleParams.getParams("x336_p").asInstanceOf[(Int,Int)] ))
      val in_x337 = Decoupled(new AppStoreData(ModuleParams.getParams("x337_p").asInstanceOf[(Int,Int)] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x249_outDram = {io.in_x249_outDram} 
    def x252_outSram_0 = {io.in_x252_outSram_0} ; io.in_x252_outSram_0 := DontCare
    def x336 = {io.in_x336} 
    def x337 = {io.in_x337} 
  }
  def connectWires0(module: x362_outr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x249_outDram <> x249_outDram
    x252_outSram_0.connectLedger(module.io.in_x252_outSram_0)
    module.io.in_x336 <> x336
    module.io.in_x337 <> x337
  }
  val x249_outDram = list_x249_outDram(0)
  val x336 = list_x336(0)
  val x252_outSram_0 = list_x252_outSram_0(0)
  val x337 = list_x337(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x362_outr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x362_outr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x362_outr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x339_reg = (new x339_reg).m.io.asInstanceOf[StandardInterface]
      val x340_reg = (new x340_reg).m.io.asInstanceOf[StandardInterface]
      val x347_inr_UnitPipe = new x347_inr_UnitPipe_kernel(List(x249_outDram), List(x336), List(x340_reg,x339_reg) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, rr)
      x347_inr_UnitPipe.sm.io.ctrDone := risingEdge(x347_inr_UnitPipe.sm.io.ctrInc)
      x347_inr_UnitPipe.backpressure := x336.ready | x347_inr_UnitPipe.sm.io.doneLatch
      x347_inr_UnitPipe.forwardpressure := (true.B) && (true.B) | x347_inr_UnitPipe.sm.io.doneLatch
      x347_inr_UnitPipe.sm.io.enableOut.zip(x347_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x347_inr_UnitPipe.sm.io.break := false.B
      x347_inr_UnitPipe.mask := true.B & true.B
      x347_inr_UnitPipe.configure("x347_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x347_inr_UnitPipe.kernel()
      val x372_rd_x340 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x372_rd_x340""")
      val x372_rd_x340_banks = List[UInt]()
      val x372_rd_x340_ofs = List[UInt]()
      val x372_rd_x340_en = List[Bool](true.B)
      val x372_rd_x340_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x372_rd_x340_shared_en")
      x372_rd_x340.toSeq.zip(x340_reg.connectRPort(372, x372_rd_x340_banks, x372_rd_x340_ofs, io.sigsIn.backpressure, x372_rd_x340_en.map(_ && x372_rd_x340_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x349_ctr = new CtrObject(Left(Some(0)), Right(x372_rd_x340), Left(Some(1)), 1, 32, false)
      val x350_ctrchain = (new CChainObject(List[CtrObject](x349_ctr), "x350_ctrchain")).cchain.io 
      x350_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x350_ctrchain_p", (x350_ctrchain.par, x350_ctrchain.widths))
      val x361_inr_Foreach = new x361_inr_Foreach_kernel(List(x252_outSram_0,x339_reg), List(x337) ,  Some(me), List(x350_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, rr)
      x361_inr_Foreach.sm.io.ctrDone := (x361_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x361_inr_Foreach.backpressure := x337.ready | x361_inr_Foreach.sm.io.doneLatch
      x361_inr_Foreach.forwardpressure := (true.B) && (true.B) | x361_inr_Foreach.sm.io.doneLatch
      x361_inr_Foreach.sm.io.enableOut.zip(x361_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x361_inr_Foreach.sm.io.break := false.B
      x361_inr_Foreach.mask := ~x361_inr_Foreach.cchain.head.output.noop & true.B
      x361_inr_Foreach.configure("x361_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x361_inr_Foreach.kernel()
    }
    val module = Module(new x362_outr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x362_outr_UnitPipe **/
