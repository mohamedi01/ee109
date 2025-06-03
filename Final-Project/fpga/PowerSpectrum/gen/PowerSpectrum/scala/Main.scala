import emul._
import emul.implicits._

object Main {
  def main(args: Array[String]): Unit = {
    OOB.open()
    val x296 = if (TRUE) System.out.print("DEBUG: PowerSpectrum main method started.\n")
    val x297 = if (TRUE) System.out.print("DEBUG: Read n_bins = 201 from config file.\n")
    x298_realDram.initMem(FixedPoint(BigDecimal("201"),FixFormat(true,32,0)) + 16,FloatPoint("-0.0", FltFormat(23,8)))
    x299_imagDram.initMem(FixedPoint(BigDecimal("201"),FixFormat(true,32,0)) + 16,FloatPoint("-0.0", FltFormat(23,8)))
    x300_outDram.initMem(FixedPoint(BigDecimal("201"),FixFormat(true,32,0)) + 16,FloatPoint("-0.0", FltFormat(23,8)))
    val x301 = {
      val file = new java.io.File("../../../../fpga_io/real.csv")
      if (false) { // Will write to file
        val writer = new java.io.PrintWriter(file)
        writer.print("")
      }
      file
    }
    val x302 = {
      val scanner = new java.util.Scanner(x301)
      val tokens = new scala.collection.mutable.ArrayBuffer[String]() 
      scanner.useDelimiter("\\s*" + "\n" + "\\s*|\\s*\n\\s*")
      while (scanner.hasNext) {
        tokens += scanner.next.trim
      }
      scanner.close()
      tokens.toArray
    }
    val x306 = Array.tabulate(x302.length){bbb => 
      val b9 = FixedPoint.fromInt(bbb)
      val x304 = x302.apply(b9)
      val x305 = FloatPoint(x304, FltFormat(23,8))
      x305
    }
    val x307 = {
      val file = new java.io.File("../../../../fpga_io/imag.csv")
      if (false) { // Will write to file
        val writer = new java.io.PrintWriter(file)
        writer.print("")
      }
      file
    }
    val x308 = {
      val scanner = new java.util.Scanner(x307)
      val tokens = new scala.collection.mutable.ArrayBuffer[String]() 
      scanner.useDelimiter("\\s*" + "\n" + "\\s*|\\s*\n\\s*")
      while (scanner.hasNext) {
        tokens += scanner.next.trim
      }
      scanner.close()
      tokens.toArray
    }
    val x312 = Array.tabulate(x308.length){bbb => 
      val b16 = FixedPoint.fromInt(bbb)
      val x310 = x308.apply(b16)
      val x311 = FloatPoint(x310, FltFormat(23,8))
      x311
    }
    val x313 = {
      for (i <- 0 until x306.length) {
        try {
          try {
            x298_realDram(i) = x306(i)
          }
          catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
            System.out.println("[warn] PowerSpectrum.scala:26:11 Memory realData: Out of bounds read at address " + err.getMessage)
            FloatPoint.invalid(FltFormat(23,8))
          }
        }
        catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
          System.out.println("[warn] PowerSpectrum.scala:26:11 Memory realDram: Out of bounds write at address " + err.getMessage)
        }
      }
    }
    val x314 = {
      for (i <- 0 until x312.length) {
        try {
          try {
            x299_imagDram(i) = x312(i)
          }
          catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
            System.out.println("[warn] PowerSpectrum.scala:27:11 Memory imagData: Out of bounds read at address " + err.getMessage)
            FloatPoint.invalid(FltFormat(23,8))
          }
        }
        catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
          System.out.println("[warn] PowerSpectrum.scala:27:11 Memory imagDram: Out of bounds write at address " + err.getMessage)
        }
      }
    }
    val x450_outr_RootController = x450_outr_RootController_kernel.run()
    val x436 = if (TRUE) System.out.print("DEBUG: PowerSpectrum Accel block finished.\n")
    val x437 = new Array[FloatPoint](FixedPoint(BigDecimal("201"),FixFormat(true,32,0)))
    val x438 = {
      for (i <- 0 until x437.length) {
        try {
          try {
            x437(i) = x300_outDram(i)
          }
          catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
            System.out.println("[warn] PowerSpectrum.scala:46:24 Memory outDram: Out of bounds read at address " + err.getMessage)
            FloatPoint.invalid(FltFormat(23,8))
          }
        }
        catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
          System.out.println("[warn] PowerSpectrum.scala:46:24 Memory result: Out of bounds write at address " + err.getMessage)
        }
      }
    }
    val x439 = {
      val file = new java.io.File("../../../../fpga_io/power_spectrum_output.csv")
      if (true) { // Will write to file
        val writer = new java.io.PrintWriter(file)
        writer.print("")
      }
      file
    }
    val x440 = FixedPoint.fromInt(x437.length)
    val x443 = {
      val writer = new java.io.PrintWriter(new java.io.FileOutputStream(x439, true /*append*/))
      (0 until x440.toInt).foreach{b45 => 
        writer.write("\n")
        val x441 = x437.apply(b45)
        val x442 = x441.toString
        writer.write(x442)
      }
      writer.close()
    }
    OOB.close()
  }
  def printHelp(): Unit = {
    System.out.print("Help for app: PowerSpectrum\n")
    System.out.print("  -- Args:    <No input args>\n");
    System.exit(0);
  }
}
