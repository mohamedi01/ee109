"""
Whisper-tiny ASR that accepts **external 80×T log-Mel features**,
so the DSP front-end can feed it directly.
"""
from functools import lru_cache
import numpy as np, torch, transformers

MODEL_NAME = "openai/whisper-tiny.en"

@lru_cache(maxsize=1)
def _load(device="cpu"):
    model = transformers.AutoModelForSpeechSeq2Seq.from_pretrained(
                MODEL_NAME, torch_dtype=torch.float32).to(device).eval()
    tok   = transformers.AutoTokenizer.from_pretrained(MODEL_NAME)
    return model, tok

def _pad_or_crop(feat: torch.Tensor, target: int = 3000) -> torch.Tensor:
    T = feat.shape[1]
    if T < target:
        pad = torch.zeros((80, target - T), dtype=feat.dtype, device=feat.device)
        feat = torch.cat([feat, pad], dim=1)
    elif T > target:
        feat = feat[:, :target]
    return feat

def transcribe_features(logmel: np.ndarray | torch.Tensor,
                        device: str = "cpu") -> str:
    """
    logmel —  float32 array (80, T) in log-Mel space.
    Pads/crops to 3000 frames (30 s) as Whisper expects.
    """
    model, tok = _load(device)
    if not torch.is_tensor(logmel):
        logmel = torch.from_numpy(logmel)
    logmel = _pad_or_crop(logmel.to(device))

    with torch.inference_mode():
        ids = model.generate(input_features=logmel.unsqueeze(0))[0]
    return tok.decode(ids, skip_special_tokens=True).strip()