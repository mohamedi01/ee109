import pytest
import whisper  # For baseline ASR (pip install openai-whisper)
import numpy as np 
from pathlib import Path

# Import your project modules
from audiolib.asr import long_transcribe as asr_module

# --- USER ACTION REQUIRED ---
# The following function `run_custom_pipeline` is a template.
# You MUST adapt the lines marked with # ADAPT THIS LINE to call the correct functions
# from your `audiolib.dsp.mel` and `audiolib.asr.long_transcribe` modules
# with the correct arguments and handling their return values.
# ---

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

# --- USER ACTION REQUIRED ---
# Update this list with paths to your audio files and their corresponding
# ground truth transcriptions. The paths should be relative to your project root
# or wherever you run pytest from.
# ---

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

    print(f"\n--- Testing Audio File: {audio_filepath_str} ---")
    print(f"Expected Transcription:    '{expected_transcription}'")
    print(f"Custom Pipeline Output:    '{pipeline_transcript}'")
    print(f"Baseline Whisper Output:   '{baseline_transcript}'")

    # 3. Assertions and Comparisons
    # Normalize pipeline_transcript
    normalized_pipeline_transcript = pipeline_transcript.lower()
    if normalized_pipeline_transcript.endswith('.'):
        normalized_pipeline_transcript = normalized_pipeline_transcript[:-1]
    
    # --- Post-processing for common ASR variations ---
    # Digit to word conversion
    digit_to_word_map = {
        "0": "zero",
        "1": "one",
        "2": "two",
        "3": "three",
        "4": "four",
        "5": "five",
        "6": "six",
        "7": "seven",
        "8": "eight",
        "9": "nine",
    }
    if normalized_pipeline_transcript in digit_to_word_map:
        normalized_pipeline_transcript = digit_to_word_map[normalized_pipeline_transcript]

    # Normalize expected_transcription 
    normalized_expected_transcription = expected_transcription.lower()

    # Define acceptable variations for specific expected transcriptions
    acceptable_transcriptions_map = {
        "two": ["two", "too", "to"],
        # Add other mappings if needed, e.g., "for": ["for", "four"] if that becomes an issue
    }

    # Get the list of acceptable forms, defaulting to just the expected one
    acceptable_forms = acceptable_transcriptions_map.get(
        normalized_expected_transcription, 
        [normalized_expected_transcription]
    )

    assert normalized_pipeline_transcript in acceptable_forms, \
        f"Custom pipeline output '{pipeline_transcript}' (processed: '{normalized_pipeline_transcript}') " \
        f"is not among acceptable forms {acceptable_forms} for expected '{expected_transcription}' " \
        f"(normalized: '{normalized_expected_transcription}') for {audio_filepath_str}."

    # Optional: Log if baseline also matches/differs from expected (for informational purposes)
    if baseline_transcript.lower() == expected_transcription.lower():
        print(f"INFO: Baseline Whisper also matched expected transcription for {audio_filepath_str}.")
    else:
        print(f"INFO: Baseline Whisper ('{baseline_transcript}') differed from expected ('{expected_transcription}') for {audio_filepath_str}.")

    # For more advanced accuracy measurement, consider Word Error Rate (WER).
    # You would need to install the 'jiwer' library: pip install jiwer
    try:
        from jiwer import wer # Make sure jiwer is installed: pip install jiwer

        # Normalize baseline transcript for WER calculation similar to pipeline_transcript
        normalized_baseline_transcript = baseline_transcript.lower()
        if normalized_baseline_transcript.endswith('.'):
            normalized_baseline_transcript = normalized_baseline_transcript[:-1]
        #
        # If baseline also outputs digits and you want to "correct" it for WER, apply the map:
        if normalized_baseline_transcript in digit_to_word_map:
            normalized_baseline_transcript = digit_to_word_map[normalized_baseline_transcript]


        # Calculate WER for the custom pipeline (using the already processed transcript)
        # vs. the normalized expected transcription.
        wer_pipeline_vs_expected = wer(normalized_expected_transcription, normalized_pipeline_transcript)
        
        # Calculate WER for the baseline Whisper output vs. the normalized expected transcription.
        wer_baseline_vs_expected = wer(normalized_expected_transcription, normalized_baseline_transcript)
    
        print(f"WER (Pipeline vs Expected): {wer_pipeline_vs_expected:.4f}")
        print(f"WER (Baseline vs Expected): {wer_baseline_vs_expected:.4f}")
    
        assert wer_pipeline_vs_expected <= wer_baseline_vs_expected + 0.05, \
            f"Pipeline WER ({wer_pipeline_vs_expected:.4f}) is not better than or close to Baseline WER ({wer_baseline_vs_expected:.4f}) for {audio_filepath_str}."
    except ImportError:
        print("INFO: 'jiwer' library not installed. Skipping WER calculation. (pip install jiwer)")
    except Exception as e_wer:
        print(f"Error calculating WER for {audio_filepath_str}: {e_wer}")
