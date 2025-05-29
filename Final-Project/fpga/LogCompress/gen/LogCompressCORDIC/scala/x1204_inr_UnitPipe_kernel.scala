import emul._
import emul.implicits._

/** BEGIN UnitPipe x1204_inr_UnitPipe **/
object x1204_inr_UnitPipe_kernel {
  def run(
  ): Unit = {
    val x1193_rd_x839 = x839_argIn.value
    val x1194_sum = x1193_rd_x839 + FixedPoint(BigDecimal("15"),FixFormat(true,32,0))
    val x1195 = x1194_sum >> FixedPoint(BigDecimal("4"),FixFormat(true,16,0))
    val x1196 = x1195 << FixedPoint(BigDecimal("4"),FixFormat(true,16,0))
    val x1252 = x1195 << FixedPoint(BigDecimal("6"),FixFormat(true,16,0))
    val x1198 = FixedPoint.fromInt(0)
    val x1199_tuple: Struct1 = new Struct1(x1198, x1252, Bool(false,true))
    val x1200 = true
    val x1201 = {
      if (x1200) x1188.enqueue(x1199_tuple)
    }
    val x1202_wr_x1191 = if (TRUE) x1191_reg.set(x1193_rd_x839)
    val x1203_wr_x1192 = if (TRUE) x1192_reg.set(x1196)
  } 
}
/** END UnitPipe x1204_inr_UnitPipe **/
