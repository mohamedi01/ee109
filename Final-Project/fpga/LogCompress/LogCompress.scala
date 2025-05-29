import spatial.dsl._
import scala.io.Source

@spatial object LogCompressCORDIC {
  def main(args: Array[String]): Unit = {
    // ─── Kernel arguments ───────────────────────────────────────────────────────
    val n        = args(0).to[I32]
    val dynRange = args(1).to[Float]

    // ─── CORDIC table size ──────────────────────────────────────────────────────
    val N = 16

    // ─── Host-side streaming load of CORDIC tables from CSV ─────────────────────
    // Each CSV has one float per line
    val expLines = Source.fromFile("logcompress_consts.csv").getLines().toArray
    val expTable: Array[Float]    = expLines.map(_.toFloat.to[Float])
    val shiftLines = Source.fromFile("logcompress_shifts.csv").getLines().toArray
    val twoNegTable: Array[Float] = shiftLines.map(_.toFloat.to[Float])

    // ─── Off-chip DRAMs ─────────────────────────────────────────────────────────
    val inDram     = DRAM[Float](n)
    val outDram    = DRAM[Float](n)
    val constDram  = DRAM[Float](N)
    val twoNegDram = DRAM[Float](N)

    // ─── Initialize DRAMs with host-loaded tables ───────────────────────────────
    setMem(constDram,  expTable)
    setMem(twoNegDram, twoNegTable)

    // ─── Accelerator ────────────────────────────────────────────────────────────
    Accel {
      // On-chip SRAMs
      val buf        = SRAM[Float](n)
      val Ktable     = SRAM[Float](N)
      val twoNegSram = SRAM[Float](N)
      buf        load inDram
      Ktable     load constDram
      twoNegSram load twoNegDram

      // 1) Explicit-accumulator reduction to find max
      val maxReg = Reg[Float](0.to[Float])
      Reduce(maxReg)(0 until n) { i => buf(i) } { (a, b) => mux(a > b, a, b) }
      val mx = maxReg.value

      // 2) Clamp dynamic range
      Foreach(n by 1) { i => buf(i) = max(buf(i), mx - dynRange) }

      // 3) Prepare output SRAM
      val outSram = SRAM[Float](n)

      // 4) CORDIC ln + convert to log10
      Foreach(n by 1) { i =>
        val v0 = buf(i)
        val xi = Reg[Float](v0)
        val yi = Reg[Float](0.to[Float])
        Foreach(N by 1) { j =>
          val kj       = Ktable(j)
          val shiftVal = twoNegSram(j)
          val gt       = xi.value > kj
          xi := mux(gt, xi.value / kj, xi.value)
          yi := mux(gt, yi.value + shiftVal, yi.value)
        }
        outSram(i) = yi.value * 0.4342944819f  // 1/ln(10)
      }

      // 5) Write results back
      outDram store outSram
    }
  }
}


// import spatial.dsl._

// //=== LogCompress.scala ===
// @spatial object LogCompress {
//   def main(args: Array[String]): Unit = {
//     val n = args(0).to[I32]
//     val dynRange = args(1).to[Float]  // e.g., 8.0f
//     val inDram = DRAM[Float](n)
//     val outDram = DRAM[Float](n)
//     Accel {
//       val buf = SRAM[Float](n)
//       buf load inDram
//       // Compute max
//       val mx = Reduce(Max)(buf)(i => buf(i))
//       Foreach(n by 1) { i =>
//         buf(i) = max(buf(i), mx - dynRange)
//       }
//       Foreach(n by 1) { i =>
//         outDram(i) = log10(max(buf(i), 1e-10.to[Float]))
//       }
//     }
//   }
// }
