Final-Project/
├─ README.md                            # (Up to Date with DSP -> ASR software implementation)             
├─ project_proposal.md                  # (Up to Date with DSP -> ASR software implementation)
├─ pyproject.toml                       # (Up to Date with DSP -> ASR software implementation)
├─ requirements.txt                     # (Up to Date with DSP -> ASR software implementation)
├─ .gitignore                           # (Up to Date with DSP -> ASR software implementation)
├─ docs/
│   ├─ architecture.md                  # (Up to Date with DSP -> ASR software implementation)
├─ src/
│   └─ audiolib/
│       ├─ __init__.py
│       ├─ cli.py
│       ├─ dsp/
│       │   ├─ __init__.py              # (Up to Date for DSP )
│       │   ├─ mel.py                   # (Completed for software implementation)
│       │   └─ mfcc.py                  # (Removed due to redundancy)
│       ├─ asr/
│       │   ├─ __init__.py              # (Completed for software implementation)
│       │   ├─ long_transcribe.py       # (Completed for software implementation)
│       │   └─ whisper_features.py      # (Completed for software implementation)
│       └─ nlp/
│           ├─ __init__.py              #(Todo)
│           └─ slice_summariser.py      # (Todo)
├─ data/
│   ├─ long/
│   │   ├─ harvard_f.wav                # (Downloaded - Succesfully tested on DSP -> ASR)
│   │   └─ harvard_m.wav                # (Downloaded - Succesfully tested on DSP -> ASR)
│   └─ numbers/
│       ├─ 0_jackson_0.wav              # (Downloaded - Succesfully tested on DSP -> ASR)
│       ├─ 1_jackson_0.wav              # (Downloaded - Succesfully tested on DSP -> ASR)
│       ├─ 2_jackson_0.wav              # (Downloaded - Succesfully tested on DSP -> ASR)
│       ├─ 3_theo_0.wav                 # (Downloaded - Succesfully tested on DSP -> ASR)
│       ├─ 4_theo_0.wav                 # (Downloaded - Succesfully tested on DSP -> ASR)
│       └─ 5_george_0.wav               # (Downloaded - Succesfully tested on DSP -> ASR)
|       └─ 6_george_0.wav               # (Downloaded - Succesfully tested on DSP -> ASR)
└─ tests/
    ├─ numbers/
    │   ├─ test_asr_numbers.py
    │   └─ test_dsp_numbers.py
    └─ test_short_sentences.py
