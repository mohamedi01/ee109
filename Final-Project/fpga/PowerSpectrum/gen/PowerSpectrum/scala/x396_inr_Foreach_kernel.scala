import emul._
import emul.implicits._

/** BEGIN UnrolledForeach x396_inr_Foreach **/
object x396_inr_Foreach_kernel {
  def run(
    x385_ctrchain: Array[Counterlike]
  ): Unit = {
    x385_ctrchain(0).foreach{case (is,vs) => 
      val b386 = is(0)
      val b387 = vs(0)
      val x388_rd = x311_realSram_0.apply("PowerSpectrum.scala:34:30", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(b386), Seq(b387))
      val x389_elem_0 = x388_rd.apply(0)
      val x391_rd = x312_imagSram_0.apply("PowerSpectrum.scala:34:56", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(b386), Seq(b387))
      val x392_elem_0 = x391_rd.apply(0)
      val x393 = x392_elem_0 * x392_elem_0
      val x442 = (x389_elem_0 * x389_elem_0) + x393
      val x395_wr = x313_outSram_0.update("PowerSpectrum.scala:34:20", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(b386), Seq(b387), Seq(x442))
    }
  } 
}
/** END UnrolledForeach x396_inr_Foreach **/
