import emul._
import emul.implicits._

/** BEGIN UnitPipe x883_inr_UnitPipe **/
object x883_inr_UnitPipe_kernel {
  def run(
  ): Unit = {
    val x872_rd_x839 = x839_argIn.value
    val x873_sum = x872_rd_x839 + FixedPoint(BigDecimal("15"),FixFormat(true,32,0))
    val x874 = x873_sum >> FixedPoint(BigDecimal("4"),FixFormat(true,16,0))
    val x875 = x874 << FixedPoint(BigDecimal("4"),FixFormat(true,16,0))
    val x1250 = x874 << FixedPoint(BigDecimal("6"),FixFormat(true,16,0))
    val x877 = FixedPoint.fromInt(0)
    val x878_tuple: Struct1 = new Struct1(x877, x1250, Bool(true,true))
    val x879 = true
    val x880 = {
      if (x879) x869.enqueue(x878_tuple)
    }
    val x881_tuple: Struct2 = new Struct2(x875, FixedPoint(BigDecimal("0"),FixFormat(true,32,0)), x872_rd_x839)
    val x882_enq_x870 = {
      if (Bool(true,true)) x870_fifo.enqueue(x881_tuple)
    }
  } 
}
/** END UnitPipe x883_inr_UnitPipe **/
