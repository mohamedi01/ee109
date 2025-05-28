import spatial.dsl._

//=== WhisperScale.scala ===
@spatial object WhisperScale {
  def main(args: Array[String]): Unit = {
    val n = args(0).to[I32]
    val inDram = DRAM[Float](n)
    val outDram = DRAM[Float](n)
    Accel {
      val buf = SRAM[Float](n)
      buf load inDram
      Foreach(n by 1) { i =>
        buf(i) = (buf(i) + 4.to[Float]) / 4.to[Float]
      }
      outDram store buf
    }
  }
}
