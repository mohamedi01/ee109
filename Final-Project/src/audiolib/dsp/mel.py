"""
Whisper-compatible log-Mel front-end
────────────────────────────────────────────────────────────
Input   : mono WAV / FLAC / OGG / MP3 — *any* sample-rate ≤ 48 kHz
Output  : float32 ndarray, shape (80, T)

Pipeline:
    1. Resample to 16 kHz (if needed)
    2. 25 ms / 10 ms Hann-window STFT (400-pt FFT)
    3. 80-band Mel filterbank  (0 – 8 kHz)
    4. log10(power + 1e-10)
    5. Whisper scaling   y = (max(x, −4) + 4) / 4   → roughly [0 … 1]

Parameters:
    - n_mels: Number of Mel filter banks (default: 80)
    - sample_rate: Target sample rate (default: 16kHz)
    - n_fft: FFT size (default: 400)
    - hop_length: Number of samples between successive frames (default: 160)
    - f_min: Minimum frequency (default: 0 Hz)
    - f_max: Maximum frequency (default: 8000 Hz)

No CMVN, no deltas — exactly what Whisper models were trained on.
"""

from pathlib import Path
from typing import Union, Optional
import numpy as np
import torch
import torchaudio
import soundfile as sf

# Default configuration
DEFAULT_SAMPLE_RATE = 16_000
DEFAULT_N_FFT = 400
DEFAULT_HOP_LENGTH = 160
DEFAULT_N_MELS = 80
DEFAULT_F_MIN = 0
DEFAULT_F_MAX = 8_000
EPSILON = 1e-10

# ── kernels ──────────────────────────────────────────────────
_MEL_CACHE: dict[torch.device, torchaudio.transforms.MelSpectrogram] = {}

def _get_mel_spec(device: torch.device) -> torchaudio.transforms.MelSpectrogram:
    """
    Return a MelSpectrogram module that lives on `device`, creating
    (and memoising) it the first time we need that device.
    """
    if device not in _MEL_CACHE:
        _MEL_CACHE[device] = torchaudio.transforms.MelSpectrogram(
            sample_rate=DEFAULT_SAMPLE_RATE,
            n_fft=DEFAULT_N_FFT,
            hop_length=DEFAULT_HOP_LENGTH,
            win_length=DEFAULT_N_FFT,
            window_fn=torch.hann_window,
            n_mels=DEFAULT_N_MELS,
            f_min=DEFAULT_F_MIN,
            f_max=DEFAULT_F_MAX,
            power=2.0,
            center=False,
        ).to(device)
    return _MEL_CACHE[device]

def _whisper_scale(mel_db: np.ndarray) -> np.ndarray:
    """
    Apply Whisper-style scaling to the mel spectrogram.
    
    Args:
        mel_db: Input mel spectrogram in dB scale
        
    Returns:
        Scaled mel spectrogram roughly in range [0, 1]
    """
    return (np.maximum(mel_db, -4.0) + 4.0) / 4.0

def wav_to_logmel(
    path: Union[str, Path],
    normalize_audio: bool = True,
    dynamic_range_db: float = 8.0,
    device: Union[str, torch.device] = "cpu",
) -> np.ndarray:
    """
    Convert audio file to log-mel spectrogram using Whisper-compatible settings.
    
    Args:
        path: Path to audio file
        normalize_audio: Whether to normalize audio to [-1, 1] range
        dynamic_range_db: Maximum dynamic range in dB for log compression
        device: Device to use for computation
        
    Returns:
        Log-mel spectrogram as numpy array of shape (n_mels, T)
        
    Raises:
        FileNotFoundError: If audio file doesn't exist
        RuntimeError: If audio file can't be loaded
    """
    if not Path(path).exists():
        raise FileNotFoundError(f"Audio file not found: {path}")
    
    try:
        wav, sr = sf.read(path, dtype="float32")
    except Exception as e:
        raise RuntimeError(f"Failed to load audio file: {e}")
    
    device = torch.device(device)

    x = torch.from_numpy(wav.squeeze()).to(device)
    
    if normalize_audio:
        x = x / (torch.max(torch.abs(x)) + EPSILON)

    # resample any Fs→16 k
    if sr != DEFAULT_SAMPLE_RATE:
        x = torchaudio.functional.resample(x, sr, DEFAULT_SAMPLE_RATE).to(device)

    # reflect-pad by one FFT length on both sides
    x = torch.nn.functional.pad(
        x.unsqueeze(0), 
        (DEFAULT_N_FFT, DEFAULT_N_FFT), 
        mode="reflect"
    ).squeeze(0)

    # Mel power → log10
    mel_power = _get_mel_spec(device)(x) + EPSILON
    log_spec = torch.log10(mel_power)

    # Dynamic range compression
    log_spec = torch.maximum(
        log_spec, 
        log_spec.max() - dynamic_range_db
    )

    mel_db = log_spec.cpu().numpy()
    return _whisper_scale(mel_db).astype(np.float32)