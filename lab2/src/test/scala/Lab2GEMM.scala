import spatial.dsl._

@spatial class Lab2Part5GEMM extends SpatialTest {

  override def runtimeArgs = "32 32 32"
  
  def main(args: Array[String]): Unit = {
  
    type T = FixPt[TRUE,_24,_8]
    
    // Reading in arguments for the dimensions of the matrices
    val M = ArgIn[Int]
    val N = ArgIn[Int]
    val K = ArgIn[Int]
    setArg(M,args(0).to[Int])
    setArg(N,args(1).to[Int])
    setArg(K,args(2).to[Int])
    
    // Declaring & Initializing the input matrices and output matrix
    val a_data = (0::args(0).to[Int], 0::args(2).to[Int]){(i,j) => random[T](3)}
    val b_data = (0::args(2).to[Int], 0::args(1).to[Int]){(i,j) => random[T](3)}
    val c_init = (0::args(0).to[Int], 0::args(1).to[Int]){(i,j) => 0.to[T]}
    val a = DRAM[T](M, K) // input matrix 1
    val b = DRAM[T](K, N) // input matrix 2
    val c = DRAM[T](M, N) // an initial matrix for the result
    
    setMem(a, a_data)
    setMem(b, b_data)
    setMem(c, c_init)

    // Set the tile size for each dimension
    val tileM = 16
    val tileN = 16
    val tileK = 16
    
    Accel {
        Foreach(K by tileK){kk => 
            val numel_k = min(tileK.to[Int], K - kk)
            Foreach(M by tileM){mm =>
                val numel_m = min(tileM.to[Int], M - mm)
                val tileA_sram = SRAM[T](tileM, tileK)
                tileA_sram load a(mm::mm+numel_m, kk::kk+numel_k)
                Foreach(N by tileN){nn =>
                    val numel_n = min(tileN.to[Int], N - nn)
                    val tileB_sram = SRAM[T](tileK, tileN)
                    val tileC_sram = SRAM[T](tileM, tileN).buffer
                    tileB_sram load b(kk::kk+numel_k, nn::nn+numel_n) // DRAM -> SRAM
                    tileC_sram load c(mm::mm+numel_m, nn::nn+numel_n) // DRAM -> SRAM
                    
                    // TODO: Implement the full outer product here
                    Foreach(numel_m by 1) { m =>
                      Foreach(numel_n by 1) { n =>
                        Foreach(numel_k by 1) { k =>
                          tileC_sram(m, n) = tileC_sram(m, n) + tileA_sram(m, k) * tileB_sram(k, n)
                        }
                      }
                    }
                    c(mm::mm+numel_m, nn::nn+numel_n) store tileC_sram // SRAM -> DRAM
                }
            }
        }
    }
    
    val accel_matrix = getMatrix(c)
    
    // Gold calculation & checking
    val gold_matrix = (0::args(0).to[Int], 0::args(1).to[Int]){(i,j) => 
      Array.tabulate(args(2).to[Int]){k => a_data(i,k) * b_data(k,j)}.reduce{_+_}
    }
    
    printMatrix(accel_matrix, "Received: ")
    printMatrix(gold_matrix, "Wanted: ")
    val cksum = accel_matrix.zip(gold_matrix){_==_}.reduce{_&&_}
    println("Pass? " + cksum)
    assert (cksum == 1)
  }
}


@spatial class Lab2Part6GEMM extends SpatialTest {

  override def runtimeArgs = "32 32 32"
  
  def main(args: Array[String]): Unit = {
  
    type T = FixPt[TRUE,_24,_8]
    
    // Reading in arguments for the dimensions of the matrices
    val M = ArgIn[Int]
    val N = ArgIn[Int]
    val K = ArgIn[Int]
    setArg(M,args(0).to[Int])
    setArg(N,args(1).to[Int])
    setArg(K,args(2).to[Int])
    
    // Declaring & Initializing the input matrices and output matrix
    val a_data = (0::args(0).to[Int], 0::args(2).to[Int]){(i,j) => random[T](3)}
    val b_data = (0::args(2).to[Int], 0::args(1).to[Int]){(i,j) => random[T](3)}
    val c_init = (0::args(0).to[Int], 0::args(1).to[Int]){(i,j) => 0.to[T]}
    val a = DRAM[T](M, K) // input matrix 1
    val b = DRAM[T](K, N) // input matrix 2
    val c = DRAM[T](M, N) // an initial matrix for the result
    
    setMem(a, a_data)
    setMem(b, b_data)
    setMem(c, c_init)

    // Set the tile size for each dimension
    val tileM = 16
    val tileN = 16
    val tileK = 16
    
    Accel {
        Foreach(K by tileK){kk => 
            val numel_k = min(tileK.to[Int], K - kk)
            Foreach(M by tileM){mm =>
                val numel_m = min(tileM.to[Int], M - mm)
                val tileA_sram = SRAM[T](tileM, tileK)
                tileA_sram load a(mm::mm+numel_m, kk::kk+numel_k)
                Foreach(N by tileN){nn =>
                    val numel_n = min(tileN.to[Int], N - nn)
                    val tileB_sram = SRAM[T](tileK, tileN)
                    val tileC_sram = SRAM[T](tileM, tileN).buffer
                    tileB_sram load b(kk::kk+numel_k, nn::nn+numel_n) // DRAM -> SRAM
                    tileC_sram load c(mm::mm+numel_m, nn::nn+numel_n) // DRAM -> SRAM
                    
                    // TODO: Implement the full outer product here
                    // You can copy paste your implementation from Lab2GEMM
                    // and add parallelization factors to your controllers that appear here
                    Foreach(numel_m by 1 par 4) { m =>
                      Foreach(numel_n by 1 par 4) { n =>
                        Foreach(numel_k by 1) { k =>
                          tileC_sram(m, n) = tileC_sram(m, n) + tileA_sram(m, k) * tileB_sram(k, n)
                        }
                      }
                    }
                    c(mm::mm+numel_m, nn::nn+numel_n) store tileC_sram // SRAM -> DRAM
                }
            }
        }
    }
    
    val accel_matrix = getMatrix(c)
    
    // Gold calculation & checking
    val gold_matrix = (0::args(0).to[Int], 0::args(1).to[Int]){(i,j) => 
      Array.tabulate(args(2).to[Int]){k => a_data(i,k) * b_data(k,j)}.reduce{_+_}
    }
    
    printMatrix(accel_matrix, "Received: ")
    printMatrix(gold_matrix, "Wanted: ")
    val cksum = accel_matrix.zip(gold_matrix){_==_}.reduce{_&&_}
    println("Pass? " + cksum)
    assert (cksum == 1)
  }
}