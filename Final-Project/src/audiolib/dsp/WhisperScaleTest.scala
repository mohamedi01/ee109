import spatial.dsl._

@spatial class WhisperScaleTest extends SpatialTest {
  def main(args: Array[String]): Unit = {
    type T = FixPt[TRUE,_16,_8]
    val N = 4
    val input = Array[T](0.to[T], -4.to[T], 4.to[T], -2.to[T])
    val expected = Array[T](1.to[T], 0.to[T], 2.to[T], 0.5.to[T])

    val inDRAM = DRAM[T](N)
    val outDRAM = DRAM[T](N)
    setMem(inDRAM, input)

    Accel {
      val buf = SRAM[T](N)
      buf load inDRAM
      Foreach(N by 1) { i =>
        buf(i) = (buf(i) + 4.to[T]) / 4.to[T]
      }
      outDRAM store buf
    }

    val result = getMem(outDRAM)

    val d0 = result(0) - expected(0)
    val d1 = result(1) - expected(1)
    val d2 = result(2) - expected(2)
    val d3 = result(3) - expected(3)

    val e0 = if (d0 < 0.to[T]) -d0 else d0
    val e1 = if (d1 < 0.to[T]) -d1 else d1
    val e2 = if (d2 < 0.to[T]) -d2 else d2
    val e3 = if (d3 < 0.to[T]) -d3 else d3

    val pass = if (e0 < 0.01.to[T] && e1 < 0.01.to[T] && e2 < 0.01.to[T] && e3 < 0.01.to[T]) 1 else 0

    println("PASS: " + pass)
    assert(pass == 1)
  }
}
