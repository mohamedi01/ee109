import spatial.dsl._

//=== LogCompress.scala ===
@spatial object LogCompress {
  def main(args: Array[String]): Unit = {
    val n = args(0).to[I32]
    val dynRange = args(1).to[Float]  // e.g., 8.0f
    val inDram = DRAM[Float](n)
    val outDram = DRAM[Float](n)
    Accel {
      val buf = SRAM[Float](n)
      buf load inDram
      // Compute max
      val mx = Reduce(Max)(buf)(i => buf(i))
      Foreach(n by 1) { i =>
        buf(i) = max(buf(i), mx - dynRange)
      }
      Foreach(n by 1) { i =>
        outDram(i) = log10(max(buf(i), 1e-10.to[Float]))
      }
    }
  }
}
