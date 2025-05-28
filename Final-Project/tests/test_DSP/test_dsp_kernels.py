import numpy as np
import pytest

from mel_modular import (
    quantize_int16,
    compute_log_mel_power,
    whisper_scale,
    wav_to_logmel,
)

def test_quantize_int16_roundtrip():
    # pick some test vectors, including edge values
    x = np.array([-1.0, -0.5, 0.0, 0.5, 1.0], dtype=np.float32)
    y = quantize_int16(x)
    # after quant+dequant, should be within 1/32768 of x
    assert np.allclose(x, y, atol=1/32768)

def test_compute_log_mel_power_matches_reference():
    # generate a tiny 16 kHz sine wave
    sr, t = 16_000, 0.025
    x = np.sin(2*np.pi*440*np.linspace(0, t, int(sr*t), endpoint=False))
    # call original monolithic wav_to_logmel
    ref = wav_to_logmel(audio_data=x, sr_in=sr)
    # now go step-by-step
    import torch
    xt = torch.from_numpy(x).float()
    logmel_t = compute_log_mel_power(xt)
    scaled = whisper_scale(logmel_t.cpu().numpy().astype(np.float32))
    assert np.allclose(ref, scaled, atol=1e-5)

def test_whisper_scale():
    arr = np.array([-4.0, -2.0, 0.0, 4.0], dtype=np.float32)
    out = whisper_scale(arr)
    # (x+4)/4
    expected = np.array([0.0, 0.5, 1.0, 2.0], dtype=np.float32)
    assert np.array_equal(out, expected)
