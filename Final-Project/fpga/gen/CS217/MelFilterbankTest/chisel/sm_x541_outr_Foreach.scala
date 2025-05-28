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

/** Hierarchy: x541 -> x612 **/
/** BEGIN None x541_outr_Foreach **/
class x541_outr_Foreach_kernel(
  list_x411_matSRAM_0: List[StandardInterface],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Pipelined, 2, isFSM = false   , latency = 0.0.toInt, myName = "x541_outr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x541_outr_Foreach_iiCtr"))
  
  abstract class x541_outr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x411_matSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x411_matSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x412_vecSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x412_vecSRAM_0_p").asInstanceOf[MemParams] ))
      val in_x413_outSRAM_0 = Flipped(new StandardInterface(ModuleParams.getParams("x413_outSRAM_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x411_matSRAM_0 = {io.in_x411_matSRAM_0} ; io.in_x411_matSRAM_0 := DontCare
    def x412_vecSRAM_0 = {io.in_x412_vecSRAM_0} ; io.in_x412_vecSRAM_0 := DontCare
    def x413_outSRAM_0 = {io.in_x413_outSRAM_0} ; io.in_x413_outSRAM_0 := DontCare
  }
  def connectWires0(module: x541_outr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x411_matSRAM_0.connectLedger(module.io.in_x411_matSRAM_0)
    x412_vecSRAM_0.connectLedger(module.io.in_x412_vecSRAM_0)
    x413_outSRAM_0.connectLedger(module.io.in_x413_outSRAM_0)
  }
  val x411_matSRAM_0 = list_x411_matSRAM_0(0)
  val x412_vecSRAM_0 = list_x411_matSRAM_0(1)
  val x413_outSRAM_0 = list_x411_matSRAM_0(2)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x541_outr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x541_outr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x541_outr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x541_outr_Foreach = Module(new InstrumentationCounter())
      val iters_x541_outr_Foreach = Module(new InstrumentationCounter())
      cycles_x541_outr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x541_outr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X541_instrctr, cycles_x541_outr_Foreach.io.count, iters_x541_outr_Foreach.io.count, 0.U, 0.U)
      val b514 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b514.suggestName("b514")
      val b514_chain = Module(new RegChainPass(2, 32, myName = "b514_chain")); b514_chain.io <> DontCare
      b514_chain.chain_pass(b514, io.sigsOut.smDoneIn.head)
      val b514_chain_read_1 = b514_chain.read(1).FP(true,32,0)
      val b515 = ~io.sigsIn.cchainOutputs.head.oobs(0); b515.suggestName("b515")
      val b515_chain = Module(new RegChainPass(2, 1, myName = "b515_chain")); b515_chain.io <> DontCare
      b515_chain.chain_pass(b515, io.sigsOut.smDoneIn.head)
      val b515_chain_read_1: Bool = b515_chain.read(1).apply(0)
