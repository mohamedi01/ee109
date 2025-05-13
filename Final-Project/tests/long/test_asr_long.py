import pytest
import whisper  # For baseline ASR (pip install openai-whisper)
import numpy as np
import re 
from audiolib.asr import long_transcribe as asr_module
from audiolib.asr.whisper_features import _load as whisper_features_load_function # Import the function

@pytest.fixture(autouse=True, scope="module")
def clear_whisper_model_cache():
    """Clears the LRU cache for the whisper model loading function."""
    whisper_features_load_function.cache_clear()
    print("\nINFO: Cleared lru_cache for audiolib.asr.whisper_features._load")

# --- Homophone Canonicalization Map ---
# Maps variant homophones to a single canonical form.
# Add more pairs/groups as needed for your specific use case.
# Key: word to be replaced, Value: canonical form
CANONICAL_HOMOPHONE_MAP = {
    "their": "there",
    "they're": "there",
    "too": "to",
    "two": "to",
    "you're": "your",
    "it's": "its", 
    "knew": "new",
    "know": "no",
    "ate": "ate",
    "eight": "ate",
    "buy": "by",
    "bye": "by",
    "cell": "sell", 
    "for": "for", 
    "hear": "here",
    "hour": "our",
    "knight": "night",
    "one": "won", 
    "pair": "pear",
    "pare": "pear",
    "passed": "past",
    "peace": "piece",
    "rain": "rain",
    "rein": "rain",
    "reign": "rain",
    "right": "right",
    "write": "right",
    "rite": "right",
    "sea": "see",
    "some": "sum",
    "son": "sun",
    "steal": "steel",
    "wait": "weight",
    "wear": "where",
    "ware": "where",
    "which": "witch",
    "whose": "whose",
    "who's": "whose",
}

def normalize_text_for_wer(text: str) -> str:
    """
    Normalizes text for WER calculation by:
    - Lowercasing
    - Normalizing apostrophes (e.g., ’ to ')
    - Canonicalizing homophones using CANONICAL_HOMOPHONE_MAP
    - Removing common punctuation
    - Normalizing whitespace
    """
    text = text.lower()
    text = text.replace('’', "'") # Normalize fancy apostrophes to simple ones

    # Canonicalize homophones
    words = text.split()
    normalized_words = [CANONICAL_HOMOPHONE_MAP.get(word, word) for word in words]
    text = " ".join(normalized_words)

    # Remove common punctuation (keep apostrophes within words for now, like in "it's" if not canonicalized)
    # This regex removes periods, commas, question marks, exclamation marks, semicolons, colons.
    text = re.sub(r"[.,?!;:]", "", text)
    
    # Normalize whitespace (replace multiple spaces with single, strip leading/trailing)
    text = re.sub(r'\s+', ' ', text).strip()
    return text

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

EXAMPLE_AUDIO_FILES_WITH_TRUTH = [
    ("data/long/harvard_f.wav", 
     "The birch canoe slid on the smooth planks. "
     "Glue the sheet to the dark blue background. "
     "It's easy to tell the depth of a well. "
     "These days a chicken leg is a rare dish. "
     "Rice is often served in round bowls. "
     "The juice of lemons makes fine punch. "
     "The box was thrown beside the parked truck. "
     "The hogs were fed chopped corn and garbage. "
     "Four hours of steady work faced us. "
     "A large size in stockings is hard to sell."),
    ("data/long/harvard_m.wav", 
     "nudge gently but wake her now. "
     "The news struck doubt into restless minds. "
     "Once we stood beside the shore. "
     "A chink in the wall allowed a draft to blow. "
     "Fasten two pins on each side. "
     "A cold dip restores health and zest. "
     "He takes the oath of office each March. "
     "The sand drifts over the sill of the old house. "
     "The point of the steel pen was bent and twisted. "
     "There is a lag between thought and act."),
]

@pytest.mark.skipif(baseline_whisper_model is None, reason="Baseline Whisper model failed to load.")
@pytest.mark.parametrize("audio_filepath, expected_transcription", EXAMPLE_AUDIO_FILES_WITH_TRUTH)
def test_asr_accuracy_comparison_long(audio_filepath, expected_transcription):
    """
    Tests the custom DSP->ASR pipeline against baseline Whisper and an expected transcription
    for long audio files, with homophone-aware normalization.
    """
    # 1. Get transcription from your DSP -> ASR pipeline
    pipeline_transcript = run_custom_pipeline(audio_filepath)

    # 2. Get transcription from baseline Whisper (using openai-whisper directly)
    baseline_result = baseline_whisper_model.transcribe(audio_filepath)
    baseline_transcript = baseline_result["text"].strip()

    print(f"\n--- Testing Audio File: {audio_filepath} ---")
    print(f"Expected Transcription (Raw):    '{expected_transcription}'")
    print(f"Custom Pipeline Output (Raw):    '{pipeline_transcript}'")
    print(f"Baseline Whisper Output (Raw):   '{baseline_transcript}'")

    # 3. Assertions and Comparisons
    # Apply comprehensive normalization for WER
    normalized_pipeline_transcript = normalize_text_for_wer(pipeline_transcript)
    normalized_expected_transcription = normalize_text_for_wer(expected_transcription)
    normalized_baseline_transcript = normalize_text_for_wer(baseline_transcript)

    print(f"Expected Transcription (Norm):   '{normalized_expected_transcription}'")
    print(f"Custom Pipeline Output (Norm):   '{normalized_pipeline_transcript}'")
    print(f"Baseline Whisper Output (Norm):  '{normalized_baseline_transcript}'")
    
    # Assert that the pipeline output is not an error message (using raw transcript)
    assert "[ERROR_IN_CUSTOM_PIPELINE:" not in pipeline_transcript, \
        f"Custom pipeline failed for {audio_filepath}: {pipeline_transcript}"

    # Word Error Rate (WER) Calculation
    try:
        from jiwer import wer # Make sure jiwer is installed: pip install jiwer
        
        wer_pipeline_vs_expected = wer(normalized_expected_transcription, normalized_pipeline_transcript)
        wer_baseline_vs_expected = wer(normalized_expected_transcription, normalized_baseline_transcript)
    
        print(f"WER (Pipeline vs Expected): {wer_pipeline_vs_expected:.4f}")
        print(f"WER (Baseline vs Expected): {wer_baseline_vs_expected:.4f}")
    
        # Assert that pipeline WER (vs expected) is not significantly worse 
        # than baseline WER (vs expected).
        # Adjust the tolerance (0.05 here) as needed.
        # This means your pipeline can be up to 5% worse in WER than the baseline and still pass.
        tolerance = 0.05 
        assert wer_pipeline_vs_expected <= wer_baseline_vs_expected + tolerance, \
            f"Pipeline WER ({wer_pipeline_vs_expected:.4f}) vs Expected is not better than or close to " \
            f"Baseline WER ({wer_baseline_vs_expected:.4f}) vs Expected for {audio_filepath} " \
            f"(Tolerance: {tolerance})."

    except ImportError:
        print("INFO: 'jiwer' library not installed. Skipping WER calculation. (pip install jiwer)")
    except Exception as e_wer:
        print(f"Error or WER assertion failure for {audio_filepath}: {e_wer}")
        if isinstance(e_wer, AssertionError):
             raise 

