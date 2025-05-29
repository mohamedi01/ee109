import emul._
import emul.implicits._

/** BEGIN UnitPipe x232_inr_UnitPipe_DenseTransferAck **/
object x232_inr_UnitPipe_DenseTransferAck_kernel {
  def run(
  ): Unit = {
    val x230 = {
      val a0 = if (TRUE && x214.nonEmpty) x214.dequeue() else Bool(false,false)
      Array[Bool](a0)
    }
  } 
}
/** END UnitPipe x232_inr_UnitPipe_DenseTransferAck **/
