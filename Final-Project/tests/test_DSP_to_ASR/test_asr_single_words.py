import pytest
import whisper  # For baseline ASR (pip install openai-whisper)
import numpy as np 
from pathlib import Path
import re
from jiwer import wer
from audiolib.dsp.mel import wav_to_logmel
from audiolib.asr import whisper_features
from testutility.text_processing_utils import normalize_text_for_wer, apply_canonical_map_to_text, DEFAULT_COMPREHENSIVE_CANONICAL_MAP

# Import your project modules
from audiolib.asr import long_transcribe as asr_module



def run_custom_pipeline(audio_path: str, device: str = "cpu") -> str:
    """
    Runs your custom DSP -> ASR pipeline using transcribe_long_clip.
    """
    try:
        # Call main transcription function from asr_module
        # This function should handle audio loading, DSP, and ASR internally.
        transcript_text = asr_module.transcribe_long_clip(audio_path, device=device)
        
        # transcribe_long_clip (via transcribe_features) already returns a stripped and lowercased string.
        return transcript_text 
    except Exception as e:
        print(f"Error running custom pipeline for {audio_path}: {e}")
        # Ensure your transcribe_long_clip function is robust or catch specific exceptions.
        return f"[ERROR_IN_CUSTOM_PIPELINE: {e}]"

# Load baseline Whisper model (can be done once globally or per test module)
# Model sizes: "tiny", "base", "small", "medium", "large"
# "base" is a good starting point.
try:
    baseline_whisper_model = whisper.load_model("base")
except Exception as e:
    print(f"Failed to load baseline Whisper model: {e}")
    print("Please ensure 'openai-whisper' is installed and models can be downloaded.")
    baseline_whisper_model = None


# --- Test Data ---
# List of tuples: (audio_filename, expected_number_word)
# Using relative paths from the project root for data files.

# Using Path objects for better path manipulation
DATA_DIR_NUMBERS = Path("data/single_words/")

TEST_DATA_NUMBERS = [
    (DATA_DIR_NUMBERS / "0_jackson_0.wav", "zero"),
    (DATA_DIR_NUMBERS / "1_jackson_0.wav", "one"),
    (DATA_DIR_NUMBERS / "2_jackson_0.wav", "two"),
    (DATA_DIR_NUMBERS / "3_theo_0.wav", "three"),
    (DATA_DIR_NUMBERS / "4_theo_0.wav", "four"),
    (DATA_DIR_NUMBERS / "5_george_0.wav", "five"),
    (DATA_DIR_NUMBERS / "6_george_0.wav", "six"),
]

# CANONICAL_HOMOPHONE_MAP is no longer needed here.
# The normalize_text_for_wer function is also no longer defined here.

@pytest.mark.skipif(baseline_whisper_model is None, reason="Baseline Whisper model failed to load.")
@pytest.mark.parametrize("audio_filepath, expected_transcription", TEST_DATA_NUMBERS)
def test_asr_accuracy_comparison(audio_filepath, expected_transcription):
    """
    Tests the custom DSP->ASR pipeline against baseline Whisper and an expected transcription.
    """
    # Convert Path object to string for functions expecting string paths
    audio_filepath_str = str(audio_filepath)

    # 1. Get transcription from your DSP -> ASR pipeline
    pipeline_transcript = run_custom_pipeline(audio_filepath_str)

    # 2. Get transcription from baseline Whisper (using openai-whisper directly)
    baseline_result = baseline_whisper_model.transcribe(audio_filepath_str)
    baseline_transcript = baseline_result["text"].strip()

    # 1. Basic normalization for the expected transcription
    norm_expected_base = normalize_text_for_wer(expected_transcription)

    # 2. Basic normalization for ASR outputs
    norm_pipeline_base = normalize_text_for_wer(pipeline_transcript)
    norm_baseline_base = normalize_text_for_wer(baseline_transcript)

    # 3. Apply canonical map ONLY to ASR outputs
    norm_expected_final = apply_canonical_map_to_text(norm_expected_base, DEFAULT_COMPREHENSIVE_CANONICAL_MAP)
    norm_pipeline_final = apply_canonical_map_to_text(norm_pipeline_base, DEFAULT_COMPREHENSIVE_CANONICAL_MAP)
    norm_baseline_final = apply_canonical_map_to_text(norm_baseline_base, DEFAULT_COMPREHENSIVE_CANONICAL_MAP)

    print(f"\n--- Testing Audio File: {audio_filepath_str} ---")
    print(f"Expected (Base Norm):    '{norm_expected_final}'")
    print(f"Custom Pipeline (Final): '{norm_pipeline_final}' (Original: '{pipeline_transcript}')")
    print(f"Baseline Whisper (Final):'{norm_baseline_final}' (Original: '{baseline_transcript}')")

    # Calculate WER between normalized pipeline output and normalized expected output
    pipeline_wer = min(wer(norm_expected_final, norm_pipeline_final), wer(norm_expected_base, norm_pipeline_base))
    # Calculate WER between normalized baseline output and normalized expected output
    baseline_wer = min(wer(norm_expected_final, norm_baseline_final), wer(norm_expected_base, norm_baseline_base))

    print(f"Pipeline WER:            {pipeline_wer:.4f}")
    print(f"Baseline Whisper WER:    {baseline_wer:.4f}")

    # Example assertion: Check if your pipeline's WER is not significantly worse than baseline,
    # or if it meets a certain threshold.
    # This threshold might need adjustment based on your expectations.
    # For single words, we might expect very low WER.
    assert pipeline_wer <= 0.5, f"Pipeline WER {pipeline_wer} is too high for {audio_filepath_str}"
    
