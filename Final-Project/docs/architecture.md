# Project Architecture

This document outlines the structure and components of the EE109 Final Project.

## Directory Structure

Final-Project/
├─ README.md                            # (Up to Date)             
├─ project_proposal.md                  # (Up to Date)
├─ pyproject.toml                       # (Up to Date)
├─ requirements.txt                     # (Up to Date)
├─ .gitignore                           # (Up to Date)
├─ docs/
│   └─ architecture.md                  # (Up to Date)
├─ submissions/                         # (Project submission documents)
│   ├─ Milestone_1_Submission.md        # (Milestone 1 submission document)
│   └─ Milestone_1_Submission.pdf       # (PDF version of Milestone 1 submission)
├─ src/
│   └─ audiolib/
│       ├─ __init__.py
│       ├─ cli.py
│       ├─ pipeline.py                  # (Provides the integrated DSP->ASR->NLP pipeline function `process_audio_to_nlp`)
│       ├─ run_pipeline.py      # (Script to run the full pipeline on an audio file from the command line)
│       ├─ dsp/
│       │   ├─ __init__.py              # (Up to Date for DSP -> ASR software implementation)
│       │   ├─ mel.py                   # (Finalized for software implementation: provides `wav_to_logmel` for DSP)
│       ├─ asr/
│       │   ├─ __init__.py              # (Finalized for software implementation: Exports core ASR functions.)
│       │   ├─ transcribe.py            # (Finalized for software implementation: Provides `transcribe_audio_file`, the main ASR entry point.)
│       │   └─ whisper_features.py      # (Finalized for software implementation: Provides `transcribe_features` for running Whisper model inference on Mel spectrograms.)
│       │
│       ├─ nlp/
│       │   ├─ __init__.py              # (Finalized for software implementation: Exports core NLP functionalities)
│       │   ├─ nlp.py                   # (Finalized for software implementation: Provides core NLP functions)
│       │   ├─ debug_nlp.py             # (Finalized for software implementation: Script for debugging NLP functionalities)
│       │   └─ models/                  # (NLP models and training scripts)
│       │       ├─ __init__.py          # (Initializes the models module)
│       │       ├─ train_keyword_spotter.py # (Script for training keyword spotter)
│       │       └─ train_topic_segmenter.py # (Script for training topic segmenter)
│       └─ testutility/
│           ├─ __init__.py              # (Makes testutility a package)
│           └─ text_processing_utils.py # (Utilities for text normalization)
├─ data/
│   ├─ short_sentences/                 # (~30 second audio clips with multiple sentences)
│   │   ├─ harvard_f.wav               
│   │   └─ harvard_m.wav                
│   ├─ long_sentences/                  # (Longer audio clips, e.g., 1-5 minutes, for more extensive pipeline testing)
│   │   ├─ bird.mp3
│   │   ├─ cold.mp3
│   │   ├─ face.mp3
│   │   └─ hot.mp3      
│   ├─ single_words/                    # (~1 second audio clips with single numbers)
│   │   ├─ 0_jackson_0.wav              
│   │   ├─ 1_jackson_0.wav              
│   │   ├─ 2_jackson_0.wav             
│   │   ├─ 3_theo_0.wav                
│   │   ├─ 4_theo_0.wav                
│   │   ├─ 5_george_0.wav              
│   │   └─ 6_george_0.wav              
│   ├─ transcripts/                     # Transcriptions for testing
│   │   ├─ short_sentences/             # Transcripts for short sentence audio files
│   │   │   ├─ harvard_f.txt           
│   │   │   └─ harvard_m.txt          
│   │   │
│   │   ├─ long_sentences/              # Transcripts for long sentence audio files
│   │   │   ├─ bird.txt
│   │   │   ├─ cold.txt
│   │   │   ├─ face.txt
│   │   │   └─ hot.txt
│   │   │
│   │   └─ single_words/               # Transcripts for single word audio files
│   │       ├─ 0_jackson_0.txt        
│   │       ├─ 1_jackson_0.txt         
│   │       ├─ 2_jackson_0.txt         
│   │       ├─ 3_theo_0.txt           
│   │       ├─ 4_theo_0.txt            
│   │       ├─ 5_george_0.txt          
│   │       └─ 6_george_0.txt         
│   └─ normalization                    # Folder for testing and normalization data
│       └─ homophones.csv               # (Downloaded & edited - Used for text normalization during WER calculation)
└─ tests/  
    ├─ test_DSP/
    │   ├─ test_dsp_single_words.py     # (Tests DSP components on single-word audio) 
    │   ├─ test_dsp_short_sentences.py  # (Tests DSP components on short-sentence audio) 
    │   └─ test_dsp_long_sentences.py   # (Tests DSP components on long-sentence audio)
    │
    ├─ test_DSP_to_ASR/                 # Tests for the DSP to ASR pipeline 
    │   ├─ test_asr_short_sentences.py  # (Tests ASR on short-sentence audio) 
    │   ├─ test_asr_single_words.py     # (Tests ASR on single-word audio) 
    │   └─ test_asr_long_sentences.py   # (Tests ASR on long-sentence audio)
    │
    ├─ test_NLP/                                     # (Tests NLP functionalities)
    │   ├─ test_nlp.py                              
    │   ├─ test_nlp_on_short_sentence_transcripts.py # (Tests NLP module on short-sentence transcripts)
    │   └─ test_nlp_on_long_sentence_transcripts.py  # (Tests NLP module on long-sentence transcripts)
    │
    └─ test_pipeline/                      # Tests for the full DSP->ASR->NLP integrated pipeline
        ├─ test_short_sentence_pipeline.py # (Tests full pipeline short-sentence audio)
        └─ test_long_sentence_pipeline.py  # (Tests full pipeline on long-sentence audio)