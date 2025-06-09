package spatial.tests
import spatial.dsl._

@spatial class PowerSpectrumTest extends SpatialTest {
  def main(args: Array[String]): Unit = {
    type T = Float
    val N = 4

    val realInput = Array[T](1, 2, 3, 4)
    val imagInput = Array[T](4, 3, 2, 1)
    val gold = Array[T](17, 13, 13, 17)  // real^2 + imag^2

    val realDRAM = DRAM[T](N)
    val imagDRAM = DRAM[T](N)
    val outDRAM = DRAM[T](N)

    setMem(realDRAM, realInput)
    setMem(imagDRAM, imagInput)

    Accel {
      val realSRAM = SRAM[T](N)
      val imagSRAM = SRAM[T](N)
      val outSRAM  = SRAM[T](N)

      realSRAM load realDRAM
      imagSRAM load imagDRAM

      Foreach(N by 1) { i =>
        outSRAM(i) = realSRAM(i)*realSRAM(i) + imagSRAM(i)*imagSRAM(i)
      }

      outDRAM store outSRAM
    }

    val result = getMem(outDRAM)

println("Result:")
for (i <- 0 until N) println(result(i))
println("Gold:")
for (i <- 0 until N) println(gold(i))



    val checks = Array.tabulate(N) { i =>
      val diff = result(i) - gold(i)
      val absDiff = if (diff < 0) -diff else diff
      absDiff <= 0.001f  // true if match
    }

    val passed = checks.reduce(_ && _)  // if all are true, passed = true

    println("PASS: " + (if (passed) 1 else 0))
    assert(passed)
  }
}
