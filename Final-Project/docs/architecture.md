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
│       │   ├─ mel.py                   # (Completed for software implementation)
│       ├─ asr/
│       │   ├─ __init__.py              # (Completed for software implementation)
│       │   ├─ long_transcribe.py       # (Completed for software implementation)
│       │   └─ whisper_features.py      # (Completed for software implementation)
│       ├─ nlp/
│       │   ├─ __init__.py              #(Todo)
│       │   └─ slice_summariser.py      # (Todo)
│       └─ testutility/
│           ├─ __init__.py              # (Makes testutility a package)
│           └─ text_processing_utils.py     # (Utilities for text normalization. Loads a canonical map for homophones and numbers directly from data/homophones.csv for testing purposes)
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
│   └─ homophones.csv                   # (Downloaded - Used for text normalization during WER calculation)
│
└─ tests/  
    ├─ test_DSP/
    │   └─ test_dsp_single_words.py     # (Tests DSP components on single-word recordings) (Finalized)
    │   └─ test_dsp_short_sentences.py  # (Tests DSP components on short sentence recordings) (Finalized)
    │
    └─ test_DSP_to_ASR/                 # Tests for the DSP to ASR pipeline 
        ├─ test_asr_short_sentences.py  # (Tests ASR on multi-sentence audio, compares custom DSP->ASR pipeline with baseline Whisper)
        ├─ test_asr_single_words.py     # (Tests ASR on single-word audio, compares custom DSP->ASR pipeline with baseline Whisper)
        └─ test_dsp_single_words.py     # (Tests DSP components on single-word recordings)
                                        # (Note: test_short_sentences.py previously at root of tests/ was removed as redundant)
