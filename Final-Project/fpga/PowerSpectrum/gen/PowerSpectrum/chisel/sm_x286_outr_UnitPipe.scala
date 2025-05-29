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

/** Hierarchy: x286 -> x287 -> x374 -> x375 **/
/** BEGIN None x286_outr_UnitPipe **/
class x286_outr_UnitPipe_kernel(
  list_x254_fifo: List[FIFOInterface],
  list_x255: List[DecoupledIO[AppLoadData]],
  list_x250_realSram_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x286_outr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x286_outr_UnitPipe_iiCtr"))
  
  abstract class x286_outr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x255 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x255_p").asInstanceOf[(Int, Int)] )))
      val in_x250_realSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x250_realSram_0_p").asInstanceOf[MemParams] ))
      val in_x254_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x254_fifo_p").asInstanceOf[MemParams] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x255 = {io.in_x255} 
    def x250_realSram_0 = {io.in_x250_realSram_0} ; io.in_x250_realSram_0 := DontCare
    def x254_fifo = {io.in_x254_fifo} ; io.in_x254_fifo := DontCare
  }
  def connectWires0(module: x286_outr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x255 <> x255
    x250_realSram_0.connectLedger(module.io.in_x250_realSram_0)
    x254_fifo.connectLedger(module.io.in_x254_fifo)
  }
  val x254_fifo = list_x254_fifo(0)
  val x255 = list_x255(0)
  val x250_realSram_0 = list_x250_realSram_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x286_outr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x286_outr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x286_outr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val x264_reg = (new x264_reg).m.io.asInstanceOf[StandardInterface]
      val x265_reg = (new x265_reg).m.io.asInstanceOf[StandardInterface]
      val x272_inr_UnitPipe = new x272_inr_UnitPipe_kernel(List(x254_fifo), List(x265_reg,x264_reg) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, rr)
      x272_inr_UnitPipe.sm.io.ctrDone := risingEdge(x272_inr_UnitPipe.sm.io.ctrInc)
      x272_inr_UnitPipe.backpressure := true.B | x272_inr_UnitPipe.sm.io.doneLatch
      x272_inr_UnitPipe.forwardpressure := ((~x254_fifo.empty.D(1.0-1) | ~(x254_fifo.active(1).out))) && (true.B) | x272_inr_UnitPipe.sm.io.doneLatch
      x272_inr_UnitPipe.sm.io.enableOut.zip(x272_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x272_inr_UnitPipe.sm.io.break := false.B
      x272_inr_UnitPipe.mask := true.B & true.B
      x272_inr_UnitPipe.configure("x272_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x272_inr_UnitPipe.kernel()
      val x370_rd_x265 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x370_rd_x265""")
      val x370_rd_x265_banks = List[UInt]()
      val x370_rd_x265_ofs = List[UInt]()
      val x370_rd_x265_en = List[Bool](true.B)
      val x370_rd_x265_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x370_rd_x265_shared_en")
      x370_rd_x265.toSeq.zip(x265_reg.connectRPort(370, x370_rd_x265_banks, x370_rd_x265_ofs, io.sigsIn.backpressure, x370_rd_x265_en.map(_ && x370_rd_x265_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x274_ctr = new CtrObject(Left(Some(0)), Right(x370_rd_x265), Left(Some(1)), 1, 32, false)
      val x275_ctrchain = (new CChainObject(List[CtrObject](x274_ctr), "x275_ctrchain")).cchain.io 
      x275_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x275_ctrchain_p", (x275_ctrchain.par, x275_ctrchain.widths))
      val x285_inr_Foreach = new x285_inr_Foreach_kernel(List(x255), List(x264_reg,x250_realSram_0) ,  Some(me), List(x275_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, rr)
      x285_inr_Foreach.sm.io.ctrDone := (x285_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x285_inr_Foreach.backpressure := true.B | x285_inr_Foreach.sm.io.doneLatch
      x285_inr_Foreach.forwardpressure := (x255.valid) && (true.B) | x285_inr_Foreach.sm.io.doneLatch
      x285_inr_Foreach.sm.io.enableOut.zip(x285_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x285_inr_Foreach.sm.io.break := false.B
      x285_inr_Foreach.mask := ~x285_inr_Foreach.cchain.head.output.noop & true.B
      x285_inr_Foreach.configure("x285_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x285_inr_Foreach.kernel()
    }
    val module = Module(new x286_outr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnitPipe x286_outr_UnitPipe **/
