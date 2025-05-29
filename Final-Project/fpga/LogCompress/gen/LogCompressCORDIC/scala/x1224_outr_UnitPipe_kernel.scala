import emul._
import emul.implicits._

/** BEGIN UnitPipe x1224_outr_UnitPipe **/
object x1224_outr_UnitPipe_kernel {
  def run(
  ): Unit = {
    val x1219_outr_UnitPipe = x1219_outr_UnitPipe_kernel.run()
    val x1220 = x1188.foreach{cmd => 
      for (i <- cmd.offset until cmd.offset+cmd.size by 4) {
        val data = x1189.dequeue()
        try {
          if (data._2) x843_outDram(i / 4) = data._1
        }
        catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
          System.out.println("[warn] LogCompress.scala:180:15 Memory outDram: Out of bounds write at address " + err.getMessage)
        }
      }
      x1190.enqueue(true)
    }
    x1188.clear()
    val x1223_inr_UnitPipe = x1223_inr_UnitPipe_kernel.run()
  } 
}
/** END UnitPipe x1224_outr_UnitPipe **/
