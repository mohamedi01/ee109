import spatial.dsl._
import scala.io.Source // Needed for reading file

//=== PowerSpectrum.scala ===
@spatial object PowerSpectrum {
  def main(args: Array[String]): Unit = {
    println("DEBUG: PowerSpectrum main method started.")
    
    val configSource = Source.fromFile("power_spectrum_config.txt")
    val n_bins_str = configSource.getLines.next()
    configSource.close()
    val n_bins_scala_int = n_bins_str.trim.toInt 
    val n_bins_spatial_i32 = n_bins_scala_int.to[I32]     
    println(s"DEBUG: Read n_bins = $n_bins_scala_int from config file.")

    // Derive NFFT from n_bins for scaling
    val NFFT_derived_scala_int = (n_bins_scala_int - 1) * 2
    val NFFT_derived_T = NFFT_derived_scala_int.to[Float]

    val realDram = DRAM[Float](n_bins_spatial_i32) 
    val imagDram = DRAM[Float](n_bins_spatial_i32)
    val outDram  = DRAM[Float](n_bins_spatial_i32)
    
    val realData = loadCSV1D[Float]("../../../../fpga_io/real.csv", "\n")
    val imagData = loadCSV1D[Float]("../../../../fpga_io/imag.csv", "\n") 
    setMem(realDram, realData)
    setMem(imagDram, imagData)
    
    Accel {
      val realSram = SRAM[Float](n_bins_spatial_i32)
      val imagSram = SRAM[Float](n_bins_spatial_i32)
      val outSram  = SRAM[Float](n_bins_spatial_i32)
      realSram load realDram
      imagSram load imagDram
      println(s"DEBUG SCALA ACCEL: n_bins = $n_bins_spatial_i32")
      
      Foreach(n_bins_spatial_i32 by 1) { i =>
        val r_sq = realSram(i)*realSram(i)
        val i_sq = imagSram(i)*imagSram(i)
        outSram(i) = r_sq + i_sq
      }
      outDram store outSram
    }
    println("DEBUG: PowerSpectrum Accel block finished.")
    
    val result = getMem(outDram)
    writeCSV1D(result, "../../../../fpga_io/power_spectrum_output.csv", delim="\n")
  }
}
