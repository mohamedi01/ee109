import emul._
import emul.implicits._

/** BEGIN UnitPipe x185_outr_UnitPipe_DenseTransfer **/
object x185_outr_UnitPipe_DenseTransfer_kernel {
  def run(
  ): Unit = {
    val x175_inr_UnitPipe_AlignedLoadCommand = x175_inr_UnitPipe_AlignedLoadCommand_kernel.run()
    val x176 = x169.foreach{cmd => 
      for (i <- cmd.offset until cmd.offset+cmd.size by 4) {
        val data = {
          try {
            x158_inDram.apply(i / 4)
          }
          catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
            System.out.println("[warn] WhisperScale.scala:29:14 Memory inDram: Out of bounds read at address " + err.getMessage)
            FloatPoint.invalid(FltFormat(23,8))
          }
        }
        x170.enqueue(data)
      }
    }
    x169.clear()
    val x177_ctr = Counter(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)), FixedPoint(BigDecimal("80"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)))
    val x178_ctrchain = Array[Counterlike](x177_ctr)
    val x184_inr_Foreach_AlignedLoadWrite = x184_inr_Foreach_AlignedLoadWrite_kernel.run(x178_ctrchain)
  } 
}
/** END UnitPipe x185_outr_UnitPipe_DenseTransfer **/
