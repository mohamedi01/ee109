import emul._
import emul.implicits._

object x365 extends StreamIn[FloatPoint]("defined at MelFilterbank.scala:34:15", {str => 
  val x = FloatPoint(str, FltFormat(23,8))
  x
})
