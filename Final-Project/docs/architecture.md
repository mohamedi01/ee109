# Project Architecture

This document describes the structure and organization of the EE109 Final Project repository.

## Directory Structure

```
Final-Project/
├── data/                # Audio data and transcripts for testing
│   ├── long_sentences/
│   ├── normalization/
│   ├── short_sentences/
│   ├── single_words/
│   └── transcripts/
│       ├── long_sentences/
│       ├── short_sentences/
│       └── single_words/
├── docs/                # Project documentation
│   ├── architecture.md
│   └── submissions      # Contains milestone 1 submission and Fi=fnal report
├── ee109_final_project/ # (If used: Python virtual environment or build artifacts, ignored)
├── fpga/                # All FPGA/Spatial kernel source, build, and generated files
│   ├── LogCompress/         # Each kernel has its own directory:
│   ├── MelFilterbank/       #   - LogCompress, MelFilterbank, PowerSpectrum, etc.
│   ├── PowerSpectrum/       #   Each contains:
│   ├── QuantizeKernel/      #     gen/, logs/, reports/, project/, target/, build.sbt, source files, etc.
│   ├── STFTKernel/
│   └── WhisperScale/
│   └── reports/            
├── fpga_io/             # CSV files for Python <-> FPGA data exchange (ignored by git)
├── src/                 # Python source code and utilities
│   ├── audiolib/            # Main Python package
│   │   ├── asr/
│   │   ├── dsp/
│   │   │   └── Helper_Checker_fns/
│   │   ├── nlp/
│   │   │   └── models/
│   │   ├── fpga_io/
│   │   ├── __pycache__/
│   │   ├── __init__.py
│   │   ├── pipeline.py
│   │   ├── run_pipeline.py
│   │   ├── run_pipeline_hw.py
│   │   ├── run_pipeline_hw_old.py
│   ├── audiolib.egg-info/   # Package metadata (ignored by git)
│   └── testutility/         # Additional test utilities
├── submissions/         # Submission documents
├── target/              # Top-level build artifacts (ignored by git)
├── tests/               # Python test scripts for DSP, ASR, NLP, and pipeline
│   ├── test_DSP/
│   ├── test_DSP_ASR_NLP/
│   ├── test_DSP_to_ASR/
│   ├── test_NLP/
│   └── test_pipeline/
├── .gitignore           # Ignore rules for build artifacts, data, and IDE files
├── project_proposal.md  # Project proposal document
├── pyproject.toml       # Python project configuration
├── README.md            # Project overview and instructions
├── requirements.txt     # Python dependencies
```

## Key Points

- **`data/`**: Contains all audio and transcript files for testing and development, organized by type and transcript set.
- **`fpga/`**: Contains all FPGA/Spatial kernel code, build files, and generated outputs. Each kernel (e.g., `QuantizeKernel`, `STFTKernel`) is in its own folder, with its own build and output subdirectories.
- **`fpga_io/`**: Used for exchanging CSV data between Python and FPGA/Spatial simulations. Contains many intermediate and debug files. Ignored by git.
- **`src/audiolib/`**: Main Python source code, organized by functionality (DSP, ASR, NLP, utilities). Contains submodules and pipeline scripts.
- **`src/audiolib/dsp/Helper_Checker_fns/`**: Helper and checker functions for DSP.
- **`src/audiolib/nlp/models/`**: NLP model files.
- **`src/audiolib/fpga_io/`**: Python-side FPGA I/O utilities.
- **`src/audiolib.egg-info/`**: Python package metadata (ignored by git).
- **`src/testutility/`**: Additional test utilities.
- **`tests/`**: Organized into subdirectories for each pipeline stage and the full pipeline.
- **`submissions/`**: Contains submission documents.
- **`target/`, `gen/`, `logs/`, `reports/`**: Build artifacts and generated files, all ignored by git.
- **`.gitignore`**: Ensures that all build artifacts, intermediate files, and virtual environments are not tracked by git.

## Data Flow

- **Python ↔ FPGA/Spatial**: The `fpga_io/` directory is the bridge for data exchange (e.g., CSV files) between the Python pipeline and FPGA/Spatial kernels.
- **Build Artifacts**: All build outputs, logs, and reports are ignored by git to keep the repository clean.
- **Source Code**: All source code for Python and FPGA/Spatial is kept under `src/` and `fpga/` respectively. Each kernel in `fpga/` is self-contained.

## Running and Testing

- **Python**: Run and test the pipeline using scripts in `src/audiolib/` and `tests/`.
- **FPGA/Spatial**: Each kernel in `fpga/` can be built and simulated independently. Use the provided `run.sh` scripts or SBT commands in each kernel directory.
- **Integration**: The Python pipeline can invoke FPGA kernels and compare results using the shared `fpga_io/` directory.