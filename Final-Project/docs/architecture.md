# Project Architecture

This document outlines the structure and components of the EE109 Final Project.

## Directory Structure

Final-Project/
├─ README.md                            # (Up to Date with DSP -> ASR software implementation)             
├─ project_proposal.md                  # (Up to Date with DSP -> ASR software implementation)
├─ pyproject.toml                       # (Up to Date with DSP -> ASR software implementation)
├─ requirements.txt                     # (Up to Date with DSP -> ASR software implementation)
├─ .gitignore                           # (Up to Date with DSP -> ASR software implementation)
├─ docs/
│   └─ architecture.md                  # (Up to Date with DSP -> ASR software implementation)
├─ src/
│   └─ audiolib/
│       ├─ __init__.py
│       ├─ cli.py
│       ├─ utils.py                     # (Provides utility functions, e.g., loading a canonical map for homophones and numbers)
│       ├─ dsp/
│       │   ├─ __init__.py              # (Up to Date for DSP -> ASR software implementation)
│       │   ├─ mel.py                   # (Finalized for software implementation: provides `wav_to_logmel` for DSP)
│       ├─ asr/
│       │   ├─ __init__.py              # (Finalized for software implementation: Exports core ASR functions.)
│       │   ├─ transcribe.py            # (Finalized for software implementation: Provides `transcribe_audio_file`, the main ASR entry point.)
│       │   └─ whisper_features.py      # (Finalized for software implementation: Provides `transcribe_features` for running Whisper model inference on Mel spectrograms.)
│       ├─ nlp/
│       │   ├─ __init__.py              # (Finalized for software implementation: Exports core NLP functionalities like `analyze_text`)
│       │   ├─ nlp.py                   # (Finalized for software implementation: Provides core NLP functions: `analyze_text` for keyword/topic classification and summarization, model loading, performance metrics, and evaluation utilities)
│       │   └─ debug_nlp.py             # (Finalized for software implementation: Script for debugging NLP functionalities, particularly `analyze_text`)
│       └─ testutility/
│           ├─ __init__.py              # (Makes testutility a package)
│           └─ text_processing_utils.py     # (Utilities for text normalization. Loads a canonical map for homophones and numbers directly from data/Testing-Normalization/homophones.csv for testing purposes) (Finalized)
├─ data/
│   ├─ short_sentences/                 # (~30 second audio clips with multiple sentences)
│   │   ├─ harvard_f.wav                # (Downloaded - Succesfully tested on DSP -> ASR)
│   │   └─ harvard_m.wav                # (Downloaded - Succesfully tested on DSP -> ASR)
|   |
│   ├─ single_words/                    # (~1 second audio clips with single numbers)
│   │   ├─ 0_jackson_0.wav              # (Downloaded - Succesfully tested on DSP -> ASR)
│   │   ├─ 1_jackson_0.wav              # (Downloaded - Succesfully tested on DSP -> ASR)
│   │   ├─ 2_jackson_0.wav              # (Downloaded - Succesfully tested on DSP -> ASR)
│   │   ├─ 3_theo_0.wav                 # (Downloaded - Succesfully tested on DSP -> ASR)
│   │   ├─ 4_theo_0.wav                 # (Downloaded - Succesfully tested on DSP -> ASR)
│   │   ├─ 5_george_0.wav               # (Downloaded - Succesfully tested on DSP -> ASR)
│   │   └─ 6_george_0.wav               # (Downloaded - Succesfully tested on DSP -> ASR)
│   │
│   └─ normalization                    # Folder for testing and normalization data
│       └─ homophones.csv               # (Downloaded & edited - Used for text normalization during WER calculation)
│       
│
└─ tests/  
    ├─ test_DSP/
    │   ├─ test_dsp_single_words.py     # (Tests DSP components on single-word recordings) (Finalized)
    │   └─ test_dsp_short_sentences.py  # (Tests DSP components on short sentence recordings) (Finalized)
    │
    └─ test_DSP_to_ASR/                 # Tests for the DSP to ASR pipeline 
        ├─ test_asr_short_sentences.py  # (Tests ASR on multi-sentence audio, compares custom DSP->ASR pipeline with baseline Whisper) (Finalized)
        └─ test_asr_single_words.py     # (Tests ASR on single-word audio, compares custom DSP->ASR pipeline with baseline Whisper) (Finalized)
    └─ test_NLP/
        └─ test_nlp.py                  # (Tests NLP functionalities including `analyze_text`, performance measurements, and classifier evaluation)
