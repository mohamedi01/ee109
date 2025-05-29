import emul._
import emul.implicits._

object x314 extends StreamOut[Struct1]("defined at PowerSpectrum.scala:31:16", {elem => 
  val elem_field0 = elem.offset
  val elem_fieldStr0 = elem_field0.toString
  val elem_field1 = elem.size
  val elem_fieldStr1 = elem_field1.toString
  val elem_field2 = elem.isLoad
  val elem_fieldStr2 = elem_field2.toString
  val x = List(elem_fieldStr0, elem_fieldStr1, elem_fieldStr2).mkString("; ")
  x
})
