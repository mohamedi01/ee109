import emul._
import emul.implicits._

/** BEGIN UnitPipe x1042_inr_UnitPipe **/
object x1042_inr_UnitPipe_kernel {
  def run(
    b950: Bool
  ): Unit = if (b950){
    val x1009_rd_x951 = x951_reg.value
    val x1010 = x1009_rd_x951 * FloatPoint(BigDecimal("10.00"), FltFormat(23,8))
    val x1011 = x1009_rd_x951 * FloatPoint(BigDecimal("100.0000"), FltFormat(23,8))
    val x1012 = x1009_rd_x951 * FloatPoint(BigDecimal("1000.000000"), FltFormat(23,8))
    val x1013 = x1009_rd_x951 * FloatPoint(BigDecimal("10000.000000000"), FltFormat(23,8))
    val x1014 = x1009_rd_x951 * FloatPoint(BigDecimal("100000.00000000000"), FltFormat(23,8))
    val x1015 = x1009_rd_x951 * FloatPoint(BigDecimal("1000000.0000000000000"), FltFormat(23,8))
    val x1016 = x1009_rd_x951 * FloatPoint(BigDecimal("10000000.0000000000000000"), FltFormat(23,8))
    val x1017 = x1009_rd_x951 * FloatPoint(BigDecimal("100000000.000000000000000000"), FltFormat(23,8))
    val x1018 = x1009_rd_x951 * FloatPoint(BigDecimal("1000000000.00000000000000000000"), FltFormat(23,8))
    val x1019_rd_x970 = x970_reg.value
    val x1020 = if (x1019_rd_x970) x1018 else FloatPoint(BigDecimal("1"), FltFormat(23,8))
    val x1021_rd_x969 = x969_reg.value
    val x1022 = if (x1021_rd_x969) x1017 else x1020
    val x1023_rd_x968 = x968_reg.value
    val x1024 = if (x1023_rd_x968) x1016 else x1022
    val x1025_rd_x967 = x967_reg.value
    val x1026 = if (x1025_rd_x967) x1015 else x1024
    val x1027_rd_x966 = x966_reg.value
    val x1028 = if (x1027_rd_x966) x1014 else x1026
    val x1029_rd_x965 = x965_reg.value
    val x1030 = if (x1029_rd_x965) x1013 else x1028
    val x1031_rd_x964 = x964_reg.value
    val x1032 = if (x1031_rd_x964) x1012 else x1030
    val x1033_rd_x963 = x963_reg.value
    val x1034 = if (x1033_rd_x963) x1011 else x1032
    val x1035_rd_x962 = x962_reg.value
    val x1036 = if (x1035_rd_x962) x1010 else x1034
    val x1037_rd_x961 = x961_reg.value
    val x1038 = if (x1037_rd_x961) x1009_rd_x951 else x1036
    val x1039_wr_x1007 = if (TRUE) x1007_norm_val_calc_reg_0.set(x1038)
    val x1040_rd_x1007 = x1007_norm_val_calc_reg_0.value
    val x1041_wr_x1008 = if (TRUE) x1008_reg.set(x1040_rd_x1007)
  } else null.asInstanceOf[Unit]
}
/** END UnitPipe x1042_inr_UnitPipe **/
