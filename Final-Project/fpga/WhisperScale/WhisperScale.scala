import spatial.dsl._
import java.io._
import emul.FloatPoint

//=== WhisperScale.scala ===
@spatial object WhisperScale {
  final val MAX_N_ELEMENTS = 400000 // Example value, can be tuned

  def main(args: Array[String]): Unit = {
    // Read runtime size from config file written by the Python driver.
    // Fallback to command-line arg if file is absent or unreadable.
    val cfgPath = "whisperscale_config.txt"
    val n_runtime: I32 = {
      val cfgFile = new File(cfgPath)
      if (cfgFile.exists()) {
        val lines = scala.io.Source.fromFile(cfgPath).getLines().toList
        if (lines.nonEmpty) lines.head.trim.toInt.to[I32] else {
          // If config file empty, fall back to CLI arg 0
          if (args.length > 0) args(0).to[I32] else 0.to[I32]
        }
      } else {
        // If file doesn't exist, fall back to CLI arg 0 (for interactive runs)
        if (args.length > 0) args(0).to[I32] else 0.to[I32]
      }
    }
    val inDram = DRAM[Float](n_runtime)
    val outDram = DRAM[Float](n_runtime)
    
    // Load input data
    val inputData = loadCSV1D[Float]("../../../../fpga_io/logcompress_output.csv", "\n")
    setMem(inDram, inputData)

    val Accel_n = ArgIn[I32]
    setArg(Accel_n, n_runtime)

    Accel {
      val buf = SRAM[Float](MAX_N_ELEMENTS)
      buf load inDram
      Foreach(Accel_n by 1) { i =>
        buf(i) = (buf(i) + 4.to[Float]) / 4.to[Float]
      }
      outDram store buf(0 :: Accel_n - 1)
    }

    // Get data from outDram
    val result = getMem(outDram)
    
    // Write output to CSV
    writeCSV1D(result, "../../../../fpga_io/fpga_output.csv", delim="\n")
  }
}
