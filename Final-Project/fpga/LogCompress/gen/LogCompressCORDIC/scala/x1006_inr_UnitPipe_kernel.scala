import emul._
import emul.implicits._

/** BEGIN UnitPipe x1006_inr_UnitPipe **/
object x1006_inr_UnitPipe_kernel {
  def run(
    b950: Bool
  ): Unit = if (b950){
    val x972_rd_x951 = x951_reg.value
    val x973 = FloatPoint(BigDecimal("1"), FltFormat(23,8)) <= x972_rd_x951
    val x974 = FloatPoint(BigDecimal("0.100000001490116119384765625"), FltFormat(23,8)) <= x972_rd_x951
    val x975 = FloatPoint(BigDecimal("0.00999999977648258209228515625"), FltFormat(23,8)) <= x972_rd_x951
    val x976 = FloatPoint(BigDecimal("0.001000000047497451305389404296875"), FltFormat(23,8)) <= x972_rd_x951
    val x977 = FloatPoint(BigDecimal("0.0000999999974737875163555145263671875"), FltFormat(23,8)) <= x972_rd_x951
    val x978 = FloatPoint(BigDecimal("0.00000999999974737875163555145263671875"), FltFormat(23,8)) <= x972_rd_x951
    val x979 = FloatPoint(BigDecimal("0.0000009999999974752427078783512115478516"), FltFormat(23,8)) <= x972_rd_x951
    val x980 = FloatPoint(BigDecimal("0.0000001000000011686097423080354928970337"), FltFormat(23,8)) <= x972_rd_x951
    val x981 = FloatPoint(BigDecimal("9.999999939225290290778502821922302E-9"), FltFormat(23,8)) <= x972_rd_x951
    val x982 = FloatPoint(BigDecimal("999.9999717180685365747194737195969E-12"), FltFormat(23,8)) <= x972_rd_x951
    val x983 = if (x982) FloatPoint(BigDecimal("-9.000"), FltFormat(23,8)) else FloatPoint(BigDecimal("-10.00"), FltFormat(23,8))
    val x984 = if (x981) FloatPoint(BigDecimal("-8"), FltFormat(23,8)) else x983
    val x985 = if (x980) FloatPoint(BigDecimal("-7.00"), FltFormat(23,8)) else x984
    val x986 = if (x979) FloatPoint(BigDecimal("-6.0"), FltFormat(23,8)) else x985
    val x987 = if (x978) FloatPoint(BigDecimal("-5.00"), FltFormat(23,8)) else x986
    val x988 = if (x977) FloatPoint(BigDecimal("-4"), FltFormat(23,8)) else x987
    val x989 = if (x976) FloatPoint(BigDecimal("-3.0"), FltFormat(23,8)) else x988
    val x990 = if (x975) FloatPoint(BigDecimal("-2"), FltFormat(23,8)) else x989
    val x991 = if (x974) FloatPoint(BigDecimal("-1"), FltFormat(23,8)) else x990
    val x992 = if (x973) FloatPoint("-0.0", FltFormat(23,8)) else x991
    val x993_wr_x960 = if (TRUE) x960_decade_calc_reg_0.set(x992)
    val x994_rd_x960 = x960_decade_calc_reg_0.value
    val x995_wr_x961 = if (TRUE) x961_reg.set(x973)
    val x996_wr_x962 = if (TRUE) x962_reg.set(x974)
    val x997_wr_x963 = if (TRUE) x963_reg.set(x975)
    val x998_wr_x964 = if (TRUE) x964_reg.set(x976)
    val x999_wr_x965 = if (TRUE) x965_reg.set(x977)
    val x1000_wr_x966 = if (TRUE) x966_reg.set(x978)
    val x1001_wr_x967 = if (TRUE) x967_reg.set(x979)
    val x1002_wr_x968 = if (TRUE) x968_reg.set(x980)
    val x1003_wr_x969 = if (TRUE) x969_reg.set(x981)
    val x1004_wr_x970 = if (TRUE) x970_reg.set(x982)
    val x1005_wr_x971 = if (TRUE) x971_reg.set(x994_rd_x960)
  } else null.asInstanceOf[Unit]
}
/** END UnitPipe x1006_inr_UnitPipe **/
