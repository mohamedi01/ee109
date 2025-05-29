import emul._
import emul.implicits._

/** BEGIN UnrolledForeach x423_inr_Foreach **/
object x423_inr_Foreach_kernel {
  def run(
    x408_ctrchain: Array[Counterlike],
    b392: FixedPoint,
    b393: Bool
  ): Unit = if (b393){
    x408_ctrchain(0).foreach{case (is,vs) => 
      val b409 = is(0)
      val b410 = vs(0)
      val x411_rd_x394 = x394_reg.value
      val x412 = x411_rd_x394 <= b409
      val x413_rd_x395 = x395_reg.value
      val x414 = b409 < x413_rd_x395
      val x415 = x412 && x414
      val x416_sub = b409 - x411_rd_x394
      val x417 = {
        val a0 = if (b410 & b393 && x365.nonEmpty) x365.dequeue() else FloatPoint.invalid(FltFormat(23,8))
        Array[FloatPoint](a0)
      }
      val x418_elem_0 = x417.apply(0)
      val x521 = (b392 * FixedPoint(BigDecimal("201"),FixFormat(true,32,0))) + x416_sub
      val x422_wr = x360_matSram_0.update("MelFilterbank.scala:34:15", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(x521), Seq(x415 & b410 & b393), Seq(x418_elem_0))
    }
  } else null.asInstanceOf[Unit]
}
/** END UnrolledForeach x423_inr_Foreach **/
