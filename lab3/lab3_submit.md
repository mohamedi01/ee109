# Lab3 Submission

## Part 1 - Convolution
### Copy and paste your control tree for your Lab3Part1Convolution implementation.
The control tree can be found in `logs/VCS/Lab3Part1Convolution/run.log`.
x373 - 12993 (12993 / 1) [1 iters/parent execution]
    x733 - 12989 (12989 / 1) [1 iters/parent execution]
      x543 - 198 (3183 / 16) [16 iters/parent execution]
        x533 - 5 (80 / 16) [1 iters/parent execution] # stalled: 0, #idle: 0
        x542 - 194 (3119 / 16) [1 iters/parent execution] # stalled: 0, #idle: 2847
      x840 - 786 (12576 / 16) [16 iters/parent execution]
        x839 - 13 (3328 / 256) [16 iters/parent execution]
          x550 - 3 (768 / 256) [1 iters/parent execution]
          x560 - 8 (2048 / 256) [1 iters/parent execution]
        x598 - 12 (3072 / 256) [16 iters/parent execution]
        x636 - 12 (3072 / 256) [16 iters/parent execution]
        x662 - 6 (1536 / 256) [16 iters/parent execution]
      x732 - 171 (2744 / 16) [16 iters/parent execution]
        x675 - 5 (80 / 16) [1 iters/parent execution] # stalled: 0, #idle: 0
        x727 - 5 (80 / 16) [1 iters/parent execution] # stalled: 0, #idle: 0
        x731 - 167 (2680 / 16) [1 iters/parent execution] # stalled: 0, #idle: 2648


## Part 2 - Vitis HLS

1. What is the accelerator runtime (`xrt::run::run` time in the `summary.csv`) of the unoptimized `Lab3VectorAddOpt`? 
    1.1 0.006591
2. How did you decide to unroll the loop to improve performance?
    2.1 To improve performance, we applied #pragmas HLS unroll factor=2 to the loop in compute_add(). Unrolling the loop allows multiple vector additions to happen concurrently and increases our throughput. We tested factors 2, 4, and 8. The unroll factor 2 gave the best performance.
3. What is the xrt::run::run time of the optimized implementation? 
    3.1 0.006254
