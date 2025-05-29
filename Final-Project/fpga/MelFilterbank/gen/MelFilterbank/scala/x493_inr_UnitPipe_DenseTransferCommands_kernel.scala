import emul._
import emul.implicits._

/** BEGIN UnitPipe x493_inr_UnitPipe_DenseTransferCommands **/
object x493_inr_UnitPipe_DenseTransferCommands_kernel {
  def run(
  ): Unit = {
    val x489 = FixedPoint.fromInt(0)
    val x490_tuple: Struct1 = new Struct1(x489, FixedPoint(BigDecimal("320"),FixFormat(true,32,0)), Bool(false,true))
    val x491 = true
    val x492 = {
      if (x491) x486.enqueue(x490_tuple)
    }
  } 
}
/** END UnitPipe x493_inr_UnitPipe_DenseTransferCommands **/
