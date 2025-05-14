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
    # ensure Tensor
    mel_t = torch.as_tensor(mel, device=device)
    
    # Prepare input for the model (padded/cropped to _TARGET frames)
    processed_mel_t = _pad_or_crop(mel_t)

    # ---- decoding options 
    # Default options aligned with Whisper common practice
    opts = dict(
        input_features=processed_mel_t.unsqueeze(0),
        num_beams=5, # Whisper default beam size
    )
    
    # Apply special options for very short original clips (before padding/cropping)
    # This condition checks the original length of mel_t
    original_mel_frames = mel_t.shape[1]
    if original_mel_frames < 300: # 300 frames ≈ 3 s (original mel length)
        opts.update(
            num_beams=8, # Can use a slightly larger beam for very short segments
            length_penalty=0.1,
            # Make max_new_tokens more dynamic: ~1 token per 0.5s (100 frames per second -> 50 frames per 0.5s)
            # Ensure at least 3 tokens.
            max_new_tokens=max(3, int(original_mel_frames / 50)),          
        )

    with torch.inference_mode():
        ids = model.generate(**opts)[0]

    return tok.decode(ids, skip_special_tokens=True).strip().lower()