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

object Main {
  def main(accelUnit: AccelUnit): Unit = {
    accelUnit.io <> DontCare
    val x878_M = accelUnit.io.argIns(api.M_arg)
    val x879_N = accelUnit.io.argIns(api.N_arg)
    val x880_K = accelUnit.io.argIns(api.K_arg)
    val x935 = accelUnit.io.memStreams.loads(0).cmd // StreamOut
    ModuleParams.addParams("x935_p", (x935.bits.addrWidth, x935.bits.sizeWidth))  
    val x937 = accelUnit.io.memStreams.loads(0).data // StreamIn
    ModuleParams.addParams("x937_p", (x937.bits.v, x937.bits.w))  
    // scoped in dram is  
    val x900_a = Wire(new FixedPoint(true, 64, 0))
    x900_a.r := accelUnit.io.argIns(api.A_ptr)
    val x1020 = accelUnit.io.memStreams.loads(1).cmd // StreamOut
    ModuleParams.addParams("x1020_p", (x1020.bits.addrWidth, x1020.bits.sizeWidth))  
    val x1022 = accelUnit.io.memStreams.loads(1).data // StreamIn
    ModuleParams.addParams("x1022_p", (x1022.bits.v, x1022.bits.w))  
    // scoped in dram is  
    val x903_b = Wire(new FixedPoint(true, 64, 0))
    x903_b.r := accelUnit.io.argIns(api.B_ptr)
    val x1088 = accelUnit.io.memStreams.loads(2).cmd // StreamOut
    ModuleParams.addParams("x1088_p", (x1088.bits.addrWidth, x1088.bits.sizeWidth))  
    val x1090 = accelUnit.io.memStreams.loads(2).data // StreamIn
    ModuleParams.addParams("x1090_p", (x1090.bits.v, x1090.bits.w))  
    val x1639 = accelUnit.io.memStreams.stores(0).cmd // StreamOut
    ModuleParams.addParams("x1639_p", (x1639.bits.addrWidth, x1639.bits.sizeWidth))  
    val x1640 = accelUnit.io.memStreams.stores(0).data // StreamOut
    ModuleParams.addParams("x1640_p", (x1640.bits.v, x1640.bits.w))  
    val x1641  = accelUnit.io.memStreams.stores(0).wresp // StreamIn
    // scoped in dram is  
    val x906_c = Wire(new FixedPoint(true, 64, 0))
    x906_c.r := accelUnit.io.argIns(api.C_ptr)
    val retime_counter = Module(new SingleCounter(1, Some(0), Some(accelUnit.max_latency), Some(1), false)); retime_counter.io <> DontCare // Counter for masking out the noise that comes out of ShiftRegister in the first few cycles of the app
    retime_counter.io.setup.saturate := true.B; retime_counter.io.input.reset := accelUnit.reset.toBool; retime_counter.io.input.enable := true.B;
    val rr = getRetimed(retime_counter.io.output.done, 1, true.B) // break up critical path by delaying this 
    val breakpoints = Wire(Vec(accelUnit.io_numArgOuts_breakpts max 1, Bool())); breakpoints.zipWithIndex.foreach{case(b,i) => b.suggestName(s"breakpoint" + i)}; breakpoints := DontCare
    val instrctrs = List.fill[InstrCtr](api.numCtrls)(Wire(new InstrCtr())); instrctrs.foreach(_ := DontCare)
    val done_latch = Module(new SRFF())
    val RootController = new RootController_kernel(List(x878_M,x879_N,x880_K), List(x1641), List(x937,x1090,x1022), List(x1640), List(x1020,x1639,x935,x1088), List(x906_c,x900_a,x903_b) ,  None, List(), -1, 1, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
    RootController.baseEn := accelUnit.io.enable && rr && ~done_latch.io.output
    RootController.resetMe := getRetimed(accelUnit.accelReset, 1)
    RootController.mask := true.B
    RootController.sm.io.parentAck := accelUnit.io.done
    RootController.sm.io.enable := RootController.baseEn & !accelUnit.io.done & (true.B) && (true.B)
    done_latch.io.input.reset := RootController.resetMe
    done_latch.io.input.asyn_reset := RootController.resetMe
    accelUnit.io.done := done_latch.io.output
    RootController.sm.io.ctrDone := risingEdge(RootController.sm.io.ctrInc)
    RootController.backpressure := true.B | RootController.sm.io.doneLatch
    RootController.forwardpressure := (true.B) && (true.B) | RootController.sm.io.doneLatch
    RootController.sm.io.enableOut.zip(RootController.smEnableOuts).foreach{case (l,r) => r := l}
    RootController.sm.io.break := false.B
    RootController.mask := true.B & true.B
    RootController.configure("RootController", None, None, isSwitchCase = false)
    RootController.kernel()
    done_latch.io.input.set := RootController.done
    Instrument.connect(accelUnit, instrctrs)
    Ledger.finish()
  }
}
