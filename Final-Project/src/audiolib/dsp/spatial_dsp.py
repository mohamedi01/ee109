#!/usr/bin/env python3
"""
spatial_dsp.py – host-side driver that feeds MelLogScaleKernel with ONE-D CSV
"""

from __future__ import annotations
import os, sys, subprocess
from pathlib import Path
import numpy as np, torch, torchaudio
from audiolib.dsp.mel_gold import (
    load_audio, DEFAULT_SAMPLE_RATE, DEFAULT_N_FFT, DEFAULT_HOP_LENGTH
)

# ── helpers ───────────────────────────────────────────────────────────
def run_cmd(cmd: str, cwd: Path) -> None:
    print(f"[INFO] 🛠  {cmd}  (cwd={cwd})")
    proc = subprocess.run(cmd, cwd=cwd, shell=True, text=True,
                          capture_output=True)
    if proc.returncode:
        print(proc.stdout); print(proc.stderr, file=sys.stderr)
        sys.exit(f"[ERROR] Command failed: {cmd}")
    if proc.stdout.strip():
        print(proc.stdout.splitlines()[0])

# ── 1.  host STFT → power → *flattened* CSV (one value per line) ──────
def write_power_vector(audio: np.ndarray, io_dir: Path) -> int:
    x = torch.from_numpy(audio)
    stft  = torch.stft(x, n_fft=DEFAULT_N_FFT,
                          hop_length=DEFAULT_HOP_LENGTH,
                          win_length=DEFAULT_N_FFT,
                          window=torch.hann_window(DEFAULT_N_FFT),
                          center=True,
                          return_complex=True)
    power = stft.abs().pow(2).cpu().numpy().astype(np.float32)
    power = np.clip(power, 1e-20, 1e8).astype(np.float32) # still a 280-dB span

    # --- add two lines ------------------------------------------------
    # power[power < 1e-30] = 1e-30         # BigDecimal safe floor
    # ------------------------------------------------------------------

    flat  = power.reshape(-1)
    out   = io_dir / "power_matrix_1d.csv"
    np.savetxt(out, flat, fmt="%.9f")
    print(f"[INFO] power_matrix_1d.csv  length={flat.size}")
    return flat.size

# ── 2.  Spatial pipeline ──────────────────────────────────────────────
def run_spatial_pipeline(audio_path: str, *, skip_rtl: bool=False) -> None:
    root        = Path(__file__).resolve().parents[3]
    fpga_root   = root / "fpga"
    kernel_dir  = fpga_root / "MelLogScaleKernel"
    io_dir      = root / "fpga_io"; io_dir.mkdir(exist_ok=True)
    io_dir.mkdir(exist_ok=True)

    # ── 0. delete stale I/O from any previous run ────────────────
    for fname in ("power_matrix_1d.csv", "whisperscale_output.csv"):
        f = io_dir / fname
        if f.exists():
            f.unlink()
    
    audio_np, sr = load_audio(audio_path, None, None)
    if sr != DEFAULT_SAMPLE_RATE:
        audio_np = torchaudio.functional.resample(
            torch.from_numpy(audio_np), sr, DEFAULT_SAMPLE_RATE
        ).numpy()

    N_elems = write_power_vector(audio_np, io_dir)
    (kernel_dir / "kernel_config.txt").write_text(str(N_elems))

    run_cmd('sbt "clean; update; run --sim"', kernel_dir)
    run_cmd("./run.sh", kernel_dir / "gen" / "MelLogScaleKernel")

    out_csv = io_dir / "whisperscale_output.csv"
    if not out_csv.exists():
        sys.exit("[ERROR] Kernel did not produce whisperscale_output.csv")
    shape = np.loadtxt(out_csv, dtype=np.float32, delimiter=",").shape
    print(f"[PASS] Spatial DSP finished. Output shape = {shape}")

if __name__ == "__main__":
    if len(sys.argv) != 2:
        sys.exit("Usage: spatial_dsp.py  path/to/audio.wav")
    run_spatial_pipeline(sys.argv[1])
