import emul._
import emul.implicits._

/** BEGIN UnitPipe x507_outr_UnitPipe_DenseTransfer **/
object x507_outr_UnitPipe_DenseTransfer_kernel {
  def run(
  ): Unit = {
    val x493_inr_UnitPipe_DenseTransferCommands = x493_inr_UnitPipe_DenseTransferCommands_kernel.run()
    val x494_ctr = Counter(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)), FixedPoint(BigDecimal("80"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)))
    val x495_ctrchain = Array[Counterlike](x494_ctr)
    val x502_inr_Foreach = x502_inr_Foreach_kernel.run(x495_ctrchain)
    val x503 = x486.foreach{cmd => 
      for (i <- cmd.offset until cmd.offset+cmd.size by 4) {
        val data = x487.dequeue()
        try {
          if (data._2) x345_outDram(i / 4) = data._1
        }
        catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
          System.out.println("[warn] MelFilterbank.scala:44:15 Memory outDram: Out of bounds write at address " + err.getMessage)
        }
      }
      x488.enqueue(true)
    }
    x486.clear()
    val x506_inr_UnitPipe_DenseTransferAck = x506_inr_UnitPipe_DenseTransferAck_kernel.run()
  } 
}
/** END UnitPipe x507_outr_UnitPipe_DenseTransfer **/
