# Lab1 Submission
Note - to fill this section, feel free to use online resources on Vitis HLS.
## Lab1Part1RegExample

1. What is the purpose `HLS INTERFACE` pragmas in the `vadd.cpp`? - The HLS INTERFACE pragmas tell the compiler how the inputs and outputs of the kernel should connect to memory. It sets up in1, in2, out arrays to use AXI interfaces.
   
3. Report total application runtime (ms): 13

## Lab1Part2DramSramExample

1. What is the role of `HLS PIPELINE` pragmas in the `vadd.cpp`? - The HLS Pipeline pragmas are used to speed things up by lettinbg the loop start a new iteration every clock cycle.
   
3. Report total application runtime (ms): 32

## Lab1Part4FIFOExample

1. What is the role of `HLS STREAM` pragmas in the `vadd.cpp`? How is enabling the synthesis of a FIFO? - The HLS STREAM pragmas are used to create hardware FIFOs to allow data to move from one part of the kernel to another without needing to wait.
2. Why `.write` and `.read` functions being used?
   .write() puts data into the stream and .read() takes it out. They are used to pass valeus between stages of the loop without having to wait for it to complete.
4. Report total application runtime (ms): 32

## Lab1Part6ReduceExample
1. Can we find a spatial `reduce` equivalent for Vitis HLS? Just add the syntax here, no need to implement the same. - Yes.
   #pragma HLS PIPELINE
   for (int i = 0; i < N; i++){
     sum += data[i]
   }
3. Report total application runtime (ms): 32
