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

/** Hierarchy: x1083 -> x1084 -> x1085 -> x1334 -> x1335 -> x1250 -> x1251 -> x810 **/
/** BEGIN None x1083_inr_Foreach **/
class x1083_inr_Foreach_kernel(
  list_x1021: List[DecoupledIO[AppLoadData]],
  list_x1055_reg: List[StandardInterface],
  list_b1052: List[FixedPoint],
  list_x1016_tileB_sram_0: List[NBufInterface],
  list_b1053: List[Bool],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 3.0.toInt, myName = "x1083_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1083_inr_Foreach_iiCtr"))
  
  abstract class x1083_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x1055_reg = Flipped(new StandardInterface(ModuleParams.getParams("x1055_reg_p").asInstanceOf[MemParams] ))
      val in_x1021 = Flipped(Decoupled(new AppLoadData(ModuleParams.getParams("x1021_p").asInstanceOf[(Int, Int)] )))
      val in_x1016_tileB_sram_0 = Flipped(new NBufInterface(ModuleParams.getParams("x1016_tileB_sram_0_p").asInstanceOf[NBufParams] ))
      val in_x1054_reg = Flipped(new StandardInterface(ModuleParams.getParams("x1054_reg_p").asInstanceOf[MemParams] ))
      val in_b1053 = Input(Bool())
      val in_b1052 = Input(new FixedPoint(true, 32, 0))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x1055_reg = {io.in_x1055_reg} ; io.in_x1055_reg := DontCare
    def x1021 = {io.in_x1021} 
    def x1016_tileB_sram_0 = {io.in_x1016_tileB_sram_0} ; io.in_x1016_tileB_sram_0 := DontCare
    def x1054_reg = {io.in_x1054_reg} ; io.in_x1054_reg := DontCare
    def b1053 = {io.in_b1053} 
    def b1052 = {io.in_b1052} 
  }
  def connectWires0(module: x1083_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    x1055_reg.connectLedger(module.io.in_x1055_reg)
    module.io.in_x1021 <> x1021
    x1016_tileB_sram_0.connectLedger(module.io.in_x1016_tileB_sram_0)
    x1054_reg.connectLedger(module.io.in_x1054_reg)
    module.io.in_b1053 <> b1053
    module.io.in_b1052 <> b1052
  }
  val x1021 = list_x1021(0)
  val x1055_reg = list_x1055_reg(0)
  val x1054_reg = list_x1055_reg(1)
  val b1052 = list_b1052(0)
  val x1016_tileB_sram_0 = list_x1016_tileB_sram_0(0)
  val b1053 = list_b1053(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1083_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x1083_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1083_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1083_inr_Foreach = Module(new InstrumentationCounter())
      val iters_x1083_inr_Foreach = Module(new InstrumentationCounter())
      cycles_x1083_inr_Foreach.io.enable := io.sigsIn.baseEn
      iters_x1083_inr_Foreach.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x1083_inr_Foreach = Module(new InstrumentationCounter())
      val idles_x1083_inr_Foreach = Module(new InstrumentationCounter())
      stalls_x1083_inr_Foreach.io.enable := io.sigsIn.baseEn & ~(true.B)
      idles_x1083_inr_Foreach.io.enable := io.sigsIn.baseEn & ~((x1021.valid) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X1083_instrctr, cycles_x1083_inr_Foreach.io.count, iters_x1083_inr_Foreach.io.count, stalls_x1083_inr_Foreach.io.count, idles_x1083_inr_Foreach.io.count)
      val b1069 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b1069.suggestName("b1069")
      val b1070 = ~io.sigsIn.cchainOutputs.head.oobs(0); b1070.suggestName("b1070")
      val x1071_rd_x1054 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1071_rd_x1054""")
      val x1071_rd_x1054_banks = List[UInt]()
      val x1071_rd_x1054_ofs = List[UInt]()
      val x1071_rd_x1054_en = List[Bool](true.B)
      val x1071_rd_x1054_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1071_rd_x1054_shared_en")
      x1071_rd_x1054.toSeq.zip(x1054_reg.connectRPort(1071, x1071_rd_x1054_banks, x1071_rd_x1054_ofs, io.sigsIn.backpressure, x1071_rd_x1054_en.map(_ && x1071_rd_x1054_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1072 = Wire(Bool()).suggestName("""x1072""")
      x1072.r := Math.lte(x1071_rd_x1054, b1069, Some(0.4), true.B,"x1072").r
      val x1073_rd_x1055 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1073_rd_x1055""")
      val x1073_rd_x1055_banks = List[UInt]()
      val x1073_rd_x1055_ofs = List[UInt]()
      val x1073_rd_x1055_en = List[Bool](true.B)
      val x1073_rd_x1055_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1073_rd_x1055_shared_en")
      x1073_rd_x1055.toSeq.zip(x1055_reg.connectRPort(1073, x1073_rd_x1055_banks, x1073_rd_x1055_ofs, io.sigsIn.backpressure, x1073_rd_x1055_en.map(_ && x1073_rd_x1055_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x1074 = Wire(Bool()).suggestName("""x1074""")
      x1074.r := Math.lt(b1069, x1073_rd_x1055, Some(0.4), true.B,"x1074").r
      val x1075 = Wire(Bool()).suggestName("""x1075""")
      x1075 := x1072 & x1074
      val x1076_sub = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1076_sub""")
      x1076_sub.r := Math.sub(b1069,x1071_rd_x1054,Some(1.0), true.B, Truncate, Wrapping, "x1076_sub").r
      val x1077 = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("""x1077""")
      x1021.ready := b1070 & b1053 & (io.sigsIn.datapathEn) 
      (0 until 1).map{ i => x1077(i).r := x1021.bits.rdata(i).r }
      val x1360 = Wire(Vec(1, new FixedPoint(true, 24, 8))).suggestName("x1360_x1077_D1") 
      (0 until 1).foreach{i => x1360(i).r := getRetimed(x1077(i).r, 1.toInt, io.sigsIn.backpressure & true.B)}
      // x1078 = VecApply(x1360,0)
      val x1078_elem_0 = Wire(new FixedPoint(true, 24, 8)).suggestName("""x1078_elem_0""")
      x1078_elem_0.r := x1360(0).r
      val x1080 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1080""")
      x1080.r := Math.arith_left_shift(b1052, 4, Some(0.2), true.B,"x1080").r
      val x1361 = Wire(new FixedPoint(true, 32, 0)).suggestName("x1361_x1080_D1") 
      x1361.r := getRetimed(x1080.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1081_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1081_sum""")
      x1081_sum.r := Math.add(x1361,x1076_sub,Some(1.0), true.B, Truncate, Wrapping, "x1081_sum").r
      val x1362 = Wire(new FixedPoint(true, 24, 8)).suggestName("x1362_x1078_elem_0_D1") 
      x1362.r := getRetimed(x1078_elem_0.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1363 = Wire(Bool()).suggestName("x1363_x1075_D2") 
      x1363.r := getRetimed(x1075.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x1364 = Wire(Bool()).suggestName("x1364_b1070_D2") 
      x1364.r := getRetimed(b1070.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x1365 = Wire(Bool()).suggestName("x1365_b1053_D2") 
      x1365.r := getRetimed(b1053.r, 2.toInt, io.sigsIn.backpressure & true.B)
      val x1082_wr_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x1082_wr_ofs = List[UInt](x1081_sum.r)
      val x1082_wr_en = List[Bool](true.B)
      val x1082_wr_data = List[UInt](x1362.r)
      x1016_tileB_sram_0.connectWPort(1082, x1082_wr_banks, x1082_wr_ofs, x1082_wr_data, x1082_wr_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.0.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && x1363 & x1364 & x1365))
    }
    val module = Module(new x1083_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnrolledForeach x1083_inr_Foreach **/
