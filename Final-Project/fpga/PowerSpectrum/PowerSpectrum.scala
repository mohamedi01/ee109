import spatial.dsl._
import scala.io.Source // Needed for reading file

//=== PowerSpectrum.scala ===
@spatial object PowerSpectrum {
  def main(args: Array[String]): Unit = {
    println("DEBUG: PowerSpectrum main method started.")
    
    // Read size n from config file
    val configSource = Source.fromFile("power_spectrum_config.txt")
    val n_str = configSource.getLines.next()
    configSource.close()
    val n_scala_int = n_str.trim.toInt // Convert Scala String to Scala Int
    val n = n_scala_int.to[I32]     // Convert Scala Int to Spatial I32
    println(s"DEBUG: Read n = $n from config file.")

    val realDram = DRAM[Float](n)
    val imagDram = DRAM[Float](n)
    val outDram  = DRAM[Float](n)
    Accel {
      val realSram = SRAM[Float](n)
      val imagSram = SRAM[Float](n)
      val outSram  = SRAM[Float](n)
      realSram load realDram
      imagSram load imagDram
      Foreach(n by 1) { i =>
        outSram(i) = realSram(i)*realSram(i) + imagSram(i)*imagSram(i)
      }
      outDram store outSram
    }
    println("DEBUG: PowerSpectrum Accel block finished.")
  }
}
