import emul._
import emul.implicits._

/** BEGIN UnitPipe x959_inr_UnitPipe **/
object x959_inr_UnitPipe_kernel {
  def run(
    b950: Bool,
    b949: FixedPoint
  ): Unit = if (b950){
    val x952_rd = x863_buf_0.apply("LogCompress.scala:78:21", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(b949), Seq(TRUE))
    val x953_elem_0 = x952_rd.apply(0)
    val x954 = x953_elem_0 < FloatPoint("-0.0", FltFormat(23,8))
    val x955 = -x953_elem_0
    val x956 = if (x954) x955 else x953_elem_0
    val x957 = Number.max(x956,FloatPoint(BigDecimal("100.0000013351431960018089739605784E-12"), FltFormat(23,8)))
    val x958_wr_x951 = if (TRUE) x951_reg.set(x957)
  } else null.asInstanceOf[Unit]
}
/** END UnitPipe x959_inr_UnitPipe **/
