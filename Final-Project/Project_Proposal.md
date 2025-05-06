# Project Proposal: FPGA-Accelerated Audio-to-NLP Engine
## By: William Briger, Chris Gutierrez, & Mohamad Ismail

## Introduction: 
We plan to use an Amazon EC2 F1 instance to accelerate the front-end of audio-driven applications, transforming raw microphone samples into textual insights. This pipeline will combine Audio Digital Signal Processing (DSP) for filtering and feature extraction, Automatic Speech Recognition (ASR) to convert speech to text, and then apply lightweight Natural Language Processing (NLP). The primary goal of this project will be to effectively accelerate the audio processing and the lightweight neural network to produce keyword spotting, topic segmentation, or even meeting minutes/ conversation summaries. However, there also exist many potential extensions that expand on the applications of the low-latency speech-to-text processing (e.g, audio to on-device sign language).

## Why an FPGA:
Running the full DSP -> ASR -> NLP stack in real-time with just a general-purpose CPU will face unpredictable latency. The Audio DSP and Neural Network/ Transformer inference are stream-oriented processes with tight latency goals so to complete these live requires a pipelined and deterministic datapath that FPGAs can complete very efficiently. 

## Literature Survey: 
Our project fuses audio DSP, ASR, and first-stage NLP into one FPGA pipeline. While there is no comprehensive research on the combined applications, there is research on FPGA usage for each individual process. To provide a literature review, we group and provide relevant prior research in three categories: (1) Audio DSP on FPGAs, (2) ASR Accelerators, (3) Lightweight NLP on FPGAs. See sources at the bottom. 
- Audio DSP utilizing FPGAs: 
- ASR Accelerators:
- Lightweight NLP on FPGAs: 

As noted, prior work typically focuses on one stage (DSP, ASR, or NLP) in isolation. Our project's novelty is a single streaming pipeline where the hardware DS{front-end feeds the software. The stretch goal is to build a single streaming pipeline that fuses all three, minimizing off-chip transfers.

## Project Description:
Block Diagram:

```
   Microphone                                                    PCIe Gen3 x8               Host (EC2 CPU)
      │ 32 kHz PCM (I²S)                                             │ AXI-Lite (Ctrl)
      ▼                        AXI-Stream (16-bit words @ 200 MHz)   ▼
 ┌───────────┐      ┌─────────────────┐      ┌──────────────────────────────┐
 │ Audio In  │──►──▶│ DSP Front-End   │──►──▶│ DMA Engine / PCIe Endpoint   │───► Host Memory → ASR SW → NLP SW → User API
 └───────────┘      └─────────────────┘      └──────────────────────────────┘
```
- DSP Front-End: We will implement a first-order pre-emphasis (high-pass filter), Hann window, FFT, Mel Filter Bank, and finally Log + DCT
```
 raw PCM ─► pre-emphasis ─► window ─► FFT ─► Mel filter bank ─► **log** ─► **DCT** ─► MFCC vector
                                                             (energy)     (decorrelate)
```                                                           
- ASR Engine: The ASR will be a 12-layer encoder-only transformer. It will run in PyTorch on the host CPU. 
- NLP Micro-Transformer: The NLP will likely be a 2-layer distilled MinLM that produces keyword and topic IDs.

Note: Depending on the complexity of the project, the final project block diagram may be: 


 
## Measure:
To measure quality, we will look at (1) raw performance, (2) resource utilization, and (3) task-specific accuracy. More specifics are below:
- Raw Performance: To analyze raw performance, we will measure end-to-end latency (mic-in to text-out). We will also analyze throughput.
- Resource Utilization: To analyze resource utilization, we will measure LUT percentage, FF percentage, BRAM percentage, and DSP slices
- Task Specific Accuracy: To ensure application specific performance is good, we will measure Word-Error-Rate (WER) and Keyword-Spotting.  

## Planning:
### Software Simulation (Week 1)
- Implement and test DSP pipeline in Python 
- Evaluate the ASR and NLP models with sample audio to establish performance baselines


### Hardware Simulation (Week 2 and 3)
- Translate DSP pipeline to spatial

###  Integration & Testing (Week 4)
- Connect hardware DSP.
- Measure system performance (latency, throughput, utilization) and verify text output quality. 

## Extended Use-cases: 
1. Sign-language avatar: By feeding the NLP text results to the python api, sign-language-translator, this application can be used for live translation of audio to sign langaugae.
2. Video highlight indexing: This model can be used to commment and sumarize videos by their audio features (e.g., laughter).

# References/Relevant Works:
Sign Language via FPGA: https://doi.org/10.1109/RTEICT.2017.8256604
This paper presents an FPGA-based system for real-time hand sign recognition, emphasizing low power, high speed, and efficient human-computer interaction. It uses image capture and filtering, followed by FPGA processing for gesture classification. Although the focus is vision-based rather than audio, it supports our extended use case of live sign-language generation from speech. 

FPGA-Powered ASR Solution: https://www.newscienceventures.com/achronix-announces-fpga-powered-accelerated-automatic-speech-recognition-solution/
Achronix developed an FPGA-based automatic speech recognition solution capable of handling 1,000 concurrent real-time streams with high accuracy and low latency. 

https://homepages.on.hs-bremen.de/~jbredereke/downloads/speech-recognition-fpga-neural-network-wp_embeds-2022.pdf
Researched from the City University of Applied Sciences Bremen implemented a speech command recognition system on an FPGA-based system on chip(SoC), The design includes audio acquisition and Mel Frqeuncy Cepstral Coefficient feature extraction on the FPGA, followed by a quantized neural network for classification
