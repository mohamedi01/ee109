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

/** Hierarchy: x1086 -> x1087 -> x1811 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1086_outr_Foreach **/
class x1086_outr_Foreach_kernel(
  list_x1021_fifo: List[FIFOInterface],
  list_x1022: List[DecoupledIO[AppLoadData]],
  list_x1017_tileB_sram_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x1086_outr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1086_outr_Foreach_iiCtr"))
  
  abstract class x1086_outr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x1021_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x1021_fifo_p").asInstanceOf[MemParams] ))
      val in_x1017_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_x1022 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x1022_p").asInstanceOf[(Int, Int)] )))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x1021_fifo = {io.in_x1021_fifo} ; io.in_x1021_fifo := DontCare
    def x1017_tileB_sram_0 = {io.in_x1017_tileB_sram_0} ; io.in_x1017_tileB_sram_0 := DontCare
    def x1022 = {io.in_x1022} 
  }
  def connectWires0(module: x1086_outr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x1021_fifo.connectLedger(module.io.in_x1021_fifo)
    x1017_tileB_sram_0.connectLedger(module.io.in_x1017_tileB_sram_0)
    module.io.in_x1022 <> x1022
  }
  val x1021_fifo = list_x1021_fifo(0)
  val x1022 = list_x1022(0)
  val x1017_tileB_sram_0 = list_x1017_tileB_sram_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1086_outr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1086_outr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1086_outr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1086_outr_Foreach = Module(new InstrumentationCounter())
      val iters_x1086_outr_Foreach = Module(new InstrumentationCounter())
      cycles_x1086_outr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1086_outr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1086_instrctr, cycles_x1086_outr_Foreach.io.count, iters_x1086_outr_Foreach.io.count, 0.U, 0.U)
      val b1053 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1053.suggestName("b1053")
      val b1054 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1054.suggestName("b1054")
      val x1055_reg = (new x1055_reg).m.io.asInstanceOf[StandardInterface]
      val x1056_reg = (new x1056_reg).m.io.asInstanceOf[StandardInterface]
      val x1057_reg = (new x1057_reg).m.io.asInstanceOf[StandardInterface]
      val x1066_inr_UnitPipe = new x1066_inr_UnitPipe_kernel(List(b1054), List(x1021_fifo), List(x1055_reg,x1057_reg,x1056_reg) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1066_inr_UnitPipe.sm.io.ctrDone := risingEdge(x1066_inr_UnitPipe.sm.io.ctrInc)
      x1066_inr_UnitPipe.backpressure := true.B | x1066_inr_UnitPipe.sm.io.doneLatch
      x1066_inr_UnitPipe.forwardpressure := ((~x1021_fifo.empty.D(1.0-1) | ~(x1021_fifo.active(1).out))) && (true.B) | x1066_inr_UnitPipe.sm.io.doneLatch
      x1066_inr_UnitPipe.sm.io.enableOut.zip(x1066_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x1066_inr_UnitPipe.sm.io.break := false.B
      x1066_inr_UnitPipe.mask := true.B & b1054
      x1066_inr_UnitPipe.configure("x1066_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1066_inr_UnitPipe.kernel()
      val x1766_rd_x1057 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1766_rd_x1057""")
      val x1766_rd_x1057_banks = List[UInt]()
      val x1766_rd_x1057_ofs = List[UInt]()
      val x1766_rd_x1057_en = List[Bool](true.B)
      val x1766_rd_x1057_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1766_rd_x1057_shared_en")
      x1766_rd_x1057.toSeq.zip(x1057_reg.connectRPort(1766, x1766_rd_x1057_banks, x1766_rd_x1057_ofs, io.sigsIn.backpressure, x1766_rd_x1057_en.map(_ && x1766_rd_x1057_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1068_ctr = new CtrObject(Left(Some(0)), Right(x1766_rd_x1057), Left(Some(1)), 1, 32, false)
      val x1069_ctrchain = (new CChainObject(List[CtrObject](x1068_ctr), "x1069_ctrchain")).cchain.io 
      x1069_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1069_ctrchain_p", (x1069_ctrchain.par, x1069_ctrchain.widths))
      val x1085_inr_Foreach = new x1085_inr_Foreach_kernel(List(x1055_reg,x1056_reg), List(x1017_tileB_sram_0), List(b1053), List(b1054), List(x1022) ,  Some(me), List(x1069_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1085_inr_Foreach.sm.io.ctrDone := (x1085_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1085_inr_Foreach.backpressure := true.B | x1085_inr_Foreach.sm.io.doneLatch
      x1085_inr_Foreach.forwardpressure := (x1022.valid) && (true.B) | x1085_inr_Foreach.sm.io.doneLatch
      x1085_inr_Foreach.sm.io.enableOut.zip(x1085_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1085_inr_Foreach.sm.io.break := false.B
      x1085_inr_Foreach.mask := ~x1085_inr_Foreach.cchain.head.output.noop & b1054
      x1085_inr_Foreach.configure("x1085_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1085_inr_Foreach.kernel()
    }
    val module = Module(new x1086_outr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1086_outr_Foreach **/
