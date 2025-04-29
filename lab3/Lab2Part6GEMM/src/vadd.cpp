/**
 * Copyright (C) 2019-2021 Xilinx, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may
 * not use this file except in compliance with the License. A copy of the
 * License is located at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

// Includes
#include <hls_vector.h>
#include <hls_stream.h>
#include "assert.h"

#define N 4
#define TILE 2

static void gemm(int *in1,
                int *in2,
                int *out)
{
    int in1_tile[TILE][TILE];
    int in2_tile[TILE][TILE];
    int out_tile[TILE][TILE];

//#pragma HLS ARRAY_PARTITION variable=in1_tile complete dim=2
//#pragma HLS ARRAY_PARTITION variable=in2_tile complete dim=1

    for (int i0 = 0; i0 < N; i0 += TILE) {
        for (int j0 = 0; j0 < N; j0 += TILE) {
            // Initialising Tile value to zero
            for (int ii = 0; ii < TILE; ii++) {
                for (int jj = 0; jj < TILE; jj++) {
                    out_tile[ii][jj] = 0;
                }
            }

            for (int k0 = 0; k0 < N; k0 += TILE) {
                // Extracting Tile for A
                for (int ii = 0; ii < TILE; ii++) {
                    for (int kk = 0; kk < TILE; kk++) {
                        //Matrices are save in row major manner
                        // Stored sequential in memory one row after another
                        in1_tile[ii][kk] = in1[(i0 + ii) * N + (k0 + kk)];
                    }
                }
                
                // Extracting Tile for B
                for (int kk = 0; kk < TILE; kk++) {
                    for (int jj = 0; jj < TILE; jj++) {
                        //Matrices are save in row major manner
                        // Stored sequential in memory one row after another
                        in2_tile[kk][jj] = in2[(k0 + kk) * N + (j0 + jj)];
                    }
                }

                // Tile Matrix Multiplication
                for (int ii = 0; ii < TILE; ii++) {
                    for (int jj = 0; jj < TILE; jj++) {
                        for (int kk = 0; kk < TILE; kk++) {
                            // TODO: add your code here to perform the tile GEMM on out_tile
                        }
                    }
                }
            }

            // Writing output tile
            for (int ii = 0; ii < TILE; ii++) {
                for (int jj = 0; jj < TILE; jj++) {
                    out[(i0 + ii) * N + (j0 + jj)] = out_tile[ii][jj];
                }
            }
        }
    }
}


extern "C"
{

    void vadd(int *in1,
              int *in2,
              int *out)
    {
#pragma HLS INTERFACE m_axi port = in1 bundle = gmem0
#pragma HLS INTERFACE m_axi port = in2 bundle = gmem1
#pragma HLS INTERFACE m_axi port = out bundle = gmem0


        gemm(in1, in2, out);
    }
}
