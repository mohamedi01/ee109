"""
log-Mel feature extractor (80 × T) with optional
  • CMVN (3-second sliding window)
  • Delta & Delta-Delta coefficients
Target sample-rate: 16 kHz (matches Whisper / Conformer).
"""
from __future__ import annotations
import numpy as np, torch, torchaudio
from scipy.ndimage import uniform_filter1d, gaussian_filter1d

# --- static transform -------------------------------------------------
_MEL = torchaudio.transforms.MelSpectrogram(
        sample_rate=16_000,
        n_fft=400, hop_length=160,        # 25-ms window, 10-ms hop
        n_mels=80, f_min=20, f_max=7600,
        power=2.0)

# --- helpers ----------------------------------------------------------
def _cmvn(feat: np.ndarray, max_win: int = 300):
    """
    Cepstral/feature mean & variance normalisation.
    When utterance is shorter than the window, fall back to global μ/σ.
    `feat` shape: (F, T)
    returns     : (F, T)
    """
    F, T = feat.shape
    win = min(max_win, T)                     # avoid broadcasting mismatch
    # per-coeff running mean / var with uniform_filter1d
    mean = uniform_filter1d(feat, size=win, axis=1, mode="nearest")
    sqr  = uniform_filter1d(feat**2, size=win, axis=1, mode="nearest")
    var  = np.maximum(sqr - mean**2, 1e-9)
    return (feat - mean) / np.sqrt(var)

def _add_deltas(base: np.ndarray):
    delta  = gaussian_filter1d(base, 1, 1, axis=1, mode="nearest")
    accel  = gaussian_filter1d(base, 1, 2, axis=1, mode="nearest")
    return np.vstack([base, delta, accel])   # 240 × T

# --- public API -------------------------------------------------------
def wav_to_logmel(path: str, cmvn: bool = True, deltas: bool = False) -> np.ndarray:
    """Return (F, T) float32 – 80 or 240 rows depending on `deltas`."""
    wav, sr = torchaudio.load(path)
    if sr != 16_000:
        wav = torchaudio.functional.resample(wav, sr, 16_000)
    mel = torch.clamp(_MEL(wav)[0], 1e-5).log().numpy()    # (80, T)

    if cmvn:
        mel = _cmvn(mel)
    if deltas:
        mel = _add_deltas(mel)
    return mel.astype("float32")
