#!/usr/bin/env python3
"""
spatial_dsp.py – host-side driver for MelLogScaleKernel
────────────────────────────────────────────────────────
 • PyTorch handles STFT → power spectrum on the CPU.
 • One Spatial kernel (MelLogScaleKernel) converts power → log-Mel+Whisper
   for *all* frames in a single run.
 • The 80 × T output matrix is written to fpga_io/whisperscale_output.csv
   for downstream NLP / ASR stages (run_pipeline_hw.py).

Assumptions
-----------
 • fpga/MelLogScaleKernel/ is the kernel directory you just got compiling.
 • fpga_io/ is a sibling of fpga/ (same layout you showed ChatGPT earlier).
 • SBT is on $PATH.

Test quickly:
$ python spatial_dsp.py examples/hello_world.wav
"""

from __future__ import annotations
import os
import sys
import subprocess
from pathlib import Path
from typing import Tuple

import numpy as np
import torch
import torchaudio

# ── Project-local helpers ---------------------------------------------------- #
from audiolib.dsp.mel_gold import (
    load_audio,                 # uses torchaudio.load + optional resample
    DEFAULT_SAMPLE_RATE,
    DEFAULT_N_FFT,
    DEFAULT_HOP_LENGTH,
    DEFAULT_N_MELS,
)

# ---------------------------------------------------------------------------- #
#  Helper: run a shell command and fail fast
# ---------------------------------------------------------------------------- #
def run_cmd(cmd: str, cwd: Path) -> None:
    """Spawn `cmd` inside `cwd`, echo first log line, abort on non-zero exit."""
    print(f"[INFO] 🛠  {cmd}  (cwd={cwd})")
    proc = subprocess.run(cmd, cwd=cwd, shell=True, text=True,
                          capture_output=True)
    if proc.returncode != 0:
        print(proc.stdout)
        print(proc.stderr, file=sys.stderr)
        sys.exit(f"[ERROR] Command failed: {cmd}")
    if proc.stdout.strip():
        print(proc.stdout.splitlines()[0])

# ---------------------------------------------------------------------------- #
#  1.  Host-side STFT → power matrix → CSV
# ---------------------------------------------------------------------------- #
def write_power_matrix(audio: np.ndarray, io_dir: Path) -> int:
    """
    Returns
    -------
    T : int
        Number of STFT frames (CSV columns).

    Side-effect
    -----------
    Saves fpga_io/power_matrix.csv   (#rows = 201 bins, #cols = T frames)
    """
    x = torch.from_numpy(audio)                             # (num_samples,)
    stft = torch.stft(
        x,
        n_fft        = DEFAULT_N_FFT,
        hop_length   = DEFAULT_HOP_LENGTH,
        win_length   = DEFAULT_N_FFT,
        window       = torch.hann_window(DEFAULT_N_FFT),
        center       = True,
        return_complex = True,
    )                                                       # → (201, T)
    power = stft.abs().pow(2).cpu().numpy().astype(np.float32)
    out_csv = io_dir / "power_matrix.csv"
    np.savetxt(out_csv, power, fmt="%.8e")
    print(f"[INFO] 💾 power_matrix.csv  shape={power.shape}")
    return power.shape[1]                                   # T

# ---------------------------------------------------------------------------- #
#  2.  One-shot Spatial pipeline (compile + sim)
# ---------------------------------------------------------------------------- #
def run_spatial_pipeline(audio_path: str, *, skip_rtl: bool = False) -> None:
    """Entry called by run_pipeline_hw.py or from the CLI below."""
    root        = Path(__file__).resolve().parents[3]       # project root
    fpga_root   = root / "fpga"
    kernel_dir  = fpga_root / "MelLogScaleKernel"
    io_dir      = root / "fpga_io"
    io_dir.mkdir(exist_ok=True)

    # ---- load & resample audio ----
    audio_np, sr = load_audio(path=audio_path, audio_data=None, sr_in=None)
    if sr != DEFAULT_SAMPLE_RATE:
        # 1-D torch tensor → resample → back to NumPy
        audio_t = torch.from_numpy(audio_np)
        audio_t = torchaudio.functional.resample(audio_t, sr, DEFAULT_SAMPLE_RATE)
        audio_np = audio_t.numpy()
        sr = DEFAULT_SAMPLE_RATE
    # assert sr == DEFAULT_SAMPLE_RATE, "load_audio() should have resampled"

    # ---- step-1: write power matrix CSV ----
    T = write_power_matrix(audio_np, io_dir)

    # ---- step-2: compile + run Spatial kernel ----
    if not kernel_dir.exists():
        sys.exit("[ERROR] MelLogScaleKernel directory not found")

    # tiny config file (optional – remove if kernel ignores it)
    (kernel_dir / "kernel_config.txt").write_text(str(T))

    # run_cmd('sbt "clean; update; run --sim"', kernel_dir)
    # run_cmd("./run.sh", kernel_dir / "gen" / "MelLogScaleKernel")

    # always run fast functional sim
    run_cmd('sbt "clean; update; run --sim"', kernel_dir)

    # heavy RTL sim only if user didn’t ask to skip
    if not skip_rtl:
        run_cmd("./run.sh", kernel_dir / "gen" / "MelLogScaleKernel")
    # ---- validate output ----
    out_csv = io_dir / "whisperscale_output.csv"
    if not out_csv.exists():
        sys.exit("[ERROR] Kernel did not produce whisperscale_output.csv")
    shape = np.loadtxt(out_csv, dtype=np.float32, delimiter=",").shape
    print(f"[PASS] ✅ Spatial DSP finished. Output shape = {shape}")

# ---------------------------------------------------------------------------- #
#  Debug entry (run as a script)
# ---------------------------------------------------------------------------- #
if __name__ == "__main__":
    if len(sys.argv) != 2:
        sys.exit("Usage: spatial_dsp.py  path/to/audio.wav")
    run_spatial_pipeline(sys.argv[1])
