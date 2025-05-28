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
