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

/** Hierarchy: x552 -> x710 **/
/** BEGIN None x552_outr_Foreach **/
class x552_outr_Foreach_kernel(
  list_x470_imag2D_0: List[StandardInterface],
  list_x468_imag_0: List[NBufInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Pipelined, 3, isFSM = false   , latency = 0.0.toInt, myName = "x552_outr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x552_outr_Foreach_iiCtr"))
  
  abstract class x552_outr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x468_imag_0 = Flipped(new NBufInterface(ModuleParams.getParams("x468_imag_0_p").asInstanceOf[NBufParams] ))
      val in_x467_real_0 = Flipped(new NBufInterface(ModuleParams.getParams("x467_real_0_p").asInstanceOf[NBufParams] ))
      val in_x466_frame_0 = Flipped(new NBufInterface(ModuleParams.getParams("x466_frame_0_p").asInstanceOf[NBufParams] ))
      val in_x470_imag2D_0 = Flipped(new StandardInterface(ModuleParams.getParams("x470_imag2D_0_p").asInstanceOf[MemParams] ))
      val in_x465_inSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x465_inSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x464_winSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x464_winSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x469_real2D_0 = Flipped(new StandardInterface(ModuleParams.getParams("x469_real2D_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(3, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(3, 1))
      val rr = Input(Bool())
    })
    def x468_imag_0 = {io.in_x468_imag_0} ; io.in_x468_imag_0 := DontCare
    def x467_real_0 = {io.in_x467_real_0} ; io.in_x467_real_0 := DontCare
    def x466_frame_0 = {io.in_x466_frame_0} ; io.in_x466_frame_0 := DontCare
    def x470_imag2D_0 = {io.in_x470_imag2D_0} ; io.in_x470_imag2D_0 := DontCare
    def x465_inSRAM_0 = {io.in_x465_inSRAM_0} ; io.in_x465_inSRAM_0 := DontCare
    def x464_winSRAM_0 = {io.in_x464_winSRAM_0} ; io.in_x464_winSRAM_0 := DontCare
    def x469_real2D_0 = {io.in_x469_real2D_0} ; io.in_x469_real2D_0 := DontCare
  }
  def connectWires0(module: x552_outr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x468_imag_0.connectLedger(module.io.in_x468_imag_0)
    x467_real_0.connectLedger(module.io.in_x467_real_0)
    x466_frame_0.connectLedger(module.io.in_x466_frame_0)
    x470_imag2D_0.connectLedger(module.io.in_x470_imag2D_0)
    x465_inSRAM_0.connectLedger(module.io.in_x465_inSRAM_0)
    x464_winSRAM_0.connectLedger(module.io.in_x464_winSRAM_0)
    x469_real2D_0.connectLedger(module.io.in_x469_real2D_0)
  }
  val x470_imag2D_0 = list_x470_imag2D_0(0)
  val x465_inSRAM_0 = list_x470_imag2D_0(1)
  val x464_winSRAM_0 = list_x470_imag2D_0(2)
  val x469_real2D_0 = list_x470_imag2D_0(3)
  val x468_imag_0 = list_x468_imag_0(0)
  val x467_real_0 = list_x468_imag_0(1)
  val x466_frame_0 = list_x468_imag_0(2)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x552_outr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x552_outr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x552_outr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x552_outr_Foreach = Module(new InstrumentationCounter())
      val iters_x552_outr_Foreach = Module(new InstrumentationCounter())
      cycles_x552_outr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x552_outr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X552_instrctr, cycles_x552_outr_Foreach.io.count, iters_x552_outr_Foreach.io.count, 0.U, 0.U)
      val b514 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b514.suggestName("b514")
      val b514_chain = Module(new RegChainPass(3, 32, myName = "b514_chain")); b514_chain.io <> DontCare
      b514_chain.chain_pass(b514, io.sigsOut.smDoneIn.head)
      val b514_chain_read_1 = b514_chain.read(1).FP(true,32,0)
      val b514_chain_read_2 = b514_chain.read(2).FP(true,32,0)
      val b515 = ~io.sigsIn.cchainOutputs.head.oobs(0); b515.suggestName("b515")
      val b515_chain = Module(new RegChainPass(3, 1, myName = "b515_chain")); b515_chain.io <> DontCare
      b515_chain.chain_pass(b515, io.sigsOut.smDoneIn.head)
      val b515_chain_read_1: Bool = b515_chain.read(1).apply(0)
      val b515_chain_read_2: Bool = b515_chain.read(2).apply(0)
      val x516_ctr = new CtrObject(Left(Some(0)), Left(Some(4)), Left(Some(1)), 1, 5, false)
      val x517_ctrchain = (new CChainObject(List[CtrObject](x516_ctr), "x517_ctrchain")).cchain.io 
      x517_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x517_ctrchain_p", (x517_ctrchain.par, x517_ctrchain.widths))
      val x528_inr_Foreach = new x528_inr_Foreach_kernel(List(b515), List(b514), List(x465_inSRAM_0,x464_winSRAM_0), List(x466_frame_0) ,  Some(me), List(x517_ctrchain), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x528_inr_Foreach.sm.io.ctrDone := (x528_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      b514_chain.connectStageCtrl((x528_inr_Foreach.done).DS(1.toInt, rr, x528_inr_Foreach.sm.io.backpressure), x528_inr_Foreach.baseEn, 0)
      b515_chain.connectStageCtrl((x528_inr_Foreach.done).DS(1.toInt, rr, x528_inr_Foreach.sm.io.backpressure), x528_inr_Foreach.baseEn, 0)
      x528_inr_Foreach.backpressure := true.B | x528_inr_Foreach.sm.io.doneLatch
      x528_inr_Foreach.forwardpressure := (true.B) && (true.B) | x528_inr_Foreach.sm.io.doneLatch
      x528_inr_Foreach.sm.io.enableOut.zip(x528_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x528_inr_Foreach.sm.io.break := false.B
      x528_inr_Foreach.mask := ~x528_inr_Foreach.cchain.head.output.noop & b515
      x528_inr_Foreach.configure("x528_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x528_inr_Foreach.kernel()
      val x529_ctr = new CtrObject(Left(Some(0)), Left(Some(3)), Left(Some(1)), 1, 4, false)
      val x530_ctrchain = (new CChainObject(List[CtrObject](x529_ctr), "x530_ctrchain")).cchain.io 
      x530_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x530_ctrchain_p", (x530_ctrchain.par, x530_ctrchain.widths))
      val x537_inr_Foreach = new x537_inr_Foreach_kernel(List(b515_chain_read_1), List(x468_imag_0,x467_real_0,x466_frame_0) ,  Some(me), List(x530_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x537_inr_Foreach.sm.io.ctrDone := (x537_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      b514_chain.connectStageCtrl((x537_inr_Foreach.done).DS(1.toInt, rr, x537_inr_Foreach.sm.io.backpressure), x537_inr_Foreach.baseEn, 1)
      b515_chain.connectStageCtrl((x537_inr_Foreach.done).DS(1.toInt, rr, x537_inr_Foreach.sm.io.backpressure), x537_inr_Foreach.baseEn, 1)
      x537_inr_Foreach.backpressure := true.B | x537_inr_Foreach.sm.io.doneLatch
      x537_inr_Foreach.forwardpressure := (true.B) && (true.B) | x537_inr_Foreach.sm.io.doneLatch
      x537_inr_Foreach.sm.io.enableOut.zip(x537_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x537_inr_Foreach.sm.io.break := false.B
      x537_inr_Foreach.mask := ~x537_inr_Foreach.cchain.head.output.noop & b515_chain_read_1
      x537_inr_Foreach.configure("x537_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x537_inr_Foreach.kernel()
      val x538_ctr = new CtrObject(Left(Some(0)), Left(Some(3)), Left(Some(1)), 1, 4, false)
      val x539_ctrchain = (new CChainObject(List[CtrObject](x538_ctr), "x539_ctrchain")).cchain.io 
      x539_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x539_ctrchain_p", (x539_ctrchain.par, x539_ctrchain.widths))
      val x551_inr_Foreach = new x551_inr_Foreach_kernel(List(b515_chain_read_2), List(b514_chain_read_2), List(x470_imag2D_0,x469_real2D_0), List(x468_imag_0,x467_real_0) ,  Some(me), List(x539_ctrchain), 2, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x551_inr_Foreach.sm.io.ctrDone := (x551_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      b514_chain.connectStageCtrl((x551_inr_Foreach.done).DS(1.toInt, rr, x551_inr_Foreach.sm.io.backpressure), x551_inr_Foreach.baseEn, 2)
      b515_chain.connectStageCtrl((x551_inr_Foreach.done).DS(1.toInt, rr, x551_inr_Foreach.sm.io.backpressure), x551_inr_Foreach.baseEn, 2)
      x551_inr_Foreach.backpressure := true.B | x551_inr_Foreach.sm.io.doneLatch
      x551_inr_Foreach.forwardpressure := (true.B) && (true.B) | x551_inr_Foreach.sm.io.doneLatch
      x551_inr_Foreach.sm.io.enableOut.zip(x551_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x551_inr_Foreach.sm.io.break := false.B
      x551_inr_Foreach.mask := ~x551_inr_Foreach.cchain.head.output.noop & b515_chain_read_2
      x551_inr_Foreach.configure("x551_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x551_inr_Foreach.kernel()
    }
    val module = Module(new x552_outr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x552_outr_Foreach **/
