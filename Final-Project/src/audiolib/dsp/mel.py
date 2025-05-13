"""
Whisper-compatible log-Mel front-end
────────────────────────────────────────────────────────────
Input   : mono WAV / FLAC / OGG / MP3 — *any* sample-rate ≤ 48 kHz
Output  : float32 ndarray, shape (80, T)

Pipeline
    1. Resample to 16 kHz (if needed)
    2. 25 ms / 10 ms Hann-window STFT (400-pt FFT)
    3. 80-band Mel filterbank  (0 – 8 kHz)
    4. log10(power + 1e-10)
    5. Whisper scaling   y = (max(x, −4) + 4) / 4   → roughly [0 … 1]
No CMVN, no deltas — exactly what Whisper models were trained on.
"""

from pathlib import Path
import numpy as np
import torch, torchaudio, soundfile as sf

# ── kernels ──────────────────────────────────────────────────
_MEL_SPEC = torchaudio.transforms.MelSpectrogram(
    sample_rate = 16_000,
    n_fft       = 400,
    hop_length  = 160,
    win_length  = 400,
    window_fn   = torch.hann_window,
    n_mels      = 80,
    f_min       = 0,
    f_max       = 8_000,
    power       = 2.0,
    center      = False,          # <<<<< key: Whisper uses center=False
)

def _whisper_scale(mel_db: np.ndarray) -> np.ndarray:
    return (np.maximum(mel_db, -4.0) + 4.0) / 4.0

# ── public API ──────────────────────────────────────────────
def wav_to_logmel(path: str | Path) -> np.ndarray:
    wav, sr = sf.read(path, dtype="float32")
    x = torch.from_numpy(wav.squeeze())

    # resample any Fs→16 k
    if sr != 16_000:
        x = torchaudio.functional.resample(x, sr, 16_000)

    # reflect-pad by one FFT length (400 samples) on both sides
    x = torch.nn.functional.pad(x.unsqueeze(0), (400, 400), mode="reflect").squeeze(0)

    # Mel power → log10
    mel_power = _MEL_SPEC(x) + 1e-10
    log_spec  = torch.log10(mel_power)

    log_spec  = torch.maximum(log_spec, log_spec.max() - 8.0)   # ← NEW clamp

    mel_db = log_spec.numpy()

    return _whisper_scale(mel_db).astype(np.float32)