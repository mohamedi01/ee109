import spatial.dsl._
import scala.io.Source

@spatial object LogCompressCORDIC {
  // Define a compile-time constant for max SRAM size.
  // Adjust this value based on the maximum expected input size and FPGA resources.
  final val MAX_N_ELEMENTS = 400000 // Example value, can be tuned
  final val LN10_CONST = 2.30258509299f // ln(10) - May not be needed if direct log10 LUT
  final val N_LUT_LOG10 = 128 // Must match N_LUT_LOG10 in spatial_dsp.py, increased from 32

  def main(args: Array[String]): Unit = {
    // ─── Kernel arguments ───────────────────────────────────────────────────────
    val n_runtime = args(0).to[I32] // Actual number of elements to process
    val dynRange  = args(1).to[Float]
    val globalLogMaxIn = args(2).to[Float] // New argument: global log max from Python

    // Remove CORDIC table size and related loads if not used
    // val N = 24 // CORDIC iteration count

    // Load log10 LUT tables
    val xLutLines = Source.fromFile("log10_x_lut.csv").getLines().toArray
    val scalaXLutArray = xLutLines.map(_.toFloat.to[Float])
    val xLutTable: Array[Float] = Array(scalaXLutArray: _*)

    val yLutLines = Source.fromFile("log10_y_lut.csv").getLines().toArray
    val scalaYLutArray = yLutLines.map(_.toFloat.to[Float])
    val yLutTable: Array[Float] = Array(scalaYLutArray: _*)

    // ─── Off-chip DRAMs ─────────────────────────────────────────────────────────
    val inDram     = DRAM[Float](n_runtime)
    val outDram    = DRAM[Float](n_runtime)
    // val constDram  = DRAM[Float](N) // For CORDIC Ktable
    // val twoNegDram = DRAM[Float](N) // For CORDIC shifts
    val xLutDram   = DRAM[Float](N_LUT_LOG10)
    val yLutDram   = DRAM[Float](N_LUT_LOG10)
    val rawLogOutDram = DRAM[Float](n_runtime)

    // ─── Initialize DRAMs with host-loaded tables ───────────────────────────────
    // setMem(constDram,  expTable) // For CORDIC
    // setMem(twoNegDram, twoNegTable) // For CORDIC
    setMem(xLutDram, xLutTable)
    setMem(yLutDram, yLutTable)
    
    val inputData = loadCSV1D[Float]("../../../../fpga_io/melfilterbank_output.csv", "\n")
    setMem(inDram, inputData)

    // Pass n_runtime to the accelerator as an argument
    val Accel_n = ArgIn[I32]
    setArg(Accel_n, n_runtime)

    // Pass globalLogMaxIn to the accelerator as an argument
    val Accel_globalLogMax = ArgIn[Float]
    setArg(Accel_globalLogMax, globalLogMaxIn)

    // ─── Accelerator ────────────────────────────────────────────────────────────
    Accel {
      // On-chip SRAMs
      val buf        = SRAM[Float](MAX_N_ELEMENTS)
      // val Ktable     = SRAM[Float](N) // For CORDIC
      // val twoNegSram = SRAM[Float](N) // For CORDIC
      val xLutSram   = SRAM[Float](N_LUT_LOG10)
      val yLutSram   = SRAM[Float](N_LUT_LOG10)
      
      buf load inDram
      // Ktable     load constDram // For CORDIC
      // twoNegSram load twoNegDram // For CORDIC
      xLutSram load xLutDram
      yLutSram load yLutDram

      // 1) Skip the max computation for now - we'll compute it after log10
      // val maxReg = Reg[Float](0.to[Float])
      // Reduce(maxReg)(0 until Accel_n) { i => buf(i) } { (a, b) => mux(a > b, a, b) }
      // val mx = maxReg.value

      // 2) Skip dynamic range clamping in linear space
      // Foreach(Accel_n by 1) { i => buf(i) = max(buf(i), mx - dynRange) }

      // 3) Prepare output SRAM
      val outSram = SRAM[Float](MAX_N_ELEMENTS)

      // 4) log10 using LUT and linear interpolation
      Foreach(Accel_n by 1) { i =>
        val v0 = buf(i)
        val v_abs = mux(v0 < 0.0f, -v0, v0)
        val v_safe = max(v_abs, 1e-10f)
        
        // --- Start: Decade and Normalization Calculation (remains the same) ---
        val decade_calc_reg = Reg[Float](0.0f)
        decade_calc_reg := mux(v_safe >= 1.0f, 0.0f,
                         mux(v_safe >= 0.1f, -1.0f,
                         mux(v_safe >= 0.01f, -2.0f,
                         mux(v_safe >= 0.001f, -3.0f,
                         mux(v_safe >= 0.0001f, -4.0f,
                         mux(v_safe >= 0.00001f, -5.0f,
                         mux(v_safe >= 0.000001f, -6.0f,
                         mux(v_safe >= 0.0000001f, -7.0f,
                         mux(v_safe >= 0.00000001f, -8.0f,
                         mux(v_safe >= 0.000000001f, -9.0f,
                         -10.0f))))))))))
        
        val norm_val_calc_reg = Reg[Float](0.0f)
        norm_val_calc_reg := mux(v_safe >= 1.0f, v_safe,
                        mux(v_safe >= 0.1f, v_safe * 10.0f,
                        mux(v_safe >= 0.01f, v_safe * 100.0f,
                        mux(v_safe >= 0.001f, v_safe * 1000.0f,
                        mux(v_safe >= 0.0001f, v_safe * 10000.0f,
                        mux(v_safe >= 0.00001f, v_safe * 100000.0f,
                        mux(v_safe >= 0.000001f, v_safe * 1000000.0f,
                        mux(v_safe >= 0.0000001f, v_safe * 10000000.0f,
                        mux(v_safe >= 0.00000001f, v_safe * 100000000.0f,
                        mux(v_safe >= 0.000000001f, v_safe * 1000000000.0f,
                        1.0f))))))))))
        
        val decade_val = decade_calc_reg.value
        val norm_val = norm_val_calc_reg.value // This is in [1, 10)
        // --- End: Decade and Normalization Calculation ---

        // --- Start: log10(norm_val) using LUT --- 
        // norm_val is in [1.0, 10.0]. xLutSram is for this range.
        
        // Find index j such that xLutSram(j) <= norm_val < xLutSram(j+1)
        // Default to the second to last index, for the last interpolation segment [x(N-2), x(N-1)]
        val found_idx = Reg[Int](N_LUT_LOG10 - 2).buffer 
        val found_flag = Reg[Bit](false.to[Bit]).buffer
        found_flag := false.to[Bit] // Reset for each norm_val

        Foreach(N_LUT_LOG10 - 1 by 1) { j_scan => // j_scan goes from 0 to N_LUT_LOG10 - 2
            if (!found_flag.value && norm_val < xLutSram(j_scan + 1)) {
                found_idx := j_scan
                found_flag := true.to[Bit]
            }
        }
        // If norm_val is exactly xLutSram(0) (i.e., 1.0), and xLutSram(0) < xLutSram(1), 
        // then j_scan=0, norm_val < xLutSram(1) is true. found_idx becomes 0. Correct.
        // If norm_val is xLutSram(N_LUT_LOG10-1) (i.e. 10.0), 
        // the loop runs up to j_scan = N_LUT_LOG10-2. 
        // Condition norm_val < xLutSram(j_scan+1) i.e. 10.0 < xLutSram(N_LUT_LOG10-1)=10.0 is false.
        // So found_flag remains false. found_idx remains N_LUT_LOG10-2. Correct.
        
        val j_idx = found_idx.value
        val x_low = xLutSram(j_idx)        // x_j
        val y_low = yLutSram(j_idx)        // y_j = log10(x_j)
        val x_high = xLutSram(j_idx + 1)   // x_j+1
        val y_high = yLutSram(j_idx + 1)   // y_j+1 = log10(x_j+1)

        // Linear interpolation: y = y0 + (x-x0)*(y1-y0)/(x1-x0)
        val log10_of_norm_val = Reg[Float]
        val dx = x_high - x_low
        // Avoid division by zero if dx is extremely small (should not happen with linspace unless N_LUT_LOG10 is huge or points are identical)
        if (dx > 1e-9f) { // Check for non-zero denominator
            log10_of_norm_val := y_low + (norm_val - x_low) * (y_high - y_low) / dx
        } else { // If x_high and x_low are ~the same, just use y_low (or y_high)
            log10_of_norm_val := y_low 
        }
        // --- End: log10 LUT ---
        
        outSram(i) = decade_val + log10_of_norm_val.value
      }
      
      // DEBUG: Store raw log10 output before dynamic range compression to the dedicated DRAM
      rawLogOutDram store outSram(0 :: Accel_n - 1)
      // End DEBUG
      
      // 5) Use the globalLogMax passed from Python instead of recalculating it here
      // val logMaxReg = Reg[Float](-1000.0f) // Initialize to very negative
      // Reduce(logMaxReg)(0 until Accel_n) { i => outSram(i) } { (a, b) => mux(a > b, a, b) }
      // val logMax = logMaxReg.value
      val logMax = Accel_globalLogMax.value // Use the globally computed max passed as an argument
      
      // Debug: Create registers to store intermediate values for debugging
      val debugFirstLog = Reg[Float](0.0f)
      val debugLogMax = Reg[Float](0.0f)
      val debugClampVal = Reg[Float](0.0f)
      
      debugFirstLog := outSram(0)  // Store first log value
      debugLogMax := logMax         // Store max log value
      debugClampVal := logMax - dynRange  // Store clamp value
      
      // 6) Apply dynamic range compression in log domain
      Foreach(Accel_n by 1) { i =>
        outSram(i) = max(outSram(i), logMax - dynRange)
      }

      // 7) Write results back
      // Store only the n_runtime computed elements from outSram to outDram
      outDram store outSram(0 :: Accel_n - 1)
    }
    
    // Write output
    val result = getMem(outDram)
    
    writeCSV1D(result, "../../../../fpga_io/logcompress_output.csv", delim="\n")
    
    // DEBUG: Get and write raw log output from its dedicated DRAM
    val rawLogResult = getMem(rawLogOutDram)
    writeCSV1D(rawLogResult, "../../../../fpga_io/logcompress_output_raw_fpga.csv", delim="\n")
    // End DEBUG
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
