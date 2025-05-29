import emul._
import emul.implicits._

/** BEGIN UnitPipe x1140_inr_UnitPipe **/
object x1140_inr_UnitPipe_kernel {
  def run(
  ): Unit = {
    val x1129_rd_x839 = x839_argIn.value
    val x1130_sum = x1129_rd_x839 + FixedPoint(BigDecimal("15"),FixFormat(true,32,0))
    val x1131 = x1130_sum >> FixedPoint(BigDecimal("4"),FixFormat(true,16,0))
    val x1132 = x1131 << FixedPoint(BigDecimal("4"),FixFormat(true,16,0))
    val x1251 = x1131 << FixedPoint(BigDecimal("6"),FixFormat(true,16,0))
    val x1134 = FixedPoint.fromInt(0)
    val x1135_tuple: Struct1 = new Struct1(x1134, x1251, Bool(false,true))
    val x1136 = true
    val x1137 = {
      if (x1136) x1124.enqueue(x1135_tuple)
    }
    val x1138_wr_x1127 = if (TRUE) x1127_reg.set(x1129_rd_x839)
    val x1139_wr_x1128 = if (TRUE) x1128_reg.set(x1132)
  } 
}
/** END UnitPipe x1140_inr_UnitPipe **/
