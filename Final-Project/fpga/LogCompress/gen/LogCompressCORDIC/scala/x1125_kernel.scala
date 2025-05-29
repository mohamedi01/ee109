import emul._
import emul.implicits._

object x1125 extends StreamOut[Struct3]("defined at LogCompress.scala:156:21", {elem => 
  val elem_field0 = elem._1
  val elem_fieldStr0 = elem_field0.toString
  val elem_field1 = elem._2
  val elem_fieldStr1 = elem_field1.toString
  val x = List(elem_fieldStr0, elem_fieldStr1).mkString("; ")
  x
})
