"""
Tests the NLP module (summarize_text) directly using pre-existing transcript files
from long sentences.
"""
import pytest
from pathlib import Path
from audiolib.nlp import summarize_text

TRANSCRIPT_DIR_LONG_SENTENCES = Path("data/transcripts/long_sentences/")
TEST_TRANSCRIPT_FILES_LONG = list(TRANSCRIPT_DIR_LONG_SENTENCES.glob("*.txt"))

if not TEST_TRANSCRIPT_FILES_LONG:
    print(f"Warning: No .txt files found in {TRANSCRIPT_DIR_LONG_SENTENCES}. Tests in test_nlp_on_long_sentence_transcripts.py may be skipped.")

@pytest.mark.parametrize("transcript_file_path", TEST_TRANSCRIPT_FILES_LONG)
def test_nlp_on_long_sentence_transcript_smoke(transcript_file_path: Path):
    """Smoke test for summarize_text using a long sentence transcript file."""
    if not transcript_file_path.exists():
        pytest.skip(f"Transcript file not found: {transcript_file_path}")

    with open(transcript_file_path, 'r', encoding='utf-8') as f:
        text_content = f.read().strip()
    
    if not text_content:
        pytest.skip(f"Transcript file is empty: {transcript_file_path}")

    print(f"\n--- Testing NLP summarize_text on transcript (long sentence): {transcript_file_path.name} ---")
    print(f"Transcript content (first 100 chars): {text_content[:100]}...")

    summary_output = summarize_text(text_content, device="cpu")

    assert isinstance(summary_output, str)
    assert len(summary_output) > 0, "Summary should not be empty if input text is not empty."
    # If dummy summarizer is used, summary will be the input text.
    # For long transcripts, a real summarizer should produce a summary shorter than the original.
    if text_content != summary_output: # Check if a non-dummy summarizer might have run
        assert len(summary_output) < len(text_content), \
            "For long texts and a real summarizer, summary should be shorter than the original text."

    print(f"Summary for {transcript_file_path.name}: {summary_output}") 