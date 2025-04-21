package model
import models.Sensitivity

object AppSensitivity extends App {
  override def main(args: Array[String]): Unit = {
    val center: Map[String,String] = Map()
    println(s"Center: $center") 
    println(s"Hashcode mapping:")
    Sensitivity.around("/Users/mohamedismail/Desktop/EE109/ee109/lab2/./gen/CS217/Lab2Part3BasicCondFSM///Lab2Part3BasicCondFSM_data.csv", center)
  }
}
