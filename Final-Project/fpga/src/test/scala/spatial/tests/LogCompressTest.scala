package spatial.tests
import spatial.dsl._
import spatial.dsl.math._

@spatial
class LogCompressTest extends SpatialTest {
  def main(args: Array[String]): Unit = {
    // 1) TYPES & PROBLEM SIZE
    type T = Float
    val N        = 5          // vector length
    val dynRange = 8.0f       // dynamic‐range clip

    // 2) HOST: build input and golden‐reference
    val input = Array[T](0f, 1f,  4f, 16f, 64f)
    val mx    = input.max
    val gold  = Array.tabulate[T](N){ i =>
      // clamp to (mx - dynRange), floor at 1e-10, then log10
      val cl = if (input(i) > mx - dynRange) input(i) else mx - dynRange
      math.log10(if (cl < 1e-10f) 1e-10f else cl).toFloat
    }

    // 3) DSL: allocate DRAM, load, run Accel
    val inD  = DRAM[T](N)
    val outD = DRAM[T](N)
    setMem(inD, input)

    Accel {
      val buf = SRAM[T](N)
      buf load inD

      // find max
      val m = Reduce(Reg[T])(N by 1){ i => buf(i) }{ (a,b) => max(a,b) }
      // clamp stage
      Foreach(N by 1) { i =>
        buf(i) = max(buf(i), m - dynRange)
      }
      // log stage
      Foreach(N by 1) { i =>
        outD(i) = log10(max(buf(i), 1e-10f))
      }
    }

    // 4) HOST: fetch, print, compare
    val result = getMem(outD)

    println("Result:")
    for (i <- 0 until N) println(result(i))
    println("Gold:")
    for (i <- 0 until N) println(gold(i))

    val checks = Array.tabulate(N){ i =>
      val d = result(i) - gold(i)
      val ad = if (d < 0) -d else d
      ad <= 1e-6f
    }
    val passed = checks.reduce(_ && _)

    println("PASS: " + (if (passed) 1 else 0))
    assert(passed, "LogCompressTest failed")
  }
}
