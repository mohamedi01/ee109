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

/** Hierarchy: x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1812_outr_Foreach **/
class x1812_outr_Foreach_kernel(
  list_x934_tileA_sram_0: List[NBufInterface],
  list_x1641: List[DecoupledIO[Bool]],
  list_x1640: List[DecoupledIO[AppStoreData]],
  list_x879_N: List[UInt],
  list_x1090: List[DecoupledIO[AppLoadData]],
  list_b925: List[Bool],
  list_b913: List[FixedPoint],
  list_x1020: List[DecoupledIO[AppCommandDense]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Pipelined, 4, isFSM = false   , latency = 0.0.toInt, myName = "x1812_outr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1812_outr_Foreach_iiCtr"))
  
  abstract class x1812_outr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b913 = Input(new FixedPoint(true, 32, 0))
      val in_x1640 = Decoupled(new AppStoreData(ModuleParams.getParams("x1640_p").asInstanceOf[(Int,Int)] ))
      val in_x934_tileA_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x934_tileA_sram_0_p").asInstanceOf[NBufParams] ))
      val in_x1641 = Flipped(Decoupled(Bool()))
      val in_b924 = Input(new FixedPoint(true, 32, 0))
      val in_x1020 = Decoupled(new AppCommandDense(ModuleParams.getParams("x1020_p").asInstanceOf[(Int,Int)] ))
      val in_b925 = Input(Bool())
      val in_x916_reg = Flipped(new NBufInterface(ModuleParams.getParams("x916_reg_p").asInstanceOf[NBufParams] ))
      val in_x879_N = Input(UInt(64.W))
      val in_x906_c = Input(new FixedPoint(true, 64, 0))
      val in_x1090 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x1090_p").asInstanceOf[(Int, Int)] )))
      val in_x1022 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x1022_p").asInstanceOf[(Int, Int)] )))
      val in_x927_reg = Flipped(new NBufInterface(ModuleParams.getParams("x927_reg_p").asInstanceOf[NBufParams] ))
      val in_x903_b = Input(new FixedPoint(true, 64, 0))
      val in_x1639 = Decoupled(new AppCommandDense(ModuleParams.getParams("x1639_p").asInstanceOf[(Int,Int)] ))
      val in_b914 = Input(Bool())
      val in_x1088 = Decoupled(new AppCommandDense(ModuleParams.getParams("x1088_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(4, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(4, 1))
      val rr = Input(Bool())
    })
    def b913 = {io.in_b913} 
    def x1640 = {io.in_x1640} 
    def x934_tileA_sram_0 = {io.in_x934_tileA_sram_0} ; io.in_x934_tileA_sram_0 := DontCare
    def x1641 = {io.in_x1641} 
    def b924 = {io.in_b924} 
    def x1020 = {io.in_x1020} 
    def b925 = {io.in_b925} 
    def x916_reg = {io.in_x916_reg} ; io.in_x916_reg := DontCare
    def x879_N = {io.in_x879_N} 
    def x906_c = {io.in_x906_c} 
    def x1090 = {io.in_x1090} 
    def x1022 = {io.in_x1022} 
    def x927_reg = {io.in_x927_reg} ; io.in_x927_reg := DontCare
    def x903_b = {io.in_x903_b} 
    def x1639 = {io.in_x1639} 
    def b914 = {io.in_b914} 
    def x1088 = {io.in_x1088} 
  }
  def connectWires0(module: x1812_outr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b913 <> b913
    module.io.in_x1640 <> x1640
    x934_tileA_sram_0.connectLedger(module.io.in_x934_tileA_sram_0)
    module.io.in_x1641 <> x1641
    module.io.in_b924 <> b924
    module.io.in_x1020 <> x1020
    module.io.in_b925 <> b925
    x916_reg.connectLedger(module.io.in_x916_reg)
    module.io.in_x879_N <> x879_N
    module.io.in_x906_c <> x906_c
    module.io.in_x1090 <> x1090
    module.io.in_x1022 <> x1022
    x927_reg.connectLedger(module.io.in_x927_reg)
    module.io.in_x903_b <> x903_b
    module.io.in_x1639 <> x1639
    module.io.in_b914 <> b914
    module.io.in_x1088 <> x1088
  }
  val x934_tileA_sram_0 = list_x934_tileA_sram_0(0)
  val x916_reg = list_x934_tileA_sram_0(1)
  val x927_reg = list_x934_tileA_sram_0(2)
  val x1641 = list_x1641(0)
  val x1640 = list_x1640(0)
  val x879_N = list_x879_N(0)
  val x1090 = list_x1090(0)
  val x1022 = list_x1090(1)
  val b925 = list_b925(0)
  val b914 = list_b925(1)
  val b913 = list_b913(0)
  val b924 = list_b913(1)
  val x906_c = list_b913(2)
  val x903_b = list_b913(3)
  val x1020 = list_x1020(0)
  val x1639 = list_x1020(1)
  val x1088 = list_x1020(2)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1812_outr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1812_outr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1812_outr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1812_outr_Foreach = Module(new InstrumentationCounter())
      val iters_x1812_outr_Foreach = Module(new InstrumentationCounter())
      cycles_x1812_outr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1812_outr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1812_instrctr, cycles_x1812_outr_Foreach.io.count, iters_x1812_outr_Foreach.io.count, 0.U, 0.U)
      val b1005 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1005.suggestName("b1005")
      val b1005_chain = Module(new RegChainPass(4, 32, myName = "b1005_chain")); b1005_chain.io <> DontCare
      b1005_chain.chain_pass(b1005, io.sigsOut.smDoneIn.head)
      val b1005_chain_read_1 = b1005_chain.read(1).FP(true,32,0)
      val b1005_chain_read_2 = b1005_chain.read(2).FP(true,32,0)
      val b1005_chain_read_3 = b1005_chain.read(3).FP(true,32,0)
      val b1006 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1006.suggestName("b1006")
      val b1006_chain = Module(new RegChainPass(4, 1, myName = "b1006_chain")); b1006_chain.io <> DontCare
      b1006_chain.chain_pass(b1006, io.sigsOut.smDoneIn.head)
      val b1006_chain_read_1: Bool = b1006_chain.read(1).apply(0)
      val b1006_chain_read_2: Bool = b1006_chain.read(2).apply(0)
      val b1006_chain_read_3: Bool = b1006_chain.read(3).apply(0)
      val x1011_reg = (new x1011_reg).m.io.asInstanceOf[NBufInterface]
      val x1016_inr_UnitPipe = new x1016_inr_UnitPipe_kernel(List(b925,b1006,b914), List(b1005), List(x1011_reg), List(x879_N) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1016_inr_UnitPipe.sm.io.ctrDone := risingEdge(x1016_inr_UnitPipe.sm.io.ctrInc)
      b1005_chain.connectStageCtrl((x1016_inr_UnitPipe.done).DS(1.toInt, rr, x1016_inr_UnitPipe.sm.io.backpressure), x1016_inr_UnitPipe.baseEn, 0)
      b1006_chain.connectStageCtrl((x1016_inr_UnitPipe.done).DS(1.toInt, rr, x1016_inr_UnitPipe.sm.io.backpressure), x1016_inr_UnitPipe.baseEn, 0)
      x1016_inr_UnitPipe.backpressure := true.B | x1016_inr_UnitPipe.sm.io.doneLatch
      x1016_inr_UnitPipe.forwardpressure := (true.B) && (true.B) | x1016_inr_UnitPipe.sm.io.doneLatch
      x1016_inr_UnitPipe.sm.io.enableOut.zip(x1016_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x1016_inr_UnitPipe.sm.io.break := false.B
      x1016_inr_UnitPipe.mask := true.B & b1006 & b925 & b914
      x1016_inr_UnitPipe.configure("x1016_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1016_inr_UnitPipe.kernel()
      val x1017_tileB_sram_0 = (new x1017_tileB_sram_0).m.io.asInstanceOf[NBufInterface]
      val x1018_tileC_sram_0 = (new x1018_tileC_sram_0).m.io.asInstanceOf[NBufInterface]
      val x1019_tileC_sram_1 = (new x1019_tileC_sram_1).m.io.asInstanceOf[NBufInterface]
      val x1811 = new x1811_kernel(List(b1005_chain_read_1,b913,b924,x906_c,x903_b), List(x916_reg,x1017_tileB_sram_0,x1011_reg,x927_reg,x1018_tileC_sram_0,x1019_tileC_sram_1), List(x1020,x1088), List(x879_N), List(x1090,x1022), List(b925,b1006_chain_read_1,b914) ,  Some(me), List(), 1, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1811.sm.io.ctrDone := risingEdge(x1811.sm.io.ctrInc)
      b1005_chain.connectStageCtrl((x1811.done).DS(1.toInt, rr, x1811.sm.io.backpressure), x1811.baseEn, 1)
      b1006_chain.connectStageCtrl((x1811.done).DS(1.toInt, rr, x1811.sm.io.backpressure), x1811.baseEn, 1)
      x1811.backpressure := true.B | x1811.sm.io.doneLatch
      x1811.forwardpressure := (true.B) && (true.B) | x1811.sm.io.doneLatch
      x1811.sm.io.enableOut.zip(x1811.smEnableOuts).foreach{case (l,r) => r := l}
      x1811.sm.io.break := false.B
      x1811.mask := true.B & true.B
      x1811.configure("x1811", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1811.kernel()
      val x1770_rd_x927 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1770_rd_x927""")
      val x1770_rd_x927_banks = List[UInt]()
      val x1770_rd_x927_ofs = List[UInt]()
      val x1770_rd_x927_en = List[Bool](true.B)
      val x1770_rd_x927_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1770_rd_x927_shared_en")
      x1770_rd_x927.toSeq.zip(x927_reg.connectRPort(1770, x1770_rd_x927_banks, x1770_rd_x927_ofs, io.sigsIn.backpressure, x1770_rd_x927_en.map(_ && x1770_rd_x927_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1159_ctr = new CtrObject(Left(Some(0)), Right(x1770_rd_x927), Left(Some(1)), 4, 32, false)
      val x1160_ctrchain = (new CChainObject(List[CtrObject](x1159_ctr), "x1160_ctrchain")).cchain.io 
      x1160_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1160_ctrchain_p", (x1160_ctrchain.par, x1160_ctrchain.widths))
      val x1638_outr_Foreach = new x1638_outr_Foreach_kernel(List(b925,b1006_chain_read_2,b914), List(x934_tileA_sram_0,x916_reg,x1017_tileB_sram_0,x1011_reg,x1018_tileC_sram_0,x1019_tileC_sram_1) ,  Some(me), List(x1160_ctrchain), 2, 1, 1, List(4), List(32), breakpoints, instrctrs.toList, rr)
      x1638_outr_Foreach.sm.io.ctrDone := (x1638_outr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      b1005_chain.connectStageCtrl((x1638_outr_Foreach.done).DS(1.toInt, rr, x1638_outr_Foreach.sm.io.backpressure), x1638_outr_Foreach.baseEn, 2)
      b1006_chain.connectStageCtrl((x1638_outr_Foreach.done).DS(1.toInt, rr, x1638_outr_Foreach.sm.io.backpressure), x1638_outr_Foreach.baseEn, 2)
      x1638_outr_Foreach.backpressure := true.B | x1638_outr_Foreach.sm.io.doneLatch
      x1638_outr_Foreach.forwardpressure := (true.B) && (true.B) | x1638_outr_Foreach.sm.io.doneLatch
      x1638_outr_Foreach.sm.io.enableOut.zip(x1638_outr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1638_outr_Foreach.sm.io.break := false.B
      x1638_outr_Foreach.mask := ~x1638_outr_Foreach.cchain.head.output.noop & b1006_chain_read_2 & b925 & b914
      x1638_outr_Foreach.configure("x1638_outr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1638_outr_Foreach.kernel()
      val x1701_outr_UnitPipe_DenseTransfer = new x1701_outr_UnitPipe_DenseTransfer_kernel(List(x1641), List(x1640), List(x879_N), List(b1005_chain_read_3,b924,x906_c), List(x1639), List(b925,b1006_chain_read_3,b914), List(x1011_reg,x927_reg,x1019_tileC_sram_1) ,  Some(me), List(), 3, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      b1005_chain.connectStageCtrl((x1701_outr_UnitPipe_DenseTransfer.done).DS(1.toInt, rr, x1701_outr_UnitPipe_DenseTransfer.sm.io.backpressure), x1701_outr_UnitPipe_DenseTransfer.baseEn, 3)
      b1006_chain.connectStageCtrl((x1701_outr_UnitPipe_DenseTransfer.done).DS(1.toInt, rr, x1701_outr_UnitPipe_DenseTransfer.sm.io.backpressure), x1701_outr_UnitPipe_DenseTransfer.baseEn, 3)
      x1701_outr_UnitPipe_DenseTransfer.backpressure := true.B | x1701_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x1701_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x1701_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x1701_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x1701_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x1701_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x1701_outr_UnitPipe_DenseTransfer.mask := true.B & b1006_chain_read_3 & b925 & b914
      x1701_outr_UnitPipe_DenseTransfer.configure("x1701_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1701_outr_UnitPipe_DenseTransfer.kernel()
      x927_reg.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 2)
      x934_tileA_sram_0.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 1)
    }
    val module = Module(new x1812_outr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1812_outr_Foreach **/
