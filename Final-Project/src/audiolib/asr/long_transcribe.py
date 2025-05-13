"""
long_transcribe.py – windowed transcription helper for audio >30 s.

• Splits audio into 30-s windows with 3-s left/right context (6 s overlap)
• Runs each chunk through wav_to_logmel → transcribe_features
• Merges adjacent texts, dropping duplicated overlap
"""

from __future__ import annotations
import tempfile, difflib, numpy as np
from pathlib import Path
import soundfile as sf, torch, torchaudio

from audiolib.dsp import wav_to_logmel
from audiolib.asr import transcribe_features

SAMPLE_RATE   = 16_000
WIN_SECONDS   = 30
STRIDE_SEC    = 3
WIN_SAMPLES   = WIN_SECONDS * SAMPLE_RATE
STRIDE_SAMPLES= STRIDE_SEC  * SAMPLE_RATE

# ── helper to remove overlap duplicates ─────────────────────────
def _merge(prev: str, new: str, min_overlap_words: int = 6, max_initial_garbage_words: int = 5) -> str:
    """
    Drop leading words in *new* that duplicate the tail of *prev*.
    Tries to be robust against a few initial garbage words in *new* before the true overlap.
    """
    a_words = prev.split()
    b_words = new.split()

    if not a_words: # If prev is empty, just return new
        return new
    if not b_words: # If new is empty, just return prev
        return prev

    # Max length of overlap to search for, from the tail of a_words
    # Capped at 40 words or length of a_words.
    max_k_search = min(len(a_words), 40)

    # Iterate from longest possible overlap k down to min_overlap_words
    for k in range(max_k_search, min_overlap_words - 1, -1):
        # This check ensures a_words is long enough for the current k.
        # It's implicitly handled by max_k_search being min(len(a_words), 40)
        # and k decreasing, but an explicit check is safer if len(a_words) < min_overlap_words.
        if len(a_words) < k:
            continue
        
        suffix_a = a_words[-k:] # The suffix of `prev` we are looking for in `new`

        # 1. Try to find suffix_a at the very beginning of b_words (original logic)
        if len(b_words) >= k and suffix_a == b_words[:k]:
            # Exact match at the beginning of new text
            return prev + (" " + " ".join(b_words[k:]) if b_words[k:] else "")

        # 2. If exact prefix match fails, try to find suffix_a shifted slightly into b_words
        # This handles cases like b_words = [GARBAGE, GARBAGE, ...OVERLAP_STARTS_HERE...]
        # j is the number of garbage words to skip at the start of b_words.
        # We search for suffix_a starting at b_words[j].
        # The loop for j goes from 1 (skip 1 word) up to max_initial_garbage_words.
        # We also ensure that b_words is long enough for j garbage words + k overlap words.
        for j in range(1, max_initial_garbage_words + 1):
            if len(b_words) >= j + k and suffix_a == b_words[j : j+k]:
                # Found a match after skipping j words in new_text
                # Append the part of new_text that comes after the identified overlap
                return prev + (" " + " ".join(b_words[j+k:]) if b_words[j+k:] else "")
                # We take the first one found (longest k, smallest j for that k due to outer loop order for k,
                # and inner loop order for j)

    # If no suitable overlap found even with skipping initial garbage, fall back to simple concatenation
    return prev + " " + new

# ── public API ──────────────────────────────────────────────────
def transcribe_long_clip(path: str | Path, device: str = "cpu") -> str:
    """Transcribe arbitrarily long audio via sliding-window Whisper."""
    wav, sr = sf.read(str(path), dtype="float32")
    if sr != SAMPLE_RATE:
        wav = torchaudio.functional.resample(torch.from_numpy(wav), sr,
                                             SAMPLE_RATE).numpy()

    texts: list[str] = []
    pos = 0
    while pos < len(wav):
        chunk = wav[pos : pos + WIN_SAMPLES]

        # pad last chunk on right so length = 30 s
        if len(chunk) < WIN_SAMPLES:
            pad = WIN_SAMPLES - len(chunk)
            chunk = np.concatenate(
                [chunk, 1e-4 * np.random.randn(pad).astype(chunk.dtype)]
            )

        # write to temp WAV so wav_to_logmel can load it
        with tempfile.NamedTemporaryFile(suffix=".wav") as tmp:
            sf.write(tmp.name, chunk, SAMPLE_RATE)
            mel = wav_to_logmel(tmp.name)

        txt = transcribe_features(mel, device=device).strip()

        if texts:
            texts[-1] = _merge(texts[-1], txt)
        else:
            texts.append(txt)

        if len(wav) - pos <= WIN_SAMPLES:
            break
        pos += WIN_SAMPLES - 2 * STRIDE_SAMPLES     # advance with overlap

    return texts[0]
