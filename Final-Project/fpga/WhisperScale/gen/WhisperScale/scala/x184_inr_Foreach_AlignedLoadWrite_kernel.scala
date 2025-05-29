import emul._
import emul.implicits._

/** BEGIN UnrolledForeach x184_inr_Foreach_AlignedLoadWrite **/
object x184_inr_Foreach_AlignedLoadWrite_kernel {
  def run(
    x178_ctrchain: Array[Counterlike]
  ): Unit = {
    x178_ctrchain(0).foreach{case (is,vs) => 
      val b179 = is(0)
      val b180 = vs(0)
      val x181 = {
        val a0 = if (b180 && x170.nonEmpty) x170.dequeue() else FloatPoint.invalid(FltFormat(23,8))
        Array[FloatPoint](a0)
      }
      val x182_elem_0 = x181.apply(0)
      val x183_wr = x167_inSram_0.update("WhisperScale.scala:29:14", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(b179), Seq(b180), Seq(x182_elem_0))
    }
  } 
}
/** END UnrolledForeach x184_inr_Foreach_AlignedLoadWrite **/
