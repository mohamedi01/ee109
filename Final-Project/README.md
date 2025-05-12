# EE 109 Final Project — DSP Front-End Demo

## Quick start

```bash
python -m venv ee109_final_project
source ee109_final_project/bin/activate      # Windows: .\ee109_final_project\Scripts\activate
pip install -r requirements.txt
python - <<'PY'
from src import dsp
mfcc = dsp.wav_to_mfcc("data/sample.wav")
print("MFCC shape:", mfcc.shape)
PY
cat > README.md <<'EOF'
quit
q
