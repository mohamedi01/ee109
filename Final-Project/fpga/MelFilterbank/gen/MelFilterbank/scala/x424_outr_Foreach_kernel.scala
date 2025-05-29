import emul._
import emul.implicits._

/** BEGIN UnrolledForeach x424_outr_Foreach **/
object x424_outr_Foreach_kernel {
  def run(
    x391_ctrchain: Array[Counterlike]
  ): Unit = {
    x391_ctrchain(0).foreach{case (is,vs) => 
      val b392 = is(0)
      val b393 = vs(0)
      x394_reg.initMem(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))
      x395_reg.initMem(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))
      x396_reg.initMem(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))
      val x405_inr_UnitPipe = x405_inr_UnitPipe_kernel.run(b393)
      val x517_rd_x396 = x396_reg.value
      val x407_ctr = Counter(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)), x517_rd_x396, FixedPoint(BigDecimal("1"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)))
      val x408_ctrchain = Array[Counterlike](x407_ctr)
      val x423_inr_Foreach = x423_inr_Foreach_kernel.run(x408_ctrchain, b392, b393)
    }
  } 
}
/** END UnrolledForeach x424_outr_Foreach **/
