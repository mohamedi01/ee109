import emul._
import emul.implicits._

/** BEGIN UnrolledForeach x458_inr_Foreach **/
object x458_inr_Foreach_kernel {
  def run(
    x448_ctrchain: Array[Counterlike]
  ): Unit = {
    x448_ctrchain(0).foreach{case (is,vs) => 
      val b449 = is(0)
      val b450 = vs(0)
      val x451 = FixedPoint(BigDecimal("0"),FixFormat(true,32,0)) <= b449
      val x452_rd_x437 = x437_reg.value
      val x453 = b449 < x452_rd_x437
      val x454 = x451 && x453
      val x455 = {
        val a0 = if (b450 && x428.nonEmpty) x428.dequeue() else FloatPoint.invalid(FltFormat(23,8))
        Array[FloatPoint](a0)
      }
      val x456_elem_0 = x455.apply(0)
      val x457_wr = x361_vecSram_0.update("MelFilterbank.scala:35:15", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(b449), Seq(x454 & b450), Seq(x456_elem_0))
    }
  } 
}
/** END UnrolledForeach x458_inr_Foreach **/
