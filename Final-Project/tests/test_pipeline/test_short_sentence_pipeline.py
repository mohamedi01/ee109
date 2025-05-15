import pytest
from pathlib import Path
import json # For pretty printing outputs
from jiwer import wer # For Word Error Rate calculation

from audiolib.pipeline import process_audio_to_nlp
from audiolib.nlp.nlp import analyze_text
from testutility.text_processing_utils import (
    normalize_text_for_wer,
    apply_canonical_map_to_text,
    DEFAULT_COMPREHENSIVE_CANONICAL_MAP
)

# --- Configuration ---
PROJECT_ROOT = Path(__file__).resolve().parent.parent.parent
SHORT_SENTENCES_AUDIO_DIR = PROJECT_ROOT / "data" / "short_sentences"
SHORT_SENTENCES_TRANSCRIPTS_DIR = PROJECT_ROOT / "data" / "transcripts" / "short_sentences"

# Tolerance for ASR Word Error Rate (adjust as needed for your models)
ASR_WER_TOLERANCE_SHORT_SENTENCES = 0.15 # Allow up to 5% WER for short, clean audio

def get_short_sentence_test_data():
    """
    Scans the data directories and returns a list of tuples (audio_path, transcript_path)
    for all short sentence audio files that have a corresponding ground truth transcript.
    """
    test_data_pairs = []
    if not SHORT_SENTENCES_AUDIO_DIR.is_dir() or not SHORT_SENTENCES_TRANSCRIPTS_DIR.is_dir():
        print(f"Warning: Audio or transcript directory for short sentences not found. "
              f"Audio: {SHORT_SENTENCES_AUDIO_DIR}, Transcripts: {SHORT_SENTENCES_TRANSCRIPTS_DIR}")
        return test_data_pairs

    for audio_file in SHORT_SENTENCES_AUDIO_DIR.glob("*.wav"): # Assuming .wav for short sentences
        transcript_file = SHORT_SENTENCES_TRANSCRIPTS_DIR / f"{audio_file.stem}.txt"
        if transcript_file.is_file():
            test_data_pairs.append(pytest.param(audio_file, transcript_file, id=audio_file.name))
        else:
            print(f"Warning: Transcript for {audio_file.name} not found at {transcript_file}. Skipping.")
    
    if not test_data_pairs:
        print("Warning: No audio/transcript pairs found for short sentences. "
              "Pipeline tests for short sentences might be skipped by pytest.")
    return test_data_pairs

# --- Test Cases ---

@pytest.mark.parametrize("audio_file_path, truth_transcript_path", get_short_sentence_test_data())
def test_short_sentence_full_pipeline_and_nlp_on_truth(audio_file_path: Path, truth_transcript_path: Path):
    """
    Tests the full DSP-ASR-NLP pipeline for a short sentence audio file and
    compares its NLP output with NLP run directly on the ground truth transcript.
    """
    print(f"\\n--- Testing Short Sentence Pipeline for: {audio_file_path.name} ---")

    # 1. Run the full audio processing pipeline
    print(f"Running process_audio_to_nlp on {audio_file_path.name}...")
    pipeline_output = process_audio_to_nlp(audio_path=audio_file_path, device="cpu")
    
    assert isinstance(pipeline_output, dict), "Pipeline output should be a dictionary."
    assert "transcript" in pipeline_output, "Pipeline output must contain 'transcript'."
    assert "nlp_analysis" in pipeline_output, "Pipeline output must contain 'nlp_analysis'."

    asr_transcript_from_pipeline = pipeline_output["transcript"]
    nlp_analysis_from_pipeline = pipeline_output["nlp_analysis"]

    assert isinstance(asr_transcript_from_pipeline, str), "ASR transcript from pipeline should be a string."
    assert isinstance(nlp_analysis_from_pipeline, dict), "NLP analysis from pipeline should be a dictionary."
    for key in ["keyword", "topic", "summary"]:
        assert key in nlp_analysis_from_pipeline, f"NLP analysis from pipeline missing '{key}'."
        if key in ["keyword", "topic"]:
            assert isinstance(nlp_analysis_from_pipeline[key], tuple), f"NLP '{key}' should be a tuple (label, confidence)."
            assert len(nlp_analysis_from_pipeline[key]) == 2, f"NLP '{key}' tuple should have 2 elements."
            assert isinstance(nlp_analysis_from_pipeline[key][0], str), f"NLP '{key}' label should be a string."
            assert isinstance(nlp_analysis_from_pipeline[key][1], float), f"NLP '{key}' confidence should be a float."
        else: # summary
            assert isinstance(nlp_analysis_from_pipeline[key], str), "NLP summary should be a string."


    print(f"  ASR Transcript (from Pipeline): '{asr_transcript_from_pipeline}'")
    print(f"  NLP Analysis (from Pipeline on ASR output):")
    print(f"    Keyword: {nlp_analysis_from_pipeline['keyword']}")
    print(f"    Topic:   {nlp_analysis_from_pipeline['topic']}")
    print(f"    Summary (first 100 chars): {nlp_analysis_from_pipeline['summary'][:100]}...")

    # 2. Load ground truth transcript and run NLP on it
    assert truth_transcript_path.is_file(), f"Truth transcript file not found: {truth_transcript_path}"
    with open(truth_transcript_path, 'r', encoding='utf-8') as f:
        truth_transcript_text = f.read().strip()
    
    print(f"\\nRunning NLP (analyze_text) directly on ground truth transcript: {truth_transcript_path.name}...")
    nlp_analysis_on_truth_transcript = analyze_text(text=truth_transcript_text, device="cpu")

    assert isinstance(nlp_analysis_on_truth_transcript, dict), "NLP on truth output should be a dictionary."
    for key in ["keyword", "topic", "summary"]:
        assert key in nlp_analysis_on_truth_transcript, f"NLP on truth output missing '{key}'."
        if key in ["keyword", "topic"]:
            assert isinstance(nlp_analysis_on_truth_transcript[key], tuple), f"NLP on truth '{key}' should be a tuple."
            assert len(nlp_analysis_on_truth_transcript[key]) == 2, f"NLP on truth '{key}' tuple should have 2 elements."
            assert isinstance(nlp_analysis_on_truth_transcript[key][0], str), f"NLP on truth '{key}' label should be string."
            assert isinstance(nlp_analysis_on_truth_transcript[key][1], float), f"NLP on truth '{key}' confidence should be float."
        else: # summary
            assert isinstance(nlp_analysis_on_truth_transcript[key], str), "NLP on truth summary should be a string."

    print(f"  Ground Truth Transcript: '{truth_transcript_text}'")
    print(f"  NLP Analysis (directly on Ground Truth Transcript):")
    print(f"    Keyword: {nlp_analysis_on_truth_transcript['keyword']}")
    print(f"    Topic:   {nlp_analysis_on_truth_transcript['topic']}")
    print(f"    Summary (first 100 chars): {nlp_analysis_on_truth_transcript['summary'][:100]}...")

    # 3. Assertions and Comparisons
    print("\\nComparing outputs...")

    # ASR Accuracy Check
    norm_asr_pipeline = apply_canonical_map_to_text(normalize_text_for_wer(asr_transcript_from_pipeline), DEFAULT_COMPREHENSIVE_CANONICAL_MAP)
    norm_truth = apply_canonical_map_to_text(normalize_text_for_wer(truth_transcript_text), DEFAULT_COMPREHENSIVE_CANONICAL_MAP)
    
    current_wer = wer(norm_truth, norm_asr_pipeline) if norm_truth or norm_asr_pipeline else 0.0
    print(f"  Normalized ASR (Pipeline): '{norm_asr_pipeline}'")
    print(f"  Normalized Truth         : '{norm_truth}'")
    print(f"  Word Error Rate (WER)    : {current_wer:.4f}")
    assert current_wer <= ASR_WER_TOLERANCE_SHORT_SENTENCES, (
        f"ASR WER {current_wer:.4f} exceeds tolerance {ASR_WER_TOLERANCE_SHORT_SENTENCES} "
        f"for {audio_file_path.name}. ASR: '{norm_asr_pipeline}', Truth: '{norm_truth}'"
    )

    # NLP Keyword Consistency Check
    # (This check is most meaningful if actual NLP models are loaded, not dummies)
    pipeline_keyword_label = nlp_analysis_from_pipeline['keyword'][0]
    truth_keyword_label = nlp_analysis_on_truth_transcript['keyword'][0]
    print(f"  Pipeline NLP Keyword Label: '{pipeline_keyword_label}'")
    print(f"  Truth NLP Keyword Label   : '{truth_keyword_label}'")
    
    if pipeline_keyword_label != "_dummy_label" and truth_keyword_label != "_dummy_label":
        assert pipeline_keyword_label == truth_keyword_label, (
            f"Keyword mismatch for {audio_file_path.name}. "
            f"Pipeline ASR-NLP: '{pipeline_keyword_label}', Truth-NLP: '{truth_keyword_label}'"
        )
    else:
        print("  (Skipping keyword label assertion due to one or both being '_dummy_label')")

    # Basic Summary Checks (Non-empty if inputs were non-empty and models are not pure dummies)
    if asr_transcript_from_pipeline and pipeline_keyword_label != "_dummy_label": # Assuming dummy keyword implies dummy summary
        assert len(nlp_analysis_from_pipeline['summary']) > 0, "Pipeline summary should not be empty if ASR transcript was processed by non-dummy NLP."
    if truth_transcript_text and truth_keyword_label != "_dummy_label":
        assert len(nlp_analysis_on_truth_transcript['summary']) > 0, "Truth NLP summary should not be empty if truth transcript was processed by non-dummy NLP."
        
    print(f"--- Test for {audio_file_path.name} Completed ---")

# Example of how to run this test with pytest:
# pytest Final-Project/tests/test_pipeline/test_short_sentence_pipeline.py -vs
# The -vs flags are for verbose output and showing print statements. 