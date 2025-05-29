import emul._
import emul.implicits._

/** BEGIN UnrolledReduce x1175_inr_Reduce **/
object x1175_inr_Reduce_kernel {
  def run(
    x1164_ctrchain: Array[Counterlike]
  ): Unit = {
    x1164_ctrchain(0).foreach{case (is,vs) => 
      val b1165 = is(0)
      val b1166 = vs(0)
      val x1167_rd = x946_outSram_0.apply("LogCompress.scala:161:56", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(b1165), Seq(b1166))
      val x1168_elem_0 = x1167_rd.apply(0)
      val x1169_rd_x1162 = x1162_logMaxReg_0.value
      val x1170 = b1165 === FixedPoint(BigDecimal("0"),FixFormat(true,32,0))
      val x1171 = x1169_rd_x1162 < x1168_elem_0
      val x1172 = if (x1171) x1168_elem_0 else x1169_rd_x1162
      val x1173 = if (x1170) x1168_elem_0 else x1172
      val x1174_wr_x1162 = if (TRUE) x1162_logMaxReg_0.set(x1173)
    }
  } 
}
/** END UnrolledReduce x1175_inr_Reduce **/
