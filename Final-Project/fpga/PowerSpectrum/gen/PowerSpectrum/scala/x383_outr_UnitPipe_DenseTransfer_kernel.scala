import emul._
import emul.implicits._

/** BEGIN UnitPipe x383_outr_UnitPipe_DenseTransfer **/
object x383_outr_UnitPipe_DenseTransfer_kernel {
  def run(
  ): Unit = {
    val x358_inr_UnitPipe = x358_inr_UnitPipe_kernel.run()
    val x359 = x349.foreach{cmd => 
      for (i <- cmd.offset until cmd.offset+cmd.size by 4) {
        val data = {
          try {
            x295_imagDram.apply(i / 4)
          }
          catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
            System.out.println("[warn] PowerSpectrum.scala:32:16 Memory imagDram: Out of bounds read at address " + err.getMessage)
            FloatPoint.invalid(FltFormat(23,8))
          }
        }
        x351.enqueue(data)
      }
    }
    x349.clear()
    val x382_outr_UnitPipe = x382_outr_UnitPipe_kernel.run()
  } 
}
/** END UnitPipe x383_outr_UnitPipe_DenseTransfer **/
