with open("fpga_io/power_matrix_1d.csv") as f:
    for i, line in enumerate(f, 1):
        try:
            val = float(line.strip())
            # Check for very large/small exponents
            if abs(val) > 1e10 or (abs(val) < 1e-10 and val != 0.0):
                print(f"Line {i}: suspicious value {val}")
        except Exception as e:
            print(f"Line {i}: could not parse: {line.strip()} ({e})")
