import emul._
import emul.implicits._

/** BEGIN UnrolledForeach x1065_inr_Foreach **/
object x1065_inr_Foreach_kernel {
  def run(
    x1048_ctrchain: Array[Counterlike],
    b950: Bool
  ): Unit = if (b950){
    x1048_ctrchain(0).foreach{case (is,vs) => 
      val b1049 = is(0)
      val b1050 = vs(0)
      val x1051_rd_x1044 = x1044_found_flag_0.value
      val x1052 = !x1051_rd_x1044
      val x1053_sum = b1049 + FixedPoint(BigDecimal("1"),FixFormat(true,32,0))
      val x1054_rd = x864_xLutSram_0.apply("LogCompress.scala:123:57", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(x1053_sum), Seq(b1050 & b950))
      val x1055_elem_0 = x1054_rd.apply(0)
      val x1056_rd_x1008 = x1008_reg.value
      val x1057 = x1056_rd_x1008 < x1055_elem_0
      val x1058 = x1052 && x1057
      val x1060_wr_x1043 = if (x1058) x1043_found_idx_0.set(b1049)
      val x1061_wr_x1044 = if (x1058) x1044_found_flag_0.set(Bool(true,true))
    }
  } else null.asInstanceOf[Unit]
}
/** END UnrolledForeach x1065_inr_Foreach **/
