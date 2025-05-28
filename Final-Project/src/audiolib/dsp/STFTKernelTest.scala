import spatial.dsl._

@spatial class STFTKernelTest extends SpatialTest {
  def main(args: Array[String]): Unit = {
    type T = Float
    val NFFT = 4
    val HOP = 2
    val FRAMES = 2
    val LEN = NFFT + (FRAMES - 1)*HOP

    val input = Array[T](1, 2, 3, 4, 5, 6)
    val inDRAM = DRAM[T](LEN)
    val realDRAM = DRAM[T](NFFT/2 + 1, FRAMES)
    val imagDRAM = DRAM[T](NFFT/2 + 1, FRAMES)

    setMem(inDRAM, input)

    Accel {
      val winSRAM = SRAM[T](NFFT)
      val inSRAM = SRAM[T](LEN)
      val frame = SRAM[T](NFFT)
      val real = SRAM[T](NFFT/2 + 1)
      val imag = SRAM[T](NFFT/2 + 1)
      val real2D = SRAM[T](NFFT/2 + 1, FRAMES)
      val imag2D = SRAM[T](NFFT/2 + 1, FRAMES)

      inSRAM load inDRAM
      Foreach(NFFT by 1) { i => winSRAM(i) = 1.to[T] }

      Foreach(FRAMES by 1) { f =>
        Foreach(NFFT by 1) { i =>
          frame(i) = inSRAM(f*HOP + i) * winSRAM(i)
        }

        Foreach(NFFT/2 + 1 by 1) { k =>
          real(k) = frame(k)
          imag(k) = 0.to[T]
        }

        Foreach(NFFT/2 + 1 by 1) { i =>
          real2D(i, f) = real(i)
          imag2D(i, f) = imag(i)
        }
      }

      realDRAM store real2D
      imagDRAM store imag2D
    }

    val realOut = getMatrix(realDRAM)
    val imagOut = getMatrix(imagDRAM)

    println("Real Output:")
    println("real(0,0): " + realOut(0,0))
    println("real(1,0): " + realOut(1,0))

    println("Imag Output:")
    println("imag(0,0): " + imagOut(0,0))
    println("imag(1,0): " + imagOut(1,0))

    val check = if (realOut(0,0) == 1.to[T] && realOut(1,0) == 2.to[T]) 1 else 0
    println("PASS: " + check)
    assert(check == 1)
  }
}
