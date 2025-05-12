Final-Project/
├── README.md            # Executive overview & quick‑start commands **(DSP section complete)**
├── pyproject.toml       # Build + dependency metadata (PEP‑621) **(added, v0.1.0)**
├── requirements.txt     # Pinned runtime wheels **(DSP deps locked)**
├── .gitignore           # Venv, caches, OS cruft **(covers ee109_final_project/**)
│
├── docs/                # Design‑time documentation (MkDocs)
│   ├── architecture.md  # **YOU ARE HERE** – repo map & rationale **(live)**
│
├── audiolib/            # Installable Python package (`pip -e .`)
│   ├── __init__.py      # Convenience re‑exports & version **(stub)**
│   ├── dsp/             # Front‑end signal processing
│   │   ├── __init__.py
│   │   ├── mfcc.py      # 13‑D MFCC baseline **(done)**
│   │   ├── mel.py       # 80‑band log‑Mel, CMVN, Δ/ΔΔ **(done)**
│   │   └── streaming.py # Generator for real‑time chunking (10 ms) **(todo)**
│   ├── asr/
│   │   ├── __init__.py
│   │   ├── whisper.py   # Tiny/Base wrappers, quantized variants **(todo)**
│   │   └── conformer.py # Conformer‑Small implementation **(optional)**
│   ├── nlp/
│   │   ├── __init__.py
│   │   ├── keywords.py  # KeyBERT extractor **(todo)**
│   │   └── summary.py   # DistilBART summarizer **(todo)**
│   └── cli.py           # Typer‑based entry‑point (`python -m audiolib`) **(stub)**
│
├── demo/
│   ├── run_demo.py      # Reproduces Milestone‑2 numbers end‑to‑end **(todo)**
│   └── assets/          # Sample WAVs & golden outputs **(sample.wav present)**
│
├── scripts/             # One‑off helpers (benchmark, eval, quantize)
│   ├── bench.py         # DSP/ASR latency benchmark **(todo)**
│   ├── eval_wer.py      # WER evaluation harness **(todo)**
│   └── dump_mel_filters.py # C header generator for FPGA **(todo)**
│
├── tests/               # Pytest unit + integration tests
│   ├── test_dsp.py      # MFCC + log‑Mel unit tests **(todo)**
│   ├── test_asr.py      # Whisper smoke‑test **(todo)**
│   └── test_cli.py      # CLI end‑to‑end **(todo)**
│
└── .github/workflows/   # CI: lint, tests, wheel build
    └── ci.yml          # GitHub Actions config **(todo)**
