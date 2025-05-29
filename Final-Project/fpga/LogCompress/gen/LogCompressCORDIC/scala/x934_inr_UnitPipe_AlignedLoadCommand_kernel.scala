import emul._
import emul.implicits._

/** BEGIN UnitPipe x934_inr_UnitPipe_AlignedLoadCommand **/
object x934_inr_UnitPipe_AlignedLoadCommand_kernel {
  def run(
  ): Unit = {
    val x930 = FixedPoint.fromInt(0)
    val x931_tuple: Struct1 = new Struct1(x930, FixedPoint(BigDecimal("512"),FixFormat(true,32,0)), Bool(true,true))
    val x932 = true
    val x933 = {
      if (x932) x928.enqueue(x931_tuple)
    }
  } 
}
/** END UnitPipe x934_inr_UnitPipe_AlignedLoadCommand **/
