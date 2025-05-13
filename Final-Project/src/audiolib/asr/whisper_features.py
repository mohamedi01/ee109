from functools import lru_cache
from typing import Union
import torch, numpy as np, transformers

_MODEL  = "openai/whisper-base.en"
_TARGET = 3000               # 30 s → 3 000 frames (10 ms hop)

@lru_cache(1)
def _load(device: str = "cpu"):
    model = transformers.AutoModelForSpeechSeq2Seq.from_pretrained(
        _MODEL, torch_dtype=torch.float32
    ).to(device).eval()
    tok   = transformers.AutoTokenizer.from_pretrained(_MODEL)
    return model, tok

def _pad_or_crop(x: torch.Tensor) -> torch.Tensor:
    T = x.shape[1]
    return torch.nn.functional.pad(x, (0, _TARGET - T)) if T < _TARGET else x[:, :_TARGET]

def transcribe_features(mel: Union[np.ndarray, torch.Tensor], device: str = "cpu") -> str:
    model, tok = _load(device)
    mel = torch.from_numpy(mel) if not torch.is_tensor(mel) else mel
    mel = _pad_or_crop(mel.to(device))
    with torch.inference_mode():
        ids = model.generate(
        input_features=mel.unsqueeze(0),
        do_sample=False,          
    )[0]
    return tok.decode(ids, skip_special_tokens=True).strip()