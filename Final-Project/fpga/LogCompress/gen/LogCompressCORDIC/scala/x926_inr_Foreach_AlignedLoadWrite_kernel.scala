import emul._
import emul.implicits._

/** BEGIN UnrolledForeach x926_inr_Foreach_AlignedLoadWrite **/
object x926_inr_Foreach_AlignedLoadWrite_kernel {
  def run(
    x918_ctrchain: Array[Counterlike]
  ): Unit = {
    x918_ctrchain(0).foreach{case (is,vs) => 
      val b919 = is(0)
      val b920 = vs(0)
      val x921 = {
        val a0 = if (b920 && x910.nonEmpty) x910.dequeue() else FloatPoint.invalid(FltFormat(23,8))
        Array[FloatPoint](a0)
      }
      val x922_elem_0 = x921.apply(0)
      val x923_wr = x864_xLutSram_0.update("LogCompress.scala:62:16", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(b919), Seq(b920), Seq(x922_elem_0))
      val x924_wr = x866_xLutSram_2.update("LogCompress.scala:62:16", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(b919), Seq(b920), Seq(x922_elem_0))
      val x925_wr = x865_xLutSram_1.update("LogCompress.scala:62:16", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(b919), Seq(b920), Seq(x922_elem_0))
    }
  } 
}
/** END UnrolledForeach x926_inr_Foreach_AlignedLoadWrite **/
