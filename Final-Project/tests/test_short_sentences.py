"""
tests/test_short_sentences.py
Download a handful of long (>30 s) clips, save them in data/long/,
and run our windowed ASR pipeline (transcribe_long_clip) on each one.
"""
from pathlib import Path
import requests, soundfile as sf
from audiolib.asr import transcribe_long_clip

# ── config ──────────────────────────────────────────────────────
CLIPS = {
    # Harvard female / male sentences (≈8 s each → still “long” by our def)
    "harvard_f.wav":
        "https://huggingface.co/datasets/HarvardSpeech/resolve/main/OSR_us_000_0010_8k.wav",
    "harvard_m.wav":
        "https://huggingface.co/datasets/HarvardSpeech/resolve/main/OSR_us_000_0035_8k.wav",
}

TARGET_DIR = Path("data/long")
TARGET_DIR.mkdir(parents=True, exist_ok=True)

# ── helpers ─────────────────────────────────────────────────────
def download(url: str, dest: Path) -> None:
    """Download *url* to *dest* unless file already exists."""
    if dest.exists():
        return
    print(f"▼ downloading {dest.name}")
    r = requests.get(url, timeout=30)
    r.raise_for_status()
    dest.write_bytes(r.content)

# ── main loop ───────────────────────────────────────────────────
for fname, url in CLIPS.items():
    path = TARGET_DIR / fname
    download(url, path)

    # sanity: print length in seconds
    n_samp, sr = sf.info(path).frames, sf.info(path).samplerate
    dur = n_samp / sr

    text = transcribe_long_clip(str(path))
    print(f"{fname:18s} ({dur:5.1f} s) → {text}…\n")
