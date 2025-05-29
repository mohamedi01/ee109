import emul._
import emul.implicits._

/** BEGIN UnitPipe x908_outr_UnitPipe_DenseTransfer **/
object x908_outr_UnitPipe_DenseTransfer_kernel {
  def run(
  ): Unit = {
    val x883_inr_UnitPipe = x883_inr_UnitPipe_kernel.run()
    val x884 = x869.foreach{cmd => 
      for (i <- cmd.offset until cmd.offset+cmd.size by 4) {
        val data = {
          try {
            x842_inDram.apply(i / 4)
          }
          catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
            System.out.println("[warn] LogCompress.scala:59:11 Memory inDram: Out of bounds read at address " + err.getMessage)
            FloatPoint.invalid(FltFormat(23,8))
          }
        }
        x871.enqueue(data)
      }
    }
    x869.clear()
    val x907_outr_UnitPipe = x907_outr_UnitPipe_kernel.run()
  } 
}
/** END UnitPipe x908_outr_UnitPipe_DenseTransfer **/
