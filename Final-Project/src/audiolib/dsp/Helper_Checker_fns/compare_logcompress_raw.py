import numpy as np
import os

io_dir = "fpga_io"

# Load the raw log10 outputs
fpga_raw = np.loadtxt(os.path.join(io_dir, "logcompress_output_raw_fpga.csv"))
python_raw = np.loadtxt(os.path.join(io_dir, "logcompress_output_raw_python.csv"))

# Print first 10 values for both
print("FPGA raw log10 (first 10):", fpga_raw[:10])
print("Python raw log10 (first 10):", python_raw[:10])
print("Difference (first 10):", fpga_raw[:10] - python_raw[:10])

# Print min/max for both
print("FPGA min/max:", np.min(fpga_raw), np.max(fpga_raw))
print("Python min/max:", np.min(python_raw), np.max(python_raw))

# Print max absolute and relative difference
abs_diff = np.abs(fpga_raw - python_raw)
rel_diff = np.abs(abs_diff / (python_raw + 1e-8))
print("Max abs diff:", np.max(abs_diff))
print("Max rel diff:", np.max(rel_diff))

tolerance = 1e-2
num_outside = np.sum(abs_diff > tolerance)
print(f"Elements with abs diff > {tolerance}: {num_outside} / {len(fpga_raw)}") 