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

## Main Features

*   **Custom DSP Frontend**: Converts audio (WAV, FLAC, MP3, etc.) to Whisper-compatible log-Mel spectrograms (`audiolib.dsp.mel.wav_to_logmel`).
*   **Whisper-based ASR**: Transcribes speech to text using `openai/whisper-base.en` (`audiolib.asr`). Handles both short and long audio (with segment merging).
*   **NLP Analysis**: Performs keyword spotting, topic identification, and summarization on transcribed text (`audiolib.nlp.nlp.analyze_text`).
*   **Integrated End-to-End Pipeline**: `audiolib.pipeline.process_audio_to_nlp` provides a single interface for DSP, ASR, and NLP.
*   **Comprehensive Testing Suite**: Utilizes `pytest` for unit, integration, and pipeline tests, with WER as a key ASR metric.
*   **Spatial DSP Kernels for FPGA Acceleration**: Implementations of key DSP tasks in Spatial, with ongoing testing and development.

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

### 1. Software Tests

These tests verify the correctness of the Python software implementation for each stage of the pipeline.

- **DSP (Digital Signal Processing) Tests:**  
  Located in `tests/test_DSP/`.  
  Run with:
  ```bash
  pytest tests/test_DSP/
  ```
  These tests check the log-Mel spectrogram features for various audio files, ensuring correct shape, value ranges, and absence of NaNs/Infs.

- **ASR (Automatic Speech Recognition) Tests:**  
  Located in `tests/test_DSP_to_ASR/`.  
  Run with:
  ```bash
  pytest tests/test_DSP_to_ASR/
  ```
  These tests compare the custom DSP+ASR pipeline against a baseline Whisper model and ground truth, using Word Error Rate (WER) as the metric.

- **NLP (Natural Language Processing) Tests:**  
  Located in `tests/test_NLP/`.  
  Run with:
  ```bash
  pytest tests/test_NLP/
  ```
  These tests check summarization and performance functions on sample texts.

  - **Pipeline Tests:**  
  Located in `tests/test_pipeline/`.  
  Run with:
  ```bash
  pytest tests/test_pipeline/
  ```
  These tests:
  - Process audio files through the full pipeline.
  - Compare the ASR transcript to ground truth using WER.
  - Optionally compare NLP outputs (summary, etc.) to those run directly on the ground truth transcript.

### 2. Hardware (FPGA/Spatial) Tests

These tests verify the correctness of the hardware-accelerated DSP pipeline implemented in Spatial and run via simulation.

- **Single-Frame Hardware DSP Validation:**  
  The script `src/audiolib/dsp/spatial_dsp_single.py` runs the hardware DSP pipeline for a single frame, comparing each hardware stage's output to the Python reference for detailed validation. This is useful for debugging and verifying hardware correctness at each step.
  Run with:
  ```bash
  python src/audiolib/dsp/spatial_dsp_single.py path/to/audio.wav
  ```

- **Standalone Hardware DSP Test (multi-frame):**  
  The script `src/audiolib/dsp/spatial_dsp.py` runs the full Spatial-based DSP frontend in software simulation mode, comparing each hardware stage's output to Python reference results.
  Run with:
  ```bash
  python src/audiolib/dsp/spatial_dsp.py path/to/audio.wav
  ```
  This will:
  - Generate input CSVs for the Spatial kernels.
  - Compile and simulate the hardware kernels using SBT and C++.
  - Compare the FPGA output CSVs to Python reference outputs.
  - Print detailed pass/fail and error analysis.

  All intermediate files are saved in the `fpga_io/` directory.

  **Note:** The script `spatial_dsp.py` is an attempt at optimizing the hardware DSP pipeline by combining multiple DSP stages (such as STFT, power spectrum, Mel filterbank, log compression, and Whisper scaling) into a single, streamlined hardware kernel (`MelLogScaleKernel`). Instead of running and validating each DSP stage separately, this approach feeds the entire audio through the full pipeline in one go, reducing intermediate I/O and improving simulation speed. This design is intended to more closely match the structure of a real-time, integrated hardware DSP accelerator, and to enable more efficient end-to-end testing and benchmarking of the hardware pipeline.

**Note on `mel.py` vs `mel_gold.py`:**

- **`mel.py`** provides a compact, high-level implementation of the Whisper-compatible log-Mel spectrogram pipeline. It is designed for direct, end-to-end conversion of audio to log-Mel features, using a single function (`wav_to_logmel`) that encapsulates all steps. This is the version typically used in the main Python pipeline for efficiency and simplicity.

- **`mel_gold.py`** is a modular, reference implementation of the same pipeline. It breaks down the process into isolated, testable functions for each stage (audio loading, resampling, quantization, STFT, power spectrum, Mel filterbank, log compression, Whisper scaling, etc.). This modularity makes it ideal for debugging, hardware validation, and stage-by-stage comparison with FPGA/Spatial outputs. It is used as the "gold standard" for verifying hardware kernels and for detailed DSP testing.

### 3. Full Pipeline/Integration Tests

These tests run the entire DSP → ASR → NLP pipeline on real audio files and compare the results to ground truth.

- **Pipeline Tests:**  
  Located in `tests/test_pipeline/`.  
  Run with:
  ```bash
  pytest tests/test_pipeline/
  ```
  These tests:
  - Process audio files through the full pipeline.
  - Compare the ASR transcript to ground truth using WER.
  - Optionally compare NLP outputs (summary, etc.) to those run directly on the ground truth transcript.

---

**Pipeline Runners Overview:**

- `run_pipeline.py`: Runs the full DSP → ASR → NLP pipeline entirely in Python (software-only, no hardware acceleration).
  ```bash
  python -m src.audiolib.run_pipeline path/to/audio.wav
  ```
- `run_pipeline_hw.py`: Runs the full pipeline with hardware-accelerated DSP (FPGA/Spatial) for the DSP stage, then Python for ASR and NLP.
  ```bash
  python -m src.audiolib.run_pipeline_hw path/to/audio.wav
  ```


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


### Requirements

- Python 3.8+ and packages listed in `requirements.txt`
- Scala + SBT (for compiling Spatial code)
- At least 8GB RAM for simulation
- ~2GB free disk space for build artifacts
- SPATIAL_HOME environment variable set
- C++ compiler (g++ or clang++)