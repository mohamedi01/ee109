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

/** Hierarchy: x1152 -> x1153 -> x1334 -> x1335 -> x1250 -> x1251 -> x810 **/
/** BEGIN None x1152_outr_Foreach **/
class x1152_outr_Foreach_kernel(
  list_x1087_fifo: List[FIFOInterface],
  list_x1088: List[DecoupledIO[AppLoadData]],
  list_x1017_tileC_sram_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x1152_outr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1152_outr_Foreach_iiCtr"))
  
  abstract class x1152_outr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x1087_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x1087_fifo_p").asInstanceOf[MemParams] ))
      val in_x1017_tileC_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1017_tileC_sram_0_p").asInstanceOf[NBufParams] ))
      val in_x1018_tileC_sram_1 = Flipped(new NBufInterface(ModuleParams.getParams("x1018_tileC_sram_1_p").asInstanceOf[NBufParams] ))
      val in_x1088 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x1088_p").asInstanceOf[(Int, Int)] )))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x1087_fifo = {io.in_x1087_fifo} ; io.in_x1087_fifo := DontCare
    def x1017_tileC_sram_0 = {io.in_x1017_tileC_sram_0} ; io.in_x1017_tileC_sram_0 := DontCare
    def x1018_tileC_sram_1 = {io.in_x1018_tileC_sram_1} ; io.in_x1018_tileC_sram_1 := DontCare
    def x1088 = {io.in_x1088} 
  }
  def connectWires0(module: x1152_outr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x1087_fifo.connectLedger(module.io.in_x1087_fifo)
    x1017_tileC_sram_0.connectLedger(module.io.in_x1017_tileC_sram_0)
    x1018_tileC_sram_1.connectLedger(module.io.in_x1018_tileC_sram_1)
    module.io.in_x1088 <> x1088
  }
  val x1087_fifo = list_x1087_fifo(0)
  val x1088 = list_x1088(0)
  val x1017_tileC_sram_0 = list_x1017_tileC_sram_0(0)
  val x1018_tileC_sram_1 = list_x1017_tileC_sram_0(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1152_outr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1152_outr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1152_outr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1152_outr_Foreach = Module(new InstrumentationCounter())
      val iters_x1152_outr_Foreach = Module(new InstrumentationCounter())
      cycles_x1152_outr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1152_outr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1152_instrctr, cycles_x1152_outr_Foreach.io.count, iters_x1152_outr_Foreach.io.count, 0.U, 0.U)
      val b1119 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1119.suggestName("b1119")
      val b1120 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1120.suggestName("b1120")
      val x1121_reg = (new x1121_reg).m.io.asInstanceOf[StandardInterface]
      val x1122_reg = (new x1122_reg).m.io.asInstanceOf[StandardInterface]
      val x1123_reg = (new x1123_reg).m.io.asInstanceOf[StandardInterface]
      val x1132_inr_UnitPipe = new x1132_inr_UnitPipe_kernel(List(b1120), List(x1087_fifo), List(x1123_reg,x1121_reg,x1122_reg) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1132_inr_UnitPipe.sm.io.ctrDone := risingEdge(x1132_inr_UnitPipe.sm.io.ctrInc)
      x1132_inr_UnitPipe.backpressure := true.B | x1132_inr_UnitPipe.sm.io.doneLatch
      x1132_inr_UnitPipe.forwardpressure := ((~x1087_fifo.empty.D(1.0-1) | ~(x1087_fifo.active(1).out))) && (true.B) | x1132_inr_UnitPipe.sm.io.doneLatch
      x1132_inr_UnitPipe.sm.io.enableOut.zip(x1132_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x1132_inr_UnitPipe.sm.io.break := false.B
      x1132_inr_UnitPipe.mask := true.B & b1120
      x1132_inr_UnitPipe.configure("x1132_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1132_inr_UnitPipe.kernel()
      val x1316_rd_x1123 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1316_rd_x1123""")
      val x1316_rd_x1123_banks = List[UInt]()
      val x1316_rd_x1123_ofs = List[UInt]()
      val x1316_rd_x1123_en = List[Bool](true.B)
      val x1316_rd_x1123_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1316_rd_x1123_shared_en")
      x1316_rd_x1123.toSeq.zip(x1123_reg.connectRPort(1316, x1316_rd_x1123_banks, x1316_rd_x1123_ofs, io.sigsIn.backpressure, x1316_rd_x1123_en.map(_ && x1316_rd_x1123_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1134_ctr = new CtrObject(Left(Some(0)), Right(x1316_rd_x1123), Left(Some(1)), 1, 32, false)
      val x1135_ctrchain = (new CChainObject(List[CtrObject](x1134_ctr), "x1135_ctrchain")).cchain.io 
      x1135_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1135_ctrchain_p", (x1135_ctrchain.par, x1135_ctrchain.widths))
      val x1151_inr_Foreach = new x1151_inr_Foreach_kernel(List(x1088), List(x1017_tileC_sram_0,x1018_tileC_sram_1), List(b1119), List(b1120), List(x1121_reg,x1122_reg) ,  Some(me), List(x1135_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1151_inr_Foreach.sm.io.ctrDone := (x1151_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1151_inr_Foreach.backpressure := true.B | x1151_inr_Foreach.sm.io.doneLatch
      x1151_inr_Foreach.forwardpressure := (x1088.valid) && (true.B) | x1151_inr_Foreach.sm.io.doneLatch
      x1151_inr_Foreach.sm.io.enableOut.zip(x1151_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1151_inr_Foreach.sm.io.break := false.B
      x1151_inr_Foreach.mask := ~x1151_inr_Foreach.cchain.head.output.noop & b1120
      x1151_inr_Foreach.configure("x1151_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1151_inr_Foreach.kernel()
    }
    val module = Module(new x1152_outr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1152_outr_Foreach **/
