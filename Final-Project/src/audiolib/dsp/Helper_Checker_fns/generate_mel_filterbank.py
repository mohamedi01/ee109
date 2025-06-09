from audiolib.dsp.mel_gold import create_mel_filterbank, DEFAULT_SAMPLE_RATE, DEFAULT_N_FFT, DEFAULT_N_MELS, DEFAULT_F_MIN, DEFAULT_F_MAX
import numpy as np
import os

mel_fb = create_mel_filterbank(
    n_fft=DEFAULT_N_FFT,
    n_mels=DEFAULT_N_MELS,
    sr=DEFAULT_SAMPLE_RATE,
    fmin=DEFAULT_F_MIN,
    fmax=DEFAULT_F_MAX
)

os.makedirs("fpga_io", exist_ok=True)
np.savetxt("fpga_io/mel_filterbank.csv", mel_fb, delimiter=",")
print("mel_filterbank.csv regenerated with shape:", mel_fb.shape)