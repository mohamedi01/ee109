import emul._
import emul.implicits._

/** BEGIN UnitPipe x1097_inr_UnitPipe **/
object x1097_inr_UnitPipe_kernel {
  def run(
    b950: Bool
  ): Unit = if (b950){
    val x1089_rd_x1068 = x1068_reg.value
    val x1090_rd_x1066 = x1066_reg.value
    val x1091 = x1089_rd_x1068 - x1090_rd_x1066
    val x1092 = FloatPoint(BigDecimal("999.9999717180685365747194737195969E-12"), FltFormat(23,8)) < x1091
    val x1093 = !x1092
    val x1094_wr_x1086 = if (TRUE) x1086_reg.set(x1091)
    val x1095_wr_x1087 = if (TRUE) x1087_reg.set(x1092)
    val x1096_wr_x1088 = if (TRUE) x1088_reg.set(x1093)
  } else null.asInstanceOf[Unit]
}
/** END UnitPipe x1097_inr_UnitPipe **/
