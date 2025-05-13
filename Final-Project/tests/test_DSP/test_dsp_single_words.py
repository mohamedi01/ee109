import numpy as np, glob
from audiolib.dsp.mel import wav_to_logmel
from pathlib import Path

# Test data: list of audio file paths (relative to project root)
# These files should exist in your data/numbers directory.
# Using Path objects for better path manipulation
DATA_DIR_NUMBERS = Path("data/single_words/")

AUDIO_FILES_NUMBERS = [
    DATA_DIR_NUMBERS / "0_jackson_0.wav",
    DATA_DIR_NUMBERS / "1_jackson_0.wav",
]

def test_logmel_shapes():
    for wav in AUDIO_FILES_NUMBERS:
        feat = wav_to_logmel(wav)          # (80, T)
        assert feat.shape[0] in (80, 240)
        assert np.isfinite(feat).all(), f"NaNs in {wav}"
