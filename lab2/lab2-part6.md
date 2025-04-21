## Part 6
* How did you parallelize your code? (Since you are submitting the code a brief explanation with what controller you parallelized and by what factor is enough)

Our initial implementation used `Foreach` loops to iterate over n and m for k iterations as k would be the dimension that they shared. Because they are iterating over k, we could not parallelize the inner most loop; instead we used `par` to create more hardware units and parallelize over n and m. We tried a couple different factors and found that there would be diminishing returns (by way of wasted hardware units) after increasing from a factor of 8. 


* Based on the parallelizaion factors you added, how does this improve performance? (In the ``PostExecution.html``, you need to find the corresponding controller blocks that you parallelized.) For each controller you parallelized, report the number of cycles before and after you added the parallelization factor.
This improves performance by a factor of nearly 10. Before parallelizing (lab2part5gemm), the code had 302291 total cycles, 1 total iters; after parallelizing (lab2part6gemm), the code had 36112 total cycles, 1 total iters. 