import spatial.dsl._

@spatial object MelLogScaleKernel extends SpatialApp {

  /* --------- compile-time constants -------- */
  val N_FFT       = 400
  val N_BINS      = N_FFT/2 + 1           // 201
  val N_MELS      = 80
  val EPS         = 1e-10f
  val INV_LN10    = 1.0f / 2.3025851f
  val MAX_ELEMS   = 700000                // > 201×longest clip
  val OUT_MAX     = MAX_ELEMS/N_BINS * N_MELS
  val MAX_FRAMES  = 4000                  // > any clip’s frame count

  def main(args: Array[String]): Unit = {

    /* 1) runtime sizes from Python */
    val nElemsHost: Int = {
      val s = scala.io.Source.fromFile("kernel_config.txt")
      val v = s.getLines.next.trim.toInt ; s.close() ; v
    }
    val nFramesHost: Int = nElemsHost / N_BINS        // should be 3363
    println("DEBUG  nElemsHost  = " + nElemsHost)     // expect 675963
    println("DEBUG  nFramesHost = " + nFramesHost)    // expect 3363
    /* 2) flat power vector */
    val flat   = loadCSV1D[Float](
      "../../../../fpga_io/power_matrix_1d.csv", "\n")
    val inDRAM = DRAM[Float](MAX_ELEMS)
    setMem(inDRAM, flat)

    /* 3) Mel LUT */
    val melLUT  = loadCSV2D[Float](
      "../../../../fpga_io/mel_filterbank.csv", "\n")
    val melDRAM = DRAM[Float](N_MELS, N_BINS)
    setMem(melDRAM, melLUT)

    /* 4) output buffer */
    val outDRAM = DRAM[Float](OUT_MAX)

    /* ---------------- kernel ---------------- */
    Accel {
      val powerCol = SRAM[Float](N_BINS)
      val melRow   = SRAM[Float](N_BINS)
      val melFr    = SRAM[Float](N_MELS)
      val outFr    = SRAM[Float](N_MELS)

      Foreach(0 until MAX_FRAMES) { t =>
        if (t < nFramesHost) {                    // runtime guard
          val base = t * N_BINS
          powerCol load inDRAM(base :: base + N_BINS)

          /* Σ power × filter */
          Foreach(0 until N_MELS){ m =>
            melRow load melDRAM(m, 0 :: N_BINS)
            val acc = Reduce(Reg[Float](0f))(0 until N_BINS){ i =>
              powerCol(i)*melRow(i)
            }{ _+_ }
            melFr(m) = acc.value
          }

          /* frame-wise max + Whisper scale */
          val maxVal = Reduce(Reg[Float](0f))(0 until N_MELS){ i =>
            max(melFr(i), EPS)
          }{ (a,b)=>mux(a>b,a,b) }

          Foreach(0 until N_MELS){ i =>
            val log10  = ln(max(melFr(i), EPS))*INV_LN10
            val clamp  = max(log10, maxVal-8f)
            outFr(i)   = (clamp+4f)/4f
          }

          /* flatten store */
          outDRAM(t*N_MELS :: (t+1)*N_MELS) store outFr
        }
      }
    }

    /* 5) host dump */
    val host   = getMem(outDRAM)                // OUT_MAX elems
    val total  = nFramesHost * N_MELS
    println(s"DEBUG total to write = $total")     // expect 269040
    val pw     = new java.io.PrintWriter(
      "../../../../fpga_io/whisperscale_output.csv")
    for(i <- 0 until total){
      if(i%N_MELS==0 && i!=0) pw.println()
      else if(i%N_MELS!=0)    pw.print(',')
      pw.print(host(i))
    }
    pw.println(); pw.close()
    println(s"[INFO] wrote (80,$nFramesHost) CSV")
  }
}
