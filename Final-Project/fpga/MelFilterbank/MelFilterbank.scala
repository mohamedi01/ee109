import spatial.dsl._
import scala.io.Source

//=== MelFilterbank.scala ===
@spatial object MelFilterbank {
  def main(args: Array[String]): Unit = {
    println("DEBUG: MelFilterbank main method started.")
    val configSource = Source.fromFile("melfilterbank_config.txt")
    val lines = configSource.getLines().toList
    configSource.close()

    val bands_str = lines(0)
    val bins_str = lines(1)

    val bands = bands_str.trim.toInt.to[I32]
    val bins  = bins_str.trim.toInt.to[I32]
    println(s"DEBUG: Read bands = $bands, bins = $bins from config file.")

    val matDram = DRAM[Float](bands, bins)
    val vecDram = DRAM[Float](bins)
    val outDram = DRAM[Float](bands)
    Accel {
      val matSram = SRAM[Float](bands, bins)
      val vecSram = SRAM[Float](bins)
      val outSram = SRAM[Float](bands)

      matSram load matDram
      vecSram load vecDram

      Foreach(bands by 1) { i =>
        var acc = 0.to[Float]
        Foreach(bins by 1) { j =>
          acc = acc + matSram(i,j) * vecSram(j)
        }
        outSram(i) = acc
      }
      outDram store outSram
    }
    println("DEBUG: MelFilterbank Accel block finished.")
  }
}
