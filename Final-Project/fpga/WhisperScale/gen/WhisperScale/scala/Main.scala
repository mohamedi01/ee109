import emul._
import emul.implicits._

object Main {
  def main(args: Array[String]): Unit = {
    OOB.open()
    x152_inDram.initMem(FixedPoint(BigDecimal("80"),FixFormat(true,32,0)) + 16,FloatPoint("-0.0", FltFormat(23,8)))
    x153_outDram.initMem(FixedPoint(BigDecimal("80"),FixFormat(true,32,0)) + 16,FloatPoint("-0.0", FltFormat(23,8)))
    val x154 = {
      val file = new java.io.File("../../../../fpga_io/logcompress_output.csv")
      if (false) { // Will write to file
        val writer = new java.io.PrintWriter(file)
        writer.print("")
      }
      file
    }
    val x155 = {
      val scanner = new java.util.Scanner(x154)
      val tokens = new scala.collection.mutable.ArrayBuffer[String]() 
      scanner.useDelimiter("\\s*" + "\n" + "\\s*|\\s*\n\\s*")
      while (scanner.hasNext) {
        tokens += scanner.next.trim
      }
      scanner.close()
      tokens.toArray
    }
    val x159 = Array.tabulate(x155.length){bbb => 
      val b6 = FixedPoint.fromInt(bbb)
      val x157 = x155.apply(b6)
      val x158 = FloatPoint(x157, FltFormat(23,8))
      x158
    }
    val x160 = {
      for (i <- 0 until x159.length) {
        try {
          try {
            x152_inDram(i) = x159(i)
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
    val x137_outr_RootController = x137_outr_RootController_kernel.run()
    val x225 = new Array[FloatPoint](FixedPoint(BigDecimal("80"),FixFormat(true,32,0)))
    val x226 = {
      for (i <- 0 until x225.length) {
        try {
          try {
            x225(i) = x153_outDram(i)
          }
          catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
            System.out.println("[warn] WhisperScale.scala:53:24 Memory outDram: Out of bounds read at address " + err.getMessage)
            FloatPoint.invalid(FltFormat(23,8))
          }
        }
        catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
          System.out.println("[warn] WhisperScale.scala:53:24 Memory result: Out of bounds write at address " + err.getMessage)
        }
      }
    }
    val x227 = {
      val file = new java.io.File("../../../../fpga_io/whisperscale_output.csv")
      if (true) { // Will write to file
        val writer = new java.io.PrintWriter(file)
        writer.print("")
      }
      file
    }
    val x228 = FixedPoint.fromInt(x225.length)
    val x231 = {
      val writer = new java.io.PrintWriter(new java.io.FileOutputStream(x227, true /*append*/))
      (0 until x228.toInt).foreach{b40 => 
        writer.write("\n")
        val x229 = x225.apply(b40)
        val x230 = x229.toString
        writer.write(x230)
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
