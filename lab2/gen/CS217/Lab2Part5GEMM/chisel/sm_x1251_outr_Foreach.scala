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

/** Hierarchy: x1251 -> x810 **/
/** BEGIN None x1251_outr_Foreach **/
class x1251_outr_Foreach_kernel(
  list_x937: List[DecoupledIO[AppLoadData]],
  list_x878_M: List[UInt],
  list_x1190: List[DecoupledIO[AppStoreData]],
  list_x1191: List[DecoupledIO[Bool]],
  list_x906_c: List[FixedPoint],
  list_x1086: List[DecoupledIO[AppCommandDense]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Pipelined, 2, isFSM = false   , latency = 0.0.toInt, myName = "x1251_outr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1251_outr_Foreach_iiCtr"))
  
  abstract class x1251_outr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x1190 = Decoupled(new AppStoreData(ModuleParams.getParams("x1190_p").asInstanceOf[(Int,Int)] ))
      val in_x878_M = Input(UInt(64.W))
      val in_x1191 = Flipped(Decoupled(Bool()))
      val in_x937 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x937_p").asInstanceOf[(Int, Int)] )))
      val in_x1021 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x1021_p").asInstanceOf[(Int, Int)] )))
      val in_x879_N = Input(UInt(64.W))
      val in_x1086 = Decoupled(new AppCommandDense(ModuleParams.getParams("x1086_p").asInstanceOf[(Int,Int)] ))
      val in_x906_c = Input(new FixedPoint(true, 64, 0))
      val in_x900_a = Input(new FixedPoint(true, 64, 0))
      val in_x880_K = Input(UInt(64.W))
      val in_x903_b = Input(new FixedPoint(true, 64, 0))
      val in_x1189 = Decoupled(new AppCommandDense(ModuleParams.getParams("x1189_p").asInstanceOf[(Int,Int)] ))
      val in_x1019 = Decoupled(new AppCommandDense(ModuleParams.getParams("x1019_p").asInstanceOf[(Int,Int)] ))
      val in_x935 = Decoupled(new AppCommandDense(ModuleParams.getParams("x935_p").asInstanceOf[(Int,Int)] ))
      val in_x1088 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x1088_p").asInstanceOf[(Int, Int)] )))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def x1190 = {io.in_x1190} 
    def x878_M = {io.in_x878_M} 
    def x1191 = {io.in_x1191} 
    def x937 = {io.in_x937} 
    def x1021 = {io.in_x1021} 
    def x879_N = {io.in_x879_N} 
    def x1086 = {io.in_x1086} 
    def x906_c = {io.in_x906_c} 
    def x900_a = {io.in_x900_a} 
    def x880_K = {io.in_x880_K} 
    def x903_b = {io.in_x903_b} 
    def x1189 = {io.in_x1189} 
    def x1019 = {io.in_x1019} 
    def x935 = {io.in_x935} 
    def x1088 = {io.in_x1088} 
  }
  def connectWires0(module: x1251_outr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x1190 <> x1190
    module.io.in_x878_M <> x878_M
    module.io.in_x1191 <> x1191
    module.io.in_x937 <> x937
    module.io.in_x1021 <> x1021
    module.io.in_x879_N <> x879_N
    module.io.in_x1086 <> x1086
    module.io.in_x906_c <> x906_c
    module.io.in_x900_a <> x900_a
    module.io.in_x880_K <> x880_K
    module.io.in_x903_b <> x903_b
    module.io.in_x1189 <> x1189
    module.io.in_x1019 <> x1019
    module.io.in_x935 <> x935
    module.io.in_x1088 <> x1088
  }
  val x937 = list_x937(0)
  val x1021 = list_x937(1)
  val x1088 = list_x937(2)
  val x878_M = list_x878_M(0)
  val x879_N = list_x878_M(1)
  val x880_K = list_x878_M(2)
  val x1190 = list_x1190(0)
  val x1191 = list_x1191(0)
  val x906_c = list_x906_c(0)
  val x900_a = list_x906_c(1)
  val x903_b = list_x906_c(2)
  val x1086 = list_x1086(0)
  val x1189 = list_x1086(1)
  val x1019 = list_x1086(2)
  val x935 = list_x1086(3)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1251_outr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1251_outr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1251_outr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1251_outr_Foreach = Module(new InstrumentationCounter())
      val iters_x1251_outr_Foreach = Module(new InstrumentationCounter())
      cycles_x1251_outr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1251_outr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1251_instrctr, cycles_x1251_outr_Foreach.io.count, iters_x1251_outr_Foreach.io.count, 0.U, 0.U)
      val b913 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b913.suggestName("b913")
      val b913_chain = Module(new RegChainPass(2, 32, myName = "b913_chain")); b913_chain.io <> DontCare
      b913_chain.chain_pass(b913, io.sigsOut.smDoneIn.head)
      val b913_chain_read_1 = b913_chain.read(1).FP(true,32,0)
      val b914 = ~io.sigsIn.cchainOutputs.head.oobs(0); b914.suggestName("b914")
      val b914_chain = Module(new RegChainPass(2, 1, myName = "b914_chain")); b914_chain.io <> DontCare
      b914_chain.chain_pass(b914, io.sigsOut.smDoneIn.head)
      val b914_chain_read_1: Bool = b914_chain.read(1).apply(0)
      val x916_reg = (new x916_reg).m.io.asInstanceOf[NBufInterface]
      val x921_inr_UnitPipe = new x921_inr_UnitPipe_kernel(List(b914), List(b913), List(x916_reg), List(x880_K) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x921_inr_UnitPipe.sm.io.ctrDone := risingEdge(x921_inr_UnitPipe.sm.io.ctrInc)
      b913_chain.connectStageCtrl((x921_inr_UnitPipe.done).DS(1.toInt, rr, x921_inr_UnitPipe.sm.io.backpressure), x921_inr_UnitPipe.baseEn, 0)
      b914_chain.connectStageCtrl((x921_inr_UnitPipe.done).DS(1.toInt, rr, x921_inr_UnitPipe.sm.io.backpressure), x921_inr_UnitPipe.baseEn, 0)
      x921_inr_UnitPipe.backpressure := true.B | x921_inr_UnitPipe.sm.io.doneLatch
      x921_inr_UnitPipe.forwardpressure := (true.B) && (true.B) | x921_inr_UnitPipe.sm.io.doneLatch
      x921_inr_UnitPipe.sm.io.enableOut.zip(x921_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x921_inr_UnitPipe.sm.io.break := false.B
      x921_inr_UnitPipe.mask := true.B & b914
      x921_inr_UnitPipe.configure("x921_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x921_inr_UnitPipe.kernel()
      val x1306_rd_x878 = Wire(new FixedPoint(true, 32, 0))
      x1306_rd_x878.r := x878_M.r
      val x922_ctr = new CtrObject(Left(Some(0)), Right(x1306_rd_x878), Left(Some(16)), 1, 32, false)
      val x923_ctrchain = (new CChainObject(List[CtrObject](x922_ctr), "x923_ctrchain")).cchain.io 
      x923_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x923_ctrchain_p", (x923_ctrchain.par, x923_ctrchain.widths))
      val x1250_outr_Foreach = new x1250_outr_Foreach_kernel(List(b913_chain_read_1,x906_c,x900_a,x903_b), List(x916_reg), List(x937,x1021,x1088), List(x878_M,x879_N,x880_K), List(x1190), List(x1191), List(b914_chain_read_1), List(x1086,x1189,x1019,x935) ,  Some(me), List(x923_ctrchain), 1, 3, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1250_outr_Foreach.sm.io.ctrDone := (x1250_outr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      b913_chain.connectStageCtrl((x1250_outr_Foreach.done).DS(1.toInt, rr, x1250_outr_Foreach.sm.io.backpressure), x1250_outr_Foreach.baseEn, 1)
      b914_chain.connectStageCtrl((x1250_outr_Foreach.done).DS(1.toInt, rr, x1250_outr_Foreach.sm.io.backpressure), x1250_outr_Foreach.baseEn, 1)
      x1250_outr_Foreach.backpressure := true.B | x1250_outr_Foreach.sm.io.doneLatch
      x1250_outr_Foreach.forwardpressure := (true.B) && (true.B) | x1250_outr_Foreach.sm.io.doneLatch
      x1250_outr_Foreach.sm.io.enableOut.zip(x1250_outr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1250_outr_Foreach.sm.io.break := false.B
      x1250_outr_Foreach.mask := ~x1250_outr_Foreach.cchain.head.output.noop & b914_chain_read_1
      x1250_outr_Foreach.configure("x1250_outr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1250_outr_Foreach.kernel()
    }
    val module = Module(new x1251_outr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1251_outr_Foreach **/
