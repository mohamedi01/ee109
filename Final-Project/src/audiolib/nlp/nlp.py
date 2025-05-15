"""
nlp.py

Simple text summarization with performance measurement.
"""
import time
from transformers import pipeline

# -- Summarization pipeline cache ----------------------------------------------
_summarizer = None

# -- Public API ----------------------------------------------------------------
def summarize_text(
    text: str,
    max_length: int = 60,
    min_length: int = 20,
    device: str = "cpu"
) -> str:
    """
    Generate a concise summary of the input text.

    Args:
        text: The input text to summarize.
        max_length: Maximum length of the summary (in tokens).
        min_length: Minimum length of the summary.
        device: 'cpu' or 'cuda'.

    Returns:
        A summary string.
    """
    global _summarizer
    if _summarizer is None:
        device_id =-1
        _summarizer = pipeline(
            "summarization",
            model="sshleifer/distilbart-cnn-12-6",
            device=device_id,
        )

    result = _summarizer(
        text,
        max_length=max_length,
        min_length=min_length,
        do_sample=False,
    )
    return result[0]["summary_text"]


def measure_latency(texts: list[str], device: str = "cpu") -> float:
    """
    Compute average inference latency (milliseconds) for summarize_text over a list of texts.

    Args:
        texts: A list of input strings to measure.
        device: 'cpu' or 'cuda'.

    Returns:
        Average latency in milliseconds.
    """
    times = []
    for t in texts:
        start = time.perf_counter()
        summarize_text(t, device=device)
        end = time.perf_counter()
        times.append(end - start)
    # convert to ms
    return sum(times) / len(times) * 1000.0

__all__ = [
    "summarize_text",
    "measure_latency",
]
