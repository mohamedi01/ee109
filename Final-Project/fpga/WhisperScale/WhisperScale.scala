import spatial.dsl._

//=== WhisperScale.scala ===
@spatial object WhisperScale {
  final val MAX_N_ELEMENTS = 400000 // Example value, can be tuned

  def main(args: Array[String]): Unit = {
    val n_runtime = args(0).to[I32]
    val inDram = DRAM[Float](n_runtime)
    val outDram = DRAM[Float](n_runtime)

    val Accel_n = ArgIn[I32]
    setArg(Accel_n, n_runtime)

    Accel {
      val buf = SRAM[Float](MAX_N_ELEMENTS)
      buf load inDram
      Foreach(Accel_n by 1) { i =>
        buf(i) = (buf(i) + 4.to[Float]) / 4.to[Float]
      }
      outDram store buf(0 :: Accel_n - 1)
    }
  }
}
