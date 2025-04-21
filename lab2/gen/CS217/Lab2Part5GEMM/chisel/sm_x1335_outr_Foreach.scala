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

/** Hierarchy: x1335 -> x1250 -> x1251 -> x810 **/
/** BEGIN None x1335_outr_Foreach **/
class x1335_outr_Foreach_kernel(
  list_x934_tileA_sram_0: List[NBufInterface],
  list_x1021: List[DecoupledIO[AppLoadData]],
  list_x1190: List[DecoupledIO[AppStoreData]],
  list_x1086: List[DecoupledIO[AppCommandDense]],
  list_x1191: List[DecoupledIO[Bool]],
  list_x879_N: List[UInt],
  list_b925: List[Bool],
  list_b913: List[FixedPoint],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Pipelined, 4, isFSM = false   , latency = 0.0.toInt, myName = "x1335_outr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1335_outr_Foreach_iiCtr"))
  
  abstract class x1335_outr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x1190 = Decoupled(new AppStoreData(ModuleParams.getParams("x1190_p").asInstanceOf[(Int,Int)] ))
      val in_b913 = Input(new FixedPoint(true, 32, 0))
      val in_x934_tileA_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x934_tileA_sram_0_p").asInstanceOf[NBufParams] ))
      val in_x1191 = Flipped(Decoupled(Bool()))
      val in_b924 = Input(new FixedPoint(true, 32, 0))
      val in_b925 = Input(Bool())
      val in_x1021 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x1021_p").asInstanceOf[(Int, Int)] )))
      val in_x916_reg = Flipped(new NBufInterface(ModuleParams.getParams("x916_reg_p").asInstanceOf[NBufParams] ))
      val in_x879_N = Input(UInt(64.W))
      val in_x1086 = Decoupled(new AppCommandDense(ModuleParams.getParams("x1086_p").asInstanceOf[(Int,Int)] ))
      val in_x906_c = Input(new FixedPoint(true, 64, 0))
      val in_x927_reg = Flipped(new NBufInterface(ModuleParams.getParams("x927_reg_p").asInstanceOf[NBufParams] ))
      val in_x903_b = Input(new FixedPoint(true, 64, 0))
      val in_x1189 = Decoupled(new AppCommandDense(ModuleParams.getParams("x1189_p").asInstanceOf[(Int,Int)] ))
      val in_b914 = Input(Bool())
      val in_x1019 = Decoupled(new AppCommandDense(ModuleParams.getParams("x1019_p").asInstanceOf[(Int,Int)] ))
      val in_x1088 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x1088_p").asInstanceOf[(Int, Int)] )))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(4, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(4, 1))
      val rr = Input(Bool())
    })
    def x1190 = {io.in_x1190} 
    def b913 = {io.in_b913} 
    def x934_tileA_sram_0 = {io.in_x934_tileA_sram_0} ; io.in_x934_tileA_sram_0 := DontCare
    def x1191 = {io.in_x1191} 
    def b924 = {io.in_b924} 
    def b925 = {io.in_b925} 
    def x1021 = {io.in_x1021} 
    def x916_reg = {io.in_x916_reg} ; io.in_x916_reg := DontCare
    def x879_N = {io.in_x879_N} 
    def x1086 = {io.in_x1086} 
    def x906_c = {io.in_x906_c} 
    def x927_reg = {io.in_x927_reg} ; io.in_x927_reg := DontCare
    def x903_b = {io.in_x903_b} 
    def x1189 = {io.in_x1189} 
    def b914 = {io.in_b914} 
    def x1019 = {io.in_x1019} 
    def x1088 = {io.in_x1088} 
  }
  def connectWires0(module: x1335_outr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x1190 <> x1190
    module.io.in_b913 <> b913
    x934_tileA_sram_0.connectLedger(module.io.in_x934_tileA_sram_0)
    module.io.in_x1191 <> x1191
    module.io.in_b924 <> b924
    module.io.in_b925 <> b925
    module.io.in_x1021 <> x1021
    x916_reg.connectLedger(module.io.in_x916_reg)
    module.io.in_x879_N <> x879_N
    module.io.in_x1086 <> x1086
    module.io.in_x906_c <> x906_c
    x927_reg.connectLedger(module.io.in_x927_reg)
    module.io.in_x903_b <> x903_b
    module.io.in_x1189 <> x1189
    module.io.in_b914 <> b914
    module.io.in_x1019 <> x1019
    module.io.in_x1088 <> x1088
  }
  val x934_tileA_sram_0 = list_x934_tileA_sram_0(0)
  val x916_reg = list_x934_tileA_sram_0(1)
  val x927_reg = list_x934_tileA_sram_0(2)
  val x1021 = list_x1021(0)
  val x1088 = list_x1021(1)
  val x1190 = list_x1190(0)
  val x1086 = list_x1086(0)
  val x1189 = list_x1086(1)
  val x1019 = list_x1086(2)
  val x1191 = list_x1191(0)
  val x879_N = list_x879_N(0)
  val b925 = list_b925(0)
  val b914 = list_b925(1)
  val b913 = list_b913(0)
  val b924 = list_b913(1)
  val x906_c = list_b913(2)
  val x903_b = list_b913(3)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1335_outr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1335_outr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1335_outr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1335_outr_Foreach = Module(new InstrumentationCounter())
      val iters_x1335_outr_Foreach = Module(new InstrumentationCounter())
      cycles_x1335_outr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1335_outr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X1335_instrctr, cycles_x1335_outr_Foreach.io.count, iters_x1335_outr_Foreach.io.count, 0.U, 0.U)
      val b1004 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1004.suggestName("b1004")
      val b1004_chain = Module(new RegChainPass(4, 32, myName = "b1004_chain")); b1004_chain.io <> DontCare
      b1004_chain.chain_pass(b1004, io.sigsOut.smDoneIn.head)
      val b1004_chain_read_1 = b1004_chain.read(1).FP(true,32,0)
      val b1004_chain_read_2 = b1004_chain.read(2).FP(true,32,0)
      val b1004_chain_read_3 = b1004_chain.read(3).FP(true,32,0)
      val b1005 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1005.suggestName("b1005")
      val b1005_chain = Module(new RegChainPass(4, 1, myName = "b1005_chain")); b1005_chain.io <> DontCare
      b1005_chain.chain_pass(b1005, io.sigsOut.smDoneIn.head)
      val b1005_chain_read_1: Bool = b1005_chain.read(1).apply(0)
      val b1005_chain_read_2: Bool = b1005_chain.read(2).apply(0)
      val b1005_chain_read_3: Bool = b1005_chain.read(3).apply(0)
      val x1010_reg = (new x1010_reg).m.io.asInstanceOf[NBufInterface]
      val x1015_inr_UnitPipe = new x1015_inr_UnitPipe_kernel(List(b1005,b925,b914), List(b1004), List(x1010_reg), List(x879_N) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1015_inr_UnitPipe.sm.io.ctrDone := risingEdge(x1015_inr_UnitPipe.sm.io.ctrInc)
      b1004_chain.connectStageCtrl((x1015_inr_UnitPipe.done).DS(1.toInt, rr, x1015_inr_UnitPipe.sm.io.backpressure), x1015_inr_UnitPipe.baseEn, 0)
      b1005_chain.connectStageCtrl((x1015_inr_UnitPipe.done).DS(1.toInt, rr, x1015_inr_UnitPipe.sm.io.backpressure), x1015_inr_UnitPipe.baseEn, 0)
      x1015_inr_UnitPipe.backpressure := true.B | x1015_inr_UnitPipe.sm.io.doneLatch
      x1015_inr_UnitPipe.forwardpressure := (true.B) && (true.B) | x1015_inr_UnitPipe.sm.io.doneLatch
      x1015_inr_UnitPipe.sm.io.enableOut.zip(x1015_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x1015_inr_UnitPipe.sm.io.break := false.B
      x1015_inr_UnitPipe.mask := true.B & b1005 & b925 & b914
      x1015_inr_UnitPipe.configure("x1015_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1015_inr_UnitPipe.kernel()
      val x1016_tileB_sram_0 = (new x1016_tileB_sram_0).m.io.asInstanceOf[NBufInterface]
      val x1017_tileC_sram_0 = (new x1017_tileC_sram_0).m.io.asInstanceOf[NBufInterface]
      val x1018_tileC_sram_1 = (new x1018_tileC_sram_1).m.io.asInstanceOf[NBufInterface]
      val x1334 = new x1334_kernel(List(x1021,x1088), List(x916_reg,x1017_tileC_sram_0,x1016_tileB_sram_0,x927_reg,x1018_tileC_sram_1,x1010_reg), List(x879_N), List(b913,b924,x906_c,x903_b,b1004_chain_read_1), List(x1086,x1019), List(b1005_chain_read_1,b925,b914) ,  Some(me), List(), 1, 2, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1334.sm.io.ctrDone := risingEdge(x1334.sm.io.ctrInc)
      b1004_chain.connectStageCtrl((x1334.done).DS(1.toInt, rr, x1334.sm.io.backpressure), x1334.baseEn, 1)
      b1005_chain.connectStageCtrl((x1334.done).DS(1.toInt, rr, x1334.sm.io.backpressure), x1334.baseEn, 1)
      x1334.backpressure := true.B | x1334.sm.io.doneLatch
      x1334.forwardpressure := (true.B) && (true.B) | x1334.sm.io.doneLatch
      x1334.sm.io.enableOut.zip(x1334.smEnableOuts).foreach{case (l,r) => r := l}
      x1334.sm.io.break := false.B
      x1334.mask := true.B & true.B
      x1334.configure("x1334", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1334.kernel()
      val x1317_rd_x927 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1317_rd_x927""")
      val x1317_rd_x927_banks = List[UInt]()
      val x1317_rd_x927_ofs = List[UInt]()
      val x1317_rd_x927_en = List[Bool](true.B)
      val x1317_rd_x927_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1317_rd_x927_shared_en")
      x1317_rd_x927.toSeq.zip(x927_reg.connectRPort(1317, x1317_rd_x927_banks, x1317_rd_x927_ofs, io.sigsIn.backpressure, x1317_rd_x927_en.map(_ && x1317_rd_x927_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1154_ctr = new CtrObject(Left(Some(0)), Right(x1317_rd_x927), Left(Some(1)), 1, 32, false)
      val x1155_ctrchain = (new CChainObject(List[CtrObject](x1154_ctr), "x1155_ctrchain")).cchain.io 
      x1155_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x1155_ctrchain_p", (x1155_ctrchain.par, x1155_ctrchain.widths))
      val x1188_outr_Foreach = new x1188_outr_Foreach_kernel(List(b1005_chain_read_2,b925,b914), List(x934_tileA_sram_0,x916_reg,x1017_tileC_sram_0,x1016_tileB_sram_0,x1018_tileC_sram_1,x1010_reg) ,  Some(me), List(x1155_ctrchain), 2, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x1188_outr_Foreach.sm.io.ctrDone := (x1188_outr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      b1004_chain.connectStageCtrl((x1188_outr_Foreach.done).DS(1.toInt, rr, x1188_outr_Foreach.sm.io.backpressure), x1188_outr_Foreach.baseEn, 2)
      b1005_chain.connectStageCtrl((x1188_outr_Foreach.done).DS(1.toInt, rr, x1188_outr_Foreach.sm.io.backpressure), x1188_outr_Foreach.baseEn, 2)
      x1188_outr_Foreach.backpressure := true.B | x1188_outr_Foreach.sm.io.doneLatch
      x1188_outr_Foreach.forwardpressure := (true.B) && (true.B) | x1188_outr_Foreach.sm.io.doneLatch
      x1188_outr_Foreach.sm.io.enableOut.zip(x1188_outr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x1188_outr_Foreach.sm.io.break := false.B
      x1188_outr_Foreach.mask := ~x1188_outr_Foreach.cchain.head.output.noop & b1005_chain_read_2 & b925 & b914
      x1188_outr_Foreach.configure("x1188_outr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1188_outr_Foreach.kernel()
      val x1248_outr_UnitPipe_DenseTransfer = new x1248_outr_UnitPipe_DenseTransfer_kernel(List(b924,x906_c,b1004_chain_read_3), List(x1189), List(x1190), List(x1191), List(x879_N), List(x927_reg,x1018_tileC_sram_1,x1010_reg), List(b1005_chain_read_3,b925,b914) ,  Some(me), List(), 3, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      b1004_chain.connectStageCtrl((x1248_outr_UnitPipe_DenseTransfer.done).DS(1.toInt, rr, x1248_outr_UnitPipe_DenseTransfer.sm.io.backpressure), x1248_outr_UnitPipe_DenseTransfer.baseEn, 3)
      b1005_chain.connectStageCtrl((x1248_outr_UnitPipe_DenseTransfer.done).DS(1.toInt, rr, x1248_outr_UnitPipe_DenseTransfer.sm.io.backpressure), x1248_outr_UnitPipe_DenseTransfer.baseEn, 3)
      x1248_outr_UnitPipe_DenseTransfer.backpressure := true.B | x1248_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x1248_outr_UnitPipe_DenseTransfer.forwardpressure := (true.B) && (true.B) | x1248_outr_UnitPipe_DenseTransfer.sm.io.doneLatch
      x1248_outr_UnitPipe_DenseTransfer.sm.io.enableOut.zip(x1248_outr_UnitPipe_DenseTransfer.smEnableOuts).foreach{case (l,r) => r := l}
      x1248_outr_UnitPipe_DenseTransfer.sm.io.break := false.B
      x1248_outr_UnitPipe_DenseTransfer.mask := true.B & b1005_chain_read_3 & b925 & b914
      x1248_outr_UnitPipe_DenseTransfer.configure("x1248_outr_UnitPipe_DenseTransfer", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x1248_outr_UnitPipe_DenseTransfer.kernel()
      x927_reg.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 2)
      x934_tileA_sram_0.connectStageCtrl((io.sigsIn.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B), io.sigsIn.baseEn, 1)
    }
    val module = Module(new x1335_outr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1335_outr_Foreach **/
