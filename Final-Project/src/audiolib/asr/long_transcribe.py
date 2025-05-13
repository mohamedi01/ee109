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
def _merge(prev: str, new: str, min_overlap_words: int = 6) -> str:
    """Drop leading words in *new* that duplicate the tail of *prev*."""
    a, b = prev.split(), new.split()
    max_k = min(len(a), len(b), 40)           # cap search len to 40 words
    for k in range(max_k, min_overlap_words - 1, -1):
        if a[-k:] == b[:k]:
            return prev + " " + " ".join(b[k:])
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
