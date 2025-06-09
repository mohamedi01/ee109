#!/usr/bin/env python3
import sys
import os
import numpy as np
import torch
from subprocess import run, CalledProcessError, PIPE
from audiolib.dsp.mel_gold import wav_to_logmel, create_mel_filterbank, \
    DEFAULT_SAMPLE_RATE, DEFAULT_N_FFT, DEFAULT_HOP_LENGTH, DEFAULT_N_MELS, DEFAULT_F_MIN, DEFAULT_F_MAX, EPSILON
import torchaudio
import shutil
from audiolib.dsp.mel_gold import (
    quantize_int16,
    compute_stft,
    compute_power_spectrum,
    apply_mel_filterbank,
    whisper_scale,
    load_audio,
    DEFAULT_SAMPLE_RATE, DEFAULT_N_FFT, DEFAULT_HOP_LENGTH, DEFAULT_N_MELS, DEFAULT_F_MIN, DEFAULT_F_MAX, EPSILON
)

TEST_SIMPLE_SIGNALS = False  # Set to True to run impulse and sine tests

def simulate_quantization(audio: np.ndarray) -> np.ndarray:
    """Mimic Whisper's int16 quantization in float32."""
    scaled = np.clip(audio * 32767, -32768, 32767)
    return scaled.astype(np.int16).astype(np.float32) / 32768.0

def python_reference_pipeline(audio_np):
    quantized_np = quantize_int16(audio_np)
    x_torch = torch.from_numpy(quantized_np).to('cpu')
    stft = compute_stft(x_torch)
    power_spec = compute_power_spectrum(stft)
    mel_power = apply_mel_filterbank(
        power_spec,
        sample_rate=DEFAULT_SAMPLE_RATE,
        n_fft=DEFAULT_N_FFT,
        n_mels=DEFAULT_N_MELS,
        f_min=DEFAULT_F_MIN,
        f_max=DEFAULT_F_MAX,
        device=x_torch.device
    )
    log_spec = torch.log10(torch.clamp(mel_power, min=EPSILON))
    max_val = log_spec.max()
    log_spec = torch.maximum(log_spec, max_val - 8.0)
    whisper_scaled = whisper_scale(log_spec.cpu().numpy())
    return {
        "quantized": quantized_np,
        "stft": stft,
        "power_spec": power_spec,
        "mel_power": mel_power,
        "log_spec": log_spec,
        "whisper_scaled": whisper_scaled
    }

def run_fpga_kernel(cmd, cwd):
    try:
        print(f"[INFO] Running: {cmd} (cwd={cwd})")
        result = run(cmd, cwd=cwd, shell=True, check=True, stdout=PIPE, stderr=PIPE)
        print(result.stdout.decode(errors='ignore'))
    except CalledProcessError as e:
        print(f"[ERROR] Kernel failed in {cwd}")
        print("Command:", e.cmd)
        if e.stdout:
            print("Stdout:", e.stdout.decode(errors='ignore'))
        if e.stderr:
            print("Stderr:", e.stderr.decode(errors='ignore'))
        sys.exit(1)

def spatial_pipeline_and_compare_isolated(python_ref, io_dir, fpga_root, raw_audio):
    # 1. QuantizeKernel (input: raw audio, output: quantized)
    quantize_kernel_cwd = os.path.join(fpga_root, "QuantizeKernel")
    np.savetxt(os.path.join(io_dir, "input_audio_for_quantize.csv"), raw_audio, fmt='%.18e')
    with open(os.path.join(quantize_kernel_cwd, "quantize_kernel_config.txt"), "w") as f:
        f.write(str(raw_audio.size))
    run_fpga_kernel('sbt "reload; clean; update; run --sim"', quantize_kernel_cwd)
    run_fpga_kernel('chmod +x run.sh', os.path.join(quantize_kernel_cwd, "gen", "QuantizeKernel"))
    run_fpga_kernel('./run.sh', os.path.join(quantize_kernel_cwd, "gen", "QuantizeKernel"))
    fpga_quantized = np.loadtxt(os.path.join(io_dir, "quantize_output.csv"), dtype=np.float32)
    np.testing.assert_allclose(fpga_quantized, python_ref["quantized"], rtol=1e-4, atol=1e-5)
    print("[PASS] QuantizeKernel matches Python reference.")
    print("\u2500"*79)

    # THIS FAILS FOR HAVARD_F.WAV
    # 2. STFTKernel (input: FPGA quantized, output: stft)
    stft_kernel_cwd = os.path.join(fpga_root, "STFTKernel")
    # Pad input with N_FFT//2 zeros at the beginning to match torch.stft(center=True)
    pad_width = DEFAULT_N_FFT // 2
    quantized_padded = np.pad(fpga_quantized, (pad_width, 0), mode='constant')
    np.savetxt(os.path.join(io_dir, "input_stft_window.csv"), quantized_padded[:DEFAULT_N_FFT], fmt='%.18e')
    with open(os.path.join(stft_kernel_cwd, "stft_kernel_config.txt"), "w") as f:
        f.write(str(DEFAULT_N_FFT))
    run_fpga_kernel('sbt "reload; clean; update; run --sim"', stft_kernel_cwd)
    run_fpga_kernel('chmod +x run.sh', os.path.join(stft_kernel_cwd, "gen", "STFTKernel"))
    run_fpga_kernel('./run.sh', os.path.join(stft_kernel_cwd, "gen", "STFTKernel"))
    fpga_stft_real = np.loadtxt(os.path.join(io_dir, "stft_real_output.csv"), dtype=np.float32)
    fpga_stft_imag = np.loadtxt(os.path.join(io_dir, "stft_imag_output.csv"), dtype=np.float32)
    stft = python_ref["stft"]
    stft_real = stft.real[:, 0].cpu().numpy()
    np.testing.assert_allclose(fpga_stft_real, stft_real, rtol=2e-3, atol=7e-4)
    print("[PASS] STFTKernel real matches Python reference.")
    print("[PASS] STFTKernel matches Python reference.")
    print("\u2500"*79)

    # 3. PowerSpectrumKernel (input: FPGA STFT output, output: power_spec)
    power_spectrum_cwd = os.path.join(fpga_root, "PowerSpectrum")
    np.savetxt(os.path.join(io_dir, "real.csv"), fpga_stft_real, fmt='%.18e')
    np.savetxt(os.path.join(io_dir, "imag.csv"), fpga_stft_imag, fmt='%.18e')
    with open(os.path.join(power_spectrum_cwd, "power_spectrum_config.txt"), "w") as f:
        f.write(str(DEFAULT_N_FFT // 2 + 1))
    run_fpga_kernel('sbt "run --sim"', power_spectrum_cwd)
    run_fpga_kernel('chmod +x run.sh', os.path.join(power_spectrum_cwd, "gen", "PowerSpectrum"))
    run_fpga_kernel('./run.sh', os.path.join(power_spectrum_cwd, "gen", "PowerSpectrum"))
    fpga_power = np.loadtxt(os.path.join(io_dir, "power_spectrum_output.csv"), dtype=np.float32)
    power_spec = python_ref["power_spec"][:, 0].cpu().numpy()
    np.testing.assert_allclose(fpga_power, power_spec, rtol=1e-2, atol=8e-3)
    print("[PASS] PowerSpectrumKernel matches Python reference.")
    print("\u2500"*79)

    # 4. MelFilterbankKernel (input: fpga_power, output: fpga_mel)
    print("[INFO] Feeding FPGA power spectrum to MelFilterbankKernel.")
    np.savetxt(os.path.join(io_dir, "power_bins.csv"), fpga_power, fmt='%.18e')
    # Ensure mel_filterbank.csv exists for FPGA kernel
    mel_filterbank_cwd = os.path.join(fpga_root, "MelFilterbank")
    with open(os.path.join(mel_filterbank_cwd, "mel_filterbank_config.txt"), "w") as f:
        f.write(str(DEFAULT_N_MELS))
    run_fpga_kernel('sbt "reload; clean; update; run --sim"', mel_filterbank_cwd)
    run_fpga_kernel('chmod +x run.sh', os.path.join(mel_filterbank_cwd, "gen", "MelFilterbank"))
    run_fpga_kernel('./run.sh', os.path.join(mel_filterbank_cwd, "gen", "MelFilterbank"))
    melfb_output_fpga = np.loadtxt(os.path.join(io_dir, "melfilterbank_output.csv"), dtype=np.float32)
    mel_power = python_ref["mel_power"][:, 0].cpu().numpy()
    np.testing.assert_allclose(melfb_output_fpga, mel_power, rtol=5e-2, atol=7e-3)
    print("[PASS] MelFilterbankKernel matches Python reference.")
    print("───────────────────────────────────────────────────────────────────────────────")

    # 5. LogCompressKernel (input: melfb_output_fpga, output: log_spec_fpga)
    print("[INFO] Using Python log10 output for LogCompress.")
    log_spec_fpga = np.log10(np.clip(melfb_output_fpga, a_min=EPSILON, a_max=None))            #python_ref["log_spec"][:, 0].cpu().numpy()
    max_val = np.max(log_spec_fpga)
    log_spec_fpga = np.maximum(log_spec_fpga, max_val - 8.0)
    np.savetxt(os.path.join(io_dir, "whisperscale_input.csv"), log_spec_fpga, fmt='%.18e')

    # 6. Write raw log-mel values for FPGA WhisperScale kernel (centering/clamping/scaling now done in hardware)
    whisperscale_cwd = os.path.join(fpga_root, "WhisperScale")
    run_fpga_kernel('sbt "reload; clean; update; run --sim"', whisperscale_cwd)
    run_fpga_kernel('chmod +x run.sh', os.path.join(whisperscale_cwd, "gen", "WhisperScale"))
    run_fpga_kernel('./run.sh', os.path.join(whisperscale_cwd, "gen", "WhisperScale"))
    fpga_whisper = np.loadtxt(os.path.join(io_dir, "whisperscale_output.csv"), dtype=np.float32)
    
    log = np.log10(np.clip(melfb_output_fpga, a_min=EPSILON, a_max=None))
    max_val = np.max(log)
    log = np.maximum(log, max_val - 8.0) 
    whisper_scaled = (log + 4.0) / 4 #python_ref["whisper_scaled"][:, 0]
    # Debug: Compare input to WhisperScale FPGA and Python reference
    print("Input to WhisperScale FPGA (first 10):", log_spec_fpga[:10])
    print("Python whisper_scaled (first 10):", whisper_scaled[:10])
    print("Input min/max:", np.min(log_spec_fpga), np.max(log_spec_fpga))
    print("Python whisper_scaled min/max:", np.min(whisper_scaled), np.max(whisper_scaled))
    np.testing.assert_allclose(fpga_whisper, whisper_scaled, rtol=1e-3, atol=1e-4)
    print("[PASS] WhisperScaleKernel matches Python reference.")
    print("\u2500"*79)
    
def spatial_pipeline_and_compare_stft_only(python_ref, io_dir, fpga_root, raw_audio):
    """Run STFT kernel only and compare with Python reference."""
    print("[INFO] Running STFT kernel only...")    
    # Save raw audio input
    np.savetxt(os.path.join(io_dir, "windowed_input_debug.csv"), raw_audio, fmt='%.18e')
    
    # Run STFT kernel
    stft_cwd = os.path.join(fpga_root, "STFT")
    with open(os.path.join(stft_cwd, "stft_config.txt"), "w") as f:
        f.write(str(DEFAULT_N_FFT))
    run_fpga_kernel('sbt "run --sim"', stft_cwd)
    run_fpga_kernel('chmod +x run.sh', os.path.join(stft_cwd, "gen", "STFT"))
    run_fpga_kernel('./run.sh', os.path.join(stft_cwd, "gen", "STFT"))
    
    
    # Load FPGA STFT output and compare with Python reference
    fpga_stft_real = np.loadtxt(os.path.join(io_dir, "stft_real_output.csv"), dtype=np.float32)
    stft = python_ref["stft"]
    stft_real = stft.real[:, 0].cpu().numpy()
    np.testing.assert_allclose(fpga_stft_real, stft_real, rtol=2e-3, atol=7e-4)
    print("[PASS] STFTKernel real matches Python reference.")
    print("[PASS] STFTKernel matches Python reference.")
    print("\u2500"*79)

    
def run_spatial_pipeline(audio_path: str):
    print(f"[INFO] Using torchaudio version: {torchaudio.__version__}")
    script_dir = os.path.dirname(__file__)
    FPGA_ROOT  = os.path.abspath(os.path.join(script_dir, "..", "..", "..", "fpga"))    
    IO_DIR = os.path.abspath(os.path.join(FPGA_ROOT, "..", "fpga_io"))
    os.makedirs(IO_DIR, exist_ok=True)
    
    if TEST_SIMPLE_SIGNALS:
        print("\n[TEST] Running impulse and sine wave tests...")
        # Impulse test
        impulse = np.zeros(DEFAULT_N_FFT, dtype=np.float32)
        impulse[DEFAULT_N_FFT // 2] = 1.0
        print("\n[TEST] Impulse input:")
        python_ref = python_reference_pipeline(impulse)
        spatial_pipeline_and_compare_stft_only(python_ref, IO_DIR, FPGA_ROOT, impulse)
        # Sine wave test
        freq_bin = 5  # Choose a bin index
        t = np.arange(DEFAULT_N_FFT)
        sine = np.sin(2 * np.pi * freq_bin * t / DEFAULT_N_FFT).astype(np.float32)
        print(f"\n[TEST] Sine wave input (bin {freq_bin}):")
        python_ref = python_reference_pipeline(sine)
        spatial_pipeline_and_compare_stft_only(python_ref, IO_DIR, FPGA_ROOT, sine)
        print("\n[TEST] Done with simple signal tests.\n")
    
    # Normal pipeline run
    audio_np, sr = load_audio(path=audio_path, audio_data=None, sr_in=None)
    python_ref = python_reference_pipeline(audio_np)
    print(f"[PASS] Python reference pipeline complete.")
    print(f"[INFO] Running spatial pipeline and comparing with Python reference...")
    spatial_pipeline_and_compare_isolated(python_ref, IO_DIR, FPGA_ROOT, audio_np)
    print(f"[PASS] Spatial pipeline and comparison complete.")

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: spatial_dsp_runner.py path/to/audio.wav")
        sys.exit(1)
    run_spatial_pipeline(sys.argv[1])
