import emul._
import emul.implicits._

/** BEGIN UnitPipe x1155_outr_UnitPipe **/
object x1155_outr_UnitPipe_kernel {
  def run(
  ): Unit = {
    x1127_reg.initMem(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))
    x1128_reg.initMem(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))
    val x1140_inr_UnitPipe = x1140_inr_UnitPipe_kernel.run()
    val x1246_rd_x1128 = x1128_reg.value
    val x1142_ctr = Counter(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)), x1246_rd_x1128, FixedPoint(BigDecimal("1"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)))
    val x1143_ctrchain = Array[Counterlike](x1142_ctr)
    val x1154_inr_Foreach = x1154_inr_Foreach_kernel.run(x1143_ctrchain)
  } 
}
/** END UnitPipe x1155_outr_UnitPipe **/
