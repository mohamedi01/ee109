import emul._
import emul.implicits._

/** BEGIN UnitPipe x459_outr_UnitPipe **/
object x459_outr_UnitPipe_kernel {
  def run(
  ): Unit = {
    x437_reg.initMem(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))
    x438_reg.initMem(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))
    val x445_inr_UnitPipe = x445_inr_UnitPipe_kernel.run()
    val x518_rd_x438 = x438_reg.value
    val x447_ctr = Counter(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)), x518_rd_x438, FixedPoint(BigDecimal("1"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)))
    val x448_ctrchain = Array[Counterlike](x447_ctr)
    val x458_inr_Foreach = x458_inr_Foreach_kernel.run(x448_ctrchain)
  } 
}
/** END UnitPipe x459_outr_UnitPipe **/
