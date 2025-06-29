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

/*******************************************************************************
Description:

    The kernel operates on vectors of NUM_WORDS integers modeled using the hls::vector
    data type. This datatype provides intuitive support for parallelism and
    fits well the vector-add computation. The vector length is set to NUM_WORDS
    since NUM_WORDS integers amount to a total of 64 bytes, which is the maximum size of
    a kernel port. It is a good practice to match the compute bandwidth to the I/O
    bandwidth. Here the kernel loads, computes and stores NUM_WORDS integer values per
    clock cycle and is implemented as below:
                                       _____________
Input Vector 1 from Global Memory --->|             |
Input Vector 2 from Global Memory --->| compute_add |
                                      |_____________|----> Output result to Global Memory

*******************************************************************************/

// Includes
#include <hls_vector.h>
#include <hls_stream.h>
#include "assert.h"

#define MEMORY_DWIDTH 512
#define SIZEOF_WORD 4
#define NUM_WORDS ((MEMORY_DWIDTH) / (8 * SIZEOF_WORD))

#define DATA_SIZE 4096

// TRIPCOUNT identifier
const int c_size = DATA_SIZE;

static void compute_add(hls::vector<uint32_t, NUM_WORDS> *in1,
                        hls::vector<uint32_t, NUM_WORDS> *in2,
                        hls::vector<uint32_t, NUM_WORDS> *out,
                        int vSize)
{
// The kernel is operating with vector of NUM_WORDS integers. The + operator performs
// an element-wise add, resulting in NUM_WORDS parallel additions.


// Fill in the TODO parts in this for loop
execute:
//Optimization - modify unroll pragma
//#pragma HLS unroll off=true
#pragma HLS unroll factor=2
	
for (int i = 0 ; i < vSize; i++) {
    out[i] = in1[i] + in2[i];
}

}


extern "C"
{

    /*
        Vector Addition Kernel

        Arguments:
            in1  (input)  --> Input vector 1
            in2  (input)  --> Input vector 2
            out  (output) --> Output vector
            size (input)  --> Number of elements in vector
    */

    void vadd(hls::vector<uint32_t, NUM_WORDS> *in1,
              hls::vector<uint32_t, NUM_WORDS> *in2,
              hls::vector<uint32_t, NUM_WORDS> *out,
              int size)
    {
#pragma HLS INTERFACE m_axi port = in1 bundle = gmem0
#pragma HLS INTERFACE m_axi port = in2 bundle = gmem1
#pragma HLS INTERFACE m_axi port = out bundle = gmem0

        // Since NUM_WORDS values are processed
        // in parallel per loop iteration, the for loop only needs to iterate 'size / NUM_WORDS' times.
        assert(size % NUM_WORDS == 0);
        int vSize = size / NUM_WORDS;

        compute_add(in1, in2, out, vSize);
    }
}
