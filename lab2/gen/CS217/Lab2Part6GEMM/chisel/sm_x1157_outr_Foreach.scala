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

/** Hierarchy: x1157 -> x1158 -> x1811 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1157_outr_Foreach **/
class x1157_outr_Foreach_kernel(
  list_x1089_fifo: List[FIFOInterface],
  list_x1090: List[DecoupledIO[AppLoadData]],
  list_x1018_tileC_sram_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x1157_outr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1157_outr_Foreach_iiCtr"))
  
  abstract class x1157_outr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x1089_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x1089_fifo_p").asInstanceOf[MemParams] ))
      val in_x1090 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x1090_p").asInstanceOf[(Int, Int)] )))
      val in_x1018_tileC_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_0_p").asInstanceOf[NBufParams] ))
      val in_x1019_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1019_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x1089_fifo = {io.in_x1089_fifo} ; io.in_x1089_fifo := DontCare
    def x1090 = {io.in_x1090} 
    def x1018_tileC_sram_0 = {io.in_x1018_tileC_sram_0} ; io.in_x1018_tileC_sram_0 := DontCare
    def x1019_tileC_sram_1 = {io.in_x1019_tileC_sram_1} ; io.in_x1019_tileC_sram_1 := DontCare
  }
  def connectWires0(module: x1157_outr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x1089_fifo.connectLedger(module.io.in_x1089_fifo)
    module.io.in_x1090 <> x1090
    x1018_tileC_sram_0.connectLedger(module.io.in_x1018_tileC_sram_0)
    x1019_tileC_sram_1.connectLedger(module.io.in_x1019_tileC_sram_1)
  }
  val x1089_fifo = list_x1089_fifo(0)
  val x1090 = list_x1090(0)
  val x1018_tileC_sram_0 = list_x1018_tileC_sram_0(0)
  val x1019_tileC_sram_1 = list_x1018_tileC_sram_0(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1157_outr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1157_outr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1157_outr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1157_outr_Foreach = Module(new InstrumentationCounter())
      val iters_x1157_outr_Foreach = Module(new InstrumentationCounter())
      cycles_x1157_outr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1157_outr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1157_instrctr, cycles_x1157_outr_Foreach.io.count, iters_x1157_outr_Foreach.io.count, 0.U, 0.U)
      val b1121 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1121.suggestName("b1121")
      val b1122 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1122.suggestName("b1122")
      val x1123_reg = (new x1123_reg).m.io.asInstanceOf[StandardInterface]
      val x1124_reg = (new x1124_reg).m.io.asInstanceOf[StandardInterface]
      val x1125_reg = (new x1125_reg).m.io.asInstanceOf[StandardInterface]
      val x1134_inr_UnitPipe = new x1134_inr_UnitPipe_kernel(List(b1122), List(x1089_fifo), List(x1123_reg,x1125_reg,x1124_reg) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1134_inr_UnitPipe.sm.io.ctrDone := risingEdge(x1134_inr_UnitPipe.sm.io.ctrInc)
      x1134_inr_UnitPipe.backpressure := true.B | x1134_inr_UnitPipe.sm.io.doneLatch
      x1134_inr_UnitPipe.forwardpressure := ((~x1089_fifo.empty.D(1.0-1) | ~(x1089_fifo.active(1).out))) && (true.B) | x1134_inr_UnitPipe.sm.io.doneLatch
      x1134_inr_UnitPipe.sm.io.enableOut.zip(x1134_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x1134_inr_UnitPipe.sm.io.break := false.B
      x1134_inr_UnitPipe.mask := true.B & b1122
      x1134_inr_UnitPipe.configure("x1134_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1134_inr_UnitPipe.kernel()
      val x1769_rd_x1125 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1769_rd_x1125""")
      val x1769_rd_x1125_banks = List[UInt]()
      val x1769_rd_x1125_ofs = List[UInt]()
      val x1769_rd_x1125_en = List[Bool](true.B)
      val x1769_rd_x1125_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1769_rd_x1125_shared_en")
      x1769_rd_x1125.toSeq.zip(x1125_reg.connectRPort(1769, x1769_rd_x1125_banks, x1769_rd_x1125_ofs, io.sigsIn.backpressure, x1769_rd_x1125_en.map(_ && x1769_rd_x1125_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1136_ctr = new CtrObject(Left(Some(0)), Right(x1769_rd_x1125), Left(Some(1)), 1, 32, false)
      val x1137_ctrchain = (new CChainObject(List[CtrObject](x1136_ctr), "x1137_ctrchain")).cchain.io 
      x1137_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1137_ctrchain_p", (x1137_ctrchain.par, x1137_ctrchain.widths))
      val x1156_inr_Foreach = new x1156_inr_Foreach_kernel(List(x1090), List(x1123_reg,x1124_reg), List(b1121), List(x1018_tileC_sram_0,x1019_tileC_sram_1), List(b1122) ,  Some(me), List(x1137_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1156_inr_Foreach.sm.io.ctrDone := (x1156_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1156_inr_Foreach.backpressure := true.B | x1156_inr_Foreach.sm.io.doneLatch
      x1156_inr_Foreach.forwardpressure := (x1090.valid) && (true.B) | x1156_inr_Foreach.sm.io.doneLatch
      x1156_inr_Foreach.sm.io.enableOut.zip(x1156_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1156_inr_Foreach.sm.io.break := false.B
      x1156_inr_Foreach.mask := ~x1156_inr_Foreach.cchain.head.output.noop & b1122
      x1156_inr_Foreach.configure("x1156_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1156_inr_Foreach.kernel()
    }
    val module = Module(new x1157_outr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1157_outr_Foreach **/
