import emul._
import emul.implicits._

/** BEGIN UnrolledForeach x346_inr_Foreach **/
object x346_inr_Foreach_kernel {
  def run(
    x336_ctrchain: Array[Counterlike]
  ): Unit = {
    x336_ctrchain(0).foreach{case (is,vs) => 
      val b337 = is(0)
      val b338 = vs(0)
      val x339 = FixedPoint(BigDecimal("0"),FixFormat(true,32,0)) <= b337
      val x340_rd_x325 = x325_reg.value
      val x341 = b337 < x340_rd_x325
      val x342 = x339 && x341
      val x343 = {
        val a0 = if (b338 && x316.nonEmpty) x316.dequeue() else FloatPoint.invalid(FltFormat(23,8))
        Array[FloatPoint](a0)
      }
      val x344_elem_0 = x343.apply(0)
      val x345_wr = x311_realSram_0.update("PowerSpectrum.scala:31:16", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(b337), Seq(x342 & b338), Seq(x344_elem_0))
    }
  } 
}
/** END UnrolledForeach x346_inr_Foreach **/
