import emul._
import emul.implicits._

/** BEGIN Switch x1117_inr_Switch **/
object x1117_inr_Switch_kernel {
  def run(
    x1245_rd_x1088: Bool,
    x1244_rd_x1087: Bool
  ): Unit = {
    if (x1244_rd_x1087) {
      val x1100_rd_x1066 = x1066_reg.value
      val x1101_rd_x1008 = x1008_reg.value
      val x1102 = x1101_rd_x1008 - x1100_rd_x1066
      val x1103_rd_x1069 = x1069_reg.value
      val x1104_rd_x1067 = x1067_reg.value
      val x1105 = x1103_rd_x1069 - x1104_rd_x1067
      val x1106 = x1102 * x1105
      val x1107_rd_x1086 = x1086_reg.value
      val x1108 = x1106 / x1107_rd_x1086
      val x1109 = x1104_rd_x1067 + x1108
      val x1110_rd_x1087 = x1087_reg.value
      val x1111_wr_x1085 = if (x1110_rd_x1087) x1085_log10_of_norm_val_0.set(x1109)
      ()
    }
    else if (x1245_rd_x1088) {
      val x1113_rd_x1088 = x1088_reg.value
      val x1114_rd_x1067 = x1067_reg.value
      val x1115_wr_x1085 = if (x1113_rd_x1088) x1085_log10_of_norm_val_0.set(x1114_rd_x1067)
      ()
    }
    else ()
  } 
}
/** END Switch x1117_inr_Switch **/
