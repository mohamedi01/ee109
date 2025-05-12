import numpy as np, glob
from audiolib.dsp.mel import wav_to_logmel

WAVS = glob.glob("data/numbers/*.wav")

def test_logmel_shapes():
    for wav in WAVS:
        feat = wav_to_logmel(wav)          # (80, T)
        assert feat.shape[0] in (80, 240)
        assert np.isfinite(feat).all(), f"NaNs in {wav}"
