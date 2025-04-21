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

#define NUM_WORDS 32

static void fsm(hls::vector<uint32_t, NUM_WORDS> *bram_out)
{
    hls::vector<uint32_t, NUM_WORDS> bram;
    for (int state=0; state < NUM_WORDS; state++) {
        //TODO add the FSM control logic
        //NUM_WORDS = 32, implying that there are 32 entries in the SRAM vector. 
        if (state < 8) bram[state] = state;
        else if (state < 16) bram[state] = state * 2;
        else if (state < 24) bram[state] = state * 3;
        else bram[state] = state * 4;
    }
    bram_out[0] = bram;
}


extern "C"
{

    /*
        Arguments:
            bram  (inout)  --> input sram array
    */

    void vadd(hls::vector<uint32_t, NUM_WORDS> *bram)
    {
#pragma HLS INTERFACE m_axi port = bram bundle = gmem0
        fsm(bram);
    }
}
