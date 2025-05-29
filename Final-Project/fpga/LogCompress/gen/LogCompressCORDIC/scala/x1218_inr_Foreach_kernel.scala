import emul._
import emul.implicits._

/** BEGIN UnrolledForeach x1218_inr_Foreach **/
object x1218_inr_Foreach_kernel {
  def run(
    x1207_ctrchain: Array[Counterlike]
  ): Unit = {
    x1207_ctrchain(0).foreach{case (is,vs) => 
      val b1208 = is(0)
      val b1209 = vs(0)
      val x1210 = FixedPoint(BigDecimal("0"),FixFormat(true,32,0)) <= b1208
      val x1211_rd_x1191 = x1191_reg.value
      val x1212 = b1208 < x1211_rd_x1191
      val x1213 = x1210 && x1212
      val x1214_rd = x946_outSram_0.apply("MemoryDealiasing.scala:32:17", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(b1208), Seq(x1213 & Bool(true,true) & b1209))
      val x1215_elem_0 = x1214_rd.apply(0)
      val x1216_tuple: Struct3 = new Struct3(x1215_elem_0, x1213)
      val x1217 = {
        if (b1209) x1189.enqueue(x1216_tuple)
      }
    }
  } 
}
/** END UnrolledForeach x1218_inr_Foreach **/
