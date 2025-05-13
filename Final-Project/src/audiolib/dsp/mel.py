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
_MEL = torchaudio.transforms.MelSpectrogram(
    sample_rate = 16_000,
    n_fft       = 400,
    hop_length  = 160,
    win_length  = 400,
    window_fn   = torch.hann_window,
    n_mels      = 80,
    f_min       = 0,
    f_max       = 8_000,
    power       = 2.0,
    center      = True,          # Changed from False to True - Whisper uses center=True
    pad_mode    = "reflect",
)

def _scale(m):
    return (np.maximum(m, -4.0) + 4.0) / 4.0        # floor –4 dB → [0,1]

def wav_to_logmel(path: str | Path) -> np.ndarray:
    wav, sr = sf.read(path, dtype="float32")
    x = torch.from_numpy(wav.squeeze())

    # resample anything →16 kHz
    if sr != 16_000:
        x = torchaudio.functional.resample(x, sr, 16_000)

    # Whisper expects 30 seconds of audio (480,000 samples at 16kHz)
    N_SAMPLES = 480_000
    if len(x) > N_SAMPLES:
        x = x[:N_SAMPLES]
    else:
        # Pad with zeros at the end
        x = torch.nn.functional.pad(x, (0, N_SAMPLES - len(x)))

    mel_power = _MEL(x) + 1e-10                     # 80×T power spec
    
    # Ensure exactly 3000 frames (Whisper's requirement)
    if mel_power.shape[1] == 3001:
        mel_power = mel_power[:, :3000]
    
    mel_db    = torch.log10(mel_power).numpy()      # log10(power)
    return _scale(mel_db).astype(np.float32)
