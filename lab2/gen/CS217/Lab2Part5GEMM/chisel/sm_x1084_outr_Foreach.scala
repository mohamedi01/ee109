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

/** Hierarchy: x1084 -> x1085 -> x1334 -> x1335 -> x1250 -> x1251 -> x810 **/
/** BEGIN None x1084_outr_Foreach **/
class x1084_outr_Foreach_kernel(
  list_x1020_fifo: List[FIFOInterface],
  list_x1021: List[DecoupledIO[AppLoadData]],
  list_x1016_tileB_sram_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x1084_outr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1084_outr_Foreach_iiCtr"))
  
  abstract class x1084_outr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x1020_fifo = Flipped(new FIFOInterface(ModuleParams.getParams("x1020_fifo_p").asInstanceOf[MemParams] ))
      val in_x1021 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x1021_p").asInstanceOf[(Int, Int)] )))
      val in_x1016_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1016_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x1020_fifo = {io.in_x1020_fifo} ; io.in_x1020_fifo := DontCare
    def x1021 = {io.in_x1021} 
    def x1016_tileB_sram_0 = {io.in_x1016_tileB_sram_0} ; io.in_x1016_tileB_sram_0 := DontCare
  }
  def connectWires0(module: x1084_outr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x1020_fifo.connectLedger(module.io.in_x1020_fifo)
    module.io.in_x1021 <> x1021
    x1016_tileB_sram_0.connectLedger(module.io.in_x1016_tileB_sram_0)
  }
  val x1020_fifo = list_x1020_fifo(0)
  val x1021 = list_x1021(0)
  val x1016_tileB_sram_0 = list_x1016_tileB_sram_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1084_outr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1084_outr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1084_outr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1084_outr_Foreach = Module(new InstrumentationCounter())
      val iters_x1084_outr_Foreach = Module(new InstrumentationCounter())
      cycles_x1084_outr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1084_outr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1084_instrctr, cycles_x1084_outr_Foreach.io.count, iters_x1084_outr_Foreach.io.count, 0.U, 0.U)
      val b1052 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1052.suggestName("b1052")
      val b1053 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1053.suggestName("b1053")
      val x1054_reg = (new x1054_reg).m.io.asInstanceOf[StandardInterface]
      val x1055_reg = (new x1055_reg).m.io.asInstanceOf[StandardInterface]
      val x1056_reg = (new x1056_reg).m.io.asInstanceOf[StandardInterface]
      val x1065_inr_UnitPipe = new x1065_inr_UnitPipe_kernel(List(b1053), List(x1020_fifo), List(x1055_reg,x1054_reg,x1056_reg) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1065_inr_UnitPipe.sm.io.ctrDone := risingEdge(x1065_inr_UnitPipe.sm.io.ctrInc)
      x1065_inr_UnitPipe.backpressure := true.B | x1065_inr_UnitPipe.sm.io.doneLatch
      x1065_inr_UnitPipe.forwardpressure := ((~x1020_fifo.empty.D(1.0-1) | ~(x1020_fifo.active(1).out))) && (true.B) | x1065_inr_UnitPipe.sm.io.doneLatch
      x1065_inr_UnitPipe.sm.io.enableOut.zip(x1065_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x1065_inr_UnitPipe.sm.io.break := false.B
      x1065_inr_UnitPipe.mask := true.B & b1053
      x1065_inr_UnitPipe.configure("x1065_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1065_inr_UnitPipe.kernel()
      val x1313_rd_x1056 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1313_rd_x1056""")
      val x1313_rd_x1056_banks = List[UInt]()
      val x1313_rd_x1056_ofs = List[UInt]()
      val x1313_rd_x1056_en = List[Bool](true.B)
      val x1313_rd_x1056_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1313_rd_x1056_shared_en")
      x1313_rd_x1056.toSeq.zip(x1056_reg.connectRPort(1313, x1313_rd_x1056_banks, x1313_rd_x1056_ofs, io.sigsIn.backpressure, x1313_rd_x1056_en.map(_ && x1313_rd_x1056_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1067_ctr = new CtrObject(Left(Some(0)), Right(x1313_rd_x1056), Left(Some(1)), 1, 32, false)
      val x1068_ctrchain = (new CChainObject(List[CtrObject](x1067_ctr), "x1068_ctrchain")).cchain.io 
      x1068_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1068_ctrchain_p", (x1068_ctrchain.par, x1068_ctrchain.widths))
      val x1083_inr_Foreach = new x1083_inr_Foreach_kernel(List(x1021), List(x1055_reg,x1054_reg), List(b1052), List(x1016_tileB_sram_0), List(b1053) ,  Some(me), List(x1068_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1083_inr_Foreach.sm.io.ctrDone := (x1083_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x1083_inr_Foreach.backpressure := true.B | x1083_inr_Foreach.sm.io.doneLatch
      x1083_inr_Foreach.forwardpressure := (x1021.valid) && (true.B) | x1083_inr_Foreach.sm.io.doneLatch
      x1083_inr_Foreach.sm.io.enableOut.zip(x1083_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1083_inr_Foreach.sm.io.break := false.B
      x1083_inr_Foreach.mask := ~x1083_inr_Foreach.cchain.head.output.noop & b1053
      x1083_inr_Foreach.configure("x1083_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1083_inr_Foreach.kernel()
    }
    val module = Module(new x1084_outr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1084_outr_Foreach **/
