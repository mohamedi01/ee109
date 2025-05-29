import emul._
import emul.implicits._

object Main {
  def main(args: Array[String]): Unit = {
    OOB.open()
    val x341 = if (TRUE) System.out.print("DEBUG: MelFilterbank main method started.\n")
    val x342 = if (TRUE) System.out.print("DEBUG: Read bands = Const(80), bins = Const(201) from config file.\n")
    x343_matDram.initMem(FixedPoint(BigDecimal("80"),FixFormat(true,32,0))*FixedPoint(BigDecimal("201"),FixFormat(true,32,0)) + 16,FloatPoint("-0.0", FltFormat(23,8)))
    x344_vecDram.initMem(FixedPoint(BigDecimal("201"),FixFormat(true,32,0)) + 16,FloatPoint("-0.0", FltFormat(23,8)))
    x345_outDram.initMem(FixedPoint(BigDecimal("80"),FixFormat(true,32,0)) + 16,FloatPoint("-0.0", FltFormat(23,8)))
    val x346 = {
      val file = new java.io.File("../../../../fpga_io/mel_filterbank.csv")
      if (false) { // Will write to file
        val writer = new java.io.PrintWriter(file)
        writer.print("")
      }
      file
    }
    val x347 = {
      val scanner = new java.util.Scanner(x346)
      val tokens = new scala.collection.mutable.ArrayBuffer[String]() 
      scanner.useDelimiter("\\s*" + "," + "\\s*|\\s*\n\\s*")
      while (scanner.hasNext) {
        tokens += scanner.next.trim
      }
      scanner.close()
      tokens.toArray
    }
    val x351 = Array.tabulate(x347.length){bbb => 
      val b10 = FixedPoint.fromInt(bbb)
      val x349 = x347.apply(b10)
      val x350 = FloatPoint(x349, FltFormat(23,8))
      x350
    }
    val x352 = {
      val file = new java.io.File("../../../../fpga_io/power_bins.csv")
      if (false) { // Will write to file
        val writer = new java.io.PrintWriter(file)
        writer.print("")
      }
      file
    }
    val x353 = {
      val scanner = new java.util.Scanner(x352)
      val tokens = new scala.collection.mutable.ArrayBuffer[String]() 
      scanner.useDelimiter("\\s*" + "\n" + "\\s*|\\s*\n\\s*")
      while (scanner.hasNext) {
        tokens += scanner.next.trim
      }
      scanner.close()
      tokens.toArray
    }
    val x357 = Array.tabulate(x353.length){bbb => 
      val b21 = FixedPoint.fromInt(bbb)
      val x355 = x353.apply(b21)
      val x356 = FloatPoint(x355, FltFormat(23,8))
      x356
    }
    val x358 = {
      for (i <- 0 until x351.length) {
        try {
          try {
            x343_matDram(i) = x351(i)
          }
          catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
            System.out.println("[warn] MelFilterbank.scala:26:11 Memory x351: Out of bounds read at address " + err.getMessage)
            FloatPoint.invalid(FltFormat(23,8))
          }
        }
        catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
          System.out.println("[warn] MelFilterbank.scala:26:11 Memory matDram: Out of bounds write at address " + err.getMessage)
        }
      }
    }
    val x359 = {
      for (i <- 0 until x357.length) {
        try {
          try {
            x344_vecDram(i) = x357(i)
          }
          catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
            System.out.println("[warn] MelFilterbank.scala:27:11 Memory vecData: Out of bounds read at address " + err.getMessage)
            FloatPoint.invalid(FltFormat(23,8))
          }
        }
        catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
          System.out.println("[warn] MelFilterbank.scala:27:11 Memory vecDram: Out of bounds write at address " + err.getMessage)
        }
      }
    }
    val x527_outr_RootController = x527_outr_RootController_kernel.run()
    val x508 = if (TRUE) System.out.print("DEBUG: MelFilterbank Accel block finished.\n")
    val x509 = new Array[FloatPoint](FixedPoint(BigDecimal("80"),FixFormat(true,32,0)))
    val x510 = {
      for (i <- 0 until x509.length) {
        try {
          try {
            x509(i) = x345_outDram(i)
          }
          catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
            System.out.println("[warn] MelFilterbank.scala:49:24 Memory outDram: Out of bounds read at address " + err.getMessage)
            FloatPoint.invalid(FltFormat(23,8))
          }
        }
        catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
          System.out.println("[warn] MelFilterbank.scala:49:24 Memory result: Out of bounds write at address " + err.getMessage)
        }
      }
    }
    val x511 = {
      val file = new java.io.File("../../../../fpga_io/melfilterbank_output.csv")
      if (true) { // Will write to file
        val writer = new java.io.PrintWriter(file)
        writer.print("")
      }
      file
    }
    val x512 = FixedPoint.fromInt(x509.length)
    val x515 = {
      val writer = new java.io.PrintWriter(new java.io.FileOutputStream(x511, true /*append*/))
      (0 until x512.toInt).foreach{b56 => 
        writer.write("\n")
        val x513 = x509.apply(b56)
        val x514 = x513.toString
        writer.write(x514)
      }
      writer.close()
    }
    OOB.close()
  }
  def printHelp(): Unit = {
    System.out.print("Help for app: MelFilterbank\n")
    System.out.print("  -- Args:    <No input args>\n");
    System.exit(0);
  }
}
