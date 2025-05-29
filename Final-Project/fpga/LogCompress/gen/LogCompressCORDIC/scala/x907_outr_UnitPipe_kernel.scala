import emul._
import emul.implicits._

/** BEGIN UnitPipe x907_outr_UnitPipe **/
object x907_outr_UnitPipe_kernel {
  def run(
  ): Unit = {
    x885_reg.initMem(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))
    x886_reg.initMem(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))
    val x893_inr_UnitPipe = x893_inr_UnitPipe_kernel.run()
    val x1242_rd_x886 = x886_reg.value
    val x895_ctr = Counter(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)), x1242_rd_x886, FixedPoint(BigDecimal("1"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)))
    val x896_ctrchain = Array[Counterlike](x895_ctr)
    val x906_inr_Foreach = x906_inr_Foreach_kernel.run(x896_ctrchain)
  } 
}
/** END UnitPipe x907_outr_UnitPipe **/
