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

/** Hierarchy: x1672 -> x1695 -> x1700 -> x1701 -> x1812 -> x1703 -> x1704 -> x810 **/
/** BEGIN None x1672_inr_UnitPipe **/
class x1672_inr_UnitPipe_kernel(
  list_x1648_reg: List[StandardInterface],
  list_x1011_reg: List[NBufInterface],
  list_x879_N: List[UInt],
  list_x1639: List[DecoupledIO[AppCommandDense]],
  list_b1005: List[FixedPoint],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Sequenced, false   , latency = 11.8.toInt, myName = "x1672_inr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x1672_inr_UnitPipe_iiCtr"))
  
  abstract class x1672_inr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b1005 = Input(new FixedPoint(true, 32, 0))
      val in_b924 = Input(new FixedPoint(true, 32, 0))
      val in_x879_N = Input(UInt(64.W))
      val in_b1644 = Input(new FixedPoint(true, 32, 0))
      val in_x906_c = Input(new FixedPoint(true, 64, 0))
      val in_x1648_reg = Flipped(new StandardInterface(ModuleParams.getParams("x1648_reg_p").asInstanceOf[MemParams] ))
      val in_x1011_reg = Flipped(new NBufInterface(ModuleParams.getParams("x1011_reg_p").asInstanceOf[NBufParams] ))
      val in_x1647_reg = Flipped(new StandardInterface(ModuleParams.getParams("x1647_reg_p").asInstanceOf[MemParams] ))
      val in_x1646_reg = Flipped(new StandardInterface(ModuleParams.getParams("x1646_reg_p").asInstanceOf[MemParams] ))
      val in_x1639 = Decoupled(new AppCommandDense(ModuleParams.getParams("x1639_p").asInstanceOf[(Int,Int)] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def b1005 = {io.in_b1005} 
    def b924 = {io.in_b924} 
    def x879_N = {io.in_x879_N} 
    def b1644 = {io.in_b1644} 
    def x906_c = {io.in_x906_c} 
    def x1648_reg = {io.in_x1648_reg} ; io.in_x1648_reg := DontCare
    def x1011_reg = {io.in_x1011_reg} ; io.in_x1011_reg := DontCare
    def x1647_reg = {io.in_x1647_reg} ; io.in_x1647_reg := DontCare
    def x1646_reg = {io.in_x1646_reg} ; io.in_x1646_reg := DontCare
    def x1639 = {io.in_x1639} 
  }
  def connectWires0(module: x1672_inr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b1005 <> b1005
    module.io.in_b924 <> b924
    module.io.in_x879_N <> x879_N
    module.io.in_b1644 <> b1644
    module.io.in_x906_c <> x906_c
    x1648_reg.connectLedger(module.io.in_x1648_reg)
    x1011_reg.connectLedger(module.io.in_x1011_reg)
    x1647_reg.connectLedger(module.io.in_x1647_reg)
    x1646_reg.connectLedger(module.io.in_x1646_reg)
    module.io.in_x1639 <> x1639
  }
  val x1648_reg = list_x1648_reg(0)
  val x1647_reg = list_x1648_reg(1)
  val x1646_reg = list_x1648_reg(2)
  val x1011_reg = list_x1011_reg(0)
  val x879_N = list_x879_N(0)
  val x1639 = list_x1639(0)
  val b1005 = list_b1005(0)
  val b924 = list_b1005(1)
  val b1644 = list_b1005(2)
  val x906_c = list_b1005(3)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x1672_inr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x1672_inr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x1672_inr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x1672_inr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x1672_inr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x1672_inr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x1672_inr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      val stalls_x1672_inr_UnitPipe = Module(new InstrumentationCounter())
      val idles_x1672_inr_UnitPipe = Module(new InstrumentationCounter())
      stalls_x1672_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~(x1639.ready)
      idles_x1672_inr_UnitPipe.io.enable := io.sigsIn.baseEn & ~((true.B) && (true.B))
      Ledger.tieInstrCtr(instrctrs.toList, X1672_instrctr, cycles_x1672_inr_UnitPipe.io.count, iters_x1672_inr_UnitPipe.io.count, stalls_x1672_inr_UnitPipe.io.count, idles_x1672_inr_UnitPipe.io.count)
      val x1649_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1649_sum""")
      val ensig0 = Wire(Bool())
      ensig0 := x1639.ready
      x1649_sum.r := Math.add(b924,b1644,Some(1.0), ensig0, Truncate, Wrapping, "x1649_sum").r
      val x1650_rd_x879 = Wire(new FixedPoint(true, 32, 0))
      x1650_rd_x879.r := x879_N.r
      val x2182 = Wire(new FixedPoint(true, 32, 0)).suggestName("x2182_x1650_rd_x879_D1") 
      x2182.r := getRetimed(x1650_rd_x879.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x2183 = Wire(new FixedPoint(true, 32, 0)).suggestName("x2183_b1005_D1") 
      x2183.r := getRetimed(b1005.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1806 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1806""")
      x1806.r := Math.fma(x1649_sum,x2182,x2183,Some(6.0), ensig0, "x1806").toFixed(x1806, "cast_x1806").r
      val x1653 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1653""")
      x1653.r := Math.arith_right_shift_div(x1806, 4, Some(0.2), ensig0,"x1653").r
      val x1654 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1654""")
      x1654.r := Math.arith_left_shift(x1653, 4, Some(0.2), ensig0,"x1654").r
      val x1807 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1807""")
      x1807.r := Math.arith_left_shift(x1653, 6, Some(0.2), ensig0,"x1807").r
      val x1656_sub = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1656_sub""")
      x1656_sub.r := Math.sub(x1806,x1654,Some(1.0), ensig0, Truncate, Wrapping, "x1656_sub").r
      val x1657_rd_x1011 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1657_rd_x1011""")
      val x1657_rd_x1011_banks = List[UInt]()
      val x1657_rd_x1011_ofs = List[UInt]()
      val x1657_rd_x1011_en = List[Bool](true.B)
      val x1657_rd_x1011_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x1657_rd_x1011_shared_en")
      x1657_rd_x1011.toSeq.zip(x1011_reg.connectRPort(1657, x1657_rd_x1011_banks, x1657_rd_x1011_ofs, io.sigsIn.backpressure, x1657_rd_x1011_en.map(_ && x1657_rd_x1011_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x2184 = Wire(new FixedPoint(true, 32, 0)).suggestName("x2184_x1657_rd_x1011_D8") 
      x2184.r := getRetimed(x1657_rd_x1011.r, 8.toInt, io.sigsIn.backpressure & true.B)
      val x1658_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1658_sum""")
      x1658_sum.r := Math.add(x1656_sub,x2184,Some(1.0), ensig0, Truncate, Wrapping, "x1658_sum").r
      val x1659_sum = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1659_sum""")
      x1659_sum.r := Math.add(x1658_sum,15L.FP(true, 32, 0),Some(1.0), ensig0, Truncate, Wrapping, "x1659_sum").r
      val x1660 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1660""")
      x1660.r := Math.arith_right_shift_div(x1659_sum, 4, Some(0.2), ensig0,"x1660").r
      val x1661 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1661""")
      x1661.r := Math.arith_left_shift(x1660, 4, Some(0.2), ensig0,"x1661").r
      val x1808 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x1808""")
      x1808.r := Math.arith_left_shift(x1660, 6, Some(0.2), ensig0,"x1808").r
      val x1663 = Wire(new FixedPoint(true, 64, 0)).suggestName("""x1663""")
      x1663.r := Math.fix2fix(x1807, true, 64, 0, Some(0.0), ensig0, Truncate, Wrapping, "x1663").r
      val x1664 = x906_c
      val x2185 = Wire(new FixedPoint(true, 64, 0)).suggestName("x2185_x1664_D7") 
      x2185.r := getRetimed(x1664.r, 7.toInt, io.sigsIn.backpressure & true.B)
      val x1665_sum = Wire(new FixedPoint(true, 64, 0)).suggestName("""x1665_sum""")
      x1665_sum.r := Math.add(x1663,x2185,Some(2.0), ensig0, Truncate, Wrapping, "x1665_sum").r
      val x2186 = Wire(new FixedPoint(true, 64, 0)).suggestName("x2186_x1665_sum_D1") 
      x2186.r := getRetimed(x1665_sum.r, 1.toInt, io.sigsIn.backpressure & true.B)
      val x1666_tuple = Wire(UInt(97.W)).suggestName("""x1666_tuple""")
      x1666_tuple.r := ConvAndCat(false.B,x1808.r,x2186.r)
      val x1667 = true.B
      val x2187 = Wire(Bool()).suggestName("x2187_x1667_D10") 
      x2187.r := getRetimed(x1667.r, 10.toInt, io.sigsIn.backpressure & true.B)
      x1639.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.8.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x2187 & io.sigsIn.backpressure
      x1639.bits.addr := x1666_tuple(63,0)
      x1639.bits.size := x1666_tuple(95,64)
      val x1669_wr_x1646_banks = List[UInt]()
      val x1669_wr_x1646_ofs = List[UInt]()
      val x1669_wr_x1646_en = List[Bool](true.B)
      val x1669_wr_x1646_data = List[UInt](x1656_sub.r)
      x1646_reg.connectWPort(1669, x1669_wr_x1646_banks, x1669_wr_x1646_ofs, x1669_wr_x1646_data, x1669_wr_x1646_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(8.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x1670_wr_x1647_banks = List[UInt]()
      val x1670_wr_x1647_ofs = List[UInt]()
      val x1670_wr_x1647_en = List[Bool](true.B)
      val x1670_wr_x1647_data = List[UInt](x1658_sum.r)
      x1647_reg.connectWPort(1670, x1670_wr_x1647_banks, x1670_wr_x1647_ofs, x1670_wr_x1647_data, x1670_wr_x1647_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(9.4.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
      val x1671_wr_x1648_banks = List[UInt]()
      val x1671_wr_x1648_ofs = List[UInt]()
      val x1671_wr_x1648_en = List[Bool](true.B)
      val x1671_wr_x1648_data = List[UInt](x1661.r)
      x1648_reg.connectWPort(1671, x1671_wr_x1648_banks, x1671_wr_x1648_ofs, x1671_wr_x1648_data, x1671_wr_x1648_en.map(_ && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(10.8.toInt, rr, io.sigsIn.backpressure & true.B) & ~io.sigsIn.break && io.sigsIn.backpressure && true.B))
    }
    val module = Module(new x1672_inr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x1672_inr_UnitPipe **/
