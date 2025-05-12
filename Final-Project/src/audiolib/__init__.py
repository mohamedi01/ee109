from importlib.metadata import version
__version__ = version("audiolib")          # expose package version

from .dsp.mfcc import wav_to_mfcc           # convenience re-exports
from .dsp.mel  import wav_to_logmel