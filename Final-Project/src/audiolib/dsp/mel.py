"""
Whisper-compatible log-Mel front-end
────────────────────────────────────────────────────────────
Input   : mono WAV / FLAC / OGG / MP3 — any sample-rate ≤ 48 kHz
Output  : float32 ndarray, shape (80, T)

Pipeline:
    1. Load audio, resample to 16 kHz (if needed).
    2. Simulate int16 quantization: scale to int16 range, clip, cast to int16, then cast back to float32 and rescale (dividing by 32768.0).
    3. 25 ms / 10 ms Hann-window STFT (400-pt FFT), with centered frames.
    4. 80-band Mel filterbank  (0 – 8 kHz)
    5. log10(clamp(power, min=1e-10))
    6. Whisper scaling   y = (x + 4) / 4   (where x is the log-Mel after dynamic range compression)

Fixed Pipeline Parameters:
    The following parameters define the fixed Whisper-compatible audio processing pipeline:
    - n_mels: Number of Mel filter banks (80)
    - sample_rate: Target sample rate (16kHz)
    - n_fft: FFT size (400)
    - hop_length: Number of samples between successive frames (160)
    - f_min: Minimum frequency (0 Hz)
    - f_max: Maximum frequency (8000 Hz)

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

# ── Software kernels ──────────────────────────────────────────────────
# Cache for MelSpectrogram transform objects to avoid re-creation per device.
_MEL_CACHE: dict[torch.device, torchaudio.transforms.MelSpectrogram] = {}

def _get_mel_spec(device: torch.device) -> torchaudio.transforms.MelSpectrogram:
    """
    Return a MelSpectrogram module that lives on `device`, creating
    it the first time we need that device.
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

def _whisper_scale(mel_db: np.ndarray) -> np.ndarray:
    """
    helper function to apply whisper scaling to the mel spectrogram        
    Returns:
        Scaled mel spectrogram. Note: values can be negative.
    """
    return (mel_db + 4.0) / 4.0

def wav_to_logmel(
    path: Union[str, Path],
    dynamic_range_db: float = 8.0,
    device: Union[str, torch.device] = "cpu",
) -> np.ndarray:
    """
    Convert audio file to log-mel spectrogram using Whisper-compatible settings.
    Audio is loaded as float32. It's then resampled to 16kHz.
    A simulation of int16 quantization (scaling, clipping, casting to int16,
    then casting back to float32 and dividing by 32768.0) is applied to the
    16kHz audio to align with ffmpeg-based processing in Whisper's reference.
    No further peak normalization is applied after this stage.
        
    Returns: Log-mel spectrogram as numpy array of shape (n_mels, T)
    """
    if not Path(path).exists():
        raise FileNotFoundError(f"Audio file not found: {path}")
    
    try:
        wav_data_from_sf, sr = sf.read(path, dtype="float32")
    except Exception as e:
        raise RuntimeError(f"Failed to load audio file: {e}")
    
    device = torch.device(device)
    
    x_tensor_at_original_sr = torch.from_numpy(wav_data_from_sf.squeeze()).to(device)

    # Resample to DEFAULT_SAMPLE_RATE (16kHz) if needed
    if sr != DEFAULT_SAMPLE_RATE:
        x_tensor_16khz = torchaudio.functional.resample(x_tensor_at_original_sr, sr, DEFAULT_SAMPLE_RATE)
    else:
        x_tensor_16khz = x_tensor_at_original_sr
    
    # Simulate pcm_s16le conversion (quantize to int16) and then scale back to float.
    # to match Whisper's load_audio (for best gold results on DSP -> ASR pipeline)
    
    # Convert 16kHz audio to NumPy array to simulate int16 conversion step
    x_np_16khz = x_tensor_16khz.cpu().numpy()
    
    # Scale float32 audio to the effective range of int16
    scaled_to_int16_range = x_np_16khz * 32767.0
    # Clip to ensure values are within int16 range before casting
    clipped_values = np.clip(scaled_to_int16_range, -32768.0, 32767.0)
    # Cast to int16
    audio_as_int16 = clipped_values.astype(np.int16)
    # 4. Scale back to float32, similar to Whisper's division by 32768.0
    final_audio_np = audio_as_int16.astype(np.float32) / 32768.0
    
    # Final audio tensor for DSP pipeline
    x = torch.from_numpy(final_audio_np).to(device)

    # Mel power → log10
    mel_power_raw = _get_mel_spec(device)(x) # Get raw Mel-filtered power
    # Clamp to EPSILON before taking log10
    log_spec = torch.log10(torch.clamp(mel_power_raw, min=EPSILON))

    # Dynamic range compression
    log_spec = torch.maximum(
        log_spec, 
        log_spec.max() - dynamic_range_db
    )

    mel_db = log_spec.cpu().numpy()
    return _whisper_scale(mel_db).astype(np.float32)