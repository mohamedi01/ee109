import pytest
import whisper  # For baseline ASR (pip install openai-whisper)
import numpy as np 

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
EXAMPLE_AUDIO_FILES_WITH_TRUTH = [
    # Format: ("path/to/audio.wav", "expected exact transcription")
    ("data/numbers/0_jackson_0.wav", "zero"), # Replace "zero" with actual ground truth
    ("data/numbers/1_jackson_0.wav", "one"),   # Replace "one" with actual ground truth
    ("data/numbers/2_jackson_0.wav", "two"),   # Replace "two" with actual ground truth
    ("data/numbers/3_theo_0.wav", "three"), # Replace "three" with actual ground truth
    ("data/numbers/4_theo_0.wav", "four"),  # Replace "four" with actual ground truth
    ("data/numbers/5_george_0.wav", "five"),# Replace "five" with actual ground truth
    ("data/numbers/6_george_0.wav", "six"), # Replace "six" with actual ground truth
    # Add more files from data/long/ as well if you have ground truth for them
    # ("data/long/harvard_f.wav", "The stale smell of old beer lingers."), # Example, replace with actual
]

@pytest.mark.skipif(baseline_whisper_model is None, reason="Baseline Whisper model failed to load.")
@pytest.mark.parametrize("audio_filepath, expected_transcription", EXAMPLE_AUDIO_FILES_WITH_TRUTH)
def test_asr_accuracy_comparison(audio_filepath, expected_transcription):
    """
    Tests the custom DSP->ASR pipeline against baseline Whisper and an expected transcription.
    """
    # 1. Get transcription from your DSP -> ASR pipeline
    pipeline_transcript = run_custom_pipeline(audio_filepath)

    # 2. Get transcription from baseline Whisper (using openai-whisper directly)
    baseline_result = baseline_whisper_model.transcribe(audio_filepath)
    baseline_transcript = baseline_result["text"].strip()

    print(f"\n--- Testing Audio File: {audio_filepath} ---")
    print(f"Expected Transcription:    '{expected_transcription}'")
    print(f"Custom Pipeline Output:    '{pipeline_transcript}'")
    print(f"Baseline Whisper Output:   '{baseline_transcript}'")

    # 3. Assertions and Comparisons
    # Normalize pipeline_transcript further for comparison
    normalized_pipeline_transcript = pipeline_transcript.lower()
    if normalized_pipeline_transcript.endswith('.'):
        normalized_pipeline_transcript = normalized_pipeline_transcript[:-1]
    
    # Normalize expected_transcription as well (though yours are already clean)
    normalized_expected_transcription = expected_transcription.lower()

    assert normalized_pipeline_transcript == normalized_expected_transcription, \
        f"Custom pipeline output '{pipeline_transcript}' (normalized: '{normalized_pipeline_transcript}') does not match expected '{expected_transcription}' (normalized: '{normalized_expected_transcription}') for {audio_filepath}."

    # Optional: Log if baseline also matches/differs from expected (for informational purposes)
    if baseline_transcript.lower() == expected_transcription.lower():
        print(f"INFO: Baseline Whisper also matched expected transcription for {audio_filepath}.")
    else:
        print(f"INFO: Baseline Whisper ('{baseline_transcript}') differed from expected ('{expected_transcription}') for {audio_filepath}.")

    # For more advanced accuracy measurement, consider Word Error Rate (WER).
    # You would need to install the 'jiwer' library: pip install jiwer
    # try:
    #     from jiwer import wer
    #     wer_pipeline_vs_expected = wer(expected_transcription, pipeline_transcript)
    #     wer_baseline_vs_expected = wer(expected_transcription, baseline_transcript)
    #
    #     print(f"WER (Pipeline vs Expected): {wer_pipeline_vs_expected:.4f}")
    #     print(f"WER (Baseline vs Expected): {wer_baseline_vs_expected:.4f}")
    #
    #     # Example assertion: pipeline WER should be less than or equal to baseline WER
    #     # assert wer_pipeline_vs_expected <= wer_baseline_vs_expected, \
    #     #    f"Pipeline WER ({wer_pipeline_vs_expected:.4f}) is not better than or equal to Baseline WER ({wer_baseline_vs_expected:.4f}) for {audio_filepath}."
    # except ImportError:
    #     print("INFO: 'jiwer' library not installed. Skipping WER calculation. (pip install jiwer)")
    # except Exception as e_wer:
    #     print(f"Error calculating WER: {e_wer}")

# To run these tests:
# 1. Ensure pytest is installed: `pip install pytest openai-whisper`
# 2. Navigate to your project root directory (`Final-Project/`) in the terminal.
# 3. Run pytest: `pytest`
#
# Make sure the paths in `EXAMPLE_AUDIO_FILES_WITH_TRUTH` are correct
# relative to where you run `pytest` (usually the project root).
