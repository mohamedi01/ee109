import emul._
import emul.implicits._

/** BEGIN UnitPipe x1084_inr_UnitPipe **/
object x1084_inr_UnitPipe_kernel {
  def run(
    b937: Bool
  ): Unit = if (b937){
    val x1076_rd_x1055 = x1055_reg.value
    val x1077_rd_x1053 = x1053_reg.value
    val x1078 = x1076_rd_x1055 - x1077_rd_x1053
    val x1079 = FloatPoint(BigDecimal("999.9999717180685365747194737195969E-12"), FltFormat(23,8)) < x1078
    val x1080 = !x1079
    val x1081_wr_x1073 = if (TRUE) x1073_reg.set(x1078)
    val x1082_wr_x1074 = if (TRUE) x1074_reg.set(x1079)
    val x1083_wr_x1075 = if (TRUE) x1075_reg.set(x1080)
  } else null.asInstanceOf[Unit]
}
/** END UnitPipe x1084_inr_UnitPipe **/
