import emul._
import emul.implicits._

/** BEGIN UnitPipe x435_inr_UnitPipe **/
object x435_inr_UnitPipe_kernel {
  def run(
  ): Unit = {
    val x429 = FixedPoint.fromInt(0)
    val x430_tuple: Struct1 = new Struct1(x429, FixedPoint(BigDecimal("832"),FixFormat(true,32,0)), Bool(true,true))
    val x431 = true
    val x432 = {
      if (x431) x426.enqueue(x430_tuple)
    }
    val x433_tuple: Struct2 = new Struct2(FixedPoint(BigDecimal("208"),FixFormat(true,32,0)), FixedPoint(BigDecimal("0"),FixFormat(true,32,0)), FixedPoint(BigDecimal("201"),FixFormat(true,32,0)))
    val x434_enq_x427 = {
      if (Bool(true,true)) x427_fifo.enqueue(x433_tuple)
    }
  } 
}
/** END UnitPipe x435_inr_UnitPipe **/
