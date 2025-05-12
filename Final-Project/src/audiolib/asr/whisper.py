"Whisper ASR wrapper (Tiny or Base)."
from functools import lru_cache
from transformers import pipeline
import soundfile as sf

def _build_pipe(model_name: str, device: str):
    return pipeline("automatic-speech-recognition",
                    model=model_name,
                    device=device)

@lru_cache(maxsize=2)
def _get_pipe(model_name: str, device: str):
    # cached by (model_name, device) tuple
    return _build_pipe(model_name, device)

def transcribe(wav_path: str,
               model: str = "openai/whisper-tiny.en",
               device: str = "cpu") -> str:
    """
    Transcribe `wav_path` and return a string.
    First call lazily downloads the model (~150 MB for Tiny).
    """
    pipe = _get_pipe(model, device)
    pcm, sr = sf.read(wav_path, dtype="float32")
    return pipe({"array": pcm, "sampling_rate": sr})["text"].strip()
