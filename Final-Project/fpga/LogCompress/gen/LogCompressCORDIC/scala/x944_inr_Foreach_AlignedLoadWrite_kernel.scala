import emul._
import emul.implicits._

/** BEGIN UnrolledForeach x944_inr_Foreach_AlignedLoadWrite **/
object x944_inr_Foreach_AlignedLoadWrite_kernel {
  def run(
    x937_ctrchain: Array[Counterlike]
  ): Unit = {
    x937_ctrchain(0).foreach{case (is,vs) => 
      val b938 = is(0)
      val b939 = vs(0)
      val x940 = {
        val a0 = if (b939 && x929.nonEmpty) x929.dequeue() else FloatPoint.invalid(FltFormat(23,8))
        Array[FloatPoint](a0)
      }
      val x941_elem_0 = x940.apply(0)
      val x942_wr = x868_yLutSram_1.update("LogCompress.scala:63:16", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(b938), Seq(b939), Seq(x941_elem_0))
      val x943_wr = x867_yLutSram_0.update("LogCompress.scala:63:16", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(b938), Seq(b939), Seq(x941_elem_0))
    }
  } 
}
/** END UnrolledForeach x944_inr_Foreach_AlignedLoadWrite **/
