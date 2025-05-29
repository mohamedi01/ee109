import emul._
import emul.implicits._

/** BEGIN UnitPipe x323_inr_UnitPipe **/
object x323_inr_UnitPipe_kernel {
  def run(
  ): Unit = {
    val x317 = FixedPoint.fromInt(0)
    val x318_tuple: Struct1 = new Struct1(x317, FixedPoint(BigDecimal("1537280"),FixFormat(true,32,0)), Bool(true,true))
    val x319 = true
    val x320 = {
      if (x319) x314.enqueue(x318_tuple)
    }
    val x321_tuple: Struct2 = new Struct2(FixedPoint(BigDecimal("384320"),FixFormat(true,32,0)), FixedPoint(BigDecimal("0"),FixFormat(true,32,0)), FixedPoint(BigDecimal("384312"),FixFormat(true,32,0)))
    val x322_enq_x315 = {
      if (Bool(true,true)) x315_fifo.enqueue(x321_tuple)
    }
  } 
}
/** END UnitPipe x323_inr_UnitPipe **/
