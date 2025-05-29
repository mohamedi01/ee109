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

/** Hierarchy: x296 -> x301 -> x302 -> x196 **/
/** BEGIN None x296_outr_UnitPipe **/
class x296_outr_UnitPipe_kernel(
  list_x266: List[DecoupledIO[AppStoreData]],
  list_x210_outDram: List[FixedPoint],
  list_x206_argIn: List[UInt],
  list_x214_buf_0: List[StandardInterface],
  list_x265: List[DecoupledIO[AppCommandDense]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x296_outr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x296_outr_UnitPipe_iiCtr"))
  
  abstract class x296_outr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x265 = Decoupled(new AppCommandDense(ModuleParams.getParams("x265_p").asInstanceOf[(Int,Int)] ))
      val in_x206_argIn = Input(UInt(64.W))
      val in_x266 = Decoupled(new AppStoreData(ModuleParams.getParams("x266_p").asInstanceOf[(Int,Int)] ))
      val in_x214_buf_0 = Flipped(new StandardInterface(ModuleParams.getParams("x214_buf_0_p").asInstanceOf[MemParams] ))
      val in_x210_outDram = Input(new FixedPoint(true, 64, 0))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x265 = {io.in_x265} 
    def x206_argIn = {io.in_x206_argIn} 
    def x266 = {io.in_x266} 
    def x214_buf_0 = {io.in_x214_buf_0} ; io.in_x214_buf_0 := DontCare
    def x210_outDram = {io.in_x210_outDram} 
  }
  def connectWires0(module: x296_outr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x265 <> x265
    module.io.in_x206_argIn <> x206_argIn
    module.io.in_x266 <> x266
    x214_buf_0.connectLedger(module.io.in_x214_buf_0)
    module.io.in_x210_outDram <> x210_outDram
  }
  val x266 = list_x266(0)
  val x210_outDram = list_x210_outDram(0)
  val x206_argIn = list_x206_argIn(0)
  val x214_buf_0 = list_x214_buf_0(0)
  val x265 = list_x265(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x296_outr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x296_outr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x296_outr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x268_reg = (new x268_reg).m.io.asInstanceOf[StandardInterface]
      val x269_reg = (new x269_reg).m.io.asInstanceOf[StandardInterface]
      val x281_inr_UnitPipe = new x281_inr_UnitPipe_kernel(List(x210_outDram), List(x265), List(x269_reg,x268_reg), List(x206_argIn) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, rr)
      x281_inr_UnitPipe.sm.io.ctrDone := risingEdge(x281_inr_UnitPipe.sm.io.ctrInc)
      x281_inr_UnitPipe.backpressure := x265.ready | x281_inr_UnitPipe.sm.io.doneLatch
      x281_inr_UnitPipe.forwardpressure := (true.B) && (true.B) | x281_inr_UnitPipe.sm.io.doneLatch
      x281_inr_UnitPipe.sm.io.enableOut.zip(x281_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x281_inr_UnitPipe.sm.io.break := false.B
      x281_inr_UnitPipe.mask := true.B & true.B
      x281_inr_UnitPipe.configure("x281_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x281_inr_UnitPipe.kernel()
      val x305_rd_x269 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x305_rd_x269""")
      val x305_rd_x269_banks = List[UInt]()
      val x305_rd_x269_ofs = List[UInt]()
      val x305_rd_x269_en = List[Bool](true.B)
      val x305_rd_x269_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x305_rd_x269_shared_en")
      x305_rd_x269.toSeq.zip(x269_reg.connectRPort(305, x305_rd_x269_banks, x305_rd_x269_ofs, io.sigsIn.backpressure, x305_rd_x269_en.map(_ && x305_rd_x269_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x283_ctr = new CtrObject(Left(Some(0)), Right(x305_rd_x269), Left(Some(1)), 1, 32, false)
      val x284_ctrchain = (new CChainObject(List[CtrObject](x283_ctr), "x284_ctrchain")).cchain.io 
      x284_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x284_ctrchain_p", (x284_ctrchain.par, x284_ctrchain.widths))
      val x295_inr_Foreach = new x295_inr_Foreach_kernel(List(x214_buf_0,x268_reg), List(x266) ,  Some(me), List(x284_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, rr)
      x295_inr_Foreach.sm.io.ctrDone := (x295_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x295_inr_Foreach.backpressure := x266.ready | x295_inr_Foreach.sm.io.doneLatch
      x295_inr_Foreach.forwardpressure := (true.B) && (true.B) | x295_inr_Foreach.sm.io.doneLatch
      x295_inr_Foreach.sm.io.enableOut.zip(x295_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x295_inr_Foreach.sm.io.break := false.B
      x295_inr_Foreach.mask := ~x295_inr_Foreach.cchain.head.output.noop & true.B
      x295_inr_Foreach.configure("x295_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x295_inr_Foreach.kernel()
    }
    val module = Module(new x296_outr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x296_outr_UnitPipe **/
