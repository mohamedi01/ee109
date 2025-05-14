# EE109 Final Project: Audio Transcription Pipeline

For our EE109 final project, we are implementing a Python-based audio transcription system which then uses NLP to convert the transcribed text into a summary. So far, it has a custom Digital Signal Processing (DSP) frontend to extract log-Mel spectrograms compatible with OpenAI's Whisper models, followed by an Automatic Speech Recognition (ASR) (utilizing the `openai/whisper-base.en` model). 

## Features

*   **Custom DSP Frontend**: Implements `wav_to_logmel` for converting audio files (WAV, FLAC, OGG, MP3) into 80-band log-Mel spectrograms, adhering to Whisper model input requirements.
    *   Resampling to 16 kHz.
    *   **Short-Time Fourier Transform (STFT):**
        *   Windowing: 25 ms Hann windows.
        *   Hop Length: 10 ms.
        *   FFT: 400-point Fast Fourier Transform.
    *   Mel Filterbank: 80 bands applied to the power spectrum (0 – 8 kHz).
    *   Logarithmic compression and Whisper-specific scaling of Mel energies.
*   **Whisper-based ASR**: Utilizes the `transformers` library with the `openai/whisper-base.en` model for speech-to-text conversion.
*   **Long Audio Transcription**: Employs a sliding window approach (`long_transcribe.py`) to process audio files exceeding 30 seconds, with intelligent merging of overlapping transcribed segments.
*   **Testing Suite**: Includes unit tests for DSP components and ASR accuracy tests comparing against baseline Whisper and ground truth transcriptions using `pytest`. Word Error Rate (WER) is used as a key metric.
*   

## Data Sources and Acknowledgements

*   **Homophone List**: For text normalization during Word Error Rate (WER) calculation, this project utilizes a list of English homophones sourced from the `pimentel/homophones` GitHub repository. The specific file used is `homophones.csv`, available at [https://raw.githubusercontent.com/pimentel/homophones/master/homophones.csv](https://raw.githubusercontent.com/pimentel/homophones/master/homophones.csv). This data is stored locally in `data/homophones.csv`.

## Project Structure
For a more detailed view of the architecture, please see [docs/architecture.md](docs/architecture.md). The main note is: 
**Design**: Code is organized into `audiolib` with submodules for `dsp` and `asr`.

## Setup and Installation

2.  **Create and activate a virtual environment**
    ```bash
    python -m venv venv
    # On Windows
    # venv\Scripts\activate
    # On macOS/Linux
    # source venv/bin/activate
    ```

3.  **Install dependencies:**
    ```bash
    pip install -r requirements.txt
    ```

## Usage

## Running Tests

The project uses `pytest` for testing.

1.  **Ensure you have installed the test dependencies** (pytest and jiwer are included in `requirements.txt`).

2.  **Run all tests:**
    From the project root directory (`Final-Project/`):
    ```bash
    pytest
    ```

3.  **Run specific test files or tests with more verbose output:**
    ```bash
    pytest -s tests/test_DSP/test_dsp_single_words.py
    pytest -s tests/test_DSP/test_dsp_short_sentences.py
    pytest -s tests/test_DSP_to_ASR/test_asr_short_sentences.py
    pytest -s tests/test_DSP_to_ASR/test_asr_single_words.py
    ```
    The `-s` flag shows output from `print()` statements

## Future Work

*   **Natural Language Processing (NLP) Integration:**
    *   Implement the NLP components outlined in `src/audiolib/nlp/`, particularly the `slice_summariser.py`, to process the transcribed text and generate concise summaries.
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

## Notes
* The test file `tests/test_short_sentences.py` (previously at the root of the tests directory) has been removed as it was redundant.
