import glob, pathlib
from audiolib.dsp.mel import wav_to_logmel
from audiolib.asr import transcribe_features

DIGIT_MAP = {
    "0": "zero", "1": "one", "2": "two",
    "3": "three", "4": "four", "5": "five",
    "6": "six",  "7": "seven", "8": "eight", "9": "nine"
}

def _expected_text(wav_path: str) -> str:
    digit = pathlib.Path(wav_path).name.split("_")[0]
    return DIGIT_MAP[digit]

def test_transcripts():
    for wav in glob.glob("data/*.wav"):
        txt = transcribe_features(wav_to_logmel(wav)).lower().strip(". ")
        assert txt.startswith(_expected_text(wav)), f"{wav} → {txt}"
