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
    val x414 = accelUnit.io.memStreams.loads(0).cmd // StreamOut
    ModuleParams.addParams("x414_p", (x414.bits.addrWidth, x414.bits.sizeWidth))  
    val x416 = accelUnit.io.memStreams.loads(0).data // StreamIn
    ModuleParams.addParams("x416_p", (x416.bits.v, x416.bits.w))  
    // scoped in dram is  
    val x406_matDRAM = Wire(new FixedPoint(true, 64, 0))
    x406_matDRAM.r := accelUnit.io.argIns(api.MATDRAM_ptr)
    val x477 = accelUnit.io.memStreams.loads(1).cmd // StreamOut
    ModuleParams.addParams("x477_p", (x477.bits.addrWidth, x477.bits.sizeWidth))  
    val x479 = accelUnit.io.memStreams.loads(1).data // StreamIn
    ModuleParams.addParams("x479_p", (x479.bits.v, x479.bits.w))  
    // scoped in dram is  
    val x407_vecDRAM = Wire(new FixedPoint(true, 64, 0))
    x407_vecDRAM.r := accelUnit.io.argIns(api.VECDRAM_ptr)
    val x542 = accelUnit.io.memStreams.stores(0).cmd // StreamOut
    ModuleParams.addParams("x542_p", (x542.bits.addrWidth, x542.bits.sizeWidth))  
    val x543 = accelUnit.io.memStreams.stores(0).data // StreamOut
    ModuleParams.addParams("x543_p", (x543.bits.v, x543.bits.w))  
    val x544  = accelUnit.io.memStreams.stores(0).wresp // StreamIn
    // scoped in dram is  
    val x408_outDRAM = Wire(new FixedPoint(true, 64, 0))
    x408_outDRAM.r := accelUnit.io.argIns(api.OUTDRAM_ptr)
    val retime_counter = Module(new SingleCounter(1, Some(0), Some(accelUnit.max_latency), Some(1), false)); retime_counter.io <> DontCare // Counter for masking out the noise that comes out of ShiftRegister in the first few cycles of the app
    retime_counter.io.setup.saturate := true.B; retime_counter.io.input.reset := accelUnit.reset.toBool; retime_counter.io.input.enable := true.B;
    val rr = getRetimed(retime_counter.io.output.done, 1, true.B) // break up critical path by delaying this 
    val breakpoints = Wire(Vec(accelUnit.io_numArgOuts_breakpts max 1, Bool())); breakpoints.zipWithIndex.foreach{case(b,i) => b.suggestName(s"breakpoint" + i)}; breakpoints := DontCare
    val instrctrs = List.fill[InstrCtr](api.numCtrls)(Wire(new InstrCtr())); instrctrs.foreach(_ := DontCare)
    val done_latch = Module(new SRFF())
    val RootController = new RootController_kernel(List(x408_outDRAM,x407_vecDRAM,x406_matDRAM), List(x544), List(x542,x477,x414), List(x543), List(x479,x416) ,  None, List(), -1, 3, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
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
