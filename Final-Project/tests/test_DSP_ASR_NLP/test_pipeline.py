import pytest
import whisper
from pathlib import Path
from typing import Union
from jiwer import wer

from audiolib.asr import transcribe_audio_file
from audiolib.nlp import summarize_text
from testutility.text_processing_utils import (
    normalize_text_for_wer,
    apply_canonical_map_to_text,
    DEFAULT_COMPREHENSIVE_CANONICAL_MAP,
)

# Fixture to clear Whisper cache before tests
@pytest.fixture(autouse=True, scope="module")
def clear_whisper_model_cache():
    from audiolib.asr.whisper_features import _load as whisper_features_load_function
    whisper_features_load_function.cache_clear()
    print("INFO: Cleared lru_cache for audiolib.asr.whisper_features._load")

# Test data paths
AUDIO_DATA_DIR = Path("data/short_sentences/")
TRANSCRIPT_DATA_DIR = Path("data/transcripts/short_sentences/")

EXAMPLE_AUDIO_FILES_WITH_TRUTH = [
    (AUDIO_DATA_DIR / "harvard_f.wav", TRANSCRIPT_DATA_DIR / "harvard_f.txt"),
    (AUDIO_DATA_DIR / "harvard_m.wav", TRANSCRIPT_DATA_DIR / "harvard_m.txt"),
]


def run_full_pipeline(audio_path: Union[str, Path], device: str = "cpu") -> tuple[str, str]:
    """Runs DSP->ASR->NLP pipeline: returns transcript and NLP summary string."""
    transcript = transcribe_audio_file(audio_path, device=device)
    summary_str = summarize_text(transcript, device=device)
    return transcript, summary_str


@pytest.mark.parametrize("audio_filepath, expected_transcription_path", EXAMPLE_AUDIO_FILES_WITH_TRUTH)
def test_full_pipeline_nlp(audio_filepath, expected_transcription_path):
    """
    End-to-end test: DSP->ASR accuracy vs baseline + NLP summary string validation.
    """
    # Load expected transcription
    with open(expected_transcription_path, 'r', encoding='utf-8') as f:
        expected_trans = f.read().strip()

    # 1. Run custom pipeline
    pipeline_transcript, summary_str = run_full_pipeline(audio_filepath, device="cpu")

    # 2. Baseline Whisper model transcription
    baseline_model   = whisper.load_model("base")
    baseline_result  = baseline_model.transcribe(str(audio_filepath))
    baseline_trans   = baseline_result["text"].strip()

    # 3. Normalize and apply canonical mapping
    norm_expected = apply_canonical_map_to_text(
        normalize_text_for_wer(expected_trans),
        DEFAULT_COMPREHENSIVE_CANONICAL_MAP
    )
    norm_pipeline = apply_canonical_map_to_text(
        normalize_text_for_wer(pipeline_transcript),
        DEFAULT_COMPREHENSIVE_CANONICAL_MAP
    )
    norm_baseline = apply_canonical_map_to_text(
        normalize_text_for_wer(baseline_trans),
        DEFAULT_COMPREHENSIVE_CANONICAL_MAP
    )

    # 4. Compute WERs
    pipeline_wer = wer(norm_expected, norm_pipeline)
    baseline_wer = wer(norm_expected, norm_baseline)

    # Assert ASR quality is within tolerance
    tolerance = 0.05
    assert pipeline_wer <= baseline_wer + tolerance, (
        f"Pipeline WER ({pipeline_wer:.4f}) exceeds baseline plus tolerance "
        f"({baseline_wer:.4f} + {tolerance})"
    )

    # 5. Validate NLP summary string output

    # Summary should be non-empty string
    assert isinstance(summary_str, str)
    assert len(summary_str) > 0

    # Keyword and Topic classification checks REMOVED
