import emul._
import emul.implicits._

/** BEGIN UnitPipe x915_inr_UnitPipe_AlignedLoadCommand **/
object x915_inr_UnitPipe_AlignedLoadCommand_kernel {
  def run(
  ): Unit = {
    val x911 = FixedPoint.fromInt(0)
    val x912_tuple: Struct1 = new Struct1(x911, FixedPoint(BigDecimal("512"),FixFormat(true,32,0)), Bool(true,true))
    val x913 = true
    val x914 = {
      if (x913) x909.enqueue(x912_tuple)
    }
  } 
}
/** END UnitPipe x915_inr_UnitPipe_AlignedLoadCommand **/
