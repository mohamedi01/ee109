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
    print(f"[DEBUG] mel_mat (filterbank weights) original shape: {mel_mat.shape}, min: {mel_mat.min()}, max: {mel_mat.max()}")
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

        # --- PowerSpectrum Gold Check ---
        power_spectrum_fpga_full = np.loadtxt("fpga_io/power_spectrum_output.csv", dtype=np.float32)
        # Assuming power_spectrum_reshaped calculation is correct for extracting first frame
        # power_spectrum_reshaped = power_spectrum_fpga_full.reshape(frames, bins, order='F').T 
        # power_spectrum_fpga_first_frame = power_spectrum_reshaped[:, 0]
        # The script already calculates power_spectrum_first_frame later, let's use that one after it's computed.
        # For now, we'll defer this check until power_spectrum_first_frame is properly isolated.

        # 2) MelFilterbank
        bins   = 400 // 2 + 1 # From STFT nfft=400
        frames = t.size      # From STFT output
        
        # Extract first frame from PowerSpectrum output (it outputs all frames flattened)
        power_spectrum_full_loaded = np.loadtxt("fpga_io/power_spectrum_output.csv", dtype=np.float32)
        print(f"[DEBUG] power_spectrum_full_loaded shape: {power_spectrum_full_loaded.shape}")
        # This was the original reshape logic, ensure it aligns with how STFT output (power_scaled) is structured
        # power_scaled has shape (num_freq_bins, num_frames) -> (201, 1912)
        # So, power_spectrum_full_loaded should be reshaped to (201, num_frames) assuming C-order flatten, or (num_frames, 201) then transpose.
        # If PowerSpectrum Spatial kernel outputs flattened C-order of (bins, frames):
        if power_spectrum_full_loaded.size == bins * frames:
             power_spectrum_fpga_reshaped = power_spectrum_full_loaded.reshape(bins, frames) # C-order default
        else:
            # Fallback or error if size doesn't match, this indicates issue with n in PowerSpectrum
            print(f"[ERROR] Power spectrum output size {power_spectrum_full_loaded.size} does not match expected {bins*frames}")
            power_spectrum_fpga_reshaped = power_spectrum_full_loaded.reshape(bins, -1) # best guess

        power_spectrum_fpga_first_frame = power_spectrum_fpga_reshaped[:, 0]
        
        # --- PowerSpectrum Gold Check (actual) ---
        gold_power_first_frame = power_scaled[:, 0] # From Python STFT (num_freq_bins,)
        print(f"[DEBUG] PowerSpectrum Check: FPGA output sample: {power_spectrum_fpga_first_frame[:5]}")
        print(f"[DEBUG] PowerSpectrum Check: Gold output sample: {gold_power_first_frame[:5]}")
        np.testing.assert_allclose(power_spectrum_fpga_first_frame, gold_power_first_frame, \
                                   rtol=1e-3, atol=1e-5, err_msg="PowerSpectrum output mismatch with gold STFT power")
        print("[INFO] PowerSpectrum FPGA output matches Python gold STFT power.")
        # Save just the first frame for MelFilterbank (already done later, but this is what's used)
        # np.savetxt("fpga_io/power_spectrum_first_frame.csv", power_spectrum_fpga_first_frame, fmt='%e')
        
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

        # DEBUG: Save MelFilterbank output (input to LogCompress)
        melfb_output_fpga = np.loadtxt("fpga_io/melfilterbank_output.csv", dtype=np.float32)
        print(f"[DEBUG] MelFilterbank FPGA output (LogCompress input) sample values: {melfb_output_fpga[:5]}")
        np.savetxt("fpga_io/debug_logcompress_input_fpga.csv", melfb_output_fpga, fmt='%e')

        # --- MelFilterbank Gold Check ---
        # Gold MelFilterbank output is dot product of mel_mat and *FPGA's* power spectrum output (which was just verified)
        # This isolates the MelFilterbank kernel.
        gold_melfb_output = np.dot(mel_mat, power_spectrum_fpga_first_frame)
        print(f"[DEBUG] MelFilterbank Check: FPGA output sample: {melfb_output_fpga[:5]}")
        print(f"[DEBUG] MelFilterbank Check: Gold output sample: {gold_melfb_output[:5]}")
        np.testing.assert_allclose(melfb_output_fpga, gold_melfb_output, \
                                   rtol=1e-3, atol=1e-5, err_msg="MelFilterbank output mismatch")
        print("[INFO] MelFilterbank FPGA output matches Python gold.")

        # DEBUG: Python equivalent input to log10 (this uses the original mel_power_gold for general debug)
        gold_log_input_debug = mel_power_gold[:, 0].numpy().clip(min=1e-10) # First frame only
        print(f"[DEBUG] Gold input to log10 (from original gold mel power) sample values: {gold_log_input_debug[:5]}")
        np.savetxt("fpga_io/debug_logcompress_input_gold_orig_mel_power.csv", gold_log_input_debug, fmt='%e')
        
        # 3) LogCompress
        logcompress_cwd = os.path.join(FPGA_ROOT, "LogCompress")
        logcompress_cfg = os.path.join(logcompress_cwd, "logcompress_config.txt")
        n_elements = 80  # Just one frame for now
        with open(logcompress_cfg, "w") as f:
            f.write(f"{n_elements}\n8.0")
        
        # --- Generate CORDIC constants for LogCompress (N=24) ---
        N = 24 # Increased from 16
        # K[j] = atanh(2**(-s_j)) where s_j = j for j = 1...N
        # shifts[j] = 2**(-s_j)
        s_j = np.arange(1, N + 1).astype(np.float32) # s_j from 1 to N
        
        two_neg_s_j = 2.0 ** -s_j
        
        # Calculate CORDIC gain for atanh: P_N = product(sqrt(1 - (2^-s_j)^2)) for j=1 to N
        # cordic_gain = np.prod(np.sqrt(1.0 - (two_neg_s_j)**2))
        # print(f"[DEBUG] CORDIC atanh gain P_N: {cordic_gain}")
        
        # K_constants should be atanh(2^-s_j)
        K_constants = np.arctanh(two_neg_s_j).astype(np.float32)
        
        consts_path = os.path.join(logcompress_cwd, "logcompress_consts.csv")
        np.savetxt(consts_path, K_constants, delimiter=",") # These are atanh(2^-s_j)
        print(f"[DEBUG] Wrote CORDIC constants (atanh(2^-s_j)) to {consts_path}")
        
        # shifts table (2^-s_j)
        shifts_table = two_neg_s_j.astype(np.float32)
        shifts_path = os.path.join(logcompress_cwd, "logcompress_shifts.csv")
        np.savetxt(shifts_path, shifts_table, delimiter=",") # These are 2^-s_j
        print(f"[DEBUG] Wrote shift factors (2^-s_j) to {shifts_path}")

        # --- Generate log10 LUT for LogCompress ---
        N_LUT_LOG10 = 128 # Number of points in the LUT, increased from 32
        # Create LUT for x in [1.0, 10.0). We need N_LUT_LOG10 points.
        # The last point will be slightly less than 10.0 to simplify interpolation logic for inputs approaching 10.0
        # Or, ensure x_lut covers up to 10.0 inclusive if the interpolation logic handles boundary conditions well.
        # Let's make it inclusive for now.
        x_lut_log10 = np.linspace(1.0, 10.0, N_LUT_LOG10, endpoint=True).astype(np.float32)
        y_lut_log10 = np.log10(x_lut_log10).astype(np.float32)

        x_lut_path = os.path.join(logcompress_cwd, "log10_x_lut.csv")
        y_lut_path = os.path.join(logcompress_cwd, "log10_y_lut.csv")
        np.savetxt(x_lut_path, x_lut_log10, delimiter=",")
        np.savetxt(y_lut_path, y_lut_log10, delimiter=",")
        print(f"[DEBUG] Wrote log10 LUT (x points) to {x_lut_path} ({N_LUT_LOG10} points)")
        print(f"[DEBUG] Wrote log10 LUT (y points) to {y_lut_path}")

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

        # DEBUG: Load LogCompress output
        logcompress_output_fpga_from_file = np.loadtxt("fpga_io/logcompress_output.csv", dtype=np.float32)
        print(f"[DEBUG] LogCompress FPGA output sample values: {logcompress_output_fpga_from_file[:5]}")
        np.savetxt("fpga_io/debug_logcompress_output_fpga_raw.csv", logcompress_output_fpga_from_file, fmt='%e')
        
        # --- LogCompress Gold Check ---
        # Input to LogCompress gold check is the verified output from FPGA MelFilterbank stage
        logcompress_input_for_gold_check = np.maximum(melfb_output_fpga, 1e-10) # Clamp to avoid log(0)
        gold_log10_raw_values = np.log10(logcompress_input_for_gold_check)
        
        # --- Intermediate Check: Raw Log10 values (before dynamic range) ---
        logcompress_output_raw_fpga = np.loadtxt("fpga_io/logcompress_output_raw_fpga.csv", dtype=np.float32)
        print(f"[DEBUG] LogCompress Raw FPGA output sample: {logcompress_output_raw_fpga[:5]}")
        print(f"[DEBUG] LogCompress Raw Gold output sample: {gold_log10_raw_values[:5]}")
        np.testing.assert_allclose(logcompress_output_raw_fpga, gold_log10_raw_values, \
                                   rtol=1e-3, atol=1e-4, err_msg="LogCompress RAW (pre-dynamic range) output mismatch")
        print("[INFO] LogCompress FPGA RAW (pre-dynamic range) output matches Python gold log10.")
        # --- End Intermediate Check ---
        
        gold_log_max = np.max(gold_log10_raw_values) # Use gold raw for consistent gold max calculation
        DYN_RANGE_DB = 8.0 # Should match what's passed to LogCompress kernel if it uses it
        gold_logcompress_output = np.maximum(gold_log10_raw_values, gold_log_max - DYN_RANGE_DB)
        
        print(f"[DEBUG] LogCompress Check: FPGA output sample: {logcompress_output_fpga_from_file[:5]}")
        print(f"[DEBUG] LogCompress Check: Gold output sample: {gold_logcompress_output[:5]}")
        np.testing.assert_allclose(logcompress_output_fpga_from_file, gold_logcompress_output, \
                                   rtol=1e-3, atol=1e-4, err_msg="LogCompress output mismatch")
        print("[INFO] LogCompress FPGA output matches Python gold.")

        # DEBUG: Python equivalent log10 output (before dynamic range, for general debug)
        # gold_log10_output_raw_debug = np.log10(gold_log_input_debug) # Uses original gold mel power
        # print(f"[DEBUG] Gold log10 output (before dyn range, from original gold mel power) sample values: {gold_log10_output_raw_debug[:5]}")
        # np.savetxt("fpga_io/debug_logcompress_output_gold_orig_raw.csv", gold_log10_output_raw_debug, fmt='%e')
        
        # 4) WhisperScale
        whisper_cwd = os.path.join(FPGA_ROOT, "WhisperScale")
        whisper_cfg_path = os.path.join(whisper_cwd, "whisperscale_config.txt") 
        DYN_RANGE_DB_FOR_WHISPER = 8.0
        with open(whisper_cfg_path, "w") as f:
            f.write(f"{n_elements}\n{DYN_RANGE_DB_FOR_WHISPER}") 
        print(f"[DEBUG] Wrote config to {whisper_cfg_path} for WhisperScale: n_elements={n_elements}, dynRange={DYN_RANGE_DB_FOR_WHISPER}")
        
        # Step 1: Compile with --sim flag (also runs if already compiled)
        print("[DEBUG] Compiling/Running WhisperScale in software simulation mode...")
        whisperscale_cmd = 'sbt "run --sim"' # Reads args from config file
        run(whisperscale_cmd, cwd=whisper_cwd, shell=True, check=True)
        
        # Step 2: Run the generated simulation
        # sbt "run --sim" typically compiles and generates run.sh.
        # We need to explicitly run the simulation script from its generated directory.
        gen_dir = os.path.join(whisper_cwd, "gen", "WhisperScale")
        print(f"[DEBUG] Running generated simulation in {gen_dir} for WhisperScale")
        run("chmod +x run.sh", cwd=gen_dir, shell=True, check=True)
        run(f"./run.sh", cwd=gen_dir, shell=True, check=True) # Assumes run.sh uses config file inside WhisperScale.scala
        
        # --- WhisperScale Gold Check --- 
        # Input to WhisperScale FPGA is logcompress_output_fpga_from_file (verified to match gold_logcompress_output)
        fpga_whisperscale_output = np.loadtxt("fpga_io/whisperscale_output.csv", dtype=np.float32)

        # Python gold standard for WhisperScale:
        # L = gold_logcompress_output (this is the output of LogCompress, already clipped by dyn_range there)
        l_max_gold = np.max(gold_logcompress_output)
        l_centered_gold = gold_logcompress_output - l_max_gold
        # Clip L_centered to [-DYN_RANGE_DB, 0.0]. Since L_centered <=0, effectively max(L_centered, -DYN_RANGE_DB)
        l_clipped_gold = np.maximum(l_centered_gold, -DYN_RANGE_DB_FOR_WHISPER)
        gold_whisperscale_final_output = (l_clipped_gold + DYN_RANGE_DB_FOR_WHISPER) / DYN_RANGE_DB_FOR_WHISPER

        print(f"[DEBUG] WhisperScale Check: FPGA output sample: {fpga_whisperscale_output[:5]}")
        print(f"[DEBUG] WhisperScale Check: Gold output sample: {gold_whisperscale_final_output[:5]}")
        np.testing.assert_allclose(fpga_whisperscale_output, gold_whisperscale_final_output, \
                                   rtol=1e-3, atol=1e-4, err_msg="WhisperScale output mismatch")
        print("[INFO] WhisperScale FPGA output matches Python gold.")

    except CalledProcessError as e:
        print("❌ Spatial kernel failed:", e)
        sys.exit(1)
        
    # ─── Step 7: Load final FPGA output & compare ─────────────────────────────────
    # This final comparison is against the original wav_to_logmel gold standard.
    # It will only pass if create_mel_filterbank is fixed AND all kernels match their staged gold checks.
    logmel_fpga_full = np.loadtxt("fpga_io/whisperscale_output.csv", dtype=np.float32)
    # Assuming logmel_gold is (n_mels, T) and whisper_scale output is also (n_mels, T)
    # For now, we are processing one frame through the later Spatial kernels
    logmel_fpga_first_frame = logmel_fpga_full 

    logmel_gold_first_frame = logmel_gold[:, 0] # From original wav_to_logmel

    print(f"[DEBUG] Final FPGA LogMel output sample: {logmel_fpga_first_frame[:5]}")
    print(f"[DEBUG] Original Gold LogMel (wav_to_logmel) output sample: {logmel_gold_first_frame[:5]}")

    # --- Recompute Python gold for the first frame using intermediate gold values and original mel_mat ---
    # This re-computation is to have a Python gold standard that follows the exact
    # sequence of operations and intermediate values that the FPGA *should* be getting.
    
    DYN_RANGE_DB = 8.0 # Consistent dynamic range

    # 1. Start with gold_power_first_frame (this is the Python STFT power for the first frame)
    #    This was verified against the output of the Spatial PowerSpectrum kernel.
    # 2. Apply Mel filterbank (using the original, now fixed, mel_mat)
    recalc_mel_power = np.dot(mel_mat, gold_power_first_frame)
    # 3. Log10 (clamp to avoid log(0)), this matches LogCompress input stage logic
    recalc_log_mel_power = np.log10(np.maximum(recalc_mel_power, 1e-10))
    # 4. Dynamic Range Compression (as in LogCompress kernel)
    recalc_log_max = np.max(recalc_log_mel_power)
    recalc_log_mel_compressed = np.maximum(recalc_log_mel_power, recalc_log_max - DYN_RANGE_DB)
    # 5. Whisper Scaling (as in WhisperScale kernel: L_scaled = ((L - L.max())_clipped + DYN_RANGE) / DYN_RANGE)
    #    Input to WhisperScale is recalc_log_mel_compressed
    l_max_for_scale = np.max(recalc_log_mel_compressed)
    l_centered = recalc_log_mel_compressed - l_max_for_scale
    l_clipped = np.maximum(l_centered, -DYN_RANGE_DB) # Clip to -DYN_RANGE_DB relative to max
    logmel_recalculated_gold_final = (l_clipped + DYN_RANGE_DB) / DYN_RANGE_DB
    
    print(f"[DEBUG] Recalculated Python Gold (using original mel_mat and staged logic) sample: {logmel_recalculated_gold_final[:5]}")

    np.testing.assert_allclose(logmel_fpga_first_frame, logmel_recalculated_gold_final, rtol=1e-3, atol=1e-4, 
                               err_msg="Full pipeline output (FPGA vs. Recalculated Python Gold) mismatch")
    print("[INFO] Full pipeline FPGA output matches Recalculated Python gold model (first frame, using original mel_mat and staged logic).")

    # Final sanity check against the direct output of wav_to_logmel
    np.testing.assert_allclose(logmel_fpga_first_frame, logmel_gold_first_frame, rtol=1e-3, atol=1e-4, 
                               err_msg="Full pipeline output (FPGA vs. original wav_to_logmel Gold) mismatch")
    print("[INFO] Full pipeline FPGA output also matches original wav_to_logmel gold output (first frame).")

    print("───────────────────────────────────────────────────────────────────────────────")
    print(" ✅✅✅ End-to-end test PASSED (FPGA vs. Python Gold) ✅✅✅ ")
    print("───────────────────────────────────────────────────────────────────────────────")

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: spatial_dsp_runner.py path/to/audio.wav")
        sys.exit(1)
    run_spatial_pipeline(sys.argv[1])
