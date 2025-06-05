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

For our EE109 final project, we implemented a hardware-accelerated audio preprocessing pipeline designed to support real-time speech recognition. The system focuses on the frontend of modern speech transcription stacks, which typically begin by transforming raw audio into log-Mel spectrograms.

Our goal was to offload this preprocessing to an FPGA to reduce latency, improve power efficiency, and enable deployment in edge devices. We modularized the digital signal processing (DSP) stack using Spatial DSL and validated our hardware output against a software implementation. The completed pipeline integrates with Whisper for automatic speech recognition (ASR) and a lightweight NLP model for downstream summarization [1](https://doi.org/10.3390/electronics11121831).

This work demonstrates the feasibility of domain-specific acceleration for speech-driven AI applications in resource-constrained or privacy-sensitive environments.

### Real-World Applications

- **On-Device Voice Assistants**: Enables real-time command recognition without cloud dependency.
- **Wearables & Medical Devices**: Supports accessibility tools like hearing aids and speech-to-text interfaces.
- **Edge AI & IoT**: Enables low-power speech interfaces for embedded systems, drones, and remote sensors.

---

## System Architecture & Pipeline

Our system is divided into three main stages:

1. **DSP Frontend (Hardware)**
2. **ASR using Whisper (Software)**
3. **NLP Summarization (Software)**

Below is a diagram of our full pipeline, with the hardware-accelerated blocks implemented in Spatial:

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

The Python pipeline integrated seamlessly with Whisper and a lightweight NLP summarizer. This allowed us to test the full speech-to-summary path and measure accuracy and latency at each step.

---

## Hardware Design & Components

The DSP frontend was broken into six hardware kernels:

- `QuantizeKernel`
- `STFTKernel` (Hann window + 400-point FFT)
- `PowerSpectrum`
- `MelFilterbank`
- `LogCompress`
- `WhisperScale`

We used Spatial DSL to implement and simulate each kernel, with modular SRAM/DRAM memory interfaces and `Foreach`/`Reduce` controllers for performance. Each kernel was tested independently before chaining them together.

We used lookup tables (LUTs) for log compression and normalized outputs using a fixed Whisper scale factor. The entire frontend produces log-Mel spectrograms formatted for Whisper.

---

## Testing & Validation

To verify correctness, we wrote unit tests comparing hardware kernel outputs to the Python baseline. Most results matched within <1% error, with small deviations due to float vs. fixed-point rounding.

We validated each kernel in isolation, then verified the chained pipeline end-to-end. Output spectrograms were visually inspected and numerically compared to the software version.

Whisper successfully accepted hardware-generated spectrograms, producing accurate transcriptions, which confirmed that the precision and format of our hardware outputs met the ASR model’s expectations.

---

## Results, Limitations & Future Work

### Results

- Full DSP pipeline functional in Spatial simulation
- Outputs closely matched Python reference (<1% error)
- Whisper ASR performed well on hardware-processed inputs
- Modular hardware kernels enable extensibility and reuse

### Limitations

- No deployment on a physical FPGA board (simulation only)
- Only tested with static `.wav` files (no live mic input)
- Log compression used float-based approximations (no fixed-point tuning)

### Future Work

- Deploy on FPGA (e.g., AWS F1 or Xilinx board)
- Add streaming audio support via microphone input
- Optimize precision and resource usage for synthesis
- Explore accelerating ASR or NLP stages in hardware

---

## Appendix

All source code is available at: 

## References

J. Park, H. Noh, H. Nam, W.-C. Lee, and H.-J. Park, "A Low-Latency Streaming On-Device Automatic Speech Recognition System Using a CNN Acoustic Model on FPGA and a Language Model on Smartphone," Electronics, vol. 11, no. 12, p. 1831, 2022. https://doi.org/10.3390/electronics11121831

Y.-K. Choi, K. You, and W. Sung, "FPGA-Based Implementation of a Real-Time 5000-Word Continuous Speech Recognizer," EUSIPCO 2008. https://www.eurasip.org/Proceedings/Eusipco/Eusipco2008/papers/1569101650.pdf

M. Lee et al., "FPGA-Based Low-Power Speech Recognition with Recurrent Neural Networks," arXiv preprint arXiv:1610.00552, 2016. https://arxiv.org/abs/1610.00552

S. Han et al., "ESE: Efficient Speech Recognition Engine with Sparse LSTM on FPGA," arXiv preprint arXiv:1612.00694, 2016. https://arxiv.org/abs/1612.00694


