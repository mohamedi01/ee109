"""
Whisper-compatible log-Mel front-end (modularized)
────────────────────────────────────────────────────────────
Input   : mono WAV / FLAC / OGG / MP3 — any sample-rate ≤ 48 kHz
Output  : float32 ndarray, shape (80, T)

Pipeline kernels are isolated into individual functions:
    - load_audio
    - resample_audio
    - quantize_int16
    - compute_log_mel_power
    - whisper_scale
    - wav_to_logmel orchestrates the pipeline

Fixed Pipeline Parameters:
    - n_mels: Number of Mel filter banks (80)
    - sample_rate: Target sample rate (16kHz)
    - n_fft: FFT size (400)
    - hop_length: Number of samples between successive frames (160)
    - f_min: Minimum frequency (0 Hz)
    - f_max: Maximum frequency (8000 Hz)

No CMVN, no deltas — exactly what Whisper models were trained on.
"""
from pathlib import Path
from typing import Union, Optional, Tuple
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

# Cache for MelSpectrogram transform objects to avoid re-creation per device.
_MEL_CACHE: dict[torch.device, torchaudio.transforms.MelSpectrogram] = {}

# ── Isolated Kernels ─────────────────────────────────────────────────

def load_audio(path: Optional[Union[str, Path]] = None,
               audio_data: Optional[np.ndarray] = None,
               sr_in: Optional[int] = None) -> Tuple[np.ndarray, int]:
    """
    Load or accept raw audio data as float32 mono.
    Returns (audio_np, sample_rate).
    """
    if audio_data is not None and sr_in is not None:
        if not isinstance(audio_data, np.ndarray) or audio_data.ndim != 1:
            raise ValueError(f"audio_data must be 1D numpy array, got {audio_data.shape}")
        data = audio_data.astype(np.float32)
        return data, sr_in
    if path is None:
        raise ValueError("Either 'path' or 'audio_data' (with 'sr_in') must be provided")
    path = Path(path)
    if not path.exists():
        raise FileNotFoundError(f"Audio file not found: {path}")
    audio_np, sr = sf.read(str(path), dtype="float32", always_2d=False)
    if audio_np.ndim != 1:
        raise ValueError(f"Expected mono audio, got shape {audio_np.shape}")
    return audio_np, sr


def resample_audio(x: torch.Tensor, orig_sr: int,
                   target_sr: int = DEFAULT_SAMPLE_RATE) -> torch.Tensor:
    """
    Resample torch tensor to target_sr.
    """
    if orig_sr == target_sr:
        return x
    return torchaudio.functional.resample(x, orig_sr, target_sr)


def quantize_int16(x_np: np.ndarray) -> np.ndarray:
    """
    Simulate PCM S16LE quantization and scale back to float32.
    """
    # Scale float32 to int16 range
    scaled = x_np * 32767.0
    clipped = np.clip(scaled, -32768.0, 32767.0)
    int16 = clipped.astype(np.int16)
    return int16.astype(np.float32) / 32768.0


def _get_mel_spec(device: torch.device) -> torchaudio.transforms.MelSpectrogram:
    """
    Return a MelSpectrogram module for log-Mel power computation.
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
            center=True,
        ).to(device)
    return _MEL_CACHE[device]


def compute_log_mel_power(x: torch.Tensor,
                          dynamic_range_db: float = 8.0) -> torch.Tensor:
    """
    Given a 1D torch audio tensor at 16kHz, compute the log-Mel spectrogram:
      1) Mel power
      2) log10 clamp
      3) dynamic-range compression
    Returns a torch.Tensor of shape (n_mels, T).
    """
    mel_power = _get_mel_spec(x.device)(x)
    log_spec = torch.log10(torch.clamp(mel_power, min=EPSILON))
    # dynamic-range compression
    max_val = log_spec.max()
    log_spec = torch.maximum(log_spec, max_val - dynamic_range_db)
    return log_spec


def whisper_scale(mel_db: np.ndarray) -> np.ndarray:
    """
    Apply Whisper-specific scaling: (x + 4) / 4
    """
    return (mel_db + 4.0) / 4.0


# ── High-Level Pipeline ─────────────────────────────────────────────────

def wav_to_logmel(
    path: Union[str, Path, None] = None,
    audio_data: Optional[np.ndarray] = None,
    sr_in: Optional[int] = None,
    dynamic_range_db: float = 8.0,
    device: Union[str, torch.device] = "cpu",
) -> np.ndarray:
    """
    Convert audio file or audio data to Whisper-compatible log-Mel spectrogram.
    """
    # 1) Load
    audio_np, sr = load_audio(path, audio_data, sr_in)
    # 2) To torch
    device = torch.device(device)
    x = torch.from_numpy(audio_np).to(device)
    # 3) Resample
    x = resample_audio(x, sr, DEFAULT_SAMPLE_RATE)
    # 4) Simulate int16 quantization (NumPy round-trip)
    x_np = x.cpu().numpy()
    x_q = quantize_int16(x_np)
    x = torch.from_numpy(x_q).to(device)
    # 5) Compute log-Mel
    log_spec = compute_log_mel_power(x, dynamic_range_db)
    # 6) To NumPy and scale
    mel_db = log_spec.cpu().numpy().astype(np.float32)
    return whisper_scale(mel_db).astype(np.float32)
