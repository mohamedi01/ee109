package model
import models.Sensitivity

object AppSensitivity extends App {
  override def main(args: Array[String]): Unit = {
    val center: Map[String,String] = Map()
    println(s"Center: $center") 
    println(s"Hashcode mapping:")
    Sensitivity.around("/Users/williambriger/Documents/Stanford - General/Junior Year /Winter Quarter/CS 124/Hw #7/ee109/lab1/./gen/CS217/Lab1Part1RegExample///Lab1Part1RegExample_data.csv", center)
  }
}
