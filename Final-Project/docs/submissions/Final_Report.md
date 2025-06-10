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

---

## System Architecture & Pipeline

Our system is divided into three main stages:

1. **DSP Frontend (Hardware)**
2. **ASR using Whisper (Software)**
3. **NLP Summarization (Software)**

### PDSP Breakfown

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
    │                      │
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

### MelLogScaleKernel (Top-level)

**Purpose:**  
Chains Mel filtering, log compression, dynamic range clamping, and Whisper scaling into a single kernel for efficient batch processing of audio frames.

**Block Diagram:**
```
[Power Matrix DRAM] + [Mel Filterbank DRAM] → [SRAMs] → [Mel Filtering] → [Log10/Clamp/Scale] → [SRAM] → [Output DRAM]
```

**Implementation Details:**
- **Input/Output:**  
  - Reads a flat power matrix and Mel filterbank from DRAM.
  - Outputs Whisper-scaled log-Mel spectrograms to DRAM/CSV.
- **Algorithm:**  
  - For each frame: load power spectrum, apply Mel filterbank, compute log10, clamp dynamic range, apply Whisper scaling, store output.
- **Spatial Features:**  
  - Uses on-chip `SRAM` for all intermediate buffers.
  - Loops are pipelined over frames and Mel bands.
  - Handles runtime frame count and memory management.
- **Precision/Resource Tradeoffs:**  
  - Uses `Float` for all arithmetic; could be further optimized with `FixPt`.
- **Debugging:**  
  - Debug prints for runtime sizes, output CSV for validation.

**Bottlenecks & Solutions:**
- **Bottleneck:** Memory bandwidth and on-chip buffer size for large audio clips.
- **Solution:** Batching, pipelined loops, and careful memory management.

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

## Results, Limitations & Future Work

### Results

| Metric         | Software (Python) | Hardware (FPGA/Spatial) |
|----------------|-------------------|-------------------------|
| Accuracy       | <1% error vs gold | <1% error vs gold       |
| ASR WER        | ~0.05-0.16        | ~0.05-0.16              |
| Latency        | [fill in]         | [fill in, from reports] |
| Resource Usage | N/A               | RAM36: 27, see reports  |

- Hardware pipeline matches software within <1% error.
- ASR and NLP work on hardware-generated features.

### Limitations

- No deployment on physical FPGA (simulation only).
- Only static `.wav` files tested (no live streaming).
- Log compression uses float-based approximations; fixed-point tuning could improve further.

### Future Work

- Deploy on real FPGA (e.g., AWS F1, Xilinx).
- Add streaming audio/microphone support.
- Optimize precision and resource usage for synthesis.
- Explore hardware acceleration for ASR/NLP stages.

---

## Appendix

- **Code:**  
  - Python DSP: `src/audiolib/dsp/`  
  - Hardware kernels: `fpga/`  
  - Tests: `