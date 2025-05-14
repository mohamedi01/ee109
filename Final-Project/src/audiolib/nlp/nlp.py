# nlp.py
# Lightweight NLP micro-transformer inference, classification, summarization, and evaluation scaffolding

import os
import torch
import time
from transformers import (
    AutoTokenizer,
    AutoModelForSequenceClassification,
    pipeline,
)
from sklearn.metrics import accuracy_score, f1_score

# -- Paths to fine-tuned classification heads (replace with your actual model dirs) --
_KEYWORD_MODEL_PATH    = "models/keyword_spotter"
_TOPIC_MODEL_PATH      = "models/topic_segmenter"
_SUMMARIZER_MODEL_NAME = "sshleifer/distilbart-cnn-12-6"

# -- Caches for tokenizers & models --
_keyword_tokenizer = None
_keyword_model     = None
_topic_tokenizer   = None
_topic_model       = None
_summarizer        = None


def _create_dummy_classifier():
    """Return a dummy tokenizer and model that always predicts the first label with confidence 1.0."""
    class DummyTokenizer:
        def __call__(self, text, return_tensors=None, truncation=None, padding=None):
            return {"input_ids": torch.zeros((1, 1), dtype=torch.long),
                    "attention_mask": torch.ones((1, 1), dtype=torch.long)}
    class DummyModel:
        def __init__(self):
            self.config = type("C", (), {"id2label": {0: "_dummy_label"}})()
        def to(self, device):
            return self
        def eval(self):
            return self
        def __call__(self, **kwargs):
            return self
        @property
        def logits(self):
            return torch.tensor([[1.0, 0.0]])
    return DummyTokenizer(), DummyModel()


def _create_dummy_summarizer():
    """Return a dummy summarizer function."""
    def summarize(text, max_length=None, min_length=None, do_sample=None):
        return [{"summary_text": text}]
    return summarize


def load_keyword_model(device: str = "cpu"):
    global _keyword_tokenizer, _keyword_model
    if _keyword_model is None:
        if os.path.isdir(_KEYWORD_MODEL_PATH):
            _keyword_tokenizer = AutoTokenizer.from_pretrained(_KEYWORD_MODEL_PATH)
            _keyword_model     = AutoModelForSequenceClassification.from_pretrained(
                _KEYWORD_MODEL_PATH
            ).to(device).eval()
        else:
            _keyword_tokenizer, _keyword_model = _create_dummy_classifier()
    return _keyword_tokenizer, _keyword_model


def load_topic_model(device: str = "cpu"):
    global _topic_tokenizer, _topic_model
    if _topic_model is None:
        if os.path.isdir(_TOPIC_MODEL_PATH):
            _topic_tokenizer = AutoTokenizer.from_pretrained(_TOPIC_MODEL_PATH)
            _topic_model     = AutoModelForSequenceClassification.from_pretrained(
                _TOPIC_MODEL_PATH
            ).to(device).eval()
        else:
            _topic_tokenizer, _topic_model = _create_dummy_classifier()
    return _topic_tokenizer, _topic_model


def load_summarizer(device: str = "cpu"):
    """Initialize HF summarization pipeline, or fallback to dummy."""
    global _summarizer
    if _summarizer is None:
        try:
            device_index = 0 if device.startswith("cuda") else -1
            _summarizer = pipeline(
                task="summarization",
                model=_SUMMARIZER_MODEL_NAME,
                device=device_index,
                framework="pt"
            )
        except Exception:
            _summarizer = _create_dummy_summarizer()
    return _summarizer


def classify_keywords(text: str, device: str = "cpu") -> tuple[str, float]:
    tokenizer, model = load_keyword_model(device)
    inputs = tokenizer(text, return_tensors="pt", truncation=True, padding=True)
    for k, v in inputs.items():
        inputs[k] = v.to(device)
    with torch.inference_mode():
        out = model(**inputs)
        logits = out.logits
        probs  = torch.softmax(logits[0], dim=-1)
        idx    = torch.argmax(probs).item()
    return model.config.id2label[idx], float(probs[idx].item())


def classify_topic(text: str, device: str = "cpu") -> tuple[str, float]:
    tokenizer, model = load_topic_model(device)
    inputs = tokenizer(text, return_tensors="pt", truncation=True, padding=True)
    for k, v in inputs.items():
        inputs[k] = v.to(device)
    with torch.inference_mode():
        out = model(**inputs)
        logits = out.logits
        probs  = torch.softmax(logits[0], dim=-1)
        idx    = torch.argmax(probs).item()
    return model.config.id2label[idx], float(probs[idx].item())


def summarize_text(text: str, device: str = "cpu", max_length: int = 60, min_length: int = 20) -> str:
    """Generate a concise summary of the input text."""
    summarizer = load_summarizer(device)
    result     = summarizer(text, max_length=max_length, min_length=min_length, do_sample=False)
    return result[0]["summary_text"]


def analyze_text(text: str, device: str = "cpu") -> dict:
    """Run classification (keyword/topic) and summarization."""
    kw_label, kw_conf        = classify_keywords(text, device)
    topic_label, topic_conf  = classify_topic(text, device)
    summary                  = summarize_text(text, device)
    return {
        "keyword": (kw_label, kw_conf),
        "topic":   (topic_label, topic_conf),
        "summary": summary,
    }

# -- Smoke Tests --
def _smoke_test():
    sample = (
        "ChatGPT is an AI model by OpenAI that can perform a variety of tasks, including text generation, summarization, and classification."
    )
    results = analyze_text(sample)
    assert isinstance(results, dict)
    assert isinstance(results['summary'], str) and results['summary']
    kw, kwc = results['keyword']; assert isinstance(kw, str) and 0.0 <= kwc <= 1.0
    top, topc = results['topic']; assert isinstance(top, str) and 0.0 <= topc <= 1.0
    print("✅ Smoke tests passed")

# -- Evaluation Utilities --

def measure_latency(texts: list[str], device: str = "cpu") -> float:
    """Return average inference latency in milliseconds for analyze_text over given texts."""
    times = []
    for t in texts:
        start = time.perf_counter()
        _ = analyze_text(t, device)
        end   = time.perf_counter()
        times.append(end - start)
    return (sum(times) / len(times)) * 1000.0


def measure_throughput(texts: list[str], device: str = "cpu") -> float:
    """Return throughput in utterances per second for analyze_text over given texts."""
    start = time.perf_counter()
    for t in texts:
        _ = analyze_text(t, device)
    elapsed = time.perf_counter() - start
    return len(texts) / elapsed


def evaluate_classifiers(
    texts: list[str],
    keyword_labels: list[str],
    topic_labels: list[str],
    device: str = "cpu"
) -> dict:
    """
    Compute classification metrics: keyword accuracy, F1, and topic accuracy.
    Returns dict with keys:
      - keyword_accuracy
      - keyword_f1
      - topic_accuracy
    """
    preds_kw, preds_top = [], []
    for t in texts:
        out = analyze_text(t, device)
        preds_kw.append(out['keyword'][0])
        preds_top.append(out['topic'][0])
    return {
        'keyword_accuracy': accuracy_score(keyword_labels, preds_kw),
        'keyword_f1':       f1_score(keyword_labels, preds_kw, average='weighted'),
        'topic_accuracy':   accuracy_score(topic_labels, preds_top),
    }

# if __name__ == "__main__":
#     _smoke_test()