# Hardware Overview: DSP/ASR Pipeline Kernels

This document provides an overview of the custom hardware kernels implemented for the audio DSP/ASR pipeline, including architectural notes, the rationale behind key design decisions, and a discussion of the tradeoffs considered. The focus is on why these choices were made, considering project goals, hardware constraints, and integration with the overall system. Special attention is given to the specific Spatial code constructs and why they were chosen over alternatives, with detailed discussion of the implications for performance, resource usage, and maintainability.

---

## 1. QuantizeKernel
**Purpose:** Quantizes floating-point audio samples to int16 range, then rescales back to float.

**Architecture:**
- Reads runtime size from a config file.
- Loads input audio from CSV into DRAM, then into SRAM.
- Scales each sample by 32767, clips to int16 range, converts to int16, then rescales to float.
- Stores results back to DRAM and writes to CSV.

**Design Rationale & Tradeoffs (Spatial Code Focus):**
- **SRAM vs. DRAM:** We explicitly use DRAM for off-chip storage and SRAM for on-chip processing. This separation is not just for performance, but also for clarity: it makes the dataflow visible in the code and helps with debugging. We could have used only DRAM, but that would have limited parallelism and increased latency.
- **Foreach for Parallelism:** The quantization loop is written with `Foreach`, which Spatial can unroll or pipeline. We considered using a sequential loop, but that would have serialized the computation and underutilized the FPGA's parallel resources. We also considered unrolling the loop fully, but this would have increased area usage; leaving it as a `Foreach` allows the compiler to choose the best mapping.
- **mux vs. if-else:** We use `mux` for conditional logic because it is synthesizable and produces predictable hardware. Using Scala's `if-else` would have resulted in less predictable hardware and potentially more complex control logic.
- **Parameterization:** The kernel is parameterized by input size, using both runtime arguments and compile-time constants. This allows the same code to be reused for different workloads. We could have hardcoded the size, but that would have reduced flexibility and required recompilation for each new workload.
- **Explicit Buffering:** We use separate input and output buffers in both DRAM and SRAM. An alternative would be in-place processing, but explicit buffers make the dataflow and dependencies clear, which is important for debugging and verification.
- **Debugging and Verification:** The explicit data movement and separation of buffers make it easier to verify correctness at each stage, as intermediate results can be inspected and compared to software reference outputs.

---

## 2. STFTKernel
**Purpose:** Computes the Short-Time Fourier Transform (STFT) for a single audio frame.

**Architecture:**
- Reads NFFT size from config.
- Loads input frame and Hann window from CSVs.
- Applies window, then computes DFT for each frequency bin (real and imaginary parts).
- Writes windowed input and DFT outputs to CSV.

**Design Rationale & Tradeoffs (Spatial Code Focus):**
- **Explicit DFT with Nested Foreach:** We use nested `Foreach` loops for the DFT, rather than a black-box FFT. This makes the computation transparent and easy to debug. We considered using a library FFT, but explicit DFT allows us to control the degree of parallelism and pipelining.
- **SRAM for Windowing:** The input frame and window are loaded into SRAM, enabling fast, parallel access. We could have streamed from DRAM, but SRAM provides higher bandwidth and lower latency.
- **Reduce for Accumulation:** The real and imaginary sums for each frequency bin are computed with `Reduce`, which is mapped to efficient adder trees or pipelined accumulators. We could have used explicit accumulation in a loop, but `Reduce` is more efficient and easier to optimize.
- **Parameterization:** The kernel is parameterized by NFFT, allowing it to be reused for different window sizes. Hardcoding the size would have reduced flexibility.
- **Debugging:** The explicit structure makes it easy to compare intermediate results to software reference implementations and to insert probes for verification.

---

## 3. PowerSpectrum
**Purpose:** Computes the power spectrum from real and imaginary FFT outputs.

**Architecture:**
- Reads number of bins from config.
- Loads real and imaginary FFT outputs from CSVs.
- For each bin, computes \( \text{power} = \text{real}^2 + \text{imag}^2 \).
- Writes output to CSV.

**Design Rationale & Tradeoffs (Spatial Code Focus):**
- **Elementwise Foreach:** The power computation is done with a `Foreach` loop. We could have used a `Reduce` to compute global statistics, but for per-bin operations, `Foreach` is more efficient and exposes more parallelism.
- **SRAM Buffers:** Real and imaginary parts are loaded into separate SRAMs. We considered using a single interleaved buffer, but separate buffers simplify the access patterns and make the code easier to understand and debug.
- **Direct Arithmetic:** The computation is written as direct arithmetic, which is easy for the compiler to optimize and for the designer to verify.
- **Unrolling and Pipelining:** The use of `Foreach` allows us to experiment with different unrolling and pipelining strategies to balance resource usage and performance. Full unrolling would maximize throughput but use more area; partial unrolling or pipelining provides a good tradeoff.
- **Debugging:** The explicit structure makes it easy to insert checks or probes for verification.

---

## 4. MelFilterbank
**Purpose:** Applies a Mel filterbank matrix to a power spectrum vector.

**Architecture:**
- Reads number of bands and bins from config.
- Loads Mel filterbank matrix and power spectrum vector from CSVs.
- For each band, computes the dot product with the power spectrum.
- Writes output to CSV.

**Design Rationale & Tradeoffs (Spatial Code Focus):**
- **Nested Foreach for Matrix-Vector Multiply:** We use a nested `Foreach` (over bands and bins) to express the matrix-vector multiply. An alternative would be to use a single `Reduce` over the product, but the nested `Foreach` gives us more control over the dataflow and allows us to tune the degree of parallelism and pipelining.
- **SRAM for Locality:** Both the filterbank and the input vector are loaded into SRAM. We could have streamed the data from DRAM, but that would have resulted in repeated DRAM accesses and lower performance. SRAM allows for data reuse and higher throughput.
- **Explicit Accumulator:** The accumulator for the dot product is a local variable, which is mapped to a register. We considered using a reduction tree, but for small inner products, a local accumulator is more efficient and easier to verify.
- **Banking and Pipelining:** By using explicit loops and SRAM, we can control memory banking and loop pipelining, which are critical for achieving high throughput on FPGAs. We could have used higher-level abstractions, but explicit control gives us better performance and predictability.
- **Debugging:** The explicit structure makes it easy to compare intermediate results to software reference implementations.

---

## 5. LogCompressCORDIC
**Purpose:** Applies log10 compression and dynamic range clamping to Mel-filtered features.

**Architecture:**
- Reads runtime size, dynamic range, and global log max from arguments.
- Loads log10 LUTs for fast log computation.
- For each input:
  - Computes log10 using LUT and interpolation.
  - Applies dynamic range clamping in log domain.
- Writes both clamped and raw log outputs to CSV.

**Design Rationale & Tradeoffs (Spatial Code Focus):**
- **LUTs for Logarithm:** We use LUTs (loaded into SRAM) and linear interpolation for log10. Alternatives include CORDIC or floating-point log, but LUTs are much more resource-efficient and fast. The explicit use of SRAM and indexed access in Spatial makes this approach easy to implement and optimize.
- **Foreach for Parallelism:** The log and clamping operations are done in a `Foreach` loop. We could have used a sequential loop, but `Foreach` exposes parallelism and allows for pipelining and unrolling.
- **Explicit Register Use:** Registers are used for intermediate values (e.g., for interpolation). We could have used global variables or memory, but registers are faster and easier to reason about in hardware.
- **Host-Driven Max Calculation:** The global log max is passed in from the host, rather than computed in hardware with a `Reduce`. We considered doing the reduction in hardware, but this would have increased area usage and complexity. Offloading to the host saves resources and simplifies the kernel.
- **Explicit Clamping:** The clamping operation is written explicitly, rather than as a function call, to ensure it is mapped efficiently to hardware.
- **Debugging:** The explicit use of LUTs and registers makes it easy to verify correctness and compare to software reference outputs.

---

## 6. WhisperScale
**Purpose:** Applies Whisper model scaling to log-Mel features: \( y = (x + 4) / 4 \).

**Architecture:**
- Reads runtime size from config.
- Loads log-Mel input from CSV.
- For each value, applies the scaling formula.
- Writes output to CSV.

**Design Rationale & Tradeoffs (Spatial Code Focus):**
- **Foreach for Elementwise Operations:** The scaling is implemented with a `Foreach` loop, which can be pipelined or unrolled. We could have used a `Reduce` if we wanted to accumulate statistics, but for pure elementwise operations, `Foreach` is more natural and exposes more parallelism.
- **SRAM Buffers:** Input and output are staged through SRAM. We considered streaming directly from DRAM, but SRAM provides much higher bandwidth and allows for parallel access, which is critical for high-throughput pipelines.
- **Direct Arithmetic:** The affine transform is written directly in Spatial, rather than as a function call or black-box operation. This ensures the operation is inlined and optimized, and makes the hardware structure transparent.
- **Minimal Control Logic:** By keeping the kernel simple and explicit, we minimize the control logic required, which reduces area and makes timing closure easier.
- **Debugging:** The explicit use of SRAM and `Foreach` makes it easy to insert debug prints or probes for verification.

---

## 7. MelLogScaleKernel

**Note:**
MelLogScaleKernel is a fused kernel that replaces the MelFilterbank, LogCompressCORDIC, and WhisperScale steps above. If you use MelLogScaleKernel, you do not need to run those three separate kernels; it performs all their operations in a single hardware block for maximum efficiency.

**Purpose:**  
Combines Mel filtering, log compression, dynamic range clamping, and Whisper scaling into a single hardware kernel. It takes a power spectrogram and produces log-Mel features scaled for Whisper, outputting an 80 × T matrix for downstream ASR/NLP.

**Architecture:**  
- **Inputs:**  
  - Power spectrogram (201 × T) from `power_matrix.csv`
  - Mel filterbank (80 × 201) from `mel_filterbank.csv`
- **Processing:**  
  - For each frame (column) of the power spectrogram:
    - Loads the frame and Mel filterbank row into on-chip SRAM.
    - Computes Mel energies via matrix-vector multiply (dot product).
    - Finds the max Mel energy for dynamic range clamping.
    - Applies log10 (via ln and scaling), clamps to an 8 dB window, and applies Whisper scaling: `(log10(x) + 4) / 4`.
  - All steps are performed in a single `Accel` block, pipelined per frame.
- **Outputs:**  
  - Writes the final 80 × T matrix to `whisperscale_output.csv`.

**Design Rationale & Tradeoffs (Spatial Code Focus):**
- **All-in-One Kernel:**  
  - By fusing Mel filtering, log compression, clamping, and scaling, we minimize off-chip memory traffic and intermediate storage, reducing latency and maximizing throughput.
  - The alternative would be to split these into separate kernels, but that would require more DRAM bandwidth and increase pipeline complexity.
- **SRAM for Locality:**  
  - Each frame and Mel filter row are loaded into SRAM, maximizing data reuse and minimizing DRAM accesses. This is critical for high-throughput, as each Mel band accesses the same frame.
- **Nested Foreach and Reduce:**  
  - Uses nested `Foreach` for looping over frames and Mel bands, and `Reduce` for dot products and max-finding. This exposes parallelism and allows the hardware generator to pipeline and unroll as resources allow.
  - We could have used a single large matrix multiply, but the explicit loops give more control over memory access patterns and pipelining.
- **Explicit Register and mux Use:**  
  - Registers are used for accumulators and max values, and `mux` is used for clamping and conditional logic, ensuring synthesizability and predictable hardware.
- **Logarithm via ln and Scaling:**  
  - Instead of a LUT or CORDIC, the kernel uses the built-in `ln` function and multiplies by `1/ln(10)` to get log10. This is a tradeoff between accuracy and resource usage; for FPGAs with efficient floating-point, this is acceptable, but a LUT could be used for further optimization.
- **Dynamic Range Clamping:**  
  - The max Mel value is found per frame using a `Reduce`, and clamping is applied to ensure robust feature scaling for ASR.
- **Whisper Scaling:**  
  - The final scaling `(clamped + 4) / 4` is performed in hardware, ensuring the output matches the Whisper model's expectations exactly.
- **Parameterization:**  
  - The kernel is parameterized by the number of frames (T), read from a config file, allowing it to process variable-length audio without recompilation.
- **Debugging and Verification:**  
  - The explicit, step-by-step structure makes it easy to compare intermediate results to software reference implementations and to insert probes for verification.
- **Tradeoffs:**  
  - **Pros:** Maximum throughput, minimal off-chip traffic, and a single, unified hardware block for the entire log-Mel+Whisper feature pipeline.
  - **Cons:** Increased kernel complexity, higher on-chip memory usage, and less flexibility if only part of the pipeline needs to be changed or debugged.

**Summary:**  
`MelLogScaleKernel` exemplifies the benefits of fusing multiple DSP stages into a single hardware kernel using Spatial. The explicit use of `Foreach`, `Reduce`, SRAM, and parameterization enables high performance and efficient resource usage, while the all-in-one design minimizes latency and off-chip bandwidth. The main tradeoff is increased kernel complexity and resource usage, but for the target application (real-time ASR/NLP), this is justified.

---

## General Design Principles & Tradeoffs (Spatial Code Focus)
- **Explicit Data Movement:** We use explicit `load` and `store` operations between DRAM and SRAM, making data movement clear and allowing us to optimize for memory bandwidth and locality. This is preferable to implicit streaming, as it gives us more control over performance. We considered using implicit streaming or higher-level abstractions, but explicit control is better for debugging and performance tuning.
- **SRAM/DRAM Separation:** By separating on-chip and off-chip memories, we can tune buffer sizes and access patterns for each kernel, maximizing throughput and minimizing contention. We could have used a unified memory model, but explicit separation gives us more control and visibility.
- **Foreach and Reduce:** We favor `Foreach` for parallel, elementwise, or nested operations, and `Reduce` for accumulations. This makes the parallelism explicit and allows Spatial to generate efficient hardware structures. We could have used only sequential loops, but that would have underutilized the hardware.
- **Parameterization:** Kernels are parameterized by runtime arguments and compile-time constants, leveraging Spatial's staged programming model for flexibility and efficiency. Hardcoding parameters would have made the code less reusable and harder to maintain.
- **mux and Register Use:** We use `mux` for conditional logic and explicit registers for intermediate values, ensuring all control and dataflow is synthesizable and predictable. Using Scala's `if-else` or global variables would have made the hardware less predictable and harder to optimize.
- **Pipelining and Unrolling:** By using explicit loops and buffers, we can experiment with different pipelining and unrolling strategies to balance resource usage and performance. Full unrolling maximizes throughput but uses more area; partial unrolling or pipelining provides a good tradeoff. We could have left this to the compiler, but explicit control gives us more predictable results.
- **Memory Banking:** Explicit use of SRAM allows us to control memory banking, which is critical for avoiding access conflicts and maximizing throughput. Implicit memory allocation could have resulted in bank conflicts and lower performance.
- **Debugging and Verification:** The explicit, low-level style of Spatial code makes it easier to insert debug prints, probes, and checks, which is important for verifying correctness and matching software reference outputs.
- **Hardware/Software Boundary:** We pushed as much computation as possible into hardware to minimize host intervention and maximize determinism, but left some global reductions and configuration in software for simplicity and resource savings. For example, reductions over large arrays (e.g., max) are done in software and passed as arguments, rather than using a hardware `Reduce`, to save area and compilation time. We could have done everything in hardware, but this would have increased area usage and complexity.
- **System Integration:** The explicit dataflow and parameterization make it easier to integrate the hardware kernels into a larger system, as the interfaces and dependencies are clear and well-defined. Implicit or black-box kernels would have made integration and debugging more difficult.

## Flaws, Failures, and Lessons Learned

While the hardware pipeline achieves high throughput and modularity, several flaws and limitations were encountered during development:

- **End-to-End Pipeline Integration Issues:** Despite having all the individual components (DSP, ASR, NLP), getting the full DSP → ASR → NLP pipeline to run smoothly end-to-end proved challenging. The handoff between stages is brittle, with each stage expecting specific file formats, shapes, and timing. If any intermediate output is missing, malformed, or misinterpreted (e.g., due to shape ambiguity in CSVs), the entire pipeline can break down. There is no robust mechanism for verifying or adapting to the actual data produced by the previous stage, making the system fragile and hard to debug.
- **Resource Usage and Scalability:** Some kernels, especially the fused MelLogScaleKernel, use significant on-chip memory and logic resources. This can limit scalability to larger models or longer audio sequences, and may require retuning or partitioning for different FPGA targets.
- **Pipeline Bottlenecks:** The all-in-one approach of MelLogScaleKernel minimizes off-chip traffic but can create a single point of failure or bottleneck. If one stage in the fused kernel is suboptimal, it can limit the performance of the entire pipeline.
- **Debugging Complexity:** While explicit dataflow aids debugging, the complexity of fused kernels makes it harder to isolate and fix bugs in individual stages. Intermediate results are less accessible compared to a multi-kernel approach.
- **Flexibility vs. Efficiency:** Fusing multiple DSP stages into one kernel maximizes efficiency but reduces flexibility. If only one stage needs to be updated or replaced, the entire kernel must be regenerated and revalidated.
- **Host/Hardware Coordination:** Some reductions and configuration are offloaded to the host for simplicity, but this increases the risk of mismatches between host and hardware, and can complicate integration and testing.
- **Floating-Point vs. Fixed-Point:** The use of floating-point arithmetic (e.g., for log and scaling) simplifies development but may not be optimal for all FPGAs. A fixed-point implementation could be more resource-efficient but would require more careful tuning and validation.
- **CSV I/O Overhead:** While CSV files make debugging and integration easy, they are not the most efficient format for high-throughput or real-time systems. For production, a binary or streaming interface would be preferable.
- **Single-Column CSVs and Shape Ambiguity:** Many intermediate files (e.g., `stft_imag_output.csv`) are written as single-column CSVs, representing flattened vectors. If the expected 2D shape (e.g., [n_bins, n_frames]) is not clearly documented or passed along, downstream stages may misinterpret the data, leading to shape mismatches, errors, or silent failures. This can make the pipeline brittle and hard to debug, especially when integrating new kernels or changing input sizes.
- **Toolchain and Build Issues:** Spatial and FPGA toolchains can be brittle, with long build times and occasional synthesis or mapping failures. This slows iteration and can make debugging hardware-specific issues challenging.

**Lessons Learned:**
- Modular, explicit Spatial code is easier to debug and maintain, but there is a tradeoff between efficiency and flexibility.
- Early profiling and resource estimation are critical to avoid late-stage surprises with FPGA resource usage.
- Maintaining clear, testable interfaces between kernels and between host and hardware is essential for robust system integration.
- For future work, consider more parameterized, reconfigurable kernels and more efficient data movement strategies for production deployment.
- Most importantly, robust end-to-end integration and error handling are essential for a reliable pipeline. Future designs should prioritize clear data contracts, automated shape and format checking, and support for streaming or in-memory data transfer to enable true DSP → ASR → NLP workflows.

---