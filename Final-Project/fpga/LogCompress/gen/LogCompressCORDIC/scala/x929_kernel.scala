import emul._
import emul.implicits._

object x929 extends StreamIn[FloatPoint]("defined at LogCompress.scala:63:16", {str => 
  val x = FloatPoint(str, FltFormat(23,8))
  x
})
