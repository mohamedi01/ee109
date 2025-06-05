import spatial.dsl._

@spatial object MelLogScaleKernel extends SpatialApp {
  // ── Constants ──────────────────────────────────────────────────
  val N_FFT    = 400
  val N_BINS   = N_FFT/2 + 1           // 201
  val N_MELS   = 80
  val EPS      = 1e-10f
  val INV_LN10 = 1.0f / 2.3025851f     // ln → log10

  def main(args: Array[String]): Unit = {
    // 1) Load CSVs on host
    val powerCSV = loadCSV2D[Float]("../../../../fpga_io/power_matrix.csv")   // 201 × T
    val melCSV   = loadCSV2D[Float]("../../../../fpga_io/mel_filterbank.csv") // 80 × 201
    val T        = powerCSV.cols.to[Int]

    // 2) Copy to device DRAMs
    val powerDRAM = DRAM[Float](N_BINS, T)
    val melDRAM   = DRAM[Float](N_MELS, N_BINS)
    setMem(powerDRAM, powerCSV)
    setMem(melDRAM,   melCSV)

    // 3) Output DRAM
    val logmel = DRAM[Float](N_MELS, T)

    // 4) Kernel
    Accel {
      Foreach(0 until T) { t =>
        // Bring one frame of the power spectrum on-chip
        val powerCol = SRAM[Float](N_BINS)
        powerCol load powerDRAM(0 :: N_BINS, t)

        val melFrame    = SRAM[Float](N_MELS)
        val logMelFrame = SRAM[Float](N_MELS)

        // For every Mel band
        Foreach(0 until N_MELS) { m =>
          // Bring this Mel filter row on-chip
          val melRow = SRAM[Float](N_BINS)
          melRow load melDRAM(m, 0 :: N_BINS)

          // Σ power × filter
          val acc = Reduce(Reg[Float](0f))(0 until N_BINS) { i =>
            powerCol(i) * melRow(i)
          }{ _ + _ }
          melFrame(m) = acc.value
        }

        // Frame-wise max (for 8 dB clamp window)
        val maxVal = Reduce(Reg[Float](0f))(0 until N_MELS) { i =>
          max(melFrame(i), EPS)
        }{ (a, b) => mux(a > b, a, b) }

        // log10 → clamp → Whisper scale
        Foreach(0 until N_MELS) { i =>
          val x       = max(melFrame(i), EPS)
          val log10x  = ln(x) * INV_LN10
          val clamped = max(log10x, maxVal - 8f)
          logMelFrame(i) = (clamped + 4f) / 4f
        }

        // Commit to DRAM
        logmel(0 :: N_MELS, t) store logMelFrame
      }
    } // Accel

    // 5) Dump CSV on host
    val mat     = getMatrix(logmel)
    val outPath = "../../../../fpga_io/whisperscale_output.csv"
    val outFile = new java.io.File(outPath); outFile.getParentFile.mkdirs()

    val pw = new java.io.PrintWriter(outFile)
    for (r <- 0 until N_MELS) {
      val sb = new StringBuilder
      for (c <- 0 until T) {
        if (c > 0) sb.append(',')
        sb.append(mat(r, c))
      }
      pw.println(sb.toString)
    }
    pw.close()
    println(s"[INFO] Wrote CSV: ${outFile.getAbsolutePath}")
  }
}
