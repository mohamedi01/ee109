#!/usr/bin/env python3
import sys
import os
import argparse
from pathlib import Path
import numpy as np
import torch
import json

# === Imports adjusted to match your project structure ===
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
        help="Device for ASR inference (cpu or cuda). Default: cpu",
    )
    parser.add_argument(
        "--fpga-root",
        type=Path,
        default=Path(__file__).resolve().parent / "fpga",
        help="Path to the top‐level fpga/ directory (contains MelLogScaleKernel/, etc.).",
    )
    parser.add_argument(
        "--io-dir",
        type=Path,
        default=None,
        help=(
            "Path to the fpga_io/ directory. "
            "If unset, will be inferred as sibling of --fpga-root."
        ),
    )

    parser.add_argument(
        "--skip_rtl",
        action="store_true",
        help="Skip RTL simulator; use fast Scala --sim only",
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

    # --- STEP 1: RUN THE FPGA‐ACCELERATED DSP (Power→Mel→Log→Scale) ---
    # This writes fpga_io/whisperscale_output.csv with shape (80 × T)
    try:
        run_spatial_pipeline(str(audio_path), skip_rtl=args.skip_rtl)
    except Exception as e:
        print(f"[ERROR] DSP step failed: {e}")
        sys.exit(1)

    # Now expect fpga_io/whisperscale_output.csv
    ws_csv = io_dir / "whisperscale_output.csv"
    if not ws_csv.is_file():
        print(f"Error: Expected `whisperscale_output.csv` not found in {io_dir}")
        sys.exit(1)

    # --- STEP 2: LOAD & SHAPE WHISPER‐SCALED FEATURES FOR ASR ---
    logmels = np.loadtxt(ws_csv, dtype=np.float32, delimiter=",")
    # After merging, logmels should be shape (80, T). If instead it's (T, 80), transpose.
    if logmels.ndim == 2 and logmels.shape[0] != DEFAULT_N_MELS:
        logmels = logmels.T

    # Convert to tensor on chosen device
    logmels_tensor = torch.from_numpy(logmels).to(device)

    # --- STEP 3: RUN ASR ON LOG‐MEL FEATURES ---
    try:
        transcript = transcribe_features(logmels_tensor, device=device)
    except Exception as e:
        print(f"[ERROR] ASR inference failed: {e}")
        sys.exit(1)

    print("\n--- ASR Transcript ---")
    print(transcript)

    # --- STEP 4: RUN NLP SUMMARIZATION ON THE TRANSCRIPT ---
    try:
        summary = summarize_text(transcript)
    except Exception as e:
        print(f"[ERROR] NLP summarization failed: {e}")
        sys.exit(1)

    print("\n--- NLP Summary ---")
    print(summary)

    # Print JSON summary of results
    pipeline_output = {
        "transcript": transcript,
        "summary": summary
    }
    print("\n--- JSON Output ---")
    print(json.dumps(pipeline_output, indent=2))


if __name__ == "__main__":
    main()
