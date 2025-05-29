#!/usr/bin/env python3
import sys
import os
import numpy as np
from subprocess import run, CalledProcessError, PIPE
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
    print(f"[DEBUG] Gold logmel shape: {logmel_gold.shape}")
    print(f"[DEBUG] Gold logmel first frame min: {logmel_gold[:, 0].min()}, max: {logmel_gold[:, 0].max()}")
    
    # Also compute intermediate steps to debug
    from audiolib.dsp.mel_gold import compute_log_mel_power, quantize_int16, _get_mel_spec
    import torch
    
    # Replicate gold computation steps
    audio_np_q = quantize_int16(audio_np)
    x_torch = torch.from_numpy(audio_np_q).to('cpu')
    
    # Get mel power before log
    mel_spec_transform = _get_mel_spec(torch.device('cpu'))
    mel_power_gold = mel_spec_transform(x_torch)
    print(f"[DEBUG] Gold mel power shape: {mel_power_gold.shape}")
    print(f"[DEBUG] Gold mel power first frame min: {mel_power_gold[:, 0].min()}, max: {mel_power_gold[:, 0].max()}")
    
    # Save first frame for comparison
    np.savetxt("fpga_io/gold_mel_power_first_frame.csv", mel_power_gold[:, 0].numpy(), fmt='%e')
    
    log_mel_before_scale = compute_log_mel_power(x_torch, dynamic_range_db=8.0)
    print(f"[DEBUG] Gold log mel before scale shape: {log_mel_before_scale.shape}")
    print(f"[DEBUG] Gold log mel before scale first frame min: {log_mel_before_scale[:, 0].min()}, max: {log_mel_before_scale[:, 0].max()}")

    # ─── Step 3: Simulate quantization & save ────────────────────────────────────
    quant = simulate_quantization(audio_np)
    np.save("fpga_io/input_quantized.npy", quant)

    # ─── Step 4: Python STFT → power spectrum → save for Spatial ────────────────
    from scipy.signal import stft
    f, t, Zxx = stft(quant, fs=sr, nperseg=400, noverlap=240, nfft=400, window='hann')
    power = (Zxx.real**2 + Zxx.imag**2).astype(np.float32)
    
    # Scale to match torch's normalization (empirically determined)
    # Torch and scipy use different STFT normalizations
    SCALE_FACTOR = 50000.0  # Approximate factor to match torch
    Zxx_scaled = Zxx * np.sqrt(SCALE_FACTOR)
    power_scaled = power * SCALE_FACTOR
    
    print(f"[DEBUG] STFT output Zxx shape: {Zxx.shape}")
    print(f"[DEBUG] Power shape: {power.shape}")
    print(f"[DEBUG] Power scale factor applied: {SCALE_FACTOR}")

    # Flatten out into one vector (Spatial's PowerSpectrum wants a 1D array)
    flat_real = Zxx_scaled.real.astype(np.float32).ravel()
    flat_imag = Zxx_scaled.imag.astype(np.float32).ravel()
    flat_power = power_scaled.ravel()
    print(f"[DEBUG] Flattened real/imag size: {flat_real.size}")
    # Save as CSV for Spatial
    np.savetxt("fpga_io/real.csv", flat_real, fmt='%f')
    np.savetxt("fpga_io/imag.csv", flat_imag, fmt='%f')

    # ─── Step 5: Export the Mel filterbank ────────────────────────────────────────
    mel_mat = create_mel_filterbank(n_fft=400, n_mels=80, sr=sr)
    np.savetxt("fpga_io/mel_filterbank.csv", mel_mat.astype(np.float32), fmt='%f', delimiter=',')
    
    # Save power spectrum for MelFilterbank
    # MelFilterbank expects to process each frame, so save frame-by-frame power spectra
    # np.savetxt("fpga_io/power_bins.csv", power[:, 0].astype(np.float32), fmt='%f')  # First frame for testing

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
        
        # Step 1: Compile with --sim flag
        power_spectrum_cmd = 'sbt "run --sim"' 
        print(f"[DEBUG] Wrote size {power_spectrum_size} to {config_file_path}")
        print(f"[DEBUG] CWD: {power_spectrum_cwd}")
        print(f"[DEBUG] CMD: {power_spectrum_cmd}")
        run(
            power_spectrum_cmd,
            cwd=power_spectrum_cwd,
            shell=True,
            check=True
        )
        
        # Step 2: Run the generated simulation
        gen_dir = os.path.join(power_spectrum_cwd, "gen", "PowerSpectrum")
        print(f"[DEBUG] Running generated simulation in {gen_dir}")
        run("chmod +x run.sh", cwd=gen_dir, shell=True, check=True)
        run(
            "./run.sh",
            cwd=gen_dir,
            shell=True,
            check=True
        )

        # 2) MelFilterbank
        bins   = 400 // 2 + 1
        frames = t.size
        
        # Extract first frame from PowerSpectrum output (it outputs all frames flattened)
        power_spectrum_full = np.loadtxt("fpga_io/power_spectrum_output.csv", dtype=np.float32)
        # Reshape to (bins, frames) and take first frame
        print(f"[DEBUG] power_spectrum_full shape: {power_spectrum_full.shape}")
        print(f"[DEBUG] Expected bins: {bins}, Expected frames: {frames}")
        print(f"[DEBUG] Total elements should be: {bins * frames} = {bins} * {frames} = {bins * frames}")
        # Try Fortran order since STFT might output in column-major order
        power_spectrum_reshaped = power_spectrum_full.reshape(frames, bins, order='F').T
        print(f"[DEBUG] power_spectrum_reshaped shape: {power_spectrum_reshaped.shape}")
        print(f"[DEBUG] First 5 values of reshaped[:, 0]: {power_spectrum_reshaped[:5, 0]}")
        power_spectrum_first_frame = power_spectrum_reshaped[:, 0]
        print(f"[DEBUG] power_spectrum_first_frame shape: {power_spectrum_first_frame.shape}")
        print(f"[DEBUG] Non-zero count: {np.count_nonzero(power_spectrum_first_frame)}")
        print(f"[DEBUG] Min value: {power_spectrum_first_frame.min()}, Max value: {power_spectrum_first_frame.max()}")
        # Save just the first frame for MelFilterbank
        np.savetxt("fpga_io/power_spectrum_first_frame.csv", power_spectrum_first_frame, fmt='%e')
        # Verify what was saved
        saved = np.loadtxt("fpga_io/power_spectrum_first_frame.csv")
        print(f"[DEBUG] Saved file non-zero count: {np.count_nonzero(saved)}")
        
        # TODO: For now, just process the first frame's power spectrum through MelFilterbank
        # In a real implementation, we'd need to process each frame
        # np.savetxt("fpga_io/power_bins.csv", power[:, 0], fmt='%f')  # First frame
        
        melfilterbank_cwd = os.path.join(FPGA_ROOT, "MelFilterbank")
        melfilterbank_config_path = os.path.join(melfilterbank_cwd, "melfilterbank_config.txt")
        with open(melfilterbank_config_path, "w") as f:
            f.write(f"80\n{bins}\n") # bands is 80, then bins

        # Step 1: Compile with --sim flag
        melfilterbank_cmd = 'sbt "run --sim"'
        print(f"[DEBUG] Wrote args to {melfilterbank_config_path}")
        print(f"[DEBUG] CWD: {melfilterbank_cwd}")
        print(f"[DEBUG] CMD: {melfilterbank_cmd}")
        run(
            melfilterbank_cmd,
            cwd=melfilterbank_cwd,
            shell=True,
            check=True
        )
        
        # Step 2: Run the generated simulation
        gen_dir = os.path.join(melfilterbank_cwd, "gen", "MelFilterbank")
        print(f"[DEBUG] Running generated simulation in {gen_dir}")
        run("chmod +x run.sh", cwd=gen_dir, shell=True, check=True)
        run(
            "./run.sh",
            cwd=gen_dir,
            shell=True,
            check=True
        )

        # 3) LogCompress
        logcompress_cwd = os.path.join(FPGA_ROOT, "LogCompress")
        logcompress_cfg = os.path.join(logcompress_cwd, "logcompress_config.txt")
        n_elements = 80  # Just one frame for now
        with open(logcompress_cfg, "w") as f:
            f.write(f"{n_elements}\n8.0")
        
        # --- Generate CORDIC constants for LogCompress (N=16) ---
        N = 16
        # K[j] = exp(2**(-j))
        K = np.exp(2.0 ** -np.arange(N)).astype(np.float32)
        consts_path = os.path.join(logcompress_cwd, "logcompress_consts.csv")
        np.savetxt(consts_path, K, delimiter=",")
        print(f"[DEBUG] Wrote CORDIC constants to {consts_path}")
        
        # --- Generate the shift table 1/(1<<j) so each line is a separate CSV entry ---
        shifts = (1.0 / (1 << np.arange(N))).astype(np.float32)
        shifts_path = os.path.join(logcompress_cwd, "logcompress_shifts.csv")
        np.savetxt(shifts_path, shifts, delimiter=",")
        print(f"[DEBUG] Wrote shift factors to {shifts_path}")        
        
        # Step 1: Compile with --sim flag
        logcompress_cmd = 'sbt "run --sim"'
        print(f"[DEBUG] CWD: {logcompress_cwd}")
        print(f"[DEBUG] CMD: {logcompress_cmd}")
        
        run(
            logcompress_cmd,
            cwd=logcompress_cwd,
            shell=True,
            check=True
        )
        
        # Step 2: Run the generated simulation with arguments
        gen_dir = os.path.join(logcompress_cwd, "gen", "LogCompressCORDIC")
        print(f"[DEBUG] Running generated simulation in {gen_dir} with args: {n_elements} 8.0")
        run("chmod +x run.sh", cwd=gen_dir, shell=True, check=True)
        run(
            f"./run.sh {n_elements} 8.0",
            cwd=gen_dir,
            shell=True,
            check=True
        )

        # 4) WhisperScale
        whisper_cwd = os.path.join(FPGA_ROOT, "WhisperScale")
        whisper_cfg = os.path.join(whisper_cwd, "whisperscale_config.txt")
        with open(whisper_cfg, "w") as f:
            f.write(str(80))  # Just one frame for now
        
        # Step 1: Compile with --sim flag
        print("[DEBUG] Compiling WhisperScale in software simulation mode...")
        result = run(
            'sbt "run --sim"',
            cwd=whisper_cwd,
            shell=True,
            check=True
        )
        
        # Step 2: Run the generated simulation
        gen_dir = os.path.join(whisper_cwd, "gen", "WhisperScale")
        print(f"[DEBUG] Running generated simulation in {gen_dir}")
        run("chmod +x run.sh", cwd=gen_dir, shell=True, check=True)
        run(
            "./run.sh",
            cwd=gen_dir,
            shell=True,
            check=True
        )

    except CalledProcessError as e:
        print("❌ Spatial kernel failed:", e)
        sys.exit(1)
        
    # ─── Step 7: Load final FPGA output & compare ─────────────────────────────────
    # Since we're only processing one frame for now, compare against first frame of gold
    logmel_fpga = np.loadtxt("fpga_io/fpga_output.csv", dtype=np.float32)
    logmel_gold_first_frame = logmel_gold[:, 0]  # First frame only

    # Compare
    print(f"[DEBUG] FPGA output shape: {logmel_fpga.shape}")
    print(f"[DEBUG] Gold first frame shape: {logmel_gold_first_frame.shape}")
    np.testing.assert_allclose(logmel_fpga, logmel_gold_first_frame, rtol=1e-3, atol=1e-4)
    print("FPGA output matches Python gold model (first frame)!")

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: spatial_dsp_runner.py path/to/audio.wav")
        sys.exit(1)
    run_spatial_pipeline(sys.argv[1])
