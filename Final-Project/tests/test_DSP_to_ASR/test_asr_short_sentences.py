import pytest
import whisper  # For baseline ASR (pip install openai-whisper)
import numpy as np
from pathlib import Path
from typing import Union 
from jiwer import wer

from audiolib.asr import long_transcribe as asr_module
from audiolib.asr.whisper_features import _load as whisper_features_load_function # Import the function
from testutility.text_processing_utils import normalize_text_for_wer, apply_canonical_map_to_text, DEFAULT_COMPREHENSIVE_CANONICAL_MAP

@pytest.fixture(autouse=True, scope="module")
def clear_whisper_model_cache():
    """Clears the LRU cache for the whisper model loading function."""
    whisper_features_load_function.cache_clear()
    print("\nINFO: Cleared lru_cache for audiolib.asr.whisper_features._load")

EXAMPLE_AUDIO_FILES_WITH_TRUTH = [
    # Format: (Path("path/to/audio.wav"), Path("path/to/expected_transcription.txt"))
    (Path("data/short_sentences/harvard_f.wav"), Path("data/transcripts/harvard_f.txt")),
    (Path("data/short_sentences/harvard_m.wav"), Path("data/transcripts/harvard_m.txt")),
    # Add more files as needed
]

def run_custom_pipeline(audio_path: Union[str, Path], device: str = "cpu") -> str:
    """
    Runs your custom DSP -> ASR pipeline using transcribe_long_clip.
    """
    transcript_text = asr_module.transcribe_long_clip(audio_path, device=device)
    return transcript_text 

# Run baseline Whisper model 
baseline_whisper_model = whisper.load_model("base")

@pytest.mark.skipif(baseline_whisper_model is None, reason="Baseline Whisper model failed to load.")
@pytest.mark.parametrize("audio_filepath, expected_transcription_path", EXAMPLE_AUDIO_FILES_WITH_TRUTH)
def test_asr_accuracy_comparison_long(audio_filepath, expected_transcription_path):
    """
    Tests the custom DSP->ASR pipeline against baseline Whisper and an expected transcription
    for long audio files, with homophone-aware normalization.
    """
    # 0. Read expected transcription from file
    with open(expected_transcription_path, 'r', encoding='utf-8') as f:
        expected_transcription = f.read().strip()

    # 1. Get transcription from your DSP -> ASR pipeline
    pipeline_transcript = run_custom_pipeline(audio_filepath)

    # 2. Get transcription from baseline Whisper (using openai-whisper directly)
    baseline_result = baseline_whisper_model.transcribe(str(audio_filepath))
    baseline_transcript = baseline_result["text"].strip()

    # 1. Basic normalization for the expected transcription
    norm_expected_base = normalize_text_for_wer(expected_transcription)
    norm_pipeline_base = normalize_text_for_wer(pipeline_transcript)
    norm_baseline_base = normalize_text_for_wer(baseline_transcript)

    # 3. Apply canonical map ONLY to ASR outputs
    norm_expected_final = apply_canonical_map_to_text(norm_expected_base, DEFAULT_COMPREHENSIVE_CANONICAL_MAP)
    norm_pipeline_final = apply_canonical_map_to_text(norm_pipeline_base, DEFAULT_COMPREHENSIVE_CANONICAL_MAP)
    norm_baseline_final = apply_canonical_map_to_text(norm_baseline_base, DEFAULT_COMPREHENSIVE_CANONICAL_MAP)

    # Print Transcription outputs
    print(f"\n--- Testing Audio File: {audio_filepath} ---")
    print(f"Expected (Final - post normalization): \n    {norm_expected_final}\n")
    print(f"Custom Pipeline (Final - post normalization): \n    {norm_pipeline_final} (Original: '{pipeline_transcript}')\n")
    print(f"Baseline Whisper (Final - post normalization): \n    {norm_baseline_final} (Original: '{baseline_transcript}')\n")

    # Calculate WER and print results
    pipeline_wer = wer(norm_expected_final, norm_pipeline_final)
    baseline_wer = wer(norm_expected_final, norm_baseline_final)

    print(f"Pipeline WER:            {pipeline_wer:.4f}")
    print(f"Baseline WER:             {baseline_wer:.4f}")
    
    # Assert that pipeline WER (vs expected) is not significantly worse 
    # than baseline WER (vs expected).
    tolerance = 0.05 
    assert pipeline_wer <= baseline_wer + tolerance, \
        f"Pipeline WER ({pipeline_wer:.4f}) vs Expected is not better than or close to " \
        f"Baseline WER ({baseline_wer:.4f}) vs Expected for {audio_filepath} " \
        f"(Tolerance: {tolerance})."


