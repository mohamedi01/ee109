source ./exports.sh

# Quantize kernel
sbt -Dtest.VCS=true "; runMain spatial.tests.QuantizeKernelTest"

# Power spectrum
sbt -Dtest.VCS=true "; testOnly spatial.tests.PowerSpectrumTest"

# Mel filterbank
sbt -Dtest.VCS=true "; testOnly spatial.tests.MelFilterbankTest"

# Log compression
sbt -Dtest.VCS=true "; testOnly spatial.tests.LogCompressTest"

# Whisper scaling
sbt -Dtest.VCS=true "; testOnly spatial.tests.WhisperScaleTest"

# STFT
sbt -Dtest.VCS=true "; testOnly spatial.tests.STFTKernelTest"
