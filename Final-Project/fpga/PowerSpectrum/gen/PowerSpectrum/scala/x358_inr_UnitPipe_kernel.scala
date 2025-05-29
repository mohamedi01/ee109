import emul._
import emul.implicits._

/** BEGIN UnitPipe x358_inr_UnitPipe **/
object x358_inr_UnitPipe_kernel {
  def run(
  ): Unit = {
    val x352 = FixedPoint.fromInt(0)
    val x353_tuple: Struct1 = new Struct1(x352, FixedPoint(BigDecimal("1537280"),FixFormat(true,32,0)), Bool(true,true))
    val x354 = true
    val x355 = {
      if (x354) x349.enqueue(x353_tuple)
    }
    val x356_tuple: Struct2 = new Struct2(FixedPoint(BigDecimal("384320"),FixFormat(true,32,0)), FixedPoint(BigDecimal("0"),FixFormat(true,32,0)), FixedPoint(BigDecimal("384312"),FixFormat(true,32,0)))
    val x357_enq_x350 = {
      if (Bool(true,true)) x350_fifo.enqueue(x356_tuple)
    }
  } 
}
/** END UnitPipe x358_inr_UnitPipe **/
