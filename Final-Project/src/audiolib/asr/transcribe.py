"""
transcribe.py – Handles audio transcription for both short and long clips.

For short clips (<= 30s), it processes the audio directly.
For long clips (> 30s), it uses a sliding window approach with overlap,
then merges the transcribed segments.
"""
from __future__ import annotations
import tempfile
import numpy as np
from pathlib import Path
from typing import Union
import soundfile as sf
import torch
import torchaudio

# Assuming wav_to_logmel is in audiolib.dsp
# If wav_to_logmel is adapted, its import might change or it might have a new name
# for handling data directly. For now, we'll assume it can take a path or data.
from audiolib.dsp.mel import wav_to_logmel
from .whisper_features import transcribe_features

# Constants for long transcription
DEFAULT_ASR_SAMPLE_RATE = 16_000 # Specific name for ASR pipeline's target rate
WIN_SECONDS = 30
CONTEXT_SECONDS = 3 # Context on each side of the window for overlap
WIN_SAMPLES = WIN_SECONDS * DEFAULT_ASR_SAMPLE_RATE
CONTEXT_SAMPLES = CONTEXT_SECONDS * DEFAULT_ASR_SAMPLE_RATE

def _merge(prev: str, new: str, min_overlap_words: int = 6, max_initial_garbage_words: int = 5) -> str:
    """
    Drop leading words in *new* that duplicate the tail of *prev*.
    robust against a few initial garbage words in *new* before the true overlap.
    """
    a_words = prev.split()
    b_words = new.split()

    if not a_words: return new
    if not b_words: return prev

    max_k_search = min(len(a_words), 40)
    for k in range(max_k_search, min_overlap_words - 1, -1):
        if len(a_words) < k: continue
        suffix_a = a_words[-k:]
        if len(b_words) >= k and suffix_a == b_words[:k]:
            return prev + (" " + " ".join(b_words[k:]) if b_words[k:] else "")
        for j in range(1, max_initial_garbage_words + 1):
            if len(b_words) >= j + k and suffix_a == b_words[j : j+k]:
                return prev + (" " + " ".join(b_words[j+k:]) if b_words[j+k:] else "")
    return prev + " " + new


def transcribe_audio_file(
    audio_path: Union[str, Path],
    device: str = "cpu"
) -> str:
    """
    Transcribes audio file, handling both short and long durations.
    Relies on audiolib.dsp.wav_to_logmel.

    Returns: The transcribed text.
    """
    audio_path_obj = Path(audio_path)
    # File existence check is implicitly handled by sf.info and sf.read

    info = sf.info(str(audio_path_obj))
    duration_seconds = info.duration # Not directly used after this, but good for context

    # Load audio. Expecting mono .wav, so wav_data should be 1D.
    wav_data, sr_loaded = sf.read(str(audio_path_obj), dtype="float32", always_2d=False)

    # Ensure wav_data is 1D
    if wav_data.ndim > 1:
        # Attempt to select the first channel if stereo, or raise error for >2 channels
        if wav_data.ndim == 2 and wav_data.shape[1] > 0:
            # print(f"Warning: Audio file {audio_path_obj} appears to be stereo. Using only the first channel.")
            wav_data = wav_data[:, 0] 
        else:
            raise ValueError(f"Audio file {audio_path_obj} has unsupported shape {wav_data.shape}. Expected mono audio (1D array).")

    wav_tensor = torch.from_numpy(wav_data) 

    if sr_loaded != DEFAULT_ASR_SAMPLE_RATE:
        # Resample 
        wav_data_resampled_tensor = torchaudio.functional.resample(
            wav_tensor, sr_loaded, DEFAULT_ASR_SAMPLE_RATE
        )
    else:
        wav_data_resampled_tensor = wav_tensor
    
    wav_data_resampled_np = wav_data_resampled_tensor.numpy() 

    texts: list[str] = []
    current_pos_samples = 0
    total_samples = wav_data_resampled_np.shape[0] if wav_data_resampled_np.ndim == 1 else 0


    while current_pos_samples < total_samples:
        chunk_start = current_pos_samples
        chunk_end = current_pos_samples + WIN_SAMPLES
        
        # Extract chunk 
        current_chunk_np = wav_data_resampled_np[chunk_start:chunk_end]

        current_chunk_len = current_chunk_np.shape[0]

        if current_chunk_len < WIN_SAMPLES:
            padding_needed = WIN_SAMPLES - current_chunk_len
            pad_shape = (padding_needed,) # Padding for 1D array
            # Change padding from random noise to zeros
            pad_array = np.zeros(pad_shape, dtype=current_chunk_np.dtype)
            current_chunk_np = np.concatenate([current_chunk_np, pad_array], axis=-1)

        
        # Process the chunk directly without saving to a temporary file
        # current_chunk_np is already 1D float32 and at DEFAULT_ASR_SAMPLE_RATE
        mel_spectrogram = wav_to_logmel(
            audio_data=current_chunk_np, 
            sr_in=DEFAULT_ASR_SAMPLE_RATE, 
            device=device
        )
        
        current_transcript = transcribe_features(mel_spectrogram, device=device).strip()

        if texts:
            texts[-1] = _merge(texts[-1], current_transcript)
        else:
            texts.append(current_transcript)
        
        if chunk_end >= total_samples: # Processed the last part
            break
        
        current_pos_samples += (WIN_SAMPLES - (2 * CONTEXT_SAMPLES))

    return texts[0] if texts else "" 