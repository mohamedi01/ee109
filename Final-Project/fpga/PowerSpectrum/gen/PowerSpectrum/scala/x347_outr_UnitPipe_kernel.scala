import emul._
import emul.implicits._

/** BEGIN UnitPipe x347_outr_UnitPipe **/
object x347_outr_UnitPipe_kernel {
  def run(
  ): Unit = {
    x325_reg.initMem(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))
    x326_reg.initMem(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))
    val x333_inr_UnitPipe = x333_inr_UnitPipe_kernel.run()
    val x439_rd_x326 = x326_reg.value
    val x335_ctr = Counter(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)), x439_rd_x326, FixedPoint(BigDecimal("1"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)))
    val x336_ctrchain = Array[Counterlike](x335_ctr)
    val x346_inr_Foreach = x346_inr_Foreach_kernel.run(x336_ctrchain)
  } 
}
/** END UnitPipe x347_outr_UnitPipe **/
