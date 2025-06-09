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

### Pipeline Stages and Parameters

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

---

## Hardware Design & Components

The DSP frontend was broken into six hardware kernels:

- `QuantizeKernel`: Fixed-point arithmetic, pipelined for throughput.
- `STFTKernel`: Implements windowing and FFT (400-pt), uses pipelined/parallelized loops.
- `PowerSpectrum`: Computes magnitude squared, optimized for memory access.
- `MelFilterbank`: Applies 80-band filterbank, uses SRAM for filter coefficients.
- `LogCompress`: Uses LUTs for log10, pipelined for speed.  
  *Resource usage example (from reports):*  
  - RAM36: 27  
  - outSram: 11 RAM36 blocks
- `WhisperScale`: Applies scaling and clamping in hardware.

**Data Types:**
- Most kernels use fixed-point (e.g., FixPt[TRUE, 5, 27]) for precision and resource efficiency.

**Parallelization:**
- Foreach and Reduce controllers used for pipelining and parallel execution.
- Memory banking and streaming for high throughput.

**Resource Utilization:**
- See `fpga/LogCompress/reports/LogCompressCORDIC/Memories.report` for detailed memory usage.
- Example: outSram uses 11 RAM36 blocks.

**Synthesis/Simulation:**
- All kernels tested in simulation; timing and resource reports generated.
- Example: LogCompressCORDIC Retime.report details pipelining and control structure.

We used Spatial DSL to implement and simulate each kernel, with modular SRAM/DRAM memory interfaces and `Foreach`/`Reduce` controllers for performance. Each kernel was tested independently before chaining them together.

We used lookup tables (LUTs) for log compression and normalized outputs using a fixed Whisper scale factor. The entire frontend produces log-Mel spectrograms formatted for Whisper.

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
  - Tests: `tests/`
- **Reports:**  
  - Hardware resource/timing: `fpga/[kernel]/reports/`
- **Data:**  
  - Audio: `data/`  
  - Transcripts: `data/transcripts/`
- **References:**  
  - [1] J. Park et al., "A Low-Latency Streaming On-Device Automatic Speech Recognition System Using a CNN Acoustic Model on FPGA and a Language Model on Smartphone," Electronics, 2022.  
  - [2] Y.-K. Choi et al., "FPGA-Based Implementation of a Real-Time 5000-Word Continuous Speech Recognizer," EUSIPCO 2008.  
  - [3] M. Lee et al., "FPGA-Based Low-Power Speech Recognition with Recurrent Neural Networks," arXiv:1610.00552, 2016.  
  - [4] S. Han et al., "ESE: Efficient Speech Recognition Engine with Sparse LSTM on FPGA," arXiv:1612.00694, 2016.


