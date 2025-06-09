import spatial.dsl._

@spatial class QuantizeKernelTestObject extends SpatialTest {
  def main(args: Array[String]): Unit = {
    // Compute gold using standard Scala types and math
    def computeGold: (Array[Float], Array[Float]) = {
      val testInput = Array(-1.0f, -0.5f, 0.0f, 0.5f, 1.0f)
      val gold = testInput.map { x =>
        val scaled = x * 32767f
        val clipped = Math.max(-32768f, Math.min(32767f, scaled))
        (clipped.toInt.toShort.toFloat) / 32768f
      }
      (testInput, gold)
    }
    val (testInput, gold) = computeGold

    val inDram = DRAM[Float](5)
    val outDram = DRAM[Float](5)
    setMem(inDram, testInput)
    Accel {
      val inSram = SRAM[Float](5)
      val outSram = SRAM[Float](5)
      inSram load inDram
      Foreach(5 by 1) { i =>
        val scaled  = inSram(i) * 32767.to[Float]
        val min_val = -32768.to[Float]
        val max_val =  32767.to[Float]
        val clipped = mux(scaled < min_val, min_val, mux(scaled > max_val, max_val, scaled))
        val int16_val = clipped.to[I16]
        outSram(i) = int16_val.to[Float] / 32768.to[Float]
      }
      outDram store outSram
    }
    val result = getMem(outDram)
    val pass = result.zip(gold).forall { case (a, b) => Math.abs(a - b) < 1e-4 }
    println(s"QuantizeKernelTestObject: ${if (pass) "PASS" else "FAIL"}")
    if (!pass) {
      println(s"Result: ${result.mkString(", ")}")
      println(s"Gold:   ${gold.mkString(", ")}")
      sys.exit(1)
    }
  }
} 