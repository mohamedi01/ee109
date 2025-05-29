import emul._
import emul.implicits._

/** BEGIN UnrolledForeach x422_inr_Foreach **/
object x422_inr_Foreach_kernel {
  def run(
    x411_ctrchain: Array[Counterlike]
  ): Unit = {
    x411_ctrchain(0).foreach{case (is,vs) => 
      val b412 = is(0)
      val b413 = vs(0)
      val x414 = FixedPoint(BigDecimal("0"),FixFormat(true,32,0)) <= b412
      val x415_rd_x400 = x400_reg.value
      val x416 = b412 < x415_rd_x400
      val x417 = x414 && x416
      val x418_rd = x313_outSram_0.apply("PowerSpectrum.scala:36:15", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(b412), Seq(x417 & b413))
      val x419_elem_0 = x418_rd.apply(0)
      val x420_tuple: Struct3 = new Struct3(x419_elem_0, x417)
      val x421 = {
        if (b413) x398.enqueue(x420_tuple)
      }
    }
  } 
}
/** END UnrolledForeach x422_inr_Foreach **/
