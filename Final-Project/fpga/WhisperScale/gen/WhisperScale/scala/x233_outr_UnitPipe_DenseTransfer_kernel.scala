import emul._
import emul.implicits._

/** BEGIN UnitPipe x233_outr_UnitPipe_DenseTransfer **/
object x233_outr_UnitPipe_DenseTransfer_kernel {
  def run(
  ): Unit = {
    val x219_inr_UnitPipe_DenseTransferCommands = x219_inr_UnitPipe_DenseTransferCommands_kernel.run()
    val x220_ctr = Counter(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)), FixedPoint(BigDecimal("80"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)))
    val x221_ctrchain = Array[Counterlike](x220_ctr)
    val x228_inr_Foreach = x228_inr_Foreach_kernel.run(x221_ctrchain)
    val x229 = x212.foreach{cmd => 
      for (i <- cmd.offset until cmd.offset+cmd.size by 4) {
        val data = x213.dequeue()
        try {
          if (data._2) x159_outDram(i / 4) = data._1
        }
        catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
          System.out.println("[warn] WhisperScale.scala:49:15 Memory outDram: Out of bounds write at address " + err.getMessage)
        }
      }
      x214.enqueue(true)
    }
    x212.clear()
    val x232_inr_UnitPipe_DenseTransferAck = x232_inr_UnitPipe_DenseTransferAck_kernel.run()
  } 
}
/** END UnitPipe x233_outr_UnitPipe_DenseTransfer **/
