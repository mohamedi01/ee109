import emul._
import emul.implicits._

/** BEGIN UnitPipe x425_outr_UnitPipe_DenseTransfer **/
object x425_outr_UnitPipe_DenseTransfer_kernel {
  def run(
  ): Unit = {
    val x366_ctr = Counter(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)), FixedPoint(BigDecimal("80"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)))
    val x367_ctrchain = Array[Counterlike](x366_ctr)
    val x388_inr_Foreach = x388_inr_Foreach_kernel.run(x367_ctrchain)
    val x389 = x363.foreach{cmd => 
      for (i <- cmd.offset until cmd.offset+cmd.size by 4) {
        val data = {
          try {
            x343_matDram.apply(i / 4)
          }
          catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
            System.out.println("[warn] MelFilterbank.scala:34:15 Memory matDram: Out of bounds read at address " + err.getMessage)
            FloatPoint.invalid(FltFormat(23,8))
          }
        }
        x365.enqueue(data)
      }
    }
    x363.clear()
    val x390_ctr = Counter(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)), FixedPoint(BigDecimal("80"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)))
    val x391_ctrchain = Array[Counterlike](x390_ctr)
    val x424_outr_Foreach = x424_outr_Foreach_kernel.run(x391_ctrchain)
  } 
}
/** END UnitPipe x425_outr_UnitPipe_DenseTransfer **/
