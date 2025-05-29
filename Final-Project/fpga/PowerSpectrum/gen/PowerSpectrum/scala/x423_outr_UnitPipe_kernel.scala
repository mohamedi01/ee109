import emul._
import emul.implicits._

/** BEGIN UnitPipe x423_outr_UnitPipe **/
object x423_outr_UnitPipe_kernel {
  def run(
  ): Unit = {
    x400_reg.initMem(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))
    x401_reg.initMem(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))
    val x408_inr_UnitPipe = x408_inr_UnitPipe_kernel.run()
    val x441_rd_x401 = x401_reg.value
    val x410_ctr = Counter(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)), x441_rd_x401, FixedPoint(BigDecimal("1"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)))
    val x411_ctrchain = Array[Counterlike](x410_ctr)
    val x422_inr_Foreach = x422_inr_Foreach_kernel.run(x411_ctrchain)
  } 
}
/** END UnitPipe x423_outr_UnitPipe **/
