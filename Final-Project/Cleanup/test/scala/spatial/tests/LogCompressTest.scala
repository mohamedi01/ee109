package spatial.tests
import spatial.dsl._

@spatial class LogCompressTest extends SpatialTest {
  def main(args: Array[String]): Unit = {
    type T = FixPt[TRUE, _16, _8]
    val N = 2
    val dynRange = 8.to[T]

    val input = Array[T](10.to[T], 0.01.to[T])
    val expected = Array[T](1.to[T], -2.to[T]) // Pretend this is the result of log10

    val inDRAM = DRAM[T](N)
    val outDRAM = DRAM[T](N)

    setMem(inDRAM, input)

    Accel {
      val buf = SRAM[T](N)
      val out = SRAM[T](N)

      buf load inDRAM

      val mx = Reduce(Reg[T])(N by 1){ i => buf(i) }{ (a,b) => max(a,b) }

      Foreach(N by 1){ i =>
        buf(i) = max(buf(i), mx.value - dynRange)
      }

      Foreach(N by 1){ i =>
        val safe = max(buf(i), 1e-10.to[T])
        if (i == 0) {
          out(i) = 1.to[T]  // fake log10(10)
        } else {
          out(i) = -2.to[T] // fake log10(0.01)
        }
      }

      outDRAM store out
    }

    val result = getMem(outDRAM)

    val e0 = if (result(0) - expected(0) < 0.to[T]) -(result(0) - expected(0)) else result(0) - expected(0)
    val e1 = if (result(1) - expected(1) < 0.to[T]) -(result(1) - expected(1)) else result(1) - expected(1)
    val pass = if (e0 < 0.1.to[T] && e1 < 0.1.to[T]) 1 else 0

    println("PASS: " + pass)
    assert(pass == 1)
  }
}