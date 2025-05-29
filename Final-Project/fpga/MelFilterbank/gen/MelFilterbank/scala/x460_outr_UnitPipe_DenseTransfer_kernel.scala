import emul._
import emul.implicits._

/** BEGIN UnitPipe x460_outr_UnitPipe_DenseTransfer **/
object x460_outr_UnitPipe_DenseTransfer_kernel {
  def run(
  ): Unit = {
    val x435_inr_UnitPipe = x435_inr_UnitPipe_kernel.run()
    val x436 = x426.foreach{cmd => 
      for (i <- cmd.offset until cmd.offset+cmd.size by 4) {
        val data = {
          try {
            x344_vecDram.apply(i / 4)
          }
          catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
            System.out.println("[warn] MelFilterbank.scala:35:15 Memory vecDram: Out of bounds read at address " + err.getMessage)
            FloatPoint.invalid(FltFormat(23,8))
          }
        }
        x428.enqueue(data)
      }
    }
    x426.clear()
    val x459_outr_UnitPipe = x459_outr_UnitPipe_kernel.run()
  } 
}
/** END UnitPipe x460_outr_UnitPipe_DenseTransfer **/
