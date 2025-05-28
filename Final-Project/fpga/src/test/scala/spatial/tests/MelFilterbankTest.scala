package spatial.tests
import spatial.dsl._

@spatial class MelFilterbankTest extends SpatialTest {
  def main(args: Array[String]): Unit = {
    type T = Float
    val BANDS = 2
    val BINS = 3

    // Use Matrix.tabulate and Array.tabulate to infer types
    val mat = Matrix.tabulate[T](BANDS, BINS){ (i,j) =>
      if (i == 0) (j+1).to[T]          // [1.0, 2.0, 3.0]
      else       (j+4).to[T]           // [4.0, 5.0, 6.0]
    }

    val vec = Array.tabulate[T](BINS){ i => 1.to[T] } // [1.0, 1.0, 1.0]
    val gold = Array.tabulate[T](BANDS){ i =>
      if (i == 0) 6.to[T]  // 1+2+3
      else       15.to[T] // 4+5+6
    }

    val matDRAM = DRAM[T](BANDS, BINS)
    val vecDRAM = DRAM[T](BINS)
    val outDRAM = DRAM[T](BANDS)

    setMem(matDRAM, mat)
    setMem(vecDRAM, vec)

    Accel {
      val matSRAM = SRAM[T](BANDS, BINS)
      val vecSRAM = SRAM[T](BINS)
      val outSRAM = SRAM[T](BANDS)

      matSRAM load matDRAM
      vecSRAM load vecDRAM

      Foreach(BANDS by 1) { i =>
        val sum = Reduce(Reg[T])(BINS by 1){ j =>
          matSRAM(i, j) * vecSRAM(j)
        }{_ + _}
        outSRAM(i) = sum.value
      }

      outDRAM store outSRAM
    }

    val result = getMem(outDRAM)

    // Manual correctness check
    val d0 = result(0) - gold(0); val e0 = if (d0 < 0) -d0 else d0
    val d1 = result(1) - gold(1); val e1 = if (d1 < 0) -d1 else d1
    val pass = if (e0 < 0.001f && e1 < 0.001f) 1 else 0

    println("PASS: " + pass)
    assert(pass == 1)
  }
}
