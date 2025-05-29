import emul._
import emul.implicits._

/** BEGIN UnitPipe x428_outr_UnitPipe **/
object x428_outr_UnitPipe_kernel {
  def run(
  ): Unit = {
    val x423_outr_UnitPipe = x423_outr_UnitPipe_kernel.run()
    val x424 = x397.foreach{cmd => 
      for (i <- cmd.offset until cmd.offset+cmd.size by 4) {
        val data = x398.dequeue()
        try {
          if (data._2) x296_outDram(i / 4) = data._1
        }
        catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
          System.out.println("[warn] PowerSpectrum.scala:36:15 Memory outDram: Out of bounds write at address " + err.getMessage)
        }
      }
      x399.enqueue(true)
    }
    x397.clear()
    val x427_inr_UnitPipe = x427_inr_UnitPipe_kernel.run()
  } 
}
/** END UnitPipe x428_outr_UnitPipe **/
