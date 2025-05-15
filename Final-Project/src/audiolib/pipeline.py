from pathlib import Path
from typing import Union, Dict, Any

from audiolib.asr.transcribe import transcribe_audio_file
from audiolib.nlp.nlp import summarize_text

def process_audio_to_nlp(
    audio_path: Union[str, Path], 
    device: str = "cpu"
) -> Dict[str, Any]:
    """
    Processes an audio file through ASR and NLP stages.
    """
    # Stage 1: Transcribe audio to text
    transcript = transcribe_audio_file(audio_path=audio_path, device=device)

    # Stage 2: Summarize the transcribed text
    summary = summarize_text(
        text=transcript,
        device=device
    )

    nlp_results = {"summary": summary}

    return {
        "transcript": transcript,
        "nlp_analysis": nlp_results,
    } 