import emul._
import emul.implicits._

/** BEGIN UnitPipe x175_inr_UnitPipe_AlignedLoadCommand **/
object x175_inr_UnitPipe_AlignedLoadCommand_kernel {
  def run(
  ): Unit = {
    val x171 = FixedPoint.fromInt(0)
    val x172_tuple: Struct1 = new Struct1(x171, FixedPoint(BigDecimal("320"),FixFormat(true,32,0)), Bool(true,true))
    val x173 = true
    val x174 = {
      if (x173) x169.enqueue(x172_tuple)
    }
  } 
}
/** END UnitPipe x175_inr_UnitPipe_AlignedLoadCommand **/
