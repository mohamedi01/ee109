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

/** Hierarchy: x1703 -> x1704 -> x810 **/
/** BEGIN None x1703_outr_Foreach **/
class x1703_outr_Foreach_kernel(
  list_b913: List[FixedPoint],
  list_x916_reg: List[NBufInterface],
  list_x878_M: List[UInt],
  list_x1641: List[DecoupledIO[Bool]],
  list_x937: List[DecoupledIO[AppLoadData]],
  list_x1640: List[DecoupledIO[AppStoreData]],
  list_x1020: List[DecoupledIO[AppCommandDense]],
  list_b914: List[Bool],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Pipelined, 3, isFSM = false   , latency = 0.0.toInt, myName = "x1703_outr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1703_outr_Foreach_iiCtr"))
  
  abstract class x1703_outr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x878_M = Input(UInt(64.W))
      val in_b913 = Input(new FixedPoint(true, 32, 0))
      val in_x1640 = Decoupled(new AppStoreData(ModuleParams.getParams("x1640_p").asInstanceOf[(Int,Int)] ))
      val in_x1641 = Flipped(Decoupled(Bool()))
      val in_x937 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x937_p").asInstanceOf[(Int, Int)] )))
      val in_x1020 = Decoupled(new AppCommandDense(ModuleParams.getParams("x1020_p").asInstanceOf[(Int,Int)] ))
      val in_x916_reg = Flipped(new NBufInterface(ModuleParams.getParams("x916_reg_p").asInstanceOf[NBufParams] ))
      val in_x879_N = Input(UInt(64.W))
      val in_x906_c = Input(new FixedPoint(true, 64, 0))
      val in_x1090 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x1090_p").asInstanceOf[(Int, Int)] )))
      val in_x900_a = Input(new FixedPoint(true, 64, 0))
      val in_x880_K = Input(UInt(64.W))
      val in_x1022 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x1022_p").asInstanceOf[(Int, Int)] )))
      val in_x903_b = Input(new FixedPoint(true, 64, 0))
      val in_x1639 = Decoupled(new AppCommandDense(ModuleParams.getParams("x1639_p").asInstanceOf[(Int,Int)] ))
      val in_b914 = Input(Bool())
      val in_x935 = Decoupled(new AppCommandDense(ModuleParams.getParams("x935_p").asInstanceOf[(Int,Int)] ))
      val in_x1088 = Decoupled(new AppCommandDense(ModuleParams.getParams("x1088_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(3, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(3, 1))
      val rr = Input(Bool())
    })
    def x878_M = {io.in_x878_M} 
    def b913 = {io.in_b913} 
    def x1640 = {io.in_x1640} 
    def x1641 = {io.in_x1641} 
    def x937 = {io.in_x937} 
    def x1020 = {io.in_x1020} 
    def x916_reg = {io.in_x916_reg} ; io.in_x916_reg := DontCare
    def x879_N = {io.in_x879_N} 
    def x906_c = {io.in_x906_c} 
    def x1090 = {io.in_x1090} 
    def x900_a = {io.in_x900_a} 
    def x880_K = {io.in_x880_K} 
    def x1022 = {io.in_x1022} 
    def x903_b = {io.in_x903_b} 
    def x1639 = {io.in_x1639} 
    def b914 = {io.in_b914} 
    def x935 = {io.in_x935} 
    def x1088 = {io.in_x1088} 
  }
  def connectWires0(module: x1703_outr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x878_M <> x878_M
    module.io.in_b913 <> b913
    module.io.in_x1640 <> x1640
    module.io.in_x1641 <> x1641
    module.io.in_x937 <> x937
    module.io.in_x1020 <> x1020
    x916_reg.connectLedger(module.io.in_x916_reg)
    module.io.in_x879_N <> x879_N
    module.io.in_x906_c <> x906_c
    module.io.in_x1090 <> x1090
    module.io.in_x900_a <> x900_a
    module.io.in_x880_K <> x880_K
    module.io.in_x1022 <> x1022
    module.io.in_x903_b <> x903_b
    module.io.in_x1639 <> x1639
    module.io.in_b914 <> b914
    module.io.in_x935 <> x935
    module.io.in_x1088 <> x1088
  }
  val b913 = list_b913(0)
  val x906_c = list_b913(1)
  val x900_a = list_b913(2)
  val x903_b = list_b913(3)
  val x916_reg = list_x916_reg(0)
  val x878_M = list_x878_M(0)
  val x879_N = list_x878_M(1)
  val x880_K = list_x878_M(2)
  val x1641 = list_x1641(0)
  val x937 = list_x937(0)
  val x1090 = list_x937(1)
  val x1022 = list_x937(2)
  val x1640 = list_x1640(0)
  val x1020 = list_x1020(0)
  val x1639 = list_x1020(1)
  val x935 = list_x1020(2)
  val x1088 = list_x1020(3)
  val b914 = list_b914(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1703_outr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1703_outr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1703_outr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1703_outr_Foreach = Module(new InstrumentationCounter())
      val iters_x1703_outr_Foreach = Module(new InstrumentationCounter())
      cycles_x1703_outr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1703_outr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1703_instrctr, cycles_x1703_outr_Foreach.io.count, iters_x1703_outr_Foreach.io.count, 0.U, 0.U)
      val b924 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b924.suggestName("b924")
      val b924_chain = Module(new RegChainPass(3, 32, myName = "b924_chain")); b924_chain.io <> DontCare
      b924_chain.chain_pass(b924, io.sigsOut.smDoneIn.head)
      val b924_chain_read_1 = b924_chain.read(1).FP(true,32,0)
      val b924_chain_read_2 = b924_chain.read(2).FP(true,32,0)
      val b925 = ~io.sigsIn.cchainOutputs.head.oobs(0); b925.suggestName("b925")
      val b925_chain = Module(new RegChainPass(3, 1, myName = "b925_chain")); b925_chain.io <> DontCare
      b925_chain.chain_pass(b925, io.sigsOut.smDoneIn.head)
      val b925_chain_read_1: Bool = b925_chain.read(1).apply(0)
      val b925_chain_read_2: Bool = b925_chain.read(2).apply(0)
      val x927_reg = (new x927_reg).m.io.asInstanceOf[NBufInterface]
      val x932_inr_UnitPipe = new x932_inr_UnitPipe_kernel(List(b925,b914), List(b924), List(x927_reg), List(x878_M) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x932_inr_UnitPipe.sm.io.ctrDone := risingEdge(x932_inr_UnitPipe.sm.io.ctrInc)
      b924_chain.connectStageCtrl((x932_inr_UnitPipe.done).DS(1.toInt, rr, x932_inr_UnitPipe.sm.io.backpressure), x932_inr_UnitPipe.baseEn, 0)
      b925_chain.connectStageCtrl((x932_inr_UnitPipe.done).DS(1.toInt, rr, x932_inr_UnitPipe.sm.io.backpressure), x932_inr_UnitPipe.baseEn, 0)
      x932_inr_UnitPipe.backpressure := true.B | x932_inr_UnitPipe.sm.io.doneLatch
      x932_inr_UnitPipe.forwardpressure := (true.B) && (true.B) | x932_inr_UnitPipe.sm.io.doneLatch
      x932_inr_UnitPipe.sm.io.enableOut.zip(x932_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x932_inr_UnitPipe.sm.io.break := false.B
      x932_inr_UnitPipe.mask := true.B & b925 & b914
      x932_inr_UnitPipe.configure("x932_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x932_inr_UnitPipe.kernel()
      val x934_tileA_sram_0 = (new x934_tileA_sram_0).m.io.asInstanceOf[NBufInterface]
      val x1002_outr_UnitPipe_DenseTransfer = new x1002_outr_UnitPipe_DenseTransfer_kernel(List(x937), List(x935), List(x934_tileA_sram_0,x916_reg,x927_reg), List(b913,b924_chain_read_1,x900_a), List(b925_chain_read_1,b914), List(x880_K) ,  Some(me), List(), 1, 2, 2, List(1), List(32), breakpoints, instrctrs.toList, rr)
      b924_chain.connectStageCtrl((x1002_outr_UnitPipe_DenseTransfer.done).DS(1.toInt, rr, x1002_outr_UnitPipe_DenseTransfer.sm.io.backpressure), x1002_outr_UnitPipe_DenseTransfer.baseEn, 1)
      b925_chain.connectStageCtrl((x1002_outr_UnitPipe_DenseTransfer.done).DS(1.toInt, rr, x1002_outr_UnitPipe_DenseTransfer.sm.io.backpressure), x1002_outr_UnitPipe_DenseTransfer.baseEn, 1)
      x1002_outr_UnitPipe_DenseTransfer.backpressure := true.B | x1002_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x1002_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x1002_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x1002_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x1002_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x1002_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x1002_outr_UnitPipe_DenseTransfer.mask := true.B & b925_chain_read_1 & b914
      x1002_outr_UnitPipe_DenseTransfer.configure("x1002_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1002_outr_UnitPipe_DenseTransfer.kernel()
      val x1763_rd_x879 = Wire(new FixedPoint(true, 32, 0))
      x1763_rd_x879.r := x879_N.r
      val x1003_ctr = new CtrObject(Left(Some(0)), Right(x1763_rd_x879), Left(Some(16)), 1, 32, false)
      val x1004_ctrchain = (new CChainObject(List[CtrObject](x1003_ctr), "x1004_ctrchain")).cchain.io 
      x1004_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1004_ctrchain_p", (x1004_ctrchain.par, x1004_ctrchain.widths))
      val x1812_outr_Foreach = new x1812_outr_Foreach_kernel(List(x934_tileA_sram_0,x916_reg,x927_reg), List(x1641), List(x1640), List(x879_N), List(x1090,x1022), List(b925_chain_read_2,b914), List(b913,b924_chain_read_2,x906_c,x903_b), List(x1020,x1639,x1088) ,  Some(me), List(x1004_ctrchain), 2, 4, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1812_outr_Foreach.sm.io.ctrDone := (x1812_outr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      b924_chain.connectStageCtrl((x1812_outr_Foreach.done).DS(1.toInt, rr, x1812_outr_Foreach.sm.io.backpressure), x1812_outr_Foreach.baseEn, 2)
      b925_chain.connectStageCtrl((x1812_outr_Foreach.done).DS(1.toInt, rr, x1812_outr_Foreach.sm.io.backpressure), x1812_outr_Foreach.baseEn, 2)
      x1812_outr_Foreach.backpressure := true.B | x1812_outr_Foreach.sm.io.doneLatch
      x1812_outr_Foreach.forwardpressure := (true.B) && (true.B) | x1812_outr_Foreach.sm.io.doneLatch
      x1812_outr_Foreach.sm.io.enableOut.zip(x1812_outr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1812_outr_Foreach.sm.io.break := false.B
      x1812_outr_Foreach.mask := ~x1812_outr_Foreach.cchain.head.output.noop & b925_chain_read_2 & b914
      x1812_outr_Foreach.configure("x1812_outr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1812_outr_Foreach.kernel()
      x916_reg.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 1)
    }
    val module = Module(new x1703_outr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1703_outr_Foreach **/
