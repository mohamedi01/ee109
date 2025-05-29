import emul._
import emul.implicits._

object Main {
  def main(args: Array[String]): Unit = {
    OOB.open()
    x158_inDram.initMem(FixedPoint(BigDecimal("80"),FixFormat(true,32,0)) + 16,FloatPoint("-0.0", FltFormat(23,8)))
    x159_outDram.initMem(FixedPoint(BigDecimal("80"),FixFormat(true,32,0)) + 16,FloatPoint("-0.0", FltFormat(23,8)))
    val x160 = {
      val file = new java.io.File("../../../../fpga_io/logcompress_output.csv")
      if (false) { // Will write to file
        val writer = new java.io.PrintWriter(file)
        writer.print("")
      }
      file
    }
    val x161 = {
      val scanner = new java.util.Scanner(x160)
      val tokens = new scala.collection.mutable.ArrayBuffer[String]() 
      scanner.useDelimiter("\\s*" + "\n" + "\\s*|\\s*\n\\s*")
      while (scanner.hasNext) {
        tokens += scanner.next.trim
      }
      scanner.close()
      tokens.toArray
    }
    val x165 = Array.tabulate(x161.length){bbb => 
      val b6 = FixedPoint.fromInt(bbb)
      val x163 = x161.apply(b6)
      val x164 = FloatPoint(x163, FltFormat(23,8))
      x164
    }
    val x166 = {
      for (i <- 0 until x165.length) {
        try {
          try {
            x158_inDram(i) = x165(i)
          }
          catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
            System.out.println("[warn] WhisperScale.scala:24:11 Memory inputData: Out of bounds read at address " + err.getMessage)
            FloatPoint.invalid(FltFormat(23,8))
          }
        }
        catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
          System.out.println("[warn] WhisperScale.scala:24:11 Memory inDram: Out of bounds write at address " + err.getMessage)
        }
      }
    }
    val x143_outr_RootController = x143_outr_RootController_kernel.run()
    val x234 = new Array[FloatPoint](FixedPoint(BigDecimal("80"),FixFormat(true,32,0)))
    val x235 = {
      for (i <- 0 until x234.length) {
        try {
          try {
            x234(i) = x159_outDram(i)
          }
          catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
            System.out.println("[warn] WhisperScale.scala:52:24 Memory outDram: Out of bounds read at address " + err.getMessage)
            FloatPoint.invalid(FltFormat(23,8))
          }
        }
        catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
          System.out.println("[warn] WhisperScale.scala:52:24 Memory result: Out of bounds write at address " + err.getMessage)
        }
      }
    }
    val x236 = {
      val file = new java.io.File("../../../../fpga_io/whisperscale_output.csv")
      if (true) { // Will write to file
        val writer = new java.io.PrintWriter(file)
        writer.print("")
      }
      file
    }
    val x237 = FixedPoint.fromInt(x234.length)
    val x240 = {
      val writer = new java.io.PrintWriter(new java.io.FileOutputStream(x236, true /*append*/))
      (0 until x237.toInt).foreach{b42 => 
        writer.write("\n")
        val x238 = x234.apply(b42)
        val x239 = x238.toString
        writer.write(x239)
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
