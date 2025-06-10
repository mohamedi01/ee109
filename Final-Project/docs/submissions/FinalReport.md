# EE109 Digital System Lab Final Report  
Will Briger, Mohamed Ismail, Chris Gutierrez

# Table of Contents  
- Application Overview  
- Software Simulation  
- Hardware Implementation  
- Design Tradeoffs  
- Appendix  

## Application Overview

Modern speech recognition systems rely on robust, low-latency digital signal processing (DSP) frontends to convert raw audio into features suitable for neural network inference. The log-Mel spectrogram is a standard input for state-of-the-art models like Whisper. However, real-time DSP on edge devices is challenging due to compute and power constraints.

**Our goal:**  
Offload the entire log-Mel spectrogram pipeline to an FPGA, enabling real-time, low-power speech preprocessing for edge AI. We modularized the DSP stack in both software (Python) and hardware (Spatial/FPGA), validated correctness, and analyzed performance and resource tradeoffs.

**Key challenges addressed:**  
We faced three primary challenges: matching the precise behavior of Whisper’s reference DSP pipeline, maintaining high accuracy with fixed-point hardware implementations, and ensuring efficient use of FPGA resources. These informed both our software modularization and our hardware design choices.

**Applications:**  
This work has practical implications for applications like smart voice assistants, medical wearables, and embedded speech interfaces in IoT devices, all of which benefit from low-latency, low-power DSP.
  
**GitHub Repository:**  
The code of the project can be found at the following [GitHub Repo](https://github.com/mohamedi01/ee109/tree/main/Final-Project). The `README.md` and `docs/architecture.md` files describe how to set up the requirements, run the different parts of the code, and outline the high-level architecture.

### Project Directory Structure

| Directory/File   | Description                                                     |
|------------------|-----------------------------------------------------------------|
| `src/`           | All Python source code for DSP, ASR, and NLP pipelines          |
| `fpga/`          | Hardware kernel implementations (Spatial DSL), reports, and logs |
| `tests/`         | Python test scripts for unit and end-to-end validation          |
| `data/`          | Audio files, intermediate CSVs, and reference transcripts       |
| `docs/`          | Project documentation and final report                          |
| `README.md`      | Project overview and setup instructions                         |

---

## Software Simulation

We first implemented a complete Python version of the pipeline using NumPy and librosa. This software reference served as our gold standard for correctness.

Each DSP stage (quantization, STFT, power spectrum, Mel filtering, log compression, and scaling) was implemented in Python and validated by visualizing outputs. These results were used to verify the accuracy of hardware kernels during simulation.

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

All Python code for the project can be found in the `src/` or `tests/` directory of the repository.

### Evolution of the Software Reference: From `mel.py` to `mel_gold.py`

#### Initial Approach: Monolithic Pipeline in `mel.py`

Our first implementation of the DSP pipeline was in a single Python script, `mel.py`. This script encapsulated the entire log-Mel spectrogram computation—from raw audio loading through quantization, STFT, power spectrum, Mel filtering, log compression, and Whisper scaling—in a single, linear function. The primary goal at this stage was to quickly validate the end-to-end pipeline and ensure that our software output matched Whisper's expectations.

- **Testing Structure:**  
  - Basic tests compared final log-Mel spectrogram output to reference outputs.  
  - Focused on end-to-end correctness, not individual stages.  
- **Limitations:**  
  - Difficult to isolate errors to a specific stage.  
  - Insufficient for hardware validation, which required per-stage test vectors.

#### Need for Modularization

As hardware development progressed, a monolithic software pipeline proved insufficient. Each hardware kernel needed isolated verification against a software gold reference. This drove the need to refactor the software into modular functions for each DSP stage.

#### Refactoring: Creation of `mel_gold.py`

To address these needs, we refactored the pipeline into a new, modular script: `mel_gold.py`.

- **Design:**  
  - Each DSP stage implemented as a standalone function (e.g., `quantize_int16`, `compute_stft`, `compute_power_spectrum`, `apply_mel_filterbank`, `whisper_scale`).  
  - Functions accept and return NumPy arrays or PyTorch tensors for easy composition and testing.  
- **Testing Framework:**  
  - Python scripts generate input CSVs for hardware kernels.  
  - After simulation, output CSVs are parsed and compared to reference outputs with tight numerical tolerances.  
  - Detailed error messages and visualizations aid debugging.

**Impact:**  

Modularizing the software pipeline significantly improved debugging efficiency by allowing us to isolate and identify mismatches at specific stages of the DSP flow. This modular structure enabled a streamlined co-design workflow in which each hardware kernel was developed alongside and validated against its corresponding Python function. By ensuring stage-level correctness throughout, we maintained high fidelity between the hardware and the software reference implementations.
---

## Hardware Implementation

### System Architecture & Pipeline

Our system is divided into three main stages:

1. **DSP Frontend (Hardware)**  
2. **ASR using Whisper (Software)**  
3. **NLP Summarization (Software)**  

#### DSP Breakdown

| Stage           | Description                                 | Parameters/Notes                |
|-----------------|---------------------------------------------|---------------------------------|
| Quantization    | Simulate int16 PCM quantization             | Scale, clip, cast, rescale      |
| STFT            | 25 ms Hann window, 400-pt FFT, hop 10 ms     | n_fft=400, hop=160, center=True |
| Power Spectrum  | Magnitude squared of FFT output             |                                 |
| Mel Filterbank  | 80 bands, 0-8 kHz, HTK scale                | n_mels=80, f_min=0, f_max=8kHz  |
| Log Compression | log10, clamp to min=1e-10, dynamic range    | Whisper: max(x, max-8.0)        |
| Whisper Scale   | (x + 4) / 4                                 |                                 |

**Data Flow:**  
- Audio is loaded, resampled to 16 kHz, quantized, and processed through each stage.  
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
│                      │
└────────┬─────────────┘
         ↓
┌──────────────────────┐
│    Final Summary     │
└──────────────────────┘
```
---

### Hardware Design & Components

The DSP frontend was broken into six hardware kernels, each implemented in Spatial DSL and designed for modularity, throughput, and resource efficiency. Below, we provide a deep technical dive into each kernel, including block diagrams, algorithmic details, memory and pipelining strategies, and design tradeoffs.

**Code Locations for Hardware Design:**  
- Each DSP kernel in its own folder under `fpga/` (e.g., `fpga/QuantizeKernel/`, `fpga/STFTKernel/`, etc.).  
- Main Spatial DSL implementations: `fpga/spatial_dsp.py` (multi-frame/batched) and `fpga/spatial_dsp_single.py` (single-frame).  
- Input/output CSVs in `Final-Project/data/`.  
- Resource and timing reports in each kernel’s `reports/` subfolder.

#### Design and Validation Workflow: Single-Frame Kernels, CSV I/O, and Kernel Integration

Early in hardware development, we encountered timing and simulation bottlenecks processing many audio frames. Simulating the full pipeline for long clips was slow. We adopted a single-frame kernel design:

- **Benefits:**  
The benefits of this approach were substantial, including a significant reduction in simulation time, which dropped from hours to mere seconds per test. Additionally, the modular design allowed for isolated debugging of each stage, making it easier to pinpoint issues and fix them efficiently. The approach also ensured high accuracy, as we were able to directly compare the hardware implementation to the Python reference, verifying correctness at every stage of the pipeline.

**CSV-Based I/O:**  
- Python scripts generate input CSVs per kernel using `mel_gold.py`.  
- After simulation, Python parses output CSVs and compares to reference.  
- Automated test scripts report error metrics (mean abs error, max error, relative error).

**Per-Stage Validation:**  
- Floating-point kernels match Python reference within <1% relative error (often much lower).  
- Used `np.allclose` with `rtol=1e-4` for tight tolerances.

---

#### QuantizeKernel

**Purpose:**  
Implements fixed-point quantization of floating-point audio samples to simulate 16-bit PCM.

**Block Diagram:**
```
[Input DRAM (Float)] → [SRAM (Float)] → [Scale/Clip/Cast] → [SRAM (Float)] → [Output DRAM (Float)]
```

**Implementation Details:**  
- **I/O:** Reads up to 1 M samples from DRAM (CSV), outputs quantized samples as float.  
- **Algorithm:** Scale by 32767, clip to [-32768, 32767], cast to int16, rescale to [-1, 1].  
- **Spatial Features:** On-chip SRAM buffering, pipelined `Foreach` loops, runtime size from config.  
- **Precision Tradeoffs:** Float used for simplicity; true fixed-point (`FixPt`) possible.  

**Bottlenecks & Solutions:**  
- **DRAM bandwidth:** Mitigated with SRAM buffering and pipelined loops.

---

#### STFTKernel

**Purpose:**  
Computes the STFT of windowed audio frames, producing real and imaginary bins.

**Block Diagram:**
```
[Input DRAM (Frame)] + [Hann Window DRAM] → [SRAMs] → [Windowing] → [DFT (Nested Foreach/Reduce)] → [SRAMs] → [Output DRAMs (Real, Imag)]
```

**Implementation Details:**  
- **I/O:** Reads frame & window from CSV, outputs real/imag bins to DRAM.  
- **Algorithm:** Apply Hann window, compute DFT (400-point) via nested `Foreach` and `Reduce`, scale amplitude.  
- **Spatial Features:** SRAM buffering, pipelined loops, configurable FFT size.  
- **Precision Tradeoffs:** Uses `Double` for precision; `Float`/`FixPt` possible.  

**Bottlenecks & Solutions:**  
- **Compute-intensive DFT:** Pipelined loops; FFT algorithms could further optimize.

---

#### PowerSpectrum

**Purpose:**  
Computes power spectrum (magnitude squared) from FFT outputs.

**Block Diagram:**
```
[Real DRAM] + [Imag DRAM] → [SRAMs] → [Elementwise Square/Add] → [SRAM] → [Output DRAM]
```

**Implementation Details:**  
- **I/O:** Reads real/imag bins from DRAM, outputs power spectrum.  
- **Algorithm:** `power = real² + imag²`.  
- **Spatial Features:** SRAM buffering, fully pipelined elementwise loop.  
- **Tradeoffs:** Float arithmetic; `FixPt` possible.  

**Bottlenecks & Solutions:**  
- **Memory bandwidth:** On-chip SRAM and pipelined loops.

---

#### MelFilterbank

**Purpose:**  
Applies an 80-band Mel filterbank, producing Mel-frequency energies.

**Block Diagram:**
```
[Mel Filterbank DRAM (80×201)] + [Power Spectrum DRAM (201)] → [SRAMs] → [Matrix-Vector Multiply] → [SRAM] → [Output DRAM]
```
**Implementation Details:**  
- **I/O:** Reads filterbank matrix and power vector, outputs Mel energies.  
- **Algorithm:** Dot product per Mel band.  
- **Spatial Features:** SRAM buffering, nested pipelined loops.  
- **Tradeoffs:** Float vs `FixPt`.  

**Bottlenecks & Solutions:**  
- **Memory-bound multiply:** SRAM buffering, pipelined loops.

---

#### LogCompress

**Purpose:**  
Applies log10 compression and dynamic range clamping using a LUT.

**Block Diagram:**
```
[Input DRAM] + [LUT DRAMs] → [SRAMs] → [Log10 via LUT/Interp] → [Dynamic Range Clamp] → [SRAM] → [Output DRAM]
```

**Implementation Details:**  
- **I/O:** Reads Mel energies & LUTs, outputs log-compressed values.  
- **Algorithm:** abs → clamp min → normalize to [1,10) → LUT + linear interp → add decade → clamp (max-8.0).  
- **Spatial Features:** SRAM buffering, pipelined loops.  
- **Tradeoffs:** LUT vs compute for log10.  

**Bottlenecks & Solutions:**  
- **Log10 expensive:** LUT + interpolation.

---

#### WhisperScale

**Purpose:**  
Applies Whisper's final scaling: (x + 4) / 4.

**Block Diagram:**
```
[Input DRAM] → [SRAM] → [Affine Transform] → [SRAM] → [Output DRAM]
```

**Implementation Details:**
- **I/O:** Reads log-compressed values, outputs scaled values.  
- **Algorithm:** `out = (in + 4) / 4`.  
- **Spatial Features:** SRAM buffering, pipelined loop.  
- **Tradeoffs:** Float vs `FixPt`.  

**Bottlenecks & Solutions:**  
- None; simple affine transform.

---

### Testing & Validation

To verify correctness, we wrote unit tests comparing hardware kernel outputs to the Python baseline. Most results matched within <1% error, with small deviations due to float vs fixed-point rounding.

**Unit Tests:**  
- Each hardware kernel output is compared using `np.testing.assert_allclose` (rtol=1e-4).

**End-to-End Tests:**  
We validated our design through rigorous unit tests comparing hardware kernel outputs to their Python equivalents, ensuring <1% relative error. End-to-end evaluations using real audio inputs confirmed compatibility with Whisper ASR, yielding WERs as low as 0.05 for short sentences and 0.17 with long sentences.

**NLP Validation:**  
- Summarization and keyword extraction consistency checks.

**Sources of Error:**  
- Differences due to float vs fixed-point, quantization, and rounding.

Whisper accepted hardware-generated spectrograms, confirming precision and format met ASR requirements.

---

#### Attempting Kernel Integration

After validating each stage in isolation, we attempted to combine multiple kernels into larger, batched hardware modules (e.g., chaining Mel filtering, log compression, and scaling). The goals were improved simulation efficiency and closer alignment with deployment.

**Challenges:**  
While we successfully validated individual kernels, integrating them introduced new challenges, especially with timing and fixed-point precision across stages. Debugging became more complex, leading us to reserve integrated kernels for final validation runs rather than development.

**Summary:**  
- Single-frame, per-stage approach was essential for rapid iteration and precise error analysis.  
- Integrated, fixed-point pipelines were reserved for final validation and resource/timing analysis.

---

#### MelLogScaleKernel (Top-level)

**Purpose:**  
Chains Mel filtering, log compression, dynamic range clamping, and Whisper scaling into one kernel for batch processing.

**Motivation:**  
This kernel was designed to reduce intermediate DRAM and SRAM transfers by chaining multiple stages into a single module. By doing so, we were able to better exploit data locality and pipelining opportunities, which are critical for achieving efficient hardware utilization. These design choices also aligned the architecture more closely with the demands of real-time audio streaming deployment.

**Architectural Design:**  
1. Load power matrix and Mel filterbank from DRAM.  
2. For each frame:  
   - Apply Mel filterbank (matrix-vector multiply).  
   - Log10 via LUT + interpolation.  
   - Clamp to [max-8.0, max].  
   - Whisper scale `(x+4)/4`.  
3. Write outputs back to DRAM/CSV.

**Pipelining & Memory:**  
The kernel employs pipelined loops over both audio frames and Mel frequency bands to maximize throughput. All intermediate data is stored in on-chip SRAM, minimizing latency and external memory access. Additionally, the design supports parameterized batch sizes and Mel band counts, offering flexibility for different deployment scenarios and hardware constraints.

**LUT-Based Log10:**  
- Normalize input to [1,10), LUT + linear interp, add integer decade.

**Dynamic Range & Scaling:**  
- Clamp after log10, then affine scale.

**CSV I/O:**  
- Flattened power matrices and filterbanks as input CSVs.  
- Output CSVs contain one row per frame spectrogram.

**Validation & Error Analysis:**  
- Floating-point error <1% (rtol=1e-4).  
- Fixed-point errors higher; required careful tuning.

**Challenges:**  
We encountered several challenges during development, including the need for careful precision management when working with fixed-point widths to prevent overflow and maintain accuracy. Simulating large batches proved to be slow and resource-intensive, which limited our ability to test full-length audio inputs efficiently. To address these issues, we implemented additional debug instrumentation within the integrated pipeline to better trace errors and monitor intermediate values across stages.

**Lessons Learned:**  

We adopted a strategy of using modular kernels during development to facilitate isolated testing and rapid iteration, while reserving integrated kernels for final validation of the full pipeline. This approach allowed us to debug individual components efficiently without the complexity of cross-stage interactions. Looking ahead, future designs should aim to balance this modularity with deeper integration to optimize for deployment performance without sacrificing maintainability.
---

#### Potential for Optimization and Parallelization

With further MelLogScaleKernel improvements:

- **Frame-Level Parallelism:** Process multiple frames concurrently.  
- **Mel Band Parallelism:** Independent computations per band.  
- **Resource Tradeoffs:** More SRAM, LUTs, DSP blocks.  
- **Memory Access:** Burst transfers, double-buffering.

These would enable real-time, streaming audio DSP on FPGA for edge AI.

---

#### Architectural Enhancements for Full Pipeline Support

- **Multi-Frame/Streaming:** Robust buffering, variable batching.  
- **Stateful Processing:** Overlap-add buffers, running stats.

#### Run-Time Optimization Strategies

- **Deep Pipelining:** One frame per clock.  
- **Parallel Frame/Band Processing**  
- **Double-Buffering & Burst DRAM**  
- **Resource Scheduling**

#### Real-Time and Edge Deployment Impact

- **Low Latency:** Microsecond-level frame latencies.  
- **Scalability:** Configurable design for various FPGA sizes.  
- **Power vs Complexity:** Clock gating, resource sharing.

#### Summary

A fully optimized pipeline would deliver end-to-end real-time DSP for ASR on resource-constrained devices.

---

## Design Tradeoffs

### Results, Limitations & Future Work

#### Results

*Software latency measured on a modern CPU; hardware latency is from simulation, not real FPGA.*

- **Per-Stage Error:** Hardware kernel outputs matched Python reference within <1% relative error (`rtol=1e-4`).  
- **End-to-End Error:** Full hardware pipeline produced spectrograms matching software within <1% error for the first frame but could not process entire files due to frame restrictions.  
- **Scalability:** Resource usage scales with batch size, Mel band count, and parallelism depth.

#### Limitations

- Integrated kernel not fully functional for entire audio pipelines; only single-frame kernels validated.  
- End-to-end hardware deployment limited to first-frame processing.  
- No physical FPGA deployment (simulation only).  
- Only static `.wav` files tested; no live streaming.  
- Log compression uses float-based approximations; fixed-point tuning remains.

This limitation highlights the need for further development and debugging of the integrated kernel to enable robust, real-time, end-to-end speech processing on hardware.

---

## Appendix

- **Code:**  
  - Python DSP: `src/audiolib/dsp/`  
  - Hardware kernels: `fpga/`  
  - Tests: `src/audiolib/`, `tests/test_*/`  
- **Presentation:** [Final Presentation](https://docs.google.com/presentation/d/1r-FnH7z8U7ozwVBeGQ0wLh01cEzEG6EEDA1MYnHPUiU/edit?usp=sharing)  
