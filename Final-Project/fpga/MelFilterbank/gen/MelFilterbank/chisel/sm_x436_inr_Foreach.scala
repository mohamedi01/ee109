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

/** Hierarchy: x436 -> x441 -> x453 **/
/** BEGIN None x436_inr_Foreach **/
class x436_inr_Foreach_kernel(
  list_x296_outSram_0: List[StandardInterface],
  list_x421: List[DecoupledIO[AppStoreData]],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new InnerControl(Pipelined, false   , latency = 2.0.toInt, myName = "x436_inr_Foreach_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x436_inr_Foreach_iiCtr"))
  
  abstract class x436_inr_Foreach_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_x421 = Decoupled(new AppStoreData(ModuleParams.getParams("x421_p").asInstanceOf[(Int,Int)] ))
      val in_x296_outSram_0 = Flipped(new StandardInterface(ModuleParams.getParams("x296_outSram_0_p").asInstanceOf[MemParams] ))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(1, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(1, 1))
      val rr = Input(Bool())
    })
    def x421 = {io.in_x421} 
    def x296_outSram_0 = {io.in_x296_outSram_0} ; io.in_x296_outSram_0 := DontCare
  }
  def connectWires0(module: x436_inr_Foreach_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_x421 <> x421
    x296_outSram_0.connectLedger(module.io.in_x296_outSram_0)
  }
  val x296_outSram_0 = list_x296_outSram_0(0)
  val x421 = list_x421(0)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x436_inr_Foreach")
    implicit val stack = ControllerStack.stack.toList
    class x436_inr_Foreach_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x436_inr_Foreach_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val rr = io.rr
      val b430 = io.sigsIn.cchainOutputs.head.counts(0).FP(true, 32, 0); b430.suggestName("b430")
      val b431 = ~io.sigsIn.cchainOutputs.head.oobs(0); b431.suggestName("b431")
      val x432_rd = Wire(Vec(1, new FloatingPoint(24, 8))).suggestName("""x432_rd""")
      val x432_rd_banks = List[UInt](0L.FP(true, 32, 0).r)
      val x432_rd_ofs = List[UInt](b430.r)
      val x432_rd_en = List[Bool](true.B)
      val x432_rd_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && b431 ).suggestName("x432_rd_shared_en")
      x432_rd.toSeq.zip(x296_outSram_0.connectRPort(432, x432_rd_banks, x432_rd_ofs, io.sigsIn.backpressure, x432_rd_en.map(_ && x432_rd_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      // x433 = VecApply(x432,0)
      val x433_elem_0 = Wire(new FloatingPoint(24, 8)).suggestName("""x433_elem_0""")
      x433_elem_0.r := x432_rd(0).r
      val x434_tuple = Wire(UInt(33.W)).suggestName("""x434_tuple""")
      x434_tuple.r := ConvAndCat(true.B,x433_elem_0.r)
      val x472 = Wire(Bool()).suggestName("x472_b431_D2") 
      x472.r := getRetimed(b431.r, 2.toInt, io.sigsIn.backpressure & true.B)
      x421.valid := (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(2.0.toInt.toInt, rr, io.sigsIn.backpressure & true.B) & x472 & io.sigsIn.backpressure
      x421.bits.wdata(0) := x434_tuple(31,0)
      x421.bits.wstrb := x434_tuple(32,32)
    }
    val module = Module(new x436_inr_Foreach_concrete(sm.p.depth)); module.io := DontCare
    // Connect ports on this kernel to its parent
    connectWires0(module)
    Ledger.connectBreakpoints(breakpoints, module.io.in_breakpoints)
    module.io.rr := rr
    module.io.sigsIn := me.sigsIn
    me.sigsOut := module.io.sigsOut
    Ledger.exit()
  }
}
/** END UnrolledForeach x436_inr_Foreach **/
