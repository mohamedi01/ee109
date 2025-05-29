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
    val x326_argIn = accelUnit.io.argIns(api.X326_ARGIN_arg)
    val x344 = accelUnit.io.memStreams.loads(0).cmd // StreamOut
    ModuleParams.addParams("x344_p", (x344.bits.addrWidth, x344.bits.sizeWidth))  
    val x346 = accelUnit.io.memStreams.loads(0).data // StreamIn
    ModuleParams.addParams("x346_p", (x346.bits.v, x346.bits.w))  
    // scoped in dram is  
    val x329_inDram = Wire(new FixedPoint(true, 64, 0))
    x329_inDram.r := accelUnit.io.argIns(api.INDRAM_ptr)
    val x445 = accelUnit.io.memStreams.stores(0).cmd // StreamOut
    ModuleParams.addParams("x445_p", (x445.bits.addrWidth, x445.bits.sizeWidth))  
    val x446 = accelUnit.io.memStreams.stores(0).data // StreamOut
    ModuleParams.addParams("x446_p", (x446.bits.v, x446.bits.w))  
    val x447  = accelUnit.io.memStreams.stores(0).wresp // StreamIn
    // scoped in dram is  
    val x330_outDram = Wire(new FixedPoint(true, 64, 0))
    x330_outDram.r := accelUnit.io.argIns(api.OUTDRAM_ptr)
    val x384 = accelUnit.io.memStreams.loads(1).cmd // StreamOut
    ModuleParams.addParams("x384_p", (x384.bits.addrWidth, x384.bits.sizeWidth))  
    val x385 = accelUnit.io.memStreams.loads(1).data // StreamIn
    ModuleParams.addParams("x385_p", (x385.bits.v, x385.bits.w))  
    // scoped in dram is  
    val x331_constDram = Wire(new FixedPoint(true, 64, 0))
    x331_constDram.r := accelUnit.io.argIns(api.CONSTDRAM_ptr)
    val x401 = accelUnit.io.memStreams.loads(2).cmd // StreamOut
    ModuleParams.addParams("x401_p", (x401.bits.addrWidth, x401.bits.sizeWidth))  
    val x402 = accelUnit.io.memStreams.loads(2).data // StreamIn
    ModuleParams.addParams("x402_p", (x402.bits.v, x402.bits.w))  
    // scoped in dram is  
    val x332_twoNegDram = Wire(new FixedPoint(true, 64, 0))
    x332_twoNegDram.r := accelUnit.io.argIns(api.TWONEGDRAM_ptr)
    val x335_Accel_n = accelUnit.io.argIns(api.ACCEL_N_arg)
    val x337_argIn = accelUnit.io.argIns(api.X337_ARGIN_arg)
    val retime_counter = Module(new SingleCounter(1, Some(0), Some(accelUnit.max_latency), Some(1), false)); retime_counter.io <> DontCare // Counter for masking out the noise that comes out of ShiftRegister in the first few cycles of the app
    retime_counter.io.setup.saturate := true.B; retime_counter.io.input.reset := accelUnit.reset.toBool; retime_counter.io.input.enable := true.B;
    val rr = getRetimed(retime_counter.io.output.done, 1, true.B) // break up critical path by delaying this 
    val breakpoints = Wire(Vec(accelUnit.io_numArgOuts_breakpts max 1, Bool())); breakpoints.zipWithIndex.foreach{case(b,i) => b.suggestName(s"breakpoint" + i)}; breakpoints := DontCare
    val done_latch = Module(new SRFF())
    val RootController = new RootController_kernel(List(x446), List(x329_inDram,x330_outDram,x331_constDram,x332_twoNegDram), List(x447), List(x335_Accel_n,x326_argIn,x337_argIn), List(x384,x344,x445,x401), List(x385,x402,x346) ,  None, List(), -1, 3, 1, List(1), List(32), breakpoints, rr)
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
    Ledger.finish()
  }
}
