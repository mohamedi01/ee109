import pytest
import whisper
from pathlib import Path
from typing import Union
from jiwer import wer

from audiolib.asr import transcribe_audio_file
from audiolib.nlp import analyze_text
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


def run_full_pipeline(audio_path: Union[str, Path], device: str = "cpu") -> tuple[str, dict]:
    """Runs DSP->ASR->NLP pipeline: returns transcript and NLP outputs."""
    transcript = transcribe_audio_file(audio_path, device=device)
    nlp_out    = analyze_text(transcript, device=device)
    return transcript, nlp_out


@pytest.mark.parametrize("audio_filepath, expected_transcription_path", EXAMPLE_AUDIO_FILES_WITH_TRUTH)
def test_full_pipeline_nlp(audio_filepath, expected_transcription_path):
    """
    End-to-end test: DSP->ASR accuracy vs baseline + NLP output validation.
    """
    # Load expected transcription
    with open(expected_transcription_path, 'r', encoding='utf-8') as f:
        expected_trans = f.read().strip()

    # 1. Run custom pipeline
    pipeline_transcript, nlp_out = run_full_pipeline(audio_filepath, device="cpu")

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

    # 5. Validate NLP outputs
    assert set(nlp_out.keys()) == {"keyword", "topic", "summary"}

    # Summary should be non-empty string
    summary = nlp_out["summary"]
    assert isinstance(summary, str)
    assert len(summary) > 0

    # Keyword classification
    kw_label, kw_conf = nlp_out["keyword"]
    assert isinstance(kw_label, str)
    assert isinstance(kw_conf, float)
    assert 0.0 <= kw_conf <= 1.0

    # Topic classification
    top_label, top_conf = nlp_out["topic"]
    assert isinstance(top_label, str)
    assert isinstance(top_conf, float)
    assert 0.0 <= top_conf <= 1.0
