import emul._
import emul.implicits._

object Main {
  def main(args: Array[String]): Unit = {
    OOB.open()
    x132_inDram.initMem(FixedPoint(BigDecimal("80"),FixFormat(true,32,0)) + 16,FloatPoint("-0.0", FltFormat(23,8)))
    x133_outDram.initMem(FixedPoint(BigDecimal("80"),FixFormat(true,32,0)) + 16,FloatPoint("-0.0", FltFormat(23,8)))
    val x134 = {
      val file = new java.io.File("../../../../fpga_io/logcompress_input.csv")
      if (false) { // Will write to file
        val writer = new java.io.PrintWriter(file)
        writer.print("")
      }
      file
    }
    val x135 = {
      val scanner = new java.util.Scanner(x134)
      val tokens = new scala.collection.mutable.ArrayBuffer[String]() 
      scanner.useDelimiter("\\s*" + "\n" + "\\s*|\\s*\n\\s*")
      while (scanner.hasNext) {
        tokens += scanner.next.trim
      }
      scanner.close()
      tokens.toArray
    }
    val x139 = Array.tabulate(x135.length){bbb => 
      val b6 = FixedPoint.fromInt(bbb)
      val x137 = x135.apply(b6)
      val x138 = FloatPoint(x137, FltFormat(23,8))
      x138
    }
    val x140 = {
      for (i <- 0 until x139.length) {
        try {
          try {
            x132_inDram(i) = x139(i)
          }
          catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
            System.out.println("[warn] WhisperScale.scala:21:11 Memory inputData: Out of bounds read at address " + err.getMessage)
            FloatPoint.invalid(FltFormat(23,8))
          }
        }
        catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
          System.out.println("[warn] WhisperScale.scala:21:11 Memory inDram: Out of bounds write at address " + err.getMessage)
        }
      }
    }
    val x117_outr_RootController = x117_outr_RootController_kernel.run()
    val x192 = new Array[FloatPoint](FixedPoint(BigDecimal("80"),FixFormat(true,32,0)))
    val x193 = {
      for (i <- 0 until x192.length) {
        try {
          try {
            x192(i) = x133_outDram(i)
          }
          catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
            System.out.println("[warn] WhisperScale.scala:36:24 Memory outDram: Out of bounds read at address " + err.getMessage)
            FloatPoint.invalid(FltFormat(23,8))
          }
        }
        catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
          System.out.println("[warn] WhisperScale.scala:36:24 Memory result: Out of bounds write at address " + err.getMessage)
        }
      }
    }
    val x194 = {
      val file = new java.io.File("../../../../fpga_io/whisperscale_output.csv")
      if (true) { // Will write to file
        val writer = new java.io.PrintWriter(file)
        writer.print("")
      }
      file
    }
    val x195 = FixedPoint.fromInt(x192.length)
    val x198 = {
      val writer = new java.io.PrintWriter(new java.io.FileOutputStream(x194, true /*append*/))
      (0 until x195.toInt).foreach{b28 => 
        writer.write("\n")
        val x196 = x192.apply(b28)
        val x197 = x196.toString
        writer.write(x197)
      }
      writer.close()
    }
    OOB.close()
  }
  def printHelp(): Unit = {
    System.out.print("Help for app: WhisperScale\n")
    System.out.print("  -- Args:    <No input args>\n");
    System.exit(0);
  }
}
