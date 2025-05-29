import emul._
import emul.implicits._

object x213 extends StreamOut[Struct2]("defined at WhisperScale.scala:49:15", {elem => 
  val elem_field0 = elem._1
  val elem_fieldStr0 = elem_field0.toString
  val elem_field1 = elem._2
  val elem_fieldStr1 = elem_field1.toString
  val x = List(elem_fieldStr0, elem_fieldStr1).mkString("; ")
  x
})
