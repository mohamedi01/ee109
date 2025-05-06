# Project Proposal: FPGA-Accelerated Audio-to-NLP Engine
## By: William Briger, Chris Gutierrez, & Mohamad Ismail

## Introduction: 
We plan to use an Amazon EC2 F1 instance to accelerate the front-end of audio-driven applications., transforming raw microphones samples into textual insights. This pipeline will combine Audio Digital Signal Processing (DSP) for filtering and feature extraction, Automatic Speech Recognition (ASR) to convert speech to text, and then apply lightweight Nautral-Langauge Processing (NLP). The primary goal of this project will be to effectively accelerate the audio processing and the light weight neural network, to produce keyword spotting, topic segmentation, or even meeting-minutes/ conversation summaries. However, there also exist many potential extensions that to expand on the applications of the low-latency speech-to-text processing (e.g, audio to on-device sign-langauge).

## Why an FPGA:
Running this stack in real-time with just a general prupose CPU will face upndredictable. The Audio DSP and Neural Network/ Transformer inference are stream-ortiented processes with tight latency goals so to complete these live requires a pipelined and deterministic datapath that FPGAs can complete very efficiently. 

## Literature Survey: 
Our project fuses audio DSP, ASR, and first-stage NLP into one FPGA pipeline. While there is no comprehensive research on the combined applications, there is research on FPGA usage for each individual process. To provide a literature review, we group and provide relevant prior research in three categories: (1) Audio DSP on FPGAs, (2) ASR Accelerators, (3) Lightweight NLP on FPGAs.
- Audio DSP utilizing FPGAs: 
- ASR Accelerators:
- Lightweight NLP on FPGAs: 

As noted, prior work typically focus on one sateg (DSP, ASR, or NLP) in isolation. Our initial implemention will pipeline all three processes, with hardware focus on the DSP. Yet, an ultimate goal will be a single streaming piple that fuses all three, minimizing off-chip transfers.

## Project Description:

## Measure:
To measure quality, we will look at, (1) raw performance, (2) resource utilization, and (3) Other. More specifics are below:
- Raw performance: To analyze raw performance, we will measure end-to-end latency (mic-in to text-out). We will also analyze throughput.
- Resource utilization: To analyze resource utilization, we will measure LLT percentage, BRRAM percentage, and DSP slices
- Other: To ensure application specific performance is good, we will measure Word-Error-Rate (WER) and Keyword-Spotting.  

## Planning:

## Extended use cases: 
1. Sign-langauge avatar: By feeding the NLP text results to the python api, sign-language-translator, this application can be used for live translation of audio to sign langaugae.
2. Video highlight indexing: This model can be used to commment and sumarize videos by their audio features (e.g., laughter).


 
