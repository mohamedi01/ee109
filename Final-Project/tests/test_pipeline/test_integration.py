import pytest
from pathlib import Path
import numpy as np # Added for numerical comparisons and loading .npy
import json # For pretty printing in smoke test

from audiolib.pipeline import process_audio_to_nlp
from audiolib.dsp.mel import wav_to_logmel # Added for direct DSP testing

# Define the root of your project if this test is run from a different CWD
# For simplicity, assuming tests are run from the Final-Project directory
# or paths are correctly resolved by your test runner.
PROJECT_ROOT = Path(__file__).resolve().parent.parent.parent # Adjust if necessary
SAMPLE_AUDIO_PATH = PROJECT_ROOT / "data" / "short_sentences" / "harvard_f.wav"
# Path for the golden Mel spectrogram
GOLDEN_MEL_PATH = Path(__file__).resolve().parent / "golden_data" / "harvard_f_mel.npy"

# Placeholder - USER SHOULD VERIFY/PROVIDE THIS
GOLDEN_TRANSCRIPT_HARVARD_F = "the birch canoe slid on the smooth planks"

@pytest.fixture(scope="module")
def audio_file_path() -> Path:
    """Fixture to provide the path to a sample audio file."""
    path = SAMPLE_AUDIO_PATH
    if not path.exists():
        pytest.skip(f"Sample audio file not found: {path}")
    return path

@pytest.fixture(scope="module")
def golden_mel_spectrogram() -> np.ndarray:
    if not GOLDEN_MEL_PATH.exists():
        pytest.skip(f"Golden Mel spectrogram not found: {GOLDEN_MEL_PATH}. Please generate and place it there.")
    return np.load(GOLDEN_MEL_PATH)

def test_process_audio_to_nlp_smoke(audio_file_path: Path):
    """Smoke test for the full audio_to_nlp pipeline."""
    result = process_audio_to_nlp(audio_path=audio_file_path, device="cpu")
    print("\n--- Pipeline Output ---")
    print(json.dumps(result, indent=4))
    print("--- End Pipeline Output ---")

    assert isinstance(result, dict)
    assert "transcript" in result
    assert isinstance(result["transcript"], str)
    
    assert "nlp_analysis" in result
    nlp_analysis = result["nlp_analysis"]
    assert isinstance(nlp_analysis, dict)
    
    # Check for NLP analysis components
    # assert "keyword" in nlp_analysis
    # assert isinstance(nlp_analysis["keyword"], tuple)
    # assert len(nlp_analysis["keyword"]) == 2
    # assert isinstance(nlp_analysis["keyword"][0], str)       # label
    # assert isinstance(nlp_analysis["keyword"][1], float)    # confidence

    # assert "topic" in nlp_analysis
    # assert isinstance(nlp_analysis["topic"], tuple)
    # assert len(nlp_analysis["topic"]) == 2
    # assert isinstance(nlp_analysis["topic"][0], str)         # label
    # assert isinstance(nlp_analysis["topic"][1], float)      # confidence

    assert "summary" in nlp_analysis
    assert isinstance(nlp_analysis["summary"], str)

    # Basic check: ensure transcript and summary are not empty if ASR/NLP worked
    # This is a smoke test, so we're not checking for correctness of content yet
    if result["transcript"]:
        assert len(result["transcript"]) > 0 # Assuming non-empty transcript implies some processing
    # If transcript is empty, summary from dummy NLP might also be empty, which is fine.
    # If actual models are used and transcript is produced, summary should ideally not be empty.
    if result["transcript"] and nlp_analysis["summary"] != result["transcript"]:
         # The dummy summarizer returns the input text. If it's not the input text, 
         # it implies a real summarizer might have run or there's a non-empty summary.
        assert len(nlp_analysis["summary"]) > 0 

def test_pipeline_detailed_checks(audio_file_path: Path, golden_mel_spectrogram: np.ndarray):
    """Detailed checks for DSP, ASR, and Summary components via the pipeline and direct calls."""
    
    # 1. Test DSP component (wav_to_logmel)
    # Generate Mel spectrogram directly
    generated_mel = wav_to_logmel(path=audio_file_path, device="cpu")
    assert isinstance(generated_mel, np.ndarray), "wav_to_logmel should return a NumPy array"
    assert generated_mel.ndim == 2, "Mel spectrogram should be 2D (n_mels, T)"
    assert generated_mel.shape[0] == 80, "Number of Mel bands should be 80"
    # Compare with golden Mel spectrogram
    assert np.allclose(generated_mel, golden_mel_spectrogram, atol=1e-5), \
        "Generated Mel spectrogram differs significantly from the golden version."

    # Run the full pipeline to get ASR and NLP results
    pipeline_result = process_audio_to_nlp(audio_path=audio_file_path, device="cpu")
    transcript = pipeline_result["transcript"]
    nlp_analysis = pipeline_result["nlp_analysis"]
    summary = nlp_analysis["summary"]

    # 2. Test ASR component (Transcript correctness)
    assert isinstance(transcript, str)
    # Basic check: non-empty transcript expected for this audio
    assert len(transcript) > 0, "Transcript should not be empty for harvard_f.wav"
    
    # More specific check (case-insensitive, punctuation-agnostic comparison might be better)
    # For now, let's check for containment or equality after lowercasing
    normalized_transcript = transcript.lower().strip()
    normalized_golden_transcript = GOLDEN_TRANSCRIPT_HARVARD_F.lower().strip()
    # This check is quite strict. Depending on ASR model, may need adjustment (e.g., WER)
    assert normalized_golden_transcript in normalized_transcript, \
        f"Golden transcript substring '{normalized_golden_transcript}' not found in generated transcript: '{normalized_transcript}'"

    # 3. Test Summary Quality (Basic Checks)
    assert isinstance(summary, str)
    if transcript: # Only makes sense if transcript is not empty
        # Check if summary is shorter (will fail if dummy summarizer is used as it echoes input)
        # Note: This assertion assumes a real summarizer is intended to be active.
        # If dummy_summarizer is active, summary == transcript, so this will FAIL.
        # Consider making this conditional or adjusting based on whether dummy models are expected.
        assert len(summary) <= len(transcript), \
            "Summary should be shorter than or equal to the transcript. (This may fail with dummy summarizer)"
        
        # Check if summary is different from transcript (also sensitive to dummy summarizer)
        if len(summary) < len(transcript): # A stronger check if it's truly shorter
             assert summary != transcript, \
                "Summary should be different from the transcript if it's shorter. (This may fail with dummy summarizer)"
        
        # If a real summarizer is expected, and it sometimes returns the original for short texts:
        # For a longer text like harvard_f, we expect a change unless the summarizer is a dummy.
        if nlp_analysis.get("_is_dummy_summarizer", False): # Imaginary flag, adapt to your NLP module
            pass # Skip stringent summary checks if dummy
        elif len(transcript) > 50: # Arbitrary length to expect summarization
             assert summary != transcript or len(summary) < len(transcript),\
                "For non-trivial inputs, summary should differ or be shorter than transcript (may fail with dummy summarizer)"

    print("\n--- Detailed Test Output ---")
    print(f"Generated Transcript: {transcript}")
    print(f"Generated Summary: {summary}")
    print("--- End Detailed Test Output ---") 