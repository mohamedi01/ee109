import emul._
import emul.implicits._

case class Struct1(
  var offset: FixedPoint,
  var size: FixedPoint,
  var isLoad: Bool,
) {

  override def productPrefix = "BurstCmd"
}

case class Struct2(
  var _1: FloatPoint,
  var _2: Bool,
) {

  override def productPrefix = "Tup2[Flt[_24,_8],Bit]"
}

