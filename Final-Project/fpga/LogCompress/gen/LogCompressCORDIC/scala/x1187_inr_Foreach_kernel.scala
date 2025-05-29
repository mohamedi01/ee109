import emul._
import emul.implicits._

/** BEGIN UnrolledForeach x1187_inr_Foreach **/
object x1187_inr_Foreach_kernel {
  def run(
    x1177_ctrchain: Array[Counterlike]
  ): Unit = {
    x1177_ctrchain(0).foreach{case (is,vs) => 
      val b1178 = is(0)
      val b1179 = vs(0)
      val x1180_rd = x946_outSram_0.apply("LogCompress.scala:175:33", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(b1178), Seq(b1179))
      val x1181_elem_0 = x1180_rd.apply(0)
      val x1182_rd_x1162 = x1162_logMaxReg_0.value
      val x1183_rd_x858 = x858_argIn.value
      val x1184 = x1182_rd_x1162 - x1183_rd_x858
      val x1185 = Number.max(x1181_elem_0,x1184)
      val x1186_wr = x946_outSram_0.update("LogCompress.scala:175:20", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(b1178), Seq(b1179), Seq(x1185))
    }
  } 
}
/** END UnrolledForeach x1187_inr_Foreach **/
