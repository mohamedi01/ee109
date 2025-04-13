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
#include <ap_int.h>

#define SRAM_DEPTH 512
#define DRAM_DEPTH 1024
#define WORD_SIZE 32
#define NUM_WORDS ((SRAM_DEPTH) / WORD_SIZE)

static void compute_add(hls::vector<uint32_t, NUM_WORDS> *in,
                        int x,
                        hls::vector<uint32_t, NUM_WORDS> *out,
                        int vSize)
{
    // The kernel is operating with vector of NUM_WORDS integers. The * operator performs
    // an element-wise multiplication, resulting in NUM_WORDS parallel operations.
    hls::vector<uint32_t, NUM_WORDS> tile_in;
    hls::vector<uint32_t, NUM_WORDS> tile_out;

    #pragma HLS PIPELINE II=1
    for (int i = 0; i < vSize; i++) {
    tile_load: //Loading tile into SRAM
        tile_in = in[i];
    tile_execute: //Executing the element wise multiplication 
    #pragma HLS PIPELINE II=1
        for (int j=0; j < NUM_WORDS; j++) {
            tile_out[j] = x * tile_in[j];
        }
    tile_store: //Storing tile from SRAM into DRAM
        out[i] = tile_out;

    }
}


extern "C"
{

    /*
        Vector Addition Operation

        Arguments:
            in  (input)  --> Input vector 1
            x   (input)  --> Multiplied factor
            out  (output) --> Output vector
            size (input)  --> Number of elements in vector
    */

    void vadd(hls::vector<uint32_t, NUM_WORDS> *in,
              int x ,
              hls::vector<uint32_t, NUM_WORDS> *out,
              int size)
    {
#pragma HLS INTERFACE m_axi port = in bundle = gmem0
#pragma HLS INTERFACE m_axi port = out bundle = gmem0

        // Since NUM_WORDS values are processed
        // in parallel per loop iteration, the for loop only needs to iterate 'size / NUM_WORDS' times.
        assert(size % NUM_WORDS == 0);
        int vSize = size / NUM_WORDS;

        compute_add(in, x, out, vSize);

    }
}
