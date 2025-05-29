import numpy as np
from scipy.signal import stft
from audiolib.dsp.mel_gold import create_mel_filterbank, load_audio

# Constants for LUT-based log10, must match Spatial kernel
N_LUT_LOG10 = 128
X_LUT_LOG10 = np.linspace(1.0, 10.0, N_LUT_LOG10, endpoint=True).astype(np.float32)
Y_LUT_LOG10 = np.log10(X_LUT_LOG10).astype(np.float32)

def _lut_log10_scalar(value: float) -> float:
    """Helper to compute log10 for a scalar using LUT and linear interpolation."""
    v_abs = np.abs(value)
    v_safe = np.maximum(v_abs, 1e-10) # Avoid log(0)

    decade_val = 0.0
    norm_val = v_safe
    
    # Determine decade and normalize v_safe to [1, 10) range for LUT
    if norm_val < 1.0: # Input is < 1.0
        # Iteratively find decade for values < 1.0
        while norm_val < 1.0 and decade_val > -10: # Cap decade to avoid excessive loops for tiny numbers
            norm_val *= 10.0
            decade_val -= 1.0
        if norm_val < 1.0: # If still less than 1 after max negative decade, clamp to 1.0 (log10(1)=0)
            norm_val = 1.0 
    
    # Iteratively find decade for values >= 10.0
    while norm_val >= 10.0:
        norm_val /= 10.0
        decade_val += 1.0

    # Find index j such that X_LUT_LOG10[j] <= norm_val < X_LUT_LOG10[j+1]
    # np.searchsorted returns the insertion point.
    # If norm_val is exactly X_LUT_LOG10[k], searchsorted(..., side='right') gives k+1.
    # So, (np.searchsorted(X_LUT_LOG10, norm_val, side='right') - 1) gives k.
    j_idx = np.searchsorted(X_LUT_LOG10, norm_val, side='right') - 1
    
    # Clip j_idx to ensure X_LUT_LOG10[j_idx+1] is a valid index
    j_idx = np.clip(j_idx, 0, N_LUT_LOG10 - 2) # N_LUT_LOG10-2 because we access j_idx+1

    x_low = X_LUT_LOG10[j_idx]
    y_low = Y_LUT_LOG10[j_idx]
    x_high = X_LUT_LOG10[j_idx+1]
    y_high = Y_LUT_LOG10[j_idx+1]

    # Linear interpolation
    dx = x_high - x_low
    if dx > 1e-9: # Avoid division by zero
        log10_of_norm_val = y_low + (norm_val - x_low) * (y_high - y_low) / dx
    else: # If x_high and x_low are ~the same (should only happen if norm_val is exactly at a LUT point)
        log10_of_norm_val = y_low 
            
    return decade_val + log10_of_norm_val

def python_lut_log10(values: np.ndarray) -> np.ndarray:
    """Computes log10 of input array elements using LUT and linear interpolation, matching Spatial LogCompress."""
    # Using np.vectorize to apply the scalar function to each element
    vectorized_log10 = np.vectorize(_lut_log10_scalar, otypes=[np.float32])
    return vectorized_log10(values)

def simulate_quantization_for_hw_equivalent(audio: np.ndarray) -> np.ndarray:
    """Mimic Whisper's int16 quantization in float32 for hardware equivalent pipeline input."""
    # Ensure input is float for multiplication
    audio_float = audio.astype(np.float32)
    scaled = np.clip(audio_float * 32767.0, -32768.0, 32767.0)
    return scaled.astype(np.int16).astype(np.float32) / 32768.0

def hw_equivalent_logmel(audio_np: np.ndarray, sr: int, 
                         n_mels: int = 80, n_fft: int = 400, 
                         hop_length: int = 160, 
                         fmin: float = 0.0, fmax: float = None, # fmax=None for librosa default, or sr/2
                         dynamic_range_db: float = 8.0,
                         return_intermediates: bool = False # New flag
                        ):
    """
    Computes a log-mel spectrogram using Python logic that mirrors the Spatial hardware pipeline.
    STFT -> PowerSpectrum -> MelFilterbank -> LogCompress (LUT) -> WhisperScale
    """
    if fmax is None:
        fmax = sr / 2.0

    # 0. Simulate quantization (as done in spatial_dsp.py before its STFT)
    audio_quantized = simulate_quantization_for_hw_equivalent(audio_np)

    # 1. STFT and Power Spectrum stage (Python equivalent of PowerSpectrum kernel)
    # Ensure audio is float32, as Spatial kernels operate on floats
    # audio_np_float32 = audio_np.astype(np.float32) # Now use audio_quantized

    # STFT parameters
    nperseg = n_fft
    noverlap = n_fft - hop_length
    
    # scipy.signal.stft with window='hann'
    # The key is this specific STFT is what PowerSpectrum Spatial kernel was fed (via real/imag parts).
    f, t, Zxx = stft(audio_quantized, fs=sr, nperseg=nperseg, noverlap=noverlap, nfft=n_fft, 
                     window='hann') # Uses scipy defaults for boundary/padded, to match spatial_dsp.py's STFT call
    
    power = (Zxx.real**2 + Zxx.imag**2).astype(np.float32)
    
    # Scaling factor used in spatial_dsp.py to match Python STFT power to FPGA PowerSpectrum input scaling.
    # This implies the Spatial PowerSpectrum worked on effectively scaled complex inputs.
    SCALE_FACTOR = 50000.0 
    power_scaled = power * SCALE_FACTOR # (num_freq_bins, num_frames)

    # 2. Mel Filterbank stage (Python equivalent of MelFilterbank kernel)
    # Uses the same create_mel_filterbank that generated 'fpga_io/mel_filterbank.csv'
    mel_mat = create_mel_filterbank(n_fft=n_fft, n_mels=n_mels, sr=sr, fmin=fmin, fmax=fmax)
    
    mel_power = np.dot(mel_mat, power_scaled) # (n_mels, n_bins) x (n_bins, n_frames) -> (n_mels, n_frames)

    # 3. Log Compression stage (Python equivalent of LogCompressCORDIC kernel)
    # This uses the LUT-based log10 and then dynamic range clamping.
    log_mel_power_unclamped = python_lut_log10(mel_power)

    # Dynamic range clamping (after log10, as in Spatial LogCompress)
    # Max is typically taken per frame in streaming, but for a full spectrogram, 
    # Whisper often refers to a global max or recent max.
    # The Spatial LogCompress CORDIC applied it based on max of its current input buffer.
    # For a full spectrogram, let's do it per frame to match typical processing.
    if log_mel_power_unclamped.ndim == 1: # Single frame input
        current_log_max = np.max(log_mel_power_unclamped)
    else: # Multi-frame input (n_mels, n_frames)
        current_log_max = np.max(log_mel_power_unclamped, axis=0, keepdims=True) # Max over mels for each frame

    log_mel_power_clamped = np.maximum(log_mel_power_unclamped, current_log_max - dynamic_range_db)

    # 4. Whisper Scaling stage (Python equivalent of WhisperScale kernel)
    # Input is log_mel_power_clamped.
    # The Spatial WhisperScale kernel's logic (and its verified Python gold in spatial_dsp.py):
    # (input - max(input) + dynRange) / dynRange  which is (input - max(input))/dynRange + 1.0
    # then clip [0,1]
    
    if log_mel_power_clamped.ndim == 1: # Single frame
        max_for_scaling = np.max(log_mel_power_clamped)
    else: # Multi-frame (n_mels, n_frames)
        # Whisper scaling is typically applied based on the max of the *entire segment* or a running max.
        # The Python gold in spatial_dsp.py took max over the first frame it processed.
        # For consistency with that validated path when comparing frame-by-frame, use per-frame max here.
        max_for_scaling = np.max(log_mel_power_clamped, axis=0, keepdims=True) # Max per frame

    scaled_log_mel = (log_mel_power_clamped - max_for_scaling) / dynamic_range_db + 1.0
    scaled_log_mel_clipped = np.clip(scaled_log_mel, 0.0, 1.0)
    
    if return_intermediates:
        return {
            "final": scaled_log_mel_clipped,
            "stft_power_scaled": power_scaled, # (bins, frames)
            "mel_power": mel_power, # (mels, frames)
            "log_mel_unclamped": log_mel_power_unclamped, # (mels, frames)
            "log_mel_clamped": log_mel_power_clamped, # (mels, frames)
            "Zxx": Zxx # For direct STFT comparison if needed
        }
    else:
        return scaled_log_mel_clipped

if __name__ == '__main__':
    # Example usage:
    # Create a dummy audio signal
    sr_test = 16000
    duration = 1.0  # seconds
    frequency = 440  # Hz (A4 note)
    dummy_audio = 0.5 * np.sin(2 * np.pi * frequency * np.arange(int(sr_test * duration)) / sr_test)
    dummy_audio = dummy_audio.astype(np.float32)

    print(f"Running test with dummy audio: shape={dummy_audio.shape}, sr={sr_test}")

    # Process with hw_equivalent_logmel
    results_dict = hw_equivalent_logmel(dummy_audio, sr=sr_test, return_intermediates=True)
    log_mel_spec = results_dict["final"]
    print(f"Output log-mel spectrogram shape: {log_mel_spec.shape}")
    print(f"Output log-mel values sample (first 5 of first mel band): {log_mel_spec[0, :5]}")
    print(f"Output min: {np.min(log_mel_spec)}, max: {np.max(log_mel_spec)}")

    # Test with an actual audio file (requires a file path)
    # You would typically load your audio file here using something like:
    # audio_data, sr_data = load_audio("path/to/your/audio.wav")
    # if sr_data != 16000:
    #     raise ValueError("Audio file must be 16kHz for this example matching Whisper settings")
    # log_mel_from_file = hw_equivalent_logmel(audio_data, sr=16000)
    # print(f"Output from file log-mel spectrogram shape: {log_mel_from_file.shape}")

    # Test the log10 function
    test_log_inputs = np.array([0.00001, 0.001, 0.01, 0.1, 1, 5, 10, 50, 100, 9.99, 0.0000000001, 1e-12], dtype=np.float32)
    py_log_outputs = python_lut_log10(test_log_inputs)
    np_log_outputs = np.log10(np.maximum(test_log_inputs, 1e-10)) # np.log10 for comparison (with safegaurd)
    
    print("\n--- LUT log10 Test ---")
    for i, val_in in enumerate(test_log_inputs):
        print(f"Input: {val_in:.10f}, LUT log10: {py_log_outputs[i]:.6f}, np.log10: {np_log_outputs[i]:.6f}")

    # Check if LUT log10 matches numpy's log10 closely
    # Allow for some tolerance due to LUT interpolation
    if np.allclose(py_log_outputs, np_log_outputs, atol=1e-2, rtol=1e-2): # Looser tolerance for LUT
         print("LUT log10 matches np.log10 within tolerance.")
    else:
         print("LUT log10 does NOT match np.log10 within tolerance.")
         diff = np.abs(py_log_outputs - np_log_outputs)
         print(f"Max absolute difference: {np.max(diff)}")

    # Test case from spatial_dsp.py output (first frame of harvard_m.wav)
    # This is the input to LogCompress in spatial_dsp.py (melfb_output_fpga)
    # Sampled from [DEBUG] MelFilterbank FPGA output (LogCompress input) sample values: [0.00925545 0.00558635 0.00122357 0.00076319 0.00238311]
    test_melfb_output_sample = np.array([0.00925545, 0.00558635, 0.00122357, 0.00076319, 0.00238311], dtype=np.float32)
    log_melfb_lut = python_lut_log10(test_melfb_output_sample)
    
    # Corresponding raw log output from Spatial LogCompress (from debug prints)
    # [DEBUG] LogCompress Raw FPGA output sample: [-2.0336058 -2.2528794 -2.912463  -3.1173742 -2.6229033]
    # [DEBUG] LogCompress Raw Gold output sample: [-2.0336027 -2.2528722 -2.91237   -3.1173694 -2.6228552]
    # Let's use the python gold from spatial_dsp, as that's what our LUT log10 should match
    expected_log_output_sample_gold = np.array([-2.0336027, -2.2528722, -2.91237, -3.1173694, -2.6228552], dtype=np.float32)

    print("\n--- LUT log10 Test on melfb_output_sample ---")
    for i in range(len(test_melfb_output_sample)):
        print(f"Input: {test_melfb_output_sample[i]:.8f}, LUT log10: {log_melfb_lut[i]:.6f}, Expected Gold: {expected_log_output_sample_gold[i]:.6f}")
    
    if np.allclose(log_melfb_lut, expected_log_output_sample_gold, atol=1e-3, rtol=1e-3): # Tighter for specific values
        print("LUT log10 matches expected gold log output for sample data within tolerance.")
    else:
        print("LUT log10 does NOT match expected gold log output for sample data.")
        diff = np.abs(log_melfb_lut - expected_log_output_sample_gold)
        print(f"Max absolute difference: {np.max(diff)}")
        print(f"Differences: {diff}") 