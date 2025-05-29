#!/usr/bin/env python3
import sys
import os
import numpy as np
from subprocess import run, CalledProcessError
from audiolib.dsp.mel_gold import wav_to_logmel, load_audio, create_mel_filterbank

def simulate_quantization(audio: np.ndarray) -> np.ndarray:
    """Mimic Whisper's int16 quantization in float32."""
    scaled = np.clip(audio * 32767, -32768, 32767)
    return scaled.astype(np.int16).astype(np.float32) / 32768.0

def run_spatial_pipeline(audio_path: str):
    # ─── Step 1: Load & resample via your gold loader ─────────────────────────────
    audio_np, sr = load_audio(path=audio_path, audio_data=None, sr_in=None)
    # `load_audio` always resamples to 16 kHz, so no need for an assert here.

    # ─── Step 2: Get the Python gold output ───────────────────────────────────────
    logmel_gold = wav_to_logmel(path=audio_path, audio_data=None, sr_in=None)

    # ─── Step 3: Simulate quantization & save ────────────────────────────────────
    quant = simulate_quantization(audio_np)
    np.save("fpga_io/input_quantized.npy", quant)

    # ─── Step 4: Python STFT → power spectrum → save for Spatial ────────────────
    from scipy.signal import stft
    f, t, Zxx = stft(quant, fs=sr, nperseg=400, noverlap=240, nfft=400, window='hann')
    power = (Zxx.real**2 + Zxx.imag**2).astype(np.float32)

    # Flatten out into one vector (Spatial's PowerSpectrum wants a 1D array)
    flat_real = Zxx.real.astype(np.float32).ravel()
    flat_imag = Zxx.imag.astype(np.float32).ravel()
    flat_power = power.ravel()
    np.save("fpga_io/real.npy", flat_real)
    np.save("fpga_io/imag.npy", flat_imag)

    # ─── Step 5: Export the Mel filterbank ────────────────────────────────────────
    mel_mat = create_mel_filterbank(n_fft=400, n_mels=80, sr=sr)
    np.save("fpga_io/mel_filterbank.npy", mel_mat.astype(np.float32))


    script_dir = os.path.dirname(__file__)
    FPGA_ROOT  = os.path.abspath(os.path.join(script_dir, "..", "..", "..", "fpga"))    
    # ─── Step 6: Invoke each Spatial kernel ──────────────────────────────────────
    try:
        # 1) PowerSpectrum
        power_spectrum_size = flat_real.size
        power_spectrum_cwd = os.path.join(FPGA_ROOT, "PowerSpectrum")
        config_file_path = os.path.join(power_spectrum_cwd, "power_spectrum_config.txt")
        with open(config_file_path, "w") as f:
            f.write(str(power_spectrum_size))
        
        # sbt command no longer takes the size argument directly
        power_spectrum_cmd = 'sbt "run --fpga VCS"' 
        print(f"[DEBUG] Wrote size {power_spectrum_size} to {config_file_path}")
        print(f"[DEBUG] CWD: {power_spectrum_cwd}")
        print(f"[DEBUG] CMD: {power_spectrum_cmd}")
        run(
            power_spectrum_cmd,
            cwd=power_spectrum_cwd,
            shell=True,
            check=True
        )

        # 2) MelFilterbank
        bins   = 400 // 2 + 1
        frames = t.size
        melfilterbank_cwd = os.path.join(FPGA_ROOT, "MelFilterbank")
        melfilterbank_config_path = os.path.join(melfilterbank_cwd, "melfilterbank_config.txt")
        with open(melfilterbank_config_path, "w") as f:
            f.write(f"80\n{bins}\n") # bands is 80, then bins

        melfilterbank_cmd = 'sbt "run --fpga VCS"'
        print(f"[DEBUG] Wrote args to {melfilterbank_config_path}")
        print(f"[DEBUG] CWD: {melfilterbank_cwd}")
        print(f"[DEBUG] CMD: {melfilterbank_cmd}")
        run(
            melfilterbank_cmd,
            cwd=melfilterbank_cwd,
            shell=True,
            check=True
        )

        # 3) LogCompress
        run(
            'sbt "runMain LogCompress {count} 8.0"'.format(count=80 * frames),
            cwd=os.path.join(FPGA_ROOT, "LogCompress"),
            shell=True,
            check=True
        )

        # 4) WhisperScale
        run(
            'sbt "runMain WhisperScale {count}"'.format(count=80 * frames),
            cwd=os.path.join(FPGA_ROOT, "WhisperScale"),
            shell=True,
            check=True
        )

    except CalledProcessError as e:
        print("❌ Spatial kernel failed:", e)
        sys.exit(1)
    # try:
    #     # 1) PowerSpectrum
    #     run(
    #         f'sbt "runMain PowerSpectrum {flat_real.size}"',
    #         cwd=os.path.join(FPGA_ROOT, "PowerSpectrum"),
    #         shell=True,
    #         check=True
    #     )

    #     # 2) MelFilterbank
    #     bins   = 400 // 2 + 1
    #     frames = t.size
    #     run(
    #         f'sbt "runMain MelFilterbank 80 {bins}"',
    #         cwd=os.path.join(FPGA_ROOT, "MelFilterbank"),
    #         shell=True,
    #         check=True
    #     )

    #     # 3) LogCompress
    #     run(
    #         f'sbt "runMain LogCompress {80*frames} 8.0"',
    #         cwd=os.path.join(FPGA_ROOT, "LogCompress"),
    #         shell=True,
    #         check=True
    #     )

    #     # 4) WhisperScale
    #     run(
    #         f'sbt "runMain WhisperScale {80*frames}"',
    #         cwd=os.path.join(FPGA_ROOT, "WhisperScale"),
    #         shell=True,
    #         check=True
    #     )

    # except CalledProcessError as e:
    #     print(" Spatial kernel failed:", e)
    #     sys.exit(1)

    # ─── Step 7: Load final FPGA output & compare ─────────────────────────────────
    logmel_fpga = np.load("fpga_io/fpga_output.npy")   # make sure WhisperScale writes this

    # Compare
    np.testing.assert_allclose(logmel_fpga, logmel_gold, rtol=1e-3, atol=1e-4)
    print("FPGA output matches Python gold model!")

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: spatial_dsp_runner.py path/to/audio.wav")
        sys.exit(1)
    run_spatial_pipeline(sys.argv[1])
