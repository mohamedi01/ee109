import numpy as np

# Test our log10 algorithm
test_values = [9.26e-03, 1e-03, 1e-06, 1e-10]

for v0 in test_values:
    v_safe = max(v0, 1e-10)
    
    # Our algorithm
    int_part = 0.0
    v_norm = v_safe
    
    # Scale to [1, 10) range
    for j in range(15):
        if v_norm < 1.0:
            int_part -= 1.0
            v_norm *= 10.0
    
    for j in range(15):
        if v_norm >= 10.0:
            int_part += 1.0
            v_norm /= 10.0
    
    # Taylor approximation
    x_minus_1 = v_norm - 1.0
    x2 = x_minus_1 * x_minus_1
    x3 = x2 * x_minus_1
    frac_part = x_minus_1 * 0.4342944819 - x2 * 0.2171472410 + x3 * 0.1447648273
    
    result = int_part + frac_part
    expected = np.log10(v_safe)
    
    print(f"Input: {v0:.2e}, Safe: {v_safe:.2e}")
    print(f"  After scaling: v_norm={v_norm:.6f}, int_part={int_part}")
    print(f"  Our result: {result:.6f}, Expected: {expected:.6f}, Error: {result-expected:.6f}")
    print() 