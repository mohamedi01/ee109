import emul._
import emul.implicits._

object Main {
  def main(args: Array[String]): Unit = {
    OOB.open()
    val x292 = if (TRUE) System.out.print("DEBUG: PowerSpectrum main method started.\n")
    val x293 = if (TRUE) System.out.print("DEBUG: Read n = Const(384312) from config file.\n")
    x294_realDram.initMem(FixedPoint(BigDecimal("384312"),FixFormat(true,32,0)) + 16,FloatPoint("-0.0", FltFormat(23,8)))
    x295_imagDram.initMem(FixedPoint(BigDecimal("384312"),FixFormat(true,32,0)) + 16,FloatPoint("-0.0", FltFormat(23,8)))
    x296_outDram.initMem(FixedPoint(BigDecimal("384312"),FixFormat(true,32,0)) + 16,FloatPoint("-0.0", FltFormat(23,8)))
    val x297 = {
      val file = new java.io.File("../../../../fpga_io/real.csv")
      if (false) { // Will write to file
        val writer = new java.io.PrintWriter(file)
        writer.print("")
      }
      file
    }
    val x298 = {
      val scanner = new java.util.Scanner(x297)
      val tokens = new scala.collection.mutable.ArrayBuffer[String]() 
      scanner.useDelimiter("\\s*" + "\n" + "\\s*|\\s*\n\\s*")
      while (scanner.hasNext) {
        tokens += scanner.next.trim
      }
      scanner.close()
      tokens.toArray
    }
    val x302 = Array.tabulate(x298.length){bbb => 
      val b9 = FixedPoint.fromInt(bbb)
      val x300 = x298.apply(b9)
      val x301 = FloatPoint(x300, FltFormat(23,8))
      x301
    }
    val x303 = {
      val file = new java.io.File("../../../../fpga_io/imag.csv")
      if (false) { // Will write to file
        val writer = new java.io.PrintWriter(file)
        writer.print("")
      }
      file
    }
    val x304 = {
      val scanner = new java.util.Scanner(x303)
      val tokens = new scala.collection.mutable.ArrayBuffer[String]() 
      scanner.useDelimiter("\\s*" + "\n" + "\\s*|\\s*\n\\s*")
      while (scanner.hasNext) {
        tokens += scanner.next.trim
      }
      scanner.close()
      tokens.toArray
    }
    val x308 = Array.tabulate(x304.length){bbb => 
      val b16 = FixedPoint.fromInt(bbb)
      val x306 = x304.apply(b16)
      val x307 = FloatPoint(x306, FltFormat(23,8))
      x307
    }
    val x309 = {
      for (i <- 0 until x302.length) {
        try {
          try {
            x294_realDram(i) = x302(i)
          }
          catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
            System.out.println("[warn] PowerSpectrum.scala:24:11 Memory realData: Out of bounds read at address " + err.getMessage)
            FloatPoint.invalid(FltFormat(23,8))
          }
        }
        catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
          System.out.println("[warn] PowerSpectrum.scala:24:11 Memory realDram: Out of bounds write at address " + err.getMessage)
        }
      }
    }
    val x310 = {
      for (i <- 0 until x308.length) {
        try {
          try {
            x295_imagDram(i) = x308(i)
          }
          catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
            System.out.println("[warn] PowerSpectrum.scala:25:11 Memory imagData: Out of bounds read at address " + err.getMessage)
            FloatPoint.invalid(FltFormat(23,8))
          }
        }
        catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
          System.out.println("[warn] PowerSpectrum.scala:25:11 Memory imagDram: Out of bounds write at address " + err.getMessage)
        }
      }
    }
    val x444_outr_RootController = x444_outr_RootController_kernel.run()
    val x430 = if (TRUE) System.out.print("DEBUG: PowerSpectrum Accel block finished.\n")
    val x431 = new Array[FloatPoint](FixedPoint(BigDecimal("384312"),FixFormat(true,32,0)))
    val x432 = {
      for (i <- 0 until x431.length) {
        try {
          try {
            x431(i) = x296_outDram(i)
          }
          catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
            System.out.println("[warn] PowerSpectrum.scala:41:24 Memory outDram: Out of bounds read at address " + err.getMessage)
            FloatPoint.invalid(FltFormat(23,8))
          }
        }
        catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
          System.out.println("[warn] PowerSpectrum.scala:41:24 Memory result: Out of bounds write at address " + err.getMessage)
        }
      }
    }
    val x433 = {
      val file = new java.io.File("../../../../fpga_io/power_spectrum_output.csv")
      if (true) { // Will write to file
        val writer = new java.io.PrintWriter(file)
        writer.print("")
      }
      file
    }
    val x434 = FixedPoint.fromInt(x431.length)
    val x437 = {
      val writer = new java.io.PrintWriter(new java.io.FileOutputStream(x433, true /*append*/))
      (0 until x434.toInt).foreach{b44 => 
        writer.write("\n")
        val x435 = x431.apply(b44)
        val x436 = x435.toString
        writer.write(x436)
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
