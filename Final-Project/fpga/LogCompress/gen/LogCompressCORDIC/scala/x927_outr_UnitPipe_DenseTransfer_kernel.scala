import emul._
import emul.implicits._

/** BEGIN UnitPipe x927_outr_UnitPipe_DenseTransfer **/
object x927_outr_UnitPipe_DenseTransfer_kernel {
  def run(
  ): Unit = {
    val x915_inr_UnitPipe_AlignedLoadCommand = x915_inr_UnitPipe_AlignedLoadCommand_kernel.run()
    val x916 = x909.foreach{cmd => 
      for (i <- cmd.offset until cmd.offset+cmd.size by 4) {
        val data = {
          try {
            x844_xLutDram.apply(i / 4)
          }
          catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
            System.out.println("[warn] LogCompress.scala:62:16 Memory xLutDram: Out of bounds read at address " + err.getMessage)
            FloatPoint.invalid(FltFormat(23,8))
          }
        }
        x910.enqueue(data)
      }
    }
    x909.clear()
    val x917_ctr = Counter(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)), FixedPoint(BigDecimal("128"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)))
    val x918_ctrchain = Array[Counterlike](x917_ctr)
    val x926_inr_Foreach_AlignedLoadWrite = x926_inr_Foreach_AlignedLoadWrite_kernel.run(x918_ctrchain)
  } 
}
/** END UnitPipe x927_outr_UnitPipe_DenseTransfer **/
