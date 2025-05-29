import emul._
import emul.implicits._

/** BEGIN UnitPipe x506_inr_UnitPipe_DenseTransferAck **/
object x506_inr_UnitPipe_DenseTransferAck_kernel {
  def run(
  ): Unit = {
    val x504 = {
      val a0 = if (TRUE && x488.nonEmpty) x488.dequeue() else Bool(false,false)
      Array[Bool](a0)
    }
  } 
}
/** END UnitPipe x506_inr_UnitPipe_DenseTransferAck **/
