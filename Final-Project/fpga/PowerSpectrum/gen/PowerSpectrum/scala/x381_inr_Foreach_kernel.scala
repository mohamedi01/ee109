import emul._
import emul.implicits._

/** BEGIN UnrolledForeach x381_inr_Foreach **/
object x381_inr_Foreach_kernel {
  def run(
    x371_ctrchain: Array[Counterlike]
  ): Unit = {
    x371_ctrchain(0).foreach{case (is,vs) => 
      val b372 = is(0)
      val b373 = vs(0)
      val x374 = FixedPoint(BigDecimal("0"),FixFormat(true,32,0)) <= b372
      val x375_rd_x360 = x360_reg.value
      val x376 = b372 < x375_rd_x360
      val x377 = x374 && x376
      val x378 = {
        val a0 = if (b373 && x351.nonEmpty) x351.dequeue() else FloatPoint.invalid(FltFormat(23,8))
        Array[FloatPoint](a0)
      }
      val x379_elem_0 = x378.apply(0)
      val x380_wr = x312_imagSram_0.update("PowerSpectrum.scala:32:16", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(b372), Seq(x377 & b373), Seq(x379_elem_0))
    }
  } 
}
/** END UnrolledForeach x381_inr_Foreach **/
