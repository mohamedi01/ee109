Lab1Part1RegExample
==================================

This is simple example of vector addition to describe the usage of XRT Native API's. The kernel uses HLS Dataflow which allows the user to schedule multiple task together to achieve higher throughput.

**KEY CONCEPTS:** `XRT Native API <https://docs.xilinx.com/r/en-US/ug1393-vitis-application-acceleration/Setting-Up-XRT-Managed-Kernels-and-Kernel-Arguments>`__, `Task Level Parallelism <https://docs.xilinx.com/r/en-US/ug1399-vitis-hls/Data-driven-Task-level-Parallelism>`__

**KEYWORDS:** `xrt::device <https://docs.xilinx.com/r/en-US/ug1393-vitis-application-acceleration/Specifying-the-Device-ID-and-Loading-the-XCLBIN>`__, load_xclbin, `xrt::bo <https://docs.xilinx.com/r/en-US/ug1393-vitis-application-acceleration/Writing-Host-Applications-with-XRT-API>`__, `xrt::kernel <https://docs.xilinx.com/r/en-US/ug1393-vitis-application-acceleration/Setting-Up-XRT-Managed-Kernels-and-Kernel-Arguments>`__, `map <https://docs.xilinx.com/r/en-US/ug1393-vitis-application-acceleration/Transferring-Data-between-Software-and-PL-Kernels>`__, `sync <https://docs.xilinx.com/r/en-US/ug1393-vitis-application-acceleration/Transferring-Data-between-Software-and-PL-Kernels>`__, `XCL_BO_SYNC_BO_TO_DEVICE <https://docs.xilinx.com/r/en-US/ug1393-vitis-application-acceleration/Writing-Host-Applications-with-XRT-API>`__, `XCL_BO_SYNC_BO_FROM_DEVICE <https://docs.xilinx.com/r/en-US/ug1393-vitis-application-acceleration/Writing-Host-Applications-with-XRT-API>`__, `gmem <https://docs.xilinx.com/r/en-US/ug1393-vitis-application-acceleration/Mapping-Kernel-Ports-to-Memory>`__, `#pragma HLS INTERFACE <https://docs.xilinx.com/r/en-US/ug1399-vitis-hls/HLS-Pragmas>`__, `dataflow <https://docs.xilinx.com/r/en-US/ug1399-vitis-hls/Dataflow>`__, `hls::stream <https://docs.xilinx.com/r/en-US/ug1399-vitis-hls/HLS-Stream-Library>`__

.. raw:: html

 <details>

.. raw:: html

 <summary> 

 <b>EXCLUDED PLATFORMS:</b>

.. raw:: html

 </summary>
|
..

 - All NoDMA Platforms, i.e u50 nodma etc

.. raw:: html

 </details>

.. raw:: html

DESIGN FILES
------------

Application code is located in the src directory. Accelerator binary files will be compiled to the xclbin directory. The xclbin directory is required by the Makefile and its contents will be filled during compilation. A listing of all the files in this example is shown below

::

   src/host.cpp
   src/vadd.cpp
   
COMMAND LINE ARGUMENTS
----------------------

Once the environment has been configured, the application can be executed by

::

   ./Lab1Part1RegExample <vadd XCLBIN>

DETAILS
-------

This is the hls implementation of Lab1Part1RegExample 
