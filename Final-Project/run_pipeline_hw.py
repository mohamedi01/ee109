#!/usr/bin/env python3
import sys
import os
import argparse
from pathlib import Path
import numpy as np
import torch
import json

# === Adjusted imports to match your actual files ===
#  - spatial_dsp.run_spatial_pipeline lives in src/audiolib/dsp/spatial_dsp.py
#  - whisper_features.transcribe_features lives in src/audiolib/asr/whisper_features.py
#  - nlp.summarize_text lives in src/audiolib/nlp/nlp.py
#
# (You must have an __init__.py in each folder so Python treats them as packages.)
from audiolib.dsp.spatial_dsp import run_spatial_pipeline
from audiolib.asr.whisper_features import transcribe_features
from audiolib.nlp.nlp import summarize_text

def main():
    parser = argparse.ArgumentParser(
        description="Run the hardware‐accelerated DSP → ASR → NLP pipeline on an audio file."
    )
    parser.add_argument(
        "audio_file",
        type=Path,
        help="Path to the WAV audio file to process (e.g. data/short_sentences/harvard_f.wav)",
    )
    parser.add_argument(
        "--device",
        type=str,
        default="cpu",
        choices=["cpu", "cuda"],
        help="Device for ASR inference. Default: cpu",
    )
    parser.add_argument(
        "--fpga-root",
        type=Path,
        default=Path(__file__).resolve().parent / "fpga",
        help="Path to the top‐level fpga/ directory (contains QuantizeKernel/, STFTKernel/, …).",
    )
    parser.add_argument(
        "--io-dir",
        type=Path,
        default=None,
        help=(
            "Path to the fpga_io/ directory. "
            "If unset, it will be inferred as sibling of --fpga-root (i.e. ../fpga_io)."
        ),
    )
    args = parser.parse_args()

    audio_path = args.audio_file
    device     = args.device
    fpga_root  = args.fpga_root.resolve()
    io_dir     = (
        args.io_dir.resolve()
        if args.io_dir
        else (fpga_root.parent / "fpga_io").resolve()
    )

    # 1) Verify the audio file exists
    if not audio_path.is_file():
        print(f"Error: Audio file not found at {audio_path}")
        sys.exit(1)

    # 2) Ensure fpga_io/ exists
    os.makedirs(io_dir, exist_ok=True)

    print("=== DSP → ASR → NLP Pipeline (HW‐accelerated DSP) ===")
    print(f"Audio file:    {audio_path}")
    print(f"FPGA root:     {fpga_root}")
    print(f"I/O directory: {io_dir}")
    print(f"ASR device:    {device}\n")

    # --- STEP 1: RUN THE FPGA‐ACCELERATED DSP (ALL 6 KERNELS) ---
    # This writes intermediate CSVs into fpga_io/, then computes log‐Mel + scale
    # and writes fpga_io/whisperscale_output.csv.
    try:
        run_spatial_pipeline(str(audio_path))
    except Exception as e:
        print(f"[ERROR] DSP step failed: {e}")
        sys.exit(1)

    # After run_spatial_pipeline, `fpga_io/whisperscale_output.csv` should exist.
    ws_csv = io_dir / "whisperscale_output.csv"
    if not ws_csv.is_file():
        print(f"Error: Expected `whisperscale_output.csv` not found in {io_dir}")
        sys.exit(1)

    # --- STEP 2: LOAD WHISPER‐SCALED FEATURES FOR ASR ---
    # Each row of `whisperscale_output.csv` is one time‐frame's log‐Mel vector (float32).
    logmels = np.loadtxt(ws_csv, dtype=np.float32)  # shape = (n_mels, n_frames)
    logmels_tensor = torch.from_numpy(logmels).to(device)

    # --- STEP 3: RUN ASR ON LOG‐MEL FEATURES ---
    # `transcribe_features` expects a numpy array or torch.Tensor of shape [n_mels, n_frames].
    # It will pad/crop internally to 3000 frames (30 s) and return a transcript string.
    try:
        transcript = transcribe_features(logmels_tensor, device=device)
    except Exception as e:
        print(f"[ERROR] ASR inference failed: {e}")
        sys.exit(1)

    print("\n--- ASR Transcript ---")
    print(transcript)

    # --- STEP 4: RUN NLP SUMMARIZATION ON THE TRANSCRIPT ---
    # `summarize_text` returns a summary string.
    try:
        summary = summarize_text(transcript)
    except Exception as e:
        print(f"[ERROR] NLP summarization failed: {e}")
        sys.exit(1)

    print("\n--- NLP Summary ---")
    print(summary)

    # (Optionally, you could add keyword/topic extraction later if you implement such functions.)
    # For now, we only summarize, because nlp.py only provides `summarize_text(...)`.

    # Print a JSON‐style summary of results
    pipeline_output = {
        "transcript": transcript,
        "summary": transcript if len(transcript) < 1 else summary
    }
    print("\n--- JSON Output ---")
    print(json.dumps(pipeline_output, indent=2))


if __name__ == "__main__":
    main()
