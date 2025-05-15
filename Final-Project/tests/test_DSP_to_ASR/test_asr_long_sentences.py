""" 
Tests the end-to-end ASR pipeline (DSP + ASR via transcribe_audio_file) for long sentence audio files.
Compares the custom pipeline's output against a baseline Whisper model and ground truth transcriptions,
using Word Error Rate (WER) after text normalization. 
"""

import pytest
import whisper  
import numpy as np
from pathlib import Path
from typing import Union 
from jiwer import wer

from audiolib.asr import transcribe_audio_file
from audiolib.asr.whisper_features import _load as whisper_features_load_function 
from testutility.text_processing_utils import normalize_text_for_wer, apply_canonical_map_to_text, DEFAULT_COMPREHENSIVE_CANONICAL_MAP

@pytest.fixture(autouse=True, scope="module")
def clear_whisper_model_cache():
    """Clears the LRU cache for the whisper model loading function."""
    whisper_features_load_function.cache_clear()
    print("\nINFO: Cleared lru_cache for audiolib.asr.whisper_features._load")

AUDIO_DATA_DIR = Path("data/long_sentences/")
TRANSCRIPT_DATA_DIR = Path("data/transcripts/long_sentences/")

EXAMPLE_AUDIO_FILES_WITH_TRUTH = [
    (AUDIO_DATA_DIR / "bird.mp3", TRANSCRIPT_DATA_DIR / "bird.txt"),
    (AUDIO_DATA_DIR / "cold.mp3", TRANSCRIPT_DATA_DIR / "cold.txt"),
    (AUDIO_DATA_DIR / "face.mp3", TRANSCRIPT_DATA_DIR / "face.txt"),
    (AUDIO_DATA_DIR / "hot.mp3", TRANSCRIPT_DATA_DIR / "hot.txt"),
] 


def run_custom_pipeline(audio_path: Union[str, Path], device: str = "cpu") -> str:
    """
    Runs your custom DSP -> ASR pipeline using transcribe_audio_file.
    """
    transcript_text = transcribe_audio_file(audio_path, device=device)
    return transcript_text 

# Run baseline Whisper model 
# Consider making model choice configurable if you test other sizes (e.g., "small", "medium")
baseline_whisper_model = whisper.load_model("base")

@pytest.mark.parametrize("audio_filepath, expected_transcription_path", EXAMPLE_AUDIO_FILES_WITH_TRUTH)
def test_asr_accuracy_long_sentences(audio_filepath, expected_transcription_path):
    """
    Tests the custom DSP->ASR pipeline against baseline Whisper and an expected transcription
    for long sentence audio files, with homophone-aware normalization.
    """
    if not audio_filepath.exists():
        pytest.skip(f"Audio file not found: {audio_filepath}")
    if not expected_transcription_path.exists():
        pytest.skip(f"Transcription file not found: {expected_transcription_path}")

    # 0. Read expected transcription from file
    with open(expected_transcription_path, 'r', encoding='utf-8') as f:
        expected_transcription = f.read().strip()

    # 1. Get transcription from your DSP -> ASR pipeline
    pipeline_transcript = run_custom_pipeline(audio_filepath)

    # 2. Get transcription from baseline Whisper (using openai-whisper directly)
    baseline_result = baseline_whisper_model.transcribe(str(audio_filepath))
    baseline_transcript = baseline_result["text"].strip()

    # 3. Basic normalization 
    norm_expected_base = normalize_text_for_wer(expected_transcription)
    norm_pipeline_base = normalize_text_for_wer(pipeline_transcript)
    norm_baseline_base = normalize_text_for_wer(baseline_transcript)

    # 4. Apply canonical map ONLY to ASR outputs
    norm_expected_final = apply_canonical_map_to_text(norm_expected_base, DEFAULT_COMPREHENSIVE_CANONICAL_MAP)
    norm_pipeline_final = apply_canonical_map_to_text(norm_pipeline_base, DEFAULT_COMPREHENSIVE_CANONICAL_MAP)
    norm_baseline_final = apply_canonical_map_to_text(norm_baseline_base, DEFAULT_COMPREHENSIVE_CANONICAL_MAP)

    # 5. Print Transcription outputs
    print(f"\n--- Testing Audio File (Long Sentence): {audio_filepath} ---")
    print(f"Expected (Final - post normalization): \n\n    Original: '{expected_transcription}'\n\n    Normalized: '{norm_expected_final}'\n")
    print(f"Custom Pipeline (Final - post normalization): \n\n    Original: '{pipeline_transcript}'\n\n    Normalized: '{norm_pipeline_final}'\n")
    print(f"Baseline Whisper (Final - post normalization): \n\n    Original: '{baseline_transcript}'\n\n    Normalized: '{norm_baseline_final}'\n")

    # 6. Calculate WER and print results
    pipeline_wer = wer(norm_expected_final, norm_pipeline_final)
    baseline_wer = wer(norm_expected_final, norm_baseline_final)

    print(f"Pipeline WER:            {pipeline_wer:.4f}")
    print(f"Baseline WER:             {baseline_wer:.4f}")
    
    # 7. Assert that pipeline WER (vs expected) is not significantly worse 
    # than baseline WER (vs expected).
    tolerance = 0.11 # Adjust tolerance as needed for longer, more complex audio
    assert pipeline_wer <= baseline_wer + tolerance, \
        f"Pipeline WER ({pipeline_wer:.4f}) vs Expected is not better than or close to " \
        f"Baseline WER ({baseline_wer:.4f}) vs Expected for {audio_filepath} " \
        f"(Tolerance: {tolerance}). Pipeline: '{norm_pipeline_final}', Expected: '{norm_expected_final}'" 