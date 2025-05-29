import emul._
import emul.implicits._

/** BEGIN UnitPipe x1084_inr_UnitPipe **/
object x1084_inr_UnitPipe_kernel {
  def run(
    b950: Bool
  ): Unit = if (b950){
    val x1070_rd_x1043 = x1043_found_idx_0.value
    val x1071_rd = x865_xLutSram_1.apply("LogCompress.scala:136:29", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(x1070_rd_x1043), Seq(TRUE))
    val x1072_elem_0 = x1071_rd.apply(0)
    val x1073_rd = x867_yLutSram_0.apply("LogCompress.scala:137:29", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(x1070_rd_x1043), Seq(TRUE))
    val x1074_elem_0 = x1073_rd.apply(0)
    val x1075_sum = x1070_rd_x1043 + FixedPoint(BigDecimal("1"),FixFormat(true,32,0))
    val x1076_rd = x866_xLutSram_2.apply("LogCompress.scala:138:30", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(x1075_sum), Seq(TRUE))
    val x1077_elem_0 = x1076_rd.apply(0)
    val x1078_rd = x868_yLutSram_1.apply("LogCompress.scala:139:30", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(x1075_sum), Seq(TRUE))
    val x1079_elem_0 = x1078_rd.apply(0)
    val x1080_wr_x1066 = if (TRUE) x1066_reg.set(x1072_elem_0)
    val x1081_wr_x1067 = if (TRUE) x1067_reg.set(x1074_elem_0)
    val x1082_wr_x1068 = if (TRUE) x1068_reg.set(x1077_elem_0)
    val x1083_wr_x1069 = if (TRUE) x1069_reg.set(x1079_elem_0)
  } else null.asInstanceOf[Unit]
}
/** END UnitPipe x1084_inr_UnitPipe **/
