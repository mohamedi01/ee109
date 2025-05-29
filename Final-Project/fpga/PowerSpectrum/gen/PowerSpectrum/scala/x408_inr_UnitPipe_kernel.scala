import emul._
import emul.implicits._

/** BEGIN UnitPipe x408_inr_UnitPipe **/
object x408_inr_UnitPipe_kernel {
  def run(
  ): Unit = {
    val x402 = FixedPoint.fromInt(0)
    val x403_tuple: Struct1 = new Struct1(x402, FixedPoint(BigDecimal("1353152"),FixFormat(true,32,0)), Bool(false,true))
    val x404 = true
    val x405 = {
      if (x404) x397.enqueue(x403_tuple)
    }
    val x406_wr_x400 = if (TRUE) x400_reg.set(FixedPoint(BigDecimal("338283"),FixFormat(true,32,0)))
    val x407_wr_x401 = if (TRUE) x401_reg.set(FixedPoint(BigDecimal("338288"),FixFormat(true,32,0)))
  } 
}
/** END UnitPipe x408_inr_UnitPipe **/
