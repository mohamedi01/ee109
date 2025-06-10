# EE109 Digital Systems Final Report

Will Briger, Mohamed Ismail, Chris Gutierrez

[Final Presentation](https://docs.google.com/presentation/d/1r-FnH7z8U7ozwVBeGQ0wLh01cEzEG6EEDA1MYnHPUiU/edit?usp=sharing)

## Table of Contents

- Project Overview & Motivation
- System Architecture & Pipeline
- Software Reference Implementation
- Hardware Design & Components
- Testing & Validation
- Results, Limitations & Future Work
- Appendix

---

## Project Overview & Motivation

Modern speech recognition systems rely on robust, low-latency digital signal processing (DSP) frontends to convert raw audio into features suitable for neural network inference. The log-Mel spectrogram is a standard input for state-of-the-art models like Whisper. However, real-time DSP on edge devices is challenging due to compute and power constraints.

**Our goal:**  
Offload the entire log-Mel spectrogram pipeline to an FPGA, enabling real-time, low-power speech preprocessing for edge AI. We modularized the DSP stack in both software (Python) and hardware (Spatial/FPGA), validated correctness, and analyzed performance and resource tradeoffs.

**Key challenges addressed:**
- Matching Whisper's reference pipeline (quantization, STFT, Mel, log, scaling)
- Achieving high accuracy with fixed-point hardware
- Efficient memory and compute resource usage on FPGA

**Applications:**  
- On-device voice assistants  
- Medical/wearable devices  
- Edge AI/IoT speech interfaces

**GitHub Repository:** 
The code of the project can be found at the following [Github Repo](https://github.com/your-repo-link-here). The `README.md` and `docs\architecture.md` files have a description on how to set up the requirements, run the different parts of the code, and the high level architecture of the files. 

## Project Directory Structure

| Directory/File         | Description                                                      |
|-----------------------|------------------------------------------------------------------|
| `src/`                | All Python source code for DSP, ASR, and NLP pipelines           |
| `fpga/`               | Hardware kernel implementations (Spatial DSL), reports, and logs  |
| `tests/`              | Python test scripts for unit and end-to-end validation            |
| `data/`               | Audio files, intermediate CSVs, and reference transcripts         |
| `docs/`               | Project documentation and final report                            |
| `README.md`           | Project overview and setup instructions                           |

---

---

## System Architecture & Pipeline

Our system is divided into three main stages:

1. **DSP Frontend (Hardware)**
2. **ASR using Whisper (Software)**
3. **NLP Summarization (Software)**

### 
DSP Breakfown

| Stage           | Description                                 | Parameters/Notes                |
|-----------------|---------------------------------------------|---------------------------------|
| Quantization    | Simulate int16 PCM quantization             | Scale, clip, cast, rescale      |
| STFT            | 25ms Hann window, 400-pt FFT, hop 10ms      | n_fft=400, hop=160, center=True |
| Power Spectrum  | Magnitude squared of FFT output             |                                 |
| Mel Filterbank  | 80 bands, 0-8kHz, HTK scale                 | n_mels=80, f_min=0, f_max=8kHz  |
| Log Compression | log10, clamp to min=1e-10, dynamic range    | Whisper: max(x, max-8.0)        |
| Whisper Scale   | (x + 4) / 4                                 |                                 |

**Data Flow:**
- Audio is loaded, resampled to 16kHz, quantized, and processed through each stage.
- Hardware and software pipelines exchange data via CSV files and DRAM buffers.

**Diagram:**
```
            ┌────────────────────┐
            │     Audio File     │
            └────────┬───────────┘
                     ↓
      ┌───────────────────────────────┐
      │     DSP Frontend (FPGA)       │
      │ ┌────────────┐                │
      │ │ Quantize   │                │
      │ └────┬───────┘                │
      │      ↓                        │
      │ ┌────────────┐                │
      │ │ Hann Window│                │
      │ └────┬───────┘                │
      │      ↓                        │
      │ ┌────────────┐                │
      │ │    FFT     │ (400-point)    │
      │ └────┬───────┘                │
      │      ↓                        │
      │ ┌────────────┐                │
      │ │ Power Spec │                │
      │ └────┬───────┘                │
      │      ↓                        │
      │ ┌────────────┐                │
      │ │  Mel Filter│ (80 bands)     │
      │ └────┬───────┘                │
      │      ↓                        │
      │ ┌────────────┐                │
      │ │   Log10    │                │
      │ └────┬───────┘                │
      │      ↓                        │
      │ ┌────────────┐                │
      │ │WhisperScale│                │
      │ └────────────┘                │
      └────────┬──────────────────────┘
               ↓
    ┌──────────────────────┐
    │ Whisper ASR (Python) │
    └────────┬─────────────┘
             ↓
    ┌──────────────────────┐
    │  NLP Summarizer      │
    └────────┬─────────────┘
             ↓
    ┌──────────────────────┐
    │    Final Summary     │
    └──────────────────────┘
```

---

## Software Reference Implementation

We first implemented a complete Python version of the pipeline using NumPy and librosa. This software reference served as our gold standard for correctness.

Each DSP stage—quantization, STFT, power spectrum, Mel filtering, log compression, and scaling—was implemented in Python and validated by visualizing outputs. These results were used to verify the accuracy of hardware kernels during simulation.

**Key Implementation Details:**
- **Quantization:** Simulates PCM S16LE by scaling, clipping, casting to int16, and rescaling to float32. See `quantize_int16(x_np: np.ndarray)` in `mel_gold.py`.
- **STFT:** Uses `torch.stft` with Hann window, 400-pt FFT, hop=160, center=True. See `compute_stft(x: torch.Tensor)`.
- **Power Spectrum:** Magnitude squared of STFT output. See `compute_power_spectrum(stft: torch.Tensor)`.
- **Mel Filterbank:** 80-band filterbank, constructed via `librosa` or manual fallback. See `apply_mel_filterbank(power_spectrum, ...)`.
- **Log Compression & Whisper Scaling:** Clamp to min=1e-10, log10, dynamic range compression, then scale. See `whisper_scale(mel_db: np.ndarray)`.

**Validation:**
- Unit tests compare each stage's output to hardware (see `tests/test_pipeline/`).
- Outputs match within <1% error for all stages.
- End-to-end tests use Word Error Rate (WER) for ASR accuracy.

The Python pipeline integrated seamlessly with Whisper and a lightweight NLP summarizer. This allowed us to test the full speech-to-summary path and measure accuracy and latency at each step.

All Python code for the project can be found in the `src/` or `tests/` directory of the repository.

### Evolution of the Software Reference: From `mel.py` to `mel_gold.py`

#### Initial Approach: Monolithic Pipeline in `mel.py`

Our first implementation of the DSP pipeline was in a single Python script, `mel.py`. This script encapsulated the entire log-Mel spectrogram computation—from raw audio loading through quantization, STFT, power spectrum, Mel filtering, log compression, and Whisper scaling—in a single, linear function. The primary goal at this stage was to quickly validate the end-to-end pipeline and ensure that our software output matched Whisper's expectations.

- **Testing Structure:**  
  - We wrote basic tests that compared the final log-Mel spectrogram output to reference outputs.
  - The focus was on end-to-end correctness, not on the behavior of individual stages.
  - This approach was sufficient for initial validation, but it had limitations:
    - **Debugging:** If a mismatch occurred, it was difficult to isolate which stage was responsible.
    - **Hardware Validation:** As we began developing hardware kernels for each DSP stage, we needed a way to compare intermediate outputs, not just the final result.

#### Need for Modularization

As hardware development progressed, it became clear that a monolithic software pipeline was insufficient for rigorous validation. Each hardware kernel (Quantize, STFT, Power Spectrum, Mel Filterbank, Log Compression, Whisper Scale) needed to be tested in isolation against a software "golden" reference. This required the ability to run and compare each stage independently.

- **Motivation:**
  - **Isolated Testing:** To verify the correctness of each hardware kernel, we needed to generate reference outputs for every intermediate stage.
  - **Debugging:** Modular functions made it easier to pinpoint and fix discrepancies.
  - **Reusability:** Modular code could be reused in unit tests and for generating test vectors for hardware simulation.

#### Refactoring: Creation of `mel_gold.py`

To address these needs, we refactored the pipeline into a new, modular script: `mel_gold.py`.

- **Design:**
  - Each DSP stage was implemented as a standalone function (e.g., `quantize_int16`, `compute_stft`, `compute_power_spectrum`, `apply_mel_filterbank`, `whisper_scale`).
  - Functions were designed to accept and return NumPy arrays or PyTorch tensors, making them easy to compose and test.
  - The script could be run end-to-end or stage-by-stage, enabling flexible testing.

- **Testing Structure:**
  - We built a Python testing framework (see `tests/test_pipeline/`) that:
    - Runs each stage independently.
    - Compares hardware kernel outputs to the corresponding software reference using tight numerical tolerances.
    - Provides detailed error messages and visualizations for debugging.
  - This modular approach enabled us to:
    - Validate each hardware kernel in isolation.
    - Rapidly iterate on hardware designs, knowing that any mismatch could be traced to a specific stage.

#### Impact

- **Debugging Efficiency:**  
  Modularization dramatically reduced debugging time. When a hardware kernel failed a test, we could immediately compare its output to the software reference for that stage, rather than sifting through the entire pipeline.
- **Hardware-Software Co-Design:**  
  The modular software reference became the "golden" standard for hardware development. Each hardware kernel was developed and validated against its corresponding Python function, ensuring correctness at every step.

**In summary:**  
Our transition from a monolithic script (`mel.py`) to a modular, function-based reference (`mel_gold.py`) was crucial for robust hardware validation. This modularization enabled precise, stage-by-stage testing, streamlined debugging, and ensured that each hardware kernel could be rigorously compared to a trusted software "golden" output.

---

## Hardware Design & Components

The DSP frontend was broken into six hardware kernels, each implemented in Spatial DSL and designed for modularity, throughput, and resource efficiency. Below, we provide a deep technical dive into each kernel, including block diagrams, algorithmic details, memory and pipelining strategies, and design tradeoffs.

**Code Locations for Hardware Design:**
- Each hardware DSP kernel is implemented in its own folder under `fpga/` (e.g., `fpga/QuantizeKernel/`, `fpga/STFTKernel/`, etc.), containing kernel-specific code, reports, and logs.
- The main Spatial DSL implementations for the kernels are in `fpga/spatial_dsp.py` (multi-frame/batched kernels) and `fpga/spatial_dsp_single.py` (single-frame kernels for rapid testing).
- Input and output data for hardware simulation are exchanged via CSV files, typically located in `Final-Project/data/` and its subdirectories.
- Hardware resource and timing reports are found in each kernel's `reports/` subfolder (e.g., `fpga/QuantizeKernel/reports/`).

### Design and Validation Workflow: Single-Frame Kernels, CSV I/O, and Kernel Integration

#### Simulation Bottlenecks and the Single-Frame Approach

Early in hardware development, we encountered significant timing and simulation bottlenecks when attempting to process large numbers of audio frames through the full DSP pipeline. Simulating the entire pipeline for long audio clips (hundreds or thousands of frames) was prohibitively slow in the Spatial simulator, especially when using fixed-point arithmetic and large on-chip buffers. This made rapid iteration and debugging nearly impossible.

To address this, we adopted a single-frame kernel design for each DSP stage. By focusing on processing one frame at a time, we could:
- Dramatically reduce simulation time (from hours to seconds per test)
- Isolate and debug each stage independently
- Achieve high accuracy and tight error bounds by directly comparing hardware output to the Python reference for the same frame

#### CSV-Based I/O and Python Integration

Each hardware kernel was designed to read its input from a CSV file and write its output to a new CSV file. This approach enabled seamless integration with the Python reference pipeline:
- Python scripts generate input CSVs for each kernel, using the modular reference functions (see `mel_gold.py`)
- After hardware simulation, Python scripts parse the output CSVs and compare them to the expected reference outputs
- This workflow allowed for automated, per-stage validation and error analysis

Python modifications included:
- Utility functions to format NumPy arrays as CSVs with precise float formatting and correct shape (e.g., 1D for frames, 2D for spectrograms)
- Parsing functions to read hardware output CSVs and reshape them for direct comparison
- Automated test scripts to run the full suite of per-stage comparisons and report error metrics (e.g., mean absolute error, max error, relative error)

#### Per-Stage Validation and Error Rates

This modular, CSV-driven workflow enabled us to achieve very low error rates at each stage:
- For floating-point kernels, outputs typically matched the Python reference within <1% relative error (often much lower)
- Tight tolerances (e.g., `np.allclose` with `rtol=1e-4`) were used to ensure hardware correctness
- Debugging was highly efficient: any mismatch could be traced to a specific kernel and input frame

### QuantizeKernel

**Purpose:**  
Implements fixed-point quantization of floating-point audio samples to simulate 16-bit PCM (Pulse Code Modulation) as used in real-world audio codecs.

**Block Diagram:**
```
[Input DRAM (Float)] → [SRAM (Float)] → [Scale/Clip/Cast] → [SRAM (Float)] → [Output DRAM (Float)]
```

**Implementation Details:**
- **Input/Output:**  
  - Reads up to 1M samples from DRAM, loaded from a CSV file.
  - Outputs quantized samples as float (to match downstream expectations), but with int16 quantization applied.
- **Algorithm:**  
  - Each sample is scaled by 32767, clipped to [-32768, 32767], cast to int16, then rescaled to [-1, 1] as float.
- **Spatial Features:**  
  - Uses `SRAM` for fast on-chip buffering.
  - Loops are pipelined with `Foreach` for throughput.
  - Runtime size is read from a config file, allowing flexible batch sizes.
- **Precision/Resource Tradeoffs:**  
  - All arithmetic is performed in float, but the quantization step simulates fixed-point.
  - Could be further optimized by using true fixed-point types (e.g., `FixPt`), but float is used for simplicity and compatibility.
- **Debugging:**  
  - Output is written to CSV for comparison with Python reference.
  - Configurable runtime size allows for easy unit testing.

**Bottlenecks & Solutions:**
- **Bottleneck:** DRAM bandwidth for large audio files.
- **Solution:** On-chip SRAM buffering and pipelined loop for high throughput.

---

### STFTKernel

**Purpose:**  
Computes the Short-Time Fourier Transform (STFT) of windowed audio frames, producing real and imaginary frequency bins for each frame.

**Block Diagram:**
```
[Input DRAM (Frame)] + [Hann Window DRAM] → [SRAMs] → [Windowing] → [DFT (Nested Foreach/Reduce)] → [SRAMs] → [Output DRAMs (Real, Imag)]
```

**Implementation Details:**
- **Input/Output:**  
  - Reads a windowed frame and Hann window from DRAM (CSV).
  - Outputs real and imaginary FFT bins to DRAM.
- **Algorithm:**  
  - Applies Hann window to input frame.
  - Computes DFT for each frequency bin using nested `Foreach` and `Reduce` (400-pt FFT, but supports up to 1024).
  - Scales output to match Python amplitude.
- **Spatial Features:**  
  - Uses on-chip `SRAM` for input, window, and output buffers.
  - Loops are pipelined for throughput.
  - Runtime FFT size is configurable via a config file.
- **Precision/Resource Tradeoffs:**  
  - Uses `Double` for high precision, but could be optimized to `Float` or `FixPt` for resource savings.
- **Debugging:**  
  - Stores windowed input to DRAM for comparison with Python.
  - Outputs are written to CSV for validation.

**Bottlenecks & Solutions:**
- **Bottleneck:** DFT is compute-intensive for large N.
- **Solution:** Pipelined loops; could be further optimized with FFT algorithms.

---

### PowerSpectrum

**Purpose:**  
Computes the power spectrum (magnitude squared) from real and imaginary FFT outputs.

**Block Diagram:**
```
[Real DRAM] + [Imag DRAM] → [SRAMs] → [Elementwise Square/Add] → [SRAM] → [Output DRAM]
```

**Implementation Details:**
- **Input/Output:**  
  - Reads real and imaginary FFT bins from DRAM.
  - Outputs power spectrum to DRAM.
- **Algorithm:**  
  - For each bin: power = real² + imag².
- **Spatial Features:**  
  - Uses on-chip `SRAM` for input and output.
  - Fully pipelined elementwise operation.
- **Precision/Resource Tradeoffs:**  
  - Uses `Float` for all arithmetic; could use `FixPt` for further optimization.
- **Debugging:**  
  - Outputs written to CSV for validation.

**Bottlenecks & Solutions:**
- **Bottleneck:** Memory bandwidth for large bin counts.
- **Solution:** On-chip SRAM and pipelined elementwise loop.

---

### MelFilterbank

**Purpose:**  
Applies an 80-band Mel filterbank to the power spectrum, producing Mel-frequency energies.

**Block Diagram:**
```
[Mel Filterbank DRAM (80x201)] + [Power Spectrum DRAM (201)] → [SRAMs] → [Matrix-Vector Multiply] → [SRAM] → [Output DRAM]
```

**Implementation Details:**
- **Input/Output:**  
  - Reads Mel filterbank matrix and power spectrum vector from DRAM.
  - Outputs Mel energies to DRAM.
- **Algorithm:**  
  - For each Mel band, computes dot product with power spectrum.
- **Spatial Features:**  
  - Uses on-chip `SRAM` for matrix, vector, and output.
  - Double loop over bands and bins, pipelined for throughput.
- **Precision/Resource Tradeoffs:**  
  - Uses `Float` for all arithmetic; could use `FixPt` for further optimization.
- **Debugging:**  
  - Outputs written to CSV for validation.

**Bottlenecks & Solutions:**
- **Bottleneck:** Matrix-vector multiply is memory-bound for large filterbanks.
- **Solution:** On-chip SRAM and pipelined nested loops.

---

### LogCompress

**Purpose:**  
Applies log10 compression and dynamic range clamping to Mel energies, using a LUT for efficient log computation.

**Block Diagram:**
```
[Input DRAM] + [LUT DRAMs] → [SRAMs] → [Log10 via LUT/Interp] → [Dynamic Range Clamp] → [SRAM] → [Output DRAM]
```

**Implementation Details:**
- **Input/Output:**  
  - Reads Mel energies and LUTs from DRAM.
  - Outputs log-compressed, clamped values to DRAM.
- **Algorithm:**  
  - For each value: take abs, clamp to min, normalize to [1,10), compute log10 via LUT and linear interpolation, add decade, clamp to (max-8.0).
- **Spatial Features:**  
  - Uses on-chip `SRAM` for input, LUTs, and output.
  - Pipelined loop for throughput.
- **Precision/Resource Tradeoffs:**  
  - Uses `Float` for all arithmetic; LUT-based log10 is a tradeoff between accuracy and resource usage.
- **Debugging:**  
  - Outputs both raw and clamped log values to CSV for validation.

**Bottlenecks & Solutions:**
- **Bottleneck:** Log10 is expensive in hardware.
- **Solution:** LUT with linear interpolation for fast, resource-efficient log10.

---

### WhisperScale

**Purpose:**  
Applies Whisper's final scaling: (x + 4) / 4, to match the input range expected by the ASR model.

**Block Diagram:**
```
[Input DRAM] → [SRAM] → [Affine Transform] → [SRAM] → [Output DRAM]
```

**Implementation Details:**
- **Input/Output:**  
  - Reads log-compressed values from DRAM.
  - Outputs scaled values to DRAM.
- **Algorithm:**  
  - For each value: out = (in + 4) / 4.
- **Spatial Features:**  
  - Uses on-chip `SRAM` for input and output.
  - Fully pipelined, single loop.
- **Precision/Resource Tradeoffs:**  
  - Uses `Float` for all arithmetic; could use `FixPt` for further optimization.
- **Debugging:**  
  - Outputs written to CSV for validation.

**Bottlenecks & Solutions:**
- **Bottleneck:** None; simple affine transform is not compute-bound.
- **Solution:** Fully pipelined loop.

---

## Testing & Validation

To verify correctness, we wrote unit tests comparing hardware kernel outputs to the Python baseline. Most results matched within <1% error, with small deviations due to float vs. fixed-point rounding.

**Unit Tests:**
- Each hardware kernel output is compared to the Python reference using `np.testing.assert_allclose` with tight tolerances (e.g., rtol=1e-4).

**End-to-End Tests:**
- Full pipeline tested on real audio (see `tests/test_pipeline/test_long_sentence_pipeline.py`).
- ASR output compared to ground truth using WER (Word Error Rate).
- Example:  
  - Tolerance for long sentences: WER ≤ 0.16  
  - Short sentences: WER ≤ 0.05

**NLP Validation:**
- Summarization and keyword extraction run on both ASR output and ground truth for consistency.

**Sources of Error:**
- Minor differences due to float vs fixed-point, quantization, and rounding.

We validated each kernel in isolation, then verified the chained pipeline end-to-end. Output spectrograms were visually inspected and numerically compared to the software version.

Whisper successfully accepted hardware-generated spectrograms, producing accurate transcriptions, which confirmed that the precision and format of our hardware outputs met the ASR model's expectations.

---

#### Attempting Kernel Integration and Fixed-Point Challenges

After validating each stage in isolation, we attempted to combine multiple kernels into larger, batched hardware modules (e.g., chaining Mel filtering, log compression, and scaling in a single kernel). The goal was to improve simulation efficiency and more closely match the intended hardware deployment.

However, this integration introduced new challenges:
- Simulation speed again became a bottleneck, especially when using fixed-point arithmetic and large input sizes
- Debugging was more difficult, as errors could propagate across multiple stages, making it harder to localize issues
- Achieving the same low error rates as the single-frame, per-stage approach proved challenging, particularly for fixed-point implementations where quantization and rounding errors accumulated

In practice, running the full pipeline with fixed-point arithmetic and large input sizes was often too slow to be practical for iterative development. As a result, most validation and tuning was performed using the single-frame, floating-point kernels, with only limited end-to-end testing of the integrated, fixed-point pipeline.

**Summary:**
- The single-frame, modular kernel approach—using CSV I/O and tight Python integration—was essential for rapid, accurate hardware validation
- Per-stage testing enabled us to achieve and verify very low error rates for each DSP component
- While kernel integration is necessary for deployment, simulation and debugging constraints made it difficult to fully validate large, fixed-point pipelines within project timelines

### MelLogScaleKernel (Top-level)

**Purpose:**  
Chains Mel filtering, log compression, dynamic range clamping, and Whisper scaling into a single kernel for efficient batch processing of audio frames. This kernel was designed to more closely mirror the end-to-end DSP pipeline required for real-time ASR, reducing memory transfers and improving hardware efficiency.

**Motivation:**
- Integrating multiple DSP stages into a single kernel reduces the need for intermediate memory transfers between DRAM and on-chip SRAM, which is a major bottleneck in hardware pipelines.
- By processing entire batches of frames in one invocation, the kernel can exploit data locality and pipelining, improving throughput and making the design more suitable for real-time, low-latency applications.
- This approach also aligns with the deployment scenario, where the FPGA is expected to process continuous streams of audio data efficiently.

**Architectural Design:**
- **Input/Output:**
  - The kernel reads a flat power matrix (shape: [num_frames, num_bins]) and the Mel filterbank matrix ([num_mels, num_bins]) from DRAM.
  - Outputs a batch of Whisper-scaled log-Mel spectrograms ([num_frames, num_mels]) to DRAM or CSV.
- **Processing Flow:**
  1. For each frame, load the power spectrum from DRAM into on-chip SRAM.
  2. Apply the Mel filterbank via a matrix-vector multiply (dot product for each Mel band).
  3. For each Mel energy, apply log10 compression using a LUT (lookup table) with linear interpolation for efficiency.
  4. Clamp the log-compressed value to the dynamic range expected by Whisper (max-8.0 to max).
  5. Apply Whisper scaling: (x + 4) / 4.
  6. Store the result in output SRAM, then write back to DRAM/CSV.
- **Pipelining:**
  - Loops are pipelined over frames and Mel bands to maximize throughput.
  - All intermediate results are kept in on-chip SRAM to minimize DRAM access latency.
- **Memory Management:**
  - The kernel is parameterized for batch size and Mel band count, allowing flexible testing and deployment.
  - Handles runtime frame count and memory management for variable-length audio.

**LUT-Based Log10 Implementation:**
- Logarithm computation is expensive in hardware, so a LUT is used to approximate log10 values.
- The input is normalized to [1, 10), and the LUT provides log10 values for this range.
- Linear interpolation between LUT entries improves accuracy.
- The decade (integer part of the log) is computed separately and added to the interpolated value.
- This approach balances resource usage and computational accuracy.

**Dynamic Range Clamping and Scaling:**
- After log10 compression, values are clamped to the range [max-8.0, max] to match Whisper's dynamic range requirements.
- The final scaling step, (x + 4) / 4, normalizes the output to the range expected by the ASR model.

**CSV I/O for Batch Processing:**
- Input CSVs contain flattened power matrices for all frames in a batch, as well as the Mel filterbank matrix.
- Output CSVs contain the resulting log-Mel spectrograms, one row per frame.
- Python scripts handle the formatting and parsing of these CSVs, ensuring compatibility between hardware and software reference outputs.

**Validation Methodology and Error Analysis:**
- For floating-point arithmetic, the kernel's output was compared to the Python reference pipeline using tight tolerances (e.g., rtol=1e-4).
- Error rates were typically <1% relative error, but could increase for longer pipelines or larger batch sizes due to accumulated rounding errors.
- For fixed-point arithmetic, error rates increased further, especially when quantization effects compounded across multiple stages. Careful tuning of fixed-point widths and scaling factors was required to balance resource usage and accuracy.
- Debugging was more complex than in the single-stage approach, as errors could propagate across the integrated pipeline.

**Challenges Encountered:**
- **Precision Management:** Selecting appropriate fixed-point widths and scaling factors for each stage was critical to avoid overflow, underflow, and excessive quantization error.
- **Simulation Speed:** Simulating the integrated kernel with large input sizes and fixed-point arithmetic was extremely slow, limiting the ability to iterate quickly.
- **Debugging:** With multiple stages integrated, localizing the source of errors required additional instrumentation and intermediate output dumps.
- **Resource Utilization:** The integrated kernel required more on-chip SRAM and LUT resources, necessitating careful design tradeoffs.

**Lessons Learned and Implications:**
- While the MelLogScaleKernel demonstrated the feasibility of integrating multiple DSP stages for hardware efficiency, the complexity of debugging and simulation speed constraints made it impractical as the primary development workflow.
- The single-frame, per-stage approach remained essential for rapid iteration and precise error analysis, while the integrated kernel was reserved for final, end-to-end validation and resource/timing analysis.
- Future hardware designs should consider hybrid approaches: modular kernels for development and debugging, with integrated kernels for deployment and performance evaluation.

**Potential for Optimization and Parallelization:**

With further improvements to the MelLogScaleKernel, there is significant potential for optimization and parallelization. A more advanced implementation could leverage deeper pipelining and parallel processing at multiple levels:
- **Frame-Level Parallelism:** Multiple audio frames could be processed simultaneously, with each frame handled by a separate processing element or pipeline stage. This would dramatically increase throughput, especially for batch processing or streaming applications.
- **Mel Band Parallelism:** Within each frame, the computation of Mel filterbank outputs and subsequent log/scale operations for different Mel bands could be parallelized, as these operations are independent. This would reduce per-frame latency and make the kernel more suitable for real-time, low-latency systems.
- **Hardware Resource Tradeoffs:** Such parallelization would require more on-chip SRAM, LUTs, and DSP blocks, so careful resource management and scheduling would be necessary to fit within FPGA constraints.
- **Optimized Memory Access:** Improved memory access patterns, such as burst reads/writes and double-buffering, could further reduce DRAM bottlenecks and enable continuous data streaming.

If these optimizations were implemented, the MelLogScaleKernel could serve as a highly efficient, real-time DSP frontend for ASR systems, capable of meeting the demands of edge AI and embedded applications. This would enable true streaming audio support and further reduce end-to-end system latency.

To achieve a fully functioning, real-time hardware pipeline capable of processing entire audio files or continuous streams, several architectural improvements and optimizations would be required:

#### Architectural Enhancements for Full Pipeline Support
- **Multi-Frame and Streaming Support:**
  - The integrated kernel must be redesigned to handle batches of frames or continuous audio streams, rather than single-frame processing.
  - This involves implementing robust memory management for input and output buffers, as well as support for variable-length audio and dynamic batching.
- **Stateful Processing:**
  - For streaming audio, the hardware must maintain state across frames (e.g., overlap-add buffers for STFT, running normalization statistics) to ensure seamless, real-time operation.

#### Run-Time Optimization Strategies
- **Deep Pipelining:**
  - All DSP stages (quantization, STFT, Mel filtering, log compression, scaling) should be pipelined so that each stage processes a new frame every clock cycle, maximizing throughput.
- **Parallel Frame and Band Processing:**
  - Multiple frames and/or Mel bands can be processed in parallel using additional processing elements, reducing per-frame latency and increasing overall throughput.
- **Double-Buffering and Burst Memory Access:**
  - Implementing double-buffered SRAMs and burst DRAM transfers allows computation and memory access to overlap, minimizing idle cycles and maximizing data throughput.
- **Resource Allocation and Scheduling:**
  - Careful allocation of FPGA resources (SRAM, LUTs, DSPs) and scheduling of operations is required to balance parallelism, pipelining depth, and hardware constraints.
- **Fixed-Point Optimization:**
  - Tuning fixed-point widths and scaling factors for each stage can reduce resource usage and power consumption while maintaining accuracy.

#### Real-Time and Edge Deployment Impact
- **Low-Latency Operation:**
  - With these optimizations, the hardware pipeline could achieve frame latencies on the order of microseconds to milliseconds, suitable for real-time speech recognition and edge AI applications.
- **Scalability:**
  - The design could be scaled up or down depending on available FPGA resources, supporting a range of deployment scenarios from embedded devices to high-throughput servers.
- **Power and Complexity Tradeoffs:**
  - Increased parallelism and pipelining improve performance but also increase resource usage and design complexity. Power-efficient design techniques (e.g., clock gating, resource sharing) would be important for battery-powered or embedded deployments.

#### Summary
A fully optimized hardware pipeline would combine deep pipelining, parallel processing, and efficient memory management to deliver real-time, end-to-end speech feature extraction. These improvements would enable deployment in latency-sensitive, resource-constrained environments, unlocking the full potential of FPGA-based speech preprocessing for modern ASR systems.

## Results, Limitations & Future Work

### Results


*Software latency measured on a modern CPU; hardware latency is from simulation, not real FPGA.

#### Accuracy
- **Per-Stage Error:** Each hardware kernel output was compared to the Python reference using `np.allclose` with `rtol=1e-4`. Most single frame test stages achieved <1% relative error, with hardware matching software almost exactly.
- **End-to-End Error:** The full hardware pipeline produced log-Mel spectrograms that matched the software reference within <1% error, as measured by mean absolute and relative error across all frames and Mel bands. This only function in the pipeline for the first frame, accurately getting the correct first word, but not being able to transcribe the entire audio file due to the frame restrictions.
- **Scalability:** Resource usage scales with batch size, Mel band count, and degree of parallelism. Optimizations such as pipelining and parallel frame/band processing could increase resource requirements but also improve throughput.

### Limitations

- The combined (integrated) kernel is not yet fully functional for processing entire audio pipelines; only the single-frame kernels have been thoroughly validated and achieve high accuracy.
- As a result, end-to-end hardware deployment is currently limited to single-frame or first-frame processing, preventing full real-time transcription of long audio files on hardware.
- No deployment on physical FPGA (simulation only).
- Only static `.wav` files tested (no live streaming).
- Log compression uses float-based approximations; fixed-point tuning could improve further.

This limitation highlights the need for further development and debugging of the integrated kernel to enable robust, real-time, end-to-end speech processing on hardware.

---

## Appendix

- **Code:**  
  - Python DSP: `src/audiolib/dsp/`  
  - Hardware kernels: `fpga/`  
  - Tests: `src/audiolib/`, `tests/test_*/`
