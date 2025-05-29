import emul._
import emul.implicits._

/** BEGIN UnitPipe x382_outr_UnitPipe **/
object x382_outr_UnitPipe_kernel {
  def run(
  ): Unit = {
    x360_reg.initMem(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))
    x361_reg.initMem(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))
    val x368_inr_UnitPipe = x368_inr_UnitPipe_kernel.run()
    val x440_rd_x361 = x361_reg.value
    val x370_ctr = Counter(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)), x440_rd_x361, FixedPoint(BigDecimal("1"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)))
    val x371_ctrchain = Array[Counterlike](x370_ctr)
    val x381_inr_Foreach = x381_inr_Foreach_kernel.run(x371_ctrchain)
  } 
}
/** END UnitPipe x382_outr_UnitPipe **/
