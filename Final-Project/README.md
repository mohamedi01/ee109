# EE109 Final Project: Audio Transcription and Analysis Pipeline

For our EE109 final project, we are implementing a Python-based audio transcription and analysis system. It features a custom Digital Signal Processing (DSP) frontend to extract log-Mel spectrograms, an Automatic Speech Recognition (ASR) stage using OpenAI's Whisper API, and a Natural Language Processing (NLP) module for text summarization and analysis (keyword and topic identification).

## Core Components & Workflow

The system processes input audio in three main stages, orchestrated by a central pipeline:

1.  **Digital Signal Processing (DSP) Frontend (`audiolib.dsp.mel`)**
    *   The `wav_to_logmel` takes an audio file (various formats like WAV, FLAC, MP3 supported) or raw audio data as input.
    *   **Key Processing Steps:**
        1.  **Loading & Resampling**: Audio is loaded and, if necessary, resampled to 16 kHz (Whisper's standard).
        2.  **Quantization Simulation**: Audio is scaled to the int16 range, clipped, cast to `int16`, then cast back to `float32` and rescaled (divided by 32768.0). This step mimics the behavior of Whisper's reference audio loading, which often involves `ffmpeg` that performs such a conversion, ensuring compatibility.
        3.  **STFT**: A Short-Time Fourier Transform is applied using a 25 ms Hann window with a 10 ms hop length and a 400-point FFT.
        4.  **Mel Filterbank**: An 80-band Mel filterbank is applied to the power spectrum, covering frequencies from 0 to 8 kHz.
        5.  **Logarithmic Compression & Scaling**: The Mel power spectrum is logarithmically compressed (`log10(clamp(power, min=1e-10))`), subjected to dynamic range compression, and finally scaled using a Whisper-specific formula `(x + 4) / 4`.
    *   **Output**: An 80-channel log-Mel spectrogram (`numpy.ndarray` of `float32`) ready for input to a Whisper ASR model.

2.  **Automatic Speech Recognition (ASR) (`audiolib.asr`)**
    *   **Feature Transcription (`whisper_features.py`)**: The `transcribe_features` function takes a log-Mel spectrogram (as a NumPy array or PyTorch tensor) and transcribes it to text.
        *   It utilizes a pre-trained Whisper model (defaulting to `openai/whisper-base.en` via the `transformers` library).
        *   Input spectrograms are padded or cropped to match the Whisper model's expected input length (30 seconds / 3000 frames).
        *   Includes dynamic decoding options (e.g., adjusting `num_beams`, `length_penalty`, `max_new_tokens`) for very short audio segments to potentially improve accuracy.
    *   **Audio File Transcription (`transcribe.py`)**: The `transcribe_audio_file` function (exposed via `audiolib.asr.__init__.py`) orchestrates the ASR process for an entire audio file:
        *   It first uses `wav_to_logmel` from the DSP module to convert the input audio file into a log-Mel spectrogram.
        *   For audio clips longer than 30 seconds, it implements a sliding window mechanism. It processes the audio in 30-second chunks with a 3-second overlap on each side. The transcribed text from these overlapping segments is then intelligently merged using a custom `_merge` function that handles duplicated words at the segment boundaries.
        *   For shorter clips (<= 30s), the audio is processed directly.
        *   Finally, it passes the resulting Mel spectrogram(s) to `transcribe_features` for actual speech-to-text conversion.
    *   **Output**: A string containing the transcribed text

3.  **Natural Language Processing (NLP) (`audiolib.nlp.nlp`)**
    *   The `analyze_text` function serves as the primary interface for NLP tasks. It takes transcribed text as input.
    *   **Key Capabilities:**
        1.  **Keyword Spotting (`classify_keywords`)**: Identifies a primary keyword or keyphrase from the text along with a confidence score.
        2.  **Topic Identification (`classify_topic`)**: Determines the main topic of the text, also providing a confidence score.
        3.  **Summarization (`summarize_text`)**: Generates a concise summary of the input text (defaulting to `sshleifer/distilbart-cnn-12-6` for summarization).
    *   Models for classification (keyword/topic) are loaded from specified paths (`models/keyword_spotter`, `models/topic_segmenter`). If these paths are not found or models are invalid, it gracefully falls back to dummy classifiers that return `_dummy_label`.
    *   The module leverages the Hugging Face `transformers` library for model loading and inference.
    *   **Output**: A dictionary containing the identified keyword (label, confidence), topic (label, confidence), and a summary string.

## Pipeline Integration (`audiolib.pipeline`)

The `process_audio_to_nlp` function in `audiolib.pipeline.py` combines these components workflow:

1.  It accepts an `audio_path` (and an optional `device` for computation).
2.  **ASR Stage**: It calls `transcribe_audio_file` (from `audiolib.asr`) to perform both the DSP frontend processing (via `wav_to_logmel` internally) and the subsequent speech-to-text conversion. This yields the full transcript of the audio.
3.  **NLP Stage**: The obtained transcript is then passed to `analyze_text` (from `audiolib.nlp`) for keyword spotting, topic identification, and summarization.
4.  **Output**: The function returns a dictionary containing the `"transcript"` and a nested dictionary under `"nlp_analysis"` which holds the results from the NLP module (keyword, topic, summary).

## FPGA Acceleration (In Progress - Milestone 2 Focus)

As part of the project, key DSP frontend components are being implemented in Spatial for FPGA acceleration. The goal is to offload these computationally intensive tasks to an FPGA for improved performance and efficiency.

*   **Location:** `Final-Project/fpga/`
*   **Kernels Implemented in Spatial:**
    *   `QuantizeKernel.scala`
    *   `PowerSpectrum.scala`
    *   `MelFilterbank.scala`
    *   `LogCompress.scala`
    *   `WhisperScale.scala`
    *   `STFTKernel.scala` (Hann windowing implemented; FFT component is a work in progress)
*   **Testing:** Each Spatial kernel has a corresponding test (`*Test.scala`) in `Final-Project/fpga/src/test/scala/spatial/tests/`.
    *   **Current Status (as of May 28, 2025):**
        *   `MelFilterbankTest`, `PowerSpectrumTest`, `QuantizeKernelTest` are passing functional simulations.
        *   `LogCompressTest` and `WhisperScaleTest` have been implemented and are undergoing final verification.
        *   `STFTKernelTest` is pending the completion of the FFT implementation within `STFTKernel.scala`.

## Main Features

*   **Custom DSP Frontend**: Converts audio (WAV, FLAC, MP3, etc.) to Whisper-compatible log-Mel spectrograms (`audiolib.dsp.mel.wav_to_logmel`).
*   **Whisper-based ASR**: Transcribes speech to text using `openai/whisper-base.en` (`audiolib.asr`). Handles both short and long audio (with segment merging).
*   **NLP Analysis**: Performs keyword spotting, topic identification, and summarization on transcribed text (`audiolib.nlp.nlp.analyze_text`).
*   **Integrated End-to-End Pipeline**: `audiolib.pipeline.process_audio_to_nlp` provides a single interface for DSP, ASR, and NLP.
*   **Comprehensive Testing Suite**: Utilizes `pytest` for unit, integration, and pipeline tests, with WER as a key ASR metric.
*   **Spatial DSP Kernels for FPGA Acceleration**: Implementations of key DSP tasks in Spatial, with ongoing testing and development.
*   **Complete FPGA DSP Frontend:**
    *   Finalize and verify all individual Spatial DSP kernels, including the FFT implementation within `STFTKernel.scala` and its corresponding test.
    *   Implement pre-emphasis filter in Spatial if deemed necessary.
    *   Integrate the individual Spatial DSP kernels into a single, cohesive hardware pipeline.
    *   Thoroughly test the integrated Spatial DSP pipeline against the golden Python implementation.
*   **FPGA Integration with Host:** Develop the mechanisms (e.g., using DMA and AXI-Lite on an AWS F1 instance) to stream data to/from the FPGA and control the accelerated DSP pipeline from the Python host application.
*   **Performance Evaluation:** Measure end-to-end latency, throughput, and resource utilization of the FPGA-accelerated pipeline.

## Data Sources

*   **Homophone List**: For text normalization during Word Error Rate (WER) calculation, this project utilizes a list of English homophones sourced from the `pimentel/homophones` GitHub repository. The specific file used is `homophones.csv`, available at [https://raw.githubusercontent.com/pimentel/homophones/master/homophones.csv](https://raw.githubusercontent.com/pimentel/homophones/master/homophones.csv). This data is stored locally in `data/homophones.csv` and contains slight modifications that include digit-to-word mappings (e.g., "1" -> "one").
* Note: Add data sources for the audio files 

## Project Structure
For a more detailed view of the architecture, please see [docs/architecture.md](docs/architecture.md).

**Core Library**: `src/audiolib/` contains the main modules:
*   `dsp/`: Digital Signal Processing.
*   `asr/`: Automatic Speech Recognition.
*   `nlp/`: Natural Language Processing.
*   `pipeline.py`: Integrates the above modules.
*   `testutility/`: Utilities for testing, like text normalization.

## Setup and Installation

1.  **Create and activate a virtual environment**
    ```bash
    python -m venv ee109_final_project
    source ee109_final_project/bin/activate
    # On Windows:
    # ee109_final_project\\Scripts\\activate
    ```

2.  **Install the package in editable mode (recommended for development):**
    ```bash
    pip install -e .
    ```
    This will install your package and all dependencies listed in `pyproject.toml` or `setup.py`.

3.  **(If you only want dependencies, or for legacy support):**
    ```bash
    pip install -r requirements.txt
    ```

> **Note:**  
> Installing with `pip install -e .` means any changes you make to the source code will be immediately reflected when you run your scripts, without needing to reinstall the package.

## Development

- To install in editable mode for development, use:
    ```bash
    pip install -e .
    ```
- To run tests:
    ```bash
    pytest
    ```
- To lint/format code (if you use tools like `black` or `flake8`):
    ```bash
    black src/
    flake8 src/
    ```

## Usage

To process an audio file through the full DSP-ASR-NLP pipeline (on the computer), follow these steps:
    ```

1.  **Run the Example Script**: Execute the `run_pipeline.py` script as a module, providing the path to your audio file as a command-line argument. The script itself is at `src/audiolib/run_pipeline.py`.
    ```bash
    python -m src.audiolib.run_pipeline path/to/your/audiofile.wav
    ```
    For example:
    ```bash
    python -m src.audiolib.run_pipeline data/short_sentences/harvard_f.wav
    ```
    Or for a long sentence:
    ```bash
    python -m src.audiolib.run_pipeline data/long_sentences/bird.mp3
    ```

    The script will output the transcribed text and the NLP analysis (keyword, topic, summary).

## Running Tests

The project uses `pytest` for thorough testing of its components and the integrated pipeline. The tests are organized into subdirectories within `tests/`:

*   `tests/test_DSP/`: Contains tests specifically for the Digital Signal Processing frontend (`wav_to_logmel`). These verify the characteristics of the generated Mel spectrograms (e.g., shape, data type, value ranges) across different types of audio inputs (single words, short sentences, long sentences).
*   `tests/test_DSP_to_ASR/`: Focuses on testing the ASR module when fed with spectrograms from the custom DSP frontend. These tests typically compare the transcription output against ground truth text using Word Error Rate (WER) and may also compare against a baseline Whisper model.
*   `tests/test_NLP/`: Includes tests for the Natural Language Processing module. This involves directly testing functions like `analyze_text` with predefined text inputs to verify keyword, topic, and summary outputs. It may also include performance tests or evaluations of the NLP models if applicable.
*   `tests/test_pipeline/`: Contains end-to-end integration tests for the full `process_audio_to_nlp` pipeline. These tests run on actual audio files, process them through all stages (DSP -> ASR -> NLP), and then check the final transcript and NLP analysis results against expected outcomes or ground truth data.

1.  **Run all tests:**
    From the project root directory (`Final-Project/`):
    ```bash
    pytest
    ```

2.  **Run specific test files or tests with more verbose output:**
    ```bash
    pytest -s tests/test_DSP/test_dsp_single_words.py
    pytest -s tests/test_DSP/test_dsp_short_sentences.py
    pytest -s tests/test_DSP_to_ASR/test_asr_short_sentences.py
    pytest -s tests/test_DSP_to_ASR/test_asr_single_words.py
    pytest -s tests/test_DSP_to_ASR/test_asr_long_sentences.py
    pytest -s tests/test_NLP/test_nlp.py
    pytest -s tests/test_NLP/test_nlp_on_short_sentence_transcripts.py
    pytest -s tests/test_NLP/test_nlp_on_long_sentence_transcripts.py
    pytest -s tests/test_pipeline/test_short_sentence_pipeline.py
    pytest -s tests/test_pipeline/test_long_sentence_pipeline.py
    ```
    The `-s` flag shows output from `print()` statements


*   **Hardware Acceleration of DSP Frontend using FPGA (Amazon F1 Instance):**
    *   **Objective:** Offload computationally intensive DSP kernels from the `wav_to_logmel` pipeline to an FPGA on an Amazon EC2 F1 instance to achieve significant performance gains and power efficiency for real-time or batch processing scenarios.
    *   **Target Kernels for Hardware Implementation:**
        *   Resampling (if input audio is not 16 kHz).
        *   Short-Time Fourier Transform (STFT): Including windowing (Hann) and the 400-point FFT.
        *   Power Spectrum Calculation.
        *   Mel Filterbank Application: Dot products for 80 Mel bands.
        *   Logarithmic Compression and Whisper Scaling.
*   Further refine ASR decoding parameters for optimal accuracy across diverse audio.
*   Expand CLI functionality for easier use and batch processing.

 BLOCK DIAGRAM FOR SOFTWARE + HARDWARE

<pre>
EE109 Audio Transcription & Analysis Pipeline – Block Diagram

[ Audio Input (WAV/MP3/etc.) ]
              │
              ▼
┌───────────────────────────────────────────────────────────────┐
│      Digital Signal Processing (DSP) Frontend — Hardware      │
│   [ To be implemented on FPGA using Spatial ]                 │
├───────────────────────────────────────────────────────────────┤
│ 1. Resampling (→ 16kHz if needed)                             │
│    └── Converts any sample rate to Whisper-compatible 16kHz   │
│                                                               │
│ 2. Hann Windowing                                             │
│    └── Applies smooth window to each frame before FFT         │
│                                                               │
│ 3. 400-pt FFT (STFT)                                          │
│    └── Transforms windowed time-domain signal → frequency     │
│                                                               │
│ 4. Power Spectrum                                             │
│    └── Computes magnitude² of each FFT output bin             │
│                                                               │
│ 5. Mel Filterbank (80 bands, 0–8kHz)                          │
│    └── Projects spectrum onto perceptual Mel scale            │
│                                                               │
│ 6. Log Compression + Whisper Scaling                          │
│    ├── log10(clamp(power, min=1e-10))                         │
│    └── Applies Whisper scaling: (x + 4) / 4                   │
└───────────────────────────────────────────────────────────────┘
              │
              ▼
[ Output: Log-Mel Spectrogram (80 × T float32 ndarray) ]
              │
              ▼
┌───────────────────────────────────────────────────────────────┐
│            Automatic Speech Recognition (ASR)                 │
│   [ Whisper (OpenAI) model via HuggingFace Transformers ]     │
├───────────────────────────────────────────────────────────────┤
│ • Takes log-Mel spectrogram as input                          │
│ • Uses sliding window for audio > 30 sec                      │
│ • Decodes each chunk into text                                │
│ • Merges overlapping segments using custom `_merge()`         │
└───────────────────────────────────────────────────────────────┘
              │
              ▼
[ Output: Transcript (string) ]
              │
              ▼
┌───────────────────────────────────────────────────────────────┐
│            Natural Language Processing (NLP)                  │
│   [ Keyword Spotting, Topic ID, Summarization ]               │
├───────────────────────────────────────────────────────────────┤
│ • classify_keywords() → Top keyword & confidence              │
│ • classify_topic()    → Main topic & confidence               │
│ • summarize_text()    → Summary of input transcript           │
└───────────────────────────────────────────────────────────────┘
              │
              ▼
[ Final Output JSON ]
{
  "transcript": "...",
  "nlp_analysis": {
    "keyword": {"label": "...", "confidence": 0.0},
    "topic": {"label": "...", "confidence": 0.0},
    "summary": "..."
  }
}
</pre>

## Milestone 2: Hardware Implementation and FPGA DSP Frontend Verification (`spatial_dsp.py`)

To verify the hardware DSP pipeline, a standalone test script (`src/audiolib/dsp/spatial_dsp.py`) was developed. This script runs the complete Spatial-based DSP frontend in software simulation mode and validates its correctness by comparing the outputs of each hardware stage against Python reference results computed using `mel_gold.py`, which replicates Whisper's preprocessing pipeline.

The purpose of this test is to ensure that each Spatial kernel behaves identically to its Python counterpart. It validates that the entire DSP pipeline—from raw audio to Whisper-scaled log-Mel spectrogram—produces numerically equivalent output for the first frame of audio input.

The Spatial kernels involved are:

- Power Spectrum: `fpga/PowerSpectrum/PowerSpectrum.scala`
  - Computes magnitude squared of complex FFT bins
  - Input: 512 real/imaginary pairs
  - Output: 512 power values
  
- Mel Filterbank: `fpga/MelFilterbank/MelFilterbank.scala`
  - Projects linear frequency bins to Mel scale
  - Input: 512 power spectrum values
  - Output: 80 Mel-filtered bands
  
- Log Compression: `fpga/LogCompress/LogCompress.scala`
  - Applies log10 and minimum value clamping
  - Input: 80 Mel band values
  - Output: 80 log-compressed values
  
- Whisper Scaling: `fpga/WhisperScale/WhisperScale.scala`
  - Normalizes values to Whisper's expected range
  - Input: 80 log values
  - Output: 80 scaled values

The script performs the following sequence of actions:

1. Loads and resamples a test audio file using `mel_gold.py`.
2. Computes Python gold reference outputs for quantization, STFT, Mel filtering, log compression, and Whisper scaling.
3. Generates input `.csv` files for the Spatial kernels, such as `real.csv`, `imag.csv`, `power_bins.csv`, and `mel_filterbank.csv`.
4. For each Spatial kernel:
   - Compiles using `sbt "run --sim"` to generate simulation artifacts in `fpga/<KernelName>/gen/`
   - The compilation produces:
     - C++ simulation code
     - `run.sh` script for execution
     - Memory initialization files
     - Chisel/FIRRTL intermediate representations
   - Executes the generated `./run.sh` script which:
     - Sets up the simulation environment variables (SPATIAL_HOME, JVM settings)
     - Configures the C++ simulator parameters
     - Loads input data from CSV files into simulated memories
     - Runs the kernel simulation with cycle-accurate timing
     - Captures performance metrics (cycles, throughput)
     - Writes output data and statistics to CSV files
     - Performs automated cleanup of temporary files
5. Collects the FPGA output `.csv` files and compares them to the Python reference outputs using `np.testing.assert_allclose`, with strict relative and absolute tolerances.
6. Logs and prints detailed mismatches, including:
   - Exact numerical differences between FPGA and reference values
   - Index positions of mismatches
   - Statistical analysis (mean error, std dev)
   - Potential causes like clamping issues or filter misalignment
   - Visualization of error patterns when applicable

All intermediate files are saved in the `fpga_io/` directory. Key outputs include:

- `real.csv`, `imag.csv`: STFT input components (512 values each)
- `power_spectrum_output.csv`: Power spectrum values (512 bins)
- `mel_filterbank.csv`: 512x80 filterbank matrix coefficients
- `melfilterbank_output.csv`: Mel-filtered output (80 bands)
- `logcompress_output.csv`: Log-compressed values (80 values)
- `whisperscale_output.csv`: Final scaled output (80 values)

Each kernel's `run.sh` also generates:
- Cycle count and timing reports in `fpga/<KernelName>/logs/`
- Memory traces in `fpga/<KernelName>/gen/`
- Simulation waveforms for debugging
- Resource utilization estimates

On successful execution, the test confirms correct behavior with messages such as:

[INFO] QuantizeKernel FPGA output matches Python Scipy STFT power path. 
[INFO] PowerSpectrum FPGA output matches Python Scipy STFT power path.  
[INFO] MelFilterbank FPGA output matches mel_gold.py/torchaudio output.  
[INFO] LogCompress Final FPGA output matches mel_gold.py pipeline's 1st frame.  
[INFO] WhisperScale FPGA output matches mel_gold.py pipeline output.  
[INFO] FINAL OUTPUT MATCHES: Full FPGA pipeline output matches mel_gold.py equivalent output.

This test provides high-confidence validation that the Spatial kernels correctly reproduce Whisper-compatible DSP behavior and are ready for integration into an end-to-end hardware deployment.

### Requirements

- Python 3.8+ and packages listed in `requirements.txt`
- Scala + SBT (for compiling Spatial code)
- At least 8GB RAM for simulation
- ~2GB free disk space for build artifacts
- SPATIAL_HOME environment variable set
- C++ compiler (g++ or clang++)