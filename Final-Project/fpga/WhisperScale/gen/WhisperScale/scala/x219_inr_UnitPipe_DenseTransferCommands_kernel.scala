import emul._
import emul.implicits._

/** BEGIN UnitPipe x219_inr_UnitPipe_DenseTransferCommands **/
object x219_inr_UnitPipe_DenseTransferCommands_kernel {
  def run(
  ): Unit = {
    val x215 = FixedPoint.fromInt(0)
    val x216_tuple: Struct1 = new Struct1(x215, FixedPoint(BigDecimal("320"),FixFormat(true,32,0)), Bool(false,true))
    val x217 = true
    val x218 = {
      if (x217) x212.enqueue(x216_tuple)
    }
  } 
}
/** END UnitPipe x219_inr_UnitPipe_DenseTransferCommands **/
