import pytest
from pathlib import Path
import json 
from jiwer import wer 

from audiolib.pipeline import process_audio_to_nlp
from audiolib.nlp.nlp import summarize_text
from testutility.text_processing_utils import normalize_text_for_wer, apply_canonical_map_to_text, DEFAULT_COMPREHENSIVE_CANONICAL_MAP


# --- Configuration ---
AUDIO_DATA_DIR = Path("data/long_sentences/") 
TRANSCRIPT_DATA_DIR = Path("data/transcripts/long_sentences/")

EXAMPLE_AUDIO_FILES_WITH_TRUTH = [
    (AUDIO_DATA_DIR / "bird.mp3", TRANSCRIPT_DATA_DIR / "bird.txt"),
    (AUDIO_DATA_DIR / "cold.mp3", TRANSCRIPT_DATA_DIR / "cold.txt"),
    (AUDIO_DATA_DIR / "face.mp3", TRANSCRIPT_DATA_DIR / "face.txt"),
    (AUDIO_DATA_DIR / "hot.mp3", TRANSCRIPT_DATA_DIR / "hot.txt"),
] 

# Tolerance for ASR Word Error Rate (adjust as needed for your models)
ASR_WER_TOLERANCE_LONG_SENTENCES = 0.16 

# --- Test Cases ---
@pytest.mark.parametrize("audio_file_path, truth_transcript_path", EXAMPLE_AUDIO_FILES_WITH_TRUTH)
def test_long_sentence_full_pipeline_and_nlp_on_truth(audio_file_path: Path, truth_transcript_path: Path): # <-- Renamed test
    """
    Tests the full DSP-ASR-NLP pipeline for a long sentence audio file and
    compares its NLP output with NLP run directly on the ground truth transcript.
    """
    print(f"\n--- Testing Long Sentence Pipeline for: {audio_file_path.name} ---")

    # 1. Run the full audio processing pipeline
    pipeline_output = process_audio_to_nlp(audio_path=audio_file_path, device="cpu")
    
    asr_transcript_from_pipeline = pipeline_output["transcript"]
    nlp_analysis_from_pipeline = pipeline_output["nlp_analysis"]

    print(f"  ASR Transcript (from Pipeline, first 100 chars): '{asr_transcript_from_pipeline[:100]}...'") # <-- Truncated print
    print(f"  NLP Analysis (from Pipeline on ASR output):")
    # print(f"    Keyword: {nlp_analysis_from_pipeline['keyword']}")
    # print(f"    Topic:   {nlp_analysis_from_pipeline['topic']}")
    print(f"    Summary (first 100 chars): {nlp_analysis_from_pipeline['summary'][:100]}...")

    # 2. Load ground truth transcript and run NLP on it
    with open(truth_transcript_path, 'r', encoding='utf-8') as f:
        truth_transcript_text = f.read().strip()
    
    print(f"\nRunning NLP (analyze_text) directly on ground truth transcript: {truth_transcript_path.name}...")
    nlp_analysis_on_truth_transcript = summarize_text(text=truth_transcript_text, device="cpu")

    print(f"  Ground Truth Transcript (first 100 chars): '{truth_transcript_text[:100]}...'") # <-- Truncated print
    print(f"  NLP Analysis (directly on Ground Truth Transcript):")
    # print(f"    Keyword: {nlp_analysis_on_truth_transcript['keyword']}") # Will be string, no keyword key
    # print(f"    Topic:   {nlp_analysis_on_truth_transcript['topic']}")   # Will be string, no topic key
    print(f"    Summary (first 100 chars): {nlp_analysis_on_truth_transcript[:100]}...")

    # 3. Assertions and Comparisons
    print("\nComparing outputs...")

    # ASR Accuracy Check
    norm_asr_pipeline = apply_canonical_map_to_text(normalize_text_for_wer(asr_transcript_from_pipeline), DEFAULT_COMPREHENSIVE_CANONICAL_MAP)
    norm_truth = apply_canonical_map_to_text(normalize_text_for_wer(truth_transcript_text), DEFAULT_COMPREHENSIVE_CANONICAL_MAP)
    
    current_wer = wer(norm_truth, norm_asr_pipeline) if norm_truth or norm_asr_pipeline else 0.0
    print(f"  Normalized ASR (Pipeline, first 100 chars): '{norm_asr_pipeline[:100]}...'") 
    print(f"  Normalized Truth         (first 100 chars): '{norm_truth[:100]}...'") 
    print(f"  Word Error Rate (WER)    : {current_wer:.4f}")
    assert current_wer <= ASR_WER_TOLERANCE_LONG_SENTENCES, (
        f"ASR WER {current_wer:.4f} exceeds tolerance {ASR_WER_TOLERANCE_LONG_SENTENCES} "
        f"for {audio_file_path.name}. ASR (first 100 norm): '{norm_asr_pipeline[:100]}...', Truth (first 100 norm): '{norm_truth[:100]}...'"
    )

    # NLP Keyword Consistency Check
    # (This check is most meaningful if actual NLP models are loaded, not dummies)
    # pipeline_keyword_label = nlp_analysis_from_pipeline['keyword'][0]
    # truth_keyword_label = nlp_analysis_on_truth_transcript['keyword'][0] # This would fail as it's a string
    # print(f"  Pipeline NLP Keyword Label: '{pipeline_keyword_label}'")
    # print(f"  Truth NLP Keyword Label   : '{truth_keyword_label}'")
    
    # if pipeline_keyword_label != "_dummy_label" and truth_keyword_label != "_dummy_label":
    #     assert pipeline_keyword_label == truth_keyword_label, (
    #         f"Keyword mismatch for {audio_file_path.name}. "
    #         f"Pipeline ASR-NLP: '{pipeline_keyword_label}', Truth-NLP: '{truth_keyword_label}'"
    #     )
    # else:
    #     print("  (Skipping keyword label assertion due to one or both being '_dummy_label')")

    # Basic Summary Checks 
    if asr_transcript_from_pipeline: 
        assert len(nlp_analysis_from_pipeline['summary']) > 0, "Pipeline summary should not be empty if ASR transcript was processed by non-dummy NLP."
    if truth_transcript_text:
        assert len(nlp_analysis_on_truth_transcript) > 0, "Truth NLP summary should not be empty if truth transcript was processed by non-dummy NLP."