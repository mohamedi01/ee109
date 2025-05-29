import emul._
import emul.implicits._

/** BEGIN UnitPipe x1219_outr_UnitPipe **/
object x1219_outr_UnitPipe_kernel {
  def run(
  ): Unit = {
    x1191_reg.initMem(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))
    x1192_reg.initMem(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))
    val x1204_inr_UnitPipe = x1204_inr_UnitPipe_kernel.run()
    val x1249_rd_x1192 = x1192_reg.value
    val x1206_ctr = Counter(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)), x1249_rd_x1192, FixedPoint(BigDecimal("1"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)))
    val x1207_ctrchain = Array[Counterlike](x1206_ctr)
    val x1218_inr_Foreach = x1218_inr_Foreach_kernel.run(x1207_ctrchain)
  } 
}
/** END UnitPipe x1219_outr_UnitPipe **/
