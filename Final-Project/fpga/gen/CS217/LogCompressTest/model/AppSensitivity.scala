package model
import models.Sensitivity

object AppSensitivity extends App {
  override def main(args: Array[String]): Unit = {
    val center: Map[String,String] = Map()
    println(s"Center: $center") 
    println(s"Hashcode mapping:")
    Sensitivity.around("/Users/williambriger/Documents/Stanford/Junior Year /Spring Quarter/EE 109/ee109-1/Final-Project/fpga/./gen/CS217/LogCompressTest///LogCompressTest_data.csv", center)
  }
}
