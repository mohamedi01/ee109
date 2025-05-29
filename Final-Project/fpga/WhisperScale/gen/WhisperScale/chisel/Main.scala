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
    val x153 = accelUnit.io.memStreams.loads(0).cmd // StreamOut
    ModuleParams.addParams("x153_p", (x153.bits.addrWidth, x153.bits.sizeWidth))  
    val x154 = accelUnit.io.memStreams.loads(0).data // StreamIn
    ModuleParams.addParams("x154_p", (x154.bits.v, x154.bits.w))  
    // scoped in dram is  
    val x147_inDram = Wire(new FixedPoint(true, 64, 0))
    x147_inDram.r := accelUnit.io.argIns(api.INDRAM_ptr)
    val x180 = accelUnit.io.memStreams.stores(0).cmd // StreamOut
    ModuleParams.addParams("x180_p", (x180.bits.addrWidth, x180.bits.sizeWidth))  
    val x181 = accelUnit.io.memStreams.stores(0).data // StreamOut
    ModuleParams.addParams("x181_p", (x181.bits.v, x181.bits.w))  
    val x182  = accelUnit.io.memStreams.stores(0).wresp // StreamIn
    // scoped in dram is  
    val x148_outDram = Wire(new FixedPoint(true, 64, 0))
    x148_outDram.r := accelUnit.io.argIns(api.OUTDRAM_ptr)
    val x149_Accel_n = accelUnit.io.argIns(api.ACCEL_N_arg)
    val retime_counter = Module(new SingleCounter(1, Some(0), Some(accelUnit.max_latency), Some(1), false)); retime_counter.io <> DontCare // Counter for masking out the noise that comes out of ShiftRegister in the first few cycles of the app
    retime_counter.io.setup.saturate := true.B; retime_counter.io.input.reset := accelUnit.reset.toBool; retime_counter.io.input.enable := true.B;
    val rr = getRetimed(retime_counter.io.output.done, 1, true.B) // break up critical path by delaying this 
    val breakpoints = Wire(Vec(accelUnit.io_numArgOuts_breakpts max 1, Bool())); breakpoints.zipWithIndex.foreach{case(b,i) => b.suggestName(s"breakpoint" + i)}; breakpoints := DontCare
    val done_latch = Module(new SRFF())
    val RootController = new RootController_kernel(List(x154), List(x147_inDram,x148_outDram), List(x181), List(x153,x180), List(x149_Accel_n), List(x182) ,  None, List(), -1, 3, 1, List(1), List(32), breakpoints, rr)
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
