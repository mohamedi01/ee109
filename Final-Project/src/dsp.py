"""
raw PCM → pre-emphasis → window → FFT → Mel → log → DCT → MFCC
returns ndarray shape (13, n_frames)
"""
from __future__ import annotations
import numpy as np, soundfile as sf
from scipy.fftpack import dct
from scipy.signal import resample_poly

def _hz2mel(hz):  return 2595.0 * np.log10(1 + hz / 700)
def _mel2hz(m):   return 700.0 * (10**(m / 2595.0) - 1)

def _mel_fb(n_filt, nfft, sr, fmin=0, fmax=None):
    fmax = fmax or sr/2
    mel  = np.linspace(_hz2mel(fmin), _hz2mel(fmax), n_filt+2)
    bins = np.floor((nfft+1) * _mel2hz(mel) / sr).astype(int)
    fb   = np.zeros((n_filt, nfft//2+1))
    for m in range(1, n_filt+1):
        k0,k1,k2 = bins[m-1:m+2]
        fb[m-1,k0:k1] = (np.arange(k0,k1)-k0)/(k1-k0)
        fb[m-1,k1:k2] = (k2-np.arange(k1,k2))/(k2-k1)
    return fb

def _mfcc_frames(sig, sr, pre=0.97, win_ms=25, hop_ms=10,
                 nfft=512, n_mels=40, n_ceps=13):
    sig = np.append(sig[0], sig[1:] - pre*sig[:-1])          # pre-emphasis
    win = int(sr*win_ms*1e-3); hop = int(sr*hop_ms*1e-3)
    nfrm= 1 + int(np.ceil((len(sig)-win)/hop))
    sig = np.pad(sig,(0, nfrm*hop+win-len(sig)))
    idx = (np.tile(np.arange(win),(nfrm,1)) +
           np.tile(np.arange(0,nfrm*hop,hop),(win,1)).T)
    frames = sig[idx] * np.hanning(win)                      # window
    power  = np.abs(np.fft.rfft(frames, nfft))**2 / nfft     # FFT → power
    fb     = _mel_fb(n_mels,nfft,sr)
    spec   = np.where(power @ fb.T == 0,
                      np.finfo(float).eps, power @ fb.T)
    logsp  = np.log(spec)
    ceps   = dct(logsp, type=2, axis=1, norm='ortho')[:,:n_ceps]
    return ceps                                              # (frames, 13)

def wav_to_mfcc(path: str|bytes, target_sr: int = 32_000):
    pcm, sr = sf.read(path, dtype='float32')
    if pcm.ndim == 2: pcm = pcm.mean(1)                      # stereo → mono
    if sr != target_sr:
        pcm = resample_poly(pcm, target_sr, sr)   # upsample/downsample
        sr  = target_sr
    return _mfcc_frames(pcm, sr).T                           # (13, frames)
