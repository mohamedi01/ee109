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

/** Hierarchy: x658 -> x663 -> x664 -> x709 -> x710 **/
/** BEGIN None x658_outr_UnitPipe **/
class x658_outr_UnitPipe_kernel(
  list_b615: List[Bool],
  list_x470_imag2D_0: List[StandardInterface],
  list_x609: List[DecoupledIO[AppCommandDense]],
  list_x610: List[DecoupledIO[AppStoreData]],
  list_b614: List[FixedPoint],
   parent: Option[Kernel], cchain: List[CounterChainInterface], childId: Int, nMyChildren: Int, ctrcopies: Int, ctrPars: List[Int], ctrWidths: List[Int], breakpoints: Vec[Bool], instrctrs: List[InstrCtr], rr: Bool
) extends Kernel(parent, cchain, childId, nMyChildren, ctrcopies, ctrPars, ctrWidths) {
  
  val me = this
  val sm = Module(new OuterControl(Sequenced, 2, isFSM = false   , latency = 0.0.toInt, myName = "x658_outr_UnitPipe_sm")); sm.io <> DontCare
  val iiCtr = Module(new IICounter(1.0.toInt, 2 + _root_.utils.math.log2Up(1.0.toInt), "x658_outr_UnitPipe_iiCtr"))
  
  abstract class x658_outr_UnitPipe_module(depth: Int)(implicit stack: List[KernelHash]) extends Module {
    val io = IO(new Bundle {
      val in_b614 = Input(new FixedPoint(true, 32, 0))
      val in_b615 = Input(Bool())
      val in_x610 = Decoupled(new AppStoreData(ModuleParams.getParams("x610_p").asInstanceOf[(Int,Int)] ))
      val in_x609 = Decoupled(new AppCommandDense(ModuleParams.getParams("x609_p").asInstanceOf[(Int,Int)] ))
      val in_x462_imagDRAM = Input(new FixedPoint(true, 64, 0))
      val in_x470_imag2D_0 = Flipped(new StandardInterface(ModuleParams.getParams("x470_imag2D_0_p").asInstanceOf[MemParams] ))
      val in_instrctrs = Vec(api.numCtrls, Output(new InstrCtr()))
      val in_breakpoints = Vec(api.numArgOuts_breakpts, Output(Bool()))
      val sigsIn = Input(new InputKernelSignals(2, 1, List(1), List(32)))
      val sigsOut = Output(new OutputKernelSignals(2, 1))
      val rr = Input(Bool())
    })
    def b614 = {io.in_b614} 
    def b615 = {io.in_b615} 
    def x610 = {io.in_x610} 
    def x609 = {io.in_x609} 
    def x462_imagDRAM = {io.in_x462_imagDRAM} 
    def x470_imag2D_0 = {io.in_x470_imag2D_0} ; io.in_x470_imag2D_0 := DontCare
  }
  def connectWires0(module: x658_outr_UnitPipe_module)(implicit stack: List[KernelHash]): Unit = {
    module.io.in_b614 <> b614
    module.io.in_b615 <> b615
    module.io.in_x610 <> x610
    module.io.in_x609 <> x609
    module.io.in_x462_imagDRAM <> x462_imagDRAM
    x470_imag2D_0.connectLedger(module.io.in_x470_imag2D_0)
  }
  val b615 = list_b615(0)
  val x470_imag2D_0 = list_x470_imag2D_0(0)
  val x609 = list_x609(0)
  val x610 = list_x610(0)
  val b614 = list_b614(0)
  val x462_imagDRAM = list_b614(1)
  def kernel(): Unit = {
    Ledger.enter(this.hashCode, "x658_outr_UnitPipe")
    implicit val stack = ControllerStack.stack.toList
    class x658_outr_UnitPipe_concrete(depth: Int)(implicit stack: List[KernelHash]) extends x658_outr_UnitPipe_module(depth) {
      io.sigsOut := DontCare
      val breakpoints = io.in_breakpoints; breakpoints := DontCare
      val instrctrs = io.in_instrctrs; instrctrs := DontCare
      val rr = io.rr
      val cycles_x658_outr_UnitPipe = Module(new InstrumentationCounter())
      val iters_x658_outr_UnitPipe = Module(new InstrumentationCounter())
      cycles_x658_outr_UnitPipe.io.enable := io.sigsIn.baseEn
      iters_x658_outr_UnitPipe.io.enable := risingEdge(io.sigsIn.done)
      Ledger.tieInstrCtr(instrctrs.toList, X658_instrctr, cycles_x658_outr_UnitPipe.io.count, iters_x658_outr_UnitPipe.io.count, 0.U, 0.U)
      val x616_reg = (new x616_reg).m.io.asInstanceOf[StandardInterface]
      val x617_reg = (new x617_reg).m.io.asInstanceOf[StandardInterface]
      val x618_reg = (new x618_reg).m.io.asInstanceOf[StandardInterface]
      val x638_inr_UnitPipe = new x638_inr_UnitPipe_kernel(List(b614,x462_imagDRAM), List(x609), List(x618_reg,x616_reg,x617_reg) ,  Some(me), List(), 0, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x638_inr_UnitPipe.sm.io.ctrDone := risingEdge(x638_inr_UnitPipe.sm.io.ctrInc)
      x638_inr_UnitPipe.backpressure := x609.ready | x638_inr_UnitPipe.sm.io.doneLatch
      x638_inr_UnitPipe.forwardpressure := (true.B) && (true.B) | x638_inr_UnitPipe.sm.io.doneLatch
      x638_inr_UnitPipe.sm.io.enableOut.zip(x638_inr_UnitPipe.smEnableOuts).foreach{case (l,r) => r := l}
      x638_inr_UnitPipe.sm.io.break := false.B
      x638_inr_UnitPipe.mask := true.B & true.B
      x638_inr_UnitPipe.configure("x638_inr_UnitPipe", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x638_inr_UnitPipe.kernel()
      val x703_rd_x618 = Wire(new FixedPoint(true, 32, 0)).suggestName("""x703_rd_x618""")
      val x703_rd_x618_banks = List[UInt]()
      val x703_rd_x618_ofs = List[UInt]()
      val x703_rd_x618_en = List[Bool](true.B)
      val x703_rd_x618_shared_en = ((io.sigsIn.forwardpressure).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && ~io.sigsIn.break && (io.sigsIn.datapathEn & io.sigsIn.iiIssue).DS(0.0.toInt, rr, io.sigsIn.backpressure & true.B) && true.B ).suggestName("x703_rd_x618_shared_en")
      x703_rd_x618.toSeq.zip(x618_reg.connectRPort(703, x703_rd_x618_banks, x703_rd_x618_ofs, io.sigsIn.backpressure, x703_rd_x618_en.map(_ && x703_rd_x618_shared_en), true)).foreach{case (a,b) => a.r := b.r}
      val x640_ctr = new CtrObject(Left(Some(0)), Right(x703_rd_x618), Left(Some(1)), 1, 32, false)
      val x641_ctrchain = (new CChainObject(List[CtrObject](x640_ctr), "x641_ctrchain")).cchain.io 
      x641_ctrchain.setup.isStream := false.B
      ModuleParams.addParams("x641_ctrchain_p", (x641_ctrchain.par, x641_ctrchain.widths))
      val x657_inr_Foreach = new x657_inr_Foreach_kernel(List(b614), List(x616_reg,x617_reg,x470_imag2D_0), List(x610) ,  Some(me), List(x641_ctrchain), 1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
      x657_inr_Foreach.sm.io.ctrDone := (x657_inr_Foreach.cchain.head.output.done).DS(1.toInt, rr, io.sigsIn.backpressure & true.B)
      x657_inr_Foreach.backpressure := x610.ready | x657_inr_Foreach.sm.io.doneLatch
      x657_inr_Foreach.forwardpressure := (true.B) && (true.B) | x657_inr_Foreach.sm.io.doneLatch
      x657_inr_Foreach.sm.io.enableOut.zip(x657_inr_Foreach.smEnableOuts).foreach{case (l,r) => r := l}
      x657_inr_Foreach.sm.io.break := false.B
      x657_inr_Foreach.mask := ~x657_inr_Foreach.cchain.head.output.noop & true.B
      x657_inr_Foreach.configure("x657_inr_Foreach", Some(io.sigsIn), Some(io.sigsOut), isSwitchCase = false)
      x657_inr_Foreach.kernel()
    }
    val module = Module(new x658_outr_UnitPipe_concrete(sm.p.depth)); module.io := DontCare
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
/** END UnitPipe x658_outr_UnitPipe **/
