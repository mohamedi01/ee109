import spatial.dsl._
import scala.io.Source
import java.io.File

//=== STFTKernel.scala ===
@spatial object STFTKernel {
  type T = Double

  // Compile-time maximums for SRAM/DRAM sizing.
  // DEFAULT_N_FFT in python is 400. This should be >= that.
  val MAX_NFFT_COMPILE_TIME = 1024
  val MAX_BINS_COMPILE_TIME = MAX_NFFT_COMPILE_TIME / 2 + 1

  def main(args: Array[String]): Unit = {
    // 1. Read NFFT_RUNTIME from config file
    val configSource = Source.fromFile("stft_kernel_config.txt") // Assumes sbt CWD is kernel's root
    val nfft_runtime_str = configSource.getLines.next()
    configSource.close()
    val NFFT_RUNTIME_SCALA_INT: scala.Int = nfft_runtime_str.trim.toInt
    val NUM_BINS_RUNTIME_SCALA_INT: scala.Int = NFFT_RUNTIME_SCALA_INT / 2 + 1

    // Convert runtime sizes to Spatial types for use in Accel
    val nfft_runtime_i32 = NFFT_RUNTIME_SCALA_INT.to[I32]
    val num_bins_runtime_i32 = NUM_BINS_RUNTIME_SCALA_INT.to[I32]
    val NFFT_RUNTIME_T = NFFT_RUNTIME_SCALA_INT.to[T] // For calculations

    // 2. Declare DRAMs for input audio frame and output STFT bins
    val inputFrameDRAM  = DRAM[T](MAX_NFFT_COMPILE_TIME)
    val realOutputDRAM  = DRAM[T](MAX_BINS_COMPILE_TIME)
    val imagOutputDRAM  = DRAM[T](MAX_BINS_COMPILE_TIME)
    val hannWindowDRAM  = DRAM[T](MAX_NFFT_COMPILE_TIME)
    val windowedDebugDRAM = DRAM[T](MAX_NFFT_COMPILE_TIME) // For debug output

    // 3. Load input audio frame from CSV
    val input_audio_frame_csv = loadCSV1D[T]("../../../../fpga_io/input_stft_window.csv", "\n")
    setMem(inputFrameDRAM, input_audio_frame_csv)

    // Load Hann window from CSV (exported from Python)
    val hann_window_csv = loadCSV1D[T]("../../../../fpga_io/hann_window.csv", "\n")
    setMem(hannWindowDRAM, hann_window_csv)

    Accel {
      // SRAMs for processing
      val rawInputSRAM        = SRAM[T](MAX_NFFT_COMPILE_TIME)
      val hannWindowSRAM      = SRAM[T](MAX_NFFT_COMPILE_TIME) 
      val windowedFrameSRAM   = SRAM[T](MAX_NFFT_COMPILE_TIME)
      val realOutputSRAM      = SRAM[T](MAX_BINS_COMPILE_TIME)
      val imagOutputSRAM      = SRAM[T](MAX_BINS_COMPILE_TIME)

      // Load relevant portion of input frame from DRAM to SRAM
      rawInputSRAM load inputFrameDRAM(0 :: nfft_runtime_i32)
      hannWindowSRAM load hannWindowDRAM(0 :: nfft_runtime_i32)

      // Apply Hann window to the input frame
      Foreach(nfft_runtime_i32 by 1) { i =>
        windowedFrameSRAM(i) = rawInputSRAM(i) * hannWindowSRAM(i)
      }

      // Debug: Store windowed input to DRAM for comparison with Python
      windowedDebugDRAM(0 :: nfft_runtime_i32) store windowedFrameSRAM(0 :: nfft_runtime_i32)

      // Scale by 2.0 to match Python STFT amplitude
      val scaling_factor = 2.0.to[T]

      Foreach(num_bins_runtime_i32 by 1) { k_idx => // k from 0 to NUM_BINS_RUNTIME-1
        val k_t = k_idx.to[T]
        
        val real_sum_reg = Reg[T](0.to[T])
        val imag_sum_reg = Reg[T](0.to[T])

        Reduce(real_sum_reg)(nfft_runtime_i32 by 1) { n_idx => // n from 0 to NFFT_RUNTIME-1
          val n_t = n_idx.to[T]
          val angle = (2.to[T] * Math.PI.to[T] * k_t * n_t) / NFFT_RUNTIME_T
          val x_n = windowedFrameSRAM(n_idx)
          x_n * cos(angle)
        }{_+_}

        Reduce(imag_sum_reg)(nfft_runtime_i32 by 1) { n_idx => // n from 0 to NFFT_RUNTIME-1
          val n_t = n_idx.to[T]
          val angle = (2.to[T] * Math.PI.to[T] * k_t * n_t) / NFFT_RUNTIME_T
          val x_n = windowedFrameSRAM(n_idx)
          x_n * (-sin(angle)) // Standard DFT sign convention
        }{_+_}

        realOutputSRAM(k_idx) = real_sum_reg.value * scaling_factor
        imagOutputSRAM(k_idx) = imag_sum_reg.value * scaling_factor
      }

      // Store results from SRAM to DRAM
      realOutputDRAM(0 :: num_bins_runtime_i32) store realOutputSRAM(0 :: num_bins_runtime_i32)
      imagOutputDRAM(0 :: num_bins_runtime_i32) store imagOutputSRAM(0 :: num_bins_runtime_i32)
    }

    // After Accel: get debug windowed input and write to CSV
    val windowed_debug = getMem(windowedDebugDRAM)
    writeCSV1D(windowed_debug, "../../../../fpga_io/fpga_windowed_input_debug.csv", "\n")

    // 4. Get output data from DRAM and save to CSV files
    val real_output_data_full = getMem(realOutputDRAM)
    val imag_output_data_full = getMem(imagOutputDRAM)

    // Extract only the valid portion (NUM_BINS_RUNTIME elements)
    val real_output_to_write = Array.tabulate(NUM_BINS_RUNTIME_SCALA_INT){ i => real_output_data_full(i) }
    val imag_output_to_write = Array.tabulate(NUM_BINS_RUNTIME_SCALA_INT){ i => imag_output_data_full(i) }

    writeCSV1D(real_output_to_write, "../../../../fpga_io/stft_real_output.csv", "\n")
    writeCSV1D(imag_output_to_write, "../../../../fpga_io/stft_imag_output.csv", "\n")

    // println("STFTKernel processing finished successfully.") // Optional: success message
  }
}