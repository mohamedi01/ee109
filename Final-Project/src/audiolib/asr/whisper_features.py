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
    # ensure Tensor, pad/crop to 80×3000
    mel_t = torch.as_tensor(mel, device=device)
    mel_t = _pad_or_crop(mel_t)

    # ---- decoding options -------------------------------------
    opts = dict(input_features=mel_t.unsqueeze(0))
    # for clips shorter than ≈1 s, add a tiny beam + hard stop
    if mel_t.shape[1] < 300:           # 300 frames ≈ 1 s
        opts.update(
            num_beams=8,
            length_penalty=0.1,
            max_new_tokens=3,          # stop after ⩽3 tokens
        )

    with torch.inference_mode():
        ids = model.generate(**opts)[0]

    return tok.decode(ids, skip_special_tokens=True).strip().lower()