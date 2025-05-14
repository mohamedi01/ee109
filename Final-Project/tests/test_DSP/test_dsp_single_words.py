# Tests the DSP (wav_to_logmel) processing for single word audio files.
# Verifies spectrogram characteristics like shape, dtype, and value range.
import pytest
import numpy as np
from pathlib import Path

from audiolib.dsp.mel import wav_to_logmel
from whisper.audio import load_audio, HOP_LENGTH as WHISPER_HOP_LENGTH, N_FFT as WHISPER_N_FFT

# Discover all .wav files in the directory to be used for testing.
DATA_DIR_SINGLE_WORDS = Path("data/single_words/")
TEST_AUDIO_FILES_SINGLE_WORDS = list(DATA_DIR_SINGLE_WORDS.glob("*.wav"))


@pytest.mark.parametrize("audio_file_path", TEST_AUDIO_FILES_SINGLE_WORDS)
def test_logmel_features_properties(audio_file_path):
    """
    Tests the log-Mel spectrogram features for individual audio files.
    Ensures:
    - The number of Mel bands is as expected.
    - The number of time frames is as expected.
    - All feature values are finite (no NaNs or Infs).
    """
    # Convert Path object to string for wav_to_logmel function
    audio_file_path_str = str(audio_file_path)

    print(f"\n--- Testing DSP on: {audio_file_path_str} ---")
    
    # Load audio to get original sample count at 16kHz for calculating expected frames.
    raw_audio_samples = load_audio(audio_file_path_str) 
    num_samples = raw_audio_samples.shape[0]
    features = wav_to_logmel(audio_file_path_str)

    # 1. Test the number of Mel bands (first dimension of the shape)
    expected_mel_bands = 80
    assert features.shape[0] == expected_mel_bands, \
        f"Expected {expected_mel_bands} Mel bands, but got {features.shape[0]} for {audio_file_path_str}"

    # 2. Calculate and test the number of time frames (second dimension)
    expected_n_frames = (num_samples + WHISPER_N_FFT) // WHISPER_HOP_LENGTH + 1
    
    assert features.shape[1] == expected_n_frames, \
        f"Expected {expected_n_frames} time frames (based on {num_samples} samples, N_FFT {WHISPER_N_FFT}, and hop {WHISPER_HOP_LENGTH}), " \
        f"but got {features.shape[1]} for {audio_file_path_str}"

    # 3. Test that the features matrix has 2 dimensions (Mel bands, Time frames)
    assert features.ndim == 2, \
        f"Expected 2D features matrix, but got {features.ndim} dimensions for {audio_file_path_str}"

    # 5. Test for finite values (no NaNs or infinities)
    assert np.isfinite(features).all(), \
        f"Features contain non-finite values (NaN or Inf) for {audio_file_path_str}"
    
    print(f"Successfully processed {audio_file_path_str}, shape: {features.shape} (Expected frames: {expected_n_frames})")