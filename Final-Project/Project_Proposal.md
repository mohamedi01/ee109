# Project Proposal: FPGA-Accelerated Audio-to-NLP Engine
## By: William Briger, Chris Gutierrez, & Mohamad Ismail

## Introduction: 
We plan to accelerate the front-end of audio-driven applications. Using raw microphones samples, we will complete Audio Digital Signal Processing (DSP), Automatic Speech Recognition (ASR), speech-to-text processing, and then apply lightweight Nautral-Langauge Processing (NLP). The primary goal of this project will be to effectively accelerate the audio processing and the light weight neural network, to produce keyword spotting, topic segmentation, or even meeting-minutes/ conversation summaries. However, there also exist many potential extensions that to expand on the applications of the low-latency speech-to-text processing (e.g, audio to on-device sign-langauge).

## Why an FPGA:
An FPGA is relevant for this application because of the Audio DSP and Neural Network/ Transformer inference. Both of these core processes are  stream-ortiented processes with tight latency goals. To complete these live requires a pipelined and deterministic datapath that FPGAs can complete very efficiently. 

## Literature Survey: 
We group prior research into four categories: (1) Audio DSP on FPGAs, (2) ASR Accelerators, (3) Lightweight NLP on FPGAs, (4) ____, 
- Audio DSP utilizing FPGAs:
- ASR Accelerators:
- Lightweight NLP on FPGAs: 

Our project addresses fuses audio DSP, ASR, and first-stage NLP into one FPGA pipeline. 

## Project Description:

## Measure:

## Planning:

## Extended use cases: 
1. Sign-langauge avatar: By feeding the NLP text results to the python api, sign-language-translator, this application can be used for live translation of audio to sign langaugae.
2. Video highlight indexing: This model can be used to commment and sumarize videos by their audio features (e.g., laughter).


 
