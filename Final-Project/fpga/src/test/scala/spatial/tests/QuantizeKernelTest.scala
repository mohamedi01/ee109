import spatial.dsl._

@spatial class QuantizeKernelTest extends SpatialTest {
  override def runtimeArgs: Args = "128"

  def main(args: Array[String]): Unit = {
    type T = FixPt[TRUE,_16,_8]
    
    val input = Array[T](
      -1.to[T], -0.75.to[T], -0.5.to[T], -0.25.to[T],
       0.to[T],  0.25.to[T],  0.5.to[T],  0.75.to[T],  1.to[T]
    )
    val N = 9

    val inDRAM = DRAM[T](N)
    val outDRAM = DRAM[T](N)

    setMem(inDRAM, input)

    Accel {
      val inSRAM = SRAM[T](N)
      val outSRAM = SRAM[T](N)

      inSRAM load inDRAM

      Foreach(N by 1) { i =>
        val x = inSRAM(i) * 32767.to[T]
        val clipped = mux(x < -32768.to[T], -32768.to[T], mux(x > 32767.to[T], 32767.to[T], x))
        val quantized = mux(clipped < 0.to[T],
                            (clipped - 0.5.to[T]),
                            (clipped + 0.5.to[T])) / 32768.to[T]
        outSRAM(i) = quantized
      }

      outDRAM store outSRAM
    }

    val result = getMem(outDRAM)

    val gold = input.map { x =>
      val scaled = x * 32767.to[T]
      val clipped = if (scaled < -32768.to[T]) -32768.to[T] else if (scaled > 32767.to[T]) 32767.to[T] else scaled
      val rounded = if (clipped < 0.to[T]) (clipped - 0.5.to[T]) else (clipped + 0.5.to[T])
      rounded / 32768.to[T]
    }

    val cksum = gold.zip(result){ case (g, r) => abs(g - r) < 0.01.to[T] }.reduce(_ && _)
    println("PASS: " + cksum)
    assert(cksum == 1)
  }
}