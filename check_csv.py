import sys

filename = 'Final-Project/fpga_io/power_matrix_1d.csv'
if len(sys.argv) > 1:
    filename = sys.argv[1]

with open(filename) as f:
    for i, line in enumerate(f, 1):
        s = line.strip()
        if not s:
            print(f'Line {i}: empty line')
            continue
        try:
            val = float(s)
            if abs(val) > 1e10 or (abs(val) < 1e-10 and val != 0.0):
                print(f'Line {i}: suspicious value {val}')
        except Exception as e:
            print(f'Line {i}: could not parse: {s} ({e})') 