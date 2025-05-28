import spatial.dsl._

//=== MelFilterbank.scala ===
@spatial object MelFilterbank {
  def main(args: Array[String]): Unit = {
    val bands = args(0).to[I32]    // e.g., 80
    val bins  = args(1).to[I32]    // e.g., n_fft/2+1
    val matDram = DRAM[Float](bands, bins)
    val vecDram = DRAM[Float](bins)
    val outDram = DRAM[Float](bands)
    Accel {
      val matSram = SRAM[Float](bands, bins)
      val vecSram = SRAM[Float](bins)
      val outSram = SRAM[Float](bands)
      matSram load matDram
      vecSram load vecDram
      Foreach(bands by 1) { i =>
        var acc = 0.to[Float]
        Foreach(bins by 1) { j =>
          acc = acc + matSram(i, j) * vecSram(j)
        }
        outSram(i) = acc
      }
      outDram store outSram
    }
  }
}
