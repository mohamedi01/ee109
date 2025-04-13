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

static void compute_add(ap_int<32> *in1,
                        ap_int<32> *in2,
                        ap_int<32> *out)
{
execute:
    out[0] = in1[0] + in2[0];
}

extern "C"
{

    /*
        Addition Kernel

        Arguments:
            in1  (input)  --> Input Register 1
            in2  (input)  --> Input Register 2
            out  (output) --> Output Register
    */

    void vadd(ap_int<32> *in1,
              ap_int<32> *in2,
              ap_int<32> *out)
    {
#pragma HLS INTERFACE m_axi port = in1 bundle = gmem0
#pragma HLS INTERFACE m_axi port = in2 bundle = gmem1
#pragma HLS INTERFACE m_axi port = out bundle = gmem0

        compute_add(in1, in2, out);
    }
}