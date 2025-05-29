import emul._
import emul.implicits._

/** BEGIN UnitPipe x1160_outr_UnitPipe **/
object x1160_outr_UnitPipe_kernel {
  def run(
  ): Unit = {
    val x1155_outr_UnitPipe = x1155_outr_UnitPipe_kernel.run()
    val x1156 = x1124.foreach{cmd => 
      for (i <- cmd.offset until cmd.offset+cmd.size by 4) {
        val data = x1125.dequeue()
        try {
          if (data._2) x846_rawLogOutDram(i / 4) = data._1
        }
        catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
          System.out.println("[warn] LogCompress.scala:156:21 Memory rawLogOutDram: Out of bounds write at address " + err.getMessage)
        }
      }
      x1126.enqueue(true)
    }
    x1124.clear()
    val x1159_inr_UnitPipe = x1159_inr_UnitPipe_kernel.run()
  } 
}
/** END UnitPipe x1160_outr_UnitPipe **/
