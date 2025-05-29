package spatialIP

import accel._
import fringe._
import chisel3.core.Module
import chisel3._
import chisel3.util._
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}
import fringe.templates.axi4._

import scala.collection.mutable.ListBuffer
/**
 * SpatialIP test harness
 */
class SpatialIPUnitTester(c: SpatialIP)(implicit args: Array[String]) extends ArgsTester(c) {
}

object Instantiator extends CommonMain {
  type DUTType = SpatialIP
  
  def dut = () => {
    // Non-memory Streams
    val axiStreamInsInfo = List()
    val axiStreamOutsInfo = List()
    
    // Scalars
    val numArgIns_reg = 1
    val numArgOuts_reg = 0
    val numArgIOs_reg = 0
    //x149_Accel_n = argIns(0) ( Accel_n )
    val io_argOutLoopbacksMap: scala.collection.immutable.Map[Int,Int] = Map()
    
    // Memory streams
    val loadStreamInfo = List(StreamParInfo(32, 1, 0)) 
    val storeStreamInfo = List(StreamParInfo(32, 1, 0)) 
    val gatherStreamInfo = List() 
    val scatterStreamInfo = List() 
    val numArgIns_mem = 2
    // Heap
    val numAllocators = 0
    
    // Instrumentation
    val numArgOuts_instr = 0
    val numCtrls = 9
    val numArgOuts_breakpts = 0
    /* Breakpoint Contexts:
    */
    val w = if (this.target == "zcu") 32 else if (this.target == "VCS" || this.target == "ASIC") 8 else 32
    val numArgIns = numArgIns_mem  + numArgIns_reg + numArgIOs_reg
    val numArgOuts = numArgOuts_reg + numArgIOs_reg + numArgOuts_instr + numArgOuts_breakpts
    val numArgIOs = numArgIOs_reg
    val numArgInstrs = numArgOuts_instr
    val numArgBreakpts = numArgOuts_breakpts
    new SpatialIP(this.target, () => Module(new AccelUnit(w, numArgIns, numArgOuts, numArgIOs, numArgOuts_instr + numArgBreakpts, numAllocators, loadStreamInfo, storeStreamInfo, gatherStreamInfo, scatterStreamInfo, axiStreamInsInfo, axiStreamOutsInfo)))
  }
  def tester = { c: DUTType => new SpatialIPUnitTester(c) }
}
