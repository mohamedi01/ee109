#!/usr/bin/env python3
import sys, os, argparse, json
from pathlib import Path
import numpy as np, torch

from audiolib.dsp.spatial_dsp import run_spatial_pipeline
from audiolib.asr.whisper_features import transcribe_features
from audiolib.nlp.nlp import summarize_text
from audiolib.dsp.mel_gold import DEFAULT_N_MELS

# ── CLI ───────────────────────────────────────────────────────────────
parser = argparse.ArgumentParser(
    description="HW-accelerated DSP → ASR → NLP pipeline")
parser.add_argument("audio_file", type=Path)
parser.add_argument("--device", choices=["cpu", "cuda"], default="cpu")
parser.add_argument("--fpga-root", type=Path,
                    default=Path(__file__).resolve().parent / "fpga")
parser.add_argument("--io-dir", type=Path, default=None)
parser.add_argument("--skip_rtl", action="store_true",
                    help="Skip RTL simulator; use fast Scala --sim only")
args = parser.parse_args()

audio_path = args.audio_file
fpga_root  = args.fpga_root.resolve()
io_dir     = args.io_dir.resolve() if args.io_dir else fpga_root.parent / "fpga_io"
io_dir.mkdir(exist_ok=True)

print("=== DSP → ASR → NLP Pipeline (HW-accelerated DSP) ===")
print(f"Audio file:    {audio_path}")
print(f"FPGA root:     {fpga_root}")
print(f"I/O directory: {io_dir}")
print(f"ASR device:    {args.device}\n")

# ── DSP (FPGA) ────────────────────────────────────────────────────────
run_spatial_pipeline(str(audio_path), skip_rtl=args.skip_rtl)

ws_csv = io_dir / "whisperscale_output.csv"
logmels = np.loadtxt(ws_csv, dtype=np.float32, delimiter=",")
if logmels.ndim == 2 and logmels.shape[0] != DEFAULT_N_MELS:
    logmels = logmels.T
logmels_t = torch.from_numpy(logmels).to(args.device)

# ── ASR ───────────────────────────────────────────────────────────────
transcript = transcribe_features(logmels_t, device=args.device)
print("\n--- ASR Transcript ---")
print(transcript)

# ── NLP ───────────────────────────────────────────────────────────────
summary = summarize_text(transcript)
print("\n--- NLP Summary ---")
print(summary)

print("\n--- JSON Output ---")
print(json.dumps({"transcript": transcript, "summary": summary}, indent=2))
