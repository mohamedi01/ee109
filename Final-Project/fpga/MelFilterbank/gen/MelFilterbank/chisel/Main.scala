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
    val x297 = accelUnit.io.memStreams.loads(0).cmd // StreamOut
    ModuleParams.addParams("x297_p", (x297.bits.addrWidth, x297.bits.sizeWidth))  
    val x299 = accelUnit.io.memStreams.loads(0).data // StreamIn
    ModuleParams.addParams("x299_p", (x299.bits.v, x299.bits.w))  
    // scoped in dram is  
    val x291_matDram = Wire(new FixedPoint(true, 64, 0))
    x291_matDram.r := accelUnit.io.argIns(api.MATDRAM_ptr)
    val x360 = accelUnit.io.memStreams.loads(1).cmd // StreamOut
    ModuleParams.addParams("x360_p", (x360.bits.addrWidth, x360.bits.sizeWidth))  
    val x362 = accelUnit.io.memStreams.loads(1).data // StreamIn
    ModuleParams.addParams("x362_p", (x362.bits.v, x362.bits.w))  
    // scoped in dram is  
    val x292_vecDram = Wire(new FixedPoint(true, 64, 0))
    x292_vecDram.r := accelUnit.io.argIns(api.VECDRAM_ptr)
    val x420 = accelUnit.io.memStreams.stores(0).cmd // StreamOut
    ModuleParams.addParams("x420_p", (x420.bits.addrWidth, x420.bits.sizeWidth))  
    val x421 = accelUnit.io.memStreams.stores(0).data // StreamOut
    ModuleParams.addParams("x421_p", (x421.bits.v, x421.bits.w))  
    val x422  = accelUnit.io.memStreams.stores(0).wresp // StreamIn
    // scoped in dram is  
    val x293_outDram = Wire(new FixedPoint(true, 64, 0))
    x293_outDram.r := accelUnit.io.argIns(api.OUTDRAM_ptr)
    val retime_counter = Module(new SingleCounter(1, Some(0), Some(accelUnit.max_latency), Some(1), false)); retime_counter.io <> DontCare // Counter for masking out the noise that comes out of ShiftRegister in the first few cycles of the app
    retime_counter.io.setup.saturate := true.B; retime_counter.io.input.reset := accelUnit.reset.toBool; retime_counter.io.input.enable := true.B;
    val rr = getRetimed(retime_counter.io.output.done, 1, true.B) // break up critical path by delaying this 
    val breakpoints = Wire(Vec(accelUnit.io_numArgOuts_breakpts max 1, Bool())); breakpoints.zipWithIndex.foreach{case(b,i) => b.suggestName(s"breakpoint" + i)}; breakpoints := DontCare
    val done_latch = Module(new SRFF())
    val RootController = new RootController_kernel(List(x362,x299), List(x297,x420,x360), List(x422), List(x293_outDram,x292_vecDram,x291_matDram), List(x421) ,  None, List(), -1, 3, 1, List(1), List(32), breakpoints, rr)
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
