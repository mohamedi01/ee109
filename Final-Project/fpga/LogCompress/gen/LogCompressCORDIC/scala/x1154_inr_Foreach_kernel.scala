import emul._
import emul.implicits._

/** BEGIN UnrolledForeach x1154_inr_Foreach **/
object x1154_inr_Foreach_kernel {
  def run(
    x1143_ctrchain: Array[Counterlike]
  ): Unit = {
    x1143_ctrchain(0).foreach{case (is,vs) => 
      val b1144 = is(0)
      val b1145 = vs(0)
      val x1146 = FixedPoint(BigDecimal("0"),FixFormat(true,32,0)) <= b1144
      val x1147_rd_x1127 = x1127_reg.value
      val x1148 = b1144 < x1147_rd_x1127
      val x1149 = x1146 && x1148
      val x1150_rd = x946_outSram_0.apply("MemoryDealiasing.scala:32:17", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(b1144), Seq(x1149 & Bool(true,true) & b1145))
      val x1151_elem_0 = x1150_rd.apply(0)
      val x1152_tuple: Struct3 = new Struct3(x1151_elem_0, x1149)
      val x1153 = {
        if (b1145) x1125.enqueue(x1152_tuple)
      }
    }
  } 
}
/** END UnrolledForeach x1154_inr_Foreach **/
