import emul._
import emul.implicits._

/** BEGIN UnitPipe x1122_inr_UnitPipe **/
object x1122_inr_UnitPipe_kernel {
  def run(
    b949: FixedPoint,
    b950: Bool
  ): Unit = if (b950){
    val x1118_rd_x1085 = x1085_log10_of_norm_val_0.value
    val x1119_rd_x971 = x971_reg.value
    val x1120 = x1119_rd_x971 + x1118_rd_x1085
    val x1121_wr = x946_outSram_0.update("LogCompress.scala:152:20", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(b949), Seq(TRUE), Seq(x1120))
  } else null.asInstanceOf[Unit]
}
/** END UnitPipe x1122_inr_UnitPipe **/
