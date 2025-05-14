from pathlib import Path
from typing import Union, Dict, Any

from audiolib.asr.transcribe import transcribe_audio_file
from audiolib.nlp.nlp import analyze_text

def process_audio_to_nlp(
    audio_path: Union[str, Path], 
    device: str = "cpu"
) -> Dict[str, Any]:
    """
    Processes an audio file through ASR and NLP stages.

    Args:
        audio_path: Path to the audio file.
        device: The device to run computations on (e.g., "cpu", "cuda").

    Returns:
        A dictionary containing the transcript and NLP analysis results.
        Example:
        {
            "transcript": "The transcribed text...",
            "nlp_analysis": {
                "keyword": ("label", 0.95),
                "topic": ("label", 0.88),
                "summary": "A concise summary..."
            }
        }
    """
    # Stage 1: Transcribe audio to text
    transcript = transcribe_audio_file(audio_path=audio_path, device=device)

    # Stage 2: Analyze transcribed text
    nlp_results = analyze_text(text=transcript, device=device)

    return {
        "transcript": transcript,
        "nlp_analysis": nlp_results,
    } 