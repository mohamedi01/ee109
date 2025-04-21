package accel
import chisel3._
import chisel3.util._
import fringe._
import utils.implicits._
import fringe.templates.math._
import fringe.templates.counters._
import fringe.templates.vector._
import fringe.templates.memory._
import fringe.templates.axi4._
import fringe.templates.retiming._
trait AccelWrapper extends Module {
  val io_w = if ("VCS" == "VCS" || "VCS" == "ASIC") 8 else 32 // TODO: How to generate these properly?
  val io_v = if ("VCS" == "VCS" || "VCS" == "ASIC") 64 else 16 // TODO: How to generate these properly?
  // Non-memory Streams
  val io_axiStreamInsInfo = List()
  val io_axiStreamOutsInfo = List()
  // Scalars
  val io_numArgIns_reg = 0
  val io_numArgOuts_reg = 0
  val io_numArgIOs_reg = 0
  val io_argOutLoopbacksMap: scala.collection.immutable.Map[Int,Int] = Map()
  // Memory Streams
  val io_loadStreamInfo = List() 
  val io_storeStreamInfo = List(StreamParInfo(32, 1, 0)) 
  val io_gatherStreamInfo = List() 
  val io_scatterStreamInfo = List() 
  val io_numArgIns_mem = 1
  val outArgMuxMap: scala.collection.mutable.Map[Int, Int] = scala.collection.mutable.Map[Int,Int]()
  // Heap
  val io_numAllocators = scala.math.max(1, 0)
  // Root controller for app: Lab2Part1SimpleMemReduce
  
  // Widths: 2, 2, 3
  //   Widest Outer Controller: 3
  // Depths: 3, 3, 3, 3, 3
  //   Deepest Inner Controller: 3
  // App Characteristics: HasTileStore,HasUnalignedStore
  // Instrumentation
  val io_numArgOuts_instr = 22
  val io_numArgCtrls = 8
  val io_numArgOuts_breakpts = 0
  // Set Build Info
  val max_latency = 3
  globals.target.fixmul_latency = 0.1875
  globals.target.fixdiv_latency = 0.625
  globals.target.fixadd_latency = 0.03125
  globals.target.fixsub_latency = 0.03125
  globals.target.fixmod_latency = 0.5
  globals.target.fixeql_latency = 0.2.toInt
  globals.perpetual = false
  globals.channelAssignment = AllToOne
  globals.target.mux_latency    = 0.5.toInt
  globals.target.sramload_latency    = 2.0.toInt
  globals.target.cheapSRAMs    = false
  globals.target.sramstore_latency    = 1.0.toInt
  globals.target.SramThreshold = 1
  globals.retime = true
  globals.enableModular = true
  // Combine values
  val io_numArgIns = scala.math.max(1, io_numArgIns_reg + io_numArgIns_mem + io_numArgIOs_reg)
  val io_numArgOuts = scala.math.max(1, io_numArgOuts_reg + io_numArgIOs_reg + io_numArgOuts_instr + io_numArgOuts_breakpts)
  val io_numArgIOs = io_numArgIOs_reg
  val io_numArgInstrs = io_numArgOuts_instr
  val io_numArgBreakpts = io_numArgOuts_breakpts
  globals.numArgIns = io_numArgIns
  globals.numArgOuts = io_numArgOuts
  globals.numArgIOs = io_numArgIOs
  globals.numArgInstrs = io_numArgInstrs
  globals.loadStreamInfo = io_loadStreamInfo
  globals.storeStreamInfo = io_storeStreamInfo
  globals.gatherStreamInfo = io_gatherStreamInfo
  globals.scatterStreamInfo = io_scatterStreamInfo
  globals.axiStreamInsInfo = io_axiStreamInsInfo
  globals.axiStreamOutsInfo = io_axiStreamOutsInfo
  globals.numAllocators = io_numAllocators
  val io = chisel3.core.dontTouch(
    IO(new CustomAccelInterface(io_w, io_v, globals.LOAD_STREAMS, globals.STORE_STREAMS, globals.GATHER_STREAMS, globals.SCATTER_STREAMS, globals.AXI_STREAMS_IN, globals.AXI_STREAMS_OUT, globals.numAllocators, io_numArgIns, io_numArgOuts))
  )
  var outStreamMuxMap: scala.collection.mutable.Map[String, Int] = scala.collection.mutable.Map[String,Int]()
  def getStreamOutLane(id: String): Int = {
    val lane = outStreamMuxMap.getOrElse(id, 0)
    outStreamMuxMap += (id -> {lane + 1})
    lane
  }
  var outBuffMuxMap: scala.collection.mutable.Map[String, Int] = scala.collection.mutable.Map[String,Int]()
  def getBuffOutLane(id: String): Int = {
    val lane = outBuffMuxMap.getOrElse(id, 0)
    outBuffMuxMap += (id -> {lane + 1})
    lane
  }
  var inStreamMuxMap: scala.collection.mutable.Map[String, Int] = scala.collection.mutable.Map[String,Int]()
  def getStreamInLane(id: String): Int = {
    val lane = inStreamMuxMap.getOrElse(id, 0)
    inStreamMuxMap += (id -> {lane + 1})
    lane
  }
}
