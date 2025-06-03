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
├── docs/                # Project documentation
│   └── architecture.md
├── ee109_final_project/ # (If used: Python virtual environment or build artifacts, ignored)
├── fpga/                # All FPGA/Spatial kernel source, build, and generated files
│   ├── LogCompress/
│   ├── MelFilterbank/
│   ├── PowerSpectrum/
│   ├── QuantizeKernel/
│   ├── STFTKernel/
│   ├── WhisperScale/
│   ├── gen/             # Generated code and simulation outputs (ignored by git)
│   ├── logs/            # Kernel logs (ignored by git)
│   ├── reports/         # Build and simulation reports (ignored by git)
│   ├── target/          # SBT build artifacts (ignored by git)
│   ├── src/             # Scala/Spatial test sources
│   └── build.sbt        # SBT build file for FPGA project
├── fpga_io/             # Intermediate CSV files for Python <-> FPGA data exchange (ignored by git)
├── src/                 # Python source code
│   └── audiolib/
│       ├── asr/
│       ├── dsp/
│       ├── nlp/
│       └── testutility/
├── submissions/         # Submission documents
├── target/              # Top-level build artifacts (ignored by git)
├── tests/               # Python test scripts for DSP, ASR, NLP, and pipeline
├── .gitignore           # Ignore rules for build artifacts, data, and IDE files
├── project_proposal.md  # Project proposal document
├── pyproject.toml       # Python project configuration
├── README.md            # Project overview and instructions
├── requirements.txt     # Python dependencies
```

## Key Points

- **`data/`**: Contains all audio and transcript files for testing and development.
- **`fpga/`**: Contains all FPGA/Spatial kernel code, build files, and generated outputs. Each kernel (e.g., `QuantizeKernel`, `STFTKernel`) is in its own folder.
- **`fpga_io/`**: Used for exchanging CSV data between Python and FPGA/Spatial simulations. This directory is ignored by git.
- **`src/audiolib/`**: Main Python source code, organized by functionality (DSP, ASR, NLP, utilities).
- **`tests/`**: Python test scripts for each pipeline stage and the full pipeline.
- **`submissions/`**: Contains submission documents.
- **`target/`, `gen/`, `logs/`, `reports/`**: Build artifacts and generated files, all ignored by git.
- **`.gitignore`**: Ensures that all build artifacts, intermediate files, and virtual environments are not tracked by git.

## Data Flow

- **Python ↔ FPGA/Spatial**: The `fpga_io/` directory is the bridge for data exchange (e.g., CSV files) between the Python pipeline and FPGA/Spatial kernels.
- **Build Artifacts**: All build outputs, logs, and reports are ignored by git to keep the repository clean.
- **Source Code**: All source code for Python and FPGA/Spatial is kept under `src/` and `fpga/` respectively.

## Running and Testing

- **Python**: Run and test the pipeline using scripts in `src/` and `tests/`.
- **FPGA/Spatial**: Each kernel in `fpga/` can be built and simulated independently. Use the provided `run.sh` scripts or SBT commands in each kernel directory.
- **Integration**: The Python pipeline can invoke FPGA kernels and compare results using the shared `fpga_io/` directory.