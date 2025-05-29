import emul._
import emul.implicits._

/** BEGIN UnrolledForeach x211_inr_Foreach **/
object x211_inr_Foreach_kernel {
  def run(
    x200_ctrchain: Array[Counterlike]
  ): Unit = {
    x200_ctrchain(0).foreach{case (is,vs) => 
      val b201 = is(0)
      val b202 = vs(0)
      val x203_rd = x167_inSram_0.apply("WhisperScale.scala:37:27", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(b201), Seq(b202))
      val x204_elem_0 = x203_rd.apply(0)
      val x205_rd_x186 = x186_currentMaxReg_0.value
      val x206 = x204_elem_0 - x205_rd_x186
      val x207 = Number.max(x206,FloatPoint(BigDecimal("-8"), FltFormat(23,8)))
      val x208 = x207 + FloatPoint(BigDecimal("8"), FltFormat(23,8))
      val x209 = x208 / FloatPoint(BigDecimal("8"), FltFormat(23,8))
      val x210_wr = x168_outSram_0.update("WhisperScale.scala:47:20", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(b201), Seq(b202), Seq(x209))
    }
  } 
}
/** END UnrolledForeach x211_inr_Foreach **/
