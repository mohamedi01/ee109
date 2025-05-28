set -e
TEST_OPTS="-Dtest.CS217=true"

# Quantize kernel
sbt $TEST_OPTS "; testOnly spatial.tests.QuantizeKernelTest"

# Power spectrum
sbt $TEST_OPTS "; testOnly spatial.tests.PowerSpectrumTest"

# Mel filterbank
sbt $TEST_OPTS "; testOnly spatial.tests.MelFilterbankTest"

# Log compression
# sbt $TEST_OPTS "; testOnly spatial.tests.LogCompressTest"

# # Whisper scaling
# sbt $TEST_OPTS "; testOnly spatial.tests.WhisperScaleTest"

# # STFT
# sbt $TEST_OPTS "; testOnly spatial.tests.STFTKernelTest"

echo "------------------------All tests passed!------------------------"
