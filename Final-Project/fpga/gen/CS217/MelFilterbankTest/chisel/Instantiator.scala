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
