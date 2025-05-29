import emul._
import emul.implicits._

object x170 extends StreamIn[FloatPoint]("defined at WhisperScale.scala:29:14", {str => 
  val x = FloatPoint(str, FltFormat(23,8))
  x
})
