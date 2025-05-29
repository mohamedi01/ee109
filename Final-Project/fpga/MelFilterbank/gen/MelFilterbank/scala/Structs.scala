import emul._
import emul.implicits._

case class Struct2(
  var size: FixedPoint,
  var start: FixedPoint,
  var end: FixedPoint,
) {

  override def productPrefix = "IssuedCmd"
}

case class Struct1(
  var offset: FixedPoint,
  var size: FixedPoint,
  var isLoad: Bool,
) {

  override def productPrefix = "BurstCmd"
}

case class Struct3(
  var _1: FloatPoint,
  var _2: Bool,
) {

  override def productPrefix = "Tup2[Flt[_24,_8],Bit]"
}

