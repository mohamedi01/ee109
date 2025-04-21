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

/** Hierarchy: x186 -> x119 **/
/** BEGIN None x186_outr_Reduce **/
class x186_outr_Reduce_kernel(
  list_x165_ctrchain: List[CounterChainInterface],
  list_x161_a_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Pipelined, 2, isFSM = false   , latency = 0.0.toInt, myName = "x186_outr_Reduce_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x186_outr_Reduce_iiCtr"))
  
  abstract class x186_outr_Reduce_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x165_ctrchain = Flipped(new CounterChainInterface(ModuleParams.getParams("x165_ctrchain_p").asInstanceOf[(List[Int],List[Int])] ))
      val in_x161_a_0 = Flipped(new StandardInterface(ModuleParams.getParams("x161_a_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x165_ctrchain = {io.in_x165_ctrchain} ; io.in_x165_ctrchain := DontCare
    def x161_a_0 = {io.in_x161_a_0} ; io.in_x161_a_0 := DontCare
  }
  def connectWires0(module: x186_outr_Reduce_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x165_ctrchain.input <> x165_ctrchain.input; module.io.in_x165_ctrchain.output <> x165_ctrchain.output
    x161_a_0.connectLedger(module.io.in_x161_a_0)
  }
  val x165_ctrchain = list_x165_ctrchain(0)
  val x161_a_0 = list_x161_a_0(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x186_outr_Reduce")
    implicit val stack = ControllerStack.stack.toList
    class x186_outr_Reduce_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x186_outr_Reduce_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x186_outr_Reduce = Module(new InstrumentationCounter())
      val iters_x186_outr_Reduce = Module(new InstrumentationCounter())
      cycles_x186_outr_Reduce.io.enable := io.sigsIn.baseEn
      iters_x186_outr_Reduce.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X186_instrctr, cycles_x186_outr_Reduce.io.count, iters_x186_outr_Reduce.io.count, 0.U, 0.U)
      val b166 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b166.suggestName("b166")
      val b166_chain = Module(new RegChainPass(2, 32, myName = "b166_chain")); b166_chain.io <> DontCare
      b166_chain.chain_pass(b166, io.sigsOut.smDoneIn.head)
      val b166_chain_read_1 = b166_chain.read(1).FP(true,32,0)
      val b168 = ~io.sigsIn.cchainOutputs.head.oobs(0); b168.suggestName("b168")
      val b168_chain = Module(new RegChainPass(2, 1, myName = "b168_chain")); b168_chain.io <> DontCare
      b168_chain.chain_pass(b168, io.sigsOut.smDoneIn.head)
      val b168_chain_read_1: Bool = b168_chain.read(1).apply(0)
      val x170_tmp_0 = (new x170_tmp_0).m.io.asInstanceOf[NBufInterface]
      val x171_ctr = new CtrObject(Left(Some(0)), Left(Some(16)), Left(Some(1)), 1, 7, false)
      val x172_ctrchain = (new CChainObject(List[CtrObject](x171_ctr), "x172_ctrchain")).cchain.io 
      x172_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x172_ctrchain_p", (x172_ctrchain.par, x172_ctrchain.widths))
      val x176_inr_Foreach = new x176_inr_Foreach_kernel(List(b168), List(x170_tmp_0) ,  Some(me), List(x172_ctrchain), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x176_inr_Foreach.sm.io.ctrDone := (x176_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      b166_chain.connectStageCtrl((x176_inr_Foreach.done).DS(1.toInt, rr, x176_inr_Foreach.sm.io.backpressure), x176_inr_Foreach.baseEn, 0)
      b168_chain.connectStageCtrl((x176_inr_Foreach.done).DS(1.toInt, rr, x176_inr_Foreach.sm.io.backpressure), x176_inr_Foreach.baseEn, 0)
      x176_inr_Foreach.backpressure := true.B | x176_inr_Foreach.sm.io.doneLatch
      x176_inr_Foreach.forwardpressure := (true.B) && (true.B) | x176_inr_Foreach.sm.io.doneLatch
      x176_inr_Foreach.sm.io.enableOut.zip(x176_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x176_inr_Foreach.sm.io.break := false.B
      x176_inr_Foreach.mask := ~x176_inr_Foreach.cchain.head.output.noop & b168
      x176_inr_Foreach.configure("x176_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x176_inr_Foreach.kernel()
      val x185_inr_Foreach = new x185_inr_Foreach_kernel(List(x161_a_0), List(x170_tmp_0) ,  Some(me), List(x165_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x185_inr_Foreach.sm.io.ctrDone := (x185_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      b166_chain.connectStageCtrl((x185_inr_Foreach.done).DS(1.toInt, rr, x185_inr_Foreach.sm.io.backpressure), x185_inr_Foreach.baseEn, 1)
      b168_chain.connectStageCtrl((x185_inr_Foreach.done).DS(1.toInt, rr, x185_inr_Foreach.sm.io.backpressure), x185_inr_Foreach.baseEn, 1)
      x185_inr_Foreach.backpressure := true.B | x185_inr_Foreach.sm.io.doneLatch
      x185_inr_Foreach.forwardpressure := (true.B) && (true.B) | x185_inr_Foreach.sm.io.doneLatch
      x185_inr_Foreach.sm.io.enableOut.zip(x185_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x185_inr_Foreach.sm.io.break := false.B
      x185_inr_Foreach.mask := ~x185_inr_Foreach.cchain.head.output.noop & true.B
      x185_inr_Foreach.configure("x185_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x185_inr_Foreach.kernel()
    }
    val module = Module(new x186_outr_Reduce_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledReduce x186_outr_Reduce **/
