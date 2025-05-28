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
    val x329 = accelUnit.io.memStreams.loads(0).cmd // StreamOut
    ModuleParams.addParams("x329_p", (x329.bits.addrWidth, x329.bits.sizeWidth))  
    val x331 = accelUnit.io.memStreams.loads(0).data // StreamIn
    ModuleParams.addParams("x331_p", (x331.bits.v, x331.bits.w))  
    // scoped in dram is  
    val x321_realDRAM = Wire(new FixedPoint(true, 64, 0))
    x321_realDRAM.r := accelUnit.io.argIns(api.REALDRAM_ptr)
    val x364 = accelUnit.io.memStreams.loads(1).cmd // StreamOut
    ModuleParams.addParams("x364_p", (x364.bits.addrWidth, x364.bits.sizeWidth))  
    val x366 = accelUnit.io.memStreams.loads(1).data // StreamIn
    ModuleParams.addParams("x366_p", (x366.bits.v, x366.bits.w))  
    // scoped in dram is  
    val x322_imagDRAM = Wire(new FixedPoint(true, 64, 0))
    x322_imagDRAM.r := accelUnit.io.argIns(api.IMAGDRAM_ptr)
    val x412 = accelUnit.io.memStreams.stores(0).cmd // StreamOut
    ModuleParams.addParams("x412_p", (x412.bits.addrWidth, x412.bits.sizeWidth))  
    val x413 = accelUnit.io.memStreams.stores(0).data // StreamOut
    ModuleParams.addParams("x413_p", (x413.bits.v, x413.bits.w))  
    val x414  = accelUnit.io.memStreams.stores(0).wresp // StreamIn
    // scoped in dram is  
    val x323_outDRAM = Wire(new FixedPoint(true, 64, 0))
    x323_outDRAM.r := accelUnit.io.argIns(api.OUTDRAM_ptr)
    val retime_counter = Module(new SingleCounter(1, Some(0), Some(accelUnit.max_latency), Some(1), false)); retime_counter.io <> DontCare // Counter for masking out the noise that comes out of ShiftRegister in the first few cycles of the app
    retime_counter.io.setup.saturate := true.B; retime_counter.io.input.reset := accelUnit.reset.toBool; retime_counter.io.input.enable := true.B;
    val rr = getRetimed(retime_counter.io.output.done, 1, true.B) // break up critical path by delaying this 
    val breakpoints = Wire(Vec(accelUnit.io_numArgOuts_breakpts max 1, Bool())); breakpoints.zipWithIndex.foreach{case(b,i) => b.suggestName(s"breakpoint" + i)}; breakpoints := DontCare
    val instrctrs = List.fill[InstrCtr](api.numCtrls)(Wire(new InstrCtr())); instrctrs.foreach(_ := DontCare)
    val done_latch = Module(new SRFF())
    val RootController = new RootController_kernel(List(x321_realDRAM,x323_outDRAM,x322_imagDRAM), List(x329,x412,x364), List(x414), List(x366,x331), List(x413) ,  None, List(), -1, 3, 1, List(1), List(32), breakpoints, instrctrs.toList, rr)
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
