import emul._
import emul.implicits._

/** BEGIN UnitPipe x945_outr_UnitPipe_DenseTransfer **/
object x945_outr_UnitPipe_DenseTransfer_kernel {
  def run(
  ): Unit = {
    val x934_inr_UnitPipe_AlignedLoadCommand = x934_inr_UnitPipe_AlignedLoadCommand_kernel.run()
    val x935 = x928.foreach{cmd => 
      for (i <- cmd.offset until cmd.offset+cmd.size by 4) {
        val data = {
          try {
            x845_yLutDram.apply(i / 4)
          }
          catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
            System.out.println("[warn] LogCompress.scala:63:16 Memory yLutDram: Out of bounds read at address " + err.getMessage)
            FloatPoint.invalid(FltFormat(23,8))
          }
        }
        x929.enqueue(data)
      }
    }
    x928.clear()
    val x936_ctr = Counter(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)), FixedPoint(BigDecimal("128"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)))
    val x937_ctrchain = Array[Counterlike](x936_ctr)
    val x944_inr_Foreach_AlignedLoadWrite = x944_inr_Foreach_AlignedLoadWrite_kernel.run(x937_ctrchain)
  } 
}
/** END UnitPipe x945_outr_UnitPipe_DenseTransfer **/
