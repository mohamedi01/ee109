import emul._
import emul.implicits._

/** BEGIN UnitPipe x348_outr_UnitPipe_DenseTransfer **/
object x348_outr_UnitPipe_DenseTransfer_kernel {
  def run(
  ): Unit = {
    val x323_inr_UnitPipe = x323_inr_UnitPipe_kernel.run()
    val x324 = x314.foreach{cmd => 
      for (i <- cmd.offset until cmd.offset+cmd.size by 4) {
        val data = {
          try {
            x294_realDram.apply(i / 4)
          }
          catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
            System.out.println("[warn] PowerSpectrum.scala:31:16 Memory realDram: Out of bounds read at address " + err.getMessage)
            FloatPoint.invalid(FltFormat(23,8))
          }
        }
        x316.enqueue(data)
      }
    }
    x314.clear()
    val x347_outr_UnitPipe = x347_outr_UnitPipe_kernel.run()
  } 
}
/** END UnitPipe x348_outr_UnitPipe_DenseTransfer **/
