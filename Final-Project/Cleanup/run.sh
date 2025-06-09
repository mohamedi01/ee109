set -e
# TEST_OPTS="-Dtest.CS217=true"

# Quantize kernel
sbt -Dtest.CS217=true "; testOnly spatial.tests.QuantizeKernelTestObject"

# Power spectrum
sbt -Dtest.CS217=true "; testOnly spatial.tests.PowerSpectrumTest"

# Mel filterbank
sbt -Dtest.CS217=true "; testOnly spatial.tests.MelFilterbankTest"

# Log compression
sbt -Dtest.CS217=true "; testOnly spatial.tests.LogCompressTest"

# Whisper scaling
sbt -Dtest.CS217=true "; testOnly spatial.tests.WhisperScaleTest"

# STFT
sbt -Dtest.CS217=true "; testOnly spatial.tests.STFTKernelTest"

echo "------------------------All tests passed!------------------------"
