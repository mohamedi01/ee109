import emul._
import emul.implicits._

/** BEGIN UnrolledForeach x906_inr_Foreach **/
object x906_inr_Foreach_kernel {
  def run(
    x896_ctrchain: Array[Counterlike]
  ): Unit = {
    x896_ctrchain(0).foreach{case (is,vs) => 
      val b897 = is(0)
      val b898 = vs(0)
      val x899 = FixedPoint(BigDecimal("0"),FixFormat(true,32,0)) <= b897
      val x900_rd_x885 = x885_reg.value
      val x901 = b897 < x900_rd_x885
      val x902 = x899 && x901
      val x903 = {
        val a0 = if (b898 && x871.nonEmpty) x871.dequeue() else FloatPoint.invalid(FltFormat(23,8))
        Array[FloatPoint](a0)
      }
      val x904_elem_0 = x903.apply(0)
      val x905_wr = x863_buf_0.update("LogCompress.scala:59:11", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(b897), Seq(x902 & b898), Seq(x904_elem_0))
    }
  } 
}
/** END UnrolledForeach x906_inr_Foreach **/
