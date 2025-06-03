import spatial.dsl._
import scala.io.Source // Needed for reading file
// import spatial.dsl.Mux // Explicit import for Mux - Removed as mux is lowercase and covered by wildcard

//=== QuantizeKernel.scala ===
@spatial class QuantizeKernel extends SpatialTest {
  val MAX_SAMPLES = 1024*1024 // Compile-time maximum size for memories

  def main(args: Array[String]): Unit = {
    val configSource = Source.fromFile("quantize_kernel_config.txt")
    val n_runtime_str = configSource.getLines.next()
    configSource.close()
    val n_runtime_scala_int: scala.Int = n_runtime_str.trim.toInt
    val n_runtime_spatial_i32: I32 = n_runtime_scala_int.to[I32]

    // Declare DRAMs with the compile-time maximum size
    val inDram = DRAM[Float](MAX_SAMPLES)
    val outDram = DRAM[Float](MAX_SAMPLES)

    // Load input data from CSV. The CSV file should contain n_runtime_scala_int elements.
    val input_data_csv = loadCSV1D[Float]("../../../../fpga_io/input_audio_for_quantize.csv", "\n")
    setMem(inDram, input_data_csv) 

    Accel {
      // Declare SRAMs with the compile-time maximum size
      val inSram  = SRAM[Float](MAX_SAMPLES)
      val outSram = SRAM[Float](MAX_SAMPLES)

      // Load only the 'n_runtime_spatial_i32' portion from DRAM to SRAM
      inSram load inDram(0 :: n_runtime_spatial_i32)

      Foreach(n_runtime_spatial_i32 by 1) { i => // Loop based on actual runtime size
        val scaled  = inSram(i) * 32767.to[Float]
        val min_val = -32768.to[Float]
        val max_val =  32767.to[Float]
        val clipped = mux(scaled < min_val, min_val, mux(scaled > max_val, max_val, scaled)) // Use lowercase mux
        val int16_val = clipped.to[I16] 
        outSram(i) = int16_val.to[Float] / 32768.to[Float]
      }

      // Store only the 'n_runtime_spatial_i32' portion from SRAM to DRAM
      outDram(0 :: n_runtime_spatial_i32) store outSram(0 :: n_runtime_spatial_i32) 
    }

    // Get all data from outDram (up to MAX_SAMPLES)
    val full_dram_output = getMem(outDram)
    // Create a new CPU-side Array with only the n_runtime_scala_int elements
    val actual_output_to_write = Array.tabulate(n_runtime_scala_int){ i => full_dram_output(i) }
    // Write only the 'n_runtime_scala_int' portion of the output to CSV
    writeCSV1D(actual_output_to_write, "../../../../fpga_io/quantize_output.csv", "\n")
  }
}
