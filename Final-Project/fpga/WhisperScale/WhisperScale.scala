import spatial.dsl._
import java.io._
import emul.FloatPoint

//=== WhisperScale.scala ===
@spatial object WhisperScale {
  final val MAX_N_ELEMENTS = 400000 // Example value, can be tuned

  def main(args: Array[String]): Unit = {
    // Read runtime size and dynRange from config file written by the Python driver.
    val cfgPath = "whisperscale_config.txt"
    val lines = scala.io.Source.fromFile(cfgPath).getLines().toList
    val n_runtime = if (lines.nonEmpty) lines.head.trim.toInt.to[I32] else 0.to[I32]
    val dynRangeConstant = if (lines.length > 1) lines(1).trim.toFloat.to[Float] else 8.0f // Default if not in file

    // Fallback to command-line args if file is absent or unreadable (e.g. for direct sbt run without python)
    // This part is tricky because Spatial's default main doesn't easily mix config file and direct args for run vs runMain.
    // For now, prioritize config file. If direct sbt run is needed with args, that's a separate use case.

    val inDram  = DRAM[Float](n_runtime)
    val outDram = DRAM[Float](n_runtime)

    val inputData = loadCSV1D[Float]("../../../../fpga_io/logcompress_output.csv", "\n")
    setMem(inDram, inputData)

    Accel {
      val inSram  = SRAM[Float](n_runtime)
      val outSram = SRAM[Float](n_runtime)
      inSram load inDram

      // Calculate the max of the current input (L.max where L is inSram)
      val currentMaxReg = Reg[Float](-1000.0f)
      Reduce(currentMaxReg)(n_runtime by 1 par 1){ k => inSram(k) }{ (a,b) => max(a,b) }
      val l_max = currentMaxReg.value

      Foreach(n_runtime by 1) { i =>
        // val l_val = inSram(i)
        // // 1. L_centered = L - L.max()
        // val l_centered = l_val - l_max
        // // 2. L_clipped = clip(L_centered, -DYN_RANGE_DB, 0.0)
        // // Since l_centered <= 0, max(l_centered, -dynRangeConstant) effectively clips the lower bound.
        // // The upper bound of 0.0 for clipping is naturally handled as l_centered is already <= 0.
        // val l_clipped = max(l_centered, -dynRangeConstant) 
        // // 3. Output = (L_clipped + DYN_RANGE_DB) / DYN_RANGE_DB
        // // Ensure dynRangeConstant is not zero to prevent division by zero.
        // val safeDynRange = mux(abs(dynRangeConstant) < 1e-9f, 1.0f, dynRangeConstant)
        // outSram(i) = (l_clipped + dynRangeConstant) / safeDynRange
        outSram(i) = (inSram(i) + 4.0f) / 4.0f
      }
      outDram store outSram
    }

    val result = getMem(outDram)
    writeCSV1D(result, "../../../../fpga_io/whisperscale_output.csv", delim="\n")
  }
}
