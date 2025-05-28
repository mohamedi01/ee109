import spatial.dsl._

//=== QuantizeKernel.scala ===
@spatial object QuantizeKernel {
  def main(args: Array[String]): Unit = {
    val n = args(0).to[I32]
    val inDram = DRAM[Float](n)
    val outDram = DRAM[Float](n)
    Accel {
      val inSram  = SRAM[Float](n)
      val outSram = SRAM[Float](n)
      inSram load inDram
      Foreach(n by 1) { i =>
        val scaled  = inSram(i) * 32767.to[Float]
        val clipped = clamp(scaled, -32768.to[Float], 32767.to[Float])
        outSram(i) = clipped.to[Int].to[Float] / 32768.to[Float]
      }
      outDram store outSram
    }
  }
}
