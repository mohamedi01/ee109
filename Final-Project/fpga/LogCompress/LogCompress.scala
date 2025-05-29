import spatial.dsl._
import scala.io.Source

@spatial object LogCompressCORDIC {
  // Define a compile-time constant for max SRAM size.
  // Adjust this value based on the maximum expected input size and FPGA resources.
  final val MAX_N_ELEMENTS = 400000 // Example value, can be tuned

  def main(args: Array[String]): Unit = {
    // ─── Kernel arguments ───────────────────────────────────────────────────────
    val n_runtime = args(0).to[I32] // Actual number of elements to process
    val dynRange  = args(1).to[Float]

    // ─── CORDIC table size ──────────────────────────────────────────────────────
    val N = 16

    // ─── Host-side streaming load of CORDIC tables from CSV ─────────────────────
    // Each CSV has one float per line
    val expLines = Source.fromFile("logcompress_consts.csv").getLines().toArray
    val scalaExpArray = expLines.map(_.toFloat.to[Float])
    val expTable: Array[Float]    = Array(scalaExpArray: _*)
    val shiftLines = Source.fromFile("logcompress_shifts.csv").getLines().toArray
    val scalaShiftArray = shiftLines.map(_.toFloat.to[Float])
    val twoNegTable: Array[Float] = Array(scalaShiftArray: _*)

    // ─── Off-chip DRAMs ─────────────────────────────────────────────────────────
    val inDram     = DRAM[Float](n_runtime)
    val outDram    = DRAM[Float](n_runtime)
    val constDram  = DRAM[Float](N)
    val twoNegDram = DRAM[Float](N)

    // ─── Initialize DRAMs with host-loaded tables ───────────────────────────────
    setMem(constDram,  expTable)
    setMem(twoNegDram, twoNegTable)
    
    // Load input data (e.g., mel filterbank output)
    val inputData = loadCSV1D[Float]("../../../../fpga_io/melfilterbank_output.csv", "\n")
    setMem(inDram, inputData)

    // Pass n_runtime to the accelerator as an argument
    val Accel_n = ArgIn[I32]
    setArg(Accel_n, n_runtime)

    // ─── Accelerator ────────────────────────────────────────────────────────────
    Accel {
      // On-chip SRAMs
      val buf        = SRAM[Float](MAX_N_ELEMENTS)
      val Ktable     = SRAM[Float](N)
      val twoNegSram = SRAM[Float](N)
      
      // Load n_runtime elements from inDram. Since inDram's size is n_runtime,
      // this will load all of inDram into the beginning of buf.
      buf load inDram
      Ktable     load constDram
      twoNegSram load twoNegDram

      // 1) Skip the max computation for now - we'll compute it after log10
      // val maxReg = Reg[Float](0.to[Float])
      // Reduce(maxReg)(0 until Accel_n) { i => buf(i) } { (a, b) => mux(a > b, a, b) }
      // val mx = maxReg.value

      // 2) Skip dynamic range clamping in linear space
      // Foreach(Accel_n by 1) { i => buf(i) = max(buf(i), mx - dynRange) }

      // 3) Prepare output SRAM
      val outSram = SRAM[Float](MAX_N_ELEMENTS)

      // 4) Simple log10
      Foreach(Accel_n by 1) { i =>
        val v0 = buf(i)
        
        // Take absolute value and clamp to minimum to avoid log(0)
        val v_abs = mux(v0 < 0.0f, -v0, v0)  // abs(v0)
        val v_safe = max(v_abs, 1e-10f)
        
        // Simple log10 using conditional logic for typical mel filterbank ranges
        val log_result = Reg[Float](0.0f)
        
        // Handle different magnitude ranges
        log_result := mux(v_safe >= 1.0f, 0.0f,              // log10(1) = 0
                     mux(v_safe >= 0.1f, -1.0f,              // log10(0.1) = -1
                     mux(v_safe >= 0.01f, -2.0f,             // log10(0.01) = -2
                     mux(v_safe >= 0.001f, -3.0f,            // log10(0.001) = -3
                     mux(v_safe >= 0.0001f, -4.0f,           // log10(0.0001) = -4
                     mux(v_safe >= 0.00001f, -5.0f,          // log10(0.00001) = -5
                     mux(v_safe >= 0.000001f, -6.0f,         // log10(0.000001) = -6
                     mux(v_safe >= 0.0000001f, -7.0f,        // log10(0.0000001) = -7
                     mux(v_safe >= 0.00000001f, -8.0f,       // log10(0.00000001) = -8
                     mux(v_safe >= 0.000000001f, -9.0f,      // log10(0.000000001) = -9
                     -10.0f))))))))))                         // log10(1e-10) = -10
        
        // Add linear interpolation within each decade for better accuracy
        // For value v in range [10^n, 10^(n+1)], use better approximation
        // log10(x) ≈ n + log10(x/10^n) where x/10^n is in [1, 10)
        
        // First, determine which decade we're in and compute the normalized value
        val norm_val = Reg[Float](0.0f)
        val decade = Reg[Float](0.0f)
        
        norm_val := mux(v_safe >= 1.0f, v_safe,
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
        
        decade := log_result.value
        
        // Now norm_val is in [1, 10), use polynomial approximation for log10(x)
        // log10(x) ≈ a0 + a1*x + a2*x^2 for x in [1, 10)
        // Using simplified coefficients that give reasonable accuracy
        val x = norm_val.value
        val x2 = x * x
        val log_frac = -0.6308f + 0.5017f * x - 0.0479f * x2
        
        outSram(i) = decade.value + log_frac
      }
      
      // 5) Find max in log domain
      val logMaxReg = Reg[Float](-1000.0f) // Initialize to very negative
      Reduce(logMaxReg)(0 until Accel_n) { i => outSram(i) } { (a, b) => mux(a > b, a, b) }
      val logMax = logMaxReg.value
      
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
