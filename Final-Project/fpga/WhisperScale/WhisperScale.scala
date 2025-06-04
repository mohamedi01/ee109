import spatial.dsl._
import java.io._
import emul.FloatPoint

//=== WhisperScale.scala ===
@spatial object WhisperScale {
  final val MAX_N_ELEMENTS = 400000 // Example value, can be tuned

  def main(args: Array[String]): Unit = {
    // Read runtime size from config file written by the Python driver.
    val cfgPath = "whisperscale_config.txt"
    val lines = scala.io.Source.fromFile(cfgPath).getLines().toList
    val n_runtime = if (lines.nonEmpty) lines.head.trim.toInt.to[I32] else 0.to[I32]
    // val dynRangeConstant = if (lines.length > 1) lines(1).trim.toFloat.to[Float] else 8.0f // Not used in this simplified version

    val inDram  = DRAM[Float](n_runtime)
    val outDram = DRAM[Float](n_runtime)

    // Input data is assumed to be log-Mel values AFTER dynamic range compression
    val inputData = loadCSV1D[Float]("../../../../fpga_io/logcompress_input.csv", "\n")
    setMem(inDram, inputData)

    Accel {
      val inSram  = SRAM[Float](n_runtime)
      val outSram = SRAM[Float](n_runtime)
      inSram load inDram

      Foreach(n_runtime by 1) { i =>
        val l_val = inSram(i) // l_val is "log-Mel after dynamic range compression"
        // Apply Whisper scaling: (x + 4) / 4
        outSram(i) = (l_val + 4.0f) / 4.0f
      }
      outDram store outSram
    }

    val result = getMem(outDram)
    writeCSV1D(result, "../../../../fpga_io/whisperscale_output.csv", delim="\n")
  }
}
