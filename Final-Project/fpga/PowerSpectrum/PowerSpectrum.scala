import spatial.dsl._

//=== PowerSpectrum.scala ===
@spatial object PowerSpectrum {
  def main(args: Array[String]): Unit = {
    val n = args(0).to[I32]
    val realDram = DRAM[Float](n)
    val imagDram = DRAM[Float](n)
    val outDram  = DRAM[Float](n)
    Accel {
      val realSram = SRAM[Float](n)
      val imagSram = SRAM[Float](n)
      val outSram  = SRAM[Float](n)
      realSram load realDram
      imagSram load imagDram
      Foreach(n by 1) { i =>
        outSram(i) = realSram(i)*realSram(i) + imagSram(i)*imagSram(i)
      }
      outDram store outSram
    }
  }
}
